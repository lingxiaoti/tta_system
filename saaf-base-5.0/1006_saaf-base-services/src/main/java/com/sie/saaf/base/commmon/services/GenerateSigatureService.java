package com.sie.saaf.base.commmon.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.inter.IGenerateSigature;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮箱服务
 * Created by huangminglin on 2018/5/24.
 */
@RestController
@RequestMapping("/generateSigatureService")
public class GenerateSigatureService extends CommonAbstractService {

    private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @Autowired
    private IGenerateSigature generateSigatureServer;


    /**
     * 网易邮箱签名服务
     * huangminglin
     * @param params
     * @return
     */
    @RequestMapping(value = "/generateSigature", method = RequestMethod.POST)
    public String generateSigature(@RequestParam String params) {
        try {

            JSONObject jsonObject = parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "src");
            String src = jsonObject.getString("src");
            String result   = generateSigatureServer.generateSigature(src);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "签名获取成功", 0, result).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }


    /**
     * 网易邮箱获取未读邮件数
     * huangminglin
     * @param params
     * @return
     */
    @RequestMapping(value = "/unreadMail", method = RequestMethod.POST)
    public String unreadMail(@RequestParam String params) {
        try {

            JSONObject jsonObject = parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "accountName");
            SaafToolUtils.validateJsonParms(jsonObject, "type");
            String accountName = jsonObject.getString("accountName");
            String type = jsonObject.getString("type");
            String result   = generateSigatureServer.unreadMail(accountName,type);
            JSONObject resultJson = JSONObject.parseObject(result);
            if(resultJson.getString("status").equals("1")) {
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "获取成功" + resultJson.getString("msg"), resultJson.getInteger("count"), null).toJSONString();
            }else{
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, resultJson.getString("msg"), resultJson.getInteger("count"), null).toJSONString();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }


    /**
     * QQ邮箱获取token
     * huangminglin
     * @param params
     * @return
     */
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public String getToken(@RequestParam String params) {
        try {

            JSONObject jsonObject = parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "corpid");
            SaafToolUtils.validateJsonParms(jsonObject, "corpsecret");
            String corpid = jsonObject.getString("corpid");
            String corpsecret = jsonObject.getString("corpsecret");
            String result   = generateSigatureServer.getToken(corpid,corpsecret);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "请求正常", 0, result).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * QQ邮箱未读数
     * huangminglin
     * @param params
     * @return
     */
    @RequestMapping(value = "/unreadQQMail", method = RequestMethod.POST)
    public String unreadQQMail(@RequestParam String params) {
        try {

            JSONObject jsonObject = parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "corpid");
            SaafToolUtils.validateJsonParms(jsonObject, "corpsecret");
            SaafToolUtils.validateJsonParms(jsonObject, "userId");
            String corpid = jsonObject.getString("corpid");
            String corpsecret = jsonObject.getString("corpsecret");
            String userId = jsonObject.getString("userId");
            String result   = generateSigatureServer.unreadQQMail(corpid,corpsecret,userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "请求正常", 0, result).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 获取登录企业邮的url  (如果不传token,就必须传corpid和corpsecret,如果传token,可以不传corpid和corpsecret)
     * huangminglin
     * @param params
     * @return
     */
    @RequestMapping(value = "/getQQMailURL", method = RequestMethod.POST)
    public String getQQMailURL(@RequestParam String params) {
        try {

            JSONObject jsonObject = parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "userId");
            String corpid = jsonObject.getString("corpid");
            String corpsecret = jsonObject.getString("corpsecret");
            String userId = jsonObject.getString("userId");
            String token = jsonObject.getString("token");
            String result   = generateSigatureServer.getQQMailURL(corpid,corpsecret,userId,token);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "请求正常", 0, result).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

}
