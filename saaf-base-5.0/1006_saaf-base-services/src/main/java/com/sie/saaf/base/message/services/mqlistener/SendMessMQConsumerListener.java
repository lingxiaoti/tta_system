package com.sie.saaf.base.message.services.mqlistener;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.message.model.inter.server.BaseMessageContentServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * 根据待发送消息分组分批发送站内信
 * @auther: huqitao 2018/8/15
 */
@Component("sendMessMQConsumerListener")
public class SendMessMQConsumerListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessMQConsumerListener.class);

    @Autowired
    public BaseMessageContentServer baseMessageContentServer;

    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "sendMessQueue",concurrency = "5-10")
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        try {
            String message_ = textMessage.getText();
            RedisMessageContentBean redisMessageContentBean = JSON.parseObject(message_, RedisMessageContentBean.class);
            baseMessageContentServer.saveTransMessageConsumer(redisMessageContentBean.getMessageIndex(), JSON.parseObject(redisMessageContentBean.getMessageBody()));
            LOGGER.info("=========根据待发送消息分组分批发送站内信，MQ消费者执行完成=========");
        } catch (JMSException e) {
            LOGGER.error("出队异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()), e);
        }
    }
}
