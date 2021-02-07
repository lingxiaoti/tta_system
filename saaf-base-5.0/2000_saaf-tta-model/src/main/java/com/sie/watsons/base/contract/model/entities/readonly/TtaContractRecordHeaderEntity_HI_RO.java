package com.sie.watsons.base.contract.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaContractRecordHeaderEntity_HI_RO Entity Object
 * Tue Jun 23 16:46:30 CST 2020  Auto Generate
 */

public class TtaContractRecordHeaderEntity_HI_RO {

	public static final String TTA_LIST_V = "SELECT TCR.TTA_CONTRACT_RECORD_ID,\n" +
			"       TCR.CREATION_DATE,\n" +
			"       TCR.CREATED_BY,\n" +
			"       TCR.LAST_UPDATED_BY,\n" +
			"       TCR.LAST_UPDATE_DATE,\n" +
			"       TCR.LAST_UPDATE_LOGIN,\n" +
			"       TCR.VERSION_NUM,\n" +
			"       TCR.YEAR,\n" +
			"       TCR.LOG_NO,\n" +
			"       TCR.DEPT_CODE,\n" +
			"       TCR.DEPT_NAME,\n" +
			"       TCR.NAME,\n" +
			"       TCR.MOBILE_PHONE,\n" +
			"       TCR.APPENDIX_TWO,\n" +
			"       TCR.APPENDIX_THREE,\n" +
			"       TCR.ATTRIBUTE_A,\n" +
			"       TCR.APPLY_DATE,\n" +
			"       TCR.RECEIVE_DATE,\n" +
			"       TCR.DATA_TO_FINANCE,\n" +
			"       TCR.VENDOR_NBR,\n" +
			"       TCR.VENDOR_NAME,\n" +
			"       TCR.USER_ID,\n" +
			"       TCR.SALE_TYPE_NAME,\n" +
			"       TCR.BRAND,\n" +
			"       TCR.REMAKE,\n" +
			"       TCR.ORDER_NBR,\n" +
			"       TCR.RECEIVE_STATUS\n" +
			"        FROM  TTA_CONTRACT_RECORD_HEADER TCR WHERE 1=1 ";
	private Integer ttaContractRecordId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
	private Integer userId;
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
    @JSONField(format="yyyy-MM-dd")
    private Date applyDate;
    @JSONField(format="yyyy-MM-dd")
    private Date receiveDate;
    private String dataToFinance;
    private String vendorNbr;
    private String vendorName;
    private String remake;
	private String saleType;
	private String saleTypeName;
	private String brand;
    private Integer operatorUserId;
	private String orderNbr;
	private String receiveStatus;

	public void setTtaContractRecordId(Integer ttaContractRecordId) {
		this.ttaContractRecordId = ttaContractRecordId;
	}

	
	public Integer getTtaContractRecordId() {
		return ttaContractRecordId;
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

	public void setYear(Integer year) {
		this.year = year;
	}

	
	public Integer getYear() {
		return year;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	
	public String getLogNo() {
		return logNo;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

	public void setMobilePhone(Integer mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	
	public Integer getMobilePhone() {
		return mobilePhone;
	}

	public void setAppendixTwo(String appendixTwo) {
		this.appendixTwo = appendixTwo;
	}

	
	public String getAppendixTwo() {
		return appendixTwo;
	}

	public void setAppendixThree(String appendixThree) {
		this.appendixThree = appendixThree;
	}

	
	public String getAppendixThree() {
		return appendixThree;
	}

	public void setAttributeA(String attributeA) {
		this.attributeA = attributeA;
	}

	
	public String getAttributeA() {
		return attributeA;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	
	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setDataToFinance(String dataToFinance) {
		this.dataToFinance = dataToFinance;
	}

	
	public String getDataToFinance() {
		return dataToFinance;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	
	public String getRemake() {
		return remake;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getSaleTypeName() {
		return saleTypeName;
	}

	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOrderNbr() {
		return orderNbr;
	}

	public void setOrderNbr(String orderNbr) {
		this.orderNbr = orderNbr;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
}
