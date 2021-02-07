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
 * TtaElecConHeaderEntity_HI Entity Object
 * Mon Mar 30 17:14:24 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_ELEC_CON_HEADER")
public class TtaElecConHeaderEntity_HI {

    private Integer elecConHeaderId;
    private String orderNo;
    private String vendorCode;
    private String vendorName;
    private String status;
    private String changeType;
    private String stampStatus;
    private Integer contractYear;
    private Integer orderVersion;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String orgCode;
    private String deptCode;
    private Integer personId;//采购人员id对应base_person表
    private String isGmApprove;
    private String contractNo; //合同书编号
    private String isElecFlag; //是否电子签章
	//private String  specialNbr;//特批单号
	private String archiveNo;// 档案柜ID
	private String venderLinkMan; //供应商联系人
	private String venderPhone; //供应商手机号
	@JSONField(format="yyyy-MM-dd")
	private Date effectDate; //合同生效日期

	private Integer contractSpecialHeaderId; //特批单号id
	private String isSpecial;//是否特批
	private Integer elecFileId;//电子合同附件id
	private String ownerCompany;//甲方公司名称

	private String isThird; //是否丙方供应商
	private String thirdVenderLinkMan; //丙方供应商联系人
	private String thirdVenderPhone; //丙方供应商手机号
	private String thirdVendorCode; //丙方供应商编号
	private String thirdVendorName; //丙方供应商名称
	private String isConversion;//是否甲乙双方更换
	private Integer contractHId; // 乙方供应商合同拆分表头tta_contract_header id
	private Integer contractThirdHId; //丙方供应商合同拆分表头 tta_contract_header id
	private Integer proposalId;//乙方proposalId
	private Integer thirdProposalId; //丙方proposalId
	private String contractNoRequire;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date bicRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date financeRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date legaRegister;


	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_ELEC_CON_HEADER", sequenceName="SEQ_TTA_ELEC_CON_HEADER", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_ELEC_CON_HEADER",strategy=GenerationType.SEQUENCE)
	@Column(name="elec_con_header_id", nullable=false, length=22)
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=false, length=50)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=false, length=50)	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=false, length=500)	
	public String getVendorName() {
		return vendorName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=2)	
	public String getStatus() {
		return status;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	@Column(name="change_type", nullable=false, length=2)	
	public String getChangeType() {
		return changeType;
	}

	public void setStampStatus(String stampStatus) {
		this.stampStatus = stampStatus;
	}

	@Column(name="stamp_status", nullable=true, length=2)	
	public String getStampStatus() {
		return stampStatus;
	}

	public void setContractYear(Integer contractYear) {
		this.contractYear = contractYear;
	}

	@Column(name="contract_year", nullable=false, length=22)	
	public Integer getContractYear() {
		return contractYear;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	@Column(name="order_version", nullable=true, length=22)	
	public Integer getOrderVersion() {
		return orderVersion;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="org_code")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="dept_code")
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="is_gm_approve")
	public String getIsGmApprove() {
		return isGmApprove;
	}

	public void setIsGmApprove(String isGmApprove) {
		this.isGmApprove = isGmApprove;
	}

	@Column(name="contract_no")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name="is_elec_flag")
	public String getIsElecFlag() {
		return isElecFlag;
	}

	public void setIsElecFlag(String isElecFlag) {
		this.isElecFlag = isElecFlag;
	}

	@Column(name="archive_no")
	public String getArchiveNo() {
		return archiveNo;
	}

	public void setArchiveNo(String archiveNo) {
		this.archiveNo = archiveNo;
	}

	@Column(name="vender_phone")
	public String getVenderPhone() {
		return venderPhone;
	}

	public void setVenderPhone(String venderPhone) {
		this.venderPhone = venderPhone;
	}

	@Column(name="vender_link_man")
	public String getVenderLinkMan() {
		return venderLinkMan;
	}

	public void setVenderLinkMan(String venderLinkMan) {
		this.venderLinkMan = venderLinkMan;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	@Column(name="effect_date")
	public Date getEffectDate() {
		return effectDate;
	}


	@Column(name="contract_special_header_id")
	public Integer getContractSpecialHeaderId() {
		return contractSpecialHeaderId;
	}

	public void setContractSpecialHeaderId(Integer contractSpecialHeaderId) {
		this.contractSpecialHeaderId = contractSpecialHeaderId;
	}

	@Column(name="is_special")
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	@Column(name="elec_file_id")
	public Integer getElecFileId() {
		return elecFileId;
	}

	public void setElecFileId(Integer elecFileId) {
		this.elecFileId = elecFileId;
	}

	@Column(name="person_id")
	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Column(name="owner_company")
	public String getOwnerCompany() {
		return ownerCompany;
	}

	public void setOwnerCompany(String ownerCompany) {
		this.ownerCompany = ownerCompany;
	}


	@Column(name="is_third")
	public String getIsThird() {
		return isThird;
	}

	public void setIsThird(String isThird) {
		this.isThird = isThird;
	}

	@Column(name="third_vender_link_man")
	public String getThirdVenderLinkMan() {
		return thirdVenderLinkMan;
	}

	public void setThirdVenderLinkMan(String thirdVenderLinkMan) {
		this.thirdVenderLinkMan = thirdVenderLinkMan;
	}

	@Column(name="third_vender_phone")
	public String getThirdVenderPhone() {
		return thirdVenderPhone;
	}

	public void setThirdVenderPhone(String thirdVenderPhone) {
		this.thirdVenderPhone = thirdVenderPhone;
	}

	@Column(name="third_vendor_code")
	public String getThirdVendorCode() {
		return thirdVendorCode;
	}

	public void setThirdVendorCode(String thirdVendorCode) {
		this.thirdVendorCode = thirdVendorCode;
	}

	@Column(name="third_vendor_name")
	public String getThirdVendorName() {
		return thirdVendorName;
	}

	public void setThirdVendorName(String thirdVendorName) {
		this.thirdVendorName = thirdVendorName;
	}

	@Column(name="is_conversion")
	public String getIsConversion() {
		return isConversion;
	}

	public void setIsConversion(String isConversion) {
		this.isConversion = isConversion;
	}

	@Column(name="contract_h_id")
	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	@Column(name="contract_third_h_id")
	public Integer getContractThirdHId() {
		return contractThirdHId;
	}

	public void setContractThirdHId(Integer contractThirdHId) {
		this.contractThirdHId = contractThirdHId;
	}

	@Column(name="proposal_id")
	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="third_proposal_id")
	public Integer getThirdProposalId() {
		return thirdProposalId;
	}

	public void setThirdProposalId(Integer thirdProposalId) {
		this.thirdProposalId = thirdProposalId;
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

	@Column(name="contract_no_require", nullable=true, length=20)
	public String getContractNoRequire() {
		return contractNoRequire;
	}

	public void setContractNoRequire(String contractNoRequire) {
		this.contractNoRequire = contractNoRequire;
	}
}
