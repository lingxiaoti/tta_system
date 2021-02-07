package com.sie.saaf.business.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaReportRelationshipInEntity_HI Entity Object
 * Wed Jul 03 16:00:35 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_report_relationship_in")
public class TtaReportRelationshipInEntity_HI {
    //private Integer reportId;
    private String employeeNo;
    private String reportTo;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	/*public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_REPORT_RELATIONSHIP_IN", sequenceName = "SEQ_TTA_REPORT_RELATIONSHIP_IN", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REPORT_RELATIONSHIP_IN", strategy = GenerationType.SEQUENCE)
	@Column(name="report_id", nullable=false, length=22)	
	public Integer getReportId() {
		return reportId;
	}*/

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	@Id
	@Column(name="employee_no", nullable=false, length=100)	
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setReportTo(String reportTo) {
		this.reportTo = reportTo;
	}

	@Column(name="report_to", nullable=true, length=100)	
	public String getReportTo() {
		return reportTo;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeNo == null) ? 0 : employeeNo.hashCode());
		result = prime * result + ((reportTo == null) ? 0 : reportTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TtaReportRelationshipInEntity_HI other = (TtaReportRelationshipInEntity_HI) obj;
		if (employeeNo == null) {
			if (other.employeeNo != null)
				return false;
		} else if (!employeeNo.equals(other.employeeNo))
			return false;
		if (reportTo == null) {
			if (other.reportTo != null)
				return false;
		} else if (!reportTo.equals(other.reportTo))
			return false;
		return true;
	}
	
}
