package com.sie.watsons.base.exclusive.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSoleNonStandarHeaderEntity_HI Entity Object
 * Mon Apr 20 15:57:29 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_SOLE_NON_STANDAR_HEADER")
public class TtaSoleNonStandarHeaderEntity_HI {
    private Integer soleNonStandarHeaderId;
    private String orderNo;
    private Integer orderVersion;
    private String vendorCode;
    private String vendorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date fileId;
    private String fileUrl;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String status;
    private String soleResouceType;
    private String soleProductType;
    private String isIncludeEc;
    private String isIncludeSpecial;
    private String orgCode;
    private String deptCode;
	private String orgName;
	private String deptName;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer resourceId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date approveDate;
	private String isSkipApprove; //是否自动跳过GM审批,Y是跳过,N不跳过

	public void setSoleNonStandarHeaderId(Integer soleNonStandarHeaderId) {
		this.soleNonStandarHeaderId = soleNonStandarHeaderId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SOLE_NON_STANDAR_HEADER", sequenceName="SEQ_TTA_SOLE_NON_STANDAR_HEADER", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SOLE_NON_STANDAR_HEADER",strategy=GenerationType.SEQUENCE)
	@Column(name="sole_non_standar_header_id", nullable=false, length=22)	
	public Integer getSoleNonStandarHeaderId() {
		return soleNonStandarHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=false, length=50)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	@Column(name="order_version", nullable=true, length=22)	
	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=false, length=50)	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=false, length=500)	
	public String getVendorName() {
		return vendorName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="start_time", nullable=true, length=7)	
	public Date getStartTime() {
		return startTime;
	}

	public void setFileId(Date fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=true, length=7)	
	public Date getFileId() {
		return fileId;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name="file_url", nullable=true, length=800)	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="end_time", nullable=true, length=7)
	public Date getEndTime() {
		return endTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)	
	public String getStatus() {
		return status;
	}

	public void setSoleResouceType(String soleResouceType) {
		this.soleResouceType = soleResouceType;
	}

	@Column(name="sole_resouce_type", nullable=true, length=2)	
	public String getSoleResouceType() {
		return soleResouceType;
	}

	public void setSoleProductType(String soleProductType) {
		this.soleProductType = soleProductType;
	}

	@Column(name="sole_product_type", nullable=true, length=2)	
	public String getSoleProductType() {
		return soleProductType;
	}

	public void setIsIncludeEc(String isIncludeEc) {
		this.isIncludeEc = isIncludeEc;
	}

	@Column(name="is_include_ec", nullable=true, length=2)	
	public String getIsIncludeEc() {
		return isIncludeEc;
	}

	public void setIsIncludeSpecial(String isIncludeSpecial) {
		this.isIncludeSpecial = isIncludeSpecial;
	}

	@Column(name="is_include_special", nullable=true, length=2)	
	public String getIsIncludeSpecial() {
		return isIncludeSpecial;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_code", nullable=true, length=50)	
	public String getOrgCode() {
		return orgCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)	
	public String getDeptCode() {
		return deptCode;
	}

	@Column(name="org_name", nullable=true, length=500)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="dept_name", nullable=true, length=500)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="approve_date", nullable=true, length=7)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name="resource_id", nullable=true, length=22)
	public Integer getResourceId() {
		return resourceId;
	}

	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
	}

	@Column(name="is_skip_approve", nullable=true, length=2)
	public String getIsSkipApprove() {
		return isSkipApprove;
	}
}
