package com.sie.saaf.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseReportChartsTypeEntity_HI Entity Object
 * Tue Dec 12 19:43:01 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_report_charts_type")
public class BaseReportChartsTypeEntity_HI {
    private Integer chartsId; //主键ID
    private String chartsCode; //charts编码
    private String chartsName; //charts名称
    private String chartsScript; //charts脚本
    private String dimensions; //dimensions
    private Integer dimensionLength; //dimension_length
    private String dataConversionScript; //数据转换脚本
    private String description; //描述
    private String attributeCategory; //attribute_category
    private String chartsType; //charts类型
    private String demoUrl; //参考例子链接
    private String chartsTitle; //charts标题
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setChartsId(Integer chartsId) {
		this.chartsId = chartsId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_REPORT_CHARTS_TYPE", sequenceName = "SEQ_BASE_REPORT_CHARTS_TYPE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_REPORT_CHARTS_TYPE", strategy = GenerationType.SEQUENCE)	
	@Column(name = "charts_id", nullable = false, length = 11)	
	public Integer getChartsId() {
		return chartsId;
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

	public void setChartsScript(String chartsScript) {
		this.chartsScript = chartsScript;
	}

	@Column(name = "charts_script", nullable = true, length = 4000)	
	public String getChartsScript() {
		return chartsScript;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	@Column(name = "dimensions", nullable = true, length = 300)	
	public String getDimensions() {
		return dimensions;
	}

	public void setDimensionLength(Integer dimensionLength) {
		this.dimensionLength = dimensionLength;
	}

	@Column(name = "dimension_length", nullable = true, length = 11)	
	public Integer getDimensionLength() {
		return dimensionLength;
	}

	public void setDataConversionScript(String dataConversionScript) {
		this.dataConversionScript = dataConversionScript;
	}

	@Column(name = "data_conversion_script", nullable = true, length = 4000)	
	public String getDataConversionScript() {
		return dataConversionScript;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "description", nullable = true, length = 250)	
	public String getDescription() {
		return description;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name = "attribute_category", nullable = true, length = 30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setChartsType(String chartsType) {
		this.chartsType = chartsType;
	}

	@Column(name = "charts_type", nullable = true, length = 30)	
	public String getChartsType() {
		return chartsType;
	}

	public void setDemoUrl(String demoUrl) {
		this.demoUrl = demoUrl;
	}

	@Column(name = "demo_url", nullable = true, length = 250)	
	public String getDemoUrl() {
		return demoUrl;
	}

	public void setChartsTitle(String chartsTitle) {
		this.chartsTitle = chartsTitle;
	}

	@Column(name = "charts_title", nullable = true, length = 300)	
	public String getChartsTitle() {
		return chartsTitle;
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

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
