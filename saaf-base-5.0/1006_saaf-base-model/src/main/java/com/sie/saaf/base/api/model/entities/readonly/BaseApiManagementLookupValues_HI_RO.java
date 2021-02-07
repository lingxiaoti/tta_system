package com.sie.saaf.base.api.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author ZhangJun
 * @createTime 2018-01-10 16:27
 * @description
 */
public class BaseApiManagementLookupValues_HI_RO {

	public static final String QUERY_SQL_1 = "select \n" +
			"  baseApiManagement.api_id as apiId,\n" +
			"  baseApiManagement.interface_name as interfaceName,\n" +
			"  baseApiManagement.request_mode as requestMode,\n" +
			"  baseApiManagement.api_status as apiStatus,\n" +
			"  lookup.meaning as apiStatusMeaning,\n" +
			"  baseApiManagement.url_address as urlAddress,\n" +
			"  baseApiManagement.developer as developer,\n" +
			"  baseApiManagement.api_desc as apiDesc,\n" +
			"  baseApiManagement.request_param as requestParam,\n" +
			"  baseApiManagement.request_param_dict as requestParamDict,\n" +
			"  baseApiManagement.response_param as responseParam,\n" +
			"  baseApiManagement.response_param_dict as responseParamDict,\n" +
			"  baseApiManagement.center_name as centerName,\n" +
			"  baseApiManagement.center_code as centerCode,\n" +
			"  baseApiManagement.model_code as modelCode,\n" +
			"  baseApiManagement.model_name as modelname,\n" +
			"  baseApiManagement.version_num as versionNum,\n" +
			"  baseApiManagement.creation_date as creationDate,\n" +
			"  baseApiManagement.created_by as createBy,\n" +
			"  baseApiManagement.last_updated_by as lastUpdateBy,\n" +
			"  baseApiManagement.last_update_date as lastUpdateDate,\n" +
			"  baseApiManagement.last_update_login as lastUpdateLogin\n" +
			"from base_api_management baseApiManagement left join (\n" +
			"select lookup_code,meaning from base_lookup_values \n" +
			"where LOOKUP_TYPE='API_STATUS' and enabled_flag='Y' and delete_flag=0 \n" +
			"and start_date_active<SYSDATE and (end_date_active>=SYSDATE or end_date_active is null )\n" +
			") lookup on lookup.lookup_code=baseApiManagement.api_status\n" +
			"where 1=1 ";

	//base_api_management
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

	//base_lookup_values
	private String apiStatusMeaning;//API状态说明

	public Integer getApiId() {
		return apiId;
	}

	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getApiDesc() {
		return apiDesc;
	}

	public void setApiDesc(String apiDesc) {
		this.apiDesc = apiDesc;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public String getRequestParamDict() {
		return requestParamDict;
	}

	public void setRequestParamDict(String requestParamDict) {
		this.requestParamDict = requestParamDict;
	}

	public String getResponseParam() {
		return responseParam;
	}

	public void setResponseParam(String responseParam) {
		this.responseParam = responseParam;
	}

	public String getResponseParamDict() {
		return responseParamDict;
	}

	public void setResponseParamDict(String responseParamDict) {
		this.responseParamDict = responseParamDict;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
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

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getApiStatusMeaning() {
		return apiStatusMeaning;
	}

	public void setApiStatusMeaning(String apiStatusMeaning) {
		this.apiStatusMeaning = apiStatusMeaning;
	}
}
