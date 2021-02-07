package com.sie.saaf.common.bean;

/**
 * OU组织信息
 * @author ZhangJun
 * @createTime 2018-04-17 09:44
 * @description
 */
public class OrgBean {
	private Integer orgId;
	private String orgName;
	private String orgDescription;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDescription() {
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}
}
