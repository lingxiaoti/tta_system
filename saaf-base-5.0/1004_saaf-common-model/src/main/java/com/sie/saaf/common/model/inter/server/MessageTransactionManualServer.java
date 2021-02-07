package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseCommonTransactionConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import com.sie.saaf.common.model.entities.readonly.MessageTransactionManualEntity_HI_RO;
import com.sie.saaf.common.model.inter.IMessageTransactionManual;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.transaction.compensation.core.TransactionConsistencyCore;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: huqitao 2018/7/26
 */
@Component("messageTransactionManualServer")
public class MessageTransactionManualServer implements IMessageTransactionManual {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTransactionManualServer.class);
//    public static final String REDIS_KEY_NAME = "MQ_Trans_Cons_Core_";
//    public static final String TRANS_KEYS = "TRANS_CORE_KEYS";

    @Autowired
    private BaseViewObject<MessageTransactionManualEntity_HI_RO> messageTransactionManualDAO_HI_RO;
    @Autowired
    private ViewObject<BaseCommonTransactionConfirmEntity_HI> baseCommonTransactionConfirmDAO_HI;
    @Autowired
    private  ViewObject<BaseCommonMessageConfirmEntity_HI> baseCommonMessageConfirmDAO_HI;
    @Autowired
    private ViewObject<BaseManualSupplementEntity_HI> baseManualSupplementDAO_HI;
    @Autowired(required = false)
    private TransactionConsistencyCore transactionConsistencyCore;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 查询同时存在于补偿表和消费表的数据，并删除
     */
    @Override
    public void deleteBothExistTransactionAndManual() throws Exception{
        StringBuffer querySQL = new StringBuffer(MessageTransactionManualEntity_HI_RO.QUERY_BOTH_EXIST_TRANSACTION_ADN_MANUAL_SQL);
        List<MessageTransactionManualEntity_HI_RO> list = messageTransactionManualDAO_HI_RO.findList(querySQL);
        for(MessageTransactionManualEntity_HI_RO obj : list){
            baseManualSupplementDAO_HI.delete(obj.getManualId());
            baseCommonTransactionConfirmDAO_HI.delete(obj.getTransConfirmId());
        }
    }

    /**
     * 查询存在补偿表不存在消费表的数据
     * @return 存在补偿表不存在消费表的数据
     */
    @Override
    public List<MessageTransactionManualEntity_HI_RO> findExistManualNotExistTransaction(){
        StringBuffer querySQL = new StringBuffer(MessageTransactionManualEntity_HI_RO.QUERY_EXIST_MANUAL_NOT_EXIST_TRANSACTION_SQL);
        return messageTransactionManualDAO_HI_RO.findList(querySQL);
    }

    /**
     * 清除补偿表，清除确认表，重新推送至redis，重新推送至MQ补偿
     * @param messageTransManual 存在补偿表不存在消费表的对象
     * @param isClearMessConfirm 是否清除消息确认表
     */
    @Override
    public void saveCompensateStepOne(MessageTransactionManualEntity_HI_RO messageTransManual, Boolean isClearMessConfirm) throws Exception{
        //清除补偿表
        baseManualSupplementDAO_HI.delete(messageTransManual.getManualId());
        if(isClearMessConfirm){
            //清除确认表
            List<BaseCommonMessageConfirmEntity_HI> messageConfirmList = baseCommonMessageConfirmDAO_HI.findByProperty("messageIndex", messageTransManual.getMessageIndex());
            for (int i = 0; i < messageConfirmList.size(); i++) {
                baseCommonMessageConfirmDAO_HI.delete(messageConfirmList.get(i).getConfirmId());
            }
        }
    }

    /**
     * 重新推送至redis，重新推送至MQ补偿
     * @param messageIndex 消息Id
     * @param messageBody 消息体
     * @param requestUrl 请求地址
     * @param queueName 队列名称
     */
    @Override
    public void saveSend2RedisAndSend2Mq(Integer messageIndex, String messageBody, String requestUrl, String queueName) throws Exception{
        //重新推送至redis
        transactionConsistencyCore.sendMessageBody2Redis(messageIndex, messageBody, requestUrl, queueName);
        //重新推送至MQ，进行补偿
        transactionConsistencyCore.sendRedisMessage2MQ(messageIndex.longValue(), true);
    }

    /**
     * 查询同时存在于消息确认表和消费表的数据
     * @param creationDate 消息确认的创建时间
     * @return 同时存在于消息确认表和消费表的数据
     */
    @Override
    public List<MessageTransactionManualEntity_HI_RO> findBothExistMessConfirmTransaction(String creationDate){
        Map<String, Object> queryMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(MessageTransactionManualEntity_HI_RO.QUERY_BOTH_EXIST_MESS_CONFIRM_ADN_TRANSACTION_SQL);
        if(StringUtils.isNotBlank(creationDate)){
            querySQL.append("AND bcmc.creation_date <= :creationDate \n");
            queryMap.put("creationDate", SaafDateUtils.string2UtilDate(creationDate, "yyyy-MM-dd HH:mm:ss"));
        }
        return messageTransactionManualDAO_HI_RO.findList(querySQL, queryMap);
    }

    /**
     * 清除清除确认表，MQ延时投递（删除对应的消费确认数据）
     * @param messConfirmAndTrans 同时存在消息确认表和消费表的对象
     */
    @Override
    public void deleteMessConfirmAndDelaySendMessage(MessageTransactionManualEntity_HI_RO messConfirmAndTrans) throws Exception{
        //清除确认表
        List<BaseCommonMessageConfirmEntity_HI> messageConfirmList = baseCommonMessageConfirmDAO_HI.findByProperty("messageIndex", messConfirmAndTrans.getMessageIndex());
        baseCommonMessageConfirmDAO_HI.delete(messageConfirmList);

        String redisKeyOrValue = TransactionConsistencyCore.REDIS_KEY_NAME + messConfirmAndTrans.getMessageIndex();
        jedisCluster.srem(TransactionConsistencyCore.TRANS_KEYS, redisKeyOrValue);
        jedisCluster.del(redisKeyOrValue);

        //延迟推送（MQ7天后出队，删除消费表对应的数据）
        String delayDateSecondStr = jedisCluster.get("delay_date_second");
        Long delayDateSecond = 3600l; //默认1天，单位：秒
        if (StringUtils.isNotBlank(delayDateSecondStr)) {
            delayDateSecond = Long.parseLong(delayDateSecondStr);
        } else {
            jedisCluster.set("delay_date_second", "3600");
        }
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("transConfirmId", messConfirmAndTrans.getTransConfirmId());
        transactionConsistencyCore.delaySendMessage("TiDB_transConfirmQueue", paramsJson, delayDateSecond);
    }

    /**
     * 查询存在于消息确认表不存在消费表的数据
     * @param creationDate 消息确认的创建时间
     * @return 存在于消息确认表不存在消费表的数据
     */
    @Override
    public List<MessageTransactionManualEntity_HI_RO> findExistMessConfirmAndNotExistTransaction(String creationDate){
        Map<String, Object> queryMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(MessageTransactionManualEntity_HI_RO.QUERY_EXIST_MESS_CONFIRM_NOT_EXIST_TRANSACTION_SQL);
        if(StringUtils.isNotBlank(creationDate)){
            querySQL.append("AND bcmc.creation_date <= :creationDate \n");
            queryMap.put("creationDate", SaafDateUtils.string2UtilDate(creationDate, "yyyy-MM-dd HH:mm:ss"));
        }
        return messageTransactionManualDAO_HI_RO.findList(querySQL, queryMap);
    }

    /**
     * 查询存在于在消费表的数据
     * @param messageIndexStr (1,2,3,4,5)
     * @return 存在于在消费表的数据
     */
    @Override
    public List<MessageTransactionManualEntity_HI_RO> findExistTransaction(String messageIndexStr){
        StringBuffer querySQL = new StringBuffer(MessageTransactionManualEntity_HI_RO.QUERY_EXIST_TRANSACTION_SQL);
        querySQL.append("AND bctc.messageIndex IN ("+ messageIndexStr +") \n");
        return messageTransactionManualDAO_HI_RO.findList(querySQL);
    }
}
