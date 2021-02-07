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
 * TtaSroiBillLineEntity_HI Entity Object
 * Mon Oct 14 17:32:39 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_sroi_bill_line")
public class TtaSroiBillLineEntity_HI {
    private Integer ttaSroiBillImportId;
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
    private String netPurchase;
    private String netPurchaseOrigin;
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
    private String tax6;
    private String nss;
    private String nssValue;
    private String nssStoreCount;
    private String nssStoreData;
    private String nssSum;
    private String nssMonthContrAdj;
    private String nssMonthReconAdj;
    private String nssPycontrAdj;
    private String nssPyreconAdj;
    private String nssPyotherAdj;
    private String nssMemo;
    private String nssTotal;
    private String rss;
    private String rssValue;
    private String rssStoreCount;
    private String rssStoreData;
    private String rssSum;
    private String rssMonthContrAdj;
    private String rssMonthReconAdj;
    private String rssPycontrAdj;
    private String rssPyreconAdj;
    private String rssPyotherAdj;
    private String rssMemo;
    private String rssTotal;
    private String fps;
    private String fpsValue;
    private String fpsStoreCount;
    private String fpsSum;
    private String fpsMonthContrAdj;
    private String fpsMonthReconAdj;
    private String fpsPycontrAdj;
    private String fpsPyreconAdj;
    private String fpsPyotherAdj;
    private String fpsMemo;
    private String fpsTotal;
    private String womdp;
    private String womdpValue;
    private String womdpStoreCount;
    private String womdpSum;
    private String womdpMonthContrAdj;
    private String womdpMonthReconAdj;
    private String womdpPycontrAdj;
    private String womdpPyreconAdj;
    private String womdpPyotherAdj;
    private String womdpMemo;
    private String womdpTotal;
    private String npmf;
    private String npmfValue;
    private String npmfStoreCount;
    private String npmfStoreData;
    private String npmfSum;
    private String npmfMonthContrAdj;
    private String npmfMonthReconAdj;
    private String npmfPycontrAdj;
    private String npmfPyreconAdj;
    private String npmfPyotherAdj;
    private String npmfMemo;
    private String npmfTotal;
    private String rgts;
    private String rgtsValue;
    private String rgtsPur;
    private String rgtsMonthContrAdj;
    private String rgtsMonthReconAdj;
    private String rgtsPycontrAdj;
    private String rgtsPyreconAdj;
    private String rgtsPyotherAdj;
    private String rgtsMemo;
    private String rgtsTotal;
    private String lpup;
    private String lpupValue;
    private String lpupSum;
    private String lpupMonthContrAdj;
    private String lpupMonthReconAdj;
    private String lpupPycontrAdj;
    private String lpupPyreconAdj;
    private String lpupPyotherAdj;
    private String lpupMemo;
    private String lpupTotal;
    private String ocrf;
    private String ocrfMonthContrAdj;
    private String ocrfMonthReconAdj;
    private String ocrfPycontraAdj;
    private String ocrfPyreconAdj;
    private String ocrfPyotherAdj;
    private String ocrfMemo;
    private String ocrfTotal;
    private String comin;
    private String cominValue;
    private String cominStoreCount;
    private String cominSum;
    private String cominMonthContrAdj;
    private String cominMonthReconAdj;
    private String cominPyContrAdj;
    private String cominPyReconAdj;
    private String cominPyOtherAdj;
    private String cominMemo;
    private String cominTotal;
    private String psfvt;
    private String psfvtValue;
    private String psfvtSum;
    private String psfvtMonthContrAdj;
    private String psfvtMonthReconAdj;
    private String psfvtPyContrAdj;
    private String psfvtPyReconAdj;
    private String psfvtPyOtherAdj;
    private String psfvtMemo;
    private String psfvtTotal;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String nssPurchaseSum;
    private String rssPurchaseSum;
    private String fpsPurchaseSum;
    private String womdpPurchaseSum;
    private String npmfPurchaseSum;
    private String istd;
    private String istdValue;
    private String istdStoreCount;
    private String istdSum;
    private String istdPurchaseSum;
    private String istdMonthContrAdj;
    private String istdMonthReconAdj;
    private String istdPycontrAdj;
    private String istdPyreconAdj;
    private String istdPyotherAdj;
    private String istdMemo;
    private String istdTotal;
    private String nsod;
    private String nsodValue;
    private String nsodStoreCount;
    private String nsodSum;
    private String nsodPurchaseSum;
    private String nsodMonthContrAdj;
    private String nsodMonthReconAdj;
    private String nsodPycontrAdj;
    private String nsodPyreconAdj;
    private String nsodPyotherAdj;
    private String nsodMemo;
    private String nsodTotal;
    private String oicn;
    private String oicnValue;
    private String oicnStoreCount;
    private String oicnSum;
    private String oicnYearSumTta;
    private String oicnYearSumOntop;
    private String oicnMonthCount;
    private String oicnMonthContrAdj;
    private String oicnMonthReconAdj;
    private String oicnPycontrAdj;
    private String oicnPyreconAdj;
    private String oicnPyotherAdj;
    private String oicnMemo;
    private String oicnTotal;
    private String oiob;
    private String oiobValue;
    private String oiobStoreCount;
    private String oiobSum;
    private String oiobYearSumTta;
    private String oiobYearSumOntop;
    private String oiobMonthCount;
    private String oiobMonthContrAdj;
    private String oiobMonthReconAdj;
    private String oiobPycontrAdj;
    private String oiobPyreconAdj;
    private String oiobPyotherAdj;
    private String oiobMemo;
    private String lpupStoreValue;
    private String lpupPurchaseSum;
    private String olpd;
    private String olpdValue;
    private String olpdStoreValue;
    private String olpdSum;
    private String olpdPurchaseSum;
    private String olpdMonthContrAdj;
    private String olpdMonthReconAdj;
    private String olpdPycontrAdj;
    private String olpdPyreconAdj;
    private String olpdPyotherAdj;
    private String olpdMemo;
    private String olpdTotal;
    private Integer operatorUserId;

