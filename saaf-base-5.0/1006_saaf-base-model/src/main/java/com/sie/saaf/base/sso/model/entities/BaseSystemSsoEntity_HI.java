package com.sie.saaf.base.sso.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseSystemSsoEntity_HI Entity Object
 * Tue Dec 12 10:53:18 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_system_sso")
public class BaseSystemSsoEntity_HI {
    private Integer sysSsoId; //主键Id
    private String systemName; //系统名称
    private String systemCode; //系统编码
    private String description; //描述
    private String clientId;
    private String enableFlag; //是否启用
    private String imageUrl; //图标地址
    private Integer orderNo; //排序
    private String params; //参数
    private String requestMethod; //请求方法 get/post
    private String requestUrl; //服务地址
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setSysSsoId(Integer sysSsoId) {
		this.sysSsoId = sysSsoId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_SYSTEM_SSO", sequenceName = "SEQ_BASE_SYSTEM_SSO", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_SYSTEM_SSO", strategy = GenerationType.SEQUENCE)
	@Column(name = "sys_sso_id", nullable = false, length = 11)	
	public Integer getSysSsoId() {
		return sysSsoId;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@Column(name = "system_name", nullable = true, length = 100)	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "system_code", nullable = true, length = 100)	
	public String getSystemCode() {
		return systemCode;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "description", nullable = true, length = 255)	
	public String getDescription() {
		return description;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Column(name = "client_id", nullable = true, length = 100)	
	public String getClientId() {
		return clientId;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	@Column(name = "enable_flag", nullable = true, length = 10)	
	public String getEnableFlag() {
		return enableFlag;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "image_url", nullable = true, length = 500)	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "order_no", nullable = true, length = 11)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "params", nullable = true, length = 500)	
	public String getParams() {
		return params;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Column(name = "request_method", nullable = true, length = 45)	
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name = "request_url", nullable = true, length = 255)	
	public String getRequestUrl() {
		return requestUrl;
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

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
}
