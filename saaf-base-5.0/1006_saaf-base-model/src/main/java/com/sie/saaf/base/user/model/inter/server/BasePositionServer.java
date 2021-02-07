package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePositionEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionOrg_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBasePosition;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 对职位表Base_Position进行CRUD操作
 * 
 * @author ZhangJun
 * @creteTime 2017-12-11
 */
@Component("basePositionServer")
public class BasePositionServer extends BaseCommonServer<BasePositionEntity_HI> implements IBasePosition {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BasePositionServer.class);
//	@Autowired
//	private ViewObject<BasePositionEntity_HI> basePositionDAO_HI;
	@Autowired
	private BaseViewObject<BasePositionPerson_HI_RO> basePositionPersonDAO_HI_RO;
	@Autowired
	private BaseViewObject<BasePositionOrg_HI_RO> basePositionOrgDAO_HI_RO;
	
	public BasePositionServer() {
		super();
	}

	/**
	 * 询人员、职位、部门关系<用于登录获取人员的职位和部门信息>
	 * @param personId
	 * @return
	 */
	@Override
	public List<BasePositionPerson_HI_RO> findPersonPositionOrgRelationByPersonId(Integer personId){
		StringBuffer sql = new StringBuffer(BasePositionPerson_HI_RO.QUERY_PERSON_POSITION_ORG_RELATION);
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("personId", personId);
		return basePositionPersonDAO_HI_RO.findList(sql, queryParamJSON);
	}

	/**
	 * 保存一条记录
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *                positionId:职务Id,（更新时必填）<br>
	 *            orgId:组织Id,<br>
	 *            positionName:职务名称,<br>
	 *            sourceSystemId:源系统Id,<br>
	 *            startDate:生效日期,<br>
	 *            endDate:失效日期,<br>
	 *            enabled:是否启用,<br>
	 *            operatorUserId:操作者<br>
	 *                versionNum: 版本号（更新时必填）<br>
	 *            }<br>
	 * @return BasePositionEntity_HI对象
	 */
	@Override
	public BasePositionEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	
	/**
	 * 设置默认值
	 * @param entity
	 */
	protected void setEntityDefaultValue(BasePositionEntity_HI entity) {

		if(StringUtils.isEmpty(entity.getEnabled())) {
			entity.setEnabled(CommonConstants.ENABLED_TRUE);
		}
		if(entity.getStartDate()==null) {
			entity.setStartDate(new Date());
		}
		
	}
	/**
	 * 分页查询职位
	 *
	 * @param queryParamJSON
	 *            查询参数
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 分页数据列表
	 */
	/**
	 * 分页查询职位
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            orgId:组织Id,<br>
	 *            positionName:职位名称,<br>
	 *            sourceSystemId:源系统Id,<br>
	 *            enabled:是否启用,<br>
	 *            startDate:生效时间,<br>
	 *            endDate:失效时间<br>
	 *            }<br>
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 分页数据列表<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 */
	@Override
	public Pagination<BasePositionEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON,pageIndex,pageRows);
	}

	/**
	 * 设置查询条件
	 * @param queryParamJSON 入参
	 * @param paramsMap sql或hql参数
	 * @param sql sql或hql
	 * @param isHql 是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
	 */
	@Override
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String,Object> paramsMap, StringBuffer sql,boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "basePosition.org_id", "orgId", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePosition.position_name", "positionName", sql, paramsMap, "like",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "basePosition.source_system_id", "sourceSystemId", sql, paramsMap, "=",isHql);

		boolean isValid = false;
		if(queryParamJSON.containsKey("isValid")){
			isValid = queryParamJSON.getBooleanValue("isValid");
		}


		if(isValid) {
			//查询有效的
			if(isHql) {
				sql.append(" and basePosition.startDate<=:startDate and (basePosition.endDate is null or basePosition.endDate>=:endDate) and basePosition.enabled=:enabledTrue");
			}else {
				sql.append(" and basePosition.start_date<=:startDate and (basePosition.end_date is null or basePosition.end_date>=:endDate) and basePosition.enabled=:enabledTrue");
			}
			paramsMap.put("startDate", new Date());
			paramsMap.put("endDate", new Date());
			paramsMap.put("enabledTrue", CommonConstants.ENABLED_TRUE);
		}else {
			SaafToolUtils.parperParam(queryParamJSON, "basePosition.enabled", "enabled", sql, paramsMap, "=",isHql);
			SaafToolUtils.parperParam(queryParamJSON, "basePosition.start_date", "startDate", sql, paramsMap, ">=",isHql);
			SaafToolUtils.parperParam(queryParamJSON, "basePosition.end_date", "endDate", sql, paramsMap, "<=",isHql);
		}
	}
	/**
	 * 查询员工职位
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param personId
	 *            人员Id
	 * @return 员工职位列表 <br>
	 *         [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }]
	 */
	@Override
	public List<BasePositionPerson_HI_RO> findBasePositionsByPersonId(Integer personId) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("personId", personId);
		Pagination<BasePositionPerson_HI_RO> findList = this.findBasePositionsJoinPersonOrg(queryParamJSON,1,100);
		return findList.getData();
	}
	/**
	 * 查询部门下设置的职位
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param orgId
	 *            组织机构Id
	 * @return 部门下设置的职位列表 <br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 */
	@Override
	public Pagination<BasePositionOrg_HI_RO> findBasePositionsByOrgId(Integer orgId,Integer pageIndex,Integer pageRows) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("orgId", orgId);
		StringBuffer sb = new StringBuffer(BasePositionOrg_HI_RO.QUERY_POSITION_BY_ORGID);
		Pagination<BasePositionOrg_HI_RO> findList = basePositionOrgDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb),queryParamJSON,pageIndex,pageRows);
		return findList;
	}
	/**
	 * 关联base_person_organization分页查询职位
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            personId:人员Id,<br>
	 *            orgId:组织Id,<br>
	 *            positionName:职位名称,<br>
	 *            sourceSystemId:源系统Id,<br>
	 *            enabled:是否启用,<br>
	 *            startDate:生效时间,<br>
	 *            endDate:失效时间<br>
	 *            }<br>
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 根据条件查询的职位列表 <br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 */
	@Override
	public Pagination<BasePositionPerson_HI_RO> findBasePositionsJoinPersonOrg(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(BasePositionPerson_HI_RO.QUERY_JOIN_PERSON_ORGANIZATION_SQL);
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		changeQuerySql(queryParamJSON, paramsMap, sql,false);
		if(queryParamJSON.containsKey("personId")) {
			sql.append(" and basePersonOrganization.person_id=:personId");
			paramsMap.put("personId", queryParamJSON.getIntValue("personId"));
		}
		if(queryParamJSON.containsKey("orgId")) {
			sql.append(" and basePersonOrganization.org_id=:orgId");
			paramsMap.put("orgId", queryParamJSON.getIntValue("orgId"));
		}
		SaafToolUtils.changeQuerySort(queryParamJSON, sql,"basePosition.position_id",false);
		
		Pagination<BasePositionPerson_HI_RO> findList = basePositionPersonDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		
		return findList;
	}

}
