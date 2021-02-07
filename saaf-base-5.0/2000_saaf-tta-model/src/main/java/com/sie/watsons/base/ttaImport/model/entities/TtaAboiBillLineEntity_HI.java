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
 * TtaAboiBillLineEntity_HI Entity Object
 * Fri Oct 18 11:26:06 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_aboi_bill_line")
public class TtaAboiBillLineEntity_HI {
    private Integer ttaAboiBillImportId;
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
    private String monthNetPur;
    private String monthNetPurOrigin;
    private String lastYearNetPur;
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
    private String dsf001;
    private String dsf001CurrentMonthTta;
    private String dsf001CurrentMonthOntop;
    private String dsf001PyyearTta;
    private String dsf001PyyearOntop;
    private String dsf001MonthContractAdj;
    private String dsf001MonthReconAdj;
    private String dsf001PyContractAdj;
    private String dsf001PyReconAdj;
    private String dsf001PyAuditAdj;
    private String dsf001Memo;
    private String dsf001Total;
    private String dro001;
    private String dro001CurrentMonthTta;
    private String dro001CurrentMonthOntop;
    private String dro001PyyearTta;
    private String dro001PyyearOntop;
    private String dro001MonthContractAdj;
    private String dro001MonthReconAdj;
    private String dro001PyContractAdj;
    private String dro001PyReconAdj;
    private String dro001PyAuditAdj;
    private String dro001Memo;
    private String dro001Total;
    private String ntp001;
    private String ntp001CurrentMonthTta;
    private String ntp001CurrentMonthOntop;
    private String ntp001PyyearTta;
    private String ntp001PyyearOntop;
    private String ntp001MonthContractAdj;
    private String ntp001MonthReconAdj;
    private String ntp001PyContractAdj;
    private String ntp001PyReconAdj;
    private String ntp001PyAuditAdj;
    private String ntp001Memo;
    private String ntp001Total;
    private String cri001;
    private String cri001CurrentMonthTta;
    private String cri001CurrentMonthOntop;
    private String cri001PyyearTta;
    private String cri001PyyearOntop;
    private String cri001MonthContractAdj;
    private String cri001MonthReconAdj;
    private String cri001PyContractAdj;
    private String cri001PyReconAdj;
    private String cri001PyAuditAdj;
    private String cri001Memo;
    private String cri001Total;
    private String dmi001;
    private String dmi001CurrentMonthTta;
    private String dmi001CurrentMonthOntop;
    private String dmi001PyyearTta;
    private String dmi001PyyearOntop;
    private String dmi001MonthContractAdj;
    private String dmi001MonthReconAdj;
    private String dmi001PyContractAdj;
    private String dmi001PyReconAdj;
    private String dmi001PyAuditAdj;
    private String dmi001Memo;
    private String dmi001Total;
    private String npd001;
    private String npd001CurrentMonthTta;
    private String npd001CurrentMonthOntop;
    private String npd001PyyearTta;
    private String npd001PyyearOntop;
    private String npd001MonthContractAdj;
    private String npd001MonthReconAdj;
    private String npd001PyContractAdj;
    private String npd001PyReconAdj;
    private String npd001PyAuditAdj;
    private String npd001Memo;
    private String npd001Total;
    private String bdf001;
    private String bdf001CurrentMonthTta;
    private String bdf001CurrentMonthOntop;
    private String bdf001PyyearTta;
    private String bdf001PyyearOntop;
    private String bdf001MonthContractAdj;
    private String bdf001MonthReconAdj;
    private String bdf001PyContractAdj;
    private String bdf001PyReconAdj;
    private String bdf001PyAuditAdj;
    private String bdf001Memo;
    private String bdf001Total;
    private String hbi001;
    private String hbi001CurrentMonthTta;
    private String hbi001CurrentMonthOntop;
    private String hbi001PyyearTta;
    private String hbi001PyyearOntop;
    private String hbi001MonthContractAdj;
    private String hbi001MonthReconAdj;
    private String hbi001PyContractAdj;
    private String hbi001PyReconAdj;
    private String hbi001PyAuditAdj;
    private String hbi001Memo;
    private String hbi001Total;
    private String oth001;
    private String oth001CurrentMonthTta;
    private String oth001CurrentMonthOntop;
    private String oth001PyyearTta;
    private String oth001PyyearOntop;
    private String oth001OtherGl;
    private String oth001OtherPurchase;
    private String oth001MonthContractAdj;
    private String oth001MonthReconAdj;
    private String oth001PyContractAdj;
    private String oth001PyReconAdj;
    private String oth001PyAuditAdj;
    private String oth001Memo;
    private String oth001Total;
    private String mkt001;
    private String mkt001CurrentMonthTta;
    private String mkt001CurrentMonthOntop;
    private String mkt001PyyearTta;
    private String mkt001PyyearOntop;
    private String mkt001MonthContractAdj;
    private String mkt001MonthReconAdj;
    private String mkt001PyContractAdj;
    private String mkt001PyReconAdj;
    private String mkt001PyAuditAdj;
    private String mkt001Memo;
    private String mkt001Total;
    private String nti001;
    private String nti001CurrentMonthTta;
    private String nti001CurrentMonthOntop;
    private String nti001PyyearTta;
    private String nti001PyyearOntop;
    private String nti001MonthContractAdj;
    private String nti001MonthReconAdj;
    private String nti001PyContractAdj;
    private String nti001PyReconAdj;
    private String nti001PyAuditAdj;
    private String nti001Memo;
    private String nti001Total;
    private String bac001;
    private String bac001CurrentMonthTta;
    private String bac001CurrentMonthOntop;
    private String bac001PyyearTta;
    private String bac001PyyearOntop;
    private String bac001MonthContractAdj;
    private String bac001MonthReconAdj;
    private String bac001PyContractAdj;
    private String bac001PyReconAdj;
    private String bac001PyAuditAdj;
    private String bac001Memo;
    private String bac001Total;
    private String opsf01;
    private String opsf01CurrentMonthTta;
    private String opsf01CurrentMonthOntop;
    private String opsf01PyyearTta;
    private String opsf01PyyearOntop;
    private String opsf01MonthContractAdj;
    private String opsf01MonthReconAdj;
    private String opsf01PyContractAdj;
    private String opsf01PyReconAdj;
    private String opsf01PyAuditAdj;
    private String opsf01Memo;
    private String opsf01Total;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String naa001;
    private String naa001CurrentMonthTta;
    private String naa001MonthContractAdj;
    private String naa001MonthReconAdj;
    private String naa001PyContractAdj;
    private String naa001PyReconAdj;
    private String naa001PyAuditAdj;
    private String naa001Memo;
    private String naa001Total;
    private String mfi001;
    private String mfi001CurrentMonthTta;
    private String mfi001CurrentMonthOntop;
    private String mfi001PyyearTta;
    private String mfi001PyyearOntop;
    private String mfi001MonthContractAdj;
    private String mfi001MonthReconAdj;
    private String mfi001PyContractAdj;
    private String mfi001PyReconAdj;
    private String mfi001PyAuditAdj;
    private String mfi001Memo;
    private String mfi001Total;
    private String npf001;
    private String npf001CurrentMonthTta;
    private String npf001MonthContractAdj;
    private String npf001MonthReconAdj;
    private String npf001PyContractAdj;
    private String npf001PyReconAdj;
    private String npf001PyAuditAdj;
    private String npf001Memo;
    private String npf001Total;
    private String sri001;
    private String sri001CurrentMonthTta;
    private String sri001MonthContractAdj;
    private String sri001MonthReconAdj;
    private String sri001PyContractAdj;
    private String sri001PyReconAdj;
    private String sri001PyAuditAdj;
    private String sri001Memo;
    private String sri001Total;
    private String ssi001;
    private String ssi001CurrentMonthTta;
    private String ssi001MonthContractAdj;
    private String ssi001MonthReconAdj;
    private String ssi001PyContractAdj;
    private String ssi001PyReconAdj;
    private String ssi001PyAuditAdj;
    private String ssi001Memo;
    private String ssi001Total;
    private String cps001;
    private String cps001CurrentMonthTta;
    private String cps001MonthContractAdj;
    private String cps001MonthReconAdj;
    private String cps001PyContractAdj;
    private String cps001PyReconAdj;
    private String cps001PyAuditAdj;
    private String cps001Memo;
    private String cps001Total;
    private String otf001CurrentMonthTta;
    private String otf001MonthContractAdj;
    private String otf001MonthReconAdj;
    private String otf001PyContractAdj;
    private String otf001PyReconAdj;
    private String otf001PyAuditAdj;
    private String otf001Total;
    private String drg001;
    private String drg001CurrentMonthTta;
    private String drg001CurrentMonthOntop;
    private String drg001PyyearTta;
    private String drg001PyyearOntop;
    private String drg001MonthContractAdj;
    private String drg001MonthReconAdj;
    private String drg001PyContractAdj;
    private String drg001PyReconAdj;
    private String drg001PyAuditAdj;
    private String drg001Memo;
    private String drg001Total;
    private String drh001;
    private String drh001CurrentMonthTta;
    private String drh001CurrentMonthOntop;
    private String drh001PyyearTta;
    private String drh001PyyearOntop;
    private String drh001MonthContractAdj;
    private String drh001MonthReconAdj;
    private String drh001PyContractAdj;
    private String drh001PyReconAdj;
    private String drh001PyAuditAdj;
    private String drh001Memo;
    private String drh001Total;
    private String drb001;
    private String drb001CurrentMonthTta;
    private String drb001CurrentMonthOntop;
    private String drb001PyyearTta;
    private String drb001PyyearOntop;
    private String drb001MonthContractAdj;
    private String drb001MonthReconAdj;
    private String drb001PyContractAdj;
    private String drb001PyReconAdj;
    private String drb001PyAuditAdj;
    private String drb001Memo;
    private String drb001Total;
    private Integer operatorUserId;

	public void setTtaAboiBillImportId(Integer ttaAboiBillImportId) {
		this.ttaAboiBillImportId = ttaAboiBillImportId;
	}