	public void setTtaSroiBillImportId(Integer ttaSroiBillImportId) {
		this.ttaSroiBillImportId = ttaSroiBillImportId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SROI_BILL_LINE", sequenceName="SEQ_TTA_SROI_BILL_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SROI_BILL_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="tta_sroi_bill_import_id", nullable=false, length=22)	
	public Integer getTtaSroiBillImportId() {
		return ttaSroiBillImportId;
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

	@Column(name="pick_up_rate", nullable=true, length=100)	
	public String getPickUpRate() {
		return pickUpRate;
	}

	public void setVenderContainTax(String venderContainTax) {
		this.venderContainTax = venderContainTax;
	}

	@Column(name="vender_contain_tax", nullable=true, length=100)	
	public String getVenderContainTax() {
		return venderContainTax;
	}

	public void setNetPurchase(String netPurchase) {
		this.netPurchase = netPurchase;
	}

	@Column(name="net_purchase", nullable=true, length=100)	
	public String getNetPurchase() {
		return netPurchase;
	}

	public void setNetPurchaseOrigin(String netPurchaseOrigin) {
		this.netPurchaseOrigin = netPurchaseOrigin;
	}

	@Column(name="net_purchase_origin", nullable=true, length=100)	
	public String getNetPurchaseOrigin() {
		return netPurchaseOrigin;
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

	@Column(name="ap_oi_total_tax", nullable=true, length=50)	
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

	public void setTax6(String tax6) {
		this.tax6 = tax6;
	}

	@Column(name="tax6", nullable=true, length=50)	
	public String getTax6() {
		return tax6;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	@Column(name="nss", nullable=true, length=500)	
	public String getNss() {
		return nss;
	}

	public void setNssValue(String nssValue) {
		this.nssValue = nssValue;
	}

	@Column(name="nss_value", nullable=true, length=50)	
	public String getNssValue() {
		return nssValue;
	}

	public void setNssStoreCount(String nssStoreCount) {
		this.nssStoreCount = nssStoreCount;
	}

	@Column(name="nss_store_count", nullable=true, length=50)	
	public String getNssStoreCount() {
		return nssStoreCount;
	}

	public void setNssStoreData(String nssStoreData) {
		this.nssStoreData = nssStoreData;
	}

	@Column(name="nss_store_data", nullable=true, length=500)	
	public String getNssStoreData() {
		return nssStoreData;
	}

	public void setNssSum(String nssSum) {
		this.nssSum = nssSum;
	}

	@Column(name="nss_sum", nullable=true, length=50)	
	public String getNssSum() {
		return nssSum;
	}

	public void setNssMonthContrAdj(String nssMonthContrAdj) {
		this.nssMonthContrAdj = nssMonthContrAdj;
	}

	@Column(name="nss_month_contr_adj", nullable=true, length=50)	
	public String getNssMonthContrAdj() {
		return nssMonthContrAdj;
	}

	public void setNssMonthReconAdj(String nssMonthReconAdj) {
		this.nssMonthReconAdj = nssMonthReconAdj;
	}

	@Column(name="nss_month_recon_adj", nullable=true, length=50)	
	public String getNssMonthReconAdj() {
		return nssMonthReconAdj;
	}

	public void setNssPycontrAdj(String nssPycontrAdj) {
		this.nssPycontrAdj = nssPycontrAdj;
	}

	@Column(name="nss_pycontr_adj", nullable=true, length=50)	
	public String getNssPycontrAdj() {
		return nssPycontrAdj;
	}

	public void setNssPyreconAdj(String nssPyreconAdj) {
		this.nssPyreconAdj = nssPyreconAdj;
	}

	@Column(name="nss_pyrecon_adj", nullable=true, length=50)	
	public String getNssPyreconAdj() {
		return nssPyreconAdj;
	}

	public void setNssPyotherAdj(String nssPyotherAdj) {
		this.nssPyotherAdj = nssPyotherAdj;
	}

	@Column(name="nss_pyother_adj", nullable=true, length=50)	
	public String getNssPyotherAdj() {
		return nssPyotherAdj;
	}

	public void setNssMemo(String nssMemo) {
		this.nssMemo = nssMemo;
	}

	@Column(name="nss_memo", nullable=true, length=500)	
	public String getNssMemo() {
		return nssMemo;
	}

	public void setNssTotal(String nssTotal) {
		this.nssTotal = nssTotal;
	}

	@Column(name="nss_total", nullable=true, length=50)	
	public String getNssTotal() {
		return nssTotal;
	}

	public void setRss(String rss) {
		this.rss = rss;
	}

	@Column(name="rss", nullable=true, length=500)	
	public String getRss() {
		return rss;
	}

	public void setRssValue(String rssValue) {
		this.rssValue = rssValue;
	}

	@Column(name="rss_value", nullable=true, length=50)	
	public String getRssValue() {
		return rssValue;
	}

	public void setRssStoreCount(String rssStoreCount) {
		this.rssStoreCount = rssStoreCount;
	}

	@Column(name="rss_store_count", nullable=true, length=50)	
	public String getRssStoreCount() {
		return rssStoreCount;
	}

	public void setRssStoreData(String rssStoreData) {
		this.rssStoreData = rssStoreData;
	}

	@Column(name="rss_store_data", nullable=true, length=500)	
	public String getRssStoreData() {
		return rssStoreData;
	}

	public void setRssSum(String rssSum) {
		this.rssSum = rssSum;
	}

	@Column(name="rss_sum", nullable=true, length=50)	
	public String getRssSum() {
		return rssSum;
	}

	public void setRssMonthContrAdj(String rssMonthContrAdj) {
		this.rssMonthContrAdj = rssMonthContrAdj;
	}

	@Column(name="rss_month_contr_adj", nullable=true, length=50)	
	public String getRssMonthContrAdj() {
		return rssMonthContrAdj;
	}

	public void setRssMonthReconAdj(String rssMonthReconAdj) {
		this.rssMonthReconAdj = rssMonthReconAdj;
	}

	@Column(name="rss_month_recon_adj", nullable=true, length=50)	
	public String getRssMonthReconAdj() {
		return rssMonthReconAdj;
	}

	public void setRssPycontrAdj(String rssPycontrAdj) {
		this.rssPycontrAdj = rssPycontrAdj;
	}

	@Column(name="rss_pycontr_adj", nullable=true, length=50)	
	public String getRssPycontrAdj() {
		return rssPycontrAdj;
	}

	public void setRssPyreconAdj(String rssPyreconAdj) {
		this.rssPyreconAdj = rssPyreconAdj;
	}

	@Column(name="rss_pyrecon_adj", nullable=true, length=50)	
	public String getRssPyreconAdj() {
		return rssPyreconAdj;
	}

	public void setRssPyotherAdj(String rssPyotherAdj) {
		this.rssPyotherAdj = rssPyotherAdj;
	}

	@Column(name="rss_pyother_adj", nullable=true, length=50)	
	public String getRssPyotherAdj() {
		return rssPyotherAdj;
	}

	public void setRssMemo(String rssMemo) {
		this.rssMemo = rssMemo;
	}

	@Column(name="rss_memo", nullable=true, length=500)	
	public String getRssMemo() {
		return rssMemo;
	}

	public void setRssTotal(String rssTotal) {
		this.rssTotal = rssTotal;
	}

	@Column(name="rss_total", nullable=true, length=50)	
	public String getRssTotal() {
		return rssTotal;
	}

	public void setFps(String fps) {
		this.fps = fps;
	}

	@Column(name="fps", nullable=true, length=500)	
	public String getFps() {
		return fps;
	}

	public void setFpsValue(String fpsValue) {
		this.fpsValue = fpsValue;
	}

	@Column(name="fps_value", nullable=true, length=50)	
	public String getFpsValue() {
		return fpsValue;
	}

	public void setFpsStoreCount(String fpsStoreCount) {
		this.fpsStoreCount = fpsStoreCount;
	}

	@Column(name="fps_store_count", nullable=true, length=50)	
	public String getFpsStoreCount() {
		return fpsStoreCount;
	}

	public void setFpsSum(String fpsSum) {
		this.fpsSum = fpsSum;
	}

	@Column(name="fps_sum", nullable=true, length=50)	
	public String getFpsSum() {
		return fpsSum;
	}

	public void setFpsMonthContrAdj(String fpsMonthContrAdj) {
		this.fpsMonthContrAdj = fpsMonthContrAdj;
	}

	@Column(name="fps_month_contr_adj", nullable=true, length=50)	
	public String getFpsMonthContrAdj() {
		return fpsMonthContrAdj;
	}

	public void setFpsMonthReconAdj(String fpsMonthReconAdj) {
		this.fpsMonthReconAdj = fpsMonthReconAdj;
	}

	@Column(name="fps_month_recon_adj", nullable=true, length=50)	
	public String getFpsMonthReconAdj() {
		return fpsMonthReconAdj;
	}

	public void setFpsPycontrAdj(String fpsPycontrAdj) {
		this.fpsPycontrAdj = fpsPycontrAdj;
	}

	@Column(name="fps_pycontr_adj", nullable=true, length=50)	
	public String getFpsPycontrAdj() {
		return fpsPycontrAdj;
	}

	public void setFpsPyreconAdj(String fpsPyreconAdj) {
		this.fpsPyreconAdj = fpsPyreconAdj;
	}

	@Column(name="fps_pyrecon_adj", nullable=true, length=50)	
	public String getFpsPyreconAdj() {
		return fpsPyreconAdj;
	}

	public void setFpsPyotherAdj(String fpsPyotherAdj) {
		this.fpsPyotherAdj = fpsPyotherAdj;
	}

	@Column(name="fps_pyother_adj", nullable=true, length=50)	
	public String getFpsPyotherAdj() {
		return fpsPyotherAdj;
	}

	public void setFpsMemo(String fpsMemo) {
		this.fpsMemo = fpsMemo;
	}

	@Column(name="fps_memo", nullable=true, length=500)	
	public String getFpsMemo() {
		return fpsMemo;
	}

	public void setFpsTotal(String fpsTotal) {
		this.fpsTotal = fpsTotal;
	}

	@Column(name="fps_total", nullable=true, length=50)	
	public String getFpsTotal() {
		return fpsTotal;
	}

	public void setWomdp(String womdp) {
		this.womdp = womdp;
	}

	@Column(name="womdp", nullable=true, length=500)	
	public String getWomdp() {
		return womdp;
	}

	public void setWomdpValue(String womdpValue) {
		this.womdpValue = womdpValue;
	}

	@Column(name="womdp_value", nullable=true, length=50)	
	public String getWomdpValue() {
		return womdpValue;
	}

	public void setWomdpStoreCount(String womdpStoreCount) {
		this.womdpStoreCount = womdpStoreCount;
	}

	@Column(name="womdp_store_count", nullable=true, length=50)	
	public String getWomdpStoreCount() {
		return womdpStoreCount;
	}

	public void setWomdpSum(String womdpSum) {
		this.womdpSum = womdpSum;
	}

	@Column(name="womdp_sum", nullable=true, length=50)	
	public String getWomdpSum() {
		return womdpSum;
	}

	public void setWomdpMonthContrAdj(String womdpMonthContrAdj) {
		this.womdpMonthContrAdj = womdpMonthContrAdj;
	}

	@Column(name="womdp_month_contr_adj", nullable=true, length=50)	
	public String getWomdpMonthContrAdj() {
		return womdpMonthContrAdj;
	}

	public void setWomdpMonthReconAdj(String womdpMonthReconAdj) {
		this.womdpMonthReconAdj = womdpMonthReconAdj;
	}

	@Column(name="womdp_month_recon_adj", nullable=true, length=50)	
	public String getWomdpMonthReconAdj() {
		return womdpMonthReconAdj;
	}

	public void setWomdpPycontrAdj(String womdpPycontrAdj) {
		this.womdpPycontrAdj = womdpPycontrAdj;
	}

	@Column(name="womdp_pycontr_adj", nullable=true, length=50)	
	public String getWomdpPycontrAdj() {
		return womdpPycontrAdj;
	}

	public void setWomdpPyreconAdj(String womdpPyreconAdj) {
		this.womdpPyreconAdj = womdpPyreconAdj;
	}

	@Column(name="womdp_pyrecon_adj", nullable=true, length=50)	
	public String getWomdpPyreconAdj() {
		return womdpPyreconAdj;
	}

	public void setWomdpPyotherAdj(String womdpPyotherAdj) {
		this.womdpPyotherAdj = womdpPyotherAdj;
	}

	@Column(name="womdp_pyother_adj", nullable=true, length=50)	
	public String getWomdpPyotherAdj() {
		return womdpPyotherAdj;
	}

	public void setWomdpMemo(String womdpMemo) {
		this.womdpMemo = womdpMemo;
	}

	@Column(name="womdp_memo", nullable=true, length=500)	
	public String getWomdpMemo() {
		return womdpMemo;
	}

	public void setWomdpTotal(String womdpTotal) {
		this.womdpTotal = womdpTotal;
	}

	@Column(name="womdp_total", nullable=true, length=50)	
	public String getWomdpTotal() {
		return womdpTotal;
	}

	public void setNpmf(String npmf) {
		this.npmf = npmf;
	}

	@Column(name="npmf", nullable=true, length=500)	
	public String getNpmf() {
		return npmf;
	}

	public void setNpmfValue(String npmfValue) {
		this.npmfValue = npmfValue;
	}

	@Column(name="npmf_value", nullable=true, length=50)	
	public String getNpmfValue() {
		return npmfValue;
	}

	public void setNpmfStoreCount(String npmfStoreCount) {
		this.npmfStoreCount = npmfStoreCount;
	}

	@Column(name="npmf_store_count", nullable=true, length=50)	
	public String getNpmfStoreCount() {
		return npmfStoreCount;
	}

	public void setNpmfStoreData(String npmfStoreData) {
		this.npmfStoreData = npmfStoreData;
	}

	@Column(name="npmf_store_data", nullable=true, length=500)	
	public String getNpmfStoreData() {
		return npmfStoreData;
	}

	public void setNpmfSum(String npmfSum) {
		this.npmfSum = npmfSum;
	}

	@Column(name="npmf_sum", nullable=true, length=50)	
	public String getNpmfSum() {
		return npmfSum;
	}

	public void setNpmfMonthContrAdj(String npmfMonthContrAdj) {
		this.npmfMonthContrAdj = npmfMonthContrAdj;
	}

	@Column(name="npmf_month_contr_adj", nullable=true, length=50)	
	public String getNpmfMonthContrAdj() {
		return npmfMonthContrAdj;
	}

	public void setNpmfMonthReconAdj(String npmfMonthReconAdj) {
		this.npmfMonthReconAdj = npmfMonthReconAdj;
	}

	@Column(name="npmf_month_recon_adj", nullable=true, length=50)	
	public String getNpmfMonthReconAdj() {
		return npmfMonthReconAdj;
	}

	public void setNpmfPycontrAdj(String npmfPycontrAdj) {
		this.npmfPycontrAdj = npmfPycontrAdj;
	}

	@Column(name="npmf_pycontr_adj", nullable=true, length=50)	
	public String getNpmfPycontrAdj() {
		return npmfPycontrAdj;
	}

	public void setNpmfPyreconAdj(String npmfPyreconAdj) {
		this.npmfPyreconAdj = npmfPyreconAdj;
	}

	@Column(name="npmf_pyrecon_adj", nullable=true, length=50)	
	public String getNpmfPyreconAdj() {
		return npmfPyreconAdj;
	}

	public void setNpmfPyotherAdj(String npmfPyotherAdj) {
		this.npmfPyotherAdj = npmfPyotherAdj;
	}

	@Column(name="npmf_pyother_adj", nullable=true, length=50)	
	public String getNpmfPyotherAdj() {
		return npmfPyotherAdj;
	}

	public void setNpmfMemo(String npmfMemo) {
		this.npmfMemo = npmfMemo;
	}

	@Column(name="npmf_memo", nullable=true, length=500)	
	public String getNpmfMemo() {
		return npmfMemo;
	}

	public void setNpmfTotal(String npmfTotal) {
		this.npmfTotal = npmfTotal;
	}

	@Column(name="npmf_total", nullable=true, length=50)	
	public String getNpmfTotal() {
		return npmfTotal;
	}

	public void setRgts(String rgts) {
		this.rgts = rgts;
	}

	@Column(name="rgts", nullable=true, length=500)	
	public String getRgts() {
		return rgts;
	}

	public void setRgtsValue(String rgtsValue) {
		this.rgtsValue = rgtsValue;
	}

	@Column(name="rgts_value", nullable=true, length=50)	
	public String getRgtsValue() {
		return rgtsValue;
	}

	public void setRgtsPur(String rgtsPur) {
		this.rgtsPur = rgtsPur;
	}

	@Column(name="rgts_pur", nullable=true, length=50)	
	public String getRgtsPur() {
		return rgtsPur;
	}

	public void setRgtsMonthContrAdj(String rgtsMonthContrAdj) {
		this.rgtsMonthContrAdj = rgtsMonthContrAdj;
	}

	@Column(name="rgts_month_contr_adj", nullable=true, length=50)	
	public String getRgtsMonthContrAdj() {
		return rgtsMonthContrAdj;
	}

	public void setRgtsMonthReconAdj(String rgtsMonthReconAdj) {
		this.rgtsMonthReconAdj = rgtsMonthReconAdj;
	}

	@Column(name="rgts_month_recon_adj", nullable=true, length=50)	
	public String getRgtsMonthReconAdj() {
		return rgtsMonthReconAdj;
	}

	public void setRgtsPycontrAdj(String rgtsPycontrAdj) {
		this.rgtsPycontrAdj = rgtsPycontrAdj;
	}

	@Column(name="rgts_pycontr_adj", nullable=true, length=50)	
	public String getRgtsPycontrAdj() {
		return rgtsPycontrAdj;
	}

	public void setRgtsPyreconAdj(String rgtsPyreconAdj) {
		this.rgtsPyreconAdj = rgtsPyreconAdj;
	}

	@Column(name="rgts_pyrecon_adj", nullable=true, length=50)	
	public String getRgtsPyreconAdj() {
		return rgtsPyreconAdj;
	}

	public void setRgtsPyotherAdj(String rgtsPyotherAdj) {
		this.rgtsPyotherAdj = rgtsPyotherAdj;
	}

	@Column(name="rgts_pyother_adj", nullable=true, length=50)	
	public String getRgtsPyotherAdj() {
		return rgtsPyotherAdj;
	}

	public void setRgtsMemo(String rgtsMemo) {
		this.rgtsMemo = rgtsMemo;
	}

	@Column(name="rgts_memo", nullable=true, length=500)	
	public String getRgtsMemo() {
		return rgtsMemo;
	}

	public void setRgtsTotal(String rgtsTotal) {
		this.rgtsTotal = rgtsTotal;
	}

	@Column(name="rgts_total", nullable=true, length=50)	
	public String getRgtsTotal() {
		return rgtsTotal;
	}

	public void setLpup(String lpup) {
		this.lpup = lpup;
	}

	@Column(name="lpup", nullable=true, length=500)	
	public String getLpup() {
		return lpup;
	}

	public void setLpupValue(String lpupValue) {
		this.lpupValue = lpupValue;
	}

	@Column(name="lpup_value", nullable=true, length=50)	
	public String getLpupValue() {
		return lpupValue;
	}

	public void setLpupSum(String lpupSum) {
		this.lpupSum = lpupSum;
	}

	@Column(name="lpup_sum", nullable=true, length=50)	
	public String getLpupSum() {
		return lpupSum;
	}

	public void setLpupMonthContrAdj(String lpupMonthContrAdj) {
		this.lpupMonthContrAdj = lpupMonthContrAdj;
	}

	@Column(name="lpup_month_contr_adj", nullable=true, length=50)	
	public String getLpupMonthContrAdj() {
		return lpupMonthContrAdj;
	}

	public void setLpupMonthReconAdj(String lpupMonthReconAdj) {
		this.lpupMonthReconAdj = lpupMonthReconAdj;
	}

	@Column(name="lpup_month_recon_adj", nullable=true, length=50)	
	public String getLpupMonthReconAdj() {
		return lpupMonthReconAdj;
	}

	public void setLpupPycontrAdj(String lpupPycontrAdj) {
		this.lpupPycontrAdj = lpupPycontrAdj;
	}

	@Column(name="lpup_pycontr_adj", nullable=true, length=50)	
	public String getLpupPycontrAdj() {
		return lpupPycontrAdj;
	}

	public void setLpupPyreconAdj(String lpupPyreconAdj) {
		this.lpupPyreconAdj = lpupPyreconAdj;
	}

	@Column(name="lpup_pyrecon_adj", nullable=true, length=50)	
	public String getLpupPyreconAdj() {
		return lpupPyreconAdj;
	}

	public void setLpupPyotherAdj(String lpupPyotherAdj) {
		this.lpupPyotherAdj = lpupPyotherAdj;
	}

	@Column(name="lpup_pyother_adj", nullable=true, length=50)	
	public String getLpupPyotherAdj() {
		return lpupPyotherAdj;
	}

	public void setLpupMemo(String lpupMemo) {
		this.lpupMemo = lpupMemo;
	}

	@Column(name="lpup_memo", nullable=true, length=500)	
	public String getLpupMemo() {
		return lpupMemo;
	}

	public void setLpupTotal(String lpupTotal) {
		this.lpupTotal = lpupTotal;
	}

	@Column(name="lpup_total", nullable=true, length=50)	
	public String getLpupTotal() {
		return lpupTotal;
	}

	public void setOcrf(String ocrf) {
		this.ocrf = ocrf;
	}

	@Column(name="ocrf", nullable=true, length=500)	
	public String getOcrf() {
		return ocrf;
	}

	public void setOcrfMonthContrAdj(String ocrfMonthContrAdj) {
		this.ocrfMonthContrAdj = ocrfMonthContrAdj;
	}

	@Column(name="ocrf_month_contr_adj", nullable=true, length=50)	
	public String getOcrfMonthContrAdj() {
		return ocrfMonthContrAdj;
	}

	public void setOcrfMonthReconAdj(String ocrfMonthReconAdj) {
		this.ocrfMonthReconAdj = ocrfMonthReconAdj;
	}

	@Column(name="ocrf_month_recon_adj", nullable=true, length=50)	
	public String getOcrfMonthReconAdj() {
		return ocrfMonthReconAdj;
	}

	public void setOcrfPycontraAdj(String ocrfPycontraAdj) {
		this.ocrfPycontraAdj = ocrfPycontraAdj;
	}

	@Column(name="ocrf_pycontra_adj", nullable=true, length=50)	
	public String getOcrfPycontraAdj() {
		return ocrfPycontraAdj;
	}

	public void setOcrfPyreconAdj(String ocrfPyreconAdj) {
		this.ocrfPyreconAdj = ocrfPyreconAdj;
	}

	@Column(name="ocrf_pyrecon_adj", nullable=true, length=50)	
	public String getOcrfPyreconAdj() {
		return ocrfPyreconAdj;
	}

	public void setOcrfPyotherAdj(String ocrfPyotherAdj) {
		this.ocrfPyotherAdj = ocrfPyotherAdj;
	}

	@Column(name="ocrf_pyother_adj", nullable=true, length=50)	
	public String getOcrfPyotherAdj() {
		return ocrfPyotherAdj;
	}

	public void setOcrfMemo(String ocrfMemo) {
		this.ocrfMemo = ocrfMemo;
	}

	@Column(name="ocrf_memo", nullable=true, length=500)	
	public String getOcrfMemo() {
		return ocrfMemo;
	}

	public void setOcrfTotal(String ocrfTotal) {
		this.ocrfTotal = ocrfTotal;
	}

	@Column(name="ocrf_total", nullable=true, length=50)	
	public String getOcrfTotal() {
		return ocrfTotal;
	}

	public void setComin(String comin) {
		this.comin = comin;
	}

	@Column(name="comin", nullable=true, length=500)	
	public String getComin() {
		return comin;
	}

	public void setCominValue(String cominValue) {
		this.cominValue = cominValue;
	}

	@Column(name="comin_value", nullable=true, length=50)	
	public String getCominValue() {
		return cominValue;
	}

	public void setCominStoreCount(String cominStoreCount) {
		this.cominStoreCount = cominStoreCount;
	}

	@Column(name="comin_store_count", nullable=true, length=50)	
	public String getCominStoreCount() {
		return cominStoreCount;
	}

	public void setCominSum(String cominSum) {
		this.cominSum = cominSum;
	}

	@Column(name="comin_sum", nullable=true, length=50)	
	public String getCominSum() {
		return cominSum;
	}

	public void setCominMonthContrAdj(String cominMonthContrAdj) {
		this.cominMonthContrAdj = cominMonthContrAdj;
	}

	@Column(name="comin_month_contr_adj", nullable=true, length=50)	
	public String getCominMonthContrAdj() {
		return cominMonthContrAdj;
	}

	public void setCominMonthReconAdj(String cominMonthReconAdj) {
		this.cominMonthReconAdj = cominMonthReconAdj;
	}

	@Column(name="comin_month_recon_adj", nullable=true, length=50)	
	public String getCominMonthReconAdj() {
		return cominMonthReconAdj;
	}

	public void setCominPyContrAdj(String cominPyContrAdj) {
		this.cominPyContrAdj = cominPyContrAdj;
	}

	@Column(name="comin_py_contr_adj", nullable=true, length=50)	
	public String getCominPyContrAdj() {
		return cominPyContrAdj;
	}

	public void setCominPyReconAdj(String cominPyReconAdj) {
		this.cominPyReconAdj = cominPyReconAdj;
	}

	@Column(name="comin_py_recon_adj", nullable=true, length=50)	
	public String getCominPyReconAdj() {
		return cominPyReconAdj;
	}

	public void setCominPyOtherAdj(String cominPyOtherAdj) {
		this.cominPyOtherAdj = cominPyOtherAdj;
	}

	@Column(name="comin_py_other_adj", nullable=true, length=50)	
	public String getCominPyOtherAdj() {
		return cominPyOtherAdj;
	}

	public void setCominMemo(String cominMemo) {
		this.cominMemo = cominMemo;
	}

	@Column(name="comin_memo", nullable=true, length=500)	
	public String getCominMemo() {
		return cominMemo;
	}

	public void setCominTotal(String cominTotal) {
		this.cominTotal = cominTotal;
	}

	@Column(name="comin_total", nullable=true, length=50)	
	public String getCominTotal() {
		return cominTotal;
	}

	public void setPsfvt(String psfvt) {
		this.psfvt = psfvt;
	}

	@Column(name="psfvt", nullable=true, length=500)	
	public String getPsfvt() {
		return psfvt;
	}

	public void setPsfvtValue(String psfvtValue) {
		this.psfvtValue = psfvtValue;
	}

	@Column(name="psfvt_value", nullable=true, length=50)	
	public String getPsfvtValue() {
		return psfvtValue;
	}

	public void setPsfvtSum(String psfvtSum) {
		this.psfvtSum = psfvtSum;
	}

	@Column(name="psfvt_sum", nullable=true, length=50)	
	public String getPsfvtSum() {
		return psfvtSum;
	}

	public void setPsfvtMonthContrAdj(String psfvtMonthContrAdj) {
		this.psfvtMonthContrAdj = psfvtMonthContrAdj;
	}

	@Column(name="psfvt_month_contr_adj", nullable=true, length=50)	
	public String getPsfvtMonthContrAdj() {
		return psfvtMonthContrAdj;
	}

	public void setPsfvtMonthReconAdj(String psfvtMonthReconAdj) {
		this.psfvtMonthReconAdj = psfvtMonthReconAdj;
	}

	@Column(name="psfvt_month_recon_adj", nullable=true, length=50)	
	public String getPsfvtMonthReconAdj() {
		return psfvtMonthReconAdj;
	}

	public void setPsfvtPyContrAdj(String psfvtPyContrAdj) {
		this.psfvtPyContrAdj = psfvtPyContrAdj;
	}

	@Column(name="psfvt_py_contr_adj", nullable=true, length=50)	
	public String getPsfvtPyContrAdj() {
		return psfvtPyContrAdj;
	}

	public void setPsfvtPyReconAdj(String psfvtPyReconAdj) {
		this.psfvtPyReconAdj = psfvtPyReconAdj;
	}

	@Column(name="psfvt_py_recon_adj", nullable=true, length=50)	
	public String getPsfvtPyReconAdj() {
		return psfvtPyReconAdj;
	}

	public void setPsfvtPyOtherAdj(String psfvtPyOtherAdj) {
		this.psfvtPyOtherAdj = psfvtPyOtherAdj;
	}

	@Column(name="psfvt_py_other_adj", nullable=true, length=50)	
	public String getPsfvtPyOtherAdj() {
		return psfvtPyOtherAdj;
	}

	public void setPsfvtMemo(String psfvtMemo) {
		this.psfvtMemo = psfvtMemo;
	}

	@Column(name="psfvt_memo", nullable=true, length=500)	
	public String getPsfvtMemo() {
		return psfvtMemo;
	}

	public void setPsfvtTotal(String psfvtTotal) {
		this.psfvtTotal = psfvtTotal;
	}

	@Column(name="psfvt_total", nullable=true, length=50)	
	public String getPsfvtTotal() {
		return psfvtTotal;
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

	public void setNssPurchaseSum(String nssPurchaseSum) {
		this.nssPurchaseSum = nssPurchaseSum;
	}

	@Column(name="nss_purchase_sum", nullable=true, length=50)	
	public String getNssPurchaseSum() {
		return nssPurchaseSum;
	}

	public void setRssPurchaseSum(String rssPurchaseSum) {
		this.rssPurchaseSum = rssPurchaseSum;
	}

	@Column(name="rss_purchase_sum", nullable=true, length=50)	
	public String getRssPurchaseSum() {
		return rssPurchaseSum;
	}

	public void setFpsPurchaseSum(String fpsPurchaseSum) {
		this.fpsPurchaseSum = fpsPurchaseSum;
	}

	@Column(name="fps_purchase_sum", nullable=true, length=50)	
	public String getFpsPurchaseSum() {
		return fpsPurchaseSum;
	}

	public void setWomdpPurchaseSum(String womdpPurchaseSum) {
		this.womdpPurchaseSum = womdpPurchaseSum;
	}

	@Column(name="womdp_purchase_sum", nullable=true, length=50)	
	public String getWomdpPurchaseSum() {
		return womdpPurchaseSum;
	}

	public void setNpmfPurchaseSum(String npmfPurchaseSum) {
		this.npmfPurchaseSum = npmfPurchaseSum;
	}

	@Column(name="npmf_purchase_sum", nullable=true, length=50)	
	public String getNpmfPurchaseSum() {
		return npmfPurchaseSum;
	}

	public void setIstd(String istd) {
		this.istd = istd;
	}

	@Column(name="istd", nullable=true, length=500)	
	public String getIstd() {
		return istd;
	}

	public void setIstdValue(String istdValue) {
		this.istdValue = istdValue;
	}

	@Column(name="istd_value", nullable=true, length=50)	
	public String getIstdValue() {
		return istdValue;
	}

	public void setIstdStoreCount(String istdStoreCount) {
		this.istdStoreCount = istdStoreCount;
	}

	@Column(name="istd_store_count", nullable=true, length=50)	
	public String getIstdStoreCount() {
		return istdStoreCount;
	}

	public void setIstdSum(String istdSum) {
		this.istdSum = istdSum;
	}

	@Column(name="istd_sum", nullable=true, length=50)	
	public String getIstdSum() {
		return istdSum;
	}

	public void setIstdPurchaseSum(String istdPurchaseSum) {
		this.istdPurchaseSum = istdPurchaseSum;
	}

	@Column(name="istd_purchase_sum", nullable=true, length=50)	
	public String getIstdPurchaseSum() {
		return istdPurchaseSum;
	}

	public void setIstdMonthContrAdj(String istdMonthContrAdj) {
		this.istdMonthContrAdj = istdMonthContrAdj;
	}

	@Column(name="istd_month_contr_adj", nullable=true, length=50)	
	public String getIstdMonthContrAdj() {
		return istdMonthContrAdj;
	}

	public void setIstdMonthReconAdj(String istdMonthReconAdj) {
		this.istdMonthReconAdj = istdMonthReconAdj;
	}

	@Column(name="istd_month_recon_adj", nullable=true, length=50)	
	public String getIstdMonthReconAdj() {
		return istdMonthReconAdj;
	}

	public void setIstdPycontrAdj(String istdPycontrAdj) {
		this.istdPycontrAdj = istdPycontrAdj;
	}

	@Column(name="istd_pycontr_adj", nullable=true, length=50)	
	public String getIstdPycontrAdj() {
		return istdPycontrAdj;
	}

	public void setIstdPyreconAdj(String istdPyreconAdj) {
		this.istdPyreconAdj = istdPyreconAdj;
	}

	@Column(name="istd_pyrecon_adj", nullable=true, length=50)	
	public String getIstdPyreconAdj() {
		return istdPyreconAdj;
	}

	public void setIstdPyotherAdj(String istdPyotherAdj) {
		this.istdPyotherAdj = istdPyotherAdj;
	}

	@Column(name="istd_pyother_adj", nullable=true, length=50)	
	public String getIstdPyotherAdj() {
		return istdPyotherAdj;
	}

	public void setIstdMemo(String istdMemo) {
		this.istdMemo = istdMemo;
	}

	@Column(name="istd_memo", nullable=true, length=500)	
	public String getIstdMemo() {
		return istdMemo;
	}

	public void setIstdTotal(String istdTotal) {
		this.istdTotal = istdTotal;
	}

	@Column(name="istd_total", nullable=true, length=50)	
	public String getIstdTotal() {
		return istdTotal;
	}

	public void setNsod(String nsod) {
		this.nsod = nsod;
	}

	@Column(name="nsod", nullable=true, length=500)	
	public String getNsod() {
		return nsod;
	}

	public void setNsodValue(String nsodValue) {
		this.nsodValue = nsodValue;
	}

	@Column(name="nsod_value", nullable=true, length=50)	
	public String getNsodValue() {
		return nsodValue;
	}

	public void setNsodStoreCount(String nsodStoreCount) {
		this.nsodStoreCount = nsodStoreCount;
	}

	@Column(name="nsod_store_count", nullable=true, length=50)	
	public String getNsodStoreCount() {
		return nsodStoreCount;
	}

	public void setNsodSum(String nsodSum) {
		this.nsodSum = nsodSum;
	}

	@Column(name="nsod_sum", nullable=true, length=50)	
	public String getNsodSum() {
		return nsodSum;
	}

	public void setNsodPurchaseSum(String nsodPurchaseSum) {
		this.nsodPurchaseSum = nsodPurchaseSum;
	}

	@Column(name="nsod_purchase_sum", nullable=true, length=50)	
	public String getNsodPurchaseSum() {
		return nsodPurchaseSum;
	}

	public void setNsodMonthContrAdj(String nsodMonthContrAdj) {
		this.nsodMonthContrAdj = nsodMonthContrAdj;
	}

	@Column(name="nsod_month_contr_adj", nullable=true, length=50)	
	public String getNsodMonthContrAdj() {
		return nsodMonthContrAdj;
	}

	public void setNsodMonthReconAdj(String nsodMonthReconAdj) {
		this.nsodMonthReconAdj = nsodMonthReconAdj;
	}

	@Column(name="nsod_month_recon_adj", nullable=true, length=50)	
	public String getNsodMonthReconAdj() {
		return nsodMonthReconAdj;
	}

	public void setNsodPycontrAdj(String nsodPycontrAdj) {
		this.nsodPycontrAdj = nsodPycontrAdj;
	}

	@Column(name="nsod_pycontr_adj", nullable=true, length=50)	
	public String getNsodPycontrAdj() {
		return nsodPycontrAdj;
	}

	public void setNsodPyreconAdj(String nsodPyreconAdj) {
		this.nsodPyreconAdj = nsodPyreconAdj;
	}

	@Column(name="nsod_pyrecon_adj", nullable=true, length=50)	
	public String getNsodPyreconAdj() {
		return nsodPyreconAdj;
	}

	public void setNsodPyotherAdj(String nsodPyotherAdj) {
		this.nsodPyotherAdj = nsodPyotherAdj;
	}

	@Column(name="nsod_pyother_adj", nullable=true, length=50)	
	public String getNsodPyotherAdj() {
		return nsodPyotherAdj;
	}

	public void setNsodMemo(String nsodMemo) {
		this.nsodMemo = nsodMemo;
	}

	@Column(name="nsod_memo", nullable=true, length=500)	
	public String getNsodMemo() {
		return nsodMemo;
	}

	public void setNsodTotal(String nsodTotal) {
		this.nsodTotal = nsodTotal;
	}

	@Column(name="nsod_total", nullable=true, length=50)	
	public String getNsodTotal() {
		return nsodTotal;
	}

	public void setOicn(String oicn) {
		this.oicn = oicn;
	}

	@Column(name="oicn", nullable=true, length=500)	
	public String getOicn() {
		return oicn;
	}

	public void setOicnValue(String oicnValue) {
		this.oicnValue = oicnValue;
	}

	@Column(name="oicn_value", nullable=true, length=50)	
	public String getOicnValue() {
		return oicnValue;
	}

	public void setOicnStoreCount(String oicnStoreCount) {
		this.oicnStoreCount = oicnStoreCount;
	}

	@Column(name="oicn_store_count", nullable=true, length=50)	
	public String getOicnStoreCount() {
		return oicnStoreCount;
	}

	public void setOicnSum(String oicnSum) {
		this.oicnSum = oicnSum;
	}

	@Column(name="oicn_sum", nullable=true, length=50)	
	public String getOicnSum() {
		return oicnSum;
	}

	public void setOicnYearSumTta(String oicnYearSumTta) {
		this.oicnYearSumTta = oicnYearSumTta;
	}

	@Column(name="oicn_year_sum_tta", nullable=true, length=50)	
	public String getOicnYearSumTta() {
		return oicnYearSumTta;
	}

	public void setOicnYearSumOntop(String oicnYearSumOntop) {
		this.oicnYearSumOntop = oicnYearSumOntop;
	}

	@Column(name="oicn_year_sum_ontop", nullable=true, length=50)	
	public String getOicnYearSumOntop() {
		return oicnYearSumOntop;
	}

	public void setOicnMonthCount(String oicnMonthCount) {
		this.oicnMonthCount = oicnMonthCount;
	}

	@Column(name="oicn_month_count", nullable=true, length=50)	
	public String getOicnMonthCount() {
		return oicnMonthCount;
	}

	public void setOicnMonthContrAdj(String oicnMonthContrAdj) {
		this.oicnMonthContrAdj = oicnMonthContrAdj;
	}

	@Column(name="oicn_month_contr_adj", nullable=true, length=50)	
	public String getOicnMonthContrAdj() {
		return oicnMonthContrAdj;
	}

	public void setOicnMonthReconAdj(String oicnMonthReconAdj) {
		this.oicnMonthReconAdj = oicnMonthReconAdj;
	}

	@Column(name="oicn_month_recon_adj", nullable=true, length=50)	
	public String getOicnMonthReconAdj() {
		return oicnMonthReconAdj;
	}

	public void setOicnPycontrAdj(String oicnPycontrAdj) {
		this.oicnPycontrAdj = oicnPycontrAdj;
	}

	@Column(name="oicn_pycontr_adj", nullable=true, length=50)	
	public String getOicnPycontrAdj() {
		return oicnPycontrAdj;
	}

	public void setOicnPyreconAdj(String oicnPyreconAdj) {
		this.oicnPyreconAdj = oicnPyreconAdj;
	}

	@Column(name="oicn_pyrecon_adj", nullable=true, length=50)	
	public String getOicnPyreconAdj() {
		return oicnPyreconAdj;
	}

	public void setOicnPyotherAdj(String oicnPyotherAdj) {
		this.oicnPyotherAdj = oicnPyotherAdj;
	}

	@Column(name="oicn_pyother_adj", nullable=true, length=50)	
	public String getOicnPyotherAdj() {
		return oicnPyotherAdj;
	}

	public void setOicnMemo(String oicnMemo) {
		this.oicnMemo = oicnMemo;
	}

	@Column(name="oicn_memo", nullable=true, length=500)	
	public String getOicnMemo() {
		return oicnMemo;
	}

	public void setOicnTotal(String oicnTotal) {
		this.oicnTotal = oicnTotal;
	}

	@Column(name="oicn_total", nullable=true, length=50)	
	public String getOicnTotal() {
		return oicnTotal;
	}

	public void setOiob(String oiob) {
		this.oiob = oiob;
	}

	@Column(name="oiob", nullable=true, length=500)	
	public String getOiob() {
		return oiob;
	}

	public void setOiobValue(String oiobValue) {
		this.oiobValue = oiobValue;
	}

	@Column(name="oiob_value", nullable=true, length=50)	
	public String getOiobValue() {
		return oiobValue;
	}

	public void setOiobStoreCount(String oiobStoreCount) {
		this.oiobStoreCount = oiobStoreCount;
	}

	@Column(name="oiob_store_count", nullable=true, length=50)	
	public String getOiobStoreCount() {
		return oiobStoreCount;
	}

	public void setOiobSum(String oiobSum) {
		this.oiobSum = oiobSum;
	}

	@Column(name="oiob_sum", nullable=true, length=50)	
	public String getOiobSum() {
		return oiobSum;
	}

	public void setOiobYearSumTta(String oiobYearSumTta) {
		this.oiobYearSumTta = oiobYearSumTta;
	}

	@Column(name="oiob_year_sum_tta", nullable=true, length=50)	
	public String getOiobYearSumTta() {
		return oiobYearSumTta;
	}

	public void setOiobYearSumOntop(String oiobYearSumOntop) {
		this.oiobYearSumOntop = oiobYearSumOntop;
	}

	@Column(name="oiob_year_sum_ontop", nullable=true, length=50)	
	public String getOiobYearSumOntop() {
		return oiobYearSumOntop;
	}

	public void setOiobMonthCount(String oiobMonthCount) {
		this.oiobMonthCount = oiobMonthCount;
	}

	@Column(name="oiob_month_count", nullable=true, length=50)	
	public String getOiobMonthCount() {
		return oiobMonthCount;
	}

	public void setOiobMonthContrAdj(String oiobMonthContrAdj) {
		this.oiobMonthContrAdj = oiobMonthContrAdj;
	}

	@Column(name="oiob_month_contr_adj", nullable=true, length=50)	
	public String getOiobMonthContrAdj() {
		return oiobMonthContrAdj;
	}

	public void setOiobMonthReconAdj(String oiobMonthReconAdj) {
		this.oiobMonthReconAdj = oiobMonthReconAdj;
	}

	@Column(name="oiob_month_recon_adj", nullable=true, length=50)	
	public String getOiobMonthReconAdj() {
		return oiobMonthReconAdj;
	}

	public void setOiobPycontrAdj(String oiobPycontrAdj) {
		this.oiobPycontrAdj = oiobPycontrAdj;
	}

	@Column(name="oiob_pycontr_adj", nullable=true, length=50)	
	public String getOiobPycontrAdj() {
		return oiobPycontrAdj;
	}

	public void setOiobPyreconAdj(String oiobPyreconAdj) {
		this.oiobPyreconAdj = oiobPyreconAdj;
	}

	@Column(name="oiob_pyrecon_adj", nullable=true, length=50)	
	public String getOiobPyreconAdj() {
		return oiobPyreconAdj;
	}

	public void setOiobPyotherAdj(String oiobPyotherAdj) {
		this.oiobPyotherAdj = oiobPyotherAdj;
	}

	@Column(name="oiob_pyother_adj", nullable=true, length=50)	
	public String getOiobPyotherAdj() {
		return oiobPyotherAdj;
	}

	public void setOiobMemo(String oiobMemo) {
		this.oiobMemo = oiobMemo;
	}

	@Column(name="oiob_memo", nullable=true, length=500)	
	public String getOiobMemo() {
		return oiobMemo;
	}

	public void setLpupStoreValue(String lpupStoreValue) {
		this.lpupStoreValue = lpupStoreValue;
	}

	@Column(name="lpup_store_value", nullable=true, length=50)	
	public String getLpupStoreValue() {
		return lpupStoreValue;
	}

	public void setLpupPurchaseSum(String lpupPurchaseSum) {
		this.lpupPurchaseSum = lpupPurchaseSum;
	}

	@Column(name="lpup_purchase_sum", nullable=true, length=50)	
	public String getLpupPurchaseSum() {
		return lpupPurchaseSum;
	}

	public void setOlpd(String olpd) {
		this.olpd = olpd;
	}

	@Column(name="olpd", nullable=true, length=500)	
	public String getOlpd() {
		return olpd;
	}

	public void setOlpdValue(String olpdValue) {
		this.olpdValue = olpdValue;
	}

	@Column(name="olpd_value", nullable=true, length=50)	
	public String getOlpdValue() {
		return olpdValue;
	}

	public void setOlpdStoreValue(String olpdStoreValue) {
		this.olpdStoreValue = olpdStoreValue;
	}

	@Column(name="olpd_store_value", nullable=true, length=50)	
	public String getOlpdStoreValue() {
		return olpdStoreValue;
	}

	public void setOlpdSum(String olpdSum) {
		this.olpdSum = olpdSum;
	}

	@Column(name="olpd_sum", nullable=true, length=50)	
	public String getOlpdSum() {
		return olpdSum;
	}

	public void setOlpdPurchaseSum(String olpdPurchaseSum) {
		this.olpdPurchaseSum = olpdPurchaseSum;
	}

	@Column(name="olpd_purchase_sum", nullable=true, length=50)	
	public String getOlpdPurchaseSum() {
		return olpdPurchaseSum;
	}

	public void setOlpdMonthContrAdj(String olpdMonthContrAdj) {
		this.olpdMonthContrAdj = olpdMonthContrAdj;
	}

	@Column(name="olpd_month_contr_adj", nullable=true, length=50)	
	public String getOlpdMonthContrAdj() {
		return olpdMonthContrAdj;
	}

	public void setOlpdMonthReconAdj(String olpdMonthReconAdj) {
		this.olpdMonthReconAdj = olpdMonthReconAdj;
	}

	@Column(name="olpd_month_recon_adj", nullable=true, length=50)	
	public String getOlpdMonthReconAdj() {
		return olpdMonthReconAdj;
	}

	public void setOlpdPycontrAdj(String olpdPycontrAdj) {
		this.olpdPycontrAdj = olpdPycontrAdj;
	}

	@Column(name="olpd_pycontr_adj", nullable=true, length=50)	
	public String getOlpdPycontrAdj() {
		return olpdPycontrAdj;
	}

	public void setOlpdPyreconAdj(String olpdPyreconAdj) {
		this.olpdPyreconAdj = olpdPyreconAdj;
	}

	@Column(name="olpd_pyrecon_adj", nullable=true, length=50)	
	public String getOlpdPyreconAdj() {
		return olpdPyreconAdj;
	}

	public void setOlpdPyotherAdj(String olpdPyotherAdj) {
		this.olpdPyotherAdj = olpdPyotherAdj;
	}

	@Column(name="olpd_pyother_adj", nullable=true, length=50)	
	public String getOlpdPyotherAdj() {
		return olpdPyotherAdj;
	}

	public void setOlpdMemo(String olpdMemo) {
		this.olpdMemo = olpdMemo;
	}

	@Column(name="olpd_memo", nullable=true, length=500)	
	public String getOlpdMemo() {
		return olpdMemo;
	}

	public void setOlpdTotal(String olpdTotal) {
		this.olpdTotal = olpdTotal;
	}

	@Column(name="olpd_total", nullable=true, length=50)	
	public String getOlpdTotal() {
		return olpdTotal;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
