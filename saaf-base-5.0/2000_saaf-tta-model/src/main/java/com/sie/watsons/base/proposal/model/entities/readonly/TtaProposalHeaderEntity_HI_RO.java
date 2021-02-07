package com.sie.watsons.base.proposal.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaProposalHeaderEntity_HI_RO Entity Object
 * Tue Jun 04 08:38:52 CST 2019  Auto Generate
 */

public class TtaProposalHeaderEntity_HI_RO {
	private Integer proposalId;
	private Integer contractHId;
    private String orderNbr;
    private String proposalNbr;
    private String vendorNbr;
	private String vendorCode;
	private String brandCn;
	private String brandEn;
    private String vendorName;
    private String proposalYear;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
    private String status;
    private BigDecimal versionCode;
    private String isTransdepart;
    private String newPaymentMethod;
	private BigDecimal newStoreQty;
	private BigDecimal plnAdjustScale;
    private String isPlnConfirm;
    private String isTermsConfirm;
    private String isDepartConfirm;
    private String isNewConfirm;
    private String isQuestConfirm;
    private String isCrossYear;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String isChange;
    private Integer sourceId;
    private String sourceCode;
    private Integer operatorUserId;
	private String newOrExisting;

	private String lastUpdatedName;
	private String createdName;
	private String statusName;
	private String newPaymentName;
	private String createdByName ;
	private String saleTypeName ;
	private String deptCode1;
	private String deptDesc1;
	private String deptCode2;
	private String deptDesc2;
	private String deptCode3;
	private String deptDesc3;
	private String deptCode4;
	private String deptDesc4;
	private String deptCode5;
	private String deptDesc5;
	private String majorDeptDesc;//主部门
	private Integer majorDeptId;//主部门id
	private String  majorDeptCode;//主部门编号
	private String saleType;//销售方式
	private String isSkipApprove; //是否自动跳过GM审批,Y是跳过,N不跳过

	private String versionStatus;
	private Integer originProposalId;
	private String deptCode;
	private String deptDesc;
	private BigDecimal fcsPurchse;
	private String invoiceType; //增值税普通发票,红字发票
	private String agreementEdition; //合同版A,B本
	private Integer lastYearProposalId ;
	private String lastYearOrderNo ;
	private String lastYearVendorCode ;
	private String lastYearVendorName ;
	private String lastYearProposalIds ;

	private String userGroupCode;//用户group
	private String userGroupName;
	private String isSplit;

	private BigDecimal waitTime; //流程等待时长小时
	private String currentUserName;//当前审批人

	private String orderNbrVersion;

	private Integer startUserId; //流程发起人

	private String venderLinkMan;//供应商联系人

	private String  venderPhone;//供应商手机号

	private Integer supplierId;//供应商id

	private String isHiddenQuestion;

	private String isCollection;//是否是补录单

	private String isCollect;//供应商资料是否完整


	public static final String TTA_ITEM_V = "select v.*, t.wait_time, t.start_user_id, us.user_full_name as current_user_name\n" +
			"  from tta_proposal_header_V v\n" +
			"  left join (select a.business_key,\n" +
			"                    b.assignee_,\n" +
			"                    round((sysdate - cast(b.create_time_ as date)) * 24, 2) as wait_time,\n" +
			"					a.created_by as start_user_id" +
			"               from act_bpm_list a\n" +
			"              inner join act_ru_task b\n" +
			"                 on a.proc_inst_id = b.proc_inst_id_\n" +
			"                and a.proc_def_key = 'TTA_PROPOSAL.-999') t\n" +
			"    on v.proposal_id = t.business_key\n" +
			"  left join base_users us\n" +
			"    on us.user_name = t.assignee_ where  1 = 1";

