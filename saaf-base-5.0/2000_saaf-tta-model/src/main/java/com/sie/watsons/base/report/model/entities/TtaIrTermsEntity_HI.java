package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaIrTermsEntity_HI Entity Object
 * Wed Jul 31 16:23:51 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_IR_TERMS")
public class TtaIrTermsEntity_HI {
    private Integer id;
    private String department;
    private String brand;
    private BigDecimal onePer;
    private BigDecimal twoPer;
    private BigDecimal threePer;
    private BigDecimal fourPer;
    private BigDecimal fivePer;
    private BigDecimal sixPer;
    private BigDecimal sevenPer;
    private BigDecimal eightPer;

    private BigDecimal oneIn;
    private BigDecimal twoIn;
    private BigDecimal threeIn;
    private BigDecimal fourIn;
    private BigDecimal fiveIn;
    private BigDecimal sixIn;
    private BigDecimal sevenIn;
    private BigDecimal eightIn;

    private BigDecimal oneNo;
    private BigDecimal twoNo;
    private BigDecimal threeNo;
    private BigDecimal fourNo;
    private BigDecimal fiveNo;
    private BigDecimal sixNo;
    private BigDecimal sevenNo;
    private BigDecimal eightNo;
    private BigDecimal purchase;
    private BigDecimal fyPurchase;
    private BigDecimal irPer;
    private String firstTier;
    private String lastTier;
    private BigDecimal forecastedIrPer;
    private BigDecimal nextIrPer;
    private BigDecimal potentialForwardBuy;
    private BigDecimal afterPotential;
    private BigDecimal extralIr;
    private BigDecimal returnRate;
    private String bicRemark;
    private String followUpAction;
    private String extraPoPlanning;
    private String remark;
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

    private String groupCode;
    private String deptCode;
    private String brandCn;

    private Integer changePerson;
    private String changePersonName;
    private Integer reportHeaderId;

	public void setId(Integer id) {
		this.id = id;
	}