    @Id
    @SequenceGenerator(name="SEQ_TTA_ABOI_BILL_LINE", sequenceName="SEQ_TTA_ABOI_BILL_LINE", allocationSize=1, initialValue=1)
    @GeneratedValue(generator="SEQ_TTA_ABOI_BILL_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="TTA_ABOI_BILL_IMPORT_ID", nullable=false, length=22)
	public Integer getTtaAboiBillImportId() {
		return ttaAboiBillImportId;
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

	public void setMonthNetPur(String monthNetPur) {
		this.monthNetPur = monthNetPur;
	}

	@Column(name="month_net_pur", nullable=true, length=100)	
	public String getMonthNetPur() {
		return monthNetPur;
	}

	public void setMonthNetPurOrigin(String monthNetPurOrigin) {
		this.monthNetPurOrigin = monthNetPurOrigin;
	}

	@Column(name="month_net_pur_origin", nullable=true, length=100)	
	public String getMonthNetPurOrigin() {
		return monthNetPurOrigin;
	}

	public void setLastYearNetPur(String lastYearNetPur) {
		this.lastYearNetPur = lastYearNetPur;
	}

	@Column(name="last_year_net_pur", nullable=true, length=100)	
	public String getLastYearNetPur() {
		return lastYearNetPur;
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

	public void setDsf001(String dsf001) {
		this.dsf001 = dsf001;
	}

	@Column(name="dsf001", nullable=true, length=500)	
	public String getDsf001() {
		return dsf001;
	}

	public void setDsf001CurrentMonthTta(String dsf001CurrentMonthTta) {
		this.dsf001CurrentMonthTta = dsf001CurrentMonthTta;
	}

	@Column(name="dsf001_current_month_tta", nullable=true, length=50)	
	public String getDsf001CurrentMonthTta() {
		return dsf001CurrentMonthTta;
	}

	public void setDsf001CurrentMonthOntop(String dsf001CurrentMonthOntop) {
		this.dsf001CurrentMonthOntop = dsf001CurrentMonthOntop;
	}

	@Column(name="dsf001_current_month_ontop", nullable=true, length=50)	
	public String getDsf001CurrentMonthOntop() {
		return dsf001CurrentMonthOntop;
	}

	public void setDsf001PyyearTta(String dsf001PyyearTta) {
		this.dsf001PyyearTta = dsf001PyyearTta;
	}

	@Column(name="dsf001_pyyear_tta", nullable=true, length=50)	
	public String getDsf001PyyearTta() {
		return dsf001PyyearTta;
	}

	public void setDsf001PyyearOntop(String dsf001PyyearOntop) {
		this.dsf001PyyearOntop = dsf001PyyearOntop;
	}

	@Column(name="dsf001_pyyear_ontop", nullable=true, length=50)	
	public String getDsf001PyyearOntop() {
		return dsf001PyyearOntop;
	}

	public void setDsf001MonthContractAdj(String dsf001MonthContractAdj) {
		this.dsf001MonthContractAdj = dsf001MonthContractAdj;
	}

	@Column(name="dsf001_month_contract_adj", nullable=true, length=50)	
	public String getDsf001MonthContractAdj() {
		return dsf001MonthContractAdj;
	}

	public void setDsf001MonthReconAdj(String dsf001MonthReconAdj) {
		this.dsf001MonthReconAdj = dsf001MonthReconAdj;
	}

	@Column(name="dsf001_month_recon_adj", nullable=true, length=50)	
	public String getDsf001MonthReconAdj() {
		return dsf001MonthReconAdj;
	}

	public void setDsf001PyContractAdj(String dsf001PyContractAdj) {
		this.dsf001PyContractAdj = dsf001PyContractAdj;
	}

	@Column(name="dsf001_py_contract_adj", nullable=true, length=50)	
	public String getDsf001PyContractAdj() {
		return dsf001PyContractAdj;
	}

	public void setDsf001PyReconAdj(String dsf001PyReconAdj) {
		this.dsf001PyReconAdj = dsf001PyReconAdj;
	}

	@Column(name="dsf001_py_recon_adj", nullable=true, length=50)	
	public String getDsf001PyReconAdj() {
		return dsf001PyReconAdj;
	}

	public void setDsf001PyAuditAdj(String dsf001PyAuditAdj) {
		this.dsf001PyAuditAdj = dsf001PyAuditAdj;
	}

	@Column(name="dsf001_py_audit_adj", nullable=true, length=50)	
	public String getDsf001PyAuditAdj() {
		return dsf001PyAuditAdj;
	}

	public void setDsf001Memo(String dsf001Memo) {
		this.dsf001Memo = dsf001Memo;
	}

	@Column(name="dsf001_memo", nullable=true, length=500)	
	public String getDsf001Memo() {
		return dsf001Memo;
	}

	public void setDsf001Total(String dsf001Total) {
		this.dsf001Total = dsf001Total;
	}

	@Column(name="dsf001_total", nullable=true, length=50)	
	public String getDsf001Total() {
		return dsf001Total;
	}

	public void setDro001(String dro001) {
		this.dro001 = dro001;
	}

	@Column(name="dro001", nullable=true, length=500)	
	public String getDro001() {
		return dro001;
	}

	public void setDro001CurrentMonthTta(String dro001CurrentMonthTta) {
		this.dro001CurrentMonthTta = dro001CurrentMonthTta;
	}

	@Column(name="dro001_current_month_tta", nullable=true, length=50)	
	public String getDro001CurrentMonthTta() {
		return dro001CurrentMonthTta;
	}

	public void setDro001CurrentMonthOntop(String dro001CurrentMonthOntop) {
		this.dro001CurrentMonthOntop = dro001CurrentMonthOntop;
	}

	@Column(name="dro001_current_month_ontop", nullable=true, length=50)	
	public String getDro001CurrentMonthOntop() {
		return dro001CurrentMonthOntop;
	}

	public void setDro001PyyearTta(String dro001PyyearTta) {
		this.dro001PyyearTta = dro001PyyearTta;
	}

	@Column(name="dro001_pyyear_tta", nullable=true, length=50)	
	public String getDro001PyyearTta() {
		return dro001PyyearTta;
	}

	public void setDro001PyyearOntop(String dro001PyyearOntop) {
		this.dro001PyyearOntop = dro001PyyearOntop;
	}

	@Column(name="dro001_pyyear_ontop", nullable=true, length=50)	
	public String getDro001PyyearOntop() {
		return dro001PyyearOntop;
	}

	public void setDro001MonthContractAdj(String dro001MonthContractAdj) {
		this.dro001MonthContractAdj = dro001MonthContractAdj;
	}

	@Column(name="dro001_month_contract_adj", nullable=true, length=50)	
	public String getDro001MonthContractAdj() {
		return dro001MonthContractAdj;
	}

	public void setDro001MonthReconAdj(String dro001MonthReconAdj) {
		this.dro001MonthReconAdj = dro001MonthReconAdj;
	}

	@Column(name="dro001_month_recon_adj", nullable=true, length=50)	
	public String getDro001MonthReconAdj() {
		return dro001MonthReconAdj;
	}

	public void setDro001PyContractAdj(String dro001PyContractAdj) {
		this.dro001PyContractAdj = dro001PyContractAdj;
	}

	@Column(name="dro001_py_contract_adj", nullable=true, length=50)	
	public String getDro001PyContractAdj() {
		return dro001PyContractAdj;
	}

	public void setDro001PyReconAdj(String dro001PyReconAdj) {
		this.dro001PyReconAdj = dro001PyReconAdj;
	}

	@Column(name="dro001_py_recon_adj", nullable=true, length=50)	
	public String getDro001PyReconAdj() {
		return dro001PyReconAdj;
	}

	public void setDro001PyAuditAdj(String dro001PyAuditAdj) {
		this.dro001PyAuditAdj = dro001PyAuditAdj;
	}

	@Column(name="dro001_py_audit_adj", nullable=true, length=50)	
	public String getDro001PyAuditAdj() {
		return dro001PyAuditAdj;
	}

	public void setDro001Memo(String dro001Memo) {
		this.dro001Memo = dro001Memo;
	}

	@Column(name="dro001_memo", nullable=true, length=500)	
	public String getDro001Memo() {
		return dro001Memo;
	}

	public void setDro001Total(String dro001Total) {
		this.dro001Total = dro001Total;
	}

	@Column(name="dro001_total", nullable=true, length=50)	
	public String getDro001Total() {
		return dro001Total;
	}

	public void setNtp001(String ntp001) {
		this.ntp001 = ntp001;
	}

	@Column(name="ntp001", nullable=true, length=500)	
	public String getNtp001() {
		return ntp001;
	}

	public void setNtp001CurrentMonthTta(String ntp001CurrentMonthTta) {
		this.ntp001CurrentMonthTta = ntp001CurrentMonthTta;
	}

	@Column(name="ntp001_current_month_tta", nullable=true, length=50)	
	public String getNtp001CurrentMonthTta() {
		return ntp001CurrentMonthTta;
	}

	public void setNtp001CurrentMonthOntop(String ntp001CurrentMonthOntop) {
		this.ntp001CurrentMonthOntop = ntp001CurrentMonthOntop;
	}

	@Column(name="ntp001_current_month_ontop", nullable=true, length=50)	
	public String getNtp001CurrentMonthOntop() {
		return ntp001CurrentMonthOntop;
	}

	public void setNtp001PyyearTta(String ntp001PyyearTta) {
		this.ntp001PyyearTta = ntp001PyyearTta;
	}

	@Column(name="ntp001_pyyear_tta", nullable=true, length=50)	
	public String getNtp001PyyearTta() {
		return ntp001PyyearTta;
	}

	public void setNtp001PyyearOntop(String ntp001PyyearOntop) {
		this.ntp001PyyearOntop = ntp001PyyearOntop;
	}

	@Column(name="ntp001_pyyear_ontop", nullable=true, length=50)	
	public String getNtp001PyyearOntop() {
		return ntp001PyyearOntop;
	}

	public void setNtp001MonthContractAdj(String ntp001MonthContractAdj) {
		this.ntp001MonthContractAdj = ntp001MonthContractAdj;
	}

	@Column(name="ntp001_month_contract_adj", nullable=true, length=50)	
	public String getNtp001MonthContractAdj() {
		return ntp001MonthContractAdj;
	}

	public void setNtp001MonthReconAdj(String ntp001MonthReconAdj) {
		this.ntp001MonthReconAdj = ntp001MonthReconAdj;
	}

	@Column(name="ntp001_month_recon_adj", nullable=true, length=50)	
	public String getNtp001MonthReconAdj() {
		return ntp001MonthReconAdj;
	}

	public void setNtp001PyContractAdj(String ntp001PyContractAdj) {
		this.ntp001PyContractAdj = ntp001PyContractAdj;
	}

	@Column(name="ntp001_py_contract_adj", nullable=true, length=50)	
	public String getNtp001PyContractAdj() {
		return ntp001PyContractAdj;
	}

	public void setNtp001PyReconAdj(String ntp001PyReconAdj) {
		this.ntp001PyReconAdj = ntp001PyReconAdj;
	}

	@Column(name="ntp001_py_recon_adj", nullable=true, length=50)	
	public String getNtp001PyReconAdj() {
		return ntp001PyReconAdj;
	}

	public void setNtp001PyAuditAdj(String ntp001PyAuditAdj) {
		this.ntp001PyAuditAdj = ntp001PyAuditAdj;
	}

	@Column(name="ntp001_py_audit_adj", nullable=true, length=50)	
	public String getNtp001PyAuditAdj() {
		return ntp001PyAuditAdj;
	}

	public void setNtp001Memo(String ntp001Memo) {
		this.ntp001Memo = ntp001Memo;
	}

	@Column(name="ntp001_memo", nullable=true, length=500)	
	public String getNtp001Memo() {
		return ntp001Memo;
	}

	public void setNtp001Total(String ntp001Total) {
		this.ntp001Total = ntp001Total;
	}

	@Column(name="ntp001_total", nullable=true, length=50)	
	public String getNtp001Total() {
		return ntp001Total;
	}

	public void setCri001(String cri001) {
		this.cri001 = cri001;
	}

	@Column(name="cri001", nullable=true, length=500)	
	public String getCri001() {
		return cri001;
	}

	public void setCri001CurrentMonthTta(String cri001CurrentMonthTta) {
		this.cri001CurrentMonthTta = cri001CurrentMonthTta;
	}

	@Column(name="cri001_current_month_tta", nullable=true, length=50)	
	public String getCri001CurrentMonthTta() {
		return cri001CurrentMonthTta;
	}

	public void setCri001CurrentMonthOntop(String cri001CurrentMonthOntop) {
		this.cri001CurrentMonthOntop = cri001CurrentMonthOntop;
	}

	@Column(name="cri001_current_month_ontop", nullable=true, length=50)	
	public String getCri001CurrentMonthOntop() {
		return cri001CurrentMonthOntop;
	}

	public void setCri001PyyearTta(String cri001PyyearTta) {
		this.cri001PyyearTta = cri001PyyearTta;
	}

	@Column(name="cri001_pyyear_tta", nullable=true, length=50)	
	public String getCri001PyyearTta() {
		return cri001PyyearTta;
	}

	public void setCri001PyyearOntop(String cri001PyyearOntop) {
		this.cri001PyyearOntop = cri001PyyearOntop;
	}

	@Column(name="cri001_pyyear_ontop", nullable=true, length=50)	
	public String getCri001PyyearOntop() {
		return cri001PyyearOntop;
	}

	public void setCri001MonthContractAdj(String cri001MonthContractAdj) {
		this.cri001MonthContractAdj = cri001MonthContractAdj;
	}

	@Column(name="cri001_month_contract_adj", nullable=true, length=50)	
	public String getCri001MonthContractAdj() {
		return cri001MonthContractAdj;
	}

	public void setCri001MonthReconAdj(String cri001MonthReconAdj) {
		this.cri001MonthReconAdj = cri001MonthReconAdj;
	}

	@Column(name="cri001_month_recon_adj", nullable=true, length=50)	
	public String getCri001MonthReconAdj() {
		return cri001MonthReconAdj;
	}

	public void setCri001PyContractAdj(String cri001PyContractAdj) {
		this.cri001PyContractAdj = cri001PyContractAdj;
	}

	@Column(name="cri001_py_contract_adj", nullable=true, length=50)	
	public String getCri001PyContractAdj() {
		return cri001PyContractAdj;
	}

	public void setCri001PyReconAdj(String cri001PyReconAdj) {
		this.cri001PyReconAdj = cri001PyReconAdj;
	}

	@Column(name="cri001_py_recon_adj", nullable=true, length=50)	
	public String getCri001PyReconAdj() {
		return cri001PyReconAdj;
	}

	public void setCri001PyAuditAdj(String cri001PyAuditAdj) {
		this.cri001PyAuditAdj = cri001PyAuditAdj;
	}

	@Column(name="cri001_py_audit_adj", nullable=true, length=50)	
	public String getCri001PyAuditAdj() {
		return cri001PyAuditAdj;
	}

	public void setCri001Memo(String cri001Memo) {
		this.cri001Memo = cri001Memo;
	}

	@Column(name="cri001_memo", nullable=true, length=500)	
	public String getCri001Memo() {
		return cri001Memo;
	}

	public void setCri001Total(String cri001Total) {
		this.cri001Total = cri001Total;
	}

	@Column(name="cri001_total", nullable=true, length=50)	
	public String getCri001Total() {
		return cri001Total;
	}

	public void setDmi001(String dmi001) {
		this.dmi001 = dmi001;
	}

	@Column(name="dmi001", nullable=true, length=500)	
	public String getDmi001() {
		return dmi001;
	}

	public void setDmi001CurrentMonthTta(String dmi001CurrentMonthTta) {
		this.dmi001CurrentMonthTta = dmi001CurrentMonthTta;
	}

	@Column(name="dmi001_current_month_tta", nullable=true, length=50)	
	public String getDmi001CurrentMonthTta() {
		return dmi001CurrentMonthTta;
	}

	public void setDmi001CurrentMonthOntop(String dmi001CurrentMonthOntop) {
		this.dmi001CurrentMonthOntop = dmi001CurrentMonthOntop;
	}

	@Column(name="dmi001_current_month_ontop", nullable=true, length=50)	
	public String getDmi001CurrentMonthOntop() {
		return dmi001CurrentMonthOntop;
	}

	public void setDmi001PyyearTta(String dmi001PyyearTta) {
		this.dmi001PyyearTta = dmi001PyyearTta;
	}

	@Column(name="dmi001_pyyear_tta", nullable=true, length=50)	
	public String getDmi001PyyearTta() {
		return dmi001PyyearTta;
	}

	public void setDmi001PyyearOntop(String dmi001PyyearOntop) {
		this.dmi001PyyearOntop = dmi001PyyearOntop;
	}

	@Column(name="dmi001_pyyear_ontop", nullable=true, length=50)	
	public String getDmi001PyyearOntop() {
		return dmi001PyyearOntop;
	}

	public void setDmi001MonthContractAdj(String dmi001MonthContractAdj) {
		this.dmi001MonthContractAdj = dmi001MonthContractAdj;
	}

	@Column(name="dmi001_month_contract_adj", nullable=true, length=50)	
	public String getDmi001MonthContractAdj() {
		return dmi001MonthContractAdj;
	}

	public void setDmi001MonthReconAdj(String dmi001MonthReconAdj) {
		this.dmi001MonthReconAdj = dmi001MonthReconAdj;
	}

	@Column(name="dmi001_month_recon_adj", nullable=true, length=50)	
	public String getDmi001MonthReconAdj() {
		return dmi001MonthReconAdj;
	}

	public void setDmi001PyContractAdj(String dmi001PyContractAdj) {
		this.dmi001PyContractAdj = dmi001PyContractAdj;
	}

	@Column(name="dmi001_py_contract_adj", nullable=true, length=50)	
	public String getDmi001PyContractAdj() {
		return dmi001PyContractAdj;
	}

	public void setDmi001PyReconAdj(String dmi001PyReconAdj) {
		this.dmi001PyReconAdj = dmi001PyReconAdj;
	}

	@Column(name="dmi001_py_recon_adj", nullable=true, length=50)	
	public String getDmi001PyReconAdj() {
		return dmi001PyReconAdj;
	}

	public void setDmi001PyAuditAdj(String dmi001PyAuditAdj) {
		this.dmi001PyAuditAdj = dmi001PyAuditAdj;
	}

	@Column(name="dmi001_py_audit_adj", nullable=true, length=50)	
	public String getDmi001PyAuditAdj() {
		return dmi001PyAuditAdj;
	}

	public void setDmi001Memo(String dmi001Memo) {
		this.dmi001Memo = dmi001Memo;
	}

	@Column(name="dmi001_memo", nullable=true, length=500)	
	public String getDmi001Memo() {
		return dmi001Memo;
	}

	public void setDmi001Total(String dmi001Total) {
		this.dmi001Total = dmi001Total;
	}

	@Column(name="dmi001_total", nullable=true, length=50)	
	public String getDmi001Total() {
		return dmi001Total;
	}

	public void setNpd001(String npd001) {
		this.npd001 = npd001;
	}

	@Column(name="npd001", nullable=true, length=500)	
	public String getNpd001() {
		return npd001;
	}

	public void setNpd001CurrentMonthTta(String npd001CurrentMonthTta) {
		this.npd001CurrentMonthTta = npd001CurrentMonthTta;
	}

	@Column(name="npd001_current_month_tta", nullable=true, length=50)	
	public String getNpd001CurrentMonthTta() {
		return npd001CurrentMonthTta;
	}

	public void setNpd001CurrentMonthOntop(String npd001CurrentMonthOntop) {
		this.npd001CurrentMonthOntop = npd001CurrentMonthOntop;
	}

	@Column(name="npd001_current_month_ontop", nullable=true, length=50)	
	public String getNpd001CurrentMonthOntop() {
		return npd001CurrentMonthOntop;
	}

	public void setNpd001PyyearTta(String npd001PyyearTta) {
		this.npd001PyyearTta = npd001PyyearTta;
	}

	@Column(name="npd001_pyyear_tta", nullable=true, length=50)	
	public String getNpd001PyyearTta() {
		return npd001PyyearTta;
	}

	public void setNpd001PyyearOntop(String npd001PyyearOntop) {
		this.npd001PyyearOntop = npd001PyyearOntop;
	}

	@Column(name="npd001_pyyear_ontop", nullable=true, length=50)	
	public String getNpd001PyyearOntop() {
		return npd001PyyearOntop;
	}

	public void setNpd001MonthContractAdj(String npd001MonthContractAdj) {
		this.npd001MonthContractAdj = npd001MonthContractAdj;
	}

	@Column(name="npd001_month_contract_adj", nullable=true, length=50)	
	public String getNpd001MonthContractAdj() {
		return npd001MonthContractAdj;
	}

	public void setNpd001MonthReconAdj(String npd001MonthReconAdj) {
		this.npd001MonthReconAdj = npd001MonthReconAdj;
	}

	@Column(name="npd001_month_recon_adj", nullable=true, length=50)	
	public String getNpd001MonthReconAdj() {
		return npd001MonthReconAdj;
	}

	public void setNpd001PyContractAdj(String npd001PyContractAdj) {
		this.npd001PyContractAdj = npd001PyContractAdj;
	}

	@Column(name="npd001_py_contract_adj", nullable=true, length=50)	
	public String getNpd001PyContractAdj() {
		return npd001PyContractAdj;
	}

	public void setNpd001PyReconAdj(String npd001PyReconAdj) {
		this.npd001PyReconAdj = npd001PyReconAdj;
	}

	@Column(name="npd001_py_recon_adj", nullable=true, length=50)	
	public String getNpd001PyReconAdj() {
		return npd001PyReconAdj;
	}

	public void setNpd001PyAuditAdj(String npd001PyAuditAdj) {
		this.npd001PyAuditAdj = npd001PyAuditAdj;
	}

	@Column(name="npd001_py_audit_adj", nullable=true, length=50)	
	public String getNpd001PyAuditAdj() {
		return npd001PyAuditAdj;
	}

	public void setNpd001Memo(String npd001Memo) {
		this.npd001Memo = npd001Memo;
	}

	@Column(name="npd001_memo", nullable=true, length=500)	
	public String getNpd001Memo() {
		return npd001Memo;
	}

	public void setNpd001Total(String npd001Total) {
		this.npd001Total = npd001Total;
	}

	@Column(name="npd001_total", nullable=true, length=50)	
	public String getNpd001Total() {
		return npd001Total;
	}

	public void setBdf001(String bdf001) {
		this.bdf001 = bdf001;
	}

	@Column(name="bdf001", nullable=true, length=500)	
	public String getBdf001() {
		return bdf001;
	}

	public void setBdf001CurrentMonthTta(String bdf001CurrentMonthTta) {
		this.bdf001CurrentMonthTta = bdf001CurrentMonthTta;
	}

	@Column(name="bdf001_current_month_tta", nullable=true, length=50)	
	public String getBdf001CurrentMonthTta() {
		return bdf001CurrentMonthTta;
	}

	public void setBdf001CurrentMonthOntop(String bdf001CurrentMonthOntop) {
		this.bdf001CurrentMonthOntop = bdf001CurrentMonthOntop;
	}

	@Column(name="bdf001_current_month_ontop", nullable=true, length=50)	
	public String getBdf001CurrentMonthOntop() {
		return bdf001CurrentMonthOntop;
	}

	public void setBdf001PyyearTta(String bdf001PyyearTta) {
		this.bdf001PyyearTta = bdf001PyyearTta;
	}

	@Column(name="bdf001_pyyear_tta", nullable=true, length=50)	
	public String getBdf001PyyearTta() {
		return bdf001PyyearTta;
	}

	public void setBdf001PyyearOntop(String bdf001PyyearOntop) {
		this.bdf001PyyearOntop = bdf001PyyearOntop;
	}

	@Column(name="bdf001_pyyear_ontop", nullable=true, length=50)	
	public String getBdf001PyyearOntop() {
		return bdf001PyyearOntop;
	}

	public void setBdf001MonthContractAdj(String bdf001MonthContractAdj) {
		this.bdf001MonthContractAdj = bdf001MonthContractAdj;
	}

	@Column(name="bdf001_month_contract_adj", nullable=true, length=50)	
	public String getBdf001MonthContractAdj() {
		return bdf001MonthContractAdj;
	}

	public void setBdf001MonthReconAdj(String bdf001MonthReconAdj) {
		this.bdf001MonthReconAdj = bdf001MonthReconAdj;
	}

	@Column(name="bdf001_month_recon_adj", nullable=true, length=50)	
	public String getBdf001MonthReconAdj() {
		return bdf001MonthReconAdj;
	}

	public void setBdf001PyContractAdj(String bdf001PyContractAdj) {
		this.bdf001PyContractAdj = bdf001PyContractAdj;
	}

	@Column(name="bdf001_py_contract_adj", nullable=true, length=50)	
	public String getBdf001PyContractAdj() {
		return bdf001PyContractAdj;
	}

	public void setBdf001PyReconAdj(String bdf001PyReconAdj) {
		this.bdf001PyReconAdj = bdf001PyReconAdj;
	}

	@Column(name="bdf001_py_recon_adj", nullable=true, length=50)	
	public String getBdf001PyReconAdj() {
		return bdf001PyReconAdj;
	}

	public void setBdf001PyAuditAdj(String bdf001PyAuditAdj) {
		this.bdf001PyAuditAdj = bdf001PyAuditAdj;
	}

	@Column(name="bdf001_py_audit_adj", nullable=true, length=50)	
	public String getBdf001PyAuditAdj() {
		return bdf001PyAuditAdj;
	}

	public void setBdf001Memo(String bdf001Memo) {
		this.bdf001Memo = bdf001Memo;
	}

	@Column(name="bdf001_memo", nullable=true, length=500)	
	public String getBdf001Memo() {
		return bdf001Memo;
	}

	public void setBdf001Total(String bdf001Total) {
		this.bdf001Total = bdf001Total;
	}

	@Column(name="bdf001_total", nullable=true, length=50)	
	public String getBdf001Total() {
		return bdf001Total;
	}

	public void setHbi001(String hbi001) {
		this.hbi001 = hbi001;
	}

	@Column(name="hbi001", nullable=true, length=500)	
	public String getHbi001() {
		return hbi001;
	}

	public void setHbi001CurrentMonthTta(String hbi001CurrentMonthTta) {
		this.hbi001CurrentMonthTta = hbi001CurrentMonthTta;
	}

	@Column(name="hbi001_current_month_tta", nullable=true, length=50)	
	public String getHbi001CurrentMonthTta() {
		return hbi001CurrentMonthTta;
	}

	public void setHbi001CurrentMonthOntop(String hbi001CurrentMonthOntop) {
		this.hbi001CurrentMonthOntop = hbi001CurrentMonthOntop;
	}

	@Column(name="hbi001_current_month_ontop", nullable=true, length=50)	
	public String getHbi001CurrentMonthOntop() {
		return hbi001CurrentMonthOntop;
	}

	public void setHbi001PyyearTta(String hbi001PyyearTta) {
		this.hbi001PyyearTta = hbi001PyyearTta;
	}

	@Column(name="hbi001_pyyear_tta", nullable=true, length=50)	
	public String getHbi001PyyearTta() {
		return hbi001PyyearTta;
	}

	public void setHbi001PyyearOntop(String hbi001PyyearOntop) {
		this.hbi001PyyearOntop = hbi001PyyearOntop;
	}

	@Column(name="hbi001_pyyear_ontop", nullable=true, length=50)	
	public String getHbi001PyyearOntop() {
		return hbi001PyyearOntop;
	}

	public void setHbi001MonthContractAdj(String hbi001MonthContractAdj) {
		this.hbi001MonthContractAdj = hbi001MonthContractAdj;
	}

	@Column(name="hbi001_month_contract_adj", nullable=true, length=50)	
	public String getHbi001MonthContractAdj() {
		return hbi001MonthContractAdj;
	}

	public void setHbi001MonthReconAdj(String hbi001MonthReconAdj) {
		this.hbi001MonthReconAdj = hbi001MonthReconAdj;
	}

	@Column(name="hbi001_month_recon_adj", nullable=true, length=50)	
	public String getHbi001MonthReconAdj() {
		return hbi001MonthReconAdj;
	}

	public void setHbi001PyContractAdj(String hbi001PyContractAdj) {
		this.hbi001PyContractAdj = hbi001PyContractAdj;
	}

	@Column(name="hbi001_py_contract_adj", nullable=true, length=50)	
	public String getHbi001PyContractAdj() {
		return hbi001PyContractAdj;
	}

	public void setHbi001PyReconAdj(String hbi001PyReconAdj) {
		this.hbi001PyReconAdj = hbi001PyReconAdj;
	}

	@Column(name="hbi001_py_recon_adj", nullable=true, length=50)	
	public String getHbi001PyReconAdj() {
		return hbi001PyReconAdj;
	}

	public void setHbi001PyAuditAdj(String hbi001PyAuditAdj) {
		this.hbi001PyAuditAdj = hbi001PyAuditAdj;
	}

	@Column(name="hbi001_py_audit_adj", nullable=true, length=50)	
	public String getHbi001PyAuditAdj() {
		return hbi001PyAuditAdj;
	}

	public void setHbi001Memo(String hbi001Memo) {
		this.hbi001Memo = hbi001Memo;
	}

	@Column(name="hbi001_memo", nullable=true, length=500)	
	public String getHbi001Memo() {
		return hbi001Memo;
	}

	public void setHbi001Total(String hbi001Total) {
		this.hbi001Total = hbi001Total;
	}

	@Column(name="hbi001_total", nullable=true, length=50)	
	public String getHbi001Total() {
		return hbi001Total;
	}

	public void setOth001(String oth001) {
		this.oth001 = oth001;
	}

	@Column(name="oth001", nullable=true, length=500)	
	public String getOth001() {
		return oth001;
	}

	public void setOth001CurrentMonthTta(String oth001CurrentMonthTta) {
		this.oth001CurrentMonthTta = oth001CurrentMonthTta;
	}

	@Column(name="oth001_current_month_tta", nullable=true, length=50)	
	public String getOth001CurrentMonthTta() {
		return oth001CurrentMonthTta;
	}

	public void setOth001CurrentMonthOntop(String oth001CurrentMonthOntop) {
		this.oth001CurrentMonthOntop = oth001CurrentMonthOntop;
	}

	@Column(name="oth001_current_month_ontop", nullable=true, length=50)	
	public String getOth001CurrentMonthOntop() {
		return oth001CurrentMonthOntop;
	}

	public void setOth001PyyearTta(String oth001PyyearTta) {
		this.oth001PyyearTta = oth001PyyearTta;
	}

	@Column(name="oth001_pyyear_tta", nullable=true, length=50)	
	public String getOth001PyyearTta() {
		return oth001PyyearTta;
	}

	public void setOth001PyyearOntop(String oth001PyyearOntop) {
		this.oth001PyyearOntop = oth001PyyearOntop;
	}

	@Column(name="oth001_pyyear_ontop", nullable=true, length=50)	
	public String getOth001PyyearOntop() {
		return oth001PyyearOntop;
	}

	public void setOth001OtherGl(String oth001OtherGl) {
		this.oth001OtherGl = oth001OtherGl;
	}

	@Column(name="oth001_other_gl", nullable=true, length=50)	
	public String getOth001OtherGl() {
		return oth001OtherGl;
	}

	public void setOth001OtherPurchase(String oth001OtherPurchase) {
		this.oth001OtherPurchase = oth001OtherPurchase;
	}

	@Column(name="oth001_other_purchase", nullable=true, length=50)	
	public String getOth001OtherPurchase() {
		return oth001OtherPurchase;
	}

	public void setOth001MonthContractAdj(String oth001MonthContractAdj) {
		this.oth001MonthContractAdj = oth001MonthContractAdj;
	}

	@Column(name="oth001_month_contract_adj", nullable=true, length=50)	
	public String getOth001MonthContractAdj() {
		return oth001MonthContractAdj;
	}

	public void setOth001MonthReconAdj(String oth001MonthReconAdj) {
		this.oth001MonthReconAdj = oth001MonthReconAdj;
	}

	@Column(name="oth001_month_recon_adj", nullable=true, length=50)	
	public String getOth001MonthReconAdj() {
		return oth001MonthReconAdj;
	}

	public void setOth001PyContractAdj(String oth001PyContractAdj) {
		this.oth001PyContractAdj = oth001PyContractAdj;
	}

	@Column(name="oth001_py_contract_adj", nullable=true, length=50)	
	public String getOth001PyContractAdj() {
		return oth001PyContractAdj;
	}

	public void setOth001PyReconAdj(String oth001PyReconAdj) {
		this.oth001PyReconAdj = oth001PyReconAdj;
	}

	@Column(name="oth001_py_recon_adj", nullable=true, length=50)	
	public String getOth001PyReconAdj() {
		return oth001PyReconAdj;
	}

	public void setOth001PyAuditAdj(String oth001PyAuditAdj) {
		this.oth001PyAuditAdj = oth001PyAuditAdj;
	}

	@Column(name="oth001_py_audit_adj", nullable=true, length=50)	
	public String getOth001PyAuditAdj() {
		return oth001PyAuditAdj;
	}

	public void setOth001Memo(String oth001Memo) {
		this.oth001Memo = oth001Memo;
	}

	@Column(name="oth001_memo", nullable=true, length=500)	
	public String getOth001Memo() {
		return oth001Memo;
	}

	public void setOth001Total(String oth001Total) {
		this.oth001Total = oth001Total;
	}

	@Column(name="oth001_total", nullable=true, length=50)	
	public String getOth001Total() {
		return oth001Total;
	}

	public void setMkt001(String mkt001) {
		this.mkt001 = mkt001;
	}

	@Column(name="mkt001", nullable=true, length=500)	
	public String getMkt001() {
		return mkt001;
	}

	public void setMkt001CurrentMonthTta(String mkt001CurrentMonthTta) {
		this.mkt001CurrentMonthTta = mkt001CurrentMonthTta;
	}

	@Column(name="mkt001_current_month_tta", nullable=true, length=50)	
	public String getMkt001CurrentMonthTta() {
		return mkt001CurrentMonthTta;
	}

	public void setMkt001CurrentMonthOntop(String mkt001CurrentMonthOntop) {
		this.mkt001CurrentMonthOntop = mkt001CurrentMonthOntop;
	}

	@Column(name="mkt001_current_month_ontop", nullable=true, length=50)	
	public String getMkt001CurrentMonthOntop() {
		return mkt001CurrentMonthOntop;
	}

	public void setMkt001PyyearTta(String mkt001PyyearTta) {
		this.mkt001PyyearTta = mkt001PyyearTta;
	}

	@Column(name="mkt001_pyyear_tta", nullable=true, length=50)	
	public String getMkt001PyyearTta() {
		return mkt001PyyearTta;
	}

	public void setMkt001PyyearOntop(String mkt001PyyearOntop) {
		this.mkt001PyyearOntop = mkt001PyyearOntop;
	}

	@Column(name="mkt001_pyyear_ontop", nullable=true, length=50)	
	public String getMkt001PyyearOntop() {
		return mkt001PyyearOntop;
	}

	public void setMkt001MonthContractAdj(String mkt001MonthContractAdj) {
		this.mkt001MonthContractAdj = mkt001MonthContractAdj;
	}

	@Column(name="mkt001_month_contract_adj", nullable=true, length=50)	
	public String getMkt001MonthContractAdj() {
		return mkt001MonthContractAdj;
	}

	public void setMkt001MonthReconAdj(String mkt001MonthReconAdj) {
		this.mkt001MonthReconAdj = mkt001MonthReconAdj;
	}

	@Column(name="mkt001_month_recon_adj", nullable=true, length=50)	
	public String getMkt001MonthReconAdj() {
		return mkt001MonthReconAdj;
	}

	public void setMkt001PyContractAdj(String mkt001PyContractAdj) {
		this.mkt001PyContractAdj = mkt001PyContractAdj;
	}

	@Column(name="mkt001_py_contract_adj", nullable=true, length=50)	
	public String getMkt001PyContractAdj() {
		return mkt001PyContractAdj;
	}

	public void setMkt001PyReconAdj(String mkt001PyReconAdj) {
		this.mkt001PyReconAdj = mkt001PyReconAdj;
	}

	@Column(name="mkt001_py_recon_adj", nullable=true, length=50)	
	public String getMkt001PyReconAdj() {
		return mkt001PyReconAdj;
	}

	public void setMkt001PyAuditAdj(String mkt001PyAuditAdj) {
		this.mkt001PyAuditAdj = mkt001PyAuditAdj;
	}

	@Column(name="mkt001_py_audit_adj", nullable=true, length=50)	
	public String getMkt001PyAuditAdj() {
		return mkt001PyAuditAdj;
	}

	public void setMkt001Memo(String mkt001Memo) {
		this.mkt001Memo = mkt001Memo;
	}

	@Column(name="mkt001_memo", nullable=true, length=500)	
	public String getMkt001Memo() {
		return mkt001Memo;
	}

	public void setMkt001Total(String mkt001Total) {
		this.mkt001Total = mkt001Total;
	}

	@Column(name="mkt001_total", nullable=true, length=50)	
	public String getMkt001Total() {
		return mkt001Total;
	}

	public void setNti001(String nti001) {
		this.nti001 = nti001;
	}

	@Column(name="nti001", nullable=true, length=500)	
	public String getNti001() {
		return nti001;
	}

	public void setNti001CurrentMonthTta(String nti001CurrentMonthTta) {
		this.nti001CurrentMonthTta = nti001CurrentMonthTta;
	}

	@Column(name="nti001_current_month_tta", nullable=true, length=50)	
	public String getNti001CurrentMonthTta() {
		return nti001CurrentMonthTta;
	}

	public void setNti001CurrentMonthOntop(String nti001CurrentMonthOntop) {
		this.nti001CurrentMonthOntop = nti001CurrentMonthOntop;
	}

	@Column(name="nti001_current_month_ontop", nullable=true, length=50)	
	public String getNti001CurrentMonthOntop() {
		return nti001CurrentMonthOntop;
	}

	public void setNti001PyyearTta(String nti001PyyearTta) {
		this.nti001PyyearTta = nti001PyyearTta;
	}

	@Column(name="nti001_pyyear_tta", nullable=true, length=50)	
	public String getNti001PyyearTta() {
		return nti001PyyearTta;
	}

	public void setNti001PyyearOntop(String nti001PyyearOntop) {
		this.nti001PyyearOntop = nti001PyyearOntop;
	}

	@Column(name="nti001_pyyear_ontop", nullable=true, length=50)	
	public String getNti001PyyearOntop() {
		return nti001PyyearOntop;
	}

	public void setNti001MonthContractAdj(String nti001MonthContractAdj) {
		this.nti001MonthContractAdj = nti001MonthContractAdj;
	}

	@Column(name="nti001_month_contract_adj", nullable=true, length=50)	
	public String getNti001MonthContractAdj() {
		return nti001MonthContractAdj;
	}

	public void setNti001MonthReconAdj(String nti001MonthReconAdj) {
		this.nti001MonthReconAdj = nti001MonthReconAdj;
	}

	@Column(name="nti001_month_recon_adj", nullable=true, length=50)	
	public String getNti001MonthReconAdj() {
		return nti001MonthReconAdj;
	}

	public void setNti001PyContractAdj(String nti001PyContractAdj) {
		this.nti001PyContractAdj = nti001PyContractAdj;
	}

	@Column(name="nti001_py_contract_adj", nullable=true, length=50)	
	public String getNti001PyContractAdj() {
		return nti001PyContractAdj;
	}

	public void setNti001PyReconAdj(String nti001PyReconAdj) {
		this.nti001PyReconAdj = nti001PyReconAdj;
	}

	@Column(name="nti001_py_recon_adj", nullable=true, length=50)	
	public String getNti001PyReconAdj() {
		return nti001PyReconAdj;
	}

	public void setNti001PyAuditAdj(String nti001PyAuditAdj) {
		this.nti001PyAuditAdj = nti001PyAuditAdj;
	}

	@Column(name="nti001_py_audit_adj", nullable=true, length=50)	
	public String getNti001PyAuditAdj() {
		return nti001PyAuditAdj;
	}

	public void setNti001Memo(String nti001Memo) {
		this.nti001Memo = nti001Memo;
	}

	@Column(name="nti001_memo", nullable=true, length=500)	
	public String getNti001Memo() {
		return nti001Memo;
	}

	public void setNti001Total(String nti001Total) {
		this.nti001Total = nti001Total;
	}

	@Column(name="nti001_total", nullable=true, length=50)	
	public String getNti001Total() {
		return nti001Total;
	}

	public void setBac001(String bac001) {
		this.bac001 = bac001;
	}

	@Column(name="bac001", nullable=true, length=500)	
	public String getBac001() {
		return bac001;
	}

	public void setBac001CurrentMonthTta(String bac001CurrentMonthTta) {
		this.bac001CurrentMonthTta = bac001CurrentMonthTta;
	}

	@Column(name="bac001_current_month_tta", nullable=true, length=50)	
	public String getBac001CurrentMonthTta() {
		return bac001CurrentMonthTta;
	}

	public void setBac001CurrentMonthOntop(String bac001CurrentMonthOntop) {
		this.bac001CurrentMonthOntop = bac001CurrentMonthOntop;
	}

	@Column(name="bac001_current_month_ontop", nullable=true, length=50)	
	public String getBac001CurrentMonthOntop() {
		return bac001CurrentMonthOntop;
	}

	public void setBac001PyyearTta(String bac001PyyearTta) {
		this.bac001PyyearTta = bac001PyyearTta;
	}

	@Column(name="bac001_pyyear_tta", nullable=true, length=50)	
	public String getBac001PyyearTta() {
		return bac001PyyearTta;
	}

	public void setBac001PyyearOntop(String bac001PyyearOntop) {
		this.bac001PyyearOntop = bac001PyyearOntop;
	}

	@Column(name="bac001_pyyear_ontop", nullable=true, length=50)	
	public String getBac001PyyearOntop() {
		return bac001PyyearOntop;
	}

	public void setBac001MonthContractAdj(String bac001MonthContractAdj) {
		this.bac001MonthContractAdj = bac001MonthContractAdj;
	}

	@Column(name="bac001_month_contract_adj", nullable=true, length=50)	
	public String getBac001MonthContractAdj() {
		return bac001MonthContractAdj;
	}

	public void setBac001MonthReconAdj(String bac001MonthReconAdj) {
		this.bac001MonthReconAdj = bac001MonthReconAdj;
	}

	@Column(name="bac001_month_recon_adj", nullable=true, length=50)	
	public String getBac001MonthReconAdj() {
		return bac001MonthReconAdj;
	}

	public void setBac001PyContractAdj(String bac001PyContractAdj) {
		this.bac001PyContractAdj = bac001PyContractAdj;
	}

	@Column(name="bac001_py_contract_adj", nullable=true, length=50)	
	public String getBac001PyContractAdj() {
		return bac001PyContractAdj;
	}

	public void setBac001PyReconAdj(String bac001PyReconAdj) {
		this.bac001PyReconAdj = bac001PyReconAdj;
	}

	@Column(name="bac001_py_recon_adj", nullable=true, length=50)	
	public String getBac001PyReconAdj() {
		return bac001PyReconAdj;
	}

	public void setBac001PyAuditAdj(String bac001PyAuditAdj) {
		this.bac001PyAuditAdj = bac001PyAuditAdj;
	}

	@Column(name="bac001_py_audit_adj", nullable=true, length=50)	
	public String getBac001PyAuditAdj() {
		return bac001PyAuditAdj;
	}

	public void setBac001Memo(String bac001Memo) {
		this.bac001Memo = bac001Memo;
	}

	@Column(name="bac001_memo", nullable=true, length=500)	
	public String getBac001Memo() {
		return bac001Memo;
	}

	public void setBac001Total(String bac001Total) {
		this.bac001Total = bac001Total;
	}

	@Column(name="bac001_total", nullable=true, length=50)	
	public String getBac001Total() {
		return bac001Total;
	}

	public void setOpsf01(String opsf01) {
		this.opsf01 = opsf01;
	}

	@Column(name="opsf01", nullable=true, length=500)	
	public String getOpsf01() {
		return opsf01;
	}

	public void setOpsf01CurrentMonthTta(String opsf01CurrentMonthTta) {
		this.opsf01CurrentMonthTta = opsf01CurrentMonthTta;
	}

	@Column(name="opsf01_current_month_tta", nullable=true, length=50)	
	public String getOpsf01CurrentMonthTta() {
		return opsf01CurrentMonthTta;
	}

	public void setOpsf01CurrentMonthOntop(String opsf01CurrentMonthOntop) {
		this.opsf01CurrentMonthOntop = opsf01CurrentMonthOntop;
	}

	@Column(name="opsf01_current_month_ontop", nullable=true, length=50)	
	public String getOpsf01CurrentMonthOntop() {
		return opsf01CurrentMonthOntop;
	}

	public void setOpsf01PyyearTta(String opsf01PyyearTta) {
		this.opsf01PyyearTta = opsf01PyyearTta;
	}

	@Column(name="opsf01_pyyear_tta", nullable=true, length=50)	
	public String getOpsf01PyyearTta() {
		return opsf01PyyearTta;
	}

	public void setOpsf01PyyearOntop(String opsf01PyyearOntop) {
		this.opsf01PyyearOntop = opsf01PyyearOntop;
	}

	@Column(name="opsf01_pyyear_ontop", nullable=true, length=50)	
	public String getOpsf01PyyearOntop() {
		return opsf01PyyearOntop;
	}

	public void setOpsf01MonthContractAdj(String opsf01MonthContractAdj) {
		this.opsf01MonthContractAdj = opsf01MonthContractAdj;
	}

	@Column(name="opsf01_month_contract_adj", nullable=true, length=50)	
	public String getOpsf01MonthContractAdj() {
		return opsf01MonthContractAdj;
	}

	public void setOpsf01MonthReconAdj(String opsf01MonthReconAdj) {
		this.opsf01MonthReconAdj = opsf01MonthReconAdj;
	}

	@Column(name="opsf01_month_recon_adj", nullable=true, length=50)	
	public String getOpsf01MonthReconAdj() {
		return opsf01MonthReconAdj;
	}

	public void setOpsf01PyContractAdj(String opsf01PyContractAdj) {
		this.opsf01PyContractAdj = opsf01PyContractAdj;
	}

	@Column(name="opsf01_py_contract_adj", nullable=true, length=50)	
	public String getOpsf01PyContractAdj() {
		return opsf01PyContractAdj;
	}

	public void setOpsf01PyReconAdj(String opsf01PyReconAdj) {
		this.opsf01PyReconAdj = opsf01PyReconAdj;
	}

	@Column(name="opsf01_py_recon_adj", nullable=true, length=50)	
	public String getOpsf01PyReconAdj() {
		return opsf01PyReconAdj;
	}

	public void setOpsf01PyAuditAdj(String opsf01PyAuditAdj) {
		this.opsf01PyAuditAdj = opsf01PyAuditAdj;
	}

	@Column(name="opsf01_py_audit_adj", nullable=true, length=50)	
	public String getOpsf01PyAuditAdj() {
		return opsf01PyAuditAdj;
	}

	public void setOpsf01Memo(String opsf01Memo) {
		this.opsf01Memo = opsf01Memo;
	}

	@Column(name="opsf01_memo", nullable=true, length=500)	
	public String getOpsf01Memo() {
		return opsf01Memo;
	}

	public void setOpsf01Total(String opsf01Total) {
		this.opsf01Total = opsf01Total;
	}

	@Column(name="opsf01_total", nullable=true, length=50)	
	public String getOpsf01Total() {
		return opsf01Total;
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

	public void setNaa001(String naa001) {
		this.naa001 = naa001;
	}

	@Column(name="naa001", nullable=true, length=500)	
	public String getNaa001() {
		return naa001;
	}

	public void setNaa001CurrentMonthTta(String naa001CurrentMonthTta) {
		this.naa001CurrentMonthTta = naa001CurrentMonthTta;
	}

	@Column(name="naa001_current_month_tta", nullable=true, length=50)	
	public String getNaa001CurrentMonthTta() {
		return naa001CurrentMonthTta;
	}

	public void setNaa001MonthContractAdj(String naa001MonthContractAdj) {
		this.naa001MonthContractAdj = naa001MonthContractAdj;
	}

	@Column(name="naa001_month_contract_adj", nullable=true, length=50)	
	public String getNaa001MonthContractAdj() {
		return naa001MonthContractAdj;
	}

	public void setNaa001MonthReconAdj(String naa001MonthReconAdj) {
		this.naa001MonthReconAdj = naa001MonthReconAdj;
	}

	@Column(name="naa001_month_recon_adj", nullable=true, length=50)	
	public String getNaa001MonthReconAdj() {
		return naa001MonthReconAdj;
	}

	public void setNaa001PyContractAdj(String naa001PyContractAdj) {
		this.naa001PyContractAdj = naa001PyContractAdj;
	}

	@Column(name="naa001_py_contract_adj", nullable=true, length=50)	
	public String getNaa001PyContractAdj() {
		return naa001PyContractAdj;
	}

	public void setNaa001PyReconAdj(String naa001PyReconAdj) {
		this.naa001PyReconAdj = naa001PyReconAdj;
	}

	@Column(name="naa001_py_recon_adj", nullable=true, length=50)	
	public String getNaa001PyReconAdj() {
		return naa001PyReconAdj;
	}

	public void setNaa001PyAuditAdj(String naa001PyAuditAdj) {
		this.naa001PyAuditAdj = naa001PyAuditAdj;
	}

	@Column(name="naa001_py_audit_adj", nullable=true, length=50)	
	public String getNaa001PyAuditAdj() {
		return naa001PyAuditAdj;
	}

	public void setNaa001Memo(String naa001Memo) {
		this.naa001Memo = naa001Memo;
	}

	@Column(name="naa001_memo", nullable=true, length=500)	
	public String getNaa001Memo() {
		return naa001Memo;
	}

	public void setNaa001Total(String naa001Total) {
		this.naa001Total = naa001Total;
	}

	@Column(name="naa001_total", nullable=true, length=50)	
	public String getNaa001Total() {
		return naa001Total;
	}

	public void setMfi001(String mfi001) {
		this.mfi001 = mfi001;
	}

	@Column(name="mfi001", nullable=true, length=500)	
	public String getMfi001() {
		return mfi001;
	}

	public void setMfi001CurrentMonthTta(String mfi001CurrentMonthTta) {
		this.mfi001CurrentMonthTta = mfi001CurrentMonthTta;
	}

	@Column(name="mfi001_current_month_tta", nullable=true, length=50)	
	public String getMfi001CurrentMonthTta() {
		return mfi001CurrentMonthTta;
	}

	public void setMfi001CurrentMonthOntop(String mfi001CurrentMonthOntop) {
		this.mfi001CurrentMonthOntop = mfi001CurrentMonthOntop;
	}

	@Column(name="mfi001_current_month_ontop", nullable=true, length=50)	
	public String getMfi001CurrentMonthOntop() {
		return mfi001CurrentMonthOntop;
	}

	public void setMfi001PyyearTta(String mfi001PyyearTta) {
		this.mfi001PyyearTta = mfi001PyyearTta;
	}

	@Column(name="mfi001_pyyear_tta", nullable=true, length=50)	
	public String getMfi001PyyearTta() {
		return mfi001PyyearTta;
	}

	public void setMfi001PyyearOntop(String mfi001PyyearOntop) {
		this.mfi001PyyearOntop = mfi001PyyearOntop;
	}

	@Column(name="mfi001_pyyear_ontop", nullable=true, length=50)	
	public String getMfi001PyyearOntop() {
		return mfi001PyyearOntop;
	}

	public void setMfi001MonthContractAdj(String mfi001MonthContractAdj) {
		this.mfi001MonthContractAdj = mfi001MonthContractAdj;
	}

	@Column(name="mfi001_month_contract_adj", nullable=true, length=50)	
	public String getMfi001MonthContractAdj() {
		return mfi001MonthContractAdj;
	}

	public void setMfi001MonthReconAdj(String mfi001MonthReconAdj) {
		this.mfi001MonthReconAdj = mfi001MonthReconAdj;
	}

	@Column(name="mfi001_month_recon_adj", nullable=true, length=50)	
	public String getMfi001MonthReconAdj() {
		return mfi001MonthReconAdj;
	}

	public void setMfi001PyContractAdj(String mfi001PyContractAdj) {
		this.mfi001PyContractAdj = mfi001PyContractAdj;
	}

	@Column(name="mfi001_py_contract_adj", nullable=true, length=50)	
	public String getMfi001PyContractAdj() {
		return mfi001PyContractAdj;
	}

	public void setMfi001PyReconAdj(String mfi001PyReconAdj) {
		this.mfi001PyReconAdj = mfi001PyReconAdj;
	}

	@Column(name="mfi001_py_recon_adj", nullable=true, length=50)	
	public String getMfi001PyReconAdj() {
		return mfi001PyReconAdj;
	}

	public void setMfi001PyAuditAdj(String mfi001PyAuditAdj) {
		this.mfi001PyAuditAdj = mfi001PyAuditAdj;
	}

	@Column(name="mfi001_py_audit_adj", nullable=true, length=50)	
	public String getMfi001PyAuditAdj() {
		return mfi001PyAuditAdj;
	}

	public void setMfi001Memo(String mfi001Memo) {
		this.mfi001Memo = mfi001Memo;
	}

	@Column(name="mfi001_memo", nullable=true, length=500)	
	public String getMfi001Memo() {
		return mfi001Memo;
	}

	public void setMfi001Total(String mfi001Total) {
		this.mfi001Total = mfi001Total;
	}

	@Column(name="mfi001_total", nullable=true, length=50)	
	public String getMfi001Total() {
		return mfi001Total;
	}

	public void setNpf001(String npf001) {
		this.npf001 = npf001;
	}

	@Column(name="npf001", nullable=true, length=500)	
	public String getNpf001() {
		return npf001;
	}

	public void setNpf001CurrentMonthTta(String npf001CurrentMonthTta) {
		this.npf001CurrentMonthTta = npf001CurrentMonthTta;
	}

	@Column(name="npf001_current_month_tta", nullable=true, length=50)	
	public String getNpf001CurrentMonthTta() {
		return npf001CurrentMonthTta;
	}

	public void setNpf001MonthContractAdj(String npf001MonthContractAdj) {
		this.npf001MonthContractAdj = npf001MonthContractAdj;
	}

	@Column(name="npf001_month_contract_adj", nullable=true, length=50)	
	public String getNpf001MonthContractAdj() {
		return npf001MonthContractAdj;
	}

	public void setNpf001MonthReconAdj(String npf001MonthReconAdj) {
		this.npf001MonthReconAdj = npf001MonthReconAdj;
	}

	@Column(name="npf001_month_recon_adj", nullable=true, length=50)	
	public String getNpf001MonthReconAdj() {
		return npf001MonthReconAdj;
	}

	public void setNpf001PyContractAdj(String npf001PyContractAdj) {
		this.npf001PyContractAdj = npf001PyContractAdj;
	}

	@Column(name="npf001_py_contract_adj", nullable=true, length=50)	
	public String getNpf001PyContractAdj() {
		return npf001PyContractAdj;
	}

	public void setNpf001PyReconAdj(String npf001PyReconAdj) {
		this.npf001PyReconAdj = npf001PyReconAdj;
	}

	@Column(name="npf001_py_recon_adj", nullable=true, length=50)	
	public String getNpf001PyReconAdj() {
		return npf001PyReconAdj;
	}

	public void setNpf001PyAuditAdj(String npf001PyAuditAdj) {
		this.npf001PyAuditAdj = npf001PyAuditAdj;
	}

	@Column(name="npf001_py_audit_adj", nullable=true, length=50)	
	public String getNpf001PyAuditAdj() {
		return npf001PyAuditAdj;
	}

	public void setNpf001Memo(String npf001Memo) {
		this.npf001Memo = npf001Memo;
	}

	@Column(name="npf001_memo", nullable=true, length=500)	
	public String getNpf001Memo() {
		return npf001Memo;
	}

	public void setNpf001Total(String npf001Total) {
		this.npf001Total = npf001Total;
	}

	@Column(name="npf001_total", nullable=true, length=50)	
	public String getNpf001Total() {
		return npf001Total;
	}

	public void setSri001(String sri001) {
		this.sri001 = sri001;
	}

	@Column(name="sri001", nullable=true, length=500)	
	public String getSri001() {
		return sri001;
	}

	public void setSri001CurrentMonthTta(String sri001CurrentMonthTta) {
		this.sri001CurrentMonthTta = sri001CurrentMonthTta;
	}

	@Column(name="sri001_current_month_tta", nullable=true, length=50)	
	public String getSri001CurrentMonthTta() {
		return sri001CurrentMonthTta;
	}

	public void setSri001MonthContractAdj(String sri001MonthContractAdj) {
		this.sri001MonthContractAdj = sri001MonthContractAdj;
	}

	@Column(name="sri001_month_contract_adj", nullable=true, length=50)	
	public String getSri001MonthContractAdj() {
		return sri001MonthContractAdj;
	}

	public void setSri001MonthReconAdj(String sri001MonthReconAdj) {
		this.sri001MonthReconAdj = sri001MonthReconAdj;
	}

	@Column(name="sri001_month_recon_adj", nullable=true, length=50)	
	public String getSri001MonthReconAdj() {
		return sri001MonthReconAdj;
	}

	public void setSri001PyContractAdj(String sri001PyContractAdj) {
		this.sri001PyContractAdj = sri001PyContractAdj;
	}

	@Column(name="sri001_py_contract_adj", nullable=true, length=50)	
	public String getSri001PyContractAdj() {
		return sri001PyContractAdj;
	}

	public void setSri001PyReconAdj(String sri001PyReconAdj) {
		this.sri001PyReconAdj = sri001PyReconAdj;
	}

	@Column(name="sri001_py_recon_adj", nullable=true, length=50)	
	public String getSri001PyReconAdj() {
		return sri001PyReconAdj;
	}

	public void setSri001PyAuditAdj(String sri001PyAuditAdj) {
		this.sri001PyAuditAdj = sri001PyAuditAdj;
	}

	@Column(name="sri001_py_audit_adj", nullable=true, length=50)	
	public String getSri001PyAuditAdj() {
		return sri001PyAuditAdj;
	}

	public void setSri001Memo(String sri001Memo) {
		this.sri001Memo = sri001Memo;
	}

	@Column(name="sri001_memo", nullable=true, length=500)	
	public String getSri001Memo() {
		return sri001Memo;
	}

	public void setSri001Total(String sri001Total) {
		this.sri001Total = sri001Total;
	}

	@Column(name="sri001_total", nullable=true, length=50)	
	public String getSri001Total() {
		return sri001Total;
	}

	public void setSsi001(String ssi001) {
		this.ssi001 = ssi001;
	}

	@Column(name="ssi001", nullable=true, length=500)	
	public String getSsi001() {
		return ssi001;
	}

	public void setSsi001CurrentMonthTta(String ssi001CurrentMonthTta) {
		this.ssi001CurrentMonthTta = ssi001CurrentMonthTta;
	}

	@Column(name="ssi001_current_month_tta", nullable=true, length=50)	
	public String getSsi001CurrentMonthTta() {
		return ssi001CurrentMonthTta;
	}

	public void setSsi001MonthContractAdj(String ssi001MonthContractAdj) {
		this.ssi001MonthContractAdj = ssi001MonthContractAdj;
	}

	@Column(name="ssi001_month_contract_adj", nullable=true, length=50)	
	public String getSsi001MonthContractAdj() {
		return ssi001MonthContractAdj;
	}

	public void setSsi001MonthReconAdj(String ssi001MonthReconAdj) {
		this.ssi001MonthReconAdj = ssi001MonthReconAdj;
	}

	@Column(name="ssi001_month_recon_adj", nullable=true, length=50)	
	public String getSsi001MonthReconAdj() {
		return ssi001MonthReconAdj;
	}

	public void setSsi001PyContractAdj(String ssi001PyContractAdj) {
		this.ssi001PyContractAdj = ssi001PyContractAdj;
	}

	@Column(name="ssi001_py_contract_adj", nullable=true, length=50)	
	public String getSsi001PyContractAdj() {
		return ssi001PyContractAdj;
	}

	public void setSsi001PyReconAdj(String ssi001PyReconAdj) {
		this.ssi001PyReconAdj = ssi001PyReconAdj;
	}

	@Column(name="ssi001_py_recon_adj", nullable=true, length=50)	
	public String getSsi001PyReconAdj() {
		return ssi001PyReconAdj;
	}

	public void setSsi001PyAuditAdj(String ssi001PyAuditAdj) {
		this.ssi001PyAuditAdj = ssi001PyAuditAdj;
	}

	@Column(name="ssi001_py_audit_adj", nullable=true, length=50)	
	public String getSsi001PyAuditAdj() {
		return ssi001PyAuditAdj;
	}

	public void setSsi001Memo(String ssi001Memo) {
		this.ssi001Memo = ssi001Memo;
	}

	@Column(name="ssi001_memo", nullable=true, length=500)	
	public String getSsi001Memo() {
		return ssi001Memo;
	}

	public void setSsi001Total(String ssi001Total) {
		this.ssi001Total = ssi001Total;
	}

	@Column(name="ssi001_total", nullable=true, length=50)	
	public String getSsi001Total() {
		return ssi001Total;
	}

	public void setCps001(String cps001) {
		this.cps001 = cps001;
	}

	@Column(name="cps001", nullable=true, length=500)	
	public String getCps001() {
		return cps001;
	}

	public void setCps001CurrentMonthTta(String cps001CurrentMonthTta) {
		this.cps001CurrentMonthTta = cps001CurrentMonthTta;
	}

	@Column(name="cps001_current_month_tta", nullable=true, length=50)	
	public String getCps001CurrentMonthTta() {
		return cps001CurrentMonthTta;
	}

	public void setCps001MonthContractAdj(String cps001MonthContractAdj) {
		this.cps001MonthContractAdj = cps001MonthContractAdj;
	}

	@Column(name="cps001_month_contract_adj", nullable=true, length=50)	
	public String getCps001MonthContractAdj() {
		return cps001MonthContractAdj;
	}

	public void setCps001MonthReconAdj(String cps001MonthReconAdj) {
		this.cps001MonthReconAdj = cps001MonthReconAdj;
	}

	@Column(name="cps001_month_recon_adj", nullable=true, length=50)	
	public String getCps001MonthReconAdj() {
		return cps001MonthReconAdj;
	}

	public void setCps001PyContractAdj(String cps001PyContractAdj) {
		this.cps001PyContractAdj = cps001PyContractAdj;
	}

	@Column(name="cps001_py_contract_adj", nullable=true, length=50)	
	public String getCps001PyContractAdj() {
		return cps001PyContractAdj;
	}

	public void setCps001PyReconAdj(String cps001PyReconAdj) {
		this.cps001PyReconAdj = cps001PyReconAdj;
	}

	@Column(name="cps001_py_recon_adj", nullable=true, length=50)	
	public String getCps001PyReconAdj() {
		return cps001PyReconAdj;
	}

	public void setCps001PyAuditAdj(String cps001PyAuditAdj) {
		this.cps001PyAuditAdj = cps001PyAuditAdj;
	}

	@Column(name="cps001_py_audit_adj", nullable=true, length=50)	
	public String getCps001PyAuditAdj() {
		return cps001PyAuditAdj;
	}

	public void setCps001Memo(String cps001Memo) {
		this.cps001Memo = cps001Memo;
	}

	@Column(name="cps001_memo", nullable=true, length=500)	
	public String getCps001Memo() {
		return cps001Memo;
	}

	public void setCps001Total(String cps001Total) {
		this.cps001Total = cps001Total;
	}

	@Column(name="cps001_total", nullable=true, length=50)	
	public String getCps001Total() {
		return cps001Total;
	}

	public void setOtf001CurrentMonthTta(String otf001CurrentMonthTta) {
		this.otf001CurrentMonthTta = otf001CurrentMonthTta;
	}

	@Column(name="otf001_current_month_tta", nullable=true, length=50)	
	public String getOtf001CurrentMonthTta() {
		return otf001CurrentMonthTta;
	}

	public void setOtf001MonthContractAdj(String otf001MonthContractAdj) {
		this.otf001MonthContractAdj = otf001MonthContractAdj;
	}

	@Column(name="otf001_month_contract_adj", nullable=true, length=50)	
	public String getOtf001MonthContractAdj() {
		return otf001MonthContractAdj;
	}

	public void setOtf001MonthReconAdj(String otf001MonthReconAdj) {
		this.otf001MonthReconAdj = otf001MonthReconAdj;
	}

	@Column(name="otf001_month_recon_adj", nullable=true, length=50)	
	public String getOtf001MonthReconAdj() {
		return otf001MonthReconAdj;
	}

	public void setOtf001PyContractAdj(String otf001PyContractAdj) {
		this.otf001PyContractAdj = otf001PyContractAdj;
	}

	@Column(name="otf001_py_contract_adj", nullable=true, length=50)	
	public String getOtf001PyContractAdj() {
		return otf001PyContractAdj;
	}

	public void setOtf001PyReconAdj(String otf001PyReconAdj) {
		this.otf001PyReconAdj = otf001PyReconAdj;
	}

	@Column(name="otf001_py_recon_adj", nullable=true, length=50)	
	public String getOtf001PyReconAdj() {
		return otf001PyReconAdj;
	}

	public void setOtf001PyAuditAdj(String otf001PyAuditAdj) {
		this.otf001PyAuditAdj = otf001PyAuditAdj;
	}

	@Column(name="otf001_py_audit_adj", nullable=true, length=50)	
	public String getOtf001PyAuditAdj() {
		return otf001PyAuditAdj;
	}

	public void setOtf001Total(String otf001Total) {
		this.otf001Total = otf001Total;
	}

	@Column(name="otf001_total", nullable=true, length=50)	
	public String getOtf001Total() {
		return otf001Total;
	}

	public void setDrg001(String drg001) {
		this.drg001 = drg001;
	}

	@Column(name="drg001", nullable=true, length=500)	
	public String getDrg001() {
		return drg001;
	}

	public void setDrg001CurrentMonthTta(String drg001CurrentMonthTta) {
		this.drg001CurrentMonthTta = drg001CurrentMonthTta;
	}

	@Column(name="drg001_current_month_tta", nullable=true, length=50)	
	public String getDrg001CurrentMonthTta() {
		return drg001CurrentMonthTta;
	}

	public void setDrg001CurrentMonthOntop(String drg001CurrentMonthOntop) {
		this.drg001CurrentMonthOntop = drg001CurrentMonthOntop;
	}

	@Column(name="drg001_current_month_ontop", nullable=true, length=50)	
	public String getDrg001CurrentMonthOntop() {
		return drg001CurrentMonthOntop;
	}

	public void setDrg001PyyearTta(String drg001PyyearTta) {
		this.drg001PyyearTta = drg001PyyearTta;
	}

	@Column(name="drg001_pyyear_tta", nullable=true, length=50)	
	public String getDrg001PyyearTta() {
		return drg001PyyearTta;
	}

	public void setDrg001PyyearOntop(String drg001PyyearOntop) {
		this.drg001PyyearOntop = drg001PyyearOntop;
	}

	@Column(name="drg001_pyyear_ontop", nullable=true, length=50)	
	public String getDrg001PyyearOntop() {
		return drg001PyyearOntop;
	}

	public void setDrg001MonthContractAdj(String drg001MonthContractAdj) {
		this.drg001MonthContractAdj = drg001MonthContractAdj;
	}

	@Column(name="drg001_month_contract_adj", nullable=true, length=50)	
	public String getDrg001MonthContractAdj() {
		return drg001MonthContractAdj;
	}

	public void setDrg001MonthReconAdj(String drg001MonthReconAdj) {
		this.drg001MonthReconAdj = drg001MonthReconAdj;
	}

	@Column(name="drg001_month_recon_adj", nullable=true, length=50)	
	public String getDrg001MonthReconAdj() {
		return drg001MonthReconAdj;
	}

	public void setDrg001PyContractAdj(String drg001PyContractAdj) {
		this.drg001PyContractAdj = drg001PyContractAdj;
	}

	@Column(name="drg001_py_contract_adj", nullable=true, length=50)	
	public String getDrg001PyContractAdj() {
		return drg001PyContractAdj;
	}

	public void setDrg001PyReconAdj(String drg001PyReconAdj) {
		this.drg001PyReconAdj = drg001PyReconAdj;
	}

	@Column(name="drg001_py_recon_adj", nullable=true, length=50)	
	public String getDrg001PyReconAdj() {
		return drg001PyReconAdj;
	}

	public void setDrg001PyAuditAdj(String drg001PyAuditAdj) {
		this.drg001PyAuditAdj = drg001PyAuditAdj;
	}

	@Column(name="drg001_py_audit_adj", nullable=true, length=50)	
	public String getDrg001PyAuditAdj() {
		return drg001PyAuditAdj;
	}

	public void setDrg001Memo(String drg001Memo) {
		this.drg001Memo = drg001Memo;
	}

	@Column(name="drg001_memo", nullable=true, length=500)	
	public String getDrg001Memo() {
		return drg001Memo;
	}

	public void setDrg001Total(String drg001Total) {
		this.drg001Total = drg001Total;
	}

	@Column(name="drg001_total", nullable=true, length=50)	
	public String getDrg001Total() {
		return drg001Total;
	}

	public void setDrh001(String drh001) {
		this.drh001 = drh001;
	}

	@Column(name="drh001", nullable=true, length=500)	
	public String getDrh001() {
		return drh001;
	}

	public void setDrh001CurrentMonthTta(String drh001CurrentMonthTta) {
		this.drh001CurrentMonthTta = drh001CurrentMonthTta;
	}

	@Column(name="drh001_current_month_tta", nullable=true, length=50)	
	public String getDrh001CurrentMonthTta() {
		return drh001CurrentMonthTta;
	}

	public void setDrh001CurrentMonthOntop(String drh001CurrentMonthOntop) {
		this.drh001CurrentMonthOntop = drh001CurrentMonthOntop;
	}

	@Column(name="drh001_current_month_ontop", nullable=true, length=50)	
	public String getDrh001CurrentMonthOntop() {
		return drh001CurrentMonthOntop;
	}

	public void setDrh001PyyearTta(String drh001PyyearTta) {
		this.drh001PyyearTta = drh001PyyearTta;
	}

	@Column(name="drh001_pyyear_tta", nullable=true, length=50)	
	public String getDrh001PyyearTta() {
		return drh001PyyearTta;
	}

	public void setDrh001PyyearOntop(String drh001PyyearOntop) {
		this.drh001PyyearOntop = drh001PyyearOntop;
	}

	@Column(name="drh001_pyyear_ontop", nullable=true, length=50)	
	public String getDrh001PyyearOntop() {
		return drh001PyyearOntop;
	}

	public void setDrh001MonthContractAdj(String drh001MonthContractAdj) {
		this.drh001MonthContractAdj = drh001MonthContractAdj;
	}

	@Column(name="drh001_month_contract_adj", nullable=true, length=50)	
	public String getDrh001MonthContractAdj() {
		return drh001MonthContractAdj;
	}

	public void setDrh001MonthReconAdj(String drh001MonthReconAdj) {
		this.drh001MonthReconAdj = drh001MonthReconAdj;
	}

	@Column(name="drh001_month_recon_adj", nullable=true, length=50)	
	public String getDrh001MonthReconAdj() {
		return drh001MonthReconAdj;
	}

	public void setDrh001PyContractAdj(String drh001PyContractAdj) {
		this.drh001PyContractAdj = drh001PyContractAdj;
	}

	@Column(name="drh001_py_contract_adj", nullable=true, length=50)	
	public String getDrh001PyContractAdj() {
		return drh001PyContractAdj;
	}

	public void setDrh001PyReconAdj(String drh001PyReconAdj) {
		this.drh001PyReconAdj = drh001PyReconAdj;
	}

	@Column(name="drh001_py_recon_adj", nullable=true, length=50)	
	public String getDrh001PyReconAdj() {
		return drh001PyReconAdj;
	}

	public void setDrh001PyAuditAdj(String drh001PyAuditAdj) {
		this.drh001PyAuditAdj = drh001PyAuditAdj;
	}

	@Column(name="drh001_py_audit_adj", nullable=true, length=50)	
	public String getDrh001PyAuditAdj() {
		return drh001PyAuditAdj;
	}

	public void setDrh001Memo(String drh001Memo) {
		this.drh001Memo = drh001Memo;
	}

	@Column(name="drh001_memo", nullable=true, length=500)	
	public String getDrh001Memo() {
		return drh001Memo;
	}

	public void setDrh001Total(String drh001Total) {
		this.drh001Total = drh001Total;
	}

	@Column(name="drh001_total", nullable=true, length=50)	
	public String getDrh001Total() {
		return drh001Total;
	}

	public void setDrb001(String drb001) {
		this.drb001 = drb001;
	}

	@Column(name="drb001", nullable=true, length=500)	
	public String getDrb001() {
		return drb001;
	}

	public void setDrb001CurrentMonthTta(String drb001CurrentMonthTta) {
		this.drb001CurrentMonthTta = drb001CurrentMonthTta;
	}

	@Column(name="drb001_current_month_tta", nullable=true, length=50)	
	public String getDrb001CurrentMonthTta() {
		return drb001CurrentMonthTta;
	}

	public void setDrb001CurrentMonthOntop(String drb001CurrentMonthOntop) {
		this.drb001CurrentMonthOntop = drb001CurrentMonthOntop;
	}

	@Column(name="drb001_current_month_ontop", nullable=true, length=50)	
	public String getDrb001CurrentMonthOntop() {
		return drb001CurrentMonthOntop;
	}

	public void setDrb001PyyearTta(String drb001PyyearTta) {
		this.drb001PyyearTta = drb001PyyearTta;
	}

	@Column(name="drb001_pyyear_tta", nullable=true, length=50)	
	public String getDrb001PyyearTta() {
		return drb001PyyearTta;
	}

	public void setDrb001PyyearOntop(String drb001PyyearOntop) {
		this.drb001PyyearOntop = drb001PyyearOntop;
	}

	@Column(name="drb001_pyyear_ontop", nullable=true, length=50)	
	public String getDrb001PyyearOntop() {
		return drb001PyyearOntop;
	}

	public void setDrb001MonthContractAdj(String drb001MonthContractAdj) {
		this.drb001MonthContractAdj = drb001MonthContractAdj;
	}

	@Column(name="drb001_month_contract_adj", nullable=true, length=50)	
	public String getDrb001MonthContractAdj() {
		return drb001MonthContractAdj;
	}

	public void setDrb001MonthReconAdj(String drb001MonthReconAdj) {
		this.drb001MonthReconAdj = drb001MonthReconAdj;
	}

	@Column(name="drb001_month_recon_adj", nullable=true, length=50)	
	public String getDrb001MonthReconAdj() {
		return drb001MonthReconAdj;
	}

	public void setDrb001PyContractAdj(String drb001PyContractAdj) {
		this.drb001PyContractAdj = drb001PyContractAdj;
	}

	@Column(name="drb001_py_contract_adj", nullable=true, length=50)	
	public String getDrb001PyContractAdj() {
		return drb001PyContractAdj;
	}

	public void setDrb001PyReconAdj(String drb001PyReconAdj) {
		this.drb001PyReconAdj = drb001PyReconAdj;
	}

	@Column(name="drb001_py_recon_adj", nullable=true, length=50)	
	public String getDrb001PyReconAdj() {
		return drb001PyReconAdj;
	}

	public void setDrb001PyAuditAdj(String drb001PyAuditAdj) {
		this.drb001PyAuditAdj = drb001PyAuditAdj;
	}

	@Column(name="drb001_py_audit_adj", nullable=true, length=50)	
	public String getDrb001PyAuditAdj() {
		return drb001PyAuditAdj;
	}

	public void setDrb001Memo(String drb001Memo) {
		this.drb001Memo = drb001Memo;
	}

	@Column(name="drb001_memo", nullable=true, length=500)	
	public String getDrb001Memo() {
		return drb001Memo;
	}

	public void setDrb001Total(String drb001Total) {
		this.drb001Total = drb001Total;
	}

	@Column(name="drb001_total", nullable=true, length=50)	
	public String getDrb001Total() {
		return drb001Total;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
