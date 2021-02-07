package com.sie.saaf.base.orgStructure.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseDepartmentHierarchyEntity_HI_RO Entity Object
 * Mon Aug 13 09:43:58 CST 2018  Auto Generate
 */

public class BaseDepartmentHierarchyEntity_HI_RO {
	//获取部门表的所有OU_ID
	public static final String QUERY_OU_GROUP_BY_OU_ID_SQL = "SELECT department.ou_id ouId\n" +
			"  FROM base_department department\n" +
			" GROUP BY department.ou_id\n" +
			" ORDER BY department.ou_id";

	//获取部门表的所有层级(倒序)
	public static final String QUERY_LEVEL_GROUP_BY_LEVEL_SQL = "SELECT department.department_level departmentLevel\n" +
			"  FROM base_department department\n" +
			" WHERE 1 = 1\n" +
			"\t AND department.ou_id = :ouId\n" +
			" GROUP BY department.department_level\n" +
			"\t\t\t\t ,department.ou_id\n" +
			" ORDER BY department.department_level DESC";

	//获取需要同步的部门层级关系数据 -- 0级
	public static final String QUERY_DEPT_INFO_BY_OU_SQL = "SELECT department.ou_id ouId\n" +
			"\t\t\t,department.department_id departmentId\n" +
			"\t\t\t,department.department_id subordinateDepartmentId\n" +
			"\t\t\t,0 levelDifference\n" +
			"\t\t\t,0 deleteFlag\n" +
			"\t\t\t,1 operatorUserId\n" +
			"  FROM base_department department\n" +
			" WHERE 1 = 1\n" +
			"\t AND department.ou_id = :ouId\n" +
			" ORDER BY department.department_level\n" +
			"\t\t\t\t,department.parent_department_id\n" +
			"\t\t\t\t,department.department_seq";

	//获取需要同步的部门层级关系数据 -- 非0级
	public static final String QUERY_DEPT_INFO_BY_OU_AND_LEVEL = "SELECT departmentHierarchy.ou_id ouId\n" +
			"\t\t\t,department.parent_department_id departmentId\n" +
			"\t\t\t,departmentHierarchy.subordinate_department_id subordinateDepartmentId\n" +
			"\t\t\t,(departmentHierarchy.level_difference + 1) levelDifference\n" +
			"\t\t\t,0 deleteFlag\n" +
			"\t\t\t,1 operatorUserId\n" +
			"\tFROM base_department department\n" +
			"\t\t\t,base_department_hierarchy departmentHierarchy\n" +
			" WHERE 1 = 1\n" +
			"\t AND department.ou_id = :ouId\n" +
			"\t AND department.department_level = :departmentLevel\n" +
			"\t AND department.department_id = departmentHierarchy.department_id";

    private Integer seqId; //主键ID
    private Integer ouId; //事业部
    private Integer departmentId; //当前部门ID
    private Integer subordinateDepartmentId; //下级部门ID
	private Integer departmentLevel; //部门层级
    private Integer levelDifference; //部门层级差异
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新时间
    private Integer lastUpdatedBy; //更新人
    private Integer lastUpdateLogin; //最后登录ID
    private Integer deleteFlag; //删除标识
    private Integer versionNum; //版本号
    private Integer operatorUserId;

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	
	public Integer getSeqId() {
		return seqId;
	}

	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}

	
	public Integer getOuId() {
		return ouId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setSubordinateDepartmentId(Integer subordinateDepartmentId) {
		this.subordinateDepartmentId = subordinateDepartmentId;
	}

	
	public Integer getSubordinateDepartmentId() {
		return subordinateDepartmentId;
	}

	public Integer getDepartmentLevel() {
		return departmentLevel;
	}

	public void setDepartmentLevel(Integer departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	public void setLevelDifference(Integer levelDifference) {
		this.levelDifference = levelDifference;
	}

	
	public Integer getLevelDifference() {
		return levelDifference;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
