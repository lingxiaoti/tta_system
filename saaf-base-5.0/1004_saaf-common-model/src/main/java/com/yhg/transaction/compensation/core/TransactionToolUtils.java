package com.yhg.transaction.compensation.core;

import com.yhg.redis.framework.JedisClusterCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionToolUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConsistencyMQClient.class);
    //确保操作的原子性，从获取状态到修改状态放到一个操作里面执行完成
    private static final String LUA_LOCK_SCRIPT = "local value = redis.call('get', KEYS[1])\n" +
            "if ( value == \"S\" )\n" +
            "then\n" +
            "return value\n" +
            "else\n" +
            "redis.call('set', KEYS[1], \"S\")\n" +
            "return \"N\"\n" +
            "end\n";
    public static final String LOCK_STR = "LOCK_";
    /*
     * 判断当前的消息在Redis中是否处于锁定状态
     * 如果此方法执行结果为false表示没有被锁定，表示当前线程可以执行此消息的消费
     * 如果此方法执行结果为true表示当前消息被锁定
     */
    public static Boolean messageIsLockedInRedis(TransactionConsistencyCore transactionConsistencyCore, Long messageIndex, JedisClusterCore jedisClusterCore){
        Boolean alreadyLockedFlag = false;
        String tempLockMessageKey = LOCK_STR + messageIndex;
        String lockStatus = jedisClusterCore.exectScript(LUA_LOCK_SCRIPT, tempLockMessageKey, "S");
        //取出锁的状态，如果该消息的锁的状态是S表示已被其他客户端锁定, 返回N表示没有被锁定，当前线程可以操作
        if(null != lockStatus && lockStatus.equals("S")){
            LOGGER.warn("Warning Code [{}] The {} key is locked in redis so the message will send to queue again ", 5002, TransactionConsistencyCore.REDIS_KEY_NAME + messageIndex);
            //将消息重新入队等待下一次执行
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                LOGGER.error("Error the messageIndex is {}, please check you source code or the message body {}", messageIndex, e.getMessage());
            }
            transactionConsistencyCore.sendRedisMessage2MQ(messageIndex, true);
            alreadyLockedFlag = true;
        }else{
            alreadyLockedFlag = false;
        }
        return alreadyLockedFlag;
    }


    /**
     *
     * @param messageIndex
     * @return L 表示redis中处于锁定，N表示Redis中不存在，C表示已经处理过，S表示可以进行处理
     */
    public static String checkMessageStatus(TransactionConsistencyCore transactionConsistencyCore, Long messageIndex, JedisClusterCore jedisClusterCore){
        //如果此方法执行结果为true表示当前消息被锁定
        Boolean lockFlag = TransactionToolUtils.messageIsLockedInRedis(transactionConsistencyCore, messageIndex, jedisClusterCore);
        if(lockFlag){
            return "L";
        }
        String tempMessageBodyKey= transactionConsistencyCore.REDIS_KEY_NAME + messageIndex;
        //如果消息在redis中不存在说明消息可能已经被销毁了，不需要再进行处理
        if(jedisClusterCore.hashMapExit(tempMessageBodyKey)==false) {
            LOGGER.warn("Warning Code [{}] The {} key is not exist in redis, or the key's value is null, please check ", 5001, TransactionConsistencyCore.REDIS_KEY_NAME + messageIndex);
            return "N";
    }
        if (transactionConsistencyCore.transactionConfirm(messageIndex.intValue())){
        return "C";
    }
        return "S";
    }

}
