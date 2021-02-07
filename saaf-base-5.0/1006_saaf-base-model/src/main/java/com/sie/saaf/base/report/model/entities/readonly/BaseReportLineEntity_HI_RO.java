package com.sie.saaf.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Clob;
import java.util.Date;

/**
 * @author huangtao
 */
public class BaseReportLineEntity_HI_RO {

    public static final String query = "SELECT\n" +
            "  brl.report_line_id       AS reportLineId,\n" +
            "  brl.report_header_id     AS reportHeaderId,\n" +
            "  brl.report_header_code   AS reportHeaderCode,\n" +
            "  brl.report_line_desc     AS reportLineDesc,\n" +
            "  brl.order_no             AS orderNo,\n" +
            "  brl.column_code          AS columnCode,\n" +
            "  brl.column_name          AS columnName,\n" +
            "  brl.column_data_type     AS columnDataType,\n" +
            "  columData.meaning        AS columnDataTypeMeaning,\n" +
            "  brl.column_display_flag  AS columnDisplayFlag,\n" +
            "  brl.column_display_width AS columnDisplayWidth,\n" +
            "  brl.param_required_flag  AS paramRequiredFlag,\n" +
            "  brl.param_display_type   AS paramDisplayType,\n" +
            "  brl.param_display_name   AS paramDisplayName,\n" +
            "  paramData.meaning        AS paramDisplayTypeMeaning,\n" +
            "  brl.param_use_code       AS paramUseCode,\n" +
            "  brl.param_data_from_type AS paramDataFromType,\n" +
            "  brl.param_data_from_type_code AS paramDataFromTypeCode,\n" +
            "  dataFrom.meaning         AS paramDataFromTypeMeaning,\n" +
            "  brl.param_default_value  AS paramDefaultValue,\n" +
            "  brl.count_flag           AS countFlag,\n" +
            "  brl.sum_flag             AS sumFlg,\n" +
            "  brl.version_num          AS versionNum,\n" +
            "  brl.condition_flag       AS conditionFlag,\n" +
            "  brl.range_flag           AS rangeFlag,\n" +
            "  brl.condition_expression AS conditionExpression,\n" +
            "  brl.creation_date        AS creationDate,\n" +
            "  brl.last_update_date     AS lastUpdateDate,\n" +
            "  brl.avg_flag             AS avgFlg,\n" +
            "  brh.query_flag           AS queryFlag\n" +
            "FROM\n" +
            "  base_report_line brl\n" +
            "  JOIN base_report_header brh ON brl.report_header_id = brh.report_header_id\n" +
            "  JOIN base_lookup_values columData ON columData.lookup_type = 'DATA_TYPE'\n" +
            "                                            AND columData.lookup_code = brl.column_data_type\n" +
            "  JOIN base_lookup_values paramData ON paramData.lookup_type = 'DATA_TYPE'\n" +
            "                                            AND paramData.lookup_code = brl.param_display_type\n" +
            "  LEFT JOIN base_lookup_values dataFrom ON dataFrom.lookup_type = 'DATA_FROM_TYPE'\n" +
            "                                           AND dataFrom.lookup_code = brl.param_data_from_type where 1=1 ";
    private Integer reportLineId;
    private Integer reportHeaderId;
    private String reportHeaderCode;
    private Clob reportLineDesc;
    private Integer orderNo;
    private String columnCode;
    private String columnName;
    private String columnDataType;
    private String columnDataTypeMeaning;
    private String columnDisplayFlag;
    private Integer columnDisplayWidth;
    private String paramRequiredFlag;
    private String paramDisplayType;
    private String paramDisplayName;
    private String paramDisplayTypeMeaning;
    private String paramUseCode;
    private String paramDataFromType;
    private String paramDataFromTypeCode;
    private String paramDataFromTypeMeaning;
    private String paramDefaultValue;
    private String countFlag;
    private String sumFlg;
    private String avgFlg;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private String conditionFlag;
    private String rangeFlag;
    private String conditionExpression;
    private String queryFlag;

    public String getConditionFlag() {
        return conditionFlag;
    }

