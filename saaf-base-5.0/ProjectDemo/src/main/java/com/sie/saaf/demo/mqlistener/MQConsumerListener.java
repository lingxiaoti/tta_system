package com.sie.saaf.demo.mqlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 * MQ 消费者
 */
public class MQConsumerListener implements MessageListener {

    private Logger log= LoggerFactory.getLogger(MQConsumerListener.class);

    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "messageQueue",concurrency = "5-10")
    public void onMessage(Message message) {
        TextMessage textMessage= (TextMessage) message;
        try {
            log.info("接受消息:{}",textMessage.getText());
        } catch (JMSException e) {
            log.error(e.getMessage(),e);
        }
    }

}
