package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CloudInstanceNameConstants;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.common.EmailUtil;
import com.sie.saaf.message.common.PushUtil;
import com.sie.saaf.message.common.SMSUtil;
import com.sie.saaf.message.common.WechatUtil;
import com.sie.saaf.message.model.dao.MessageDAO_HI;
import com.sie.saaf.message.model.dao.readonly.MsgInstanceDAO_HI_RO;
import com.sie.saaf.message.model.entities.*;
import com.sie.saaf.message.model.entities.readonly.MsgInstanceEntity_HI_RO;
import com.sie.saaf.message.model.entities.readonly.MsgPushEntity;
import com.sie.saaf.message.model.inter.IMsgInstance;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.text.ParseException;
import java.util.*;

@Component("msgInstanceServer")
public class MsgInstanceServer extends BaseCommonServer<MsgInstanceEntity_HI> implements IMsgInstance {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgInstanceServer.class);

    @Autowired
    private MsgLogServer msgLogServer;

    @Autowired
    private MsgTempleCfgServer msgTempleCfgServer;

    @Autowired
    private MsgReceiveSqlServer msgReceiveSqlServer;

    @Autowired
    private MsgSourceCfgServer msgSourceCfgServer;

//    @Autowired
//    private MsgHistoryServer msgHistoryServer;

    @Autowired
    private MsgCfgServer msgCfgServer;


    @Autowired
    private MessageDAO_HI messageDAO_HI;

    @Autowired
    private MsgInstanceDAO_HI_RO msgInstanceDAO_HI_RO;

    @Autowired
    private ViewObject<MsgInstanceEntity_HI> msgInstanceDAO_HI;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private MsgTdServer msgTdServer;
    @Autowired
    private JedisCluster jedisCluster;
