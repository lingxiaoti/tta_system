package com.sie.watsons.base.supplement.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSaNonStandarHeaderEntity_HI_RO Entity Object
 * Mon Apr 20 16:00:53 CST 2020  Auto Generate
 */

public class TtaSaNonStandarHeaderEntity_HI_RO {

	public static final String TTA_ITEM_V = "select tsnsh.sa_non_standar_header_id,\n" +
			"       tsnsh.order_no,\n" +
			"       tsnsh.order_version,\n" +
			"       tsnsh.vendor_code,\n" +
			"       tsnsh.vendor_name,\n" +
			"       tsnsh.file_id,\n" +
			"       tsnsh.file_url,\n" +
			"       tsnsh.start_time,\n" +
			"       tsnsh.end_time,\n" +
			"       tsnsh.status,\n" +
			"       look_status.meaning            status_name,\n" +
			"       tsnsh.org_code,\n" +
			"       tsnsh.dept_code,\n" +
			"       tsnsh.version_num,\n" +
			"       tsnsh.creation_date,\n" +
			"       tsnsh.created_by,\n" +
			"       bu.user_full_name              created_by_name,\n" +
			"       tsnsh.last_updated_by,\n" +
			"       tsnsh.last_update_date,\n" +
			"       tsnsh.last_update_login,\n" +
			"       tsnsh.approve_date,\n" +
			"       tsnsh.resource_id,\n" +
			"       tsnsh.is_skip_approve,\n" +
			"       tsnsh.party_a_vendor_name,\n" +
			"       look1.meaning                  party_a_vendor_name_name,\n" +
			"       tsnsh.party_b_vendor_name,\n" +
			"       tsnsh.party_c_vendor_name,\n" +
			"       tsnsh.contract_year,\n" +
			"       tsnsh.tpl_id,\n" +
			"       tsstdh.tpl_name                quote_tpl_name,\n" +
			"       tsstdh.tpl_code,\n" +
			"       tsnsh.dept_name,\n" +
			"       ba.file_id  attachment_file_id,\n" +
			"       ba.source_file_name,\n" +
			"       ba.file_path,\n" +
			"       tsnsh.bic_register,\n" +
			"       tsnsh.finance_register,\n" +
			"       tsnsh.lega_register,\n" +
			"       tsnsh.pigeonhole_date\n" +
			"  from tta_sa_non_standar_header tsnsh\n" +
			"  left join (select blv.lookup_code, blv.meaning\n" +
			"               from base_lookup_values blv\n" +
			"              where blv.lookup_type = 'TTA_STD_APPLY_HEADER_STATUS') look_status\n" +
			"    on look_status.lookup_code = tsnsh.status\n" +
			"  left join (select blv.lookup_code, blv.meaning\n" +
			"               from base_lookup_values blv\n" +
			"              where blv.lookup_type = 'TTA_OWNER_COMPANY') look1\n" +
			"    on look1.lookup_code = tsnsh.party_a_vendor_name\n" +
			"  left join base_users bu\n" +
			"    on tsnsh.created_by = bu.user_id\n" +
			"  left join tta_sa_std_tpl_def_header tsstdh\n" +
			"    on tsnsh.tpl_id = tsstdh.sa_std_tpl_def_header_id\n" +
			"  left join base_attachment ba \n" +
			"    on ba.function_id = 'TTA_SA_NON_STANDAR_HEADER_ATTACHMENT' \n" +
			"    and  ba.business_id = tsnsh.sa_non_standar_header_id and nvl(ba.delete_flag,0) = 0  \n" +
			" where 1 = 1";

    private Integer saNonStandarHeaderId;
    private String orderNo;
    private Integer orderVersion;
    private String vendorCode;
    private String vendorName;
    private Integer fileId;
	private Integer tplId;
    private String fileUrl;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String status;
	private String statusName;
	private String quoteTplName;
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
	private String partyAVendorName;
	private String partyAVendorNameName;
	private String partyBVendorName;
	private String partyCVendorName;
	private Integer contractYear;
	private String tplCode;//模板编码

	private Integer attachmentFileId;//附件id
	private String sourceFileName;//附件名
	private String filePath;//附件路径

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date bicRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date financeRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date legaRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date pigeonholeDate;//归档日期

	public void setSaNonStandarHeaderId(Integer saNonStandarHeaderId) {
		this.saNonStandarHeaderId = saNonStandarHeaderId;
	}

	
	public Integer getSaNonStandarHeaderId() {
		return saNonStandarHeaderId;
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

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public Integer getTplId() {
		return tplId;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	
	public Date getStartTime() {
		return startTime;
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

	public String getQuoteTplName() {
		return quoteTplName;
	}

	public void setQuoteTplName(String quoteTplName) {
		this.quoteTplName = quoteTplName;
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

	public String getPartyAVendorName() {
		return partyAVendorName;
	}

	public void setPartyAVendorName(String partyAVendorName) {
		this.partyAVendorName = partyAVendorName;
	}

	public String getPartyBVendorName() {
		return partyBVendorName;
	}

	public void setPartyBVendorName(String partyBVendorName) {
		this.partyBVendorName = partyBVendorName;
	}

	public String getPartyCVendorName() {
		return partyCVendorName;
	}

	public void setPartyCVendorName(String partyCVendorName) {
		this.partyCVendorName = partyCVendorName;
	}

	public Integer getContractYear() {
		return contractYear;
	}

	public void setContractYear(Integer contractYear) {
		this.contractYear = contractYear;
	}

	public String getPartyAVendorNameName() {
		return partyAVendorNameName;
	}

	public void setPartyAVendorNameName(String partyAVendorNameName) {
		this.partyAVendorNameName = partyAVendorNameName;
	}

	public String getTplCode() {
		return tplCode;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	public Integer getAttachmentFileId() {
		return attachmentFileId;
	}

	public void setAttachmentFileId(Integer attachmentFileId) {
		this.attachmentFileId = attachmentFileId;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
