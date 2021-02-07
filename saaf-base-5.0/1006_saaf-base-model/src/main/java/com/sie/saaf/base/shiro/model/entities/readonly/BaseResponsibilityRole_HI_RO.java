package com.sie.saaf.base.shiro.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author ZhangJun
 * @creteTime 2017-12-13
 */
public class BaseResponsibilityRole_HI_RO {

	public static final String QUERY_RESP_ROLE_SQL = "SELECT\n" +
			"\tbaseResponsibility.responsibility_id AS responsibilityId ,\n" +
			"\tbaseResponsibility.responsibility_code AS responsibilityCode ,\n" +
			"\tbaseResponsibility.responsibility_name AS responsibilityName ,\n" +
			"\tbaseResponsibility.responsibility_desc AS responsibilityDesc ,\n" +
			"\tbaseResponsibility.start_date_active AS startDateActive ,\n" +
			"\tbaseResponsibility.end_date_active AS endDateActive ,\n" +
			"\tbaseResponsibility.version_num AS versionNum ,\n" +
			"\tbaseResponsibility.creation_date AS creationDate ,\n" +
			"\tbaseResponsibility.created_by AS createdBy ,\n" +
			"\tbaseResponsibility.last_updated_by AS lastUpdatedBy ,\n" +
			"\tbaseResponsibility.last_update_date AS lastUpdateDate ,\n" +
			"\tbaseRole.role_id AS roleId ,\n" +
			"\tbaseRole.role_name AS roleName ,\n" +
			"\tbaseRole.role_code AS roleCode ,\n" +
			"\tbaseRole.role_desc AS roleDesc ,\n" +
			"\tbaseRole.system_code AS systemCode ,\n" +
			"\tbaseRole.start_date_active AS roleStartDateActive ,\n" +
			"\tbaseRole.end_date_active AS roleEndDateActive ,\n" +
			"\tbaseRole.version_num AS roleVersionNum\n" +
			"FROM\n" +
			"\tbase_responsibility  baseResponsibility ,\n" +
			"\tbase_responsibility_role  baseResponsibilityRole ,\n" +
			"\tbase_role AS baseRole\n" +
			"WHERE\n" +
			"\tbaseResponsibility.responsibility_id = baseResponsibilityRole.responsibility_id\n" +
			"AND baseResponsibilityRole.role_id = baseRole.role_id\n\t";

	public static final String QUERY_ROLE_RESPONSIBILITY_SQL = "select " +
			"   baseResponsibilityRole.resp_role_id as respRoleId," +
			"   baseResponsibilityRole.responsibility_id as responsibilityId," +
			"   baseResponsibilityRole.role_id as roleId," +
			"   baseResponsibilityRole.version_num as versionNum," +
			"   baseRole.role_name as roleName," +
			"   baseRole.role_code as roleCode," +
			"   baseRole.role_desc as roleDesc," +
			"   baseRole.system_code as roleCode " +
			" from " +
			"   base_responsibility_role  baseResponsibilityRole, " +
			"   base_role  baseRole " +
			" where " +
			"   baseResponsibilityRole.role_id = baseRole.role_id " +
			"   and baseRole.start_date_active < sysdate and (baseRole.end_date_active is null or baseRole.end_date_active>=sysdate)";

	private Integer responsibilityId; //职责标识
	private String responsibilityCode; //职责编号
	private String responsibilityName; //职责名称
	private String responsibilityDesc; //职责描述
	@JSONField(format = "yyyy-MM-dd")
	private Date startDateActive; //生效时间
	@JSONField(format = "yyyy-MM-dd")
	private Date endDateActive; //失效时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建日期
	private Integer createdBy; //创建人
	private Integer lastUpdatedBy; //更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新日期
	private Integer versionNum; //版本号

	private Integer respRoleId;//职责角色Id

	private Integer roleId; //角色Id
	private String roleName; //角色名称
	private String roleCode; //角色编号
	private String roleDesc; //角色描述
	private String systemCode; //系统编码
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date roleStartDateActive; //生效时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date roleEndDateActive; //失效时间
	private Integer roleVersionNum; //版本号

	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	public String getResponsibilityCode() {
		return responsibilityCode;
	}

	public void setResponsibilityCode(String responsibilityCode) {
		this.responsibilityCode = responsibilityCode;
	}

	public String getResponsibilityName() {
		return responsibilityName;
	}

	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}

	public String getResponsibilityDesc() {
		return responsibilityDesc;
	}

	public void setResponsibilityDesc(String responsibilityDesc) {
		this.responsibilityDesc = responsibilityDesc;
	}

	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public Date getRoleStartDateActive() {
		return roleStartDateActive;
	}

	public void setRoleStartDateActive(Date roleStartDateActive) {
		this.roleStartDateActive = roleStartDateActive;
	}

	public Date getRoleEndDateActive() {
		return roleEndDateActive;
	}

	public void setRoleEndDateActive(Date roleEndDateActive) {
		this.roleEndDateActive = roleEndDateActive;
	}

	public Integer getRoleVersionNum() {
		return roleVersionNum;
	}

	public void setRoleVersionNum(Integer roleVersionNum) {
		this.roleVersionNum = roleVersionNum;
	}

	public Integer getRespRoleId() {
		return respRoleId;
	}

	public void setRespRoleId(Integer respRoleId) {
		this.respRoleId = respRoleId;
	}
}
