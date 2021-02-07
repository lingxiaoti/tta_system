package com.sie.saaf.message.send.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.common.DesUtil;
import com.sie.saaf.message.model.entities.MsgUserEntity_HI;
import com.sie.saaf.message.model.inter.IMsgHistory;
import com.sie.saaf.message.model.inter.IMsgInstance;
import com.sie.saaf.message.model.inter.IMsgTd;
import com.sie.saaf.message.model.inter.server.MsgUserServer;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.base.utils.SToolUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/messageService")
public class MessageService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private IMsgInstance msgInstanceServer;

    @Autowired
    private IMsgHistory msgHistoryServer;
    @Autowired
    private IMsgTd msgTdServer;
    @Autowired
    private MsgUserServer msgUserServer;
    @Autowired
    private ProducerService producerService;
//    @Autowired
//    private IBaseLookupValues baseLookupValuesServer;
    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return this.msgInstanceServer;
    }

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 发送消息服务
     * 2018年07月30日15:14:13
     *
     * @param params {
     *               requestId 请求id
     *               synchro 是否实时(Y/N) 默认N
     *               bizType 业务类型 SMS：短信 WECHAT: 微信 EMAIL:邮件,  站内信 INMAIL
     *               bizId 业务主键 请求源记录的主键ID
     *               userName 用户
     *               password 密码
     *               msgCfgId 配置id
     *               templateValue 模版值
     *               sendTo 接收对象（多个以分号隔开）
     *               labelId 标签值
     *               labelValue 标签值
     *               message 消息内容,如果不配置模版消息,直接传文本内容  (非模版消息时必填)
     *               msgSubject 消息主题,如果不配置模版消息,直接传文本内容 (非模版消息时必填)
     *               <p>
     *               }
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "sendMessage")
    public String sendMessage(@RequestParam(required = true) String params) {
        Integer logId = null;
        String returnMessage = "";
        try {
            LOGGER.info("消息中心接受消息内容:" + params.toString());
            JSONObject paramJSON = JSON.parseObject(params);
            if (isNotSendMsg(paramJSON)) {
                return  SToolUtils.convertResultJSONObj("S", "服务调用成功", 1,  "redis设置不发送邮件!").toString();
            }
            // 参数非空校验
            SaafToolUtils.validateJsonParms(paramJSON, "requestId", "bizType", "userName", "msgCfgId", "password");
            String jobId = UUID.randomUUID().toString();//本次任务ID, uuid
            paramJSON.put("jobId", jobId);
            //拿到用户信息
            MsgUserEntity_HI user = validateMsgUser(paramJSON);
            paramJSON.put("orgId", user.getOrgId());
            paramJSON.put("userId", user.getMsgUserId());
            paramJSON.put("jobId", jobId);

            if (user.getNoLog() != null && user.getNoLog().equals("1")) { // 记录日志
                // insert into msg_log  记录日志
                logId = msgInstanceServer.saveRequestLog(paramJSON);
                paramJSON.put("logId", logId);
            }
            //开始实例任务
            Map<String, Object> ret = msgInstanceServer.saveInstance(paramJSON);
            returnMessage = SToolUtils.convertResultJSONObj("S", "服务调用成功", 1, ret.get("ret")).toString();


            //发送MQ
            if (ret.get("mqList") != null) {
                List<JSONObject> mqList = (List<JSONObject>) ret.get("mqList");
                for (int i = 0; i < mqList.size(); i++) {
                    producerService.sendMessage(new ActiveMQQueue("messageCenterQueue"), mqList.get(i).toJSONString());
                }
            }


            return returnMessage;
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            returnMessage = SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
            return returnMessage;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            returnMessage = SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
            return returnMessage;
        } finally {
            if (logId != null) {
                msgInstanceServer.updateRequestLog(logId, returnMessage);
            }
        }
    }


    private boolean isNotSendMsg(JSONObject paramJSON){
        boolean flag = false;
        if (paramJSON != null && paramJSON.containsKey("templateValue")) {
            JSONObject templateValue = paramJSON.getJSONObject("templateValue");
            if (templateValue != null) {
                String processKey = templateValue.getString("ProcessKey");
                String keyStrArr = jedisCluster.hget("GLOBAL_REDIS_CACHE","NOT_SEND_MSG");
                //当前方法的作用 是判断是空的，空格，制表符，tab r如果是空的就直接返回
                if (StringUtils.isNotBlank(keyStrArr) && StringUtils.isNotBlank(processKey)){
                    String[] keyArr = keyStrArr.split(",");
                    //这些字符都是以逗隔开的
                    for (String key : keyArr){
                        if ((key + "").contains(processKey)) {
                            LOGGER.info("消息中心接受消息内容:{}, KEY:{}, 设置不发送邮件!", paramJSON.toString(), processKey);
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        LOGGER.info("消息中心接受消息内容:" + paramJSON + ", flag: " + flag);
        return flag;
    }


    private MsgUserEntity_HI validateMsgUser(JSONObject paramJSON) {
        //用户密码
        String pwd = "";
        try {
            //对加密密钥进行查询解密
            String desUtilKey = msgUserServer.getDesKey();
            pwd = DesUtil.AESEncode(desUtilKey,paramJSON.getString("password"));
        } catch (Exception ex) {
            throw new IllegalArgumentException("密码加密错误");
        }
        // 用户校验
        JSONObject paramUser = new JSONObject();
        paramUser.put("msgUserName", paramJSON.get("userName"));
        paramUser.put("msgUserPwd", pwd);
        List<MsgUserEntity_HI> uList = msgUserServer.findList(paramUser);
        if (uList != null && uList.size() > 0) {
            MsgUserEntity_HI user = uList.get(0);
            return user;
        } else {
            throw new IllegalArgumentException("非法用户");
        }

    }

    /**
     * 根据外部请求ID(requestId)从消息历史记录表(MSG_HISTORY)表获得
     *
     * @param params {
     *               requestId 请求id
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "queryMessageList")
    public String queryMessageList(@RequestParam(required = true) String params) {
        try {
            LOGGER.info(params);
            JSONObject paramJSON = JSON.parseObject(params);
            // validate
            SaafToolUtils.validateJsonParms(paramJSON, "requestId");
            paramJSON.put("orderBy", "creation_date");//加入排序
            String resultStr = JSONObject.toJSONString(msgHistoryServer.findList(paramJSON));
            LOGGER.info(resultStr);
            return resultStr;
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 短信退订同步
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "getSmsExEx")
    public String getSmsExEx(@RequestParam(required = false) String params) {
        try {
            String resultStr = msgTdServer.getSmsExEx();
            LOGGER.info(params);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, resultStr).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}