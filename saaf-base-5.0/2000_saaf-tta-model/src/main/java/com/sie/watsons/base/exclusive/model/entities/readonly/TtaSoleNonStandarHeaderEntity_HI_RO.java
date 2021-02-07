package com.sie.watsons.base.exclusive.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSoleNonStandarHeaderEntity_HI_RO Entity Object
 * Mon Apr 20 15:57:29 CST 2020  Auto Generate
 */

public class TtaSoleNonStandarHeaderEntity_HI_RO {

	public static final String TTA_ITEM_V = "       select tsnsh.sole_non_standar_header_id,\n" +
			"              tsnsh.order_no,\n" +
			"              tsnsh.order_version,\n" +
			"              tsnsh.vendor_code,\n" +
			"              tsnsh.vendor_name,\n" +
			"              tsnsh.start_time,\n" +
			"              tsnsh.file_id,\n" +
			"              tsnsh.file_url,\n" +
			"              tsnsh.end_time,\n" +
			"              tsnsh.status,\n" +
			"              look_status.meaning status_name,\n" +
			"              tsnsh.sole_resouce_type,\n" +
			"              tsnsh.sole_product_type,\n" +
			"              tsnsh.is_include_ec,\n" +
			"              tsnsh.is_include_special,\n" +
			"              tsnsh.org_code,\n" +
			"              tsnsh.dept_code,\n" +
			"              tsnsh.version_num,\n" +
			"              tsnsh.creation_date,\n" +
			"              tsnsh.created_by,\n" +
			"              bu.user_full_name created_by_name,\n" +
			"              tsnsh.last_updated_by,\n" +
			"              tsnsh.last_update_date,\n" +
			"              tsnsh.is_skip_approve,\n" +
			"              tsnsh.last_update_login,\n" +
			"              tsnsh.dept_name\n" +
			"              \n" +
			"               from tta_sole_non_standar_header tsnsh\n" +
			"               left join (select blv.lookup_code,blv.meaning from  base_lookup_values blv where blv.lookup_type = 'TTA_STD_APPLY_HEADER_STATUS') look_status \n" +
			"               on look_status.lookup_code = tsnsh.status\n" +
			"               left join base_users bu on tsnsh.created_by = bu.user_id \n" +
			"               where 1=1 ";

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
	private String statusName;
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
	private String createdByName;
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

	
	public Integer getSoleNonStandarHeaderId() {
		return soleNonStandarHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	
	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	
	public Date getStartTime() {
		return startTime;
	}

	public void setFileId(Date fileId) {
		this.fileId = fileId;
	}

	
	public Date getFileId() {
		return fileId;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	public Date getEndTime() {
		return endTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setSoleResouceType(String soleResouceType) {
		this.soleResouceType = soleResouceType;
	}

	
	public String getSoleResouceType() {
		return soleResouceType;
	}

	public void setSoleProductType(String soleProductType) {
		this.soleProductType = soleProductType;
	}

	
	public String getSoleProductType() {
		return soleProductType;
	}

	public void setIsIncludeEc(String isIncludeEc) {
		this.isIncludeEc = isIncludeEc;
	}

	
	public String getIsIncludeEc() {
		return isIncludeEc;
	}

	public void setIsIncludeSpecial(String isIncludeSpecial) {
		this.isIncludeSpecial = isIncludeSpecial;
	}

	
	public String getIsIncludeSpecial() {
		return isIncludeSpecial;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getOrgCode() {
		return orgCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getIsSkipApprove() {
		return isSkipApprove;
	}

	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
	}
}
