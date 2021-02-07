package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.dao.BaseOrganizationDAO_HI;
import com.sie.saaf.base.user.model.entities.BaseOrganizationEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseOrganizationPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseOrganization_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseOrganization;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.Chinese2PinyinUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.util.*;

/**
 * 对组织表base_organization进行CRUD操作
 * 
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
@Component("baseOrganizationServer")
public class BaseOrganizationServer extends BaseCommonServer<BaseOrganizationEntity_HI> implements IBaseOrganization {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseOrganizationServer.class);
	@Autowired
	private BaseOrganizationDAO_HI baseOrganizationDAO_HI;
	@Autowired
	private BaseViewObject<BaseOrganizationPerson_HI_RO> baseOrganizationPersonDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseOrganization_HI_RO> baseOrganizationDAO_HI_RO;
//	@Autowired
//	private OracleTemplateServer oracleTemplateServer;
	@Autowired
	private JedisCluster jedisCluster;

	public BaseOrganizationServer() {
		super();
	}

	/**
	 * 保存一条记录
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            参数<br>
	 *            {<br>
	 *            orgId: 组织机构Id（更新时必填）<br>
	 *            parentOrgId: 父机构Id<br>
	 *            oldParentOrgId:旧的父机构Id（当更新层级时需要传入）<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgLevel: 组织机构层级<br>
	 *            isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            remark: 备注<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            orderNo: 排序号<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            orgHierarchyId: 层级结构<br>
	 *            orgEmail: 邮件地址<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            leaderId: 组织领导Id<br>
	 *            operatorUserId:<br>
	 *            versionNum: 版本号（更新时必填）<br>
	 *            }
	 * @return BaseOrganizationEntity_HI对象
	 */
	@Override
	public BaseOrganizationEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		BaseOrganizationEntity_HI baseOrganizationEntity_HI = JSON.parseObject(queryParamJSON.toString(),
				BaseOrganizationEntity_HI.class);
		boolean isupdate = false;
		if (baseOrganizationEntity_HI.getOrgId() != null) {
			if(StringUtils.isEmpty(queryParamJSON.getString("versionNum"))) {
				throw new IllegalArgumentException("缺少参数versionNum");
			}
			isupdate = true;
		}

		BaseOrganizationEntity_HI oldParentEntity = null;
		if(StringUtils.isNotEmpty(queryParamJSON.getString("oldParentOrgId"))){
			oldParentEntity = this.getById(queryParamJSON.getInteger("oldParentOrgId"));
		}

		BaseOrganizationEntity_HI parentEntity = null;
		if (baseOrganizationEntity_HI.getParentOrgId() != null && baseOrganizationEntity_HI.getParentOrgId()!=CommonConstants.ROOT_PARENT_ID) {
			parentEntity = getById(baseOrganizationEntity_HI.getParentOrgId());
		}

		setEntityDefaultValue(baseOrganizationEntity_HI, parentEntity);
		if(isupdate){
			//如果是更新，则在保存前先更新组织架构层级
			String orgHierarchyId = baseOrganizationEntity_HI.getOrgId() + CommonConstants.HIERARCHY_ID_SPLIT;
			if (parentEntity != null) {
				orgHierarchyId = parentEntity.getOrgHierarchyId() + baseOrganizationEntity_HI.getOrgId() + CommonConstants.HIERARCHY_ID_SPLIT;
			}
			baseOrganizationEntity_HI.setOrgHierarchyId(orgHierarchyId);
		}


		baseOrganizationDAO_HI.saveOrUpdate(baseOrganizationEntity_HI);

		if(!isupdate){
			//如果是新增，则在保存之后更新组织架构层级
			updateOrgHierarchyId(baseOrganizationEntity_HI, parentEntity);
		}
		this.updateChildrens(baseOrganizationEntity_HI);//更新所有子级
		this.updateParentIsLeaf(oldParentEntity);//更新旧父级最子节点字段
		this.updateParentIsLeaf(parentEntity);//更新父级最子节点字段

		return baseOrganizationEntity_HI;
	}

	/**
	 * 更新上级的是否最子节点字段
	 * @author ZhangJun
	 * @createTime 2017/12/22 09:52
	 * @description 更新上级的是否最子节点字段
	 */
	private void updateParentIsLeaf(BaseOrganizationEntity_HI parentEntity){
		if(parentEntity!=null) {
			//更新父级isLeaf
			if (parentEntity.getIsLeaf() == null || parentEntity.getIsLeaf() == 0) {
				//this.updateProperty(parentEntity, "isLeaf", 1);
				parentEntity.setIsLeaf(CommonConstants.IS_LEAF_FALSE);
			} else {
				JSONObject findChildrensParam = new JSONObject();
				findChildrensParam.put("orgId", parentEntity.getOrgId());
				List list = this.findCurrentOrgChildrens(findChildrensParam);
				if (list == null || list.isEmpty()) {
					//如果没有子层，则更新父级isLeaf=0
					//this.updateProperty(parentEntity, "isLeaf", 0);
					parentEntity.setIsLeaf(CommonConstants.IS_LEAF_TRUE);
				}
			}
			baseOrganizationDAO_HI.update(parentEntity);
		}
	}

	/**
	 * 更新所有下层节点的层级结构
	 * @author ZhangJun
	 * @createTime 2017/12/22 09:52
	 * @description 更新所有下层节点的层级结构
	 */
	private void updateChildrens(BaseOrganizationEntity_HI parentEntity){
		//更新所有子级父节点
		String hql = "from BaseOrganizationEntity_HI where orgHierarchyId like ? and orgId != ?";
		List<BaseOrganizationEntity_HI> findChildrens = baseOrganizationDAO_HI.findList(hql,new Object[]{parentEntity.getOrgHierarchyId()+"%",parentEntity.getOrgId()});
		if(findChildrens!=null && !findChildrens.isEmpty()){
			for(BaseOrganizationEntity_HI child : findChildrens){
				String orgHierarchyId = parentEntity.getOrgHierarchyId()+child.getOrgId()+CommonConstants.HIERARCHY_ID_SPLIT;
				child.setOrgHierarchyId(orgHierarchyId);
				baseOrganizationDAO_HI.saveOrUpdate(child);
			}
		}
	}

	/**
	 * 新增时更新层级结构
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param entity
	 *            组织机构对象
	 * @param parentEntity
	 *            父对象
	 */
	private void updateOrgHierarchyId(BaseOrganizationEntity_HI entity, BaseOrganizationEntity_HI parentEntity) {
		String orgHierarchyId = entity.getOrgId() + CommonConstants.HIERARCHY_ID_SPLIT;
		if (parentEntity != null) {
			orgHierarchyId = parentEntity.getOrgHierarchyId() + entity.getOrgId() + CommonConstants.HIERARCHY_ID_SPLIT;
		}
		entity.setOrgHierarchyId(orgHierarchyId);
		baseOrganizationDAO_HI.update(entity);
	}

	/**
	 * 新增或更新时设置一些组织机构默认值
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param entity
	 *            组织机构对象
	 * @param parentEntity
	 *            父对象
	 */
	private void setEntityDefaultValue(BaseOrganizationEntity_HI entity,
			BaseOrganizationEntity_HI parentEntity) {
		if (StringUtils.isEmpty(entity.getOrgPinyinName()) || StringUtils.isEmpty(entity.getOrgSimplePinyinName())) {
			entity.setOrgPinyinName(Chinese2PinyinUtil.convertToPinyinSpell(entity.getOrgName()));
			entity.setOrgSimplePinyinName(Chinese2PinyinUtil.convertToFirstSpell(entity.getOrgName()));
		}
		if(StringUtils.isEmpty(entity.getOrgCode())){
			String pcode = parentEntity==null?"":parentEntity.getOrgCode();
			if (pcode == null) {
				pcode = "";
			}
			entity.setOrgCode(pcode+entity.getOrgSimplePinyinName());
		}
		if (entity.getDeleteFlag()==null) {
			entity.setDeleteFlag(CommonConstants.DELETE_FALSE);
		}
		if (StringUtils.isEmpty(entity.getEnabled())) {
			entity.setEnabled(CommonConstants.ENABLED_TRUE);
		}
		if(entity.getIsLeaf()==null){
			//新增时默认是最子层节点
			entity.setIsLeaf(CommonConstants.IS_LEAF_TRUE);
		}
		if (entity.getStartDate() == null) {
			entity.setStartDate(new Date());
		}
		if (parentEntity == null) {
			entity.setOrgLevel(1);
			entity.setParentOrgId(CommonConstants.ROOT_PARENT_ID);
		} else {
			entity.setOrgLevel(parentEntity.getOrgLevel() + 1);
			entity.setParentOrgId(parentEntity.getOrgId());
		}
	}

	/**
	 * 根据组织机构编码查询
	 * @param orgCode 组织机构编码
	 * @return BaseOrganizationEntity_HI
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@Override
	public List<BaseOrganizationEntity_HI> findByOrgCode(String orgCode){
		return baseOrganizationDAO_HI.findByProperty("orgCode",orgCode);
	}

	/**
	 * 分页查询组织机构列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 组织机构列表<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }<br>
	 */
	@Override
	public Pagination<BaseOrganizationPerson_HI_RO> findBaseOrganizationsPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();

		sql.append(BaseOrganizationPerson_HI_RO.getDeptSql());
		// 权限校验
