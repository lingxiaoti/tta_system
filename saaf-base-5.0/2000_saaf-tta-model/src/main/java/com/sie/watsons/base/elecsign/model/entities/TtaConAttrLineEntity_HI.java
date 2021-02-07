package com.sie.watsons.base.elecsign.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaProposalConAttrLineEntity_HI Entity Object
 * Mon Mar 30 17:14:25 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_CON_ATTR_LINE")
public class TtaConAttrLineEntity_HI {
    private Integer conAttrLineId;
    private Integer fileId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    //private String status;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer conYear;//合同年份
	private String vendorCode;//供应商编码
	private Integer orderVersion;//合同附件版本号
	private String orderNo;
	private String orgCode;
	private String deptCode;
	private String orgName;
	private String deptName;

	public void setConAttrLineId(Integer conAttrLineId) {
		this.conAttrLineId = conAttrLineId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_CON_ATTR_LINE", sequenceName="SEQ_TTA_CON_ATTR_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_CON_ATTR_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="con_attr_line_id", nullable=false, length=22)
	public Integer getConAttrLineId() {
		return conAttrLineId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=false, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=500)	
	public String getFileName() {
		return fileName;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name="file_url", nullable=true, length=800)	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=false, length=3)	
	public String getFileType() {
		return fileType;
	}

	//public void setStatus(String status) {
	//	this.status = status;
	//}

//	@Column(name="status", nullable=false, length=2)
//	public String getStatus() {
//		return status;
//	}

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

	@Column(name="con_year", nullable=true, length=22)
	public Integer getConYear() {
		return conYear;
	}

	public void setConYear(Integer conYear) {
		this.conYear = conYear;
	}

	@Column(name="vendor_code", nullable=true)
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="order_version", nullable=true)
	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	@Column(name="order_no", nullable=true)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
}
