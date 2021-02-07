package com.sie.watsons.base.proposal.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaDeptFeeLineEntity_HI_RO Entity Object
 * Thu Jun 13 11:55:36 CST 2019  Auto Generate
 */

public class TtaDeptFeeLineEntity_HI_RO {
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

	private String accordType;
	private String accordTypeName;
    private String itemDetail;
    private Integer operatorUserId;

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
    private String checked;// -1:最下级 其他值:不是最下级
	private String proposalYear;//proposal制作年度(作为找历史数据使用)
	private String dmFlyer;//宣传单类型
	private String flag ;


	public static final String TTA_ITEM_V = "select * from TTA_DEPT_FEE_V V where 1=1 ";

	public static final String TTA_ITEM_REPORT = "select * from   tta_dept_fee_report_v tdfrv where 1=1 ";

	public static  String getDeptFeeShowOrHide(String proposalId){
		return "select count(terms_l_id) flag\n" +
				"            from (select tptl.terms_l_id\n" +
				"                    from tta_proposal_terms_line tptl\n" +
				"                   where tptl.proposal_id = "+proposalId+"\n" +
				"                     and tptl.y_terms = '陈列服务费'\n" +
				"                     and nvl(tptl.income_type, '按公司标准') = '按其他协定标准'\n" +
				"                  union all\n" +
				"                  select tptl.terms_l_id\n" +
				"                    from tta_proposal_terms_line tptl\n" +
				"                   where tptl.proposal_id ="+proposalId+"\n" +
				"                     and tptl.y_terms = 'DM'\n" +
				"                     and nvl(tptl.income_type, '按公司标准') = '按其他协定标准'\n" +
				"                  union all\n" +
				"                  select tptl.terms_l_id\n" +
				"                    from tta_proposal_terms_line tptl\n" +
				"                   where tptl.proposal_id = "+proposalId+"\n" +
				"                     and tptl.y_terms = 'FYLER'\n" +
				"                     and nvl(tptl.income_type, '按公司标准') = '按其他协定标准'\n" +
				"					union all\n" +
				"                   select tptl.terms_l_id\n" +
				"                    from tta_proposal_terms_line tptl\n" +
				"                   where tptl.proposal_id = " +proposalId+ "\n" +
				"                     and tptl.y_terms = '促销陈列服务费'\n" +
				"                     and nvl(tptl.income_type, '按公司标准') = '按其他协定标准'\n" +
				")";
	}


	public void setDeptfeeLineId(Integer deptfeeLineId) {
		this.deptfeeLineId = deptfeeLineId;
	}

	
	public Integer getDeptfeeLineId() {
		return deptfeeLineId;
	}

	public void setDeptFeeId(Integer deptFeeId) {
		this.deptFeeId = deptFeeId;
	}

	
	public Integer getDeptFeeId() {
		return deptFeeId;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	
	public String getLineCode() {
		return lineCode;
	}

	public void setParentLineCode(String parentLineCode) {
		this.parentLineCode = parentLineCode;
	}

	
	public String getParentLineCode() {
		return parentLineCode;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	
	public Integer getSortId() {
		return sortId;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	
	public String getItemNbr() {
		return itemNbr;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	
	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setItemDescEn(String itemDescEn) {
		this.itemDescEn = itemDescEn;
	}

	
	public String getItemDescEn() {
		return itemDescEn;
	}

	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	
	public String getUnit1() {
		return unit1;
	}

	public void setStandardValue1(BigDecimal standardValue1) {
		this.standardValue1 = standardValue1;
	}

	
	public BigDecimal getStandardValue1() {
		return standardValue1;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	
	public String getUnit2() {
		return unit2;
	}

	public void setStandardValue2(BigDecimal standardValue2) {
		this.standardValue2 = standardValue2;
	}

	
	public BigDecimal getStandardValue2() {
		return standardValue2;
	}

	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}

	
	public String getUnit3() {
		return unit3;
	}

	public void setStandardValue3(BigDecimal standardValue3) {
		this.standardValue3 = standardValue3;
	}

	
	public BigDecimal getStandardValue3() {
		return standardValue3;
	}

	public void setUnit4(String unit4) {
		this.unit4 = unit4;
	}

	
	public String getUnit4() {
		return unit4;
	}

	public void setStandardValue4(BigDecimal standardValue4) {
		this.standardValue4 = standardValue4;
	}

	
	public BigDecimal getStandardValue4() {
		return standardValue4;
	}

	public void setUnit5(String unit5) {
		this.unit5 = unit5;
	}

	
	public String getUnit5() {
		return unit5;
	}

	public void setStandardValue5(BigDecimal standardValue5) {
		this.standardValue5 = standardValue5;
	}

	
	public BigDecimal getStandardValue5() {
		return standardValue5;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public String getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public String getAccordType() {
		return accordType;
	}

	public void setAccordType(String accordType) {
		this.accordType = accordType;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getAccordTypeName() {
		return accordTypeName;
	}

	public void setAccordTypeName(String accordTypeName) {
		this.accordTypeName = accordTypeName;
	}

    public Integer getDefaultValus1() {
        return defaultValus1;
    }

    public void setDefaultValus1(Integer defaultValus1) {
        this.defaultValus1 = defaultValus1;
    }

    public Integer getDefaultValus2() {
        return defaultValus2;
    }

    public void setDefaultValus2(Integer defaultValus2) {
        this.defaultValus2 = defaultValus2;
    }

    public Integer getDefaultValus3() {
        return defaultValus3;
    }

    public void setDefaultValus3(Integer defaultValus3) {
        this.defaultValus3 = defaultValus3;
    }

    public Integer getDefaultValus4() {
        return defaultValus4;
    }

    public void setDefaultValus4(Integer defaultValus4) {
        this.defaultValus4 = defaultValus4;
    }

    public Integer getDefaultValus5() {
        return defaultValus5;
    }

    public void setDefaultValus5(Integer defaultValus5) {
        this.defaultValus5 = defaultValus5;
    }

    public String getDefaultUnit1() {
        return defaultUnit1;
    }

    public void setDefaultUnit1(String defaultUnit1) {
        this.defaultUnit1 = defaultUnit1;
    }

    public String getDefaultUnit2() {
        return defaultUnit2;
    }

    public void setDefaultUnit2(String defaultUnit2) {
        this.defaultUnit2 = defaultUnit2;
    }

    public String getDefaultUnit3() {
        return defaultUnit3;
    }

    public void setDefaultUnit3(String defaultUnit3) {
        this.defaultUnit3 = defaultUnit3;
    }

    public String getDefaultUnit4() {
        return defaultUnit4;
    }

    public void setDefaultUnit4(String defaultUnit4) {
        this.defaultUnit4 = defaultUnit4;
    }

    public String getDefaultUnit5() {
        return defaultUnit5;
    }

    public void setDefaultUnit5(String defaultUnit5) {
        this.defaultUnit5 = defaultUnit5;
    }

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	public String getDmFlyer() {
		return dmFlyer;
	}

	public void setDmFlyer(String dmFlyer) {
		this.dmFlyer = dmFlyer;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
