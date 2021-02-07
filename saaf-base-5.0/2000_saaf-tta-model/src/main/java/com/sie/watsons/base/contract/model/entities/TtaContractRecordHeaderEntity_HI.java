package com.sie.watsons.base.contract.model.entities;

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
 * TtaContractRecordHeaderEntity_HI Entity Object
 * Tue Jun 23 16:46:30 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_CONTRACT_RECORD_HEADER")
public class TtaContractRecordHeaderEntity_HI {
    private Integer ttaContractRecordId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer year;
    private String logNo;
    private String deptCode;
    private String deptName;
    private String name;
    private Integer mobilePhone;
    private String appendixTwo;
    private String appendixThree;
    private String attributeA;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date applyDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;
    private String dataToFinance;
	private String orderNbr;
    private String vendorNbr;
    private String vendorName;
    private String remake;
	private Integer userId;
	private String saleType;
	private String saleTypeName;
	private String brand;
    private Integer operatorUserId;
    private String receiveStatus;

	public void setTtaContractRecordId(Integer ttaContractRecordId) {
		this.ttaContractRecordId = ttaContractRecordId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_CONTRACT_RECORD_HEADER", sequenceName = "SEQ_TTA_CONTRACT_RECORD_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_CONTRACT_RECORD_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="tta_contract_record_id", nullable=false, length=22)	
	public Integer getTtaContractRecordId() {
		return ttaContractRecordId;
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

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name="year", nullable=true, length=22)	
	public Integer getYear() {
		return year;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	@Column(name="log_no", nullable=true, length=50)	
	public String getLogNo() {
		return logNo;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=500)	
	public String getDeptName() {
		return deptName;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="name", nullable=true, length=50)	
	public String getName() {
		return name;
	}

	public void setMobilePhone(Integer mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name="mobile_phone", nullable=true, length=22)	
	public Integer getMobilePhone() {
		return mobilePhone;
	}

	public void setAppendixTwo(String appendixTwo) {
		this.appendixTwo = appendixTwo;
	}

	@Column(name="appendix_two", nullable=true, length=2)	
	public String getAppendixTwo() {
		return appendixTwo;
	}

	public void setAppendixThree(String appendixThree) {
		this.appendixThree = appendixThree;
	}

	@Column(name="appendix_three", nullable=true, length=2)	
	public String getAppendixThree() {
		return appendixThree;
	}

	public void setAttributeA(String attributeA) {
		this.attributeA = attributeA;
	}

	@Column(name="attribute_a", nullable=true, length=2)	
	public String getAttributeA() {
		return attributeA;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name="apply_date", nullable=true, length=7)	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Column(name="receive_date", nullable=true, length=7)	
	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setDataToFinance(String dataToFinance) {
		this.dataToFinance = dataToFinance;
	}

	@Column(name="data_to_finance", nullable=true, length=2)	
	public String getDataToFinance() {
		return dataToFinance;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=false, length=30)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=false, length=230)	
	public String getVendorName() {
		return vendorName;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	@Column(name="remake", nullable=true, length=1000)	
	public String getRemake() {
		return remake;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="user_id", nullable=true, length=22)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name="sale_type")
	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	@Column(name="sale_type_name")
	public String getSaleTypeName() {
		return saleTypeName;
	}

	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}

	@Column(name="brand")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="order_nbr")
	public String getOrderNbr() {
		return orderNbr;
	}

	public void setOrderNbr(String orderNbr) {
		this.orderNbr = orderNbr;
	}

	@Column(name="receive_status", nullable=true, length=10)
	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
}
