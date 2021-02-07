package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOsdBaseLineEntity_HI_RO Entity Object
 * Wed Nov 20 17:18:37 CST 2019  Auto Generate
 */

public class TtaOsdBaseLineEntity_HI_RO {

	public static final String  QUERY = "select \n" +
			"\n" +
			"tobl.osd_base_line_id,\n" +
			"tobl.item_code,\n" +
			"tobl.stores_num,\n" +
			"tobl.time_dimension,\n" +
			"tobl.created_by,\n" +
			"tobl.last_updated_by,\n" +
			"tobl.last_update_date,\n" +
			"tobl.creation_date,\n" +
			"tobl.last_update_login,\n" +
			"tobl.version_num,\n" +
			"tobl.osd_year,\n" +
			"tobl.promotion_location,\n" +
			"tobl.promotion_end_date,\n" +
			"tobl.promotion_start_date,\n" +
			"tobl.promotion_section,\n" +
			"nvl(tobl.is_create,'N') isCreate,\n" +
			"tobl.dept_name,\n" +
			"bu.user_full_name createdByName\n" +
			"from  \n" +
			"\n" +
			"tta_osd_base_line tobl\n" +
			"left join base_users bu on tobl.created_by = bu.user_id \n" +
			"\n" +
			"where 1=1" ;


	public static final String  CREATE_QUERY ="   select *\n" +
			"     from (select tob.osd_base_line_id,\n" +
			"                  tob.promotion_section,\n" +
			"                  nvl(tob.is_create, 'N') is_create,\n" +
			"                  row_number() over(partition by tob.promotion_section order by nvl(tob.is_create, 'N') desc) rn\n" +
			"             from tta_osd_base_line tob) tobl\n" +
			"    where tobl.rn = 1";

    private Integer osdBaseLineId;
    private String itemCode;
    private Integer storesNum;
    private Integer timeDimension;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer osdYear;
    private String promotionLocation;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    private String promotionSection;
    private String isCreate;
	private String deptName;
    private Integer operatorUserId;
	private String  createdByName ;

	public void setOsdBaseLineId(Integer osdBaseLineId) {
		this.osdBaseLineId = osdBaseLineId;
	}

	
	public Integer getOsdBaseLineId() {
		return osdBaseLineId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setStoresNum(Integer storesNum) {
		this.storesNum = storesNum;
	}

	
	public Integer getStoresNum() {
		return storesNum;
	}

	public void setTimeDimension(Integer timeDimension) {
		this.timeDimension = timeDimension;
	}

	
	public Integer getTimeDimension() {
		return timeDimension;
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

	public void setOsdYear(Integer osdYear) {
		this.osdYear = osdYear;
	}

	
	public Integer getOsdYear() {
		return osdYear;
	}

	public void setPromotionLocation(String promotionLocation) {
		this.promotionLocation = promotionLocation;
	}

	
	public String getPromotionLocation() {
		return promotionLocation;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	
	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	
	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}

	
	public String getPromotionSection() {
		return promotionSection;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	
	public String getIsCreate() {
		return isCreate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
}
