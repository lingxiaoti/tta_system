package com.sie.watsons.base.elecsign.model.entities;

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
 * TtaAttrCheckItemEntity_HI Entity Object
 * Thu Jun 18 18:45:57 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_attr_check_item")
public class TtaAttrCheckItemEntity_HI {
    private Integer attrCheckItemId;
    private Integer elecConHeaderId;
    private String attrCode;
    private String attrName;
    private String isCheck;
    private Integer orderNo;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private Integer versionNum;
	private String isRead;

	public void setAttrCheckItemId(Integer attrCheckItemId) {
		this.attrCheckItemId = attrCheckItemId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_ATTR_CHECK_ITEM", sequenceName="SEQ_TTA_ATTR_CHECK_ITEM", allocationSize=1)
	@GeneratedValue(generator="SEQ_TTA_ATTR_CHECK_ITEM",strategy=GenerationType.SEQUENCE)
	@Column(name="attr_check_item_id", nullable=false, length=22)	
	public Integer getAttrCheckItemId() {
		return attrCheckItemId;
	}

	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}

	@Column(name="elec_con_header_id", nullable=false, length=22)	
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	@Column(name="attr_code", nullable=true, length=50)	
	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	@Column(name="attr_name", nullable=true, length=500)	
	public String getAttrName() {
		return attrName;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	@Column(name="is_check", nullable=true, length=2)	
	public String getIsCheck() {
		return isCheck;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=22)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=50)	
	public String getStatus() {
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

	@Column(name="version_num", nullable=true, length=22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Column(name="is_read", nullable=true, length=2)
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}
