package com.sie.watsons.base.ttasastdtpl.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaStdApplyHeaderEntity_HI_RO Entity Object
 * Fri Apr 10 10:43:14 CST 2020  Auto Generate
 */

public class TtaStdApplyHeaderEntity_HI_RO {

	public static final String TTA_ITEM_V = "select * from (  select tsah.std_apply_header_id,\n" +
			"       tsah.order_no,\n" +
			"       nvl(tsah.version_code,1) version_code,\n" +
			"       tsah.tpl_id,\n" +
			"       tsah.tpl_name,\n" +
			"       tsah.tpl_type,\n" +
			"       look_type.meaning tpl_type_name,\n" +
			"       decode( tsah.tpl_type,'1',tsstdh.tpl_name,'2',ttrd.rule_name) quote_tpl_name,\n" +
			"       tsah.effective_start_time,\n" +
			"       tsah.effective_end_time,\n" +
			"       tsah.status,\n" +
			"       look_status.meaning status_name,\n" +
			"       tsah.resource_id,\n" +
			"       tsah.version_num,\n" +
			"       tsah.creation_date,\n" +
			"       tsah.created_by,\n" +
			"       bu.user_full_name created_by_name,\n" +
			"       tsah.last_updated_by,\n" +
			"       tsah.last_update_date,\n" +
			"       tsah.last_update_login,\n" +
			"       tsah.org_code,\n" +
			"       tsah.org_code_name,\n" +
			"       tsah.dept_code,\n" +
			"       tsah.dept_code_name,\n" +
			"       tsah.approve_date,\n" +
			"       tsah.is_alert\n" +
			" from tta_std_apply_header tsah\n" +
			"      left join (select blv.lookup_code,blv.meaning from  base_lookup_values blv where blv.lookup_type = 'TTA_STD_APPLY_HEADER_STATUS') look_status \n" +
			"       on look_status.lookup_code = tsah.status\n" +
			"       \n" +
			"       left join base_users bu on tsah.created_by = bu.user_id\n" +
			"             left join (select blv.lookup_code,blv.meaning from  base_lookup_values blv where blv.lookup_type = 'TTA_TPL_TYPE') look_type \n" +
			"       on look_type.lookup_code = tsah.tpl_type\n" +
			"       \n" +
			"       left join tta_sa_std_tpl_def_header tsstdh on tsstdh.sa_std_tpl_def_header_id = tsah.tpl_id\n" +
			"       left join tta_temp_rule_def ttrd on ttrd.rul_id = tsah.tpl_id\n" +
			"       \n" +
			"        ) tsah where 1=1 ";
    private Integer stdApplyHeaderId;
    private String orderNo;
    private Integer versionCode;
    private Integer tplId;
    private String tplName;
	private String tplType;
	private String tplTypeName;
	private String quoteTplName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveStartTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveEndTime;
    private String status;
	private String statusName;
    private Integer resourceId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
	private String createdByName;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String orgCode;
    private String orgCodeName;
    private String deptCode;
    private String deptCodeName;
	private String isAlert;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date approveDate;
    private Integer operatorUserId;

	public void setStdApplyHeaderId(Integer stdApplyHeaderId) {
		this.stdApplyHeaderId = stdApplyHeaderId;
	}

	
	public Integer getStdApplyHeaderId() {
		return stdApplyHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	public String getOrderNo() {
		return orderNo;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	
	public Integer getVersionCode() {
		return versionCode;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	
	public Integer getTplId() {
		return tplId;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	
	public String getTplName() {
		return tplName;
	}

	public String getTplType() {
		return tplType;
	}

	public void setTplType(String tplType) {
		this.tplType = tplType;
	}

	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}

	
	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	
	public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	
	public Integer getResourceId() {
		return resourceId;
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

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCodeName(String orgCodeName) {
		this.orgCodeName = orgCodeName;
	}

	
	public String getOrgCodeName() {
		return orgCodeName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCodeName(String deptCodeName) {
		this.deptCodeName = deptCodeName;
	}

	
	public String getDeptCodeName() {
		return deptCodeName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getTplTypeName() {
		return tplTypeName;
	}

	public void setTplTypeName(String tplTypeName) {
		this.tplTypeName = tplTypeName;
	}

	public String getQuoteTplName() {
		return quoteTplName;
	}

	public void setQuoteTplName(String quoteTplName) {
		this.quoteTplName = quoteTplName;
	}
}
