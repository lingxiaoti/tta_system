package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaAnalysisEditDataEntity_HI_RO Entity Object
 * Mon Sep 23 14:44:03 CST 2019  Auto Generate
 */

public class TtaAnalysisEditDataEntity_HI_RO {
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

    public static final String  QUERY = "select * from TTA_ANALYSIS_EDIT_DATA d where 1=1 ";

    public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setProposalid(String proposalid) {
		this.proposalid = proposalid;
	}

	
	public String getProposalid() {
		return proposalid;
	}

	public void setMarkup(Integer markup) {
		this.markup = markup;
	}

	
	public Integer getMarkup() {
		return markup;
	}

	public void setFreegoods(Integer freegoods) {
		this.freegoods = freegoods;
	}

	
	public Integer getFreegoods() {
		return freegoods;
	}

	public void setAboiList(String aboiList) {
		this.aboiList = aboiList;
	}

	
	public String getAboiList() {
		return aboiList;
	}

	public void setBicRemark(String bicRemark) {
		this.bicRemark = bicRemark;
	}

	
	public String getBicRemark() {
		return bicRemark;
	}

	public void setPurchaseRemark(String purchaseRemark) {
		this.purchaseRemark = purchaseRemark;
	}

	
	public String getPurchaseRemark() {
		return purchaseRemark;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
