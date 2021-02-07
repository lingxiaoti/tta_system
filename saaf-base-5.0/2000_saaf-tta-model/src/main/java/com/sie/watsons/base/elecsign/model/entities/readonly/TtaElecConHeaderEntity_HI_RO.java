package com.sie.watsons.base.elecsign.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaElecConHeaderEntity_HI_RO Entity Object
 * Mon Mar 30 17:14:24 CST 2020  Auto Generate
 */

public class TtaElecConHeaderEntity_HI_RO {
	private Integer elecConHeaderId;
	private String orderNo;
	private String vendorCode;
	private String vendorName;
	private String status;
	private String changeType;
	private String stampStatus;
	private Integer contractYear;
	private Integer orderVersion;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String userFullName;
	private String statusName;
	private String changeTypeName;
	private String stampStatusName;
	private String orgCode;//大部门
	private String orgName;
	private String deptCode;//小部门
	private String deptName;
	private String employeeNumber;//采购人员工号
	private String employeeName;//采购人员姓名
	private String isGmApprove;
	private String contractNo; //合同书编号
	private String isElecFlag; //是否电子签章
	private String specialNbr;//特批单号
	private String archiveNo;// 档案柜ID
	private String venderLinkMan; //供应商联系人
	private String venderPhone; //供应商手机号
	@JSONField(format = "yyyy-MM-dd")
	private Date effectDate; //合同生效日期

	private Integer contractSpecialHeaderId; //特批单号id
	private String isSpecial;//是否特批
	private Integer elecFileId;//电子合同附件id
	private Integer personId;//采购人员id，来自base_person表
	private String ownerCompany;//甲方公司名称
	private Integer supplierId;//供应商id

	private String isThird; //是否丙方供应商
	private String thirdVenderLinkMan; //丙方供应商联系人
	private String thirdVenderPhone; //丙方供应商手机号
	private String thirdVendorCode; //丙方供应商编号
	private String thirdVendorName; //丙方供应商名称
	private String isConversion;//是否甲乙双方更换
	private String isCollect;//甲方供应商资料是否收集完整
	private String isThirdCollect;//乙方供应商资料是否收集完整
	private Integer thirdSupplierId;//丙方供应商id

	private Integer contractHId; // 乙方供应商合同拆分表头tta_contract_header id
	private Integer contractThirdHId; //丙方供应商合同拆分表头 tta_contract_header id
	private Integer proposalId;//乙方proposalId
	private Integer thirdProposalId; //丙方proposalId
	private String contractNoRequire;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date bicRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date financeRegister;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date legaRegister;

	public static final String QUERY_COMPANY = "select  t2.MEANING as ENTERPRISE_NAME\n" +
			"  from base_lookup_types t1, base_lookup_values t2\n" +
			" where t2.lookup_type = t1.lookup_type\n" +
			"   and t2.system_code = t1.system_code\n" +
			"   and t2.lookup_type = 'TTA_OWNER_COMPANY'\n" +
			"   and t2.lookup_code =:lookCode";

	public static String QUERY_ELEC_EADER_SQL = "select   \n" +
			"		  tech.*,\n" +
			"		  ts.supplier_id,\n" +
			"		  ts.is_collect,\n" +
			"	      ths.supplier_id as third_supplier_id,\n" +
			"	      ths.is_collect  as is_third_collect,\n" +
			"         fdp.department_name  as org_name,\n" +
			"         sdp.department_name as dept_name,\n" +
			"         tcsh.order_no as special_nbr,\n" +
			"         bu.user_full_name,\n" +
			"         blv.meaning       as status_name,\n" +
			"         get_split_char(tech.change_type) as change_type_name,\n" +
			"         blv3.meaning      as stamp_status_name\n" +
			//"         bp.person_name as employee_name,\n" +
           // "         bp.employee_number as employee_number\n" +
			"	 from tta_elec_con_header tech\n" +
			"    left join (select lookup_type, lookup_code, meaning\n" +
			"                 from base_lookup_values\n" +
			"                where lookup_type = 'PROPOSAL_STATUS'\n" +
			"                  and enabled_flag = 'Y'\n" +
			"                  and delete_flag = 0\n" +
			"                  and start_date_active < sysdate\n" +
			"                  and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                  and system_code = 'BASE') blv\n" +
			"      on tech.status = blv.lookup_code\n" +
			"    left join (select lookup_type, lookup_code, meaning\n" +
			"                 from base_lookup_values\n" +
			"                where lookup_type = 'TTA_STAMP_STATUS'\n" +
			"                  and enabled_flag = 'Y'\n" +
			"                  and delete_flag = 0\n" +
			"                  and start_date_active < sysdate\n" +
			"                  and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                  and system_code = 'BASE') blv3\n" +
			"      on tech.stamp_status = blv3.lookup_code\n" +
			"    left join base_users bu\n" +
			"      on tech.created_by = bu.user_id\n" +
		/*	"  left join base_user bp \n" +
			"  on bp.person_id = tech.person_id \n" +*/
			"  left join tta_contract_special_header tcsh \n" +
			"  on tcsh.tta_contract_special_header_id = tech.contract_special_header_id\n" +
			"  left join base_department fdp on fdp.department_code = tech.org_code \n" + //大部门
			"  and  department_type = '20'\n" +
			"   left join base_department sdp on sdp.department_code = tech.dept_code \n" +//小部门
			"   and  sdp.department_type = '30'\n" +
			"	left join tta_supplier ts on ts.supplier_code = tech.vendor_code\n" +
			"   left join tta_supplier ths on ths.supplier_code = tech.third_vendor_code\n" +
			"   where 1 = 1 ";

