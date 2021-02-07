package com.sie.saaf.message.common;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgPushEntity;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

/**
 * @author huangminglin
 * @version v1.0
 * @description: app推送工具类
 * @Name:PushUtil
 * @date 2018/10/11 10:50
 */
public class PushUtil {
    private static final Logger log = LoggerFactory.getLogger(PushUtil.class);
    /**
    * app推送
    * @author      huangminglin
    * @time        2018/10/11 10:55
    * @return      com.alibaba.fastjson.JSONObject
    */
    public static JSONObject sendPush(MsgPushEntity entity, MsgSourceCfgEntity_HI sourceCfg){
        JSONObject returnMsg = new JSONObject();

        try {

            //获取url------------------------------
            String     serverHost = null;
            String     serverPort = null;
            String     appkey = null;
            String     masterSecret = null;
            String url = null;
            String paramCfg = sourceCfg.getParamCfg();
            if(!StringUtils.isEmpty(paramCfg)) {
                JSONObject paramsCfgJSON = JSONObject.parseObject(paramCfg);
                serverHost    = paramsCfgJSON.getString("ServerHost");
                serverPort    = paramsCfgJSON.getString("ServerPort");
                appkey  = paramsCfgJSON.getString("appkey");
                masterSecret = paramsCfgJSON.getString("masterSecret");
            }else{
                throw new IllegalArgumentException("缺少源参数");
            }

            //发送url
            if(StringUtils.isEmpty(serverHost) && StringUtils.isEmpty(serverPort)) {
                url = "https://api.jpush.cn/v3/push";
            }else{
                url = (serverHost == null ? "" : serverHost) + (serverPort == null ? "" : serverPort);
            }
            //--------------------------------------------------

            //参数
            JSONObject pushParams = new JSONObject();

            //发送基本设置-----------------------------
            //设置平台
            pushParams.put("platform","all");
            //设置发送人，如果没有,则发送全部
            if(entity.getAlias() != null && !entity.getAlias().isEmpty()){
                pushParams.put("audience",new JSONObject().fluentPut("alias",entity.getAlias()));
            }else{
                pushParams.put("audience","all");
            }
            //---------------------------------------

            //发送内容设置-----------------------
            JSONObject notification = new JSONObject();
            //设置发送内容
            notification.put("alert",entity.getAlert());
            //安卓发送设置
            notification.put("android",new JSONObject());
            //ios发送设置
            notification.put("ios",new JSONObject());
            pushParams.put("notification",notification);
            //---------------------------------

            //头参数
            HttpHeaders headerParams = new HttpHeaders();

            //生成签名
            if(StringUtils.isEmpty(appkey) || StringUtils.isEmpty(masterSecret)){
                throw new IllegalArgumentException("AppKey 或 Master Secret 不能为空");
            }

            String authorization_group = appkey + ":" + masterSecret;
            String authorization = "Basic " + Base64.encodeBase64String(authorization_group.getBytes("utf-8"));
            headerParams.add("Authorization",authorization);

            JSONObject request = Postman.request(url,headerParams, pushParams);

            if (request != null) {
                returnMsg.put("CODE","S");
                returnMsg.put("MSG","消息推送成功,流水号:");
                return returnMsg;
            }else{
                returnMsg.put("CODE","E");
                returnMsg.put("MSG","消息推送失败");
                return returnMsg;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            returnMsg.put("CODE","E");
            returnMsg.put("MSG","消息推送失败"+e.getMessage());
            return returnMsg;
        }
    }

}
