package com.sie.watsons.base.supplement.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldAttrIgnore;

import javax.persistence.Transient;

/**
 * TtaSaTabLineEntity_HI_RO Entity Object
 * Tue Mar 31 15:25:17 CST 2020  Auto Generate
 */

public class TtaSaTabLineEntity_HI_RO {
    private Integer saTabLineId;
    private Integer saStdHeaderId;
    private Integer saTplTabLineId;
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

	private String gridPointValue;
	private String pointIdentiCode;
	private String pointExpression;

	@FieldAttrIgnore
	public static final String QUERY_SA_TAB_LINE = "select tstl.sa_tab_line_id,\n" +
			"       tstl.sa_std_header_id,\n" +
			"       tstl.sa_tpl_tab_line_id,\n" +
			"       tstl.row_name,\n" +
			"       tstl.col_name,\n" +
			"       tstl.x_point,\n" +
			"       tstl.y_point,\n" +
			"       tstl.point_value,\n" +
			"       tstl.version_num,\n" +
			"       tstl.creation_date,\n" +
			"       tstl.created_by,\n" +
			"       tstl.last_updated_by,\n" +
			"       tstl.last_update_date,\n" +
			"       tstl.last_update_login,\n" +
			"       tstl.grid_point_value\n" +
			"  from tta_sa_tab_line tstl where 1=1";

	public void setSaTabLineId(Integer saTabLineId) {
		this.saTabLineId = saTabLineId;
	}

	
	public Integer getSaTabLineId() {
		return saTabLineId;
	}

	public void setSaStdHeaderId(Integer saStdHeaderId) {
		this.saStdHeaderId = saStdHeaderId;
	}

	
	public Integer getSaStdHeaderId() {
		return saStdHeaderId;
	}

	public void setSaTplTabLineId(Integer saTplTabLineId) {
		this.saTplTabLineId = saTplTabLineId;
	}

	
	public Integer getSaTplTabLineId() {
		return saTplTabLineId;
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

	public String getGridPointValue() {
		return gridPointValue;
	}

	public void setGridPointValue(String gridPointValue) {
		this.gridPointValue = gridPointValue;
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
}
