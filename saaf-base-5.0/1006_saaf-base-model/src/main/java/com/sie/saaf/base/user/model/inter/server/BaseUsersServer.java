package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.entities.TtaSupplierEntity_RO;
import com.sie.saaf.base.commmon.model.inter.IBaseRequestLog;
import com.sie.saaf.base.mailUtil.EmailUtil;
import com.sie.saaf.base.shiro.model.dao.BaseProfileDAO_HI;
import com.sie.saaf.base.shiro.model.dao.readonly.BaseProfileValueDAO_HI_RO;
import com.sie.saaf.base.shiro.model.entities.BaseProfileValueEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseUserResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseProfileValue_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserResponsibility_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseProfileValue;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.*;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.cache.server.IRedisCacheData;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.Chinese2PinyinUtil;
import com.sie.saaf.common.util.HttpUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.transaction.annotation.TransMessageConsumer;
import com.sie.saaf.transaction.annotation.TransMsgParam;
import com.yhg.base.utils.DigestUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sun.misc.BASE64Decoder;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 对用户表Base_Users进行CRUD操作<br>
 *
 * @author ZhangJun
 * @creteTime 2017-12-11
 */
@Component("baseUsersServer")
public class BaseUsersServer extends BaseCommonServer<BaseUsersEntity_HI>
		implements IBaseUsers {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseUsersServer.class);
	@Autowired
	private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;
	@Autowired
	private BaseViewObject<BaseUsersEntity_HI_RO> baseUsersDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseUsersPerson_HI_RO> baseUsersPersonDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseUserResponsibility_HI_RO> baseUserResponsibilityDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseUserRoleProfile_HI_RO> baseUserRoleProfile_HI_RO;
	@Autowired
	private BaseViewObject<BaseUserRoleMenu_HI_RO> baseUserRoleMenu_HI_RO;
	@Autowired
	private BaseViewObject<BaseUsersOrganization_HI_RO> baseUsersOrginationDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseWechatUsers_HI_RO> baseWechatUsersDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseUsersPersonAuthority_HI_RO> baseUsersPersonAuthorityDAO_HI_RO;
	@Autowired
	private BaseViewObject<BasePDAUserMenuEntity_HI_RO> baseUserMenuDAO_HI_RO;
	@Autowired
	private IBaseProfileValue baseProfileValueServer;
	@Autowired
	private BaseProfileValueDAO_HI_RO baseProfileValueDAO_HI_RO;
	@Autowired
	private IRedisCacheData redisCacheDataServer;
	@Autowired
	private ViewObject<BaseUserResponsibilityEntity_HI> baseUserResponsibilityDAO_HI;
    @Autowired
    protected HibernateTemplate hibernateTemplete;

	@Autowired
	private IBaseRequestLog baseRequestLog;

	private static String bpmUrl;

    @Value("${api.bpm.rms:}")
    private String url;
	@Autowired
	private BaseProfileDAO_HI baseProfileDAO_HI;
	@Autowired
	private BaseViewObject<TtaSupplierEntity_RO> ttaSupplierDAO_HI_RO;

	 @PostConstruct
	  public void init() {
		 BaseUsersServer.bpmUrl = this.url;
	  }

	// 密钥 (需要前端和后端保持一致)
	private static final String KEY = "abcdefgabcdefg12";
	// 算法
	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	public static final String PROFILE_KEY_TABLE_NAME = "base_users";

	public BaseUsersServer() {
		super();
	}

	/**
	 * 保存用户
	 *
	 * @param queryParamJSON
	 *            保存参数<br>
	 *            {<br>
	 *            userId:用户Id,（更新数据时必填）<br>
	 *            deleteFlag: 删除标记（0：未删除；1：已删除）,<br>
	 *            password: 用户密码（base64加密）,<br>
	 *            internalUser: 是否是EBS用户，如果是，需要将用户、密码回写EBS系统,<br>
	 *            isadmin: 是否是系统管理员,<br>
	 *            orderNo: 排序号,<br>
	 *            personId: 对应经销商、门店、员工的外围系统ID,<br>
	 *            phoneNumber: 手机号码,<br>
	 *            sourceId: 关联人员ID、关联经销商ID、关联门店编码,<br>
	 *            startDate: 生效日期,<br>
	 *            endDate: 失效日期,<br>
	 *            userDesc: 用户描述,<br>
	 *            userFullName: 姓名,<br>
	 *            userName: 用户名/登录帐号,<br>
	 *            userType: 用户类型：IN:内部员工，OUT：经销商、门店、导购,<br>
	 *            versionNum: 版本号,（更新数据时必填）<br>
	 *            operatorUserId:操作者<br>
	 *            }
	 * @return BaseUsersEntity_HI对象
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	@Override
	public BaseUsersEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	@Override
	public List<BaseUsersEntity_HI> findList(JSONObject queryParamJSON) {
		StringBuffer sb = new StringBuffer(
				" from BaseUsersEntity_HI where 1=1 ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();

		SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
				sb, queryParamMap);
		changeQuerySort(queryParamJSON, sb, "", true);

		List<BaseUsersEntity_HI> findList = baseUsersDAO_HI.findList(sb,
				queryParamMap);
		return findList;
	}

	/**
	 * 设置默认值
	 *
	 * @param entity
	 */
	@Override
	protected void setEntityDefaultValue(BaseUsersEntity_HI entity) {
		entity.setUserName(entity.getUserName().toUpperCase());
		if (entity.getStartDate() == null) {
			entity.setStartDate(new Date());
		}
		if (entity.getDeleteFlag() == null) {
			entity.setDeleteFlag(CommonConstants.DELETE_FALSE);
		}
		if (StringUtils.isEmpty(entity.getNamePingyin())
				|| StringUtils.isEmpty(entity.getNameSimplePinyin())) {
			entity.setNamePingyin(Chinese2PinyinUtil
					.convertToPinyinSpell(entity.getUserFullName()));
			entity.setNameSimplePinyin(Chinese2PinyinUtil
					.convertToFirstSpell(entity.getUserFullName()));
		}
		if (entity.getSourceId() == null) {
			entity.setSourceId(CommonConstants.ROOT_PARENT_ID + "");
		}
		if (!StringUtils.isEmpty(entity.getPassword())) {
			// 密码，通过MD5加密后保存

			String password = entity.getPassword();
			password = new String(Base64.decodeBase64(password.getBytes()));
			String encryptedPassword = DigestUtils.md5(password);
			entity.setEncryptedPassword(encryptedPassword);
			// if(entity.getUserId() != null) {
			// 如果是修改用户密码，设置最后一次密码修改时间
			// 创建用户的时候不设置，当作初始化密码，要求用户修改密码
			entity.setPwdUpdateDate(new Date());
			// }
		}
	}

	/**
	 * 分页查询用户
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
	 *            }
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 分页列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
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
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	@Override
	public Pagination<BaseUsersEntity_HI> findPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON, pageIndex, pageRows);
	}

	/**
	 * 根据用户名/登录帐号查询用户
	 *
	 * @param userName
	 *            用户登录帐号/用户名
	 * @return BaseUsersEntity_HI
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@Override
	public BaseUsersEntity_HI findByUserName(String userName) {
		List<BaseUsersEntity_HI> findList = baseUsersDAO_HI.findByProperty(
				"userName", userName.toUpperCase());
		if (findList != null && !findList.isEmpty()) {
			return findList.get(0);
		}
		return null;
	}

	/**
	 * 登录时查询校验用户名密码
	 *
	 * @param queryParamJSON
	 * @return
	 */
	public List<BaseUsersEntity_HI> findUserEntities(JSONObject queryParamJSON) {

		StringBuffer sqlSB = new StringBuffer(
				" from BaseUsersEntity_HI where ( startDate is null or TO_DAYS(startDate) <= TO_DAYS(CURRENT_DATE()) ) and   (endDate is null or endDate>=CURRENT_DATE()) and deleteFlag='0' ");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
				"userId", "userId", sqlSB, paramsMap, "=");
		SaafToolUtils
				.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
						"encryptedPassword", "encryptedPassword", sqlSB,
						paramsMap, "=");
		SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
				"deleteFlag", "deleteFlag", sqlSB, paramsMap, "=");
		SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
				"startDate", "startDate", sqlSB, paramsMap, "<");
		SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
				"endDate", "endDate", sqlSB, paramsMap, ">");
		SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
				"userName", "userName", sqlSB, paramsMap, "=");
		SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class, queryParamJSON,
				"phoneNumber", "phoneNumber", sqlSB, paramsMap, "=");
		List<BaseUsersEntity_HI> baseUsersEntitys = baseUsersDAO_HI.findList(
				sqlSB.toString(), paramsMap);
		return baseUsersEntitys;
	}

	/**
	 * 设置查询条件
	 *
	 * @param queryParamJSON
	 *            入参
	 * @param paramsMap
	 *            sql或hql参数
	 * @param sql
	 *            sql或hql
	 * @param isHql
	 *            是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
	 */
	@Override
	protected void changeQuerySql(JSONObject queryParamJSON,
			Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_id",
				"userId", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.phone_number",
				"phoneNumber", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.name_pingyin",
				"namePingyin", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON,
				"baseUsers.name_simple_pinyin", "nameSimplePinyin", sql,
				paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.person_id",
				"personId", sql, paramsMap, "in", isHql);
		// SaafToolUtils.parperParam(queryParamJSON, "baseUsers.source_id",
		// "sourceId",
		// sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.isadmin",
				"isadmin", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_name",
				"userName", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_type",
				"userType", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_full_name",
				"userFullName", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.internal_user",
				"internalUser", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.person_name",
				"personName", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.dept_type",
				"deptType", sql, paramsMap, "like", isHql);
		boolean isValid = false;
		if (queryParamJSON.containsKey("isValid")) {
			isValid = queryParamJSON.getBooleanValue("isValid");
		}

		if (isValid) {
			// 查询有效的
			if (isHql) {
				sql.append(" and baseUsers.startDate<=:startDate and (baseUsers.endDate is null or baseUsers.endDate>=:endDate) and baseUsers.deleteFlag=:deleteFalse");
			} else {
				sql.append(" and baseUsers.start_date<=:startDate and (baseUsers.end_date is null or baseUsers.end_date>=:endDate) and baseUsers.delete_flag=:deleteFalse");
			}
			paramsMap.put("startDate", new Date());
			paramsMap.put("endDate", new Date());
			paramsMap.put("deleteFalse", CommonConstants.DELETE_FALSE);
		} else {
			SaafToolUtils.parperParam(queryParamJSON, "baseUsers.delete_flag",
					"deleteFlag", sql, paramsMap, "=", isHql);
			SaafToolUtils.parperParam(queryParamJSON, "baseUsers.start_date",
					"startDate", sql, paramsMap, ">=", isHql);
			SaafToolUtils.parperParam(queryParamJSON, "baseUsers.end_date",
					"endDate", sql, paramsMap, "<=", isHql);
		}
	}

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
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	@Override
	public List<BaseUsersPerson_HI_RO> findChildrenUsers(
			JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();

		sql.append(BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("varSystemCode",
				queryParamJSON.getString("varSystemCode"));
		changeQuerySql(queryParamJSON, paramsMap, sql, false);
		sql.append(" and baseUsers.source_id=:userId");
		paramsMap.put("userId", queryParamJSON.getIntValue("userId"));
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "order_no,user_id",
				false);

		List<BaseUsersPerson_HI_RO> findList = baseUsersPersonDAO_HI_RO
				.findList(sql, paramsMap);

		return findList;
	}

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
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	@Override
	public Pagination<BaseUsersPerson_HI_RO> findBaseUsersJoinPersonPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();

		sql.append(BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		// 20180808 like->fulllike
		if (queryParamJSON.containsKey("userName")) {
			SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class,
					queryParamJSON, "baseUsers.user_name", "userName", sql,
					paramsMap, "fulllike");
			queryParamJSON.remove("userName");
		}
		// 英文名模糊查询
		if (StringUtils.isNotBlank(queryParamJSON.getString("personNameEn"))) {
			String personNameEnUpper = queryParamJSON.getString("personNameEn")
					.toUpperCase();
			sql.append(" and upper(basePerson.person_name_en) like '%"
					+ personNameEnUpper + "%'");
		}

		if (queryParamJSON.containsKey("isInternal")) {
			sql.append(" and baseUsers.user_type <> '60'");
		}

		SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class,
				queryParamJSON, "baseUsers.group_code", "groupCode", sql,
				paramsMap, "fulllike");
		SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class,
				queryParamJSON, "baseUsers.group_name", "groupName", sql,
				paramsMap, "fulllike");
		changeQuerySql(queryParamJSON, paramsMap, sql, false);
		if (queryParamJSON.containsKey("isValid")
				&& !queryParamJSON.getBooleanValue("isValid")) {
			paramsMap.put("startDate", new Date());
			paramsMap.put("endDate", new Date());
			sql.append(" and (baseUsers.start_date>:startDate or baseUsers.end_date<:endDate)");
		}
		// 通过orgId过滤用户信息
		if (StringUtils.isNotBlank(queryParamJSON.getString("orgId"))) {
			sql.append("AND EXISTS (SELECT 1\n"
					+ "              FROM base_person_assign personAssign \n"
					+ "             WHERE 1 = 1\n"
					+ "               AND personAssign.person_id = baseUsers.person_id\n"
					+ "               AND personAssign.delete_flag = 0\n"
					+ "               AND personAssign.ou_id = :orgId)");
			paramsMap.put("orgId", queryParamJSON.getInteger("orgId"));
		}
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "order_no,user_id",
				false);
		StringBuffer simpleSqlCountString = SaafToolUtils
				.getSqlCountString(sql);

		Pagination<BaseUsersPerson_HI_RO> findList = baseUsersPersonDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);

		return findList;
	}

	/**
	 * 根据职责Id查询职责所分配的用户
	 *
	 * @param responsibilityId
	 *            职责Id
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
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public List<BaseUserResponsibility_HI_RO> findBaserUsersByRespId(
			Integer responsibilityId) {
		StringBuffer sb = new StringBuffer();
		sb.append(BaseUserResponsibility_HI_RO.QUERY_USER_BY_RESPONSIBILITYID_SQL);
		JSONObject queryParamJSON = new JSONObject();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		changeQuerySql(queryParamJSON, paramsMap, sb, false);
		// 查询有效职责
		sb.append(" and baseUserResponsibility.start_date_active<=:startDate and (baseUserResponsibility.end_date_active is null or baseUserResponsibility.end_date_active>=:endDate) ");
		sb.append(" and baseResponsibility.responsibility_id=:responsibilityId");
		paramsMap.put("responsibilityId", responsibilityId);
		paramsMap.put("startDate", new Date());
		paramsMap.put("endDate", new Date());

		List<BaseUserResponsibility_HI_RO> findList = baseUserResponsibilityDAO_HI_RO
				.findList(sb, paramsMap);
		return findList;
	}

	@Override
	public Pagination<BaseUsersOrganization_HI_RO> findBaseUsersByOrgId(
			Integer orgId, JSONObject queryParamJSON, Integer pageIndex,
			Integer pageRows) {
		StringBuffer sb = new StringBuffer();
		sb.append(BaseUsersOrganization_HI_RO.QUERY_USER_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orgId", orgId);
		changeQuerySql(queryParamJSON, paramsMap, sb, false);
		// SaafToolUtils.parperParam(queryParamJSON,"","",sb,paramsMap,"=",false);
		Pagination<BaseUsersOrganization_HI_RO> findList = baseUsersOrginationDAO_HI_RO
				.findPagination(sb, SaafToolUtils.getSqlCountString(sb),
						paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public Pagination<BaseUserRoleProfile_HI_RO> findUserRoleProfile(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(BaseUserRoleProfile_HI_RO.SQL_USER_ROLE_PROFILE);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		// 用户名称
		SaafToolUtils.parperParam(queryParamJSON, "T1.userName", "userName", sql, paramsMap, "like");
		// 用户类型
		SaafToolUtils.parperParam(queryParamJSON, "T1.userType", "userType", sql, paramsMap, "=");
		// 员工姓名
		SaafToolUtils.parperParam(queryParamJSON, "T1.userFullName", "userFullName", sql, paramsMap, "like");
		// 手机号码
		SaafToolUtils.parperParam(queryParamJSON, "T1.phoneNumber", "phoneNumber", sql, paramsMap, "like");
		// email
		SaafToolUtils.parperParam(queryParamJSON, "T1.email", "email", sql, paramsMap, "like");
		// 职责名称
		SaafToolUtils.parperParam(queryParamJSON, "T1.responsibilityName", "responsibilityName", sql, paramsMap, "like");
		// 角色名称
		SaafToolUtils.parperParam(queryParamJSON, "T1.roleName", "roleName", sql, paramsMap, "like");
		// 部门
		SaafToolUtils.parperParam(queryParamJSON, "T1.dept", "dept", sql, paramsMap, "like");
		// profile
    	SaafToolUtils.parperParam(queryParamJSON, "T1.profile", "profile",sql, paramsMap, "like");
		//系统类型
		SaafToolUtils.parperParam(queryParamJSON, "T1.system_code", "systemCode",sql, paramsMap, "like");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "T1.userId desc", false);
		Pagination<BaseUserRoleProfile_HI_RO> findList = baseUserRoleProfile_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public Pagination<BaseUserRoleMenu_HI_RO> findUserRoleMenu(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(BaseUserRoleMenu_HI_RO.SQL_USER_ROLE_MENU);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "T4.menuName1", "menuName1", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "T4.menuName2", "menuName2", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "T4.menuName3", "menuName3", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "T1.responsibility_name", "responsibilityName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "T3.role_name", "roleName", sql, paramsMap, "like");
		//SaafToolUtils.changeQuerySort(queryParamJSON, sql, "T3.role_name,T4.menuName1,T4.menuName2", false);
    SaafToolUtils.changeQuerySort(queryParamJSON, sql, "T1.RESPONSIBILITY_ID,T4.MENUID1,T4.MENUID2,T4.MENUID3", false);
		Pagination<BaseUserRoleMenu_HI_RO> findList = baseUserRoleMenu_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	/**
	 * 查询用户session 相关信息
	 *
	 * @param paramJson
	 *            { userId:用户id }
	 * @return
	 */
	@Override
	public BaseUsersPerson_HI_RO findUserSessionInfo(JSONObject paramJson) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer(
				BaseUsersPerson_HI_RO.SQL_USER_SESSION);
		SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class, paramJson,
				sql, map);
		List<BaseUsersPerson_HI_RO> list = baseUsersPersonDAO_HI_RO.findList(
				sql, map);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	/**
	 * 通过员工编号查询用户信息
	 *
	 * @return
	 */
	@Override
	public BaseUsersPerson_HI_RO findUserInfoByEmployeeNumber(
			String employeeNumber) {
		StringBuffer sb = new StringBuffer(
				BaseUsersPerson_HI_RO.SQL_USER_EMPLOYEE);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("employeeNumber", employeeNumber);
		List<BaseUsersPerson_HI_RO> findList = baseUsersPersonDAO_HI_RO
				.findList(sb, paramsMap);
		if (findList != null && !findList.isEmpty()) {
			return findList.get(0);
		}
		return null;
	}

	/**
	 * 根据微信公众号查询用户
	 *
	 * @param wxOpenid
	 *            微信公众号Id
	 * @return BaseUsersEntity_HI
	 * @author ZhangJun
	 * @createTime 2017/12/27 15:05
	 * @description 根据微信公众号查询用户
	 */
	@Override
	public BaseWechatUsers_HI_RO findUsersByWxOpenId(String wxOpenid) {
		StringBuffer sql = new StringBuffer(BaseWechatUsers_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		sql.append(" and baseUsers.start_date<=:startDate and (baseUsers.end_date is null or baseUsers.end_date>=:endDate) and baseUsers.delete_flag=:deleteFalse");

		paramsMap.put("wxOpenId", wxOpenid);
		paramsMap.put("startDate", new Date());
		paramsMap.put("endDate", new Date());
		paramsMap.put("deleteFalse", CommonConstants.DELETE_FALSE);

		BaseWechatUsers_HI_RO entity = null;
		List<BaseWechatUsers_HI_RO> list = baseWechatUsersDAO_HI_RO.findList(
				sql, paramsMap);
		if (list != null && !list.isEmpty()) {
			entity = list.get(0);
		}

		return entity;
	}

	/**
	 * 修改用户密码
	 *
	 * @param queryParamJSON
	 *            参数 {<br>
	 *            userId:用户Id<br>
	 *            userName:用户名<br>
	 *            oldPassword:旧密码<br>
	 *            newPassword:新密码<br>
	 *            }
	 * @author ZhangJun
	 * @createTime 2018/1/6 17:30
	 * @description 修改用户密码
	 */
	@Override
	public void updateUserPassword(JSONObject queryParamJSON) {
		try {
			String oldPass = aesDecrypt(
					queryParamJSON.getString("oldPassword"), KEY);
			String newPass = aesDecrypt(
					queryParamJSON.getString("newPassword"), KEY);
			queryParamJSON.put("oldPassword", oldPass);
			queryParamJSON.put("newPassword", newPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Integer userId = null;
		String userName = null;

		if (!queryParamJSON.containsKey("userId")
				|| !queryParamJSON.containsKey("userName")
				|| (StringUtils.isEmpty(queryParamJSON.getString("userName")) && StringUtils
						.isEmpty(queryParamJSON.getString("userId")))) {
			throw new IllegalArgumentException("必须输入用户Id或用户名");
		}


    if(!"Y".equals(queryParamJSON.getString("varIsadmin"))){
      LOGGER.info("userId:" + queryParamJSON.getString("userId"));
      LOGGER.info("varUserId:" + queryParamJSON.getString("varUserId"));
      if (!queryParamJSON.getString("userId").equals(queryParamJSON.getString("varUserId"))){
        throw new IllegalArgumentException("只可以修改自己的账号");
      }
    }


		if (!queryParamJSON.containsKey("oldPassword")) {
			throw new IllegalArgumentException("必须输入旧密码");
		}
		if (!queryParamJSON.containsKey("newPassword")) {
			throw new IllegalArgumentException("必须输入新密码");
		}

		String oldPassword = queryParamJSON.getString("oldPassword");
		if (oldPassword.equals(queryParamJSON.getString("newPassword")))
			throw new IllegalArgumentException("新密码不能与旧密码一样");

		if (!"".equals(queryParamJSON.getString("vmiType"))
				&& queryParamJSON.getString("vmiType") != null) {
			oldPassword = new String(Base64.decodeBase64(new String(
					Base64.decodeBase64(new String(Base64
							.decodeBase64(oldPassword.substring(0,
									oldPassword.length() - 5).getBytes()))
							.getBytes())).getBytes()));

		} else {
			// oldPassword = new String(
			// Base64.decodeBase64(oldPassword.getBytes()));
		}

		String oldPasswordMd5 = DigestUtils.md5(oldPassword);

		BaseUsersEntity_HI entity = null;
		if (queryParamJSON.containsKey("userId")
				&& StringUtils.isNotEmpty(queryParamJSON.getString("userId"))) {
			userId = queryParamJSON.getInteger("userId");
			entity = getById(userId);
		} else if (queryParamJSON.containsKey("userName")
				&& StringUtils.isNotEmpty(queryParamJSON.getString("userName"))) {
			userName = queryParamJSON.getString("userName");
			entity = findByUserName(userName);
		}

		if (entity != null) {
			if (entity.getEncryptedPassword().equals(oldPasswordMd5)) {
				String newPassword = queryParamJSON.getString("newPassword");

				/*
				 * oldPassword = new String(Base64.decodeBase64(newPassword
				 * .getBytes()));
				 */
				if (!"".equals(queryParamJSON.getString("vmiType"))
						&& queryParamJSON.getString("vmiType") != null) {
					newPassword = new String(Base64.decodeBase64(new String(
							Base64.decodeBase64(new String(Base64
									.decodeBase64(newPassword.substring(0,
											newPassword.length() - 5)
											.getBytes())).getBytes()))
							.getBytes()));

				} else {
					// newPassword = new String(Base64.decodeBase64(newPassword
					// .getBytes()));
				}

				entity.setEncryptedPassword(DigestUtils.md5(newPassword));
				entity.setPwdUpdateDate(new Date());
				baseUsersDAO_HI.update(entity);
			} else {
				throw new RuntimeException("旧密码输入错误");
			}
		} else {
			throw new ObjectNotFoundException("userId:" + userId + ",userName:"
					+ userName, "找不到对应的用户");
		}
	}

	public String aesDecrypt(String encryptStr, String decryptKey)
			throws Exception {
		return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	public byte[] base64Decode(String base64Code) throws Exception {
		return new BASE64Decoder().decodeBuffer(base64Code);
	}

	public String aesDecryptByBytes(byte[] encryptBytes, String decryptKey)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.DECRYPT_MODE,
				new SecretKeySpec(decryptKey.getBytes(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes);
	}

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
	@Override
	public String createPassword(JSONObject queryParamJSON) {

		String userName = null;
		if (!queryParamJSON.containsKey("userName")
				|| (StringUtils.isEmpty(queryParamJSON.getString("userName")))) {
			throw new IllegalArgumentException("必须输入用户名");
		}
		String newPasswordStr = getRandomPwd(8);

		String newPassword = null;

		BaseUsersEntity_HI entity = null;
		if (queryParamJSON.containsKey("userName")
				&& StringUtils.isNotEmpty(queryParamJSON.getString("userName"))) {
			userName = queryParamJSON.getString("userName");
			entity = findByUserName(userName);
		}

		if (StringUtils.isEmpty(entity.getEmailAddress())) {
			throw new IllegalArgumentException("邮箱地址为空");
		}

		if (entity != null) {
			entity.setEncryptedPassword(DigestUtils.md5(newPasswordStr));
			entity.setPwdUpdateDate(new Date());
			baseUsersDAO_HI.update(entity);
      JSONObject defaultRespCodeJSON = redisCacheDataServer.findLookupValueMeaning("SUPPLIER_PORTAL_INFO", "PUBLIC");

//			EmailUtil.sendMailFromWatsons(entity.getEmailAddress(), "重置",
//					"已为你重置Watsons系统密码" + newPasswordStr + "，建议你尽快上线修改密码。");
      EmailUtil.sendMailFromWatsons(entity.getEmailAddress(), "屈臣氏供应商门户系统账号密码重置（"+defaultRespCodeJSON.getString("ENV_NAME")+"）",
        "<html> \n" +
          "Dear \n" +
          "<br/>\n" +
          "&nbsp;&nbsp;&nbsp;已经为您重置 屈臣氏供应商门户系统 账号密码;\n" +
          "<br/>\n" +
          "&nbsp;&nbsp;&nbsp;"+defaultRespCodeJSON.getString("ENV_NAME")+"网址为：\n" +
          "<a href=\""+defaultRespCodeJSON.getString("ENV_URL")+"\" target=\"_blank\">屈臣氏供应商门户</a>\n" +
          "<br/>\n" +
          "&nbsp;&nbsp;&nbsp;账户名为"+queryParamJSON.getString("userName")+",重置密码为"+newPasswordStr+", 请尽快登录进行初始化修改。\n" +
          "</html>");
		} else {
			throw new ObjectNotFoundException("userName:" + userName,
					"找不到对应的用户");
		}
		return newPassword;
	}

	/**
	 * 忘记密码，重新生成密码
	 *
	 * @param queryParamJSON
	 *            参数 {<br>
	 *            userName:用户名<br>
	 *            }
	 * @author Liujj
	 * @createTime 2019/11/26 10:30
	 * @description 重新生成密码字符串，不保存，直接返回
	 */
	@Override
	public String newPassword(JSONObject queryParamJSON) {

		String newPasswordStr = getRandomPwd(8);

		if (StringUtils.isEmpty(queryParamJSON.getString("emailAddress"))) {
			throw new IllegalArgumentException("邮箱地址为空");
		}
		String passwordMD5 = DigestUtils.md5(newPasswordStr);
    JSONObject defaultRespCodeJSON = redisCacheDataServer.findLookupValueMeaning("SUPPLIER_PORTAL_INFO", "PUBLIC");
//		EmailUtil.sendMailFromWatsons(queryParamJSON.getString("emailAddress"),
//				"屈臣氏供应商门户系统账号开通（"+defaultRespCodeJSON.getString("ENV_NAME")+"）",
//				"已经为您开通 屈臣氏供应商门户系统 权限，测试网址为："+defaultRespCodeJSON.getString("ENV_URL")+"，账户名:" + queryParamJSON.getString("userName")
//						+ ",初始密码:" + newPasswordStr + "，请尽快登陆进行初始化修改。。");
    EmailUtil.sendMailFromWatsons(queryParamJSON.getString("emailAddress"),
      "屈臣氏供应商门户系统账号开通（"+defaultRespCodeJSON.getString("ENV_NAME")+"）",
      "<html> \n" +
        "Dear \n" +
        "<br/>\n" +
        "&nbsp;&nbsp;&nbsp;已经为您开通 屈臣氏供应商门户系统 权限;\n" +
        "<br/>\n" +
        "&nbsp;&nbsp;&nbsp;"+defaultRespCodeJSON.getString("ENV_NAME")+"网址为：\n" +
        "<a href=\""+defaultRespCodeJSON.getString("ENV_URL")+"\" target=\"_blank\">屈臣氏供应商门户</a>\n" +
        "<br/>\n" +
        "&nbsp;&nbsp;&nbsp;账户名"+queryParamJSON.getString("userName")+",初始密码为"+newPasswordStr+", 请尽快登陆进行初始化修改。\n" +
        "</html>\n" +
        "   ");

		return passwordMD5;
	}

	public static String getRandomPwd(int len) {
		String result = null;
		while (len == 8) {
			result = makeRandomPwd(len);
			if (result.matches(".*[a-z]{1,}.*")
					&& result.matches(".*[A-Z]{1,}.*")
					&& result.matches(".*\\d{1,}.*")
					&& result.matches(".*[~;<@#:>%^]{1,}.*")) {
				return result;
			}
			result = makeRandomPwd(len);
		}
		return result;
	}

	public static String makeRandomPwd(int len) {
		char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*?"
				.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int x = 0; x < len; ++x) {
			sb.append(charr[r.nextInt(charr.length)]);
		}
		return sb.toString();
	}

	/**
	 * 手机验证码找回密码
	 *
	 * @param queryParamJSON
	 *            { mobilePhone:手机号 code: 验证码 pwd:新密码 base64编码 }
	 */
	@Override
	public BaseUsersEntity_HI updatePasswordReminder(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON, "code", "pwd",
				"phoneNumber");
		String phoneNumber = queryParamJSON.getString("phoneNumber");
		StringBuffer sb = new StringBuffer(
				BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);
		sb.append(" and baseUsers.phone_number = :phoneNumber");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("phoneNumber", phoneNumber);
		List<BaseUsersPerson_HI_RO> list = baseUsersPersonDAO_HI_RO.findList(
				sb, paramsMap);
		Assert.notEmpty(list, "号码#未绑定帐号,或帐号已失效".replace("#", phoneNumber));
		if (list.size() > 1)
			throw new IllegalArgumentException("当前手机号绑定了多个用户，若要修改密码，请联系管理员");
		BaseUsersEntity_HI user = baseUsersDAO_HI.getById(list.get(0)
				.getUserId());
		String password = new String(Base64.decodeBase64(queryParamJSON
				.getString("pwd").getBytes()));
		String encryptedPassword = DigestUtils.md5(password);
		user.setEncryptedPassword(encryptedPassword);
		user.setPwdUpdateDate(new Date());
		baseUsersDAO_HI.update(user);
		return user;
	}

	/**
	 * 根据手机号查询用户
	 *
	 * @param phoneNumber
	 *            用户手机号
	 * @return BaseUsersEntity_HI
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@Override
	public BaseUsersPerson_HI_RO findByPhoneNumber(String phoneNumber) {
		StringBuffer sb = new StringBuffer(
				BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);
		sb.append(" and baseUsers.phone_number = :phoneNumber ");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("phoneNumber", phoneNumber);
		List<BaseUsersPerson_HI_RO> findList = baseUsersPersonDAO_HI_RO
				.findList(sb, paramsMap);
		if (findList != null && !findList.isEmpty()) {
			return findList.get(0);
		}
		return null;
	}

	@Override
	public BaseUsersPerson_HI_RO findBaseUsersById(JSONObject queryParamJSON) {
		Integer userId = queryParamJSON.getInteger("id");
		Assert.notNull(userId, "参数id不能为空");
		StringBuffer sb = new StringBuffer(
				BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);
		sb.append(" and baseUsers.user_id = :userId");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("userId", userId);
		List<BaseUsersPerson_HI_RO> list = baseUsersPersonDAO_HI_RO.findList(
				sb, paramsMap);
		if (list != null && !list.isEmpty()) {
			BaseUsersPerson_HI_RO entity = list.get(0);

			StringBuffer sb2 = new StringBuffer(
					BaseProfileValue_HI_RO.QUERY_PROFILE_VALUE_QUERY);
			sb2.append(" and baseProfileValue.business_key = :businessKey and baseProfileValue.key_table_name = :keyTableName and baseProfileValue.delete_flag=:deleteFlag");
			JSONObject queryJSON2 = new JSONObject();
			queryJSON2.put("keyTableName", PROFILE_KEY_TABLE_NAME);
			queryJSON2.put("businessKey", String.valueOf(entity.getUserId()));
			queryJSON2.put("deleteFlag", CommonConstants.DELETE_FALSE);

			List<BaseProfileValue_HI_RO> profileValues = this.baseProfileValueDAO_HI_RO
					.findList(sb2, queryJSON2);
			entity.setProfileValues(profileValues);
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询用户子库、组织 用户菜单
	 *
	 * @param loginName
	 * @return
	 * @author xiangyipo
	 * @date 2018/2/5
	 */

	@Override
	public String findBaseUserMenu(String loginName, boolean filter) {
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("userName", loginName);

		StringBuffer userInfoSQL = new StringBuffer(
				BasePDAUserMenuEntity_HI_RO.QUERY_USER_INFO_SQL);
		userInfoSQL.append("AND bu.user_name = :userName");
		List<BasePDAUserMenuEntity_HI_RO> baseUserEntity = baseUserMenuDAO_HI_RO
				.findList(userInfoSQL, paramsMap);
		Assert.notEmpty(baseUserEntity, "[#]没有这个用户名".replace("#", loginName));
		Integer userId = baseUserEntity.get(0).getUserId();
		paramsMap.put("userId", userId);

		paramsMap.remove("userName");
		StringBuffer userWarehouseOrgSQL = new StringBuffer(
				BasePDAUserMenuEntity_HI_RO.QUERY_USER_WAREHOUSE_ORGANIZATION_SQL);
		userWarehouseOrgSQL.append("AND bu.user_id = :userId");
		List<BasePDAUserMenuEntity_HI_RO> userWarehouseOrgList = baseUserMenuDAO_HI_RO
				.findList(userWarehouseOrgSQL, paramsMap);
		Assert.notEmpty(userWarehouseOrgList,
				"[#]你没有子库、组织".replace("#", baseUserEntity.get(0).getUserName()));

		for (BasePDAUserMenuEntity_HI_RO userWarehouseOrg : userWarehouseOrgList) {
			paramsMap.remove("userId");
			StringBuffer userMenuSQL = new StringBuffer(
					BasePDAUserMenuEntity_HI_RO.QUERY_USER_MENU_SQL);
			userMenuSQL.append(" AND role.ROLE_ID  = "
					+ userWarehouseOrg.getRoleId());
			userMenuSQL.append(" AND role.ORGANIZATION_ID  = "
					+ userWarehouseOrg.getOrganizationId());
			userMenuSQL.append(" AND role.CHANNEL_CODE  = "
					+ userWarehouseOrg.getChannelCode());

			List<BasePDAUserMenuEntity_HI_RO> userMenuList = baseUserMenuDAO_HI_RO
					.findList(userMenuSQL, paramsMap);
			for (Iterator<BasePDAUserMenuEntity_HI_RO> iterator = userMenuList
					.iterator(); iterator.hasNext();) {
				BasePDAUserMenuEntity_HI_RO item = iterator.next();
				// 低版本过滤部分盘点菜单
				if (filter
						&& (Objects.equals(item.getFunctionId(), "1601") || Objects
								.equals(item.getFunctionId(), "1602"))) {
					iterator.remove();
					continue;
				}
				if (StringUtils.isBlank(item.getParenMenuCode()))
					item.setParenMenuCode("");
			}
			for (int i = 0; i < userMenuList.size(); i++) {
				String parenMenuCode = userMenuList.get(i).getParenMenuCode();
				if (StringUtils.isBlank(parenMenuCode)) {
					userMenuList.get(i).setParenMenuCode("");
				}
			}
			String menuStr = JSON.toJSONString(userMenuList);
			LOGGER.info("menuStr:{}", menuStr);
			userWarehouseOrg.setMenus(JSONArray.parseArray(menuStr));
		}
		baseUserEntity.get(0).setUserOrg(userWarehouseOrgList);

		return JSON.toJSONString(baseUserEntity);
	}

	@Override
	public JSONObject saveUserAndProfiles(JSONObject queryParamJSON)
			throws Exception {

		/*
		 * BaseUsersEntity_HI baseUsersEntity =
		 * SaafToolUtils.setEntity(BaseUsersEntity_HI
		 * .class,queryParamJSON,baseUsersDAO_HI,1);
		 * setEntityDefaultValue(baseUsersEntity);
		 * baseUsersDAO_HI.saveOrUpdate(baseUsersEntity); JSONObject retValue =
		 * JSONObject.parseObject(JSONObject.toJSONString(baseUsersEntity));
		 * List<BaseProfileValueEntity_HI> profileValues =
		 * this.saveBaseProfileValue(queryParamJSON,baseUsersEntity);
		 * retValue.put("profileValues", profileValues); return retValue;
		 */
    Integer userId = queryParamJSON.getInteger("userId");
    String userType = queryParamJSON.getString("userType");
    if (userId == null){
      if (StringUtils.isBlank(queryParamJSON.getString("password")) && !"60".equals(userType) && !"66".equals(userType)){
        throw new IllegalAccessException("请输入密码");
      }
    }else {
       BaseUsersOrganization_HI_RO user = baseUsersOrginationDAO_HI_RO.get("SELECT ENCRYPTED_PASSWORD encryptedPassword FROM BASE_USERS WHERE USER_ID =?",userId);
       if (user == null){
         throw new IllegalAccessException("用户id有误");
       }
       if (StringUtils.isBlank(queryParamJSON.getString("password"))){
         queryParamJSON.put("encryptedPassword",user.getEncryptedPassword());
       }
    }


		BaseUsersEntity_HI baseUsersEntity = super.saveOrUpdate(queryParamJSON);

		//生成BPM id
		updateNamePingyin(queryParamJSON, userType,baseUsersEntity);

		JSONObject retValue = JSONObject.parseObject(JSONObject
				.toJSONString(baseUsersEntity));
		List<BaseProfileValueEntity_HI> profileValues = this
				.saveBaseProfileValue(queryParamJSON, baseUsersEntity);
		retValue.put("profileValues", profileValues);
		return retValue;
	}

	private void updateNamePingyin(JSONObject queryParamJSON, String userType, BaseUsersEntity_HI baseUsersEntity) {
    if (!"60".equals(userType)) {
      return;
    }
    JSONObject json = new JSONObject(true);
    json.put("Method", "SynchEmpOther");

    List<JSONObject> listJson = new ArrayList<>();
    JSONObject jsonMin = new JSONObject(true);
    jsonMin.put("KEY_OTHER", (baseUsersEntity.getUserId() + "-" + baseUsersEntity.getUserName()));
    jsonMin.put("NAME_CN", baseUsersEntity.getUserName());
    listJson.add(jsonMin);
    json.put("Employee_Other", listJson);

    Resource resource = new ClassPathResource("application.yml");
    Properties properties = null;
    YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
    yamlFactory.setResources(resource);
    properties =  yamlFactory.getObject();
    Object rmsUrl = properties.get("api.bpm.rms");
    String resp = HttpUtils.doPost(rmsUrl.toString(),json);
    LOGGER.info(resp);
    JSONArray parseArray = JSONObject.parseArray(resp);
    if (parseArray != null && !parseArray.isEmpty()) {
      String namePingyin = parseArray.getJSONObject(0).getString("EMPLOYEE_NO");
      if(StringUtils.isNotBlank(namePingyin)){
        baseUsersEntity.setNamePingyin(namePingyin);
      }
    }
    return;

	}

	private String getNamePingyin(String supplierCode,String supplierName, BaseUsersEntity_HI baseUsersEntity) {
		JSONObject json = new JSONObject(true);
		json.put("Method", "GetTaskList");
		List<JSONObject> listJson = new ArrayList<>();
		if(supplierName == null){
//			StringBuffer sb = new StringBuffer(TtaSupplierEntity_RO.TTA_SUPPLIER);
//			sb.append(" and s.supplier_code = :supplierCode");
//			Map<String, Object> map = new HashMap<>();
//			map.put("supplierCode", supplierCode);
//			List<TtaSupplierEntity_RO> supplierList = ttaSupplierDAO_HI_RO.findList(sb, map);
			JSONObject queryParamJSON = new JSONObject();
			queryParamJSON.put("supplierCode", supplierCode);
			Pagination findPlmSupplier = baseRequestLog.findPlmSupplier(queryParamJSON, 1, 5);
			List<HashMap<String,Object>> supplierList = findPlmSupplier.getData();
			if(supplierList != null && !supplierList.isEmpty()){
				supplierName = supplierList.get(0).get("supplierName").toString();
			}
		}
		if(supplierName != null){
			JSONObject jsonMin = new JSONObject(true);
			jsonMin.put("KEY_OTHER",(supplierCode+"-"+baseUsersEntity.getUserId()));
			jsonMin.put("NAME_CN",supplierName);
			listJson.add(jsonMin);
			json.put("Employee_Other", listJson);
			String resp = HttpUtils.doPost(bpmUrl, json);
			LOGGER.info(resp);
		    JSONArray parseArray = JSONObject.parseArray(resp);
		    if(parseArray != null && !parseArray.isEmpty()){
		    	return parseArray.getJSONObject(0).getString("EMPLOYEE_NO");
		    }else{
		    	return null;
		    }
		}
		return null;
	}

	/**
	 * 创建用户的消费者服务
	 *
	 * @author ZhangJun
	 * @createTime 2018/8/7
	 * @description 创建用户的消费者服务，提供给其他系统创建用户，有分布式事务
	 */
	@Override
	@TransMessageConsumer(desc = "saveUserServiceProvider:saveUserServiceConsumer")
	public JSONObject createUserService(JSONObject queryParamJSON,
			@TransMsgParam Long messageIndexId) throws Exception {

		JSONObject retUser = this.saveUserAndProfiles(queryParamJSON);

		if (StringUtils.isNotBlank(queryParamJSON.getString("defaultRespCode"))) {
			// 如果存在默认职责编码，则添加默认职责
			String respCode = queryParamJSON.getString("defaultRespCode");

			// 获取快码DEFAULT_RESPONSIBILITY_CFG中配置的默认职责Id
			JSONObject defaultRespCodeJSON = redisCacheDataServer
					.findLookupValueMeaning("DEFAULT_RESPONSIBILITY_CFG",
							"BASE");
			Integer respId = defaultRespCodeJSON.getInteger(respCode);

			BaseUserResponsibilityEntity_HI userResp = new BaseUserResponsibilityEntity_HI();
			userResp.setOperatorUserId(queryParamJSON
					.getIntValue("operatorUserId"));
			userResp.setResponsibilityId(respId);
			userResp.setUserId(retUser.getInteger("userId"));
			userResp.setStartDateActive(new Date());
			userResp.setEndDateActive(null);
			baseUserResponsibilityDAO_HI.saveOrUpdate(userResp);

		}

		return retUser;
	}

	private List<BaseProfileValueEntity_HI> saveBaseProfileValue(
			JSONObject queryParamJSON, BaseUsersEntity_HI baseUsersEntity) {
		Integer operatorUserId = queryParamJSON.getInteger("operatorUserId");
		String systemCode = queryParamJSON.getString("systemCode");
		List<BaseProfileValueEntity_HI> newProfiles = new ArrayList<>();
		if (queryParamJSON.containsKey("profileValues")) {
			// 传了profiles
			JSONArray profilesArray = queryParamJSON
					.getJSONArray("profileValues");
			if (profilesArray != null && !profilesArray.isEmpty()) {
				List<BaseProfileValueEntity_HI> profileValueList = baseProfileValueServer
						.findList(PROFILE_KEY_TABLE_NAME,
								baseUsersEntity.getUserId());
				Map<Integer, BaseProfileValueEntity_HI> profileValueMap = new HashMap<>();

				if (profileValueList != null && !profileValueList.isEmpty()) {
					for (BaseProfileValueEntity_HI entity : profileValueList) {
						if (entity.getDeleteFlag().intValue() == CommonConstants.DELETE_FALSE) {
							profileValueMap.put(entity.getProfileValueId(),
									entity);
						}
					}
				}
				// 查询end
				for (int i = 0; i < profilesArray.size(); i++) {
					JSONObject profileJSON = profilesArray.getJSONObject(i);
					if (profileJSON.containsKey("profileValueId")) {
						// 存在主键，则这条数据需要更新，从profileValueMap中移除
						profileValueMap.remove(profileJSON
								.getInteger("profileValueId"));
						continue;
					}

					profileJSON.put("businessKey", baseUsersEntity.getUserId());
					profileJSON.put("keyTableName", PROFILE_KEY_TABLE_NAME);
					profileJSON.put("deleteFlag", CommonConstants.DELETE_FALSE);
					profileJSON.put("operatorUserId", operatorUserId);
					profileJSON.put("systemCode", systemCode);
					BaseProfileValueEntity_HI profileValue = this.baseProfileValueServer
							.saveOrUpdate(profileJSON);
					newProfiles.add(profileValue);

				}

				if (!profileValueMap.isEmpty()) {
					// 到此处，profileValueMap中已移除更新的数据，剩下的则是需要删除的，直接设置状态标记deleteFlag
					// = 1
					Collection<BaseProfileValueEntity_HI> deleteValues = profileValueMap
							.values();
					Iterator<BaseProfileValueEntity_HI> it = deleteValues
							.iterator();
					while (it.hasNext()) {
						BaseProfileValueEntity_HI deleteEntity = it.next();
						deleteEntity.setDeleteFlag(CommonConstants.DELETE_TRUE);
						this.baseProfileValueServer.saveOrUpdate(deleteEntity);
					}
				}
			} else {
				saveAllProfiles(baseUsersEntity.getUserId());
			}
		} else {
			saveAllProfiles(baseUsersEntity.getUserId());
		}
		return newProfiles;
	}

	/**
	 * 删除职责对应的所有的profileValue
	 *
	 * @author ZhangJun
	 * @createTime 2018/1/23 10:32
	 * @description 删除职责对应的所有的profileValue
	 */
	private void saveAllProfiles(Integer userId) {
		List<BaseProfileValueEntity_HI> list = baseProfileValueServer.findList(
				PROFILE_KEY_TABLE_NAME, userId);
		if (list != null && !list.isEmpty()) {
			for (BaseProfileValueEntity_HI entity : list) {
				entity.setDeleteFlag(CommonConstants.DELETE_TRUE);
				this.baseProfileValueServer.saveOrUpdate(entity);
			}
		}
	}

	/**
	 * 使用户有效或失效
	 *
	 * @param queryParamJSON
	 *            { userName:用户名称 status:状态，Y：有效，N：失效 }
	 * @param messageIndexId
	 * @author ZhangJun
	 * @createTime 2018/9/6
	 * @description 使用户有效或失效
	 */
	@Override
	@TransMessageConsumer(desc = "expireServiceProvider:expireServiceConsumer")
	public JSONObject expireUser(JSONObject queryParamJSON,
			@TransMsgParam Long messageIndexId) {

		String userName = queryParamJSON.getString("userName");
		Boolean status = queryParamJSON.getBoolean("status");
		BaseUsersEntity_HI user = findByUserName(userName);
		if (user != null) {

			if (status) {
				user.setDeleteFlag(CommonConstants.DELETE_FALSE);
				user.setEndDate(null);
			} else {
				user.setDeleteFlag(CommonConstants.DELETE_TRUE);
				user.setEndDate(new Date());
			}
			this.update(user);
			return JSON.parseObject(JSON.toJSONString(user));
		}

		return null;
	}

	@Override
	public Pagination<BaseUsersPersonAuthority_HI_RO> findBaseUsersPersonAuthorityPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(BaseUsersPersonAuthority_HI_RO.SQL_USER_AUTHORITY);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(BaseUsersPersonAuthority_HI_RO.class,
				queryParamJSON, "bu.user_name", "userName", sql,
				paramsMap, "fulllike");
		SaafToolUtils.parperHbmParam(BaseUsersPersonAuthority_HI_RO.class,
				queryParamJSON, "bu.user_Full_Name", "userFullName", sql,
				paramsMap, "fulllike");
		SaafToolUtils.parperHbmParam(BaseUsersPersonAuthority_HI_RO.class,
				queryParamJSON, "bd.department_name", "departmentName", sql,
				paramsMap, "fulllike");
		SaafToolUtils
				.parperHbmParam(BaseUsersPersonAuthority_HI_RO.class,
						queryParamJSON, "bu.data_type", "dataType", sql,
						paramsMap, "=");
		sql.append(" group by bu.user_id");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "bu.user_id desc",
				false);
		return baseUsersPersonAuthorityDAO_HI_RO.findPagination(sql,
				SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex,
				pageRows);

	}

	@Override
	public void saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
			throws Exception {
		JSONArray jsonA = queryParamJSON.getJSONArray("userList");
		List<BaseUsersEntity_HI> objects = new ArrayList<>();

		StringBuffer sql = new StringBuffer();
		sql.append(BaseUsersPersonAuthority_HI_RO.SQL_USER_DATA_TYPE_CHECK);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(" and tu.user_id = :userId");
		for (int i = 0; i < jsonA.size(); i++) {
			SaafToolUtils
					.validateJsonParms((JSONObject) jsonA.get(i), "userId");
			paramsMap.put("userId",
					((JSONObject) jsonA.get(i)).getInteger("userId"));
			paramsMap.put("dataType",
					((JSONObject) jsonA.get(i)).getInteger("dataType"));
			// 校验权限类型的一致性
			BaseUsersPersonAuthority_HI_RO baseUsersPersonAuthority_hi_ro = baseUsersPersonAuthorityDAO_HI_RO
					.get(sql, paramsMap);
			int counts = baseUsersPersonAuthority_hi_ro.getCounts().intValue();
			if (0 != counts) {
				throw new IllegalArgumentException("左右两边权限类型不一致,用户为【"
						+ ((JSONObject) jsonA.get(i)).getString("userFullName")
						+ "】");
			}
			paramsMap.clear();
			BaseUsersEntity_HI baseUsersEntity = SaafToolUtils.setEntity(
					BaseUsersEntity_HI.class, (JSONObject) jsonA.get(i),
					baseUsersDAO_HI, userId);
			objects.add(baseUsersEntity);
		}
		baseUsersDAO_HI.updateAll(objects);
	}

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
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	@Override
	public Pagination<BaseUsersPerson_HI_RO> findBaseUsersJoinPersonPagination2(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();

		sql.append(BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (queryParamJSON.containsKey("userName")) {
			String uname = queryParamJSON.getString("userName");
			if (uname.equals("")) {
				queryParamJSON.remove("userName");
			} else {
				SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class,
						queryParamJSON, "baseUsers.user_name", "userName", sql,
						paramsMap, "=");
			}
		}
		if (queryParamJSON.containsKey("userFullName")) {
			String fullname = queryParamJSON.getString("userFullName");
			if (fullname.equals("")) {
				queryParamJSON.remove("userFullName");
			} else {
				SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class,
						queryParamJSON, "baseUsers.user_full_name",
						"userFullName", sql, paramsMap, "=");
			}
		}
		// 英文名模糊查询
		if (queryParamJSON.containsKey("personNameEn")) {
			String personNameEn = queryParamJSON.getString("personNameEn");
			if (personNameEn.equals("")) {
				queryParamJSON.remove("personNameEn");
			} else {
				String personNameEnUpper = queryParamJSON.getString(
						"personNameEn").toUpperCase();
				sql.append(" and upper(basePerson.person_name_en)  ='"
						+ personNameEnUpper + "'");
			}
		}

		if (queryParamJSON.containsKey("isInternal")) {
			sql.append(" and baseUsers.user_type <> '60'");
		}

		SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class,
				queryParamJSON, "baseUsers.group_code", "groupCode", sql,
				paramsMap, "fulllike");
		SaafToolUtils.parperHbmParam(BaseUsersPerson_HI_RO.class,
				queryParamJSON, "baseUsers.group_name", "groupName", sql,
				paramsMap, "fulllike");
		changeQuerySql2(queryParamJSON, paramsMap, sql, false);
		if (queryParamJSON.containsKey("isValid")
				&& !queryParamJSON.getBooleanValue("isValid")) {
			paramsMap.put("startDate", new Date());
			paramsMap.put("endDate", new Date());
			sql.append(" and (baseUsers.start_date>:startDate or baseUsers.end_date<:endDate)");
		}
		// 通过orgId过滤用户信息
		if (StringUtils.isNotBlank(queryParamJSON.getString("orgId"))) {
			sql.append("AND EXISTS (SELECT 1\n"
					+ "              FROM base_person_assign personAssign \n"
					+ "             WHERE 1 = 1\n"
					+ "               AND personAssign.person_id = baseUsers.person_id\n"
					+ "               AND personAssign.delete_flag = 0\n"
					+ "               AND personAssign.ou_id = :orgId)");
			paramsMap.put("orgId", queryParamJSON.getInteger("orgId"));
		}
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "order_no,user_id",
				false);

		String sql2 = "";
		if (sql.toString().contains("'yyyy-mm-dd hh24:mi:ss'")) {

			sql2 = sql.toString().replace(" to_char(", " ")
					.replace(",'yyyy-mm-dd hh24:mi:ss')", " ");
		}
		StringBuffer simpleSqlCountString = SaafToolUtils
				.getSqlCountString(sql);
		if (!sql2.equals("")) {
			simpleSqlCountString = SaafToolUtils
					.getSqlCountString(new StringBuffer(sql2));
			sql = new StringBuffer(sql2);
		}

		Pagination<BaseUsersPerson_HI_RO> findList = baseUsersPersonDAO_HI_RO
				.findPagination(sql, simpleSqlCountString, paramsMap,
						pageIndex, pageRows);

		if (!queryParamJSON.containsKey("userName")
				&& !queryParamJSON.containsKey("personNameEn")
				&& !queryParamJSON.containsKey("userFullName")) {
			List<BaseUsersPerson_HI_RO> ro = new ArrayList<BaseUsersPerson_HI_RO>();
			findList.setData(ro);
			findList.setPagesCount(0);
			findList.setCount(0);
		}
		return findList;
	}

	/**
	 * 设置查询条件
	 *
	 * @param queryParamJSON
	 *            入参
	 * @param paramsMap
	 *            sql或hql参数
	 * @param sql
	 *            sql或hql
	 * @param isHql
	 *            是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
	 */
	@Override
	public void changeQuerySql2(JSONObject queryParamJSON,
			Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_id",
				"userId", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.phone_number",
				"phoneNumber", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.name_pingyin",
				"namePingyin", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON,
				"baseUsers.name_simple_pinyin", "nameSimplePinyin", sql,
				paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.person_id",
				"personId", sql, paramsMap, "in", isHql);
		// SaafToolUtils.parperParam(queryParamJSON, "baseUsers.source_id",
		// "sourceId",
		// sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.isadmin",
				"isadmin", sql, paramsMap, "=", isHql);
		// SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_name",
		// "userName", sql, paramsMap, "=", false);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_type",
				"userType", sql, paramsMap, "=", isHql);
		// SaafToolUtils.parperParam(queryParamJSON, "baseUsers.user_full_name",
		// "userFullName", sql, paramsMap, "=", false);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.internal_user",
				"internalUser", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.person_name",
				"personName", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUsers.dept_type",
				"deptType", sql, paramsMap, "like", isHql);
		boolean isValid = false;
		if (queryParamJSON.containsKey("isValid")) {
			isValid = queryParamJSON.getBooleanValue("isValid");
		}

		if (isValid) {
			// 查询有效的
			if (isHql) {
				sql.append(" and baseUsers.startDate<=:startDate and (baseUsers.endDate is null or baseUsers.endDate>=:endDate) and baseUsers.deleteFlag=:deleteFalse");
			} else {
				sql.append(" and baseUsers.start_date<=:startDate and (baseUsers.end_date is null or baseUsers.end_date>=:endDate) and baseUsers.delete_flag=:deleteFalse");
			}
			paramsMap.put("startDate", new Date());
			paramsMap.put("endDate", new Date());
			paramsMap.put("deleteFalse", CommonConstants.DELETE_FALSE);
		} else {
			SaafToolUtils.parperParam(queryParamJSON, "baseUsers.delete_flag",
					"deleteFlag", sql, paramsMap, "=", isHql);
			SaafToolUtils.parperParam(queryParamJSON, "baseUsers.start_date",
					"startDate", sql, paramsMap, ">=", isHql);
			SaafToolUtils.parperParam(queryParamJSON, "baseUsers.end_date",
					"endDate", sql, paramsMap, "<=", isHql);
		}
	}


	public static void main(String[] args) throws Exception {
		String sql = "SELECT baseUsers.user_id            AS userId\r\n"
				+ "      ,baseUsers.group_code         AS groupCode\r\n"
				+ "      ,baseUsers.group_name         AS groupName\r\n"
				+ "      ,baseUsers.phone_number       AS phoneNumber\r\n"
				+ "      ,baseUsers.name_pingyin       AS namePingyin\r\n"
				+ "      ,baseUsers.name_simple_pinyin AS nameSimplePinyin\r\n"
				+ "      ,baseUsers.person_id          AS personId\r\n"
				+ "      ,baseUsers.order_no           AS orderNo\r\n"
				+ "      ,basePerson.Department_full_Name   AS departmentName\r\n"
				+ "      ,baseUsers.end_date           AS endDate\r\n"
				+ "      ,baseUsers.start_date         AS startDate\r\n"
				+ "      ,baseUsers.delete_flag        AS deleteFlag\r\n"
				+ "      ,baseUsers.internal_user      AS internalUser\r\n"
				+ "      ,baseUsers.encrypted_password AS encryptedPassword\r\n"
				+ "      ,baseUsers.user_full_name     AS userFullName\r\n"
				+ "      ,baseUsers.user_type          AS userType\r\n"
				+ "      ,baseUsers.user_head_img_url  AS userHeadImgUrl\r\n"
				+ "      ,baseUsers.version_num        AS versionNum\r\n"
				+ "      ,baseUsers.email_address      AS emailAddress\r\n"
				+ "      ,baseUsers.dept_type          AS deptType\r\n"
				+ "      ,lookup.meaning               AS userTypeDesc\r\n"
				+ "      ,baseUsers.user_desc          AS userDesc\r\n"
				+ "      ,baseUsers.user_name          AS userName\r\n"
				+ "      ,baseUsers.isadmin            AS isadmin\r\n"
				+ "      ,baseUsers.source_id          AS sourceId\r\n"
				+ "      ,basePerson.employee_number   AS employeeNumber\r\n"
				+ "      ,basePerson.person_name       AS personName\r\n"
				+ "      ,basePerson.person_type       AS personType\r\n"
				+ "      ,basePerson.sex               AS sex\r\n"
				+ "      ,basePerson.birth_day         AS birthDay\r\n"
				+ "      ,basePerson.card_no           AS cardNo\r\n"
				+ "      ,basePerson.enabled           AS enabled\r\n"
				+ "      ,basePerson.tel_phone         AS telPhone\r\n"
				+ "      ,basePerson.mobile_phone      AS mobilePhone\r\n"
				+ "      ,basePerson.email             AS email\r\n"
				+ "      ,basePerson.postal_address    AS postalAddress\r\n"
				+ "      ,basePerson.postcode          AS postcode\r\n"
				+ "      ,basePerson.person_name_en    AS personNameEn\r\n"
				+ " FROM   base_users baseUsers\r\n"
				+ "LEFT   JOIN (SELECT B.*\r\n"
				+ "                   ,bd.department_name\r\n"
				+ "                   ,bd.department_full_name\r\n"
				+ "             FROM   base_person     B\r\n"
				+ "                   ,Base_Department bd\r\n"
				+ "             WHERE  b.DEPT_NO = BD.DEPARTMENT_CODE) basePerson\r\n"
				+ "ON     baseUsers.person_id = basePerson.person_id\r\n"
				+ "LEFT   JOIN (SELECT lookup_type\r\n"
				+ "                   ,lookup_code\r\n"
				+ "                   ,meaning\r\n"
				+ "             FROM   base_lookup_values\r\n"
				+ "             WHERE  lookup_type = 'USER_TYPE'\r\n"
				+ "             AND    enabled_flag = 'Y'\r\n"
				+ "             AND    delete_flag = 0\r\n"
				+ "             AND    start_date_active < SYSDATE\r\n"
				+ "             AND    (end_date_active >= SYSDATE OR end_date_active IS NULL)\r\n"
				+ "             AND    system_code = 'BASE') lookup\r\n"
				+ "ON     lookup.lookup_code = baseUsers.user_Type\r\n"
				+ "WHERE  1 = 1  and to_char(baseUsers.user_name,'yyyy-mm-dd hh24:mi:ss')  = :userName and baseUsers.user_type = :userType order by order_no,user_id";
		if (sql.contains("'yyyy-mm-dd hh24:mi:ss'")) {
			int begin = sql.indexOf("and to_char");
			int end = sql.indexOf(":userName") + 9;
			String str = sql.substring(begin, end);
			String sql2 = sql.replace("and to_char(", "").replace(
					",'yyyy-mm-dd hh24:mi:ss')", "");
		}
	}

	public Pagination<BaseUsersEntity_HI_RO> findBaseUsersPage(
			JSONObject param, Integer pageIndex, Integer pageRows) {

		StringBuffer querySql = new StringBuffer(BaseUsersEntity_HI_RO.QUERY_SQL);
		if(null != param) {
			if(!param.containsKey("deleteFlag")) {
				querySql.append(" AND U.DELETE_FLAG = 0 ");
			}
			if(param.containsKey("userType") && StringUtils.isNotBlank(param.getString("userType").trim())){
				querySql.append(" AND U.USER_TYPE =");
				querySql.append(param.getString("userType").trim());
				querySql.append(" ");
			}
			if(param.containsKey("userName") && StringUtils.isNotBlank(param.getString("userName").trim())){
				querySql.append(" AND U.USER_NAME LIKE '%");
				querySql.append(param.getString("userName").trim());
				querySql.append("%' ");
			}
			if(param.containsKey("emailAddress") && StringUtils.isNotBlank(param.getString("emailAddress").trim())){
				querySql.append(" AND U.EMAIL_ADDRESS LIKE '%");
				querySql.append(param.getString("emailAddress").trim());
				querySql.append("%' ");
			}
			if(param.containsKey("userFullName") && StringUtils.isNotBlank(param.getString("userFullName").trim())){
				querySql.append(" AND U.USER_FULL_NAME LIKE '%");
				querySql.append(param.getString("userFullName").trim());
				querySql.append("%' ");
			}
//			if(param.containsKey("profileCode") && StringUtils.isNotBlank(param.getString("profileCode").trim())){
//				querySql.append(" AND BP.PROFILE_CODE LIKE '%");
//				querySql.append(param.getString("profileCode").trim());
//				querySql.append("%' ");
//			}
			if(param.containsKey("profileValues") && StringUtils.isNotBlank(param.getString("profileValues").trim())){
				querySql.append(" AND BPV.profile_value =  '");
				querySql.append(param.getString("profileValues").trim());
				querySql.append("' ");
			}
		}
		Pagination<BaseUsersEntity_HI_RO> pagination = baseUsersDAO_HI_RO
				.findPagination(querySql.toString(),SaafToolUtils.getSqlCountString(querySql), pageIndex, pageRows);

		return pagination;
	}

	public BaseUsersEntity_HI findById(Integer baseUserId) {
	    String hql = "FROM BaseUsersEntity_HI baseUser WHERE baseUser.userId = ?";
	    Query query = hibernateTemplete.getSessionFactory().getCurrentSession().createQuery(hql);
	    query.setInteger(0, baseUserId);
	    List<BaseUsersEntity_HI> list = query.list();
	    if(CollectionUtils.isNotEmpty(list)) {
	        list.get(0);
        }
	    return null;
    }
}
