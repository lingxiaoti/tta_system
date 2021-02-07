package com.sie.saaf.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseReportLineEntity_HI Entity Object
 * Tue Dec 12 19:43:03 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_report_line")
public class BaseReportLineEntity_HI {
    private Integer reportLineId; //主键ID
    private Integer reportHeaderId; //头表Id
    private String reportHeaderCode; //报表编码
    private Integer orderNo; //列排序编号
    private String columnCode; //列编码
    private String columnName; //列名称
    private Integer columnDisplayWidth; //列显示宽度
    private String columnDataType; //数据类型
    private String columnDisplayFlag; //列是否显示表示 Y表示显示 N表示不显示，默认为Y
    private String paramRequiredFlag; //查询条件是否必输 Y表示必输 N表示非必输，默认为N
    private String paramDisplayType; //查询条件显示类型
    private String paramDisplayName; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox界面显示的字段名
    private String paramUseCode; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox系统使用的字段名
    private String paramDataFromType; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox来源的渠道，lookup，profile
    private String paramDataFromTypeCode; //lookup，profile 类型code
    private String paramDefaultValue; //查询条件的默认值
    private String countFlag; //count_flag
    private String sumFlag; //sum_flag
    private String avgFlag; //avg_flag
    private String reportLineDesc; //report_line_desc
	private String rangeFlag;//范围查询标识
	private String conditionFlag;//查询字段标识
	private String conditionExpression;//查询条件表达式
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setReportLineId(Integer reportLineId) {
		this.reportLineId = reportLineId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_REPORT_LINE", sequenceName = "SEQ_BASE_REPORT_LINE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_REPORT_LINE", strategy = GenerationType.SEQUENCE)	
	@Column(name = "report_line_id", nullable = false, length = 11)	
	public Integer getReportLineId() {
		return reportLineId;
	}

	public void setReportHeaderId(Integer reportHeaderId) {
		this.reportHeaderId = reportHeaderId;
	}

	@Column(name = "report_header_id", nullable = true, length = 11)	
	public Integer getReportHeaderId() {
		return reportHeaderId;
	}

	public void setReportHeaderCode(String reportHeaderCode) {
		this.reportHeaderCode = reportHeaderCode;
	}

	@Column(name = "report_header_code", nullable = true, length = 30)	
	public String getReportHeaderCode() {
		return reportHeaderCode;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "order_no", nullable = true, length = 11)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	@Column(name = "column_code", nullable = true, length = 50)	
	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "column_name", nullable = true, length = 50)	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnDisplayWidth(Integer columnDisplayWidth) {
		this.columnDisplayWidth = columnDisplayWidth;
	}

	@Column(name = "column_display_width", nullable = true, length = 11)	
	public Integer getColumnDisplayWidth() {
		return columnDisplayWidth;
	}

	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}

	@Column(name = "column_data_type", nullable = true, length = 30)	
	public String getColumnDataType() {
		return columnDataType;
	}

	public void setColumnDisplayFlag(String columnDisplayFlag) {
		this.columnDisplayFlag = columnDisplayFlag;
	}

	@Column(name = "column_display_flag", nullable = true, length = 5)	
	public String getColumnDisplayFlag() {
		return columnDisplayFlag;
	}

	public void setParamRequiredFlag(String paramRequiredFlag) {
		this.paramRequiredFlag = paramRequiredFlag;
	}

	@Column(name = "param_required_flag", nullable = true, length = 5)	
	public String getParamRequiredFlag() {
		return paramRequiredFlag;
	}

	public void setParamDisplayType(String paramDisplayType) {
		this.paramDisplayType = paramDisplayType;
	}

	@Column(name = "param_display_type", nullable = true, length = 20)	
	public String getParamDisplayType() {
		return paramDisplayType;
	}

	public void setParamDisplayName(String paramDisplayName) {
		this.paramDisplayName = paramDisplayName;
	}

	@Column(name = "param_display_name", nullable = true, length = 200)	
	public String getParamDisplayName() {
		return paramDisplayName;
	}

	public void setParamUseCode(String paramUseCode) {
		this.paramUseCode = paramUseCode;
	}

	@Column(name = "param_use_code", nullable = true, length = 200)	
	public String getParamUseCode() {
		return paramUseCode;
	}

	public void setParamDataFromType(String paramDataFromType) {
		this.paramDataFromType = paramDataFromType;
	}

	@Column(name = "param_data_from_type", nullable = true, length = 30)	
	public String getParamDataFromType() {
		return paramDataFromType;
	}

	@Column(name = "param_data_from_type_code", nullable = true, length = 100)
	public String getParamDataFromTypeCode() {
		return paramDataFromTypeCode;
	}

	public void setParamDataFromTypeCode(String paramDataFromTypeCode) {
		this.paramDataFromTypeCode = paramDataFromTypeCode;
	}

	public void setParamDefaultValue(String paramDefaultValue) {
		this.paramDefaultValue = paramDefaultValue;
	}

	@Column(name = "param_default_value", nullable = true, length = 200)	
	public String getParamDefaultValue() {
		return paramDefaultValue;
	}

	public void setCountFlag(String countFlag) {
		this.countFlag = countFlag;
	}

	@Column(name = "count_flag", nullable = true, length = 30)	
	public String getCountFlag() {
		return countFlag;
	}

	public void setSumFlag(String sumFlag) {
		this.sumFlag = sumFlag;
	}

	@Column(name = "sum_flag", nullable = true, length = 30)	
	public String getSumFlag() {
		return sumFlag;
	}

	public void setAvgFlag(String avgFlag) {
		this.avgFlag = avgFlag;
	}

	@Column(name = "avg_flag", nullable = true, length = 30)	
	public String getAvgFlag() {
		return avgFlag;
	}

	public void setReportLineDesc(String reportLineDesc) {
		this.reportLineDesc = reportLineDesc;
	}

	@Column(name = "report_line_desc", nullable = true, length = 0)	
	public String getReportLineDesc() {
		return reportLineDesc;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name = "range_flag", nullable = true, length = 10)
	public String getRangeFlag() {
		return rangeFlag;
	}


	public void setRangeFlag(String rangeFlag) {
		this.rangeFlag = rangeFlag;
	}

	@Column(name = "condition_flag", nullable = true, length = 10)
	public String getConditionFlag() {
		return conditionFlag;
	}

	public void setConditionFlag(String conditionFlag) {
		this.conditionFlag = conditionFlag;
	}

	@Column(name = "condition_expression", nullable = true, length = 10)
	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
