package com.sie.saaf.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseReportHeaderEntity_HI Entity Object
 * Tue Dec 12 19:43:02 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_report_header")
public class BaseReportHeaderEntity_HI {
    private Integer reportHeaderId; //主键ID
    private String reportHeaderCode; //报表编码
    private String webScript; //页面脚本
    private String reportTitle; //报表标题
    private String reportType; //页面类型
	private Integer pageSize;  //列表行数
    private String countSql; //统计的查询SQL，如：select count(1) from dual
    private String querySql; //报表查询的sql语句
    private Integer dsId; //对应的数据源Id
    private String whereClause; //查询条件
    private String orderBy; //排序标识，如：id desc
    private String queryFlag; //默认是否执行查询标识：Y/N
    private String countFlag; //是否计算
    private String reportDesc; //描述
    private String chartsTitle; //charts标题
    private String chartsType; //charts类型
    private String chartsCode; //charts编码
    private String chartsName; //charts名称
    private String dataConversionScript; //数据转换脚本
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setReportHeaderId(Integer reportHeaderId) {
		this.reportHeaderId = reportHeaderId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_REPORT_HEADER", sequenceName = "SEQ_BASE_REPORT_HEADER", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_REPORT_HEADER", strategy = GenerationType.SEQUENCE)	
	@Column(name = "report_header_id", nullable = false, length = 11)	
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

	public void setWebScript(String webScript) {
		this.webScript = webScript;
	}

	@Column(name = "web_script", nullable = true, length = 3999)	
	public String getWebScript() {
		return webScript;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	@Column(name = "count_sql", nullable = true, length = 2000)	
	public String getCountSql() {
		return countSql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	@Column(name = "query_sql", nullable = true, length = 3999)	
	public String getQuerySql() {
		return querySql;
	}

	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	@Column(name = "ds_id", nullable = true, length = 11)	
	public Integer getDsId() {
		return dsId;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	@Column(name = "where_clause", nullable = true, length = 1000)	
	public String getWhereClause() {
		return whereClause;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "order_by", nullable = true, length = 500)	
	public String getOrderBy() {
		return orderBy;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

	@Column(name = "query_flag", nullable = true, length = 5)	
	public String getQueryFlag() {
		return queryFlag;
	}

	public void setCountFlag(String countFlag) {
		this.countFlag = countFlag;
	}

	@Column(name = "count_flag", nullable = true, length = 30)	
	public String getCountFlag() {
		return countFlag;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	@Column(name = "report_desc", nullable = true, length = 2000)
	public String getReportDesc() {
		return reportDesc;
	}

	public void setChartsTitle(String chartsTitle) {
		this.chartsTitle = chartsTitle;
	}

	@Column(name = "charts_title", nullable = true, length = 300)	
	public String getChartsTitle() {
		return chartsTitle;
	}

	public void setChartsType(String chartsType) {
		this.chartsType = chartsType;
	}

	@Column(name = "charts_type", nullable = true, length = 30)	
	public String getChartsType() {
		return chartsType;
	}

	public void setChartsCode(String chartsCode) {
		this.chartsCode = chartsCode;
	}

	@Column(name = "charts_code", nullable = true, length = 30)	
	public String getChartsCode() {
		return chartsCode;
	}

	public void setChartsName(String chartsName) {
		this.chartsName = chartsName;
	}

	@Column(name = "charts_name", nullable = true, length = 30)	
	public String getChartsName() {
		return chartsName;
	}

	public void setDataConversionScript(String dataConversionScript) {
		this.dataConversionScript = dataConversionScript;
	}

	@Column(name = "data_conversion_script", nullable = true, length = 4000)	
	public String getDataConversionScript() {
		return dataConversionScript;
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

	@Column(name = "page_size", nullable = true, length = 11)
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name = "report_title", nullable = true, length = 100)
	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	@Column(name = "report_Type", nullable = true, length = 50)
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
