package com.sie.watsons.base.sync.model.entities;

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
 * PlmSyncItemUdaFromRmsEntity_HI Entity Object
 * Tue Aug 11 11:03:49 GMT+08:00 2020  Auto Generate
 */
@Entity
@Table(name="PLM_SYNC_ITEM_UDA_FROM_RMS")
public class PlmSyncItemUdaFromRmsEntity_HI {
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

	@Id
	@SequenceGenerator(name="plmSyncItemUdaFromRmsEntity_HISeqGener", sequenceName="SEQ_PLM_SYNC_ITEM_UDA_FROM_RMS", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmSyncItemUdaFromRmsEntity_HISeqGener",strategy=GenerationType.SEQUENCE)
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

	public void setUdaType(String udaType) {
		this.udaType = udaType;
	}

	@Column(name="uda_type", nullable=true, length=10)	
	public String getUdaType() {
		return udaType;
	}

	public void setUdaId(String udaId) {
		this.udaId = udaId;
	}

	@Column(name="uda_id", nullable=true, length=10)	
	public String getUdaId() {
		return udaId;
	}

	public void setUdaValue(String udaValue) {
		this.udaValue = udaValue;
	}

	@Column(name="uda_value", nullable=true, length=300)	
	public String getUdaValue() {
		return udaValue;
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