//		if (!"Y".equals(queryParamJSON.getString("varIsadmin"))) {
//			String organizationId_in = StringUtils.join(queryParamJSON.getJSONArray("operationOrgIds"), ",");
//			if (StringUtils.isBlank(organizationId_in)) {
//				organizationId_in = "0";
//			}
//			queryParamJSON.put("organizationId_in", organizationId_in);
//		}

		Long startTime = System.currentTimeMillis();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		//changeQuerySql(queryParamJSON, paramsMap, sql,false);
		Pagination<BaseOrganizationPerson_HI_RO> findList = baseOrganizationPersonDAO_HI_RO.findPagination(sql,
				SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		LOGGER.info("查询业务组织后台耗时:{}秒", (System.currentTimeMillis() - startTime) /1000);
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
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_id", "orgId", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_id", "orgId_in", sql, paramsMap, "in",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.parent_org_id", "parentOrgId", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_code", "orgCode", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_name", "orgName", sql, paramsMap, "like",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.tree_type", "treeType", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_level", "orgLevel", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.channel_type", "channelType", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.business_type", "businessType", sql, paramsMap,
				"=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.is_dep", "isDep", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_type", "orgType", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_pinyin_name", "orgPinyinName", sql, paramsMap,
				"like",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_simple_pinyin_name", "orgSimplePinyinName", sql,
				paramsMap, "like",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.source_system_id", "sourceSystemId", sql, paramsMap,
				"=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.organization_id", "organizationId_in", sql, paramsMap, "in",isHql);

		boolean isValid = false;
		if(queryParamJSON.containsKey("isValid")){
			isValid = queryParamJSON.getBooleanValue("isValid");
		}

		if (isValid) {
			// 查询有效的
			sql.append(
					" and baseOrganization.start_date<=:startDate and (baseOrganization.end_date is null or baseOrganization.end_date>=:endDate) and baseOrganization.delete_flag=:deleteFalse and baseOrganization.enabled=:enableTrue");
			paramsMap.put("startDate", new Date());
			paramsMap.put("endDate", new Date());
			paramsMap.put("deleteFalse", CommonConstants.DELETE_FALSE);
			paramsMap.put("enableTrue", CommonConstants.ENABLED_TRUE);
		} else {
			SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.enabled", "enabled", sql, paramsMap, "=");
			SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.delete_flag", "deleteFlag", sql, paramsMap,
					"=");
			SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.start_date", "startDate", sql, paramsMap, "=");
			SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.end_date", "endDate", sql, paramsMap, "=");
		}
	}

	/**
	 * 查询所有下层组织机构列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询列表<br>
	 *            {<br>
	 *            orgHierarchyId:组织机构层级关系(必须),<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * 
	 * @return 下层组织列表<br>
	 *         [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 * 
	 */
	@Override
	public Pagination<BaseOrganizationPerson_HI_RO> findAllChildrens(JSONObject queryParamJSON,Integer pageIndex,Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(BaseOrganizationPerson_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		changeQuerySql(queryParamJSON, paramsMap, sql,false);
		sql.append(" and baseOrganization.org_hierarchy_id like :orgHierarchyId");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "baseOrganization.order_no,baseOrganization.org_id", false);

		paramsMap.put("orgHierarchyId", queryParamJSON.getString("orgHierarchyId") + "%");

		Pagination<BaseOrganizationPerson_HI_RO> findList = baseOrganizationPersonDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap,pageIndex,pageRows);

		return findList;
	}

	/**
	 * 查询所有上层组织机构列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询参数（orgHierarchyId和orgId任选其一）<br>
	 *            {<br>
	 *            orgHierarchyId:组织机构层级Id,<br>
	 *            orgId:组织机构Id,<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @return 所有上层组织机构列表<br>
	 *         [{ <br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 */
	@Override
	public List<BaseOrganizationPerson_HI_RO> findAllParents(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(BaseOrganizationPerson_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		changeQuerySql(queryParamJSON, paramsMap, sql,false);

		String ids = "";
		if (queryParamJSON.containsKey("orgHierarchyId")) {
			String orgHierachyId = queryParamJSON.getString("orgHierarchyId");
			ids = orgHierachyId.replaceAll(CommonConstants.HIERARCHY_ID_SPLIT, ",");
		} else if (queryParamJSON.containsKey("orgId")) {
			int orgId = queryParamJSON.getIntValue("orgId");
			BaseOrganizationEntity_HI entity = getById(orgId);
			ids = entity.getOrgHierarchyId().replaceAll(CommonConstants.HIERARCHY_ID_SPLIT, ",");
		} else {
			throw new IllegalArgumentException("缺少参数 orgHierarchyId 或 orgId");
		}
		queryParamJSON.put("orgIds", ids);
		SaafToolUtils.parperParam(queryParamJSON, "baseOrganization.org_id", "orgIds", sql, paramsMap, "in");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "baseOrganization.order_no,baseOrganization.org_id", false);

		List<BaseOrganizationPerson_HI_RO> findList = baseOrganizationPersonDAO_HI_RO.findList(sql, paramsMap);

		return findList;
	}

	/**
	 * 删除，组织机构不做物理删除
	 */
	@Override
	public void deleteById(Serializable Id) {
		// super.deleteById(Id);
		BaseOrganizationEntity_HI entity = getById(Id);
		entity.setEnabled(CommonConstants.ENABLED_FALSE);
		entity.setDeleteFlag(CommonConstants.DELETE_TRUE);
		entity.setEndDate(new Date());
		super.saveOrUpdate(entity);
	}

	/**
	 * 更新删除标记
	 * @author ZhangJun
	 * @createTime 2018/1/10 14:07
	 * @description 此方法覆盖deleteAll，Service调用此方法时，更新Profile删除标记，不直接删除
	 */
	@Override
	public void deleteAll(List<Serializable> ids) {
		Iterator<Serializable> it = ids.iterator();
		List<Integer> orgIds = new ArrayList<>();
		while(it.hasNext()){
			orgIds.add(Integer.parseInt(it.next().toString()));
		}
		this.baseOrganizationDAO_HI.updateDeleteFlag(orgIds,CommonConstants.DELETE_TRUE);
	}

	/**
	 * 查询当前组织机构下层
	 * 
	 * @param queryParamJSON
	 *            查询参数，必须传入orgId <br>
	 *            {<br>
	 *            orgId:组织机构Id（必填）<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @return 下一层组织机构列表<br>
	 *         [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 */
	@Override
	public List<BaseOrganizationPerson_HI_RO> findCurrentOrgChildrens(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(BaseOrganizationPerson_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		changeQuerySql(queryParamJSON, paramsMap, sql,false);
		sql.append(" and baseOrganization.parent_org_id=:orgId");
		paramsMap.put("orgId", queryParamJSON.getIntValue("orgId"));
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "baseOrganization.order_no,baseOrganization.org_id", false);

		List<BaseOrganizationPerson_HI_RO> findList = baseOrganizationPersonDAO_HI_RO.findList(sql, paramsMap);
		return findList;
	}

	/**
	 * 查询指定人员的所属组织机构
	 * @param queryParamJSON
	 *            查询参数，必须传入personId <br>
	 *            {<br>
	 *            personId:人员Id（必填）<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @return 指定人员所属组织机构列表<br>
	 *         [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 */
	@Override
	public List<BaseOrganizationPerson_HI_RO> findOrganizationByPersonId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(BaseOrganizationPerson_HI_RO.QUERY_JOIN_PERSON_ORGANIZATION_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		changeQuerySql(queryParamJSON, paramsMap, sql,false);
		sql.append(" and basePersonOrganization.person_id=:personId");
		paramsMap.put("personId", queryParamJSON.getIntValue("personId"));
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "baseOrganization.order_no,baseOrganization.org_id", false);

		List<BaseOrganizationPerson_HI_RO> findList = baseOrganizationPersonDAO_HI_RO.findList(sql, paramsMap);
		return findList;
	}

	@Override
	public List<BaseOrganization_HI_RO> findBaseOrganizationROEntities(){
		List<BaseOrganization_HI_RO> baseOrganizationROEntities = baseOrganizationDAO_HI_RO.findList(BaseOrganization_HI_RO.QUEYR_ORG_SQL, new HashMap<String, Object>());
		return baseOrganizationROEntities;
	}


	/**
	 * 查询库存组织
	 * @author ZhangJun
	 * @createTime 2018/3/20
	 * @description 查询库存组织
	 */
	@Override
	public JSONArray findCacheOrgInvList(JSONObject queryParamJSON) {
		List<BaseOrganizationEntity_HI> findList = super.findList(queryParamJSON);
		JSONArray array = new JSONArray();
		if(findList != null && !findList.isEmpty()){
			for(BaseOrganizationEntity_HI entity : findList){
				JSONObject data = new JSONObject();
				data.put("organizationId",entity.getOrgId());
				data.put("organizationCode",entity.getOrgCode());
				data.put("organizationName",entity.getOrgName());
				data.put("orgId",entity.getOrganizationId());
				array.add(data);
			}
		}

		return array;
	}

    /**
     *  箱码追溯记录查询符合条件的操作子库编码
     * @param queryParam
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public Pagination findOprSubInvList(Set<String> queryParam, Integer pageIndex, Integer pageRows) {

        StringBuffer paramStr = new StringBuffer(100);

        for (String param : queryParam) {
            paramStr.append(param + ",");
        }

        if (paramStr.length() > 0) {
            paramStr.deleteCharAt(paramStr.length() - 1);
        }

        JSONObject jsonObject = new JSONObject().fluentPut("orgId", paramStr);

        StringBuffer sql = new StringBuffer(BaseOrganization_HI_RO.QUERY_OPRSUBINV_RECORD);

        Map<String, Object> organizationParams = new HashMap<>();

        SaafToolUtils.parperParam(jsonObject,"e.organization_id","orgId",sql,organizationParams,"in");

        sql.append(" OR e.organization_id = '261') OR ( 1=1 ");

        SaafToolUtils.parperParam(jsonObject,"'261'","orgId",sql,organizationParams,"in");

        sql.append("AND e.organization_id IN ('81', '181', '182')) ");

        //取出编码，产品编码，产品批次Id,归属子库编号
        Pagination<BaseOrganization_HI_RO> pagination = baseOrganizationDAO_HI_RO.findPagination(sql.toString(),SaafToolUtils.getSqlCountString(sql), organizationParams, pageIndex, pageRows);

        return pagination;
    }

	/**
	 * 同步库存组织
	 * @author ZhangJun
	 * @createTime 2018/3/16
	 * @description 同步库存组织
	 */
	@Override
	public JSONObject saveSyncInvOrganization(JSONObject queryParamJSON){
		JSONObject result = new JSONObject();
//		try {
//			StringBuffer sb = new StringBuffer();
//			sb.append("SELECT ORGANIZATION_ID        AS ORG_ID,\n" +
//					"       ORGANIZATION_ID        AS SOURCE_SYSTEM_ID,\n" +
//					"       ORGANIZATION_CODE      AS ORG_CODE,\n" +
//					"       ORGANIZATION_NAME      AS ORG_NAME,\n" +
//					"       OPERATING_UNIT         AS ORGANIZATION_ID,\n" +
//					"       INVENTORY_ENABLED_FLAG AS ENABLED\n" +
//					"  FROM org_organization_definitions ");
//
//			List<JSONObject> orgs = oracleTemplateServer.findList(sb.toString());
//			int count = 0;
//			if(orgs!=null && !orgs.isEmpty()){
//
//				JSONObject params = new JSONObject();
//				params.put("orgType","INV_ORG");
//				List<BaseOrganizationEntity_HI> baseOrganizations = this.findList(params);
//				Map<Integer,BaseOrganizationEntity_HI> orgsMap = new HashMap<>();
//				if(baseOrganizations!=null && !baseOrganizations.isEmpty()){
//					for (BaseOrganizationEntity_HI entity : baseOrganizations) {
//						orgsMap.put(entity.getOrgId(),entity);
//					}
//				}
//
//				List<BaseOrganizationEntity_HI> savesList = new ArrayList<>();
//				for (int i = 0; i < orgs.size(); i++) {
//					JSONObject org = orgs.get(i);
//					Integer orgId = org.getInteger("ORG_ID");
//
//					BaseOrganizationEntity_HI entity = null;
//					if(orgsMap.containsKey(orgId)){
//						entity = orgsMap.get(orgId);
//						BaseOrganizationEntity_HI newEntity = JSON.toJavaObject(org,BaseOrganizationEntity_HI.class);
//						SaafBeantUtils.copyUnionProperties(newEntity,entity);
//
//						//修改
//						entity.setOperatorUserId(1);
//						//设置默认值
//						setEntityDefaultValue(entity,null);
//						entity.setOrgHierarchyId(entity.getOrgId()+CommonConstants.HIERARCHY_ID_SPLIT);
//						//保存
//						this.update(entity);
//
//					}else{
//						entity = JSON.toJavaObject(org,BaseOrganizationEntity_HI.class);
//						entity.setIsDep("N");
//						entity.setOrgType("INV_ORG");
//
//						//新增
//						entity.setOperatorUserId(1);
//						//设置默认值
//						setEntityDefaultValue(entity,null);
//						entity.setOrgHierarchyId(entity.getOrgId()+CommonConstants.HIERARCHY_ID_SPLIT);
//						//保存
//						baseOrganizationDAO_HI.saveInvOrg(entity);
//
//					}
//
//
//					savesList.add(entity);
//				}
//
//				if(!savesList.isEmpty()){
//					for (BaseOrganizationEntity_HI entity : savesList) {
//						//保存至redis中
//						JSONObject redisValue = new JSONObject();
//						redisValue.put("organizationId",entity.getOrgId());
//						redisValue.put("organizationCode",entity.getOrgCode());
//						redisValue.put("organizationName",entity.getOrgName());
//						redisValue.put("orgId",entity.getOrganizationId());
//						jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_ORGANIZATION_INV_VIEW_KEY,String.valueOf(entity.getOrgId()),redisValue.toJSONString());
//
//						count ++;
//					}
//				}
//			}else{
//				result.put("syncStatus","success");
//				result.put("syncMsg","当前未取得产品数据");
//				LOGGER.info("库存组织同步结果：{}",result.toJSONString());
//			}
//
//			result.put("syncStatus","success");
//			result.put("syncMsg","库存组织同步完成");
//			result.put("updateCount",count);
//		} catch (Exception e) {
//			LOGGER.error("",e);
//			result.put("syncStatus","fail");
//			result.put("syncMsg","库存组织同步出现异常:"+e.getMessage());
//		}
		return result;
	}


	@Override
	public JSONObject saveInvOrg2Redis(JSONObject queryJSONParam){
		JSONObject result = new JSONObject();
		List<BaseOrganizationEntity_HI> list = baseOrganizationDAO_HI.findByProperty("orgType","INV_ORG");
		int count = 0;
		if(list != null && !list.isEmpty()){
			for(BaseOrganizationEntity_HI entity : list){
				JSONObject redisValue = new JSONObject();
				redisValue.put("organizationId",entity.getOrgId());
				redisValue.put("organizationCode",entity.getOrgCode());
				redisValue.put("organizationName",entity.getOrgName());
				redisValue.put("orgId",entity.getOrganizationId());
				jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_ORGANIZATION_INV_VIEW_KEY,String.valueOf(entity.getOrgId()),redisValue.toJSONString());
				count++;
			}
		}
		result.put("count",count);
		return result;
	}
}
