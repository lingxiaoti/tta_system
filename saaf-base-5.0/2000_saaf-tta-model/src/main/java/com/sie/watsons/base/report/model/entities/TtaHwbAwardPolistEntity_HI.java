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
 * TtaHwbAwardPolistEntity_HI Entity Object
 * Thu Aug 08 10:58:04 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_HWB_AWARD_POLIST")
public class TtaHwbAwardPolistEntity_HI {
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

	public void setId(Integer id) {
		this.id = id;
	}
    @Id
    @SequenceGenerator(name = "SEQ_TTA_HWB_AWARD_POLIST", sequenceName = "SEQ_TTA_HWB_AWARD_POLIST", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_HWB_AWARD_POLIST", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name="dept", nullable=true, length=100)	
	public String getDept() {
		return dept;
	}

	public void setAwardDescriptionEn(String awardDescriptionEn) {
		this.awardDescriptionEn = awardDescriptionEn;
	}

	@Column(name="award_description_en", nullable=true, length=100)	
	public String getAwardDescriptionEn() {
		return awardDescriptionEn;
	}

	public void setAwardDescriptionCn(String awardDescriptionCn) {
		this.awardDescriptionCn = awardDescriptionCn;
	}

	@Column(name="award_description_cn", nullable=true, length=100)	
	public String getAwardDescriptionCn() {
		return awardDescriptionCn;
	}

	public void setDmPopCn(String dmPopCn) {
		this.dmPopCn = dmPopCn;
	}

	@Column(name="dm_pop_cn", nullable=true, length=100)	
	public String getDmPopCn() {
		return dmPopCn;
	}

	public void setRateCard2017(String rateCard2017) {
		this.rateCard2017 = rateCard2017;
	}

	@Column(name="rate_card_2017", nullable=true, length=50)	
	public String getRateCard2017() {
		return rateCard2017;
	}

	public void setRateCard2018(String rateCard2018) {
		this.rateCard2018 = rateCard2018;
	}

	@Column(name="rate_card_2018", nullable=true, length=50)	
	public String getRateCard2018() {
		return rateCard2018;
	}

	public void setActSupplierOi2018(String actSupplierOi2018) {
		this.actSupplierOi2018 = actSupplierOi2018;
	}

	@Column(name="act_supplier_oi_2018", nullable=true, length=50)	
	public String getActSupplierOi2018() {
		return actSupplierOi2018;
	}

	public void setOiGap(String oiGap) {
		this.oiGap = oiGap;
	}

	@Column(name="oi_gap", nullable=true, length=50)	
	public String getOiGap() {
		return oiGap;
	}

	public void setDmPage(String dmPage) {
		this.dmPage = dmPage;
	}

	@Column(name="dm_page", nullable=true, length=50)	
	public String getDmPage() {
		return dmPage;
	}

	public void setDmRateCard(String dmRateCard) {
		this.dmRateCard = dmRateCard;
	}

	@Column(name="dm_rate_card", nullable=true, length=50)	
	public String getDmRateCard() {
		return dmRateCard;
	}

	public void setSuggestedOsdLocation(String suggestedOsdLocation) {
		this.suggestedOsdLocation = suggestedOsdLocation;
	}

	@Column(name="suggested_osd_location", nullable=true, length=50)	
	public String getSuggestedOsdLocation() {
		return suggestedOsdLocation;
	}

	public void setBiddingOsd(String biddingOsd) {
		this.biddingOsd = biddingOsd;
	}

	@Column(name="bidding_osd", nullable=true, length=50)	
	public String getBiddingOsd() {
		return biddingOsd;
	}

	public void setBrandProductNameCn(String brandProductNameCn) {
		this.brandProductNameCn = brandProductNameCn;
	}

	@Column(name="brand_product_name_cn", nullable=true, length=100)	
	public String getBrandProductNameCn() {
		return brandProductNameCn;
	}

	public void setPrAward(String prAward) {
		this.prAward = prAward;
	}

	@Column(name="pr_award", nullable=true, length=250)	
	public String getPrAward() {
		return prAward;
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
