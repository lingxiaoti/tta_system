package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaSroiBillLineEntity_HI Entity Object
 * Mon Oct 14 17:32:39 CST 2019  Auto Generate
 */

public class TtaSroiBillLineEntity_HI_MODEL {

	@ExcelIgnore
	private Integer ttaSroiBillImportId;

	@ExcelProperty(value ="年月")
	private Date accountMonth;

	@ExcelProperty(value ="供应商编号")
	private String rmsCode;

	@ExcelProperty(value ="供应商名称")
	private String venderName;

	@ExcelProperty(value ="JV")
	private String bookInJv;

	@ExcelProperty(value ="供应商编号+JV")
	private String bookInJv1;

	@ExcelProperty(value ="采购")
	private String buyer;

	@ExcelProperty(value ="TC采购总监")
	private String tc;

	@ExcelProperty(value ="部门")
	private String department;

	@ExcelProperty(value ="品牌")
	private String brand;

	@ExcelProperty(value ="供应商属性")
	private String venderab;

	@ExcelProperty(value ="合同编号")
	private String userContractId;

	@ExcelProperty(value ="合作状态")
	private String cooperateStatus;

	@ExcelProperty(value ="供应商类型")
	private String venderType;

	@ExcelProperty(value ="计生供应商")
	private String familyPlaningFlag;

	@ExcelProperty(value ="合同有效期开始日期")
	private Date validBeginDate;

	@ExcelProperty(value ="合同有效期结束日期")
	private Date validEndDate;

	@ExcelProperty(value ="购货折扣比例")
	private String pickUpRate;

	@ExcelProperty(value ="购货折扣比例是否含税")
	private String venderContainTax;

	@ExcelProperty(value ="monthlyNetPurchase(本月净采购额)折扣前")
	private String netPurchase;

	@ExcelProperty(value ="monthlyNetPurchase(本月净采购额)折扣后")
	private String netPurchaseOrigin;

	@ExcelProperty(value ="本月采购额计算")
	private String monthPur;

	@ExcelProperty(value ="本月计算")
	private String monthCount;

	@ExcelProperty(value ="本月计算-ABI(TTA)")
	private String monthAbi;

	@ExcelProperty(value ="本月计算-ABI(Ontop)")
	private String monthAbiOntop;

	@ExcelProperty(value ="以前年度计算-ABI(TTA)")
	private String yearAbi;

	@ExcelProperty(value ="以前年度计算-ABI(Ontop)")
	private String yearAbiOntop;

	@ExcelProperty(value ="本月合同调整")
	private String contractAdj;

	@ExcelProperty(value ="本月对帐调整")
	private String billAdj;

	@ExcelProperty(value ="以前年度合同调整")
	private String yearContractAdj;

	@ExcelProperty(value ="以前年度对帐调整")
	private String yearBillAdj;

	@ExcelProperty(value ="以前年度采购额计算/PRG补收/自查补收")
	private String yearPurchaseCount;

	@ExcelProperty(value ="APOITotal(含税)")
	private String apOiTotalTax;

	@ExcelProperty(value ="APOITotal(不含税-VAT)")
	private String apOiTotalNotax;

	@ExcelProperty(value ="税金（6%VAT)")
	private String tax6;

	@ExcelProperty(value ="条款-新店宣传推广服务费")
	private String nss;

	@ExcelProperty(value ="条款(%)-新店宣传推广服务费")
	private String nssValue;

	@ExcelProperty(value ="店铺数量-新店宣传推广服务费")
	private String nssStoreCount;

