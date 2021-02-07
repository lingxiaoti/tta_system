package com.sie.saaf.base.api.model.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BaseApiManagementEntity_HI Entity Object
 * Mon Dec 04 11:31:35 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_api_management")
public class BaseApiManagementEntity_HI{
    private Integer apiId; //主键ID
    private String interfaceName; //API名称
    private String requestMode; //请求方式：Post/Get
    private String apiStatus; //状态
    private String urlAddress; //服务地址
    private String developer; //开发人员
    private String apiDesc; //详细描述
    private String requestParam; //请求参数
    private String requestParamDict; //请求参数描述
    private String responseParam; //返回参数
    private String responseParamDict; //返回参数描述
    private String centerName; //项目/中心名称
    private String centerCode; //项目/中心编码
    private String modelName; //模块名称
    private String modelCode; //模块编码
    private Integer versionNum; //版本号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_API_MANAGEMENT", sequenceName = "SEQ_BASE_API_MANAGEMENT", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_API_MANAGEMENT", strategy = GenerationType.SEQUENCE)
	@Column(name = "api_id", nullable = false, length = 11)	
	public Integer getApiId() {
		return apiId;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Column(name = "interface_name", nullable = true, length = 400)	
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	@Column(name = "request_mode", nullable = true, length = 100)	
	public String getRequestMode() {
		return requestMode;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	@Column(name = "api_status", nullable = true, length = 100)	
	public String getApiStatus() {
		return apiStatus;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	@Column(name = "url_address", nullable = true, length = 400)	
	public String getUrlAddress() {
		return urlAddress;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	@Column(name = "developer", nullable = true, length = 128)	
	public String getDeveloper() {
		return developer;
	}

	public void setApiDesc(String apiDesc) {
		this.apiDesc = apiDesc;
	}

	@Column(name = "api_desc", nullable = true, length = 400)	
	public String getApiDesc() {
		return apiDesc;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "request_param",columnDefinition="CLOB", nullable = true)	
	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParamDict(String requestParamDict) {
		this.requestParamDict = requestParamDict;
	}

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "request_param_dict",columnDefinition="CLOB", nullable = true)	
	public String getRequestParamDict() {
		return requestParamDict;
	}

	public void setResponseParam(String responseParam) {
		this.responseParam = responseParam;
	}

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "response_param",columnDefinition="CLOB", nullable = true)	
	public String getResponseParam() {
		return responseParam;
	}

	public void setResponseParamDict(String responseParamDict) {
		this.responseParamDict = responseParamDict;
	}

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "response_param_dict",columnDefinition="CLOB", nullable = true)	
	public String getResponseParamDict() {
		return responseParamDict;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	@Column(name = "center_name", nullable = true, length = 100)	
	public String getCenterName() {
		return centerName;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	@Column(name = "center_code", nullable = true, length = 100)	
	public String getCenterCode() {
		return centerCode;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "model_name", nullable = true, length = 400)	
	public String getModelName() {
		return modelName;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	@Column(name = "model_code", nullable = true, length = 400)	
	public String getModelCode() {
		return modelCode;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
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

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
