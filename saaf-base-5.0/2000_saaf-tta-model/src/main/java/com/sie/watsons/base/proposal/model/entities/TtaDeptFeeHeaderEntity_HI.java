package com.sie.watsons.base.proposal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaDeptFeeHeaderEntity_HI Entity Object
 * Thu Jun 13 11:55:35 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_dept_fee_header")
public class TtaDeptFeeHeaderEntity_HI {
    private Integer deptFeeId;
    private String yearCode;
    private String accordType;
    private String feeItem;
    private String companyUnit;
    private Integer deptCode1;
    private Integer deptCode2;
    private Integer deptCode3;
    private Integer deptCode4;
    private Integer deptCode5;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer proposalId;

    private String isTransDept;
    private Integer operatorUserId;

    private String standardUnitValue;

	public void setDeptFeeId(Integer deptFeeId) {
		this.deptFeeId = deptFeeId;
	}


	@Id
	@SequenceGenerator(name = "SEQ_TTA_DEPT_FEE_HEADER", sequenceName = "SEQ_TTA_DEPT_FEE_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_DEPT_FEE_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="dept_fee_id", nullable=false, length=22)	
	public Integer getDeptFeeId() {
		return deptFeeId;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	@Column(name="year_code", nullable=false, length=50)	
	public String getYearCode() {
		return yearCode;
	}

	public void setAccordType(String accordType) {
		this.accordType = accordType;
	}

	@Column(name="accord_type", nullable=true, length=10)	
	public String getAccordType() {
		return accordType;
	}

	public void setFeeItem(String feeItem) {
		this.feeItem = feeItem;
	}

	@Column(name="fee_item")
	public String getFeeItem() {
		return feeItem;
	}

	public void setCompanyUnit(String companyUnit) {
		this.companyUnit = companyUnit;
	}

	@Column(name="company_unit", nullable=true, length=50)	
	public String getCompanyUnit() {
		return companyUnit;
	}

	public void setDeptCode1(Integer deptCode1) {
		this.deptCode1 = deptCode1;
	}

	@Column(name="dept_code1", nullable=true, length=22)	
	public Integer getDeptCode1() {
		return deptCode1;
	}

	public void setDeptCode2(Integer deptCode2) {
		this.deptCode2 = deptCode2;
	}

	@Column(name="dept_code2", nullable=true, length=22)	
	public Integer getDeptCode2() {
		return deptCode2;
	}

	public void setDeptCode3(Integer deptCode3) {
		this.deptCode3 = deptCode3;
	}

	@Column(name="dept_code3", nullable=true, length=22)	
	public Integer getDeptCode3() {
		return deptCode3;
	}

	public void setDeptCode4(Integer deptCode4) {
		this.deptCode4 = deptCode4;
	}

	@Column(name="dept_code4", nullable=true, length=22)	
	public Integer getDeptCode4() {
		return deptCode4;
	}

	public void setDeptCode5(Integer deptCode5) {
		this.deptCode5 = deptCode5;
	}

	@Column(name="dept_code5", nullable=true, length=22)	
	public Integer getDeptCode5() {
		return deptCode5;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2030)	
	public String getRemark() {
		return remark;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}



	public void setIsTransDept(String isTransDept) {
		this.isTransDept = isTransDept;
	}

	@Column(name="is_trans_dept", nullable=true, length=1)	
	public String getIsTransDept() {
		return isTransDept;
	}

    @Column(name="standard_unit_value")
    public String getStandardUnitValue() {
        return standardUnitValue;
    }

    public void setStandardUnitValue(String standardUnitValue) {
        this.standardUnitValue = standardUnitValue;
    }

    public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
