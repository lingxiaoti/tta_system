package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaOiReportHeaderEntity_HI_RO Entity Object
 * Tue May 05 16:57:51 CST 2020  Auto Generate
 */

public class TtaOiReportHeaderEntity_HI_RO {
    private Integer id;
    private String batchCode;
    private String status;
    private String reportType;
    private String backUp;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date passDate;
    private String isPublish;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date isPublishDate;
    private Integer isPublishBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String createUserName;//单据创建人
	private String isPublishByName; //单据发布人
	private String confirmStatus;

	public static String tta_report_header = "" +
			"select " +
			"		h.*,\n" +
			"       bu.user_full_name  as create_user_name,\n" +
			"       bu2.user_full_name as is_publish_by_name\n" +
			"    from tta_oi_report_header h\n" +
			"    left join base_users bu\n" +
			"      on bu.user_id = h.created_by\n" +
			"    left join base_users bu2\n" +
			"      on bu2.user_id = h.is_publish_by where  1=1 \n" ;

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	
	public String getBatchCode() {
		return batchCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	
	public String getReportType() {
		return reportType;
	}

	public void setBackUp(String backUp) {
		this.backUp = backUp;
	}

	
	public String getBackUp() {
		return backUp;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	
	public Date getPassDate() {
		return passDate;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	
	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublishDate(Date isPublishDate) {
		this.isPublishDate = isPublishDate;
	}

	
	public Date getIsPublishDate() {
		return isPublishDate;
	}

	public void setIsPublishBy(Integer isPublishBy) {
		this.isPublishBy = isPublishBy;
	}

	
	public Integer getIsPublishBy() {
		return isPublishBy;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getIsPublishByName() {
		return isPublishByName;
	}

	public void setIsPublishByName(String isPublishByName) {
		this.isPublishByName = isPublishByName;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
}
