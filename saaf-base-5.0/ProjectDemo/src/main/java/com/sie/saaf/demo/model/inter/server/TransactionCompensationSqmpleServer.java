package com.sie.saaf.demo.model.inter.server;


import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.user.model.entities.BaseChannelEntity_HI;
import com.sie.saaf.transaction.annotation.TransMessageConsumer;
import com.sie.saaf.transaction.annotation.TransMessageProvider;
import com.sie.saaf.transaction.annotation.TransMsgParam;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 事务一致性代码样例
 */
@Component
public class TransactionCompensationSqmpleServer {

    private static final org.slf4j.Logger log= LoggerFactory.getLogger(TransactionCompensationSqmpleServer.class);

    @Autowired
    private ViewObject<BaseChannelEntity_HI> baseChannelDAO_HI;




    /**
     *  分布式事务主动方(生产者)业务
     */
    @TransMessageProvider(desc = "businessProvider")
    public void saveTransactionProduct(){

        //带事务的业务逻辑代码
        List<BaseChannelEntity_HI> list=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BaseChannelEntity_HI instance=new BaseChannelEntity_HI();
            instance.setChannelCode("testChanelCode"+i);
            instance.setOperatorUserId(-1);
            baseChannelDAO_HI.save(instance);
            list.add(instance);
        }

        //消息预发送----方法结束，事务提交成功后，框架会自动推送消息至MQ，同时redis消息状态设置为1
        for (int i = 0; i < list.size(); i++) {
            RedisMessageContentBean redisMessageContentBean=new RedisMessageContentBean(JSON.toJSONString(list.get(i)),"transQueue");
            RemoteServiceInvoke.sendMessageBody2Redis(redisMessageContentBean);
        }
        log.info("主动方业务执行完成");
    }


    /**
     *
     * (TransSampleListener 中调用此方法)
     * 分布式事务被动方(消费者者)业务
     * 进入方法前框架会写消费表，做幂等校验
     * 事务成功提交后，删除redis中数据
     *
     * @param msgId
     * @param instance
     */
    @TransMessageConsumer(desc = "businessProvider:businessConsumer")
    public void saveTransMessageConsumer(@TransMsgParam Long msgId, BaseChannelEntity_HI instance){

        // 业务逻辑代码
        instance= baseChannelDAO_HI.getById(instance.getChannelId());
        if (instance==null)
            return;
       baseChannelDAO_HI.delete(instance);
        log.info("被动方业务执行完成");
    }


}
