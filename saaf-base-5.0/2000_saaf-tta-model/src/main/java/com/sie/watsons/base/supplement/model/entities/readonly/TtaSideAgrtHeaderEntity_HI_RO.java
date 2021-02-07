package com.sie.watsons.base.supplement.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSideAgrtHeaderEntity_HI_RO Entity Object
 * Wed Jun 19 12:21:47 CST 2019  Auto Generate
 */   

public class TtaSideAgrtHeaderEntity_HI_RO {
    private Integer sideAgrtHId;
    private String sideAgrtCode;
    private String billStatus;   
    private String sideAgrtVersion;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date approveDate;
	private String createByUser;
	private String statusName;

    public static final String SIDEAGRT_QUERY_H ="select \n" +
			"       tsah.side_agrt_h_id sideAgrtHId,\n" +
			"       tsah.side_agrt_code sideAgrtCode,\n" +
			"       tsah.side_agrt_version sideAgrtVersion,\n" +
			"       tsah.start_date startDate,\n" +
			"       tsah.end_date endDate,\n" +
			"       tsah.version_num versionNum,\n" +
			"       tsah.creation_date creationDate,\n" +
			"       tsah.created_by createdBy,\n" +
			"       tsah.last_updated_by lastUpdatedBy,\n" +
			"       tsah.last_update_date lastUpdateDate,\n" +
			"       tsah.last_update_login lastUpdateLogin,\n" +
			"       tsah.bill_status billStatus,\n" +
			"       bu.user_full_name      createByUser,\n" +
			"       lookup1.meaning        statusName\n" +
			"  from tta_side_agrt_header tsah\n" +
			"  join base_users bu\n" +
			"    on tsah.created_by = bu.user_id\n" +
			"  left join (select lookup_type, lookup_code, meaning\n" +
			"               from base_lookup_values\n" +
			"              where lookup_type = 'SUPPLEMEN_STATUS'\n" +
			"                and enabled_flag = 'Y'\n" +
			"                and delete_flag = 0\n" +
			"                and start_date_active < sysdate\n" +
			"                and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                and system_code = 'BASE') lookup1\n" +
			"    on tsah.bill_status = lookup1.lookup_code where 1=1 ";

	public void setSideAgrtHId(Integer sideAgrtHId) {
		this.sideAgrtHId = sideAgrtHId;
	}
	
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public Integer getSideAgrtHId() {
		return sideAgrtHId;
	}

	public void setSideAgrtCode(String sideAgrtCode) {
		this.sideAgrtCode = sideAgrtCode;
	}

	
	public String getSideAgrtCode() {
		return sideAgrtCode;
	}

	public void setSideAgrtVersion(String sideAgrtVersion) {
		this.sideAgrtVersion = sideAgrtVersion;
	}

	
	public String getSideAgrtVersion() {
		return sideAgrtVersion;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
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

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getCreateByUser() {
		return createByUser;
	}

	public void setCreateByUser(String createByUser) {
		this.createByUser = createByUser;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
