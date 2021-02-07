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
 * TtaHwbSittingPlanEntity_HI Entity Object
 * Thu Aug 08 10:58:05 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_HWB_SITTING_PLAN")
public class TtaHwbSittingPlanEntity_HI {
    private Integer id;
    private String tableNo;
    private String indexNum;
    private String dept;
    private String nameEn;
    private String nameCn;
    private String titleEn;
    private String brandEnorcn;
    private String tendCard;
    private String cellPhoneNumber;
    private String attend;
    private String wtc;
    private String timesVersion;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}
    @Id
    @SequenceGenerator(name = "SEQ_TTA_HWB_SITTING_PLAN", sequenceName = "SEQ_TTA_HWB_SITTING_PLAN", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_HWB_SITTING_PLAN", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	@Column(name="table_no", nullable=true, length=30)	
	public String getTableNo() {
		return tableNo;
	}

	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}

	@Column(name="index_num", nullable=true, length=10)	
	public String getIndexNum() {
		return indexNum;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name="dept", nullable=true, length=100)	
	public String getDept() {
		return dept;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@Column(name="name_en", nullable=true, length=100)	
	public String getNameEn() {
		return nameEn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	@Column(name="name_cn", nullable=true, length=100)	
	public String getNameCn() {
		return nameCn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	@Column(name="title_en", nullable=true, length=250)	
	public String getTitleEn() {
		return titleEn;
	}

	public void setBrandEnorcn(String brandEnorcn) {
		this.brandEnorcn = brandEnorcn;
	}

	@Column(name="brand_enorcn", nullable=true, length=250)	
	public String getBrandEnorcn() {
		return brandEnorcn;
	}

	public void setTendCard(String tendCard) {
		this.tendCard = tendCard;
	}

	@Column(name="tend_card", nullable=true, length=50)	
	public String getTendCard() {
		return tendCard;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	@Column(name="cell_phone_number", nullable=true, length=50)	
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setAttend(String attend) {
		this.attend = attend;
	}

	@Column(name="attend", nullable=true, length=50)	
	public String getAttend() {
		return attend;
	}

	public void setWtc(String wtc) {
		this.wtc = wtc;
	}

	@Column(name="wtc", nullable=true, length=50)	
	public String getWtc() {
		return wtc;
	}

	public void setTimesVersion(String timesVersion) {
		this.timesVersion = timesVersion;
	}

	@Column(name="times_version", nullable=true, length=50)	
	public String getTimesVersion() {
		return timesVersion;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=50)	
	public String getStatus() {
		return status;
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

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
