package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaAnalysisEditDataEntity_HI Entity Object
 * Mon Sep 23 14:44:03 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_ANALYSIS_EDIT_DATA")
public class TtaAnalysisEditDataEntity_HI {
    private Integer id;
    private String proposalid;
    private Integer markup;
    private Integer freegoods;
    private String aboiList;
    private String bicRemark;
    private String purchaseRemark;
    private Integer status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    private String versionCode;
    private String batch;

    private String oldAboiList;//往年的ABOI类型的条款的值,数据格式:{"A167421":"3","A167395":"3","A167405":"3","A167419":"3"}
	private Integer oldFreeGoods;//往年的freeGoogs

	public void setId(Integer id) {
		this.id = id;
	}

    @Id
    @SequenceGenerator(name = "SEQ_TTA_ANALYSIS_EDIT_DATA", sequenceName = "SEQ_TTA_ANALYSIS_EDIT_DATA", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_ANALYSIS_EDIT_DATA", strategy = GenerationType.SEQUENCE)
    @Column(name="ID", nullable=false, length=22)
	public Integer getId() {
		return id;
	}

	public void setProposalid(String proposalid) {
		this.proposalid = proposalid;
	}

	@Column(name="proposalid", nullable=true, length=100)	
	public String getProposalid() {
		return proposalid;
	}

	public void setMarkup(Integer markup) {
		this.markup = markup;
	}

	@Column(name="markup", nullable=true, length=22)	
	public Integer getMarkup() {
		return markup;
	}

	public void setFreegoods(Integer freegoods) {
		this.freegoods = freegoods;
	}

	@Column(name="freegoods", nullable=true, length=22)	
	public Integer getFreegoods() {
		return freegoods;
	}

	public void setAboiList(String aboiList) {
		this.aboiList = aboiList;
	}

	@Column(name="aboi_list", nullable=true, length=4000)	
	public String getAboiList() {
		return aboiList;
	}

	public void setBicRemark(String bicRemark) {
		this.bicRemark = bicRemark;
	}

	@Column(name="bic_remark", nullable=true, length=4000)	
	public String getBicRemark() {
		return bicRemark;
	}

	public void setPurchaseRemark(String purchaseRemark) {
		this.purchaseRemark = purchaseRemark;
	}

	@Column(name="purchase_remark", nullable=true, length=4000)	
	public String getPurchaseRemark() {
		return purchaseRemark;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=22)	
	public Integer getStatus() {
		return status;
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

    @Column(name="version_code")
    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @Column(name="batch")
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

	@Column(name="old_aboi_list", nullable=true, length=4000)
	public String getOldAboiList() {
		return oldAboiList;
	}

	public void setOldAboiList(String oldAboiList) {
		this.oldAboiList = oldAboiList;
	}

	@Column(name="old_free_goods", nullable=true, length=22)
	public Integer getOldFreeGoods() {
		return oldFreeGoods;
	}

	public void setOldFreeGoods(Integer oldFreeGoods) {
		this.oldFreeGoods = oldFreeGoods;
	}
}
