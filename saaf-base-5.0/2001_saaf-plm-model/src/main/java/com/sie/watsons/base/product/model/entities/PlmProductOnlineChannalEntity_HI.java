package com.sie.watsons.base.product.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductOnlineChannalEntity_HI Entity Object Thu Aug 29 10:51:50 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_ONLINE_CHANNAL")
public class PlmProductOnlineChannalEntity_HI {
	private Integer channalId;
	@columnNames(name = "线上渠道")
	private String channal;
	@columnNames(name = "子渠道")
	private String channalSub;
	@columnNames(name = "线上渠道专有")
	private String channalUnique;
	private Integer productHeadId;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String flags;

	public void setChannalId(Integer channalId) {
		this.channalId = channalId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_ONLINE_CHANNAL", sequenceName = "SEQ_PLM_PRODUCT_ONLINE_CHANNAL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_ONLINE_CHANNAL", strategy = GenerationType.SEQUENCE)
	@Column(name = "channal_id", nullable = false, length = 22)
	public Integer getChannalId() {
		return channalId;
	}

	public void setChannal(String channal) {
		this.channal = channal;
	}

	@Column(name = "channal", nullable = true, length = 255)
	public String getChannal() {
		return channal;
	}

	public void setChannalSub(String channalSub) {
		this.channalSub = channalSub;
	}

	@Column(name = "channal_sub", nullable = true, length = 255)
	public String getChannalSub() {
		return channalSub;
	}

	public void setChannalUnique(String channalUnique) {
		this.channalUnique = channalUnique;
	}

	@Column(name = "channal_unique", nullable = true, length = 50)
	public String getChannalUnique() {
		return channalUnique;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by ", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
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

	@Column(name = "flags", nullable = true, length = 255)
	public String getFlag() {
		return flags;
	}

	public void setFlag(String flags) {
		this.flags = flags;
	}
}
