package com.sie.saaf.base.orgStructure.services.mqlistener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseAccessBasedataEntity_HI;
import com.sie.saaf.base.orgStructure.model.inter.IBaseAccessBasedata;
import com.sie.saaf.common.util.SaafDateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.List;

/**
 * 员工用户10权限同步和经销商10权限同步（共用同一个队列）
 * @auther: huqitao 2018/9/21
 */
@Component("access10PersonAndDealerMQConsumerListener")
public class Access10PersonAndDealerMQConsumerListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(Access10PersonAndDealerMQConsumerListener.class);

    @Autowired
    public IBaseAccessBasedata baseAccessBasedataServer;

    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "access10PersonAndDealerQueue",concurrency = "5-10")
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        try {
            String message_ = textMessage.getText();
            JSONObject jsonObject = JSON.parseObject(message_);
            if (StringUtils.isNotBlank(jsonObject.getString("processingMethod")) && "delete".equals(jsonObject.getString("processingMethod"))) {
                JSONArray accessIdArray = JSON.parseArray(jsonObject.getString("accessIdArr"));
                baseAccessBasedataServer.deletePerson10AccessData(accessIdArray);
            } else if (StringUtils.isNotBlank(jsonObject.getString("processingMethod")) && "save".equals(jsonObject.getString("processingMethod"))) {
                List<BaseAccessBasedataEntity_HI> insertList = JSON.parseArray(jsonObject.getString("insertParamMQ"), BaseAccessBasedataEntity_HI.class);
                baseAccessBasedataServer.savePerson10AccessData(insertList);
            }
            LOGGER.info("=========处理类型：" + jsonObject.getString("processingType") + "，处理方式：" + jsonObject.getString("processingType") + "，MQ消费者执行完成=========");
        } catch (Exception e) {
            LOGGER.error("出队异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()), e);
        }
    }
}
