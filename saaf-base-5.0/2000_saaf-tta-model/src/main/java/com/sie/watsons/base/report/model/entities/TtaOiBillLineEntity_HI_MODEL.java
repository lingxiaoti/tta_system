package com.sie.watsons.base.report.model.entities;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.report.utils.CustomNumberStringConverter;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiBillLineEntity_HI_RO Entity Object
 * Sun Sep 01 19:33:36 CST 2019  Auto Generate
 */

public class TtaOiBillLineEntity_HI_MODEL  {
	@ExcelProperty(value = "ACCOUNT_MONTH")
	private Date accountMonth;
	@ExcelProperty(value = "TTA_OI_BILL_IMPORT_ID")
	private Integer ttaOiBillImportId ;
	@ExcelProperty(value = "RMS_CODE")
	private String rmsCode;
	@ExcelProperty(value = "VENDER_NAME")
	private String venderName;
	@ExcelProperty(value = "IMPORTJV")
	private String importjv;
	@ExcelProperty(value = "INVOICENAME")
	private String invoicename;
	@ExcelProperty(value = "BUYER")
	private String buyer;
	@ExcelProperty(value = "TC")
	private String tc;
	@ExcelProperty(value = "DEPARTMENT")
	private String department;
	@ExcelProperty(value = "BRAND")
	private String brand;
	@ExcelProperty(value = "VENDERAB")
	private String venderab;
	@ExcelProperty(value = "USER_CONTRACT_ID")
	private String userContractId;
	@ExcelProperty(value = "COOPERATE_STATUS")
	private String cooperateStatus;
	@ExcelProperty(value = "VENDER_TYPE")
	private String venderType;
	@ExcelProperty(value = "FAMILY_PLANING_FLAG")
	private String familyPlaningFlag;
	@ExcelProperty(value = "VALID_BEGIN_DATE")
	private Date validBeginDate;
	@ExcelProperty(value = "PURCHASE")
	private String purchase;
	@ExcelProperty(value = "GOODS_RETURN")
	private String goodsReturn;
	@ExcelProperty(value = "NET_PURCHASE")
	private String netPurchase;
	@ExcelProperty(value = "DSD")
	private String dsd;
	@ExcelProperty(value = "PURCHASE_ORIGIN")
	private String purchaseOrigin;
	@ExcelProperty(value = "GOODS_RETURN_ORIGIN")
	private String goodsReturnOrigin;
	@ExcelProperty(value = "NET_PURCHASE_ORIGIN")
	private String netPurchaseOrigin;
	@ExcelProperty(value = "ADRB_CONTRACT_TEXT")
	private String adrbContractText;
	@ExcelProperty(value = "ADRB_SUM_MONEY")
	private String adrbSumMoney;
	@ExcelProperty(value = "EP_CONTRACT_TEXT")
	private String epContractText;
	@ExcelProperty(value = "EP_SUM_MONEY")
	private String epSumMoney;
	@ExcelProperty(value = "ADPF_CONTRACT_TEXT")
	private String adpfContractText;
	@ExcelProperty(value = "ADPF_SUM_MONEY")
	private String adpfSumMoney;
	@ExcelProperty(value = "ADDA_CONTRACT_TEXT")
	private String addaContractText;
	@ExcelProperty(value = "ADDA_SUM_MONEY")
	private String addaSumMoney;
	@ExcelProperty(value = "VIR002_CONTRACT_TEXT")
	private String vir002ContractText;
	@ExcelProperty(value = "VIR002_SUM_MONEY")
	private String vir002SumMoney;
	@ExcelProperty(value = "VIR001_CONTRACT_TEXT")
	private String vir001ContractText;
	@ExcelProperty(value = "VIR001_SUM_MONEY")
	private String vir001SumMoney;
	@ExcelProperty(value = "ADDG_CONTRACT_TEXT")
	private String addgContractText;
	@ExcelProperty(value = "ADDG_SUM_MONEY")
	private String addgSumMoney;
	@ExcelProperty(value = "ADTR_CONTRACT_TEXT")
	private String adtrContractText;
	@ExcelProperty(value = "ADTR_SUM_MONEY")
	private String adtrSumMoney;
	@ExcelProperty(value = "BDF_CONTRACT_TEXT")
	private String bdfContractText;
	@ExcelProperty(value = "BDF_SUM_MONEY")
	private String bdfSumMoney;
	@ExcelProperty(value = "NSS001_CONTRACT_TEXT")
	private String nss001ContractText;
	@ExcelProperty(value = "NSS001_CONTRACT_NUM")
	private String nss001ContractNum;
	@ExcelProperty(value = "NSS001_CONTRACT_DATA")
	private String nss001ContractData;
	@ExcelProperty(value = "NSS001_SUM_MONEY")
	private String nss001SumMoney;
	@ExcelProperty(value = "RSS001_CONTRACT_TEXT")
	private String rss001ContractText;
	@ExcelProperty(value = "RSS001_CONTRACT_NUM")
	private String rss001ContractNum;
	@ExcelProperty(value = "RSS001_CONTRACT_DATA")
	private String rss001ContractData;
	@ExcelProperty(value = "RSS001_SUM_MONEY")
	private String rss001SumMoney;
	@ExcelProperty(value = "ASS001_CONTRACT_TEXT")
	private String ass001ContractText;
	@ExcelProperty(value = "ASS001_CONTRACT_NUM")
	private String ass001ContractNum;
	@ExcelProperty(value = "ASS001_SUM_MONEY")
	private String ass001SumMoney;
	@ExcelProperty(value = "WDP001_CONTRACT_TEXT")
	private String wdp001ContractText;
	@ExcelProperty(value = "WDP001_SUM_MONEY")
	private String wdp001SumMoney;
	@ExcelProperty(value = "NPM001_CONTRACT_TEXT")
	private String npm001ContractText;
	@ExcelProperty(value = "NPM001_CONTRACT_NUM")
	private String npm001ContractNum;
	@ExcelProperty(value = "NPM001_CONTRACT_DATA")
	private String npm001ContractData;
	@ExcelProperty(value = "NPM001_SUM_MONEY")
	private String npm001SumMoney;
	@ExcelProperty(value = "DRG001_CONTRACT_TEXT")
	private String drg001ContractText;
	@ExcelProperty(value = "DRG001_SUM_MONEY")
	private String drg001SumMoney;
	@ExcelProperty(value = "DRH001_CONTRACT_TEXT")
	private String drh001ContractText;
	@ExcelProperty(value = "DRH001SUMMONEY")
	private String drh001summoney;
	@ExcelProperty(value = "DRB001_CONTRACT_TEXT")
	private String drb001ContractText;
	@ExcelProperty(value = "DRB001_SUM_MONEY")
	private String drb001SumMoney;
	@ExcelProperty(value = "DRO001_CONTRACT_TEXT")
	private String dro001ContractText;
	@ExcelProperty(value = "DRO001_SUM_MONEY")
	private String dro001SumMoney;
	@ExcelProperty(value = "CRI001_SUM_MONEY")
	private String cri001ContractText;
	@ExcelProperty(value = "CRI001_SUM_MONEY")
	private String cri001SumMoney;
	@ExcelProperty(value = "DMI001_CONTRACT_TEXT")
	private String dmi001ContractText;
	@ExcelProperty(value = "DMI001_SUM_MONEY")
	private String dmi001SumMoney;
	@ExcelProperty(value = "HBI001_CONTRACT_TEXT")
	private String hbi001ContractText;
	@ExcelProperty(value = "HBI001_SUM_MONEY")
	private String hbi001SumMoney;
	@ExcelProperty(value = "NPD001_CONTRACT_TEXT")
	private String npd001ContractText;
	@ExcelProperty(value = "NPD001_SUM_MONEY")
	private String npd001SumMoney;
	@ExcelProperty(value = "BDF001_CONTRACT_TEXT")
	private String bdf001ContractText;
	@ExcelProperty(value = "BDF001_SUM_MONEY")
	private String bdf001SumMoney;
	@ExcelProperty(value = "COS001_CONTRACT_TEXT")
	private String cos001ContractText;
	@ExcelProperty(value = "COS001_SUM_MONEY")
	private String cos001SumMoney;
	@ExcelProperty(value = "OTH001_CONTRACT_TEXT")
	private String oth001ContractText;
	@ExcelProperty(value = "OTH001_SUM_MONEY")
	private String oth001SumMoney;
	@ExcelProperty(value = "NTI001_CONTRACT_TEXT")
	private String nti001ContractText;
	@ExcelProperty(value = "NTI001_SUM_MONEY")
	private String nti001SumMoney;
	@ExcelProperty(value = "LDP001_SUM_MONEY")
	private String ldp001SumMoney;
	@ExcelProperty(value = "LDP001_CONTRACT_TEXT")
	private String ldp001ContractText;
	@ExcelProperty(value = "NFP001_CONTRACT_TEXT")
	private String nfp001ContractText;
	@ExcelProperty(value = "NFP001_SUM_MONEY")
	private String nfp001SumMoney;
	@ExcelProperty(value = "COU001_CONTRACT_TEXT")
	private String cou001ContractText;
	@ExcelProperty(value = "COU001_SUM_MONEY")
	private String cou001SumMoney;
	@ExcelProperty(value = "VIP001_CONTRACT_TEXT")
	private String vip001ContractText;
	@ExcelProperty(value = "VIP001_SUM_MONEY")
	private String vip001SumMoney;
	@ExcelProperty(value = "LPU001_CONTRACT_TEXT")
	private String lpu001ContractText;
	@ExcelProperty(value = "LPU001_SUM_MONEY")
	private String lpu001SumMoney;
	@ExcelProperty(value = "BAC001_CONTRACT_TEXT")
	private String bac001ContractText;
	@ExcelProperty(value = "BAC001_SUM_MONEY")
	private String bac001SumMoney;
	@ExcelProperty(value = "UEP001_CONTRACT_TEXT")
	private String uep001ContractText;
	@ExcelProperty(value = "UEP001_SUM_MONEY")
	private String uep001SumMoney;
	@ExcelProperty(value = "OTF001_CONTRACT_TEXT")
	private String otf001ContractText;
	@ExcelProperty(value = "OTF001_SUM_MONEY")
	private String otf001SumMoney;
	@ExcelProperty(value = "PSF001_CONTRACT_TEXT")
	private String psf001ContractText;
	@ExcelProperty(value = "PSF001_SUM_MONEY")
	private String psf001SumMoney;
	@ExcelProperty(value = "CPS001_CONTRACT_TEXT")
	private String cps001ContractText;
	@ExcelProperty(value = "CPS001_SUM_MONEY")
	private String cps001SumMoney;
	@ExcelProperty(value = "AP_SUM_MONEY")
	private String apSumMoney;
	@ExcelProperty(value = "REMARK")
	private String remark;
	@ExcelProperty(value = "RMS_CODE_COUNT")
	private String rmsCodeCount;
	@ExcelProperty(value = "SUM_IF")
	private String sumIf;
	@ExcelProperty(value = "VALID_END_DATE")
	private Date validEndDate;
	@ExcelProperty(value = "INVOICE_DIS_RATE")
	private String invoiceDisRate;
	@ExcelProperty(value = "INVOICE_DIS_IFVAT")
	private String invoiceDisIfvat;
	@ExcelProperty(value = "ADRB_SUM_EXCLTAX")
	private String adrbSumExcltax;
	@ExcelProperty(value = "EP_SUM_EXCLTAX")
	private String epSumExcltax;
	@ExcelProperty(value = "ADPF_SUM_EXCLTAX")
	private String adpfSumExcltax;
	@ExcelProperty(value = "PROMOTION_DISCOUNT")
	private String promotionDiscount;
	@ExcelProperty(value = "PROMOTION_DISCOUNT_TOTAL")
	private String promotionDiscountTotal;
	@ExcelProperty(value = "PROMOTION_DISCOUNT_EXCLTAX")
	private String promotionDiscountExcltax;
	@ExcelProperty(value = "DISTRIBUTION_DISCOUNT")
	private String distributionDiscount;
	@ExcelProperty(value = "DISTRIBUTION_DISCOUNT_TOTAL")
	private String distributionDiscountTotal;
	@ExcelProperty(value = "DISTRIBUTION_DISCOUNT_EXCLTAX")
	private String distributionDiscountExcltax;
	@ExcelProperty(value = "ADDA_SUM_EXCLTX")
	private String addaSumExcltx;
	@ExcelProperty(value = "VIR002_SUM_MONEY_EXCLTX")
	private String vir002SumMoneyExcltx;
	@ExcelProperty(value = "VIR001_SUM_MONEY_EXCLTX")
	private String vir001SumMoneyExcltx;
	@ExcelProperty(value = "RETURNED_GOODS_FEE")
	private String returnedGoodsFee;
	@ExcelProperty(value = "RETURNED_GOODS_FEE_TOTAL")
	private String returnedGoodsFeeTotal;
	@ExcelProperty(value = "RETURNED_GOODS_FEE_EXCLTX")
	private String returnedGoodsFeeExcltx;
	@ExcelProperty(value = "ADDG_SUM_EXCLTX")
	private String addgSumExcltx;
	@ExcelProperty(value = "ADTR_SUM_EXCLTX")
	private String adtrSumExcltx;
	@ExcelProperty(value = "BDF_SUM_EXCLTX")
	private String bdfSumExcltx;
	@ExcelProperty(value = "NSS001_SUM_EXCLTX")
	private String nss001SumExcltx;
	@ExcelProperty(value = "RSS001_SUM_EXLTX")
	private String rss001SumExltx;
	@ExcelProperty(value = "REFIT_STORE_SUPPORT")
	private String refitStoreSupport;
	@ExcelProperty(value = "REFIT_STORE_NUM_SUPPORT")
	private String refitStoreNumSupport;
	@ExcelProperty(value = "REFIT_STORE_DATA_SUPPORT")
	private String refitStoreDataSupport;
	@ExcelProperty(value = "REFIT_STORE_SUPPORT_TOTAL")
	private String refitStoreSupportTotal;
	@ExcelProperty(value = "REFIT_STORE_SUPPORT_TEXLTX")
	private String refitStoreSupportTexltx;
	@ExcelProperty(value = "ASS001_SUM_EXLTX")
	private String ass001SumExltx;
	@ExcelProperty(value = "WDP001_SUM_EXLTX")
	private String wdp001SumExltx;
	@ExcelProperty(value = "NPM001_SUM_EXLTX")
	private String npm001SumExltx;
	@ExcelProperty(value = "DATA_SHARING_FEE")
	private String dataSharingFee;
	@ExcelProperty(value = "DATA_SHARING_FEE_TOTAL")
	private String dataSharingFeeTotal;
	@ExcelProperty(value = "DATA_SHARING_FEE_EXLTX")
	private String dataSharingFeeExltx;
	@ExcelProperty(value = "DRB001_SUM_EXLTX")
	private String drb001SumExltx;
	@ExcelProperty(value = "DRO001_SUM_EXLTX")
	private String dro001SumExltx;
	@ExcelProperty(value = "DISPLAY_RENTAL")
	private String displayRental;
	@ExcelProperty(value = "DISPLAY_RENTAL_TOTAL")
	private String displayRentalTotal;
	@ExcelProperty(value = "DISPLAY_RENTAL_TOTAL_EXLTX")
	private String displayRentalTotalExltx;
	@ExcelProperty(value = "DISPLAY_NEWTP")
	private String displayNewtp;
	@ExcelProperty(value = "DISPLAY_NEWTP_TOTAL")
	private String displayNewtpTotal;
	@ExcelProperty(value = "DISPLAY_NEWTP_EXLTX")
	private String displayNewtpExltx;
	@ExcelProperty(value = "DMI001_SUM_EXLTX")
	private String dmi001SumExltx;
	@ExcelProperty(value = "PROMOTION_FUND")
	private String promotionFund;
	@ExcelProperty(value = "PROMOTION_FUND_TOTAL")
	private String promotionFundTotal;
	@ExcelProperty(value = "PROMOTION_FUND_EXLTX")
	private String promotionFundExltx;
	@ExcelProperty(value = "HBI001_SUM_EXLTX")
	private String hbi001SumExltx;
	@ExcelProperty(value = "BDF001_SUM_EXLTX")
	private String bdf001SumExltx;
	@ExcelProperty(value = "BUSINESS_DEV_FUND")
	private String businessDevFund;
	@ExcelProperty(value = "BUSINESS_DEV_FUND_TOTAL")
	private String businessDevFundTotal;
	@ExcelProperty(value = "BUSINESS_DEV_FUND_EXLTX")
	private String businessDevFundExltx;
	@ExcelProperty(value = "COS001_SUM_EXLTX")
	private String cos001SumExltx;
	@ExcelProperty(value = "OTH001_SUM_EXLTX")
	private String oth001SumExltx;
	@ExcelProperty(value = "NTI001_SUM_EXLTX")
	private String nti001SumExltx;
	@ExcelProperty(value = "LDP001_SUM_EXLTX")
	private String ldp001SumExltx;
	@ExcelProperty(value = "NFP001_SUM_EXLTX")
	private String nfp001SumExltx;
	@ExcelProperty(value = "LPU001_SUM_EXLTX")
	private String lpu001SumExltx;
	@ExcelProperty(value = "BAC001_SUM_EXLTX")
	private String bac001SumExltx;
	@ExcelProperty(value = "UEP001_SUM_EXLTX")
	private String uep001SumExltx;
	@ExcelProperty(value = "OTF001_SUM_EXLTX")
	private String otf001SumExltx;
	@ExcelProperty(value = "QA_SER_FEE")
	private String qaSerFee;
	@ExcelProperty(value = "QA_SER_FEE_TOTAL")
	private String qaSerFeeTotal;
	@ExcelProperty(value = "QA_SER_FEE_EXLTX")
	private String qaSerFeeExltx;
	@ExcelProperty(value = "PSF001_SUM_EXLTX")
	private String psf001SumExltx;
	@ExcelProperty(value = "PROMOTION_SER_FEE")
	private String promotionSerFee;
	@ExcelProperty(value = "PROMOTION_SER_FEE_TOTAL")
	private String promotionSerFeeTotal;
	@ExcelProperty(value = "PROMOTION_SER_FEE_EXLTX")
	private String promotionSerFeeExltx;
	@ExcelProperty(value = "CPS001_SUM_EXLTX")
	private String cps001SumExltx;
	@ExcelProperty(value = "COMPENSATION_INCOME_TOTAL")
	private String compensationIncomeTotal;
	@ExcelProperty(value = "COMPENSATION_INCOME_EXLTX")
	private String compensationIncomeExltx;
	@ExcelProperty(value = "OTHER_PRO_FEE")
	private String otherProFee;
	@ExcelProperty(value = "OTHER_PRO_FEE_TOTAL")
	private String otherProFeeTotal;
	@ExcelProperty(value = "OTHER_PRO_FEE_EXLTX")
	private String otherProFeeExltx;
	@ExcelProperty(value = "OB_SERVICE")
	private String obService;
	@ExcelProperty(value = "OB_SERVICE_TOTAL")
	private String obServiceTotal;
	@ExcelProperty(value = "OB_SERVICE_EXLTX")
	private String obServiceExltx;
	@ExcelProperty(value = "OEM_TESTER")
	private String oemTester;
	@ExcelProperty(value = "OEM_TESTER_TOTAL")
	private String oemTesterTotal;
	@ExcelProperty(value = "OEM_TESTER_EXLTX")
	private String oemTesterExltx;
	@ExcelProperty(value = "ROMOTION_MKTG")
	private String romotionMktg;
	@ExcelProperty(value = "ROMOTION_MKTG_TOTAL")
	private String romotionMktgTotal;
	@ExcelProperty(value = "ROMOTION_MKTG_EXLTX")
	private String romotionMktgExltx;
	@ExcelProperty(value = "TERMS_SALES")
	private String termsSales;
	@ExcelProperty(value = "TERMS_SALES_TOTAL")
	private String termsSalesTotal;
	@ExcelProperty(value = "TERMS_SALES_EXLTX")
	private String termsSalesExltx;
	@ExcelProperty(value = "TAX_SUM")
	private String taxSum;
	@ExcelProperty(value = "TAX_SUM_EXLTX")
	private String taxSumExltx;
	@ExcelProperty(value = "TAX_INCLUDE")
	private String taxInclude;
	@ExcelProperty(value = "TAX1_SUM")
	private String tax1Sum;
	@ExcelProperty(value = "TAX1_SUM_EXLTX")
	private String tax1SumExltx;
	@ExcelProperty(value = "TAX1_INCLUDE")
	private String tax1Include;
	@ExcelProperty(value = "TAX_SUM16")
	private String taxSum16;
	@ExcelProperty(value = "TAX_SUM16_EXLTX")
	private String taxSum16Exltx;
	@ExcelProperty(value = "TAX_INCLUDE16")
	private String taxInclude16;
	@ExcelProperty(value = "TAX_SUM13")
	private String taxSum13;
	@ExcelProperty(value = "TAX_SUM13_EXLTX")
	private String taxSum13Exltx;
	@ExcelProperty(value = "TAX_INCLUDE13")
	private String taxInclude13;
	@ExcelProperty(value = "TAX_SUM10")
	private String taxSum10;
	@ExcelProperty(value = "TAX_SUM10_EXLTX")
	private String taxSum10Exltx;
	@ExcelProperty(value = "TAX_INCLUDE10")
	private String taxInclude10;
	@ExcelProperty(value = "TAX1_SUM6")
	private String tax1Sum6;
	@ExcelProperty(value = "TAX1_SUM6_EXLTX",converter = CustomNumberStringConverter.class)
	private String tax1Sum6Exltx;
	@ExcelProperty(value = "TAX1_INCLUDE6")
	private String tax1Include6;
	@ExcelProperty(value = "NET_OI_EXLTX")
	private String netOiExltx;
	@ExcelProperty(value = "COMPENSATION_INCOME")
	private String compensationIncome;
	@ExcelProperty(value = "VIP001_SUM_EXLTX")
	private String vip001SumExltx;
	@ExcelProperty(value = "COU001_SUM_EXLTX")
	private String cou001SumExltx;
	@ExcelProperty(value = "NPD001_SUM_EXLTX")
	private String npd001SumExltx;
	@ExcelProperty(value = "CRI001_SUM_EXLTX")
	private String cri001SumExltx;
	@ExcelProperty(value = "BUSINESS_DEV_SUPPORT_EXCLTX")
	private String businessDevSupportExcltx;
	@ExcelProperty(value = "BUSINESS_DEV_SUPPORT")
	private String businessDevSupport;
	@ExcelProperty(value = "BUSINESS_DEV_SUPPORT_TOTAL")
	private String businessDevSupportTotal;

