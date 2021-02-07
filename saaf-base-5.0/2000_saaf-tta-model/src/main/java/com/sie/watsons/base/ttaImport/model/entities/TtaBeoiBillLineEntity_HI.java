package com.sie.watsons.base.ttaImport.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaBeoiBillLineEntity_HI Entity Object
 * Fri Oct 18 18:05:16 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_beoi_bill_line")
public class TtaBeoiBillLineEntity_HI {
    private Integer ttaBeoiBillImportId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date accountMonth;
    private String rmsCode;
    private String venderName;
    private String bookInJv;
    private String bookInJv1;
    private String buyer;
    private String tc;
    private String department;
    private String brand;
    private String venderab;
    private String userContractId;
    private String cooperateStatus;
    private String venderType;
    private String familyPlaningFlag;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date validBeginDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date validEndDate;
    private String pickUpRate;
    private String venderContainTax;
    private String purchase;
    private String goodsReturn;
    private String netPur;
    private String dsd;
    private String purchaseOrigin;
    private String goodsReturnOrigin;
    private String netPurOrigin;
    private String pyPurchase;
    private String pyGoodsReturn;
    private String pyNetPur;
    private String pydsd;
    private String monthPur;
    private String monthCount;
    private String monthAbi;
    private String monthAbiOntop;
    private String yearAbi;
    private String yearAbiOntop;
    private String contractAdj;
    private String billAdj;
    private String yearContractAdj;
    private String yearBillAdj;
    private String yearPurchaseCount;
    private String apOiTotalTax;
    private String apOiTotalNotax;
    private String tax16;
    private String noTax13;
    private String tax13;
    private String noTax10;
    private String tax10;
    private String purDisctContractText;
    private String purDisctContractValue;
    private String purDisctSumMoney;
    private String purDisctContractAdj;
    private String purDisctReconAdj;
    private String purDisctLastyearConAdj;
    private String purDisctLastyearRecAdj;
    private String purDisctLastyearAudAdj;
    private String purDisctMemo;
    private String purDisctIncludeTax;
    private String purDisctIncludeNotax;
    private String earlypayDisctContractText;
    private String earlypayDisctContractValue;
    private String earlypayDisctSumMoney;
    private String earlypayDisctContractAdj;
    private String earlypayDisctReconAdj;
    private String earlypayDisLastyearConAdj;
    private String earlypayDisLastyearRecAdj;
    private String earlypayDisLastyearAudAdj;
    private String earlypayDisctMemo;
    private String earlypayDisctIncludeTax;
    private String earlypayDisctIncludeNotax;
    private String promotDisctContractText;
    private String promotDisctContractValue;
    private String promotDisctSumMoney;
    private String promotDisctContractAdj;
    private String promotDisctReconAdj;
    private String promotDisctLastyearConAdj;
    private String promotDisctLastyearRecAdj;
    private String promotDisctLastyearAudAdj;
    private String promotDisctMemo;
    private String promotDisctIncludeTax;
    private String promotDisctIncludeNotax;
    private String distriDisctContractText;
    private String distriDisctContractValue;
    private String distriDisctSumMoney;
    private String distriDisctContractAdj;
    private String distriDisctReconAdj;
    private String distriDisctLastyearConAdj;
    private String distriDisctLastyearRecAdj;
    private String distriDisctLastyearAudAdj;
    private String distriDisctMemo;
    private String distriDisctIncludeTax;
    private String distriDisctIncludeNotax;
    private String dgdsDisctContractText;
    private String dgdsDisctContractValue;
    private String dgdsDisctSumMoney;
    private String dgdsDisctContractAdj;
    private String dgdsDisctReconAdj;
    private String dgdsDisctLastyearConAdj;
    private String dgdsDisctLastyearRecAdj;
    private String dgdsDisctLastyearAudAdj;
    private String dgdsDisctMemo;
    private String dgdsDisctIncludeTax;
    private String dgdsDisctIncludeNotax;
    private String itrDisctContractText;
    private String itrDisctContractValue;
    private String itrDisctSumMoney;
    private String itrDisctContractAdj;
    private String itrDisctReconAdj;
    private String itrDisctLastyearConAdj;
    private String itrDisctLastyearRecAdj;
    private String itrDisctLastyearAudAdj;
    private String itrDisctMemo;
    private String itrDisctIncludeTax;
    private String itrDisctIncludeNotax;
    private String bsdtDisctContractText;
    private String bsdtDisctContractValue;
    private String bsdtDisctSumMoney;
    private String bsdtDisctContractAdj;
    private String bsdtDisctReconAdj;
    private String bsdtDisctLastyearConAdj;
    private String bsdtDisctLastyearRecAdj;
    private String bsdtDisctLastyearAudAdj;
    private String bsdtDisctMemo;
    private String bsdtDisctIncludeTax;
    private String bsdtDisctIncludeNotax;
    private String ldiDisctContractText;
    private String ldiDisctContractValue;
    private String ldiDisctSumMoney;
    private String ldiDisctContractAdj;
    private String ldiDisctReconAdj;
    private String ldiDisctLastyearConAdj;
    private String ldiDisctLastyearRecAdj;
    private String ldiDisctLastyearAudAdj;
    private String ldiDisctMemo;
    private String ldiDisctIncludeTax;
    private String ldiDisctIncludeNotax;
    private String nonfpDisctContractText;
    private String nonfpDisctContractValue;
    private String nonfpDisctSumMoney;
    private String nonfpDisctContractAdj;
    private String nonfpDisctReconAdj;
    private String nonfpDisctLastyearConAdj;
    private String nonfpDisctLastyearRecAdj;
    private String nonfpDisctLastyearAudAdj;
    private String nonfpDisctMemo;
    private String nonfpDisctIncludeTax;
    private String nonfpDisctIncludeNotax;
    private String couDisctContractText;
    private String couDisctContractValue;
    private String couDisctSumMoney;
    private String couDisctContractAdj;
    private String couDisctReconAdj;
    private String couDisctLastyearConAdj;
    private String couDisctLastyearRecAdj;
    private String couDisctLastyearAudAdj;
    private String couDisctMemo;
    private String couDisctIncludeTax;
    private String couDisctIncludeNotax;
    private String vipDisctContractText;
    private String vipDisctContractValue;
    private String vipDisctSumMoney;
    private String vipDisctContractAdj;
    private String vipDisctReconAdj;
    private String vipDisctLastyearConAdj;
    private String vipDisctLastyearRecAdj;
    private String vipDisctLastyearAudAdj;
    private String vipDisctMemo;
    private String vipDisctIncludeTax;
    private String vipDisctIncludeNotax;
    private String uncontrDisctContractText;
    private String uncontrDisctContractValue;
    private String uncontrDisctSumMoney;
    private String uncontrDisctContractAdj;
    private String uncontrDisctReconAdj;
    private String uncontrDisLastyearConAdj;
    private String uncontrDisLastyearRecAdj;
    private String uncontrDisLastyearAudAdj;
    private String uncontrDisctMemo;
    private String uncontrDisctIncludeTax;
    private String uncontrDisctIncludeNotax;
    private String costRedIncome;
    private String costRedIncomeValue;
    private String costRedIncomeCurmonTta;
    private String costRedIncomeCurmonOntop;
    private String costRedIncomePyyearTta;
    private String costRedIncomePyyearOntop;
    private String costRedContractAdj;
    private String costRedReconAdj;
    private String costRedLastyearConAdj;
    private String costRedLastyearRecAdj;
    private String costRedLastyearAudAdj;
    private String costRedMemo;
    private String costRedIncludeTax;
    private String costDisctExcludeNotax;
    private String otherOem;
    private String otherOemValue;
    private String otherOemMonthTta;
    private String otherOemMonthOntop;
    private String otherOemPyyearTta;
    private String otherOemPyyearOntop;
    private String otherOemContractAdj;
    private String otherOemReconAdj;
    private String otherOemLastyearConAdj;
    private String otherOemLastyearRecAdj;
    private String otherOemLastyearAudAdj;
    private String otherOemMemo;
    private String otherOemIncludeTax;
    private String otherOemExcludeNotax;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String apOiTotalNotax1;
    private String tax1;
    private String pfc01DisctContractText;
    private String pfc01DisctContractValue;
    private String pfc01DisctSumMoney;
    private String pfc01DisctContractAdj;
    private String pfc01DisctReconAdj;
    private String pfc01DisLastyearConAdj;
    private String pfc01DisLastyearRecAdj;
    private String pfc01DisLastyearAudAdj;
    private String pfc01DisctMemo;
    private String pfc01DisctIncludeTax;
    private String pfc01DisctIncludeNotax;
    private String ddb01DisctContractText;
    private String ddb01DisctContractValue;
    private String ddb01DisctSumMoney;
    private String ddb01DisctContractAdj;
    private String ddb01DisctReconAdj;
    private String ddb01DisLastyearConAdj;
    private String ddb01DisLastyearRecAdj;
    private String ddb01DisLastyearAudAdj;
    private String ddb01DisctMemo;
    private String ddb01DisctIncludeTax;
    private String ddb01DisctIncludeNotax;
    private String rgt01DisctContractText;
    private String rgt01DisctContractValue;
    private String rgt01DisctSumMoney;
    private String rgt01DisctContractAdj;
    private String rgt01DisctReconAdj;
    private String rgt01DisLastyearConAdj;
    private String rgt01DisLastyearRecAdj;
    private String rgt01DisLastyearAudAdj;
    private String rgt01DisctMemo;
    private String rgt01DisctIncludeTax;
    private String rgt01DisctIncludeNotax;
    private String bdf01DisctContractText;
    private String bdf01DisctContractValue;
    private String bdf01DisctSumMoney;
    private String bdf01DisctContractAdj;
    private String bdf01DisctReconAdj;
    private String bdf01DisLastyearConAdj;
    private String bdf01DisLastyearRecAdj;
    private String bdf01DisLastyearAudAdj;
    private String bdf01DisctMemo;
    private String bdf01DisctIncludeTax;
    private String bdf01DisctIncludeNotax;
    private String oic01RedIncome;
    private String oic01RedIncomeValue;
    private String oic01RedIncomeCurmonTta;
    private String oic01RedIncomeCurmonOntop;
    private String oic01RedIncomePyyearTta;
    private String oic01RedIncomePyyearOntop;
    private String oic01RedContractAdj;
    private String oic01RedReconAdj;
    private String oic01RedLastyearConAdj;
    private String oic01RedLastyearRecAdj;
    private String oic01RedLastyearAudAdj;
    private String oic01RedMemo;
    private String oic01RedIncludeTax;
    private String oic01RedIncludeNotax;
    private String psf01DisctContractText;
    private String psf01DisctContractValue;
    private String psf01DisctSumMoney;
    private String psf01DisctContractAdj;
    private String psf01DisctReconAdj;
    private String psf01DisLastyearConAdj;
    private String psf01DisLastyearRecAdj;
    private String psf01DisLastyearAudAdj;
    private String psf01DisctMemo;
    private String psf01DisctIncludeTax;
    private String psf01DisctIncludeNotax;
    private Integer operatorUserId;

	public void setTtaBeoiBillImportId(Integer ttaBeoiBillImportId) {
		this.ttaBeoiBillImportId = ttaBeoiBillImportId;
	}

    @Id
    @SequenceGenerator(name="SEQ_TTA_BEOI_BILL_LINE", sequenceName="SEQ_TTA_BEOI_BILL_LINE", allocationSize=1, initialValue=1)
    @GeneratedValue(generator="SEQ_TTA_BEOI_BILL_LINE",strategy=GenerationType.SEQUENCE)
    @Column(name="tta_beoi_bill_import_id", nullable=false, length=22)
	public Integer getTtaBeoiBillImportId() {
		return ttaBeoiBillImportId;
	}

	public void setAccountMonth(Date accountMonth) {
		this.accountMonth = accountMonth;
	}

	@Column(name="account_month", nullable=true, length=7)	
	public Date getAccountMonth() {
		return accountMonth;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name="rms_code", nullable=true, length=20)	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	@Column(name="vender_name", nullable=true, length=500)	
	public String getVenderName() {
		return venderName;
	}

