package com.sie.watsons.base.report.model.entities;

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
 * TtaOiBillLineEntity_HI Entity Object
 * Sun Sep 01 19:26:03 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_OI_BILL_LINE")
public class TtaOiBillLineEntity_HI {
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date accountMonth;
	private Integer ttaOiBillImportId;
	private String rmsCode;
	private String venderName;
	private String importjv;
	private String invoicename;
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
	private String purchase;
	private String goodsReturn;
	private String netPurchase;
	private String dsd;
	private String purchaseOrigin;
	private String goodsReturnOrigin;
	private String netPurchaseOrigin;
	private String adrbContractText;
	private String adrbSumMoney;
	private String epContractText;
	private String epSumMoney;
	private String adpfContractText;
	private String adpfSumMoney;
	private String addaContractText;
	private String addaSumMoney;
	private String vir002ContractText;
	private String vir002SumMoney;
	private String vir001ContractText;
	private String vir001SumMoney;
	private String addgContractText;
	private String addgSumMoney;
	private String adtrContractText;
	private String adtrSumMoney;
	private String bdfContractText;
	private String bdfSumMoney;
	private String nss001ContractText;
	private String nss001ContractNum;
	private String nss001ContractData;
	private String nss001SumMoney;
	private String rss001ContractText;
	private String rss001ContractNum;
	private String rss001ContractData;
	private String rss001SumMoney;
	private String ass001ContractText;
	private String ass001ContractNum;
	private String ass001SumMoney;
	private String wdp001ContractText;
	private String wdp001SumMoney;
	private String npm001ContractText;
	private String npm001ContractNum;
	private String npm001ContractData;
	private String npm001SumMoney;
	private String drg001ContractText;
	private String drg001SumMoney;
	private String drh001ContractText;
	private String drh001summoney;
	private String drb001ContractText;
	private String drb001SumMoney;
	private String dro001ContractText;
	private String dro001SumMoney;
	private String cri001ContractText;
	private String cri001SumMoney;
	private String dmi001ContractText;
	private String dmi001SumMoney;
	private String hbi001ContractText;
	private String hbi001SumMoney;
	private String npd001ContractText;
	private String npd001SumMoney;
	private String bdf001ContractText;
	private String bdf001SumMoney;
	private String cos001ContractText;
	private String cos001SumMoney;
	private String oth001ContractText;
	private String oth001SumMoney;
	private String nti001ContractText;
	private String nti001SumMoney;
	private String ldp001SumMoney;
	private String ldp001ContractText;
	private String nfp001ContractText;
	private String nfp001SumMoney;
	private String cou001ContractText;
	private String cou001SumMoney;
	private String vip001ContractText;
	private String vip001SumMoney;
	private String lpu001ContractText;
	private String lpu001SumMoney;
	private String bac001ContractText;
	private String bac001SumMoney;
	private String uep001ContractText;
	private String uep001SumMoney;
	private String otf001ContractText;
	private String otf001SumMoney;
	private String psf001ContractText;
	private String psf001SumMoney;
	private String cps001ContractText;
	private String cps001SumMoney;
	private String apSumMoney;
	private String remark;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private String rmsCodeCount;
	private String sumIf;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date validEndDate;
	private String invoiceDisRate;
	private String invoiceDisIfvat;
	private String adrbSumExcltax;
	private String epSumExcltax;
	private String adpfSumExcltax;
	private String promotionDiscount;
	private String promotionDiscountTotal;
	private String promotionDiscountExcltax;
	private String distributionDiscount;
	private String distributionDiscountTotal;
	private String distributionDiscountExcltax;
	private String addaSumExcltx;
	private String vir002SumMoneyExcltx;
	private String vir001SumMoneyExcltx;
	private String returnedGoodsFee;
	private String returnedGoodsFeeTotal;
	private String returnedGoodsFeeExcltx;
	private String addgSumExcltx;
	private String adtrSumExcltx;
	private String bdfSumExcltx;
	private String nss001SumExcltx;
	private String rss001SumExltx;
	private String refitStoreSupport;
	private String refitStoreNumSupport;
	private String refitStoreDataSupport;
	private String refitStoreSupportTotal;
	private String refitStoreSupportTexltx;
	private String ass001SumExltx;
	private String wdp001SumExltx;
	private String npm001SumExltx;
	private String dataSharingFee;
	private String dataSharingFeeTotal;
	private String dataSharingFeeExltx;
	private String drb001SumExltx;
	private String dro001SumExltx;
	private String displayRental;
	private String displayRentalTotal;
	private String displayRentalTotalExltx;
	private String displayNewtp;
	private String displayNewtpTotal;
	private String displayNewtpExltx;
	private String dmi001SumExltx;
	private String promotionFund;
	private String promotionFundTotal;
	private String promotionFundExltx;
	private String hbi001SumExltx;
	private String bdf001SumExltx;
	private String businessDevFund;
	private String businessDevFundTotal;
	private String businessDevFundExltx;
	private String cos001SumExltx;
	private String oth001SumExltx;
	private String nti001SumExltx;
	private String ldp001SumExltx;
	private String nfp001SumExltx;
	private String lpu001SumExltx;
	private String bac001SumExltx;
	private String uep001SumExltx;
	private String otf001SumExltx;
	private String qaSerFee;
	private String qaSerFeeTotal;
	private String qaSerFeeExltx;
	private String psf001SumExltx;
	private String promotionSerFee;
	private String promotionSerFeeTotal;
	private String promotionSerFeeExltx;
	private String cps001SumExltx;
	private String compensationIncomeTotal;
	private String compensationIncomeExltx;
	private String otherProFee;
	private String otherProFeeTotal;
	private String otherProFeeExltx;
	private String obService;
	private String obServiceTotal;
	private String obServiceExltx;
	private String oemTester;
	private String oemTesterTotal;
	private String oemTesterExltx;
	private String romotionMktg;
	private String romotionMktgTotal;
	private String romotionMktgExltx;
	private String termsSales;
	private String termsSalesTotal;
	private String termsSalesExltx;
	private String taxSum;
	private String taxSumExltx;
	private String taxInclude;
	private String tax1Sum;
	private String tax1SumExltx;
	private String tax1Include;
	private String taxSum16;
	private String taxSum16Exltx;
	private String taxInclude16;
	private String taxSum13;
	private String taxSum13Exltx;
	private String taxInclude13;
	private String taxSum10;
	private String taxSum10Exltx;
	private String taxInclude10;
	private String tax1Sum6;
	private String tax1Sum6Exltx;
	private String tax1Include6;
	private String netOiExltx;
	private String compensationIncome;
	private String vip001SumExltx;
	private String cou001SumExltx;
	private String npd001SumExltx;
	private String cri001SumExltx;
	private String businessDevSupportExcltx;
	private String businessDevSupport;
	private String businessDevSupportTotal;
	private Integer operatorUserId;

	public void setAccountMonth(Date accountMonth) {
		this.accountMonth = accountMonth;
	}

	@Column(name="account_month", nullable=true, length=7)
	public Date getAccountMonth() {
		return accountMonth;
	}

