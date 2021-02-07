package com.sie.watsons.base.withdrawal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSupplierItemRelSupplierEntity_HI Entity Object
 * Fri Aug 09 00:28:34 CST 2019  Auto Generate
 *  proposal拆分与合并中的供应商选择------>关联供应商表的实体类
 */
@Entity
@Table(name="tta_supplier_item_rel_supplier")
public class TtaSupplierItemRelSupplierEntity_HI {
    private Integer supItemRelSId;//主键
    private String relSupplierCode;//关联供应商编号
    private String relSupplierName;//关联供应商名字
    private Integer supplierItemHId;//proposal拆分与合并单据头id
    private String marjorSupplierCode;//主供应商编号
    private String marjorSupplierName;//主供应商名称
    private String status;//状态
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setSupItemRelSId(Integer supItemRelSId) {
		this.supItemRelSId = supItemRelSId;
	}

	@Id
	@SequenceGenerator(name="seq_tta_supplier_item_rel_sup", sequenceName="seq_tta_supplier_item_rel_sup", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="seq_tta_supplier_item_rel_sup",strategy=GenerationType.SEQUENCE)
	@Column(name="sup_item_rel_s_id", nullable=false, length=22)	
	public Integer getSupItemRelSId() {
		return supItemRelSId;
	}

	public void setRelSupplierCode(String relSupplierCode) {
		this.relSupplierCode = relSupplierCode;
	}

	@Column(name="rel_supplier_code", nullable=false, length=50)	
	public String getRelSupplierCode() {
		return relSupplierCode;
	}

	public void setRelSupplierName(String relSupplierName) {
		this.relSupplierName = relSupplierName;
	}

	@Column(name="rel_supplier_name", nullable=false, length=230)	
	public String getRelSupplierName() {
		return relSupplierName;
	}

	public void setSupplierItemHId(Integer supplierItemHId) {
		this.supplierItemHId = supplierItemHId;
	}

	@Column(name="supplier_item_h_id", nullable=false, length=22)	
	public Integer getSupplierItemHId() {
		return supplierItemHId;
	}

	public void setMarjorSupplierCode(String marjorSupplierCode) {
		this.marjorSupplierCode = marjorSupplierCode;
	}

	@Column(name="marjor_supplier_code", nullable=true, length=50)	
	public String getMarjorSupplierCode() {
		return marjorSupplierCode;
	}

	public void setMarjorSupplierName(String marjorSupplierName) {
		this.marjorSupplierName = marjorSupplierName;
	}

	@Column(name="marjor_supplier_name", nullable=true, length=230)	
	public String getMarjorSupplierName() {
		return marjorSupplierName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=1)	
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