	public void setBookInJv(String bookInJv) {
		this.bookInJv = bookInJv;
	}

	@Column(name="book_in_jv", nullable=true, length=200)	
	public String getBookInJv() {
		return bookInJv;
	}

	public void setBookInJv1(String bookInJv1) {
		this.bookInJv1 = bookInJv1;
	}

	@Column(name="book_in_jv1", nullable=true, length=200)	
	public String getBookInJv1() {
		return bookInJv1;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	@Column(name="buyer", nullable=true, length=200)	
	public String getBuyer() {
		return buyer;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	@Column(name="tc", nullable=true, length=200)	
	public String getTc() {
		return tc;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="department", nullable=true, length=200)	
	public String getDepartment() {
		return department;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=200)	
	public String getBrand() {
		return brand;
	}

	public void setVenderab(String venderab) {
		this.venderab = venderab;
	}

	@Column(name="venderab", nullable=true, length=2)	
	public String getVenderab() {
		return venderab;
	}

	public void setUserContractId(String userContractId) {
		this.userContractId = userContractId;
	}

	@Column(name="user_contract_id", nullable=true, length=200)	
	public String getUserContractId() {
		return userContractId;
	}

	public void setCooperateStatus(String cooperateStatus) {
		this.cooperateStatus = cooperateStatus;
	}

	@Column(name="cooperate_status", nullable=true, length=100)	
	public String getCooperateStatus() {
		return cooperateStatus;
	}

	public void setVenderType(String venderType) {
		this.venderType = venderType;
	}

	@Column(name="vender_type", nullable=true, length=100)	
	public String getVenderType() {
		return venderType;
	}

	public void setFamilyPlaningFlag(String familyPlaningFlag) {
		this.familyPlaningFlag = familyPlaningFlag;
	}

	@Column(name="family_planing_flag", nullable=true, length=10)	
	public String getFamilyPlaningFlag() {
		return familyPlaningFlag;
	}

	public void setValidBeginDate(Date validBeginDate) {
		this.validBeginDate = validBeginDate;
	}

	@Column(name="valid_begin_date", nullable=true, length=7)	
	public Date getValidBeginDate() {
		return validBeginDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	@Column(name="valid_end_date", nullable=true, length=7)	
	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setPickUpRate(String pickUpRate) {
		this.pickUpRate = pickUpRate;
	}

	@Column(name="pick_up_rate", nullable=true, length=200)	
	public String getPickUpRate() {
		return pickUpRate;
	}

	public void setVenderContainTax(String venderContainTax) {
		this.venderContainTax = venderContainTax;
	}

	@Column(name="vender_contain_tax", nullable=true, length=200)	
	public String getVenderContainTax() {
		return venderContainTax;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	@Column(name="purchase", nullable=true, length=200)	
	public String getPurchase() {
		return purchase;
	}

	public void setGoodsReturn(String goodsReturn) {
		this.goodsReturn = goodsReturn;
	}

	@Column(name="goods_return", nullable=true, length=200)	
	public String getGoodsReturn() {
		return goodsReturn;
	}

	public void setNetPur(String netPur) {
		this.netPur = netPur;
	}

	@Column(name="net_pur", nullable=true, length=200)	
	public String getNetPur() {
		return netPur;
	}

	public void setDsd(String dsd) {
		this.dsd = dsd;
	}

	@Column(name="dsd", nullable=true, length=200)	
	public String getDsd() {
		return dsd;
	}

	public void setPurchaseOrigin(String purchaseOrigin) {
		this.purchaseOrigin = purchaseOrigin;
	}

	@Column(name="purchase_origin", nullable=true, length=200)	
	public String getPurchaseOrigin() {
		return purchaseOrigin;
	}

	public void setGoodsReturnOrigin(String goodsReturnOrigin) {
		this.goodsReturnOrigin = goodsReturnOrigin;
	}

	@Column(name="goods_return_origin", nullable=true, length=200)	
	public String getGoodsReturnOrigin() {
		return goodsReturnOrigin;
	}

	public void setNetPurOrigin(String netPurOrigin) {
		this.netPurOrigin = netPurOrigin;
	}

	@Column(name="net_pur_origin", nullable=true, length=200)	
	public String getNetPurOrigin() {
		return netPurOrigin;
	}

	public void setPyPurchase(String pyPurchase) {
		this.pyPurchase = pyPurchase;
	}

	@Column(name="py_purchase", nullable=true, length=200)	
	public String getPyPurchase() {
		return pyPurchase;
	}

	public void setPyGoodsReturn(String pyGoodsReturn) {
		this.pyGoodsReturn = pyGoodsReturn;
	}

	@Column(name="py_goods_return", nullable=true, length=200)	
	public String getPyGoodsReturn() {
		return pyGoodsReturn;
	}

	public void setPyNetPur(String pyNetPur) {
		this.pyNetPur = pyNetPur;
	}

	@Column(name="py_net_pur", nullable=true, length=200)	
	public String getPyNetPur() {
		return pyNetPur;
	}

	public void setPydsd(String pydsd) {
		this.pydsd = pydsd;
	}

	@Column(name="pydsd", nullable=true, length=200)	
	public String getPydsd() {
		return pydsd;
	}

	public void setMonthPur(String monthPur) {
		this.monthPur = monthPur;
	}

	@Column(name="month_pur", nullable=true, length=100)	
	public String getMonthPur() {
		return monthPur;
	}

	public void setMonthCount(String monthCount) {
		this.monthCount = monthCount;
	}

	@Column(name="month_count", nullable=true, length=100)	
	public String getMonthCount() {
		return monthCount;
	}

	public void setMonthAbi(String monthAbi) {
		this.monthAbi = monthAbi;
	}

	@Column(name="month_abi", nullable=true, length=100)	
	public String getMonthAbi() {
		return monthAbi;
	}

	public void setMonthAbiOntop(String monthAbiOntop) {
		this.monthAbiOntop = monthAbiOntop;
	}

	@Column(name="month_abi_ontop", nullable=true, length=100)	
	public String getMonthAbiOntop() {
		return monthAbiOntop;
	}

	public void setYearAbi(String yearAbi) {
		this.yearAbi = yearAbi;
	}

	@Column(name="year_abi", nullable=true, length=100)	
	public String getYearAbi() {
		return yearAbi;
	}

	public void setYearAbiOntop(String yearAbiOntop) {
		this.yearAbiOntop = yearAbiOntop;
	}

	@Column(name="year_abi_ontop", nullable=true, length=100)	
	public String getYearAbiOntop() {
		return yearAbiOntop;
	}

	public void setContractAdj(String contractAdj) {
		this.contractAdj = contractAdj;
	}

	@Column(name="contract_adj", nullable=true, length=100)	
	public String getContractAdj() {
		return contractAdj;
	}

	public void setBillAdj(String billAdj) {
		this.billAdj = billAdj;
	}

	@Column(name="bill_adj", nullable=true, length=100)	
	public String getBillAdj() {
		return billAdj;
	}

	public void setYearContractAdj(String yearContractAdj) {
		this.yearContractAdj = yearContractAdj;
	}

	@Column(name="year_contract_adj", nullable=true, length=100)	
	public String getYearContractAdj() {
		return yearContractAdj;
	}

	public void setYearBillAdj(String yearBillAdj) {
		this.yearBillAdj = yearBillAdj;
	}

	@Column(name="year_bill_adj", nullable=true, length=100)	
	public String getYearBillAdj() {
		return yearBillAdj;
	}

	public void setYearPurchaseCount(String yearPurchaseCount) {
		this.yearPurchaseCount = yearPurchaseCount;
	}

	@Column(name="year_purchase_count", nullable=true, length=100)	
	public String getYearPurchaseCount() {
		return yearPurchaseCount;
	}

	public void setApOiTotalTax(String apOiTotalTax) {
		this.apOiTotalTax = apOiTotalTax;
	}

	@Column(name="ap_oi_total_tax", nullable=true, length=100)	
	public String getApOiTotalTax() {
		return apOiTotalTax;
	}

	public void setApOiTotalNotax(String apOiTotalNotax) {
		this.apOiTotalNotax = apOiTotalNotax;
	}

	@Column(name="ap_oi_total_notax", nullable=true, length=50)	
	public String getApOiTotalNotax() {
		return apOiTotalNotax;
	}

	public void setTax16(String tax16) {
		this.tax16 = tax16;
	}

	@Column(name="tax16", nullable=true, length=50)	
	public String getTax16() {
		return tax16;
	}

	public void setNoTax13(String noTax13) {
		this.noTax13 = noTax13;
	}

	@Column(name="no_tax13", nullable=true, length=50)	
	public String getNoTax13() {
		return noTax13;
	}

	public void setTax13(String tax13) {
		this.tax13 = tax13;
	}

	@Column(name="tax13", nullable=true, length=50)	
	public String getTax13() {
		return tax13;
	}

	public void setNoTax10(String noTax10) {
		this.noTax10 = noTax10;
	}

	@Column(name="no_tax10", nullable=true, length=50)	
	public String getNoTax10() {
		return noTax10;
	}

	public void setTax10(String tax10) {
		this.tax10 = tax10;
	}

	@Column(name="tax10", nullable=true, length=50)	
	public String getTax10() {
		return tax10;
	}

	public void setPurDisctContractText(String purDisctContractText) {
		this.purDisctContractText = purDisctContractText;
	}

	@Column(name="pur_disct_contract_text", nullable=true, length=500)	
	public String getPurDisctContractText() {
		return purDisctContractText;
	}

	public void setPurDisctContractValue(String purDisctContractValue) {
		this.purDisctContractValue = purDisctContractValue;
	}

	@Column(name="pur_disct_contract_value", nullable=true, length=50)	
	public String getPurDisctContractValue() {
		return purDisctContractValue;
	}

	public void setPurDisctSumMoney(String purDisctSumMoney) {
		this.purDisctSumMoney = purDisctSumMoney;
	}

	@Column(name="pur_disct_sum_money", nullable=true, length=50)	
	public String getPurDisctSumMoney() {
		return purDisctSumMoney;
	}

	public void setPurDisctContractAdj(String purDisctContractAdj) {
		this.purDisctContractAdj = purDisctContractAdj;
	}

	@Column(name="pur_disct_contract_adj", nullable=true, length=50)	
	public String getPurDisctContractAdj() {
		return purDisctContractAdj;
	}

	public void setPurDisctReconAdj(String purDisctReconAdj) {
		this.purDisctReconAdj = purDisctReconAdj;
	}

	@Column(name="pur_disct_recon_adj", nullable=true, length=50)	
	public String getPurDisctReconAdj() {
		return purDisctReconAdj;
	}

	public void setPurDisctLastyearConAdj(String purDisctLastyearConAdj) {
		this.purDisctLastyearConAdj = purDisctLastyearConAdj;
	}

	@Column(name="pur_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getPurDisctLastyearConAdj() {
		return purDisctLastyearConAdj;
	}

	public void setPurDisctLastyearRecAdj(String purDisctLastyearRecAdj) {
		this.purDisctLastyearRecAdj = purDisctLastyearRecAdj;
	}

	@Column(name="pur_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getPurDisctLastyearRecAdj() {
		return purDisctLastyearRecAdj;
	}

	public void setPurDisctLastyearAudAdj(String purDisctLastyearAudAdj) {
		this.purDisctLastyearAudAdj = purDisctLastyearAudAdj;
	}

	@Column(name="pur_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getPurDisctLastyearAudAdj() {
		return purDisctLastyearAudAdj;
	}

	public void setPurDisctMemo(String purDisctMemo) {
		this.purDisctMemo = purDisctMemo;
	}

	@Column(name="pur_disct_memo", nullable=true, length=500)	
	public String getPurDisctMemo() {
		return purDisctMemo;
	}

	public void setPurDisctIncludeTax(String purDisctIncludeTax) {
		this.purDisctIncludeTax = purDisctIncludeTax;
	}

	@Column(name="pur_disct_include_tax", nullable=true, length=50)	
	public String getPurDisctIncludeTax() {
		return purDisctIncludeTax;
	}

	public void setPurDisctIncludeNotax(String purDisctIncludeNotax) {
		this.purDisctIncludeNotax = purDisctIncludeNotax;
	}

	@Column(name="pur_disct_include_notax", nullable=true, length=50)	
	public String getPurDisctIncludeNotax() {
		return purDisctIncludeNotax;
	}

	public void setEarlypayDisctContractText(String earlypayDisctContractText) {
		this.earlypayDisctContractText = earlypayDisctContractText;
	}

	@Column(name="earlypay_disct_contract_text", nullable=true, length=500)	
	public String getEarlypayDisctContractText() {
		return earlypayDisctContractText;
	}

	public void setEarlypayDisctContractValue(String earlypayDisctContractValue) {
		this.earlypayDisctContractValue = earlypayDisctContractValue;
	}

	@Column(name="earlypay_disct_contract_value", nullable=true, length=50)	
	public String getEarlypayDisctContractValue() {
		return earlypayDisctContractValue;
	}

	public void setEarlypayDisctSumMoney(String earlypayDisctSumMoney) {
		this.earlypayDisctSumMoney = earlypayDisctSumMoney;
	}

	@Column(name="earlypay_disct_sum_money", nullable=true, length=50)	
	public String getEarlypayDisctSumMoney() {
		return earlypayDisctSumMoney;
	}

	public void setEarlypayDisctContractAdj(String earlypayDisctContractAdj) {
		this.earlypayDisctContractAdj = earlypayDisctContractAdj;
	}

	@Column(name="earlypay_disct_contract_adj", nullable=true, length=50)	
	public String getEarlypayDisctContractAdj() {
		return earlypayDisctContractAdj;
	}

	public void setEarlypayDisctReconAdj(String earlypayDisctReconAdj) {
		this.earlypayDisctReconAdj = earlypayDisctReconAdj;
	}

	@Column(name="earlypay_disct_recon_adj", nullable=true, length=50)	
	public String getEarlypayDisctReconAdj() {
		return earlypayDisctReconAdj;
	}

	public void setEarlypayDisLastyearConAdj(String earlypayDisLastyearConAdj) {
		this.earlypayDisLastyearConAdj = earlypayDisLastyearConAdj;
	}

	@Column(name="earlypay_dis_lastyear_con_adj", nullable=true, length=50)	
	public String getEarlypayDisLastyearConAdj() {
		return earlypayDisLastyearConAdj;
	}

	public void setEarlypayDisLastyearRecAdj(String earlypayDisLastyearRecAdj) {
		this.earlypayDisLastyearRecAdj = earlypayDisLastyearRecAdj;
	}

	@Column(name="earlypay_dis_lastyear_rec_adj", nullable=true, length=50)	
	public String getEarlypayDisLastyearRecAdj() {
		return earlypayDisLastyearRecAdj;
	}

	public void setEarlypayDisLastyearAudAdj(String earlypayDisLastyearAudAdj) {
		this.earlypayDisLastyearAudAdj = earlypayDisLastyearAudAdj;
	}

	@Column(name="earlypay_dis_lastyear_aud_adj", nullable=true, length=50)	
	public String getEarlypayDisLastyearAudAdj() {
		return earlypayDisLastyearAudAdj;
	}

	public void setEarlypayDisctMemo(String earlypayDisctMemo) {
		this.earlypayDisctMemo = earlypayDisctMemo;
	}

	@Column(name="earlypay_disct_memo", nullable=true, length=500)	
	public String getEarlypayDisctMemo() {
		return earlypayDisctMemo;
	}

	public void setEarlypayDisctIncludeTax(String earlypayDisctIncludeTax) {
		this.earlypayDisctIncludeTax = earlypayDisctIncludeTax;
	}

	@Column(name="earlypay_disct_include_tax", nullable=true, length=50)	
	public String getEarlypayDisctIncludeTax() {
		return earlypayDisctIncludeTax;
	}

	public void setEarlypayDisctIncludeNotax(String earlypayDisctIncludeNotax) {
		this.earlypayDisctIncludeNotax = earlypayDisctIncludeNotax;
	}

	@Column(name="earlypay_disct_include_notax", nullable=true, length=50)	
	public String getEarlypayDisctIncludeNotax() {
		return earlypayDisctIncludeNotax;
	}

	public void setPromotDisctContractText(String promotDisctContractText) {
		this.promotDisctContractText = promotDisctContractText;
	}

	@Column(name="promot_disct_contract_text", nullable=true, length=500)	
	public String getPromotDisctContractText() {
		return promotDisctContractText;
	}

	public void setPromotDisctContractValue(String promotDisctContractValue) {
		this.promotDisctContractValue = promotDisctContractValue;
	}

	@Column(name="promot_disct_contract_value", nullable=true, length=50)	
	public String getPromotDisctContractValue() {
		return promotDisctContractValue;
	}

	public void setPromotDisctSumMoney(String promotDisctSumMoney) {
		this.promotDisctSumMoney = promotDisctSumMoney;
	}

	@Column(name="promot_disct_sum_money", nullable=true, length=50)	
	public String getPromotDisctSumMoney() {
		return promotDisctSumMoney;
	}

	public void setPromotDisctContractAdj(String promotDisctContractAdj) {
		this.promotDisctContractAdj = promotDisctContractAdj;
	}

	@Column(name="promot_disct_contract_adj", nullable=true, length=50)	
	public String getPromotDisctContractAdj() {
		return promotDisctContractAdj;
	}

	public void setPromotDisctReconAdj(String promotDisctReconAdj) {
		this.promotDisctReconAdj = promotDisctReconAdj;
	}

	@Column(name="promot_disct_recon_adj", nullable=true, length=50)	
	public String getPromotDisctReconAdj() {
		return promotDisctReconAdj;
	}

	public void setPromotDisctLastyearConAdj(String promotDisctLastyearConAdj) {
		this.promotDisctLastyearConAdj = promotDisctLastyearConAdj;
	}

	@Column(name="promot_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getPromotDisctLastyearConAdj() {
		return promotDisctLastyearConAdj;
	}

	public void setPromotDisctLastyearRecAdj(String promotDisctLastyearRecAdj) {
		this.promotDisctLastyearRecAdj = promotDisctLastyearRecAdj;
	}

	@Column(name="promot_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getPromotDisctLastyearRecAdj() {
		return promotDisctLastyearRecAdj;
	}

	public void setPromotDisctLastyearAudAdj(String promotDisctLastyearAudAdj) {
		this.promotDisctLastyearAudAdj = promotDisctLastyearAudAdj;
	}

	@Column(name="promot_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getPromotDisctLastyearAudAdj() {
		return promotDisctLastyearAudAdj;
	}

	public void setPromotDisctMemo(String promotDisctMemo) {
		this.promotDisctMemo = promotDisctMemo;
	}

	@Column(name="promot_disct_memo", nullable=true, length=500)	
	public String getPromotDisctMemo() {
		return promotDisctMemo;
	}

	public void setPromotDisctIncludeTax(String promotDisctIncludeTax) {
		this.promotDisctIncludeTax = promotDisctIncludeTax;
	}

	@Column(name="promot_disct_include_tax", nullable=true, length=50)	
	public String getPromotDisctIncludeTax() {
		return promotDisctIncludeTax;
	}

	public void setPromotDisctIncludeNotax(String promotDisctIncludeNotax) {
		this.promotDisctIncludeNotax = promotDisctIncludeNotax;
	}

	@Column(name="promot_disct_include_notax", nullable=true, length=50)	
	public String getPromotDisctIncludeNotax() {
		return promotDisctIncludeNotax;
	}

	public void setDistriDisctContractText(String distriDisctContractText) {
		this.distriDisctContractText = distriDisctContractText;
	}

	@Column(name="distri_disct_contract_text", nullable=true, length=500)	
	public String getDistriDisctContractText() {
		return distriDisctContractText;
	}

	public void setDistriDisctContractValue(String distriDisctContractValue) {
		this.distriDisctContractValue = distriDisctContractValue;
	}

	@Column(name="distri_disct_contract_value", nullable=true, length=50)	
	public String getDistriDisctContractValue() {
		return distriDisctContractValue;
	}

	public void setDistriDisctSumMoney(String distriDisctSumMoney) {
		this.distriDisctSumMoney = distriDisctSumMoney;
	}

	@Column(name="distri_disct_sum_money", nullable=true, length=50)	
	public String getDistriDisctSumMoney() {
		return distriDisctSumMoney;
	}

	public void setDistriDisctContractAdj(String distriDisctContractAdj) {
		this.distriDisctContractAdj = distriDisctContractAdj;
	}

	@Column(name="distri_disct_contract_adj", nullable=true, length=50)	
	public String getDistriDisctContractAdj() {
		return distriDisctContractAdj;
	}

	public void setDistriDisctReconAdj(String distriDisctReconAdj) {
		this.distriDisctReconAdj = distriDisctReconAdj;
	}

	@Column(name="distri_disct_recon_adj", nullable=true, length=50)	
	public String getDistriDisctReconAdj() {
		return distriDisctReconAdj;
	}

	public void setDistriDisctLastyearConAdj(String distriDisctLastyearConAdj) {
		this.distriDisctLastyearConAdj = distriDisctLastyearConAdj;
	}

	@Column(name="distri_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getDistriDisctLastyearConAdj() {
		return distriDisctLastyearConAdj;
	}

	public void setDistriDisctLastyearRecAdj(String distriDisctLastyearRecAdj) {
		this.distriDisctLastyearRecAdj = distriDisctLastyearRecAdj;
	}

	@Column(name="distri_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getDistriDisctLastyearRecAdj() {
		return distriDisctLastyearRecAdj;
	}

	public void setDistriDisctLastyearAudAdj(String distriDisctLastyearAudAdj) {
		this.distriDisctLastyearAudAdj = distriDisctLastyearAudAdj;
	}

	@Column(name="distri_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getDistriDisctLastyearAudAdj() {
		return distriDisctLastyearAudAdj;
	}

	public void setDistriDisctMemo(String distriDisctMemo) {
		this.distriDisctMemo = distriDisctMemo;
	}

	@Column(name="distri_disct_memo", nullable=true, length=500)	
	public String getDistriDisctMemo() {
		return distriDisctMemo;
	}

	public void setDistriDisctIncludeTax(String distriDisctIncludeTax) {
		this.distriDisctIncludeTax = distriDisctIncludeTax;
	}

	@Column(name="distri_disct_include_tax", nullable=true, length=50)	
	public String getDistriDisctIncludeTax() {
		return distriDisctIncludeTax;
	}

	public void setDistriDisctIncludeNotax(String distriDisctIncludeNotax) {
		this.distriDisctIncludeNotax = distriDisctIncludeNotax;
	}

	@Column(name="distri_disct_include_notax", nullable=true, length=50)	
	public String getDistriDisctIncludeNotax() {
		return distriDisctIncludeNotax;
	}

	public void setDgdsDisctContractText(String dgdsDisctContractText) {
		this.dgdsDisctContractText = dgdsDisctContractText;
	}

	@Column(name="dgds_disct_contract_text", nullable=true, length=500)	
	public String getDgdsDisctContractText() {
		return dgdsDisctContractText;
	}

	public void setDgdsDisctContractValue(String dgdsDisctContractValue) {
		this.dgdsDisctContractValue = dgdsDisctContractValue;
	}

	@Column(name="dgds_disct_contract_value", nullable=true, length=50)	
	public String getDgdsDisctContractValue() {
		return dgdsDisctContractValue;
	}

	public void setDgdsDisctSumMoney(String dgdsDisctSumMoney) {
		this.dgdsDisctSumMoney = dgdsDisctSumMoney;
	}

	@Column(name="dgds_disct_sum_money", nullable=true, length=50)	
	public String getDgdsDisctSumMoney() {
		return dgdsDisctSumMoney;
	}

	public void setDgdsDisctContractAdj(String dgdsDisctContractAdj) {
		this.dgdsDisctContractAdj = dgdsDisctContractAdj;
	}

	@Column(name="dgds_disct_contract_adj", nullable=true, length=50)	
	public String getDgdsDisctContractAdj() {
		return dgdsDisctContractAdj;
	}

	public void setDgdsDisctReconAdj(String dgdsDisctReconAdj) {
		this.dgdsDisctReconAdj = dgdsDisctReconAdj;
	}

	@Column(name="dgds_disct_recon_adj", nullable=true, length=50)	
	public String getDgdsDisctReconAdj() {
		return dgdsDisctReconAdj;
	}

	public void setDgdsDisctLastyearConAdj(String dgdsDisctLastyearConAdj) {
		this.dgdsDisctLastyearConAdj = dgdsDisctLastyearConAdj;
	}

	@Column(name="dgds_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getDgdsDisctLastyearConAdj() {
		return dgdsDisctLastyearConAdj;
	}

	public void setDgdsDisctLastyearRecAdj(String dgdsDisctLastyearRecAdj) {
		this.dgdsDisctLastyearRecAdj = dgdsDisctLastyearRecAdj;
	}

	@Column(name="dgds_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getDgdsDisctLastyearRecAdj() {
		return dgdsDisctLastyearRecAdj;
	}

	public void setDgdsDisctLastyearAudAdj(String dgdsDisctLastyearAudAdj) {
		this.dgdsDisctLastyearAudAdj = dgdsDisctLastyearAudAdj;
	}

	@Column(name="dgds_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getDgdsDisctLastyearAudAdj() {
		return dgdsDisctLastyearAudAdj;
	}

	public void setDgdsDisctMemo(String dgdsDisctMemo) {
		this.dgdsDisctMemo = dgdsDisctMemo;
	}

	@Column(name="dgds_disct_memo", nullable=true, length=500)	
	public String getDgdsDisctMemo() {
		return dgdsDisctMemo;
	}

	public void setDgdsDisctIncludeTax(String dgdsDisctIncludeTax) {
		this.dgdsDisctIncludeTax = dgdsDisctIncludeTax;
	}

	@Column(name="dgds_disct_include_tax", nullable=true, length=50)	
	public String getDgdsDisctIncludeTax() {
		return dgdsDisctIncludeTax;
	}

	public void setDgdsDisctIncludeNotax(String dgdsDisctIncludeNotax) {
		this.dgdsDisctIncludeNotax = dgdsDisctIncludeNotax;
	}

	@Column(name="dgds_disct_include_notax", nullable=true, length=50)	
	public String getDgdsDisctIncludeNotax() {
		return dgdsDisctIncludeNotax;
	}

	public void setItrDisctContractText(String itrDisctContractText) {
		this.itrDisctContractText = itrDisctContractText;
	}

	@Column(name="itr_disct_contract_text", nullable=true, length=500)	
	public String getItrDisctContractText() {
		return itrDisctContractText;
	}

	public void setItrDisctContractValue(String itrDisctContractValue) {
		this.itrDisctContractValue = itrDisctContractValue;
	}

	@Column(name="itr_disct_contract_value", nullable=true, length=50)	
	public String getItrDisctContractValue() {
		return itrDisctContractValue;
	}

	public void setItrDisctSumMoney(String itrDisctSumMoney) {
		this.itrDisctSumMoney = itrDisctSumMoney;
	}

	@Column(name="itr_disct_sum_money", nullable=true, length=50)	
	public String getItrDisctSumMoney() {
		return itrDisctSumMoney;
	}

	public void setItrDisctContractAdj(String itrDisctContractAdj) {
		this.itrDisctContractAdj = itrDisctContractAdj;
	}

	@Column(name="itr_disct_contract_adj", nullable=true, length=50)	
	public String getItrDisctContractAdj() {
		return itrDisctContractAdj;
	}

	public void setItrDisctReconAdj(String itrDisctReconAdj) {
		this.itrDisctReconAdj = itrDisctReconAdj;
	}

	@Column(name="itr_disct_recon_adj", nullable=true, length=50)	
	public String getItrDisctReconAdj() {
		return itrDisctReconAdj;
	}

	public void setItrDisctLastyearConAdj(String itrDisctLastyearConAdj) {
		this.itrDisctLastyearConAdj = itrDisctLastyearConAdj;
	}

	@Column(name="itr_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getItrDisctLastyearConAdj() {
		return itrDisctLastyearConAdj;
	}

	public void setItrDisctLastyearRecAdj(String itrDisctLastyearRecAdj) {
		this.itrDisctLastyearRecAdj = itrDisctLastyearRecAdj;
	}

	@Column(name="itr_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getItrDisctLastyearRecAdj() {
		return itrDisctLastyearRecAdj;
	}

	public void setItrDisctLastyearAudAdj(String itrDisctLastyearAudAdj) {
		this.itrDisctLastyearAudAdj = itrDisctLastyearAudAdj;
	}

	@Column(name="itr_disct_lastyear_aud_adj", nullable=true, length=500)	
	public String getItrDisctLastyearAudAdj() {
		return itrDisctLastyearAudAdj;
	}

	public void setItrDisctMemo(String itrDisctMemo) {
		this.itrDisctMemo = itrDisctMemo;
	}

	@Column(name="itr_disct_memo", nullable=true, length=500)	
	public String getItrDisctMemo() {
		return itrDisctMemo;
	}

	public void setItrDisctIncludeTax(String itrDisctIncludeTax) {
		this.itrDisctIncludeTax = itrDisctIncludeTax;
	}

	@Column(name="itr_disct_include_tax", nullable=true, length=50)	
	public String getItrDisctIncludeTax() {
		return itrDisctIncludeTax;
	}

	public void setItrDisctIncludeNotax(String itrDisctIncludeNotax) {
		this.itrDisctIncludeNotax = itrDisctIncludeNotax;
	}

	@Column(name="itr_disct_include_notax", nullable=true, length=50)	
	public String getItrDisctIncludeNotax() {
		return itrDisctIncludeNotax;
	}

	public void setBsdtDisctContractText(String bsdtDisctContractText) {
		this.bsdtDisctContractText = bsdtDisctContractText;
	}

	@Column(name="bsdt_disct_contract_text", nullable=true, length=500)	
	public String getBsdtDisctContractText() {
		return bsdtDisctContractText;
	}

	public void setBsdtDisctContractValue(String bsdtDisctContractValue) {
		this.bsdtDisctContractValue = bsdtDisctContractValue;
	}

	@Column(name="bsdt_disct_contract_value", nullable=true, length=50)	
	public String getBsdtDisctContractValue() {
		return bsdtDisctContractValue;
	}

	public void setBsdtDisctSumMoney(String bsdtDisctSumMoney) {
		this.bsdtDisctSumMoney = bsdtDisctSumMoney;
	}

	@Column(name="bsdt_disct_sum_money", nullable=true, length=50)	
	public String getBsdtDisctSumMoney() {
		return bsdtDisctSumMoney;
	}

	public void setBsdtDisctContractAdj(String bsdtDisctContractAdj) {
		this.bsdtDisctContractAdj = bsdtDisctContractAdj;
	}

	@Column(name="bsdt_disct_contract_adj", nullable=true, length=50)	
	public String getBsdtDisctContractAdj() {
		return bsdtDisctContractAdj;
	}

	public void setBsdtDisctReconAdj(String bsdtDisctReconAdj) {
		this.bsdtDisctReconAdj = bsdtDisctReconAdj;
	}

	@Column(name="bsdt_disct_recon_adj", nullable=true, length=50)	
	public String getBsdtDisctReconAdj() {
		return bsdtDisctReconAdj;
	}

	public void setBsdtDisctLastyearConAdj(String bsdtDisctLastyearConAdj) {
		this.bsdtDisctLastyearConAdj = bsdtDisctLastyearConAdj;
	}

	@Column(name="bsdt_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getBsdtDisctLastyearConAdj() {
		return bsdtDisctLastyearConAdj;
	}

	public void setBsdtDisctLastyearRecAdj(String bsdtDisctLastyearRecAdj) {
		this.bsdtDisctLastyearRecAdj = bsdtDisctLastyearRecAdj;
	}

	@Column(name="bsdt_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getBsdtDisctLastyearRecAdj() {
		return bsdtDisctLastyearRecAdj;
	}

	public void setBsdtDisctLastyearAudAdj(String bsdtDisctLastyearAudAdj) {
		this.bsdtDisctLastyearAudAdj = bsdtDisctLastyearAudAdj;
	}

	@Column(name="bsdt_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getBsdtDisctLastyearAudAdj() {
		return bsdtDisctLastyearAudAdj;
	}

	public void setBsdtDisctMemo(String bsdtDisctMemo) {
		this.bsdtDisctMemo = bsdtDisctMemo;
	}

	@Column(name="bsdt_disct_memo", nullable=true, length=500)	
	public String getBsdtDisctMemo() {
		return bsdtDisctMemo;
	}

	public void setBsdtDisctIncludeTax(String bsdtDisctIncludeTax) {
		this.bsdtDisctIncludeTax = bsdtDisctIncludeTax;
	}

	@Column(name="bsdt_disct_include_tax", nullable=true, length=50)	
	public String getBsdtDisctIncludeTax() {
		return bsdtDisctIncludeTax;
	}

	public void setBsdtDisctIncludeNotax(String bsdtDisctIncludeNotax) {
		this.bsdtDisctIncludeNotax = bsdtDisctIncludeNotax;
	}

	@Column(name="bsdt_disct_include_notax", nullable=true, length=50)	
	public String getBsdtDisctIncludeNotax() {
		return bsdtDisctIncludeNotax;
	}

	public void setLdiDisctContractText(String ldiDisctContractText) {
		this.ldiDisctContractText = ldiDisctContractText;
	}

	@Column(name="ldi_disct_contract_text", nullable=true, length=500)	
	public String getLdiDisctContractText() {
		return ldiDisctContractText;
	}

	public void setLdiDisctContractValue(String ldiDisctContractValue) {
		this.ldiDisctContractValue = ldiDisctContractValue;
	}

	@Column(name="ldi_disct_contract_value", nullable=true, length=50)	
	public String getLdiDisctContractValue() {
		return ldiDisctContractValue;
	}

	public void setLdiDisctSumMoney(String ldiDisctSumMoney) {
		this.ldiDisctSumMoney = ldiDisctSumMoney;
	}

	@Column(name="ldi_disct_sum_money", nullable=true, length=50)	
	public String getLdiDisctSumMoney() {
		return ldiDisctSumMoney;
	}

	public void setLdiDisctContractAdj(String ldiDisctContractAdj) {
		this.ldiDisctContractAdj = ldiDisctContractAdj;
	}

	@Column(name="ldi_disct_contract_adj", nullable=true, length=50)	
	public String getLdiDisctContractAdj() {
		return ldiDisctContractAdj;
	}

	public void setLdiDisctReconAdj(String ldiDisctReconAdj) {
		this.ldiDisctReconAdj = ldiDisctReconAdj;
	}

	@Column(name="ldi_disct_recon_adj", nullable=true, length=50)	
	public String getLdiDisctReconAdj() {
		return ldiDisctReconAdj;
	}

	public void setLdiDisctLastyearConAdj(String ldiDisctLastyearConAdj) {
		this.ldiDisctLastyearConAdj = ldiDisctLastyearConAdj;
	}

	@Column(name="ldi_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getLdiDisctLastyearConAdj() {
		return ldiDisctLastyearConAdj;
	}

	public void setLdiDisctLastyearRecAdj(String ldiDisctLastyearRecAdj) {
		this.ldiDisctLastyearRecAdj = ldiDisctLastyearRecAdj;
	}

	@Column(name="ldi_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getLdiDisctLastyearRecAdj() {
		return ldiDisctLastyearRecAdj;
	}

	public void setLdiDisctLastyearAudAdj(String ldiDisctLastyearAudAdj) {
		this.ldiDisctLastyearAudAdj = ldiDisctLastyearAudAdj;
	}

	@Column(name="ldi_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getLdiDisctLastyearAudAdj() {
		return ldiDisctLastyearAudAdj;
	}

	public void setLdiDisctMemo(String ldiDisctMemo) {
		this.ldiDisctMemo = ldiDisctMemo;
	}

	@Column(name="ldi_disct_memo", nullable=true, length=500)	
	public String getLdiDisctMemo() {
		return ldiDisctMemo;
	}

	public void setLdiDisctIncludeTax(String ldiDisctIncludeTax) {
		this.ldiDisctIncludeTax = ldiDisctIncludeTax;
	}

	@Column(name="ldi_disct_include_tax", nullable=true, length=50)	
	public String getLdiDisctIncludeTax() {
		return ldiDisctIncludeTax;
	}

	public void setLdiDisctIncludeNotax(String ldiDisctIncludeNotax) {
		this.ldiDisctIncludeNotax = ldiDisctIncludeNotax;
	}

	@Column(name="ldi_disct_include_notax", nullable=true, length=50)	
	public String getLdiDisctIncludeNotax() {
		return ldiDisctIncludeNotax;
	}

	public void setNonfpDisctContractText(String nonfpDisctContractText) {
		this.nonfpDisctContractText = nonfpDisctContractText;
	}

	@Column(name="nonfp_disct_contract_text", nullable=true, length=500)	
	public String getNonfpDisctContractText() {
		return nonfpDisctContractText;
	}

	public void setNonfpDisctContractValue(String nonfpDisctContractValue) {
		this.nonfpDisctContractValue = nonfpDisctContractValue;
	}

	@Column(name="nonfp_disct_contract_value", nullable=true, length=50)	
	public String getNonfpDisctContractValue() {
		return nonfpDisctContractValue;
	}

	public void setNonfpDisctSumMoney(String nonfpDisctSumMoney) {
		this.nonfpDisctSumMoney = nonfpDisctSumMoney;
	}

	@Column(name="nonfp_disct_sum_money", nullable=true, length=50)	
	public String getNonfpDisctSumMoney() {
		return nonfpDisctSumMoney;
	}

	public void setNonfpDisctContractAdj(String nonfpDisctContractAdj) {
		this.nonfpDisctContractAdj = nonfpDisctContractAdj;
	}

	@Column(name="nonfp_disct_contract_adj", nullable=true, length=50)	
	public String getNonfpDisctContractAdj() {
		return nonfpDisctContractAdj;
	}

	public void setNonfpDisctReconAdj(String nonfpDisctReconAdj) {
		this.nonfpDisctReconAdj = nonfpDisctReconAdj;
	}

	@Column(name="nonfp_disct_recon_adj", nullable=true, length=50)	
	public String getNonfpDisctReconAdj() {
		return nonfpDisctReconAdj;
	}

	public void setNonfpDisctLastyearConAdj(String nonfpDisctLastyearConAdj) {
		this.nonfpDisctLastyearConAdj = nonfpDisctLastyearConAdj;
	}

	@Column(name="nonfp_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getNonfpDisctLastyearConAdj() {
		return nonfpDisctLastyearConAdj;
	}

	public void setNonfpDisctLastyearRecAdj(String nonfpDisctLastyearRecAdj) {
		this.nonfpDisctLastyearRecAdj = nonfpDisctLastyearRecAdj;
	}

	@Column(name="nonfp_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getNonfpDisctLastyearRecAdj() {
		return nonfpDisctLastyearRecAdj;
	}

	public void setNonfpDisctLastyearAudAdj(String nonfpDisctLastyearAudAdj) {
		this.nonfpDisctLastyearAudAdj = nonfpDisctLastyearAudAdj;
	}

	@Column(name="nonfp_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getNonfpDisctLastyearAudAdj() {
		return nonfpDisctLastyearAudAdj;
	}

	public void setNonfpDisctMemo(String nonfpDisctMemo) {
		this.nonfpDisctMemo = nonfpDisctMemo;
	}

	@Column(name="nonfp_disct_memo", nullable=true, length=500)	
	public String getNonfpDisctMemo() {
		return nonfpDisctMemo;
	}

	public void setNonfpDisctIncludeTax(String nonfpDisctIncludeTax) {
		this.nonfpDisctIncludeTax = nonfpDisctIncludeTax;
	}

	@Column(name="nonfp_disct_include_tax", nullable=true, length=50)	
	public String getNonfpDisctIncludeTax() {
		return nonfpDisctIncludeTax;
	}

	public void setNonfpDisctIncludeNotax(String nonfpDisctIncludeNotax) {
		this.nonfpDisctIncludeNotax = nonfpDisctIncludeNotax;
	}

	@Column(name="nonfp_disct_include_notax", nullable=true, length=50)	
	public String getNonfpDisctIncludeNotax() {
		return nonfpDisctIncludeNotax;
	}

	public void setCouDisctContractText(String couDisctContractText) {
		this.couDisctContractText = couDisctContractText;
	}

	@Column(name="cou_disct_contract_text", nullable=true, length=500)	
	public String getCouDisctContractText() {
		return couDisctContractText;
	}

	public void setCouDisctContractValue(String couDisctContractValue) {
		this.couDisctContractValue = couDisctContractValue;
	}

	@Column(name="cou_disct_contract_value", nullable=true, length=50)	
	public String getCouDisctContractValue() {
		return couDisctContractValue;
	}

	public void setCouDisctSumMoney(String couDisctSumMoney) {
		this.couDisctSumMoney = couDisctSumMoney;
	}

	@Column(name="cou_disct_sum_money", nullable=true, length=50)	
	public String getCouDisctSumMoney() {
		return couDisctSumMoney;
	}

	public void setCouDisctContractAdj(String couDisctContractAdj) {
		this.couDisctContractAdj = couDisctContractAdj;
	}

	@Column(name="cou_disct_contract_adj", nullable=true, length=50)	
	public String getCouDisctContractAdj() {
		return couDisctContractAdj;
	}

	public void setCouDisctReconAdj(String couDisctReconAdj) {
		this.couDisctReconAdj = couDisctReconAdj;
	}

	@Column(name="cou_disct_recon_adj", nullable=true, length=50)	
	public String getCouDisctReconAdj() {
		return couDisctReconAdj;
	}

	public void setCouDisctLastyearConAdj(String couDisctLastyearConAdj) {
		this.couDisctLastyearConAdj = couDisctLastyearConAdj;
	}

	@Column(name="cou_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getCouDisctLastyearConAdj() {
		return couDisctLastyearConAdj;
	}

	public void setCouDisctLastyearRecAdj(String couDisctLastyearRecAdj) {
		this.couDisctLastyearRecAdj = couDisctLastyearRecAdj;
	}

	@Column(name="cou_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getCouDisctLastyearRecAdj() {
		return couDisctLastyearRecAdj;
	}

	public void setCouDisctLastyearAudAdj(String couDisctLastyearAudAdj) {
		this.couDisctLastyearAudAdj = couDisctLastyearAudAdj;
	}

	@Column(name="cou_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getCouDisctLastyearAudAdj() {
		return couDisctLastyearAudAdj;
	}

	public void setCouDisctMemo(String couDisctMemo) {
		this.couDisctMemo = couDisctMemo;
	}

	@Column(name="cou_disct_memo", nullable=true, length=500)	
	public String getCouDisctMemo() {
		return couDisctMemo;
	}

	public void setCouDisctIncludeTax(String couDisctIncludeTax) {
		this.couDisctIncludeTax = couDisctIncludeTax;
	}

	@Column(name="cou_disct_include_tax", nullable=true, length=50)	
	public String getCouDisctIncludeTax() {
		return couDisctIncludeTax;
	}

	public void setCouDisctIncludeNotax(String couDisctIncludeNotax) {
		this.couDisctIncludeNotax = couDisctIncludeNotax;
	}

	@Column(name="cou_disct_include_notax", nullable=true, length=50)	
	public String getCouDisctIncludeNotax() {
		return couDisctIncludeNotax;
	}

	public void setVipDisctContractText(String vipDisctContractText) {
		this.vipDisctContractText = vipDisctContractText;
	}

	@Column(name="vip_disct_contract_text", nullable=true, length=500)	
	public String getVipDisctContractText() {
		return vipDisctContractText;
	}

	public void setVipDisctContractValue(String vipDisctContractValue) {
		this.vipDisctContractValue = vipDisctContractValue;
	}

	@Column(name="vip_disct_contract_value", nullable=true, length=50)	
	public String getVipDisctContractValue() {
		return vipDisctContractValue;
	}

	public void setVipDisctSumMoney(String vipDisctSumMoney) {
		this.vipDisctSumMoney = vipDisctSumMoney;
	}

	@Column(name="vip_disct_sum_money", nullable=true, length=50)	
	public String getVipDisctSumMoney() {
		return vipDisctSumMoney;
	}

	public void setVipDisctContractAdj(String vipDisctContractAdj) {
		this.vipDisctContractAdj = vipDisctContractAdj;
	}

	@Column(name="vip_disct_contract_adj", nullable=true, length=50)	
	public String getVipDisctContractAdj() {
		return vipDisctContractAdj;
	}

	public void setVipDisctReconAdj(String vipDisctReconAdj) {
		this.vipDisctReconAdj = vipDisctReconAdj;
	}

	@Column(name="vip_disct_recon_adj", nullable=true, length=50)	
	public String getVipDisctReconAdj() {
		return vipDisctReconAdj;
	}

	public void setVipDisctLastyearConAdj(String vipDisctLastyearConAdj) {
		this.vipDisctLastyearConAdj = vipDisctLastyearConAdj;
	}

	@Column(name="vip_disct_lastyear_con_adj", nullable=true, length=50)	
	public String getVipDisctLastyearConAdj() {
		return vipDisctLastyearConAdj;
	}

	public void setVipDisctLastyearRecAdj(String vipDisctLastyearRecAdj) {
		this.vipDisctLastyearRecAdj = vipDisctLastyearRecAdj;
	}

	@Column(name="vip_disct_lastyear_rec_adj", nullable=true, length=50)	
	public String getVipDisctLastyearRecAdj() {
		return vipDisctLastyearRecAdj;
	}

	public void setVipDisctLastyearAudAdj(String vipDisctLastyearAudAdj) {
		this.vipDisctLastyearAudAdj = vipDisctLastyearAudAdj;
	}

	@Column(name="vip_disct_lastyear_aud_adj", nullable=true, length=50)	
	public String getVipDisctLastyearAudAdj() {
		return vipDisctLastyearAudAdj;
	}

	public void setVipDisctMemo(String vipDisctMemo) {
		this.vipDisctMemo = vipDisctMemo;
	}

	@Column(name="vip_disct_memo", nullable=true, length=500)	
	public String getVipDisctMemo() {
		return vipDisctMemo;
	}

	public void setVipDisctIncludeTax(String vipDisctIncludeTax) {
		this.vipDisctIncludeTax = vipDisctIncludeTax;
	}

	@Column(name="vip_disct_include_tax", nullable=true, length=50)	
	public String getVipDisctIncludeTax() {
		return vipDisctIncludeTax;
	}

	public void setVipDisctIncludeNotax(String vipDisctIncludeNotax) {
		this.vipDisctIncludeNotax = vipDisctIncludeNotax;
	}

	@Column(name="vip_disct_include_notax", nullable=true, length=50)	
	public String getVipDisctIncludeNotax() {
		return vipDisctIncludeNotax;
	}

	public void setUncontrDisctContractText(String uncontrDisctContractText) {
		this.uncontrDisctContractText = uncontrDisctContractText;
	}

	@Column(name="uncontr_disct_contract_text", nullable=true, length=500)	
	public String getUncontrDisctContractText() {
		return uncontrDisctContractText;
	}

	public void setUncontrDisctContractValue(String uncontrDisctContractValue) {
		this.uncontrDisctContractValue = uncontrDisctContractValue;
	}

	@Column(name="uncontr_disct_contract_value", nullable=true, length=50)	
	public String getUncontrDisctContractValue() {
		return uncontrDisctContractValue;
	}

	public void setUncontrDisctSumMoney(String uncontrDisctSumMoney) {
		this.uncontrDisctSumMoney = uncontrDisctSumMoney;
	}

	@Column(name="uncontr_disct_sum_money", nullable=true, length=50)	
	public String getUncontrDisctSumMoney() {
		return uncontrDisctSumMoney;
	}

	public void setUncontrDisctContractAdj(String uncontrDisctContractAdj) {
		this.uncontrDisctContractAdj = uncontrDisctContractAdj;
	}

	@Column(name="uncontr_disct_contract_adj", nullable=true, length=50)	
	public String getUncontrDisctContractAdj() {
		return uncontrDisctContractAdj;
	}

	public void setUncontrDisctReconAdj(String uncontrDisctReconAdj) {
		this.uncontrDisctReconAdj = uncontrDisctReconAdj;
	}

	@Column(name="uncontr_disct_recon_adj", nullable=true, length=50)	
	public String getUncontrDisctReconAdj() {
		return uncontrDisctReconAdj;
	}

	public void setUncontrDisLastyearConAdj(String uncontrDisLastyearConAdj) {
		this.uncontrDisLastyearConAdj = uncontrDisLastyearConAdj;
	}

	@Column(name="uncontr_dis_lastyear_con_adj", nullable=true, length=50)	
	public String getUncontrDisLastyearConAdj() {
		return uncontrDisLastyearConAdj;
	}

	public void setUncontrDisLastyearRecAdj(String uncontrDisLastyearRecAdj) {
		this.uncontrDisLastyearRecAdj = uncontrDisLastyearRecAdj;
	}

	@Column(name="uncontr_dis_lastyear_rec_adj", nullable=true, length=50)	
	public String getUncontrDisLastyearRecAdj() {
		return uncontrDisLastyearRecAdj;
	}

	public void setUncontrDisLastyearAudAdj(String uncontrDisLastyearAudAdj) {
		this.uncontrDisLastyearAudAdj = uncontrDisLastyearAudAdj;
	}

	@Column(name="uncontr_dis_lastyear_aud_adj", nullable=true, length=50)	
	public String getUncontrDisLastyearAudAdj() {
		return uncontrDisLastyearAudAdj;
	}

	public void setUncontrDisctMemo(String uncontrDisctMemo) {
		this.uncontrDisctMemo = uncontrDisctMemo;
	}

	@Column(name="uncontr_disct_memo", nullable=true, length=500)	
	public String getUncontrDisctMemo() {
		return uncontrDisctMemo;
	}

	public void setUncontrDisctIncludeTax(String uncontrDisctIncludeTax) {
		this.uncontrDisctIncludeTax = uncontrDisctIncludeTax;
	}

	@Column(name="uncontr_disct_include_tax", nullable=true, length=50)	
	public String getUncontrDisctIncludeTax() {
		return uncontrDisctIncludeTax;
	}

	public void setUncontrDisctIncludeNotax(String uncontrDisctIncludeNotax) {
		this.uncontrDisctIncludeNotax = uncontrDisctIncludeNotax;
	}

	@Column(name="uncontr_disct_include_notax", nullable=true, length=50)	
	public String getUncontrDisctIncludeNotax() {
		return uncontrDisctIncludeNotax;
	}

	public void setCostRedIncome(String costRedIncome) {
		this.costRedIncome = costRedIncome;
	}

	@Column(name="cost_red_income", nullable=true, length=500)	
	public String getCostRedIncome() {
		return costRedIncome;
	}

	public void setCostRedIncomeValue(String costRedIncomeValue) {
		this.costRedIncomeValue = costRedIncomeValue;
	}

	@Column(name="cost_red_income_value", nullable=true, length=50)	
	public String getCostRedIncomeValue() {
		return costRedIncomeValue;
	}

	public void setCostRedIncomeCurmonTta(String costRedIncomeCurmonTta) {
		this.costRedIncomeCurmonTta = costRedIncomeCurmonTta;
	}

	@Column(name="cost_red_income_curmon_tta", nullable=true, length=50)	
	public String getCostRedIncomeCurmonTta() {
		return costRedIncomeCurmonTta;
	}

	public void setCostRedIncomeCurmonOntop(String costRedIncomeCurmonOntop) {
		this.costRedIncomeCurmonOntop = costRedIncomeCurmonOntop;
	}

	@Column(name="cost_red_income_curmon_ontop", nullable=true, length=50)	
	public String getCostRedIncomeCurmonOntop() {
		return costRedIncomeCurmonOntop;
	}

	public void setCostRedIncomePyyearTta(String costRedIncomePyyearTta) {
		this.costRedIncomePyyearTta = costRedIncomePyyearTta;
	}

	@Column(name="cost_red_income_pyyear_tta", nullable=true, length=50)	
	public String getCostRedIncomePyyearTta() {
		return costRedIncomePyyearTta;
	}

	public void setCostRedIncomePyyearOntop(String costRedIncomePyyearOntop) {
		this.costRedIncomePyyearOntop = costRedIncomePyyearOntop;
	}

	@Column(name="cost_red_income_pyyear_ontop", nullable=true, length=50)	
	public String getCostRedIncomePyyearOntop() {
		return costRedIncomePyyearOntop;
	}

	public void setCostRedContractAdj(String costRedContractAdj) {
		this.costRedContractAdj = costRedContractAdj;
	}

	@Column(name="cost_red_contract_adj", nullable=true, length=50)	
	public String getCostRedContractAdj() {
		return costRedContractAdj;
	}

	public void setCostRedReconAdj(String costRedReconAdj) {
		this.costRedReconAdj = costRedReconAdj;
	}

	@Column(name="cost_red_recon_adj", nullable=true, length=50)	
	public String getCostRedReconAdj() {
		return costRedReconAdj;
	}

	public void setCostRedLastyearConAdj(String costRedLastyearConAdj) {
		this.costRedLastyearConAdj = costRedLastyearConAdj;
	}

	@Column(name="cost_red_lastyear_con_adj", nullable=true, length=50)	
	public String getCostRedLastyearConAdj() {
		return costRedLastyearConAdj;
	}

	public void setCostRedLastyearRecAdj(String costRedLastyearRecAdj) {
		this.costRedLastyearRecAdj = costRedLastyearRecAdj;
	}

	@Column(name="cost_red_lastyear_rec_adj", nullable=true, length=50)	
	public String getCostRedLastyearRecAdj() {
		return costRedLastyearRecAdj;
	}

	public void setCostRedLastyearAudAdj(String costRedLastyearAudAdj) {
		this.costRedLastyearAudAdj = costRedLastyearAudAdj;
	}

	@Column(name="cost_red_lastyear_aud_adj", nullable=true, length=50)	
	public String getCostRedLastyearAudAdj() {
		return costRedLastyearAudAdj;
	}

	public void setCostRedMemo(String costRedMemo) {
		this.costRedMemo = costRedMemo;
	}

	@Column(name="cost_red_memo", nullable=true, length=500)	
	public String getCostRedMemo() {
		return costRedMemo;
	}

	public void setCostRedIncludeTax(String costRedIncludeTax) {
		this.costRedIncludeTax = costRedIncludeTax;
	}

	@Column(name="cost_red_include_tax", nullable=true, length=50)	
	public String getCostRedIncludeTax() {
		return costRedIncludeTax;
	}

	public void setCostDisctExcludeNotax(String costDisctExcludeNotax) {
		this.costDisctExcludeNotax = costDisctExcludeNotax;
	}

	@Column(name="cost_disct_exclude_notax", nullable=true, length=50)	
	public String getCostDisctExcludeNotax() {
		return costDisctExcludeNotax;
	}

	public void setOtherOem(String otherOem) {
		this.otherOem = otherOem;
	}

	@Column(name="other_oem", nullable=true, length=500)	
	public String getOtherOem() {
		return otherOem;
	}

	public void setOtherOemValue(String otherOemValue) {
		this.otherOemValue = otherOemValue;
	}

	@Column(name="other_oem_value", nullable=true, length=50)	
	public String getOtherOemValue() {
		return otherOemValue;
	}

	public void setOtherOemMonthTta(String otherOemMonthTta) {
		this.otherOemMonthTta = otherOemMonthTta;
	}

	@Column(name="other_oem_month_tta", nullable=true, length=50)	
	public String getOtherOemMonthTta() {
		return otherOemMonthTta;
	}

	public void setOtherOemMonthOntop(String otherOemMonthOntop) {
		this.otherOemMonthOntop = otherOemMonthOntop;
	}

	@Column(name="other_oem_month_ontop", nullable=true, length=50)	
	public String getOtherOemMonthOntop() {
		return otherOemMonthOntop;
	}

	public void setOtherOemPyyearTta(String otherOemPyyearTta) {
		this.otherOemPyyearTta = otherOemPyyearTta;
	}

	@Column(name="other_oem_pyyear_tta", nullable=true, length=50)	
	public String getOtherOemPyyearTta() {
		return otherOemPyyearTta;
	}

	public void setOtherOemPyyearOntop(String otherOemPyyearOntop) {
		this.otherOemPyyearOntop = otherOemPyyearOntop;
	}

	@Column(name="other_oem_pyyear_ontop", nullable=true, length=500)	
	public String getOtherOemPyyearOntop() {
		return otherOemPyyearOntop;
	}

	public void setOtherOemContractAdj(String otherOemContractAdj) {
		this.otherOemContractAdj = otherOemContractAdj;
	}

	@Column(name="other_oem_contract_adj", nullable=true, length=50)	
	public String getOtherOemContractAdj() {
		return otherOemContractAdj;
	}

	public void setOtherOemReconAdj(String otherOemReconAdj) {
		this.otherOemReconAdj = otherOemReconAdj;
	}

	@Column(name="other_oem_recon_adj", nullable=true, length=50)	
	public String getOtherOemReconAdj() {
		return otherOemReconAdj;
	}

	public void setOtherOemLastyearConAdj(String otherOemLastyearConAdj) {
		this.otherOemLastyearConAdj = otherOemLastyearConAdj;
	}

	@Column(name="other_oem_lastyear_con_adj", nullable=true, length=50)	
	public String getOtherOemLastyearConAdj() {
		return otherOemLastyearConAdj;
	}

	public void setOtherOemLastyearRecAdj(String otherOemLastyearRecAdj) {
		this.otherOemLastyearRecAdj = otherOemLastyearRecAdj;
	}

	@Column(name="other_oem_lastyear_rec_adj", nullable=true, length=50)	
	public String getOtherOemLastyearRecAdj() {
		return otherOemLastyearRecAdj;
	}

	public void setOtherOemLastyearAudAdj(String otherOemLastyearAudAdj) {
		this.otherOemLastyearAudAdj = otherOemLastyearAudAdj;
	}

	@Column(name="other_oem_lastyear_aud_adj", nullable=true, length=50)	
	public String getOtherOemLastyearAudAdj() {
		return otherOemLastyearAudAdj;
	}

	public void setOtherOemMemo(String otherOemMemo) {
		this.otherOemMemo = otherOemMemo;
	}

	@Column(name="other_oem_memo", nullable=true, length=500)	
	public String getOtherOemMemo() {
		return otherOemMemo;
	}

	public void setOtherOemIncludeTax(String otherOemIncludeTax) {
		this.otherOemIncludeTax = otherOemIncludeTax;
	}

	@Column(name="other_oem_include_tax", nullable=true, length=50)	
	public String getOtherOemIncludeTax() {
		return otherOemIncludeTax;
	}

	public void setOtherOemExcludeNotax(String otherOemExcludeNotax) {
		this.otherOemExcludeNotax = otherOemExcludeNotax;
	}

	@Column(name="other_oem_exclude_notax", nullable=true, length=50)	
	public String getOtherOemExcludeNotax() {
		return otherOemExcludeNotax;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setApOiTotalNotax1(String apOiTotalNotax1) {
		this.apOiTotalNotax1 = apOiTotalNotax1;
	}

	@Column(name="ap_oi_total_notax1", nullable=true, length=50)	
	public String getApOiTotalNotax1() {
		return apOiTotalNotax1;
	}

	public void setTax1(String tax1) {
		this.tax1 = tax1;
	}

	@Column(name="tax1", nullable=true, length=50)	
	public String getTax1() {
		return tax1;
	}

	public void setPfc01DisctContractText(String pfc01DisctContractText) {
		this.pfc01DisctContractText = pfc01DisctContractText;
	}

	@Column(name="pfc01_disct_contract_text", nullable=true, length=500)	
	public String getPfc01DisctContractText() {
		return pfc01DisctContractText;
	}

	public void setPfc01DisctContractValue(String pfc01DisctContractValue) {
		this.pfc01DisctContractValue = pfc01DisctContractValue;
	}

	@Column(name="pfc01_disct_contract_value", nullable=true, length=50)	
	public String getPfc01DisctContractValue() {
		return pfc01DisctContractValue;
	}

	public void setPfc01DisctSumMoney(String pfc01DisctSumMoney) {
		this.pfc01DisctSumMoney = pfc01DisctSumMoney;
	}

	@Column(name="pfc01_disct_sum_money", nullable=true, length=50)	
	public String getPfc01DisctSumMoney() {
		return pfc01DisctSumMoney;
	}

	public void setPfc01DisctContractAdj(String pfc01DisctContractAdj) {
		this.pfc01DisctContractAdj = pfc01DisctContractAdj;
	}

	@Column(name="pfc01_disct_contract_adj", nullable=true, length=50)	
	public String getPfc01DisctContractAdj() {
		return pfc01DisctContractAdj;
	}

	public void setPfc01DisctReconAdj(String pfc01DisctReconAdj) {
		this.pfc01DisctReconAdj = pfc01DisctReconAdj;
	}

	@Column(name="pfc01_disct_recon_adj", nullable=true, length=50)	
	public String getPfc01DisctReconAdj() {
		return pfc01DisctReconAdj;
	}

	public void setPfc01DisLastyearConAdj(String pfc01DisLastyearConAdj) {
		this.pfc01DisLastyearConAdj = pfc01DisLastyearConAdj;
	}

	@Column(name="pfc01_dis_lastyear_con_adj", nullable=true, length=50)	
	public String getPfc01DisLastyearConAdj() {
		return pfc01DisLastyearConAdj;
	}

	public void setPfc01DisLastyearRecAdj(String pfc01DisLastyearRecAdj) {
		this.pfc01DisLastyearRecAdj = pfc01DisLastyearRecAdj;
	}

	@Column(name="pfc01_dis_lastyear_rec_adj", nullable=true, length=50)	
	public String getPfc01DisLastyearRecAdj() {
		return pfc01DisLastyearRecAdj;
	}

	public void setPfc01DisLastyearAudAdj(String pfc01DisLastyearAudAdj) {
		this.pfc01DisLastyearAudAdj = pfc01DisLastyearAudAdj;
	}

	@Column(name="pfc01_dis_lastyear_aud_adj", nullable=true, length=50)	
	public String getPfc01DisLastyearAudAdj() {
		return pfc01DisLastyearAudAdj;
	}

	public void setPfc01DisctMemo(String pfc01DisctMemo) {
		this.pfc01DisctMemo = pfc01DisctMemo;
	}

	@Column(name="pfc01_disct_memo", nullable=true, length=500)	
	public String getPfc01DisctMemo() {
		return pfc01DisctMemo;
	}

	public void setPfc01DisctIncludeTax(String pfc01DisctIncludeTax) {
		this.pfc01DisctIncludeTax = pfc01DisctIncludeTax;
	}

	@Column(name="pfc01_disct_include_tax", nullable=true, length=50)	
	public String getPfc01DisctIncludeTax() {
		return pfc01DisctIncludeTax;
	}

	public void setPfc01DisctIncludeNotax(String pfc01DisctIncludeNotax) {
		this.pfc01DisctIncludeNotax = pfc01DisctIncludeNotax;
	}

	@Column(name="pfc01_disct_include_notax", nullable=true, length=50)	
	public String getPfc01DisctIncludeNotax() {
		return pfc01DisctIncludeNotax;
	}

	public void setDdb01DisctContractText(String ddb01DisctContractText) {
		this.ddb01DisctContractText = ddb01DisctContractText;
	}

	@Column(name="ddb01_disct_contract_text", nullable=true, length=500)	
	public String getDdb01DisctContractText() {
		return ddb01DisctContractText;
	}

	public void setDdb01DisctContractValue(String ddb01DisctContractValue) {
		this.ddb01DisctContractValue = ddb01DisctContractValue;
	}

	@Column(name="ddb01_disct_contract_value", nullable=true, length=50)	
	public String getDdb01DisctContractValue() {
		return ddb01DisctContractValue;
	}

	public void setDdb01DisctSumMoney(String ddb01DisctSumMoney) {
		this.ddb01DisctSumMoney = ddb01DisctSumMoney;
	}

	@Column(name="ddb01_disct_sum_money", nullable=true, length=50)	
	public String getDdb01DisctSumMoney() {
		return ddb01DisctSumMoney;
	}

	public void setDdb01DisctContractAdj(String ddb01DisctContractAdj) {
		this.ddb01DisctContractAdj = ddb01DisctContractAdj;
	}

	@Column(name="ddb01_disct_contract_adj", nullable=true, length=50)	
	public String getDdb01DisctContractAdj() {
		return ddb01DisctContractAdj;
	}

	public void setDdb01DisctReconAdj(String ddb01DisctReconAdj) {
		this.ddb01DisctReconAdj = ddb01DisctReconAdj;
	}

	@Column(name="ddb01_disct_recon_adj", nullable=true, length=50)	
	public String getDdb01DisctReconAdj() {
		return ddb01DisctReconAdj;
	}

	public void setDdb01DisLastyearConAdj(String ddb01DisLastyearConAdj) {
		this.ddb01DisLastyearConAdj = ddb01DisLastyearConAdj;
	}

	@Column(name="ddb01_dis_lastyear_con_adj", nullable=true, length=50)	
	public String getDdb01DisLastyearConAdj() {
		return ddb01DisLastyearConAdj;
	}

	public void setDdb01DisLastyearRecAdj(String ddb01DisLastyearRecAdj) {
		this.ddb01DisLastyearRecAdj = ddb01DisLastyearRecAdj;
	}

	@Column(name="ddb01_dis_lastyear_rec_adj", nullable=true, length=50)	
	public String getDdb01DisLastyearRecAdj() {
		return ddb01DisLastyearRecAdj;
	}

	public void setDdb01DisLastyearAudAdj(String ddb01DisLastyearAudAdj) {
		this.ddb01DisLastyearAudAdj = ddb01DisLastyearAudAdj;
	}

	@Column(name="ddb01_dis_lastyear_aud_adj", nullable=true, length=50)	
	public String getDdb01DisLastyearAudAdj() {
		return ddb01DisLastyearAudAdj;
	}

	public void setDdb01DisctMemo(String ddb01DisctMemo) {
		this.ddb01DisctMemo = ddb01DisctMemo;
	}

	@Column(name="ddb01_disct_memo", nullable=true, length=500)	
	public String getDdb01DisctMemo() {
		return ddb01DisctMemo;
	}

	public void setDdb01DisctIncludeTax(String ddb01DisctIncludeTax) {
		this.ddb01DisctIncludeTax = ddb01DisctIncludeTax;
	}

	@Column(name="ddb01_disct_include_tax", nullable=true, length=50)	
	public String getDdb01DisctIncludeTax() {
		return ddb01DisctIncludeTax;
	}

	public void setDdb01DisctIncludeNotax(String ddb01DisctIncludeNotax) {
		this.ddb01DisctIncludeNotax = ddb01DisctIncludeNotax;
	}

	@Column(name="ddb01_disct_include_notax", nullable=true, length=50)	
	public String getDdb01DisctIncludeNotax() {
		return ddb01DisctIncludeNotax;
	}

	public void setRgt01DisctContractText(String rgt01DisctContractText) {
		this.rgt01DisctContractText = rgt01DisctContractText;
	}

	@Column(name="rgt01_disct_contract_text", nullable=true, length=500)	
	public String getRgt01DisctContractText() {
		return rgt01DisctContractText;
	}

	public void setRgt01DisctContractValue(String rgt01DisctContractValue) {
		this.rgt01DisctContractValue = rgt01DisctContractValue;
	}

	@Column(name="rgt01_disct_contract_value", nullable=true, length=50)	
	public String getRgt01DisctContractValue() {
		return rgt01DisctContractValue;
	}

	public void setRgt01DisctSumMoney(String rgt01DisctSumMoney) {
		this.rgt01DisctSumMoney = rgt01DisctSumMoney;
	}

	@Column(name="rgt01_disct_sum_money", nullable=true, length=50)	
	public String getRgt01DisctSumMoney() {
		return rgt01DisctSumMoney;
	}

	public void setRgt01DisctContractAdj(String rgt01DisctContractAdj) {
		this.rgt01DisctContractAdj = rgt01DisctContractAdj;
	}

	@Column(name="rgt01_disct_contract_adj", nullable=true, length=50)	
	public String getRgt01DisctContractAdj() {
		return rgt01DisctContractAdj;
	}

	public void setRgt01DisctReconAdj(String rgt01DisctReconAdj) {
		this.rgt01DisctReconAdj = rgt01DisctReconAdj;
	}

	@Column(name="rgt01_disct_recon_adj", nullable=true, length=50)	
	public String getRgt01DisctReconAdj() {
		return rgt01DisctReconAdj;
	}

	public void setRgt01DisLastyearConAdj(String rgt01DisLastyearConAdj) {
		this.rgt01DisLastyearConAdj = rgt01DisLastyearConAdj;
	}

	@Column(name="rgt01_dis_lastyear_con_adj", nullable=true, length=50)	
	public String getRgt01DisLastyearConAdj() {
		return rgt01DisLastyearConAdj;
	}

	public void setRgt01DisLastyearRecAdj(String rgt01DisLastyearRecAdj) {
		this.rgt01DisLastyearRecAdj = rgt01DisLastyearRecAdj;
	}

	@Column(name="rgt01_dis_lastyear_rec_adj", nullable=true, length=50)	
	public String getRgt01DisLastyearRecAdj() {
		return rgt01DisLastyearRecAdj;
	}

	public void setRgt01DisLastyearAudAdj(String rgt01DisLastyearAudAdj) {
		this.rgt01DisLastyearAudAdj = rgt01DisLastyearAudAdj;
	}

	@Column(name="rgt01_dis_lastyear_aud_adj", nullable=true, length=50)	
	public String getRgt01DisLastyearAudAdj() {
		return rgt01DisLastyearAudAdj;
	}

	public void setRgt01DisctMemo(String rgt01DisctMemo) {
		this.rgt01DisctMemo = rgt01DisctMemo;
	}

	@Column(name="rgt01_disct_memo", nullable=true, length=500)	
	public String getRgt01DisctMemo() {
		return rgt01DisctMemo;
	}

	public void setRgt01DisctIncludeTax(String rgt01DisctIncludeTax) {
		this.rgt01DisctIncludeTax = rgt01DisctIncludeTax;
	}

	@Column(name="rgt01_disct_include_tax", nullable=true, length=50)	
	public String getRgt01DisctIncludeTax() {
		return rgt01DisctIncludeTax;
	}

	public void setRgt01DisctIncludeNotax(String rgt01DisctIncludeNotax) {
		this.rgt01DisctIncludeNotax = rgt01DisctIncludeNotax;
	}

	@Column(name="rgt01_disct_include_notax", nullable=true, length=50)	
	public String getRgt01DisctIncludeNotax() {
		return rgt01DisctIncludeNotax;
	}

	public void setBdf01DisctContractText(String bdf01DisctContractText) {
		this.bdf01DisctContractText = bdf01DisctContractText;
	}

	@Column(name="bdf01_disct_contract_text", nullable=true, length=500)	
	public String getBdf01DisctContractText() {
		return bdf01DisctContractText;
	}

	public void setBdf01DisctContractValue(String bdf01DisctContractValue) {
		this.bdf01DisctContractValue = bdf01DisctContractValue;
	}

	@Column(name="bdf01_disct_contract_value", nullable=true, length=50)	
	public String getBdf01DisctContractValue() {
		return bdf01DisctContractValue;
	}

	public void setBdf01DisctSumMoney(String bdf01DisctSumMoney) {
		this.bdf01DisctSumMoney = bdf01DisctSumMoney;
	}

	@Column(name="bdf01_disct_sum_money", nullable=true, length=50)	
	public String getBdf01DisctSumMoney() {
		return bdf01DisctSumMoney;
	}

	public void setBdf01DisctContractAdj(String bdf01DisctContractAdj) {
		this.bdf01DisctContractAdj = bdf01DisctContractAdj;
	}

	@Column(name="bdf01_disct_contract_adj", nullable=true, length=50)	
	public String getBdf01DisctContractAdj() {
		return bdf01DisctContractAdj;
	}

	public void setBdf01DisctReconAdj(String bdf01DisctReconAdj) {
		this.bdf01DisctReconAdj = bdf01DisctReconAdj;
	}

	@Column(name="bdf01_disct_recon_adj", nullable=true, length=50)	
	public String getBdf01DisctReconAdj() {
		return bdf01DisctReconAdj;
	}

	public void setBdf01DisLastyearConAdj(String bdf01DisLastyearConAdj) {
		this.bdf01DisLastyearConAdj = bdf01DisLastyearConAdj;
	}

	@Column(name="bdf01_dis_lastyear_con_adj", nullable=true, length=50)	
	public String getBdf01DisLastyearConAdj() {
		return bdf01DisLastyearConAdj;
	}

	public void setBdf01DisLastyearRecAdj(String bdf01DisLastyearRecAdj) {
		this.bdf01DisLastyearRecAdj = bdf01DisLastyearRecAdj;
	}

	@Column(name="bdf01_dis_lastyear_rec_adj", nullable=true, length=50)	
	public String getBdf01DisLastyearRecAdj() {
		return bdf01DisLastyearRecAdj;
	}

	public void setBdf01DisLastyearAudAdj(String bdf01DisLastyearAudAdj) {
		this.bdf01DisLastyearAudAdj = bdf01DisLastyearAudAdj;
	}

	@Column(name="bdf01_dis_lastyear_aud_adj", nullable=true, length=50)	
	public String getBdf01DisLastyearAudAdj() {
		return bdf01DisLastyearAudAdj;
	}

	public void setBdf01DisctMemo(String bdf01DisctMemo) {
		this.bdf01DisctMemo = bdf01DisctMemo;
	}

	@Column(name="bdf01_disct_memo", nullable=true, length=500)	
	public String getBdf01DisctMemo() {
		return bdf01DisctMemo;
	}

	public void setBdf01DisctIncludeTax(String bdf01DisctIncludeTax) {
		this.bdf01DisctIncludeTax = bdf01DisctIncludeTax;
	}

	@Column(name="bdf01_disct_include_tax", nullable=true, length=50)	
	public String getBdf01DisctIncludeTax() {
		return bdf01DisctIncludeTax;
	}

	public void setBdf01DisctIncludeNotax(String bdf01DisctIncludeNotax) {
		this.bdf01DisctIncludeNotax = bdf01DisctIncludeNotax;
	}

	@Column(name="bdf01_disct_include_notax", nullable=true, length=50)	
	public String getBdf01DisctIncludeNotax() {
		return bdf01DisctIncludeNotax;
	}

	public void setOic01RedIncome(String oic01RedIncome) {
		this.oic01RedIncome = oic01RedIncome;
	}

	@Column(name="oic01_red_income", nullable=true, length=500)	
	public String getOic01RedIncome() {
		return oic01RedIncome;
	}

	public void setOic01RedIncomeValue(String oic01RedIncomeValue) {
		this.oic01RedIncomeValue = oic01RedIncomeValue;
	}

	@Column(name="oic01_red_income_value", nullable=true, length=50)	
	public String getOic01RedIncomeValue() {
		return oic01RedIncomeValue;
	}

	public void setOic01RedIncomeCurmonTta(String oic01RedIncomeCurmonTta) {
		this.oic01RedIncomeCurmonTta = oic01RedIncomeCurmonTta;
	}

	@Column(name="oic01_red_income_curmon_tta", nullable=true, length=50)	
	public String getOic01RedIncomeCurmonTta() {
		return oic01RedIncomeCurmonTta;
	}

	public void setOic01RedIncomeCurmonOntop(String oic01RedIncomeCurmonOntop) {
		this.oic01RedIncomeCurmonOntop = oic01RedIncomeCurmonOntop;
	}

	@Column(name="oic01_red_income_curmon_ontop", nullable=true, length=50)	
	public String getOic01RedIncomeCurmonOntop() {
		return oic01RedIncomeCurmonOntop;
	}

	public void setOic01RedIncomePyyearTta(String oic01RedIncomePyyearTta) {
		this.oic01RedIncomePyyearTta = oic01RedIncomePyyearTta;
	}

	@Column(name="oic01_red_income_pyyear_tta", nullable=true, length=50)	
	public String getOic01RedIncomePyyearTta() {
		return oic01RedIncomePyyearTta;
	}

	public void setOic01RedIncomePyyearOntop(String oic01RedIncomePyyearOntop) {
		this.oic01RedIncomePyyearOntop = oic01RedIncomePyyearOntop;
	}

	@Column(name="oic01_red_income_pyyear_ontop", nullable=true, length=50)	
	public String getOic01RedIncomePyyearOntop() {
		return oic01RedIncomePyyearOntop;
	}

	public void setOic01RedContractAdj(String oic01RedContractAdj) {
		this.oic01RedContractAdj = oic01RedContractAdj;
	}

	@Column(name="oic01_red_contract_adj", nullable=true, length=50)	
	public String getOic01RedContractAdj() {
		return oic01RedContractAdj;
	}

	public void setOic01RedReconAdj(String oic01RedReconAdj) {
		this.oic01RedReconAdj = oic01RedReconAdj;
	}

	@Column(name="oic01_red_recon_adj", nullable=true, length=50)	
	public String getOic01RedReconAdj() {
		return oic01RedReconAdj;
	}

	public void setOic01RedLastyearConAdj(String oic01RedLastyearConAdj) {
		this.oic01RedLastyearConAdj = oic01RedLastyearConAdj;
	}

	@Column(name="oic01_red_lastyear_con_adj", nullable=true, length=50)	
	public String getOic01RedLastyearConAdj() {
		return oic01RedLastyearConAdj;
	}

	public void setOic01RedLastyearRecAdj(String oic01RedLastyearRecAdj) {
		this.oic01RedLastyearRecAdj = oic01RedLastyearRecAdj;
	}

	@Column(name="oic01_red_lastyear_rec_adj", nullable=true, length=50)	
	public String getOic01RedLastyearRecAdj() {
		return oic01RedLastyearRecAdj;
	}

	public void setOic01RedLastyearAudAdj(String oic01RedLastyearAudAdj) {
		this.oic01RedLastyearAudAdj = oic01RedLastyearAudAdj;
	}

	@Column(name="oic01_red_lastyear_aud_adj", nullable=true, length=50)	
	public String getOic01RedLastyearAudAdj() {
		return oic01RedLastyearAudAdj;
	}

	public void setOic01RedMemo(String oic01RedMemo) {
		this.oic01RedMemo = oic01RedMemo;
	}

	@Column(name="oic01_red_memo", nullable=true, length=500)	
	public String getOic01RedMemo() {
		return oic01RedMemo;
	}

	public void setOic01RedIncludeTax(String oic01RedIncludeTax) {
		this.oic01RedIncludeTax = oic01RedIncludeTax;
	}

	@Column(name="oic01_red_include_tax", nullable=true, length=50)	
	public String getOic01RedIncludeTax() {
		return oic01RedIncludeTax;
	}

	public void setOic01RedIncludeNotax(String oic01RedIncludeNotax) {
		this.oic01RedIncludeNotax = oic01RedIncludeNotax;
	}

	@Column(name="oic01_red_include_notax", nullable=true, length=50)	
	public String getOic01RedIncludeNotax() {
		return oic01RedIncludeNotax;
	}

	public void setPsf01DisctContractText(String psf01DisctContractText) {
		this.psf01DisctContractText = psf01DisctContractText;
	}

	@Column(name="psf01_disct_contract_text", nullable=true, length=500)	
	public String getPsf01DisctContractText() {
		return psf01DisctContractText;
	}

	public void setPsf01DisctContractValue(String psf01DisctContractValue) {
		this.psf01DisctContractValue = psf01DisctContractValue;
	}

	@Column(name="psf01_disct_contract_value", nullable=true, length=50)	
	public String getPsf01DisctContractValue() {
		return psf01DisctContractValue;
	}

	public void setPsf01DisctSumMoney(String psf01DisctSumMoney) {
		this.psf01DisctSumMoney = psf01DisctSumMoney;
	}

	@Column(name="psf01_disct_sum_money", nullable=true, length=50)	
	public String getPsf01DisctSumMoney() {
		return psf01DisctSumMoney;
	}

	public void setPsf01DisctContractAdj(String psf01DisctContractAdj) {
		this.psf01DisctContractAdj = psf01DisctContractAdj;
	}

	@Column(name="psf01_disct_contract_adj", nullable=true, length=50)	
	public String getPsf01DisctContractAdj() {
		return psf01DisctContractAdj;
	}

	public void setPsf01DisctReconAdj(String psf01DisctReconAdj) {
		this.psf01DisctReconAdj = psf01DisctReconAdj;
	}

	@Column(name="psf01_disct_recon_adj", nullable=true, length=50)	
	public String getPsf01DisctReconAdj() {
		return psf01DisctReconAdj;
	}

	public void setPsf01DisLastyearConAdj(String psf01DisLastyearConAdj) {
		this.psf01DisLastyearConAdj = psf01DisLastyearConAdj;
	}

	@Column(name="psf01_dis_lastyear_con_adj", nullable=true, length=50)	
	public String getPsf01DisLastyearConAdj() {
		return psf01DisLastyearConAdj;
	}

	public void setPsf01DisLastyearRecAdj(String psf01DisLastyearRecAdj) {
		this.psf01DisLastyearRecAdj = psf01DisLastyearRecAdj;
	}

	@Column(name="psf01_dis_lastyear_rec_adj", nullable=true, length=50)	
	public String getPsf01DisLastyearRecAdj() {
		return psf01DisLastyearRecAdj;
	}

	public void setPsf01DisLastyearAudAdj(String psf01DisLastyearAudAdj) {
		this.psf01DisLastyearAudAdj = psf01DisLastyearAudAdj;
	}

	@Column(name="psf01_dis_lastyear_aud_adj", nullable=true, length=50)	
	public String getPsf01DisLastyearAudAdj() {
		return psf01DisLastyearAudAdj;
	}

	public void setPsf01DisctMemo(String psf01DisctMemo) {
		this.psf01DisctMemo = psf01DisctMemo;
	}

	@Column(name="psf01_disct_memo", nullable=true, length=500)	
	public String getPsf01DisctMemo() {
		return psf01DisctMemo;
	}

	public void setPsf01DisctIncludeTax(String psf01DisctIncludeTax) {
		this.psf01DisctIncludeTax = psf01DisctIncludeTax;
	}

	@Column(name="psf01_disct_include_tax", nullable=true, length=50)	
	public String getPsf01DisctIncludeTax() {
		return psf01DisctIncludeTax;
	}

	public void setPsf01DisctIncludeNotax(String psf01DisctIncludeNotax) {
		this.psf01DisctIncludeNotax = psf01DisctIncludeNotax;
	}

	@Column(name="psf01_disct_include_notax", nullable=true, length=50)	
	public String getPsf01DisctIncludeNotax() {
		return psf01DisctIncludeNotax;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
