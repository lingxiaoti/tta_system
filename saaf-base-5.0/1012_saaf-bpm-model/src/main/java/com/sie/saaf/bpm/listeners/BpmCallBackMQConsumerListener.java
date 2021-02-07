package com.sie.saaf.bpm.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.inter.IBpmCallBack;
import com.yhg.redis.framework.JedisClusterCore;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**

 */
@Component
public class BpmCallBackMQConsumerListener  extends JedisClusterCore implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(BpmCallBackMQConsumerListener.class);
	
	@Autowired
	private IBpmCallBack callBackServer;

	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
    public void onMessage(Message message) {
        if (message == null) {
        	LOGGER.info("接收消息为null");
            return;
        }
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            RedisMessageContentBean msgBean = JSONObject.parseObject(text, RedisMessageContentBean.class);
            Long indexValue = msgBean.getMessageIndex();
            JSONObject messgaeJson = JSON.parseObject(msgBean.getMessageBody());
            callBackServer.updateCallBack(indexValue, messgaeJson);
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        } 
	}

}
