package com.sie.watsons.base.ttasastdtpl.model.entities;

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
 * TtaSaStdTplFieldCfgEntity_HI Entity Object
 * Tue Mar 31 15:25:16 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_SA_STD_TPL_FIELD_CFG")
public class TtaSaStdTplFieldCfgEntity_HI {
    private Integer saStdTplFieldCfgId;
    private String tplCode;
    private String filedName;
    private String filedType;
    private String dicCode;
    private String isEnable;
    private Integer orderNum;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setSaStdTplFieldCfgId(Integer saStdTplFieldCfgId) {
		this.saStdTplFieldCfgId = saStdTplFieldCfgId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_SA_STD_TPL_FIELD_CFG", sequenceName = "SEQ_TTA_SA_STD_TPL_FIELD_CFG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_SA_STD_TPL_FIELD_CFG", strategy = GenerationType.SEQUENCE)
	@Column(name="sa_std_tpl_field_cfg_id", nullable=false, length=22)	
	public Integer getSaStdTplFieldCfgId() {
		return saStdTplFieldCfgId;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	@Column(name="tpl_code", nullable=false, length=50)	
	public String getTplCode() {
		return tplCode;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	@Column(name="filed_name", nullable=false, length=200)	
	public String getFiledName() {
		return filedName;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	@Column(name="filed_type", nullable=false, length=2)	
	public String getFiledType() {
		return filedType;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	@Column(name="dic_code", nullable=true, length=80)	
	public String getDicCode() {
		return dicCode;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=false, length=2)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name="order_num", nullable=true, length=22)	
	public Integer getOrderNum() {
		return orderNum;
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
}
