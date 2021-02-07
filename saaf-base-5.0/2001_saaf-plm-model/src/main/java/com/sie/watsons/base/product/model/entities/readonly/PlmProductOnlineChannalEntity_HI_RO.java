package com.sie.watsons.base.product.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductOnlineChannalEntity_HI_RO Entity Object
 * Thu Aug 29 10:51:50 CST 2019  Auto Generate
 */

public class PlmProductOnlineChannalEntity_HI_RO {
    private Integer channalId;
    private String channal;
    private String channalSub;
    private String channalUnique;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy ;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setChannalId(Integer channalId) {
		this.channalId = channalId;
	}

	
	public Integer getChannalId() {
		return channalId;
	}

	public void setChannal(String channal) {
		this.channal = channal;
	}

	
	public String getChannal() {
		return channal;
	}

	public void setChannalSub(String channalSub) {
		this.channalSub = channalSub;
	}

	
	public String getChannalSub() {
		return channalSub;
	}

	public void setChannalUnique(String channalUnique) {
		this.channalUnique = channalUnique;
	}

	
	public String getChannalUnique() {
		return channalUnique;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy (Integer createdBy ) {
		this.createdBy  = createdBy ;
	}

	
	public Integer getCreatedBy () {
		return createdBy ;
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
}
