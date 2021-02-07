package com.sie.watsons.base.contract.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaContractHeaderEntity_HI_RO Entity Object
 * Wed Jun 19 10:25:06 CST 2019  Auto Generate
 */

public class TtaContractHeaderEntity_HI_RO {
    private Integer contractHId;
    private String contractCode;
    private String vendorNbr;
    private String vendorName;
    private String billStatus;
    private String proposalCode;
    private Integer proposalYear;
    private Integer proposalVersion;
    private String isSplit;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date confirmDate;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date submitDate;

    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	private String statusName;
	private String lastUpdatedName;
	private String createdName;

	private String proposalVendorNbr;
	private String proposalVendorName;
	private String createdUserName;
	private Integer proposalId;
	private String isSpecial;

	public static final String updateConHeaderSpecial(String proposalOrderNo, Integer versionCode, Integer userId, String isSpecial) {
		String sql = "  update tta_contract_line tcl\n" +
				"     set tcl.is_special = '" + isSpecial + "'" +
				" 		,tcl.last_updated_by =" + userId +
				"		,tcl.last_update_date = sysdate " +
                "   where tcl.contract_h_id is null \n" +
				"     and tcl.proposal_id = (select t.proposal_id\n" +
				"                              from tta_proposal_header t\n" +
				"                             where t.order_nbr = '" + proposalOrderNo + "'\n" +
				"                               and t.version_code = " + versionCode + " and t.status = 'C')";
		return sql;
	}

	//通过proposal编号查询proposal信息
	public static final String queryProposalHeaderSql() {
		String queryProposalHeaderSql = "select " +
				"	t.proposal_id,\n" +
				" t.order_nbr as proposal_code, \n" +
				" t.proposal_year, t.vendor_nbr, t.vendor_name, \n" +
				" t.version_code as proposal_version \n" +
				" from tta_proposal_header_v t \n" +
				" where t.proposal_id =:proposalId\n";
		return queryProposalHeaderSql;
	}


	//通过合同头id查询proposal信息及合同头信息
	public static final String queryProposalHeaderAndContractSql() {
		String queryProposalHeaderAndContractSql = "select " +
				"		t.proposal_id," +
				"       t.vendor_nbr       as proposal_vendor_nbr,\n" +
				"       t.vendor_name      as proposal_vendor_name,\n" +
				"       t.version_code,\n" +
				"       t1.contract_h_id,\n" +
				"       t1.contract_code,\n" +
				"       t1.vendor_nbr,\n" +
				"       t1.vendor_name,\n" +
				"       t1.bill_status,\n" +
				"       t1.proposal_code,\n" +
				"       t1.proposal_year,\n" +
				"       t1.proposal_version,\n" +
				"       t1.is_split,\n" +
				"       t1.confirm_date,\n" +
				"       t1.version_num,\n" +
				"       t1.creation_date,\n" +
				"       t1.created_by,\n" +
				"       t1.last_updated_by,\n" +
				"       t1.last_update_date,\n" +
				"       t1.last_update_login,\n" +
				"       t1.submit_date,\n" +
				"       t1.is_special,\n" +
				"       bu.user_full_name as created_user_name\n" +
				"  from tta_proposal_header_v t\n" +
				" inner join tta_contract_header t1\n" +
				"    on t1.proposal_code = t.order_nbr\n" +
				" and t1.proposal_version = t.version_code\n" +
			 //	" and t1.vendor_nbr = t1.vendor_nbr\n" +
				" left join base_users bu on bu.user_id = t1.created_by\n" +
				" where t1.contract_h_id=:contractHId";
		return queryProposalHeaderAndContractSql;
	}


	
	/**
	 * 功能描述： 获取条款信息
	 */
	public static  String getContractTitleSlq() {
		//1、条款"新品额外折扣(首6个月)"可隐藏，此条款跟BEOI条款一样也是不参与拆分的
		//2、条款"免费货品"，当收取方式为"按含税采购额"的可隐藏不参与拆分，当收取方式为"全年不少于"则继续参与拆分；
		String sql = "select listagg(t.terms_cn, '|') within group(order by t.order_no) as VALUE\n" +
				"  from (select *\n" +
				"          from tta_contract_line t\n" +
				"         where t.oi_type not in ('BEOI')\n" +
				"           and t.terms_cn not like '%新品额外折扣%'\n" +
				"           and t.proposal_id =:proposalId\n" +
				"           and t.terms_cn not like '免费货品%'\n" +
				"           and t.contract_h_id is null\n" +
				"        union all\n" +
				"        select *\n" +
				"          from tta_contract_line t\n" +
				"         where t.terms_cn like '%免费货品%'\n" +
				"           and t.income_type not like '%按含税采购额%'\n" +
				"           and t.proposal_id =:proposalId\n" +
				"           and t.contract_h_id is null) t\n" +
				" order by t.order_no";

		return sql;
	}

