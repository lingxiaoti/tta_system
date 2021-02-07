package com.sie.saaf.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.message.model.entities.MsgCfgEntity_HI;
import com.sie.saaf.message.model.entities.MsgInstanceEntity_HI;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import com.sie.saaf.message.model.inter.server.MsgCfgServer;
import com.sie.saaf.message.model.inter.server.MsgHistoryServer;
import com.sie.saaf.message.model.inter.server.MsgInstanceServer;
import com.sie.saaf.message.model.inter.server.MsgSourceCfgServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author chenzg
 * @createTime 2018-06-14 18:07
 * @description
 */
@Component
public class MessageCenterMQConsumerListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageCenterMQConsumerListener.class);

    @Autowired
    private MsgInstanceServer msgInstanceServer;

    @Autowired
    private MsgCfgServer msgCfgServer;

    @Autowired
    private MsgSourceCfgServer msgSourceCfgServer;

//    @Autowired
//    private MsgHistoryServer msgHistoryServer;

//    @Autowired
//    private MsgTdServer msgTdServer;
//
//    private List<MsgCfgEntity_HI> msgCfgList = new ArrayList<>();
//
//    private List<MsgSourceCfgEntity_HI> sourceList = new ArrayList<>();

    /**
     * 消息中心的消费者,进行发送非实时消息
     * sendStatus发送状态
     * 1: waiting for send
     * 2: sending (locked)
     * 3. finished
     */
    @Override
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "messageCenterQueue",concurrency = "1")
    public void onMessage(Message message) {

        //  getInitData();

        logger.info(message.toString());
        String message_ = null;
        if (message instanceof TextMessage) {
            try {
                message_ = ((TextMessage) message).getText();
            } catch (JMSException e) {
                logger.error(e.getMessage(),e);
            }
        }

        JSONObject messageJSON = JSONObject.parseObject(message_);

        //发送实例
        //      instance = getInstance(textMessage);
        Integer msgInstanceId = messageJSON.getInteger("msgInstanceId");
        if (msgInstanceId == null)
            return;

        MsgInstanceEntity_HI instance = msgInstanceServer.getById(msgInstanceId);

        logger.info("处理实例id"+msgInstanceId+"----getById:"+instance);
        if(instance!=null)
        {
            //变更状态为发送中
            boolean isWaitting4Send = updateStatusToSending(instance.getMsgInstanceId().toString());

            if (!isWaitting4Send) {
                return;
            }

            //获取配置对象
            MsgCfgEntity_HI msgCfg = msgCfgServer.getById(instance.getMsgCfgId());
            if (msgCfg == null) {
                updateStatusToError(instance.getMsgInstanceId().toString(), "配置对象未找到");
            }

            //获取源对象
            MsgSourceCfgEntity_HI sourceCfg = msgSourceCfgServer.getById(msgCfg.getMsgSourceId());
            if (sourceCfg == null) {
                updateStatusToError(instance.getMsgInstanceId().toString(), "源对象未找到");
            }

            //实际发送信息
            JSONObject ret = msgInstanceServer.sendSMSImmediately(instance, sourceCfg, msgCfg);

            //发送失败
            if ("F".equals(ret.getString("CODE"))) {
                updateStatusToFail(instance.getMsgInstanceId().toString(), ret.getString("MSG"));
            } else if ("E".equals(ret.getString("CODE"))) {
                updateStatusToError(instance.getMsgInstanceId().toString(), ret.getString("MSG"));
            } else {
                updateStatusToSuccess(instance.getMsgInstanceId().toString(), ret.getString("MSG"));
            }
        }

//        //重新查询出最新信息
//        instance = msgInstanceServer.getById(instance.getMsgInstanceId());
//
//        // 成功,计入历史表
//        MsgHistoryEntity_HI his = new MsgHistoryEntity_HI();
//        BeanUtils.copyProperties(instance, his);
//        msgHistoryServer.save(his);
//
//        //发送成功,删除
//        msgInstanceServer.deleteById(instance.getMsgInstanceId());


    }

