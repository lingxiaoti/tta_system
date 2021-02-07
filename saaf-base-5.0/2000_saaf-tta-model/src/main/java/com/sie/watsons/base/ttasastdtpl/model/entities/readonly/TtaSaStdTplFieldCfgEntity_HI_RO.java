package com.sie.watsons.base.ttasastdtpl.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSaStdTplFieldCfgEntity_HI_RO Entity Object
 * Tue Mar 31 15:25:16 CST 2020  Auto Generate
 */

public class TtaSaStdTplFieldCfgEntity_HI_RO {
	public static final String TTA_TSSTF_LIST = "SELECT tsstf.sa_std_tpl_field_cfg_id,\n" +
			"       tsstf.tpl_code,\n" +
			"       tsstf.filed_name,\n" +
			"       tsstf.filed_type,\n" +
			"       blv.meaning filed_type_name,\n" +
			"       tsstf.dic_code,\n" +
			"       tsstf.is_enable,\n" +
			"       decode(tsstf.is_enable,'Y','是','N','否') is_enable_name,\n" +
			"       tsstf.order_num,\n" +
			"       tsstf.version_num,\n" +
			"       tsstf.creation_date,\n" +
			"       tsstf.created_by,\n" +
			"       tsstf.last_updated_by,\n" +
			"       tsstf.last_update_date,\n" +
			"       tsstf.last_update_login\n" +
			"       \n" +
			"        FROM TTA_SA_STD_TPL_FIELD_CFG tsstf \n" +
			"        left join base_lookup_values blv on blv.lookup_type = 'TTA_FIELD_TYPE' and blv.lookup_code = tsstf.filed_type  where 1=1 ";
    private Integer saStdTplFieldCfgId;
    private String tplCode;
    private String filedName;
    private String filedType;
	private String filedTypeName;
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

	public void setSaStdTplFieldCfgId(Integer saStdTplFieldCfgId) {
		this.saStdTplFieldCfgId = saStdTplFieldCfgId;
	}

	
	public Integer getSaStdTplFieldCfgId() {
		return saStdTplFieldCfgId;
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

	public String getFiledTypeName() {
		return filedTypeName;
	}

	public void setFiledTypeName(String filedTypeName) {
		this.filedTypeName = filedTypeName;
	}

	public String getIsEnableName() {
		return isEnableName;
	}

	public void setIsEnableName(String isEnableName) {
		this.isEnableName = isEnableName;
	}
}
