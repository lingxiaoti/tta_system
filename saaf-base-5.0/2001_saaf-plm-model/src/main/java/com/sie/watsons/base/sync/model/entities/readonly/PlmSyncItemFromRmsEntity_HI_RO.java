package com.sie.watsons.base.sync.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmSyncItemFromRmsEntity_HI_RO Entity Object
 * Tue Aug 11 11:03:49 GMT+08:00 2020  Auto Generate
 */

public class PlmSyncItemFromRmsEntity_HI_RO {
    private java.math.BigDecimal syncId;
    private String item;
    private String itemDesc;
    private String groupNo;
    private String dept;
    private String classes;
    private String subclass;
    private String wastePct;
    private String status;
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

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	
	public String getItemDesc() {
		return itemDesc;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	
	public String getGroupNo() {
		return groupNo;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	
	public String getDept() {
		return dept;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	
	public String getClasses() {
		return classes;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	
	public String getSubclass() {
		return subclass;
	}

	public void setWastePct(String wastePct) {
		this.wastePct = wastePct;
	}

	
	public String getWastePct() {
		return wastePct;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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
