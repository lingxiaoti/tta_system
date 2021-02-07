package com.sie.saaf.demo.model.inter.server;

import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MQ 生产者
 */
@Component
public class MqProductSampleServer {
    private  static final Logger log=LoggerFactory.getLogger(MqProductSampleServer.class);


    @Autowired
    private ProducerService producerService;


    /**
     * 生产者发送消息
     */
    public void sendMessage(){
        ActiveMQQueue queueDestination_ = new ActiveMQQueue("messageQueue");
        producerService.sendMessage(queueDestination_, SaafDateUtils.convertDateToString(new Date()));
    }



}
