package com.sie.saaf.deploy.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * BaseDeployeeAppInfoEntity_HI Entity Object
 * Mon Jul 23 11:28:13 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_deployee_app_info")
public class BaseDeployeeAppInfoEntity_HI {
    private Integer appWapId; //主键Id
    private String appCode; //app底座的编码
    private String appName; //app底座的名字
    private String appWapCode; //应用编码
    private String appWapName; //应用名称
    private String appWapDesc; //描述
    private Integer appWapSortId; //应用排序
    private BigDecimal appWapVersion; //应用版本
    private BigDecimal appPackingVersion; //打包版本
    private String appWapAccessHomePath; //应用首页访问地址
    private String appWapFullScreen;//是否全屏 Y.是  N.否
    private String appWapBackupPath;//应用备份路径
    private String appWapImagePath; //应用图标地址
    private String appWapUploadPath; //应用上传地址
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date appWapDeployeeTime;//应用发布时间
    private String appWapStatus;//应用状态  1.上架  0.下架
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
    
    private List<BaseDeployeeAppMenuEntity_HI> menuData;//应用菜单

	public void setAppWapId(Integer appWapId) {
		this.appWapId = appWapId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_DEPLOYEE_APP_INFO", sequenceName = "SEQ_BASE_DEPLOYEE_APP_INFO", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_DEPLOYEE_APP_INFO", strategy = GenerationType.SEQUENCE)	
	@Column(name="app_wap_id", nullable=false, length=11)	
	public Integer getAppWapId() {
		return appWapId;
	}
		
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	@Column(name="app_code", nullable=true, length=32)	
	public String getAppCode() {
		return appCode;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name="app_name", nullable=true, length=32)	
	public String getAppName() {
		return appName;
	}
	
	public void setAppWapCode(String appWapCode) {
		this.appWapCode = appWapCode;
	}

	@Column(name="app_wap_code", nullable=true, length=64)	
	public String getAppWapCode() {
		return appWapCode;
	}	

	public void setAppWapName(String appWapName) {
		this.appWapName = appWapName;
	}

	@Column(name="app_wap_name", nullable=true, length=64)	
	public String getAppWapName() {
		return appWapName;
	}

	public void setAppWapDesc(String appWapDesc) {
		this.appWapDesc = appWapDesc;
	}

	@Column(name="app_wap_desc", nullable=true, length=256)	
	public String getAppWapDesc() {
		return appWapDesc;
	}

	public void setAppWapSortId(Integer appWapSortId) {
		this.appWapSortId = appWapSortId;
	}

	@Column(name="app_wap_sort_id", nullable=true, length=11)	
	public Integer getAppWapSortId() {
		return appWapSortId;
	}

	public void setAppWapVersion(BigDecimal appWapVersion) {
		this.appWapVersion = appWapVersion;
	}

	@Column(name="app_wap_version", nullable=true)	
	public BigDecimal getAppWapVersion() {
		return appWapVersion;
	}

	public void setAppPackingVersion(BigDecimal appPackingVersion) {
		this.appPackingVersion = appPackingVersion;
	}

	@Column(name="app_packing_version", nullable=true)	
	public BigDecimal getAppPackingVersion() {
		return appPackingVersion;
	}

	public void setAppWapAccessHomePath(String appWapAccessHomePath) {
		this.appWapAccessHomePath = appWapAccessHomePath;
	}

	@Column(name="app_wap_access_home_path", nullable=true, length=2000)	
	public String getAppWapAccessHomePath() {
		return appWapAccessHomePath;
	}

	public void setAppWapFullScreen(String appWapFullScreen) {
		this.appWapFullScreen = appWapFullScreen;
	}

	@Column(name="app_wap_full_screen", nullable=true, length=2)	
	public String getAppWapFullScreen() {
		return appWapFullScreen;
	}

	public void setAppWapBackupPath(String appWapBackupPath) {
		this.appWapBackupPath = appWapBackupPath;
	}

	@Column(name="app_wap_backup_path", nullable=true, length=1000)	
	public String getAppWapBackupPath() {
		return appWapBackupPath;
	}

	public void setAppWapImagePath(String appWapImagePath) {
		this.appWapImagePath = appWapImagePath;
	}

	@Column(name="app_wap_image_path", nullable=true, length=1000)	
	public String getAppWapImagePath() {
		return appWapImagePath;
	}

	public void setAppWapUploadPath(String appWapUploadPath) {
		this.appWapUploadPath = appWapUploadPath;
	}

	@Column(name="app_wap_upload_path", nullable=true, length=128)	
	public String getAppWapUploadPath() {
		return appWapUploadPath;
	}
	
	public void setAppWapDeployeeTime(Date appWapDeployeeTime) {
		this.appWapDeployeeTime = appWapDeployeeTime;
	}
	
	@Column(name="app_wap_deployee_time", nullable=true)	
	public Date getAppWapDeployeeTime() {
		return appWapDeployeeTime;
	}
	
	public void setAppWapStatus(String appWapStatus) {
		this.appWapStatus = appWapStatus;
	}

	@Column(name="app_wap_status", nullable=true, length=11)	
	public String getAppWapStatus() {
		return appWapStatus;
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

	@Column(name="creation_date", nullable=true)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true)	
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
	public List<BaseDeployeeAppMenuEntity_HI> getMenuData() {
		return menuData;
	}

	public void setMenuData(List<BaseDeployeeAppMenuEntity_HI> menuData) {
		this.menuData = menuData;
	}

	@Transient
	public String getAppWapStatusName(){
		return "1".equals(appWapStatus)?"上架":"下架";
	}
	
}
