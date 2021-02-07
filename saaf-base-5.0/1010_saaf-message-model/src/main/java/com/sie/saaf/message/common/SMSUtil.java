package com.sie.saaf.message.common;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SimpleXml;
import com.sie.saaf.message.model.entities.MsgInstanceEntity_HI;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chenzg
 * @createTime 2018-05-30 16:35
 * @description sms util
 */
public class SMSUtil {

    public static JSONObject sendSMS(MsgInstanceEntity_HI instance, MsgSourceCfgEntity_HI sourceCfg) {
        JSONObject returnMsg = new JSONObject();

        try {
            String paraConfig = sourceCfg.getParamCfg();
            JSONObject configJson = JSONObject.parseObject(paraConfig);
            String url = "http://%serverhost%:%port%/MWGate/wmgw.asmx/MongateCsSpSendSmsNew";
            Map params = new HashMap<>();
            params.put("userId", sourceCfg.getSourceUser());
            params.put("password", sourceCfg.getSourcePwd());
            params.put("pszMobis", instance.getReceiveCode());
            params.put("pszMsg", instance.getMsgContent());
            params.put("iMobiCount", "1");
            params.put("pszSubPort", "*");
            url = url.replace("%serverhost%", configJson.getString("ServerHost")).replace("%port%", configJson.getString("ServerPort"));


            String xml = Postman.jsonPost(url, params);
            if (!"E".equals(xml)) {
                SimpleXml root = SimpleXml.read(xml);
                if (root != null && root.getSubElements() != null && root.getSubElements().size() > 0) {
                    returnMsg.put("CODE","S");
                    returnMsg.put("MSG","短信发送成功,流水号:"+root.getSubElements().get(0).getText());
                    return returnMsg;
                }
            }

            returnMsg.put("CODE","E");
            returnMsg.put("MSG","短信发送失败!");
            return returnMsg;
        }catch(Exception e){
            returnMsg.put("CODE","E");
            returnMsg.put("MSG","短信发送失败"+e.getMessage());
            return returnMsg;
        }
    }
}