	/*public static final String PROPOSAL_QUERY = "select v.order_nbr,\n" +
			"        v.proposal_year,\n" +
			"        v.vendor_nbr,\n" +
			"        v.vendor_name,\n" +
			"        v.version_code\n" +
			"   from tta_proposal_header v\n" +
			"  where 1 = 1\n" +
			"    and v.status <> 'D'\n" +
			"    and v.vendor_nbr =:vendorNbr\n" +
			"	 and v.proposal_year =:proposalYear\n" +
			"    and v.status =:status\n" +
 			" union\n" +
			" select tph.order_nbr,\n" +
			"        tph.proposal_year,\n" +
			"        tph.vendor_nbr,\n" +
			"        tph.vendor_name,\n" +
			"        tph.version_code\n" +
			"   from tta_proposal_header tph\n" +
			"   join tta_supplier ts\n" +
			"     on tph.vendor_nbr = ts.supplier_code\n" +
			"    and nvl(ts.formal_code, '-1') != '-1'\n" +
			"  where tph.status <> 'D'\n" +
			"    and ts.formal_code =:vendorNbr\n" +
			"    and tph.proposal_year =:proposalYear\n" +
			"    and tph.status =:status ";*/

	public static final String PROPOSAL_QUERY ="select \n" +
			"        tph.order_nbr,\n" +
			"        tph.proposal_year,\n" +
			"        tph.vendor_nbr,\n" +
			"        tph.vendor_name,\n" +
			"        tph.version_code\n" +
			"  from tta_supplier_major_v t\n" +
			"  join tta_proposal_header tph\n" +
			"    on t.SUPPLIER_CODE = tph.vendor_nbr\n" +
			" where \n" +
			"       tph.status <> 'D' \n" +
			"       and tph.proposal_year=:proposalYear \n" +
			"       and tph.status =:status\n" +
			"       and t.major_supplier_code =:vendorNbr ";

	public static final String getTtaVendorCode (String contractYear) {
		String sql = "SELECT * FROM (\n" +
				"SELECT * FROM (\n" +
				"SELECT \n" +
				"tph.order_nbr\n" +
				",tph.proposal_year\n" +
				",tcl.vendor_code\n" +
				",tcl.vendor_name\n" +
				",tpth.brand_cn\n" +
				",tpth.brand_en\n" +
				",tph.SALE_TYPE\n" +
				",tph.proposal_id\n" +
				",lookup1.meaning SALE_TYPE_NAME\n" +
				",Row_Number() Over(Partition By tcl.vendor_code,tcl.proposal_year Order By tcl.contract_l_id desc) Row_Id\n" +
				",tpth.agreement_edition\n" +
				"FROM  (SELECT tcl.vendor_code,tcl.proposal_year,max(tcl.contract_l_id) contract_l_id,\n" +
				"      max(vendor_name) vendor_name,max(tcl.proposal_id) proposal_id FROM   tta_contract_line tcl group by tcl.vendor_code,tcl.proposal_year) tcl\n" +
				"join (" +
				"	select * from tta_proposal_header tph" +
				" where tph.status = 'C'\n" +
				"	and (tph.version_status = '1' \n" +
				"   or NVL(tph.version_status,'-1') ='-1')\n " +
				(StringUtils.isNotBlank(contractYear) ? "\n and tph.proposal_year=" + contractYear : "" ) +
				" ) tph " +
				"   on tcl.proposal_id = tph.proposal_id " +
				"join tta_proposal_terms_header tpth  on tpth.proposal_id = tph.proposal_id\n" +
				"  left join\n" +
				"  (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='SALE_WAY' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  on tph.SALE_TYPE = lookup1.lookup_code ) t2 where  T2.Row_Id = 1) tphh where 1=1 ";
		return sql;
	}


	public static final String TTA_ITEM_DETAIL = "select * from tta_proposal_header_V V left join  " +
			" tta_proposal_terms_headerold_v t on t.proposal_id = v.proposal_id  where 1=1 ";


