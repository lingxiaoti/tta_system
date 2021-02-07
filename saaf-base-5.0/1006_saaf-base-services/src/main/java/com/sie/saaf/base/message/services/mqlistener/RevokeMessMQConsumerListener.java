package com.sie.saaf.base.message.services.mqlistener;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.message.model.inter.server.BaseMessageInstationServer;
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
 * @auther: huqitao 2018/8/15
 */
@Component("revokeMessMQConsumerListener")
public class RevokeMessMQConsumerListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RevokeMessMQConsumerListener.class);

    @Autowired
    public BaseMessageInstationServer baseMessageInstationServer;

    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "revokeMessQueue",concurrency = "5")
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        try {
            String message_ = textMessage.getText();
            RedisMessageContentBean redisMessageContentBean = JSON.parseObject(message_, RedisMessageContentBean.class);
            baseMessageInstationServer.saveTransRevokeMessConsumer(redisMessageContentBean.getMessageIndex(), JSON.parseObject(redisMessageContentBean.getMessageBody()));
            LOGGER.info("=========站内消息撤回，MQ消费者执行完成=========");
        } catch (JMSException e) {
            LOGGER.error("出队异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()), e);
        }
    }
}
