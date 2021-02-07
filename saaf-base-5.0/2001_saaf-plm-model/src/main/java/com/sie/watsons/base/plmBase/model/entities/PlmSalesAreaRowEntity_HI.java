package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmSalesAreaRowEntity_HI Entity Object
 * Wed Nov 06 11:32:36 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_SALES_AREA_ROW")
public class PlmSalesAreaRowEntity_HI {
    private Integer plmSalesAreaRowId;
    private Integer plmSalesAreaId;
    private String areaCode;
    private String areaName;
    private String creator;
    private String groupCode;
    private String groupName;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
    private Integer operatorUserId;

	public void setPlmSalesAreaRowId(Integer plmSalesAreaRowId) {
		this.plmSalesAreaRowId = plmSalesAreaRowId;
	}

	@Id	
	@SequenceGenerator(name="plmSalesAreaRowEntity_HISeqGener", sequenceName="SEQ_PLM_SALES_AREA_ROW", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmSalesAreaRowEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_sales_area_row_id", nullable=false, length=22)	
	public Integer getPlmSalesAreaRowId() {
		return plmSalesAreaRowId;
	}

	public void setPlmSalesAreaId(Integer plmSalesAreaId) {
		this.plmSalesAreaId = plmSalesAreaId;
	}

	@Column(name="plm_sales_area_id", nullable=true, length=22)	
	public Integer getPlmSalesAreaId() {
		return plmSalesAreaId;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name="area_code", nullable=true, length=20)	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name="area_name", nullable=true, length=100)	
	public String getAreaName() {
		return areaName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="creator", nullable=true, length=100)	
	public String getCreator() {
		return creator;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=20)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=50)	
	public String getGroupName() {
		return groupName;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
