package com.sie.watsons.base.pon.scoring.model.entities.readonly;

import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonScoringDetailEntity_HI_RO Entity Object
 * Sat Oct 19 11:38:59 CST 2019  Auto Generate
 */

public class EquPonScoringDetailEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	public  static  final String  QUERY_SQL =
			"SELECT t.scoring_detail_id scoringDetailId\n" +
					"      ,t.scoring_id scoringId\n" +
					"      ,t.scoring_type scoringType\n" +
					"      ,t.scoring_item scoringItem\n" +
					"      ,t.supplier_id supplierId\n" +
					"      ,t.score\n" +
					"      ,t.highest_score highestScore\n" +
					"      ,t.bench_mark benchMark\n" +
					"      ,t.annual_sales_amount annualSalesAmount\n" +
					"      ,t.participation\n" +
					"      ,t.line_number lineNumber\n" +
					"      ,t.line_lv lineLv\n" +
					"      ,t.weighting\n" +
					"      ,t.scoring_ranking scoringRanking\n" +
					"      ,t.quotation_price quotationPrice\n" +
					"      ,t.annual_sales_quantity annualSalesQuantity\n" +
					"      ,t.purchase_volume purchaseVolume\n" +
					"      ,t.version_num versionNum\n" +
					"      ,t.creation_date creationDate\n" +
					"      ,t.created_by createdBy\n" +
					"      ,t.last_updated_by lastUpdatedBy\n" +
					"      ,t.last_update_date lastUpdateDate\n" +
					"      ,t.last_update_login lastUpdateLogin\n" +
					"      ,t.attribute_category attributeCategory\n" +
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
					"FROM   equ_pon_scoring_detail t\n" +
					"      ,equ_pos_supplier_info p\n" +
					"where t.supplier_id = p.supplier_id(+)";

	public  static  final String  QUERY_BASE_SCORE_SQL =
			"SELECT psi.supplier_number supplierNumber\n" +
			"      ,psi.supplier_name supplierName\n" +
			"      ,psi.supplier_id supplierId\n" +
			"      ,nvl((SELECT t.score\n" +
			"           FROM   (SELECT c.score\n" +
			"                   FROM   equ_pos_contract_update_line c\n" +
			"                   WHERE  SYSDATE BETWEEN\n" +
			"                          nvl(c.begin_valid_date, SYSDATE - 1) AND\n" +
			"                          nvl(c.end_valid_date, SYSDATE + 1)\n" +
			"                   AND    c.supplier_number = psi.supplier_number\n" +
			"                   ORDER  BY c.last_update_date DESC) t\n" +
			"           WHERE  rownum = 1),\n" +
			"           0) commercialContractScore\n" +
			"      ,nvl((SELECT t.score\n" +
			"           FROM   (SELECT decode(count(1), 0, 0, 1) score\n" +
			"                   FROM   equ_pos_score_update_line c\n" +
			"                   WHERE  c.result = 'GREEN'\n" +
			"                   and    SYSDATE BETWEEN\n" +
			"                          nvl(c.begin_valid_date, SYSDATE - 1) AND\n" +
			"                          nvl(c.end_valid_date, SYSDATE + 1)\n" +
			"                   AND    c.supplier_number = psi.supplier_number\n" +
			"                   ORDER  BY c.last_update_date DESC) t\n" +
			"           WHERE  rownum = 1),\n" +
			"           0) currentPerformanceScore\n" +
			"      ,nvl((SELECT t.score\n" +
			"           FROM   (SELECT c.score\n" +
			"                   FROM   equ_pos_spend_update_line c\n" +
			"                   WHERE  SYSDATE BETWEEN\n" +
			"                          nvl(c.begin_valid_date, SYSDATE - 1) AND\n" +
			"                          nvl(c.end_valid_date, SYSDATE + 1)\n" +
			"                   AND    c.supplier_number = psi.supplier_number\n" +
			"                   ORDER  BY c.last_update_date DESC) t\n" +
			"           WHERE  rownum = 1),\n" +
			"           0) spendProfileScore\n" +
			"FROM   equ_pos_supplier_info psi\n" +
			"WHERE  psi.supplier_number = :varSupplierNumber";

	private Integer scoringDetailId;
    private Integer scoringId;
    private String scoringType;
    private String scoringItem;
    private Integer supplierId;
    private BigDecimal score;
	private BigDecimal highestScore;
	private BigDecimal benchMark;
	private BigDecimal annualSalesAmount;
	private String participation;
	private BigDecimal quotationPrice;
	private BigDecimal annualSalesQuantity;
	private BigDecimal purchaseVolume;
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
    private Integer operatorUserId;
    private String supplierNumber;
    private String supplierName;
    private BigDecimal commercialContractScore;
    private BigDecimal currentPerformanceScore;
    private BigDecimal spendProfileScore;
	private String lineNumber;
	private BigDecimal lineLv;
	private BigDecimal weighting;
	private BigDecimal scoringRanking;

	public void setScoringDetailId(Integer scoringDetailId) {
		this.scoringDetailId = scoringDetailId;
	}

	
	public Integer getScoringDetailId() {
		return scoringDetailId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	
	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringType(String scoringType) {
		this.scoringType = scoringType;
	}

	
	public String getScoringType() {
		return scoringType;
	}

	public void setScoringItem(String scoringItem) {
		this.scoringItem = scoringItem;
	}

	
	public String getScoringItem() {
		return scoringItem;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	
	public String getAttribute10() {
		return attribute10;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
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

	public BigDecimal getCommercialContractScore() {
		return commercialContractScore;
	}

	public void setCommercialContractScore(BigDecimal commercialContractScore) {
		this.commercialContractScore = commercialContractScore;
	}

	public BigDecimal getCurrentPerformanceScore() {
		return currentPerformanceScore;
	}

	public void setCurrentPerformanceScore(BigDecimal currentPerformanceScore) {
		this.currentPerformanceScore = currentPerformanceScore;
	}

	public BigDecimal getSpendProfileScore() {
		return spendProfileScore;
	}

	public void setSpendProfileScore(BigDecimal spendProfileScore) {
		this.spendProfileScore = spendProfileScore;
	}

	public BigDecimal getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(BigDecimal highestScore) {
		this.highestScore = highestScore;
	}

	public BigDecimal getBenchMark() {
		return benchMark;
	}

	public void setBenchMark(BigDecimal benchMark) {
		this.benchMark = benchMark;
	}

	public BigDecimal getAnnualSalesAmount() {
		return annualSalesAmount;
	}

	public void setAnnualSalesAmount(BigDecimal annualSalesAmount) {
		this.annualSalesAmount = annualSalesAmount;
	}

	public String getParticipation() {
		return participation;
	}

	public void setParticipation(String participation) {
		this.participation = participation;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public BigDecimal getLineLv() {
		return lineLv;
	}

	public void setLineLv(BigDecimal lineLv) {
		this.lineLv = lineLv;
	}

	public BigDecimal getWeighting() {
		return weighting;
	}

	public void setWeighting(BigDecimal weighting) {
		this.weighting = weighting;
	}

	public BigDecimal getScoringRanking() {
		return scoringRanking;
	}

	public void setScoringRanking(BigDecimal scoringRanking) {
		this.scoringRanking = scoringRanking;
	}

	public BigDecimal getQuotationPrice() {
		return quotationPrice;
	}

	public void setQuotationPrice(BigDecimal quotationPrice) {
		this.quotationPrice = quotationPrice;
	}

	public BigDecimal getAnnualSalesQuantity() {
		return annualSalesQuantity;
	}

	public void setAnnualSalesQuantity(BigDecimal annualSalesQuantity) {
		this.annualSalesQuantity = annualSalesQuantity;
	}

	public BigDecimal getPurchaseVolume() {
		return purchaseVolume;
	}

	public void setPurchaseVolume(BigDecimal purchaseVolume) {
		this.purchaseVolume = purchaseVolume;
	}
}
