package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaPogSpaceLineEntity_HI Entity Object
 * Tue Nov 12 12:08:33 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_POG_SPACE_LINE")
public class TtaPogSpaceLineEntity_HI {
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
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    private String promotionSection;
	private String isCreate;
	private String deptName;
    private Integer operatorUserId;

	public void setPogSpaceLineId(Integer pogSpaceLineId) {
		this.pogSpaceLineId = pogSpaceLineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_POG_SPACE_LINE", sequenceName = "SEQ_TTA_POG_SPACE_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_POG_SPACE_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="pog_space_line_id", nullable=false, length=22)	
	public Integer getPogSpaceLineId() {
		return pogSpaceLineId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=200)	
	public String getItemCode() {
		return itemCode;
	}

	public void setStoresNum(Integer storesNum) {
		this.storesNum = storesNum;
	}

	@Column(name="stores_num", nullable=true, length=22)	
	public Integer getStoresNum() {
		return storesNum;
	}

	public void setTimeDimension(Integer timeDimension) {
		this.timeDimension = timeDimension;
	}

	@Column(name="time_dimension", nullable=true, length=22)	
	public Integer getTimeDimension() {
		return timeDimension;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setPogYear(Integer pogYear) {
		this.pogYear = pogYear;
	}

	@Column(name="pog_year", nullable=true, length=22)	
	public Integer getPogYear() {
		return pogYear;
	}

	public void setPromotionLocation(String promotionLocation) {
		this.promotionLocation = promotionLocation;
	}

	@Column(name="promotion_location", nullable=true, length=200)	
	public String getPromotionLocation() {
		return promotionLocation;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	@Column(name="promotion_end_date", nullable=true, length=7)	
	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	@Column(name="promotion_start_date", nullable=true, length=7)	
	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}

	@Column(name="promotion_section", nullable=true, length=200)	
	public String getPromotionSection() {
		return promotionSection;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="is_create")
	public String getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	@Column(name="dept_name")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
