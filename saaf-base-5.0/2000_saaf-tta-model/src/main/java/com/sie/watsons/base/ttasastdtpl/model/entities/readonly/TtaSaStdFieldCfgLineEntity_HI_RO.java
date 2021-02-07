package com.sie.watsons.base.ttasastdtpl.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSaStdFieldCfgLineEntity_HI_RO Entity Object
 * Wed Apr 01 14:17:09 CST 2020  Auto Generate
 */

public class TtaSaStdFieldCfgLineEntity_HI_RO {
	public static final String TTA_TSSFCL_LIST = " select tssfcl.sa_std_field_cfg_line_id,\n" +
			"               tssfcl.sa_std_tpl_field_cfg_id,\n" +
			"               tssfcl.sa_std_tpl_def_header_id,\n" +
			"               tssfcl.tpl_code,\n" +
			"               tssfcl.filed_name,\n" +
			"               tssfcl.filed_type,\n" +
			"               tssfcl.dic_code,\n" +
			"               tssfcl.is_enable,\n" +
            "               decode(tssfcl.is_enable,'Y','是','N','否') is_enable_name,\n" +
			"               tssfcl.order_num,\n" +
			"               tssfcl.version_num,\n" +
			"               tssfcl.creation_date,\n" +
			"               tssfcl.created_by,\n" +
			"               tssfcl.last_updated_by,\n" +
			"               tssfcl.last_update_date,\n" +
			"               tssfcl.last_update_login\n" +
			"                from tta_sa_std_field_cfg_line tssfcl where 1=1 ";
    private Integer saStdFieldCfgLineId;
    private Integer saStdTplFieldCfgId;
    private Integer saStdTplDefHeaderId;
    private String tplCode;
    private String filedName;
    private String filedType;
    private String dicCode;
    private String isEnable;
    private String isEnableName;
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

    public static final String QUERE_STD_FIELD = "select tssf.sa_std_field_cfg_line_id saStdFieldCfgLineId,\n" +
			"       tssf.sa_std_tpl_field_cfg_id saStdTplFieldCfgId,\n" +
			"       tssf.sa_std_tpl_def_header_id saStdTplDefHeaderId,\n" +
			"       tssf.tpl_code tplCode,\n" +
			"       tssf.filed_name filedName,\n" +
			"       tssf.filed_type filedType,\n" +
			"       tssf.dic_code dicCode,\n" +
			"       tssf.is_enable isEnable,\n" +
			"       tssf.order_num orderNum\n" +
			"  from tta_sa_std_field_cfg_line tssf where 1=1";

	public void setSaStdFieldCfgLineId(Integer saStdFieldCfgLineId) {
		this.saStdFieldCfgLineId = saStdFieldCfgLineId;
	}

	
	public Integer getSaStdFieldCfgLineId() {
		return saStdFieldCfgLineId;
	}

	public void setSaStdTplFieldCfgId(Integer saStdTplFieldCfgId) {
		this.saStdTplFieldCfgId = saStdTplFieldCfgId;
	}

	
	public Integer getSaStdTplFieldCfgId() {
		return saStdTplFieldCfgId;
	}

	public void setSaStdTplDefHeaderId(Integer saStdTplDefHeaderId) {
		this.saStdTplDefHeaderId = saStdTplDefHeaderId;
	}

	
	public Integer getSaStdTplDefHeaderId() {
		return saStdTplDefHeaderId;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	
	public String getTplCode() {
		return tplCode;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	
	public String getFiledName() {
		return filedName;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	
	public String getFiledType() {
		return filedType;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	
	public String getDicCode() {
		return dicCode;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	
	public Integer getOrderNum() {
		return orderNum;
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

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
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

	public String getIsEnableName() {
		return isEnableName;
	}

	public void setIsEnableName(String isEnableName) {
		this.isEnableName = isEnableName;
	}
}
