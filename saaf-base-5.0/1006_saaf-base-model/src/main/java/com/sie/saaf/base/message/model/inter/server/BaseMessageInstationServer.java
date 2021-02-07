package com.sie.saaf.base.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageContentEntity_HI;
import com.sie.saaf.base.message.model.entities.BaseMessageInstationEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageInstationEntity_HI_RO;
import com.sie.saaf.base.message.model.inter.IBaseMessageInstation;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseAttachmentServer;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.transaction.annotation.TransMessageConsumer;
import com.sie.saaf.transaction.annotation.TransMessageProvider;
import com.sie.saaf.transaction.annotation.TransMsgParam;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseMessageInstationServer")
public class BaseMessageInstationServer extends BaseCommonServer<BaseMessageInstationEntity_HI> implements IBaseMessageInstation {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageInstationServer.class);
    @Autowired
    private ViewObject<BaseMessageInstationEntity_HI> baseMessageInstationDAO_HI;
    @Autowired
    private BaseViewObject<BaseMessageInstationEntity_HI_RO> baseMessageInstationDAO_HI_RO;
    @Autowired
    private ViewObject<BaseMessageContentEntity_HI> baseMessageContentDAO_HI;

    @Autowired
    private BaseAttachmentServer baseAttachmentServer;

    public BaseMessageInstationServer() {
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
    public Pagination<BaseMessageInstationEntity_HI_RO> findMessInstationPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        SaafToolUtils.validateJsonParms(queryParamJSON, "messReceiverId");
        StringBuffer querySQL = new StringBuffer(BaseMessageInstationEntity_HI_RO.QUERY_MESSAGE_INFO_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("messReceiverId", queryParamJSON.getInteger("messReceiverId"));

        if (StringUtils.isNotBlank(queryParamJSON.getString("creationDateFrom"))) {
            querySQL.append(" and Date(bmi.creation_date) >= '").append(queryParamJSON.getString("creationDateFrom")).append("' \n");
        }
        if (StringUtils.isNotBlank(queryParamJSON.getString("creationDateTo"))) {
            querySQL.append(" and Date(bmi.creation_date) <= '").append(queryParamJSON.getString("creationDateTo")).append("' \n");
        }
        if (StringUtils.isNotBlank(queryParamJSON.getString("messFromSystemArr"))) {
            JSONArray messFromSystemArr = JSON.parseArray(queryParamJSON.getString("messFromSystemArr"));
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < messFromSystemArr.size(); i++) {
                if (i == (messFromSystemArr.size() -1)) {
                    str.append("'" + messFromSystemArr.get(i) + "'");
                } else {
                    str.append("'" + messFromSystemArr.get(i) + "',");
                }
            }
            querySQL.append(" and bmi.mess_from_system IN (").append(str).append(") \n");
        }
        SaafToolUtils.parperHbmParam(BaseMessageInstationEntity_HI_RO.class, queryParamJSON, "bmi.ins_mess_id", "insMessId", querySQL, paramsMap, "=");
        SaafToolUtils.parperHbmParam(BaseMessageInstationEntity_HI_RO.class, queryParamJSON, "bmi.mess_title", "messTitle", querySQL, paramsMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseMessageInstationEntity_HI_RO.class, queryParamJSON, "bmi.mess_status", "messStatus", querySQL, paramsMap, "=");
        SaafToolUtils.parperHbmParam(BaseMessageInstationEntity_HI_RO.class, queryParamJSON, "bmi.mess_from_system", "messFromSystem", querySQL, paramsMap, "=");
        SaafToolUtils.parperHbmParam(BaseMessageInstationEntity_HI_RO.class, queryParamJSON, "bmi.mess_from_module", "messFromModule", querySQL, paramsMap, "=");
        //SaafToolUtils.parperHbmParam(BaseMessageInstationEntity_HI_RO.class, queryParamJSON, "bmi.creation_date", "creationDateFrom", querySQL, paramsMap, ">=");
        //SaafToolUtils.parperHbmParam(BaseMessageInstationEntity_HI_RO.class, queryParamJSON, "bmi.creation_date", "creationDateTo", querySQL, paramsMap, "<=");

        querySQL.append(" ORDER BY bmi.mess_status\n" +
                        "         ,bmi.creation_date DESC");

        Pagination<BaseMessageInstationEntity_HI_RO> pagination = baseMessageInstationDAO_HI_RO.findPagination(querySQL,SaafToolUtils.getSqlCountString(querySQL), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    /**
     * 查询站内消息列表(含图片)
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public Pagination<BaseMessageInstationEntity_HI_RO> findPaginationIncludeImg(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Pagination<BaseMessageInstationEntity_HI_RO> pagination=this.findMessInstationPagination(queryParamJSON,pageIndex,pageRows);
        String push_message_type=queryParamJSON.getString("push_message_type");
        if (!StringUtils.isEmpty(push_message_type)) {


            //根据来源取图片
            if (pagination.getData() != null && pagination.getData().size() > 0) {
                for (BaseMessageInstationEntity_HI_RO ro : pagination.getData()) {
                    if (!StringUtils.isEmpty(ro.getSourceId()) && NumberUtils.isNumber(ro.getSourceId())) {
                        //如果源不为空
                        BaseAttachmentEntity_HI_RO att = baseAttachmentServer.findBaseAttachmentInfo(Long.parseLong(ro.getSourceId()), push_message_type);
                        if(att!=null)
                        {
                            ro.setImageUrl(att.getFilePath());
                        }
                       
                    }
                }
            }
        }


        return pagination;
    }

    /**
     * 新增站内接收消息
     *
     * @param paramsJSON
     * @return
     * @throws Exception
     */
    @Override
    public BaseMessageInstationEntity_HI saveMessageInstation(JSONObject paramsJSON) throws Exception {
        SaafToolUtils.validateJsonParms(paramsJSON, "messTitle", "messContent", "messFromSystem", "messFromModule", "messReceiverId", "messSenderId");
        BaseMessageInstationEntity_HI messageInstationEntity = new BaseMessageInstationEntity_HI();
        if (StringUtils.isNotBlank(paramsJSON.getString("conMessId"))) {
            messageInstationEntity.setConMessId(paramsJSON.getInteger("conMessId"));
        }
        messageInstationEntity.setMessStatus(0);
        messageInstationEntity.setMessTitle(paramsJSON.getString("messTitle"));
        messageInstationEntity.setMessContent(paramsJSON.getString("messContent"));
        messageInstationEntity.setMessFromSystem(paramsJSON.getString("messFromSystem"));
        messageInstationEntity.setMessFromModule(paramsJSON.getString("messFromModule"));
        messageInstationEntity.setMessReceiverId(paramsJSON.getInteger("messReceiverId"));
        messageInstationEntity.setMessReceiver(paramsJSON.getString("messReceiver"));
        messageInstationEntity.setMessSenderId(paramsJSON.getInteger("messSenderId"));
        messageInstationEntity.setMessSender(paramsJSON.getString("messSender"));
        messageInstationEntity.setSourceId(paramsJSON.getString("sourceId"));
        messageInstationEntity.setDeleteFlag(0);
        messageInstationEntity.setOperatorUserId(paramsJSON.getInteger("messSenderId"));
        baseMessageInstationDAO_HI.saveOrUpdate(messageInstationEntity);
        return messageInstationEntity;
    }

    /**
     * 更新消息状态
     *
     * @param paramsJSON
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject updateMessageStatus(JSONObject paramsJSON) throws Exception {
        //SaafToolUtils.validateJsonParms(paramsJSON, "insMessId", "messReceiverId");
        SaafToolUtils.validateJsonParms(paramsJSON, "insMessId");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("insMessId", paramsJSON.getInteger("insMessId"));
        //queryMap.put("messReceiverId", paramsJSON.getInteger("messReceiverId"));
        List<BaseMessageInstationEntity_HI> messageList = baseMessageInstationDAO_HI.findByProperty(queryMap);
        if (messageList.size() == 0) {
            return SToolUtils.convertResultJSONObj("E", "不存在的消息", 0, null);
        }
        BaseMessageInstationEntity_HI messageInstationEntity = messageList.get(0);
        if (messageInstationEntity.getMessStatus() == 1) {
            LOGGER.warn("不需要修改消息状态。");
            return SToolUtils.convertResultJSONObj("S", "不需要修改状态", 1, messageInstationEntity);
        }
        messageInstationEntity.setMessStatus(1);
        messageInstationEntity.setLastUpdateDate(new Date());
        messageInstationEntity.setLastUpdatedBy(paramsJSON.getInteger("messReceiverId"));
        baseMessageInstationDAO_HI.update(messageInstationEntity);
        return SToolUtils.convertResultJSONObj("S", "已读", 1, messageInstationEntity);
    }

    /**
     * 批量更新消息状态
     *
     * @param paramsJSON
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject updateMessageStatusByBatch(JSONObject paramsJSON) throws Exception {
        SaafToolUtils.validateJsonParms(paramsJSON, "messReceiverId");
        StringBuffer querySQL = new StringBuffer();
        if (StringUtils.isNotBlank(paramsJSON.getString("insMessIdStr"))) {
            querySQL.append("from BaseMessageInstationEntity_HI where insMessId IN (" + paramsJSON.getString("insMessIdStr") + ") and messReceiverId = :messReceiverId");
        } else {
            querySQL.append("from BaseMessageInstationEntity_HI where messReceiverId = :messReceiverId");
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("messReceiverId", paramsJSON.getInteger("messReceiverId"));
        List<BaseMessageInstationEntity_HI> messageList = baseMessageInstationDAO_HI.findList(querySQL, queryMap);
        if (messageList.size() == 0) {
            return SToolUtils.convertResultJSONObj("E", "不存在的消息", 0, null);
        }
        for (BaseMessageInstationEntity_HI messageInstationEntity : messageList) {
            if ("1".equals(messageInstationEntity.getMessStatus())) {
                LOGGER.warn("{}不需要修改消息状态。", messageInstationEntity.getInsMessId());
                continue;
            }
            messageInstationEntity.setMessStatus(1);
            messageInstationEntity.setLastUpdateDate(new Date());
            messageInstationEntity.setLastUpdatedBy(paramsJSON.getInteger("messReceiverId"));
            baseMessageInstationDAO_HI.update(messageInstationEntity);
        }
        return SToolUtils.convertResultJSONObj("S", "批量修改成功", messageList.size(), messageList);
    }

    /**
     * 撤回站内消息：查询需要撤回的所有消息记录
     *
     * @param conMessId
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseMessageInstationEntity_HI_RO> findNeedToRevokeMess(Integer conMessId) {
        StringBuffer querySQL = new StringBuffer("select bmi.ins_mess_id insMessId " +
                                                "   from base_message_instation bmi " +
                                                "  where bmi.con_mess_id = :conMessId" +
                                                "    and bmi.delete_flag = 0");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("conMessId", conMessId);
        return baseMessageInstationDAO_HI_RO.findList(querySQL, queryMap);
    }

    /**
     *  分布式事务主动方(生产者)业务
     */
    @TransMessageProvider(desc = "revokeMessProvider")
    public void saveTranRevokeMessProduct(JSONObject queryParamJSON){
        List<BaseMessageInstationEntity_HI_RO> messageList = findNeedToRevokeMess(queryParamJSON.getInteger("conMessId"));

        JSONArray paramsJsonArray = new JSONArray();
        StringBuffer insMessIdArr = new StringBuffer();
        for (int i = 0, n = 0; i < messageList.size(); i++) {
            if (n == 0) {
                insMessIdArr.append(messageList.get(i).getInsMessId());
            } else {
                insMessIdArr.append("," + messageList.get(i).getInsMessId());
            }
            n++;
            if (n == 500) {
                n = 0;
                JSONObject updateDeleteFlagParams = new JSONObject();
                updateDeleteFlagParams.put("insMessIdArr", insMessIdArr.toString());
                updateDeleteFlagParams.put("varUserId", queryParamJSON.getInteger("varUserId"));
                paramsJsonArray.add(updateDeleteFlagParams);
                insMessIdArr = new StringBuffer();
            }
        }
        if (insMessIdArr.length() > 0) {
            JSONObject updateDeleteFlagParams = new JSONObject();
            updateDeleteFlagParams.put("insMessIdArr", insMessIdArr.toString());
            updateDeleteFlagParams.put("varUserId", queryParamJSON.getInteger("varUserId"));
            paramsJsonArray.add(updateDeleteFlagParams);
        }

        BaseMessageContentEntity_HI messageContentEntity = baseMessageContentDAO_HI.getById(queryParamJSON.getInteger("conMessId"));
        messageContentEntity.setConMessState(2);
        messageContentEntity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
        baseMessageContentDAO_HI.update(messageContentEntity);

        //消息预发送----方法结束，事务提交成功后，框架会自动推送消息至MQ，同时redis消息状态设置为1
        for (int i = 0; i < paramsJsonArray.size(); i++) {
            RedisMessageContentBean redisMessageContentBean = new RedisMessageContentBean(paramsJsonArray.getString(i),"revokeMessQueue");
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
     * @param paramsJSON
     */
    @TransMessageConsumer(desc = "revokeMessProvider:revokeMessConsumer")
    public void saveTransRevokeMessConsumer(@TransMsgParam Long msgId, JSONObject paramsJSON){
        String insMessIdArrStr = paramsJSON.getString("insMessIdArr");
        String[] insMessIdArr = insMessIdArrStr.split(",");
        for (int i = 0; i < insMessIdArr.length; i++) {
            BaseMessageInstationEntity_HI messInstationEntity = baseMessageInstationDAO_HI.getById(Integer.parseInt(insMessIdArr[i]));
            messInstationEntity.setDeleteFlag(1);
            messInstationEntity.setOperatorUserId(paramsJSON.getInteger("varUserId"));
            baseMessageInstationDAO_HI.saveOrUpdate(messInstationEntity);
        }
        LOGGER.info("被动方业务执行完成,messageIndex:[{}],本次发送消息数量:{}个", msgId, insMessIdArr.length);
    }

    /**
     * 获取用户新
     * @param paramJSON
     * @return
     */
    @Override
    public JSONObject findUserInfo(JSONObject paramJSON) {
        StringBuffer querySQL = new StringBuffer(BaseMessageInstationEntity_HI_RO.QUERY_USER_INFO_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJSON.getString("userName"))) {
            querySQL.append("\t and users.user_name = :userName \n");
            paramsMap.put("userName", paramJSON.getString("userName"));
        }
        if (StringUtils.isNotBlank(paramJSON.getString("userId"))) {
            querySQL.append("\t and users.user_id = :userId \n");
            paramsMap.put("userId", paramJSON.getInteger("userId"));
        }
        List<BaseMessageInstationEntity_HI_RO> list = baseMessageInstationDAO_HI_RO.findList(querySQL, paramsMap);
        if (list.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(list.get(0)));
        }
        return null;
    }

    /**
     * 查看消息
     * @param queryParamJSON
     * @return
     */
    @Override
    public JSONObject findMessInstationDetail(JSONObject queryParamJSON) {
        SaafToolUtils.validateJsonParms(queryParamJSON, "insMessId");
        StringBuffer querySQL = new StringBuffer(BaseMessageInstationEntity_HI_RO.QUERY_MESSAGE_DETAIL_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("insMessId", queryParamJSON.getInteger("insMessId"));

        JSONObject results = new JSONObject();
        results.put("data", baseMessageInstationDAO_HI_RO.findList(querySQL, paramsMap));
        return results;
    }
}
