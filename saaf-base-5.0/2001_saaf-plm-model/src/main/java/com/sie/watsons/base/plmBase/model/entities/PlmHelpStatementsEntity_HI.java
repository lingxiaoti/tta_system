package com.sie.watsons.base.plmBase.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmHelpStatementsEntity_HI Entity Object
 * Sat Nov 09 11:47:05 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_HELP_STATEMENTS")
public class PlmHelpStatementsEntity_HI {
    private String creator;
    private Integer plmHelpStatementsId;
    private String fieldCode;
    private String fieldMeaning;
    private String billStatusName;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="creator", nullable=true, length=50)	
	public String getCreator() {
		return creator;
	}

	public void setPlmHelpStatementsId(Integer plmHelpStatementsId) {
		this.plmHelpStatementsId = plmHelpStatementsId;
	}

	@Id	
	@SequenceGenerator(name="plmHelpStatementsEntity_HISeqGener", sequenceName="SEQ_PLM_HELP_STATEMENTS", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmHelpStatementsEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_help_statements_id", nullable=false, length=22)	
	public Integer getPlmHelpStatementsId() {
		return plmHelpStatementsId;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	@Column(name="field_code", nullable=true, length=200)
	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldMeaning(String fieldMeaning) {
		this.fieldMeaning = fieldMeaning;
	}

	@Column(name="field_meaning", nullable=true, length=400)	
	public String getFieldMeaning() {
		return fieldMeaning;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	@Column(name="bill_status_name", nullable=true, length=50)	
	public String getBillStatusName() {
		return billStatusName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
