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
 * TtaOiTaxEntity_HI Entity Object
 * Tue Oct 15 09:37:41 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_oi_tax")
public class TtaOiTaxEntity_HI {
    private Integer ttaOiTaxId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date yearMonth;
    private Integer tax;
    private String terms;
    private String familyPlaningFlag;
    private String chineseHerbalDrink;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setTtaOiTaxId(Integer ttaOiTaxId) {
		this.ttaOiTaxId = ttaOiTaxId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_OI_TAX", sequenceName="SEQ_TTA_OI_TAX", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_OI_TAX",strategy=GenerationType.SEQUENCE)
	@Column(name="tta_oi_tax_id", nullable=false, length=22)	
	public Integer getTtaOiTaxId() {
		return ttaOiTaxId;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Column(name="year_month", nullable=true, length=7)	
	public Date getYearMonth() {
		return yearMonth;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	@Column(name="tax", nullable=true, length=22)	
	public Integer getTax() {
		return tax;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	@Column(name="terms", nullable=true, length=500)	
	public String getTerms() {
		return terms;
	}

	public void setFamilyPlaningFlag(String familyPlaningFlag) {
		this.familyPlaningFlag = familyPlaningFlag;
	}

	@Column(name="family_planing_flag", nullable=true, length=2)	
	public String getFamilyPlaningFlag() {
		return familyPlaningFlag;
	}

	public void setChineseHerbalDrink(String chineseHerbalDrink) {
		this.chineseHerbalDrink = chineseHerbalDrink;
	}

	@Column(name="chinese_herbal_drink", nullable=true, length=2)	
	public String getChineseHerbalDrink() {
		return chineseHerbalDrink;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