	public static  String getContractBottomTitle() {
		//1、条款"新品额外折扣(首6个月)"可隐藏，此条款跟BEOI条款一样也是不参与拆分的
		String sql = " \n" +
				" select listagg('条款值|条款内容', '|') within group(order by t.order_no) as VALUE\n" +
				"   from (select *\n" +
				"           from tta_contract_line t\n" +
				"          where t.oi_type not in ('BEOI')\n" +
				"            and t.terms_cn not like '%新品额外折扣%'\n" +
				"            and t.proposal_id =:proposalId\n" +
				"            and t.terms_cn not like '%免费货品%'\n" +
				"            and t.contract_h_id is null\n" +
				"         union all\n" +
				"         select *\n" +
				"           from tta_contract_line t\n" +
				"          where t.terms_cn like '%免费货品%'\n" +
				"            and t.income_type not like '%按含税采购额%'\n" +
				"            and t.proposal_id =:proposalId\n" +
				"            and t.contract_h_id is null) t\n" +
				"  order by t.order_no\n";
		return sql;
	}


	/**
	 * 功能描述： 获取合同条款和值信息
	 */
	public static  String getContractTermsAndValueSlq() {
		String sql = "select replace(((select tcl.vendor_code || '|' || tcl.vendor_name || '|' ||\n" +
				"               tcl.delivery_granary || '|' || tcl.sales_area || '|' ||\n" +
				"               getformatnumber(tcl.sales) || '|' || getformatnumber(tcl.purchase) as value\n" +
				"          from tta_contract_line tcl\n" +
				"         where tcl.proposal_id =:proposalId\n" +
				" \t\t\tand tcl.contract_h_id is  null \n" +
				"           and rownum = 1) || '|'|| value), chr(10),'</br>') as value\n" +
				"  from (select listagg(getformatnumber(t.tta_value) || '|' || t.terms_system, '|') WITHIN GROUP(ORDER BY t.order_no) as VALUE\n" +
				"          from ( \n" +
				"          select * from  tta_contract_line t\n" +
				"           where t.oi_type not in ('BEOI')\n" +
				"            and t.terms_cn not like '%新品额外折扣%'  \n" +
				"            and t.terms_cn not like '%免费货品%' \n" +
				"            and t.proposal_id =:proposalId\n" +
				"            and t.contract_h_id is null\n" +
				"           union all\n" +
				"            select * from  tta_contract_line t\n" +
				"            where  t.terms_cn  like '%免费货品%' \n" +
				"            and t.income_type not like '%按含税采购额%'\n" +
				"            and t.proposal_id =:proposalId\n" +
				"            and t.contract_h_id is null\n" +
				"      ) t  order by t.order_no)";
		return sql;
	}

