package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserResponsibility_HI_RO;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBasePerson;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.HttpClientUtil;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;

/**
 * @author ZhangJun
 * @creteTime 2017-12-15
 */

@RestController
@RequestMapping("/baseUsersService")
public class BaseUsersService extends CommonAbstractService {
  private static final Logger logger = LoggerFactory
    .getLogger(BaseUsersService.class);

  @Autowired
  private IBaseUsers baseUsersServer;

  @Autowired
  private BaseViewObject<BaseUsersEntity_HI_RO> baseUsersDAO_HI_RO;

  @Autowired
  private IBasePerson basePersonServer;

  @Autowired
  private JedisCluster jedisCluster;

  @Override
  public IBaseCommon getBaseCommonServer() {
    return this.baseUsersServer;
  }

  /**
   * 保存或更新数据
   *
   * @param params {<br>
   *               userId:用户Id,（更新数据时必填）<br>
   *               deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
   *               password: 用户密码（base64加密）,<br>
   *               internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
   *               isadmin: 是否是系统管理员,<br>
   *               orderNo: 排序号,<br>
   *               personId: 对应经销商、门店、员工的外围系统ID,<br>
   *               phoneNumber: 手机号码,<br>
   *               sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
   *               startDate: 生效日期,<br>
   *               endDate: 失效日期,<br>
   *               userDesc: 用户描述,<br>
   *               userFullName: 姓名,<br>
   *               userName: 用户名/登录帐号,<br>
   *               userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   *               versionNum: 版本号,（更新数据时必填）<br>
   *               "profileValues":[{"profileId":1202,"profileValue":"904"}],
   *               用户关联profile }
   * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
   * @author ZhangJun
   * @creteTime 2017/12/15
   */
  @RequestMapping(method = RequestMethod.POST, value = "save")
  @Override
  public String saveOrUpdate(@RequestParam(required = true) String params) {
    JSONObject queryParamJSON = parseObject(params);

    if (!queryParamJSON.containsKey("userFullName")
      || StringUtils.isBlank(queryParamJSON.getString("userFullName"))) {
      String userFullName = null;
      if (queryParamJSON.containsKey("userDesc") && StringUtils.isNotBlank("userDesc")) {
        userFullName = queryParamJSON.getString("userDesc").length() < 10 ? queryParamJSON.getString("userDesc") :
          queryParamJSON.getString("userDesc").substring(0, 10);
      } else {
        userFullName = queryParamJSON.getString("userName").length() < 10 ? queryParamJSON.getString("userName") :
          queryParamJSON.getString("userName").substring(0, 10);
      }
      queryParamJSON.put("userFullName", userFullName);
    }

    if (!queryParamJSON.containsKey("userId")
      || StringUtils.isBlank(queryParamJSON.getString("userId"))) {
      String userName = queryParamJSON.getString("userName");
      BaseUsersEntity_HI entity = this.baseUsersServer
        .findByUserName(userName);
      if (entity != null) {
        return SToolUtils.convertResultJSONObj(ERROR_STATUS,
          "用户名" + userName + "已存在", 0, null).toString();
      }
    }
    String userType = queryParamJSON.getString("userType");
    String iterFlag = queryParamJSON.getString("iterFlag");
    if ("20".equals(userType)) {
      String personId = queryParamJSON.getString("personId");
      if (StringUtils.isNotEmpty(personId)) {
        JSONObject isIN = new JSONObject();
        isIN.put("userType", userType);
        isIN.put("personId", personId);
        List<BaseUsersEntity_HI> list = this.baseUsersServer
          .findList(isIN);
        if (list != null
          && list.size() > 0
          && !list.get(0).getUserId()
          .equals(queryParamJSON.getInteger("userId"))) {
          return SToolUtils.convertResultJSONObj(ERROR_STATUS,
            "该员工已创建账号！", 0, null).toString();
        }
      }
    }
    JSONObject result = new JSONObject();
    try {
        boolean flag = false;
        if ("60".equals(userType) || "66".equals(userType)){
          flag = true;
        }
        logger.info(queryParamJSON.getString("userId"));
      if (queryParamJSON.getString("userId") == null
        && flag
        && null == iterFlag) {
        logger.info("/baseUsersService/save params:{}","生成邮件中");
        String newPassword = baseUsersServer
          .newPassword(queryParamJSON);
        if (!StringUtils.isEmpty(queryParamJSON.getString("password"))) {
          queryParamJSON.put("password", null);
        }
        queryParamJSON.put("encryptedPassword", newPassword);
        queryParamJSON.put("pwdUpdateDate", new Date());
      }
      result = baseUsersServer.saveUserAndProfiles(queryParamJSON);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败", 0,
        null).toJSONString();
    }
    return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
      new JSONArray().fluentAdd(result)).toString();
  }

  @RequestMapping(method = RequestMethod.POST, value = "createPassword")
  public String createPassword(@RequestParam String params) {
    JSONObject jsonObject = JSON.parseObject(params);
    SaafToolUtils.validateJsonParms(jsonObject, "userName");
    BaseUsersEntity_HI entity = null;
    try {
      entity = baseUsersServer.findByUserName(jsonObject
        .getString("userName"));
      if (entity != null) {
        baseUsersServer.createPassword(jsonObject);
        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
          1, null).toString();
      } else {
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, "用户名不存在",
          1, null).toString();
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 1, null).toString();
    }

  }

  @RequestMapping(method = RequestMethod.POST, value = "updateHeadImg")
  public String updateHeadImg(@RequestParam String params) {
    JSONObject jsonObject = JSON.parseObject(params);
    SaafToolUtils.validateJsonParms(jsonObject, "userHeadImgUrl");
    BaseUsersEntity_HI instance = baseUsersServer
      .getById(getSessionUserId());
    instance.setUserHeadImgUrl(jsonObject.getString("userHeadImgUrl"));
    baseUsersServer.update(instance);
    instance.setEncryptedPassword(null);
    return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
      instance).toString();
  }

  /**
   * 根据ID查询
   *
   * @param params {"id":1}
   * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:[{
   * userId:用户Id,（更新数据时必填） deleteFlag: 删除标记（0：未删除；1：已删除）, password:
   * 用户密码（base64加密）, internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,
   * isadmin: 是否是系统管理员, orderNo: 排序号, personId: 对应经销商、门店、员工的外围系统ID,
   * phoneNumber: 手机号码, sourceId: 关联人员ID、关联经销商ID、关联门店编码, startDate:
   * 生效日期, endDate: 失效日期, userDesc: 用户描述, userFullName: 姓名, userName:
   * 用户名/登录帐号, userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购, versionNum:
   * 版本号,（更新数据时必填） }] }
   * @author ZhangJun
   * @createTime 2018/1/11 09:39
   * @description 根据ID查询
   */
  @RequestMapping(method = RequestMethod.POST, value = "findById")
  @Override
  public String findById(String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);

      BaseUsersPerson_HI_RO entity = this.baseUsersServer
        .findBaseUsersById(queryParamJSON);

      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
        1, new JSONArray().fluentAdd(entity)).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 分页查询用户，关联人员表信息
   *
   * @param params    JSON参数 {<br>
   *                  phoneNumber:电话号码,<br>
   *                  namePingyin:姓名拼音,<br>
   *                  nameSimplePinyin:姓名拼音首字母,<br>
   *                  personId:对应经销商、门店、员工的外围系统ID,<br>
   *                  isadmin:是否是系统管理员,<br>
   *                  userName:用户名/登录帐号,<br>
   *                  userType:用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   *                  userFullName:姓名,<br>
   *                  internalUser:是否是EBS用户<br>
   *                  deleteFlag:删除标识,<br>
   *                  startDate:生效时间,<br>
   *                  endDate:失效时间,<br>
   *                  }
   * @param pageIndex
   * @param pageRows
   * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:[{<br>
   * deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
   * encryptedPassword: 用户密码（加密）,<br>
   * internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
   * isadmin: 是否是系统管理员,<br>
   * namePingyin: 用户姓名（拼音）,<br>
   * nameSimplePinyin: 用户姓名（拼音首字母）,<br>
   * orderNo: 排序号,<br>
   * personId: 对应经销商、门店、员工的外围系统ID,<br>
   * phoneNumber: 手机号码,<br>
   * sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
   * startDate: 生效日期,<br>
   * endDate: 失效日期,<br>
   * userDesc: 用户描述,<br>
   * userFullName: 姓名,<br>
   * userId: 用户Id,<br>
   * userName: 用户名/登录帐号,<br>
   * userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   * versionNum: 版本号,<br>
   * employeeNumber:员工号,<br>
   * personName:人员名称,IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
   * personType:人员类型,<br>
   * sex:性别,<br>
   * birthDay:出生日期,<br>
   * cardNo:身份证号,<br>
   * enabled:是否启用,<br>
   * telPhone:电话号码,<br>
   * mobilePhone:手机号,<br>
   * email:邮箱地址,<br>
   * postalAddress:通信地址,<br>
   * postcode:邮编<br>
   * }] }
   * @author ZhangJun
   * @createTime 2017/12/20 08:52
   * @description 分页查询用户，关联人员表信息
   */
  @RequestMapping(method = RequestMethod.POST, value = "findPagination")
  @Override
  public String findPagination(
    @RequestParam(required = false) String params,
    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,
        "userType", "isValid", "personName", "userName",
        "phoneNumber", "groupCode", "groupName");
      Pagination findList = this.baseUsersServer
        .findBaseUsersJoinPersonPagination(queryParamJSON,
          pageIndex, pageRows);

      JSONObject results = JSONObject.parseObject(JSON
        .toJSONString(findList));
      results.put(SToolUtils.STATUS, SUCCESS_STATUS);
      results.put(SToolUtils.MSG, "成功");
      return results.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "findUserRoleProfile")
  public String findUserRoleProfile(
    @RequestParam(required = false) String params,
    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,
        "userName", "userType", "userFullName", "phoneNumber",
        "email", "responsibilityName", "roleName");
      Pagination findList = this.baseUsersServer.findUserRoleProfile(
        queryParamJSON, pageIndex, pageRows);

      JSONObject results = JSONObject.parseObject(JSON
        .toJSONString(findList));
      results.put(SToolUtils.STATUS, SUCCESS_STATUS);
      results.put(SToolUtils.MSG, "成功");
      return results.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "findUserRoleMenu")
  public String findUserRoleMenu(
    @RequestParam(required = false) String params,
    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,
        "responsibilityName", "roleName", "menuName1", "menuName2",
        "menuName3");
      Pagination findList = this.baseUsersServer.findUserRoleMenu(
        queryParamJSON, pageIndex, pageRows);

      JSONObject results = JSONObject.parseObject(JSON
        .toJSONString(findList));
      results.put(SToolUtils.STATUS, SUCCESS_STATUS);
      results.put(SToolUtils.MSG, "成功");
      return results.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 分页查询用户，关联人员表信息
   *
   * @param params    {<br>
   *                  phoneNumber:电话号码,<br>
   *                  namePingyin:姓名拼音,<br>
   *                  nameSimplePinyin:姓名拼音首字母,<br>
   *                  personId:对应经销商、门店、员工的外围系统ID,<br>
   *                  isadmin:是否是系统管理员,<br>
   *                  userName:用户名/登录帐号,<br>
   *                  userFullName:姓名,<br>
   *                  internalUser:是否是EBS用户<br>
   *                  deleteFlag:删除标识,<br>
   *                  startDate:生效时间,<br>
   *                  endDate:失效时间,<br>
   *                  }
   * @param pageIndex
   * @param pageRows
   * @author laoqunzhao
   * @createTime 2018/08/14
   */
  @RequestMapping(method = RequestMethod.POST, value = "findInternalUsers")
  public String findInternalUsers(
    @RequestParam(required = false) String params,
    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,
        "userType", "isValid", "personName", "userName",
        "phoneNumber");
      // queryParamJSON.put("userType", "20");//只查询内部员工
      Pagination<BaseUsersPerson_HI_RO> findList = this.baseUsersServer
        .findBaseUsersJoinPersonPagination(queryParamJSON,
          pageIndex, pageRows);
      JSONObject results = JSONObject.parseObject(JSON
        .toJSONString(findList));
      results.put(SToolUtils.STATUS, SUCCESS_STATUS);
      results.put(SToolUtils.MSG, "成功");
      return results.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 删除数据
   *
   * @param params 参数id { id:需要删除的数据Id，如果需要删除多个，则用;分隔 }
   * @return
   * @author ZhangJun
   * @creteTime 2017/12/15
   */
  @RequestMapping(method = RequestMethod.POST, value = "delete")
  @Override
  public String delete(@RequestParam(required = false) String params) {
    return super.delete(params);
  }

  /**
   * 查询子用户
   *
   * @param params {<br>
   *               phoneNumber:电话号码,<br>
   *               namePingyin:姓名拼音,<br>
   *               nameSimplePinyin:姓名拼音首字母,<br>
   *               personId:对应经销商、门店、员工的外围系统ID,<br>
   *               isadmin:是否是系统管理员,<br>
   *               userName:用户名/登录帐号,<br>
   *               userType:用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   *               userFullName:姓名,<br>
   *               internalUser:是否是EBS用户<br>
   *               deleteFlag:删除标识,<br>
   *               startDate:生效时间,<br>
   *               endDate:失效时间,<br>
   *               }
   * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:[{<br>
   * deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
   * encryptedPassword: 用户密码（加密）,<br>
   * internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
   * isadmin: 是否是系统管理员,<br>
   * namePingyin: 用户姓名（拼音）,<br>
   * nameSimplePinyin: 用户姓名（拼音首字母）,<br>
   * orderNo: 排序号,<br>
   * personId: 对应经销商、门店、员工的外围系统ID,<br>
   * phoneNumber: 手机号码,<br>
   * sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
   * startDate: 生效日期,<br>
   * endDate: 失效日期,<br>
   * userDesc: 用户描述,<br>
   * userFullName: 姓名,<br>
   * userId: 用户Id,<br>
   * userName: 用户名/登录帐号,<br>
   * userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   * versionNum: 版本号,<br>
   * wxOpenId: 微信的OpenId,<br>
   * employeeNumber:员工号,<br>
   * personName:人员名称,IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
   * personType:人员类型,<br>
   * sex:性别,<br>
   * birthDay:出生日期,<br>
   * cardNo:身份证号,<br>
   * enabled:是否启用,<br>
   * telPhone:电话号码,<br>
   * mobilePhone:手机号,<br>
   * email:邮箱地址,<br>
   * postalAddress:通信地址,<br>
   * postcode:邮编<br>
   * }] }
   * @author ZhangJun
   * @createTime 2017/12/20 08:49
   * @description 查询子用户
   */
  @RequestMapping(method = RequestMethod.POST, value = "findChildrenUsers")
  public String findChildrenUsers(
    @RequestParam(required = false) String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      if (queryParamJSON == null || !queryParamJSON.containsKey("userId")) {
        return SToolUtils.convertResultJSONObj(ERROR_STATUS,
          "缺少参数 userId ", 0, null).toString();
      }

      List<BaseUsersPerson_HI_RO> findList = baseUsersServer
        .findChildrenUsers(queryParamJSON);

      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0,
        findList).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 根据职责查询用户
   *
   * @param params 查询参数 { responsibilityId:职责Id }
   * @return 用户与职责关系列表{ status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数
   * data:[{<br>
   * creationDate: 创建日期,<br>
   * deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
   * encryptedPassword: 用户密码（加密）,<br>
   * internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
   * isadmin: 是否是系统管理员,<br>
   * lastUpdateDate: 更新日期,<br>
   * namePingyin: 用户姓名（拼音）,<br>
   * nameSimplePinyin: 用户姓名（拼音首字母）,<br>
   * orderNo: 排序号,<br>
   * personId: 对应经销商、门店、员工的外围系统ID,<br>
   * phoneNumber: 手机号码,<br>
   * sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
   * startDate: 生效日期,<br>
   * endDate: 失效日期,<br>
   * userDesc: 用户描述,<br>
   * userFullName: 姓名,<br>
   * userId: 用户Id,<br>
   * userName: 用户名/登录帐号,<br>
   * userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   * versionNum: 版本号,<br>
   * wxOpenId: 微信的OpenId<br>
   * }] }
   * @author ZhangJun
   * @createTime 2017/12/20 08:56
   * @description 根据职责查询用户
   */
  @RequestMapping(method = RequestMethod.POST, value = "findBaseUsersByRespId")
  public String findBaseUsersByRespId(
    @RequestParam(required = false) String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      if (queryParamJSON == null
        || !queryParamJSON.containsKey("responsibilityId")) {
        return SToolUtils.convertResultJSONObj(ERROR_STATUS,
          "缺少参数 responsibilityId ", 0, null).toString();
      }

      Integer responsibilityId = queryParamJSON
        .getIntValue("responsibilityId");
      List<BaseUserResponsibility_HI_RO> findList = baseUsersServer
        .findBaserUsersByRespId(responsibilityId);

      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0,
        findList).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 修改用户密码
   *
   * @param params 参数<br>
   *               {<br>
   *               userId:用户Id<br>
   *               userName:用户名<br>
   *               oldPassword:旧密码<br>
   *               newPassword:新密码<br>
   *               }
   * @author ZhangJun
   * @createTime 2018/1/6 17:24
   * @description 修改用户密码
   */
  @RequestMapping(method = RequestMethod.POST, value = "changePassword")
  public String changePassword(@RequestParam(required = false) String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      baseUsersServer.updateUserPassword(queryParamJSON);
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0,
        null).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * @param params { phoneNumber:手机号 code: 验证码 pwd:新密码 base64编码 }
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "passwordReminder")
  public String passwordReminder(@RequestParam String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      String code = queryParamJSON.getString("code");
      String rcode = jedisCluster.get("phonecode_"
        + queryParamJSON.getString("phoneNumber"));
      if (!code.equals(rcode)) {
        throw new IllegalArgumentException("请输入正确的验证码！");
      }
      BaseUsersEntity_HI user = baseUsersServer
        .updatePasswordReminder(queryParamJSON);
      JSONObject json = new JSONObject().fluentPut("userName",
        user.getUserName());
      json.put("userId", user.getUserId());
      jedisCluster.del("phonecode_"
        + queryParamJSON.getString("phoneNumber"));
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0,
        json).toString();
    } catch (IllegalArgumentException e) {
      logger.warn(e.getMessage());
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * @param params { phoneNumber:手机号 }
   * @return
   * @description 获取验证码
   * @yuzhenli
   */
  @RequestMapping(method = RequestMethod.POST, value = "getCheckCode")
  public String getCheckCode(@RequestParam String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      SaafToolUtils.validateJsonParms(queryParamJSON, "phoneNumber");
      String phoneNumber = queryParamJSON.getString("phoneNumber");
      BaseUsersPerson_HI_RO entity = this.baseUsersServer
        .findByPhoneNumber(phoneNumber);
      if (entity != null) {
        // 生成验证码时效5min并发送验证码
        int random = RandomUtils.nextInt(999999);
        String code = String.format("%06d", random);
        jedisCluster.set("phonecode_" + phoneNumber, code);
        jedisCluster.expire("phonecode_" + phoneNumber, 300);
        String result = HttpClientUtil
          .send("http://61.145.229.29:9002/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?userId=j30199&password=080234&pszMobis="
            + phoneNumber
            + "&pszMsg="
            + code
            + "&iMobiCount=1&pszSubPort=*");
        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
          0, entity.getUserName()).toString();
      } else {
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, "不存在此号码！",
          0, null).toString();
      }
    } catch (IllegalArgumentException e) {
      logger.warn(e.getMessage());
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 通过 登录名 获取子库组织 ，菜单
   *
   * @param params loginName 登录名
   * @return
   * @author xiangyipo
   * @date 2018/2/6
   */
  @RequestMapping(method = RequestMethod.POST, value = "findBaseUserMenu")
  public String findBaseUserMenu(@RequestParam(required = false) String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      String userId = baseUsersServer.findBaseUserMenu(
        queryParamJSON.getString("loginName"),
        queryParamJSON.containsKey("filter"));
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0,
        userId).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 查询用户列表
   *
   * @param params
   * @return {@link BaseUsersEntity_HI}
   * @author ZhangJun
   * @createTime 2018/3/2
   * @description 查询用户列表
   */
  @RequestMapping(method = RequestMethod.POST, value = "findList")
  @Override
  public String findList(String params) {
    return super.findList(params);
  }

  @RequestMapping(method = RequestMethod.GET, value = "sleep")
  public String sleep(@RequestParam(defaultValue = "5000") Long time)
    throws InterruptedException {
    try {
      Thread.sleep(time);

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      logger.info("执行完成");
    }
    return SaafDateUtils.convertDateToString(new Date());

  }

  /**
   * 保存用户服务
   *
   * @author ZhangJun
   * @createTime 2018/8/7
   * @description 保存用户服务，提供给其他系统跨服务调用，有分布式事务处理
   */
  @RequestMapping(method = RequestMethod.POST, value = "createUserService")
  public String createUserService(
    @RequestParam(required = false) String params,
    @RequestParam Long messageIndex) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      JSONObject result = baseUsersServer.createUserService(
        queryParamJSON, messageIndex);
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
        result).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 失效用户服务
   *
   * @param params       { userName:用户名称 status:状态，Y：有效，N：失效 }
   * @param messageIndex
   * @author ZhangJun
   * @createTime 2018/9/6
   * @description 失效用户服务，提供给其他系统跨服务调用，有分布式事务处理
   */
  @RequestMapping(method = RequestMethod.POST, value = "expireUserService")
  public String expireUserService(
    @RequestParam(required = false) String params,
    @RequestParam Long messageIndex) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      JSONObject result = baseUsersServer.expireUser(queryParamJSON,
        messageIndex);
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
        result).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "findBaseUsersPersonAuthorityPagination")
  public String findBaseUsersPersonAuthorityPagination(
    @RequestParam(required = false) String params,
    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,
        "userType", "isValid", "personName", "userName",
        "phoneNumber");
      Pagination findList = this.baseUsersServer
        .findBaseUsersPersonAuthorityPagination(queryParamJSON,
          pageIndex, pageRows);

      JSONObject results = JSONObject.parseObject(JSON
        .toJSONString(findList));
      results.put(SToolUtils.STATUS, SUCCESS_STATUS);
      results.put(SToolUtils.MSG, "成功");
      return results.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 人员权限保存
   *
   * @param params JSON参数，对象属性的JSON格式
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAll")
  public String saveOrUpdateAll(@RequestParam(required = true) String params) {
    JSONObject queryParamJSON = parseObject(params);
    JSONObject result = new JSONObject();
    try {
      baseUsersServer.saveOrUpdateAll(queryParamJSON,
        queryParamJSON.getInteger("varUserId"));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toJSONString();
    }
    return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, null)
      .toString();
  }

  /**
   * 分页查询用户，关联人员表信息
   *
   * @param params    JSON参数 {<br>
   *                  phoneNumber:电话号码,<br>
   *                  namePingyin:姓名拼音,<br>
   *                  nameSimplePinyin:姓名拼音首字母,<br>
   *                  personId:对应经销商、门店、员工的外围系统ID,<br>
   *                  isadmin:是否是系统管理员,<br>
   *                  userName:用户名/登录帐号,<br>
   *                  userType:用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   *                  userFullName:姓名,<br>
   *                  internalUser:是否是EBS用户<br>
   *                  deleteFlag:删除标识,<br>
   *                  startDate:生效时间,<br>
   *                  endDate:失效时间,<br>
   *                  }
   * @param pageIndex
   * @param pageRows
   * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:[{<br>
   * deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
   * encryptedPassword: 用户密码（加密）,<br>
   * internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
   * isadmin: 是否是系统管理员,<br>
   * namePingyin: 用户姓名（拼音）,<br>
   * nameSimplePinyin: 用户姓名（拼音首字母）,<br>
   * orderNo: 排序号,<br>
   * personId: 对应经销商、门店、员工的外围系统ID,<br>
   * phoneNumber: 手机号码,<br>
   * sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
   * startDate: 生效日期,<br>
   * endDate: 失效日期,<br>
   * userDesc: 用户描述,<br>
   * userFullName: 姓名,<br>
   * userId: 用户Id,<br>
   * userName: 用户名/登录帐号,<br>
   * userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
   * versionNum: 版本号,<br>
   * employeeNumber:员工号,<br>
   * personName:人员名称,IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
   * personType:人员类型,<br>
   * sex:性别,<br>
   * birthDay:出生日期,<br>
   * cardNo:身份证号,<br>
   * enabled:是否启用,<br>
   * telPhone:电话号码,<br>
   * mobilePhone:手机号,<br>
   * email:邮箱地址,<br>
   * postalAddress:通信地址,<br>
   * postcode:邮编<br>
   * }] }
   * @author ZhangJun
   * @createTime 2017/12/20 08:52
   * @description 分页查询用户，关联人员表信息
   */
  @RequestMapping(method = RequestMethod.POST, value = "findPagination2")
  public String findPagination2(
    @RequestParam(required = false) String params,
    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,
        "userType", "isValid", "personName", "userName",
        "phoneNumber", "groupCode", "groupName");
      Pagination findList = this.baseUsersServer
        .findBaseUsersJoinPersonPagination2(queryParamJSON,
          pageIndex, pageRows);

      JSONObject results = JSONObject.parseObject(JSON
        .toJSONString(findList));
      results.put(SToolUtils.STATUS, SUCCESS_STATUS);
      results.put(SToolUtils.MSG, "成功");
      return results.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS,
        e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 2020/11/20
   *
   * @Title:
   * @Description: TODO(获取base_user用户列表)
   * @param @param params
   * @param @param pageIndex
   * @param @param pageRows
   * @param @return 参数
   * @return json字符串
   * @throws
   * @author:caojin
   */
  @PostMapping(value = "findBaseUsersPage")
  public String findBaseUsersPage(
          @RequestParam(required = false) String params,
          @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
          @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    try {
      JSONObject param = parseObject(params);

      Pagination<BaseUsersEntity_HI_RO> results = baseUsersServer.findBaseUsersPage(param, pageIndex, pageRows);

      String resultString = JSON.toJSONString(results);
      JSONObject jsonObject = parseObject(resultString);
      jsonObject.put(SToolUtils.STATUS, "S");
      jsonObject.put(SToolUtils.MSG, "查询成功");
      return jsonObject.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj("E",
              e.getMessage(), 0, null).toString();
    }
  }

  @GetMapping("getByUserId/{userId}")
  public BaseUsersEntity_HI getByUserId(@PathVariable("userId") Integer userId) {
    return baseUsersServer.getById(userId);
  }

}
