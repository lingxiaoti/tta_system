package com.sie.saaf.base.region.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author ZhangJun
 * @createTime 2018-11-20 11:15 PM
 * @description
 */
public class BaseAdminstrativeRegion_HI_RO {

	public static final String QUERY = "select\n" +
			"   region.region_id          as regionId,\n" +
			"   region.parent_region_id   as parentRegionId,\n" +
			"   (select region_name from base_adminstrative_region parent where parent.region_id=region.parent_region_id) as parentRegionName,\n" +
			"   region.city_code          as cityCode,\n" +
			"   region.ad_code            as adCode,\n" +
			"   region.region_name        as regionName,\n" +
			"   region.region_center      as regionCenter,\n" +
			"   region.region_level       as regionLevel,\n" +
			"   region.region_description as regionDescription,\n" +
			"   region.is_hot_city        as isHotCity,\n" +
			"   region.delete_flag        as deleteFlag,\n" +
			"   region.creation_date      as creationDate,\n" +
			"   region.created_by         as createdBy,\n" +
			"   region.last_updated_by    as lastUpdatedBy,\n" +
			"   region.last_update_date   as lastUpdateDate,\n" +
			"   region.last_update_login  as lastUpdateLogin,\n" +
			"   region.version_num        as versionNum\n" +
			"from base_adminstrative_region region\n" +
			"where 1 = 1 ";

	private Long regionId; //行动区域Id
	private Long parentRegionId; //上级行政区域Id
	private String parentRegionName; //上级行政区域名称
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

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getParentRegionId() {
		return parentRegionId;
	}

	public void setParentRegionId(Long parentRegionId) {
		this.parentRegionId = parentRegionId;
	}

	public String getParentRegionName() {
		return parentRegionName;
	}

	public void setParentRegionName(String parentRegionName) {
		this.parentRegionName = parentRegionName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCenter() {
		return regionCenter;
	}

	public void setRegionCenter(String regionCenter) {
		this.regionCenter = regionCenter;
	}

	public String getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}

	public String getRegionDescription() {
		return regionDescription;
	}

	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}

	public String getIsHotCity() {
		return isHotCity;
	}

	public void setIsHotCity(String isHotCity) {
		this.isHotCity = isHotCity;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
}
