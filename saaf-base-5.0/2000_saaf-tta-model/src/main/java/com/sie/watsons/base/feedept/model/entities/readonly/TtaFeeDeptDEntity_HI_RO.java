package com.sie.watsons.base.feedept.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFeeDeptDEntity_HI_RO Entity Object
 * Wed May 29 11:24:38 CST 2019  Auto Generate
 */

public class TtaFeeDeptDEntity_HI_RO {
    private Integer feedeptDetailId;
    private Integer feedeptId;
    private Integer feedeptLineId;
    private String deptCode;
    private String deptDesc;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String unit;
    private Integer standardValue;
    private String remark;
    private String unitName;
    private Integer operatorUserId;

	public static final String TTA_ITEM_V = "select * from TTA_FEE_DEPT_D_V V where 1=1";

	public void setFeedeptDetailId(Integer feedeptDetailId) {
		this.feedeptDetailId = feedeptDetailId;
	}

	
	public Integer getFeedeptDetailId() {
		return feedeptDetailId;
	}

	public void setFeedeptId(Integer feedeptId) {
		this.feedeptId = feedeptId;
	}

	
	public Integer getFeedeptId() {
		return feedeptId;
	}

	public void setFeedeptLineId(Integer feedeptLineId) {
		this.feedeptLineId = feedeptLineId;
	}

	
	public Integer getFeedeptLineId() {
		return feedeptLineId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
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

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public void setStandardValue(Integer standardValue) {
		this.standardValue = standardValue;
	}

	
	public Integer getStandardValue() {
		return standardValue;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
