package com.sie.saaf.message.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.message.model.entities.MsgCfgEntity_HI;
import com.sie.saaf.message.model.entities.MsgInstanceEntity_HI;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import com.yhg.base.utils.StringUtils;
import redis.clients.jedis.JedisCluster;

/**
 * @author Chenzg
 * @createTime 2018-05-30 16:36
 * @description wechat util
 */
public class WechatUtil {
//    public static void main(String[] args) {
//        try {
//        System.out.println(DesUtil.decrypt("Pp9rnK/LWBH9m7ag7gJA1rkTnioZq+CCfoQgaSQDrUhkllBXbK/8pf7lVp+25n8dzBMGcTMqzf2GdX7zwP2VFh66/cB4Mzp1", DesUtil.KEY));
//        } catch (Exception e) {
//
//        }
//    }
    public static String MESSAGE_CENTER_ACCESS_TOKEN_KEY = "MESSAGE_CENTER_TOKEN_";

    public static JSONObject sendMsg(MsgInstanceEntity_HI instance, MsgSourceCfgEntity_HI sourceCfg, MsgCfgEntity_HI msgCfg, JedisCluster jedisCluster) {
        JSONObject returnMsg = new JSONObject();
        try {

//            JSONObject configJson = JSONObject.parseObject(sourceCfg.getParamCfg());
            // get token
//            Map tokenParams = new HashMap<>();


            String token = jedisCluster.get(MESSAGE_CENTER_ACCESS_TOKEN_KEY + sourceCfg.getMsgSourceId());

            if (StringUtils.isEmpty(token)) {
                //获取token
                String tokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%corpid%&corpsecret=%corpsecret%";
                tokenUrl = tokenUrl.replace("%corpid%", sourceCfg.getSourceUser()).replace("%corpsecret%", sourceCfg.getSourcePwd());

                JSONObject retJSON = JSONObject.parseObject(Postman.get(tokenUrl));
                if (!"0".equals(retJSON.getString("errcode"))) {
                    returnMsg.put("CODE", "E");
                    returnMsg.put("MSG", "获得Token失败，微信源信息有误!请联系管理员!");
                }
                token = retJSON.getString("access_token");//token
                Integer expiresTime = retJSON.getIntValue("expires_in");//失效时间
                jedisCluster.set(MESSAGE_CENTER_ACCESS_TOKEN_KEY + sourceCfg.getMsgSourceId(), token);
                jedisCluster.expire(MESSAGE_CENTER_ACCESS_TOKEN_KEY + sourceCfg.getMsgSourceId(), expiresTime);//设置过期时间
            }


//            json.put("expiresIn", retJSON.getString("expires_in"));

//            tokenParams.put("params", "{'orgId':'" + sourceCfg.getOrgId() + "'}");
//   String tokenUrl="http://192.168.21.116:8560/accessTokenService/getAccessToken";


//            JSONObject tokenRet = JSONObject.parseObject(Postman.jsonPost(configJson.getString("tokenUrl"), tokenParams));

            // get agentId
//             String token = tokenRet.getJSONObject("data").getString("accessToken");
            String agentId = msgCfg.getAgentId();


            JSONObject jsonObj = new JSONObject();
            //普通文本信息或新闻内容消息
            if (!StringUtils.isEmpty(instance.getMsgUrl())) {
                jsonObj.put("touser", instance.getReceiveCode());
                jsonObj.put("msgtype", "news");
                jsonObj.put("agentid", agentId);


                JSONObject body = new JSONObject();
                body.put("title", instance.getMsgSubject());
                body.put("description", instance.getMsgContent());
                body.put("url", instance.getMsgUrl());

                JSONObject articles = new JSONObject();
                JSONArray articleslist = new JSONArray();
                articleslist.add(body);
                articles.put("articles", articleslist);

                jsonObj.put("news", articles);

            } else {

                JSONObject body = new JSONObject();
                body.put("content", instance.getMsgContent());

                jsonObj.put("touser", instance.getReceiveCode());
                jsonObj.put("msgtype", "text");
                jsonObj.put("agentid", agentId);
                jsonObj.put("text", body);
            }


//            String jsonStr = "{\n" +
//                    "   \"touser\" : \"" + +"\",\n" +
//                    "   \"msgtype\" : \"text\",\n" +
//                    "   \"agentid\" : " + agentId + ",\n" +
//                    "   \"text\" : {\n" +
//                    "       \"content\" : \"" + instance.getMsgContent() + "\"\n" +
//                    "   },\n" +
//                    "   \"safe\":0\n" +
//                    "}";

            //  System.out.println(jsonObj.toJSONString());
            //发送消息
            String code = Postman.httpPostByRaw("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token, jsonObj.toJSONString());

            if ("E".equals(code)) {
                returnMsg.put("CODE", code);
                returnMsg.put("MSG", "微信发送失败!");
            } else {
                returnMsg.put("CODE", "S");
                returnMsg.put("MSG", "微信发送成功,返回信息:" + code);
            }
            return returnMsg;
        } catch (Exception e) {
            returnMsg.put("CODE", "E");
            returnMsg.put("MSG", "微信发送失败" + e.getMessage());
            return returnMsg;
        }

    }

//    private static String getAgentId(MsgInstanceEntity_HI instance, MsgSourceCfgEntity_HI sourceCfg) {
//        String bizId = instance.getBizId();
//        JSONObject agentObj = JSONObject.parseObject(sourceCfg.getParamCfg()).getJSONObject("agentId");
//        return agentObj.getString(bizId);
//    }

}
