package com.sie.saaf.base.cms.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseCmsCarouselEntity_HI Entity Object
 * Sun Feb 25 10:43:23 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_cms_carousel")
public class BaseCmsCarouselEntity_HI {
    private Integer carouselId; //轮播图id
    private Integer orderSequence; //显示顺序
    private String carouselType; //轮播图类型,0图片;1flash;2代码3文字
    private String carouselName; //轮播图名称
    private String carouselPosition; //轮播图位置
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

	public void setCarouselId(Integer carouselId) {
		this.carouselId = carouselId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_CMS_CAROUSEL", sequenceName = "SEQ_BASE_CMS_CAROUSEL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_CMS_CAROUSEL", strategy = GenerationType.SEQUENCE)
	@Column(name = "carousel_id", nullable = false, length = 11)	
	public Integer getCarouselId() {
		return carouselId;
	}

	public void setOrderSequence(Integer orderSequence) {
		this.orderSequence = orderSequence;
	}

	@Column(name = "order_sequence", nullable = true, length = 11)	
	public Integer getOrderSequence() {
		return orderSequence;
	}

	public void setCarouselType(String carouselType) {
		this.carouselType = carouselType;
	}

	@Column(name = "carousel_type", nullable = false, length = 30)	
	public String getCarouselType() {
		return carouselType;
	}

	public void setCarouselName(String carouselName) {
		this.carouselName = carouselName;
	}

	@Column(name = "carousel_name", nullable = false, length = 240)	
	public String getCarouselName() {
		return carouselName;
	}

	public void setCarouselPosition(String carouselPosition) {
		this.carouselPosition = carouselPosition;
	}

	@Column(name = "carousel_position", nullable = false, length = 240)	
	public String getCarouselPosition() {
		return carouselPosition;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	@Column(name = "display_flag", nullable = false, length = 2)	
	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setPictureDesc(String pictureDesc) {
		this.pictureDesc = pictureDesc;
	}

	@Column(name = "picture_desc", nullable = true, length = 1000)	
	public String getPictureDesc() {
		return pictureDesc;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@Column(name = "picture_url", nullable = true, length = 300)	
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setUrlAddressess(String urlAddressess) {
		this.urlAddressess = urlAddressess;
	}

	@Column(name = "url_addressess", nullable = true, length = 300)	
	public String getUrlAddressess() {
		return urlAddressess;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name = "start_date_active", nullable = false, length = 0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name = "end_date_active", nullable = true, length = 0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = false, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = false, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = false, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = false, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "source_type", nullable = true, length = 30)	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}

	@Column(name = "source_type_name", nullable = true, length = 30)	
	public String getSourceTypeName() {
		return sourceTypeName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Column(name = "source_name", nullable = true, length = 30)	
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceNameId(String sourceNameId) {
		this.sourceNameId = sourceNameId;
	}

	@Column(name = "source_name_id", nullable = true, length = 30)	
	public String getSourceNameId() {
		return sourceNameId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