    public static final String TTA_PROPOSAL_HEADER = "select * from tta_proposal_header h where " +
            "h.IS_TERMS_CONFIRM='Y' ";

    public static final String TTA_GET_PROPOSAL_HEARER = "select proposal_id as proposalId,\n" +
			"       order_nbr as orderNbr,\n" +
			"       proposal_nbr as proposalNbr,\n" +
			"       vendor_nbr as vendorNbr,\n" +
			"       vendor_name as vendorName,\n" +
			"       proposal_year as proposalYear,\n" +
			"       user_group_code as userGroupCode,\n" +
			"       user_group_name as userGroupName\n" +
			"  from tta_proposal_header tph where 1=1 ";

	public static final String SELECT_SALETYPE_PROPOSALYEAR = "select tph.proposal_id proposalId,\n" +
			"       tph.vendor_nbr vendorNbr,\n" +
			"       tph.vendor_name vendorName,\n" +
			"       tph.proposal_year proposalYear,\n" +
			"       tph.sale_type saleType\n" +
			"  from tta_proposal_header tph\n" +
			" where tph.vendor_nbr = :vendorNbr\n" +
			"   and tph.proposal_year = :proposalYear\n" +
			"   and tph.new_or_existing = :newOrExisting\n" +
			"   and tph.status = 'C' and 1=1 ";

	public static final String SELECT_PROPOSAL_BY_SUPPLIER = "select tph.proposal_id proposalId,\n" +
			"       tph.order_nbr orderNbr,\n" +
			"       tph.proposal_year proposalYear,\n" +
			"       tph.vendor_nbr vendorNbr,\n" +
			"       tph.vendor_name vendorName\n" +
			"  from tta_proposal_header tph where tph.status in('A', 'B', 'C') ";

	//根据续签供应商Existing_Vendor,年度,供应商编号查找proposal单据
	public static final String SELECT_PROPOSAL_BY_PROPOSALYEAR_VENDORNBR = " select tph.proposal_id proposalId,\n" +
			"             tph.vendor_nbr vendorNbr,\n" +
			"             tph.vendor_name vendorName,\n" +
			"             tph.proposal_year proposalYear,\n" +
			"             tph.new_or_existing newOrExisting,\n" +
			"             tph.order_nbr orderNbr\n" +
			"        from tta_proposal_header tph\n" +
			"       where tph.new_or_existing ='Existing_Vendor'\n" +
			"         and tph.status != 'D' and tph.vendor_nbr in(:vendorNbr) and 1=1 ";


	public static final  String QUERY_CURRENT_NODE_NAME = "select \n" +
			" name_ as \"currentNodeName\" \n" +
			"  from act_ru_task a\n" +
			" inner join act_bpm_task_leavel b\n" +
			"    on a.id_ = b.task_id\n" +
			" inner join act_bpm_list c on \n" +
			" c.proc_inst_id = b.top_proc_inst_id\n" +
			" where c.proc_def_key=:procDefKey and c.business_key=:businessKey";

