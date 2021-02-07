package com.sie.saaf.base.orgStructure.services.mqlistener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.inter.IBaseAccessBasedata;
import com.sie.saaf.common.util.SaafDateUtils;
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
 * 人员20权限同步
 * @auther: huqitao 2018/8/6
 */
@Component("access20PersonMQConsumerListener")
public class Access20PersonMQConsumerListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(Access20PersonMQConsumerListener.class);

    @Autowired
    public IBaseAccessBasedata baseAccessBasedataServer;

    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "access20PersonQueue",concurrency = "5-20")
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        try {
            String message_ = textMessage.getText();
            JSONObject jsonObject = JSON.parseObject(message_);
            baseAccessBasedataServer.person20AccessDataSynConsumer(jsonObject);
            LOGGER.info("=========人员20权限同步，MQ消费者执行完成=========");
        } catch (JMSException e) {
            LOGGER.error("出队异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()), e);
        }
    }
}