//    private MsgCfgEntity_HI getMsgConfig(List<MsgCfgEntity_HI> msgCfgList, String cfgId) throws Exception {
//        for (MsgCfgEntity_HI cfg : msgCfgList) {
//            if (cfg.getMsgCfgId().toString().equals(cfgId)) {
//                return cfg;
//            }
//        }
//        // when msgCfg not find,then get form db
//        return getFromDB(cfgId);
//    }
//
//    private MsgSourceCfgEntity_HI getSourceCfg(Integer sourceId, List<MsgSourceCfgEntity_HI> sourceList) throws Exception {
//        for (MsgSourceCfgEntity_HI sConfig : sourceList) {
//            if (sConfig.getMsgSourceId().toString().equals(sourceId.toString())) {
//                return sConfig;
//            }
//        }
//
//        // when sourceCfg not foud ,then get from db
//        MsgSourceCfgEntity_HI sourceCfg = getSourceCfgFromDB(sourceId);
//        return sourceCfg;
//    }

//    private MsgSourceCfgEntity_HI getSourceCfgFromDB(Integer sourceId) throws Exception {
//        JSONObject param = new JSONObject();
//        param.put("isDelete", "0");
//        List<MsgSourceCfgEntity_HI> retList = msgSourceCfgServer.findList(param);
//        if (retList.isEmpty()) throw new Exception("配置信息丢失，请联系管理员");
//        // add to memory
//        sourceList.add(retList.get(0));
//        return retList.get(0);
//    }
//
//    private MsgCfgEntity_HI getFromDB(String cfgId) throws Exception {
//        JSONObject param = new JSONObject();
//        param.put("isDelete", "0");
//        List<MsgCfgEntity_HI> retList = msgCfgServer.findList(param);
//        if (retList.isEmpty()) throw new Exception("配置信息丢失，请联系管理员");
//        // add to memory
//        msgCfgList.add(retList.get(0));
//        return retList.get(0);
//    }
//
//    private void getInitData() {
//        JSONObject param = new JSONObject();
//        param.put("isDelete", "0");
//        if (msgCfgList.isEmpty()) {
//            msgCfgList = msgCfgServer.findList(param);
//        }
//        if (sourceList.isEmpty()) {
//            sourceList = msgSourceCfgServer.findList(param);
//        }
//    }

//    private MsgInstanceEntity_HI getInstance(TextMessage textMessage) throws JMSException {
//        String message_ = textMessage.getText();
//        try {
//            return JSONObject.parseObject(message_, MsgInstanceEntity_HI.class);
//        } catch (Exception e) {
//            logger.error("转换失败，数据格式错误");
//            return new MsgInstanceEntity_HI();
//        }
//    }

//    private boolean updateStatusToFinished(String instanceId, String ret) {
//        try {
//
//            MsgInstanceEntity_HI ins = msgInstanceServer.getById(instanceId);
//
//            msgInstanceServer.deleteById(instanceId);
//
//            MsgHistoryEntity_HI his = new MsgHistoryEntity_HI();
//            BeanUtils.copyProperties(ins, his);
//            his.setSendStatus("3");
//            msgHistoryServer.save(his);
//
//            return true;
//        } catch (Exception e) {
//            logger.error("存入历史表失败");
//            return false;
//        }
//        /*if (msgInstanceServer.updateStatus(instanceId, "3", "2", ret) > 0)
//            return true;*/
//    }

    private void updateStatusToError(String instanceId, String msg) {
        msgInstanceServer.updateErrorStatus(instanceId, "EXCEPTION", "RUNING", msg);
    }

    private void updateStatusToFail(String instanceId, String msg) {
        msgInstanceServer.updateErrorStatus(instanceId, "FAIL", "RUNING", msg);
    }

    private void updateStatusToSuccess(String instanceId, String msg) {
        msgInstanceServer.updateSuccessStatus(instanceId, "SUCCEED", "RUNING", msg);
    }


    private boolean updateStatusToSending(String instanceId) {
        if (msgInstanceServer.updateStatus(instanceId, "RUNING", "PENDING") > 0)
            return true;
        return false;
    }

}