	public static final  String QUERY_LAST_YEAR_ORDER_NO = "SELECT * FROM (\n" +
			"\n" +
			"\n" +
			"select tph.proposal_id,tph.order_nbr,tph.vendor_nbr,tph.vendor_name,tph.version_code,tph.proposal_year,tph.is_split from tta_proposal_header tph  \n" +
			"                                                                                          where tph.vendor_nbr = :vendorNbr  \n" +
			"                                                                                          and tph.status = 'C' \n" +
			"                                                                                          and tph.proposal_year = :proposalYear\n" +
			"                                                                                          \n" +
			"union\n" +
			"\n" +
			"select tph.proposal_id,tph.order_nbr,tph.vendor_nbr,tph.vendor_name,tph.version_code,tph.proposal_year,tph.is_split from tta_supplier  ts,\n" +
			"                                                                                          tta_proposal_header tph  \n" +
			"                                                                                           where  ts.formal_code = :vendorNbr\n" +
			"                                                                                           and   ts.supplier_code = tph.vendor_nbr\n" +
			"                                                                                           and   tph.status = 'C'\n" +
			"                                                                                           and   tph.proposal_year = :proposalYear \n" +
			"                                                                                     \n" +
			"union\n" +
			"                                                                                     \n" +
			"                                                                                     \n" +
			"select tph.proposal_id,tph.order_nbr,tph.vendor_nbr,tph.vendor_name,tph.version_code,tph.proposal_year,tph.is_split from tta_supplier ts,\n" +
			"                                                                                          tta_rel_supplier trs,\n" +
			"                                                                                          tta_proposal_header tph\n" +
			"                                                where ts.supplier_code = :vendorNbr\n" +
			"                                                and ts.supplier_id = trs.rel_id\n" +
			"                                                and trs.rel_supplier_code = tph.vendor_nbr\n" +
			"                                                and tph.status = 'C'\n" +
			"                                                and tph.proposal_year = :proposalYear  \n" +
			"                                               \n" +
			"union                                               \n" +
			"                                               \n" +
			"                                               \n" +
			"select tph.proposal_id,tph.order_nbr,tph.vendor_nbr,tph.vendor_name,tph.version_code,tph.proposal_year,tph.is_split from tta_supplier ts,\n" +
			"                                                                                          tta_rel_supplier trs,\n" +
			"                                                                                          tta_proposal_header tph\n" +
			"                                                                                           where ts.formal_code = :vendorNbr\n" +
			"                                                                                           and ts.supplier_id = trs.rel_id\n" +
			"                                                                                           and trs.rel_supplier_code = tph.vendor_nbr\n" +
			"                                                                                           and tph.status = 'C'\n" +
			"                                                                                           and tph.proposal_year = :proposalYear      \n" +
			"                                                                                           \n" +
			"                                                                                           \n" +
			" )  v where 1=1 ";

	public static final String CONTRACT_SPLIT_PROPOSAL_VENDOR = "select * from tta_contract_split_proposal_v t where 1=1 ";

	public static final String CONTRACT_EDIT_PROPOSAL = "select * from tta_contract_edit_proposal_v t where  1=1 ";

	public static String getLastYearOrderNo(String vendorNbr,String proposalYear){
		//return  "select * from (select sum(TtaGetProposalLastYearVendorNbr('"+vendorNbr+"','"+proposalYear+"')) as LAST_YEAR_PROPOSAL_ID, 1 as VALUE  from  dual ) t "  ;
		return  "select TtaGetProposalLastYearVendorNbr('"+vendorNbr+"','"+proposalYear+"')  LAST_YEAR_PROPOSAL_IDS  from  dual  tph where rownum =1 "  ;
	}

	public static String getTtaBrandCount(Integer proposalId, String proposalYear) {
		return "select count(1) brand_count from tta_proposal_header tph,tta_brandpln_header tbh where \n" +
				"tph.proposal_id = tbh.proposal_id and tbh.proposal_id = "+proposalId+"  and tbh.year_code != '"+proposalYear+"'";
	}


	//public static final String QUERY_LAST_YEAR_ORDER_NO_LIST = "select  TtaGetProposalLastYearVendorNbr(:vendorNbr,:proposalYear)  LAST_YEAR_PROPOSAL_IDS  from  tta_proposal_header  tph where rownum =1 " ;
	//public static final String QUERY_LAST_YEAR_ORDER_NO_LIST = "select vendor_nbr from  tta_proposal_header  tph where rownum =1 " ;

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOrderNbr(String orderNbr) {
		this.orderNbr = orderNbr;
	}

	
	public String getOrderNbr() {
		return orderNbr;
	}

