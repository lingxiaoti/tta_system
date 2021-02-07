package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author ZhangJun
 * @createTime 2018-01-19 16:43
 * @description
 */
public class BasePositionOrg_HI_RO {
	public static final String QUERY_POSITION_BY_ORGID = "SELECT\n" +
			"    basePosition.position_id AS positionId ,\n" +
			"    basePosition.org_id AS orgId ,\n" +
			"    basePosition.position_name AS positionName ,\n" +
			"    basePosition.start_date AS startDate ,\n" +
			"    basePosition.end_date AS endDate ,\n" +
			"    basePosition.enabled AS enabled ,\n" +
			"    basePosition.version_num AS versionNum,\n" +
			"    baseOrganization.org_name as orgName\n" +
			"FROM\n" +
			"    base_position AS basePosition ,\n" +
			"    base_organization  baseOrganization\n" +
			"WHERE\n" +
			"    basePosition.org_id = baseOrganization.org_id and basePosition.enabled = 'Y'\n" +
			"    and basePosition.start_date <= sysdate and (basePosition.end_date is null or basePosition.end_date >= sysdate)\n" +
			"    AND basePosition.org_id = :orgId";

	private Integer positionId; // 职位ID
	private Integer orgId; // 组织机构Id
	private String positionName; // 职位名称
	private String sourceSystemId; // 源系统Id
	@JSONField(format = "yyyy-MM-dd")
	private Date startDate; // 启用日期
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate; // 失效日期
	private String enabled; // 是否启用（Y：启用；N：禁用）
	private Integer versionNum; // 版本号

	private String orgName;//组织名称

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getSourceSystemId() {
		return sourceSystemId;
	}

	public void setSourceSystemId(String sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