    @Id
    @SequenceGenerator(name = "SEQ_TTA_IR_TERMS", sequenceName = "SEQ_TTA_IR_TERMS", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_IR_TERMS", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="department", nullable=true, length=250)	
	public String getDepartment() {
		return department;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=250)	
	public String getBrand() {
		return brand;
	}

    @Column(name="one_per", nullable=true, length=50)
    public BigDecimal getOnePer() {
        return onePer;
    }

    public void setOnePer(BigDecimal onePer) {
        this.onePer = onePer;
    }
    @Column(name="teo_per", nullable=true, length=50)
    public BigDecimal getTwoPer() {
        return twoPer;
    }

    public void setTwoPer(BigDecimal twoPer) {
        this.twoPer = twoPer;
    }
    @Column(name="three_per", nullable=true, length=50)
    public BigDecimal getThreePer() {
        return threePer;
    }

    public void setThreePer(BigDecimal threePer) {
        this.threePer = threePer;
    }

    @Column(name="four_per", nullable=true, length=50)
    public BigDecimal getFourPer() {
        return fourPer;
    }

    public void setFourPer(BigDecimal fourPer) {
        this.fourPer = fourPer;
    }
    @Column(name="five_per", nullable=true, length=50)
    public BigDecimal getFivePer() {
        return fivePer;
    }

    public void setFivePer(BigDecimal fivePer) {
        this.fivePer = fivePer;
    }
    @Column(name="six_per", nullable=true, length=50)
    public BigDecimal getSixPer() {
        return sixPer;
    }

    public void setSixPer(BigDecimal sixPer) {
        this.sixPer = sixPer;
    }
    @Column(name="seven_per", nullable=true, length=50)
    public BigDecimal getSevenPer() {
        return sevenPer;
    }

    public void setSevenPer(BigDecimal sevenPer) {
        this.sevenPer = sevenPer;
    }
    @Column(name="eight_per", nullable=true, length=50)
    public BigDecimal getEightPer() {
        return eightPer;
    }

    public void setEightPer(BigDecimal eightPer) {
        this.eightPer = eightPer;
    }
    @Column(name="one_in", nullable=true, length=50)
    public BigDecimal getOneIn() {
        return oneIn;
    }

    public void setOneIn(BigDecimal oneIn) {
        this.oneIn = oneIn;
    }
    @Column(name="two_in", nullable=true, length=50)
    public BigDecimal getTwoIn() {
        return twoIn;
    }

    public void setTwoIn(BigDecimal twoIn) {
        this.twoIn = twoIn;
    }
    @Column(name="three_in", nullable=true, length=50)
    public BigDecimal getThreeIn() {
        return threeIn;
    }

    public void setThreeIn(BigDecimal threeIn) {
        this.threeIn = threeIn;
    }
    @Column(name="four_in", nullable=true, length=50)
    public BigDecimal getFourIn() {
        return fourIn;
    }

    public void setFourIn(BigDecimal fourIn) {
        this.fourIn = fourIn;
    }
    @Column(name="five_in", nullable=true, length=50)
    public BigDecimal getFiveIn() {
        return fiveIn;
    }

    public void setFiveIn(BigDecimal fiveIn) {
        this.fiveIn = fiveIn;
    }
    @Column(name="six_in", nullable=true, length=50)
    public BigDecimal getSixIn() {
        return sixIn;
    }

    public void setSixIn(BigDecimal sixIn) {
        this.sixIn = sixIn;
    }
    @Column(name="seven_in", nullable=true, length=50)
    public BigDecimal getSevenIn() {
        return sevenIn;
    }

    public void setSevenIn(BigDecimal sevenIn) {
        this.sevenIn = sevenIn;
    }
    @Column(name="eight_in", nullable=true, length=50)
    public BigDecimal getEightIn() {
        return eightIn;
    }

    public void setEightIn(BigDecimal eightIn) {
        this.eightIn = eightIn;
    }
    @Column(name="one_no", nullable=true, length=50)
    public BigDecimal getOneNo() {
        return oneNo;
    }

    public void setOneNo(BigDecimal oneNo) {
        this.oneNo = oneNo;
    }
    @Column(name="two_no", nullable=true, length=50)
    public BigDecimal getTwoNo() {
        return twoNo;
    }

    public void setTwoNo(BigDecimal twoNo) {
        this.twoNo = twoNo;
    }
    @Column(name="three_no", nullable=true, length=50)
    public BigDecimal getThreeNo() {
        return threeNo;
    }

    public void setThreeNo(BigDecimal threeNo) {
        this.threeNo = threeNo;
    }
    @Column(name="four_no", nullable=true, length=50)
    public BigDecimal getFourNo() {
        return fourNo;
    }

    public void setFourNo(BigDecimal fourNo) {
        this.fourNo = fourNo;
    }
    @Column(name="five_no", nullable=true, length=50)
    public BigDecimal getFiveNo() {
        return fiveNo;
    }

    public void setFiveNo(BigDecimal fiveNo) {
        this.fiveNo = fiveNo;
    }
    @Column(name="six_no", nullable=true, length=50)
    public BigDecimal getSixNo() {
        return sixNo;
    }

    public void setSixNo(BigDecimal sixNo) {
        this.sixNo = sixNo;
    }
    @Column(name="seven_no", nullable=true, length=50)
    public BigDecimal getSevenNo() {
        return sevenNo;
    }

    public void setSevenNo(BigDecimal sevenNo) {
        this.sevenNo = sevenNo;
    }
    @Column(name="eight_no", nullable=true, length=50)
    public BigDecimal getEightNo() {
        return eightNo;
    }

    public void setEightNo(BigDecimal eightNo) {
        this.eightNo = eightNo;
    }

    public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	@Column(name="purchase", nullable=true, length=50)	
	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setFyPurchase(BigDecimal fyPurchase) {
		this.fyPurchase = fyPurchase;
	}

	@Column(name="fy_purchase", nullable=true, length=50)	
	public BigDecimal getFyPurchase() {
		return fyPurchase;
	}

	public void setIrPer(BigDecimal irPer) {
		this.irPer = irPer;
	}

	@Column(name="ir_per", nullable=true, length=50)	
	public BigDecimal getIrPer() {
		return irPer;
	}

	public void setFirstTier(String firstTier) {
		this.firstTier = firstTier;
	}

	@Column(name="first_tier", nullable=true, length=50)	
	public String getFirstTier() {
		return firstTier;
	}

	public void setLastTier(String lastTier) {
		this.lastTier = lastTier;
	}

	@Column(name="last_tier", nullable=true, length=50)	
	public String getLastTier() {
		return lastTier;
	}

	public void setForecastedIrPer(BigDecimal forecastedIrPer) {
		this.forecastedIrPer = forecastedIrPer;
	}

	@Column(name="forecasted_ir_per", nullable=true, length=50)	
	public BigDecimal getForecastedIrPer() {
		return forecastedIrPer;
	}

	public void setNextIrPer(BigDecimal nextIrPer) {
		this.nextIrPer = nextIrPer;
	}

	@Column(name="next_ir_per", nullable=true, length=50)	
	public BigDecimal getNextIrPer() {
		return nextIrPer;
	}

	public void setPotentialForwardBuy(BigDecimal potentialForwardBuy) {
		this.potentialForwardBuy = potentialForwardBuy;
	}

	@Column(name="potential_forward_buy", nullable=true, length=50)	
	public BigDecimal getPotentialForwardBuy() {
		return potentialForwardBuy;
	}

	public void setAfterPotential(BigDecimal afterPotential) {
		this.afterPotential = afterPotential;
	}

	@Column(name="after_potential", nullable=true, length=50)	
	public BigDecimal getAfterPotential() {
		return afterPotential;
	}

	public void setExtralIr(BigDecimal extralIr) {
		this.extralIr = extralIr;
	}

	@Column(name="extral_ir", nullable=true, length=50)	
	public BigDecimal getExtralIr() {
		return extralIr;
	}

	public void setReturnRate(BigDecimal returnRate) {
		this.returnRate = returnRate;
	}

	@Column(name="return_rate", nullable=true, length=50)	
	public BigDecimal getReturnRate() {
		return returnRate;
	}

	public void setBicRemark(String bicRemark) {
		this.bicRemark = bicRemark;
	}

	@Column(name="bic_remark", nullable=true, length=1000)	
	public String getBicRemark() {
		return bicRemark;
	}

	public void setFollowUpAction(String followUpAction) {
		this.followUpAction = followUpAction;
	}

	@Column(name="follow_up_action", nullable=true, length=50)	
	public String getFollowUpAction() {
		return followUpAction;
	}

	public void setExtraPoPlanning(String extraPoPlanning) {
		this.extraPoPlanning = extraPoPlanning;
	}

	@Column(name="extra_po_planning", nullable=true, length=50)	
	public String getExtraPoPlanning() {
		return extraPoPlanning;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=1000)	
	public String getRemark() {
		return remark;
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

    @Column(name="GROUP_CODE", nullable=true, length=100)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name="DEPT_CODE", nullable=true, length=100)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Column(name="BRAND_CN", nullable=true, length=100)
    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }

    @Column(name="CHANGE_PERSON", nullable=true, length=22)
    public Integer getChangePerson() {
        return changePerson;
    }

    public void setChangePerson(Integer changePerson) {
        this.changePerson = changePerson;
    }

    @Column(name="CHANGE_PERSON_NAME", nullable=true, length=100)
    public String getChangePersonName() {
        return changePersonName;
    }

    public void setChangePersonName(String changePersonName) {
        this.changePersonName = changePersonName;
    }

    @Column(name="REPORT_HEADER_ID")
    public Integer getReportHeaderId() {
        return reportHeaderId;
    }

    public void setReportHeaderId(Integer reportHeaderId) {
        this.reportHeaderId = reportHeaderId;
    }

    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