	@ExcelProperty(value ="店铺资料-新店宣传推广服务费")
	private String nssStoreData;
	@ExcelProperty(value ="本月计算-新店宣传推广服务费")
	private String nssSum;
	@ExcelProperty(value ="本月合同调整-新店宣传推广服务费")
	private String nssMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-新店宣传推广服务费")
	private String nssMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-新店宣传推广服务费")
	private String nssPycontrAdj;
	@ExcelProperty(value ="以前年度对帐调整-新店宣传推广服务费")
	private String nssPyreconAdj;
	@ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-新店宣传推广服务费")
	private String nssPyotherAdj;
	@ExcelProperty(value ="备注-新店宣传推广服务费")
	private String nssMemo;
	@ExcelProperty(value ="新店宣传推广服务费total")
	private String nssTotal;
	@ExcelProperty(value ="条款-店铺升级推广服务费")
	private String rss;
	@ExcelProperty(value ="条款(%)-店铺升级推广服务费")
	private String rssValue;
	@ExcelProperty(value ="店铺数量-店铺升级推广服务费")
	private String rssStoreCount;
	@ExcelProperty(value ="店铺资料-店铺升级推广服务费")
	private String rssStoreData;
	@ExcelProperty(value ="本月计算-店铺升级推广服务费")
	private String rssSum;
	@ExcelProperty(value ="本月合同调整-店铺升级推广服务费")
	private String rssMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-店铺升级推广服务费")
	private String rssMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-店铺升级推广服务费")
	private String rssPycontrAdj;
	@ExcelProperty(value ="以前年度对帐调整-店铺升级推广服务费")
	private String rssPyreconAdj;
	@ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-店铺升级推广服务费")
	private String rssPyotherAdj;
	@ExcelProperty(value ="备注-店铺升级推广服务费")
	private String rssMemo;
	@ExcelProperty(value ="店铺升级推广服务费Total")
	private String rssTotal;
	@ExcelProperty(value ="条款-节庆促销服务费")
	private String fps;
	@ExcelProperty(value ="条款(%)-节庆促销服务费")
	private String fpsValue;
	@ExcelProperty(value ="店铺数量-节庆促销服务费")
	private String fpsStoreCount;
	@ExcelProperty(value ="本月计算-节庆促销服务费")
	private String fpsSum;
	@ExcelProperty(value ="本月合同调整-节庆促销服务费")
	private String fpsMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-节庆促销服务费")
	private String fpsMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-节庆促销服务费")
	private String fpsPycontrAdj;
	@ExcelProperty(value ="以前年度对帐调整-节庆促销服务费")
	private String fpsPyreconAdj;
	@ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-节庆促销服务费")
	private String fpsPyotherAdj;
	@ExcelProperty(value ="备注-节庆促销服务费")
	private String fpsMemo;
	@ExcelProperty(value ="节庆促销服务费Total")
	private String fpsTotal;
	@ExcelProperty(value ="条款-节庆促销服务费（38妇女节）")
	private String womdp;
	@ExcelProperty(value ="条款(%)-节庆促销服务费（38妇女节）")
	private String womdpValue;
	@ExcelProperty(value ="店铺数量-节庆促销服务费（38妇女节）")
	private String womdpStoreCount;
	@ExcelProperty(value ="本月计算-节庆促销服务费（38妇女节）")
	private String womdpSum;
	@ExcelProperty(value ="本月合同调整-节庆促销服务费（38妇女节）")
	private String womdpMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-节庆促销服务费（38妇女节）")
	private String womdpMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-节庆促销服务费（38妇女节）")
	private String womdpPycontrAdj;
	@ExcelProperty(value ="以前年度对帐调整-节庆促销服务费（38妇女节）")
	private String womdpPyreconAdj;
	@ExcelProperty(value ="以前年度PRG调整/自查调整-节庆促销服务费（38妇女节）")
	private String womdpPyotherAdj;
	@ExcelProperty(value ="备注Women'sdaypromotionsupport-节庆促销服务费（38妇女节）")
	private String womdpMemo;
	@ExcelProperty(value ="节庆促销服务费（38妇女节）Total")
	private String womdpTotal;
	@ExcelProperty(value ="条款-新城市宣传推广服务费")
	private String npmf;
	@ExcelProperty(value ="条款(%)-新城市宣传推广服务费")
	private String npmfValue;
	@ExcelProperty(value ="店铺数量-新城市宣传推广服务费")
	private String npmfStoreCount;
	@ExcelProperty(value ="店铺资料-新城市宣传推广服务费")
	private String npmfStoreData;
	@ExcelProperty(value ="本月计算-新城市宣传推广服务费")
	private String npmfSum;
	@ExcelProperty(value ="本月合同调整-新城市宣传推广服务费")
	private String npmfMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-新城市宣传推广服务费")
	private String npmfMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-新城市宣传推广服务费")
	private String npmfPycontrAdj;
	@ExcelProperty(value ="以前年度对帐调整-新城市宣传推广服务费")
	private String npmfPyreconAdj;
	@ExcelProperty(value ="以前年度PRG调整/自查调整-新城市宣传推广服务费")
	private String npmfPyotherAdj;
	@ExcelProperty(value ="备注-新城市宣传推广服务费")
	private String npmfMemo;
	@ExcelProperty(value ="新城市宣传推广服务费Total")
	private String npmfTotal;
	@ExcelProperty(value ="条款-退货处理服务费")
	private String rgts;
	@ExcelProperty(value ="条款(%)-退货处理服务费")
	private String rgtsValue;
	@ExcelProperty(value ="本月采购额计算-退货处理服务费")
	private String rgtsPur;
	@ExcelProperty(value ="本月合同调整-退货处理服务费")
	private String rgtsMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-退货处理服务费")
	private String rgtsMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-退货处理服务费")
	private String rgtsPycontrAdj;
	@ExcelProperty(value ="以前年度对帐调整-退货处理服务费")
	private String rgtsPyreconAdj;
	@ExcelProperty(value ="以前年度调整-PRG&自查-退货处理服务费")
	private String rgtsPyotherAdj;
	@ExcelProperty(value ="备注-退货处理服务费")
	private String rgtsMemo;
	@ExcelProperty(value ="退货处理服务费Total")
	private String rgtsTotal;
	@ExcelProperty(value ="条款-延误退货罚款")
	private String lpup;
	@ExcelProperty(value ="条款(%)-延误退货罚款")
	private String lpupValue;
	@ExcelProperty(value ="本月计算-延误退货罚款")
	private String lpupSum;
	@ExcelProperty(value ="本月合同调整-延误退货罚款")
	private String lpupMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-延误退货罚款")
	private String lpupMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-延误退货罚款")
	private String lpupPycontrAdj;
	@ExcelProperty(value ="以前年度对帐调整-延误退货罚款")
	private String lpupPyreconAdj;
	@ExcelProperty(value ="以前年度调整-PRG&自查-延误退货罚款")
	private String lpupPyotherAdj;
	@ExcelProperty(value ="备注-延误退货罚款")
	private String lpupMemo;
	@ExcelProperty(value ="延误退货罚款Total")
	private String lpupTotal;
	@ExcelProperty(value ="本月计算-QA审查服务费")
	private String ocrf;
	@ExcelProperty(value ="本月合同调整-QA审查服务费")
	private String ocrfMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-QA审查服务费")
	private String ocrfMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-QA审查服务费")
	private String ocrfPycontraAdj;
	@ExcelProperty(value ="以前年度对帐调整-QA审查服务费")
	private String ocrfPyreconAdj;
	@ExcelProperty(value ="以前年度调整-PRG&自查-QA审查服务费")
	private String ocrfPyotherAdj;
	@ExcelProperty(value ="备注-QA审查服务费")
	private String ocrfMemo;
	@ExcelProperty(value ="QA审查服务费Total")
	private String ocrfTotal;
	@ExcelProperty(value ="条款-赔偿(补偿)收入")
	private String comin;
	@ExcelProperty(value ="条款(%)-赔偿(补偿)收入")
	private String cominValue;
	@ExcelProperty(value ="本月计算-赔偿(补偿)收入")
	private String cominStoreCount;
	@ExcelProperty(value ="本月采购额计算-赔偿(补偿)收入")
	private String cominSum;
	@ExcelProperty(value ="本月合同调整-赔偿(补偿)收入")
	private String cominMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-赔偿(补偿)收入")
	private String cominMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-赔偿(补偿)收入")
	private String cominPyContrAdj;
	@ExcelProperty(value ="以前年度对帐调整-赔偿(补偿)收入")
	private String cominPyReconAdj;
	@ExcelProperty(value ="以前年度调整-PRG&自查-赔偿(补偿)收入")
	private String cominPyOtherAdj;
	@ExcelProperty(value ="备注-赔偿(补偿)收入")
	private String cominMemo;
	@ExcelProperty(value ="赔偿(补偿)收入Total")
	private String cominTotal;
	@ExcelProperty(value ="条款-促销服务费(第三方)")
	private String psfvt;
	@ExcelProperty(value ="条款(%)-促销服务费(第三方)")
	private String psfvtValue;
	@ExcelProperty(value ="本月计算-促销服务费(第三方)")
	private String psfvtSum;
	@ExcelProperty(value ="本月合同调整-促销服务费(第三方)")
	private String psfvtMonthContrAdj;
	@ExcelProperty(value ="本月对帐调整-促销服务费(第三方)")
	private String psfvtMonthReconAdj;
	@ExcelProperty(value ="以前年度合同调整-促销服务费(第三方)")
	private String psfvtPyContrAdj;
	@ExcelProperty(value ="以前年度对帐调整-促销服务费(第三方)")
	private String psfvtPyReconAdj;
	@ExcelProperty(value ="以前年度调整-PRG&自查-促销服务费(第三方)")
	private String psfvtPyOtherAdj;
	@ExcelProperty(value ="备注-促销服务费(第三方)")
	private String psfvtMemo;
	@ExcelProperty(value ="促销服务费(第三方)Total")
	private String psfvtTotal;

	@ExcelIgnore
	private Date creationDate;

	@ExcelIgnore
	private Integer createdBy;

	@ExcelIgnore
	private Integer lastUpdatedBy;

	@ExcelIgnore
	private Date lastUpdateDate;

	@ExcelIgnore
	private Integer lastUpdateLogin;

	@ExcelIgnore
	private Integer versionNum;

	@ExcelIgnore
	private String nssPurchaseSum;

	@ExcelIgnore
	private String rssPurchaseSum;

	@ExcelIgnore
	private String fpsPurchaseSum;

	@ExcelIgnore
	private String womdpPurchaseSum;

	@ExcelIgnore
	private String npmfPurchaseSum;

	@ExcelIgnore
	private String istd;

	@ExcelIgnore
	private String istdValue;

	@ExcelIgnore
	private String istdStoreCount;

	@ExcelIgnore
	private String istdSum;

