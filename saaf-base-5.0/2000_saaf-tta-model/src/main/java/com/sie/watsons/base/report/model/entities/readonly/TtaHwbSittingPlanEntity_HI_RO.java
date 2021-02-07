package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaHwbSittingPlanEntity_HI_RO Entity Object
 * Thu Aug 08 10:58:05 CST 2019  Auto Generate
 */

public class TtaHwbSittingPlanEntity_HI_RO {
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

    public static final String TTA_HWB_SITTING_PLAN = "select * from TTA_HWB_SITTING_PLAN s where 1=1";

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	
	public String getTableNo() {
		return tableNo;
	}

	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}

	
	public String getIndexNum() {
		return indexNum;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	
	public String getDept() {
		return dept;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	
	public String getNameEn() {
		return nameEn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	
	public String getNameCn() {
		return nameCn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	
	public String getTitleEn() {
		return titleEn;
	}

	public void setBrandEnorcn(String brandEnorcn) {
		this.brandEnorcn = brandEnorcn;
	}

	
	public String getBrandEnorcn() {
		return brandEnorcn;
	}

	public void setTendCard(String tendCard) {
		this.tendCard = tendCard;
	}

	
	public String getTendCard() {
		return tendCard;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setAttend(String attend) {
		this.attend = attend;
	}

	
	public String getAttend() {
		return attend;
	}

	public void setWtc(String wtc) {
		this.wtc = wtc;
	}

	
	public String getWtc() {
		return wtc;
	}

	public void setTimesVersion(String timesVersion) {
		this.timesVersion = timesVersion;
	}

	
	public String getTimesVersion() {
		return timesVersion;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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
}
