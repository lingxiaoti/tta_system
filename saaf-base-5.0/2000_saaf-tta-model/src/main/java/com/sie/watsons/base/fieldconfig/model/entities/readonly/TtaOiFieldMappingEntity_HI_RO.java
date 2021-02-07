package com.sie.watsons.base.fieldconfig.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiFieldMappingEntity_HI_RO Entity Object
 * Tue Nov 12 12:12:43 CST 2019  Auto Generate
 */

public class TtaOiFieldMappingEntity_HI_RO {
    private Integer fieldId;
    private Integer tradeYear;
    private String sourceFieldName;
    private String sourceFieldRemark;
    private String targetFieldName;
    private String targetFieldRemark;
    private String businessType;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String businessName;
    private String isEnable;

    public static final String QUERY_LIST_SQL = "select om.field_id,\n" +
			"       om.trade_year,\n" +
			"       om.source_field_name,\n" +
			"       om.source_field_remark,\n" +
			"       om.target_field_name,\n" +
			"       om.target_field_remark,\n" +
			"       om.business_type,\n" +
			"       om.is_enable,\n" +
			"       om.created_by,\n" +
			"       om.last_updated_by,\n" +
			"       om.last_update_date,\n" +
			"       om.creation_date,\n" +
			"       om.last_update_login,\n" +
			"       om.version_num,\n" +
			"       bv.meaning as business_name\n" +
			"  from tta_oi_field_mapping om\n" +
			"  left join base_lookup_values bv\n" +
			"    on bv.LOOKUP_TYPE = 'TTA_OI_BUSINESS_TYPE'\n" +
			"   and bv.enabled_flag = 'Y'\n" +
			"   and bv.start_date_active < sysdate\n" +
			"   and (bv.end_date_active is null or bv.end_date_active >= sysdate)\n" +
			"   and bv.delete_flag = 0\n" +
			"   and om.business_type = bv.lookup_code\n" +
			" where 1 = 1 ";

    public static final String RESOURCE_FIED_SQL = "select t.column_name as source_field_name, \n" +
			"t.comments as source_field_remark \n" +
			" from  user_col_comments t \n" +
			" where t.table_name='TTA_OI_SUMMARY_LINE' and t.comments not like '%EX%'";

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}


	public Integer getFieldId() {
		return fieldId;
	}

	public void setTradeYear(Integer tradeYear) {
		this.tradeYear = tradeYear;
	}


	public Integer getTradeYear() {
		return tradeYear;
	}

	public void setSourceFieldName(String sourceFieldName) {
		this.sourceFieldName = sourceFieldName;
	}


	public String getSourceFieldName() {
		return sourceFieldName;
	}

	public void setSourceFieldRemark(String sourceFieldRemark) {
		this.sourceFieldRemark = sourceFieldRemark;
	}


	public String getSourceFieldRemark() {
		return sourceFieldRemark;
	}

	public void setTargetFieldName(String targetFieldName) {
		this.targetFieldName = targetFieldName;
	}


	public String getTargetFieldName() {
		return targetFieldName;
	}

	public void setTargetFieldRemark(String targetFieldRemark) {
		this.targetFieldRemark = targetFieldRemark;
	}


	public String getTargetFieldRemark() {
		return targetFieldRemark;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getBusinessType() {
		return businessType;
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

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getIsEnable() {
		return isEnable;
	}

}
