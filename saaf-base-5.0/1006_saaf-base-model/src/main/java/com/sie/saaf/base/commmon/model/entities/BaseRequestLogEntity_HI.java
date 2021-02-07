package com.sie.saaf.base.commmon.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseRequestLogEntity_HI Entity Object
 * Wed Mar 21 08:56:45 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_request_log")
public class BaseRequestLogEntity_HI {
    private Integer id;
    private String requestUrl;
    private String requestMethod;
    private String requestBody;
    private String response;
    private String requestHeader;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_REQUEST_LOG", sequenceName = "SEQ_BASE_REQUEST_LOG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_REQUEST_LOG", strategy = GenerationType.SEQUENCE)	
	@Column(name = "id", nullable = false, length = 11)	
	public Integer getId() {
		return id;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name = "request_url", nullable = true, length = 255)	
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Column(name = "request_method", nullable = true, length = 255)	
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	@Column(name = "request_body", nullable = true)
	public String getRequestBody() {
		return requestBody;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Column(name = "response", nullable = true)
	public String getResponse() {
		return response;
	}

	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	@Column(name = "request_header", nullable = true, length = 255)	
	public String getRequestHeader() {
		return requestHeader;
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