//    private List<MsgCfgEntity_HI> msgCfgList = new ArrayList<>();
//
//    private List<MsgSourceCfgEntity_HI> sourceList = new ArrayList<>();


    public MsgInstanceServer() {
        super();
    }

    public List<MsgInstanceEntity_HI> findMsgInstanceInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<MsgInstanceEntity_HI> findListResult = msgInstanceDAO_HI.findList("from MsgInstanceEntity_HI", queryParamMap);
        return findListResult;
    }

    public MsgInstanceEntity_HI saveMsgInstanceInfo(JSONObject queryParamJSON) {
        MsgInstanceEntity_HI msgInstanceEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgInstanceEntity_HI.class);
        msgInstanceDAO_HI.save(msgInstanceEntity_HI);
        return msgInstanceEntity_HI;
    }


    public Map<String, Object> saveInstance(JSONObject paramJSON) throws Exception {
        Map<String, Object> retObj = new HashMap<>();
        Map<String, String> ret = new HashMap<>();

        //实时消息字段判断
        String synchro = paramJSON.getString("synchro");//实时消息字段(Y/N) 默认N
        if (StringUtils.isEmpty(synchro)) {
            synchro = "N";
        } else if (!"Y".equals(synchro) && !"N".equals(synchro)) {
            throw new IllegalArgumentException("实时消息字段错误!必须为(Y/N) 默认不传为N");
        }


        String lbId = paramJSON.getString("labelId");//标签ID,如果按照标签ID传入则去取标签SQL中内容

        String sendTo = paramJSON.getString("sendTo");//发送对象

        String bizType = paramJSON.getString("bizType");//业务类型:   SMS：短信 WECHAT: 微信 EMAIL:邮件 ,  站内信 INMAIL, app推送 PUSH

//        String bizId = paramJSON.getString("bizId");//请求源记录的主键ID

        String message = paramJSON.getString("message");//消息内容

        String msgSubject = paramJSON.getString("msgSubject");//消息主题
        String msgUrl = paramJSON.getString("msgUrl");//消息url
        Integer userOrgId = paramJSON.getInteger("orgId");//消息url


//        JSONObject templateValue = paramJSON.getJSONObject("templateValue");

        //标签ID(labelId)和发送对象(sendTo) 只取一种类型
        if ((!StringUtils.isEmpty(lbId)) && (!StringUtils.isEmpty(sendTo))) {
            throw new IllegalArgumentException("标签ID(labelId)和发送对象(sendTo)不能同时有值!");
        }

        //业务类型 支持种类
        if (!"SMS".equals(bizType) && !"WECHAT".equals(bizType) && !"EMAIL".equals(bizType) && !"INMAIL".equals(bizType) && !"PUSH".equals(bizType)&& !"POPUP".equals(bizType)) {
            throw new IllegalArgumentException("不支持的业务类型，目前支持类型(SMS,WECHAT,EMAIL,INMAIL,PUSH,POPUP)");
        }

        //获取配置信息
        MsgCfgEntity_HI msgCfg = getMsgCfg(paramJSON);

        if (msgCfg == null) {
            throw new IllegalArgumentException("传入的msgCfgId找不到配置信息!");
        }
        if (msgCfg.getMsgSourceId() == null) {
            throw new IllegalArgumentException("消息配置中的消息源未设置!");
        }
        if (!bizType.equals(msgCfg.getMsgTypeCode())) {
            throw new IllegalArgumentException("配置信息和传入的业务类型不匹配,请检查");
        }
//        if (!userOrgId.equals(msgCfg.getOrgId())) {
//            throw new IllegalArgumentException("配置信息的事业部和用户事业部不匹配,请检查");
//        }
        if (msgCfg.getIsDelete() == 1) {
            throw new IllegalArgumentException("消息配置已被删除,请检查");
        }
        if ("0".equals(msgCfg.getEnabledFlag())) {
            throw new IllegalArgumentException("消息配置已停用,请检查");
        }
        //非模版消息时, 则消息内容和消息主题必填
        if (msgCfg.getTempleId() == null && (StringUtils.isEmpty(message) || StringUtils.isEmpty(msgSubject))) {
            throw new IllegalArgumentException("消息主题msgSubject,消息内容message,(非模版消息时必填)");
        }
        //如果是微信模式,则判断应用ID非空
        if ("WECHAT".equals(msgCfg.getMsgTypeCode()) && StringUtils.isEmpty(msgCfg.getAgentId())) {
            throw new IllegalArgumentException("如果是微信模式,应用ID(agentId)必须非空");
        }


        //接收者列表
        List<String> sendToList = new ArrayList<>();

        //优先获取接收者内容
        if (!StringUtils.isEmpty(sendTo)) {
            sendToList = Arrays.asList(paramJSON.getString("sendTo").split(";"));
        } else if (!StringUtils.isEmpty(lbId)) {
            sendToList = getReceiveList(paramJSON); //从标签ID里取人员信息
        } else {
            throw new IllegalArgumentException("请传入标签ID(labelId)或发送对象(sendTo)!");
        }
        //判断是否存在接收对象
        if (sendToList == null || sendToList.size() <= 0) {
            throw new IllegalArgumentException("接收对象为空");
        }

        //获取发送的消息内容
        String templeNameValue ="";
        JSONObject templateValueCheck = JSON.parseObject(paramJSON.getString("templateValue"));
        if(!SaafToolUtils.isNullOrEmpty(templateValueCheck)){
            templeNameValue = templateValueCheck.getString("templeName") ;
        }
        if ( (!SaafToolUtils.isNullOrEmpty(templeNameValue)) || StringUtils.isEmpty(message)) {
            Map<String, String> reMap = fillTemple(paramJSON, msgCfg.getTempleId());
            message = reMap.get("templeContent");
            msgSubject = reMap.get("templeSubject");
//            msgUrl = reMap.get("msgUrl");
        }

        paramJSON.put("message", message);
        paramJSON.put("msgSubject", msgSubject);
        paramJSON.put("msgUrl", msgUrl);

        List<JSONObject> mqList = null;
       // synchro ="Y";
        //判断是否实时消息 （实时消息不写实例表）,直接发送完毕后走历史表记录
        if ("Y".equals(synchro)) {
            sendImmediately(paramJSON, sendToList, msgCfg);
        } else {
            //非实时
            mqList = sendMQ(paramJSON, sendToList, msgCfg);
        }


        // 7.返回jobId ，要求调用方记录此id,后续查询使用
        // fill return data
        ret.put("requestId", paramJSON.getString("requestId"));
        ret.put("requestTime", SaafDateUtils.convertDateToString(new Date()));

        retObj.put("ret", ret);
        retObj.put("mqList", mqList);

        return retObj;
    }

//    private void validateSend2List(String bizType, List<String> send2List) {
//        for (String send2 : send2List) {
//            if ("EMAIL".equals(bizType)) { // 邮件
//                if (!CommonUtil.isEmail(send2)) {
//                    throw new IllegalArgumentException("邮箱地址不合法");
//                }
//            } else if ("SMS".equals(bizType)) { // 短信
//                if (!CommonUtil.isMobileNum(send2)) {
//                    throw new IllegalArgumentException("手机号码不合法");
//                }
//            } else if ("WECHAT".equals(bizType)) { // 微信
//
//            }
//        }
//    }

//    private void sendImmediately(JSONObject paramJSON) {
//        JSONObject param = new JSONObject();
//        param.put("orgId", paramJSON.getString("orgId"));
//        param.put("msgTypeCode", "SMS");
//        List<MsgSourceCfgEntity_HI> sourceList = msgSourceCfgServer.findList(param);
//        if (sourceList.isEmpty()) throw new IllegalArgumentException("非法操作：该机构下消息源配置不存在");
//        String ret = sendSMSImmediately(paramJSON, sourceList.get(0));
//    }

