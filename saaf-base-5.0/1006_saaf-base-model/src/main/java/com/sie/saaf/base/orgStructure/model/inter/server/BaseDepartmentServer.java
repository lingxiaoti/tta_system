package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseDepartment;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Component("baseDepartmentServer")
public class BaseDepartmentServer extends BaseCommonServer<BaseDepartmentEntity_HI> implements IBaseDepartment {
	@Autowired
	private ViewObject<BaseDepartmentEntity_HI> baseDepartmentDAO_HI;
	@Autowired
	private BaseViewObject<BaseDepartmentEntity_HI_RO> baseDepartmentDAO_HI_RO;
	@Autowired
	private GenerateCodeServer generateCodeServer;
//	@Autowired
//	private OracleTemplateServer oracleTemplateServer;

	public BaseDepartmentServer() {
		super();
	}

	/**
	 * 部门树
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public List<BaseDepartmentEntity_HI_RO> findDeptTreeList(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer(BaseDepartmentEntity_HI_RO.QUERY_DEPT_TREE_LIST);
		getUnifiedParams(queryParamJSON, querySql, queryParamMap);
		querySql.append(" ORDER BY department.ou_id\n" +
				"\t\t\t\t ,department.department_level\n" +
				"\t\t\t\t ,department.parent_department_id\n" +
				"\t\t\t\t ,department.department_seq");
		return baseDepartmentDAO_HI_RO.findList(querySql, queryParamMap);
	}

	/**
	 * 部门（分页）
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public Pagination<BaseDepartmentEntity_HI_RO> findDeptPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer();
		if("findDept".equals(queryParamJSON.getString("flag"))){
			querySql.append(BaseDepartmentEntity_HI_RO.SELECT_DEPT_LIST);
			SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "bd.department_type", "departmentType", querySql, queryParamMap, "=");
			SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "bd.parent_department_id", "parentDepartmentId", querySql, queryParamMap, "=");
			SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "bd.enable_flag", "enableFlag", querySql, queryParamMap, "=");
			SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "bd.department_code", "departmentCode", querySql, queryParamMap, "like");
			SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "bd.department_name", "departmentName", querySql, queryParamMap, "like");
			querySql.append(" order by bd.department_seq asc, bd.department_id asc");
		}else{
			querySql.append(BaseDepartmentEntity_HI_RO.QUERY_DEPT_PAGINATION_SQL);
			getUnifiedParams(queryParamJSON, querySql, queryParamMap);
			if (StringUtils.isNotBlank(queryParamJSON.getString("effectiveDate"))) {
				querySql.append(" AND department.date_From <= :dateFrom \n");
				queryParamMap.put("dateFrom", SaafDateUtils.string2UtilDate(queryParamJSON.getString("effectiveDate"), "yyyy-MM-dd"));
			}
			if (StringUtils.isNotBlank(queryParamJSON.getString("effectiveDate"))) {
				querySql.append(" AND department.date_to >= :dateTo \n");
				queryParamMap.put("dateTo", SaafDateUtils.string2UtilDate(queryParamJSON.getString("effectiveDate"), "yyyy-MM-dd"));
			}
			SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.department_type", "departmentType", querySql, queryParamMap, "=");
			SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "parentDepartment.department_name", "parentDepartmentName", querySql, queryParamMap, "fulllike");
			querySql.append(" ORDER BY department.ou_id\n" +
					"\t\t\t\t ,department.department_level\n" +
					"\t\t\t\t ,department.parent_department_id\n" +
					"\t\t\t\t ,department.department_seq");
		}



		return baseDepartmentDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql) ,queryParamMap, pageIndex, pageRows);
	}

	private void getUnifiedParams(JSONObject queryParamJSON, StringBuffer querySql, Map<String, Object> queryParamMap) {
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.ou_id", "ouId", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.department_id", "departmentId", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.department_code", "departmentCode", querySql, queryParamMap, "fulllike");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.department_name", "departmentName", querySql, queryParamMap, "fulllike");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.department_level", "departmentLevel", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.parent_department_id", "parentDepartmentId", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.biz_line_type", "bizLineType", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.enable_flag", "enableFlag", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "department.channel", "channel", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "channel.CHANNEL_NAME", "channelName", querySql, queryParamMap, "fulllike");
	}

	/**
	 * 根据部门类型和上级部门查找部门
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<BaseDepartmentEntity_HI_RO> findDeptByGroupIdAndDepartmentType(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer();
		querySql.append(BaseDepartmentEntity_HI_RO.SELECT_DEPT_LIST_BYGROUPID);
		String parentDepartmentId = queryParamJSON.getString("parentDepartmentId");
		if (StringUtils.isBlank(parentDepartmentId)) {
			queryParamMap.put("parentDepartmentId",-1000);
		}else {
			queryParamMap.put("parentDepartmentId",Integer.parseInt(parentDepartmentId));
		}

		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "t.department_code", "departmentCode", querySql, queryParamMap, "like");
		SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "t.department_full_name", "departmentFullName", querySql, queryParamMap, "like");
		querySql.append(" order by t.department_id asc");
		return baseDepartmentDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql) ,queryParamMap, pageIndex, pageRows);
	}

	/**
	 * 新增&修改部门信息
	 *
	 * @param paramsJSON 对象属性的JSON格式
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 * @throws Exception 抛出异常
	 */
	@Override
	public BaseDepartmentEntity_HI saveOrUpdateBaseDepartment(JSONObject paramsJSON, Integer userId) throws Exception{
		BaseDepartmentEntity_HI baseDepartmentEntity = SaafToolUtils.setEntity(BaseDepartmentEntity_HI.class, paramsJSON, baseDepartmentDAO_HI, userId);
		baseDepartmentEntity.setDeleteFlag(0);
		if (StringUtils.isBlank(paramsJSON.getString("departmentId"))) {
			String departmentCodeKey = "base_department_code_" + paramsJSON.getString("ouId");
			int generateId = generateCodeServer.getGenerateId(departmentCodeKey);
			String departmentCode = String.format("%0" + 4 + "d", generateId);
			baseDepartmentEntity.setDepartmentCode(departmentCode);
		}
		baseDepartmentDAO_HI.saveOrUpdate(baseDepartmentEntity);
		return baseDepartmentEntity;
	}

