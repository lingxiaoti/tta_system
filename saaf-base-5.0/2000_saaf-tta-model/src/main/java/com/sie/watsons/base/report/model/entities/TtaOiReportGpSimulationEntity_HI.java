package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiReportGpSimulationEntity_HI Entity Object
 * Thu Apr 30 09:53:34 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_oi_report_gp_simulation_line")
public class TtaOiReportGpSimulationEntity_HI {

    private Integer simulationId;
    private String orderNbr;
    private Integer versionCode;
    private String vendorNbr;
    private String vendorName;
    private String groupCode;
    private String groupName;
    private String deptCode;
    private String deptDesc;
    private String brandCn;
    private String brandEn;
    private String majorDeptCode;
    private String majorDeptDesc;
    private String salesType;
    private BigDecimal gp;
    private BigDecimal salesExcludeAmt;
    private String remark;
    private String actionCode;
    private String actionPlan;
    private String targetDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String gpReasonCode;
    private String confirmStatus;
	private Integer confirmUser;//确认人id
	private Date confirmDate;//确认日期
	private BigDecimal propGpAmt;  //预估gp金额
	private BigDecimal propSalesAmt; //预估salse金额

	public void setSimulationId(Integer simulationId) {
		this.simulationId = simulationId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_OI_REPORT_GP_SIMULATION", sequenceName = "SEQ_TTA_OI_REPORT_GP_SIMULATION", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_OI_REPORT_GP_SIMULATION", strategy = GenerationType.SEQUENCE)
	@Column(name="simulation_id", nullable=false, length=22)
	public Integer getSimulationId() {
		return simulationId;
	}

	public void setOrderNbr(String orderNbr) {
		this.orderNbr = orderNbr;
	}

	@Column(name="order_nbr", nullable=true, length=50)	
	public String getOrderNbr() {
		return orderNbr;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	@Column(name="version_code", nullable=true, length=22)	
	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=true, length=50)
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=800)	
	public String getVendorName() {
		return vendorName;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=50)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=800)	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=800)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=800)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=800)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setMajorDeptCode(String majorDeptCode) {
		this.majorDeptCode = majorDeptCode;
	}

	@Column(name="major_dept_code", nullable=true, length=50)	
	public String getMajorDeptCode() {
		return majorDeptCode;
	}

	public void setMajorDeptDesc(String majorDeptDesc) {
		this.majorDeptDesc = majorDeptDesc;
	}

	@Column(name="major_dept_desc", nullable=true, length=800)	
	public String getMajorDeptDesc() {
		return majorDeptDesc;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	@Column(name="sales_type", nullable=true, length=50)	
	public String getSalesType() {
		return salesType;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	@Column(name="gp", nullable=true, length=22)	
	public BigDecimal getGp() {
		return gp;
	}

	public void setSalesExcludeAmt(BigDecimal salesExcludeAmt) {
		this.salesExcludeAmt = salesExcludeAmt;
	}

	@Column(name="sales_exclude_amt", nullable=true, length=22)	
	public BigDecimal getSalesExcludeAmt() {
		return salesExcludeAmt;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2000)	
	public String getRemark() {
		return remark;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	@Column(name="action_code", nullable=true, length=80)	
	public String getActionCode() {
		return actionCode;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	@Column(name="action_plan", nullable=true, length=2000)	
	public String getActionPlan() {
		return actionPlan;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	@Column(name="target_date", nullable=true, length=10)	
	public String getTargetDate() {
		return targetDate;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="gp_reason_code", nullable=true, length=80)
	public String getGpReasonCode() {
		return gpReasonCode;
	}

	public void setGpReasonCode(String gpReasonCode) {
		this.gpReasonCode = gpReasonCode;
	}

	@Column(name="confirm_status", nullable=true, length=10)
	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	@Column(name="confirm_user", nullable=true)
	public Integer getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Integer confirmUser) {
		this.confirmUser = confirmUser;
	}

	@Column(name="confirm_date", nullable=true)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name="prop_gp_amt", nullable=true)
	public BigDecimal getPropGpAmt() {
		return propGpAmt;
	}

	public void setPropGpAmt(BigDecimal propGpAmt) {
		this.propGpAmt = propGpAmt;
	}

	@Column(name="prop_sales_amt", nullable=true)
	public BigDecimal getPropSalesAmt() {
		return propSalesAmt;
	}

	public void setPropSalesAmt(BigDecimal propSalesAmt) {
		this.propSalesAmt = propSalesAmt;
	}
}