	public static String getindAddNotExistsAttListSql(Integer elecConHeaderId, String vendorCode) {
		String deptAttSql = "\n" +
				"select tcal.*,\n" +
				"       blv2.meaning as file_type_name,\n" +
				"		blv2.description\n" +
				"  from tta_con_attr_line tcal\n" +
				"  left join (select " +
				"				lookup_type,\n" +
				"				lookup_code, \n" +
				"				meaning,\n" +
				"				description\n" +
				"       from base_lookup_values\n" +
				"              where lookup_type = 'TTA_FILE_TYPE'\n" +
				"                and enabled_flag = 'Y'\n" +
				"                and delete_flag = 0\n" +
				"                and start_date_active < sysdate\n" +
				"                and (end_date_active >= sysdate or end_date_active is null)\n" +
				"                and system_code = 'BASE') blv2\n" +
				"    on tcal.file_type = blv2.lookup_code\n" +
				" where 1 = 1\n" +
				"   and exists (select tech.org_code, \n" +
				"			tech.dept_code\n" +
				"          from tta_elec_con_header tech\n" +
				"         where tech.dept_code = tcal.dept_code\n" +
				"           and tech.org_code = tcal.org_code\n" +
				"           and tech.elec_con_header_id =" + elecConHeaderId + "  and tcal.file_type >= 10 \n)\n";

		String sql = "   select a.*,\n" +
				"				dic.meaning as file_type_name," +
				"				dic.description\n" +
				"    from tta_con_attr_line a\n" +
				" left join (select " +
				"				lookup_type,\n" +
				"				lookup_code, \n" +
				"				meaning,\n" +
				" 			 	description\n" +
				"           from base_lookup_values\n" +
				"                where lookup_type = 'TTA_FILE_TYPE'\n" +
				"                  and enabled_flag = 'Y'\n" +
				"                  and delete_flag = 0\n" +
				"                  and start_date_active < sysdate\n" +
				"                  and (end_date_active >= sysdate or end_date_active is null)\n" +
				"                  and system_code = 'BASE') dic on dic.lookup_code = a.file_type\n" +
				"   where not exists\n" +
				"   (select 1\n" +
				"            from tta_elec_con_attr_line b\n" +
				"           where a.con_attr_line_id = b.con_attr_line_id\n" +
				"             and b.elec_con_header_id ="+ elecConHeaderId + ")\n" +
				"     and a.vendor_code ='" + vendorCode + "'\n" +
				// "     and a.con_year =:conYear\n" +
				"     and a.vendor_code = (select max(vendor_code)\n" +
				"                            from tta_con_attr_line c\n" +
				"                           where c.vendor_code ='" + vendorCode + "'\n" +
				//"                             and c.con_year =:conYear" +
				")";
			sql = "select * from (" + deptAttSql + " \n union all\n " + sql + ") order by description asc ";
		return sql;
	}

