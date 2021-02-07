package com.sie.saaf.base.user.model.inter;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserResponsibility_HI_RO;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.*;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * 接口：对用户表Base_Users进行CRUD操作<br>
 * 
 * @author ZhangJun
 * @creteTime 2017-12-11
 */
public interface IBaseUsers extends IBaseCommon<BaseUsersEntity_HI> {

	/**
	 * 根据用户名/登录帐号查询用户
	 * 
	 * @param userName
	 *            用户登录帐号/用户名
	 * @return BaseUsersEntity_HI
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	BaseUsersEntity_HI findByUserName(String userName);

	public List<BaseUsersEntity_HI> findUserEntities(JSONObject queryJSON);

	/**
	 * 查询用户下面的子用户
	 * 
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            phoneNumber:电话号码,<br>
	 *            namePingyin:姓名拼音,<br>
	 *            nameSimplePinyin:姓名拼音首字母,<br>
	 *            personId:对应经销商、门店、员工的外围系统ID,<br>
	 *            isadmin:是否是系统管理员,<br>
	 *            userName:用户名/登录帐号,<br>
	 *            userType:用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
	 *            userFullName:姓名,<br>
	 *            internalUser:是否是EBS用户<br>
	 *            deleteFlag:删除标识,<br>
	 *            startDate:生效时间,<br>
	 *            endDate:失效时间,<br>
	 *            }<br>
	 * 
	 * @return 子用户列表<br>
	 *         [{<br>
	 *         deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
	 *         encryptedPassword: 用户密码（加密）,<br>
	 *         internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
	 *         isadmin: 是否是系统管理员,<br>
	 *         namePingyin: 用户姓名（拼音）,<br>
	 *         nameSimplePinyin: 用户姓名（拼音首字母）,<br>
	 *         orderNo: 排序号,<br>
	 *         personId: 对应经销商、门店、员工的外围系统ID,<br>
	 *         phoneNumber: 手机号码,<br>
	 *         sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
	 *         startDate: 生效日期,<br>
	 *         endDate: 失效日期,<br>
	 *         userDesc: 用户描述,<br>
	 *         userFullName: 姓名,<br>
	 *         userId: 用户Id,<br>
	 *         userName: 用户名/登录帐号,<br>
	 *         userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
	 *         versionNum: 版本号,<br>
	 *         employeeNumber:员工号,<br>
	 *         personName:人员名称,IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
	 *         personType:人员类型,<br>
	 *         sex:性别,<br>
	 *         birthDay:出生日期,<br>
	 *         cardNo:身份证号,<br>
	 *         enabled:是否启用,<br>
	 *         telPhone:电话号码,<br>
	 *         mobilePhone:手机号,<br>
	 *         email:邮箱地址,<br>
	 *         postalAddress:通信地址,<br>
	 *         postcode:邮编<br>
	 *         }]
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	List<BaseUsersPerson_HI_RO> findChildrenUsers(JSONObject queryParamJSON);

	/**
	 * 分页查询用户，关联员工表查询
	 * 
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            phoneNumber:电话号码,<br>
	 *            namePingyin:姓名拼音,<br>
	 *            nameSimplePinyin:姓名拼音首字母,<br>
	 *            personId:对应经销商、门店、员工的外围系统ID,<br>
	 *            isadmin:是否是系统管理员,<br>
	 *            userName:用户名/登录帐号,<br>
	 *            userType:用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
	 *            userFullName:姓名,<br>
	 *            internalUser:是否是EBS用户<br>
	 *            deleteFlag:删除标识,<br>
	 *            startDate:生效时间,<br>
	 *            endDate:失效时间,<br>
	 *            }<br>
	 * 
	 * @return 用户列表<br>
	 *         [{<br>
	 *         deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
	 *         encryptedPassword: 用户密码（加密）,<br>
	 *         internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
	 *         isadmin: 是否是系统管理员,<br>
	 *         namePingyin: 用户姓名（拼音）,<br>
	 *         nameSimplePinyin: 用户姓名（拼音首字母）,<br>
	 *         orderNo: 排序号,<br>
	 *         personId: 对应经销商、门店、员工的外围系统ID,<br>
	 *         phoneNumber: 手机号码,<br>
	 *         sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
	 *         startDate: 生效日期,<br>
	 *         endDate: 失效日期,<br>
	 *         userDesc: 用户描述,<br>
	 *         userFullName: 姓名,<br>
	 *         userId: 用户Id,<br>
	 *         userName: 用户名/登录帐号,<br>
	 *         userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
	 *         versionNum: 版本号,<br>
	 *         employeeNumber:员工号,<br>
	 *         personName:人员名称,IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
	 *         personType:人员类型,<br>
	 *         sex:性别,<br>
	 *         birthDay:出生日期,<br>
	 *         cardNo:身份证号,<br>
	 *         enabled:是否启用,<br>
	 *         telPhone:电话号码,<br>
	 *         mobilePhone:手机号,<br>
	 *         email:邮箱地址,<br>
	 *         postalAddress:通信地址,<br>
	 *         postcode:邮编<br>
	 *         }]
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	Pagination<BaseUsersPerson_HI_RO> findBaseUsersJoinPersonPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 根据职责Id查询职责所分配的用户
	 * 
	 * @param responsibilityId
	 *            职责Id
	 * 
	 * @return 用户与职责关系列表<br>
	 *         [{<br>
	 *         creationDate: 创建日期,<br>
	 *         deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
	 *         encryptedPassword: 用户密码（加密）,<br>
	 *         internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
	 *         isadmin: 是否是系统管理员,<br>
	 *         lastUpdateDate: 更新日期,<br>
	 *         namePingyin: 用户姓名（拼音）,<br>
	 *         nameSimplePinyin: 用户姓名（拼音首字母）,<br>
	 *         orderNo: 排序号,<br>
	 *         personId: 对应经销商、门店、员工的外围系统ID,<br>
	 *         phoneNumber: 手机号码,<br>
	 *         sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
	 *         startDate: 生效日期,<br>
	 *         endDate: 失效日期,<br>
	 *         userDesc: 用户描述,<br>
	 *         userFullName: 姓名,<br>
	 *         userId: 用户Id,<br>
	 *         userName: 用户名/登录帐号,<br>
	 *         userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
	 *         versionNum: 版本号,<br>
	 *         }]
	 * 
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	List<BaseUserResponsibility_HI_RO> findBaserUsersByRespId(
			Integer responsibilityId);

	/**
	 * 根据组织机构Id查询用户
	 * 
	 * @param orgId
	 *            组织机构Id
	 * @param queryParamJSON
	 *            查询参数
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 关联用户表<br>
	 *         [{<br>
	 *         deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
	 *         encryptedPassword: 用户密码（加密）,<br>
	 *         internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
	 *         isadmin: 是否是系统管理员,<br>
	 *         namePingyin: 用户姓名（拼音）,<br>
	 *         nameSimplePinyin: 用户姓名（拼音首字母）,<br>
	 *         orderNo: 排序号,<br>
	 *         personId: 对应经销商、门店、员工的外围系统ID,<br>
	 *         phoneNumber: 手机号码,<br>
	 *         sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
	 *         startDate: 生效日期,<br>
	 *         endDate: 失效日期,<br>
	 *         userDesc: 用户描述,<br>
	 *         userFullName: 姓名,<br>
	 *         userId: 用户Id,<br>
	 *         userName: 用户名/登录帐号,<br>
	 *         userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
	 *         versionNum: 版本号,<br>
	 *         employeeNumber:员工号,<br>
	 *         personName:人员名称,IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
	 *         personType:人员类型,<br>
	 *         sex:性别,<br>
	 *         birthDay:出生日期,<br>
	 *         cardNo:身份证号,<br>
	 *         enabled:是否启用,<br>
	 *         telPhone:电话号码,<br>
	 *         mobilePhone:手机号,<br>
	 *         email:邮箱地址,<br>
	 *         postalAddress:通信地址,<br>
	 *         postcode:邮编<br>
	 *         positionId:职位ID<br>
	 *         orgId:组织机构Id<br>
	 *         }]
	 * @author ZhangJun
	 * @createTime 2017/12/25 10:19
	 * @description 根据组织机构Id查询用户
	 */
	Pagination<BaseUsersOrganization_HI_RO> findBaseUsersByOrgId(Integer orgId,
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * @see
	 * @param paramJson
	 * @return
	 */
	BaseUsersPerson_HI_RO findUserSessionInfo(JSONObject paramJson);

	/**
	 * 根据员工编号查询用户信息
	 * 
	 * @param employeeNumber
	 *            员工编号
	 * @return
	 */
	BaseUsersPerson_HI_RO findUserInfoByEmployeeNumber(String employeeNumber);

	/**
	 * 根据微信公众号查询用户
	 * 
	 * @param wxOpenid
	 *            微信公众号Id
	 * @return BaseWechatUsers_HI_RO
	 * @author ZhangJun
	 * @createTime 2017/12/27 15:05
	 * @description 根据微信公众号查询用户
	 */
	BaseWechatUsers_HI_RO findUsersByWxOpenId(String wxOpenid);

	/**
	 * 更新用户密码
	 * 
	 * @param queryParamJSON
	 *            参数 {<br>
	 *            userId:用户Id<br>
	 *            userName:用户名<br>
	 *            oldPassword:旧密码<br>
	 *            newPassword:新密码<br>
	 *            }
	 * @author ZhangJun
	 * @createTime 2018/1/6 17:29
	 * @description 更新用户密码
	 */
	void updateUserPassword(JSONObject queryParamJSON);

	BaseUsersEntity_HI updatePasswordReminder(JSONObject queryParamJSON);

	/**
	 * 根据UserId查询用户及员工信息
	 * 
	 * @param queryParamJSON
	 *            { id:用户Id(user_id) }
	 * @return {@link BaseUsersPerson_HI_RO}
	 * @author ZhangJun
	 * @createTime 2018/1/24 15:04
	 * @description 根据UserId查询用户及员工信息
	 */
	BaseUsersPerson_HI_RO findBaseUsersById(JSONObject queryParamJSON);

	/**
	 * userNane 用户子库、组织 查询用户菜单
	 * 
	 * @param userNane
	 * @return
	 * @author xiangyipo
	 * @date 2018/2/5
	 */

	String findBaseUserMenu(String userNane, boolean filter);

	/**
	 * 根据手机号查询用户
	 * 
	 * @param phoneNumber
	 *            手机号
	 * @return BaseUsersPerson_HI_RO
	 * @author yuzhenli
	 * @creteTime 2017/2/6
	 */
	BaseUsersPerson_HI_RO findByPhoneNumber(String phoneNumber);

	/**
	 * 保存用户及Profile
	 * 
	 * @author ZhangJun
	 * @createTime 2018/4/16
	 * @description 保存用户及Profile
	 */
	JSONObject saveUserAndProfiles(JSONObject queryParamJSON) throws Exception;

	/**
	 * 创建用户服务
	 * 
	 * @author ZhangJun
	 * @createTime 2018/8/7
	 * @description 创建用户，提供给其他系统调用，有分布式事务
	 */
	JSONObject createUserService(JSONObject queryParamJSON, Long messageIndexId)
			throws Exception;

	/**
	 * 使用用户失效服务
	 * 
	 * @author ZhangJun
	 * @createTime 2018/9/6
	 * @description 使用用户失效服务，提供给其他系统调用，有分布式事务
	 */
	JSONObject expireUser(JSONObject queryParamJSON, Long messageIndexId);

	/**
	 * tta 配置人员权限
	 * 
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	Pagination<BaseUsersPersonAuthority_HI_RO> findBaseUsersPersonAuthorityPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 用户权限报表-用户与职责角色Profile
	 * 
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public Pagination<BaseUserRoleProfile_HI_RO> findUserRoleProfile(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 保存人员权限
	 * 
	 * @param queryParamJSON
	 * @param userId
	 * @throws Exception
	 */
	void saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
			throws Exception;

	/**
	 * 忘记密码，重新生成密码
	 * 
	 * @param queryParamJSON
	 *            参数 {<br>
	 *            userName:用户名<br>
	 *            }
	 * @author Liujj
	 * @createTime 2019/11/26 10:30
	 * @description 忘记密码，重新生成密码
	 */
	public String createPassword(JSONObject queryParamJSON);

	public String newPassword(JSONObject queryParamJSON);

	public Pagination<BaseUserRoleMenu_HI_RO> findUserRoleMenu(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	Pagination<BaseUsersPerson_HI_RO> findBaseUsersJoinPersonPagination2(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	void changeQuerySql2(JSONObject queryParamJSON,
			Map<String, Object> paramsMap, StringBuffer sql, boolean isHql);

	public Pagination<BaseUsersEntity_HI_RO> findBaseUsersPage(
			JSONObject param, Integer pageIndex, Integer pageRows);

    BaseUsersEntity_HI findById(Integer baseUserId);
}
