package com.sie.watsons.base.elecsign.model.entities;

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
 * TtaElecConAttrLineEntity_HI Entity Object
 * Mon Mar 30 17:14:25 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_ELEC_CON_ATTR_LINE")
public class TtaElecConAttrLineEntity_HI {

	private Integer elecConAttrLineId;
    private Integer elecConHeaderId;
    private Integer conAttrLineId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer fileId; //添加水印后的附件id
	private String fileUrl; //添加水印后的附件url



	public void setElecConAttrLineId(Integer elecConAttrLineId) {
		this.elecConAttrLineId = elecConAttrLineId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_ELEC_CON_ATTR_LINE", sequenceName="SEQ_TTA_ELEC_CON_ATTR_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_ELEC_CON_ATTR_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="elec_con_attr_line_id", nullable=false, length=22)
	public Integer getElecConAttrLineId() {
		return elecConAttrLineId;
	}

	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}

	@Column(name="elec_con_header_id", nullable=true, length=22)	
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setConAttrLineId(Integer conAttrLineId) {
		this.conAttrLineId = conAttrLineId;
	}

	@Column(name="con_attr_line_id", nullable=true, length=22)
	public Integer getConAttrLineId() {
		return conAttrLineId;
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

	@Column(name = "file_id")
	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "file_url")
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