//    private void saveRecord(JSONObject paramJSON, String ret, String receiveCode) {
//        MsgHistoryEntity_HI history = new MsgHistoryEntity_HI();
//        history.setOrgId(paramJSON.getInteger("orgId"));
//        history.setChannelType("0");
//        history.setMsgTypeCode("SMS");
//        history.setBizId("3");
//        history.setBizType("3");
//        history.setSynchro("y");
//        history.setMsgContent(paramJSON.getString("message"));
//        history.setReceiveCode(receiveCode);
//        history.setReceiveId(receiveCode);
//        history.setReturnMsg(ret);
//        history.setOperatorUserId(-1);
//        history.setIsDelete("0");
//        msgHistoryServer.save(history);
//    }


//    private String sendSMSImmediately(JSONObject paramJSON, MsgSourceCfgEntity_HI sourceCfg) {
//        MsgInstanceEntity_HI instance = new MsgInstanceEntity_HI();
//        JSONObject sendToArray = paramJSON.getJSONObject("sendToArray");
//        for (Map.Entry<String, Object> entry : sendToArray.entrySet()) {
//            // final validation
//            String val = entry.getValue().toString();
//            if (!CommonUtil.isMobileNum(val)) {
//                throw new IllegalArgumentException("手机号码不合法");
//            }
//            instance.setReceiveCode(val);
//            instance.setMsgContent(paramJSON.getString("message"));
//            // 记录到历史表
//            String ret = SMSUtil.sendSMS(instance, sourceCfg);
//            saveRecord(paramJSON, ret, val);
//            return ret;
//        }
//        return "err";
//    }

    private void sendImmediately(JSONObject paramJSON, List<String> sendToList, MsgCfgEntity_HI msgCfg) throws Exception {
//        getInitData();

        // 获得消息源配置(可缓存至redis)
        MsgSourceCfgEntity_HI sourceCfg = msgSourceCfgServer.getById(msgCfg.getMsgSourceId());
        if (sourceCfg == null) {
            throw new IllegalArgumentException("消息源配置找不到,请联系管理员!");
        }

        if (sourceCfg.getIsDelete() == 1) {
            throw new IllegalArgumentException("消息源已被删除,请检查");
        }
        if ("0".equals(sourceCfg.getEnabledFlag())) {
            throw new IllegalArgumentException("消息源已停用,请检查");
        }


        if (!msgCfg.getMsgTypeCode().equals(sourceCfg.getMsgTypeCode())) {
            throw new IllegalArgumentException("消息源配置和消息配置的业务类型不匹配,请检查");
        }

        for (String send2 : sendToList) {

            //  MsgHistoryEntity_HI his = new MsgHistoryEntity_HI();

            JSONObject instanceJson = new JSONObject();
            instanceJson.put("orgId", paramJSON.getString("orgId"));
            instanceJson.put("jobId", paramJSON.getString("jobId"));
            instanceJson.put("msgSubject", paramJSON.getString("msgSubject"));
            instanceJson.put("channelType", msgCfg.getChannelType());
            instanceJson.put("msgTypeCode", msgCfg.getMsgTypeCode());
            instanceJson.put("msgCfgId", msgCfg.getMsgCfgId());
            instanceJson.put("receiveCode", send2);//发送人
            instanceJson.put("sourceType", paramJSON.getString("sourceType"));
            instanceJson.put("bizType", paramJSON.getString("bizType"));
            instanceJson.put("bizId", paramJSON.getString("bizId"));
            instanceJson.put("msgTemplateId", msgCfg.getTempleId());
            instanceJson.put("msgContent", paramJSON.getString("message"));
            instanceJson.put("msgPriority", "1"); //消息优先级
            instanceJson.put("sendStatus", "PENDING");//默认状态为:待发送
            instanceJson.put("operatorUserId", paramJSON.getString("userId"));//操作人为userid
            instanceJson.put("synchro", "Y");//实时,同步发送
            instanceJson.put("isDelete", "0");
            instanceJson.put("requestId", paramJSON.getString("requestId"));
            instanceJson.put("msgUrl", paramJSON.getString("msgUrl"));

            MsgInstanceEntity_HI instance = JSON.parseObject(instanceJson.toString(), MsgInstanceEntity_HI.class);

            //开始发送消息,返回信息,存入实例表
            JSONObject returnMsg = sendSMSImmediately(instance, sourceCfg, msgCfg);

            // 保存到实例表

            instance.setReturnMsg(returnMsg.getString("MSG"));
            instance.setMsgTransactionDate(new Date());
            instance.setSendDate(new Date());

            if ("E".equals(returnMsg.getString("CODE"))) {
                instance.setSendStatus("EXCEPTION");
            } else if ("F".equals(returnMsg.getString("CODE"))) {
                instance.setSendStatus("FAIL");
            } else {
                instance.setSendStatus("SUCCEED");
            }

            this.save(instance);//保存实例

        }


    }

    @Override
    public JSONObject retry(JSONObject queryParamJSON) {
        JSONObject rs = new JSONObject();
        rs.put("status", "S");
        rs.put("msg", "完成补偿");
//        List<MsgInstanceEntity_HI> instanceList = getInstanceList(queryParamJSON);
        StringBuffer sql = new StringBuffer();
        sql.append(MsgInstanceEntity_HI_RO.QUERY_COMPENSATE_SQL);
        List<MsgInstanceEntity_HI_RO> instanceList = msgInstanceDAO_HI_RO.findList(sql);

        if (instanceList != null && instanceList.size() > 0) {
            for (MsgInstanceEntity_HI_RO instanceJson : instanceList) {

                //重置状态为待发送
                this.updateStatus(instanceJson.getMsgInstanceId().toString(), "PENDING", instanceJson.getSendStatus());

                //传送MQ
                JSONObject sendJson = new JSONObject();
                sendJson.put("msgInstanceId", instanceJson.getMsgInstanceId());
                producerService.sendMessage(new ActiveMQQueue("messageCenterQueue"), sendJson.toJSONString());
            }
            rs.put("count", instanceList.size());

        }
        return rs;
    }

    @Override
    public Pagination<MsgInstanceEntity_HI_RO> findInstance(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(MsgInstanceEntity_HI_RO.QUERY_SELECT_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

            if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("msgTransactionDateStart"))){
                sql.append(" and msg_instance.msg_transaction_date >= to_date(:msgTransactionDateStart,'YYYY-MM-DD')");
                paramsMap.put("msgTransactionDateStart",queryParamJSON.getString("msgTransactionDateStart"));
            }
            if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("msgTransactionDateEnd"))){
                sql.append(" and msg_instance.msg_transaction_date <= to_date(:msgTransactionDateEnd,'YYYY-MM-DD hh24:mi:ss')");
                paramsMap.put("msgTransactionDateEnd",queryParamJSON.getString("msgTransactionDateEnd"));
            }
            if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("sendDateStart"))){
                sql.append(" and msg_instance.msg_transaction_date >= to_date(:sendDateStart,'YYYY-MM-DD')");
                paramsMap.put("sendDateStart",queryParamJSON.getString("sendDateStart"));
            }
            if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("sendDateEnd"))){
                sql.append(" and msg_instance.msg_transaction_date <= to_date(:sendDateEnd,'YYYY-MM-DD hh24:mi:ss')");
                paramsMap.put("sendDateEnd",queryParamJSON.getString("sendDateEnd"));
            }


        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.channel_type", "channelType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.msg_type_code", "msgTypeCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.send_status", "sendStatus", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.org_id", "orgId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.biz_id", "bizId", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.receive_code", "receiveCode", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.source_type", "sourceType", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.msg_subject", "msgSubject", sql, paramsMap, "fulllike");
      //  SaafToolUtils.parperParam(queryParamJSON, "msg_instance.msg_transaction_date", "msgTransactionDateStart", sql, paramsMap, ">=");
      //  SaafToolUtils.parperParam(queryParamJSON, "msg_instance.msg_transaction_date", "msgTransactionDateEnd", sql, paramsMap, "<=");
      //  SaafToolUtils.parperParam(queryParamJSON, "msg_instance.send_date", "sendDateStart", sql, paramsMap, ">=");
       // SaafToolUtils.parperParam(queryParamJSON, "msg_instance.send_date", "sendDateEnd", sql, paramsMap, "<=");
        sql.append(" order by msg_instance.msg_transaction_date desc");
        Pagination<MsgInstanceEntity_HI_RO> findList = msgInstanceDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);

        //查询数据字典
      /*  JSONArray msgTypeName = CommonServiceUtil.findDictList("MESSAGE_TYPE", "BASE");
        JSONArray sendStatusName = CommonServiceUtil.findDictList("MESSAGE_SEND_STATUS", "BASE");
        JSONArray orgName = CommonServiceUtil.findDictList("SAM_OU", "BASE");
        List<MsgInstanceEntity_HI_RO> paginatList = findList.getData();

        Iterator<MsgInstanceEntity_HI_RO> paginatListiterator = paginatList.iterator();

        while (paginatListiterator.hasNext()) {

            MsgInstanceEntity_HI_RO entity = paginatListiterator.next();

            if (("Y").equals(entity.getSynchro())) {
                entity.setSynchroName("同步");
            } else {
                entity.setSynchroName("异步");
            }
            //数据字典配对插入
            dataDictionary(entity, msgTypeName);
            dataDictionaryOrgName(entity, orgName);
            dataDictionarySendStatusName(entity, sendStatusName);
        }*/
        return findList;
    }


