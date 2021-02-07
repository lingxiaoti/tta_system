package com.sie.watsons.base.proposal.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaDeptFeeHeaderEntity_HI_RO Entity Object
 * Thu Jun 13 11:55:35 CST 2019  Auto Generate
 */

public class TtaDeptFeeHeaderEntity_HI_RO {
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

	private String deptDesc1;
	private String deptDesc2;
	private String deptDesc3;
	private String deptDesc4;
	private String deptDesc5;

	private String vendorNbr;
	private String vendorName;
	private String accordTypeName;




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
    private String isConf;
    private String isTransDept;
    private Integer operatorUserId;

    private String standardUnitValue;

	public static final String TTA_ITEM_V = "select * from TTA_DEPT_FEE_HEADER_V V where 1=1 ";

	public static String getTableDeptSql(Integer operatorUserId, String userType) {
		String sql = null; //助理采购,采购,高级采购,经理,高级经理
		if ("12,13,15,20,25".contains(userType)) {
			 sql = "(select \n" +
					" nvl(t1.group_name,'-1') as group_name," +
					" nvl(t1.group_code,'-1') as group_code," +
					" nvl(t2.department_code,'-1') as department_code\n" +
					" from base_users t1 \n" +
					" inner join  \n" +
					"(select \n" +
					"  bd.department_code,\n" +
					"  bu.user_id\n" +
					"  from base_users bu\n" +
					" inner join base_person bp\n" +
					"    on bu.person_id = bp.person_id --  寻找用户表\n" +
					" inner join base_person_assign bpa\n" +
					"    on bp.person_id = bpa.person_id -- 寻找职位分配表\n" +
					" inner join base_position bpn\n" +
					"    on bpa.position_id = bpn.position_id --  寻找职位表\n" +
					" inner join base_department bd\n" +
					"    on bpn.department_id = bd.department_id -- 寻找部门表\n" +
					" where nvl(bd.enable_flag,'Y')='Y' and bu.user_id =" +  operatorUserId + "  group by bd.department_code, bu.user_id) t2\n" +
					" on t1.user_id =  " + operatorUserId + "\n) dept\t";
		}
		return sql;
	}


	public void setDeptFeeId(Integer deptFeeId) {
		this.deptFeeId = deptFeeId;
	}

	
	public Integer getDeptFeeId() {
		return deptFeeId;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	
	public String getYearCode() {
		return yearCode;
	}

	public void setAccordType(String accordType) {
		this.accordType = accordType;
	}

	
	public String getAccordType() {
		return accordType;
	}

	public void setFeeItem(String feeItem) {
		this.feeItem = feeItem;
	}

	
	public String getFeeItem() {
		return feeItem;
	}

	public void setCompanyUnit(String companyUnit) {
		this.companyUnit = companyUnit;
	}

	
	public String getCompanyUnit() {
		return companyUnit;
	}

	public void setDeptCode1(Integer deptCode1) {
		this.deptCode1 = deptCode1;
	}

	
	public Integer getDeptCode1() {
		return deptCode1;
	}

	public void setDeptCode2(Integer deptCode2) {
		this.deptCode2 = deptCode2;
	}

	
	public Integer getDeptCode2() {
		return deptCode2;
	}

	public void setDeptCode3(Integer deptCode3) {
		this.deptCode3 = deptCode3;
	}

	
	public Integer getDeptCode3() {
		return deptCode3;
	}

	public void setDeptCode4(Integer deptCode4) {
		this.deptCode4 = deptCode4;
	}

	
	public Integer getDeptCode4() {
		return deptCode4;
	}

	public void setDeptCode5(Integer deptCode5) {
		this.deptCode5 = deptCode5;
	}

	
	public Integer getDeptCode5() {
		return deptCode5;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setIsConf(String isConf) {
		this.isConf = isConf;
	}

	
	public String getIsConf() {
		return isConf;
	}

	public void setIsTransDept(String isTransDept) {
		this.isTransDept = isTransDept;
	}

	
	public String getIsTransDept() {
		return isTransDept;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public String getDeptDesc1() {
		return deptDesc1;
	}

	public void setDeptDesc1(String deptDesc1) {
		this.deptDesc1 = deptDesc1;
	}

	public String getDeptDesc2() {
		return deptDesc2;
	}

	public void setDeptDesc2(String deptDesc2) {
		this.deptDesc2 = deptDesc2;
	}

	public String getDeptDesc3() {
		return deptDesc3;
	}

	public void setDeptDesc3(String deptDesc3) {
		this.deptDesc3 = deptDesc3;
	}

	public String getDeptDesc4() {
		return deptDesc4;
	}

	public void setDeptDesc4(String deptDesc4) {
		this.deptDesc4 = deptDesc4;
	}

	public String getDeptDesc5() {
		return deptDesc5;
	}

	public void setDeptDesc5(String deptDesc5) {
		this.deptDesc5 = deptDesc5;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getAccordTypeName() {
		return accordTypeName;
	}

	public void setAccordTypeName(String accordTypeName) {
		this.accordTypeName = accordTypeName;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

    public String getStandardUnitValue() {
        return standardUnitValue;
    }

    public void setStandardUnitValue(String standardUnitValue) {
        this.standardUnitValue = standardUnitValue;
    }
}