	/**
	 * 功能描述： 各供应商合同条款信息
	 */
	public static final String getVendorContractTermsAndValueSlq(String userType) {
		String sql = "select (" +
				"	select   " +
				"			'-1@vendor_code@'|| tcl.vendor_code " +
				"			|| '|' || '-2@vendor_name@'|| tcl.vendor_name " +
				"			|| '|' || '-3@delivery_granary@' || nvl(tcl.delivery_granary,'-9999') " +
				"			|| '|' || '-4@sales_area@'|| nvl(tcl.sales_area,'-9999') " +
				"			|| '|' || '-5@sales@'||  nvl(tcl.sales,'-9999') " +
				"			|| '|' || '-6@purchase@' || nvl(tcl.purchase,'-9999') " +
				"			|| '|' || '-7@is_special@' || nvl(nvl(tcl.is_special, 'N'),'-9999')" +
				"	as value from tta_contract_line tcl\n" +
				"          where 1 = 1 \n" +
				" 			 and tcl.terms_cn not like '%新品额外折扣%' \n" + //20200406 因不需要拆分，显示错位问题，不显示新品额外折扣
				//"			 and tcl.terms_cn not like '%免费货品%' \n" + 		//20200415 需求显示免费货品（不含税成本之金额）, 如单号CSP2020040125
				"                and tcl.proposal_id=:proposalId \n" +
				"                and tcl.contract_h_id=:contractHId \n" +
				"                and tcl.vendor_code =:vendorCode \n" +
				"                and rownum=1) || '|' || contract_value  as value from  (\n" +
				" select listagg(t.contract_l_id || '@tta_value@'|| nvl(t.tta_value,'-9999')  || '|' || t.contract_l_id || '@terms_system@' || "
				+ ("45".equalsIgnoreCase(userType) ? " nvl(t.terms_system,'-9999') " : "	replace(nvl(t.terms_system,'-9999'), chr(10),'</br>') " ) +
				"	|| '|' || t.contract_l_id || '@split_parent_id@' || t.split_parent_id, '|')  WITHIN GROUP(ORDER BY t.order_no) as contract_value\n" +
				"           from tta_contract_line t\n" +
				"          where 1 = 1 \n" +
				" 			 and t.terms_cn not like '%新品额外折扣%'\n" + //20200406 因不需要拆分，显示错位问题，不显示新品额外折扣
				//"			 and t.terms_cn not like '%免费货品%' \n" + //20200415 需求显示免费货品（不含税成本之金额）, 如单号CSP2020040125
				"                and t.proposal_id=:proposalId \n" +
				"                and t.contract_h_id=:contractHId \n" +
				"                and t.vendor_code =:vendorCode \n" +
				"          order by t.order_no) t2";
		return sql;
	}

	public  static final String getContractVendorList() {
	    return "select t.vendor_code as vendor_code  from tta_contract_line t where t.contract_h_id=:contractHId  group by t.vendor_code order by t.vendor_code asc\n";
    }

	public static final String TTA_ITEM_V = "select * from tta_contract_header_V V where 1=1";

	public static final String TTA_ITEM_S_V = "SELECT a.* from tta_contract_header_v a inner join tta_proposal_header_v b " +
			" on a.proposal_code = b.order_nbr and a.proposal_version = b.version_code where 1=1 ";


	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	
	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	
	public String getContractCode() {
		return contractCode;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}


	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	
	public String getBillStatus() {
		return billStatus;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	
	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalYear(Integer proposalYear) {
		this.proposalYear = proposalYear;
	}

	
	public Integer getProposalYear() {
		return proposalYear;
	}

	public void setProposalVersion(Integer proposalVersion) {
		this.proposalVersion = proposalVersion;
	}

	
	public Integer getProposalVersion() {
		return proposalVersion;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	
	public String getIsSplit() {
		return isSplit;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	
	public Date getConfirmDate() {
		return confirmDate;
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



	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}


	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getLastUpdatedName() {
		return lastUpdatedName;
	}

	public void setLastUpdatedName(String lastUpdatedName) {
		this.lastUpdatedName = lastUpdatedName;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getProposalVendorNbr() {
		return proposalVendorNbr;
	}

	public void setProposalVendorNbr(String proposalVendorNbr) {
		this.proposalVendorNbr = proposalVendorNbr;
	}

	public String getProposalVendorName() {
		return proposalVendorName;
	}

	public void setProposalVendorName(String proposalVendorName) {
		this.proposalVendorName = proposalVendorName;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getProposalId() {
		return proposalId;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}
}