//    private List<MsgInstanceEntity_HI> getInstanceList(JSONObject queryParamJSON) {
//        StringBuffer sql = new StringBuffer();
//        sql.append("select msgInstanceId,msgCfgId,msgTypeCode,receiveCode,msgContent,msgSubject,orgId,bizId,synchro from MsgInstanceEntity_HI where sendStatus = 'EXCEPTION'  ");
//        if (queryParamJSON.getString("instanceId") != null && (StringUtils.isNotEmpty(queryParamJSON.getString("instanceId")))) {
//            sql.append("and instanceId =" + queryParamJSON.getString("instanceId"));
//        } else if (queryParamJSON.getString("instanceId") == null || StringUtils.isEmpty(queryParamJSON.getString("instanceId"))) {
//            sql.append("and sysdate - creationDate > 12*60*60");//为避免冲突，只处理12小时前的
//        }
//
//
//        List<MsgInstanceEntity_HI> instanceList = msgInstanceDAO_HI.findList(sql, new JSONObject());
//        return instanceList;
//    }

    private List<JSONObject> sendMQ(JSONObject paramJSON, List<String> sendToList, MsgCfgEntity_HI msgCfg) {

        List<JSONObject> sendList = new ArrayList<>();
        for (String send2 : sendToList) {
            JSONObject instanceJson = new JSONObject();
            instanceJson.put("orgId", paramJSON.getString("orgId"));
            instanceJson.put("jobId", paramJSON.getString("jobId"));
            instanceJson.put("msgSubject", paramJSON.getString("msgSubject"));
            instanceJson.put("channelType", msgCfg.getChannelType());
            instanceJson.put("msgTypeCode", msgCfg.getMsgTypeCode());
            instanceJson.put("msgCfgId", msgCfg.getMsgCfgId());
            // instanceJson.put("receiveId", send2);
            instanceJson.put("receiveCode", send2);//发送人
            // instanceJson.put("receiveName", send2);
            instanceJson.put("sourceType", paramJSON.getString("sourceType"));
            instanceJson.put("bizType", paramJSON.getString("bizType"));
            instanceJson.put("bizId", paramJSON.getString("bizId"));
            instanceJson.put("msgTemplateId", msgCfg.getTempleId());
            instanceJson.put("msgContent", paramJSON.getString("message"));
            instanceJson.put("msgPriority", "1"); //消息优先级
            instanceJson.put("sendStatus", "PENDING");//默认状态为:待发送
            instanceJson.put("operatorUserId", paramJSON.getString("userId"));//操作人为userid
            instanceJson.put("synchro", "N");
            instanceJson.put("isDelete", "0");
            instanceJson.put("requestId", paramJSON.getString("requestId"));
            instanceJson.put("msgUrl", paramJSON.getString("msgUrl"));
            instanceJson.put("msgTransactionDate", new Date());

            MsgInstanceEntity_HI instance = this.saveMsgInstanceInfo(instanceJson);//保存实例表
            //传送MQ
            JSONObject sendJson = new JSONObject();
            sendJson.put("msgInstanceId", instance.getMsgInstanceId());
//            sendJson.put("msgCfgId", instance.getMsgCfgId());
//            sendJson.put("msgTypeCode", instance.getMsgTypeCode());
//            sendJson.put("receiveCode", instance.getReceiveCode());
//            sendJson.put("msgContent", instance.getMsgContent());
//            sendJson.put("msgSubject", instance.getMsgSubject());
//            sendJson.put("orgId", instance.getOrgId());
//            sendJson.put("bizId", instance.getBizId());
            sendList.add(sendJson);
//            producerService.sendMessage(new ActiveMQQueue("messageCenterQueue"), sendJson.toJSONString());


        }

        return sendList;

    }