	public void setAccountMonth(Date accountMonth) {
		this.accountMonth = accountMonth;
	}


	public Date getAccountMonth() {
		return accountMonth;
	}


	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}


	public String getRmsCode() {
		return rmsCode;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}


	public String getVenderName() {
		return venderName;
	}

	public void setImportjv(String importjv) {
		this.importjv = importjv;
	}


	public String getImportjv() {
		return importjv;
	}

	public void setInvoicename(String invoicename) {
		this.invoicename = invoicename;
	}


	public String getInvoicename() {
		return invoicename;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}


	public String getBuyer() {
		return buyer;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}


	public String getTc() {
		return tc;
	}

	public void setDepartment(String department) {
		this.department = department;
	}


	public String getDepartment() {
		return department;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getBrand() {
		return brand;
	}

	public void setVenderab(String venderab) {
		this.venderab = venderab;
	}


	public String getVenderab() {
		return venderab;
	}

	public void setUserContractId(String userContractId) {
		this.userContractId = userContractId;
	}


	public String getUserContractId() {
		return userContractId;
	}

	public void setCooperateStatus(String cooperateStatus) {
		this.cooperateStatus = cooperateStatus;
	}


	public String getCooperateStatus() {
		return cooperateStatus;
	}

	public void setVenderType(String venderType) {
		this.venderType = venderType;
	}


	public String getVenderType() {
		return venderType;
	}

	public void setFamilyPlaningFlag(String familyPlaningFlag) {
		this.familyPlaningFlag = familyPlaningFlag;
	}


	public String getFamilyPlaningFlag() {
		return familyPlaningFlag;
	}

	public void setValidBeginDate(Date validBeginDate) {
		this.validBeginDate = validBeginDate;
	}


	public Date getValidBeginDate() {
		return validBeginDate;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}


	public String getPurchase() {
		return purchase;
	}

	public void setGoodsReturn(String goodsReturn) {
		this.goodsReturn = goodsReturn;
	}


	public String getGoodsReturn() {
		return goodsReturn;
	}

	public void setNetPurchase(String netPurchase) {
		this.netPurchase = netPurchase;
	}


	public String getNetPurchase() {
		return netPurchase;
	}

	public void setDsd(String dsd) {
		this.dsd = dsd;
	}


	public String getDsd() {
		return dsd;
	}

	public void setPurchaseOrigin(String purchaseOrigin) {
		this.purchaseOrigin = purchaseOrigin;
	}


	public String getPurchaseOrigin() {
		return purchaseOrigin;
	}

	public void setGoodsReturnOrigin(String goodsReturnOrigin) {
		this.goodsReturnOrigin = goodsReturnOrigin;
	}


	public String getGoodsReturnOrigin() {
		return goodsReturnOrigin;
	}


	public void setNetPurchaseOrigin(String netPurchaseOrigin) {
		this.netPurchaseOrigin = netPurchaseOrigin;
	}


	public String getNetPurchaseOrigin() {
		return netPurchaseOrigin;
	}

	public void setAdrbContractText(String adrbContractText) {
		this.adrbContractText = adrbContractText;
	}


	public String getAdrbContractText() {
		return adrbContractText;
	}

	public void setAdrbSumMoney(String adrbSumMoney) {
		this.adrbSumMoney = adrbSumMoney;
	}


	public String getAdrbSumMoney() {
		return adrbSumMoney;
	}

	public void setEpContractText(String epContractText) {
		this.epContractText = epContractText;
	}


	public String getEpContractText() {
		return epContractText;
	}

	public void setEpSumMoney(String epSumMoney) {
		this.epSumMoney = epSumMoney;
	}


	public String getEpSumMoney() {
		return epSumMoney;
	}

	public void setAdpfContractText(String adpfContractText) {
		this.adpfContractText = adpfContractText;
	}


	public String getAdpfContractText() {
		return adpfContractText;
	}

	public void setAdpfSumMoney(String adpfSumMoney) {
		this.adpfSumMoney = adpfSumMoney;
	}


	public String getAdpfSumMoney() {
		return adpfSumMoney;
	}

	public void setAddaContractText(String addaContractText) {
		this.addaContractText = addaContractText;
	}


	public String getAddaContractText() {
		return addaContractText;
	}

	public void setAddaSumMoney(String addaSumMoney) {
		this.addaSumMoney = addaSumMoney;
	}


	public String getAddaSumMoney() {
		return addaSumMoney;
	}

	public void setVir002ContractText(String vir002ContractText) {
		this.vir002ContractText = vir002ContractText;
	}


	public String getVir002ContractText() {
		return vir002ContractText;
	}

	public void setVir002SumMoney(String vir002SumMoney) {
		this.vir002SumMoney = vir002SumMoney;
	}


	public String getVir002SumMoney() {
		return vir002SumMoney;
	}

	public void setVir001ContractText(String vir001ContractText) {
		this.vir001ContractText = vir001ContractText;
	}


	public String getVir001ContractText() {
		return vir001ContractText;
	}

	public void setVir001SumMoney(String vir001SumMoney) {
		this.vir001SumMoney = vir001SumMoney;
	}


	public String getVir001SumMoney() {
		return vir001SumMoney;
	}

	public void setAddgContractText(String addgContractText) {
		this.addgContractText = addgContractText;
	}


	public String getAddgContractText() {
		return addgContractText;
	}

	public void setAddgSumMoney(String addgSumMoney) {
		this.addgSumMoney = addgSumMoney;
	}


	public String getAddgSumMoney() {
		return addgSumMoney;
	}

	public void setAdtrContractText(String adtrContractText) {
		this.adtrContractText = adtrContractText;
	}


	public String getAdtrContractText() {
		return adtrContractText;
	}

	public void setAdtrSumMoney(String adtrSumMoney) {
		this.adtrSumMoney = adtrSumMoney;
	}


	public String getAdtrSumMoney() {
		return adtrSumMoney;
	}

	public void setBdfContractText(String bdfContractText) {
		this.bdfContractText = bdfContractText;
	}


	public String getBdfContractText() {
		return bdfContractText;
	}

	public void setBdfSumMoney(String bdfSumMoney) {
		this.bdfSumMoney = bdfSumMoney;
	}


	public String getBdfSumMoney() {
		return bdfSumMoney;
	}

	public void setNss001ContractText(String nss001ContractText) {
		this.nss001ContractText = nss001ContractText;
	}


	public String getNss001ContractText() {
		return nss001ContractText;
	}

	public void setNss001ContractNum(String nss001ContractNum) {
		this.nss001ContractNum = nss001ContractNum;
	}


	public String getNss001ContractNum() {
		return nss001ContractNum;
	}

	public void setNss001ContractData(String nss001ContractData) {
		this.nss001ContractData = nss001ContractData;
	}


	public String getNss001ContractData() {
		return nss001ContractData;
	}

	public void setNss001SumMoney(String nss001SumMoney) {
		this.nss001SumMoney = nss001SumMoney;
	}


	public String getNss001SumMoney() {
		return nss001SumMoney;
	}

	public void setRss001ContractText(String rss001ContractText) {
		this.rss001ContractText = rss001ContractText;
	}


	public String getRss001ContractText() {
		return rss001ContractText;
	}

	public void setRss001ContractNum(String rss001ContractNum) {
		this.rss001ContractNum = rss001ContractNum;
	}


	public String getRss001ContractNum() {
		return rss001ContractNum;
	}

	public void setRss001ContractData(String rss001ContractData) {
		this.rss001ContractData = rss001ContractData;
	}


	public String getRss001ContractData() {
		return rss001ContractData;
	}

	public void setRss001SumMoney(String rss001SumMoney) {
		this.rss001SumMoney = rss001SumMoney;
	}


	public String getRss001SumMoney() {
		return rss001SumMoney;
	}

	public void setAss001ContractText(String ass001ContractText) {
		this.ass001ContractText = ass001ContractText;
	}


	public String getAss001ContractText() {
		return ass001ContractText;
	}

	public void setAss001ContractNum(String ass001ContractNum) {
		this.ass001ContractNum = ass001ContractNum;
	}


	public String getAss001ContractNum() {
		return ass001ContractNum;
	}

	public void setAss001SumMoney(String ass001SumMoney) {
		this.ass001SumMoney = ass001SumMoney;
	}


	public String getAss001SumMoney() {
		return ass001SumMoney;
	}

	public void setWdp001ContractText(String wdp001ContractText) {
		this.wdp001ContractText = wdp001ContractText;
	}


	public String getWdp001ContractText() {
		return wdp001ContractText;
	}

	public void setWdp001SumMoney(String wdp001SumMoney) {
		this.wdp001SumMoney = wdp001SumMoney;
	}


	public String getWdp001SumMoney() {
		return wdp001SumMoney;
	}

	public void setNpm001ContractText(String npm001ContractText) {
		this.npm001ContractText = npm001ContractText;
	}


	public String getNpm001ContractText() {
		return npm001ContractText;
	}

	public void setNpm001ContractNum(String npm001ContractNum) {
		this.npm001ContractNum = npm001ContractNum;
	}


	public String getNpm001ContractNum() {
		return npm001ContractNum;
	}

	public void setNpm001ContractData(String npm001ContractData) {
		this.npm001ContractData = npm001ContractData;
	}


	public String getNpm001ContractData() {
		return npm001ContractData;
	}

	public void setNpm001SumMoney(String npm001SumMoney) {
		this.npm001SumMoney = npm001SumMoney;
	}


	public String getNpm001SumMoney() {
		return npm001SumMoney;
	}

	public void setDrg001ContractText(String drg001ContractText) {
		this.drg001ContractText = drg001ContractText;
	}


	public String getDrg001ContractText() {
		return drg001ContractText;
	}

	public void setDrg001SumMoney(String drg001SumMoney) {
		this.drg001SumMoney = drg001SumMoney;
	}


	public String getDrg001SumMoney() {
		return drg001SumMoney;
	}

	public void setDrh001ContractText(String drh001ContractText) {
		this.drh001ContractText = drh001ContractText;
	}


	public String getDrh001ContractText() {
		return drh001ContractText;
	}

	public void setDrh001summoney(String drh001summoney) {
		this.drh001summoney = drh001summoney;
	}


	public String getDrh001summoney() {
		return drh001summoney;
	}

	public void setDrb001ContractText(String drb001ContractText) {
		this.drb001ContractText = drb001ContractText;
	}


	public String getDrb001ContractText() {
		return drb001ContractText;
	}

	public void setDrb001SumMoney(String drb001SumMoney) {
		this.drb001SumMoney = drb001SumMoney;
	}


	public String getDrb001SumMoney() {
		return drb001SumMoney;
	}

	public void setDro001ContractText(String dro001ContractText) {
		this.dro001ContractText = dro001ContractText;
	}


	public String getDro001ContractText() {
		return dro001ContractText;
	}

	public void setDro001SumMoney(String dro001SumMoney) {
		this.dro001SumMoney = dro001SumMoney;
	}


	public String getDro001SumMoney() {
		return dro001SumMoney;
	}

	public void setCri001ContractText(String cri001ContractText) {
		this.cri001ContractText = cri001ContractText;
	}


	public String getCri001ContractText() {
		return cri001ContractText;
	}

	public void setCri001SumMoney(String cri001SumMoney) {
		this.cri001SumMoney = cri001SumMoney;
	}


	public String getCri001SumMoney() {
		return cri001SumMoney;
	}

	public void setDmi001ContractText(String dmi001ContractText) {
		this.dmi001ContractText = dmi001ContractText;
	}


	public String getDmi001ContractText() {
		return dmi001ContractText;
	}

	public void setDmi001SumMoney(String dmi001SumMoney) {
		this.dmi001SumMoney = dmi001SumMoney;
	}


	public String getDmi001SumMoney() {
		return dmi001SumMoney;
	}

	public void setHbi001ContractText(String hbi001ContractText) {
		this.hbi001ContractText = hbi001ContractText;
	}


	public String getHbi001ContractText() {
		return hbi001ContractText;
	}

	public void setHbi001SumMoney(String hbi001SumMoney) {
		this.hbi001SumMoney = hbi001SumMoney;
	}


	public String getHbi001SumMoney() {
		return hbi001SumMoney;
	}

	public void setNpd001ContractText(String npd001ContractText) {
		this.npd001ContractText = npd001ContractText;
	}


	public String getNpd001ContractText() {
		return npd001ContractText;
	}

	public void setNpd001SumMoney(String npd001SumMoney) {
		this.npd001SumMoney = npd001SumMoney;
	}


	public String getNpd001SumMoney() {
		return npd001SumMoney;
	}

	public void setBdf001ContractText(String bdf001ContractText) {
		this.bdf001ContractText = bdf001ContractText;
	}


	public String getBdf001ContractText() {
		return bdf001ContractText;
	}

	public void setBdf001SumMoney(String bdf001SumMoney) {
		this.bdf001SumMoney = bdf001SumMoney;
	}


	public String getBdf001SumMoney() {
		return bdf001SumMoney;
	}

	public void setCos001ContractText(String cos001ContractText) {
		this.cos001ContractText = cos001ContractText;
	}


	public String getCos001ContractText() {
		return cos001ContractText;
	}

	public void setCos001SumMoney(String cos001SumMoney) {
		this.cos001SumMoney = cos001SumMoney;
	}


	public String getCos001SumMoney() {
		return cos001SumMoney;
	}

	public void setOth001ContractText(String oth001ContractText) {
		this.oth001ContractText = oth001ContractText;
	}


	public String getOth001ContractText() {
		return oth001ContractText;
	}

	public void setOth001SumMoney(String oth001SumMoney) {
		this.oth001SumMoney = oth001SumMoney;
	}


	public String getOth001SumMoney() {
		return oth001SumMoney;
	}

	public void setNti001ContractText(String nti001ContractText) {
		this.nti001ContractText = nti001ContractText;
	}


	public String getNti001ContractText() {
		return nti001ContractText;
	}

	public void setNti001SumMoney(String nti001SumMoney) {
		this.nti001SumMoney = nti001SumMoney;
	}


	public String getNti001SumMoney() {
		return nti001SumMoney;
	}

	public void setLdp001SumMoney(String ldp001SumMoney) {
		this.ldp001SumMoney = ldp001SumMoney;
	}


	public String getLdp001SumMoney() {
		return ldp001SumMoney;
	}

	public void setLdp001ContractText(String ldp001ContractText) {
		this.ldp001ContractText = ldp001ContractText;
	}


	public String getLdp001ContractText() {
		return ldp001ContractText;
	}

	public void setNfp001ContractText(String nfp001ContractText) {
		this.nfp001ContractText = nfp001ContractText;
	}


	public String getNfp001ContractText() {
		return nfp001ContractText;
	}

	public void setNfp001SumMoney(String nfp001SumMoney) {
		this.nfp001SumMoney = nfp001SumMoney;
	}


	public String getNfp001SumMoney() {
		return nfp001SumMoney;
	}

	public void setCou001ContractText(String cou001ContractText) {
		this.cou001ContractText = cou001ContractText;
	}


	public String getCou001ContractText() {
		return cou001ContractText;
	}

	public void setCou001SumMoney(String cou001SumMoney) {
		this.cou001SumMoney = cou001SumMoney;
	}


	public String getCou001SumMoney() {
		return cou001SumMoney;
	}

	public void setVip001ContractText(String vip001ContractText) {
		this.vip001ContractText = vip001ContractText;
	}


	public String getVip001ContractText() {
		return vip001ContractText;
	}

	public void setVip001SumMoney(String vip001SumMoney) {
		this.vip001SumMoney = vip001SumMoney;
	}


	public String getVip001SumMoney() {
		return vip001SumMoney;
	}

	public void setLpu001ContractText(String lpu001ContractText) {
		this.lpu001ContractText = lpu001ContractText;
	}


	public String getLpu001ContractText() {
		return lpu001ContractText;
	}

	public void setLpu001SumMoney(String lpu001SumMoney) {
		this.lpu001SumMoney = lpu001SumMoney;
	}


	public String getLpu001SumMoney() {
		return lpu001SumMoney;
	}

	public void setBac001ContractText(String bac001ContractText) {
		this.bac001ContractText = bac001ContractText;
	}


	public String getBac001ContractText() {
		return bac001ContractText;
	}

	public void setBac001SumMoney(String bac001SumMoney) {
		this.bac001SumMoney = bac001SumMoney;
	}


	public String getBac001SumMoney() {
		return bac001SumMoney;
	}

	public void setUep001ContractText(String uep001ContractText) {
		this.uep001ContractText = uep001ContractText;
	}


	public String getUep001ContractText() {
		return uep001ContractText;
	}

	public void setUep001SumMoney(String uep001SumMoney) {
		this.uep001SumMoney = uep001SumMoney;
	}


	public String getUep001SumMoney() {
		return uep001SumMoney;
	}

	public void setOtf001ContractText(String otf001ContractText) {
		this.otf001ContractText = otf001ContractText;
	}


	public String getOtf001ContractText() {
		return otf001ContractText;
	}

	public void setOtf001SumMoney(String otf001SumMoney) {
		this.otf001SumMoney = otf001SumMoney;
	}


	public String getOtf001SumMoney() {
		return otf001SumMoney;
	}

	public void setPsf001ContractText(String psf001ContractText) {
		this.psf001ContractText = psf001ContractText;
	}


	public String getPsf001ContractText() {
		return psf001ContractText;
	}

	public void setPsf001SumMoney(String psf001SumMoney) {
		this.psf001SumMoney = psf001SumMoney;
	}


	public String getPsf001SumMoney() {
		return psf001SumMoney;
	}

	public void setCps001ContractText(String cps001ContractText) {
		this.cps001ContractText = cps001ContractText;
	}


	public String getCps001ContractText() {
		return cps001ContractText;
	}

	public void setCps001SumMoney(String cps001SumMoney) {
		this.cps001SumMoney = cps001SumMoney;
	}


	public String getCps001SumMoney() {
		return cps001SumMoney;
	}

	public void setApSumMoney(String apSumMoney) {
		this.apSumMoney = apSumMoney;
	}


	public String getApSumMoney() {
		return apSumMoney;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRemark() {
		return remark;
	}



	public void setRmsCodeCount(String rmsCodeCount) {
		this.rmsCodeCount = rmsCodeCount;
	}


	public String getRmsCodeCount() {
		return rmsCodeCount;
	}

	public void setSumIf(String sumIf) {
		this.sumIf = sumIf;
	}


	public String getSumIf() {
		return sumIf;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}


	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setInvoiceDisRate(String invoiceDisRate) {
		this.invoiceDisRate = invoiceDisRate;
	}


	public String getInvoiceDisRate() {
		return invoiceDisRate;
	}

	public void setInvoiceDisIfvat(String invoiceDisIfvat) {
		this.invoiceDisIfvat = invoiceDisIfvat;
	}


	public String getInvoiceDisIfvat() {
		return invoiceDisIfvat;
	}

	public void setAdrbSumExcltax(String adrbSumExcltax) {
		this.adrbSumExcltax = adrbSumExcltax;
	}


	public String getAdrbSumExcltax() {
		return adrbSumExcltax;
	}

	public void setEpSumExcltax(String epSumExcltax) {
		this.epSumExcltax = epSumExcltax;
	}


	public String getEpSumExcltax() {
		return epSumExcltax;
	}

	public void setAdpfSumExcltax(String adpfSumExcltax) {
		this.adpfSumExcltax = adpfSumExcltax;
	}


	public String getAdpfSumExcltax() {
		return adpfSumExcltax;
	}

	public void setPromotionDiscount(String promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}


	public String getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscountTotal(String promotionDiscountTotal) {
		this.promotionDiscountTotal = promotionDiscountTotal;
	}


	public String getPromotionDiscountTotal() {
		return promotionDiscountTotal;
	}

	public void setPromotionDiscountExcltax(String promotionDiscountExcltax) {
		this.promotionDiscountExcltax = promotionDiscountExcltax;
	}


	public String getPromotionDiscountExcltax() {
		return promotionDiscountExcltax;
	}

	public void setDistributionDiscount(String distributionDiscount) {
		this.distributionDiscount = distributionDiscount;
	}


	public String getDistributionDiscount() {
		return distributionDiscount;
	}

	public void setDistributionDiscountTotal(String distributionDiscountTotal) {
		this.distributionDiscountTotal = distributionDiscountTotal;
	}


	public String getDistributionDiscountTotal() {
		return distributionDiscountTotal;
	}

	public void setDistributionDiscountExcltax(String distributionDiscountExcltax) {
		this.distributionDiscountExcltax = distributionDiscountExcltax;
	}


	public String getDistributionDiscountExcltax() {
		return distributionDiscountExcltax;
	}

	public void setAddaSumExcltx(String addaSumExcltx) {
		this.addaSumExcltx = addaSumExcltx;
	}


	public String getAddaSumExcltx() {
		return addaSumExcltx;
	}

	public void setVir002SumMoneyExcltx(String vir002SumMoneyExcltx) {
		this.vir002SumMoneyExcltx = vir002SumMoneyExcltx;
	}


	public String getVir002SumMoneyExcltx() {
		return vir002SumMoneyExcltx;
	}

	public void setVir001SumMoneyExcltx(String vir001SumMoneyExcltx) {
		this.vir001SumMoneyExcltx = vir001SumMoneyExcltx;
	}


	public String getVir001SumMoneyExcltx() {
		return vir001SumMoneyExcltx;
	}

	public void setReturnedGoodsFee(String returnedGoodsFee) {
		this.returnedGoodsFee = returnedGoodsFee;
	}


	public String getReturnedGoodsFee() {
		return returnedGoodsFee;
	}

	public void setReturnedGoodsFeeTotal(String returnedGoodsFeeTotal) {
		this.returnedGoodsFeeTotal = returnedGoodsFeeTotal;
	}


	public String getReturnedGoodsFeeTotal() {
		return returnedGoodsFeeTotal;
	}

	public void setReturnedGoodsFeeExcltx(String returnedGoodsFeeExcltx) {
		this.returnedGoodsFeeExcltx = returnedGoodsFeeExcltx;
	}


	public String getReturnedGoodsFeeExcltx() {
		return returnedGoodsFeeExcltx;
	}

	public void setAddgSumExcltx(String addgSumExcltx) {
		this.addgSumExcltx = addgSumExcltx;
	}


	public String getAddgSumExcltx() {
		return addgSumExcltx;
	}

	public void setAdtrSumExcltx(String adtrSumExcltx) {
		this.adtrSumExcltx = adtrSumExcltx;
	}


	public String getAdtrSumExcltx() {
		return adtrSumExcltx;
	}

	public void setBdfSumExcltx(String bdfSumExcltx) {
		this.bdfSumExcltx = bdfSumExcltx;
	}


	public String getBdfSumExcltx() {
		return bdfSumExcltx;
	}

	public void setNss001SumExcltx(String nss001SumExcltx) {
		this.nss001SumExcltx = nss001SumExcltx;
	}


	public String getNss001SumExcltx() {
		return nss001SumExcltx;
	}

	public void setRss001SumExltx(String rss001SumExltx) {
		this.rss001SumExltx = rss001SumExltx;
	}


	public String getRss001SumExltx() {
		return rss001SumExltx;
	}

	public void setRefitStoreSupport(String refitStoreSupport) {
		this.refitStoreSupport = refitStoreSupport;
	}


	public String getRefitStoreSupport() {
		return refitStoreSupport;
	}

	public void setRefitStoreNumSupport(String refitStoreNumSupport) {
		this.refitStoreNumSupport = refitStoreNumSupport;
	}


	public String getRefitStoreNumSupport() {
		return refitStoreNumSupport;
	}

	public void setRefitStoreDataSupport(String refitStoreDataSupport) {
		this.refitStoreDataSupport = refitStoreDataSupport;
	}


	public String getRefitStoreDataSupport() {
		return refitStoreDataSupport;
	}

	public void setRefitStoreSupportTotal(String refitStoreSupportTotal) {
		this.refitStoreSupportTotal = refitStoreSupportTotal;
	}


	public String getRefitStoreSupportTotal() {
		return refitStoreSupportTotal;
	}

	public void setRefitStoreSupportTexltx(String refitStoreSupportTexltx) {
		this.refitStoreSupportTexltx = refitStoreSupportTexltx;
	}


	public String getRefitStoreSupportTexltx() {
		return refitStoreSupportTexltx;
	}

	public void setAss001SumExltx(String ass001SumExltx) {
		this.ass001SumExltx = ass001SumExltx;
	}


	public String getAss001SumExltx() {
		return ass001SumExltx;
	}

	public void setWdp001SumExltx(String wdp001SumExltx) {
		this.wdp001SumExltx = wdp001SumExltx;
	}


	public String getWdp001SumExltx() {
		return wdp001SumExltx;
	}

	public void setNpm001SumExltx(String npm001SumExltx) {
		this.npm001SumExltx = npm001SumExltx;
	}


	public String getNpm001SumExltx() {
		return npm001SumExltx;
	}

	public void setDataSharingFee(String dataSharingFee) {
		this.dataSharingFee = dataSharingFee;
	}


	public String getDataSharingFee() {
		return dataSharingFee;
	}

	public void setDataSharingFeeTotal(String dataSharingFeeTotal) {
		this.dataSharingFeeTotal = dataSharingFeeTotal;
	}


	public String getDataSharingFeeTotal() {
		return dataSharingFeeTotal;
	}

	public void setDataSharingFeeExltx(String dataSharingFeeExltx) {
		this.dataSharingFeeExltx = dataSharingFeeExltx;
	}


	public String getDataSharingFeeExltx() {
		return dataSharingFeeExltx;
	}

	public void setDrb001SumExltx(String drb001SumExltx) {
		this.drb001SumExltx = drb001SumExltx;
	}


	public String getDrb001SumExltx() {
		return drb001SumExltx;
	}

	public void setDro001SumExltx(String dro001SumExltx) {
		this.dro001SumExltx = dro001SumExltx;
	}


	public String getDro001SumExltx() {
		return dro001SumExltx;
	}

	public void setDisplayRental(String displayRental) {
		this.displayRental = displayRental;
	}


	public String getDisplayRental() {
		return displayRental;
	}

	public void setDisplayRentalTotal(String displayRentalTotal) {
		this.displayRentalTotal = displayRentalTotal;
	}


	public String getDisplayRentalTotal() {
		return displayRentalTotal;
	}

	public void setDisplayRentalTotalExltx(String displayRentalTotalExltx) {
		this.displayRentalTotalExltx = displayRentalTotalExltx;
	}


	public String getDisplayRentalTotalExltx() {
		return displayRentalTotalExltx;
	}

	public void setDisplayNewtp(String displayNewtp) {
		this.displayNewtp = displayNewtp;
	}


	public String getDisplayNewtp() {
		return displayNewtp;
	}

	public void setDisplayNewtpTotal(String displayNewtpTotal) {
		this.displayNewtpTotal = displayNewtpTotal;
	}


	public String getDisplayNewtpTotal() {
		return displayNewtpTotal;
	}

	public void setDisplayNewtpExltx(String displayNewtpExltx) {
		this.displayNewtpExltx = displayNewtpExltx;
	}


	public String getDisplayNewtpExltx() {
		return displayNewtpExltx;
	}

	public void setDmi001SumExltx(String dmi001SumExltx) {
		this.dmi001SumExltx = dmi001SumExltx;
	}


	public String getDmi001SumExltx() {
		return dmi001SumExltx;
	}

	public void setPromotionFund(String promotionFund) {
		this.promotionFund = promotionFund;
	}


	public String getPromotionFund() {
		return promotionFund;
	}

	public void setPromotionFundTotal(String promotionFundTotal) {
		this.promotionFundTotal = promotionFundTotal;
	}


	public String getPromotionFundTotal() {
		return promotionFundTotal;
	}

	public void setPromotionFundExltx(String promotionFundExltx) {
		this.promotionFundExltx = promotionFundExltx;
	}


	public String getPromotionFundExltx() {
		return promotionFundExltx;
	}

	public void setHbi001SumExltx(String hbi001SumExltx) {
		this.hbi001SumExltx = hbi001SumExltx;
	}


	public String getHbi001SumExltx() {
		return hbi001SumExltx;
	}

	public void setBdf001SumExltx(String bdf001SumExltx) {
		this.bdf001SumExltx = bdf001SumExltx;
	}


	public String getBdf001SumExltx() {
		return bdf001SumExltx;
	}

	public void setBusinessDevFund(String businessDevFund) {
		this.businessDevFund = businessDevFund;
	}


	public String getBusinessDevFund() {
		return businessDevFund;
	}

	public void setBusinessDevFundTotal(String businessDevFundTotal) {
		this.businessDevFundTotal = businessDevFundTotal;
	}


	public String getBusinessDevFundTotal() {
		return businessDevFundTotal;
	}

	public void setBusinessDevFundExltx(String businessDevFundExltx) {
		this.businessDevFundExltx = businessDevFundExltx;
	}


	public String getBusinessDevFundExltx() {
		return businessDevFundExltx;
	}

	public void setCos001SumExltx(String cos001SumExltx) {
		this.cos001SumExltx = cos001SumExltx;
	}


	public String getCos001SumExltx() {
		return cos001SumExltx;
	}

	public void setOth001SumExltx(String oth001SumExltx) {
		this.oth001SumExltx = oth001SumExltx;
	}


	public String getOth001SumExltx() {
		return oth001SumExltx;
	}

	public void setNti001SumExltx(String nti001SumExltx) {
		this.nti001SumExltx = nti001SumExltx;
	}


	public String getNti001SumExltx() {
		return nti001SumExltx;
	}

	public void setLdp001SumExltx(String ldp001SumExltx) {
		this.ldp001SumExltx = ldp001SumExltx;
	}


	public String getLdp001SumExltx() {
		return ldp001SumExltx;
	}

	public void setNfp001SumExltx(String nfp001SumExltx) {
		this.nfp001SumExltx = nfp001SumExltx;
	}


	public String getNfp001SumExltx() {
		return nfp001SumExltx;
	}

	public void setLpu001SumExltx(String lpu001SumExltx) {
		this.lpu001SumExltx = lpu001SumExltx;
	}


	public String getLpu001SumExltx() {
		return lpu001SumExltx;
	}

	public void setBac001SumExltx(String bac001SumExltx) {
		this.bac001SumExltx = bac001SumExltx;
	}


	public String getBac001SumExltx() {
		return bac001SumExltx;
	}

	public void setUep001SumExltx(String uep001SumExltx) {
		this.uep001SumExltx = uep001SumExltx;
	}


	public String getUep001SumExltx() {
		return uep001SumExltx;
	}

	public void setOtf001SumExltx(String otf001SumExltx) {
		this.otf001SumExltx = otf001SumExltx;
	}


	public String getOtf001SumExltx() {
		return otf001SumExltx;
	}

	public void setQaSerFee(String qaSerFee) {
		this.qaSerFee = qaSerFee;
	}


	public String getQaSerFee() {
		return qaSerFee;
	}

	public void setQaSerFeeTotal(String qaSerFeeTotal) {
		this.qaSerFeeTotal = qaSerFeeTotal;
	}


	public String getQaSerFeeTotal() {
		return qaSerFeeTotal;
	}

	public void setQaSerFeeExltx(String qaSerFeeExltx) {
		this.qaSerFeeExltx = qaSerFeeExltx;
	}


	public String getQaSerFeeExltx() {
		return qaSerFeeExltx;
	}

	public void setPsf001SumExltx(String psf001SumExltx) {
		this.psf001SumExltx = psf001SumExltx;
	}


	public String getPsf001SumExltx() {
		return psf001SumExltx;
	}

	public void setPromotionSerFee(String promotionSerFee) {
		this.promotionSerFee = promotionSerFee;
	}


	public String getPromotionSerFee() {
		return promotionSerFee;
	}

	public void setPromotionSerFeeTotal(String promotionSerFeeTotal) {
		this.promotionSerFeeTotal = promotionSerFeeTotal;
	}


	public String getPromotionSerFeeTotal() {
		return promotionSerFeeTotal;
	}

	public void setPromotionSerFeeExltx(String promotionSerFeeExltx) {
		this.promotionSerFeeExltx = promotionSerFeeExltx;
	}


	public String getPromotionSerFeeExltx() {
		return promotionSerFeeExltx;
	}

	public void setCps001SumExltx(String cps001SumExltx) {
		this.cps001SumExltx = cps001SumExltx;
	}


	public String getCps001SumExltx() {
		return cps001SumExltx;
	}

	public void setCompensationIncomeTotal(String compensationIncomeTotal) {
		this.compensationIncomeTotal = compensationIncomeTotal;
	}


	public String getCompensationIncomeTotal() {
		return compensationIncomeTotal;
	}

	public void setCompensationIncomeExltx(String compensationIncomeExltx) {
		this.compensationIncomeExltx = compensationIncomeExltx;
	}


	public String getCompensationIncomeExltx() {
		return compensationIncomeExltx;
	}

	public void setOtherProFee(String otherProFee) {
		this.otherProFee = otherProFee;
	}


	public String getOtherProFee() {
		return otherProFee;
	}

	public void setOtherProFeeTotal(String otherProFeeTotal) {
		this.otherProFeeTotal = otherProFeeTotal;
	}


	public String getOtherProFeeTotal() {
		return otherProFeeTotal;
	}

	public void setOtherProFeeExltx(String otherProFeeExltx) {
		this.otherProFeeExltx = otherProFeeExltx;
	}


	public String getOtherProFeeExltx() {
		return otherProFeeExltx;
	}

	public void setObService(String obService) {
		this.obService = obService;
	}


	public String getObService() {
		return obService;
	}

	public void setObServiceTotal(String obServiceTotal) {
		this.obServiceTotal = obServiceTotal;
	}


	public String getObServiceTotal() {
		return obServiceTotal;
	}

	public void setObServiceExltx(String obServiceExltx) {
		this.obServiceExltx = obServiceExltx;
	}


	public String getObServiceExltx() {
		return obServiceExltx;
	}

	public void setOemTester(String oemTester) {
		this.oemTester = oemTester;
	}


	public String getOemTester() {
		return oemTester;
	}

	public void setOemTesterTotal(String oemTesterTotal) {
		this.oemTesterTotal = oemTesterTotal;
	}


	public String getOemTesterTotal() {
		return oemTesterTotal;
	}

	public void setOemTesterExltx(String oemTesterExltx) {
		this.oemTesterExltx = oemTesterExltx;
	}


	public String getOemTesterExltx() {
		return oemTesterExltx;
	}

	public void setRomotionMktg(String romotionMktg) {
		this.romotionMktg = romotionMktg;
	}


	public String getRomotionMktg() {
		return romotionMktg;
	}

	public void setRomotionMktgTotal(String romotionMktgTotal) {
		this.romotionMktgTotal = romotionMktgTotal;
	}


	public String getRomotionMktgTotal() {
		return romotionMktgTotal;
	}

	public void setRomotionMktgExltx(String romotionMktgExltx) {
		this.romotionMktgExltx = romotionMktgExltx;
	}


	public String getRomotionMktgExltx() {
		return romotionMktgExltx;
	}

	public void setTermsSales(String termsSales) {
		this.termsSales = termsSales;
	}


	public String getTermsSales() {
		return termsSales;
	}

	public void setTermsSalesTotal(String termsSalesTotal) {
		this.termsSalesTotal = termsSalesTotal;
	}


	public String getTermsSalesTotal() {
		return termsSalesTotal;
	}

	public void setTermsSalesExltx(String termsSalesExltx) {
		this.termsSalesExltx = termsSalesExltx;
	}


	public String getTermsSalesExltx() {
		return termsSalesExltx;
	}

	public void setTaxSum(String taxSum) {
		this.taxSum = taxSum;
	}


	public String getTaxSum() {
		return taxSum;
	}

	public void setTaxSumExltx(String taxSumExltx) {
		this.taxSumExltx = taxSumExltx;
	}


	public String getTaxSumExltx() {
		return taxSumExltx;
	}

	public void setTaxInclude(String taxInclude) {
		this.taxInclude = taxInclude;
	}


	public String getTaxInclude() {
		return taxInclude;
	}

	public void setTax1Sum(String tax1Sum) {
		this.tax1Sum = tax1Sum;
	}


	public String getTax1Sum() {
		return tax1Sum;
	}

	public void setTax1SumExltx(String tax1SumExltx) {
		this.tax1SumExltx = tax1SumExltx;
	}


	public String getTax1SumExltx() {
		return tax1SumExltx;
	}

	public void setTax1Include(String tax1Include) {
		this.tax1Include = tax1Include;
	}


	public String getTax1Include() {
		return tax1Include;
	}

	public void setTaxSum16(String taxSum16) {
		this.taxSum16 = taxSum16;
	}


	public String getTaxSum16() {
		return taxSum16;
	}

	public void setTaxSum16Exltx(String taxSum16Exltx) {
		this.taxSum16Exltx = taxSum16Exltx;
	}


	public String getTaxSum16Exltx() {
		return taxSum16Exltx;
	}

	public void setTaxInclude16(String taxInclude16) {
		this.taxInclude16 = taxInclude16;
	}


	public String getTaxInclude16() {
		return taxInclude16;
	}

	public void setTaxSum13(String taxSum13) {
		this.taxSum13 = taxSum13;
	}


	public String getTaxSum13() {
		return taxSum13;
	}

	public void setTaxSum13Exltx(String taxSum13Exltx) {
		this.taxSum13Exltx = taxSum13Exltx;
	}


	public String getTaxSum13Exltx() {
		return taxSum13Exltx;
	}

	public void setTaxInclude13(String taxInclude13) {
		this.taxInclude13 = taxInclude13;
	}


	public String getTaxInclude13() {
		return taxInclude13;
	}

	public void setTaxSum10(String taxSum10) {
		this.taxSum10 = taxSum10;
	}


	public String getTaxSum10() {
		return taxSum10;
	}

	public void setTaxSum10Exltx(String taxSum10Exltx) {
		this.taxSum10Exltx = taxSum10Exltx;
	}


	public String getTaxSum10Exltx() {
		return taxSum10Exltx;
	}

	public void setTaxInclude10(String taxInclude10) {
		this.taxInclude10 = taxInclude10;
	}


	public String getTaxInclude10() {
		return taxInclude10;
	}

	public void setTax1Sum6(String tax1Sum6) {
		this.tax1Sum6 = tax1Sum6;
	}


	public String getTax1Sum6() {
		return tax1Sum6;
	}

	public void setTax1Sum6Exltx(String tax1Sum6Exltx) {
		this.tax1Sum6Exltx = tax1Sum6Exltx;
	}


	public String getTax1Sum6Exltx() {
		return tax1Sum6Exltx;
	}

	public void setTax1Include6(String tax1Include6) {
		this.tax1Include6 = tax1Include6;
	}


	public String getTax1Include6() {
		return tax1Include6;
	}

	public void setNetOiExltx(String netOiExltx) {
		this.netOiExltx = netOiExltx;
	}


	public String getNetOiExltx() {
		return netOiExltx;
	}

	public void setCompensationIncome(String compensationIncome) {
		this.compensationIncome = compensationIncome;
	}


	public String getCompensationIncome() {
		return compensationIncome;
	}

	public void setVip001SumExltx(String vip001SumExltx) {
		this.vip001SumExltx = vip001SumExltx;
	}


	public String getVip001SumExltx() {
		return vip001SumExltx;
	}

	public void setCou001SumExltx(String cou001SumExltx) {
		this.cou001SumExltx = cou001SumExltx;
	}


	public String getCou001SumExltx() {
		return cou001SumExltx;
	}

	public void setNpd001SumExltx(String npd001SumExltx) {
		this.npd001SumExltx = npd001SumExltx;
	}


	public String getNpd001SumExltx() {
		return npd001SumExltx;
	}

	public void setCri001SumExltx(String cri001SumExltx) {
		this.cri001SumExltx = cri001SumExltx;
	}


	public String getCri001SumExltx() {
		return cri001SumExltx;
	}

	public void setBusinessDevSupportExcltx(String businessDevSupportExcltx) {
		this.businessDevSupportExcltx = businessDevSupportExcltx;
	}


	public String getBusinessDevSupportExcltx() {
		return businessDevSupportExcltx;
	}

	public void setBusinessDevSupport(String businessDevSupport) {
		this.businessDevSupport = businessDevSupport;
	}


	public String getBusinessDevSupport() {
		return businessDevSupport;
	}

	public void setBusinessDevSupportTotal(String businessDevSupportTotal) {
		this.businessDevSupportTotal = businessDevSupportTotal;
	}


	public String getBusinessDevSupportTotal() {
		return businessDevSupportTotal;
	}


}
