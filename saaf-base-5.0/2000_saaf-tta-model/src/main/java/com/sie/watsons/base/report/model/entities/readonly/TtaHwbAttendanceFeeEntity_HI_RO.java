package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaHwbAttendanceFeeEntity_HI_RO Entity Object
 * Thu Aug 08 10:58:02 CST 2019  Auto Generate
 */

public class TtaHwbAttendanceFeeEntity_HI_RO {
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

    private Integer changePerson;

    private String changePersonName;
    private Integer reportHeaderId;

    private String headerStatus;
    private String batchId;

    private String groupCode;
    private String deptCode;
    private String brandCn;

    public static final String TTA_HWB_ATTENDANCE_FEE_HISTORY= "select t.*,r.status headerStatus,r.batch_id \n" +
            "from TTA_HWB_ATTENDANCE_FEE t \n" +
            "LEFT JOIN TTA_REPORT_HEADER r \n" +
            "on t.TIMES_VERSION = r.BATCH_ID \n" +
            "LEFT JOIN base_users usr ON t.created_by = usr.user_id \n" +
            "where 1=1 " ;
    public static String TTA_HWB_ATTENDANCE_FEE= "select t.*,r.status headerStatus,r.batch_id \n" +
            "from TTA_HWB_ATTENDANCE_FEE t \n" +
            "LEFT JOIN TTA_REPORT_HEADER r \n" +
            "on t.REPORT_HEADER_ID = r.id \n" +
            "where 1=1 " ;

    public static String TTA_HWB_USER_LIST= "select * from BASE_USERS u where USER_TYPE in(12,13,15) " ;

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

    public Integer getChangePerson() {
        return changePerson;
    }

    public void setChangePerson(Integer changePerson) {
        this.changePerson = changePerson;
    }

    public String getChangePersonName() {
        return changePersonName;
    }

    public void setChangePersonName(String changePersonName) {
        this.changePersonName = changePersonName;
    }

    public void setDept(String dept) {
		this.dept = dept;
	}

	
	public String getDept() {
		return dept;
	}

	public void setBrandEnorcn(String brandEnorcn) {
		this.brandEnorcn = brandEnorcn;
	}

	
	public String getBrandEnorcn() {
		return brandEnorcn;
	}

	public void setNum(String num) {
		this.num = num;
	}

	
	public String getNum() {
		return num;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	
	public String getFeeType() {
		return feeType;
	}

	public void setRateCard(String rateCard) {
		this.rateCard = rateCard;
	}

	
	public String getRateCard() {
		return rateCard;
	}

	public void setFeeGroup(String feeGroup) {
		this.feeGroup = feeGroup;
	}

	
	public String getFeeGroup() {
		return feeGroup;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public String getCategory() {
		return category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getContent() {
		return content;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	public String getBrand() {
		return brand;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	
	public String getAmount() {
		return amount;
	}

	public void setConfirmAmount(String confirmAmount) {
		this.confirmAmount = confirmAmount;
	}

	
	public String getConfirmAmount() {
		return confirmAmount;
	}

	public void setFg(String fg) {
		this.fg = fg;
	}

	
	public String getFg() {
		return fg;
	}

	public void setDifferent(String different) {
		this.different = different;
	}

	
	public String getDifferent() {
		return different;
	}

	public void setActive(String active) {
		this.active = active;
	}

	
	public String getActive() {
		return active;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	
	public String getProjectType() {
		return projectType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

    public Integer getReportHeaderId() {
        return reportHeaderId;
    }

    public void setReportHeaderId(Integer reportHeaderId) {
        this.reportHeaderId = reportHeaderId;
    }

    public String getHeaderStatus() {
        return headerStatus;
    }

    public void setHeaderStatus(String headerStatus) {
        this.headerStatus = headerStatus;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }
}
