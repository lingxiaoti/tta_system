package com.sie.saaf.base.shiro.model.entities.readonly;

/**
 * @author ZhangJun
 * @createTime 2018-01-17 11:44
 * @description
 */
public class BasePrivilegeRecordDetail_HI_RO {
	public static final String QUERY_PRIVILEGE_RECORD_DETAIL = "SELECT\n" +
			"    basePrivilegeRecord.privilege_record_id as privilegeRecordId,\n" +
			"    basePrivilegeRecord.org_id as orgId,\n" +
			"    basePrivilegeRecord.access_type as accessType,\n" +
			"    basePrivilegeRecord.business_table_name as businessTableName,\n" +
			"    basePrivilegeRecord.business_code as businessCode,\n" +
			"    basePrivilegeRecord.enabled as enabled,\n" +
			"    basePrivilegeDetail.privilege_detail_code as privilegeDetailCode,\n" +
			"    basePrivilegeDetail.privilege_detail_value as privilegeDetailValue\n" +
			"FROM\n" +
			"    base_privilege_record AS basePrivilegeRecord ,\n" +
			"    base_privilege_detail AS basePrivilegeDetail\n" +
			"WHERE\n" +
			"    basePrivilegeRecord.privilege_record_id = basePrivilegeDetail.privilege_record_id\n" +
			"    and basePrivilegeRecord.enabled='Y'" +
			"    and basePrivilegeRecord.privilege_record_id=:privilegeRecordId";

	/*base_privilege_record*/
	private Integer privilegeRecordId;//授权头Id
	private Integer orgId;//库存组织Id
	private String accessType;//访问类型
	private String businessTableName = "base_channel";//业务表名
	private String businessTableCode;//业务表值（ID或Code等）
	private String enabled;//是否启用

	/*base_privilege_detail*/
	private String privilegeDetailCode;//Code
	private String privilegeDetailValue;//值

	public Integer getPrivilegeRecordId() {
		return privilegeRecordId;
	}

	public void setPrivilegeRecordId(Integer privilegeRecordId) {
		this.privilegeRecordId = privilegeRecordId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getBusinessTableName() {
		return businessTableName;
	}

	public void setBusinessTableName(String businessTableName) {
		this.businessTableName = businessTableName;
	}

	public String getBusinessTableCode() {
		return businessTableCode;
	}

	public void setBusinessTableCode(String businessTableCode) {
		this.businessTableCode = businessTableCode;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getPrivilegeDetailCode() {
		return privilegeDetailCode;
	}

	public void setPrivilegeDetailCode(String privilegeDetailCode) {
		this.privilegeDetailCode = privilegeDetailCode;
	}

	public String getPrivilegeDetailValue() {
		return privilegeDetailValue;
	}

	public void setPrivilegeDetailValue(String privilegeDetailValue) {
		this.privilegeDetailValue = privilegeDetailValue;
	}
}
