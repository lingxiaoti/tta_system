package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaReportHeaderEntity_HI Entity Object
 * Sat Aug 03 15:30:12 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_REPORT_HEADER")
public class TtaReportHeaderEntity_HI {
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
	private String isPublish;
	private Integer isPublishBy;
	private Date isPublishDate;
    private Integer operatorUserId;
	private String promotionSection;



	public void setId(Integer id) {
		this.id = id;
	}


	@Id
	@SequenceGenerator(name = "SEQ_TTA_REPORT_HEADER", sequenceName = "SEQ_TTA_REPORT_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REPORT_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)

	public Integer getId() {
		return id;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Column(name="batch_id", nullable=true, length=50)	
	public String getBatchId() {
		return batchId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=50)	
	public String getStatus() {
		return status;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Column(name="report_type", nullable=true, length=50)	
	public String getReportType() {
		return reportType;
	}

	public void setBackUp(String backUp) {
		this.backUp = backUp;
	}

	@Column(name="back_up", nullable=true, length=50)	
	public String getBackUp() {
		return backUp;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    @Column(name="pass_date")
    public Date getPassDate() {
        return passDate;
    }

    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="is_publish")
	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	@Column(name="is_publish_date")
	public Date getIsPublishDate() {
		return isPublishDate;
	}

	public void setIsPublishDate(Date isPublishDate) {
		this.isPublishDate = isPublishDate;
	}

	@Column(name="is_publish_by")
	public Integer getIsPublishBy() {
		return isPublishBy;
	}

	public void setIsPublishBy(Integer isPublishBy) {
		this.isPublishBy = isPublishBy;
	}

	@Column(name="promotion_section")
	public String getPromotionSection() {
		return promotionSection;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}
}