//    private List<MsgInstanceEntity_HI> generateInstanceMq(JSONObject paramJSON, List<String> send2List, MsgCfgEntity_HI msgCfg,String messageContent) throws IllegalArgumentException {
//
//
//        return add2Instance(paramJSON, send2List, msgCfg, messageContent);
//    }
//
//    private List<MsgInstanceEntity_HI> add2Instance(JSONObject paramJSON, List<String> send2List, MsgCfgEntity_HI msgCfg, String content, String isSynchro) throws IllegalArgumentException {
//        List<MsgInstanceEntity_HI> retList = new ArrayList<MsgInstanceEntity_HI>();
//        for (String send2 : send2List) {
//            JSONObject instanceJson = new JSONObject();
//            instanceJson.put("orgId", paramJSON.getString("orgId"));
//            instanceJson.put("jobId", paramJSON.getString("jobId"));
//            instanceJson.put("msgSubject", paramJSON.getString("msgSubject"));
//            instanceJson.put("channelType", msgCfg.getChannelType());
//            instanceJson.put("msgTypeCode", msgCfg.getMsgTypeCode());
//            instanceJson.put("msgCfgId", msgCfg.getMsgCfgId());
//            instanceJson.put("receiveId", send2);
//            instanceJson.put("receiveCode", send2);
//            instanceJson.put("receiveName", send2);
//            instanceJson.put("sourceType", paramJSON.getString("bizType"));
//            instanceJson.put("bizType", paramJSON.getString("bizType"));
//            instanceJson.put("bizId", paramJSON.getString("bizId"));
//            instanceJson.put("msgTemplateId", msgCfg.getTempleId());
//            instanceJson.put("msgContent", content);
//            instanceJson.put("msgPriority", "1"); //
//            instanceJson.put("sendStatus", "1");
//            instanceJson.put("operatorUserId", "-1");
//            instanceJson.put("synchro", "1");
//            instanceJson.put("isDelete", "0");
//
//            MsgInstanceEntity_HI instanceEntity_hi = JSON.parseObject(instanceJson.toString(), MsgInstanceEntity_HI.class);
//            if ("Y".equals(isSynchro)) {
//                this.saveOrUpdate(instanceEntity_hi);
//            }
//            retList.add(instanceEntity_hi);
//        }
//
//        return retList;
//    }

    private Map<String, String> fillTemple(JSONObject paramJSON, Integer templeId) {
        // get temple
        MsgTempleCfgEntity_HI temple = getTempleCfg(templeId);
        String templeContent = temple.getTempleContent();
        String templeSubject = temple.getTempleSubject();
        // get temple values
        JSONObject templateValue = paramJSON.getJSONObject("templateValue");
        // fill temple
        if (templateValue != null) {
            if (!StringUtils.isEmpty(templeContent)) {
                for (Map.Entry<String, Object> entry : templateValue.entrySet()) {
                    templeContent = templeContent.replace("%" + entry.getKey() + "%", entry.getValue().toString());
                }
            }
            if (!StringUtils.isEmpty(templeSubject)) {
                for (Map.Entry<String, Object> entry : templateValue.entrySet()) {
                    templeSubject = templeSubject.replace("%" + entry.getKey() + "%", entry.getValue().toString());
                }
            }
        }

        Map<String, String> reMap = new HashMap<>();
        reMap.put("templeContent", templeContent);
        reMap.put("templeSubject", templeSubject);
        reMap.put("msgUrl", temple.getMsgUrl());
        return reMap;
    }

    private MsgTempleCfgEntity_HI getTempleCfg(Integer templeId) {
        MsgTempleCfgEntity_HI temp = msgTempleCfgServer.getById(templeId);
        if (null == temp) throw new IllegalArgumentException("msgTemple配置信息不存在");
        return temp;
    }

    private MsgCfgEntity_HI getMsgCfg(JSONObject paramJSON) {

        if( (!SaafToolUtils.isNullOrEmpty(paramJSON.getString("templateValue")))
            && (!SaafToolUtils.isNullOrEmpty(JSON.parseObject(paramJSON.getString("templateValue")).getString("templeName")))   ){
            JSONObject getMsgCfgJsonObject = new JSONObject();
            getMsgCfgJsonObject.put("msgCfgName",JSON.parseObject(paramJSON.getString("templateValue")).getString("templeName"));
            List<MsgCfgEntity_HI> list = msgCfgServer.findList(getMsgCfgJsonObject);
            return list.size()>0? list.get(0):new MsgCfgEntity_HI();
        }else{
            return msgCfgServer.getById(paramJSON.getInteger("msgCfgId"));
        }

    }

    private List<String> getReceiveList(JSONObject paramJSON) {
        MsgReceiveSqlEntity_HI sqlEntity = msgReceiveSqlServer.getById(paramJSON.getInteger("labelId"));
        if (null == sqlEntity) throw new IllegalArgumentException("操作失败:配置数据不存在");
        // get label values
        JSONObject labelJson = paramJSON.getJSONObject("labelValue");
        String sql = sqlEntity.getSqlSentence();

        if (labelJson != null) {
            // fill sql
            for (Map.Entry<String, Object> entry : labelJson.entrySet()) {
                sql = sql.replace("%" + entry.getKey() + "%", entry.getValue().toString());
            }
        }

        List<Map<String, Object>> receiveList = messageDAO_HI.executeQuerySql(sql, new HashMap<String, Object>());


        List<String> sendToList = new ArrayList<>();
        if (receiveList != null && receiveList.size() > 0) {
            for (int i = 0; i < receiveList.size(); i++) {
                for (Map.Entry<String, Object> entry : receiveList.get(i).entrySet()) {
                    sendToList.add(entry.getValue().toString());
                }
            }
        }
        return sendToList;
    }

    public Integer saveRequestLog(JSONObject paramJSON) {
        // validate first
//        JSONObject queryJson = new JSONObject();
//        queryJson.put("requestId", paramJSON.getString("requestId"));
//        List<MsgLogEntity_HI> logList = msgLogServer.findList(queryJson);
//        if (logList.size() > 0) {
//            throw new IllegalArgumentException("非法操作:requestId已存在");
//        }
        // insert into log
        JSONObject logJson = new JSONObject();
        logJson.put("requestParam", paramJSON.toJSONString());
        logJson.put("requestId", paramJSON.getString("requestId"));
        logJson.put("orgId", paramJSON.getString("orgId"));
        logJson.put("jobId", paramJSON.getString("jobId"));
        logJson.put("userName", paramJSON.getString("userName"));
        logJson.put("userId", paramJSON.getString("userId"));
        logJson.put("operatorUserId", "-1");
        logJson.put("isDelete", "0");
        try {
            MsgLogEntity_HI obj = msgLogServer.saveOrUpdate(logJson);
            return obj.getLogId();
        } catch (Exception e) {
            LOGGER.error("日志保存失败:" + e.getMessage());
        }
        return null;
    }

    public void updateRequestLog(int logId, String returnStr) {
        MsgLogEntity_HI msglog = msgLogServer.getById(logId);
        if (msglog != null) {
            msglog.setReturnData(returnStr);
            msglog.setOperatorUserId(-1);
            try {
                msgLogServer.update(msglog);
            } catch (Exception e) {
                LOGGER.error("日志回写失败:" + e.getMessage());
            }
        }

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
//            LOGGER.error("转换失败，数据格式错误");
//            return new MsgInstanceEntity_HI();
//        }
//    }

//    private boolean updateStatusToFinished(String instanceId, String ret) {
//        try {
//            JSONObject param = new JSONObject();
//            param.put("msgInstanceId", instanceId);
//            MsgInstanceEntity_HI ins = this.findList(param).get(0);
//            this.deleteById(instanceId);
//
//            MsgHistoryEntity_HI his = new MsgHistoryEntity_HI();
//            BeanUtils.copyProperties(ins, his);
//            his.setSendStatus("3");
//            msgHistoryServer.save(his);
//
//            return true;
//        } catch (Exception e) {
//            LOGGER.error("存入历史表失败");
//            return false;
//        }
//        /*if (msgInstanceServer.updateStatus(instanceId, "3", "2", ret) > 0)
//            return true;*/
//    }

//    private void updateStatusToError(String instanceId) {
//        this.updateStatus(instanceId, "4", "2");
//    }

    /**
     * msgcode
     * 1:邮件
     * 2.短信
     * 3.微信
     * 4.站内信
     * 5.app推送
     */
    public JSONObject sendSMSImmediately(MsgInstanceEntity_HI instance, MsgSourceCfgEntity_HI sourceCfg, MsgCfgEntity_HI msgCfg) {
        JSONObject returnMsg = new JSONObject();


        if ("EMAIL".equals(instance.getMsgTypeCode())) {
            returnMsg = EmailUtil.sendMail(instance, sourceCfg);
        } else if ("SMS".equals(instance.getMsgTypeCode())) {

            //判断是否需要过滤黑名单,如果是Y,则需要过滤黑名单
            if ("Y".equals(msgCfg.getBlacklistFlag())) {
                //   已经退订的不再发消息
                JSONObject param = new JSONObject();
                param.put("phone", instance.getReceiveCode());//电话
                param.put("content", "TD");//回复TD
                param.put("msgSourceId", sourceCfg.getMsgSourceId());//来源id
                List<MsgTdEntity_HI> tdList = msgTdServer.findList(param);
                if (tdList != null && tdList.size() > 0) {
                    //用户已退订,不发送信息
                    returnMsg.put("CODE", "F");
                    returnMsg.put("MSG", "用户已退订,未发送信息!");
                    return returnMsg;
                }
            }

            returnMsg = SMSUtil.sendSMS(instance, sourceCfg);
        } else if ("WECHAT".equals(instance.getMsgTypeCode())) {
            returnMsg = WechatUtil.sendMsg(instance, sourceCfg, msgCfg, jedisCluster);
        } else if ("INMAIL".equals(instance.getMsgTypeCode())) {

            String messFromSystem="OA";
            String paraConfig = sourceCfg.getParamCfg();
            if(!StringUtils.isEmpty(paraConfig))
            {
                JSONObject configJson = JSONObject.parseObject(paraConfig);
                messFromSystem=configJson.getString("messFromSystem");
            }
            JSONObject inMailParams = new JSONObject();
            inMailParams.put("messTitle", instance.getMsgSubject());
            inMailParams.put("messContent", instance.getMsgContent());
            inMailParams.put("messFromSystem", messFromSystem);//OA CCCAPP  CCCAPP_POPUP
            inMailParams.put("messFromModule", "MESSAGE_CENTER");
            inMailParams.put("sourceFlag", "MESSAGE_CENTER");
            inMailParams.put("userName", instance.getReceiveCode());
            inMailParams.put("messSenderId", "1");

            String baseServerUrl = CloudInstanceNameConstants.INSTANCE_BASE + "/baseMessageInstationService/saveMessageInstation";

            JSONObject paramJSON = new JSONObject();
            JSONObject jsonArray = SaafToolUtils.preaseServiceResultJSON(baseServerUrl, paramJSON.fluentPut("params", inMailParams));
            if (jsonArray != null) {
                returnMsg.put("CODE", "S");
                returnMsg.put("MSG", "站内信发送成功!");
            } else {
                returnMsg.put("CODE", "E");
                returnMsg.put("MSG", "站内信发送失败!");
            }

        }else if("PUSH".equals(instance.getMsgTypeCode())){

            MsgPushEntity pushEntity = new MsgPushEntity();

            //设置发送内容
            pushEntity.setAlert(instance.getMsgSubject());//推送发送标题
            //设置发送人别名
            List<String> alias = new ArrayList<>();
            alias.add(instance.getReceiveCode());
            pushEntity.setAlias(alias);

            returnMsg = PushUtil.sendPush(pushEntity,sourceCfg);
        }else if("POPUP".equals(instance.getMsgTypeCode())){

            String messFromSystem="OA";
            String paraConfig = sourceCfg.getParamCfg();
            if(!StringUtils.isEmpty(paraConfig))
            {
                JSONObject configJson = JSONObject.parseObject(paraConfig);
                messFromSystem=configJson.getString("messFromSystem");
            }

            JSONObject inMailParams = new JSONObject();
            inMailParams.put("messTitle", instance.getMsgSubject());
            inMailParams.put("messContent", instance.getMsgContent());
            inMailParams.put("messFromSystem", messFromSystem);//OA CCCAPP  CCCAPP_POPUP
            inMailParams.put("messFromModule", "MESSAGE_CENTER");
            inMailParams.put("sourceFlag", "MESSAGE_CENTER");
            inMailParams.put("userName", instance.getReceiveCode());
            inMailParams.put("messSenderId", "1");
            inMailParams.put("sourceId", instance.getBizId());//传给站内信, 业务id

            String baseServerUrl = CloudInstanceNameConstants.INSTANCE_BASE + "/baseMessageInstationService/saveMessageInstation";

            JSONObject paramJSON = new JSONObject();
            JSONObject jsonArray = SaafToolUtils.preaseServiceResultJSON(baseServerUrl, paramJSON.fluentPut("params", inMailParams));
            if (jsonArray != null) {
                returnMsg.put("CODE", "S");
                returnMsg.put("MSG", "弹屏发送成功!");
            } else {
                returnMsg.put("CODE", "E");
                returnMsg.put("MSG", "弹屏发送失败!");
            }
        }
        return returnMsg;
    }

//    private boolean updateStatusToSending(String instanceId) {
//        if (this.updateStatus(instanceId, "2", "1") > 0)
//            return true;
//        return false;
//    }

    public Integer updateStatus(String instanceId, String newStatus, String oldStatus) {
        String sql = "update MsgInstanceEntity_HI set send_status = '" + newStatus + "',last_update_date=now() " +
                " where msg_instance_id =  '" + instanceId + "' and send_status='" + oldStatus + "'";

        return msgInstanceDAO_HI.executeUpdate(sql);
    }

    public Integer updateErrorStatus(String instanceId, String newStatus, String oldStatus, String msg) {
        String sql = "update MsgInstanceEntity_HI set send_status = '" + newStatus + "',last_update_date=now() ,send_date=now(),failure_times=nvl(failure_times,0)+1" +
                ",return_msg='" + msg + "' where msg_instance_id =  '" + instanceId + "' and send_status='" + oldStatus + "'";

        return msgInstanceDAO_HI.executeUpdate(sql);
    }

    public Integer updateSuccessStatus(String instanceId, String newStatus, String oldStatus, String msg) {
        String sql = "update MsgInstanceEntity_HI set send_status = '" + newStatus + "',last_update_date=now() ,send_date=now() " +
                ",return_msg='" + msg + "' where msg_instance_id =  '" + instanceId + "' and send_status='" + oldStatus + "'";

        return msgInstanceDAO_HI.executeUpdate(sql);
    }

    public String deleteMsgInstance(JSONObject queryParamJSON, int userId) {
        JSONObject jsonResult = new JSONObject();
        if (queryParamJSON.get("idDetails") == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（idDetails）", 0, null);
            return jsonResult.toString();
        }
        JSONArray msgInstanceIdDetails = queryParamJSON.getJSONArray("idDetails");
        if (msgInstanceIdDetails != null && !msgInstanceIdDetails.isEmpty()) {
            for (int i = 0; i < msgInstanceIdDetails.size(); i++) {
                JSONObject object = msgInstanceIdDetails.getJSONObject(i);
                String id = object.getString("id");
                MsgInstanceEntity_HI entity = msgInstanceDAO_HI.getById(Integer.valueOf(id));
                if (entity != null) {
                    entity.setIsDelete(CommonConstants.DELETE_TRUE);
                    entity.setOperatorUserId(userId);
                    msgInstanceDAO_HI.update(entity);
                }
            }
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
        return jsonResult.toString();
    }

    @Override
    public MsgInstanceEntity_HI_RO findMsgInstanceDetailById(JSONObject queryParamJSON) {
        Map<String, Object> paramsMap = new HashMap<>();
        StringBuffer sql = new StringBuffer(MsgInstanceEntity_HI_RO.QUERY_SELECT_SQL);
        SaafToolUtils.parperParam(queryParamJSON, "msg_instance.msg_instance_id", "id", sql, paramsMap, "=");
        List<MsgInstanceEntity_HI_RO> list = msgInstanceDAO_HI_RO.findList(sql, paramsMap);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