	@ExcelIgnore
	private String istdPurchaseSum;

	@ExcelIgnore
	private String istdMonthContrAdj;

	@ExcelIgnore
	private String istdMonthReconAdj;

	@ExcelIgnore
	private String istdPycontrAdj;

	@ExcelIgnore
	private String istdPyreconAdj;

	@ExcelIgnore
	private String istdPyotherAdj;

	@ExcelIgnore
	private String istdMemo;

	@ExcelIgnore
	private String istdTotal;

	@ExcelIgnore
	private String nsod;

	@ExcelIgnore
	private String nsodValue;

	@ExcelIgnore
	private String nsodStoreCount;

	@ExcelIgnore
	private String nsodSum;

	@ExcelIgnore
	private String nsodPurchaseSum;

	@ExcelIgnore
	private String nsodMonthContrAdj;

	@ExcelIgnore
	private String nsodMonthReconAdj;

	@ExcelIgnore
	private String nsodPycontrAdj;

	@ExcelIgnore
	private String nsodPyreconAdj;

	@ExcelIgnore
	private String nsodPyotherAdj;

	@ExcelIgnore
	private String nsodMemo;

	@ExcelIgnore
	private String nsodTotal;

	@ExcelIgnore
	private String oicn;

	@ExcelIgnore
	private String oicnValue;

	@ExcelIgnore
	private String oicnStoreCount;

	@ExcelIgnore
	private String oicnSum;

	@ExcelIgnore
	private String oicnYearSumTta;

	@ExcelIgnore
	private String oicnYearSumOntop;

	@ExcelIgnore
	private String oicnMonthCount;

	@ExcelIgnore
	private String oicnMonthContrAdj;

	@ExcelIgnore
	private String oicnMonthReconAdj;

	@ExcelIgnore
	private String oicnPycontrAdj;

	@ExcelIgnore
	private String oicnPyreconAdj;

	@ExcelIgnore
	private String oicnPyotherAdj;

	@ExcelIgnore
	private String oicnMemo;

	@ExcelIgnore
	private String oicnTotal;

	@ExcelIgnore
	private String oiob;

	@ExcelIgnore
	private String oiobValue;

	@ExcelIgnore
	private String oiobStoreCount;

	@ExcelIgnore
	private String oiobSum;

	@ExcelIgnore
	private String oiobYearSumTta;

	@ExcelIgnore
	private String oiobYearSumOntop;

	@ExcelIgnore
	private String oiobMonthCount;

	@ExcelIgnore
	private String oiobMonthContrAdj;

	@ExcelIgnore
	private String oiobMonthReconAdj;

	@ExcelIgnore
	private String oiobPycontrAdj;

	@ExcelIgnore
	private String oiobPyreconAdj;

	@ExcelIgnore
	private String oiobPyotherAdj;

	@ExcelIgnore
	private String oiobMemo;

	@ExcelIgnore
	private String lpupStoreValue;

	@ExcelIgnore
	private String lpupPurchaseSum;

	@ExcelIgnore
	private String olpd;

	@ExcelIgnore
	private String olpdValue;

	@ExcelIgnore
	private String olpdStoreValue;

	@ExcelIgnore
	private String olpdSum;

	@ExcelIgnore
	private String olpdPurchaseSum;

	@ExcelIgnore
	private String olpdMonthContrAdj;

	@ExcelIgnore
	private String olpdMonthReconAdj;

	@ExcelIgnore
	private String olpdPycontrAdj;

	@ExcelIgnore
	private String olpdPyreconAdj;

	@ExcelIgnore
	private String olpdPyotherAdj;

	@ExcelIgnore
	private String olpdMemo;

	@ExcelIgnore
	private String olpdTotal;

	public Integer getTtaSroiBillImportId() {
		return ttaSroiBillImportId;
	}

	public void setTtaSroiBillImportId(Integer ttaSroiBillImportId) {
		this.ttaSroiBillImportId = ttaSroiBillImportId;
	}

	public Date getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(Date accountMonth) {
		this.accountMonth = accountMonth;
	}

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getBookInJv() {
		return bookInJv;
	}

	public void setBookInJv(String bookInJv) {
		this.bookInJv = bookInJv;
	}

	public String getBookInJv1() {
		return bookInJv1;
	}

