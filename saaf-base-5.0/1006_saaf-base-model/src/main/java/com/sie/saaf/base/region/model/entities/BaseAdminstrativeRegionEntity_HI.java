package com.sie.saaf.base.region.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseAdminstrativeRegionEntity_HI Entity Object
 * Tue Nov 20 21:43:25 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_adminstrative_region")
public class BaseAdminstrativeRegionEntity_HI {
    private Long regionId; //行动区域Id
    private Long parentRegionId; //上级行政区域Id
    private String cityCode; //城市编码
    private String adCode; //区域编码
    private String regionName; //行政区域名称
    private String regionCenter; //城市中心点经纬度
    private String regionLevel; //行政区划级别,country:国家,province:省份（直辖市会在province和city显示）,city:市（直辖市会在province和city显示,district:区县,street:街道
    private String regionDescription; //行政区域描述
    private String isHotCity; //是否热门城市,Y/N
    private Integer deleteFlag; //删除标记
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //最后一次更新人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后一次更新时间
    private Integer lastUpdateLogin; //最后更新人登录帐号
    private Integer versionNum; //版本号
    private Integer operatorUserId;

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_ADMINSTRATIVE_REGION", sequenceName = "SEQ_BASE_ADMINSTRATIVE_REGION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_ADMINSTRATIVE_REGION", strategy = GenerationType.SEQUENCE)	
	@Column(name="region_id", nullable=false, length=20)	
	public Long getRegionId() {
		return regionId;
	}

	public void setParentRegionId(Long parentRegionId) {
		this.parentRegionId = parentRegionId;
	}

	@Column(name="parent_region_id", nullable=true, length=20)	
	public Long getParentRegionId() {
		return parentRegionId;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name="city_code", nullable=true, length=10)	
	public String getCityCode() {
		return cityCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	@Column(name="ad_code", nullable=true, length=10)	
	public String getAdCode() {
		return adCode;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name="region_name", nullable=true, length=50)	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionCenter(String regionCenter) {
		this.regionCenter = regionCenter;
	}

	@Column(name="region_center", nullable=true, length=50)	
	public String getRegionCenter() {
		return regionCenter;
	}

	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}

	@Column(name="region_level", nullable=true, length=20)	
	public String getRegionLevel() {
		return regionLevel;
	}

	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}

	@Column(name="region_description", nullable=true, length=400)	
	public String getRegionDescription() {
		return regionDescription;
	}

	public void setIsHotCity(String isHotCity) {
		this.isHotCity = isHotCity;
	}

	@Column(name="is_hot_city", nullable=true, length=5)	
	public String getIsHotCity() {
		return isHotCity;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
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
