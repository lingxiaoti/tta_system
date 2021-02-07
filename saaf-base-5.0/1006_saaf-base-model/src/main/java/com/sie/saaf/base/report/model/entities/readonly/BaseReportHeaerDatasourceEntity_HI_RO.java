package com.sie.saaf.base.report.model.entities.readonly;

/**
 * @author huangtao
 */
public class BaseReportHeaerDatasourceEntity_HI_RO {
    public static final String query = " select brh.report_header_id AS reportHeaderId,\n" +
            "\tbrh.charts_type AS chartsType,\n" +
            "\tbrh.charts_title AS chartsTitle,\n" +
            "\tbrh.charts_name AS chartsName,\n" +
            "\tbrh.charts_code AS chartsCode,\n" +
            "\tbrh.count_flag AS countFlag,\n" +
            "\tbrh.query_sql AS querySql,\n" +
            "\tbrh.query_flag AS queryFlag,\n" +
            "\tbrh.data_conversion_script AS dataConversionScript,\n" +
            "\tbrh.order_by AS orderBy,\n" +
            "\tbrh.report_header_code AS reportHeaderCode,\n" +
            "\tbrh.report_desc AS reportDesc,\n" +
            "\tbrh.web_script AS webScript,\n" +
            "\tbrd.ds_id AS dsId,\n" +
            "\tbrd.ds_name AS dsName,\n" +
            "\tbrh.report_type AS reportType,\n" +
            "\treportType.meaning AS reportTypeMeaning,\n" +
            "\tbrh.page_size AS pageSize,\n" +
            "\tbrh.version_num AS versionNum  FROM \n" +
            "\tbase_report_header brh\n" +
            "\tLEFT JOIN base_lookup_values reportType ON reportType.lookup_type = 'REPORT_TYPE' and reportType.lookup_code=brh.report_type  \n" +
            "\tLEFT JOIN base_report_datasource brd ON brh.ds_id = brd.ds_id and brd.ds_type='DataBase' where 1=1 ";


    private Integer reportHeaderId; //主键
    private Integer reportGroupId;// 报表组id
    private String chartsType;//图表类型
    private String chartsName;//图表名
    private String chartsCode;//图表编码
    private String countFlag;//统计标识
    private String querySql;//查询sql
    private String queryFlag;// 查询数据标识
    private String dataConversionScript;
    private String orderBy;// 排序
    private String reportHeaderCode; // 报表编码
    private String reportDesc;// 报表描述
    private String webScript;//页面脚本
    private Integer dsId;//数据源id
    private String dsName;//数据源名称
    private Integer versionNum;//版本号
    private String chartsTitle;
    private String reportTypeMeaning;//报表类型
    private String reportType;//报表类型
    private Integer pageSize; //列表行数


    public String getReportTypeMeaning() {
        return reportTypeMeaning;
    }

    public void setReportTypeMeaning(String reportTypeMeaning) {
        this.reportTypeMeaning = reportTypeMeaning;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getReportHeaderId() {
        return reportHeaderId;
    }

    public void setReportHeaderId(Integer reportHeaderId) {
        this.reportHeaderId = reportHeaderId;
    }

    public Integer getReportGroupId() {
        return reportGroupId;
    }

    public void setReportGroupId(Integer reportGroupId) {
        this.reportGroupId = reportGroupId;
    }

    public String getChartsType() {
        return chartsType;
    }

    public void setChartsType(String chartsType) {
        this.chartsType = chartsType;
    }

    public String getChartsName() {
        return chartsName;
    }

    public void setChartsName(String chartsName) {
        this.chartsName = chartsName;
    }

    public String getChartsCode() {
        return chartsCode;
    }

    public void setChartsCode(String chartsCode) {
        this.chartsCode = chartsCode;
    }

    public String getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    public String getDataConversionScript() {
        return dataConversionScript;
    }

    public void setDataConversionScript(String dataConversionScript) {
        this.dataConversionScript = dataConversionScript;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getReportHeaderCode() {
        return reportHeaderCode;
    }

    public void setReportHeaderCode(String reportHeaderCode) {
        this.reportHeaderCode = reportHeaderCode;
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public String getWebScript() {
        return webScript;
    }

    public void setWebScript(String webScript) {
        this.webScript = webScript;
    }

    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getChartsTitle() {
        return chartsTitle;
    }

    public void setChartsTitle(String chartsTitle) {
        this.chartsTitle = chartsTitle;
    }
}
