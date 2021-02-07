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
import javax.persistence.Transient;

/**
 * TtaNppNewProductReportEntity_HI Entity Object
 * Wed Nov 20 14:40:10 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_npp_new_product_report")
public class TtaNppNewProductReportEntity_HI {
    private Integer nppId;
    private String month;
    private Integer storeCoun;
    private String groupDesc;
    private String deptDesc;
    private String itemCode;
    private String itemDescCn;
    private String brandCn;
    private String brandEn;
    private String vendorNbr;
    private String vendorName;
    private String activity;
    private String ctw;
    private String eb;
    private String contractTerms;
    private String collectWay;
    private BigDecimal chargeStandards;
    private String chargeUnit;
    private BigDecimal receiveAmount;
    private BigDecimal unconfirmAmount;
    private BigDecimal difference;
    private String sourceFilepath;
    private String sourceFilename;
    private String collectSelect;
    private String purchaseAct;
    private String exemptionReason;
    private String exemptionReason2;
    private String debitOrderCode;
    private BigDecimal aboiReceipts;
    private String batchCode;
    private Integer transferInPerson;
    private String remark;
    private Integer status;
    private Integer parentId;
    private String parentVendorCode;
    private String parentVendorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer transferOutPerson;
    private Integer transferLastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    private String chargeUnitName;
    private BigDecimal originalBeforeAmount;
    private Integer additionRate;
    private Integer operatorUserId;

    private String groupCode;
    private String deptCode;
	private String isCreate;
	private BigDecimal receiveAmountAdd;
	private String chargeMethod;//收费方式
	private Integer annualMonthN;//实际月份 数字类型
	private Integer monthN;//导入月 数字类型
	private BigDecimal unconfirmAmountA;//需采购确认金额(含加成)

	private Integer processId;
	private Integer fileId;//文件id

	private BigDecimal affirmTotStoreCount;//采购确认的TOTAL店铺数量
	private BigDecimal addReceiveAmount;//追加应收金额

	private Integer adjustBy;
	private BigDecimal adjustAmount;
	private BigDecimal adjustReceiveAmount;//调整应实收金额(含加成)
	private BigDecimal noAdjustReceiveAmount;//调整应收金额(不含加成)
	private String type;
	private String priorVendorCode;
	private String priorVendorName;

	public void setNppId(Integer nppId) {
		this.nppId = nppId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_NPP_NEW_PRODUCT", sequenceName = "SEQ_TTA_NPP_NEW_PRODUCT", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_NPP_NEW_PRODUCT", strategy = GenerationType.SEQUENCE)
	@Column(name="npp_id", nullable=false, length=22)	
	public Integer getNppId() {
		return nppId;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name="month", nullable=true, length=60)	
	public String getMonth() {
		return month;
	}

	public void setStoreCoun(Integer storeCoun) {
		this.storeCoun = storeCoun;
	}

	@Column(name="store_coun", nullable=true, length=22)	
	public Integer getStoreCoun() {
		return storeCoun;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=100)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=100)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=100)	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	@Column(name="item_desc_cn", nullable=true, length=250)	
	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=150)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=150)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=true, length=100)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=200)	
	public String getVendorName() {
		return vendorName;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@Column(name="activity", nullable=true, length=500)	
	public String getActivity() {
		return activity;
	}

	public void setCtw(String ctw) {
		this.ctw = ctw;
	}

	@Column(name="ctw", nullable=true, length=100)	
	public String getCtw() {
		return ctw;
	}

	public void setEb(String eb) {
		this.eb = eb;
	}

	@Column(name="eb", nullable=true, length=100)	
	public String getEb() {
		return eb;
	}

	public void setContractTerms(String contractTerms) {
		this.contractTerms = contractTerms;
	}

	@Column(name="contract_terms", nullable=true, length=700)	
	public String getContractTerms() {
		return contractTerms;
	}

	public void setCollectWay(String collectWay) {
		this.collectWay = collectWay;
	}

	@Column(name="collect_way", nullable=true, length=60)	
	public String getCollectWay() {
		return collectWay;
	}

	public void setChargeStandards(BigDecimal chargeStandards) {
		this.chargeStandards = chargeStandards;
	}

	@Column(name="charge_standards", nullable=true, length=22)
	public BigDecimal getChargeStandards() {
		return chargeStandards;
	}

	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}

	@Column(name="charge_unit", nullable=true, length=50)	
	public String getChargeUnit() {
		return chargeUnit;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	@Column(name="receive_amount", nullable=true, length=22)	
	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setUnconfirmAmount(BigDecimal unconfirmAmount) {
		this.unconfirmAmount = unconfirmAmount;
	}

	@Column(name="unconfirm_amount", nullable=true, length=22)	
	public BigDecimal getUnconfirmAmount() {
		return unconfirmAmount;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	@Column(name="difference", nullable=true, length=22)	
	public BigDecimal getDifference() {
		return difference;
	}

	public void setSourceFilepath(String sourceFilepath) {
		this.sourceFilepath = sourceFilepath;
	}

	@Column(name="source_filepath", nullable=true, length=200)	
	public String getSourceFilepath() {
		return sourceFilepath;
	}

	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	@Column(name="source_filename", nullable=true, length=100)	
	public String getSourceFilename() {
		return sourceFilename;
	}

	public void setCollectSelect(String collectSelect) {
		this.collectSelect = collectSelect;
	}

	@Column(name="collect_select", nullable=true, length=50)	
	public String getCollectSelect() {
		return collectSelect;
	}

	public void setPurchaseAct(String purchaseAct) {
		this.purchaseAct = purchaseAct;
	}

	@Column(name="purchase_act", nullable=true, length=50)	
	public String getPurchaseAct() {
		return purchaseAct;
	}

	public void setExemptionReason(String exemptionReason) {
		this.exemptionReason = exemptionReason;
	}

	@Column(name="exemption_reason", nullable=true, length=255)	
	public String getExemptionReason() {
		return exemptionReason;
	}

	public void setExemptionReason2(String exemptionReason2) {
		this.exemptionReason2 = exemptionReason2;
	}

	@Column(name="exemption_reason2", nullable=true, length=255)	
	public String getExemptionReason2() {
		return exemptionReason2;
	}

	public void setDebitOrderCode(String debitOrderCode) {
		this.debitOrderCode = debitOrderCode;
	}

	@Column(name="debit_order_code", nullable=true, length=50)	
	public String getDebitOrderCode() {
		return debitOrderCode;
	}

	public void setAboiReceipts(BigDecimal aboiReceipts) {
		this.aboiReceipts = aboiReceipts;
	}

	@Column(name="aboi_receipts", nullable=true, length=22)	
	public BigDecimal getAboiReceipts() {
		return aboiReceipts;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	@Column(name="batch_code", nullable=true, length=255)	
	public String getBatchCode() {
		return batchCode;
	}

	public void setTransferInPerson(Integer transferInPerson) {
		this.transferInPerson = transferInPerson;
	}

	@Column(name="transfer_in_person", nullable=true, length=22)	
	public Integer getTransferInPerson() {
		return transferInPerson;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=255)	
	public String getRemark() {
		return remark;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=22)	
	public Integer getStatus() {
		return status;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name="parent_id", nullable=true, length=22)	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentVendorCode(String parentVendorCode) {
		this.parentVendorCode = parentVendorCode;
	}

	@Column(name="parent_vendor_code", nullable=true, length=255)	
	public String getParentVendorCode() {
		return parentVendorCode;
	}

	public void setParentVendorName(String parentVendorName) {
		this.parentVendorName = parentVendorName;
	}

	@Column(name="parent_vendor_name", nullable=true, length=255)	
	public String getParentVendorName() {
		return parentVendorName;
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

	public void setTransferOutPerson(Integer transferOutPerson) {
		this.transferOutPerson = transferOutPerson;
	}

	@Column(name="transfer_out_person", nullable=true, length=22)	
	public Integer getTransferOutPerson() {
		return transferOutPerson;
	}

	public void setTransferLastPerson(Integer transferLastPerson) {
		this.transferLastPerson = transferLastPerson;
	}

	@Column(name="transfer_last_person", nullable=true, length=22)	
	public Integer getTransferLastPerson() {
		return transferLastPerson;
	}

	public void setTransferInDate(Date transferInDate) {
		this.transferInDate = transferInDate;
	}

	@Column(name="transfer_in_date", nullable=true, length=7)	
	public Date getTransferInDate() {
		return transferInDate;
	}

	public void setTransferOutDate(Date transferOutDate) {
		this.transferOutDate = transferOutDate;
	}

	@Column(name="transfer_out_date", nullable=true, length=7)	
	public Date getTransferOutDate() {
		return transferOutDate;
	}

	public void setTransferLastDate(Date transferLastDate) {
		this.transferLastDate = transferLastDate;
	}

	@Column(name="transfer_last_date", nullable=true, length=7)	
	public Date getTransferLastDate() {
		return transferLastDate;
	}

	public void setChargeUnitName(String chargeUnitName) {
		this.chargeUnitName = chargeUnitName;
	}

	@Column(name="charge_unit_name", nullable=true, length=100)	
	public String getChargeUnitName() {
		return chargeUnitName;
	}

	public void setOriginalBeforeAmount(BigDecimal originalBeforeAmount) {
		this.originalBeforeAmount = originalBeforeAmount;
	}

	@Column(name="original_before_amount", nullable=true, length=22)	
	public BigDecimal getOriginalBeforeAmount() {
		return originalBeforeAmount;
	}

	public void setAdditionRate(Integer additionRate) {
		this.additionRate = additionRate;
	}

	@Column(name="addition_rate", nullable=true, length=22)	
	public Integer getAdditionRate() {
		return additionRate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="group_code", nullable=true, length=100)
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="dept_code", nullable=true, length=100)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="is_create", nullable=true, length=50)
	public String getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	@Column(name="receive_amount_add", nullable=true, length=22)
	public BigDecimal getReceiveAmountAdd() {
		return receiveAmountAdd;
	}

	public void setReceiveAmountAdd(BigDecimal receiveAmountAdd) {
		this.receiveAmountAdd = receiveAmountAdd;
	}

	@Column(name="charge_method", nullable=true, length=30)
	public String getChargeMethod() {
		return chargeMethod;
	}

	public void setChargeMethod(String chargeMethod) {
		this.chargeMethod = chargeMethod;
	}

	@Column(name="ANNUAL_MONTH_N", nullable=true, length=22)
	public Integer getAnnualMonthN() {
		return annualMonthN;
	}

	public void setAnnualMonthN(Integer annualMonthN) {
		this.annualMonthN = annualMonthN;
	}

	@Column(name="MONTH_N", nullable=true, length=22)
	public Integer getMonthN() {
		return monthN;
	}

	public void setMonthN(Integer monthN) {
		this.monthN = monthN;
	}

	@Column(name="unconfirm_amount_a", nullable=true, length=22)
	public BigDecimal getUnconfirmAmountA() {
		return unconfirmAmountA;
	}

	public void setUnconfirmAmountA(BigDecimal unconfirmAmountA) {
		this.unconfirmAmountA = unconfirmAmountA;
	}

	@Column(name="process_id")
	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	@Column(name="file_id")
	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="affirm_tot_store_count", nullable=true, length=22)
	public BigDecimal getAffirmTotStoreCount() {
		return affirmTotStoreCount;
	}

	public void setAffirmTotStoreCount(BigDecimal affirmTotStoreCount) {
		this.affirmTotStoreCount = affirmTotStoreCount;
	}

	@Column(name="add_receive_amount", nullable=true, length=22)
	public BigDecimal getAddReceiveAmount() {
		return addReceiveAmount;
	}

	public void setAddReceiveAmount(BigDecimal addReceiveAmount) {
		this.addReceiveAmount = addReceiveAmount;
	}

	@Column(name="adjust_by", nullable=true, length=22)
	public Integer getAdjustBy() {
		return adjustBy;
	}

	public void setAdjustBy(Integer adjustBy) {
		this.adjustBy = adjustBy;
	}

	@Column(name="adjust_amount", nullable=true, length=22)
	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	@Column(name="adjust_receive_amount", nullable=true, length=22)
	public BigDecimal getAdjustReceiveAmount() {
		return adjustReceiveAmount;
	}

	public void setAdjustReceiveAmount(BigDecimal adjustReceiveAmount) {
		this.adjustReceiveAmount = adjustReceiveAmount;
	}

	@Column(name="type", nullable=true, length=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="no_adjust_receive_amount", nullable=true, length=22)
	public BigDecimal getNoAdjustReceiveAmount() {
		return noAdjustReceiveAmount;
	}

	public void setNoAdjustReceiveAmount(BigDecimal noAdjustReceiveAmount) {
		this.noAdjustReceiveAmount = noAdjustReceiveAmount;
	}

	@Column(name="prior_vendor_code", nullable=true, length=80)
	public String getPriorVendorCode() {
		return priorVendorCode;
	}

	public void setPriorVendorCode(String priorVendorCode) {
		this.priorVendorCode = priorVendorCode;
	}

	@Column(name="prior_vendor_name", nullable=true, length=250)
	public String getPriorVendorName() {
		return priorVendorName;
	}

	public void setPriorVendorName(String priorVendorName) {
		this.priorVendorName = priorVendorName;
	}
}
