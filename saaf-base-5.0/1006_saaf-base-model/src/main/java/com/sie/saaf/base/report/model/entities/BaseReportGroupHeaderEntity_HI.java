package com.sie.saaf.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseReportGroupHeaderEntity_HI Entity Object
 * Mon Jan 29 11:09:47 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_report_group_header")
public class BaseReportGroupHeaderEntity_HI {
    private Integer reportGroupHeaderId;
    private Integer reportHeaderId; //报表id
    private Integer reportGroupId; //报表组Id
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setReportGroupHeaderId(Integer reportGroupHeaderId) {
		this.reportGroupHeaderId = reportGroupHeaderId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_REPORT_GROUP_HEADER", sequenceName = "SEQ_BASE_REPORT_GROUP_HEADER", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_REPORT_GROUP_HEADER", strategy = GenerationType.SEQUENCE)	
	@Column(name = "report_group_header_id", nullable = false, length = 11)	
	public Integer getReportGroupHeaderId() {
		return reportGroupHeaderId;
	}

	public void setReportHeaderId(Integer reportHeaderId) {
		this.reportHeaderId = reportHeaderId;
	}

	@Column(name = "report_header_id", nullable = true, length = 11)	
	public Integer getReportHeaderId() {
		return reportHeaderId;
	}

	public void setReportGroupId(Integer reportGroupId) {
		this.reportGroupId = reportGroupId;
	}

	@Column(name = "report_group_id", nullable = true, length = 11)	
	public Integer getReportGroupId() {
		return reportGroupId;
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
