package com.sie.watsons.base.params.model.entities.readonly;

import java.io.Serializable;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TempParamDefEntity_HI_RO Entity Object
 * Tue May 28 19:49:34 CST 2019  Auto Generate
 */

public class TempParamDefEntity_HI_RO implements Serializable {
	
	public static final String QUERY_SQL = "select \n" +
			"t.param_id,\n" +
			"t.param_key,\n" +
			"to_char(t.param_content) as param_content,\n" +
			"t.is_sql,\n" +
			"t.remark,\n" +
			"t.created_by,\n" +
			"t.last_updated_by,\n" +
			"t.last_update_date,\n" +
			"t.creation_date,\n" +
			"t.last_update_login,\n" +
			"t.version_num \n from tta_temp_param_def t" +
			" where 1 = 1 ";

	public static final String QUERY_PARAM_BY_RULE = "select\n" +
			"b.rule_param_id,\n " +
			"t.param_id,\n " +
			"t.param_key, \n" +
			"to_char(t.param_content) as param_content, \n" +
			"t.is_sql, \n" +
			"t.remark\n" +
			" from tta_temp_param_def t\n" +
			"inner join tta_temp_param_rule_midle b\n" +
			"on t.param_id = b.param_id\n" +
			"where 1 = 1 \n";

	public static final String QUERY_PARAM_BY_RULE_FIELD = "select\n" +
			"b.rule_param_id,\n" +
			" t.param_id,\n" +
			" t.param_key, \n" +
			"to_char(t.param_content) as param_content, \n" +
			"t.is_sql, \n" +
			"t.remark\n" +
			" from tta_temp_param_def t\n" +
			"inner join tta_temp_param_rule_midle b on t.param_id = b.param_id\n" +
			"join tta_temp_rule_def tt on b.rule_id = tt.rul_id\n" +
			"where 1 = 1 ";

	public static final String QUERY_NOT_EXISTS_PARAMS = "select \n" +
			" 	t.param_id,\n" +
			"   t.param_key,\n" +
			"   to_char(t.param_content) as param_content,\n" +
			"   t.is_sql,\n" +
			"   t.remark\n" +
			" from tta_temp_param_def t\n" +
			" where not exists (select 1\n" +
			" from tta_temp_param_rule_midle a\n" +
			" where a.param_id = t.param_id\n" +
			" and a.rule_id = :ruleId)";


	private Integer paramId;
    private String paramKey;
    private String paramContent;
    private String isSql;
    private String remark;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private Integer ruleParamId;

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	
	public Integer getParamId() {
		return paramId;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	
	public String getParamKey() {
		return paramKey;
	}

	public void setParamContent(String paramContent) {
		this.paramContent = paramContent;
	}

	
	public String getParamContent() {
		return paramContent;
	}

	public void setIsSql(String isSql) {
		this.isSql = isSql;
	}

	
	public String getIsSql() {
		return isSql;
	}


	public void setRuleParamId(Integer ruleParamId) {
		this.ruleParamId = ruleParamId;
	}

	public Integer getRuleParamId() {
		return ruleParamId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
