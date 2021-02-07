package com.sie.watsons.base.pon.itscoring.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonItScoringDetailEntity_HI Entity Object
 * Mon Jul 13 16:48:24 CST 2020  Auto Generate
 */
@Entity
@Table(name="equ_pon_it_scoring_maintain")
public class EquPonItScoringMaintainEntity_HI {
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

	public void setScoringMaintainId(Integer scoringMaintainId) {
		this.scoringMaintainId = scoringMaintainId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_IT_SCORING_MAINTAIN_S", sequenceName = "EQU_PON_IT_SCORING_MAINTAIN_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_IT_SCORING_MAINTAIN_S", strategy = GenerationType.SEQUENCE)
	@Column(name="scoring_maintain_id", nullable=false, length=22)
	public Integer getScoringMaintainId() {
		return scoringMaintainId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	@Column(name="scoring_id", nullable=false, length=22)
	public Integer getScoringId() {
		return scoringId;
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

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Column(name="line_number", nullable=true, length=20)
	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineLv(Integer lineLv) {
		this.lineLv = lineLv;
	}

	@Column(name="line_lv", nullable=true, length=22)
	public Integer getLineLv() {
		return lineLv;
	}

	public void setWeighting(BigDecimal weighting) {
		this.weighting = weighting;
	}

	@Column(name="weighting", nullable=true, length=22)
	public BigDecimal getWeighting() {
		return weighting;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="full_score", nullable=true, length=22)
	public BigDecimal getFullScore() {
		return fullScore;
	}

	public void setFullScore(BigDecimal fullScore) {
		this.fullScore = fullScore;
	}

	@Column(name="requirement", nullable=true, length=240)
	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	@Column(name="scoring_member_number", nullable=true, length=20)
	public String getScoringMemberNumber() {
		return scoringMemberNumber;
	}

	public void setScoringMemberNumber(String scoringMemberNumber) {
		this.scoringMemberNumber = scoringMemberNumber;
	}

	@Column(name="scoring_member_name", nullable=true, length=50)
	public String getScoringMemberName() {
		return scoringMemberName;
	}

	public void setScoringMemberName(String scoringMemberName) {
		this.scoringMemberName = scoringMemberName;
	}

	@Column(name="remark", nullable=true, length=240)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
