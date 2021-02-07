package com.sie.watsons.base.questionnaire.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaQuestionNewMapDetailEntity_HI Entity Object
 * Wed Sep 11 19:39:56 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_question_new_map_detail")
public class TtaQuestionNewMapDetailEntity_HI {
    private Integer mapDetailId;
    private Integer proposalId;
    private String skuDesc;
    private BigDecimal cost;
    private BigDecimal retailPrice;
    private String normalGp;
    private BigDecimal promoPrice;
    private String promoGp;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String remark;

	public void setMapDetailId(Integer mapDetailId) {
		this.mapDetailId = mapDetailId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_QUESTION_NEW_MAP_DETAIL", sequenceName = "SEQ_TTA_QUESTION_NEW_MAP_DETAIL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_QUESTION_NEW_MAP_DETAIL", strategy = GenerationType.SEQUENCE)
	@Column(name="map_detail_id", nullable=true, length=22)	
	public Integer getMapDetailId() {
		return mapDetailId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}

	@Column(name="sku_desc", nullable=true, length=1000)
	public String getSkuDesc() {
		return skuDesc;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(name="cost", nullable=true, length=22)	
	public BigDecimal getCost() {
		return cost;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Column(name="retail_price", nullable=true, length=22)	
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setNormalGp(String normalGp) {
		this.normalGp = normalGp;
	}

	@Column(name="normal_gp", nullable=true, length=20)	
	public String getNormalGp() {
		return normalGp;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		this.promoPrice = promoPrice;
	}

	@Column(name="promo_price", nullable=true, length=22)	
	public BigDecimal getPromoPrice() {
		return promoPrice;
	}

	public void setPromoGp(String promoGp) {
		this.promoGp = promoGp;
	}

	@Column(name="promo_gp", nullable=true, length=20)	
	public String getPromoGp() {
		return promoGp;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