    public void setConditionFlag(String conditionFlag) {
        this.conditionFlag = conditionFlag;
    }

    public String getRangeFlag() {
        return rangeFlag;
    }

    public void setRangeFlag(String rangeFlag) {
        this.rangeFlag = rangeFlag;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public Integer getReportLineId() {
        return reportLineId;
    }

    public void setReportLineId(Integer reportLineId) {
        this.reportLineId = reportLineId;
    }

    public Integer getReportHeaderId() {
        return reportHeaderId;
    }

    public void setReportHeaderId(Integer reportHeaderId) {
        this.reportHeaderId = reportHeaderId;
    }

    public String getReportHeaderCode() {
        return reportHeaderCode;
    }

    public void setReportHeaderCode(String reportHeaderCode) {
        this.reportHeaderCode = reportHeaderCode;
    }

    public Clob getReportLineDesc() {
        return reportLineDesc;
    }

    public void setReportLineDesc(Clob reportLineDesc) {
        this.reportLineDesc = reportLineDesc;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    public String getColumnDataTypeMeaning() {
        return columnDataTypeMeaning;
    }

    public void setColumnDataTypeMeaning(String columnDataTypeMeaning) {
        this.columnDataTypeMeaning = columnDataTypeMeaning;
    }

    public Integer getColumnDisplayWidth() {
        return columnDisplayWidth;
    }

    public void setColumnDisplayWidth(Integer columnDisplayWidth) {
        this.columnDisplayWidth = columnDisplayWidth;
    }

    public String getColumnDisplayFlag() {
        return columnDisplayFlag;
    }

    public void setColumnDisplayFlag(String columnDisplayFlag) {
        this.columnDisplayFlag = columnDisplayFlag;
    }

    public String getParamRequiredFlag() {
        return paramRequiredFlag;
    }

    public void setParamRequiredFlag(String paramRequiredFlag) {
        this.paramRequiredFlag = paramRequiredFlag;
    }

    public String getParamDisplayType() {
        return paramDisplayType;
    }

    public void setParamDisplayType(String paramDisplayType) {
        this.paramDisplayType = paramDisplayType;
    }

    public String getParamDisplayName() {
        return paramDisplayName;
    }

    public void setParamDisplayName(String paramDisplayName) {
        this.paramDisplayName = paramDisplayName;
    }

    public String getParamDisplayTypeMeaning() {
        return paramDisplayTypeMeaning;
    }

    public void setParamDisplayTypeMeaning(String paramDisplayTypeMeaning) {
        this.paramDisplayTypeMeaning = paramDisplayTypeMeaning;
    }

    public String getParamUseCode() {
        return paramUseCode;
    }

    public void setParamUseCode(String paramUseCode) {
        this.paramUseCode = paramUseCode;
    }

    public String getParamDataFromType() {
        return paramDataFromType;
    }

    public void setParamDataFromType(String paramDataFromType) {
        this.paramDataFromType = paramDataFromType;
    }

    public String getParamDataFromTypeMeaning() {
        return paramDataFromTypeMeaning;
    }

    public void setParamDataFromTypeMeaning(String paramDataFromTypeMeaning) {
        this.paramDataFromTypeMeaning = paramDataFromTypeMeaning;
    }

    public String getParamDefaultValue() {
        return paramDefaultValue;
    }

    public void setParamDefaultValue(String paramDefaultValue) {
        this.paramDefaultValue = paramDefaultValue;
    }

    public String getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag;
    }

    public String getSumFlg() {
        return sumFlg;
    }

    public void setSumFlg(String sumFlg) {
        this.sumFlg = sumFlg;
    }

    public String getAvgFlg() {
        return avgFlg;
    }

    public void setAvgFlg(String avgFlg) {
        this.avgFlg = avgFlg;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getParamDataFromTypeCode() {
        return paramDataFromTypeCode;
    }

    public void setParamDataFromTypeCode(String paramDataFromTypeCode) {
        this.paramDataFromTypeCode = paramDataFromTypeCode;
    }

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }
}
