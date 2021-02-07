package com.sie.watsons.base.supplement.model.entities;

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
 * TtaSaStdHeaderEntity_HI Entity Object
 * Tue Mar 31 15:25:13 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_SA_STD_HEADER")
public class TtaSaStdHeaderEntity_HI {
    private Integer saStdHeaderId;
    private Integer saStdTplDefHeaderId;
    private String saStdCode;//单据号
    private Integer saStdVersion;//版本号,从1开始
    private String saTeyp;//补充类型:standard.标准,nostandard.非标准
    private String vendorCode;//供应商编号
    private String vendorName;//供应商名称
    private Integer tplId;//模板id
    private String tplName;//模板名称
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveStartTime;//合同生效时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveEndTime;//合同失效截止时间
    private String status;//单据状态
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private Date approveDate;//审批通过时间
	private Integer alertBy;//变更人
	private Date alertDate;//变更时间
	private String isChange;//是否变更(Y是,N否)
	private String versionStatus;//版本状态('1'生效,'0'失效)

	private String orgCode;//大部门
	private String orgName;
	private String deptCode;//小部门
	private String deptName;
	private String isSkipApprove; //是否自动跳过GM审批,Y是跳过,N不跳过
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date bicRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date financeRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date legaRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date pigeonholeDate;//归档日期

	public void setSaStdHeaderId(Integer saStdHeaderId) {
		this.saStdHeaderId = saStdHeaderId;
	}

	@Id
	@SequenceGenerator(name="SEQ_SA_STD_HEADER", sequenceName="SEQ_SA_STD_HEADER", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_SA_STD_HEADER",strategy=GenerationType.SEQUENCE)
	@Column(name="sa_std_header_id", nullable=false, length=22)	
	public Integer getSaStdHeaderId() {
		return saStdHeaderId;
	}

	public void setSaStdTplDefHeaderId(Integer saStdTplDefHeaderId) {
		this.saStdTplDefHeaderId = saStdTplDefHeaderId;
	}

	@Column(name="sa_std_tpl_def_header_id", nullable=true, length=22)	
	public Integer getSaStdTplDefHeaderId() {
		return saStdTplDefHeaderId;
	}

	public void setSaStdCode(String saStdCode) {
		this.saStdCode = saStdCode;
	}

	@Column(name="sa_std_code", nullable=true, length=50)	
	public String getSaStdCode() {
		return saStdCode;
	}

	public void setSaStdVersion(Integer saStdVersion) {
		this.saStdVersion = saStdVersion;
	}

	@Column(name="sa_std_version", nullable=true, length=22)	
	public Integer getSaStdVersion() {
		return saStdVersion;
	}

	public void setSaTeyp(String saTeyp) {
		this.saTeyp = saTeyp;
	}

	@Column(name="sa_teyp", nullable=true, length=15)
	public String getSaTeyp() {
		return saTeyp;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=true, length=100)	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=500)	
	public String getVendorName() {
		return vendorName;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	@Column(name="tpl_id", nullable=true, length=22)	
	public Integer getTplId() {
		return tplId;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	@Column(name="tpl_name", nullable=true, length=300)
	public String getTplName() {
		return tplName;
	}

	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}

	@Column(name="effective_start_time", nullable=true, length=7)	
	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	@Column(name="effective_end_time", nullable=true, length=7)	
	public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)	
	public String getStatus() {
		return status;
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

	@Column(name="approve_date", nullable=true, length=7)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name="alert_by", nullable=true, length=22)
	public Integer getAlertBy() {
		return alertBy;
	}

	public void setAlertBy(Integer alertBy) {
		this.alertBy = alertBy;
	}

	@Column(name="alert_date", nullable=true, length=7)
	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	@Column(name="is_change", nullable=true, length=10)
	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	@Column(name="version_status", nullable=true, length=10)
	public String getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(String versionStatus) {
		this.versionStatus = versionStatus;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
	}

	@Column(name="is_skip_approve", nullable=true, length=2)
	public String getIsSkipApprove() {
		return isSkipApprove;
	}

	@Column(name="bic_register", nullable=true, length=7)
	public Date getBicRegister() {
		return bicRegister;
	}

	public void setBicRegister(Date bicRegister) {
		this.bicRegister = bicRegister;
	}

	@Column(name="finance_register", nullable=true, length=7)
	public Date getFinanceRegister() {
		return financeRegister;
	}

	public void setFinanceRegister(Date financeRegister) {
		this.financeRegister = financeRegister;
	}

	@Column(name="lega_register", nullable=true, length=7)
	public Date getLegaRegister() {
		return legaRegister;
	}

	public void setLegaRegister(Date legaRegister) {
		this.legaRegister = legaRegister;
	}

	@Column(name="pigeonhole_date", nullable=true, length=7)
	public Date getPigeonholeDate() {
		return pigeonholeDate;
	}

	public void setPigeonholeDate(Date pigeonholeDate) {
		this.pigeonholeDate = pigeonholeDate;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
