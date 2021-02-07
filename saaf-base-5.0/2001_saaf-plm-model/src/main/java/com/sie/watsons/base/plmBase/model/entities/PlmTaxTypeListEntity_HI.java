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
 * PlmTaxTypeListEntity_HI Entity Object
 * Wed Nov 06 11:32:35 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_TAX_TYPE_LIST")
public class PlmTaxTypeListEntity_HI {
    private Integer plmTaxTypeListId;
    private String plmTaxTypeCode;
    private String plmTaxTypeName;
    private String billStatus;
    private String billStatusName;
    private String remarks;
    private String creator;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;
	private Integer createdBy;

	public void setPlmTaxTypeListId(Integer plmTaxTypeListId) {
		this.plmTaxTypeListId = plmTaxTypeListId;
	}

	@Id	
	@SequenceGenerator(name="plmTaxTypeListEntity_HISeqGener", sequenceName="SEQ_PLM_TAX_TYPE_LIST", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmTaxTypeListEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_tax_type_list_id", nullable=false, length=22)	
	public Integer getPlmTaxTypeListId() {
		return plmTaxTypeListId;
	}

	public void setPlmTaxTypeCode(String plmTaxTypeCode) {
		this.plmTaxTypeCode = plmTaxTypeCode;
	}

	@Column(name="plm_tax_type_code", nullable=true, length=20)	
	public String getPlmTaxTypeCode() {
		return plmTaxTypeCode;
	}

	public void setPlmTaxTypeName(String plmTaxTypeName) {
		this.plmTaxTypeName = plmTaxTypeName;
	}

	@Column(name="plm_tax_type_name", nullable=true, length=100)	
	public String getPlmTaxTypeName() {
		return plmTaxTypeName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name="bill_status", nullable=true, length=20)	
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	@Column(name="bill_status_name", nullable=true, length=20)	
	public String getBillStatusName() {
		return billStatusName;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=400)	
	public String getRemarks() {
		return remarks;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="creator", nullable=true, length=400)	
	public String getCreator() {
		return creator;
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

	@Column(name="created_by", nullable=true, length=22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
}
