package com.sie.saaf.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseReportDatasourceEntity_HI Entity Object
 * Tue Dec 12 19:43:02 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_report_datasource")
public class BaseReportDatasourceEntity_HI {
    private Integer dsId; //主键
    private String dsName; //数据源名字
    private String dsType; //数据源类型：DataBase、webServiceSOAP、webServiceRestful
    private String dsAccessType; //数据源访问类别
    private String dsAccessProtocal; //访问协议：http/https
    private String dsIp; //数据源所在的IP地址
    private String dsPort; //服务端口
    private String dsWebserverAddress; //数据源访问地址
	private String dsWebserverUser; //服务访问的用户名
    private String dsWebserverPassword; //服务访问的密码
    private String dsWebserverToken; //服务访问的密钥
    private String dsDbInstanceName; //数据库实例名
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_REPORT_DATASOURCE", sequenceName = "SEQ_BASE_REPORT_DATASOURCE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_REPORT_DATASOURCE", strategy = GenerationType.SEQUENCE)
	@Column(name = "ds_id", nullable = false, length = 11)	
	public Integer getDsId() {
		return dsId;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	@Column(name = "ds_name", nullable = true, length = 100)	
	public String getDsName() {
		return dsName;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	@Column(name = "ds_type", nullable = true, length = 25)	
	public String getDsType() {
		return dsType;
	}

	public void setDsAccessType(String dsAccessType) {
		this.dsAccessType = dsAccessType;
	}

	@Column(name = "ds_access_type", nullable = true, length = 10)	
	public String getDsAccessType() {
		return dsAccessType;
	}

	public void setDsAccessProtocal(String dsAccessProtocal) {
		this.dsAccessProtocal = dsAccessProtocal;
	}

	@Column(name = "ds_access_protocal", nullable = true, length = 10)	
	public String getDsAccessProtocal() {
		return dsAccessProtocal;
	}

	public void setDsIp(String dsIp) {
		this.dsIp = dsIp;
	}

	@Column(name = "ds_ip", nullable = true, length = 20)	
	public String getDsIp() {
		return dsIp;
	}

	public void setDsPort(String dsPort) {
		this.dsPort = dsPort;
	}

	@Column(name = "ds_port", nullable = true, length = 10)	
	public String getDsPort() {
		return dsPort;
	}

	public void setDsWebserverAddress(String dsWebserverAddress) {
		this.dsWebserverAddress = dsWebserverAddress;
	}

	@Column(name = "ds_webserver_address", nullable = true, length = 500)	
	public String getDsWebserverAddress() {
		return dsWebserverAddress;
	}

	@Column(name = "ds_webserver_user", nullable = true, length = 45)
	public String getDsWebserverUser() {
		return dsWebserverUser;
	}

	public void setDsWebserverUser(String dsWebserverUser) {
		this.dsWebserverUser = dsWebserverUser;
	}

	public void setDsWebserverPassword(String dsWebserverPassword) {
		this.dsWebserverPassword = dsWebserverPassword;
	}

	@Column(name = "ds_webserver_password", nullable = true, length = 100)	
	public String getDsWebserverPassword() {
		return dsWebserverPassword;
	}

	public void setDsWebserverToken(String dsWebserverToken) {
		this.dsWebserverToken = dsWebserverToken;
	}

	@Column(name = "ds_webserver_token", nullable = true, length = 500)	
	public String getDsWebserverToken() {
		return dsWebserverToken;
	}

	public void setDsDbInstanceName(String dsDbInstanceName) {
		this.dsDbInstanceName = dsDbInstanceName;
	}

	@Column(name = "ds_db_instance_name", nullable = true, length = 100)	
	public String getDsDbInstanceName() {
		return dsDbInstanceName;
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
