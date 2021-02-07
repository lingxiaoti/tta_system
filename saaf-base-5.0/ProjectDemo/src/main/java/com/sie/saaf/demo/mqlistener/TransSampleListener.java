package com.sie.saaf.demo.mqlistener;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.user.model.entities.BaseChannelEntity_HI;
import com.sie.saaf.demo.model.inter.server.TransactionCompensationSqmpleServer;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * 事务一致性 消费者样例代码
 *
 */
@Component
public class TransSampleListener implements MessageListener {
    private static final Logger log= LoggerFactory.getLogger(TransSampleListener.class);

    @Autowired
    private TransactionCompensationSqmpleServer transactionCompensationSqmpleServer;


    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "transQueue",concurrency = "2")
    public void onMessage(Message message) {

        if (! (message instanceof TextMessage))
            return;
        TextMessage textMessage= (TextMessage) message;
        try {
            String text= textMessage.getText();
            RedisMessageContentBean redisMessageContentBean= JSON.parseObject(text,RedisMessageContentBean.class);
            log.info("消费者收到消息:{}",text);
            //执行带事务注解的方法
            transactionCompensationSqmpleServer.saveTransMessageConsumer(redisMessageContentBean.getMessageIndex(),JSON.parseObject(redisMessageContentBean.getMessageBody(), BaseChannelEntity_HI.class));
            log.info("MQ消费者执行完成");
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }


    }
}
