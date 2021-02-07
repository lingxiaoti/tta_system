package com.sie.watsons.base.supplement.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSideAgrtLineEntity_HI Entity Object
 * Wed Jun 19 11:58:44 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SIDE_AGRT_LINE")
public class TtaSideAgrtLineEntity_HI {
    private Integer sideAgrtLId;//主键
    private Integer sideAgrtHId;//关联头信息id
    private String sideAgrtVersion;//关联头信息版本号
    private String proposalContractCode;//proposal合同号
    private String vendorName;//供应商名称
    private String vendorCode;//供应商编码
    private String status;//删除状态 Y/N
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setSideAgrtLId(Integer sideAgrtLId) {
		this.sideAgrtLId = sideAgrtLId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SIDE_AGRT_LINE", sequenceName="SEQ_TTA_SIDE_AGRT_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SIDE_AGRT_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="side_agrt_l_id", nullable=false, length=22)	
	public Integer getSideAgrtLId() {
		return sideAgrtLId;
	}

	public void setSideAgrtHId(Integer sideAgrtHId) {
		this.sideAgrtHId = sideAgrtHId;
	}

	@Column(name="side_agrt_h_id", nullable=true, length=22)	
	public Integer getSideAgrtHId() {
		return sideAgrtHId;
	}

	public void setSideAgrtVersion(String sideAgrtVersion) {
		this.sideAgrtVersion = sideAgrtVersion;
	}

	@Column(name="side_agrt_version", nullable=true, length=50)	
	public String getSideAgrtVersion() {
		return sideAgrtVersion;
	}

	public void setProposalContractCode(String proposalContractCode) {
		this.proposalContractCode = proposalContractCode;
	}

	@Column(name="proposal_contract_code", nullable=true, length=20)
	public String getProposalContractCode() {
		return proposalContractCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=200)	
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=true, length=50)	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)	
	public String getStatus() {
		return status;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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
}