	public void setProposalNbr(String proposalNbr) {
		this.proposalNbr = proposalNbr;
	}

	
	public String getProposalNbr() {
		return proposalNbr;
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

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	
	public String getProposalYear() {
		return proposalYear;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	
	public Date getApproveDate() {
		return approveDate;
	}


	public void setVersionCode(BigDecimal versionCode) {
		this.versionCode = versionCode;
	}

	
	public BigDecimal getVersionCode() {
		return versionCode;
	}

	public void setIsTransdepart(String isTransdepart) {
		this.isTransdepart = isTransdepart;
	}

	
	public String getIsTransdepart() {
		return isTransdepart;
	}

	public void setNewPaymentMethod(String newPaymentMethod) {
		this.newPaymentMethod = newPaymentMethod;
	}

	
	public String getNewPaymentMethod() {
		return newPaymentMethod;
	}

	public BigDecimal getNewStoreQty() {
		return newStoreQty;
	}

	public void setNewStoreQty(BigDecimal newStoreQty) {
		this.newStoreQty = newStoreQty;
	}

	public BigDecimal getPlnAdjustScale() {
		return plnAdjustScale;
	}

	public void setPlnAdjustScale(BigDecimal plnAdjustScale) {
		this.plnAdjustScale = plnAdjustScale;
	}

	public void setIsPlnConfirm(String isPlnConfirm) {
		this.isPlnConfirm = isPlnConfirm;
	}

	
	public String getIsPlnConfirm() {
		return isPlnConfirm;
	}

	public void setIsTermsConfirm(String isTermsConfirm) {
		this.isTermsConfirm = isTermsConfirm;
	}

	
	public String getIsTermsConfirm() {
		return isTermsConfirm;
	}

	public void setIsDepartConfirm(String isDepartConfirm) {
		this.isDepartConfirm = isDepartConfirm;
	}

	
	public String getIsDepartConfirm() {
		return isDepartConfirm;
	}

	public void setIsNewConfirm(String isNewConfirm) {
		this.isNewConfirm = isNewConfirm;
	}

	
	public String getIsNewConfirm() {
		return isNewConfirm;
	}

	public void setIsQuestConfirm(String isQuestConfirm) {
		this.isQuestConfirm = isQuestConfirm;
	}

	
	public String getIsQuestConfirm() {
		return isQuestConfirm;
	}

	public void setIsCrossYear(String isCrossYear) {
		this.isCrossYear = isCrossYear;
	}

	
	public String getIsCrossYear() {
		return isCrossYear;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
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

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	
	public String getIsChange() {
		return isChange;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	
	public String getSourceCode() {
		return sourceCode;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getNewPaymentName() {
		return newPaymentName;
	}

	public void setNewPaymentName(String newPaymentName) {
		this.newPaymentName = newPaymentName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getNewOrExisting() {
		return newOrExisting;
	}

	public void setNewOrExisting(String newOrExisting) {
		this.newOrExisting = newOrExisting;
	}

	public String getDeptCode1() {
		return deptCode1;
	}

	public void setDeptCode1(String deptCode1) {
		this.deptCode1 = deptCode1;
	}

	public String getDeptDesc1() {
		return deptDesc1;
	}

	public void setDeptDesc1(String deptDesc1) {
		this.deptDesc1 = deptDesc1;
	}

	public String getDeptCode2() {
		return deptCode2;
	}

	public void setDeptCode2(String deptCode2) {
		this.deptCode2 = deptCode2;
	}

	public String getDeptDesc2() {
		return deptDesc2;
	}

	public void setDeptDesc2(String deptDesc2) {
		this.deptDesc2 = deptDesc2;
	}

	public String getDeptCode3() {
		return deptCode3;
	}

	public void setDeptCode3(String deptCode3) {
		this.deptCode3 = deptCode3;
	}

	public String getDeptDesc3() {
		return deptDesc3;
	}

	public void setDeptDesc3(String deptDesc3) {
		this.deptDesc3 = deptDesc3;
	}

	public String getDeptCode4() {
		return deptCode4;
	}

	public void setDeptCode4(String deptCode4) {
		this.deptCode4 = deptCode4;
	}

	public String getDeptDesc4() {
		return deptDesc4;
	}

	public void setDeptDesc4(String deptDesc4) {
		this.deptDesc4 = deptDesc4;
	}

	public String getDeptCode5() {
		return deptCode5;
	}

	public void setDeptCode5(String deptCode5) {
		this.deptCode5 = deptCode5;
	}

	public String getDeptDesc5() {
		return deptDesc5;
	}

	public void setDeptDesc5(String deptDesc5) {
		this.deptDesc5 = deptDesc5;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(String versionStatus) {
		this.versionStatus = versionStatus;
	}

	public Integer getOriginProposalId() {
		return originProposalId;
	}

	public void setOriginProposalId(Integer originProposalId) {
		this.originProposalId = originProposalId;
	}

	public String getMajorDeptDesc() {
		return majorDeptDesc;
	}

	public void setMajorDeptDesc(String majorDeptDesc) {
		this.majorDeptDesc = majorDeptDesc;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public Integer getMajorDeptId() {
		return majorDeptId;
	}

	public void setMajorDeptId(Integer majorDeptId) {
		this.majorDeptId = majorDeptId;
	}

	public String getMajorDeptCode() {
		return majorDeptCode;
	}

	public void setMajorDeptCode(String majorDeptCode) {
		this.majorDeptCode = majorDeptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public void setFcsPurchse(BigDecimal fcsPurchse) {
		this.fcsPurchse = fcsPurchse;
	}

	public BigDecimal getFcsPurchse() {
		return fcsPurchse;
	}

	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
	}

	public String getIsSkipApprove() {
		return isSkipApprove;
	}


	public String getInvoiceType() {
		return invoiceType;
	}


	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}


	public String getAgreementEdition() {
		return agreementEdition;
	}


	public void setAgreementEdition(String agreementEdition) {
		this.agreementEdition = agreementEdition;
	}

	public Integer getLastYearProposalId() {
		return lastYearProposalId;
	}

	public void setLastYearProposalId(Integer lastYearProposalId) {
		this.lastYearProposalId = lastYearProposalId;
	}

	public String getLastYearOrderNo() {
		return lastYearOrderNo;
	}

	public void setLastYearOrderNo(String lastYearOrderNo) {
		this.lastYearOrderNo = lastYearOrderNo;
	}

	public String getLastYearVendorCode() {
		return lastYearVendorCode;
	}

	public void setLastYearVendorCode(String lastYearVendorCode) {
		this.lastYearVendorCode = lastYearVendorCode;
	}

	public String getLastYearVendorName() {
		return lastYearVendorName;
	}

	public void setLastYearVendorName(String lastYearVendorName) {
		this.lastYearVendorName = lastYearVendorName;
	}

	public String getLastYearProposalIds() {
		return lastYearProposalIds;
	}

	public void setLastYearProposalIds(String lastYearProposalIds) {
		this.lastYearProposalIds = lastYearProposalIds;
	}

	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public void setWaitTime(BigDecimal waitTime) {
		this.waitTime = waitTime;
	}

	public BigDecimal getWaitTime() {
		return waitTime;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getSaleTypeName() {
		return saleTypeName;
	}

	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}

	public String getOrderNbrVersion() {
		return orderNbrVersion;
	}

	public void setOrderNbrVersion(String orderNbrVersion) {
		this.orderNbrVersion = orderNbrVersion;
	}

	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	public void setStartUserId(Integer startUserId) {
		this.startUserId = startUserId;
	}

	public Integer getStartUserId() {
		return startUserId;
	}

	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
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

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public String getIsHiddenQuestion() {
		return isHiddenQuestion;
	}

	public void setIsHiddenQuestion(String isHiddenQuestion) {
		this.isHiddenQuestion = isHiddenQuestion;
	}

	public String getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}
}
