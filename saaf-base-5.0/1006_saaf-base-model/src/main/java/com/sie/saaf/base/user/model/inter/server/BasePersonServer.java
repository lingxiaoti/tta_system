package com.sie.saaf.base.user.model.inter.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.BasePersonOrganizationEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePersonOrganizationPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserGroupAssignEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBasePerson;
import com.sie.saaf.base.user.model.inter.IBasePersonOrganization;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * 对人员表base_person进行CRUD操作
 * 
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
@Component("basePersonServer")
public class BasePersonServer extends BaseCommonServer<BasePersonEntity_HI>
		implements IBasePerson {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BasePersonServer.class);
	@Autowired
	private ViewObject<BasePersonEntity_HI> basePersonDAO_HI;
	@Autowired
	private BaseViewObject<BasePersonOrganizationPerson_HI_RO> basePersonOrganizationPersonDAO_HI_RO;
	@Autowired
	private IBasePersonOrganization basePersonOrganizationServer;
	// @Autowired
	// private OracleTemplateServer oracleTemplateServer;

	@Autowired
	private BaseViewObject<BaseUsersPerson_HI_RO> baseUsersPersonDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseUserGroupAssignEntity_HI_RO> userGroupAssignEntity_RO;

	public BasePersonServer() {
		super();
	}

	/**
	 * 获取需要同步的人员数据
	 * 
	 * @param lastSyncTime
	 * @return
	 */
	@Override
	public List<JSONObject> findPerAllPeopleFList(String lastSyncTime) {
		// try {
		// StringBuffer sb = new StringBuffer();
		// sb.append("SELECT PAP.PERSON_ID SOURCE_ID,\n" +
		// "       PAP.EMPLOYEE_NUMBER,\n" +
		// "       PAP.LAST_NAME PERSON_NAME,\n" +
		// "       pap.sex SEX,\n" +
		// "       PAP.DATE_OF_BIRTH BIRTH_DAY,\n" +
		// "       PAP.EFFECTIVE_START_DATE,\n" +
		// "       PAP.EFFECTIVE_END_DATE,\n" +
		// "       PAP.ATTRIBUTE3 CARD_NO,\n" +
		// "       PAP.ATTRIBUTE4 NATIONAL_ID,\n" +
		// "       PAP.ATTRIBUTE5 AS MOBILE_PHONE,\n" +
		// "       PAP.EMAIL_ADDRESS EMAIL,\n" +
		// "       PAP.CURRENT_EMPLOYEE_FLAG,\n" +
		// "       PAP.LAST_UPDATE_DATE\n" +
		// "  FROM HR.PER_ALL_PEOPLE_F PAP\n" +
		// " WHERE PAP.EFFECTIVE_END_DATE > SYSDATE\n" +
		// "   AND PAP.LAST_UPDATE_DATE >= TO_DATE('" + lastSyncTime +
		// "','yyyy-mm-dd HH24:mi:ss') ");
		// LOGGER.info("{}", sb.toString());
		// return oracleTemplateServer.findList(sb.toString());
		// } catch (SQLException e) {
		// LOGGER.error("查询需要同步的人数数据异常:{}", e);
		// return null;
		// }
		return null;
	}

	/**
	 * 新增或修改人员数据（同步人员时用到）
	 * 
	 * @param basePersonEntity
	 * @return
	 * @throws Exception
	 */
	@Override
	public BasePersonEntity_HI saveOrUpdateBasePerson(
			BasePersonEntity_HI basePersonEntity) throws Exception {
		try {
			basePersonDAO_HI.saveOrUpdate(basePersonEntity);
			return basePersonEntity;
		} catch (Exception e) {
			throw new Exception("更新人员信息异常:{}", e);
		}
	}

	/**
	 * 通过源系统ID和系统来源查询人员信息
	 * 
	 * @param sourceId
	 *            源系统ID
	 * @param sourceCode
	 *            系统来源
	 * @return
	 */
	@Override
	public List<BasePersonEntity_HI> findBasePersonList(Integer sourceId,
			String sourceCode) {
		String hql = "from BasePersonEntity_HI where sourceId = :sourceId and sourceCode = :sourceCode ";
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("sourceId", sourceId);
		paramsMap.put("sourceCode", sourceCode);
		return basePersonDAO_HI.findList(hql, paramsMap);
	}

	/**
	 * 保存一条记录
	 * 
	 * @param queryParamJSON
	 *            参数<br>
	 *            {<br>
	 *            personId: 人员Id,（更新时必填）<br>
	 *            employeeNumber: 员工号,<br>
	 *            personName: 人员姓名,<br>
	 *            personType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
	 *            sex: 性别,<br>
	 *            birthDay: 生日,<br>
	 *            cardNo: 身份证号,<br>
	 *            enabled: 启用标识,<br>
	 *            telPhone: 电话号码,<br>
	 *            mobilePhone: 手机号码,<br>
	 *            email: 邮箱地址,<br>
	 *            postalAddress: 通信地址,<br>
	 *            postcode: 邮编,<br>
	 *            operatorUserId:操作人<br>
	 *            versionNum: 版本号,（更新时必填）<br>
	 *            }
	 * @return basePersonEntity_HI对象
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	@Override
	public BasePersonEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 分页查询人员列表
	 * 
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            personName: 人员姓名,<br>
	 *            personName: 人员姓名,<br>
	 *            personType: 人员类型,<br>
	 *            cardNo: 身份证号,<br>
	 *            enabled: 是否启用,<br>
	 *            postalAddress: 通信地址,<br>
	 *            postcode: 邮编,<br>
	 *            sex: 性别,<br>
	 *            }
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 分页人员列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         birthDay: 出生日期,<br>
	 *         cardNo: 身份证号,<br>
	 *         creationDate: 创建时间,<br>
	 *         email: 邮箱地址,<br>
	 *         employeeNumber: 员工号,<br>
	 *         enabled: 是否启用,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         mobilePhone: 手机号,<br>
	 *         personId: 人员,<br>
	 *         personName: 人员姓名,<br>
	 *         personType: 人员类型,<br>
	 *         postalAddress: 通信地址,<br>
	 *         postcode: 邮编,<br>
	 *         sex: 性别,<br>
	 *         telPhone: 电话,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	@Override
	public Pagination<BasePersonEntity_HI> findPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON, pageIndex, pageRows);
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
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.person_id",
				"personId", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.employee_number",
				"employeeNumber", sql, paramsMap, "fulllike", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.person_name",
				"personName", sql, paramsMap, "fulllike", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.tel_phone",
				"telPhone", sql, paramsMap, "fulllike", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.mobile_phone",
				"mobilePhone", sql, paramsMap, "fulllike", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.person_type",
				"personType", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.card_no",
				"cardNo", sql, paramsMap, "fulllike", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.postal_address",
				"postalAddress", sql, paramsMap, "fulllike", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.postcode",
				"postcode", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.sex", "sex", sql,
				paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.email", "email",
				sql, paramsMap, "fulllike", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.person_name_cn",
				"personNameCn", sql, paramsMap, "fulllike", isHql);//员工中文名
		SaafToolUtils.parperParam(queryParamJSON, "basePerson.person_name_en",
				"nameEn", sql, paramsMap, "fulllike", isHql);//员工英文名

		boolean isValid = false;
		if (queryParamJSON.containsKey("isValid")) {
			isValid = queryParamJSON.getBooleanValue("isValid");
		}

		if (isValid) {
			// 查询有效的
			sql.append(" and basePerson.enabled=:enableTrue");
			paramsMap.put("enableTrue", CommonConstants.ENABLED_TRUE);
		} else {
			SaafToolUtils.parperParam(queryParamJSON, "basePerson.enabled",
					"enableTrue", sql, paramsMap, "=", isHql);
		}
	}

	/**
	 * 根据组织机构Id查询人员列表
	 * 
	 * @param orgId
	 *            组织机构Id
	 * @return 分页人员列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         birthDay: 出生日期,<br>
	 *         cardNo: 身份证号,<br>
	 *         creationDate: 创建时间,<br>
	 *         email: 邮箱地址,<br>
	 *         employeeNumber: 员工号,<br>
	 *         enabled: 是否启用,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         mobilePhone: 手机号,<br>
	 *         personId: 人员,<br>
	 *         personName: 人员姓名,<br>
	 *         personType: 人员类型,<br>
	 *         postalAddress: 通信地址,<br>
	 *         postcode: 邮编,<br>
	 *         sex: 性别,<br>
	 *         telPhone: 电话,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	@Override
	public Pagination<BasePersonOrganizationPerson_HI_RO> findBasePersonsByOrgId(
			Integer orgId, Integer pageIndex, Integer pageRows) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("orgId", orgId);
		return findBasePersonsJoinPersonOrg(queryParamJSON, pageIndex, pageRows);
	}

	/**
	 * 根据职位Id查询人员列表
	 * 
	 * @param positionId
	 *            职位Id
	 * @return 分页人员列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         birthDay: 出生日期,<br>
	 *         cardNo: 身份证号,<br>
	 *         creationDate: 创建时间,<br>
	 *         email: 邮箱地址,<br>
	 *         employeeNumber: 员工号,<br>
	 *         enabled: 是否启用,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         mobilePhone: 手机号,<br>
	 *         personId: 人员,<br>
	 *         personName: 人员姓名,<br>
	 *         personType: 人员类型,<br>
	 *         postalAddress: 通信地址,<br>
	 *         postcode: 邮编,<br>
	 *         sex: 性别,<br>
	 *         telPhone: 电话,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	@Override
	public Pagination<BasePersonOrganizationPerson_HI_RO> findBasePersonsByPositionId(
			Integer positionId, Integer pageIndex, Integer pageRows) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("positionId", positionId);
		return findBasePersonsJoinPersonOrg(queryParamJSON, pageIndex, pageRows);
	}

	/**
	 * 关联base_person_organization查询人员列表
	 * 
	 * @param queryParamJSON
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数 <br>
	 *            {<br>
	 *            personName: 人员姓名,<br>
	 *            personName: 人员姓名,<br>
	 *            personType: 人员类型,<br>
	 *            cardNo: 身份证号,<br>
	 *            enabled: 是否启用,<br>
	 *            postalAddress: 通信地址,<br>
	 *            postcode: 邮编,<br>
	 *            sex: 性别,<br>
	 *            }
	 * @return 分页人员列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         birthDay: 出生日期,<br>
	 *         cardNo: 身份证号,<br>
	 *         creationDate: 创建时间,<br>
	 *         email: 邮箱地址,<br>
	 *         employeeNumber: 员工号,<br>
	 *         enabled: 是否启用,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         mobilePhone: 手机号,<br>
	 *         personId: 人员,<br>
	 *         personName: 人员姓名,<br>
	 *         personType: 人员类型,<br>
	 *         postalAddress: 通信地址,<br>
	 *         postcode: 邮编,<br>
	 *         sex: 性别,<br>
	 *         telPhone: 电话,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	@Override
	public Pagination<BasePersonOrganizationPerson_HI_RO> findBasePersonsJoinPersonOrg(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();

		sql.append(BasePersonOrganizationPerson_HI_RO.QUERY_JOIN_PERSON_ORGANIZATION_SQL);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		changeQuerySql(queryParamJSON, paramsMap, sql, false);
		/*
		 * if (queryParamJSON.containsKey("positionId")) {
		 * sql.append(" and basePersonOrganization.position_id=:positionId");
		 * paramsMap.put("positionId",
		 * queryParamJSON.getIntValue("positionId")); } if
		 * (queryParamJSON.containsKey("orgId")) {
		 * sql.append(" and basePersonOrganization.org_id=:orgId");
		 * paramsMap.put("orgId", queryParamJSON.getIntValue("orgId")); }
		 * SaafToolUtils.changeQuerySort(queryParamJSON, sql,
		 * "basePerson.person_id", false);
		 */
		// mysql2oracle sql.append(" group by basePerson.person_id ");

		Pagination<BasePersonOrganizationPerson_HI_RO> findList = basePersonOrganizationPersonDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);

		return findList;
	}

	/**
	 * 保存人员并保存与组织、职位关系
	 * 
	 * @param queryParamJSON
	 *            保存参数 { personId:人员Id,更新时必填） employeeNumber: 员工号,<br>
	 *            personName: 人员姓名,<br>
	 *            personType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
	 *            sex: 性别,<br>
	 *            birthDay: 生日,<br>
	 *            cardNo: 身份证号,<br>
	 *            enabled: 启用标识,<br>
	 *            telPhone: 电话号码,<br>
	 *            mobilePhone: 手机号码,<br>
	 *            email: 邮箱地址,<br>
	 *            postalAddress: 通信地址,<br>
	 *            postcode: 邮编,<br>
	 *            operatorUserId:操作人<br>
	 *            versionNum: 版本号,（更新时必填）<br>
	 *            orgIds:[{<br>
	 *            orgId:组织Id,<br>
	 *            positionIds:[1,2,3,4]职位列表<br>
	 *            }] }
	 * @return 人员表信息 BasePersonEntity_HI
	 * @author ZhangJun
	 * @createTime 2018/1/7 20:46
	 * @description 保存人员并保存与组织、职位关系
	 */
	@Override
	public BasePersonEntity_HI savePersonOrganization(JSONObject queryParamJSON) {
		LOGGER.info("-----parameters------queryParamJSON:" + queryParamJSON);

		// 保存人员
		BasePersonEntity_HI entity = this.saveOrUpdate(queryParamJSON);

		if (queryParamJSON.containsKey("orgIds")) {
			// 如果有分配组织
			JSONObject parameters = new JSONObject();
			parameters.put("personId", entity.getPersonId());
			List<BasePersonOrganizationEntity_HI> personOrgs = basePersonOrganizationServer
					.findList(parameters);
			JSONArray array = queryParamJSON.getJSONArray("orgIds");
			if (array != null && !array.isEmpty()) {

				/*---------被移除的组织--------*/
				Set<Serializable> ids = new HashSet<>();
				if (personOrgs != null && !personOrgs.isEmpty()) {
					for (BasePersonOrganizationEntity_HI temp : personOrgs) {
						boolean delflag = true;
						for (int i = 0; i < array.size(); i++) {
							JSONObject orgInfo = array.getJSONObject(i);
							Integer orgId = orgInfo.getInteger("orgId");
							if (temp.getOrgId().intValue() == orgId.intValue()) {
								delflag = false;
								break;
							}
						}
						if (delflag) {
							ids.add(temp.getPersonOrgId());
						}
					}
				}
				/*---------被移除的组织--------*/

				for (int i = 0; i < array.size(); i++) {
					JSONObject orgInfo = array.getJSONObject(i);
					Integer orgId = orgInfo.getInteger("orgId");

					BasePersonOrganizationEntity_HI basePersonOrg = validatePersonHasOrg(
							personOrgs, orgId, null, true);
					if (basePersonOrg == null) {
						basePersonOrg = new BasePersonOrganizationEntity_HI();
						basePersonOrg.setOrgId(orgId);
						basePersonOrg.setPersonId(entity.getPersonId());
					}

					// 职位是组织下的，所以传入的参数职位应放在组织下
					if (orgInfo.containsKey("positionIds")) {
						// 如果有分配职位
						JSONArray positionArray = orgInfo
								.getJSONArray("positionIds");
						/*---------被移除的职位--------*/
						if (personOrgs != null && !personOrgs.isEmpty()) {
							for (BasePersonOrganizationEntity_HI temp : personOrgs) {
								boolean delflag = true;
								for (int k = 0; k < positionArray.size(); k++) {
									Integer tempPositionId = positionArray
											.getInteger(k);
									if (temp.getOrgId().intValue() == orgId
											.intValue()
											&& temp.getPositionId().intValue() == tempPositionId
													.intValue()) {
										delflag = false;
										break;
									}
								}
								if (delflag) {
									ids.add(temp.getPersonOrgId());
								}
							}
						}
						/*---------被移除的职位--------*/

						for (int j = 0; j < positionArray.size(); j++) {
							Integer positionId = positionArray.getInteger(j);
							BasePersonOrganizationEntity_HI basePersonOrg2 = validatePersonHasOrg(
									personOrgs, orgId, positionId, false);
							if (basePersonOrg2 == null) {
								BeanUtils.copyProperties(basePersonOrg,
										basePersonOrg2);
								basePersonOrg2.setPositionId(positionId);
								basePersonOrg2
										.setPersonId(entity.getPersonId());
								basePersonOrganizationServer
										.saveOrUpdate(basePersonOrg);
							}
						}
					} else {
						// 没有职位，直接保存组织机构关系即可
						basePersonOrganizationServer
								.saveOrUpdate(basePersonOrg);
					}
				}

				// 删除，删除已被去掉的组织及职位
				if (!ids.isEmpty()) {
					List<Serializable> serializableIds = new ArrayList<>();
					serializableIds.addAll(ids);
					basePersonOrganizationServer.deleteAll(serializableIds);
				}

			} else {
				// 如果没有分配组织，之前已分配的组织都要删除（包括组织下分配的职位）
				if (personOrgs != null && !personOrgs.isEmpty()) {
					List<Serializable> ids = new ArrayList<>();
					for (BasePersonOrganizationEntity_HI basePersonOrg : personOrgs) {
						ids.add(basePersonOrg.getPersonOrgId());
					}
					basePersonOrganizationServer.deleteAll(ids);
				}
			}
		}

		return entity;
	}

	/**
	 * 验证人员是否存在于当前组织或职位
	 * 
	 * @param personOrgs
	 *            当前人员所拥有的组织及职位
	 * @param orgId
	 *            组织或职位Id
	 * @param isOrg
	 *            true为验证组织，false为验证职位
	 * @return BasePersonOrganizationEntity_HI
	 * @author ZhangJun
	 * @createTime 2018/1/7 22:58
	 * @description 验证人员是否存在于当前组织或职位
	 */
	private BasePersonOrganizationEntity_HI validatePersonHasOrg(
			List<BasePersonOrganizationEntity_HI> personOrgs, Integer orgId,
			Integer posiitonId, boolean isOrg) {
		if (personOrgs != null && !personOrgs.isEmpty()) {
			for (BasePersonOrganizationEntity_HI entity : personOrgs) {
				if (isOrg) {
					// 验证组织
					if (entity.getOrgId() != null
							&& entity.getOrgId().intValue() == orgId.intValue()) {
						return entity;
					}
				} else {
					// 验证职位
					if (entity.getOrgId().intValue() == orgId.intValue()) {
						// 组织下的职位才做判断
						if (entity.getPositionId() != null
								&& entity.getPositionId().intValue() == orgId
										.intValue()) {
							return entity;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void saveByExcuteSql(String sql) {
		basePersonDAO_HI.executeSqlUpdate(sql);
	}

	@Override
	public List<BaseUsersPerson_HI_RO> findProccessUsers(
			JSONObject queryParamJSON) {
		List<BaseUsersPerson_HI_RO> resultList = new ArrayList<BaseUsersPerson_HI_RO>();
		String assignFlag = queryParamJSON.getString("assignFlag");
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("userId", queryParamJSON.getInteger("startUserId"));
		// queryMap.put("userId", 2);
		if ("BIC".equalsIgnoreCase(assignFlag)) {
			resultList = baseUsersPersonDAO_HI_RO.findList(
					BaseUsersPerson_HI_RO.QUERY_BIC_USER, queryMap);
		} else {
			List<BaseUsersPerson_HI_RO> personList = baseUsersPersonDAO_HI_RO
					.findList(BaseUsersPerson_HI_RO.QUERY_PARENT_PERSON_SQL,
							queryMap);
			String personId = null;
			if (personList != null && !personList.isEmpty()) {
				personId = personList.get(0).getPersonId();
			}
			if (personId != null) {
				queryMap.clear();
				queryMap.put("personId", Integer.parseInt(personId));
				queryMap.put("userType", queryParamJSON.getInteger("userType"));
				resultList = baseUsersPersonDAO_HI_RO.findList(
						BaseUsersPerson_HI_RO.QUERY_USER_BY_PERSON_ID_SQL,
						queryMap);
			}
		}
		return resultList;
	}

	@Override
	public List<BaseUsersPerson_HI_RO> findProccessStartUser(
			JSONObject queryParamJSON) {
		LOGGER.info(".findProccessStartUser 调用流程通过流程发起人查询人员信息入参:{}",
				queryParamJSON);
		List<BaseUsersPerson_HI_RO> personList = null;
		HashMap<String, Object> queryMap = new HashMap<>();
		Integer userId = queryParamJSON.getInteger("userId");// 发起人
		String paramUserType = queryParamJSON.getString("userType");
		String assignFlag = queryParamJSON.getString("assignFlag");
		if ("BIC".equalsIgnoreCase(assignFlag)) {
			queryMap.put("userId", userId);
			personList = baseUsersPersonDAO_HI_RO.findList(
					BaseUsersPerson_HI_RO.QUERY_BIC_USER, queryMap);
			LOGGER.info(".findProccessStartUser 当前BIC审批，入参userId:{},出参信息:{}",
					queryMap, personList);
		} else {
			String userType = "";
			do {
				Integer userTemp = userId;
				queryMap.put("userId", userId);
				personList = baseUsersPersonDAO_HI_RO
						.findList(
								BaseUsersPerson_HI_RO.QUERY_USER_BY_PROCCESS_START_USER_ID,
								queryMap);
				LOGGER.info(".findProccessStartUser 当前查询的入参userId:{},出参信息:{}",
						queryMap, personList);
				if (personList != null && !personList.isEmpty()) {
					BaseUsersPerson_HI_RO personHiRo = personList.get(0);
					userType = StringUtils.isBlank(personHiRo.getUserType()) ? ""
							: personHiRo.getUserType();
					userId = personHiRo.getUserId();
					if (userTemp.equals(userId)) {
						// 避免死循环:传入的参数userId跟查到用户相同,则跳出(避免自己的上级是自己的情况死循环)
						break;
					}
				}
			} while (paramUserType.compareTo(userType) != 0
					&& personList != null && !personList.isEmpty());
		}
		if (personList == null) {
			personList = new ArrayList<BaseUsersPerson_HI_RO>();
		}
		LOGGER.info(".findProccessStartUser 入参信息:{},最终结果信息:{}", queryParamJSON,
				SaafToolUtils.toJson(personList));
		return personList;
	}

	@Override
	public List<BaseUserGroupAssignEntity_HI_RO> findAssignStartUser(
			JSONObject queryParamJSON) {
		LOGGER.info(".findAssignStartUser 调用流程通过流程发起人查询指定的用户群组人员 入参:{}",
				queryParamJSON);
		List<BaseUserGroupAssignEntity_HI_RO> assignRoList = null;
		HashMap<String, Object> queryMap = new HashMap<>();
		Integer userId = queryParamJSON.getInteger("userId");// 发起人
		String userGroupCode = queryParamJSON.getString("userGroupCode");// 发起人
		queryMap.put("userId", userId);
		assignRoList = userGroupAssignEntity_RO.findList(
				BaseUserGroupAssignEntity_HI_RO.QUERY_FOR_PERSON, queryMap);
		List<BaseUserGroupAssignEntity_HI_RO> resultList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(assignRoList)) {
			 List<BaseUserGroupAssignEntity_HI_RO> filters =
			 assignRoList.stream().filter(assign
			 ->userGroupCode.equals(assign.getUserGroupCode())).collect(Collectors.toList());
			 if(!CollectionUtils.isEmpty(filters)){
			 resultList.add(filters.get(0));
			 }
		}
		return resultList;
	}

	@Override
	public List<BaseUserGroupAssignEntity_HI_RO> findAssignStartUser2(
			JSONObject queryParamJSON) {
		LOGGER.info(".findAssignStartUser 调用流程通过流程发起人查询指定的用户群组人员 入参:{}",
				queryParamJSON);
		List<BaseUserGroupAssignEntity_HI_RO> assignRoList = new ArrayList<BaseUserGroupAssignEntity_HI_RO>();
		HashMap<String, Object> queryMap = new HashMap<>();
		Integer userId = queryParamJSON.getInteger("userId");// 发起人
		// String userGroupCode = queryParamJSON.getString("userGroupCode");//
		// 发起人
		queryMap.put("userId", userId);
		assignRoList = userGroupAssignEntity_RO.findList(
				BaseUsersPerson_HI_RO.QUERY_USER_BY_PROCCESS_START_USER_ID2,
				queryMap);
		return assignRoList;
	}

	public static void main(String[] args) {
		System.out.println("26".compareTo(""));
	}

}
