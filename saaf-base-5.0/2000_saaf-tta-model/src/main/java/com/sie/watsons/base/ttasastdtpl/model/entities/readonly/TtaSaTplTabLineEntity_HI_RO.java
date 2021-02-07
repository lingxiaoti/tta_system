package com.sie.watsons.base.ttasastdtpl.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSaTplTabLineEntity_HI_RO Entity Object
 * Wed Apr 01 14:16:55 CST 2020  Auto Generate
 */

public class TtaSaTplTabLineEntity_HI_RO {

	public static final String QUERY_LIST ="select tsttl.sa_tpl_tab_line_id,\n" +
			"       tsttl.sa_std_tpl_def_header_id,\n" +
			"       tsttl.row_name,\n" +
			"       tsttl.col_name,\n" +
			"       tsttl.x_point,\n" +
			"       tsttl.y_point,\n" +
			"       tsttl.point_value,\n" +
			"       tsttl.version_num,\n" +
			"       tsttl.creation_date,\n" +
			"       tsttl.created_by,\n" +
			"       tsttl.last_updated_by,\n" +
			"       tsttl.last_update_date,\n" +
			"       tsttl.last_update_login\n" +
			" from tta_sa_tpl_tab_line tsttl where 1=1 ";
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

    private String pointIdentiCode;
    private String pointExpression;
    private String gridPointValue;

	public void setSaTplTabLineId(Integer saTplTabLineId) {
		this.saTplTabLineId = saTplTabLineId;
	}

	
	public Integer getSaTplTabLineId() {
		return saTplTabLineId;
	}

	public void setSaStdTplDefHeaderId(Integer saStdTplDefHeaderId) {
		this.saStdTplDefHeaderId = saStdTplDefHeaderId;
	}

	
	public Integer getSaStdTplDefHeaderId() {
		return saStdTplDefHeaderId;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	
	public String getRowName() {
		return rowName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	
	public String getColName() {
		return colName;
	}

	public void setXPoint(String xPoint) {
		this.xPoint = xPoint;
	}

	
	public String getXPoint() {
		return xPoint;
	}

	public void setYPoint(Integer yPoint) {
		this.yPoint = yPoint;
	}

	
	public Integer getYPoint() {
		return yPoint;
	}

	public void setPointValue(String pointValue) {
		this.pointValue = pointValue;
	}

	
	public String getPointValue() {
		return pointValue;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getPointIdentiCode() {
		return pointIdentiCode;
	}

	public void setPointIdentiCode(String pointIdentiCode) {
		this.pointIdentiCode = pointIdentiCode;
	}

	public String getPointExpression() {
		return pointExpression;
	}

	public void setPointExpression(String pointExpression) {
		this.pointExpression = pointExpression;
	}

	public String getGridPointValue() {
		return gridPointValue;
	}

	public void setGridPointValue(String gridPointValue) {
		this.gridPointValue = gridPointValue;
	}
}
