package com.sie.watsons.base.supplement.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSaStdFieldLineEntity_HI_RO Entity Object
 * Tue Mar 31 15:25:15 CST 2020  Auto Generate
 */

public class TtaSaStdFieldLineEntity_HI_RO {
    private Integer saStdFieldLineId;
    private Integer saStdHeaderId;//单据头信息id
    private Integer saStdFieldCfgLineId;//字段配置行表id
    private String filedName;//字段名
    private String filedValue;//字段值
    private String filedType;//字段类型
    private Integer orderNum;//排序
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String tplCode;//字段代号
	private String dicCode;//字典值

	public static final String QUERE_STD_FIELD_LINE = "select tssf.sa_std_field_line_id saStdFieldLineId,\n" +
			"       tssf.sa_std_header_id saStdHeaderId,\n" +
			"       tssf.sa_std_field_cfg_line_id saStdFieldCfgLineId,\n" +
			"       tssf.filed_name filedName,\n" +
			"       tssf.filed_value filedValue,\n" +
			"       tssf.filed_type filedType,\n" +
			"       tssf.order_num orderNum,\n" +
			"       tssf.version_num versionNum,\n" +
			"       tssf.creation_date creationDate,\n" +
			"       tssf.created_by createdBy,\n" +
			"       tssf.last_updated_by lastUpdatedBy,\n" +
			"       tssf.last_update_date lastUpdateDate,\n" +
			"       tssf.last_update_login lastUpdateLogin,\n" +
			"       tssf.tpl_code tplCode,\n" +
			"       tssf.dic_code dicCode\n" +
			"  from tta_sa_std_field_line tssf where 1=1";

	public void setSaStdFieldLineId(Integer saStdFieldLineId) {
		this.saStdFieldLineId = saStdFieldLineId;
	}

	
	public Integer getSaStdFieldLineId() {
		return saStdFieldLineId;
	}

	public void setSaStdHeaderId(Integer saStdHeaderId) {
		this.saStdHeaderId = saStdHeaderId;
	}

	
	public Integer getSaStdHeaderId() {
		return saStdHeaderId;
	}

	public void setSaStdFieldCfgLineId(Integer saStdFieldCfgLineId) {
		this.saStdFieldCfgLineId = saStdFieldCfgLineId;
	}

	
	public Integer getSaStdFieldCfgLineId() {
		return saStdFieldCfgLineId;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	
	public String getFiledName() {
		return filedName;
	}

	public void setFiledValue(String filedValue) {
		this.filedValue = filedValue;
	}

	
	public String getFiledValue() {
		return filedValue;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	
	public String getFiledType() {
		return filedType;
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

	public String getTplCode() {
		return tplCode;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
}