	public static final String queryLookupSql() {
		String sql = "select lookup_type, -1 as \"parentCode\"," +
				"			lookup_code \"code\", " +
				"			meaning as \"name\" \n" +
				"       from base_lookup_values\n" +
				"       where lookup_type =:lookupType\n" +
				"           and enabled_flag = 'Y'\n" +
				"           and delete_flag = 0\n" +
				"           and start_date_active < sysdate\n" +
				"           and (end_date_active >= sysdate or end_date_active is null)\n" +
				"           and system_code = 'BASE'";
		return sql;
	}


	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}


	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getOrderNo() {
		return orderNo;
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

	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus() {
		return status;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}


	public String getChangeType() {
		return changeType;
	}

	public void setStampStatus(String stampStatus) {
		this.stampStatus = stampStatus;
	}


	public String getStampStatus() {
		return stampStatus;
	}

	public void setContractYear(Integer contractYear) {
		this.contractYear = contractYear;
	}


	public Integer getContractYear() {
		return contractYear;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}


	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}


	public Integer getVersionNum() {
		return versionNum;
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

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getChangeTypeName() {
		return changeTypeName;
	}

	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	public String getStampStatusName() {
		return stampStatusName;
	}

	public void setStampStatusName(String stampStatusName) {
		this.stampStatusName = stampStatusName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getIsGmApprove() {
		return isGmApprove;
	}

	public void setIsGmApprove(String isGmApprove) {
		this.isGmApprove = isGmApprove;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getIsElecFlag() {
		return isElecFlag;
	}

	public void setIsElecFlag(String isElecFlag) {
		this.isElecFlag = isElecFlag;
	}

	public String getSpecialNbr() {
		return specialNbr;
	}

	public void setSpecialNbr(String specialNbr) {
		this.specialNbr = specialNbr;
	}

	public String getArchiveNo() {
		return archiveNo;
	}

	public void setArchiveNo(String archiveNo) {
		this.archiveNo = archiveNo;
	}

	public String getVenderLinkMan() {
		return venderLinkMan;
	}

	public void setVenderLinkMan(String venderLinkMan) {
		this.venderLinkMan = venderLinkMan;
	}

	public String getVenderPhone() {
		return venderPhone;
	}

	public void setVenderPhone(String venderPhone) {
		this.venderPhone = venderPhone;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public Integer getContractSpecialHeaderId() {
		return contractSpecialHeaderId;
	}

	public void setContractSpecialHeaderId(Integer contractSpecialHeaderId) {
		this.contractSpecialHeaderId = contractSpecialHeaderId;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Integer getElecFileId() {
		return elecFileId;
	}

	public void setElecFileId(Integer elecFileId) {
		this.elecFileId = elecFileId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getOwnerCompany() {
		return ownerCompany;
	}

	public void setOwnerCompany(String ownerCompany) {
		this.ownerCompany = ownerCompany;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getIsThird() {
		return isThird;
	}

	public void setIsThird(String isThird) {
		this.isThird = isThird;
	}

	public String getThirdVenderLinkMan() {
		return thirdVenderLinkMan;
	}

	public void setThirdVenderLinkMan(String thirdVenderLinkMan) {
		this.thirdVenderLinkMan = thirdVenderLinkMan;
	}

	public String getThirdVenderPhone() {
		return thirdVenderPhone;
	}

	public void setThirdVenderPhone(String thirdVenderPhone) {
		this.thirdVenderPhone = thirdVenderPhone;
	}

	public String getThirdVendorCode() {
		return thirdVendorCode;
	}

	public void setThirdVendorCode(String thirdVendorCode) {
		this.thirdVendorCode = thirdVendorCode;
	}

	public String getThirdVendorName() {
		return thirdVendorName;
	}

	public void setThirdVendorName(String thirdVendorName) {
		this.thirdVendorName = thirdVendorName;
	}

	public String getIsConversion() {
		return isConversion;
	}

	public void setIsConversion(String isConversion) {
		this.isConversion = isConversion;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getIsThirdCollect() {
		return isThirdCollect;
	}

	public void setIsThirdCollect(String isThirdCollect) {
		this.isThirdCollect = isThirdCollect;
	}

	public Integer getThirdSupplierId() {
		return thirdSupplierId;
	}

	public void setThirdSupplierId(Integer thirdSupplierId) {
		this.thirdSupplierId = thirdSupplierId;
	}

	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	public Integer getContractThirdHId() {
		return contractThirdHId;
	}

	public void setContractThirdHId(Integer contractThirdHId) {
		this.contractThirdHId = contractThirdHId;
	}

	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getThirdProposalId() {
		return thirdProposalId;
	}

	public void setThirdProposalId(Integer thirdProposalId) {
		this.thirdProposalId = thirdProposalId;
	}

	public Date getBicRegister() {
		return bicRegister;
	}

	public void setBicRegister(Date bicRegister) {
		this.bicRegister = bicRegister;
	}

	public Date getFinanceRegister() {
		return financeRegister;
	}

	public void setFinanceRegister(Date financeRegister) {
		this.financeRegister = financeRegister;
	}

	public Date getLegaRegister() {
		return legaRegister;
	}

	public void setLegaRegister(Date legaRegister) {
		this.legaRegister = legaRegister;
	}

	public String getContractNoRequire() {
		return contractNoRequire;
	}

	public void setContractNoRequire(String contractNoRequire) {
		this.contractNoRequire = contractNoRequire;
	}
}