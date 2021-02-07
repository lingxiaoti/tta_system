package com.sie.watsons.base.newbreedextend.model.entities;

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
 * TtaNewbreedExtendLineEntity_HI Entity Object
 * Wed Jun 26 14:15:21 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_newbreed_extend_line")
public class TtaNewbreedExtendLineEntity_HI {
    private Integer newbreedExtendLId;
    private Integer newbreedExtendHId;
    private String breadName;
    private java.math.BigDecimal standard;
    private String chargeMethod;
    private String feeStandard;
    private String unit;
    private Integer breadQty;
    private String beyondChargeMethod;
    private String beyondFeeStandard;
    private String beyondUnit;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setNewbreedExtendLId(Integer newbreedExtendLId) {
		this.newbreedExtendLId = newbreedExtendLId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_TTA_NEWBREED_EXTEND_LINE", sequenceName = "SEQ_TTA_NEWBREED_EXTEND_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_NEWBREED_EXTEND_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="newbreed_extend_l_id", nullable=false, length=22)	
	public Integer getNewbreedExtendLId() {
		return newbreedExtendLId;
	}

	public void setNewbreedExtendHId(Integer newbreedExtendHId) {
		this.newbreedExtendHId = newbreedExtendHId;
	}

	@Column(name="newbreed_extend_h_id", nullable=true, length=22)	
	public Integer getNewbreedExtendHId() {
		return newbreedExtendHId;
	}

	public void setBreadName(String breadName) {
		this.breadName = breadName;
	}

	@Column(name="bread_name", nullable=true, length=100)	
	public String getBreadName() {
		return breadName;
	}

	public void setStandard(java.math.BigDecimal standard) {
		this.standard = standard;
	}

	@Column(name="standard", nullable=true, length=22)	
	public java.math.BigDecimal getStandard() {
		return standard;
	}

	public void setChargeMethod(String chargeMethod) {
		this.chargeMethod = chargeMethod;
	}

	@Column(name="charge_method", nullable=true, length=5)	
	public String getChargeMethod() {
		return chargeMethod;
	}

	public void setFeeStandard(String feeStandard) {
		this.feeStandard = feeStandard;
	}

	@Column(name="fee_standard", nullable=true, length=100)	
	public String getFeeStandard() {
		return feeStandard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=100)	
	public String getUnit() {
		return unit;
	}

	public void setBreadQty(Integer breadQty) {
		this.breadQty = breadQty;
	}

	@Column(name="bread_qty", nullable=true, length=22)	
	public Integer getBreadQty() {
		return breadQty;
	}

	public void setBeyondChargeMethod(String beyondChargeMethod) {
		this.beyondChargeMethod = beyondChargeMethod;
	}

	@Column(name="beyond_charge_method", nullable=true, length=5)	
	public String getBeyondChargeMethod() {
		return beyondChargeMethod;
	}

	public void setBeyondFeeStandard(String beyondFeeStandard) {
		this.beyondFeeStandard = beyondFeeStandard;
	}

	@Column(name="beyond_fee_standard", nullable=true, length=100)	
	public String getBeyondFeeStandard() {
		return beyondFeeStandard;
	}

	public void setBeyondUnit(String beyondUnit) {
		this.beyondUnit = beyondUnit;
	}

	@Column(name="beyond_unit", nullable=true, length=100)	
	public String getBeyondUnit() {
		return beyondUnit;
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