	public void setTtaOiBillImportId(Integer ttaOiBillImportId) {
		this.ttaOiBillImportId = ttaOiBillImportId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_TTA_OI_BILL_LINE", sequenceName = "SEQ_TTA_OI_BILL_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_OI_BILL_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="tta_oi_bill_import_id", nullable=false, length=22)
	public Integer getTtaOiBillImportId() {
		return ttaOiBillImportId;
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

	public void setImportjv(String importjv) {
		this.importjv = importjv;
	}

	@Column(name="importjv", nullable=true, length=200)
	public String getImportjv() {
		return importjv;
	}

	public void setInvoicename(String invoicename) {
		this.invoicename = invoicename;
	}

	@Column(name="invoicename", nullable=true, length=200)
	public String getInvoicename() {
		return invoicename;
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

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	@Column(name="purchase", nullable=true, length=500)
	public String getPurchase() {
		return purchase;
	}

	public void setGoodsReturn(String goodsReturn) {
		this.goodsReturn = goodsReturn;
	}

	@Column(name="goods_return", nullable=true, length=500)
	public String getGoodsReturn() {
		return goodsReturn;
	}

	public void setNetPurchase(String netPurchase) {
		this.netPurchase = netPurchase;
	}

	@Column(name="net_purchase", nullable=true, length=500)
	public String getNetPurchase() {
		return netPurchase;
	}

	public void setDsd(String dsd) {
		this.dsd = dsd;
	}

	@Column(name="dsd", nullable=true, length=500)
	public String getDsd() {
		return dsd;
	}

	public void setPurchaseOrigin(String purchaseOrigin) {
		this.purchaseOrigin = purchaseOrigin;
	}

	@Column(name="purchase_origin", nullable=true, length=500)
	public String getPurchaseOrigin() {
		return purchaseOrigin;
	}

	public void setGoodsReturnOrigin(String goodsReturnOrigin) {
		this.goodsReturnOrigin = goodsReturnOrigin;
	}

	@Column(name="goods_return_origin", nullable=true, length=500)
	public String getGoodsReturnOrigin() {
		return goodsReturnOrigin;
	}

	public void setNetPurchaseOrigin(String netPurchaseOrigin) {
		this.netPurchaseOrigin = netPurchaseOrigin;
	}

	@Column(name="net_purchase_origin", nullable=true, length=500)
	public String getNetPurchaseOrigin() {
		return netPurchaseOrigin;
	}

	public void setAdrbContractText(String adrbContractText) {
		this.adrbContractText = adrbContractText;
	}

	@Column(name="adrb_contract_text", nullable=true, length=500)
	public String getAdrbContractText() {
		return adrbContractText;
	}

	public void setAdrbSumMoney(String adrbSumMoney) {
		this.adrbSumMoney = adrbSumMoney;
	}

	@Column(name="adrb_sum_money", nullable=true, length=500)
	public String getAdrbSumMoney() {
		return adrbSumMoney;
	}

	public void setEpContractText(String epContractText) {
		this.epContractText = epContractText;
	}

	@Column(name="ep_contract_text", nullable=true, length=500)
	public String getEpContractText() {
		return epContractText;
	}

	public void setEpSumMoney(String epSumMoney) {
		this.epSumMoney = epSumMoney;
	}

	@Column(name="ep_sum_money", nullable=true, length=500)
	public String getEpSumMoney() {
		return epSumMoney;
	}

	public void setAdpfContractText(String adpfContractText) {
		this.adpfContractText = adpfContractText;
	}

	@Column(name="adpf_contract_text", nullable=true, length=500)
	public String getAdpfContractText() {
		return adpfContractText;
	}

	public void setAdpfSumMoney(String adpfSumMoney) {
		this.adpfSumMoney = adpfSumMoney;
	}

	@Column(name="adpf_sum_money", nullable=true, length=500)
	public String getAdpfSumMoney() {
		return adpfSumMoney;
	}

	public void setAddaContractText(String addaContractText) {
		this.addaContractText = addaContractText;
	}

	@Column(name="adda_contract_text", nullable=true, length=500)
	public String getAddaContractText() {
		return addaContractText;
	}

	public void setAddaSumMoney(String addaSumMoney) {
		this.addaSumMoney = addaSumMoney;
	}

	@Column(name="adda_sum_money", nullable=true, length=500)
	public String getAddaSumMoney() {
		return addaSumMoney;
	}

	public void setVir002ContractText(String vir002ContractText) {
		this.vir002ContractText = vir002ContractText;
	}

	@Column(name="vir002_contract_text", nullable=true, length=500)
	public String getVir002ContractText() {
		return vir002ContractText;
	}

	public void setVir002SumMoney(String vir002SumMoney) {
		this.vir002SumMoney = vir002SumMoney;
	}

	@Column(name="vir002_sum_money", nullable=true, length=500)
	public String getVir002SumMoney() {
		return vir002SumMoney;
	}

	public void setVir001ContractText(String vir001ContractText) {
		this.vir001ContractText = vir001ContractText;
	}

	@Column(name="vir001_contract_text", nullable=true, length=500)
	public String getVir001ContractText() {
		return vir001ContractText;
	}

	public void setVir001SumMoney(String vir001SumMoney) {
		this.vir001SumMoney = vir001SumMoney;
	}

	@Column(name="vir001_sum_money", nullable=true, length=500)
	public String getVir001SumMoney() {
		return vir001SumMoney;
	}

	public void setAddgContractText(String addgContractText) {
		this.addgContractText = addgContractText;
	}

	@Column(name="addg_contract_text", nullable=true, length=500)
	public String getAddgContractText() {
		return addgContractText;
	}

	public void setAddgSumMoney(String addgSumMoney) {
		this.addgSumMoney = addgSumMoney;
	}

	@Column(name="addg_sum_money", nullable=true, length=500)
	public String getAddgSumMoney() {
		return addgSumMoney;
	}

	public void setAdtrContractText(String adtrContractText) {
		this.adtrContractText = adtrContractText;
	}

	@Column(name="adtr_contract_text", nullable=true, length=500)
	public String getAdtrContractText() {
		return adtrContractText;
	}

	public void setAdtrSumMoney(String adtrSumMoney) {
		this.adtrSumMoney = adtrSumMoney;
	}

	@Column(name="adtr_sum_money", nullable=true, length=500)
	public String getAdtrSumMoney() {
		return adtrSumMoney;
	}

	public void setBdfContractText(String bdfContractText) {
		this.bdfContractText = bdfContractText;
	}

	@Column(name="bdf_contract_text", nullable=true, length=500)
	public String getBdfContractText() {
		return bdfContractText;
	}

	public void setBdfSumMoney(String bdfSumMoney) {
		this.bdfSumMoney = bdfSumMoney;
	}

	@Column(name="bdf_sum_money", nullable=true, length=500)
	public String getBdfSumMoney() {
		return bdfSumMoney;
	}

	public void setNss001ContractText(String nss001ContractText) {
		this.nss001ContractText = nss001ContractText;
	}

	@Column(name="nss001_contract_text", nullable=true, length=500)
	public String getNss001ContractText() {
		return nss001ContractText;
	}

	public void setNss001ContractNum(String nss001ContractNum) {
		this.nss001ContractNum = nss001ContractNum;
	}

	@Column(name="nss001_contract_num", nullable=true, length=500)
	public String getNss001ContractNum() {
		return nss001ContractNum;
	}

	public void setNss001ContractData(String nss001ContractData) {
		this.nss001ContractData = nss001ContractData;
	}

	@Column(name="nss001_contract_data", nullable=true, length=1000)
	public String getNss001ContractData() {
		return nss001ContractData;
	}

	public void setNss001SumMoney(String nss001SumMoney) {
		this.nss001SumMoney = nss001SumMoney;
	}

	@Column(name="nss001_sum_money", nullable=true, length=500)
	public String getNss001SumMoney() {
		return nss001SumMoney;
	}

	public void setRss001ContractText(String rss001ContractText) {
		this.rss001ContractText = rss001ContractText;
	}

	@Column(name="rss001_contract_text", nullable=true, length=500)
	public String getRss001ContractText() {
		return rss001ContractText;
	}

	public void setRss001ContractNum(String rss001ContractNum) {
		this.rss001ContractNum = rss001ContractNum;
	}

	@Column(name="rss001_contract_num", nullable=true, length=500)
	public String getRss001ContractNum() {
		return rss001ContractNum;
	}

	public void setRss001ContractData(String rss001ContractData) {
		this.rss001ContractData = rss001ContractData;
	}

	@Column(name="rss001_contract_data", nullable=true, length=1000)
	public String getRss001ContractData() {
		return rss001ContractData;
	}

	public void setRss001SumMoney(String rss001SumMoney) {
		this.rss001SumMoney = rss001SumMoney;
	}

	@Column(name="rss001_sum_money", nullable=true, length=500)
	public String getRss001SumMoney() {
		return rss001SumMoney;
	}

	public void setAss001ContractText(String ass001ContractText) {
		this.ass001ContractText = ass001ContractText;
	}

	@Column(name="ass001_contract_text", nullable=true, length=500)
	public String getAss001ContractText() {
		return ass001ContractText;
	}

	public void setAss001ContractNum(String ass001ContractNum) {
		this.ass001ContractNum = ass001ContractNum;
	}

	@Column(name="ass001_contract_num", nullable=true, length=500)
	public String getAss001ContractNum() {
		return ass001ContractNum;
	}

	public void setAss001SumMoney(String ass001SumMoney) {
		this.ass001SumMoney = ass001SumMoney;
	}

	@Column(name="ass001_sum_money", nullable=true, length=500)
	public String getAss001SumMoney() {
		return ass001SumMoney;
	}

	public void setWdp001ContractText(String wdp001ContractText) {
		this.wdp001ContractText = wdp001ContractText;
	}

	@Column(name="wdp001_contract_text", nullable=true, length=500)
	public String getWdp001ContractText() {
		return wdp001ContractText;
	}

	public void setWdp001SumMoney(String wdp001SumMoney) {
		this.wdp001SumMoney = wdp001SumMoney;
	}

	@Column(name="wdp001_sum_money", nullable=true, length=500)
	public String getWdp001SumMoney() {
		return wdp001SumMoney;
	}

	public void setNpm001ContractText(String npm001ContractText) {
		this.npm001ContractText = npm001ContractText;
	}

	@Column(name="npm001_contract_text", nullable=true, length=500)
	public String getNpm001ContractText() {
		return npm001ContractText;
	}

	public void setNpm001ContractNum(String npm001ContractNum) {
		this.npm001ContractNum = npm001ContractNum;
	}

	@Column(name="npm001_contract_num", nullable=true, length=500)
	public String getNpm001ContractNum() {
		return npm001ContractNum;
	}

	public void setNpm001ContractData(String npm001ContractData) {
		this.npm001ContractData = npm001ContractData;
	}

	@Column(name="npm001_contract_data", nullable=true, length=500)
	public String getNpm001ContractData() {
		return npm001ContractData;
	}

	public void setNpm001SumMoney(String npm001SumMoney) {
		this.npm001SumMoney = npm001SumMoney;
	}

	@Column(name="npm001_sum_money", nullable=true, length=500)
	public String getNpm001SumMoney() {
		return npm001SumMoney;
	}

	public void setDrg001ContractText(String drg001ContractText) {
		this.drg001ContractText = drg001ContractText;
	}

	@Column(name="drg001_contract_text", nullable=true, length=500)
	public String getDrg001ContractText() {
		return drg001ContractText;
	}

	public void setDrg001SumMoney(String drg001SumMoney) {
		this.drg001SumMoney = drg001SumMoney;
	}

	@Column(name="drg001_sum_money", nullable=true, length=500)
	public String getDrg001SumMoney() {
		return drg001SumMoney;
	}

	public void setDrh001ContractText(String drh001ContractText) {
		this.drh001ContractText = drh001ContractText;
	}

	@Column(name="drh001_contract_text", nullable=true, length=500)
	public String getDrh001ContractText() {
		return drh001ContractText;
	}

	public void setDrh001summoney(String drh001summoney) {
		this.drh001summoney = drh001summoney;
	}

	@Column(name="drh001summoney", nullable=true, length=500)
	public String getDrh001summoney() {
		return drh001summoney;
	}

	public void setDrb001ContractText(String drb001ContractText) {
		this.drb001ContractText = drb001ContractText;
	}

	@Column(name="drb001_contract_text", nullable=true, length=500)
	public String getDrb001ContractText() {
		return drb001ContractText;
	}

	public void setDrb001SumMoney(String drb001SumMoney) {
		this.drb001SumMoney = drb001SumMoney;
	}

	@Column(name="drb001_sum_money", nullable=true, length=500)
	public String getDrb001SumMoney() {
		return drb001SumMoney;
	}

	public void setDro001ContractText(String dro001ContractText) {
		this.dro001ContractText = dro001ContractText;
	}

	@Column(name="dro001_contract_text", nullable=true, length=500)
	public String getDro001ContractText() {
		return dro001ContractText;
	}

	public void setDro001SumMoney(String dro001SumMoney) {
		this.dro001SumMoney = dro001SumMoney;
	}

	@Column(name="dro001_sum_money", nullable=true, length=500)
	public String getDro001SumMoney() {
		return dro001SumMoney;
	}

	public void setCri001ContractText(String cri001ContractText) {
		this.cri001ContractText = cri001ContractText;
	}

	@Column(name="cri001_contract_text", nullable=true, length=500)
	public String getCri001ContractText() {
		return cri001ContractText;
	}

	public void setCri001SumMoney(String cri001SumMoney) {
		this.cri001SumMoney = cri001SumMoney;
	}

	@Column(name="cri001_sum_money", nullable=true, length=500)
	public String getCri001SumMoney() {
		return cri001SumMoney;
	}

	public void setDmi001ContractText(String dmi001ContractText) {
		this.dmi001ContractText = dmi001ContractText;
	}

	@Column(name="dmi001_contract_text", nullable=true, length=500)
	public String getDmi001ContractText() {
		return dmi001ContractText;
	}

	public void setDmi001SumMoney(String dmi001SumMoney) {
		this.dmi001SumMoney = dmi001SumMoney;
	}

	@Column(name="dmi001_sum_money", nullable=true, length=500)
	public String getDmi001SumMoney() {
		return dmi001SumMoney;
	}

	public void setHbi001ContractText(String hbi001ContractText) {
		this.hbi001ContractText = hbi001ContractText;
	}

	@Column(name="hbi001_contract_text", nullable=true, length=500)
	public String getHbi001ContractText() {
		return hbi001ContractText;
	}

	public void setHbi001SumMoney(String hbi001SumMoney) {
		this.hbi001SumMoney = hbi001SumMoney;
	}

	@Column(name="hbi001_sum_money", nullable=true, length=500)
	public String getHbi001SumMoney() {
		return hbi001SumMoney;
	}

	public void setNpd001ContractText(String npd001ContractText) {
		this.npd001ContractText = npd001ContractText;
	}

	@Column(name="npd001_contract_text", nullable=true, length=500)
	public String getNpd001ContractText() {
		return npd001ContractText;
	}

	public void setNpd001SumMoney(String npd001SumMoney) {
		this.npd001SumMoney = npd001SumMoney;
	}

	@Column(name="npd001_sum_money", nullable=true, length=500)
	public String getNpd001SumMoney() {
		return npd001SumMoney;
	}

	public void setBdf001ContractText(String bdf001ContractText) {
		this.bdf001ContractText = bdf001ContractText;
	}

	@Column(name="bdf001_contract_text", nullable=true, length=500)
	public String getBdf001ContractText() {
		return bdf001ContractText;
	}

	public void setBdf001SumMoney(String bdf001SumMoney) {
		this.bdf001SumMoney = bdf001SumMoney;
	}

	@Column(name="bdf001_sum_money", nullable=true, length=500)
	public String getBdf001SumMoney() {
		return bdf001SumMoney;
	}

	public void setCos001ContractText(String cos001ContractText) {
		this.cos001ContractText = cos001ContractText;
	}

	@Column(name="cos001_contract_text", nullable=true, length=500)
	public String getCos001ContractText() {
		return cos001ContractText;
	}

	public void setCos001SumMoney(String cos001SumMoney) {
		this.cos001SumMoney = cos001SumMoney;
	}

	@Column(name="cos001_sum_money", nullable=true, length=500)
	public String getCos001SumMoney() {
		return cos001SumMoney;
	}

	public void setOth001ContractText(String oth001ContractText) {
		this.oth001ContractText = oth001ContractText;
	}

	@Column(name="oth001_contract_text", nullable=true, length=500)
	public String getOth001ContractText() {
		return oth001ContractText;
	}

	public void setOth001SumMoney(String oth001SumMoney) {
		this.oth001SumMoney = oth001SumMoney;
	}

	@Column(name="oth001_sum_money", nullable=true, length=100)
	public String getOth001SumMoney() {
		return oth001SumMoney;
	}

	public void setNti001ContractText(String nti001ContractText) {
		this.nti001ContractText = nti001ContractText;
	}

	@Column(name="nti001_contract_text", nullable=true, length=500)
	public String getNti001ContractText() {
		return nti001ContractText;
	}

	public void setNti001SumMoney(String nti001SumMoney) {
		this.nti001SumMoney = nti001SumMoney;
	}

	@Column(name="nti001_sum_money", nullable=true, length=500)
	public String getNti001SumMoney() {
		return nti001SumMoney;
	}

	public void setLdp001SumMoney(String ldp001SumMoney) {
		this.ldp001SumMoney = ldp001SumMoney;
	}

	@Column(name="ldp001_sum_money", nullable=true, length=500)
	public String getLdp001SumMoney() {
		return ldp001SumMoney;
	}

	public void setLdp001ContractText(String ldp001ContractText) {
		this.ldp001ContractText = ldp001ContractText;
	}

	@Column(name="ldp001_contract_text", nullable=true, length=500)
	public String getLdp001ContractText() {
		return ldp001ContractText;
	}

	public void setNfp001ContractText(String nfp001ContractText) {
		this.nfp001ContractText = nfp001ContractText;
	}

	@Column(name="nfp001_contract_text", nullable=true, length=500)
	public String getNfp001ContractText() {
		return nfp001ContractText;
	}

	public void setNfp001SumMoney(String nfp001SumMoney) {
		this.nfp001SumMoney = nfp001SumMoney;
	}

	@Column(name="nfp001_sum_money", nullable=true, length=500)
	public String getNfp001SumMoney() {
		return nfp001SumMoney;
	}

	public void setCou001ContractText(String cou001ContractText) {
		this.cou001ContractText = cou001ContractText;
	}

	@Column(name="cou001_contract_text", nullable=true, length=500)
	public String getCou001ContractText() {
		return cou001ContractText;
	}

	public void setCou001SumMoney(String cou001SumMoney) {
		this.cou001SumMoney = cou001SumMoney;
	}

	@Column(name="cou001_sum_money", nullable=true, length=500)
	public String getCou001SumMoney() {
		return cou001SumMoney;
	}

	public void setVip001ContractText(String vip001ContractText) {
		this.vip001ContractText = vip001ContractText;
	}

	@Column(name="vip001_contract_text", nullable=true, length=500)
	public String getVip001ContractText() {
		return vip001ContractText;
	}

	public void setVip001SumMoney(String vip001SumMoney) {
		this.vip001SumMoney = vip001SumMoney;
	}

	@Column(name="vip001_sum_money", nullable=true, length=500)
	public String getVip001SumMoney() {
		return vip001SumMoney;
	}

	public void setLpu001ContractText(String lpu001ContractText) {
		this.lpu001ContractText = lpu001ContractText;
	}

	@Column(name="lpu001_contract_text", nullable=true, length=500)
	public String getLpu001ContractText() {
		return lpu001ContractText;
	}

	public void setLpu001SumMoney(String lpu001SumMoney) {
		this.lpu001SumMoney = lpu001SumMoney;
	}

	@Column(name="lpu001_sum_money", nullable=true, length=500)
	public String getLpu001SumMoney() {
		return lpu001SumMoney;
	}

	public void setBac001ContractText(String bac001ContractText) {
		this.bac001ContractText = bac001ContractText;
	}

	@Column(name="bac001_contract_text", nullable=true, length=500)
	public String getBac001ContractText() {
		return bac001ContractText;
	}

	public void setBac001SumMoney(String bac001SumMoney) {
		this.bac001SumMoney = bac001SumMoney;
	}

	@Column(name="bac001_sum_money", nullable=true, length=500)
	public String getBac001SumMoney() {
		return bac001SumMoney;
	}

	public void setUep001ContractText(String uep001ContractText) {
		this.uep001ContractText = uep001ContractText;
	}

	@Column(name="uep001_contract_text", nullable=true, length=500)
	public String getUep001ContractText() {
		return uep001ContractText;
	}

	public void setUep001SumMoney(String uep001SumMoney) {
		this.uep001SumMoney = uep001SumMoney;
	}

	@Column(name="uep001_sum_money", nullable=true, length=500)
	public String getUep001SumMoney() {
		return uep001SumMoney;
	}

	public void setOtf001ContractText(String otf001ContractText) {
		this.otf001ContractText = otf001ContractText;
	}

	@Column(name="otf001_contract_text", nullable=true, length=500)
	public String getOtf001ContractText() {
		return otf001ContractText;
	}

	public void setOtf001SumMoney(String otf001SumMoney) {
		this.otf001SumMoney = otf001SumMoney;
	}

	@Column(name="otf001_sum_money", nullable=true, length=500)
	public String getOtf001SumMoney() {
		return otf001SumMoney;
	}

	public void setPsf001ContractText(String psf001ContractText) {
		this.psf001ContractText = psf001ContractText;
	}

	@Column(name="psf001_contract_text", nullable=true, length=500)
	public String getPsf001ContractText() {
		return psf001ContractText;
	}

	public void setPsf001SumMoney(String psf001SumMoney) {
		this.psf001SumMoney = psf001SumMoney;
	}

	@Column(name="psf001_sum_money", nullable=true, length=100)
	public String getPsf001SumMoney() {
		return psf001SumMoney;
	}

	public void setCps001ContractText(String cps001ContractText) {
		this.cps001ContractText = cps001ContractText;
	}

	@Column(name="cps001_contract_text", nullable=true, length=500)
	public String getCps001ContractText() {
		return cps001ContractText;
	}

	public void setCps001SumMoney(String cps001SumMoney) {
		this.cps001SumMoney = cps001SumMoney;
	}

	@Column(name="cps001_sum_money", nullable=true, length=500)
	public String getCps001SumMoney() {
		return cps001SumMoney;
	}

	public void setApSumMoney(String apSumMoney) {
		this.apSumMoney = apSumMoney;
	}

	@Column(name="ap_sum_money", nullable=true, length=500)
	public String getApSumMoney() {
		return apSumMoney;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=500)
	public String getRemark() {
		return remark;
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

	public void setRmsCodeCount(String rmsCodeCount) {
		this.rmsCodeCount = rmsCodeCount;
	}

	@Column(name="rms_code_count", nullable=true, length=100)
	public String getRmsCodeCount() {
		return rmsCodeCount;
	}

	public void setSumIf(String sumIf) {
		this.sumIf = sumIf;
	}

	@Column(name="sum_if", nullable=true, length=200)
	public String getSumIf() {
		return sumIf;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	@Column(name="valid_end_date", nullable=true, length=7)
	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setInvoiceDisRate(String invoiceDisRate) {
		this.invoiceDisRate = invoiceDisRate;
	}

	@Column(name="invoice_dis_rate", nullable=true, length=10)
	public String getInvoiceDisRate() {
		return invoiceDisRate;
	}

	public void setInvoiceDisIfvat(String invoiceDisIfvat) {
		this.invoiceDisIfvat = invoiceDisIfvat;
	}

	@Column(name="invoice_dis_ifvat", nullable=true, length=10)
	public String getInvoiceDisIfvat() {
		return invoiceDisIfvat;
	}

	public void setAdrbSumExcltax(String adrbSumExcltax) {
		this.adrbSumExcltax = adrbSumExcltax;
	}

	@Column(name="adrb_sum_excltax", nullable=true, length=500)
	public String getAdrbSumExcltax() {
		return adrbSumExcltax;
	}

	public void setEpSumExcltax(String epSumExcltax) {
		this.epSumExcltax = epSumExcltax;
	}

	@Column(name="ep_sum_excltax", nullable=true, length=500)
	public String getEpSumExcltax() {
		return epSumExcltax;
	}

	public void setAdpfSumExcltax(String adpfSumExcltax) {
		this.adpfSumExcltax = adpfSumExcltax;
	}

	@Column(name="adpf_sum_excltax", nullable=true, length=500)
	public String getAdpfSumExcltax() {
		return adpfSumExcltax;
	}

	public void setPromotionDiscount(String promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	@Column(name="promotion_discount", nullable=true, length=500)
	public String getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscountTotal(String promotionDiscountTotal) {
		this.promotionDiscountTotal = promotionDiscountTotal;
	}

	@Column(name="promotion_discount_total", nullable=true, length=500)
	public String getPromotionDiscountTotal() {
		return promotionDiscountTotal;
	}

	public void setPromotionDiscountExcltax(String promotionDiscountExcltax) {
		this.promotionDiscountExcltax = promotionDiscountExcltax;
	}

	@Column(name="promotion_discount_excltax", nullable=true, length=500)
	public String getPromotionDiscountExcltax() {
		return promotionDiscountExcltax;
	}

	public void setDistributionDiscount(String distributionDiscount) {
		this.distributionDiscount = distributionDiscount;
	}

	@Column(name="distribution_discount", nullable=true, length=500)
	public String getDistributionDiscount() {
		return distributionDiscount;
	}

	public void setDistributionDiscountTotal(String distributionDiscountTotal) {
		this.distributionDiscountTotal = distributionDiscountTotal;
	}

	@Column(name="distribution_discount_total", nullable=true, length=500)
	public String getDistributionDiscountTotal() {
		return distributionDiscountTotal;
	}

	public void setDistributionDiscountExcltax(String distributionDiscountExcltax) {
		this.distributionDiscountExcltax = distributionDiscountExcltax;
	}

	@Column(name="distribution_discount_excltax", nullable=true, length=500)
	public String getDistributionDiscountExcltax() {
		return distributionDiscountExcltax;
	}

	public void setAddaSumExcltx(String addaSumExcltx) {
		this.addaSumExcltx = addaSumExcltx;
	}

	@Column(name="adda_sum_excltx", nullable=true, length=500)
	public String getAddaSumExcltx() {
		return addaSumExcltx;
	}

	public void setVir002SumMoneyExcltx(String vir002SumMoneyExcltx) {
		this.vir002SumMoneyExcltx = vir002SumMoneyExcltx;
	}

	@Column(name="vir002_sum_money_excltx", nullable=true, length=500)
	public String getVir002SumMoneyExcltx() {
		return vir002SumMoneyExcltx;
	}

	public void setVir001SumMoneyExcltx(String vir001SumMoneyExcltx) {
		this.vir001SumMoneyExcltx = vir001SumMoneyExcltx;
	}

	@Column(name="vir001_sum_money_excltx", nullable=true, length=500)
	public String getVir001SumMoneyExcltx() {
		return vir001SumMoneyExcltx;
	}

	public void setReturnedGoodsFee(String returnedGoodsFee) {
		this.returnedGoodsFee = returnedGoodsFee;
	}

	@Column(name="returned_goods_fee", nullable=true, length=500)
	public String getReturnedGoodsFee() {
		return returnedGoodsFee;
	}

	public void setReturnedGoodsFeeTotal(String returnedGoodsFeeTotal) {
		this.returnedGoodsFeeTotal = returnedGoodsFeeTotal;
	}

	@Column(name="returned_goods_fee_total", nullable=true, length=500)
	public String getReturnedGoodsFeeTotal() {
		return returnedGoodsFeeTotal;
	}

	public void setReturnedGoodsFeeExcltx(String returnedGoodsFeeExcltx) {
		this.returnedGoodsFeeExcltx = returnedGoodsFeeExcltx;
	}

	@Column(name="returned_goods_fee_excltx", nullable=true, length=500)
	public String getReturnedGoodsFeeExcltx() {
		return returnedGoodsFeeExcltx;
	}

	public void setAddgSumExcltx(String addgSumExcltx) {
		this.addgSumExcltx = addgSumExcltx;
	}

	@Column(name="addg_sum_excltx", nullable=true, length=500)
	public String getAddgSumExcltx() {
		return addgSumExcltx;
	}

	public void setAdtrSumExcltx(String adtrSumExcltx) {
		this.adtrSumExcltx = adtrSumExcltx;
	}

	@Column(name="adtr_sum_excltx", nullable=true, length=500)
	public String getAdtrSumExcltx() {
		return adtrSumExcltx;
	}

	public void setBdfSumExcltx(String bdfSumExcltx) {
		this.bdfSumExcltx = bdfSumExcltx;
	}

	@Column(name="bdf_sum_excltx", nullable=true, length=500)
	public String getBdfSumExcltx() {
		return bdfSumExcltx;
	}

	public void setNss001SumExcltx(String nss001SumExcltx) {
		this.nss001SumExcltx = nss001SumExcltx;
	}

	@Column(name="nss001_sum_excltx", nullable=true, length=500)
	public String getNss001SumExcltx() {
		return nss001SumExcltx;
	}

	public void setRss001SumExltx(String rss001SumExltx) {
		this.rss001SumExltx = rss001SumExltx;
	}

	@Column(name="rss001_sum_exltx", nullable=true, length=500)
	public String getRss001SumExltx() {
		return rss001SumExltx;
	}

	public void setRefitStoreSupport(String refitStoreSupport) {
		this.refitStoreSupport = refitStoreSupport;
	}

	@Column(name="refit_store_support", nullable=true, length=500)
	public String getRefitStoreSupport() {
		return refitStoreSupport;
	}

	public void setRefitStoreNumSupport(String refitStoreNumSupport) {
		this.refitStoreNumSupport = refitStoreNumSupport;
	}

	@Column(name="refit_store_num_support", nullable=true, length=500)
	public String getRefitStoreNumSupport() {
		return refitStoreNumSupport;
	}

	public void setRefitStoreDataSupport(String refitStoreDataSupport) {
		this.refitStoreDataSupport = refitStoreDataSupport;
	}

	@Column(name="refit_store_data_support", nullable=true, length=1000)
	public String getRefitStoreDataSupport() {
		return refitStoreDataSupport;
	}

	public void setRefitStoreSupportTotal(String refitStoreSupportTotal) {
		this.refitStoreSupportTotal = refitStoreSupportTotal;
	}

	@Column(name="refit_store_support_total", nullable=true, length=100)
	public String getRefitStoreSupportTotal() {
		return refitStoreSupportTotal;
	}

	public void setRefitStoreSupportTexltx(String refitStoreSupportTexltx) {
		this.refitStoreSupportTexltx = refitStoreSupportTexltx;
	}

	@Column(name="refit_store_support_texltx", nullable=true, length=100)
	public String getRefitStoreSupportTexltx() {
		return refitStoreSupportTexltx;
	}

	public void setAss001SumExltx(String ass001SumExltx) {
		this.ass001SumExltx = ass001SumExltx;
	}

	@Column(name="ass001_sum_exltx", nullable=true, length=100)
	public String getAss001SumExltx() {
		return ass001SumExltx;
	}

	public void setWdp001SumExltx(String wdp001SumExltx) {
		this.wdp001SumExltx = wdp001SumExltx;
	}

	@Column(name="wdp001_sum_exltx", nullable=true, length=100)
	public String getWdp001SumExltx() {
		return wdp001SumExltx;
	}

	public void setNpm001SumExltx(String npm001SumExltx) {
		this.npm001SumExltx = npm001SumExltx;
	}

	@Column(name="npm001_sum_exltx", nullable=true, length=100)
	public String getNpm001SumExltx() {
		return npm001SumExltx;
	}

	public void setDataSharingFee(String dataSharingFee) {
		this.dataSharingFee = dataSharingFee;
	}

	@Column(name="data_sharing_fee", nullable=true, length=500)
	public String getDataSharingFee() {
		return dataSharingFee;
	}

	public void setDataSharingFeeTotal(String dataSharingFeeTotal) {
		this.dataSharingFeeTotal = dataSharingFeeTotal;
	}

	@Column(name="data_sharing_fee_total", nullable=true, length=100)
	public String getDataSharingFeeTotal() {
		return dataSharingFeeTotal;
	}

	public void setDataSharingFeeExltx(String dataSharingFeeExltx) {
		this.dataSharingFeeExltx = dataSharingFeeExltx;
	}

	@Column(name="data_sharing_fee_exltx", nullable=true, length=100)
	public String getDataSharingFeeExltx() {
		return dataSharingFeeExltx;
	}

	public void setDrb001SumExltx(String drb001SumExltx) {
		this.drb001SumExltx = drb001SumExltx;
	}

	@Column(name="drb001_sum_exltx", nullable=true, length=500)
	public String getDrb001SumExltx() {
		return drb001SumExltx;
	}

	public void setDro001SumExltx(String dro001SumExltx) {
		this.dro001SumExltx = dro001SumExltx;
	}

	@Column(name="dro001_sum_exltx", nullable=true, length=500)
	public String getDro001SumExltx() {
		return dro001SumExltx;
	}

	public void setDisplayRental(String displayRental) {
		this.displayRental = displayRental;
	}

	@Column(name="display_rental", nullable=true, length=500)
	public String getDisplayRental() {
		return displayRental;
	}

	public void setDisplayRentalTotal(String displayRentalTotal) {
		this.displayRentalTotal = displayRentalTotal;
	}

	@Column(name="display_rental_total", nullable=true, length=500)
	public String getDisplayRentalTotal() {
		return displayRentalTotal;
	}

	public void setDisplayRentalTotalExltx(String displayRentalTotalExltx) {
		this.displayRentalTotalExltx = displayRentalTotalExltx;
	}

	@Column(name="display_rental_total_exltx", nullable=true, length=500)
	public String getDisplayRentalTotalExltx() {
		return displayRentalTotalExltx;
	}

	public void setDisplayNewtp(String displayNewtp) {
		this.displayNewtp = displayNewtp;
	}

	@Column(name="display_newtp", nullable=true, length=500)
	public String getDisplayNewtp() {
		return displayNewtp;
	}

	public void setDisplayNewtpTotal(String displayNewtpTotal) {
		this.displayNewtpTotal = displayNewtpTotal;
	}

	@Column(name="display_newtp_total", nullable=true, length=500)
	public String getDisplayNewtpTotal() {
		return displayNewtpTotal;
	}

	public void setDisplayNewtpExltx(String displayNewtpExltx) {
		this.displayNewtpExltx = displayNewtpExltx;
	}

	@Column(name="display_newtp_exltx", nullable=true, length=500)
	public String getDisplayNewtpExltx() {
		return displayNewtpExltx;
	}

	public void setDmi001SumExltx(String dmi001SumExltx) {
		this.dmi001SumExltx = dmi001SumExltx;
	}

	@Column(name="dmi001_sum_exltx", nullable=true, length=500)
	public String getDmi001SumExltx() {
		return dmi001SumExltx;
	}

	public void setPromotionFund(String promotionFund) {
		this.promotionFund = promotionFund;
	}

	@Column(name="promotion_fund", nullable=true, length=500)
	public String getPromotionFund() {
		return promotionFund;
	}

	public void setPromotionFundTotal(String promotionFundTotal) {
		this.promotionFundTotal = promotionFundTotal;
	}

	@Column(name="promotion_fund_total", nullable=true, length=500)
	public String getPromotionFundTotal() {
		return promotionFundTotal;
	}

	public void setPromotionFundExltx(String promotionFundExltx) {
		this.promotionFundExltx = promotionFundExltx;
	}

	@Column(name="promotion_fund_exltx", nullable=true, length=500)
	public String getPromotionFundExltx() {
		return promotionFundExltx;
	}

	public void setHbi001SumExltx(String hbi001SumExltx) {
		this.hbi001SumExltx = hbi001SumExltx;
	}

	@Column(name="hbi001_sum_exltx", nullable=true, length=500)
	public String getHbi001SumExltx() {
		return hbi001SumExltx;
	}

	public void setBdf001SumExltx(String bdf001SumExltx) {
		this.bdf001SumExltx = bdf001SumExltx;
	}

	@Column(name="bdf001_sum_exltx", nullable=true, length=500)
	public String getBdf001SumExltx() {
		return bdf001SumExltx;
	}

	public void setBusinessDevFund(String businessDevFund) {
		this.businessDevFund = businessDevFund;
	}

	@Column(name="business_dev_fund", nullable=true, length=500)
	public String getBusinessDevFund() {
		return businessDevFund;
	}

	public void setBusinessDevFundTotal(String businessDevFundTotal) {
		this.businessDevFundTotal = businessDevFundTotal;
	}

	@Column(name="business_dev_fund_total", nullable=true, length=500)
	public String getBusinessDevFundTotal() {
		return businessDevFundTotal;
	}

	public void setBusinessDevFundExltx(String businessDevFundExltx) {
		this.businessDevFundExltx = businessDevFundExltx;
	}

	@Column(name="business_dev_fund_exltx", nullable=true, length=500)
	public String getBusinessDevFundExltx() {
		return businessDevFundExltx;
	}

	public void setCos001SumExltx(String cos001SumExltx) {
		this.cos001SumExltx = cos001SumExltx;
	}

	@Column(name="cos001_sum_exltx", nullable=true, length=500)
	public String getCos001SumExltx() {
		return cos001SumExltx;
	}

	public void setOth001SumExltx(String oth001SumExltx) {
		this.oth001SumExltx = oth001SumExltx;
	}

	@Column(name="oth001_sum_exltx", nullable=true, length=100)
	public String getOth001SumExltx() {
		return oth001SumExltx;
	}

	public void setNti001SumExltx(String nti001SumExltx) {
		this.nti001SumExltx = nti001SumExltx;
	}

	@Column(name="nti001_sum_exltx", nullable=true, length=100)
	public String getNti001SumExltx() {
		return nti001SumExltx;
	}

	public void setLdp001SumExltx(String ldp001SumExltx) {
		this.ldp001SumExltx = ldp001SumExltx;
	}

	@Column(name="ldp001_sum_exltx", nullable=true, length=100)
	public String getLdp001SumExltx() {
		return ldp001SumExltx;
	}

	public void setNfp001SumExltx(String nfp001SumExltx) {
		this.nfp001SumExltx = nfp001SumExltx;
	}

	@Column(name="nfp001_sum_exltx", nullable=true, length=100)
	public String getNfp001SumExltx() {
		return nfp001SumExltx;
	}

	public void setLpu001SumExltx(String lpu001SumExltx) {
		this.lpu001SumExltx = lpu001SumExltx;
	}

	@Column(name="lpu001_sum_exltx", nullable=true, length=100)
	public String getLpu001SumExltx() {
		return lpu001SumExltx;
	}

	public void setBac001SumExltx(String bac001SumExltx) {
		this.bac001SumExltx = bac001SumExltx;
	}

	@Column(name="bac001_sum_exltx", nullable=true, length=100)
	public String getBac001SumExltx() {
		return bac001SumExltx;
	}

	public void setUep001SumExltx(String uep001SumExltx) {
		this.uep001SumExltx = uep001SumExltx;
	}

	@Column(name="uep001_sum_exltx", nullable=true, length=100)
	public String getUep001SumExltx() {
		return uep001SumExltx;
	}

	public void setOtf001SumExltx(String otf001SumExltx) {
		this.otf001SumExltx = otf001SumExltx;
	}

	@Column(name="otf001_sum_exltx", nullable=true, length=100)
	public String getOtf001SumExltx() {
		return otf001SumExltx;
	}

	public void setQaSerFee(String qaSerFee) {
		this.qaSerFee = qaSerFee;
	}

	@Column(name="qa_ser_fee", nullable=true, length=500)
	public String getQaSerFee() {
		return qaSerFee;
	}

	public void setQaSerFeeTotal(String qaSerFeeTotal) {
		this.qaSerFeeTotal = qaSerFeeTotal;
	}

	@Column(name="qa_ser_fee_total", nullable=true, length=100)
	public String getQaSerFeeTotal() {
		return qaSerFeeTotal;
	}

	public void setQaSerFeeExltx(String qaSerFeeExltx) {
		this.qaSerFeeExltx = qaSerFeeExltx;
	}

	@Column(name="qa_ser_fee_exltx", nullable=true, length=100)
	public String getQaSerFeeExltx() {
		return qaSerFeeExltx;
	}

	public void setPsf001SumExltx(String psf001SumExltx) {
		this.psf001SumExltx = psf001SumExltx;
	}

	@Column(name="psf001_sum_exltx", nullable=true, length=100)
	public String getPsf001SumExltx() {
		return psf001SumExltx;
	}

	public void setPromotionSerFee(String promotionSerFee) {
		this.promotionSerFee = promotionSerFee;
	}

	@Column(name="promotion_ser_fee", nullable=true, length=500)
	public String getPromotionSerFee() {
		return promotionSerFee;
	}

	public void setPromotionSerFeeTotal(String promotionSerFeeTotal) {
		this.promotionSerFeeTotal = promotionSerFeeTotal;
	}

	@Column(name="promotion_ser_fee_total", nullable=true, length=100)
	public String getPromotionSerFeeTotal() {
		return promotionSerFeeTotal;
	}

	public void setPromotionSerFeeExltx(String promotionSerFeeExltx) {
		this.promotionSerFeeExltx = promotionSerFeeExltx;
	}

	@Column(name="promotion_ser_fee_exltx", nullable=true, length=100)
	public String getPromotionSerFeeExltx() {
		return promotionSerFeeExltx;
	}

	public void setCps001SumExltx(String cps001SumExltx) {
		this.cps001SumExltx = cps001SumExltx;
	}

	@Column(name="cps001_sum_exltx", nullable=true, length=100)
	public String getCps001SumExltx() {
		return cps001SumExltx;
	}

	public void setCompensationIncomeTotal(String compensationIncomeTotal) {
		this.compensationIncomeTotal = compensationIncomeTotal;
	}

	@Column(name="compensation_income_total", nullable=true, length=100)
	public String getCompensationIncomeTotal() {
		return compensationIncomeTotal;
	}

	public void setCompensationIncomeExltx(String compensationIncomeExltx) {
		this.compensationIncomeExltx = compensationIncomeExltx;
	}

	@Column(name="compensation_income_exltx", nullable=true, length=100)
	public String getCompensationIncomeExltx() {
		return compensationIncomeExltx;
	}

	public void setOtherProFee(String otherProFee) {
		this.otherProFee = otherProFee;
	}

	@Column(name="other_pro_fee", nullable=true, length=500)
	public String getOtherProFee() {
		return otherProFee;
	}

	public void setOtherProFeeTotal(String otherProFeeTotal) {
		this.otherProFeeTotal = otherProFeeTotal;
	}

	@Column(name="other_pro_fee_total", nullable=true, length=100)
	public String getOtherProFeeTotal() {
		return otherProFeeTotal;
	}

	public void setOtherProFeeExltx(String otherProFeeExltx) {
		this.otherProFeeExltx = otherProFeeExltx;
	}

	@Column(name="other_pro_fee_exltx", nullable=true, length=100)
	public String getOtherProFeeExltx() {
		return otherProFeeExltx;
	}

	public void setObService(String obService) {
		this.obService = obService;
	}

	@Column(name="ob_service", nullable=true, length=500)
	public String getObService() {
		return obService;
	}

	public void setObServiceTotal(String obServiceTotal) {
		this.obServiceTotal = obServiceTotal;
	}

	@Column(name="ob_service_total", nullable=true, length=100)
	public String getObServiceTotal() {
		return obServiceTotal;
	}

	public void setObServiceExltx(String obServiceExltx) {
		this.obServiceExltx = obServiceExltx;
	}

	@Column(name="ob_service_exltx", nullable=true, length=100)
	public String getObServiceExltx() {
		return obServiceExltx;
	}

	public void setOemTester(String oemTester) {
		this.oemTester = oemTester;
	}

	@Column(name="oem_tester", nullable=true, length=500)
	public String getOemTester() {
		return oemTester;
	}

	public void setOemTesterTotal(String oemTesterTotal) {
		this.oemTesterTotal = oemTesterTotal;
	}

	@Column(name="oem_tester_total", nullable=true, length=100)
	public String getOemTesterTotal() {
		return oemTesterTotal;
	}

	public void setOemTesterExltx(String oemTesterExltx) {
		this.oemTesterExltx = oemTesterExltx;
	}

	@Column(name="oem_tester_exltx", nullable=true, length=100)
	public String getOemTesterExltx() {
		return oemTesterExltx;
	}

	public void setRomotionMktg(String romotionMktg) {
		this.romotionMktg = romotionMktg;
	}

	@Column(name="romotion_mktg", nullable=true, length=500)
	public String getRomotionMktg() {
		return romotionMktg;
	}

	public void setRomotionMktgTotal(String romotionMktgTotal) {
		this.romotionMktgTotal = romotionMktgTotal;
	}

	@Column(name="romotion_mktg_total", nullable=true, length=100)
	public String getRomotionMktgTotal() {
		return romotionMktgTotal;
	}

	public void setRomotionMktgExltx(String romotionMktgExltx) {
		this.romotionMktgExltx = romotionMktgExltx;
	}

	@Column(name="romotion_mktg_exltx", nullable=true, length=100)
	public String getRomotionMktgExltx() {
		return romotionMktgExltx;
	}

	public void setTermsSales(String termsSales) {
		this.termsSales = termsSales;
	}

	@Column(name="terms_sales", nullable=true, length=500)
	public String getTermsSales() {
		return termsSales;
	}

	public void setTermsSalesTotal(String termsSalesTotal) {
		this.termsSalesTotal = termsSalesTotal;
	}

	@Column(name="terms_sales_total", nullable=true, length=100)
	public String getTermsSalesTotal() {
		return termsSalesTotal;
	}

	public void setTermsSalesExltx(String termsSalesExltx) {
		this.termsSalesExltx = termsSalesExltx;
	}

	@Column(name="terms_sales_exltx", nullable=true, length=100)
	public String getTermsSalesExltx() {
		return termsSalesExltx;
	}

	public void setTaxSum(String taxSum) {
		this.taxSum = taxSum;
	}

	@Column(name="tax_sum", nullable=true, length=100)
	public String getTaxSum() {
		return taxSum;
	}

	public void setTaxSumExltx(String taxSumExltx) {
		this.taxSumExltx = taxSumExltx;
	}

	@Column(name="tax_sum_exltx", nullable=true, length=100)
	public String getTaxSumExltx() {
		return taxSumExltx;
	}

	public void setTaxInclude(String taxInclude) {
		this.taxInclude = taxInclude;
	}

	@Column(name="tax_include", nullable=true, length=100)
	public String getTaxInclude() {
		return taxInclude;
	}

	public void setTax1Sum(String tax1Sum) {
		this.tax1Sum = tax1Sum;
	}

	@Column(name="tax1_sum", nullable=true, length=100)
	public String getTax1Sum() {
		return tax1Sum;
	}

	public void setTax1SumExltx(String tax1SumExltx) {
		this.tax1SumExltx = tax1SumExltx;
	}

	@Column(name="tax1_sum_exltx", nullable=true, length=100)
	public String getTax1SumExltx() {
		return tax1SumExltx;
	}

	public void setTax1Include(String tax1Include) {
		this.tax1Include = tax1Include;
	}

	@Column(name="tax1_include", nullable=true, length=100)
	public String getTax1Include() {
		return tax1Include;
	}

	public void setTaxSum16(String taxSum16) {
		this.taxSum16 = taxSum16;
	}

	@Column(name="tax_sum16", nullable=true, length=100)
	public String getTaxSum16() {
		return taxSum16;
	}

	public void setTaxSum16Exltx(String taxSum16Exltx) {
		this.taxSum16Exltx = taxSum16Exltx;
	}

	@Column(name="tax_sum16_exltx", nullable=true, length=100)
	public String getTaxSum16Exltx() {
		return taxSum16Exltx;
	}

	public void setTaxInclude16(String taxInclude16) {
		this.taxInclude16 = taxInclude16;
	}

	@Column(name="tax_include16", nullable=true, length=100)
	public String getTaxInclude16() {
		return taxInclude16;
	}

	public void setTaxSum13(String taxSum13) {
		this.taxSum13 = taxSum13;
	}

	@Column(name="tax_sum13", nullable=true, length=100)
	public String getTaxSum13() {
		return taxSum13;
	}

	public void setTaxSum13Exltx(String taxSum13Exltx) {
		this.taxSum13Exltx = taxSum13Exltx;
	}

	@Column(name="tax_sum13_exltx", nullable=true, length=100)
	public String getTaxSum13Exltx() {
		return taxSum13Exltx;
	}

	public void setTaxInclude13(String taxInclude13) {
		this.taxInclude13 = taxInclude13;
	}

	@Column(name="tax_include13", nullable=true, length=100)
	public String getTaxInclude13() {
		return taxInclude13;
	}

	public void setTaxSum10(String taxSum10) {
		this.taxSum10 = taxSum10;
	}

	@Column(name="tax_sum10", nullable=true, length=100)
	public String getTaxSum10() {
		return taxSum10;
	}

	public void setTaxSum10Exltx(String taxSum10Exltx) {
		this.taxSum10Exltx = taxSum10Exltx;
	}

	@Column(name="tax_sum10_exltx", nullable=true, length=100)
	public String getTaxSum10Exltx() {
		return taxSum10Exltx;
	}

	public void setTaxInclude10(String taxInclude10) {
		this.taxInclude10 = taxInclude10;
	}

	@Column(name="tax_include10", nullable=true, length=100)
	public String getTaxInclude10() {
		return taxInclude10;
	}

	public void setTax1Sum6(String tax1Sum6) {
		this.tax1Sum6 = tax1Sum6;
	}

	@Column(name="tax1_sum6", nullable=true, length=100)
	public String getTax1Sum6() {
		return tax1Sum6;
	}

	public void setTax1Sum6Exltx(String tax1Sum6Exltx) {
		this.tax1Sum6Exltx = tax1Sum6Exltx;
	}

	@Column(name="tax1_sum6_exltx", nullable=true, length=100)
	public String getTax1Sum6Exltx() {
		return tax1Sum6Exltx;
	}

	public void setTax1Include6(String tax1Include6) {
		this.tax1Include6 = tax1Include6;
	}

	@Column(name="tax1_include6", nullable=true, length=100)
	public String getTax1Include6() {
		return tax1Include6;
	}

	public void setNetOiExltx(String netOiExltx) {
		this.netOiExltx = netOiExltx;
	}

	@Column(name="net_oi_exltx", nullable=true, length=100)
	public String getNetOiExltx() {
		return netOiExltx;
	}

	public void setCompensationIncome(String compensationIncome) {
		this.compensationIncome = compensationIncome;
	}

	@Column(name="compensation_income", nullable=true, length=500)
	public String getCompensationIncome() {
		return compensationIncome;
	}

	public void setVip001SumExltx(String vip001SumExltx) {
		this.vip001SumExltx = vip001SumExltx;
	}

	@Column(name="vip001_sum_exltx", nullable=true, length=100)
	public String getVip001SumExltx() {
		return vip001SumExltx;
	}

	public void setCou001SumExltx(String cou001SumExltx) {
		this.cou001SumExltx = cou001SumExltx;
	}

	@Column(name="cou001_sum_exltx", nullable=true, length=100)
	public String getCou001SumExltx() {
		return cou001SumExltx;
	}

	public void setNpd001SumExltx(String npd001SumExltx) {
		this.npd001SumExltx = npd001SumExltx;
	}

	@Column(name="npd001_sum_exltx", nullable=true, length=100)
	public String getNpd001SumExltx() {
		return npd001SumExltx;
	}

	public void setCri001SumExltx(String cri001SumExltx) {
		this.cri001SumExltx = cri001SumExltx;
	}

	@Column(name="cri001_sum_exltx", nullable=true, length=100)
	public String getCri001SumExltx() {
		return cri001SumExltx;
	}

	public void setBusinessDevSupportExcltx(String businessDevSupportExcltx) {
		this.businessDevSupportExcltx = businessDevSupportExcltx;
	}

	@Column(name="business_dev_support_excltx", nullable=true, length=100)
	public String getBusinessDevSupportExcltx() {
		return businessDevSupportExcltx;
	}

	public void setBusinessDevSupport(String businessDevSupport) {
		this.businessDevSupport = businessDevSupport;
	}

	@Column(name="business_dev_support", nullable=true, length=500)
	public String getBusinessDevSupport() {
		return businessDevSupport;
	}

	public void setBusinessDevSupportTotal(String businessDevSupportTotal) {
		this.businessDevSupportTotal = businessDevSupportTotal;
	}

	@Column(name="business_dev_support_total", nullable=true, length=100)
	public String getBusinessDevSupportTotal() {
		return businessDevSupportTotal;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
