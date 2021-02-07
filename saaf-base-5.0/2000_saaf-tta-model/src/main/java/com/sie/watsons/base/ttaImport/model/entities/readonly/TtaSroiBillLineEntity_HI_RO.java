package com.sie.watsons.base.ttaImport.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSroiBillLineEntity_HI_RO Entity Object
 * Mon Oct 14 17:32:39 CST 2019  Auto Generate
 */

public class TtaSroiBillLineEntity_HI_RO {

	public static final String  QUERY ="select *  from tta_sroi_bill_line tsbl where 1=1 " ;
    private Integer ttaSroiBillImportId;
    @JSONField(format="yyyy-MM-dd")
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

	
	public Integer getTtaSroiBillImportId() {
		return ttaSroiBillImportId;
	}

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

	public void setBookInJv(String bookInJv) {
		this.bookInJv = bookInJv;
	}

	
	public String getBookInJv() {
		return bookInJv;
	}

	public void setBookInJv1(String bookInJv1) {
		this.bookInJv1 = bookInJv1;
	}

	
	public String getBookInJv1() {
		return bookInJv1;
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

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	
	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setPickUpRate(String pickUpRate) {
		this.pickUpRate = pickUpRate;
	}

	
	public String getPickUpRate() {
		return pickUpRate;
	}

	public void setVenderContainTax(String venderContainTax) {
		this.venderContainTax = venderContainTax;
	}

	
	public String getVenderContainTax() {
		return venderContainTax;
	}

	public void setNetPurchase(String netPurchase) {
		this.netPurchase = netPurchase;
	}

	
	public String getNetPurchase() {
		return netPurchase;
	}

	public void setNetPurchaseOrigin(String netPurchaseOrigin) {
		this.netPurchaseOrigin = netPurchaseOrigin;
	}

	
	public String getNetPurchaseOrigin() {
		return netPurchaseOrigin;
	}

	public void setMonthPur(String monthPur) {
		this.monthPur = monthPur;
	}

	
	public String getMonthPur() {
		return monthPur;
	}

	public void setMonthCount(String monthCount) {
		this.monthCount = monthCount;
	}

	
	public String getMonthCount() {
		return monthCount;
	}

	public void setMonthAbi(String monthAbi) {
		this.monthAbi = monthAbi;
	}

	
	public String getMonthAbi() {
		return monthAbi;
	}

	public void setMonthAbiOntop(String monthAbiOntop) {
		this.monthAbiOntop = monthAbiOntop;
	}

	
	public String getMonthAbiOntop() {
		return monthAbiOntop;
	}

	public void setYearAbi(String yearAbi) {
		this.yearAbi = yearAbi;
	}

	
	public String getYearAbi() {
		return yearAbi;
	}

	public void setYearAbiOntop(String yearAbiOntop) {
		this.yearAbiOntop = yearAbiOntop;
	}

	
	public String getYearAbiOntop() {
		return yearAbiOntop;
	}

	public void setContractAdj(String contractAdj) {
		this.contractAdj = contractAdj;
	}

	
	public String getContractAdj() {
		return contractAdj;
	}

	public void setBillAdj(String billAdj) {
		this.billAdj = billAdj;
	}

	
	public String getBillAdj() {
		return billAdj;
	}

	public void setYearContractAdj(String yearContractAdj) {
		this.yearContractAdj = yearContractAdj;
	}

	
	public String getYearContractAdj() {
		return yearContractAdj;
	}

	public void setYearBillAdj(String yearBillAdj) {
		this.yearBillAdj = yearBillAdj;
	}

	
	public String getYearBillAdj() {
		return yearBillAdj;
	}

	public void setYearPurchaseCount(String yearPurchaseCount) {
		this.yearPurchaseCount = yearPurchaseCount;
	}

	
	public String getYearPurchaseCount() {
		return yearPurchaseCount;
	}

	public void setApOiTotalTax(String apOiTotalTax) {
		this.apOiTotalTax = apOiTotalTax;
	}

	
	public String getApOiTotalTax() {
		return apOiTotalTax;
	}

	public void setApOiTotalNotax(String apOiTotalNotax) {
		this.apOiTotalNotax = apOiTotalNotax;
	}

	
	public String getApOiTotalNotax() {
		return apOiTotalNotax;
	}

	public void setTax6(String tax6) {
		this.tax6 = tax6;
	}

	
	public String getTax6() {
		return tax6;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	
	public String getNss() {
		return nss;
	}

	public void setNssValue(String nssValue) {
		this.nssValue = nssValue;
	}

	
	public String getNssValue() {
		return nssValue;
	}

	public void setNssStoreCount(String nssStoreCount) {
		this.nssStoreCount = nssStoreCount;
	}

	
	public String getNssStoreCount() {
		return nssStoreCount;
	}

	public void setNssStoreData(String nssStoreData) {
		this.nssStoreData = nssStoreData;
	}

	
	public String getNssStoreData() {
		return nssStoreData;
	}

	public void setNssSum(String nssSum) {
		this.nssSum = nssSum;
	}

	
	public String getNssSum() {
		return nssSum;
	}

	public void setNssMonthContrAdj(String nssMonthContrAdj) {
		this.nssMonthContrAdj = nssMonthContrAdj;
	}

	
	public String getNssMonthContrAdj() {
		return nssMonthContrAdj;
	}

	public void setNssMonthReconAdj(String nssMonthReconAdj) {
		this.nssMonthReconAdj = nssMonthReconAdj;
	}

	
	public String getNssMonthReconAdj() {
		return nssMonthReconAdj;
	}

	public void setNssPycontrAdj(String nssPycontrAdj) {
		this.nssPycontrAdj = nssPycontrAdj;
	}

	
	public String getNssPycontrAdj() {
		return nssPycontrAdj;
	}

	public void setNssPyreconAdj(String nssPyreconAdj) {
		this.nssPyreconAdj = nssPyreconAdj;
	}

	
	public String getNssPyreconAdj() {
		return nssPyreconAdj;
	}

	public void setNssPyotherAdj(String nssPyotherAdj) {
		this.nssPyotherAdj = nssPyotherAdj;
	}

	
	public String getNssPyotherAdj() {
		return nssPyotherAdj;
	}

	public void setNssMemo(String nssMemo) {
		this.nssMemo = nssMemo;
	}

	
	public String getNssMemo() {
		return nssMemo;
	}

	public void setNssTotal(String nssTotal) {
		this.nssTotal = nssTotal;
	}

	
	public String getNssTotal() {
		return nssTotal;
	}

	public void setRss(String rss) {
		this.rss = rss;
	}

	
	public String getRss() {
		return rss;
	}

	public void setRssValue(String rssValue) {
		this.rssValue = rssValue;
	}

	
	public String getRssValue() {
		return rssValue;
	}

	public void setRssStoreCount(String rssStoreCount) {
		this.rssStoreCount = rssStoreCount;
	}

	
	public String getRssStoreCount() {
		return rssStoreCount;
	}

	public void setRssStoreData(String rssStoreData) {
		this.rssStoreData = rssStoreData;
	}

	
	public String getRssStoreData() {
		return rssStoreData;
	}

	public void setRssSum(String rssSum) {
		this.rssSum = rssSum;
	}

	
	public String getRssSum() {
		return rssSum;
	}

	public void setRssMonthContrAdj(String rssMonthContrAdj) {
		this.rssMonthContrAdj = rssMonthContrAdj;
	}

	
	public String getRssMonthContrAdj() {
		return rssMonthContrAdj;
	}

	public void setRssMonthReconAdj(String rssMonthReconAdj) {
		this.rssMonthReconAdj = rssMonthReconAdj;
	}

	
	public String getRssMonthReconAdj() {
		return rssMonthReconAdj;
	}

	public void setRssPycontrAdj(String rssPycontrAdj) {
		this.rssPycontrAdj = rssPycontrAdj;
	}

	
	public String getRssPycontrAdj() {
		return rssPycontrAdj;
	}

	public void setRssPyreconAdj(String rssPyreconAdj) {
		this.rssPyreconAdj = rssPyreconAdj;
	}

	
	public String getRssPyreconAdj() {
		return rssPyreconAdj;
	}

	public void setRssPyotherAdj(String rssPyotherAdj) {
		this.rssPyotherAdj = rssPyotherAdj;
	}

	
	public String getRssPyotherAdj() {
		return rssPyotherAdj;
	}

	public void setRssMemo(String rssMemo) {
		this.rssMemo = rssMemo;
	}

	
	public String getRssMemo() {
		return rssMemo;
	}

	public void setRssTotal(String rssTotal) {
		this.rssTotal = rssTotal;
	}

	
	public String getRssTotal() {
		return rssTotal;
	}

	public void setFps(String fps) {
		this.fps = fps;
	}

	
	public String getFps() {
		return fps;
	}

	public void setFpsValue(String fpsValue) {
		this.fpsValue = fpsValue;
	}

	
	public String getFpsValue() {
		return fpsValue;
	}

	public void setFpsStoreCount(String fpsStoreCount) {
		this.fpsStoreCount = fpsStoreCount;
	}

	
	public String getFpsStoreCount() {
		return fpsStoreCount;
	}

	public void setFpsSum(String fpsSum) {
		this.fpsSum = fpsSum;
	}

	
	public String getFpsSum() {
		return fpsSum;
	}

	public void setFpsMonthContrAdj(String fpsMonthContrAdj) {
		this.fpsMonthContrAdj = fpsMonthContrAdj;
	}

	
	public String getFpsMonthContrAdj() {
		return fpsMonthContrAdj;
	}

	public void setFpsMonthReconAdj(String fpsMonthReconAdj) {
		this.fpsMonthReconAdj = fpsMonthReconAdj;
	}

	
	public String getFpsMonthReconAdj() {
		return fpsMonthReconAdj;
	}

	public void setFpsPycontrAdj(String fpsPycontrAdj) {
		this.fpsPycontrAdj = fpsPycontrAdj;
	}

	
	public String getFpsPycontrAdj() {
		return fpsPycontrAdj;
	}

	public void setFpsPyreconAdj(String fpsPyreconAdj) {
		this.fpsPyreconAdj = fpsPyreconAdj;
	}

	
	public String getFpsPyreconAdj() {
		return fpsPyreconAdj;
	}

	public void setFpsPyotherAdj(String fpsPyotherAdj) {
		this.fpsPyotherAdj = fpsPyotherAdj;
	}

	
	public String getFpsPyotherAdj() {
		return fpsPyotherAdj;
	}

	public void setFpsMemo(String fpsMemo) {
		this.fpsMemo = fpsMemo;
	}

	
	public String getFpsMemo() {
		return fpsMemo;
	}

	public void setFpsTotal(String fpsTotal) {
		this.fpsTotal = fpsTotal;
	}

	
	public String getFpsTotal() {
		return fpsTotal;
	}

	public void setWomdp(String womdp) {
		this.womdp = womdp;
	}

	
	public String getWomdp() {
		return womdp;
	}

	public void setWomdpValue(String womdpValue) {
		this.womdpValue = womdpValue;
	}

	
	public String getWomdpValue() {
		return womdpValue;
	}

	public void setWomdpStoreCount(String womdpStoreCount) {
		this.womdpStoreCount = womdpStoreCount;
	}

	
	public String getWomdpStoreCount() {
		return womdpStoreCount;
	}

	public void setWomdpSum(String womdpSum) {
		this.womdpSum = womdpSum;
	}

	
	public String getWomdpSum() {
		return womdpSum;
	}

	public void setWomdpMonthContrAdj(String womdpMonthContrAdj) {
		this.womdpMonthContrAdj = womdpMonthContrAdj;
	}

	
	public String getWomdpMonthContrAdj() {
		return womdpMonthContrAdj;
	}

	public void setWomdpMonthReconAdj(String womdpMonthReconAdj) {
		this.womdpMonthReconAdj = womdpMonthReconAdj;
	}

	
	public String getWomdpMonthReconAdj() {
		return womdpMonthReconAdj;
	}

	public void setWomdpPycontrAdj(String womdpPycontrAdj) {
		this.womdpPycontrAdj = womdpPycontrAdj;
	}

	
	public String getWomdpPycontrAdj() {
		return womdpPycontrAdj;
	}

	public void setWomdpPyreconAdj(String womdpPyreconAdj) {
		this.womdpPyreconAdj = womdpPyreconAdj;
	}

	
	public String getWomdpPyreconAdj() {
		return womdpPyreconAdj;
	}

	public void setWomdpPyotherAdj(String womdpPyotherAdj) {
		this.womdpPyotherAdj = womdpPyotherAdj;
	}

	
	public String getWomdpPyotherAdj() {
		return womdpPyotherAdj;
	}

	public void setWomdpMemo(String womdpMemo) {
		this.womdpMemo = womdpMemo;
	}

	
	public String getWomdpMemo() {
		return womdpMemo;
	}

	public void setWomdpTotal(String womdpTotal) {
		this.womdpTotal = womdpTotal;
	}

	
	public String getWomdpTotal() {
		return womdpTotal;
	}

	public void setNpmf(String npmf) {
		this.npmf = npmf;
	}

	
	public String getNpmf() {
		return npmf;
	}

	public void setNpmfValue(String npmfValue) {
		this.npmfValue = npmfValue;
	}

	
	public String getNpmfValue() {
		return npmfValue;
	}

	public void setNpmfStoreCount(String npmfStoreCount) {
		this.npmfStoreCount = npmfStoreCount;
	}

	
	public String getNpmfStoreCount() {
		return npmfStoreCount;
	}

	public void setNpmfStoreData(String npmfStoreData) {
		this.npmfStoreData = npmfStoreData;
	}

	
	public String getNpmfStoreData() {
		return npmfStoreData;
	}

	public void setNpmfSum(String npmfSum) {
		this.npmfSum = npmfSum;
	}

	
	public String getNpmfSum() {
		return npmfSum;
	}

	public void setNpmfMonthContrAdj(String npmfMonthContrAdj) {
		this.npmfMonthContrAdj = npmfMonthContrAdj;
	}

	
	public String getNpmfMonthContrAdj() {
		return npmfMonthContrAdj;
	}

	public void setNpmfMonthReconAdj(String npmfMonthReconAdj) {
		this.npmfMonthReconAdj = npmfMonthReconAdj;
	}

	
	public String getNpmfMonthReconAdj() {
		return npmfMonthReconAdj;
	}

	public void setNpmfPycontrAdj(String npmfPycontrAdj) {
		this.npmfPycontrAdj = npmfPycontrAdj;
	}

	
	public String getNpmfPycontrAdj() {
		return npmfPycontrAdj;
	}

	public void setNpmfPyreconAdj(String npmfPyreconAdj) {
		this.npmfPyreconAdj = npmfPyreconAdj;
	}

	
	public String getNpmfPyreconAdj() {
		return npmfPyreconAdj;
	}

	public void setNpmfPyotherAdj(String npmfPyotherAdj) {
		this.npmfPyotherAdj = npmfPyotherAdj;
	}

	
	public String getNpmfPyotherAdj() {
		return npmfPyotherAdj;
	}

	public void setNpmfMemo(String npmfMemo) {
		this.npmfMemo = npmfMemo;
	}

	
	public String getNpmfMemo() {
		return npmfMemo;
	}

	public void setNpmfTotal(String npmfTotal) {
		this.npmfTotal = npmfTotal;
	}

	
	public String getNpmfTotal() {
		return npmfTotal;
	}

	public void setRgts(String rgts) {
		this.rgts = rgts;
	}

	
	public String getRgts() {
		return rgts;
	}

	public void setRgtsValue(String rgtsValue) {
		this.rgtsValue = rgtsValue;
	}

	
	public String getRgtsValue() {
		return rgtsValue;
	}

	public void setRgtsPur(String rgtsPur) {
		this.rgtsPur = rgtsPur;
	}

	
	public String getRgtsPur() {
		return rgtsPur;
	}

	public void setRgtsMonthContrAdj(String rgtsMonthContrAdj) {
		this.rgtsMonthContrAdj = rgtsMonthContrAdj;
	}

	
	public String getRgtsMonthContrAdj() {
		return rgtsMonthContrAdj;
	}

	public void setRgtsMonthReconAdj(String rgtsMonthReconAdj) {
		this.rgtsMonthReconAdj = rgtsMonthReconAdj;
	}

	
	public String getRgtsMonthReconAdj() {
		return rgtsMonthReconAdj;
	}

	public void setRgtsPycontrAdj(String rgtsPycontrAdj) {
		this.rgtsPycontrAdj = rgtsPycontrAdj;
	}

	
	public String getRgtsPycontrAdj() {
		return rgtsPycontrAdj;
	}

	public void setRgtsPyreconAdj(String rgtsPyreconAdj) {
		this.rgtsPyreconAdj = rgtsPyreconAdj;
	}

	
	public String getRgtsPyreconAdj() {
		return rgtsPyreconAdj;
	}

	public void setRgtsPyotherAdj(String rgtsPyotherAdj) {
		this.rgtsPyotherAdj = rgtsPyotherAdj;
	}

	
	public String getRgtsPyotherAdj() {
		return rgtsPyotherAdj;
	}

	public void setRgtsMemo(String rgtsMemo) {
		this.rgtsMemo = rgtsMemo;
	}

	
	public String getRgtsMemo() {
		return rgtsMemo;
	}

	public void setRgtsTotal(String rgtsTotal) {
		this.rgtsTotal = rgtsTotal;
	}

	
	public String getRgtsTotal() {
		return rgtsTotal;
	}

	public void setLpup(String lpup) {
		this.lpup = lpup;
	}

	
	public String getLpup() {
		return lpup;
	}

	public void setLpupValue(String lpupValue) {
		this.lpupValue = lpupValue;
	}

	
	public String getLpupValue() {
		return lpupValue;
	}

	public void setLpupSum(String lpupSum) {
		this.lpupSum = lpupSum;
	}

	
	public String getLpupSum() {
		return lpupSum;
	}

	public void setLpupMonthContrAdj(String lpupMonthContrAdj) {
		this.lpupMonthContrAdj = lpupMonthContrAdj;
	}

	
	public String getLpupMonthContrAdj() {
		return lpupMonthContrAdj;
	}

	public void setLpupMonthReconAdj(String lpupMonthReconAdj) {
		this.lpupMonthReconAdj = lpupMonthReconAdj;
	}

	
	public String getLpupMonthReconAdj() {
		return lpupMonthReconAdj;
	}

	public void setLpupPycontrAdj(String lpupPycontrAdj) {
		this.lpupPycontrAdj = lpupPycontrAdj;
	}

	
	public String getLpupPycontrAdj() {
		return lpupPycontrAdj;
	}

	public void setLpupPyreconAdj(String lpupPyreconAdj) {
		this.lpupPyreconAdj = lpupPyreconAdj;
	}

	
	public String getLpupPyreconAdj() {
		return lpupPyreconAdj;
	}

	public void setLpupPyotherAdj(String lpupPyotherAdj) {
		this.lpupPyotherAdj = lpupPyotherAdj;
	}

	
	public String getLpupPyotherAdj() {
		return lpupPyotherAdj;
	}

	public void setLpupMemo(String lpupMemo) {
		this.lpupMemo = lpupMemo;
	}

	
	public String getLpupMemo() {
		return lpupMemo;
	}

	public void setLpupTotal(String lpupTotal) {
		this.lpupTotal = lpupTotal;
	}

	
	public String getLpupTotal() {
		return lpupTotal;
	}

	public void setOcrf(String ocrf) {
		this.ocrf = ocrf;
	}

	
	public String getOcrf() {
		return ocrf;
	}

	public void setOcrfMonthContrAdj(String ocrfMonthContrAdj) {
		this.ocrfMonthContrAdj = ocrfMonthContrAdj;
	}

	
	public String getOcrfMonthContrAdj() {
		return ocrfMonthContrAdj;
	}

	public void setOcrfMonthReconAdj(String ocrfMonthReconAdj) {
		this.ocrfMonthReconAdj = ocrfMonthReconAdj;
	}

	
	public String getOcrfMonthReconAdj() {
		return ocrfMonthReconAdj;
	}

	public void setOcrfPycontraAdj(String ocrfPycontraAdj) {
		this.ocrfPycontraAdj = ocrfPycontraAdj;
	}

	
	public String getOcrfPycontraAdj() {
		return ocrfPycontraAdj;
	}

	public void setOcrfPyreconAdj(String ocrfPyreconAdj) {
		this.ocrfPyreconAdj = ocrfPyreconAdj;
	}

	
	public String getOcrfPyreconAdj() {
		return ocrfPyreconAdj;
	}

	public void setOcrfPyotherAdj(String ocrfPyotherAdj) {
		this.ocrfPyotherAdj = ocrfPyotherAdj;
	}

	
	public String getOcrfPyotherAdj() {
		return ocrfPyotherAdj;
	}

	public void setOcrfMemo(String ocrfMemo) {
		this.ocrfMemo = ocrfMemo;
	}

	
	public String getOcrfMemo() {
		return ocrfMemo;
	}

	public void setOcrfTotal(String ocrfTotal) {
		this.ocrfTotal = ocrfTotal;
	}

	
	public String getOcrfTotal() {
		return ocrfTotal;
	}

	public void setComin(String comin) {
		this.comin = comin;
	}

	
	public String getComin() {
		return comin;
	}

	public void setCominValue(String cominValue) {
		this.cominValue = cominValue;
	}

	
	public String getCominValue() {
		return cominValue;
	}

	public void setCominStoreCount(String cominStoreCount) {
		this.cominStoreCount = cominStoreCount;
	}

	
	public String getCominStoreCount() {
		return cominStoreCount;
	}

	public void setCominSum(String cominSum) {
		this.cominSum = cominSum;
	}

	
	public String getCominSum() {
		return cominSum;
	}

	public void setCominMonthContrAdj(String cominMonthContrAdj) {
		this.cominMonthContrAdj = cominMonthContrAdj;
	}

	
	public String getCominMonthContrAdj() {
		return cominMonthContrAdj;
	}

	public void setCominMonthReconAdj(String cominMonthReconAdj) {
		this.cominMonthReconAdj = cominMonthReconAdj;
	}

	
	public String getCominMonthReconAdj() {
		return cominMonthReconAdj;
	}

	public void setCominPyContrAdj(String cominPyContrAdj) {
		this.cominPyContrAdj = cominPyContrAdj;
	}

	
	public String getCominPyContrAdj() {
		return cominPyContrAdj;
	}

	public void setCominPyReconAdj(String cominPyReconAdj) {
		this.cominPyReconAdj = cominPyReconAdj;
	}

	
	public String getCominPyReconAdj() {
		return cominPyReconAdj;
	}

	public void setCominPyOtherAdj(String cominPyOtherAdj) {
		this.cominPyOtherAdj = cominPyOtherAdj;
	}

	
	public String getCominPyOtherAdj() {
		return cominPyOtherAdj;
	}

	public void setCominMemo(String cominMemo) {
		this.cominMemo = cominMemo;
	}

	
	public String getCominMemo() {
		return cominMemo;
	}

	public void setCominTotal(String cominTotal) {
		this.cominTotal = cominTotal;
	}

	
	public String getCominTotal() {
		return cominTotal;
	}

	public void setPsfvt(String psfvt) {
		this.psfvt = psfvt;
	}

	
	public String getPsfvt() {
		return psfvt;
	}

	public void setPsfvtValue(String psfvtValue) {
		this.psfvtValue = psfvtValue;
	}

	
	public String getPsfvtValue() {
		return psfvtValue;
	}

	public void setPsfvtSum(String psfvtSum) {
		this.psfvtSum = psfvtSum;
	}

	
	public String getPsfvtSum() {
		return psfvtSum;
	}

	public void setPsfvtMonthContrAdj(String psfvtMonthContrAdj) {
		this.psfvtMonthContrAdj = psfvtMonthContrAdj;
	}

	
	public String getPsfvtMonthContrAdj() {
		return psfvtMonthContrAdj;
	}

	public void setPsfvtMonthReconAdj(String psfvtMonthReconAdj) {
		this.psfvtMonthReconAdj = psfvtMonthReconAdj;
	}

	
	public String getPsfvtMonthReconAdj() {
		return psfvtMonthReconAdj;
	}

	public void setPsfvtPyContrAdj(String psfvtPyContrAdj) {
		this.psfvtPyContrAdj = psfvtPyContrAdj;
	}

	
	public String getPsfvtPyContrAdj() {
		return psfvtPyContrAdj;
	}

	public void setPsfvtPyReconAdj(String psfvtPyReconAdj) {
		this.psfvtPyReconAdj = psfvtPyReconAdj;
	}

	
	public String getPsfvtPyReconAdj() {
		return psfvtPyReconAdj;
	}

	public void setPsfvtPyOtherAdj(String psfvtPyOtherAdj) {
		this.psfvtPyOtherAdj = psfvtPyOtherAdj;
	}

	
	public String getPsfvtPyOtherAdj() {
		return psfvtPyOtherAdj;
	}

	public void setPsfvtMemo(String psfvtMemo) {
		this.psfvtMemo = psfvtMemo;
	}

	
	public String getPsfvtMemo() {
		return psfvtMemo;
	}

	public void setPsfvtTotal(String psfvtTotal) {
		this.psfvtTotal = psfvtTotal;
	}

	
	public String getPsfvtTotal() {
		return psfvtTotal;
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

	public void setNssPurchaseSum(String nssPurchaseSum) {
		this.nssPurchaseSum = nssPurchaseSum;
	}

	
	public String getNssPurchaseSum() {
		return nssPurchaseSum;
	}

	public void setRssPurchaseSum(String rssPurchaseSum) {
		this.rssPurchaseSum = rssPurchaseSum;
	}

	
	public String getRssPurchaseSum() {
		return rssPurchaseSum;
	}

	public void setFpsPurchaseSum(String fpsPurchaseSum) {
		this.fpsPurchaseSum = fpsPurchaseSum;
	}

	
	public String getFpsPurchaseSum() {
		return fpsPurchaseSum;
	}

	public void setWomdpPurchaseSum(String womdpPurchaseSum) {
		this.womdpPurchaseSum = womdpPurchaseSum;
	}

	
	public String getWomdpPurchaseSum() {
		return womdpPurchaseSum;
	}

	public void setNpmfPurchaseSum(String npmfPurchaseSum) {
		this.npmfPurchaseSum = npmfPurchaseSum;
	}

	
	public String getNpmfPurchaseSum() {
		return npmfPurchaseSum;
	}

	public void setIstd(String istd) {
		this.istd = istd;
	}

	
	public String getIstd() {
		return istd;
	}

	public void setIstdValue(String istdValue) {
		this.istdValue = istdValue;
	}

	
	public String getIstdValue() {
		return istdValue;
	}

	public void setIstdStoreCount(String istdStoreCount) {
		this.istdStoreCount = istdStoreCount;
	}

	
	public String getIstdStoreCount() {
		return istdStoreCount;
	}

	public void setIstdSum(String istdSum) {
		this.istdSum = istdSum;
	}

	
	public String getIstdSum() {
		return istdSum;
	}

	public void setIstdPurchaseSum(String istdPurchaseSum) {
		this.istdPurchaseSum = istdPurchaseSum;
	}

	
	public String getIstdPurchaseSum() {
		return istdPurchaseSum;
	}

	public void setIstdMonthContrAdj(String istdMonthContrAdj) {
		this.istdMonthContrAdj = istdMonthContrAdj;
	}

	
	public String getIstdMonthContrAdj() {
		return istdMonthContrAdj;
	}

	public void setIstdMonthReconAdj(String istdMonthReconAdj) {
		this.istdMonthReconAdj = istdMonthReconAdj;
	}

	
	public String getIstdMonthReconAdj() {
		return istdMonthReconAdj;
	}

	public void setIstdPycontrAdj(String istdPycontrAdj) {
		this.istdPycontrAdj = istdPycontrAdj;
	}

	
	public String getIstdPycontrAdj() {
		return istdPycontrAdj;
	}

	public void setIstdPyreconAdj(String istdPyreconAdj) {
		this.istdPyreconAdj = istdPyreconAdj;
	}

	
	public String getIstdPyreconAdj() {
		return istdPyreconAdj;
	}

	public void setIstdPyotherAdj(String istdPyotherAdj) {
		this.istdPyotherAdj = istdPyotherAdj;
	}

	
	public String getIstdPyotherAdj() {
		return istdPyotherAdj;
	}

	public void setIstdMemo(String istdMemo) {
		this.istdMemo = istdMemo;
	}

	
	public String getIstdMemo() {
		return istdMemo;
	}

	public void setIstdTotal(String istdTotal) {
		this.istdTotal = istdTotal;
	}

	
	public String getIstdTotal() {
		return istdTotal;
	}

	public void setNsod(String nsod) {
		this.nsod = nsod;
	}

	
	public String getNsod() {
		return nsod;
	}

	public void setNsodValue(String nsodValue) {
		this.nsodValue = nsodValue;
	}

	
	public String getNsodValue() {
		return nsodValue;
	}

	public void setNsodStoreCount(String nsodStoreCount) {
		this.nsodStoreCount = nsodStoreCount;
	}

	
	public String getNsodStoreCount() {
		return nsodStoreCount;
	}

	public void setNsodSum(String nsodSum) {
		this.nsodSum = nsodSum;
	}

	
	public String getNsodSum() {
		return nsodSum;
	}

	public void setNsodPurchaseSum(String nsodPurchaseSum) {
		this.nsodPurchaseSum = nsodPurchaseSum;
	}

	
	public String getNsodPurchaseSum() {
		return nsodPurchaseSum;
	}

	public void setNsodMonthContrAdj(String nsodMonthContrAdj) {
		this.nsodMonthContrAdj = nsodMonthContrAdj;
	}

	
	public String getNsodMonthContrAdj() {
		return nsodMonthContrAdj;
	}

	public void setNsodMonthReconAdj(String nsodMonthReconAdj) {
		this.nsodMonthReconAdj = nsodMonthReconAdj;
	}

	
	public String getNsodMonthReconAdj() {
		return nsodMonthReconAdj;
	}

	public void setNsodPycontrAdj(String nsodPycontrAdj) {
		this.nsodPycontrAdj = nsodPycontrAdj;
	}

	
	public String getNsodPycontrAdj() {
		return nsodPycontrAdj;
	}

	public void setNsodPyreconAdj(String nsodPyreconAdj) {
		this.nsodPyreconAdj = nsodPyreconAdj;
	}

	
	public String getNsodPyreconAdj() {
		return nsodPyreconAdj;
	}

	public void setNsodPyotherAdj(String nsodPyotherAdj) {
		this.nsodPyotherAdj = nsodPyotherAdj;
	}

	
	public String getNsodPyotherAdj() {
		return nsodPyotherAdj;
	}

	public void setNsodMemo(String nsodMemo) {
		this.nsodMemo = nsodMemo;
	}

	
	public String getNsodMemo() {
		return nsodMemo;
	}

	public void setNsodTotal(String nsodTotal) {
		this.nsodTotal = nsodTotal;
	}

	
	public String getNsodTotal() {
		return nsodTotal;
	}

	public void setOicn(String oicn) {
		this.oicn = oicn;
	}

	
	public String getOicn() {
		return oicn;
	}

	public void setOicnValue(String oicnValue) {
		this.oicnValue = oicnValue;
	}

	
	public String getOicnValue() {
		return oicnValue;
	}

	public void setOicnStoreCount(String oicnStoreCount) {
		this.oicnStoreCount = oicnStoreCount;
	}

	
	public String getOicnStoreCount() {
		return oicnStoreCount;
	}

	public void setOicnSum(String oicnSum) {
		this.oicnSum = oicnSum;
	}

	
	public String getOicnSum() {
		return oicnSum;
	}

	public void setOicnYearSumTta(String oicnYearSumTta) {
		this.oicnYearSumTta = oicnYearSumTta;
	}

	
	public String getOicnYearSumTta() {
		return oicnYearSumTta;
	}

	public void setOicnYearSumOntop(String oicnYearSumOntop) {
		this.oicnYearSumOntop = oicnYearSumOntop;
	}

	
	public String getOicnYearSumOntop() {
		return oicnYearSumOntop;
	}

	public void setOicnMonthCount(String oicnMonthCount) {
		this.oicnMonthCount = oicnMonthCount;
	}

	
	public String getOicnMonthCount() {
		return oicnMonthCount;
	}

	public void setOicnMonthContrAdj(String oicnMonthContrAdj) {
		this.oicnMonthContrAdj = oicnMonthContrAdj;
	}

	
	public String getOicnMonthContrAdj() {
		return oicnMonthContrAdj;
	}

	public void setOicnMonthReconAdj(String oicnMonthReconAdj) {
		this.oicnMonthReconAdj = oicnMonthReconAdj;
	}

	
	public String getOicnMonthReconAdj() {
		return oicnMonthReconAdj;
	}

	public void setOicnPycontrAdj(String oicnPycontrAdj) {
		this.oicnPycontrAdj = oicnPycontrAdj;
	}

	
	public String getOicnPycontrAdj() {
		return oicnPycontrAdj;
	}

	public void setOicnPyreconAdj(String oicnPyreconAdj) {
		this.oicnPyreconAdj = oicnPyreconAdj;
	}

	
	public String getOicnPyreconAdj() {
		return oicnPyreconAdj;
	}

	public void setOicnPyotherAdj(String oicnPyotherAdj) {
		this.oicnPyotherAdj = oicnPyotherAdj;
	}

	
	public String getOicnPyotherAdj() {
		return oicnPyotherAdj;
	}

	public void setOicnMemo(String oicnMemo) {
		this.oicnMemo = oicnMemo;
	}

	
	public String getOicnMemo() {
		return oicnMemo;
	}

	public void setOicnTotal(String oicnTotal) {
		this.oicnTotal = oicnTotal;
	}

	
	public String getOicnTotal() {
		return oicnTotal;
	}

	public void setOiob(String oiob) {
		this.oiob = oiob;
	}

	
	public String getOiob() {
		return oiob;
	}

	public void setOiobValue(String oiobValue) {
		this.oiobValue = oiobValue;
	}

	
	public String getOiobValue() {
		return oiobValue;
	}

	public void setOiobStoreCount(String oiobStoreCount) {
		this.oiobStoreCount = oiobStoreCount;
	}

	
	public String getOiobStoreCount() {
		return oiobStoreCount;
	}

	public void setOiobSum(String oiobSum) {
		this.oiobSum = oiobSum;
	}

	
	public String getOiobSum() {
		return oiobSum;
	}

	public void setOiobYearSumTta(String oiobYearSumTta) {
		this.oiobYearSumTta = oiobYearSumTta;
	}

	
	public String getOiobYearSumTta() {
		return oiobYearSumTta;
	}

	public void setOiobYearSumOntop(String oiobYearSumOntop) {
		this.oiobYearSumOntop = oiobYearSumOntop;
	}

	
	public String getOiobYearSumOntop() {
		return oiobYearSumOntop;
	}

	public void setOiobMonthCount(String oiobMonthCount) {
		this.oiobMonthCount = oiobMonthCount;
	}

	
	public String getOiobMonthCount() {
		return oiobMonthCount;
	}

	public void setOiobMonthContrAdj(String oiobMonthContrAdj) {
		this.oiobMonthContrAdj = oiobMonthContrAdj;
	}

	
	public String getOiobMonthContrAdj() {
		return oiobMonthContrAdj;
	}

	public void setOiobMonthReconAdj(String oiobMonthReconAdj) {
		this.oiobMonthReconAdj = oiobMonthReconAdj;
	}

	
	public String getOiobMonthReconAdj() {
		return oiobMonthReconAdj;
	}

	public void setOiobPycontrAdj(String oiobPycontrAdj) {
		this.oiobPycontrAdj = oiobPycontrAdj;
	}

	
	public String getOiobPycontrAdj() {
		return oiobPycontrAdj;
	}

	public void setOiobPyreconAdj(String oiobPyreconAdj) {
		this.oiobPyreconAdj = oiobPyreconAdj;
	}

	
	public String getOiobPyreconAdj() {
		return oiobPyreconAdj;
	}

	public void setOiobPyotherAdj(String oiobPyotherAdj) {
		this.oiobPyotherAdj = oiobPyotherAdj;
	}

	
	public String getOiobPyotherAdj() {
		return oiobPyotherAdj;
	}

	public void setOiobMemo(String oiobMemo) {
		this.oiobMemo = oiobMemo;
	}

	
	public String getOiobMemo() {
		return oiobMemo;
	}

	public void setLpupStoreValue(String lpupStoreValue) {
		this.lpupStoreValue = lpupStoreValue;
	}

	
	public String getLpupStoreValue() {
		return lpupStoreValue;
	}

	public void setLpupPurchaseSum(String lpupPurchaseSum) {
		this.lpupPurchaseSum = lpupPurchaseSum;
	}

	
	public String getLpupPurchaseSum() {
		return lpupPurchaseSum;
	}

	public void setOlpd(String olpd) {
		this.olpd = olpd;
	}

	
	public String getOlpd() {
		return olpd;
	}

	public void setOlpdValue(String olpdValue) {
		this.olpdValue = olpdValue;
	}

	
	public String getOlpdValue() {
		return olpdValue;
	}

	public void setOlpdStoreValue(String olpdStoreValue) {
		this.olpdStoreValue = olpdStoreValue;
	}

	
	public String getOlpdStoreValue() {
		return olpdStoreValue;
	}

	public void setOlpdSum(String olpdSum) {
		this.olpdSum = olpdSum;
	}

	
	public String getOlpdSum() {
		return olpdSum;
	}

	public void setOlpdPurchaseSum(String olpdPurchaseSum) {
		this.olpdPurchaseSum = olpdPurchaseSum;
	}

	
	public String getOlpdPurchaseSum() {
		return olpdPurchaseSum;
	}

	public void setOlpdMonthContrAdj(String olpdMonthContrAdj) {
		this.olpdMonthContrAdj = olpdMonthContrAdj;
	}

	
	public String getOlpdMonthContrAdj() {
		return olpdMonthContrAdj;
	}

	public void setOlpdMonthReconAdj(String olpdMonthReconAdj) {
		this.olpdMonthReconAdj = olpdMonthReconAdj;
	}

	
	public String getOlpdMonthReconAdj() {
		return olpdMonthReconAdj;
	}

	public void setOlpdPycontrAdj(String olpdPycontrAdj) {
		this.olpdPycontrAdj = olpdPycontrAdj;
	}

	
	public String getOlpdPycontrAdj() {
		return olpdPycontrAdj;
	}

	public void setOlpdPyreconAdj(String olpdPyreconAdj) {
		this.olpdPyreconAdj = olpdPyreconAdj;
	}

	
	public String getOlpdPyreconAdj() {
		return olpdPyreconAdj;
	}

	public void setOlpdPyotherAdj(String olpdPyotherAdj) {
		this.olpdPyotherAdj = olpdPyotherAdj;
	}

	
	public String getOlpdPyotherAdj() {
		return olpdPyotherAdj;
	}

	public void setOlpdMemo(String olpdMemo) {
		this.olpdMemo = olpdMemo;
	}

	
	public String getOlpdMemo() {
		return olpdMemo;
	}

	public void setOlpdTotal(String olpdTotal) {
		this.olpdTotal = olpdTotal;
	}

	
	public String getOlpdTotal() {
		return olpdTotal;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
