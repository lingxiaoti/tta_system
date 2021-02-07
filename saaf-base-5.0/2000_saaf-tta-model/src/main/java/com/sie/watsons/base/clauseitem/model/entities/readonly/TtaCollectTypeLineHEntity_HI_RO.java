package com.sie.watsons.base.clauseitem.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaCollectTypeLineHEntity_HI_RO Entity Object
 * Mon Jul 01 16:05:10 CST 2019  Auto Generate
 */

public class TtaCollectTypeLineHEntity_HI_RO {

	public static final String QUERY_CURRENT_LIST = "select tctl.collect_type_id,\n" +
			"       tctl.clause_id,\n" +
			"       tctl.collect_type,\n" +
			"       tctl.standard_value,\n" +
			"       tctl.unit_value,\n" +
			"       tctl.is_enable,\n" +
			"       tctl.is_default_value,\n" +
			"       tctl.version_num,\n" +
			"       tctl.creation_date,\n" +
			"       tctl.created_by,\n" +
			"       tctl.last_updated_by,\n" +
			"       tctl.last_update_date,\n" +
			"       tctl.last_update_login,\n" +
			"       tctl.team_framework_id,\n" +
			"       tctl.resouce_id,\n" +
			"       tctl.rule,\n" +
			"       tctl.delete_flag,\n" +
			"       tctl.parent_id,\n" +
			"       tctl.is_major,\n" +
			"       p.unit_value parentValue\n" +
			"       from  tta_collect_type_line_h  tctl,\n" +
			"             tta_collect_type_line_h   p \n" +
			"         where tctl.team_framework_id = p.team_framework_id(+)\n" +
			"         and tctl.parent_id = p.collect_type_id(+)";
    private Integer collectTypeId;
    private Integer clauseId;
    private String collectType;
    private String standardValue;
    private String unitValue;
    private String isEnable;
    private String isDefaultValue;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer team_framework_id;
	private Integer resouce_id;
	private Integer deleteFlag;
	private String isMajor;
	private String parentValue;
	private Integer parentId;
	private String rule;
	public void setCollectTypeId(Integer collectTypeId) {
		this.collectTypeId = collectTypeId;
	}

	
	public Integer getCollectTypeId() {
		return collectTypeId;
	}

	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}

	
	public Integer getClauseId() {
		return clauseId;
	}

	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}

	
	public String getCollectType() {
		return collectType;
	}

	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}

	
	public String getStandardValue() {
		return standardValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	
	public String getUnitValue() {
		return unitValue;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsDefaultValue(String isDefaultValue) {
		this.isDefaultValue = isDefaultValue;
	}

	
	public String getIsDefaultValue() {
		return isDefaultValue;
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

	public Integer getTeam_framework_id() {
		return team_framework_id;
	}

	public void setTeam_framework_id(Integer team_framework_id) {
		this.team_framework_id = team_framework_id;
	}

	public Integer getResouce_id() {
		return resouce_id;
	}

	public void setResouce_id(Integer resouce_id) {
		this.resouce_id = resouce_id;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}

	public String getParentValue() {
		return parentValue;
	}

	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
}
