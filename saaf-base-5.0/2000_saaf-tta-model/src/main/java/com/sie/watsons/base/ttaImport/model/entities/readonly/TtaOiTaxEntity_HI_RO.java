package com.sie.watsons.base.ttaImport.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiTaxEntity_HI_RO Entity Object
 * Tue Oct 15 09:37:41 CST 2019  Auto Generate
 */

public class TtaOiTaxEntity_HI_RO {
	public static final String  QUERY ="select *  from tta_oi_tax tot where 1=1 " ;
    private Integer ttaOiTaxId;
    @JSONField(format="yyyy-MM")
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

	
	public Integer getTtaOiTaxId() {
		return ttaOiTaxId;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	
	public Date getYearMonth() {
		return yearMonth;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	
	public Integer getTax() {
		return tax;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	
	public String getTerms() {
		return terms;
	}

	public void setFamilyPlaningFlag(String familyPlaningFlag) {
		this.familyPlaningFlag = familyPlaningFlag;
	}

	
	public String getFamilyPlaningFlag() {
		return familyPlaningFlag;
	}

	public void setChineseHerbalDrink(String chineseHerbalDrink) {
		this.chineseHerbalDrink = chineseHerbalDrink;
	}

	
	public String getChineseHerbalDrink() {
		return chineseHerbalDrink;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
