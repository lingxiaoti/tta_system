package com.sie.watsons.base.exclusive.model.entities;
	
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSoleAgrtEntity_HI Entity Object
 * Tue Jun 25 10:44:35 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SOLE_AGRT")
public class TtaSoleAgrtEntity_HI {
    private Integer soleAgrtHId;//主键
    private String soleAgrtCode;//单据编号
    private String status;//单据状态
    private String soleAgrtVersion;//协议版本号
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date applyDate;//申请日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;//独家协议开始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;//独家协议结束日期
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    private String agrtType;//独家协议类型 standard 标准
    private String vendorCode;
    private String vendorName;

	private String orgCode;//大部门
	private String orgName;
	private String deptCode;//小部门
	private String deptName;
	@JSONField(format ="yyyy-MM-dd HH:mm:ss")
	private Date contractTerminationTime;
	@JSONField(format ="yyyy-MM-dd HH:mm:ss")
	private Date 	approveDate;
	private Integer		alertBy;
	@JSONField(format ="yyyy-MM-dd HH:mm:ss")
	private Date alertDate;
	private String isChange;
	private String versionStatus;
	private String isSkipApprove; //是否自动跳过GM审批,Y是跳过,N不跳过
	private String ownerCompany;//	屈臣氏甲方名称

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date bicRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date financeRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date legaRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date pigeonholeDate;//归档日期

	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
	}

	@Column(name="is_skip_approve", nullable=true, length=2)
	public String getIsSkipApprove() {
		return isSkipApprove;
	}


	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}
	
	@Id
	@SequenceGenerator(name="SEQ_TTA_SOLE_AGRT", sequenceName="SEQ_TTA_SOLE_AGRT", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SOLE_AGRT",strategy=GenerationType.SEQUENCE)
	@Column(name="sole_agrt_h_id", nullable=false, length=22)	
	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setSoleAgrtCode(String soleAgrtCode) {
		this.soleAgrtCode = soleAgrtCode;
	}

	@Column(name="sole_agrt_code", nullable=true, length=50)	
	public String getSoleAgrtCode() {
		return soleAgrtCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=50)	
	public String getStatus() {
		return status;
	}

	public void setSoleAgrtVersion(String soleAgrtVersion) {
		this.soleAgrtVersion = soleAgrtVersion;
	}

	@Column(name="sole_agrt_version", nullable=true, length=50)	
	public String getSoleAgrtVersion() {
		return soleAgrtVersion;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name="apply_date", nullable=true, length=7)	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=true, length=7)	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=7)	
	public Date getEndDate() {
		return endDate;
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

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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

	@Column(name="vendor_code", nullable=true, length=100)
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_name", nullable=true, length=500)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="agrt_type", nullable=true, length=2)
	public String getAgrtType() {
		return agrtType;
	}

	public void setAgrtType(String agrtType) {
		this.agrtType = agrtType;
	}

	@Column(name="org_code", nullable=true, length=30)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_name", nullable=true, length=80)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="dept_code", nullable=true, length=30)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_name", nullable=true, length=80)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "contract_termination_time",nullable = true,length = 7)
	public Date getContractTerminationTime() {
		return contractTerminationTime;
	}

	public void setContractTerminationTime(Date contractTerminationTime) {
		this.contractTerminationTime = contractTerminationTime;
	}

	@Column(name = "approve_date",nullable = true,length = 7)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name = "alert_by",nullable = true,length = 22)
	public Integer getAlertBy() {
		return alertBy;
	}

	public void setAlertBy(Integer alertBy) {
		this.alertBy = alertBy;
	}

	@Column(name = "alert_date",nullable = true,length = 7)
	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	@Column(name = "is_change",nullable = true,length = 10)
	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	@Column(name = "version_status",nullable = true,length = 10)
	public String getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(String versionStatus) {
		this.versionStatus = versionStatus;
	}

	@Column(name = "owner_company",nullable = true,length = 50)
	public String getOwnerCompany() {
		return ownerCompany;
	}

	public void setOwnerCompany(String ownerCompany) {
		this.ownerCompany = ownerCompany;
	}

	@Column(name = "bic_register",nullable = true,length = 7)
	public Date getBicRegister() {
		return bicRegister;
	}

	public void setBicRegister(Date bicRegister) {
		this.bicRegister = bicRegister;
	}

	@Column(name = "finance_register",nullable = true,length = 7)
	public Date getFinanceRegister() {
		return financeRegister;
	}

	public void setFinanceRegister(Date financeRegister) {
		this.financeRegister = financeRegister;
	}

	@Column(name = "lega_register",nullable = true,length = 7)
	public Date getLegaRegister() {
		return legaRegister;
	}

	public void setLegaRegister(Date legaRegister) {
		this.legaRegister = legaRegister;
	}

	@Column(name = "pigeonhole_date",nullable = true,length = 7)
	public Date getPigeonholeDate() {
		return pigeonholeDate;
	}

	public void setPigeonholeDate(Date pigeonholeDate) {
		this.pigeonholeDate = pigeonholeDate;
	}
}
