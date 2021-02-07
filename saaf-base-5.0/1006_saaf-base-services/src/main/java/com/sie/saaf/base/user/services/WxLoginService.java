package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.inter.IBaseProfileValue;
import com.sie.saaf.base.shiro.model.inter.IBaseResponsibility;
import com.sie.saaf.base.shiro.model.inter.IBaseRole;
import com.sie.saaf.base.sso.model.inter.IBaseResponsibilitySystem;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.DomainWxEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.base.user.model.inter.IDomainWx;
import com.sie.saaf.common.cache.server.IRedisCacheData;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.HttpClientUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录
 */
@RestController
@RequestMapping("/wxLoginService")
public class WxLoginService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(WxLoginService.class);

    @Autowired
    private IBaseUsers baseUsersServer;

//    @Autowired
//    private IBaseResponsibilitySystem baseResponsibilitySystemServer;

//    @Autowired
//    private IBaseResponsibility baseResponsibilityServer;

//    @Autowired
//    private IBaseRole baseRoleServer;

    @Autowired
    private IDomainWx domainWxServer;

//    @Autowired
//    private ISsoServer ssoServer;
//    @Autowired
//    private IBaseProfileValue baseProfileValueServer;
//    @Autowired
//    private IRedisCacheData redisCacheDataServer;
    @Autowired
    private BaseLoginService baseLoginService;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return domainWxServer;
    }

    /**
     * 通过域名获取二维码
     *
     * @param {domain: "域名"}
     * @return 获取二维码的链接
     */
    @RequestMapping(method = RequestMethod.POST, value = "getQrCodeUrl")
    public String getQrCodeUrl(@RequestParam String params) {
        JSONObject queryParamJSON = parseObject(params);
        SaafToolUtils.validateJsonParms(queryParamJSON, "domain_like");
        String domain_like = queryParamJSON.getString("domain_like");
        queryParamJSON.put("domain_like", "%" + domain_like + "%");
        //domain = "http%3a%2f%2fsatest.iseeau.cn%3a8090%2fwxLoginService%2fcallback";
        String QrCodeUrl = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect?appid=CORP_ID&agentid=AGENT_ID&redirect_uri=http%3a%2f%2fCALLBACK_SCOPE%2fwxLoginService%2fcallback&state=web_login";
        List<DomainWxEntity_HI> list = getBaseCommonServer().findList(queryParamJSON);
        if (!list.isEmpty()) {
            String result = QrCodeUrl.replace("CORP_ID", list.get(0).getCorpId()).replace("CALLBACK_SCOPE",list.get(0).getCallbackScope()).replace("AGENT_ID", list.get(0).getAgentId() + "");
            result = result.replace("web_login",list.get(0).getCallbackScope());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, result).toString();
        } else {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "此域名未配置二维码登录！", 0, domain_like).toString();
        }
    }

    /**
     * 确认登录
     *
     * @param code 确认登录后返回的code值
     * @return
     */
    @RequestMapping(produces = "text/html;charset=UTF-8", method = RequestMethod.GET, path = "callback")
    public void confirmlogin(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state, @RequestParam(value = "appid") String appid) {
        log.info("code:{},state:{},appid:{}", code, state, appid);
        try {
            String domain = "";
            //根据授权回调域获取域名
            JSONObject queryDomain = new JSONObject();
            queryDomain.put("callbackScope_like", "%" + state + "%");
            List<DomainWxEntity_HI> list = getBaseCommonServer().findList(queryDomain);
            if (!list.isEmpty()) {
                domain=list.get(0).getDomain()+"/";
            } else {
                log.error(state+"授权回调域未配置域名！");
            }
            state = "web_login@gyoss9";
            String sendRedirectUrl=domain + "#/wwopen" + "?code=" + code + "&state=" + state + "&appid=" + appid;
            response.sendRedirect(sendRedirectUrl);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public String login(@RequestParam String params) {

        try {
            JSONObject paramJson = JSON.parseObject(params);

            String redirectUrl = paramJson.getString("redirectUrl");
            //根据域名获取 access_token
            String accessTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORP_ID&corpsecret=CORP_SECRET";
            String access_token="";
            String domain = paramJson.getString("domain");
            JSONObject queryDomainWx = new JSONObject();
            queryDomainWx.put("domain_like", "%" + domain + "%");
            List<DomainWxEntity_HI> list = getBaseCommonServer().findList(queryDomainWx);
            if (!list.isEmpty()) {
                String getAccessTokenUrl = accessTokenUrl.replace("CORP_ID", list.get(0).getCorpId()).replace("CORP_SECRET", list.get(0).getCorpSecret());
                String AccessTokenStr = HttpClientUtil.send(getAccessTokenUrl);
                JSONObject accessToken = JSON.parseObject(AccessTokenStr);
                SaafToolUtils.validateJsonParms(accessToken, "access_token");
                access_token = accessToken.getString("access_token");
            } else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "此域名未配置二维码登录！", 0, domain).toString();
            }

            String code = paramJson.getString("code");

            //code获取userId
            String getUserIdUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=" + access_token + "&code=" + code;
            String userIDStr = HttpClientUtil.send(getUserIdUrl);
            JSONObject userID = JSON.parseObject(userIDStr);
            SaafToolUtils.validateJsonParms(userID, "UserId");
            String userId = userID.getString("UserId");
            //userId获取用户信息phoneNumber
            String getUserInfoUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token + "&userid=" + userId;
            String userInfoStr = HttpClientUtil.send(getUserInfoUrl);
            JSONObject userInfo = JSON.parseObject(userInfoStr);
            SaafToolUtils.validateJsonParms(userInfo, "mobile");
            String phoneNumber = userInfo.getString("mobile");
            //设置用户session
            BaseUsersPerson_HI_RO userSessionInfo = baseUsersServer.findByPhoneNumber(phoneNumber);
            Assert.notNull(userSessionInfo, "不存在此用户，请确认企业微信与企业账号的手机号一致！");
            BaseUsersEntity_HI user = baseUsersServer.getById(userSessionInfo.getUserId());
            return baseLoginService.setUserSession(user,domain,redirectUrl,"2",null);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
    }
}
