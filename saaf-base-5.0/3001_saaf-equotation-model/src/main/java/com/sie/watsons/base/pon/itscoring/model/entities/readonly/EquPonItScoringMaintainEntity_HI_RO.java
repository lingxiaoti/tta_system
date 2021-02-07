package com.sie.watsons.base.pon.itscoring.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonItScoringDetailEntity_HI_RO Entity Object
 * Mon Jul 13 16:48:24 CST 2020  Auto Generate
 */

public class EquPonItScoringMaintainEntity_HI_RO {
	public  static  final String  QUERY_SQL = "select t.scoring_maintain_id  scoringMaintainId\n" +
			"      ,t.scoring_id           scoringId\n" +
			"      ,t.scoring_item         scoringItem\n" +
			"      ,t.supplier_id          supplierId\n" +
			"      ,t.scoring_member_number scoringMemberNumber\n" +
			"      ,t.scoring_member_name scoringMemberName\n" +
			"      ,t.score\n" +
			"      ,t.line_number          lineNumber\n" +
			"      ,t.line_lv              lineLv\n" +
			"      ,t.weighting\n" +
			"      ,t.full_score           fullScore\n" +
			"      ,t.requirement\n" +
			"      ,t.remark\n" +
			"      ,t.version_num          versionNum\n" +
			"      ,t.creation_date        creationDate\n" +
			"      ,t.created_by           createdBy\n" +
			"      ,t.last_update_date     lastUpdateDate\n" +
			"      ,t.last_updated_by      lastUpdatedBy\n" +
			"      ,t.last_update_login    lastUpdateLogin\n" +
			"      ,t.attribute_category   attributeCategory\n" +
			"      ,t.attribute1\n" +
			"      ,t.attribute2\n" +
			"      ,t.attribute3\n" +
			"      ,t.attribute4\n" +
			"      ,t.attribute5\n" +
			"      ,t.attribute6\n" +
			"      ,t.attribute7\n" +
			"      ,t.attribute8\n" +
			"      ,t.attribute9\n" +
			"      ,t.attribute10\n" +
			"      ,p.supplier_number supplierNumber\n" +
			"      ,p.supplier_name supplierName\n" +
			"from   equ_pon_it_scoring_maintain t\n" +
			"      ,equ_pos_supplier_info p\n" +
			"where t.supplier_id = p.supplier_id(+)";

	private Integer scoringMaintainId;
	private Integer scoringId;
	private String scoringItem;
	private Integer supplierId;
	private String scoringMemberNumber;
	private String scoringMemberName;
	private BigDecimal score;
	private String lineNumber;
	private Integer lineLv;
	private BigDecimal weighting;
	private BigDecimal fullScore;
	private String requirement;
	private String remark;
	private String supplierNumber;
	private String supplierName;
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

	public Integer getScoringMaintainId() {
		return scoringMaintainId;
	}

	public void setScoringMaintainId(Integer scoringMaintainId) {
		this.scoringMaintainId = scoringMaintainId;
	}

	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	public String getScoringItem() {
		return scoringItem;
	}

	public void setScoringItem(String scoringItem) {
		this.scoringItem = scoringItem;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getScoringMemberNumber() {
		return scoringMemberNumber;
	}

	public void setScoringMemberNumber(String scoringMemberNumber) {
		this.scoringMemberNumber = scoringMemberNumber;
	}

	public String getScoringMemberName() {
		return scoringMemberName;
	}

	public void setScoringMemberName(String scoringMemberName) {
		this.scoringMemberName = scoringMemberName;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getLineLv() {
		return lineLv;
	}

	public void setLineLv(Integer lineLv) {
		this.lineLv = lineLv;
	}

	public BigDecimal getWeighting() {
		return weighting;
	}

	public void setWeighting(BigDecimal weighting) {
		this.weighting = weighting;
	}

	public BigDecimal getFullScore() {
		return fullScore;
	}

	public void setFullScore(BigDecimal fullScore) {
		this.fullScore = fullScore;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
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

	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
