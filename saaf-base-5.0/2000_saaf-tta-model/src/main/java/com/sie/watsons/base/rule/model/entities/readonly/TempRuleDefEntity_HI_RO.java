package com.sie.watsons.base.rule.model.entities.readonly;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;

/**
 * TempRuleDefEntity_HI_RO Entity Object
 * Wed May 29 16:12:16 CST 2019  Auto Generate
 */

public class TempRuleDefEntity_HI_RO implements Serializable {
	
	public static final String QUERY_SQL = "select " +
			"t.rul_id,\n" +
			"(select v.meaning from base_lookup_values v " + 
				"where v.lookup_code = t.sole_resouce_type and v.lookup_type = 'SOLE_RESOUCE_TYPE') as sole_resouce_type_name,\n" +
			"(select v.meaning from base_lookup_values v " +
			" where v.lookup_code = t.sole_product_type and v.lookup_type = 'SOLE_PRODUCT_TYPE') sole_product_type_name,\n" +
			"t.rule_name,\n" +
			"t.is_enable,\n" +
			"t.sole_resouce_type,\n" +
			"t.sole_product_type,\n" +
			"t.is_include_ec,\n" +
			"t.is_include_special,\n" +
			"t.file_url,\n" +
			"t.remark,\n" +
			"t.created_by,\n" +
			"t.last_updated_by,\n" +
			"t.last_update_date,\n" +
			"t.creation_date,\n" +
			"t.last_update_login,\n" +
			"t.version_num \n from tta_temp_rule_def t where 1 = 1 \n";

	public static final String SELECT_RULEINFO_LIST = " select t.rul_id  rulId,\n" +
			"        t.rule_name ruleName,\n" +
			"        t.is_enable isEnable,\n" +
			"        t.sole_resouce_type soleResouceType,\n" +
			"        t.sole_product_type soleProductType,\n" +
			"        t.is_include_ec isIncludeEc,\n" +
			"        t.is_include_special isIncludeSpecial,\n" +
			"        t.file_url fileUrl,\n" +
			"        t.remark remark,\n" +
			"        t.created_by createdBy,\n" +
			"        t.last_updated_by lastUpdatedBy,\n" +
			"        t.last_update_date lastUpdateDate,\n" +
			"        t.creation_date creationDate,\n" +
			"        t.last_update_login lastUpdateLogin,\n" +
			"        t.version_num versionNum\n" +
			"   from tta_temp_rule_def t where 1=1  ";



	public static final String QUERY_RULE_BY_PARAM = "select listagg(b.rule_name, '，') within group(order by b.rule_name) as ruleName \n" +
			"  from tta_temp_param_rule_midle a\n" +
			" inner join tta_temp_rule_def b\n" +
			"    on a.rule_id = b.rul_id\n" +
			" where 1 =1 ";

	private Integer rulId;
    private String ruleName;
    private String isEnable;
    private Integer soleResouceType;
    private Integer soleProductType;
    private String isIncludeEc;
    private String isIncludeSpecial;
    private String fileUrl;
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
    private String soleResouceTypeName;//独家渠道类别名称
    private String soleProductTypeName;//独家产品类型名称
	private List<TempParamDefEntity_HI_RO> tempParamDefEntityLiist = null;

	public void setRulId(Integer rulId) {
		this.rulId = rulId;
	}

	
	public Integer getRulId() {
		return rulId;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	
	public String getRuleName() {
		return ruleName;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
	}

	public void setSoleResouceType(Integer soleResouceType) {
		this.soleResouceType = soleResouceType;
	}

	
	public Integer getSoleResouceType() {
		return soleResouceType;
	}

	public void setSoleProductType(Integer soleProductType) {
		this.soleProductType = soleProductType;
	}

	
	public Integer getSoleProductType() {
		return soleProductType;
	}

	public void setIsIncludeEc(String isIncludeEc) {
		this.isIncludeEc = isIncludeEc;
	}

	
	public String getIsIncludeEc() {
		return isIncludeEc;
	}

	public void setIsIncludeSpecial(String isIncludeSpecial) {
		this.isIncludeSpecial = isIncludeSpecial;
	}

	
	public String getIsIncludeSpecial() {
		return isIncludeSpecial;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	
	public String getFileUrl() {
		return fileUrl;
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

	public void setSoleProductTypeName(String soleProductTypeName) {
		this.soleProductTypeName = soleProductTypeName;
	}

	public String getSoleProductTypeName() {
		return soleProductTypeName;
	}

	public String getSoleResouceTypeName() {
		return soleResouceTypeName;
	}

	public void setSoleResouceTypeName(String soleResouceTypeName) {
		this.soleResouceTypeName = soleResouceTypeName;
	}

	public void setTempParamDefEntityLiist(List<TempParamDefEntity_HI_RO> tempParamDefEntityLiist) {
		this.tempParamDefEntityLiist = tempParamDefEntityLiist;
	}

	public List<TempParamDefEntity_HI_RO> getTempParamDefEntityLiist() {
		return tempParamDefEntityLiist;
	}
}
