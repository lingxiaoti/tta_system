package com.sie.watsons.base.supplier.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaSupplierEntity_HI_RO Entity Object
 * Wed May 22 12:51:33 CST 2019  Auto Generate
 */

public class TtaSupplierEntity_HI_RO {

	public static final String TTA_SUPPLIER_V = "select nvl(tts.supplier_code, v.supplier_code) marjor_supplier_code,\n" +
			"       nvl(tts.supplier_name, v.supplier_name) marjor_supplier_name,\n" +
			"       v.*\n" +
			"  from tta_supplier_V V\n" +
			"  left join tta_rel_supplier trs\n" +
			"    on V.supplier_code = trs.rel_supplier_code\n" +
			"  left join Tta_Supplier tts\n" +
			"    on trs.rel_id = tts.supplier_id\n" +
			" where 1 = 1";

	public static final String TTA_SUPPLIER_IS_V = "select * from tta_supplier_IS_V V where 1=1";
    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    private String status;
    private String isLatent;
	private BigDecimal additionRate;


	public static final String TTA_SUPPLIER_ADDITION_RATE_V = "select v.*, tcm.addition_rate\n" +
			"  from tta_supplier_V V\n" +
			"  left join tta_contract_master tcm\n" +
			"    on v.SUPPLIER_CODE = tcm.vendor_nbr\n" +
			" where 1 = 1 ";
    public static String getVenderSql() {
    	String sql =
				"select *\n" +
				"  from (select b.rel_supplier_code as supplier_code,\n" +
				"               b.rel_supplier_name as supplier_name\n" +
				"          from tta_supplier a\n" +
				"         inner join tta_rel_supplier b\n" +
				"            on a.supplier_id = b.rel_id\n" +
				"         where a.supplier_code =:venderCode -- 1.从供应商\n" +
				"        union\n" +
				"        select a.supplier_code, a.supplier_name\n" +
				"          from tta_supplier a\n" +
				"         where a.is_latent = 'N'\n" +
				"           and a.formal_code =:venderCode  --2.查找潜在供应商\n" +
				"        union\n" +
				"        -- 3.正式供应去 关联潜在供应商的从供应商\n" +
				"        select b.rel_supplier_code as supplier_code,\n" +
				"               b.rel_supplier_name as supplier_name\n" +
				"          from tta_supplier a\n" +
				"         inner join tta_rel_supplier b\n" +
				"            on a.supplier_id = b.rel_id\n" +
				"         where a.is_latent = 'N'\n" +
				"           and a.formal_code =:venderCode" +
				 " 			union " +
				"		select t.supplier_code, t.supplier_name  from tta_supplier t\n  -- 4.自身供应商\n" +
						" where t.supplier_code=:venderCode \n" +
						") t where 1 = 1  \n" +
						" and not exists (select * from tta_contract_line tcl where tcl.vendor_code = t.supplier_code and tcl.contract_h_id=:contractHId)";
    	return sql;
	}

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String ownerDept;
    private String deptName;
    private String ownerGroup;
	private String ownerGroupName;
    private String contractOutput;
	private String isNew;
	private String isNewName;
	private String purchaseMode;
    private String proposalBrandGroup;
    private String formalCode;
    private String formalName;
    private Integer operatorUserId;
    private String statusName;
	private String lastUpdatedName;
	private String createdName;
	private String returnedPurchaseFlag;
	private String returnFreightFlag;
	private String isSubmitProposal;//是否提交PROPOSAL Y需要
	private String noSubmitReason;//不提交PROPOSAL原因
	private String isSubmitContract;//是否提交合同Y需要
	private String isNewSupplier;//是否全新供应商
	private String isCollect;//资料是否收集完整
	private String venderLinkMan;//供应商联系人
	private String  venderPhone;//供应商手机号
	private String marjorSupplierCode;//主供应商编号
	private String marjorSupplierName;//主供应商名称

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
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

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setIsLatent(String isLatent) {
		this.isLatent = isLatent;
	}

	
	public String getIsLatent() {
		return isLatent;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOwnerDept(String ownerDept) {
		this.ownerDept = ownerDept;
	}

	
	public String getOwnerDept() {
		return ownerDept;
	}

	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	
	public String getOwnerGroup() {
		return ownerGroup;
	}

	public void setContractOutput(String contractOutput) {
		this.contractOutput = contractOutput;
	}

	
	public String getContractOutput() {
		return contractOutput;
	}

	public void setPurchaseMode(String purchaseMode) {
		this.purchaseMode = purchaseMode;
	}

	
	public String getPurchaseMode() {
		return purchaseMode;
	}

    public String getFormalCode() {
        return formalCode;
    }

    public void setFormalCode(String formalCode) {
        this.formalCode = formalCode;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getProposalBrandGroup() {
		return proposalBrandGroup;
	}

	public void setProposalBrandGroup(String proposalBrandGroup) {
		this.proposalBrandGroup = proposalBrandGroup;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOwnerGroupName() {
		return ownerGroupName;
	}

	public String getLastUpdatedName() {
		return lastUpdatedName;
	}

	public void setLastUpdatedName(String lastUpdatedName) {
		this.lastUpdatedName = lastUpdatedName;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public void setOwnerGroupName(String ownerGroupName) {
		this.ownerGroupName = ownerGroupName;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getIsNewName() {
		return isNewName;
	}

	public void setIsNewName(String isNewName) {
		this.isNewName = isNewName;
	}

	public String getReturnedPurchaseFlag() {
		return returnedPurchaseFlag;
	}

	public void setReturnedPurchaseFlag(String returnedPurchaseFlag) {
		this.returnedPurchaseFlag = returnedPurchaseFlag;
	}

	public String getReturnFreightFlag() {
		return returnFreightFlag;
	}

	public void setReturnFreightFlag(String returnFreightFlag) {
		this.returnFreightFlag = returnFreightFlag;
	}

	public String getIsSubmitProposal() {
		return isSubmitProposal;
	}

	public void setIsSubmitProposal(String isSubmitProposal) {
		this.isSubmitProposal = isSubmitProposal;
	}

	public String getNoSubmitReason() {
		return noSubmitReason;
	}

	public void setNoSubmitReason(String noSubmitReason) {
		this.noSubmitReason = noSubmitReason;
	}

	public String getIsSubmitContract() {
		return isSubmitContract;
	}

	public void setIsSubmitContract(String isSubmitContract) {
		this.isSubmitContract = isSubmitContract;
	}

	public void setIsNewSupplier(String isNewSupplier) {
		this.isNewSupplier = isNewSupplier;
	}

	public String getIsNewSupplier() {
		return isNewSupplier;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
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

	public BigDecimal getAdditionRate() {
		return additionRate;
	}

	public void setAdditionRate(BigDecimal additionRate) {
		this.additionRate = additionRate;
	}

	public String getMarjorSupplierCode() {
		return marjorSupplierCode;
	}

	public void setMarjorSupplierCode(String marjorSupplierCode) {
		this.marjorSupplierCode = marjorSupplierCode;
	}

	public String getMarjorSupplierName() {
		return marjorSupplierName;
	}

	public void setMarjorSupplierName(String marjorSupplierName) {
		this.marjorSupplierName = marjorSupplierName;
	}
}
