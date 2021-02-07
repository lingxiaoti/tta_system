package com.sie.saaf.base.user.services;

import Edge.DES.DES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.UserLoginEvent;
import com.sie.saaf.base.commmon.model.inter.IBaseData;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseDepartment;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserRole_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseRole;
import com.sie.saaf.base.sso.model.entities.readonly.BaseSystemSsoRepEntity_HI_RO;
import com.sie.saaf.base.sso.model.inter.IBaseResponsibilitySystem;
import com.sie.saaf.base.user.model.entities.BaseLoginLogEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.DomainWxEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseLoginLog;
import com.sie.saaf.base.user.model.inter.IBasePosition;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.base.user.model.inter.IDomainWx;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
import com.sie.saaf.common.bean.PositionBean;
import com.sie.saaf.common.bean.ResponsibilityBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.configration.MessageCenterConfigration;
import com.sie.saaf.common.constant.CloudInstanceNameConstants;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.exception.BusinessException;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.DesUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.base.utils.DigestUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.misc.BASE64Decoder;

import static org.apache.commons.lang.ArrayUtils.reverse;

/**
 * 登录
 *
 * @author huangtao
 */
@RestController
@RequestMapping("/baseLoginService")
public class BaseLoginService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseLoginService.class);

    @Autowired
    private IBaseLoginLog baseLoginLogServer;

    @Autowired
    private IBaseUsers baseUsersServer;

    @Autowired
    private IBaseResponsibilitySystem baseResponsibilitySystemServer;

    @Autowired
    private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

    @Autowired
    private IBaseData baseDataServer;


    @Autowired
    private IBaseRole baseRoleServer;

    @Autowired
    private ISsoServer ssoServer;

    @Autowired
    private IDomainWx domainWxServer;

    @Autowired
    private IBasePosition basePositionServer;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @Autowired
    private BaseAccreditServer baseAccreditServer;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private MessageCenterConfigration messageCenterConfigration;

    @Autowired
    private IBaseDepartment baseDepartmentServer;

    @Autowired
    private ProducerService producerService;

    //密钥 (需要前端和后端保持一致)
    private static final String KEY = "abcdefgabcdefg12";
    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 获取用户session信息
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "getUserInfo")
    public String getUserInfo() {
        try {
            Assert.notNull(getUserSessionBean(), "登录已失效");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "successs", 1, getUserSessionBean()).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
    }

    /**
     * 登录tta系统(Employee Portal 门户网站过来的
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "loginTta")
    public String loginTTa(HttpServletRequest request) {
        try {
            String empno = request.getParameter("empno");
            String language = request.getParameter("Language");
            String key = request.getParameter("key");
            String check = request.getParameter("check");

            if (null == empno) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "跳转TTA系统失败,缺失员工信息,不允许打开子系统,请联系统负责人或IT!", 0, null).toString();
            }
            System.out.println("empno :" + empno +"Language :"+ language + "key :" + key + "check : " + check);
            log.info("===========登录tta系统=====" );
            //系统来源标识
            String isEmployeePortal = "1";

            //根据员工编号去获取数据
            //解密员工账号
            String employeeNumber = DES.DecryptString(empno,key);

            BaseUsersPerson_HI_RO userLoginInfo = baseUsersServer.findUserInfoByEmployeeNumber(employeeNumber);
            if (null == userLoginInfo) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "TTA系统没有相关用户信息,请联系系统负责人或IT!", 0, null).toString();
            }
            log.info("当前登录人工号:" + employeeNumber+ "登录TTA系统");

            return loginComm(userLoginInfo.getUserId(),"","","",null,isEmployeePortal);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 登录vmi系统(Employee Portal 门户网站过来的
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "loginVmi")
    public String loginVmi(HttpServletRequest request) {
        try {
            String empno = request.getParameter("empno");
            String language = request.getParameter("Language");
            String key = request.getParameter("key");
            String check = request.getParameter("check");

            if (null == empno) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "跳转VMI系统失败,缺失员工信息,不允许打开子系统,请联系统负责人或IT!", 0, null).toString();
            }
            System.out.println("empno :" + empno +"Language :"+ language + "key :" + key + "check : " + check);
            log.info("===========登录vmi系统=====" );
            //系统来源标识
            String isEmployeePortal = "1";

            //根据员工编号去获取数据
            //解密员工账号
            String employeeNumber = DES.DecryptString(empno,key);

            BaseUsersPerson_HI_RO userLoginInfo = baseUsersServer.findUserInfoByEmployeeNumber(employeeNumber);
            if (null == userLoginInfo) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "VMI系统没有相关用户信息,请联系系统负责人或IT!", 0, null).toString();
            }
            log.info("当前登录人工号:" + employeeNumber+ "登录VMI系统");

            return loginComm(userLoginInfo.getUserId(),"","","",null,isEmployeePortal);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 登录Equotation系统(Employee Portal 门户网站过来的
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "loginEqu")
    public String loginEqu(HttpServletRequest request) {
        try {
            String empno = request.getParameter("empno");
            String language = request.getParameter("Language");
            String key = request.getParameter("key");
            String check = request.getParameter("check");

            if (null == empno) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "跳转E-Quotation系统失败,缺失员工信息,不允许打开子系统,请联系统负责人或IT!", 0, null).toString();
            }
            System.out.println("empno :" + empno +"Language :"+ language + "key :" + key + "check : " + check);
            log.info("===========登录Equotation系统=====" );
            //系统来源标识
            String isEmployeePortal = "1";

            //根据员工编号去获取数据
            //解密员工账号
            String employeeNumber = DES.DecryptString(empno,key);

            BaseUsersPerson_HI_RO userLoginInfo = baseUsersServer.findUserInfoByEmployeeNumber(employeeNumber);
            if (null == userLoginInfo) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "E-Quotation系统没有相关用户信息,请联系系统负责人或IT!", 0, null).toString();
            }
            log.info("当前登录人工号:" + employeeNumber+ "登录Equotation系统");

            return loginComm(userLoginInfo.getUserId(),"","","",null,isEmployeePortal);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 注销
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "logoff")
    public String logoff() {
        try {
            ssoServer.deleteCookie(getUserSessionBean());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public String login(@RequestParam String params) {
        try {
            JSONObject paramJson = JSON.parseObject(params);
            String userName = paramJson.getString("userName");
            String pwd = paramJson.getString("pwd");
            String redirectUrl = paramJson.getString("redirectUrl");
            String phoneNumber=paramJson.getString("phoneNumber");
            String domain = paramJson.getString("domain");
            String verificationCode = paramJson.getString("verificationCode");
            String loginMode=StringUtils.isBlank(request.getHeader("appsign"))?"1":"3";

            //校验用户名密码
            if ((StringUtils.isBlank(userName) ||  StringUtils.isBlank(pwd))&& (StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(verificationCode)) ) {
                throw new IllegalArgumentException("用户名/密码错误");
            }

            if (isPhoneNumber(userName)){
                //app手机号登录
                paramJson.put("phoneNumber",userName);
                paramJson.remove("userName");
            }else if (StringUtils.isNotBlank(userName)){
                userName = userName.toUpperCase();
                paramJson.put("userName", userName);
                paramJson.remove("phoneNumber");
            }

            BaseUsersEntity_HI user = null;
            //验证码登录
            if (isPhoneNumber(phoneNumber) && StringUtils.isNotBlank(verificationCode)){
                String key= "login_VCode_"+phoneNumber;
                if (!verificationCode.equals(jedisCluster.get(key))){
                    throw new IllegalArgumentException("验证码错误");
                }
                jedisCluster.del(key);

                List<BaseUsersEntity_HI> list = baseUsersServer.findUserEntities(paramJson);
                if (list.size() == 0) {
                    throw new BusinessException("帐号或密码错误，请重新输入");
                } else if (list.size()>1){
                    Set<String> userNames = new HashSet<>();
                    for (int i = 0, size = list.size(); i < size; i++) {
                        BaseUsersEntity_HI entity =  list.get(i);
                        userNames.add(entity.getUserName());
                    }
                    throw new BusinessException("当前手机号绑定了多个账户，绑定的工号分别是："+StringUtils.join(userNames,",")+"。请使用相应工号登录。");
                }
                user = list.get(0);
            }else {

                user = this.baseUsersServer.findByUserName(userName);

                if(user == null){
                    throw new BusinessException("帐号或密码错误，请重新输入");
                }
                //因加密组合是手机号码 + 固定注册 + 前端输入密码,复杂,2019.8.12日注释
                //String password = PasswordUtil.generateServerPwd(user.getPhoneNumber(),pwd);
                log.info("前端密码------>{}",pwd);
                String loginType = paramJson.getString("loginType") == null ? "" : paramJson.getString("loginType");
                if(StringUtils.isNotBlank(loginType) && ("equ".equals(loginType) || "TTA".equals(loginType))){
                    pwd =  aesDecrypt(pwd, KEY);
                } else {
                    int n = Integer.parseInt(pwd.substring(pwd.length() - 1, pwd.length()));
                    pwd = pwd.substring(0, pwd.length() - 1);
                    CharacterIterator ci = new StringCharacterIterator(pwd);
                    String[] pwds = new String[pwd.length()];
                    int j = 0;
                    for (char ch = ci.first(); ch != CharacterIterator.DONE; ch = ci.next()) {
                        String nn = String.valueOf(ch);
                        pwds[j++] = nn;
                    }
                    reverse(pwds);
                    pwd = "";
                    for (String s : pwds) {
                        pwd = pwd+s;
                    }
                    for (int i = 0; i < n; i++) {
                        pwd = pwd + "=";
                    }
                    System.out.println(pwd);
                    pwd = new String(Base64.decodeBase64(pwd.getBytes()));
                }

                String password = DigestUtils.md5(pwd);
                if(!StringUtils.equalsIgnoreCase(password,user.getEncryptedPassword())){
                    throw new BusinessException("帐号或密码错误，请重新输入");
                }
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

            if(!checkPasswordDate(userName)){
                throw new IllegalArgumentException("密码已过期，请点击忘记密码重新获取");
            }

            BaseLoginLogEntity_HI loginInfo=JSON.parseObject(params,BaseLoginLogEntity_HI.class);

            //return setUserSession(user, domain, redirectUrl,loginMode,loginInfo); 2019.8.7 注释
            return loginComm(user.getUserId(), domain, redirectUrl,loginMode,loginInfo,"2");
        }catch (BusinessException e){
            throw e;
        }catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
    }

    public String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return  aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public byte[] base64Decode(String base64Code) throws Exception{
        return  new BASE64Decoder().decodeBuffer(base64Code);
    }

    public String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String decryptAES(byte[] content, String secretKeyStr, String iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        // 密文进行 BASE64 解密处理
        byte[] contentDecByBase64 = java.util.Base64.getDecoder().decode(content);
        // 获得一个 SecretKeySpec
        // SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKeyStr), "AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyStr.getBytes(), "AES");
        // 获得加密算法实例对象 Cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
        // 获得一个初始化 IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 根据参数初始化算法
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 解密
        return new String(cipher.doFinal(contentDecByBase64), "utf8");
    }

    public Boolean checkPasswordDate(String userName){
        BaseUsersEntity_HI buser= baseUsersServer.findByUserName(userName);

        if("60".equals(buser.getDataType())) {
            if(buser.getPwdUpdateDate()==null){
                return false;
            }
            Date pdate = buser.getPwdUpdateDate();
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.set(Calendar.DAY_OF_YEAR, day - 90);
            Date nowDate = calendar.getTime();
            if (pdate.before(nowDate)) {
                buser.setEncryptedPassword("");
                buser.setPwdUpdateDate(new Date());
                baseUsersServer.saveOrUpdate(buser);
                return false;
            } else {
                return true;
            }
        }else{
            return true;
        }
    }


    /**
     * 公共登录方法
     * @param loginUserId 用户id
     * @param domain 登录域名
     * @param redirectUrl 重定向地址
     * @param loginMode 登录方式
     * @param loginInfo 登录信息
     * @param isEmployeeProtal 登录系统类型  1:代表是从Employee Portal跳转  2:代表是TTA系统的自带登录方式
     * @return
     */
    public String  loginComm(Integer loginUserId, String domain, String redirectUrl,String loginMode,BaseLoginLogEntity_HI loginInfo,String isEmployeeProtal) throws Exception{
        //设置用户session
        UserSessionBean userSessionBean = new UserSessionBean();
        BaseUsersPerson_HI_RO userSessionInfo = baseUsersServer.findUserSessionInfo(new JSONObject().fluentPut("userId", loginUserId));
        Assert.notNull(userSessionInfo, "数据异常");
        BeanUtils.copyProperties(userSessionInfo, userSessionBean);
        userSessionBean.setLastLoginDate(baseLoginLogServer.getLastLoginDate(userSessionBean.getUserId()));

        String personIdStr = userSessionInfo.getPersonId();
        //设置当前访问域名
        userSessionBean.setDomain(domain);
        //查询访问域名对应的OU
        DomainWxEntity_HI orgInfo = domainWxServer.findByDomain(domain);
        if (orgInfo != null) {
            userSessionBean.setOrgId(orgInfo.getConfigureId());
            userSessionBean.setOrgName(orgInfo.getDepartment());
        }
        //职责
        baseAccreditServer.findUserAccreditInfo(loginUserId, userSessionBean);

        //职位信息
        List<PositionBean> positionList = new ArrayList<>();
        if (StringUtils.isNotBlank(personIdStr)) {
            Integer personId = Integer.parseInt(personIdStr);
            userSessionBean.setPersonId(personId);
            List<BasePositionPerson_HI_RO> personPositionOrgRelationList = basePositionServer.findPersonPositionOrgRelationByPersonId(personId);
            positionList = JSON.parseArray(JSON.toJSONString(personPositionOrgRelationList), PositionBean.class);
        }
        userSessionBean.setPositionList(positionList);

        //当前登录人的顶级部门的id,名字,编号
        Integer userId = userSessionInfo.getUserId();
        BaseDepartmentEntity_HI_RO deptRo = baseDepartmentServer.findDeptList(userId);
        if (null == deptRo) {
            userSessionBean.setDeptId(-1000);
            userSessionBean.setDeptCode("");
            userSessionBean.setDeptName("");
        }else {
            Integer deptId = deptRo.getDepartmentId();
            String deptCode = "";
            String deptName = "";
            //递归找到顶级部门
            BaseDepartmentEntity_HI_RO entity_hi_ro = baseDepartmentServer.findDeptByRecursion(deptId);
            if (entity_hi_ro == null) {
                userSessionBean.setDeptId(-1000);
                userSessionBean.setDeptCode("");
                userSessionBean.setDeptName("");
            }else {
                deptId = entity_hi_ro.getDepartmentId();
                deptCode = entity_hi_ro.getDepartmentCode();
                deptName = entity_hi_ro.getDepartmentFullName();
                userSessionBean.setDeptId(deptId);
                userSessionBean.setDeptCode(deptCode);
                userSessionBean.setDeptName(deptName);
            }

        }

        //角色信息
        List<BaseUserRole_HI_RO> roles = baseRoleServer.findRoleByuserId(loginUserId);
        userSessionBean.setRoles((JSONArray) JSONArray.toJSON(roles));

        //系统
        Set<Integer> respIdSet=new HashSet<>();

        for (ResponsibilityBean responsibilityBean:userSessionBean.getUserRespList()){
            respIdSet.add(responsibilityBean.getResponsibilityId());
        }
        userSessionBean.setVendorCodes(baseDataServer.findVendor( respIdSet,userId));
        userSessionBean.setVendorGroupCodes(baseDataServer.findVendorGroup(respIdSet,userId));
        List<BaseSystemSsoRepEntity_HI_RO> systemSsoRoles = baseResponsibilitySystemServer.findSystemByResponsibilityId(respIdSet);
        userSessionBean.setSsoSystems((JSONArray) JSONArray.toJSON(systemSsoRoles));
        if (StringUtils.isNotBlank(request.getHeader("appsign")))
            userSessionBean.setFromApp(true);

        if ("2".equals(isEmployeeProtal)){ //TTA系统
            //还需要设置系统来源方式
            userSessionBean.setLoadResource(LOGIN_TTA_RESOURCE);
        } else {//employee portal系统进来
            userSessionBean.setLoadResource(LOGIN_EMPLOYEE_TOTTA);
        }
        ssoServer.setCookie(userSessionBean);

        baseAccreditServer.setDefaulOprMenuResp(userSessionBean);
        updateVendorPermission(userSessionBean.getVendorGroupCodes(),userSessionBean.getCertificate());

        //TTA系统登录方式(自身系统登录进来)
        if ("2".equals(isEmployeeProtal)) {
            //单点登录跳转
            String timestamp = System.currentTimeMillis() + "";
            String sign = org.apache.commons.codec.digest.DigestUtils.sha256Hex(timestamp + userSessionBean.getCertificate());
            if (StringUtils.isNotBlank(redirectUrl)) {
                if (redirectUrl.contains("?") == false)
                    redirectUrl += "?";
                redirectUrl = redirectUrl + "sign=" + sign + "&timestamp" + timestamp + "&token=" + userSessionBean.getCertificate();
            }

        }
        JSONObject result = (JSONObject) JSON.toJSON(userSessionBean);
        result.fluentPut("redirectUrl", redirectUrl);
        //result.put("versionNum",user.getVersionNum()); 2019.8.7 注释

        //2019.8.7号注释,不用,需要用再放开修改(TTA系统登录专用)
/*        //密码定期修改检查
        int val=pwdUpdateCheck(user);
        String status=SUCCESS_STATUS;
        if (val==1){
            status="D";
        }else if (val==2){
            status="M";
        }*/

        if ("2".equals(isEmployeeProtal)) {
            //登录事件
            if (loginInfo == null)
                loginInfo = new BaseLoginLogEntity_HI();
            loginInfo.setUserAgent(request.getHeader("User-Agent"));
            loginInfo.setUserId(userId);
            loginInfo.setUserName(userSessionBean.getUserName());
            loginInfo.setLoginMode(loginMode);
            loginInfo.setIp(getIpAddr());

            SpringBeanUtil.applicationContext.publishEvent(new UserLoginEvent("login", loginInfo));

        }
        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, result).toString();
    }

    /**
     * 跳转系统方式 : 1 :代表ep系统方式 2:TTA 系统
     * @return
     */
