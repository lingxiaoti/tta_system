package com.sie.watsons.report.model.entities;

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
 * TtaPrintRecordEntity_HI Entity Object
 * Thu Jun 20 21:58:23 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_print_record")
public class TtaPrintRecordEntity_HI {
    private Integer printRecordId;
    private String printType;
    private Integer printCount;
    private String remark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setPrintRecordId(Integer printRecordId) {
		this.printRecordId = printRecordId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_PRINT_RECORD", sequenceName="SEQ_TTA_PRINT_RECORD", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_PRINT_RECORD",strategy=GenerationType.SEQUENCE)
	@Column(name="print_record_id", nullable=false, length=22)	
	public Integer getPrintRecordId() {
		return printRecordId;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	@Column(name="print_type", nullable=false, length=50)	
	public String getPrintType() {
		return printType;
	}

	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	@Column(name="print_count", nullable=false, length=22)	
	public Integer getPrintCount() {
		return printCount;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=150)	
	public String getRemark() {
		return remark;
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

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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
}
