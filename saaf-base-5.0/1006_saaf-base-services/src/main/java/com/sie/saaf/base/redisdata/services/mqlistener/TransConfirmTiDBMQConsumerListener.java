package com.sie.saaf.base.redisdata.services.mqlistener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommonTransactionConfirm;
import com.sie.saaf.common.util.SaafDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * @auther: huqitao 2018/7/25
 */
@Component("transConfirmTiDBMQConsumerListener")
public class TransConfirmTiDBMQConsumerListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransConfirmTiDBMQConsumerListener.class);

    @Autowired
    private IBaseCommonTransactionConfirm baseCommonTransactionConfirmServer;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        try {
            String message_ = textMessage.getText();
            LOGGER.info("出队时间:{} <==> 出队参数:{}", SaafDateUtils.convertDateToString(new Date()), message_);
            JSONObject paramsJSON = JSON.parseObject(message_);
            Integer transConfirmId = paramsJSON.getInteger("transConfirmId");
            baseCommonTransactionConfirmServer.deleteTransactionConfirmById(transConfirmId);
        } catch (JMSException e) {
            LOGGER.error("出队异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()), e);
        }
    }
}