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
 * 门店、分销商子库20权限同步
 * @auther: huqitao 2018/8/10
 */
@Component("access20StoreDealerSubMQConsumerListener")
public class Access20StoreDealerSubMQConsumerListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(Access20StoreDealerSubMQConsumerListener.class);

    @Autowired
    public IBaseAccessBasedata baseAccessBasedataServer;

    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "access20StoreDealerSubQueue",concurrency = "5-10")
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        try {
            String message_ = textMessage.getText();
            JSONObject jsonObject = JSON.parseObject(message_);
            baseAccessBasedataServer.saveDealerStoreSub20AccessConsumer(jsonObject);
            LOGGER.info("=========门店、分销商子库20权限同步，MQ消费者执行完成=========");
        } catch (JMSException e) {
            LOGGER.error("出队异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()), e);
        }
    }
}
