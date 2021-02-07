package com.sie.saaf.base.sso.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseFunctionCollectionEntity_HI Entity Object
 * Tue Jan 30 16:48:18 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_function_collection")
public class BaseFunctionCollectionEntity_HI {
    private Integer functionCollectionId;
    private Integer userId; //用户Id
    private String collectionType; //收藏类型 (外部菜单/内部菜单)
    private String functionName; //菜单名称
    private String functionUrl; //菜单url
    private String requestMethod; //请求方式 get/post
    private Integer menuId; //菜单id
    private Integer respId; //职责id
    private String systemCode; //系统编码
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setFunctionCollectionId(Integer functionCollectionId) {
		this.functionCollectionId = functionCollectionId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_FUNCTION_COLLECTION", sequenceName = "SEQ_BASE_FUNCTION_COLLECTION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_FUNCTION_COLLECTION", strategy = GenerationType.SEQUENCE)	
	@Column(name = "function_collection_id", nullable = false, length = 11)	
	public Integer getFunctionCollectionId() {
		return functionCollectionId;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

    @Column(name = "user_id", nullable = true, length = 11)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	@Column(name = "collection_type", nullable = true, length = 45)
	public String getCollectionType() {
		return collectionType;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "function_name", nullable = true, length = 45)	
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	@Column(name = "function_url", nullable = true, length = 255)	
	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Column(name = "request_method", nullable = true, length = 10)	
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menu_id", nullable = true, length = 11)	
	public Integer getMenuId() {
		return menuId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	@Column(name = "resp_id", nullable = true, length = 11)	
	public Integer getRespId() {
		return respId;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "system_code", nullable = true, length = 100)	
	public String getSystemCode() {
		return systemCode;
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
