package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaPogSpaceLineEntity_HI_RO Entity Object
 * Tue Nov 12 12:08:33 CST 2019  Auto Generate
 */

public class TtaPogSpaceLineEntity_HI_RO {

	public static final String  QUERY ="select tpsl.pog_space_line_id,\n" +
			"       tpsl.item_code,\n" +
			"       tpsl.stores_num,\n" +
			"       tpsl.time_dimension,\n" +
			"       tpsl.created_by,\n" +
			"       tpsl.last_updated_by,\n" +
			"       tpsl.last_update_date,\n" +
			"       tpsl.creation_date,\n" +
			"       tpsl.last_update_login,\n" +
			"       tpsl.dept_name,\n" +
			"       tpsl.version_num,\n" +
			"       tpsl.pog_year,\n" +
			"       nvl(tpsl.is_create,'N') isCreate,\n" +
			"       tpsl.promotion_location,\n" +
			"       tpsl.promotion_end_date,\n" +
			"       tpsl.promotion_start_date,\n" +
			"       tpsl.promotion_section ,\n" +
			"       bu.user_full_name createdByName\n" +
			"from   tta_pog_space_line tpsl\n" +
			"left join base_users bu on tpsl.created_by = bu.user_id \n" +
			"where 1=1 ";

	public static final String  CREATE_QUERY ="  select * from \n" +
			"    (select tpslv.pog_Space_Line_Id,tpslv.promotion_section,nvl(tpslv.is_create,'N') is_create, row_number() \n" +
			"    over(partition by tpslv.promotion_section order by nvl(tpslv.is_create,'N') desc ) rn\n" +
			"    from tta_pog_space_line tpslv ) tpsl where tpsl.rn=1 ";

    private Integer pogSpaceLineId;
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
    private Integer pogYear;
    private String promotionLocation;
    @JSONField(format="yyyy-MM-dd")
    private Date promotionEndDate;
    @JSONField(format="yyyy-MM-dd")
    private Date promotionStartDate;
    private String promotionSection;
	private String  createdByName ;
	private String isCreate;
	private String deptName;
    private Integer operatorUserId;

	public void setPogSpaceLineId(Integer pogSpaceLineId) {
		this.pogSpaceLineId = pogSpaceLineId;
	}

	
	public Integer getPogSpaceLineId() {
		return pogSpaceLineId;
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

	public void setPogYear(Integer pogYear) {
		this.pogYear = pogYear;
	}

	
	public Integer getPogYear() {
		return pogYear;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
