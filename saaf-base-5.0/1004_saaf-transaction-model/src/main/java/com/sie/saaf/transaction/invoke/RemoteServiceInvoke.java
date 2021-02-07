package com.sie.saaf.transaction.invoke;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommonTransactionConfirm;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.redis.framework.JedisClusterCore;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;

/**
 *  通用服务调用类
 *  用于消息确认,预发送消息保存redis，消息发送
 *  author: duzh
 *  date: 2018-06-12
 */
@Component
public class RemoteServiceInvoke extends JedisClusterCore {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RemoteServiceInvoke.class);
    public static final String REDIS_KEY_STRING = "TRANS_CORE_SEQUENCE";
    public static final String REDIS_KEY_NAME = "MQ_Trans_Cons_Core_";
    public static final String TRANS_KEYS = "TRANS_CORE_KEYS";
    public static final String REDIS_KEY_QUEUE_NAME = "Transaction_Consistency_Queue";


    private static RemoteServiceInvoke remoteServiceInvoke;

    private  ProducerService producerService;

    @Resource(name = "producerService")
    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    /**
     * 用于保存主动方的预发送消息
     */
    private static ThreadLocal<Set<String>> threadLocal = new ThreadLocal(){

        @Override
        protected Set<String> initialValue() {
            return new HashSet<>();
        }

    };


    /**
     *  用于静态方法中使用注入的对象
     */
    @PostConstruct
    public void init() {
        remoteServiceInvoke = this;
        remoteServiceInvoke.jedisCluster = this.jedisCluster;
        remoteServiceInvoke.baseCommonTransactionConfirmServer = this.baseCommonTransactionConfirmServer;
        remoteServiceInvoke.producerService=this.producerService;
    }


    @Autowired
    private IBaseCommonTransactionConfirm baseCommonTransactionConfirmServer;


    public static Set<String>  getMessageIds(){
        Set<String> messageIds=  new HashSet<>();
        if (threadLocal.get()!=null){
            messageIds.addAll(threadLocal.get());
        }
        return  messageIds;
    }

    public static void   clearMessageIds(){
        threadLocal.remove();
    }



    /**
     * 业务系统执行调用服务之前发送消息到Redis中
     * 包括消息体，执行消息的URL或者MQ名称
     * 并设置当前消息的状态为 0，表示未发送 NO_SEND
     */
    public static void sendMessageBody2Redis(RedisMessageContentBean redisMessageContentBean){
        Long indexValue = remoteServiceInvoke.jedisCluster.incr(REDIS_KEY_STRING);
        remoteServiceInvoke.baseCommonTransactionConfirmServer.saveMessageSendConfirm(indexValue.intValue());
        //判断如果indexValue的值超过2147483647，则将redis中REDIS_KEY_STRING的key重置为1
        if(indexValue > 2147483646){
            indexValue = remoteServiceInvoke.jedisCluster.incrBy(REDIS_KEY_STRING, -2147483646);
        }
        redisMessageContentBean.setMessageIndex(indexValue);
        redisMessageContentBean.setStatus(0);
        redisMessageContentBean.setCreationDate(new Date());
        redisMessageContentBean.setSendQueueTimes(0);
        String key=REDIS_KEY_NAME + indexValue;
        remoteServiceInvoke.jedisCluster.hmset(key, getTransactionCompensationMap(redisMessageContentBean));
        remoteServiceInvoke.jedisCluster.sadd(TRANS_KEYS,key);
        /**
         *
         * 主动方应用，预发送消息,并将消息ID写入线程变量中
         *
         */
        threadLocal.get().add(indexValue.toString());
    }


    public static Map<String,String> getTransactionCompensationMap(Object bean){
        if (bean==null) {
            return new HashMap<>();
        }
        try {
            Map<String,String> map=new HashMap<>();
            Method method[] = bean.getClass().getDeclaredMethods();
            for (Method m : method) {
                if (m.getName().startsWith("get")){
                    Object object= m.invoke(bean);
                    if (object==null) {
                        continue;
                    }
                    String fieldName=m.getName().replace("get","");
                    if (fieldName.length()==0) {
                        continue;
                    }
                    fieldName=fieldName.substring(0,1).toLowerCase()+fieldName.substring(1,fieldName.length());
                    if ("java.util.Date".equals(m.getReturnType().getName())){
                        map.put(fieldName, SaafDateUtils.convertDateToString((Date) object));
                        continue;
                    }
                    map.put(fieldName,object+"");
                }
            }
            return map;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        return new HashMap<>();
    }


    /**
     * 业务系统自身事务执行完毕之后将Redis中的消息发送到MQ中
     * 业务系统自身事务执行失败将Redis中的消息删除掉.
     * 同时修改Redis的消息的状态为1，表示已发送，SEND
     */
    public static void sendRedisMessage2MQ(Long messageIndex){
        sendRedisMessage2MQ(messageIndex,true);
    }

    public static void sendRedisMessage2MQ(Long messageIndex,boolean autoCompensate){
        StringBuilder key = new StringBuilder(REDIS_KEY_NAME + messageIndex);

        Map<String, String> redisMessageContentBeanMap = remoteServiceInvoke.jedisCluster.hgetAll(key.toString());

        Assert.isTrue(StringUtils.isNotBlank(redisMessageContentBeanMap.get("messageBody")),"hashkey[#]消息内容在redis中不存在".replace("#", key.toString()));

        String queueName = redisMessageContentBeanMap.get("queueName");
        if (null == queueName || "".equals(queueName)) {
            queueName = REDIS_KEY_QUEUE_NAME;
        }

        /**
         * 调用mq
         */
        ActiveMQQueue queueDestination_ = new ActiveMQQueue(queueName);
        int status = autoCompensate ? 1 : 2;
        redisMessageContentBeanMap.put("status", status + "");
        remoteServiceInvoke.jedisCluster.hset(key.toString(),"status",status+"");
        remoteServiceInvoke.producerService.sendMessage(queueDestination_,JSONObject.toJSONString(redisMessageContentBeanMap));
        remoteServiceInvoke.jedisCluster.hincrBy(key.toString(),"sendQueueTimes",1);
    }



}
