package com.sie.watsons.base.ttasastdtpl.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSaTplTabLineEntity_HI Entity Object
 * Wed Apr 01 14:16:55 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_SA_TPL_TAB_LINE")
public class TtaSaTplTabLineEntity_HI {
    private Integer saTplTabLineId;
    private Integer saStdTplDefHeaderId;
    private String rowName;
    private String colName;
    private String xPoint;
    private Integer yPoint;
    private String pointValue;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setSaTplTabLineId(Integer saTplTabLineId) {
		this.saTplTabLineId = saTplTabLineId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_TTA_SA_TPL_TAB_LINE", sequenceName = "SEQ_TTA_SA_TPL_TAB_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_SA_TPL_TAB_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="sa_tpl_tab_line_id", nullable=false, length=22)	
	public Integer getSaTplTabLineId() {
		return saTplTabLineId;
	}

	public void setSaStdTplDefHeaderId(Integer saStdTplDefHeaderId) {
		this.saStdTplDefHeaderId = saStdTplDefHeaderId;
	}

	@Column(name="sa_std_tpl_def_header_id", nullable=true, length=22)	
	public Integer getSaStdTplDefHeaderId() {
		return saStdTplDefHeaderId;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	@Column(name="row_name", nullable=false, length=200)	
	public String getRowName() {
		return rowName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@Column(name="col_name", nullable=false, length=200)	
	public String getColName() {
		return colName;
	}

	public void setXPoint(String xPoint) {
		this.xPoint = xPoint;
	}

	@Column(name="x_point", nullable=false, length=200)
	public String getXPoint() {
		return xPoint;
	}

	public void setYPoint(Integer yPoint) {
		this.yPoint = yPoint;
	}

	@Column(name="y_point", nullable=false, length=22)	
	public Integer getYPoint() {
		return yPoint;
	}

	public void setPointValue(String pointValue) {
		this.pointValue = pointValue;
	}

	@Column(name="point_value", nullable=true, length=500)
	public String getPointValue() {
		return pointValue;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
