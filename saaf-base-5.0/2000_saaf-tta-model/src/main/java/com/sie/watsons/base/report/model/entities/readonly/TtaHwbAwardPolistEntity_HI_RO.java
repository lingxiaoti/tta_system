package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaHwbAwardPolistEntity_HI_RO Entity Object
 * Thu Aug 08 10:58:04 CST 2019  Auto Generate
 */

public class TtaHwbAwardPolistEntity_HI_RO {
    private Integer id;
    private String dept;
    private String awardDescriptionEn;
    private String awardDescriptionCn;
    private String dmPopCn;
    private String rateCard2017;
    private String rateCard2018;
    private String actSupplierOi2018;
    private String oiGap;
    private String dmPage;
    private String dmRateCard;
    private String suggestedOsdLocation;
    private String biddingOsd;
    private String brandProductNameCn;
    private String prAward;
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

    public static final String TTA_HWB_AWARD_POLIST = "select " +
            "s.* ,\n" +
            "s.RATE_CARD_2017 rateCard2017 ,\n" +
            "s.RATE_CARD_2018 rateCard2018 ,\n" +
            "s.ACT_SUPPLIER_OI_2018 actSupplierOi2018 \n" +
            "from TTA_HWB_AWARD_POLIST s where 1=1";

    public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	
	public String getDept() {
		return dept;
	}

	public void setAwardDescriptionEn(String awardDescriptionEn) {
		this.awardDescriptionEn = awardDescriptionEn;
	}

	
	public String getAwardDescriptionEn() {
		return awardDescriptionEn;
	}

	public void setAwardDescriptionCn(String awardDescriptionCn) {
		this.awardDescriptionCn = awardDescriptionCn;
	}

	
	public String getAwardDescriptionCn() {
		return awardDescriptionCn;
	}

	public void setDmPopCn(String dmPopCn) {
		this.dmPopCn = dmPopCn;
	}

	
	public String getDmPopCn() {
		return dmPopCn;
	}

	public void setRateCard2017(String rateCard2017) {
		this.rateCard2017 = rateCard2017;
	}

	
	public String getRateCard2017() {
		return rateCard2017;
	}

	public void setRateCard2018(String rateCard2018) {
		this.rateCard2018 = rateCard2018;
	}

	
	public String getRateCard2018() {
		return rateCard2018;
	}

	public void setActSupplierOi2018(String actSupplierOi2018) {
		this.actSupplierOi2018 = actSupplierOi2018;
	}

	
	public String getActSupplierOi2018() {
		return actSupplierOi2018;
	}

	public void setOiGap(String oiGap) {
		this.oiGap = oiGap;
	}

	
	public String getOiGap() {
		return oiGap;
	}

	public void setDmPage(String dmPage) {
		this.dmPage = dmPage;
	}

	
	public String getDmPage() {
		return dmPage;
	}

	public void setDmRateCard(String dmRateCard) {
		this.dmRateCard = dmRateCard;
	}

	
	public String getDmRateCard() {
		return dmRateCard;
	}

	public void setSuggestedOsdLocation(String suggestedOsdLocation) {
		this.suggestedOsdLocation = suggestedOsdLocation;
	}

	
	public String getSuggestedOsdLocation() {
		return suggestedOsdLocation;
	}

	public void setBiddingOsd(String biddingOsd) {
		this.biddingOsd = biddingOsd;
	}

	
	public String getBiddingOsd() {
		return biddingOsd;
	}

	public void setBrandProductNameCn(String brandProductNameCn) {
		this.brandProductNameCn = brandProductNameCn;
	}

	
	public String getBrandProductNameCn() {
		return brandProductNameCn;
	}

	public void setPrAward(String prAward) {
		this.prAward = prAward;
	}

	
	public String getPrAward() {
		return prAward;
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
