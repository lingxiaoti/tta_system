package com.sie.watsons.base.ob.model.entities;

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
 * PlmProductExceptionInfoEntity_HI Entity Object
 * Thu Aug 29 14:13:10 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_EXCEPTION_INFO")
public class PlmProductExceptionInfoEntity_HI {
    private Integer plmProductExceptionInfoId;
    private String productExceptionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private String productExceptionBillStatus;
    private String creator;
    private String exceptionSource;
    private String exceptionCategory;
    private String complaintShopNumber;
    private String cityOfShop;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date qaResponseTime;
    private String department;
    private String detail;
    private String indemnity;
	@JSONField(format="yyyy-MM-dd")
	private Date startDate;
	@JSONField(format="yyyy-MM-dd")
    private Date endDate;
    private String treatment;
    private String results;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setPlmProductExceptionInfoId(Integer plmProductExceptionInfoId) {
		this.plmProductExceptionInfoId = plmProductExceptionInfoId;
	}

	@Id	
	@SequenceGenerator(name="plmProductExceptionInfoEntity_HISeqGener", sequenceName="SEQ_PLM_PROD_EXCEPTION_INFO", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmProductExceptionInfoEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_product_exception_info_id", nullable=false, length=22)	
	public Integer getPlmProductExceptionInfoId() {
		return plmProductExceptionInfoId;
	}

	public void setProductExceptionNum(String productExceptionNum) {
		this.productExceptionNum = productExceptionNum;
	}

	@Column(name="product_exception_num", nullable=true, length=20)	
	public String getProductExceptionNum() {
		return productExceptionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setProductExceptionBillStatus(String productExceptionBillStatus) {
		this.productExceptionBillStatus = productExceptionBillStatus;
	}

	@Column(name="product_exception_bill_status", nullable=true, length=20)	
	public String getProductExceptionBillStatus() {
		return productExceptionBillStatus;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="creator", nullable=true, length=100)	
	public String getCreator() {
		return creator;
	}

	public void setExceptionSource(String exceptionSource) {
		this.exceptionSource = exceptionSource;
	}

	@Column(name="exception_source", nullable=true, length=100)	
	public String getExceptionSource() {
		return exceptionSource;
	}

	public void setExceptionCategory(String exceptionCategory) {
		this.exceptionCategory = exceptionCategory;
	}

	@Column(name="exception_category", nullable=true, length=200)	
	public String getExceptionCategory() {
		return exceptionCategory;
	}

	public void setComplaintShopNumber(String complaintShopNumber) {
		this.complaintShopNumber = complaintShopNumber;
	}

	@Column(name="complaint_shop_number", nullable=true, length=200)	
	public String getComplaintShopNumber() {
		return complaintShopNumber;
	}

	public void setCityOfShop(String cityOfShop) {
		this.cityOfShop = cityOfShop;
	}

	@Column(name="city_of_shop", nullable=true, length=200)	
	public String getCityOfShop() {
		return cityOfShop;
	}

	@Column(name="qa_response_time", nullable=true, length=7)
	public Date getQaResponseTime() {
		return qaResponseTime;
	}

	public void setQaResponseTime(Date qaResponseTime) {
		this.qaResponseTime = qaResponseTime;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="department", nullable=true, length=200)	
	public String getDepartment() {
		return department;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Column(name="detail", nullable=true, length=800)	
	public String getDetail() {
		return detail;
	}

	public void setIndemnity(String indemnity) {
		this.indemnity = indemnity;
	}

	@Column(name="indemnity", nullable=true, length=200)	
	public String getIndemnity() {
		return indemnity;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=7)	
	public Date getEndDate() {
		return endDate;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	@Column(name="treatment", nullable=true, length=100)	
	public String getTreatment() {
		return treatment;
	}

	public void setResults(String results) {
		this.results = results;
	}

	@Column(name="results", nullable=true, length=800)	
	public String getResults() {
		return results;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="start_date", nullable=true, length=7)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
