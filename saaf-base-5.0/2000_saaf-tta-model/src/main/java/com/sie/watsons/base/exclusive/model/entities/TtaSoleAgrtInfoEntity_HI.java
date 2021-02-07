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
 * TtaSoleAgrtInfoEntity_HI Entity Object
 * Tue Jun 25 10:44:38 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SOLE_AGRT_INFO")
public class TtaSoleAgrtInfoEntity_HI {
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
    private String productType;//指定产品:1:全品牌,2:指定产品
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

	public void setSoleAgrtInfoId(Integer soleAgrtInfoId) {
		this.soleAgrtInfoId = soleAgrtInfoId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SOLE_AGRT_INFO", sequenceName="SEQ_TTA_SOLE_AGRT_INFO", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SOLE_AGRT_INFO",strategy=GenerationType.SEQUENCE)
	@Column(name="sole_agrt_info_id", nullable=false, length=22)	
	public Integer getSoleAgrtInfoId() {
		return soleAgrtInfoId;
	}

	public void setSoleSupplierId(Integer soleSupplierId) {
		this.soleSupplierId = soleSupplierId;
	}

	@Column(name="sole_supplier_id", nullable=true, length=22)	
	public Integer getSoleSupplierId() {
		return soleSupplierId;
	}

	public void setIsPeb(String isPeb) {
		this.isPeb = isPeb;
	}

	@Column(name="is_peb", nullable=true, length=2)	
	public String getIsPeb() {
		return isPeb;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=50)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=50)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_code", nullable=true, length=50)	
	public String getOrgCode() {
		return orgCode;
	}

	public void setSoleBrandCn(String soleBrandCn) {
		this.soleBrandCn = soleBrandCn;
	}

	@Column(name="sole_brand_cn", nullable=true, length=100)	
	public String getSoleBrandCn() {
		return soleBrandCn;
	}

	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	@Column(name="sale_start_date", nullable=true, length=7)	
	public Date getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	@Column(name="sale_end_date", nullable=true, length=7)	
	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleRegion(String saleRegion) {
		this.saleRegion = saleRegion;
	}

	@Column(name="sale_region", nullable=true, length=2)	
	public String getSaleRegion() {
		return saleRegion;
	}

	public void setIsNewSole(String isNewSole) {
		this.isNewSole = isNewSole;
	}

	@Column(name="is_new_sole", nullable=true, length=2)	
	public String getIsNewSole() {
		return isNewSole;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	@Column(name="is_special", nullable=true, length=2)	
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsAutoDeferral(String isAutoDeferral) {
		this.isAutoDeferral = isAutoDeferral;
	}

	@Column(name="is_auto_deferral", nullable=true, length=2)	
	public String getIsAutoDeferral() {
		return isAutoDeferral;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name="channel_type", nullable=true, length=2)	
	public String getChannelType() {
		return channelType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name="product_type", nullable=true, length=2)	
	public String getProductType() {
		return productType;
	}

	public void setIsEcChannel(String isEcChannel) {
		this.isEcChannel = isEcChannel;
	}

	@Column(name="is_ec_channel", nullable=true, length=2)	
	public String getIsEcChannel() {
		return isEcChannel;
	}

	public void setIsException(String isException) {
		this.isException = isException;
	}

	@Column(name="is_exception", nullable=true, length=2)	
	public String getIsException() {
		return isException;
	}

	public void setExceptionRemark(String exceptionRemark) {
		this.exceptionRemark = exceptionRemark;
	}

	@Column(name="exception_remark", nullable=true, length=500)	
	public String getExceptionRemark() {
		return exceptionRemark;
	}

	public void setIsCovered(String isCovered) {
		this.isCovered = isCovered;
	}

	@Column(name="is_covered", nullable=true, length=2)	
	public String getIsCovered() {
		return isCovered;
	}

	public void setIsEndArgt(String isEndArgt) {
		this.isEndArgt = isEndArgt;
	}

	@Column(name="is_end_argt", nullable=true, length=2)	
	public String getIsEndArgt() {
		return isEndArgt;
	}

	public void setIsChangeClause(String isChangeClause) {
		this.isChangeClause = isChangeClause;
	}

	@Column(name="is_change_clause", nullable=true, length=2)	
	public String getIsChangeClause() {
		return isChangeClause;
	}

	public void setIsSignAccount(String isSignAccount) {
		this.isSignAccount = isSignAccount;
	}

	@Column(name="is_sign_account", nullable=true, length=2)	
	public String getIsSignAccount() {
		return isSignAccount;
	}

	public void setContractSituation(String contractSituation) {
		this.contractSituation = contractSituation;
	}

	@Column(name="contract_situation", nullable=true, length=100)
	public String getContractSituation() {
		return contractSituation;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=500)	
	public String getRemark() {
		return remark;
	}

	public void setContractAcceptDate(String contractAcceptDate) {
		this.contractAcceptDate = contractAcceptDate;
	}

	@Column(name="contract_accept_date", nullable=true, length=500)	
	public String getContractAcceptDate() {
		return contractAcceptDate;
	}

	public void setSaleEffectDate(Date saleEffectDate) {
		this.saleEffectDate = saleEffectDate;
	}

	@Column(name="sale_effect_date", nullable=true, length=7)	
	public Date getSaleEffectDate() {
		return saleEffectDate;
	}

	public void setIsSpecialApproval(String isSpecialApproval) {
		this.isSpecialApproval = isSpecialApproval;
	}

	@Column(name="is_special_approval", nullable=true, length=2)
	public String getIsSpecialApproval() {
		return isSpecialApproval;
	}

	public void setIsRenewal(String isRenewal) {
		this.isRenewal = isRenewal;
	}

	@Column(name="is_renewal", nullable=true, length=2)	
	public String getIsRenewal() {
		return isRenewal;
	}

	public void setSysStartDate(Date sysStartDate) {
		this.sysStartDate = sysStartDate;
	}

	@Column(name="sys_start_date", nullable=true, length=7)	
	public Date getSysStartDate() {
		return sysStartDate;
	}

	public void setSysEndDate(Date sysEndDate) {
		this.sysEndDate = sysEndDate;
	}

	@Column(name="sys_end_date", nullable=true, length=7)	
	public Date getSysEndDate() {
		return sysEndDate;
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

	@Column(name="sole_agrt_h_id", nullable=true, length=22)
	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}

	@Column(name="org_name", nullable=true, length=230)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="brandpln_l_id", nullable=true, length=22)
	public Integer getBrandplnLId() {
		return brandplnLId;
	}

	public void setBrandplnLId(Integer brandplnLId) {
		this.brandplnLId = brandplnLId;
	}

	@Column(name="group_code", nullable=true, length=30)
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_desc", nullable=true, length=230)
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="dept_code", nullable=true, length=30)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_desc", nullable=true, length=230)
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="brand_cn", nullable=true, length=230)
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_en", nullable=true, length=230)
	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="proposal_id", nullable=true, length=22)
	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="is_take_back_sell", nullable=true, length=30)
	public String getIsTakeBackSell() {
		return isTakeBackSell;
	}

	public void setIsTakeBackSell(String isTakeBackSell) {
		this.isTakeBackSell = isTakeBackSell;
	}
}
