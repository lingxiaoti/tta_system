package com.sie.watsons.base.api.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * VmiPromotionItemEntity_HI_RO Entity Object
 * Fri Dec 20 10:43:50 CST 2019  Auto Generate
 */

public class VmiPromotionItemEntity_HI_RO {
	public static final String QUERY_SQL="select \n" +
			"t.ID id,\n" +
			"t.PROMOTION_NO promotionNo,\n" +
			"t.OFFER_CODE offerCode,\n" +
			"t.PROMOTION_TYPE promotionType,\n" +
			"t.EFFECT_AREA effectArea,\n" +
			"t.ITEM_CODE itemCode,\n" +
			"t.BUYERNOTE buyernote,\n" +
			"t.PROMOTION_PACK promotionPack,\n" +
			"t.RETAIL_PRICE retailPrice,\n" +
			"t.PROMOTION_PRICE promotionPrice,\n" +
			"t.PROMOTION_MECH promotionMech,\n" +
			"t.OSD_TYPE osdType,\n" +
			"t.OFFER_DESC offerDesc,\n" +
			"t.OFFER_START_DATE offerStartDate,\n" +
			"t.OFFER_END_DATE offerEndDate,\n" +
			"t.DISPLAY display,\n" +
			"t.LIMITED_ITEM_FLAG limitedItemFlag,\n" +
			"t.CREATED_BY createdBy,\n" +
			"t.LAST_UPDATED_BY lastUpdatedBy,\n" +
			"t.CREATION_DATE creationDate,\n" +
			"t.LAST_UPDATE_LOGIN lastUpdateLogin,\n" +
			"t.VERSION_NUM versionNum,\n" +
			"t.LAST_UPDATE_DATE lastUpdateDate,\n" +
			"t.MEMBER_OFFER_FLAG memberOfferFlag,\n" +
			"t.MEMBER_OFFER memberOffer,\n" +
			"t.STEP_ID stepId,\n" +
			"t.OSD_MIN osdMin,\n" +
			"t.OSD_MAX_QTY osdMaxQty,\n" +
			"t.FREE_GOOD_FLAG freeGoodFlag,\n" +
			"t.DM_DISPLAY_FLAG dmDisplayFlag\n" +
			"from VMI_PROMOTION_ITEM t where 1=1\n";


    private Integer id;
    private String promotionNo;
    private String offerCode;
    private String promotionType;
    private String effectArea;
    private String itemCode;
    private String buyernote;
    private String promotionPack;
    private Integer retailPrice;
    private Integer promotionPrice;
    private String promotionMech;
    private String osdType;
    private String offerDesc;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date offerStartDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date offerEndDate;
    private String display;
    private String limitedItemFlag;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private String memberOfferFlag;
    private String memberOffer;
    private Integer stepId;
    private Integer osdMin;
    private Integer osdMaxQty;
    private String freeGoodFlag;
    private String dmDisplayFlag;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setPromotionNo(String promotionNo) {
		this.promotionNo = promotionNo;
	}

	
	public String getPromotionNo() {
		return promotionNo;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	
	public String getOfferCode() {
		return offerCode;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	
	public String getPromotionType() {
		return promotionType;
	}

	public void setEffectArea(String effectArea) {
		this.effectArea = effectArea;
	}

	
	public String getEffectArea() {
		return effectArea;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setBuyernote(String buyernote) {
		this.buyernote = buyernote;
	}

	
	public String getBuyernote() {
		return buyernote;
	}

	public void setPromotionPack(String promotionPack) {
		this.promotionPack = promotionPack;
	}

	
	public String getPromotionPack() {
		return promotionPack;
	}

	public void setRetailPrice(Integer retailPrice) {
		this.retailPrice = retailPrice;
	}

	
	public Integer getRetailPrice() {
		return retailPrice;
	}

	public void setPromotionPrice(Integer promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	
	public Integer getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionMech(String promotionMech) {
		this.promotionMech = promotionMech;
	}

	
	public String getPromotionMech() {
		return promotionMech;
	}

	public void setOsdType(String osdType) {
		this.osdType = osdType;
	}

	
	public String getOsdType() {
		return osdType;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}

	
	public String getOfferDesc() {
		return offerDesc;
	}

	public void setOfferStartDate(Date offerStartDate) {
		this.offerStartDate = offerStartDate;
	}

	
	public Date getOfferStartDate() {
		return offerStartDate;
	}

	public void setOfferEndDate(Date offerEndDate) {
		this.offerEndDate = offerEndDate;
	}

	
	public Date getOfferEndDate() {
		return offerEndDate;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	
	public String getDisplay() {
		return display;
	}

	public void setLimitedItemFlag(String limitedItemFlag) {
		this.limitedItemFlag = limitedItemFlag;
	}

	
	public String getLimitedItemFlag() {
		return limitedItemFlag;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setMemberOfferFlag(String memberOfferFlag) {
		this.memberOfferFlag = memberOfferFlag;
	}

	
	public String getMemberOfferFlag() {
		return memberOfferFlag;
	}

	public void setMemberOffer(String memberOffer) {
		this.memberOffer = memberOffer;
	}

	
	public String getMemberOffer() {
		return memberOffer;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	
	public Integer getStepId() {
		return stepId;
	}

	public void setOsdMin(Integer osdMin) {
		this.osdMin = osdMin;
	}

	
	public Integer getOsdMin() {
		return osdMin;
	}

	public void setOsdMaxQty(Integer osdMaxQty) {
		this.osdMaxQty = osdMaxQty;
	}

	
	public Integer getOsdMaxQty() {
		return osdMaxQty;
	}

	public void setFreeGoodFlag(String freeGoodFlag) {
		this.freeGoodFlag = freeGoodFlag;
	}

	
	public String getFreeGoodFlag() {
		return freeGoodFlag;
	}

	public void setDmDisplayFlag(String dmDisplayFlag) {
		this.dmDisplayFlag = dmDisplayFlag;
	}

	
	public String getDmDisplayFlag() {
		return dmDisplayFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
