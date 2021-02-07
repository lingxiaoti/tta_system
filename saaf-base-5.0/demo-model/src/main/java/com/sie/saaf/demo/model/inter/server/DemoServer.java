package com.sie.saaf.demo.model.inter.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.demo.model.entities.DemoEntity_HI;
import com.sie.saaf.demo.model.inter.IDemo;
import com.sie.saaf.transaction.annotation.TransMessageConsumer;
import com.sie.saaf.transaction.annotation.TransMessageProvider;
import com.sie.saaf.transaction.annotation.TransMsgParam;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 事务一致性代码样例
 */
@Component
public class DemoServer extends BaseCommonServer<DemoEntity_HI> implements IDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoServer.class);

    @Autowired
    private ViewObject<DemoEntity_HI> demoDAO_HI;

    public JSONObject test(JSONObject queryParamJSON){

        return new JSONObject();
    }

    /**
     *  分布式事务主动方(生产者)业务
     */
    @Override
    @TransMessageProvider(desc = "businessProvider")
    public void saveTransactionProduct(){

        //带事务的业务逻辑代码
        List<DemoEntity_HI> list=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DemoEntity_HI instance=new DemoEntity_HI();
//            instance.setDemoId(i);
            instance.setDemoName("demo_name_"+i);
            instance.setDemoDesc("demo_desc_"+i);
            demoDAO_HI.save(instance);
            list.add(instance);
        }

        //消息预发送----方法结束，事务提交成功后，框架会自动推送消息至MQ，同时redis消息状态设置为1
        for (int i = 0; i < list.size(); i++) {
            RedisMessageContentBean redisMessageContentBean=new RedisMessageContentBean(JSON.toJSONString(list.get(i)),"transQueue");
            RemoteServiceInvoke.sendMessageBody2Redis(redisMessageContentBean);
        }
        LOGGER.info("主动方业务执行完成");
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
    @Override
    @TransMessageConsumer(desc = "businessProvider:businessConsumer")
    public void saveTransMessageConsumer(@TransMsgParam Long msgId, DemoEntity_HI instance){

        // 业务逻辑代码
        instance= demoDAO_HI.getById(instance.getDemoId());
        if (instance==null)
            return;
        demoDAO_HI.delete(instance);
        LOGGER.info("被动方业务执行完成");
    }


}
