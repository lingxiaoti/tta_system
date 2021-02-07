package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseButtonDataEntity_HI Entity Object
 * Tue Dec 12 19:08:05 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_button_data")
public class BaseButtonDataEntity_HI {
    private Integer bbdId; //主键ID
    private String bbdName; //功能按钮名称
    private String bbdCode; //功能按钮编码
    private String resIcon; //功能按钮图标
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setBbdId(Integer bbdId) {
		this.bbdId = bbdId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_BUTTON_DATA", sequenceName = "SEQ_BASE_BUTTON_DATA", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_BUTTON_DATA", strategy = GenerationType.SEQUENCE)	
	@Column(name = "bbd_id", nullable = false, length = 11)	
	public Integer getBbdId() {
		return bbdId;
	}

	public void setBbdName(String bbdName) {
		this.bbdName = bbdName;
	}

	@Column(name = "bbd_name", nullable = true, length = 20)	
	public String getBbdName() {
		return bbdName;
	}

	public void setBbdCode(String bbdCode) {
		this.bbdCode = bbdCode;
	}

	@Column(name = "bbd_code", nullable = true, length = 50)	
	public String getBbdCode() {
		return bbdCode;
	}

	@Column(name = "res_icon", nullable = true, length = 50)
	public String getResIcon() {
		return resIcon;
	}

	public void setResIcon(String resIcon) {
		this.resIcon = resIcon;
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
