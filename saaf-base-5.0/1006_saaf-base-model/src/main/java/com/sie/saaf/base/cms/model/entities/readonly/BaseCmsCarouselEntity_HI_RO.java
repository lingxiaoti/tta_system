package com.sie.saaf.base.cms.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseCmsCarouselEntity_HI_RO Entity Object
 * Sun Feb 25 10:43:23 CST 2018  Auto Generate
 */

public class BaseCmsCarouselEntity_HI_RO {
	public static final String QUERY_CAROUSEL_SQL=" SELECT \n" +
			"\t carousel.carousel_id AS carouselId, \n" +
			"\t carousel.carousel_name AS carouselName, \n" +
			"\t carousel.order_sequence AS orderSequence, \n" +
			"\t carousel.carousel_type AS carouselType, \n" +
			"\t blv1.meaning AS carouselTypeDesc, \n" +
			"\t carousel.carousel_position AS carouselPosition, \n" +
			"\t carousel.display_flag AS displayFlag, \n" +
			"\t blv.meaning AS carouselPositionDesc, \n" +
			"\t carousel.picture_desc AS pictureDesc, \n" +
			"\t carousel.picture_url AS pictureUrl, \n" +
			"\t carousel.version_num AS versionNum, \n" +
			"\t carousel.source_type AS sourceType, \n" +
			"\t carousel.source_name AS sourceName, \n" +
			"\t carousel.source_name_id AS sourceNameId \n" +
			" FROM \n" +
			"\t base_cms_carousel carousel \n" +
			"\t LEFT JOIN base_lookup_values blv ON carousel.carousel_position = blv.lookup_code \n" +
			"\t LEFT JOIN base_lookup_values blv1 ON carousel.carousel_type = blv1.lookup_code \n" +
			"\t where 1=1 \n";
    private Integer carouselId; //轮播图id
    private Integer orderSequence; //显示顺序
    private String carouselType; //轮播图类型,0图片;1flash;2代码3文字
	private String carouselTypeDesc;
    private String carouselName; //轮播图名称
    private String carouselPosition; //轮播图位置
	private String carouselPositionDesc;
    private String displayFlag; //是否显示标志
    private String pictureDesc; //图片描述
    private String pictureUrl; //图片url地址
    private String urlAddressess; //超链接地址
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDateActive; //开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDateActive; //结束时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum; //版本号
    private String sourceType; //来源类型
    private String sourceTypeName;
    private String sourceName; //来源名称
    private String sourceNameId; //来源名称id
    private Integer operatorUserId;

	public String getCarouselTypeDesc() {
		return carouselTypeDesc;
	}

	public void setCarouselTypeDesc(String carouselTypeDesc) {
		this.carouselTypeDesc = carouselTypeDesc;
	}

	public String getCarouselPositionDesc() {
		return carouselPositionDesc;
	}

	public void setCarouselPositionDesc(String carouselPositionDesc) {
		this.carouselPositionDesc = carouselPositionDesc;
	}

	public void setCarouselId(Integer carouselId) {
		this.carouselId = carouselId;
	}

	
	public Integer getCarouselId() {
		return carouselId;
	}

	public void setOrderSequence(Integer orderSequence) {
		this.orderSequence = orderSequence;
	}

	
	public Integer getOrderSequence() {
		return orderSequence;
	}

	public void setCarouselType(String carouselType) {
		this.carouselType = carouselType;
	}

	
	public String getCarouselType() {
		return carouselType;
	}

	public void setCarouselName(String carouselName) {
		this.carouselName = carouselName;
	}

	
	public String getCarouselName() {
		return carouselName;
	}

	public void setCarouselPosition(String carouselPosition) {
		this.carouselPosition = carouselPosition;
	}

	
	public String getCarouselPosition() {
		return carouselPosition;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	
	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setPictureDesc(String pictureDesc) {
		this.pictureDesc = pictureDesc;
	}

	
	public String getPictureDesc() {
		return pictureDesc;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setUrlAddressess(String urlAddressess) {
		this.urlAddressess = urlAddressess;
	}

	
	public String getUrlAddressess() {
		return urlAddressess;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	
	public Date getEndDateActive() {
		return endDateActive;
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

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}

	
	public String getSourceTypeName() {
		return sourceTypeName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceNameId(String sourceNameId) {
		this.sourceNameId = sourceNameId;
	}

	
	public String getSourceNameId() {
		return sourceNameId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
