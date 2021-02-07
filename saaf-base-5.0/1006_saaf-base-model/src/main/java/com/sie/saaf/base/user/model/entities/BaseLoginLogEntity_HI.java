package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseLoginLogEntity_HI Entity Object
 * Tue Jul 24 10:10:36 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_login_log")
public class BaseLoginLogEntity_HI {
	private Integer id; //主键
	private Integer userId; //用户id
	private String userName; //用户名/登录帐号
	private String userAgent; //用户代理头
	private String loginMode; //登录方式 1：帐号密码，2：微信扫码，3：app
	private String ip; //登录ip
	private String phoneNumber; //设备手机号(Android APP)
	private String deviceinfo; //设备型号
	private String brand;//设备品牌
	private String system; //Android/IOS
	private String systemVersion; //系统版本
	private String appName;//app应用名
	private String appVersion; //app版本
	private String totalMem; //设备内存大小
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建日期
	private Integer createdBy; //创建人
	private Integer lastUpdatedBy; //更新人
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新日期
	private Integer versionNum; //版本号
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BASE_LOGIN_LOG", sequenceName = "SEQ_BASE_LOGIN_LOG", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_LOGIN_LOG", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=11)
	public Integer getId() {
		return id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name="user_id", nullable=false, length=11)
	public Integer getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="user_name", nullable=true, length=100)
	public String getUserName() {
		return userName;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(name="user_agent", nullable=true, length=255)
	public String getUserAgent() {
		return userAgent;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	@Column(name="login_mode", nullable=true, length=2)
	public String getLoginMode() {
		return loginMode;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name="ip", nullable=true, length=45)
	public String getIp() {
		return ip;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name="phone_number", nullable=true, length=20)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setDeviceinfo(String deviceinfo) {
		this.deviceinfo = deviceinfo;
	}

	@Column(name="deviceinfo", nullable=true, length=45)
	public String getDeviceinfo() {
		return deviceinfo;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Column(name="system", nullable=true, length=20)
	public String getSystem() {
		return system;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	@Column(name="system_version", nullable=true, length=20)
	public String getSystemVersion() {
		return systemVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@Column(name="app_version", nullable=true, length=20)
	public String getAppVersion() {
		return appVersion;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	@Column(name="app_name", nullable=true, length=45)
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name="total_mem", nullable=true, length=20)
	public String getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(String totalMem) {
		this.totalMem = totalMem;
	}

	@Column(name="brand", nullable=true, length=45)
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
