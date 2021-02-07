package com.sie.watsons.base.supplement.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldAttrIgnore;

import javax.persistence.Transient;

/**
 * TtaSaStdProposalLineEntity_HI_RO Entity Object
 * Tue Mar 31 15:25:14 CST 2020  Auto Generate
 */

public class TtaSaStdProposalLineEntity_HI_RO {
	private Integer saStdProposalLine;
    private Integer saStdHeaderId;
    private String proposalOrder;
    private Integer proposalVersion;
    private Integer proposalYear;
    private String vendorCode;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String vendorName;
	private Integer proposalId;
	private Integer contractHId;
	private String saleType;
	private String saleTypeName;
	private String brandCn;
	private String brandEn;
	private Integer supplierId;
	private String venderLinkMan;
	private String venderPhone;
	private String isCollect;//供应资料是否收集
	private String conditionVendor;

	@FieldAttrIgnore
	public static final String QUERY = " select t.sa_std_proposal_line,\n" +
			"        t.sa_std_header_id,\n" +
			"        t.proposal_order,\n" +
			"        t.proposal_version,\n" +
			"        t.proposal_year,\n" +
			"        t.vendor_code,\n" +
			"        t.version_num,\n" +
			"        t.creation_date,\n" +
			"        t.created_by,\n" +
			"        t.last_updated_by,\n" +
			"        t.last_update_date,\n" +
			"        t.last_update_login,\n" +
			"        t.vendor_name\n" +
			"   from tta_sa_std_proposal_line t where 1=1 ";

	@FieldAttrIgnore
	public static final String QUERY_PROPOSAL_PARAMS = "  select tph.proposal_id,\n" +
			"         tsspl.proposal_order,\n" +
			"         tsspl.vendor_code,\n" +
			"         tsspl.vendor_name,\n" +
			"         tsspl.proposal_year,\n" +
			"         tsspl.proposal_version\n" +
			"    from tta_sa_std_proposal_line tsspl\n" +
			"    join tta_proposal_header tph\n" +
			"      on tsspl.proposal_order = tph.order_nbr\n" +
			"     and tsspl.proposal_version = tph.version_code where 1=1";

	public void setSaStdProposalLine(Integer saStdProposalLine) {
		this.saStdProposalLine = saStdProposalLine;
	}

	
	public Integer getSaStdProposalLine() {
		return saStdProposalLine;
	}

	public void setSaStdHeaderId(Integer saStdHeaderId) {
		this.saStdHeaderId = saStdHeaderId;
	}

	
	public Integer getSaStdHeaderId() {
		return saStdHeaderId;
	}

	public void setProposalOrder(String proposalOrder) {
		this.proposalOrder = proposalOrder;
	}

	
	public String getProposalOrder() {
		return proposalOrder;
	}

	public void setProposalVersion(Integer proposalVersion) {
		this.proposalVersion = proposalVersion;
	}

	
	public Integer getProposalVersion() {
		return proposalVersion;
	}

	public void setProposalYear(Integer proposalYear) {
		this.proposalYear = proposalYear;
	}

	
	public Integer getProposalYear() {
		return proposalYear;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	public String getVendorCode() {
		return vendorCode;
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

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getSaleTypeName() {
		return saleTypeName;
	}

	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getVenderLinkMan() {
		return venderLinkMan;
	}

	public void setVenderLinkMan(String venderLinkMan) {
		this.venderLinkMan = venderLinkMan;
	}

	public String getVenderPhone() {
		return venderPhone;
	}

	public void setVenderPhone(String venderPhone) {
		this.venderPhone = venderPhone;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getConditionVendor() {
		return conditionVendor;
	}

	public void setConditionVendor(String conditionVendor) {
		this.conditionVendor = conditionVendor;
	}
}
