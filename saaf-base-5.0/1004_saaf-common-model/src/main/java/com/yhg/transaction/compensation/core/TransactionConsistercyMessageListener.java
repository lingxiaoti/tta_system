package com.yhg.transaction.compensation.core;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import com.yhg.redis.framework.JedisClusterCore;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Objects;

/**
 *  @author huangtao
 *  @createTime 2018年5月21日 11:49:26
 *
 */
public abstract  class TransactionConsistercyMessageListener extends JedisClusterCore implements MessageListener {

    private static final Logger log= LoggerFactory.getLogger(TransactionConsistercyMessageListener.class);

    @Autowired
    private TransactionConsistencyCore transactionConsistencyCore;

    /**
     * 事务补偿消 MQ消费者
     * 1、检测消息状态
     *     消息状态为1或者2
     *     分布式锁
     *     redis中是否存在消息内容
     *     查询消息消费表，确认消息是否消费
     * 2、检测消息状态通过，执行消费端业务逻辑
     * 3.、消费端业务逻辑执行成功，移除redis，数据库中相关消息内容；执行失败，重新入队自动补偿，自动补偿6失败后写入数据库消息补偿表
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage == false) {
            return;
        }
        TextMessage textMessage = (TextMessage)message;
        String message_ = null;
        boolean isSuccess = false;
        try {
            message_ = textMessage.getText();
            log.info("TransactionConsistercyMessageListener MQ出队==={}",message_);

            RedisMessageContentBean redisMessageContentBean = JSONObject.parseObject(message_, RedisMessageContentBean.class);
            log.info("            ---------------" + redisMessageContentBean.getMessageIndex() + "\t" + redisMessageContentBean.getStatus());
            if (Objects.equals(1,redisMessageContentBean.getStatus())==false && Objects.equals(2,redisMessageContentBean.getStatus())==false){
                log.warn("{}消息状态为{},消费者不执行",redisMessageContentBean.getRequestURL(),redisMessageContentBean.getStatus());
                return;
            }
            Long messageIndex = redisMessageContentBean.getMessageIndex();
            String requestURL = redisMessageContentBean.getRequestURL();
            String messageBody = redisMessageContentBean.getMessageBody();
            String tempMessageBodyKey = TransactionConsistencyCore.REDIS_KEY_NAME + messageIndex;

            //检查消息是否可执行
            String messageStatus = TransactionToolUtils.checkMessageStatus(transactionConsistencyCore, messageIndex, this);
            if(!"S".equals(messageStatus)){
                //如果此方法执行结果为true表示当前消息被锁定
                log.warn("messageIndex[{}]检查消息状态{},消费者不执行",redisMessageContentBean.getMessageIndex(),messageStatus);
                return;
            }


            try {
                isSuccess=messageAction(redisMessageContentBean);

            } catch (Exception e) {
                isSuccess = false;
                log.error("Error the messageIndex is {}, please check you source code or the message body {}", messageIndex, e.getMessage());
            } finally {
                del(TransactionToolUtils.LOCK_STR + messageIndex);
                if(isSuccess){
                    transactionConsistencyCore.releaseKey(messageIndex.intValue());
                }else if (redisMessageContentBean != null && Objects.equals(2,redisMessageContentBean.getStatus())){
                    //非自动补偿执行失败后直接将补偿数据写入到数据库
                    BaseManualSupplementEntity_HI manualSupplementEntityHi = new BaseManualSupplementEntity_HI();
                    manualSupplementEntityHi.setMessageIndex(messageIndex.intValue());
                    manualSupplementEntityHi.setMessageBody(messageBody);
                    manualSupplementEntityHi.setRequestUrl(requestURL);
                    manualSupplementEntityHi.setQueueName(redisMessageContentBean.getQueueName());
                    //manualSupplementEntityHi.setStatus(redisMessageContentBeanMap.get("status"));
                    manualSupplementEntityHi.setSendQueueTimes(0);
                    manualSupplementEntityHi.setStatus("0");
                    manualSupplementEntityHi.setMessageContentBean(new JSONObject().fluentPut("creationDate",redisMessageContentBean.getCreationDate()).toJSONString());
                    if (transactionConsistencyCore.autoSaveMessage2DB(manualSupplementEntityHi)){
                        del(tempMessageBodyKey);
                        jedisCluster.srem(transactionConsistencyCore.TRANS_KEYS,tempMessageBodyKey);
                    }
                }else{
                    //执行失败自动补偿
                    transactionConsistencyCore.sendRedisMessage2MQ(messageIndex,true);
                }
            }
        } catch (JMSException e) {
            log.error(e.getMessage());
        }

    }


    /**
     *  子类重写此方法，实现mq订阅端的处理逻辑
     *  处理成功时返回true
     *  处理失败时返回false
     * @param redisMessageContentBean
     * @return
     */
    public abstract boolean messageAction(RedisMessageContentBean redisMessageContentBean);

}
