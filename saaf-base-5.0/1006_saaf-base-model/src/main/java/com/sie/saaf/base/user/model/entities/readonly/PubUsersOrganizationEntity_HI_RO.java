package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * PubUsersOrganizationEntity_HI_RO Entity Object
 * Fri Oct 26 11:12:47 CST 2018  Auto Generate
 */

public class PubUsersOrganizationEntity_HI_RO {

	/*查询用户*/
	public static final String QUERY_LIST="SELECT DISTINCT\n"+
			"\tborg.org_id AS orgId,\n"+
			"\tborg.parent_org_id AS parentOrgId,\n"+
			"\tborg.org_id AS menuId,\n"+
			"\tborg.parent_org_id AS menuParentId,\n"+
			"\tborg.org_code AS orgCode,\n"+
			"\tborg.org_name AS orgName\n" +
			"FROM\n"+
			"\tbase_organization AS borg\n"+
			"LEFT JOIN\n"+
			"\tpub_users_organization AS bporg ON borg.org_id=bporg.org_id\n"+
			"WHERE\n"+
			"\t1 = 1\n";

	public static final String QUERY_DELETE_LIST="SELECT\n"+
			"\tbporg.user_org_id AS userOrgId\n"+
			"FROM\n"+
			"\tbase_organization AS borg\n"+
			"LEFT JOIN\n"+
			"\tpub_users_organization AS bporg ON borg.org_id=bporg.org_id\n"+
			"WHERE\n"+
			"\t1 = 1\n";
    private Integer userOrgId; //主键Id
    private Integer orgId;
    private Integer userId; //用户ID
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate; //生效日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate; //失效日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	private Integer parentOrgId; // 父机构Id
	private String orgCode; // 组织机构编码
	private String orgName; // 组织机构名称

	private Integer menuId; // 父机构Id

	public Integer getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(Integer menuParentId) {
		this.menuParentId = menuParentId;
	}

	private Integer menuParentId; // 父机构Id


	public void setUserOrgId(Integer userOrgId) {
		this.userOrgId = userOrgId;
	}

	
	public Integer getUserOrgId() {
		return userOrgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	
	public Integer getOrgId() {
		return orgId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public Integer getUserId() {
		return userId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
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

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