	/**
	 * 查询是否存在相同的部门：同一事业部内部门名称+部门后缀在有效期间内是否重复；
	 * @param queryParamMap
	 * @return
	 */
	@Override
	public Boolean findIsExistRepeatDept(Map<String, Object> queryParamMap) {
		StringBuffer querySql = new StringBuffer(BaseDepartmentEntity_HI_RO.QUERY_IS_EXIST_REPEAT_DEPT);
		List<BaseDepartmentEntity_HI_RO> list = baseDepartmentDAO_HI_RO.findList(querySql, queryParamMap);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询需要同步的部门数据
	 * @param lastUpdateDateStr
	 * @return
	 */
	@Override
	public List<BaseDepartmentEntity_HI_RO> findDepartmentSynList(String lastUpdateDateStr) {
		StringBuffer querySql = new StringBuffer(BaseDepartmentEntity_HI_RO.QUERY_BASE_DEPT_SYNC_SQL);
		querySql.append(" AND department.last_update_date >= '" + lastUpdateDateStr + "'");
		return baseDepartmentDAO_HI_RO.findList(querySql);
	}

	public List<JSONObject> findOracleDeptList(Integer departmentId) throws Exception{
//		try {
//			StringBuffer querySql = new StringBuffer();
//			querySql.append("SELECT DEPARTMENT.DEPARTMENT_ID DEPARTMENT_ID\n" +
//					"      ,DEPARTMENT.DEPARTMENT_NAME DEPARTMENT_NAME\n" +
//					"  FROM BASE.BASE_DEPARTMENT DEPARTMENT\n" +
//					" WHERE 1 = 1\n" +
//					"   AND DEPARTMENT.DEPARTMENT_ID = " + departmentId);
//			LOGGER.info("{}", querySql.toString());
//			return oracleTemplateServer.findList(querySql.toString());
//		} catch (SQLException e) {
//			LOGGER.error("查询Oracle数据源部门信息异常:{}", e);
//			throw new Exception("查询Oracle数据源部门信息异常:", e);
//		}
		return new ArrayList<>();
	}

	/**
	 * 新增&修改Oracle数据的部门信息
	 * @param deptSynInfo
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveOrUpdateOracleDeptInfo(BaseDepartmentEntity_HI_RO deptSynInfo) throws Exception{
		Integer departmentId = deptSynInfo.getDepartmentId();
		List<JSONObject> oracleDeptInfo = findOracleDeptList(departmentId);
		Map<String, Object> saveOrUpdateMap = new LinkedHashMap<>();
		if (oracleDeptInfo == null || oracleDeptInfo.isEmpty() || oracleDeptInfo.size() == 0) {
			//oracle数据源不存在此,插入数据
			saveOrUpdateMap.put("departmentId", departmentId);
			saveOrUpdateMap.put("departmentName", deptSynInfo.getDepartmentName());
			saveOrUpdateMap.put("orgId", deptSynInfo.getOuId());
			saveOrUpdateMap.put("parentDepartmentId", deptSynInfo.getParentDepartmentId());
			saveOrUpdateMap.put("costCenter", deptSynInfo.getCostCenter() == null ? "" : deptSynInfo.getCostCenter());
			saveOrUpdateMap.put("departmentSeq", deptSynInfo.getDepartmentSeq());
			saveOrUpdateMap.put("enabledFlag", deptSynInfo.getEnableFlag());
			saveOrUpdateMap.put("lastUpdateLogin", deptSynInfo.getLastUpdatedBy());
			saveOrUpdateMap.put("lastUpdatedBy", deptSynInfo.getLastUpdatedBy());
			saveOrUpdateMap.put("createdBy", deptSynInfo.getCreatedBy());
			saveOrUpdateMap.put("dateFrom", SaafDateUtils.convertDateToString(deptSynInfo.getDateFrom(), "yyyy-MM-dd"));
			saveOrUpdateMap.put("dateTo", SaafDateUtils.convertDateToString(deptSynInfo.getDateTo(), "yyyy-MM-dd"));
			saveOrUpdateMap.put("departmentLevel", deptSynInfo.getDepartmentLevel());
			saveOrUpdateMap.put("channelType", deptSynInfo.getChannelCode());
			saveOrUpdateMap.put("bizLineType", deptSynInfo.getBizLineType());
			saveOrUpdateMap.put("departmentSuffix", deptSynInfo.getDepartmentSuffix());
			saveOrUpdateMap.put("departmentType", deptSynInfo.getDepartmentType());
//			oracleTemplateServer.executeInsertDoSql(BaseDepartmentEntity_HI_RO.INSERT_DEPARTMENT_SQL, saveOrUpdateMap);
		} else {
			saveOrUpdateMap.put("departmentName", deptSynInfo.getDepartmentName());
			saveOrUpdateMap.put("orgId", deptSynInfo.getOuId());
			saveOrUpdateMap.put("parentDepartmentId", deptSynInfo.getParentDepartmentId());
			saveOrUpdateMap.put("costCenter", deptSynInfo.getCostCenter());
			saveOrUpdateMap.put("departmentSeq", deptSynInfo.getDepartmentSeq());
			saveOrUpdateMap.put("enabledFlag", deptSynInfo.getEnableFlag());
			saveOrUpdateMap.put("lastUpdateLogin", deptSynInfo.getLastUpdatedBy());
			saveOrUpdateMap.put("lastUpdatedBy", deptSynInfo.getLastUpdatedBy());
			saveOrUpdateMap.put("dateFrom", SaafDateUtils.convertDateToString(deptSynInfo.getDateFrom(), "yyyy-MM-dd"));
			saveOrUpdateMap.put("dateTo", SaafDateUtils.convertDateToString(deptSynInfo.getDateTo(), "yyyy-MM-dd"));
			saveOrUpdateMap.put("departmentLevel", deptSynInfo.getDepartmentLevel());
			saveOrUpdateMap.put("channelType", deptSynInfo.getChannelCode());
			saveOrUpdateMap.put("bizLineType", deptSynInfo.getBizLineType());
			saveOrUpdateMap.put("departmentSuffix", deptSynInfo.getDepartmentSuffix());
			saveOrUpdateMap.put("departmentType", deptSynInfo.getDepartmentType());
			saveOrUpdateMap.put("departmentId", departmentId);
//			oracleTemplateServer.executeUpdateSql(BaseDepartmentEntity_HI_RO.UPDATE_DEPARTMENT_SQL, saveOrUpdateMap);
		}

		//更新存储过程
//		StringBuffer updateSql = new StringBuffer(BaseDepartmentEntity_HI_RO.REFRESH_DEPARTMENT_HIERARCHIES);
//		Connection conn = oracleTemplateServer.getDataSource().getConnection();
//		OracleConnection oracleConnection = conn.unwrap(OracleConnection.class);
//		OracleCallableStatement stmt  = (OracleCallableStatement) oracleConnection.prepareCall(updateSql.toString());
//		//1、P_ORG_ID
//		stmt.setInt(1,null !=deptSynInfo.getOuId()?deptSynInfo.getOuId():0);
//		//2、P_CREATED_BY
//		stmt.setInt(2, null !=deptSynInfo.getCreatedBy()?deptSynInfo.getCreatedBy():0);
//		stmt.execute();
	}

	/**
	 * 根据部门id找到顶级部门
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	@Override
	public BaseDepartmentEntity_HI_RO findDeptByRecursion(Integer deptId) throws Exception {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer();
		querySql.append(BaseDepartmentEntity_HI_RO.SELECT_DEPTID_BY_RECIESION);
		queryParamMap.put("departmentId",deptId);

		//SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "t.department_code", "departmentCode", querySql, queryParamMap, "like");
		//SaafToolUtils.parperHbmParam(BaseDepartmentEntity_HI_RO.class, queryParamJSON, "t.department_full_name", "departmentFullName", querySql, queryParamMap, "like");

		return baseDepartmentDAO_HI_RO.get(querySql,queryParamMap);
	}

	/**
	 * 根据用户id查找部门
	 * @param userId
	 * @return
	 */
	@Override
	public BaseDepartmentEntity_HI_RO findDeptList(Integer userId) {
		Map<String,Object> queryParamMap = new HashMap<>();
		queryParamMap.put("userId",userId);
		StringBuffer sql = new StringBuffer();
		sql.append(BaseDepartmentEntity_HI_RO.SELECT_DEPT_LIST_BYUSERID);
		List<BaseDepartmentEntity_HI_RO> list = baseDepartmentDAO_HI_RO.findList(sql, queryParamMap);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}