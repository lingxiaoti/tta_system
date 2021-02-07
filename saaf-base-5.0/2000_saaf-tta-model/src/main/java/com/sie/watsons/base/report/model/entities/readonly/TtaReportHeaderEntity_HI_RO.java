package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaReportHeaderEntity_HI_RO Entity Object
 * Sat Aug 03 15:30:12 CST 2019  Auto Generate
 */

public class TtaReportHeaderEntity_HI_RO {
    private Integer id;
    private String batchId;
    private String status;
    private String reportType;
    private String backUp;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date passDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String isPublish;
	private String promotionSectionName;
	private Integer isPublishBy;
	private String isPublishByName;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date isPublishDate;
    private String passBy;
    private String promotionSection;

    public static final String TTA_REPORT_HEADER = "select h.id,\n" +
			"       h.batch_id,\n" +
			"       h.status,\n" +
			"       h.report_type,\n" +
			"       h.is_Publish_Date isPublishDate,\n" +
			"       h.pass_date,\n" +
			"		h.promotion_section,\n" +
			"       nvl(h.is_publish, 'N') isPublish,\n" +
			"       usr.user_full_name as passBy,\n" +
			"       h.promotion_section promotion_section_name,\n" +
			"       usr2.user_full_name isPublishByName\n" +
			"  from tta_report_header h\n" +
			"  left join base_users usr on h.last_updated_by = usr.user_id\n" +
			"  left join base_users usr2 on h.is_publish_by = usr2.user_id\n" +
			" where 1 = 1\n" ;


	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	
	public String getBatchId() {
		return batchId;
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

    public Date getPassDate() {
        return passDate;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    public String getPassBy() {
        return passBy;
    }

    public void setPassBy(String passBy) {
        this.passBy = passBy;
    }

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public Date getIsPublishDate() {
		return isPublishDate;
	}

	public void setIsPublishDate(Date isPublishDate) {
		this.isPublishDate = isPublishDate;
	}

	public Integer getIsPublishBy() {
		return isPublishBy;
	}

	public void setIsPublishBy(Integer isPublishBy) {
		this.isPublishBy = isPublishBy;
	}

	public String getIsPublishByName() {
		return isPublishByName;
	}

	public String getPromotionSectionName() {
		return promotionSectionName;
	}

	public void setPromotionSectionName(String promotionSectionName) {
		this.promotionSectionName = promotionSectionName;
	}

	public void setIsPublishByName(String isPublishByName) {
		this.isPublishByName = isPublishByName;
	}

	public String getPromotionSection() {
		return promotionSection;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}
}
