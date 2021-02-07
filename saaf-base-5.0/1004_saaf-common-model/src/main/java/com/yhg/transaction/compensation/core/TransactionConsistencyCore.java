package com.yhg.transaction.compensation.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseCommonTransactionConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommonMessageConfirm;
import com.sie.saaf.common.model.inter.IBaseCommonTransactionConfirm;
import com.sie.saaf.common.model.inter.IBaseManualSupplement;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.redis.framework.JedisClusterCore;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.ScanResult;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionConsistencyCore extends JedisClusterCore {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TransactionConsistencyCore.class);
    public static final String REDIS_KEY_STRING = "TRANS_CORE_SEQUENCE";
    public static final String REDIS_KEY_QUEUE_NAME = "Transaction_Consistency_Queue";
    public static final String REDIS_KEY_NAME = "MQ_Trans_Cons_Core_";
    public static final String TRANS_KEYS = "TRANS_CORE_KEYS";
    private ProducerService producerService;
    private RestTemplate restTemplate;

//    @Autowired
//    private IBaseManualSupplement baseManualSupplementServer;

    @Autowired
    private ViewObject<BaseManualSupplementEntity_HI> baseManualSupplementDAO_HI;

    @Autowired
    private IBaseCommonMessageConfirm baseCommonMessageConfirmServer;

    @Autowired
    private IBaseCommonTransactionConfirm baseCommonTransactionConfirmServer;

    private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public TransactionConsistencyCore() {
        super();
    }

    /**
     * 业务系统调用服务之前发送消息到Redis中
     *
     * @param requestURL  调用的服务
     * @param messageBody 服务参数
     * @return {@link RedisMessageContentBean}
     */
    public RedisMessageContentBean sendMessageBody2Redis(String requestURL, String messageBody) {

        RedisMessageContentBean redisMessageContentBean = new RedisMessageContentBean();
        redisMessageContentBean.setRequestURL(requestURL);
        redisMessageContentBean.setMessageBody(messageBody);
        return this.sendMessageBody2Redis(redisMessageContentBean);
    }

    /**
     * 业务系统执行调用服务之前发送消息到Redis中
     * 包括消息体，执行消息的URL或者MQ名称
     * 并设置当前消息的状态为 0，表示未发送 NO_SEND
     */
    public RedisMessageContentBean sendMessageBody2Redis(RedisMessageContentBean redisMessageContentBean) {
        Long indexValue = this.incr(REDIS_KEY_STRING);
        baseCommonTransactionConfirmServer.saveMessageSendConfirm(indexValue.intValue());
        //判断如果indexValue的值超过2147483647，则将redis中REDIS_KEY_STRING的key重置为1
        if (indexValue > 2147483646) {
            indexValue = jedisCluster.incrBy(REDIS_KEY_STRING, -2147483646);
        }
        redisMessageContentBean.setMessageIndex(indexValue);
        redisMessageContentBean.setStatus(0);
        redisMessageContentBean.setCreationDate(new Date());
        redisMessageContentBean.setSendQueueTimes(0);
        String key = REDIS_KEY_NAME + indexValue;
        jedisCluster.hmset(key, getTransactionCompensationMap(redisMessageContentBean));
        jedisCluster.sadd(TRANS_KEYS, key);
        return redisMessageContentBean;
    }

    public RedisMessageContentBean sendMessageBody2Redis(Integer messageIndex, String messageBody, String requestUrl, String queueName) {
        baseCommonTransactionConfirmServer.saveMessageSendConfirm(messageIndex);
        RedisMessageContentBean redisMessageContentBean = new  RedisMessageContentBean();
        redisMessageContentBean.setMessageIndex(messageIndex.longValue());
        redisMessageContentBean.setMessageBody(messageBody);
        redisMessageContentBean.setRequestURL(requestUrl);
        redisMessageContentBean.setQueueName(queueName);
        redisMessageContentBean.setMessageIndex(messageIndex.longValue());
        redisMessageContentBean.setStatus(0);
        redisMessageContentBean.setCreationDate(new Date());
        redisMessageContentBean.setSendQueueTimes(0);
        String key = REDIS_KEY_NAME + messageIndex.longValue();
        jedisCluster.hmset(key, getTransactionCompensationMap(redisMessageContentBean));
        jedisCluster.sadd(TRANS_KEYS, key);
        return redisMessageContentBean;
    }

    /**
     * 业务系统自身事务执行完毕之后将Redis中的消息发送到MQ中
     * 业务系统自身事务执行失败将Redis中的消息删除掉.
     * 同时修改Redis的消息的状态为1，表示已发送，SEND
     */
    public void sendRedisMessage2MQ(Long messageIndex, Boolean executeFlag) {
        sendRedisMessage2MQ(messageIndex, executeFlag, true);
    }

    public void sendRedisMessage2MQ(Long messageIndex, Boolean executeFlag, boolean autoCompensate) {
        StringBuilder key = new StringBuilder(REDIS_KEY_NAME + messageIndex);
        if (executeFlag == false) {
            releaseKey(messageIndex.intValue());
            return;
        }

        Map<String, String> redisMessageContentBeanMap = jedisCluster.hgetAll(key.toString());
        Assert.isTrue(StringUtils.isNotBlank(redisMessageContentBeanMap.get("messageBody")), "hashkey[#]消息内容在redis中不存在".replace("#", key.toString()));

        String queueName = redisMessageContentBeanMap.get("queueName");
        if (null == queueName || "".equals(queueName)) {
            queueName = REDIS_KEY_QUEUE_NAME;
        }

        if (Integer.parseInt(redisMessageContentBeanMap.get("sendQueueTimes")) >= 6) {
            //将次消息发送到数据库补偿消息中，等待人工补偿
            BaseManualSupplementEntity_HI manualSupplementEntityHi = new BaseManualSupplementEntity_HI();
            manualSupplementEntityHi.setMessageIndex(Integer.parseInt(redisMessageContentBeanMap.get("messageIndex")));
            manualSupplementEntityHi.setMessageBody(redisMessageContentBeanMap.get("messageBody"));
            manualSupplementEntityHi.setRequestUrl(redisMessageContentBeanMap.get("requestURL"));
            manualSupplementEntityHi.setQueueName(redisMessageContentBeanMap.get("queueName"));
            //manualSupplementEntityHi.setStatus(redisMessageContentBeanMap.get("status"));
            manualSupplementEntityHi.setSendQueueTimes(0);
            manualSupplementEntityHi.setStatus("0");
            manualSupplementEntityHi.setMessageContentBean(new JSONObject().fluentPut("creationDate", redisMessageContentBeanMap.get("creationDate")).toJSONString());
            //执行成功之后，将Redis中的内容清理掉 注意这里需要事务的一致性，如果没有完整的保存到数据库中，则不进行数据的清理
            //这里需要单独给出事务
            Boolean flag = autoSaveMessage2DB(manualSupplementEntityHi);
            //this.del(new String[]{});
            if (flag) {
                jedisCluster.del(key.toString());
                jedisCluster.del(TransactionToolUtils.LOCK_STR + messageIndex);
                jedisCluster.srem(TRANS_KEYS, key.toString());
            }
        } else {
            //开始执行入队操作
            ActiveMQQueue queueDestination_ = new ActiveMQQueue(queueName);
            int status = autoCompensate ? 1 : 2;
            redisMessageContentBeanMap.put("status", status + "");
            jedisCluster.hset(key.toString(), "status", status + "");
            producerService.sendMessage(queueDestination_, JSONObject.toJSONString(redisMessageContentBeanMap));
            jedisCluster.hincrBy(key.toString(), "sendQueueTimes", 1);
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Boolean autoSaveMessage2DB(BaseManualSupplementEntity_HI manualSupplementEntityHi) {
        Boolean executeFlag = false;
        try {
            baseManualSupplementDAO_HI.save(manualSupplementEntityHi);
            executeFlag = true;
        } catch (Exception e) {
            LOGGER.error("Error Code [4001] save message to tidb have exception {}", e.getMessage());
            executeFlag = true;
        }
        return executeFlag;
    }

    /**
     * 避免业务系统自身事务失败导致的不一致，批量处理redis消息发送
     * 写入REDIS用作补偿
     * 写入MQ调用实际服务,并通过MQ保证消息得到一次成功消费
     * redis操作采用异步线程完成，保证主事务的正确完成,避免事务不一致
     * 最终一致需避免主事务失败，消息消费，及重复消费问题的产生
     * 备注：当消息消费时，存在redis操作超时（REDIS消息被删除），消费程序回滚的情形，此问题忽略
     * warn: 服务调用需放到方法体最后部分处理
     *
     * @param messageIndex 需要发送的的消息ID
     */
    public void sendMessageRedisbBatch(Long... messageIndex) {
        sendMessageRedisbBatch(true, messageIndex);
    }

    /**
     * @param autoCompensate 是否自动补偿
     * @param messageIndex
     */
    public void sendMessageRedisbBatch(boolean autoCompensate, Long... messageIndex) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (Long id : messageIndex) {
                    sendRedisMessage2MQ(id, true, autoCompensate);
                }
            }
        };
        pool.submit(runnable);
    }

    public static Map<String, String> getTransactionCompensationMap(Object bean) {
        if (bean == null)
            return new HashMap<>();
        try {
            Map<String, String> map = new HashMap<>();
            Method method[] = bean.getClass().getDeclaredMethods();
            for (Method m : method) {
                if (m.getName().startsWith("get")) {
                    Object object = m.invoke(bean);
                    if (object == null)
                        continue;
                    String fieldName = m.getName().replace("get", "");
                    if (fieldName.length() == 0)
                        continue;
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1, fieldName.length());
                    LOGGER.info(fieldName);
                    if ("java.util.Date".equals(m.getReturnType().getName())) {
                        map.put(fieldName, SaafDateUtils.convertDateToString((Date) object));
                        continue;
                    }
                    map.put(fieldName, object + "");
                }
            }
            return map;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new HashMap<>();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void releaseKey(Integer messageIndex) {
        String tempMessageBodyKey = TransactionConsistencyCore.REDIS_KEY_NAME + messageIndex;
        del(new String[]{tempMessageBodyKey, TransactionToolUtils.LOCK_STR + messageIndex});
        jedisCluster.srem(TRANS_KEYS, tempMessageBodyKey);
        List<BaseManualSupplementEntity_HI> list = baseManualSupplementDAO_HI.findByProperty("messageIndex", messageIndex);
        if (list.size() == 0)
            return;
        baseManualSupplementDAO_HI.delete(list);
    }


    /**
     * 通过消息确认表判断消息是否已被消费
     *
     * @param messageIndex
     * @return true:已消费，fasle：未消费
     */
    @Transactional
    public boolean transactionConfirm(Integer messageIndex) {
        BaseCommonTransactionConfirmEntity_HI obj = baseCommonTransactionConfirmServer.findByMessageIndex(messageIndex);
        //如果已消费，删除redis中消息内容
        if (obj != null) {
            String tempMessageBodyKey = TransactionConsistencyCore.REDIS_KEY_NAME + messageIndex;
            del(new String[]{tempMessageBodyKey});
            jedisCluster.srem(TRANS_KEYS, tempMessageBodyKey);
        }
        return !(obj == null);
    }

    /**
     * MQ延迟投递
     * @param queueName 队列名称
     * @param mqParamsJson 入队参数
     * @param delayDateSecond 延迟时间，单位：秒
     */
    public void delaySendMessage(String queueName, JSONObject mqParamsJson, Long delayDateSecond) {
        ActiveMQQueue queueDestination = new ActiveMQQueue(queueName);
        producerService.delaySendMessage(queueDestination, JSON.toJSONString(mqParamsJson), delayDateSecond);
    }

    /**
     * MQ入队
     * @param queueName 队列名称
     * @param mqParams 入队参数
     */
    public void sendMessage(String queueName, String mqParams) {
        ActiveMQQueue queueDestination = new ActiveMQQueue(queueName);
        producerService.sendMessage(queueDestination, mqParams);
    }

    /**
     * 分批清除redis中的数据
     */
    public void scanClearRedisData() {
        String redisCursor = "0";
        do {
            redisCursor = cleanUpRedisTransDatasByBatch(redisCursor);
        } while (!redisCursor.equals("0") && !redisCursor.isEmpty());
    }

    public String cleanUpRedisTransDatasByBatch(String redisCursor) {
        if (StringUtils.isBlank(redisCursor)) {
            redisCursor = "0";
        }
        ScanResult<String> scanResult = jedisCluster.sscan(TRANS_KEYS, redisCursor);
        redisCursor = scanResult.getStringCursor();
        if (redisCursor.equals("0") || redisCursor.isEmpty()) {
            return redisCursor;
        }
        if (scanResult == null || scanResult.getResult() == null || scanResult.getResult().size() == 0) {
            return redisCursor;
        }
        List<String> keyList = scanResult.getResult();
        cleanUpRedisMessageContent(keyList);
        return redisCursor;
    }

    public void cleanUpRedisMessageContent(List<String> keyList) {
        int cleanUpNum = 0;
        for (int i = 0; i < keyList.size(); i++) {
            Map<String, String> redisValueMap = jedisCluster.hgetAll(keyList.get(i));
            RedisMessageContentBean redisMessageContentBean = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(redisValueMap)), RedisMessageContentBean.class);
            if (redisMessageContentBean == null || redisValueMap.size() == 0) {
                jedisCluster.srem(TRANS_KEYS, keyList.get(i));
                cleanUpNum++;
                continue;
            }
            Long messageIndex = redisMessageContentBean.getMessageIndex();
            Date creationDate = redisMessageContentBean.getCreationDate();
            Date before7Day = SaafDateUtils.getNextDay(new Date(), -7);
            if ((before7Day.compareTo(creationDate)) == 1) {
                //当前时间 - 7天 > creationDate
                List<BaseCommonMessageConfirmEntity_HI> confirmEntity = baseCommonMessageConfirmServer.findByProperty("messageIndex", messageIndex.intValue());
                if (confirmEntity == null || confirmEntity.size() == 0) {
                    jedisCluster.srem(TRANS_KEYS, keyList.get(i));
                    jedisCluster.del(keyList.get(i));
                    cleanUpNum++;
                }
            }
        }
        LOGGER.info("本次清除TRANS_CORE_KEYS中的数量:{}", cleanUpNum);
    }

    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    public ProducerService getProducerService() {
        return producerService;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