/*    public String getLoginPageFlag() {
        String value = CommonConstants.LOGIN_SSO_RESOURCE;
        try {
            value = jedisCluster.get("JUMP_FLAG");
            if (StringUtils.isBlank(value) ) {
                jedisCluster.set("JUMP_FLAG", CommonConstants.LOGIN_SSO_RESOURCE);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, value).toString();
    }*/

    /**  @author hmb
     * 2019.8.7 号起不用,原来是在Login方法中调用的,不能注释,因其他类有调用到
     * 改成两种登录方式,一种是从Employee portal
     * description: return setUserSession(user, domain, redirectUrl,loginMode,loginInfo);
     * @param user
     * @param domain      登录域名
     * @param redirectUrl 登录后重定向地址
     * @return
     */
    public String setUserSession(BaseUsersEntity_HI user, String domain, String redirectUrl,String loginMode,BaseLoginLogEntity_HI loginInfo) throws Exception{
        //设置用户session
        UserSessionBean userSessionBean = new UserSessionBean();
        BaseUsersPerson_HI_RO userSessionInfo = baseUsersServer.findUserSessionInfo(new JSONObject().fluentPut("userId", user.getUserId()));
        Assert.notNull(userSessionInfo, "数据异常");
        BeanUtils.copyProperties(userSessionInfo, userSessionBean);

        String personIdStr = userSessionInfo.getPersonId();
        //设置当前访问域名
        userSessionBean.setDomain(domain);
        //查询访问域名对应的OU
        DomainWxEntity_HI orgInfo = domainWxServer.findByDomain(domain);
        if (orgInfo != null) {
            userSessionBean.setOrgId(orgInfo.getConfigureId());
            userSessionBean.setOrgName(orgInfo.getDepartment());
        }
        //职责
        baseAccreditServer.findUserAccreditInfo(user.getUserId(), userSessionBean);

        //职位信息
        List<PositionBean> positionList = new ArrayList<>();
        if (StringUtils.isNotBlank(personIdStr)) {
            Integer personId = Integer.parseInt(personIdStr);
            userSessionBean.setPersonId(personId);
            List<BasePositionPerson_HI_RO> personPositionOrgRelationList = basePositionServer.findPersonPositionOrgRelationByPersonId(personId);
            positionList = JSON.parseArray(JSON.toJSONString(personPositionOrgRelationList), PositionBean.class);
        }
        userSessionBean.setPositionList(positionList);

        //当前登录人的顶级部门的id,名字,编号
        Integer userId = userSessionInfo.getUserId();
        BaseDepartmentEntity_HI_RO deptRo = baseDepartmentServer.findDeptList(userId);
        if (null == deptRo) {
            userSessionBean.setDeptId(-1000);
            userSessionBean.setDeptCode("");
            userSessionBean.setDeptName("");
        }else {
            Integer deptId = deptRo.getDepartmentId();
            String deptCode = "";
            String deptName = "";
            //递归找到顶级部门
            BaseDepartmentEntity_HI_RO entity_hi_ro = baseDepartmentServer.findDeptByRecursion(deptId);
            if (entity_hi_ro == null) {
                userSessionBean.setDeptId(-1000);
                userSessionBean.setDeptCode("");
                userSessionBean.setDeptName("");
            }else {
                deptId = entity_hi_ro.getDepartmentId();
                deptCode = entity_hi_ro.getDepartmentCode();
                deptName = entity_hi_ro.getDepartmentFullName();
                userSessionBean.setDeptId(deptId);
                userSessionBean.setDeptCode(deptCode);
                userSessionBean.setDeptName(deptName);
            }

        }

        //角色信息
        List<BaseUserRole_HI_RO> roles = baseRoleServer.findRoleByuserId(user.getUserId());
        userSessionBean.setRoles((JSONArray) JSONArray.toJSON(roles));

        //系统
        Set<Integer> respIdSet=new HashSet<>();
        for (ResponsibilityBean responsibilityBean:userSessionBean.getUserRespList()){
            respIdSet.add(responsibilityBean.getResponsibilityId());
        }
        List<BaseSystemSsoRepEntity_HI_RO> systemSsoRoles = baseResponsibilitySystemServer.findSystemByResponsibilityId(respIdSet);
        userSessionBean.setSsoSystems((JSONArray) JSONArray.toJSON(systemSsoRoles));
        if (StringUtils.isNotBlank(request.getHeader("appsign")))
            userSessionBean.setFromApp(true);
        ssoServer.setCookie(userSessionBean);
        baseAccreditServer.setDefaulOprMenuResp(userSessionBean);

        //单点登录跳转
        String timestamp = System.currentTimeMillis() + "";
        String sign = org.apache.commons.codec.digest.DigestUtils.sha256Hex(timestamp + userSessionBean.getCertificate());
        if (StringUtils.isNotBlank(redirectUrl)) {
            if (redirectUrl.contains("?") == false)
                redirectUrl += "?";
            redirectUrl = redirectUrl + "sign=" + sign + "&timestamp" + timestamp + "&token=" + userSessionBean.getCertificate();
        }
        JSONObject result = (JSONObject) JSON.toJSON(userSessionBean);
        result.fluentPut("redirectUrl", redirectUrl);
        result.put("versionNum",user.getVersionNum());

        //密码定期修改检查
        int val=pwdUpdateCheck(user);
        String status=SUCCESS_STATUS;
        if (val==1){
            status="D";
        }else if (val==2){
            status="M";
        }

        updateVendorPermission(userSessionBean.getVendorGroupCodes(),userSessionBean.getCertificate());

        //登录事件
        if (loginInfo==null)
            loginInfo=new BaseLoginLogEntity_HI();
        loginInfo.setUserAgent(request.getHeader("User-Agent"));
        loginInfo.setUserId(user.getUserId());
        loginInfo.setUserName(userSessionBean.getUserName());
        loginInfo.setLoginMode(loginMode);
        loginInfo.setIp(getIpAddr());

        SpringBeanUtil.applicationContext.publishEvent(new UserLoginEvent("login",loginInfo));

        return SToolUtils.convertResultJSONObj(status, SUCCESS_MSG, 1, result).toString();
    }


    /**
     * 密码定期修改检查
     * @param user
     * @return
     * 1:初始密码
     * 2：密码到期
     * 0：正常
     *
     *
     */
    private int pwdUpdateCheck(BaseUsersEntity_HI user){
        if (user.getPwdUpdateDate()==null)
            return 1;
        String val= jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,"pwd_change_interval");
        if (StringUtils.isBlank(val)){
            jedisCluster.hset(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,"pwd_change_interval","180");
        }
        Long time=Long.valueOf(val)*24*3600000;
        if ((System.currentTimeMillis()-user.getPwdUpdateDate().getTime())>=time)
            return 2;
        return 0;
    }

    /**
     * 心跳检测
     *
     * @author ZhangJun
     * @createTime 2018/1/29 09:07
     * @description 心跳检测
     */
    @RequestMapping(method = RequestMethod.POST, value = "checkNetwork")
    public String checkNetwork(@RequestParam(required = false) String params) {
        try {
            String date = SToolUtils.date2String(new Date(), "yyyyMMddHHmmssSSS");
            JSONObject object = new JSONObject();
            object.put("time", date);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, object).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }




    @RequestMapping(method = RequestMethod.POST, value = "getVerificationCode")
    public String getVerificationCode(@RequestParam String params) {
        try {
            JSONObject queryParamJSON = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"phoneNumber");
            List<BaseUsersEntity_HI> list = baseUsersServer.findUserEntities(queryParamJSON);
            if (list.size()==0)
                throw new IllegalArgumentException("该手机号码没有绑定账号，请联系管理员");
            else if (list.size()>1)
                throw new IllegalArgumentException("当前手机号码绑定了多个用户，无法使用短信验证登录，请联系管理员");
            log.info("requestInfo:{}", queryParamJSON);
            int random= RandomUtils.nextInt(999998)+1;
            String code=String.format("%6d",random);
            String key= "login_VCode_"+queryParamJSON.getString("phoneNumber");
            String val= jedisCluster.set(key,code,"NX","EX",60);
            Assert.notNull(val,"您的操作过于频繁，请稍后再试！");
            JSONObject jsonObject=new JSONObject();
            String uuid=UUID.randomUUID().toString();
            jsonObject.fluentPut("userName",messageCenterConfigration.getUserName())
                    .fluentPut("password",messageCenterConfigration.getPassword())
                    .fluentPut("msgCfgId",messageCenterConfigration.getLoginVerificationCodeCfgId())
                    .fluentPut("requestId",uuid)
                    .fluentPut("bizId",uuid)
                    .fluentPut("bizType","SMS")
                    .fluentPut("templateValue",new JSONObject().fluentPut("CODE",code))
                    .fluentPut("synchro","Y")
                    .fluentPut("sendTo",queryParamJSON.getString("phoneNumber"));
            if (log.isInfoEnabled())
                log.info("请求消息中心:{}",jsonObject);
             return SaafToolUtils.preaseServiceResultJSON(CloudInstanceNameConstants.INSTANCE_MESSAGE+"/messageService/sendMessage",new JSONObject().fluentPut("params",jsonObject)).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    private boolean isPhoneNumber(String str) {
        if (StringUtils.isBlank(str) || str.length() != 11)
            return false;
        String val = jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "phoneNumberRegx");
        if (StringUtils.isBlank(val)) {
            val = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
            jedisCluster.hset(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "phoneNumberRegx", val);
        }
        Pattern p = Pattern.compile(val);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 供应商分组信息在另一个库，异步更新
     */
    private void updateVendorPermission(List<String> groupCode,String cer){
        try {
            if (groupCode==null || groupCode.isEmpty() || StringUtils.isBlank(cer)){
                return;
            }
            JSONObject jsonObject=new JSONObject()
                    .fluentPut("groupCode",groupCode)
                    .fluentPut("cer",cer);
            Destination destination=new ActiveMQQueue("vendorGroupPermissionQueue");
            producerService.sendMessage(destination,jsonObject.toJSONString());
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }

    public static void main(String[] args) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        System.out.println("格式化日期:" + format);
        String str = "127.0.0.1|" + format;
        //加密
/*        String be19644a = DES.encrypt(str, "be19644a");
        System.out.println("check: " + be19644a);

        //解密
        String s = DES.DecryptString("EA90BB1AF6417BD12C11DCE936BE0489", "be19644a");
        System.out.println("员工号1:" + s);

 //解密
        String s1 = DES.DecryptString("3E25B202B1E4A5E65C25ECAF9373304409614530B38525E6", "162fe0e0");
        System.out.println("员工号2:" + s1);

        //加密工号:
        String employeeInfo = DES.encrypt("41032996", "be19644a");
        System.out.println("员工号3: " + employeeInfo);

        String ll = "10.66.13.26|20190819";
        int i = ll.indexOf("|");
        System.out.println(i);
        System.out.println(ll.substring(i+1));*/

        //加密工号:
        String employeeInfo = DES.encrypt("41032996", "162fe0e0");
        System.out.println("员工号3: " + employeeInfo);

        String be19644a = DES.encrypt("SAAF5.0", "162fe0e0");
//        String be19644a = DES.encrypt(str, "162fe0e0");
        System.out.println("check: " + be19644a);

        //162fe0e0
        String s = DES.DecryptString("CA61DF9227DFC02716EF7576CC2F5245", "162fe0e0");
        System.out.println("员工号1:" + s);

        String des_string = DES.DecryptString("B27BA31B44766B2E7949D18ECA1DA4048736C984586021C8", "162fe0e0");
        System.out.println("解密check信息:" + des_string);

        String ll = "10.66.13.26|20190819";
        int i = ll.indexOf("|");
        System.out.println(i);
        System.out.println(ll.substring(i+1));

        String dePassword = null;
        String password = null;
        try {
//            dePassword = DesUtil.AESDncode(CommonConstants.ENCODE_RULE,"gSRlyvbb/9Fe3VOA3B25dw==");
            dePassword = DesUtil.AESDncode(CommonConstants.ENCODE_RULE,"gSRlyvbb/9Fe3VOA3B25dw==");
            password = DesUtil.AESEncode(CommonConstants.ENCODE_RULE,"GpOqCyPw0U");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("解密decode信息:" + dePassword);
        System.out.println("解密encode信息:" + password);
    }

}
