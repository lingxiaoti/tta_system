package com.sie.watsons.base.proposal.model.entities;

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
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaDeptFeeLineEntity_HI Entity Object
 * Thu Jun 13 11:55:36 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_dept_fee")
public class TtaDeptFeeLineEntity_HI {
    private Integer deptfeeLineId;
    private Integer deptFeeId;
    private String lineCode;
    private String parentLineCode;
    private Integer sortId;
    private String itemNbr;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String remark;
    private Integer proposalId;
    private String itemDescCn;
    private String itemDescEn;
    private String unit1;
    private BigDecimal standardValue1;
    private String unit2;
    private BigDecimal standardValue2;
    private String unit3;
    private BigDecimal standardValue3;
    private String unit4;
    private BigDecimal standardValue4;
    private String unit5;
    private BigDecimal standardValue5;
    private String unit;

    private Integer defaultValus1;
    private Integer defaultValus2;
    private Integer defaultValus3;
    private Integer defaultValus4;
    private Integer defaultValus5;
    private String defaultUnit1;
    private String defaultUnit2;
    private String defaultUnit3;
    private String defaultUnit4;
    private String defaultUnit5;

	private String accordType;

    private Integer operatorUserId;

    private String proposalYear;//proposal制作年度(作为找历史数据使用)
	private String dmFlyer;//宣传单类型

