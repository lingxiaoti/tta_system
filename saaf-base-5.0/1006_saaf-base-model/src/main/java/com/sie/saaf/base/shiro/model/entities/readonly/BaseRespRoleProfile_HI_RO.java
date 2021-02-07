package com.sie.saaf.base.shiro.model.entities.readonly;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author ZhangJun
 * @createTime 2018-01-12 11:53
 * @description
 */
public class BaseRespRoleProfile_HI_RO {
	private Integer responsibilityId; //职责标识
	private String responsibilityCode; //职责编号
	private String responsibilityName; //职责名称
	private String responsibilityDesc; //职责描述
	@JSONField(format = "yyyy-MM-dd")
	private Date startDateActive; //生效时间
	@JSONField(format = "yyyy-MM-dd")
	private Date endDateActive; //失效时间
	private Integer versionNum; //版本号

	private JSONArray respRoles;

	private JSONArray profileValues;

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

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public JSONArray getRespRoles() {
		return respRoles;
	}

	public void setRespRoles(JSONArray respRoles) {
		this.respRoles = respRoles;
	}

	public JSONArray getProfileValues() {
		return profileValues;
	}

	public void setProfileValues(JSONArray profileValues) {
		this.profileValues = profileValues;
	}
}
