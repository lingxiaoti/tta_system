package com.sie.watsons.base.newbreedextend.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaNewbreedExtendHeaderEntity_HI Entity Object
 * Wed Jun 26 14:15:20 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_newbreed_extend_header")
public class TtaNewbreedExtendHeaderEntity_HI {
    private Integer newbreedExtendHId;
    private Integer proposalId;
    private String currentYearTerm;
    private String lastYearTerm;
    private java.math.BigDecimal includeTax;
    private java.math.BigDecimal excludeTax;
    private String collectRange;
    private String storeStyle;
    private String status;
    private String remark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String newPayment;
    private Integer newShopNum;
    private Integer operatorUserId;
	private String  year ;
	public void setNewbreedExtendHId(Integer newbreedExtendHId) {
		this.newbreedExtendHId = newbreedExtendHId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_TTA_NEWBREED_EXTEND_HEADER", sequenceName = "SEQ_TTA_NEWBREED_EXTEND_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_NEWBREED_EXTEND_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="newbreed_extend_h_id", nullable=false, length=22)	
	public Integer getNewbreedExtendHId() {
		return newbreedExtendHId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setCurrentYearTerm(String currentYearTerm) {
		this.currentYearTerm = currentYearTerm;
	}

	@Column(name="current_year_term", nullable=true, length=500)	
	public String getCurrentYearTerm() {
		return currentYearTerm;
	}

	public void setLastYearTerm(String lastYearTerm) {
		this.lastYearTerm = lastYearTerm;
	}

	@Column(name="last_year_term", nullable=true, length=500)	
	public String getLastYearTerm() {
		return lastYearTerm;
	}

	public void setIncludeTax(java.math.BigDecimal includeTax) {
		this.includeTax = includeTax;
	}

	@Column(name="include_tax", nullable=true, length=22)	
	public java.math.BigDecimal getIncludeTax() {
		return includeTax;
	}

	public void setExcludeTax(java.math.BigDecimal excludeTax) {
		this.excludeTax = excludeTax;
	}

	@Column(name="exclude_tax", nullable=true, length=22)	
	public java.math.BigDecimal getExcludeTax() {
		return excludeTax;
	}

	public void setCollectRange(String collectRange) {
		this.collectRange = collectRange;
	}

	@Column(name="collect_range", nullable=true, length=50)	
	public String getCollectRange() {
		return collectRange;
	}

	public void setStoreStyle(String storeStyle) {
		this.storeStyle = storeStyle;
	}

	@Column(name="store_style", nullable=true, length=100)	
	public String getStoreStyle() {
		return storeStyle;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=50)	
	public String getStatus() {
		return status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=500)	
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

	public void setNewPayment(String newPayment) {
		this.newPayment = newPayment;
	}

	@Column(name="new_payment", nullable=true, length=1)	
	public String getNewPayment() {
		return newPayment;
	}

	public void setNewShopNum(Integer newShopNum) {
		this.newShopNum = newShopNum;
	}

	@Column(name="new_shop_num", nullable=true, length=22)	
	public Integer getNewShopNum() {
		return newShopNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="year", nullable=true, length=30)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
