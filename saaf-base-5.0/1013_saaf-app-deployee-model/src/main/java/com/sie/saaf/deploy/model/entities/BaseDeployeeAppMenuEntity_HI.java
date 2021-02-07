package com.sie.saaf.deploy.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseAppDeployeeMenuEntity_HI Entity Object
 * Mon Jul 23 11:28:01 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_deployee_app_menu")
public class BaseDeployeeAppMenuEntity_HI {
    private Integer appMenuId;
    private Integer appWapId;
    private String appMenuName; //app菜单名称
    private String appMenuCode; //app菜单编码
	private String appMenuImagePath; //app菜单图标
    private String appMenuUrl; //app菜单访问地址
    private Integer appMenuSort;//app菜单排序
    private Integer appDefaultDisplay; //默认显示在用户常用收藏栏,1表示显示 0表示不显示 默认值为0
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;

	public void setAppMenuId(Integer appMenuId) {
		this.appMenuId = appMenuId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_DEPLOYEE_APP_MENU", sequenceName = "SEQ_BASE_DEPLOYEE_APP_MENU", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_DEPLOYEE_APP_MENU", strategy = GenerationType.SEQUENCE)	
	@Column(name="app_menu_id", nullable=false, length=11)	
	public Integer getAppMenuId() {
		return appMenuId;
	}

	public void setAppWapId(Integer appWapId) {
		this.appWapId = appWapId;
	}

	@Column(name="app_wap_id", nullable=true, length=11)	
	public Integer getAppWapId() {
		return appWapId;
	}

	public void setAppMenuName(String appMenuName) {
		this.appMenuName = appMenuName;
	}

	@Column(name="app_menu_name", nullable=true, length=64)	
	public String getAppMenuName() {
		return appMenuName;
	}

	public void setAppMenuCode(String appMenuCode) {
		this.appMenuCode = appMenuCode;
	}

	@Column(name="app_menu_code", nullable=true, length=64)	
	public String getAppMenuCode() {
		return appMenuCode;
	}


	public void setAppMenuImagePath(String appMenuImagePath) {
		this.appMenuImagePath = appMenuImagePath;
	}
	
	@Column(name="app_menu_image_path", nullable=true, length=255)
	public String getAppMenuImagePath() {
		return appMenuImagePath;
	}

	public void setAppMenuUrl(String appMenuUrl) {
		this.appMenuUrl = appMenuUrl;
	}

	@Column(name="app_menu_url", nullable=true, length=255)	
	public String getAppMenuUrl() {
		return appMenuUrl;
	}

	public void setAppMenuSort(Integer appMenuSort) {
		this.appMenuSort = appMenuSort;
	}

	@Column(name="app_menu_sort", nullable=true, length=11)	
	public Integer getAppMenuSort() {
		return appMenuSort;
	}

	public void setAppDefaultDisplay(Integer appDefaultDisplay) {
		this.appDefaultDisplay = appDefaultDisplay;
	}

	@Column(name="app_default_display", nullable=true, length=11)	
	public Integer getAppDefaultDisplay() {
		return appDefaultDisplay;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=11)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Transient
	public String getAppDefaultDisplayYN() {
		return appDefaultDisplay != null && appDefaultDisplay == 1?"Y" : "N";
	}

	public void setAppDefaultDisplayYN(String appDefaultDisplayYN) {
		this.appDefaultDisplay = "Y".equals(appDefaultDisplayYN)?1:0;
	}
	
}
