package com.sie.saaf.base.sso.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.UserLoginEvent;
import com.sie.saaf.base.commmon.model.inter.IBaseData;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.server.BaseLookupValuesServer;
import com.sie.saaf.base.sso.model.entities.BaseSystemSsoEntity_HI;
import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionEntity_HI_RO;
import com.sie.saaf.base.sso.model.inter.IBaseFunctionCollection;
import com.sie.saaf.base.sso.model.inter.IBaseSystemSso;
import com.sie.saaf.base.user.model.entities.BaseLoginLogEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseLoginLog;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.base.user.services.BaseLoginService;
import com.sie.saaf.common.bean.ResponsibilityBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.exception.BusinessException;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.DesUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisCluster;
import sun.misc.BASE64Decoder;
import sun.security.krb5.internal.crypto.dk.AesDkCrypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author huangtao
 * @createTime 2017年12月13日 20:12:07
 * @description 单点登录
 */
@RestController
@RequestMapping("/baseSystemSsoService")
public class BaseSystemSsoService extends CommonAbstractService {
  private static final Logger log = LoggerFactory.getLogger(BaseSystemSsoService.class);

  @Autowired
  private IBaseData baseDataServer;
  @Autowired
  private IBaseLoginLog baseLoginLogServer;

  @Autowired
  private IBaseUsers baseUsersServer;

  @Autowired
  private IBaseSystemSso baseSystemSsoServer;

  @Autowired
  private BaseLookupValuesServer baseLookupValuesServer;

//    @Autowired
//    private IBaseFunctionCollectionUser baseFunctionCollectionUserServer;

  @Autowired
  private IBaseFunctionCollection baseFunctionCollectionServer;

  @Autowired
  private JedisCluster jedisCluster;

  @Autowired
  private ISsoServer ssoServer;

