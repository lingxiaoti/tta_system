package com.sie.watsons.base.feedept.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFeeDeptHEntity_HI_RO Entity Object
 * Wed May 29 11:24:37 CST 2019  Auto Generate
 */

public class TtaFeeDeptHEntity_HI_RO {
    private Integer feedeptId;
    private String yearCode;
    private String accordType;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
    private Integer sourceFeeId;
    private String isAlert;
    private String versionCode;
    private String remark;
	private String lastUpdatedName;
	private String createdName;
	private String statusName;
	private String accordTypeName;
	private String deptCode;
	private String deptDesc;
	private Integer deptId;
	private Integer counts;
    private Integer operatorUserId;
	public static final String TTA_ITEM_V = "select * from TTA_FEE_DEPT_H_V V where 1=1";

	public static final String TTA_DEPT_COUNT = "select count(1) counts from \n" +
			"tta_fee_dept_header tfh,\n" +
			"tta_fee_dept_detail tfd,\n" +
			"base_department bd\n" +
			"where \n" +
			"tfh.feedept_id = tfd.feedept_id\n" +
			"and tfd.dept_code = bd.department_code\n" +
			"and bd.parent_department_id <>tfh.dept_id";


	public void setFeedeptId(Integer feedeptId) {
		this.feedeptId = feedeptId;
	}

	
	public Integer getFeedeptId() {
		return feedeptId;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	
	public String getYearCode() {
		return yearCode;
	}

	public void setAccordType(String accordType) {
		this.accordType = accordType;
	}

	
	public String getAccordType() {
		return accordType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	
	public Date getApproveDate() {
		return approveDate;
	}

	public void setSourceFeeId(Integer sourceFeeId) {
		this.sourceFeeId = sourceFeeId;
	}

	
	public Integer getSourceFeeId() {
		return sourceFeeId;
	}

	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}

	
	public String getIsAlert() {
		return isAlert;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	
	public String getVersionCode() {
		return versionCode;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public String getLastUpdatedName() {
		return lastUpdatedName;
	}

	public void setLastUpdatedName(String lastUpdatedName) {
		this.lastUpdatedName = lastUpdatedName;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAccordTypeName() {
		return accordTypeName;
	}

	public void setAccordTypeName(String accordTypeName) {
		this.accordTypeName = accordTypeName;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}
}
