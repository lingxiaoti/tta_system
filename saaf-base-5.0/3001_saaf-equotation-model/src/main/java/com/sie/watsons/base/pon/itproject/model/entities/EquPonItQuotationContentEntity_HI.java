package com.sie.watsons.base.pon.itproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonItQuotationContentEntity_HI Entity Object
 * Mon Dec 16 23:18:01 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_IT_QUOTATION_CONTENT")
public class EquPonItQuotationContentEntity_HI {
    private Integer contentId;
    private Integer projectId;
    private String content;
    private String unit;
    private String specs;
    private String brand;
    private BigDecimal unitPrice;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal amountOfMoney;
    private BigDecimal taxRate;
    private String remark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
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
    private Integer sourceId;
    private Integer operatorUserId;

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_IT_QUOTATION_CONTENT_S", sequenceName = "EQU_PON_IT_QUOTATION_CONTENT_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_IT_QUOTATION_CONTENT_S", strategy = GenerationType.SEQUENCE)
	@Column(name="content_id", nullable=false, length=22)	
	public Integer getContentId() {
		return contentId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=true, length=22)	
	public Integer getProjectId() {
		return projectId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="content", nullable=true, length=2000)	
	public String getContent() {
		return content;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=50)	
	public String getUnit() {
		return unit;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	@Column(name="specs", nullable=true, length=100)	
	public String getSpecs() {
		return specs;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=50)	
	public String getBrand() {
		return brand;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name="unit_price", nullable=true, length=22)	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name="amount", nullable=true, length=22)	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(name="discount", nullable=true, length=22)	
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setAmountOfMoney(BigDecimal amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}

	@Column(name="amount_of_money", nullable=true, length=22)	
	public BigDecimal getAmountOfMoney() {
		return amountOfMoney;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name="tax_rate", nullable=true, length=22)	
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=4000)	
	public String getRemark() {
		return remark;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name="attribute_category", nullable=true, length=30)	
	public String getAttributeCategory() {
		return attributeCategory;
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

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name="source_id", nullable=true, length=22)	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
