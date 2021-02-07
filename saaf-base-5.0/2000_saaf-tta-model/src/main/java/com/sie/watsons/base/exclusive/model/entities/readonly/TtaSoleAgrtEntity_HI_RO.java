package com.sie.watsons.base.exclusive.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSoleAgrtEntity_HI_RO Entity Object
 * Tue Jun 25 10:44:35 CST 2019  Auto Generate
 */

public class TtaSoleAgrtEntity_HI_RO {
	public static final String QUEEY_SOLE_ARGRT = "select * from tta_sole_agrt_v tsa where 1=1 ";
	public static final String QUERY_SINGLE_ARGRT = "select   tsa.sole_agrt_h_id,\n" +
			"         tsa.sole_agrt_code,\n" +
			"         tsa.status,\n" +
			"         tsa.sole_agrt_version,\n" +
			"         tsa.apply_date,\n" +
			"         tsa.start_date,\n" +
			"         tsa.end_date,\n" +
			"         tsa.contract_termination_time,\n" +
			"         tsa.vendor_code,\n" +
			"         tsa.vendor_name,\n" +
			"         tsa.agrt_type,\n" +
			"         tsa.version_num,\n" +
			"         tsa.creation_date,\n" +
			"         tsa.created_by,\n" +
			"         tsa.last_updated_by,\n" +
			"         tsa.last_update_date,\n" +
			"         tsa.last_update_login,\n" +
			"         tsa.org_code,\n" +
			"         tsa.org_name,\n" +
			"         tsa.dept_code,\n" +
			"         tsa.dept_name,\n" +
			"         bu.user_full_name     createByUser,\n" +
			"         lookup1.meaning       statusName,\n" +
			"		  tsa.is_skip_approve,\n" +
			"		  tsa.owner_company,\n" +
			"		  tsa.bic_register,\n" +
			"		  tsa.finance_register,\n" +
			"		  tsa.lega_register,\n" +
			"		  tsa.pigeonhole_date\n" +
			"    from tta_sole_agrt tsa\n" +
			"    join base_users bu\n" +
			"      on tsa.created_by = bu.user_id\n" +
			"    left join (select lookup_type, lookup_code, meaning\n" +
			"                 from base_lookup_values\n" +
			"                where lookup_type = 'EXCLUSIVE_STAUTS'\n" +
			"                  and enabled_flag = 'Y'\n" +
			"                  and delete_flag = 0\n" +
			"                  and start_date_active < sysdate\n" +
			"                  and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                  and system_code = 'BASE') lookup1\n" +
			"      on tsa.status = lookup1.lookup_code\n" +
			"      where 1=1 ";



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

	private String createByUser;
	private String statusName;
	private String vendorCode;
	private String vendorName;
	private String agrtType;//独家协议类型 standard 标准

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
	private String ownerCompany;
	private String ownerCompanyName;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date bicRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date financeRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date legaRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date pigeonholeDate;//归档日期


	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}

	
	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setSoleAgrtCode(String soleAgrtCode) {
		this.soleAgrtCode = soleAgrtCode;
	}

	
	public String getSoleAgrtCode() {
		return soleAgrtCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setSoleAgrtVersion(String soleAgrtVersion) {
		this.soleAgrtVersion = soleAgrtVersion;
	}

	
	public String getSoleAgrtVersion() {
		return soleAgrtVersion;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
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

	public String getCreateByUser() {
		return createByUser;
	}

	public void setCreateByUser(String createByUser) {
		this.createByUser = createByUser;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getAgrtType() {
		return agrtType;
	}

	public void setAgrtType(String agrtType) {
		this.agrtType = agrtType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getContractTerminationTime() {
		return contractTerminationTime;
	}

	public void setContractTerminationTime(Date contractTerminationTime) {
		this.contractTerminationTime = contractTerminationTime;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Integer getAlertBy() {
		return alertBy;
	}

	public void setAlertBy(Integer alertBy) {
		this.alertBy = alertBy;
	}

	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	public String getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(String versionStatus) {
		this.versionStatus = versionStatus;
	}

	public String getIsSkipApprove() {
		return isSkipApprove;
	}

	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
	}

	public String getOwnerCompany() {
		return ownerCompany;
	}

	public void setOwnerCompany(String ownerCompany) {
		this.ownerCompany = ownerCompany;
	}

	public String getOwnerCompanyName() {
		return ownerCompanyName;
	}

	public void setOwnerCompanyName(String ownerCompanyName) {
		this.ownerCompanyName = ownerCompanyName;
	}

	public Date getBicRegister() {
		return bicRegister;
	}

	public void setBicRegister(Date bicRegister) {
		this.bicRegister = bicRegister;
	}

	public Date getFinanceRegister() {
		return financeRegister;
	}

	public void setFinanceRegister(Date financeRegister) {
		this.financeRegister = financeRegister;
	}

	public Date getLegaRegister() {
		return legaRegister;
	}

	public void setLegaRegister(Date legaRegister) {
		this.legaRegister = legaRegister;
	}

	public Date getPigeonholeDate() {
		return pigeonholeDate;
	}

	public void setPigeonholeDate(Date pigeonholeDate) {
		this.pigeonholeDate = pigeonholeDate;
	}
}
