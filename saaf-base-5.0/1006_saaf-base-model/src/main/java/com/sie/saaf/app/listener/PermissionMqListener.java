package com.sie.saaf.app.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
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

@Component
public class PermissionMqListener implements MessageListener {

    private static final Logger log= LoggerFactory.getLogger(PermissionMqListener.class);

    @Autowired
    private BaseAccreditServer baseAccreditServer;


    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "permissionUpdaetQueue",concurrency = "5-20")
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        try {
            String message_ = textMessage.getText();
            JSONObject jsonObject = JSON.parseObject(message_);
            if (!jsonObject.containsKey("userId"))
                return;
            Integer userId=jsonObject.getInteger("userId");
            baseAccreditServer.flushAccreditCache(userId,jsonObject.getString("timestamp"));
        } catch (JMSException e) {
            log.error("出队异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()), e);
        }
    }
}
