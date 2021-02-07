package com.sie.saaf.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseReportGroupEntity_HI Entity Object
 * Tue Dec 12 19:43:03 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_report_group")
public class BaseReportGroupEntity_HI {
    private Integer reportGroupId; //主键Id
    private String menuTabName; //报表组菜单名
    private String reportGroupDesc; //报表组描述
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setReportGroupId(Integer reportGroupId) {
		this.reportGroupId = reportGroupId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_REPORT_GROUP", sequenceName = "SEQ_BASE_REPORT_GROUP", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_REPORT_GROUP", strategy = GenerationType.SEQUENCE)
	@Column(name = "report_group_id", nullable = false, length = 11)	
	public Integer getReportGroupId() {
		return reportGroupId;
	}

	public void setMenuTabName(String menuTabName) {
		this.menuTabName = menuTabName;
	}

	@Column(name = "menu_tab_name", nullable = true, length = 200)	
	public String getMenuTabName() {
		return menuTabName;
	}

	public void setReportGroupDesc(String reportGroupDesc) {
		this.reportGroupDesc = reportGroupDesc;
	}

	@Column(name = "report_group_desc", nullable = true, length = 2000)	
	public String getReportGroupDesc() {
		return reportGroupDesc;
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
