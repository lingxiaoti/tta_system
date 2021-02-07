package com.sie.watsons.base.sync.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmSyncItemUdaFromRmsEntity_HI_RO Entity Object
 * Tue Aug 11 11:03:49 GMT+08:00 2020  Auto Generate
 */

public class PlmSyncItemUdaFromRmsEntity_HI_RO {
    private java.math.BigDecimal syncId;
    private String item;
    private String udaType;
    private String udaId;
    private String udaValue;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String lastUpdateId;
    private String processStatus;
    private Integer operatorUserId;

	public void setSyncId(java.math.BigDecimal syncId) {
		this.syncId = syncId;
	}

	
	public java.math.BigDecimal getSyncId() {
		return syncId;
	}

	public void setItem(String item) {
		this.item = item;
	}

	
	public String getItem() {
		return item;
	}

	public void setUdaType(String udaType) {
		this.udaType = udaType;
	}

	
	public String getUdaType() {
		return udaType;
	}

	public void setUdaId(String udaId) {
		this.udaId = udaId;
	}

	
	public String getUdaId() {
		return udaId;
	}

	public void setUdaValue(String udaValue) {
		this.udaValue = udaValue;
	}

	
	public String getUdaValue() {
		return udaValue;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	
	public String getProcessStatus() {
		return processStatus;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