	public void setDeptfeeLineId(Integer deptfeeLineId) {
		this.deptfeeLineId = deptfeeLineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_DEPT_FEE", sequenceName = "SEQ_TTA_DEPT_FEE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_DEPT_FEE", strategy = GenerationType.SEQUENCE)
	@Column(name="deptfee_line_id", nullable=false, length=22)	
	public Integer getDeptfeeLineId() {
		return deptfeeLineId;
	}

	public void setDeptFeeId(Integer deptFeeId) {
		this.deptFeeId = deptFeeId;
	}

	@Column(name="dept_fee_id", nullable=false, length=22)	
	public Integer getDeptFeeId() {
		return deptFeeId;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	@Column(name="line_code", nullable=false, length=50)	
	public String getLineCode() {
		return lineCode;
	}

	public void setParentLineCode(String parentLineCode) {
		this.parentLineCode = parentLineCode;
	}

	@Column(name="parent_line_code", nullable=true, length=22)	
	public String getParentLineCode() {
		return parentLineCode;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@Column(name="sort_id", nullable=true, length=22)	
	public Integer getSortId() {
		return sortId;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	@Column(name="item_nbr")
	public String getItemNbr() {
		return itemNbr;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=1500)	
	public String getRemark() {
		return remark;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	@Column(name="item_desc_cn", nullable=true, length=260)	
	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setItemDescEn(String itemDescEn) {
		this.itemDescEn = itemDescEn;
	}

	@Column(name="item_desc_en", nullable=true, length=260)	
	public String getItemDescEn() {
		return itemDescEn;
	}

	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	@Column(name="unit1", nullable=true, length=50)	
	public String getUnit1() {
		return unit1;
	}

	public void setStandardValue1(BigDecimal standardValue1) {
		this.standardValue1 = standardValue1;
	}

	@Column(name="standard_value1", nullable=true, length=22)	
	public BigDecimal getStandardValue1() {
		return standardValue1;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	@Column(name="unit2", nullable=true, length=50)	
	public String getUnit2() {
		return unit2;
	}

	public void setStandardValue2(BigDecimal standardValue2) {
		this.standardValue2 = standardValue2;
	}

	@Column(name="standard_value2", nullable=true, length=22)	
	public BigDecimal getStandardValue2() {
		return standardValue2;
	}

	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}

	@Column(name="unit3", nullable=true, length=50)	
	public String getUnit3() {
		return unit3;
	}

	public void setStandardValue3(BigDecimal standardValue3) {
		this.standardValue3 = standardValue3;
	}

	@Column(name="standard_value3", nullable=true, length=22)	
	public BigDecimal getStandardValue3() {
		return standardValue3;
	}

	public void setUnit4(String unit4) {
		this.unit4 = unit4;
	}

	@Column(name="unit4", nullable=true, length=50)	
	public String getUnit4() {
		return unit4;
	}

	public void setStandardValue4(BigDecimal standardValue4) {
		this.standardValue4 = standardValue4;
	}

	@Column(name="standard_value4", nullable=true, length=22)	
	public BigDecimal getStandardValue4() {
		return standardValue4;
	}

	public void setUnit5(String unit5) {
		this.unit5 = unit5;
	}

	@Column(name="unit5", nullable=true, length=50)	
	public String getUnit5() {
		return unit5;
	}

	public void setStandardValue5(BigDecimal standardValue5) {
		this.standardValue5 = standardValue5;
	}

	@Column(name="standard_value5", nullable=true, length=22)	
	public BigDecimal getStandardValue5() {
		return standardValue5;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=50)	
	public String getUnit() {
		return unit;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name="accord_type")
	public String getAccordType() {
		return accordType;
	}

	public void setAccordType(String accordType) {
		this.accordType = accordType;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

    @Column(name="DEFAULT_VALUS1")
    public Integer getDefaultValus1() {
        return defaultValus1;
    }

    public void setDefaultValus1(Integer defaultValus1) {
        this.defaultValus1 = defaultValus1;
    }
    @Column(name="DEFAULT_VALUS2")
    public Integer getDefaultValus2() {
        return defaultValus2;
    }

    public void setDefaultValus2(Integer defaultValus2) {
        this.defaultValus2 = defaultValus2;
    }
    @Column(name="DEFAULT_VALUS3")
    public Integer getDefaultValus3() {
        return defaultValus3;
    }

    public void setDefaultValus3(Integer defaultValus3) {
        this.defaultValus3 = defaultValus3;
    }
    @Column(name="DEFAULT_VALUS4")
    public Integer getDefaultValus4() {
        return defaultValus4;
    }

    public void setDefaultValus4(Integer defaultValus4) {
        this.defaultValus4 = defaultValus4;
    }
    @Column(name="DEFAULT_VALUS5")
    public Integer getDefaultValus5() {
        return defaultValus5;
    }

    public void setDefaultValus5(Integer defaultValus5) {
        this.defaultValus5 = defaultValus5;
    }
    @Column(name="DEFAULT_UNIT1")
    public String getDefaultUnit1() {
        return defaultUnit1;
    }

    public void setDefaultUnit1(String defaultUnit1) {
        this.defaultUnit1 = defaultUnit1;
    }
    @Column(name="DEFAULT_UNIT2")
    public String getDefaultUnit2() {
        return defaultUnit2;
    }

    public void setDefaultUnit2(String defaultUnit2) {
        this.defaultUnit2 = defaultUnit2;
    }
    @Column(name="DEFAULT_UNIT3")
    public String getDefaultUnit3() {
        return defaultUnit3;
    }

    public void setDefaultUnit3(String defaultUnit3) {
        this.defaultUnit3 = defaultUnit3;
    }
    @Column(name="DEFAULT_UNIT4")
    public String getDefaultUnit4() {
        return defaultUnit4;
    }

    public void setDefaultUnit4(String defaultUnit4) {
        this.defaultUnit4 = defaultUnit4;
    }
    @Column(name="DEFAULT_UNIT5")
    public String getDefaultUnit5() {
        return defaultUnit5;
    }

    public void setDefaultUnit5(String defaultUnit5) {
        this.defaultUnit5 = defaultUnit5;
    }

	@Column(name="proposal_year")
	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="dm_flyer")
	public String getDmFlyer() {
		return dmFlyer;
	}

	public void setDmFlyer(String dmFlyer) {
		this.dmFlyer = dmFlyer;
	}
}
