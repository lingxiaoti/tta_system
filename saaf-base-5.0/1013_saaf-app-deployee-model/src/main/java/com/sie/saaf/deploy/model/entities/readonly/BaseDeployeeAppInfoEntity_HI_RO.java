package com.sie.saaf.deploy.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppMenuEntity_HI;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class BaseDeployeeAppInfoEntity_HI_RO {
	
	public static final String QUERY_SQL_USR_APP = "select \n" +
			"\t app.app_wap_id as appWapId,\n" +
			"\t app.app_code as appCode,\n" +
			"\t app.app_name as appName,\n" +
			"\t app.app_wap_code as appWapCode,\n" +
			"\t app.app_wap_name as appWapName,\n" +
			"\t app.app_wap_desc as appWapDesc,\n" +
			"\t app.app_wap_sort_id as appWapSortId,\n" +
			"\t app.app_wap_version as appWapVersion,\n" +
			"\t app.app_packing_version as appPackingVersion,\n" +
			"\t app.app_wap_access_home_path as appWapAccessHomePath,\n" +
			"\t app.app_wap_full_screen as appWapFullScreen,\n" +
			"\t app.app_wap_backup_path as appWapBackupPath,\n" +
			"\t app.app_wap_image_path as appWapImagePath,\n" +
			"\t app.app_wap_upload_path as appWapUploadPath,\n" +
			"\t app.app_wap_deployee_time as appWapDeployeeTime,\n" +
			"\t app.app_wap_status as appWapStatus\n" +
			"\t from base_deployee_app_info app where 1=1 ";

	
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

    private List<BaseDeployeeAppMenuEntity_HI> menuData;//应用菜单

	public void setAppWapId(Integer appWapId) {
		this.appWapId = appWapId;
	}

	public Integer getAppWapId() {
		return appWapId;
	}
		
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	public String getAppCode() {
		return appCode;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppName() {
		return appName;
	}
	
	public void setAppWapCode(String appWapCode) {
		this.appWapCode = appWapCode;
	}

	public String getAppWapCode() {
		return appWapCode;
	}	

	public void setAppWapName(String appWapName) {
		this.appWapName = appWapName;
	}

	public String getAppWapName() {
		return appWapName;
	}

	public void setAppWapDesc(String appWapDesc) {
		this.appWapDesc = appWapDesc;
	}

	public String getAppWapDesc() {
		return appWapDesc;
	}

	public void setAppWapSortId(Integer appWapSortId) {
		this.appWapSortId = appWapSortId;
	}

	public Integer getAppWapSortId() {
		return appWapSortId;
	}

	public void setAppWapVersion(BigDecimal appWapVersion) {
		this.appWapVersion = appWapVersion;
	}

	public BigDecimal getAppWapVersion() {
		return appWapVersion;
	}

	public void setAppPackingVersion(BigDecimal appPackingVersion) {
		this.appPackingVersion = appPackingVersion;
	}

	public BigDecimal getAppPackingVersion() {
		return appPackingVersion;
	}

	public void setAppWapAccessHomePath(String appWapAccessHomePath) {
		this.appWapAccessHomePath = appWapAccessHomePath;
	}

	public String getAppWapAccessHomePath() {
		return appWapAccessHomePath;
	}

	public void setAppWapFullScreen(String appWapFullScreen) {
		this.appWapFullScreen = appWapFullScreen;
	}

	public String getAppWapFullScreen() {
		return appWapFullScreen;
	}

	public void setAppWapBackupPath(String appWapBackupPath) {
		this.appWapBackupPath = appWapBackupPath;
	}

	public String getAppWapBackupPath() {
		return appWapBackupPath;
	}

	public void setAppWapImagePath(String appWapImagePath) {
		this.appWapImagePath = appWapImagePath;
	}

	public String getAppWapImagePath() {
		return appWapImagePath;
	}

	public void setAppWapUploadPath(String appWapUploadPath) {
		this.appWapUploadPath = appWapUploadPath;
	}

	public String getAppWapUploadPath() {
		return appWapUploadPath;
	}
	
	public void setAppWapDeployeeTime(Date appWapDeployeeTime) {
		this.appWapDeployeeTime = appWapDeployeeTime;
	}
	
	public Date getAppWapDeployeeTime() {
		return appWapDeployeeTime;
	}
	
	public void setAppWapStatus(String appWapStatus) {
		this.appWapStatus = appWapStatus;
	}

	public String getAppWapStatus() {
		return appWapStatus;
	}

	public List<BaseDeployeeAppMenuEntity_HI> getMenuData() {
		return menuData;
	}

	public void setMenuData(List<BaseDeployeeAppMenuEntity_HI> menuData) {
		this.menuData = menuData;
	}

	public String getAppWapStatusName(){
		return "1".equals(appWapStatus)?"上架":"下架";
	}
	
}