  /**
   * @param params    ｛
   *                  systemName 系统名
   *                  sysSsoId   主键id
   *                  systemCode 系统编码
   *                  clientId   clientid
   *                  requestUrl     服务地址
   *                  imageUrl       图标地址
   *                  enableFlag 生效标识
   *                  ｝
   * @param pageIndex
   * @param pageRows
   * @return
   * @description 单点登录系统分页查询
   */
  @RequestMapping(method = RequestMethod.POST, value = "find")
  public String find(@RequestParam(required = false) String params,
                     @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                     @RequestParam(required = false, defaultValue = "-1") String pageRows) {
    try {
      JSONObject jsonObject = new JSONObject();
      if (StringUtils.isNotBlank(params))
        jsonObject = JSON.parseObject(params);
      jsonObject = SaafToolUtils.cleanNull(jsonObject, "enableFlag");
      Pagination<BaseSystemSsoEntity_HI> result = baseSystemSsoServer.findSystemSsoInfoWithPage(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
      jsonObject = (JSONObject) JSON.toJSON(result);
      jsonObject.put(SToolUtils.STATUS, "S");
      jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
      return jsonObject.toString();
    } catch (IllegalArgumentException e) {
      log.warn(e.getMessage());
      return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
    }
  }


  /**
   * 获取最大序号
   *
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "getOrderNo")
  public String getOrderNo() {
    try {
      Integer val = baseSystemSsoServer.getOrderNo();
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, val).toJSONString();
    } catch (IllegalArgumentException e) {
      log.warn(e.getMessage());
      return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
    }
  }

  @Override
  public IBaseCommon<?> getBaseCommonServer() {
    return null;
  }

  /**
   * @param params ｛
   *               sysSsoId       主键Id
   *               orderNo        排序
   *               lastUpdateDate 更新日期
   *               versionNum     版本号
   *               requestMethod  请求方法 get/post
   *               description    描述
   *               params         参数
   *               creationDate   创建日期
   *               systemName     系统名称
   *               systemCode     系统编码
   *               createdBy      创建人
   *               requestUrl     服务地址
   *               imageUrl       图标地址
   *               enableFlag     是否启用
   *               ｝
   * @return
   * @throws Exception
   * @description 单点登陆表 新增/修改
   */
  @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
  public String saveOrUpdate(@RequestParam(required = true) String params) {
    try {
      JSONObject jsonObject = JSON.parseObject(params);
      BaseSystemSsoEntity_HI instance = baseSystemSsoServer.saveOrUpdateSystemSsoInfo(jsonObject, getSessionUserId());
      return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
    } catch (IllegalArgumentException e) {
      log.warn(e.getMessage());
      return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
    }
  }

  /**
   * @param params-id 主键
   * @description 删除
   */
  @RequestMapping(method = RequestMethod.POST, value = "delete")
  public String delete(@RequestParam(required = false) String params) {
    try {

      JSONObject jsonObject = JSON.parseObject(params);
      if (StringUtils.isBlank(jsonObject.getString("id"))) {
        return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
      }
      String[] ids = jsonObject.getString("id").split(",");

      baseSystemSsoServer.delete(ids);
      return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
    } catch (IllegalArgumentException e) {
      log.warn(e.getMessage());
      return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
    }
  }


  /**
   * 单点登录
   *
   * @param systemCode 系统编码
   * @param cookie     cookie
   * @return
   */
  @RequestMapping(produces = "text/html;charset=UTF-8", method = RequestMethod.GET, path = "sso/{systemCode}/{cookie}")
  public String ssologin(@PathVariable String systemCode, @PathVariable String cookie, @RequestParam String path) {
    log.info("systemCode:{},cookie:{},path:{}", systemCode, cookie, path);
    try {
      if (StringUtils.isBlank(systemCode) || StringUtils.isBlank(cookie)) {
        return "<script>alert('非法请求');window.close();</script>";
      }
      if (ssoServer.authCookie(cookie) == false) {
        return "<script>alert('登录已失效，请重新登录');window.close();</script>";
      }
      List<BaseSystemSsoEntity_HI> list = baseSystemSsoServer.findList(new JSONObject().fluentPut("systemCode", systemCode));
      if (list.size() == 0) {
        throw new IllegalArgumentException("系统编码[#]不存在".replace("#", systemCode));
      }

      //从redis中获取到跳转系统的域名 domain
      UserSessionBean userSession = ssoServer.getUserSession(cookie);
      if (StringUtils.isBlank(userSession.getDomain())) {
        throw new IllegalArgumentException("当前跳转系统[#]没携带访问域名".replace("#", systemCode));
      }
      String domain = userSession.getDomain().replace("https://", "");
      domain = domain.replace("http://", "");
      String domainUrl = jedisCluster.hget(domain, systemCode);
      String str = jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, systemCode);
      if (userSession.getDomain().toLowerCase(Locale.ENGLISH).startsWith("https") && StringUtils.isNotBlank(domainUrl)) {
        String protocol = StringUtils.isBlank(str) ? "https://" : str;
        domainUrl = protocol + domainUrl.replace("https://", "").replace("http://", "");
      }
      String url = list.get(0).getRequestUrl();
      if (!StringUtils.isBlank(domainUrl)) {
        url = domainUrl + url;
      } else {
        if ((url.toLowerCase(Locale.ENGLISH).startsWith("http://") || url.toLowerCase(Locale.ENGLISH).startsWith("https://")) == false && StringUtils.isNotBlank(path)) {
          url = path + url;
        }
      }

      // 文档系统单点登录需拼接文档系统token
//            if ("EDOC".equals(systemCode)) {
//                url += "?token=" + edocApiServer.getUserToken(userSession.getEmployeeNumber());
//            }

      log.info("url:{}", url);
      String form = createRequestForm(cookie, url, list.get(0).getRequestMethod(), list.get(0).getParams());
      return form;
    } catch (IllegalArgumentException e) {
      log.warn(e.getMessage());
      return "<script>alert('" + e.getMessage() + "');window.close();</script>";
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return "<script>alert('系统异常');window.close();</script>";
    }
  }


