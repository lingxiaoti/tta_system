package com.sie.saaf.base.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageContentEntity_HI;
import com.sie.saaf.base.message.model.entities.BaseMessageInstationEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageContentEntity_HI_RO;
import com.sie.saaf.base.message.model.inter.IBaseMessageContent;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.transaction.annotation.TransMessageConsumer;
import com.sie.saaf.transaction.annotation.TransMessageProvider;
import com.sie.saaf.transaction.annotation.TransMsgParam;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseMessageContentServer")
public class BaseMessageContentServer extends BaseCommonServer<BaseMessageContentEntity_HI> implements IBaseMessageContent {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageContentServer.class);
    @Autowired
    private ViewObject<BaseMessageContentEntity_HI> baseMessageContentDAO_HI;
    @Autowired
    private BaseViewObject<BaseMessageContentEntity_HI_RO> baseMessageContentDAO_HI_RO;
    @Autowired
    private ViewObject<BaseMessageInstationEntity_HI> baseMessageInstationDAO_HI;

    public BaseMessageContentServer() {
        super();
    }

    /**
     * 查询站内消息列表
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public Pagination<BaseMessageContentEntity_HI_RO> findBaseConMessPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer querySQL = new StringBuffer(BaseMessageContentEntity_HI_RO.QUERY_CON_MESS_LIST);
        Map<String, Object> paramsMap = new HashMap<>();

        if (StringUtils.isNotBlank(queryParamJSON.getString("creationDateFrom"))) {
            querySQL.append(" and Date(bmc.creation_date) >= '").append(queryParamJSON.getString("creationDateFrom")).append("' \n");
        }

        if (StringUtils.isNotBlank(queryParamJSON.getString("creationDateTo"))) {
            querySQL.append(" and Date(bmc.creation_date) <= '").append(queryParamJSON.getString("creationDateTo")).append("' \n");
        }

        SaafToolUtils.parperHbmParam(BaseMessageContentEntity_HI_RO.class, queryParamJSON, "bmc.con_mess_id", "conMessId", querySQL, paramsMap, "=");
        SaafToolUtils.parperHbmParam(BaseMessageContentEntity_HI_RO.class, queryParamJSON, "bmc.con_mess_title", "conMessTitle", querySQL, paramsMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseMessageContentEntity_HI_RO.class, queryParamJSON, "bmc.con_mess_type", "conMessType", querySQL, paramsMap, "=");
        SaafToolUtils.parperHbmParam(BaseMessageContentEntity_HI_RO.class, queryParamJSON, "bmc.con_mess_state", "conMessState", querySQL, paramsMap, "=");
        SaafToolUtils.parperHbmParam(BaseMessageContentEntity_HI_RO.class, queryParamJSON, "bmc.con_mess_system", "conMessSystem", querySQL, paramsMap, "=");
        //SaafToolUtils.parperHbmParam(BaseMessageContentEntity_HI_RO.class, queryParamJSON, "bmc.creation_date", "creationDateFrom", querySQL, paramsMap, ">=");
        //SaafToolUtils.parperHbmParam(BaseMessageContentEntity_HI_RO.class, queryParamJSON, "bmc.creation_date", "creationDateTo", querySQL, paramsMap, "<=");
        Pagination<BaseMessageContentEntity_HI_RO> pagination = baseMessageContentDAO_HI_RO.findPagination(querySQL,SaafToolUtils.getSqlCountString(querySQL), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    /**
     * 查询站内消息已读未读数量
     *
     * @param conMessId
     * @return
     */
    @Override
    public List<BaseMessageContentEntity_HI_RO> findIsOrNotConsulted(Integer conMessId) {
        StringBuffer querySQL = new StringBuffer(BaseMessageContentEntity_HI_RO.QUERY_IS_OT_NOT_CONSULTED_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("conMessId", conMessId);
        return baseMessageContentDAO_HI_RO.findList(querySQL, paramsMap);
    }

    /**
     * 新增&修改站内消息
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public BaseMessageContentEntity_HI saveOrUpdateMessContent(JSONObject paramsJSON, int userId) throws Exception {
        SaafToolUtils.validateJsonParms(paramsJSON, "conMessTitle", "conMessType", "conMessSystem", "conMessContent");
        BaseMessageContentEntity_HI messageContentEntity = SaafToolUtils.setEntity(BaseMessageContentEntity_HI.class, paramsJSON, baseMessageContentDAO_HI, userId);
        if (StringUtils.isBlank(paramsJSON.getString("conMessId"))) {
            messageContentEntity.setConMessState(0);
        }
        if (messageContentEntity.getDeleteFlag() == null || StringUtils.isBlank(messageContentEntity.getDeleteFlag().toString())) {
            messageContentEntity.setDeleteFlag(0);
        }
        baseMessageContentDAO_HI.saveOrUpdate(messageContentEntity);
        return messageContentEntity;
    }

    /**
     *  分布式事务主动方(生产者)业务
     */
    @TransMessageProvider(desc = "sendMessProvider")
    public void saveTransactionProduct(JSONObject queryParamJSON){
        BaseMessageContentEntity_HI messageContentEntity = baseMessageContentDAO_HI.getById(queryParamJSON.getInteger("conMessId"));
        messageContentEntity.setConMessState(1);
        messageContentEntity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
        messageContentEntity.setConSendDate(new Date());
        baseMessageContentDAO_HI.update(messageContentEntity);

        List<BaseMessageContentEntity_HI_RO> waitingSendList = findWaitingSendList(queryParamJSON);

        //消息预发送----方法结束，事务提交成功后，框架会自动推送消息至MQ，同时redis消息状态设置为1
        for (int i = 0; i < waitingSendList.size(); i++) {
            JSONObject messageBodyJSON = JSON.parseObject(JSON.toJSONString(waitingSendList.get(i)));
            messageBodyJSON.put("varUserId", queryParamJSON.getInteger("varUserId"));
            messageBodyJSON.put("varUserName", queryParamJSON.getString("varUserName"));
            RedisMessageContentBean redisMessageContentBean = new RedisMessageContentBean(messageBodyJSON.toJSONString(),"sendMessQueue");
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
     * @param messageJSON
     */
    @TransMessageConsumer(desc = "sendMessProvider:sendMessConsumer")
    public void saveTransMessageConsumer(@TransMsgParam Long msgId, JSONObject messageJSON){
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("conMessId", messageJSON.getInteger("conMessId"));
        queryParamMap.put("buMessId", messageJSON.getInteger("buMessId"));
        queryParamMap.put("depMessId", messageJSON.getInteger("depMessId"));
        queryParamMap.put("deptId", messageJSON.getInteger("deptId"));
        List<BaseMessageContentEntity_HI_RO> list = baseMessageContentDAO_HI_RO.findList(BaseMessageContentEntity_HI_RO.QUERY_MESS_WAITING_TO_SEND, queryParamMap);
        for (int i = 0; i < list.size(); i++) {
            BaseMessageInstationEntity_HI messInstationEntity = new BaseMessageInstationEntity_HI();
            messInstationEntity.setConMessId(list.get(i).getConMessId());
            messInstationEntity.setMessTitle(list.get(i).getConMessTitle());
            messInstationEntity.setMessContent(list.get(i).getConMessContent());
            messInstationEntity.setMessStatus(0);
            messInstationEntity.setMessFromSystem(list.get(i).getConMessSystem());
            messInstationEntity.setMessFromModule("BASE_MODEL");
            messInstationEntity.setMessReceiver(list.get(i).getMessReceiver());
            messInstationEntity.setMessReceiverId(list.get(i).getMessReceiverId());
            messInstationEntity.setMessSender(messageJSON.getString("varUserName"));
            messInstationEntity.setMessSenderId(messageJSON.getInteger("varUserId"));
            messInstationEntity.setDeleteFlag(0);
            messInstationEntity.setOperatorUserId(messageJSON.getInteger("varUserId"));
            baseMessageInstationDAO_HI.save(messInstationEntity);
        }
        LOGGER.info("被动方业务执行完成,messageIndex:[{}],本次发送消息数量:{}个", msgId, list.size());
    }

    /**
     * 获取待发送消息分组
     * @param queryParamJSON
     * {
     *      conMessId：消息ID
     * }
     * @return
     */
    @Override
    public List<BaseMessageContentEntity_HI_RO> findWaitingSendList(JSONObject queryParamJSON){
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("conMessId", queryParamJSON.getInteger("conMessId"));
        StringBuffer querySQL = new StringBuffer(BaseMessageContentEntity_HI_RO.QUERY_WAITING_SEND_MESS_GROUP_SQL);
        return baseMessageContentDAO_HI_RO.findList(querySQL, queryParamMap);
    }

}
