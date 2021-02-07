package com.sie.watsons.base.sync.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * PlmSyncItemFromRmsEntity_HI Entity Object
 * Tue Aug 11 11:03:49 GMT+08:00 2020  Auto Generate
 */
@Entity
@Table(name="PLM_SYNC_ITEM_FROM_RMS")
public class PlmSyncItemFromRmsEntity_HI {
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

	@Id
	@SequenceGenerator(name="plmSyncItemFromRmsEntity_HISeqGener", sequenceName="SEQ_PLM_SYNC_ITEM_FROM_RMS", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmSyncItemFromRmsEntity_HISeqGener",strategy=GenerationType.SEQUENCE)
	@Column(name="sync_id", nullable=true, length=22)	
	public java.math.BigDecimal getSyncId() {
		return syncId;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name="item", nullable=true, length=30)	
	public String getItem() {
		return item;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Column(name="item_desc", nullable=true, length=300)	
	public String getItemDesc() {
		return itemDesc;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	@Column(name="group_no", nullable=true, length=10)	
	public String getGroupNo() {
		return groupNo;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name="dept", nullable=true, length=10)	
	public String getDept() {
		return dept;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	@Column(name="classes", nullable=true, length=10)	
	public String getClasses() {
		return classes;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	@Column(name="subclass", nullable=true, length=10)	
	public String getSubclass() {
		return subclass;
	}

	public void setWastePct(String wastePct) {
		this.wastePct = wastePct;
	}

	@Column(name="waste_pct", nullable=true, length=10)	
	public String getWastePct() {
		return wastePct;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=10)	
	public String getStatus() {
		return status;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Column(name="last_update_datetime", nullable=true, length=7)	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	@Column(name="last_update_id", nullable=true, length=20)	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name="process_status", nullable=true, length=10)	
	public String getProcessStatus() {
		return processStatus;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