  @RequestMapping(produces = "text/html;charset=UTF-8", method = RequestMethod.GET, path = "functionsso/{functionCollectionUserId}/{token}")
  public String functionsso(@PathVariable Integer functionCollectionUserId,
                            @PathVariable String token) {
    try {
      UserSessionBean userSessionBean = ssoServer.getUserSession(token);
      Assert.notNull(userSessionBean, "请重新登录");
      List<BaseFunctionCollectionEntity_HI_RO> list = baseFunctionCollectionServer.findUserCollection(new JSONObject().fluentPut("functionCollectionId", functionCollectionUserId));  //
      Assert.notEmpty(list, "数据不存在,functionCollectionUserId[#]".replace("#", functionCollectionUserId + ""));
      BaseFunctionCollectionEntity_HI_RO collectinInfo = list.get(0);

      if (StringUtils.isBlank(userSessionBean.getDomain())) {
        throw new IllegalArgumentException("当前跳转系统[#]没携带访问域名".replace("#", "ESS"));
      }
      String domain = userSessionBean.getDomain().replace("https://", "");
      domain = domain.replace("http://", "");

      String domainUrl = jedisCluster.hget(domain, "ESS");

      String functionUrl = domainUrl == null ? collectinInfo.getFunctionUrl() : domainUrl + collectinInfo.getFunctionUrl();

      List<BaseSystemSsoEntity_HI> systemList = baseSystemSsoServer.findList(new JSONObject().fluentPut("systemCode", collectinInfo.getSystemCode()));
      String customsParams = "";
      if (systemList.size() > 0) {
        customsParams = systemList.get(0).getParams();
      }
      //临时方案
      if (collectinInfo.getFunctionName().contains("工作台")) {
        customsParams = new JSONArray().fluentAdd(new JSONObject().fluentPut("key", "redirectUrl").fluentPut("value", jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "ess_redirectUrl"))).toJSONString();
      }
      String form = createRequestForm(userSessionBean.getCertificate(), functionUrl, collectinInfo.getRequestMethod(), customsParams);
      return form;
    } catch (IllegalArgumentException e) {
      log.warn(e.getMessage());
      return "<script>alert('" + e.getMessage() + "');window.close()</script>";
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return "<script>alert('系统异常');window.close();</script>";
    }
  }


  private String createRequestForm(String cookie, String url, String requestMethod, String params) throws IOException {
    String form = ("<form id=\"formsubmit\" name=\"formsubmit\" action=\"#url#\" method=\"#method#\"> " +
      "<input type=\"hidden\" name=\"sign\" value=\"#sign#\"> " +
      "<input type=\"hidden\" name=\"timestamp\" value=\"#timestamp#\"> " +
      "<input type=\"hidden\" name=\"token\" value=\"#token#\"> #otherParams#" +
      "<input type=\"hidden\" name=\"outter\" value=\"#outter#\">" +
      "</form><script>document.forms['formsubmit'].submit();</script>");
    String inputTemple = " <input type=\"hidden\" name=\"#name#\" value=\"#val#\"> ";
    // BaseSystemSsoEntity_HI obj = list.get(0);
    if (StringUtils.isBlank(url)) {
      throw new IllegalArgumentException("sso配置错误");
    }
    //相对地址时从根路径跳转，避免死循环
    if (url.toLowerCase(Locale.ENGLISH).startsWith("http://") == false && url.startsWith("/") == false && url.toLowerCase(Locale.ENGLISH).startsWith("https://") == false) {
      url = "/" + url;
    }
    StringBuilder requestUrl = new StringBuilder(url);
    if (requestUrl.indexOf("?") == -1)
      requestUrl.append("?");

    StringBuilder otherParams = new StringBuilder();
    if (StringUtils.isNotBlank(params)) {
      JSONArray otherParamsArray = JSONArray.parseArray(params);
      for (int i = 0; i < otherParamsArray.size(); i++) {
        String val = otherParamsArray.getJSONObject(i).getString("value");
        otherParams.append(inputTemple.replace("#name#", otherParamsArray.getJSONObject(i).getString("key")).replace("#val#", val));
        if (StringUtils.isBlank(val))
          continue;
        try {
          requestUrl.append(otherParamsArray.getJSONObject(i).getString("key")).append("=").append(URLEncoder.encode(val, "utf-8"));
        } catch (UnsupportedEncodingException e) {
          log.warn(e.getMessage());
        }
        if (i == otherParamsArray.size() - 1)
          break;
        requestUrl.append("&");
      }
    }


    String timestamp = System.currentTimeMillis() + "";
    String sign = DigestUtils.sha256Hex(timestamp + cookie);

    requestUrl.append(requestUrl.indexOf("?") == requestUrl.length() - 1 ? "" : "&")
      .append("token=").append(cookie)
      .append("&timestamp=").append(timestamp)
      .append("&sign=").append(sign)
      .append("&outter=").append("true");

    if (StringUtils.isBlank(requestMethod) || "get".equals(requestMethod.toLowerCase(Locale.ENGLISH))) {
      response.sendRedirect(requestUrl.toString());
      return "";
    }

    form = form.replace("#token#", cookie)
      .replace("#url#", url)
      .replace("#timestamp#", timestamp)
      .replace("#sign#", sign)
      .replace("#method#", "post")
      .replace("#otherParams#", otherParams)
      .replace("#outter#", "true");
    return form;
  }


  /**
   * @param params {
   *               token: cookie
   *               timestamp: 时间戳
   *               sign:签名（ MD5(timestamp+token) ）
   *               }
   * @return
   */
  @RequestMapping(path = "authorize", method = RequestMethod.POST)
  public String authorize(@RequestParam String params) {
    // 记录
    BaseLoginLogEntity_HI loginInfo = new BaseLoginLogEntity_HI();
    loginInfo.setUserAgent(request.getHeader("User-Agent"));
    loginInfo.setIp(getIpAddr());
    loginInfo.setUserId(-1);
    String userAgent = loginInfo.getUserAgent();
    String resultStatus = "S";
    String errorMessage = null;
    try {
      JSONObject jsonObject = JSONObject.parseObject(params);
      SaafToolUtils.validateJsonParms(jsonObject, "token", "timestamp", "sign", "systemCode");
      // 系统编码
      loginInfo.setSystem(jsonObject.getString("systemCode"));
      String code = jsonObject.getString("systemCode").toUpperCase();
      if ("PUBLIC".equals(code) || "BASE".equals(code)) {
        throw new IllegalArgumentException("不可使用BASE或者PUBLIC为系统编码");
      }
      List<BaseLookupValuesEntity_HI_RO> list = baseLookupValuesServer.findBaseLookupValues("SYSTEM_CODE", code);
      if (list == null || list.isEmpty()) {
        throw new IllegalArgumentException("系统编码有误");
      }

      String token = jsonObject.getString("token");
      Long t = System.currentTimeMillis() - jsonObject.getLong("timestamp");
      t = t < 0 ? (-1 * t) : t;
      Assert.isTrue(t <= 5 * 60 * 1000, "时间戳过期");
      String sign = DigestUtils.sha256Hex(jsonObject.getString("timestamp") + token);
      if (sign.equals(jsonObject.getString("sign")) == false) {
        throw new IllegalArgumentException("签名错误");
        //return SToolUtils.convertResultJSONObj("F", "签名错误", 0, null).toString();
      }
      UserSessionBean userSessionBean = ssoServer.getUserSession(token);
      // 获取供应商信息
      List<String> vendorGroupCodes = userSessionBean.getVendorGroupCodes();
      List<String> vendorCodes = userSessionBean.getVendorCodes();
      Set<String> codes = new HashSet<>();
      codes.addAll(vendorGroupCodes);
      codes.addAll(vendorCodes);
/*            JSONObject jsonObject = new JSONObject();
            UserSessionBean userSessionBean = new UserSessionBean();
            List<String> vendorGroupCodes = new ArrayList<>();
            List<String> vendorCodes = new ArrayList<>();
            vendorGroupCodes.add("G1015694");
            vendorCodes.add("G1015694");
            Set<String> codes = new HashSet<>();
            codes.addAll(vendorGroupCodes);
            codes.addAll(vendorCodes);*/
      JSONArray supplierInfo = null;
      if (!CollectionUtils.isEmpty(codes)) {
        JSONObject param = new JSONObject();
        param.put("vendorGroupCodes", codes);
        supplierInfo = SaafToolUtils.preaseServiceResult("http://4001-saaf-vmi-server/vmi/dataCenter/VmiVendorGroupService/getVendorGroupInfo", param);
      }
      if (userSessionBean == null) {
        throw new IllegalArgumentException("token失效");
        //return SToolUtils.convertResultJSONObj("F", "token失效", 0, null).toString();
      }
      jsonObject = (JSONObject) JSON.toJSON(userSessionBean);
      jsonObject.put("supplierInfo", supplierInfo);
      jsonObject.remove("userRespList");
      jsonObject.fluentRemove("roles")
        .fluentRemove("ssoSystems")
        .fluentRemove("storeCode")
        .fluentRemove("orgId")
        .fluentRemove("orgName")
        .fluentRemove("subinvType")
        .fluentRemove("orgCode")
        .fluentRemove("leaderId")
        .fluentRemove("certificate");
      loginInfo.setUserId(jsonObject.getInteger("userId"));
      loginInfo.setUserName(jsonObject.getString("userName"));
      return SToolUtils.convertResultJSONObj("S", "success", 1, jsonObject).toString();
    } catch (JSONException e) {
      resultStatus = "F";
      errorMessage = "参数格式错误";
      return SToolUtils.convertResultJSONObj("F", "参数格式错误", 0, null).toString();
    } catch (IllegalArgumentException e) {
      resultStatus = "F";
      errorMessage = e.getMessage();
      log.warn(e.getMessage());
      return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      resultStatus = "E";
      errorMessage = e.getMessage();
      log.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
    } finally {
      if (!"S".equals(resultStatus) && StringUtils.isNotBlank(userAgent)) {
        String userAgent1 = userAgent + " " + resultStatus;
        if (userAgent1.getBytes().length <= 255) {
          loginInfo.setUserAgent(userAgent1);
        }
        String userAgent2 = userAgent1 + ",params:{" + params + "}";
        if (userAgent2.getBytes().length <= 255) {
          loginInfo.setUserAgent(userAgent2);
        }
        String userAgent3 = userAgent2 + ",error:{" + errorMessage + "}";
        if (userAgent3.getBytes().length <= 255) {
          loginInfo.setUserAgent(userAgent3);
        }
      }
      SpringBeanUtil.applicationContext.publishEvent(new UserLoginEvent("login", loginInfo));
    }
  }

  @RequestMapping(path = "getToken", method = RequestMethod.POST)
  public String getToken(@RequestBody(required = true) String params) {
    try {
      JSONObject jsonObject = JSONObject.parseObject(params);
      SaafToolUtils.validateJsonParms(jsonObject, "username", "password", "systemCode");
      String userName = jsonObject.getString("username");
      String pwd = jsonObject.getString("password");
      String code = jsonObject.getString("systemCode").toUpperCase();
      if ("PUBLIC".equals(code) || "BASE".equals(code)) {
        throw new IllegalArgumentException("不可使用BASE或者PUBLIC为系统编码");
      }
      List<BaseLookupValuesEntity_HI_RO> list = baseLookupValuesServer.findBaseLookupValues("SYSTEM_CODE", code);
      if (list == null || list.isEmpty()) {
        throw new IllegalArgumentException("系统编码有误");
      }
      BaseUsersEntity_HI user = this.baseUsersServer.findByUserName(userName);
      if (user == null) {
        throw new BusinessException("帐号或密码错误，请重新输入");
      }
      if (!"60".equals(user.getUserType()) && !"66".equals(user.getUserType())){
        throw new BusinessException("账户不存在");
      }
      log.info("传入的密码------>{}",pwd);

      String dePassword = DesUtil.AESDncode("LjdTNJh6ld8PbwgZJsbF", pwd);

      String password = com.yhg.base.utils.DigestUtils.md5(dePassword);
      if(!StringUtils.equalsIgnoreCase(password,user.getEncryptedPassword())){
        throw new BusinessException("帐号或密码错误，请重新输入");
      }

      // 状态
      Integer deleteFlag = user.getDeleteFlag();
      if (deleteFlag > 0) {
        throw new BusinessException("帐号或密码错误，请重新输入");
      }
      // 生效日期
      Date startDate = user.getStartDate();
      // 失效日期
      Date endDate = user.getEndDate();
      if (null != startDate && startDate.after(new Date())) {
        throw new BusinessException("帐号或密码错误，请重新输入");
      }
      if (null != endDate && endDate.before(new Date())) {
        throw new BusinessException("帐号或密码错误，请重新输入");
      }
      //设置用户session
      UserSessionBean userSessionBean = new UserSessionBean();
      BaseUsersPerson_HI_RO userSessionInfo = baseUsersServer.findUserSessionInfo(new JSONObject().fluentPut("userId", user.getUserId()));
      Assert.notNull(userSessionInfo, "数据异常");
      BeanUtils.copyProperties(userSessionInfo, userSessionBean);
      userSessionBean.setLastLoginDate(baseLoginLogServer.getLastLoginDate(userSessionBean.getUserId()));
      Integer userId = userSessionInfo.getUserId();

      //系统
      Set<Integer> respIdSet=new HashSet<>();

      for (ResponsibilityBean responsibilityBean:userSessionBean.getUserRespList()){
        respIdSet.add(responsibilityBean.getResponsibilityId());
      }

      userSessionBean.setVendorCodes(baseDataServer.findVendor( respIdSet,userId));
      userSessionBean.setVendorGroupCodes(baseDataServer.findVendorGroup(respIdSet,userId));
      String token = ssoServer.setCookie(userSessionBean);
      JSONObject jsonObject2 = new JSONObject();
      // 用户名
      jsonObject2.put("userName",user.getUserName());
      // token
      jsonObject2.put("token",token);
      // unix时间戳
      String dateStr = Long.toString(System.currentTimeMillis());
      jsonObject2.put("timestamp",dateStr);
      // 签名
      String sign = DigestUtils.sha256Hex(dateStr + token);
      jsonObject2.put("sign",sign);
      return SToolUtils.convertResultJSONObj("S", "success", 1, jsonObject2).toString();
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toString();
    } catch (BusinessException e) {
      log.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      e.printStackTrace();
      return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
    }
  }

}
