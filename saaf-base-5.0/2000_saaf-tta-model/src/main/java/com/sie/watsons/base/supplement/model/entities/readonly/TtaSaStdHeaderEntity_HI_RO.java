package com.sie.watsons.base.supplement.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSaStdHeaderEntity_HI_RO Entity Object
 * Tue Mar 31 15:25:13 CST 2020  Auto Generate
 */

public class TtaSaStdHeaderEntity_HI_RO {
	private Integer saStdHeaderId;
    private Integer saStdTplDefHeaderId;
    private String saStdCode;//单据编号
    private Integer saStdVersion;//版本
    private String saTeyp;//补充协议类型
    private String vendorCode;//供应商编号
    private String vendorName;//供应商名称
    private Integer tplId;//模板id
    private String tplName;//模板名称
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveStartTime;//合同生效日期起
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveEndTime;//合同生效日期至
    private String status;//状态
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    private String createByUser;//制单人
	private String statusName;//单据状态名称
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

	public static final String SELECT_SA_STD_HEADER_LIST = "SELECT * FROM TTA_SA_STD_HEADER_V V where 1=1";

	public static final String QUERY_DIC_RESULT_SQL = "\n" +
			"select blv.lookup_code as \"lookupCode\", blv.meaning as \"meaning\"\n" +
			"  from base_lookup_values blv\n" +
			" where  blv.enabled_flag = 'Y'\n" +
			"   and blv.delete_flag = 0\n" +
			"   and blv.start_date_active < sysdate\n" +
			"   and (blv.end_date_active >= sysdate or blv.end_date_active is null)\n" +
			"   and blv.system_code = 'BASE' ";


	public void setSaStdHeaderId(Integer saStdHeaderId) {
		this.saStdHeaderId = saStdHeaderId;
	}

	
	public Integer getSaStdHeaderId() {
		return saStdHeaderId;
	}

	public void setSaStdTplDefHeaderId(Integer saStdTplDefHeaderId) {
		this.saStdTplDefHeaderId = saStdTplDefHeaderId;
	}

	
	public Integer getSaStdTplDefHeaderId() {
		return saStdTplDefHeaderId;
	}

	public void setSaStdCode(String saStdCode) {
		this.saStdCode = saStdCode;
	}

	
	public String getSaStdCode() {
		return saStdCode;
	}

	public void setSaStdVersion(Integer saStdVersion) {
		this.saStdVersion = saStdVersion;
	}

	
	public Integer getSaStdVersion() {
		return saStdVersion;
	}

	public void setSaTeyp(String saTeyp) {
		this.saTeyp = saTeyp;
	}

	
	public String getSaTeyp() {
		return saTeyp;
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

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	
	public Integer getTplId() {
		return tplId;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	
	public String getTplName() {
		return tplName;
	}

	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}

	
	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	
	public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
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

	public String getIsSkipApprove() {
		return isSkipApprove;
	}

	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
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