	public void setBookInJv1(String bookInJv1) {
		this.bookInJv1 = bookInJv1;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getVenderab() {
		return venderab;
	}

	public void setVenderab(String venderab) {
		this.venderab = venderab;
	}

	public String getUserContractId() {
		return userContractId;
	}

	public void setUserContractId(String userContractId) {
		this.userContractId = userContractId;
	}

	public String getCooperateStatus() {
		return cooperateStatus;
	}

	public void setCooperateStatus(String cooperateStatus) {
		this.cooperateStatus = cooperateStatus;
	}

	public String getVenderType() {
		return venderType;
	}

	public void setVenderType(String venderType) {
		this.venderType = venderType;
	}

	public String getFamilyPlaningFlag() {
		return familyPlaningFlag;
	}

	public void setFamilyPlaningFlag(String familyPlaningFlag) {
		this.familyPlaningFlag = familyPlaningFlag;
	}

	public Date getValidBeginDate() {
		return validBeginDate;
	}

	public void setValidBeginDate(Date validBeginDate) {
		this.validBeginDate = validBeginDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getPickUpRate() {
		return pickUpRate;
	}

	public void setPickUpRate(String pickUpRate) {
		this.pickUpRate = pickUpRate;
	}

	public String getVenderContainTax() {
		return venderContainTax;
	}

	public void setVenderContainTax(String venderContainTax) {
		this.venderContainTax = venderContainTax;
	}

	public String getNetPurchase() {
		return netPurchase;
	}

	public void setNetPurchase(String netPurchase) {
		this.netPurchase = netPurchase;
	}

	public String getNetPurchaseOrigin() {
		return netPurchaseOrigin;
	}

	public void setNetPurchaseOrigin(String netPurchaseOrigin) {
		this.netPurchaseOrigin = netPurchaseOrigin;
	}

	public String getMonthPur() {
		return monthPur;
	}

	public void setMonthPur(String monthPur) {
		this.monthPur = monthPur;
	}

	public String getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(String monthCount) {
		this.monthCount = monthCount;
	}

	public String getMonthAbi() {
		return monthAbi;
	}

	public void setMonthAbi(String monthAbi) {
		this.monthAbi = monthAbi;
	}

	public String getMonthAbiOntop() {
		return monthAbiOntop;
	}

	public void setMonthAbiOntop(String monthAbiOntop) {
		this.monthAbiOntop = monthAbiOntop;
	}

	public String getYearAbi() {
		return yearAbi;
	}

	public void setYearAbi(String yearAbi) {
		this.yearAbi = yearAbi;
	}

	public String getYearAbiOntop() {
		return yearAbiOntop;
	}

	public void setYearAbiOntop(String yearAbiOntop) {
		this.yearAbiOntop = yearAbiOntop;
	}

	public String getContractAdj() {
		return contractAdj;
	}

	public void setContractAdj(String contractAdj) {
		this.contractAdj = contractAdj;
	}

	public String getBillAdj() {
		return billAdj;
	}

	public void setBillAdj(String billAdj) {
		this.billAdj = billAdj;
	}

	public String getYearContractAdj() {
		return yearContractAdj;
	}

	public void setYearContractAdj(String yearContractAdj) {
		this.yearContractAdj = yearContractAdj;
	}

	public String getYearBillAdj() {
		return yearBillAdj;
	}

	public void setYearBillAdj(String yearBillAdj) {
		this.yearBillAdj = yearBillAdj;
	}

	public String getYearPurchaseCount() {
		return yearPurchaseCount;
	}

	public void setYearPurchaseCount(String yearPurchaseCount) {
		this.yearPurchaseCount = yearPurchaseCount;
	}

	public String getApOiTotalTax() {
		return apOiTotalTax;
	}

	public void setApOiTotalTax(String apOiTotalTax) {
		this.apOiTotalTax = apOiTotalTax;
	}

	public String getApOiTotalNotax() {
		return apOiTotalNotax;
	}

	public void setApOiTotalNotax(String apOiTotalNotax) {
		this.apOiTotalNotax = apOiTotalNotax;
	}

	public String getTax6() {
		return tax6;
	}

	public void setTax6(String tax6) {
		this.tax6 = tax6;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getNssValue() {
		return nssValue;
	}

	public void setNssValue(String nssValue) {
		this.nssValue = nssValue;
	}

	public String getNssStoreCount() {
		return nssStoreCount;
	}

	public void setNssStoreCount(String nssStoreCount) {
		this.nssStoreCount = nssStoreCount;
	}

	public String getNssStoreData() {
		return nssStoreData;
	}

	public void setNssStoreData(String nssStoreData) {
		this.nssStoreData = nssStoreData;
	}

	public String getNssSum() {
		return nssSum;
	}

	public void setNssSum(String nssSum) {
		this.nssSum = nssSum;
	}

	public String getNssMonthContrAdj() {
		return nssMonthContrAdj;
	}

	public void setNssMonthContrAdj(String nssMonthContrAdj) {
		this.nssMonthContrAdj = nssMonthContrAdj;
	}

	public String getNssMonthReconAdj() {
		return nssMonthReconAdj;
	}

	public void setNssMonthReconAdj(String nssMonthReconAdj) {
		this.nssMonthReconAdj = nssMonthReconAdj;
	}

	public String getNssPycontrAdj() {
		return nssPycontrAdj;
	}

	public void setNssPycontrAdj(String nssPycontrAdj) {
		this.nssPycontrAdj = nssPycontrAdj;
	}

	public String getNssPyreconAdj() {
		return nssPyreconAdj;
	}

	public void setNssPyreconAdj(String nssPyreconAdj) {
		this.nssPyreconAdj = nssPyreconAdj;
	}

	public String getNssPyotherAdj() {
		return nssPyotherAdj;
	}

	public void setNssPyotherAdj(String nssPyotherAdj) {
		this.nssPyotherAdj = nssPyotherAdj;
	}

	public String getNssMemo() {
		return nssMemo;
	}

	public void setNssMemo(String nssMemo) {
		this.nssMemo = nssMemo;
	}

	public String getNssTotal() {
		return nssTotal;
	}

	public void setNssTotal(String nssTotal) {
		this.nssTotal = nssTotal;
	}

	public String getRss() {
		return rss;
	}

	public void setRss(String rss) {
		this.rss = rss;
	}

	public String getRssValue() {
		return rssValue;
	}

	public void setRssValue(String rssValue) {
		this.rssValue = rssValue;
	}

	public String getRssStoreCount() {
		return rssStoreCount;
	}

	public void setRssStoreCount(String rssStoreCount) {
		this.rssStoreCount = rssStoreCount;
	}

	public String getRssStoreData() {
		return rssStoreData;
	}

	public void setRssStoreData(String rssStoreData) {
		this.rssStoreData = rssStoreData;
	}

	public String getRssSum() {
		return rssSum;
	}

	public void setRssSum(String rssSum) {
		this.rssSum = rssSum;
	}

	public String getRssMonthContrAdj() {
		return rssMonthContrAdj;
	}

	public void setRssMonthContrAdj(String rssMonthContrAdj) {
		this.rssMonthContrAdj = rssMonthContrAdj;
	}

	public String getRssMonthReconAdj() {
		return rssMonthReconAdj;
	}

	public void setRssMonthReconAdj(String rssMonthReconAdj) {
		this.rssMonthReconAdj = rssMonthReconAdj;
	}

	public String getRssPycontrAdj() {
		return rssPycontrAdj;
	}

	public void setRssPycontrAdj(String rssPycontrAdj) {
		this.rssPycontrAdj = rssPycontrAdj;
	}

	public String getRssPyreconAdj() {
		return rssPyreconAdj;
	}

	public void setRssPyreconAdj(String rssPyreconAdj) {
		this.rssPyreconAdj = rssPyreconAdj;
	}

	public String getRssPyotherAdj() {
		return rssPyotherAdj;
	}

	public void setRssPyotherAdj(String rssPyotherAdj) {
		this.rssPyotherAdj = rssPyotherAdj;
	}

	public String getRssMemo() {
		return rssMemo;
	}

	public void setRssMemo(String rssMemo) {
		this.rssMemo = rssMemo;
	}

	public String getRssTotal() {
		return rssTotal;
	}

	public void setRssTotal(String rssTotal) {
		this.rssTotal = rssTotal;
	}

	public String getFps() {
		return fps;
	}

	public void setFps(String fps) {
		this.fps = fps;
	}

	public String getFpsValue() {
		return fpsValue;
	}

	public void setFpsValue(String fpsValue) {
		this.fpsValue = fpsValue;
	}

	public String getFpsStoreCount() {
		return fpsStoreCount;
	}

	public void setFpsStoreCount(String fpsStoreCount) {
		this.fpsStoreCount = fpsStoreCount;
	}

	public String getFpsSum() {
		return fpsSum;
	}

	public void setFpsSum(String fpsSum) {
		this.fpsSum = fpsSum;
	}

	public String getFpsMonthContrAdj() {
		return fpsMonthContrAdj;
	}

	public void setFpsMonthContrAdj(String fpsMonthContrAdj) {
		this.fpsMonthContrAdj = fpsMonthContrAdj;
	}

	public String getFpsMonthReconAdj() {
		return fpsMonthReconAdj;
	}

	public void setFpsMonthReconAdj(String fpsMonthReconAdj) {
		this.fpsMonthReconAdj = fpsMonthReconAdj;
	}

	public String getFpsPycontrAdj() {
		return fpsPycontrAdj;
	}

	public void setFpsPycontrAdj(String fpsPycontrAdj) {
		this.fpsPycontrAdj = fpsPycontrAdj;
	}

	public String getFpsPyreconAdj() {
		return fpsPyreconAdj;
	}

	public void setFpsPyreconAdj(String fpsPyreconAdj) {
		this.fpsPyreconAdj = fpsPyreconAdj;
	}

	public String getFpsPyotherAdj() {
		return fpsPyotherAdj;
	}

	public void setFpsPyotherAdj(String fpsPyotherAdj) {
		this.fpsPyotherAdj = fpsPyotherAdj;
	}

	public String getFpsMemo() {
		return fpsMemo;
	}

	public void setFpsMemo(String fpsMemo) {
		this.fpsMemo = fpsMemo;
	}

	public String getFpsTotal() {
		return fpsTotal;
	}

	public void setFpsTotal(String fpsTotal) {
		this.fpsTotal = fpsTotal;
	}

	public String getWomdp() {
		return womdp;
	}

	public void setWomdp(String womdp) {
		this.womdp = womdp;
	}

	public String getWomdpValue() {
		return womdpValue;
	}

	public void setWomdpValue(String womdpValue) {
		this.womdpValue = womdpValue;
	}

	public String getWomdpStoreCount() {
		return womdpStoreCount;
	}

	public void setWomdpStoreCount(String womdpStoreCount) {
		this.womdpStoreCount = womdpStoreCount;
	}

	public String getWomdpSum() {
		return womdpSum;
	}

	public void setWomdpSum(String womdpSum) {
		this.womdpSum = womdpSum;
	}

	public String getWomdpMonthContrAdj() {
		return womdpMonthContrAdj;
	}

	public void setWomdpMonthContrAdj(String womdpMonthContrAdj) {
		this.womdpMonthContrAdj = womdpMonthContrAdj;
	}

	public String getWomdpMonthReconAdj() {
		return womdpMonthReconAdj;
	}

	public void setWomdpMonthReconAdj(String womdpMonthReconAdj) {
		this.womdpMonthReconAdj = womdpMonthReconAdj;
	}

	public String getWomdpPycontrAdj() {
		return womdpPycontrAdj;
	}

	public void setWomdpPycontrAdj(String womdpPycontrAdj) {
		this.womdpPycontrAdj = womdpPycontrAdj;
	}

	public String getWomdpPyreconAdj() {
		return womdpPyreconAdj;
	}

	public void setWomdpPyreconAdj(String womdpPyreconAdj) {
		this.womdpPyreconAdj = womdpPyreconAdj;
	}

	public String getWomdpPyotherAdj() {
		return womdpPyotherAdj;
	}

	public void setWomdpPyotherAdj(String womdpPyotherAdj) {
		this.womdpPyotherAdj = womdpPyotherAdj;
	}

	public String getWomdpMemo() {
		return womdpMemo;
	}

	public void setWomdpMemo(String womdpMemo) {
		this.womdpMemo = womdpMemo;
	}

	public String getWomdpTotal() {
		return womdpTotal;
	}

	public void setWomdpTotal(String womdpTotal) {
		this.womdpTotal = womdpTotal;
	}

	public String getNpmf() {
		return npmf;
	}

	public void setNpmf(String npmf) {
		this.npmf = npmf;
	}

	public String getNpmfValue() {
		return npmfValue;
	}

	public void setNpmfValue(String npmfValue) {
		this.npmfValue = npmfValue;
	}

	public String getNpmfStoreCount() {
		return npmfStoreCount;
	}

	public void setNpmfStoreCount(String npmfStoreCount) {
		this.npmfStoreCount = npmfStoreCount;
	}

	public String getNpmfStoreData() {
		return npmfStoreData;
	}

	public void setNpmfStoreData(String npmfStoreData) {
		this.npmfStoreData = npmfStoreData;
	}

	public String getNpmfSum() {
		return npmfSum;
	}

	public void setNpmfSum(String npmfSum) {
		this.npmfSum = npmfSum;
	}

	public String getNpmfMonthContrAdj() {
		return npmfMonthContrAdj;
	}

	public void setNpmfMonthContrAdj(String npmfMonthContrAdj) {
		this.npmfMonthContrAdj = npmfMonthContrAdj;
	}

	public String getNpmfMonthReconAdj() {
		return npmfMonthReconAdj;
	}

	public void setNpmfMonthReconAdj(String npmfMonthReconAdj) {
		this.npmfMonthReconAdj = npmfMonthReconAdj;
	}

	public String getNpmfPycontrAdj() {
		return npmfPycontrAdj;
	}

	public void setNpmfPycontrAdj(String npmfPycontrAdj) {
		this.npmfPycontrAdj = npmfPycontrAdj;
	}

	public String getNpmfPyreconAdj() {
		return npmfPyreconAdj;
	}

	public void setNpmfPyreconAdj(String npmfPyreconAdj) {
		this.npmfPyreconAdj = npmfPyreconAdj;
	}

	public String getNpmfPyotherAdj() {
		return npmfPyotherAdj;
	}

	public void setNpmfPyotherAdj(String npmfPyotherAdj) {
		this.npmfPyotherAdj = npmfPyotherAdj;
	}

	public String getNpmfMemo() {
		return npmfMemo;
	}

	public void setNpmfMemo(String npmfMemo) {
		this.npmfMemo = npmfMemo;
	}

	public String getNpmfTotal() {
		return npmfTotal;
	}

	public void setNpmfTotal(String npmfTotal) {
		this.npmfTotal = npmfTotal;
	}

	public String getRgts() {
		return rgts;
	}

	public void setRgts(String rgts) {
		this.rgts = rgts;
	}

	public String getRgtsValue() {
		return rgtsValue;
	}

	public void setRgtsValue(String rgtsValue) {
		this.rgtsValue = rgtsValue;
	}

	public String getRgtsPur() {
		return rgtsPur;
	}

	public void setRgtsPur(String rgtsPur) {
		this.rgtsPur = rgtsPur;
	}

	public String getRgtsMonthContrAdj() {
		return rgtsMonthContrAdj;
	}

	public void setRgtsMonthContrAdj(String rgtsMonthContrAdj) {
		this.rgtsMonthContrAdj = rgtsMonthContrAdj;
	}

	public String getRgtsMonthReconAdj() {
		return rgtsMonthReconAdj;
	}

	public void setRgtsMonthReconAdj(String rgtsMonthReconAdj) {
		this.rgtsMonthReconAdj = rgtsMonthReconAdj;
	}

	public String getRgtsPycontrAdj() {
		return rgtsPycontrAdj;
	}

	public void setRgtsPycontrAdj(String rgtsPycontrAdj) {
		this.rgtsPycontrAdj = rgtsPycontrAdj;
	}

	public String getRgtsPyreconAdj() {
		return rgtsPyreconAdj;
	}

	public void setRgtsPyreconAdj(String rgtsPyreconAdj) {
		this.rgtsPyreconAdj = rgtsPyreconAdj;
	}

	public String getRgtsPyotherAdj() {
		return rgtsPyotherAdj;
	}

	public void setRgtsPyotherAdj(String rgtsPyotherAdj) {
		this.rgtsPyotherAdj = rgtsPyotherAdj;
	}

	public String getRgtsMemo() {
		return rgtsMemo;
	}

	public void setRgtsMemo(String rgtsMemo) {
		this.rgtsMemo = rgtsMemo;
	}

	public String getRgtsTotal() {
		return rgtsTotal;
	}

	public void setRgtsTotal(String rgtsTotal) {
		this.rgtsTotal = rgtsTotal;
	}

	public String getLpup() {
		return lpup;
	}

	public void setLpup(String lpup) {
		this.lpup = lpup;
	}

	public String getLpupValue() {
		return lpupValue;
	}

	public void setLpupValue(String lpupValue) {
		this.lpupValue = lpupValue;
	}

	public String getLpupSum() {
		return lpupSum;
	}

	public void setLpupSum(String lpupSum) {
		this.lpupSum = lpupSum;
	}

	public String getLpupMonthContrAdj() {
		return lpupMonthContrAdj;
	}

	public void setLpupMonthContrAdj(String lpupMonthContrAdj) {
		this.lpupMonthContrAdj = lpupMonthContrAdj;
	}

	public String getLpupMonthReconAdj() {
		return lpupMonthReconAdj;
	}

	public void setLpupMonthReconAdj(String lpupMonthReconAdj) {
		this.lpupMonthReconAdj = lpupMonthReconAdj;
	}

	public String getLpupPycontrAdj() {
		return lpupPycontrAdj;
	}

	public void setLpupPycontrAdj(String lpupPycontrAdj) {
		this.lpupPycontrAdj = lpupPycontrAdj;
	}

	public String getLpupPyreconAdj() {
		return lpupPyreconAdj;
	}

	public void setLpupPyreconAdj(String lpupPyreconAdj) {
		this.lpupPyreconAdj = lpupPyreconAdj;
	}

	public String getLpupPyotherAdj() {
		return lpupPyotherAdj;
	}

	public void setLpupPyotherAdj(String lpupPyotherAdj) {
		this.lpupPyotherAdj = lpupPyotherAdj;
	}

	public String getLpupMemo() {
		return lpupMemo;
	}

	public void setLpupMemo(String lpupMemo) {
		this.lpupMemo = lpupMemo;
	}

	public String getLpupTotal() {
		return lpupTotal;
	}

	public void setLpupTotal(String lpupTotal) {
		this.lpupTotal = lpupTotal;
	}

	public String getOcrf() {
		return ocrf;
	}

	public void setOcrf(String ocrf) {
		this.ocrf = ocrf;
	}

	public String getOcrfMonthContrAdj() {
		return ocrfMonthContrAdj;
	}

	public void setOcrfMonthContrAdj(String ocrfMonthContrAdj) {
		this.ocrfMonthContrAdj = ocrfMonthContrAdj;
	}

	public String getOcrfMonthReconAdj() {
		return ocrfMonthReconAdj;
	}

	public void setOcrfMonthReconAdj(String ocrfMonthReconAdj) {
		this.ocrfMonthReconAdj = ocrfMonthReconAdj;
	}

	public String getOcrfPycontraAdj() {
		return ocrfPycontraAdj;
	}

	public void setOcrfPycontraAdj(String ocrfPycontraAdj) {
		this.ocrfPycontraAdj = ocrfPycontraAdj;
	}

	public String getOcrfPyreconAdj() {
		return ocrfPyreconAdj;
	}

	public void setOcrfPyreconAdj(String ocrfPyreconAdj) {
		this.ocrfPyreconAdj = ocrfPyreconAdj;
	}

	public String getOcrfPyotherAdj() {
		return ocrfPyotherAdj;
	}

	public void setOcrfPyotherAdj(String ocrfPyotherAdj) {
		this.ocrfPyotherAdj = ocrfPyotherAdj;
	}

	public String getOcrfMemo() {
		return ocrfMemo;
	}

	public void setOcrfMemo(String ocrfMemo) {
		this.ocrfMemo = ocrfMemo;
	}

	public String getOcrfTotal() {
		return ocrfTotal;
	}

	public void setOcrfTotal(String ocrfTotal) {
		this.ocrfTotal = ocrfTotal;
	}

	public String getComin() {
		return comin;
	}

	public void setComin(String comin) {
		this.comin = comin;
	}

	public String getCominValue() {
		return cominValue;
	}

	public void setCominValue(String cominValue) {
		this.cominValue = cominValue;
	}

	public String getCominStoreCount() {
		return cominStoreCount;
	}

	public void setCominStoreCount(String cominStoreCount) {
		this.cominStoreCount = cominStoreCount;
	}

	public String getCominSum() {
		return cominSum;
	}

	public void setCominSum(String cominSum) {
		this.cominSum = cominSum;
	}

	public String getCominMonthContrAdj() {
		return cominMonthContrAdj;
	}

	public void setCominMonthContrAdj(String cominMonthContrAdj) {
		this.cominMonthContrAdj = cominMonthContrAdj;
	}

	public String getCominMonthReconAdj() {
		return cominMonthReconAdj;
	}

	public void setCominMonthReconAdj(String cominMonthReconAdj) {
		this.cominMonthReconAdj = cominMonthReconAdj;
	}

	public String getCominPyContrAdj() {
		return cominPyContrAdj;
	}

	public void setCominPyContrAdj(String cominPyContrAdj) {
		this.cominPyContrAdj = cominPyContrAdj;
	}

	public String getCominPyReconAdj() {
		return cominPyReconAdj;
	}

	public void setCominPyReconAdj(String cominPyReconAdj) {
		this.cominPyReconAdj = cominPyReconAdj;
	}

	public String getCominPyOtherAdj() {
		return cominPyOtherAdj;
	}

	public void setCominPyOtherAdj(String cominPyOtherAdj) {
		this.cominPyOtherAdj = cominPyOtherAdj;
	}

	public String getCominMemo() {
		return cominMemo;
	}

	public void setCominMemo(String cominMemo) {
		this.cominMemo = cominMemo;
	}

	public String getCominTotal() {
		return cominTotal;
	}

	public void setCominTotal(String cominTotal) {
		this.cominTotal = cominTotal;
	}

	public String getPsfvt() {
		return psfvt;
	}

	public void setPsfvt(String psfvt) {
		this.psfvt = psfvt;
	}

	public String getPsfvtValue() {
		return psfvtValue;
	}

	public void setPsfvtValue(String psfvtValue) {
		this.psfvtValue = psfvtValue;
	}

	public String getPsfvtSum() {
		return psfvtSum;
	}

	public void setPsfvtSum(String psfvtSum) {
		this.psfvtSum = psfvtSum;
	}

	public String getPsfvtMonthContrAdj() {
		return psfvtMonthContrAdj;
	}

	public void setPsfvtMonthContrAdj(String psfvtMonthContrAdj) {
		this.psfvtMonthContrAdj = psfvtMonthContrAdj;
	}

	public String getPsfvtMonthReconAdj() {
		return psfvtMonthReconAdj;
	}

	public void setPsfvtMonthReconAdj(String psfvtMonthReconAdj) {
		this.psfvtMonthReconAdj = psfvtMonthReconAdj;
	}

	public String getPsfvtPyContrAdj() {
		return psfvtPyContrAdj;
	}

	public void setPsfvtPyContrAdj(String psfvtPyContrAdj) {
		this.psfvtPyContrAdj = psfvtPyContrAdj;
	}

	public String getPsfvtPyReconAdj() {
		return psfvtPyReconAdj;
	}

	public void setPsfvtPyReconAdj(String psfvtPyReconAdj) {
		this.psfvtPyReconAdj = psfvtPyReconAdj;
	}

	public String getPsfvtPyOtherAdj() {
		return psfvtPyOtherAdj;
	}

	public void setPsfvtPyOtherAdj(String psfvtPyOtherAdj) {
		this.psfvtPyOtherAdj = psfvtPyOtherAdj;
	}

	public String getPsfvtMemo() {
		return psfvtMemo;
	}

	public void setPsfvtMemo(String psfvtMemo) {
		this.psfvtMemo = psfvtMemo;
	}

	public String getPsfvtTotal() {
		return psfvtTotal;
	}

	public void setPsfvtTotal(String psfvtTotal) {
		this.psfvtTotal = psfvtTotal;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getNssPurchaseSum() {
		return nssPurchaseSum;
	}

	public void setNssPurchaseSum(String nssPurchaseSum) {
		this.nssPurchaseSum = nssPurchaseSum;
	}

	public String getRssPurchaseSum() {
		return rssPurchaseSum;
	}

	public void setRssPurchaseSum(String rssPurchaseSum) {
		this.rssPurchaseSum = rssPurchaseSum;
	}

	public String getFpsPurchaseSum() {
		return fpsPurchaseSum;
	}

	public void setFpsPurchaseSum(String fpsPurchaseSum) {
		this.fpsPurchaseSum = fpsPurchaseSum;
	}

	public String getWomdpPurchaseSum() {
		return womdpPurchaseSum;
	}

	public void setWomdpPurchaseSum(String womdpPurchaseSum) {
		this.womdpPurchaseSum = womdpPurchaseSum;
	}

	public String getNpmfPurchaseSum() {
		return npmfPurchaseSum;
	}

	public void setNpmfPurchaseSum(String npmfPurchaseSum) {
		this.npmfPurchaseSum = npmfPurchaseSum;
	}

	public String getIstd() {
		return istd;
	}

	public void setIstd(String istd) {
		this.istd = istd;
	}

	public String getIstdValue() {
		return istdValue;
	}

	public void setIstdValue(String istdValue) {
		this.istdValue = istdValue;
	}

	public String getIstdStoreCount() {
		return istdStoreCount;
	}

	public void setIstdStoreCount(String istdStoreCount) {
		this.istdStoreCount = istdStoreCount;
	}

	public String getIstdSum() {
		return istdSum;
	}

	public void setIstdSum(String istdSum) {
		this.istdSum = istdSum;
	}

	public String getIstdPurchaseSum() {
		return istdPurchaseSum;
	}

	public void setIstdPurchaseSum(String istdPurchaseSum) {
		this.istdPurchaseSum = istdPurchaseSum;
	}

	public String getIstdMonthContrAdj() {
		return istdMonthContrAdj;
	}

	public void setIstdMonthContrAdj(String istdMonthContrAdj) {
		this.istdMonthContrAdj = istdMonthContrAdj;
	}

	public String getIstdMonthReconAdj() {
		return istdMonthReconAdj;
	}

	public void setIstdMonthReconAdj(String istdMonthReconAdj) {
		this.istdMonthReconAdj = istdMonthReconAdj;
	}

	public String getIstdPycontrAdj() {
		return istdPycontrAdj;
	}

	public void setIstdPycontrAdj(String istdPycontrAdj) {
		this.istdPycontrAdj = istdPycontrAdj;
	}

	public String getIstdPyreconAdj() {
		return istdPyreconAdj;
	}

	public void setIstdPyreconAdj(String istdPyreconAdj) {
		this.istdPyreconAdj = istdPyreconAdj;
	}

	public String getIstdPyotherAdj() {
		return istdPyotherAdj;
	}

	public void setIstdPyotherAdj(String istdPyotherAdj) {
		this.istdPyotherAdj = istdPyotherAdj;
	}

	public String getIstdMemo() {
		return istdMemo;
	}

	public void setIstdMemo(String istdMemo) {
		this.istdMemo = istdMemo;
	}

	public String getIstdTotal() {
		return istdTotal;
	}

	public void setIstdTotal(String istdTotal) {
		this.istdTotal = istdTotal;
	}

	public String getNsod() {
		return nsod;
	}

	public void setNsod(String nsod) {
		this.nsod = nsod;
	}

	public String getNsodValue() {
		return nsodValue;
	}

	public void setNsodValue(String nsodValue) {
		this.nsodValue = nsodValue;
	}

	public String getNsodStoreCount() {
		return nsodStoreCount;
	}

	public void setNsodStoreCount(String nsodStoreCount) {
		this.nsodStoreCount = nsodStoreCount;
	}

	public String getNsodSum() {
		return nsodSum;
	}

	public void setNsodSum(String nsodSum) {
		this.nsodSum = nsodSum;
	}

	public String getNsodPurchaseSum() {
		return nsodPurchaseSum;
	}

	public void setNsodPurchaseSum(String nsodPurchaseSum) {
		this.nsodPurchaseSum = nsodPurchaseSum;
	}

	public String getNsodMonthContrAdj() {
		return nsodMonthContrAdj;
	}

	public void setNsodMonthContrAdj(String nsodMonthContrAdj) {
		this.nsodMonthContrAdj = nsodMonthContrAdj;
	}

	public String getNsodMonthReconAdj() {
		return nsodMonthReconAdj;
	}

	public void setNsodMonthReconAdj(String nsodMonthReconAdj) {
		this.nsodMonthReconAdj = nsodMonthReconAdj;
	}

	public String getNsodPycontrAdj() {
		return nsodPycontrAdj;
	}

	public void setNsodPycontrAdj(String nsodPycontrAdj) {
		this.nsodPycontrAdj = nsodPycontrAdj;
	}

	public String getNsodPyreconAdj() {
		return nsodPyreconAdj;
	}

	public void setNsodPyreconAdj(String nsodPyreconAdj) {
		this.nsodPyreconAdj = nsodPyreconAdj;
	}

	public String getNsodPyotherAdj() {
		return nsodPyotherAdj;
	}

	public void setNsodPyotherAdj(String nsodPyotherAdj) {
		this.nsodPyotherAdj = nsodPyotherAdj;
	}

	public String getNsodMemo() {
		return nsodMemo;
	}

	public void setNsodMemo(String nsodMemo) {
		this.nsodMemo = nsodMemo;
	}

	public String getNsodTotal() {
		return nsodTotal;
	}

	public void setNsodTotal(String nsodTotal) {
		this.nsodTotal = nsodTotal;
	}

	public String getOicn() {
		return oicn;
	}

	public void setOicn(String oicn) {
		this.oicn = oicn;
	}

	public String getOicnValue() {
		return oicnValue;
	}

	public void setOicnValue(String oicnValue) {
		this.oicnValue = oicnValue;
	}

	public String getOicnStoreCount() {
		return oicnStoreCount;
	}

	public void setOicnStoreCount(String oicnStoreCount) {
		this.oicnStoreCount = oicnStoreCount;
	}

	public String getOicnSum() {
		return oicnSum;
	}

	public void setOicnSum(String oicnSum) {
		this.oicnSum = oicnSum;
	}

	public String getOicnYearSumTta() {
		return oicnYearSumTta;
	}

	public void setOicnYearSumTta(String oicnYearSumTta) {
		this.oicnYearSumTta = oicnYearSumTta;
	}

	public String getOicnYearSumOntop() {
		return oicnYearSumOntop;
	}

	public void setOicnYearSumOntop(String oicnYearSumOntop) {
		this.oicnYearSumOntop = oicnYearSumOntop;
	}

	public String getOicnMonthCount() {
		return oicnMonthCount;
	}

	public void setOicnMonthCount(String oicnMonthCount) {
		this.oicnMonthCount = oicnMonthCount;
	}

	public String getOicnMonthContrAdj() {
		return oicnMonthContrAdj;
	}

	public void setOicnMonthContrAdj(String oicnMonthContrAdj) {
		this.oicnMonthContrAdj = oicnMonthContrAdj;
	}

	public String getOicnMonthReconAdj() {
		return oicnMonthReconAdj;
	}

	public void setOicnMonthReconAdj(String oicnMonthReconAdj) {
		this.oicnMonthReconAdj = oicnMonthReconAdj;
	}

	public String getOicnPycontrAdj() {
		return oicnPycontrAdj;
	}

	public void setOicnPycontrAdj(String oicnPycontrAdj) {
		this.oicnPycontrAdj = oicnPycontrAdj;
	}

	public String getOicnPyreconAdj() {
		return oicnPyreconAdj;
	}

	public void setOicnPyreconAdj(String oicnPyreconAdj) {
		this.oicnPyreconAdj = oicnPyreconAdj;
	}

	public String getOicnPyotherAdj() {
		return oicnPyotherAdj;
	}

	public void setOicnPyotherAdj(String oicnPyotherAdj) {
		this.oicnPyotherAdj = oicnPyotherAdj;
	}

	public String getOicnMemo() {
		return oicnMemo;
	}

	public void setOicnMemo(String oicnMemo) {
		this.oicnMemo = oicnMemo;
	}

	public String getOicnTotal() {
		return oicnTotal;
	}

	public void setOicnTotal(String oicnTotal) {
		this.oicnTotal = oicnTotal;
	}

	public String getOiob() {
		return oiob;
	}

	public void setOiob(String oiob) {
		this.oiob = oiob;
	}

	public String getOiobValue() {
		return oiobValue;
	}

	public void setOiobValue(String oiobValue) {
		this.oiobValue = oiobValue;
	}

	public String getOiobStoreCount() {
		return oiobStoreCount;
	}

	public void setOiobStoreCount(String oiobStoreCount) {
		this.oiobStoreCount = oiobStoreCount;
	}

	public String getOiobSum() {
		return oiobSum;
	}

	public void setOiobSum(String oiobSum) {
		this.oiobSum = oiobSum;
	}

	public String getOiobYearSumTta() {
		return oiobYearSumTta;
	}

	public void setOiobYearSumTta(String oiobYearSumTta) {
		this.oiobYearSumTta = oiobYearSumTta;
	}

	public String getOiobYearSumOntop() {
		return oiobYearSumOntop;
	}

	public void setOiobYearSumOntop(String oiobYearSumOntop) {
		this.oiobYearSumOntop = oiobYearSumOntop;
	}

	public String getOiobMonthCount() {
		return oiobMonthCount;
	}

	public void setOiobMonthCount(String oiobMonthCount) {
		this.oiobMonthCount = oiobMonthCount;
	}

	public String getOiobMonthContrAdj() {
		return oiobMonthContrAdj;
	}

	public void setOiobMonthContrAdj(String oiobMonthContrAdj) {
		this.oiobMonthContrAdj = oiobMonthContrAdj;
	}

	public String getOiobMonthReconAdj() {
		return oiobMonthReconAdj;
	}

	public void setOiobMonthReconAdj(String oiobMonthReconAdj) {
		this.oiobMonthReconAdj = oiobMonthReconAdj;
	}

	public String getOiobPycontrAdj() {
		return oiobPycontrAdj;
	}

	public void setOiobPycontrAdj(String oiobPycontrAdj) {
		this.oiobPycontrAdj = oiobPycontrAdj;
	}

	public String getOiobPyreconAdj() {
		return oiobPyreconAdj;
	}

	public void setOiobPyreconAdj(String oiobPyreconAdj) {
		this.oiobPyreconAdj = oiobPyreconAdj;
	}

	public String getOiobPyotherAdj() {
		return oiobPyotherAdj;
	}

	public void setOiobPyotherAdj(String oiobPyotherAdj) {
		this.oiobPyotherAdj = oiobPyotherAdj;
	}

	public String getOiobMemo() {
		return oiobMemo;
	}

	public void setOiobMemo(String oiobMemo) {
		this.oiobMemo = oiobMemo;
	}

	public String getLpupStoreValue() {
		return lpupStoreValue;
	}

	public void setLpupStoreValue(String lpupStoreValue) {
		this.lpupStoreValue = lpupStoreValue;
	}

	public String getLpupPurchaseSum() {
		return lpupPurchaseSum;
	}

	public void setLpupPurchaseSum(String lpupPurchaseSum) {
		this.lpupPurchaseSum = lpupPurchaseSum;
	}

	public String getOlpd() {
		return olpd;
	}

	public void setOlpd(String olpd) {
		this.olpd = olpd;
	}

	public String getOlpdValue() {
		return olpdValue;
	}

	public void setOlpdValue(String olpdValue) {
		this.olpdValue = olpdValue;
	}

	public String getOlpdStoreValue() {
		return olpdStoreValue;
	}

	public void setOlpdStoreValue(String olpdStoreValue) {
		this.olpdStoreValue = olpdStoreValue;
	}

	public String getOlpdSum() {
		return olpdSum;
	}

	public void setOlpdSum(String olpdSum) {
		this.olpdSum = olpdSum;
	}

	public String getOlpdPurchaseSum() {
		return olpdPurchaseSum;
	}

	public void setOlpdPurchaseSum(String olpdPurchaseSum) {
		this.olpdPurchaseSum = olpdPurchaseSum;
	}

	public String getOlpdMonthContrAdj() {
		return olpdMonthContrAdj;
	}

	public void setOlpdMonthContrAdj(String olpdMonthContrAdj) {
		this.olpdMonthContrAdj = olpdMonthContrAdj;
	}

	public String getOlpdMonthReconAdj() {
		return olpdMonthReconAdj;
	}

	public void setOlpdMonthReconAdj(String olpdMonthReconAdj) {
		this.olpdMonthReconAdj = olpdMonthReconAdj;
	}

	public String getOlpdPycontrAdj() {
		return olpdPycontrAdj;
	}

	public void setOlpdPycontrAdj(String olpdPycontrAdj) {
		this.olpdPycontrAdj = olpdPycontrAdj;
	}

	public String getOlpdPyreconAdj() {
		return olpdPyreconAdj;
	}

	public void setOlpdPyreconAdj(String olpdPyreconAdj) {
		this.olpdPyreconAdj = olpdPyreconAdj;
	}

	public String getOlpdPyotherAdj() {
		return olpdPyotherAdj;
	}

	public void setOlpdPyotherAdj(String olpdPyotherAdj) {
		this.olpdPyotherAdj = olpdPyotherAdj;
	}

	public String getOlpdMemo() {
		return olpdMemo;
	}

	public void setOlpdMemo(String olpdMemo) {
		this.olpdMemo = olpdMemo;
	}

	public String getOlpdTotal() {
		return olpdTotal;
	}

	public void setOlpdTotal(String olpdTotal) {
		this.olpdTotal = olpdTotal;
	}
}
