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
 * TtaHwbAttendanceFeeEntity_HI Entity Object
 * Thu Aug 08 10:58:02 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_HWB_ATTENDANCE_FEE")
public class TtaHwbAttendanceFeeEntity_HI {
    private Integer id;
    private String dept;
    private String brandEnorcn;
    private String num;
    private String feeType;
    private String rateCard;
    private String feeGroup;
    private String category;
    private String content;
    private String vendorCode;
    private String vendorName;
    private String brand;
    private String amount;
    private String confirmAmount;
    private String fg;
    private String different;
    private String active;
    private String projectType;
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
    @SequenceGenerator(name = "SEQ_TTA_HWB_ATTENDANCE_FEE", sequenceName = "SEQ_TTA_HWB_ATTENDANCE_FEE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_HWB_ATTENDANCE_FEE", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name="dept", nullable=true, length=30)	
	public String getDept() {
		return dept;
	}

	public void setBrandEnorcn(String brandEnorcn) {
		this.brandEnorcn = brandEnorcn;
	}

	@Column(name="brand_enorcn", nullable=true, length=10)	
	public String getBrandEnorcn() {
		return brandEnorcn;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Column(name="num", nullable=true, length=100)	
	public String getNum() {
		return num;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name="fee_type", nullable=true, length=100)	
	public String getFeeType() {
		return feeType;
	}

	public void setRateCard(String rateCard) {
		this.rateCard = rateCard;
	}

	@Column(name="rate_card", nullable=true, length=100)	
	public String getRateCard() {
		return rateCard;
	}

	public void setFeeGroup(String feeGroup) {
		this.feeGroup = feeGroup;
	}

	@Column(name="fee_group", nullable=true, length=250)	
	public String getFeeGroup() {
		return feeGroup;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name="category", nullable=true, length=250)	
	public String getCategory() {
		return category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="content", nullable=true, length=50)	
	public String getContent() {
		return content;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=true, length=50)	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=50)	
	public String getVendorName() {
		return vendorName;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=50)	
	public String getBrand() {
		return brand;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Column(name="amount", nullable=true, length=50)	
	public String getAmount() {
		return amount;
	}

	public void setConfirmAmount(String confirmAmount) {
		this.confirmAmount = confirmAmount;
	}

	@Column(name="confirm_amount", nullable=true, length=50)	
	public String getConfirmAmount() {
		return confirmAmount;
	}

	public void setFg(String fg) {
		this.fg = fg;
	}

	@Column(name="fg", nullable=true, length=50)	
	public String getFg() {
		return fg;
	}

	public void setDifferent(String different) {
		this.different = different;
	}

	@Column(name="different", nullable=true, length=50)	
	public String getDifferent() {
		return different;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Column(name="active", nullable=true, length=50)	
	public String getActive() {
		return active;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@Column(name="project_type", nullable=true, length=50)	
	public String getProjectType() {
		return projectType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=50)	
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

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
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
}
