package com.sie.watsons.base.pon.information.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import javax.persistence.*;

/**
 * EquPonQuotationAppReleEntity_HI Entity Object
 * Sun Oct 27 23:10:42 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_QUOTATION_APP_RELE")
public class EquPonQuotationAppReleEntity_HI {
    private Integer approvalRele;
    private Integer supplierId;
    private String supplierNumber;
	private String supplierName;
	private String projectNumber;
    private Integer quotationId;
	private Integer approvalId;
    private String quotationNumber;
    private Integer createdBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;

	public void setApprovalRele(Integer approvalRele) {
		this.approvalRele = approvalRele;
	}

	@Column(name="approval_rele", nullable=false, length=22)
	@Id
	@SequenceGenerator(name = "EQU_PON_QUOTATION_APPROVAL_S", sequenceName = "EQU_PON_QUOTATION_APPROVAL_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_QUOTATION_APPROVAL_S", strategy = GenerationType.SEQUENCE)
	public Integer getApprovalRele() {
		return approvalRele;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	@Column(name="supplier_number", nullable=true, length=30)	
	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}
	@Column(name="supplier_Name", nullable=true, length=30)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="quotation_id", nullable=true, length=22)
	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	@Column(name="quotation_number", nullable=true, length=30)	
	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	@Column(name="approval_Id", nullable=false, length=22)
	public Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}

	@Column(name="last_updated_by", nullable=false, length=22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@Column(name="project_Number", nullable=false, length=22)
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	@Column(name="last_update_date", nullable=true, length=7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)
	public Date getCreationDate() {
		return creationDate;
	}
	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
