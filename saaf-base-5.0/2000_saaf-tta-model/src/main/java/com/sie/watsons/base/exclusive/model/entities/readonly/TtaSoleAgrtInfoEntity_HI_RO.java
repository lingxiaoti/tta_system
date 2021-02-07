package com.sie.watsons.base.exclusive.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSoleAgrtInfoEntity_HI_RO Entity Object
 * Tue Jun 25 10:44:38 CST 2019  Auto Generate
 */

public class TtaSoleAgrtInfoEntity_HI_RO {
	private Integer soleAgrtInfoId;//主键
	private Integer soleSupplierId;//表tta_sole_supplier外键ID
	private Integer soleAgrtHId;//表tta_sole_agrt的外鍵
	private String isPeb;//是否PEB Y/N
	private String supplierCode;//供应商编号
	private String supplierName;//供应商名称
	private String orgCode;//部门
	private String orgName;//部门名称
	private String soleBrandCn;//独家品牌(中文)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date saleStartDate;//独家销售起始时间
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date saleEndDate;//独家销售结束时间
	private String saleRegion;//独家销售区域,值包含:中国(除港,澳,台)及空白
	private String isNewSole;//新品是否直接成为独家产品 Y/N
	private String isSpecial;//是否独家产品主要成分显著不同 Y/N
	private String isAutoDeferral;//是否独家期限自动顺延
	private String channelType;//独家渠道类别:全渠道独家、健与美渠道，药房，大卖场和超市独家、健与美渠道独家、特殊渠道及空白
	private String productType;//指定产品:1指定产品、指定全品牌
	private String isEcChannel;//是否包含电商渠道 Y/N
	private String isException;//是否包含独家渠道例外情形 Y/N
	private String exceptionRemark;//独家渠道例外情形具体描述
	private String isCovered;//双方是否已盖章 Y/N
	private String isEndArgt;//是否结束旧协议  Y/N
	private String isChangeClause;//是否后期变更条 Y/N
	private String isSignAccount;//是否签订转户协议 Y/N
	private String contractSituation;//合同情况
	private String remark;//备注
	private String contractAcceptDate;//合同接收时间
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date saleEffectDate;//独家生效时间
	private String isSpecialApproval;//是否获得特批
	private String isRenewal;//是否续签
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sysStartDate;//系统实际起始时间
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sysEndDate;//系统实际结束时间
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String isTakeBackSell;//是否追回销售

	private Integer brandplnLId;
	private String groupCode;
	private String groupDesc;
	private String deptCode;
	private String deptDesc;
	private String brandCn;
	private String brandEn;
	private Integer proposalId;


	public static final String QUERY_SOLE_AGRT_INFO = "select * from tta_sole_agrt_info tsai where 1=1 ";

	public void setSoleAgrtInfoId(Integer soleAgrtInfoId) {
		this.soleAgrtInfoId = soleAgrtInfoId;
	}

	
	public Integer getSoleAgrtInfoId() {
		return soleAgrtInfoId;
	}

	public void setSoleSupplierId(Integer soleSupplierId) {
		this.soleSupplierId = soleSupplierId;
	}

	
	public Integer getSoleSupplierId() {
		return soleSupplierId;
	}

	public void setIsPeb(String isPeb) {
		this.isPeb = isPeb;
	}

	
	public String getIsPeb() {
		return isPeb;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getOrgCode() {
		return orgCode;
	}

	public void setSoleBrandCn(String soleBrandCn) {
		this.soleBrandCn = soleBrandCn;
	}

	
	public String getSoleBrandCn() {
		return soleBrandCn;
	}

	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	
	public Date getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	
	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleRegion(String saleRegion) {
		this.saleRegion = saleRegion;
	}

	
	public String getSaleRegion() {
		return saleRegion;
	}

	public void setIsNewSole(String isNewSole) {
		this.isNewSole = isNewSole;
	}

	
	public String getIsNewSole() {
		return isNewSole;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsAutoDeferral(String isAutoDeferral) {
		this.isAutoDeferral = isAutoDeferral;
	}

	
	public String getIsAutoDeferral() {
		return isAutoDeferral;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	
	public String getChannelType() {
		return channelType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	
	public String getProductType() {
		return productType;
	}

	public void setIsEcChannel(String isEcChannel) {
		this.isEcChannel = isEcChannel;
	}

	
	public String getIsEcChannel() {
		return isEcChannel;
	}

	public void setIsException(String isException) {
		this.isException = isException;
	}

	
	public String getIsException() {
		return isException;
	}

	public void setExceptionRemark(String exceptionRemark) {
		this.exceptionRemark = exceptionRemark;
	}

	
	public String getExceptionRemark() {
		return exceptionRemark;
	}

	public void setIsCovered(String isCovered) {
		this.isCovered = isCovered;
	}

	
	public String getIsCovered() {
		return isCovered;
	}

	public void setIsEndArgt(String isEndArgt) {
		this.isEndArgt = isEndArgt;
	}

	
	public String getIsEndArgt() {
		return isEndArgt;
	}

	public void setIsChangeClause(String isChangeClause) {
		this.isChangeClause = isChangeClause;
	}

	
	public String getIsChangeClause() {
		return isChangeClause;
	}

	public void setIsSignAccount(String isSignAccount) {
		this.isSignAccount = isSignAccount;
	}

	
	public String getIsSignAccount() {
		return isSignAccount;
	}

	public void setContractSituation(String contractSituation) {
		this.contractSituation = contractSituation;
	}

	
	public String getContractSituation() {
		return contractSituation;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setContractAcceptDate(String contractAcceptDate) {
		this.contractAcceptDate = contractAcceptDate;
	}

	
	public String getContractAcceptDate() {
		return contractAcceptDate;
	}

	public void setSaleEffectDate(Date saleEffectDate) {
		this.saleEffectDate = saleEffectDate;
	}

	
	public Date getSaleEffectDate() {
		return saleEffectDate;
	}

	public void setIsSpecialApproval(String isSpecialApproval) {
		this.isSpecialApproval = isSpecialApproval;
	}

	
	public String getIsSpecialApproval() {
		return isSpecialApproval;
	}

	public void setIsRenewal(String isRenewal) {
		this.isRenewal = isRenewal;
	}

	
	public String getIsRenewal() {
		return isRenewal;
	}

	public void setSysStartDate(Date sysStartDate) {
		this.sysStartDate = sysStartDate;
	}

	
	public Date getSysStartDate() {
		return sysStartDate;
	}

	public void setSysEndDate(Date sysEndDate) {
		this.sysEndDate = sysEndDate;
	}

	
	public Date getSysEndDate() {
		return sysEndDate;
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

	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getBrandplnLId() {
		return brandplnLId;
	}

	public void setBrandplnLId(Integer brandplnLId) {
		this.brandplnLId = brandplnLId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
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

	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public String getIsTakeBackSell() {
		return isTakeBackSell;
	}

	public void setIsTakeBackSell(String isTakeBackSell) {
		this.isTakeBackSell = isTakeBackSell;
	}
}
