package com.sie.watsons.base.pon.scoring.model.entities;

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
 * EquPonScoringDetailEntity_HI Entity Object
 * Sat Oct 19 11:38:59 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pon_scoring_detail")
public class EquPonScoringDetailEntity_HI {
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
    private String lineNumber;
    private BigDecimal lineLv;
    private BigDecimal weighting;
    private BigDecimal scoringRanking;
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

	public void setScoringDetailId(Integer scoringDetailId) {
		this.scoringDetailId = scoringDetailId;
	}

	@Id
	@SequenceGenerator(name = "equ_pon_scoring_detail_s", sequenceName = "equ_pon_scoring_detail_s", allocationSize = 1)
	@GeneratedValue(generator = "equ_pon_scoring_detail_s", strategy = GenerationType.SEQUENCE)
	@Column(name="scoring_detail_id", nullable=false, length=22)	
	public Integer getScoringDetailId() {
		return scoringDetailId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	@Column(name="scoring_id", nullable=false, length=22)	
	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringType(String scoringType) {
		this.scoringType = scoringType;
	}

	@Column(name="scoring_type", nullable=true, length=30)	
	public String getScoringType() {
		return scoringType;
	}

	public void setScoringItem(String scoringItem) {
		this.scoringItem = scoringItem;
	}

	@Column(name="scoring_item", nullable=true, length=50)	
	public String getScoringItem() {
		return scoringItem;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=22)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	@Column(name="score", nullable=true, length=22)
	public BigDecimal getScore() {
		return score;
	}

	public void setHighestScore(BigDecimal highestScore) {
		this.highestScore = highestScore;
	}

	@Column(name="highest_score", nullable=true, length=22)
	public BigDecimal getHighestScore() {
		return highestScore;
	}

	public void setBenchMark(BigDecimal benchMark) {
		this.benchMark = benchMark;
	}

	@Column(name="bench_mark", nullable=true, length=22)
	public BigDecimal getBenchMark() {
		return benchMark;
	}

	public void setAnnualSalesAmount(BigDecimal annualSalesAmount) {
		this.annualSalesAmount = annualSalesAmount;
	}

	@Column(name="annual_sales_amount", nullable=true, length=22)
	public BigDecimal getAnnualSalesAmount() {
		return annualSalesAmount;
	}

	public void setParticipation(String participation) {
		this.participation = participation;
	}

	@Column(name="participation", nullable=true, length=10)
	public String getParticipation() {
		return participation;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Column(name="line_number", nullable=true, length=10)
	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineLv(BigDecimal lineLv) {
		this.lineLv = lineLv;
	}

	@Column(name="line_lv", nullable=true, length=22)
	public BigDecimal getLineLv() {
		return lineLv;
	}

	public void setWeighting(BigDecimal weighting) {
		this.weighting = weighting;
	}

	@Column(name="weighting", nullable=true, length=22)
	public BigDecimal getWeighting() {
		return weighting;
	}

	public void setScoringRanking(BigDecimal scoringRanking) {
		this.scoringRanking = scoringRanking;
	}

	@Column(name="scoring_ranking", nullable=true, length=22)
	public BigDecimal getScoringRanking() {
		return scoringRanking;
	}

	@Column(name="quotation_price", nullable=true, length=22)
	public BigDecimal getQuotationPrice() {
		return quotationPrice;
	}

	public void setQuotationPrice(BigDecimal quotationPrice) {
		this.quotationPrice = quotationPrice;
	}

	@Column(name="annual_sales_quantity", nullable=true, length=22)
	public BigDecimal getAnnualSalesQuantity() {
		return annualSalesQuantity;
	}

	public void setAnnualSalesQuantity(BigDecimal annualSalesQuantity) {
		this.annualSalesQuantity = annualSalesQuantity;
	}

	@Column(name="purchase_volume", nullable=true, length=22)
	public BigDecimal getPurchaseVolume() {
		return purchaseVolume;
	}

	public void setPurchaseVolume(BigDecimal purchaseVolume) {
		this.purchaseVolume = purchaseVolume;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
