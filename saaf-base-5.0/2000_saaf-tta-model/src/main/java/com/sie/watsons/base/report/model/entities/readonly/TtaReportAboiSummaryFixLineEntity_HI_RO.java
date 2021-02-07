package com.sie.watsons.base.report.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaReportAboiSummaryFixLineEntity_HI_RO Entity Object
 * Fri May 22 17:28:29 CST 2020  Auto Generate
 */

public class TtaReportAboiSummaryFixLineEntity_HI_RO {
    private Integer aboiFixId;
	private Integer proposalId;
	private Integer reportYear;
	private BigDecimal purchase;
	private BigDecimal fcsInAmount;
	private BigDecimal acInAmount;
    private String remake;
	private String val;
	private String acVal;
    private String actionCode;
    private String reasonCode;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String groupCode;
    private String groupDesc;
    private String deptCode;
    private String deptDesc;
    private String brandCn;
    private String brandEn;
    private String termsCn;
	private String vendorNbr;
	@JSONField(format="yyyy-MM-dd")
	private Date beginDate;
	@JSONField(format="yyyy-MM-dd")
	private Date endDate;
	private String orderNo;
    private Integer operatorUserId;
    private BigDecimal  netpurchase;

	public static String getTermsCn(String year){
		return "               select \n" +
				"      tct.clause_cn terms_cn,\n" +
				"      tct.payment_method,\n" +
				"      tct.order_no\n" +
				"      from \n" +
				"      tta_clause_tree tct\n" +
				"      join tta_terms_frame_header ttfh on tct.team_framework_id = ttfh.team_framework_id\n" +
				"      and ttfh.year = "+ year +" and nvl(tct.p_code,'0') = '0' and ttfh.dept_code = '05'\n" +
				"      join (  select blv.lookup_code,blv.description from base_lookup_values blv \n" +
				"                   where blv.lookup_type = 'CLAUSE_INP'\n" +
				"                   and blv.description is not null \n" +
				"                ) blvc on tct.payment_method = blvc.lookup_code\n" +
				"      \n" +
				"      where exists (SELECT 1 FROM tta_oi_report_field_mapping tor \n" +
				"                    where tor.business_type = 8 \n" +
				"                    and tor.account_name_cn is not null \n" +
				"                    and  tor.trade_year = " + year +"\n" +
				"                    and tct.clause_cn = tor.account_name_cn )\n" +
				"            and tct.payment_method = 'ABOI'\n" +
				"      order by blvc.description asc,tct.order_no asc"  ;
	}

	public static final String QUERY = " select \n" +
			"      tras.*\n" +
			"      ,fcs.val\n" +
			"      ,ac.ac_val \n" +
			"     ,nvl(ac_p.netpurchase,0) netpurchase\n" +
			"      from tta_report_aboi_summary_fix_line tras\n" +
			"      left join (  select tra.report_year ,tra.vendor_nbr,tra.group_code,tra.dept_code,tra.brand_cn,tra.brand_en\n" +
			"                   ,listagg(tra.aboi_id||'@'||round(nvl(tra.fee_intas,0)),'|') within group(order by  tra.order_no asc) val\n" +
			"                   from  tta_report_aboi_summary  tra where tra.report_year =:year \n" +
			"                   group by tra.report_year ,tra.vendor_nbr,tra.group_code,tra.dept_code,tra.brand_cn,tra.brand_en) fcs\n" +
			"                                                                      on tras.report_year = fcs.report_year\n" +
			"                                                                      and  tras.vendor_nbr =fcs.vendor_nbr\n" +
			"                                                                      and  tras.group_code = fcs.group_code\n" +
			"                                                                      and  tras.dept_code = fcs.dept_code\n" +
			"                                                                      and  tras.brand_cn = fcs.brand_cn\n" +
			"                                                                      and  tras.brand_en = fcs.brand_en\n" +
			"          \n" +
			"      left join (  select tta.year ,tta.vendor_nbr,tta.group_code,tta.dept_code,tta.brand_cn,tta.brand_en\n" +
			"                           ,listagg('N' || tta.no||'@'||round(nvl(tta.amount,0)),'|') within group(order by  tta.no asc) ac_val\n" +
			"                   from  tta_terms_amount tta where tta.oi_type = 'ABOI' and  tta.year = :year\n" +
			"                   group by  tta.year ,tta.vendor_nbr,tta.group_code,tta.dept_code,tta.brand_cn,tta.brand_en) ac\n" +
			"                                                                      on tras.report_year = ac.year\n" +
			"                                                                      and  tras.vendor_nbr =ac.vendor_nbr\n" +
			"                                                                      and  tras.group_code = ac.group_code\n" +
			"                                                                      and  tras.dept_code = ac.dept_code\n" +
			"                                                                      and  tras.brand_cn = ac.brand_cn\n" +
			"                                                                      and  tras.brand_en = ac.brand_en   \n" +
			"      left join (  SELECT tph.vendor_nbr,\n" +
			"substr(top.account_month,1,4) account_month,\n" +
			"top.group_code,\n" +
			"top.dept_code,\n" +
			"top.brand_cn,\n" +
			"top.brand_en,\n" +
			"round(sum(nvl(top.netpurchase,0))) netpurchase \n" +
			"FROM            tta_proposal_header tph,\n" +
			"                tta_supplier_major_v tsm,\n" +
			"                tta_oi_po_scene_sum top\n" +
			"                where \n" +
			"                tph.vendor_nbr = tsm.MAJOR_SUPPLIER_CODE\n" +
			"                and tsm.SUPPLIER_CODE = top.split_supplier_code\n" +
			"                and tph.status != 'D' and nvl(tph.version_status,'1') = '1'\n" +
			"                and top.group_code is not null\n" +
			"                group by tph.vendor_nbr,substr(top.account_month,1,4),top.group_code,top.dept_code,top.brand_cn,top.brand_en) ac_p\n" +
			"                                                                      on tras.report_year = ac_p.account_month\n" +
			"                                                                      and  tras.vendor_nbr =ac_p.vendor_nbr\n" +
			"                                                                      and  tras.group_code = ac_p.group_code\n" +
			"                                                                      and  tras.dept_code = ac_p.dept_code\n" +
			"                                                                      and  tras.brand_cn = ac_p.brand_cn\n" +
			"                                                                      and  tras.brand_en = ac_p.brand_en   \n" +
			" where 1=1 and tras.report_year =:year" ;

	public static final String QUERY_BRAND = "SELECT * FROM (\n" +
			"select \n" +
			"tras.REPORT_YEAR ,tras.group_code,max(tras.group_desc) group_desc,tras.dept_code,tras.brand_cn,tras.brand_en,\n" +
			"round(nvl(sum(tras.PURCHASE),0))  PURCHASE,\n" +
			"round(nvl(sum(fcs.fcs_in_amount),0))  fcs_in_amount,\n" +
			"round(nvl(sum(ac.ac_in_amount),0))  ac_in_amount,\n" +
			"nvl(sum(ac_p.netpurchase),0)  netpurchase,\n" +
			"listagg(tras.remake,'|') within group(order by  tras.aboi_fix_id asc) remake,\n" +
			"listagg(tras.action_code,'|') within group(order by  tras.aboi_fix_id asc) action_code,\n" +
			"listagg(tras.reason_code,'|') within group(order by  tras.aboi_fix_id asc) reason_code\n" +
			"      from tta_report_aboi_summary_fix_line tras\n" +
			"      left join (  select tra.report_year ,tra.vendor_nbr,tra.group_code,tra.dept_code,tra.brand_cn,tra.brand_en\n" +
			"                   ,sum(tra.fee_intas) fcs_in_amount\n" +
			"                   from  tta_report_aboi_summary  tra where tra.report_year =:year\n" +
			"                   group by tra.report_year ,tra.vendor_nbr,tra.group_code,tra.dept_code,tra.brand_cn,tra.brand_en) fcs\n" +
			"                                                                      on tras.report_year = fcs.report_year\n" +
			"                                                                      and  tras.vendor_nbr =fcs.vendor_nbr\n" +
			"                                                                      and  tras.group_code = fcs.group_code\n" +
			"                                                                      and  tras.dept_code = fcs.dept_code\n" +
			"                                                                      and  tras.brand_cn = fcs.brand_cn\n" +
			"                                                                      and  tras.brand_en = fcs.brand_en\n" +
			"          \n" +
			"      left join (  select tta.year ,tta.vendor_nbr,tta.group_code,tta.dept_code,tta.brand_cn,tta.brand_en\n" +
			"                           ,sum(tta.amount) ac_in_amount\n" +
			"                   from  tta_terms_amount tta where tta.oi_type = 'ABOI'  and  tta.year = :year\n" +
			"                   group by  tta.year ,tta.vendor_nbr,tta.group_code,tta.dept_code,tta.brand_cn,tta.brand_en) ac\n" +
			"                                                                      on tras.report_year = ac.year\n" +
			"                                                                      and  tras.vendor_nbr =ac.vendor_nbr\n" +
			"                                                                      and  tras.group_code = ac.group_code\n" +
			"                                                                      and  tras.dept_code = ac.dept_code\n" +
			"                                                                      and  tras.brand_cn = ac.brand_cn\n" +
			"                                                                      and  tras.brand_en = ac.brand_en   \n" +
			"      left join (  SELECT tph.vendor_nbr,\n" +
			"substr(top.account_month,1,4) account_month,\n" +
			"top.group_code,\n" +
			"top.dept_code,\n" +
			"top.brand_cn,\n" +
			"top.brand_en,\n" +
			"round(sum(nvl(top.netpurchase,0))) netpurchase \n" +
			"FROM            tta_proposal_header tph,\n" +
			"                tta_supplier_major_v tsm,\n" +
			"                tta_oi_po_scene_sum top\n" +
			"                where \n" +
			"                tph.vendor_nbr = tsm.MAJOR_SUPPLIER_CODE\n" +
			"                and tsm.SUPPLIER_CODE = top.split_supplier_code\n" +
			"                and tph.status != 'D' and nvl(tph.version_status,'1') = '1'\n" +
			"                and top.group_code is not null\n" +
			"                group by tph.vendor_nbr,substr(top.account_month,1,4),top.group_code,top.dept_code,top.brand_cn,top.brand_en) ac_p\n" +
			"                                                                      on tras.report_year = ac_p.account_month\n" +
			"                                                                      and  tras.vendor_nbr =ac_p.vendor_nbr\n" +
			"                                                                      and  tras.group_code = ac_p.group_code\n" +
			"                                                                      and  tras.dept_code = ac_p.dept_code\n" +
			"                                                                      and  tras.brand_cn = ac_p.brand_cn\n" +
			"                                                                      and  tras.brand_en = ac_p.brand_en   \n" +
			" where 1=1 and tras.report_year =:year   group  by \n" +
			" \n" +
			" tras.REPORT_YEAR ,tras.group_code,tras.dept_code,tras.brand_cn,tras.brand_en\n" +
			" \n" +
			" ) v  where 1=1 " ;

	public static final String QUERY_GROUP = "SELECT * FROM (\n" +
			"select \n" +
			"tras.REPORT_YEAR ,tras.group_code,max(tras.group_desc) group_desc,\n" +
			"round(nvl(sum(fcs.fcs_in_amount),0))  fcs_in_amount,\n" +
			"round(nvl(sum(ac.ac_in_amount),0))  ac_in_amount,\n" +
			"listagg(tras.action_code,'|') within group(order by  tras.aboi_fix_id asc) action_code\n" +
			"      from tta_report_aboi_summary_fix_line tras\n" +
			"      left join (  select tra.report_year ,tra.vendor_nbr,tra.group_code,tra.dept_code,tra.brand_cn,tra.brand_en\n" +
			"                   ,sum(tra.fee_intas) fcs_in_amount\n" +
			"                   from  tta_report_aboi_summary  tra  where tra.report_year =:year\n" +
			"                   group by tra.report_year ,tra.vendor_nbr,tra.group_code,tra.dept_code,tra.brand_cn,tra.brand_en) fcs\n" +
			"                                                                      on tras.report_year = fcs.report_year\n" +
			"                                                                      and  tras.vendor_nbr =fcs.vendor_nbr\n" +
			"                                                                      and  tras.group_code = fcs.group_code\n" +
			"                                                                      and  tras.dept_code = fcs.dept_code\n" +
			"                                                                      and  tras.brand_cn = fcs.brand_cn\n" +
			"                                                                      and  tras.brand_en = fcs.brand_en\n" +
			"          \n" +
			"      left join (  select tta.year ,tta.vendor_nbr,tta.group_code,tta.dept_code,tta.brand_cn,tta.brand_en\n" +
			"                           ,sum(tta.amount) ac_in_amount\n" +
			"                   from  tta_terms_amount tta where tta.oi_type = 'ABOI' and tta.year = :year\n" +
			"                   group by  tta.year ,tta.vendor_nbr,tta.group_code,tta.dept_code,tta.brand_cn,tta.brand_en) ac\n" +
			"                                                                      on tras.report_year = ac.year\n" +
			"                                                                      and  tras.vendor_nbr =ac.vendor_nbr\n" +
			"                                                                      and  tras.group_code = ac.group_code\n" +
			"                                                                      and  tras.dept_code = ac.dept_code\n" +
			"                                                                      and  tras.brand_cn = ac.brand_cn\n" +
			"                                                                      and  tras.brand_en = ac.brand_en   \n" +
			" where 1=1 and tras.report_year =:year    group  by \n" +
			" \n" +
			" tras.REPORT_YEAR ,tras.group_code\n" +
			" \n" +
			" ) v  where 1=1 " ;

	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	public static String getInsertReportBaseTtaProposalStatus(){
		return "update tta_proposal_header tph set tph.is_report_aboi_create  = 'S' where tph.is_report_aboi_create is null  and tph.status = 'C' and nvl(tph.version_status,'1') = '1'"  ;
	}

	public static String getInsertReportBaseTtaProposalStatusEnd(){
		return "update tta_proposal_header tph set tph.is_report_aboi_create  = 'Y' where tph.is_report_aboi_create = 'S'"  ;
	}

	public static String getInsertReportBase(String date,Integer userId){
		return "insert into \n" +
				"\n" +
				"TTA_REPORT_ABOI_SUMMARY_FIX_LINE tra\n" +
				"\n" +
				"\n" +
				"(\n" +
				"tra.aboi_fix_id,\n" +
				"        tra.creation_date,\n" +
				"        tra.created_by,\n" +
				"        tra.last_updated_by,\n" +
				"        tra.last_update_date,\n" +
				"        tra.last_update_login,\n" +
				"        tra.version_num,\n" +
				"        tra.group_code,\n" +
				"        tra.group_desc,\n" +
				"        tra.dept_code,\n" +
				"        tra.dept_desc,\n" +
				"        tra.brand_cn,\n" +
				"        tra.brand_en,\n" +
				"        tra.proposal_id,\n" +
				"        tra.vendor_nbr,\n" +
				"        tra.begin_date,\n" +
				"        tra.end_date,\n" +
				"        report_year,\n" +
				"        PURCHASE\n" +
				")\n" +
				"\n" +
				"select \n" +
				"SEQ_TTA_REPORT_ABOI_SUMMARY_FIX_LINE.NEXTVAL\n" +
				",to_date('"+date +"','yyyy-mm-dd hh24:mi:ss')\n" +
				","+userId+"\n" +
				","+userId+"\n" +
				",sysdate\n" +
				","+userId+"\n" +
				",0\n" +
				",tbl.group_code\n" +
				",tbl.group_desc  --group \n" +
				",tbl.dept_code\n" +
				",tbl.dept_desc\n" +
				",tbl.brand_cn\n" +
				",tbl.brand_en\n" +
				",tph.proposal_id\n" +
				",tph.vendor_nbr\n" +
				",tph.begin_date  -- 合同开始日期\n" +
				",tph.end_date  --合同结束日期\n" +
				",TPH.PROPOSAL_YEAR\n" +
				",tbl.fcs_purchase\n" +
				"from \n" +
				"\n" +
				"tta_proposal_header tph \n" +
				" join tta_brandpln_line tbl \n" +
				"\n" +
				"on tph.proposal_id = tbl.proposal_id\n" +
				"where \n" +
				"  tph.is_report_aboi_create = 'S'"  ;
	}
	public static String getInsertReportBaseInfoTermsUpdate(String date,Integer userId){
		return "          update TTA_REPORT_ABOI_SUMMARY traas set traas.aboi_fix_id = (SELECT tras.aboi_fix_id FROM TTA_REPORT_ABOI_SUMMARY_FIX_LINE tras where tras.report_year = traas.report_year\n" +
				"          and tras.vendor_nbr = traas.vendor_nbr\n" +
				"          and nvl(tras.group_code,'-1') = nvl(traas.group_code,'-1')\n" +
				"          and nvl(tras.dept_code,'-1') = nvl(traas.dept_code,'-1')\n" +
				"          and nvl(tras.brand_cn,'-1') = nvl(traas.brand_cn,'-1')\n" +
				"          and nvl(tras.brand_en,'-1') = nvl(traas.brand_en,'-1') and rownum =1) where traas.aboi_fix_id is null ";
	}
	public static String getInsertReportBaseInfoTerms(String date,Integer userId){
		return "      insert into TTA_REPORT_ABOI_SUMMARY tra\n" +
				"      (      \n" +
				"             tra.aboi_id,      \n" +
				"             tra.report_year,\n" +
				"             tra.vendor_nbr,\n" +
				"             tra.name,\n" +
				"             tra.fee_notax,\n" +
				"             tra.fee_intas,\n" +
				"             tra.creation_date,\n" +
				"             tra.created_by,\n" +
				"             tra.last_updated_by,\n" +
				"             tra.last_update_date,\n" +
				"             tra.last_update_login,\n" +
				"             tra.version_num,\n" +
				"             tra.group_code,\n" +
				"             tra.group_desc,\n" +
				"             tra.dept_code,\n" +
				"             tra.dept_desc,\n" +
				"             tra.brand_cn,\n" +
				"             tra.brand_en,\n" +
				"             tra.proposal_id,\n" +
				"             tra.purchase,\n" +
				"             tra.order_no\n" +
				"             )\n" +
				"             \n" +
				"select \n" +
				"SEQ_TTA_REPORT_ABOI_SUMMARY.NEXTVAL\n" +
				",TPH.PROPOSAL_YEAR\n" +
				",tph.vendor_nbr\n" +
				",rtt.terms_cn\n" +
				",0\n" +
				",round(nvl(tal.current_year_tta_ontop,0) *(nvl(tbl.fcs_sales,0)/decode(nvl(tbh.fcs_sales,0),0,1,tbh.fcs_sales)) )\n" +
				",to_date('"+date +"','yyyy-mm-dd hh24:mi:ss')\n" +
				","+userId+"\n" +
				","+userId+"\n" +
				",sysdate\n" +
				","+userId+"\n" +
				",0\n" +
				",tbl.group_code\n" +
				",tbl.group_desc  --group \n" +
				",tbl.dept_code\n" +
				",tbl.dept_desc\n" +
				",tbl.brand_cn\n" +
				",tbl.brand_en\n" +
				",tph.proposal_id\n" +
				",tbl.fcs_purchase\n" +
				",rtt.order_no\n" +
				"from \n" +
				"\n" +
				"tta_proposal_header tph \n" +
				"left join tta_brandpln_line tbl\n" +
				"on tph.proposal_id = tbl.proposal_id\n" +
				"left join tta_brandpln_header tbh \n" +
				"on tbh.proposal_id = tph.proposal_id\n" +
				"left join (SELECT rt.terms_cn,rt.proposal_id,rt.order_no FROM  TTA_CONTRACT_LINE rt where RT.OI_TYPE = 'ABOI' and rt.contract_h_id is null ) rtt on rtt.proposal_id = tph.proposal_id\n" +
				"left join  (SELECT tal.proposal_id,\n" +
				" tal.proposal_year,\n" +
				" tal.order_no,\n" +
				" tal.terms_comparison,\n" +
				" tal.terms_cn,\n" +
				"  tal.current_year_tta_ontop FROM  tta_analysis_line tal \n" +
				"  where tal.oi_type = 'ABOI'\n" +
				"  and tal.contract_l_id is not null ) tal\n" +
				"  on tph.proposal_id = tal.proposal_id and tal.terms_cn = rtt.terms_cn \n" +
				"where tph.status = 'C' and nvl(tph.version_status,'1') = '1'\n" +
				" and tph.is_report_aboi_create = 'S'";
	}
	public static String getInsertReportBaseInfoTwo(String date,Integer userId,String year){
		return "insert into \n" +
				"\n" +
				"TTA_REPORT_ABOI_SUMMARY_FIX_LINE tra\n" +
				"\n" +
				"\n" +
				"(\n" +
				"tra.aboi_fix_id,\n" +
				"        tra.creation_date,\n" +
				"        tra.created_by,\n" +
				"        tra.last_updated_by,\n" +
				"        tra.last_update_date,\n" +
				"        tra.last_update_login,\n" +
				"        tra.version_num,\n" +
				"        tra.group_code,\n" +
				"        tra.group_desc,\n" +
				"        tra.dept_code,\n" +
				"        tra.dept_desc,\n" +
				"        tra.brand_cn,\n" +
				"        tra.brand_en,\n" +
				"        tra.proposal_id,\n" +
				"        tra.vendor_nbr,\n" +
				"        tra.begin_date,\n" +
				"        tra.end_date,\n" +
				"        report_year\n" +
				")\n" +
				"SELECT \n" +
				"SEQ_TTA_REPORT_ABOI_SUMMARY_FIX_LINE.NEXTVAL\n" +
				",to_date('"+date +"','yyyy-mm-dd hh24:mi:ss')\n" +
				","+userId+"\n" +
				","+userId+"\n" +
				",sysdate\n" +
				","+userId+"\n" +
				",0\n" +
				",tta.group_code\n" +
				",tta.group_desc  --group \n" +
				",tta.dept_code\n" +
				",tta.dept_desc\n" +
				",tta.brand_cn\n" +
				",tta.brand_en\n" +
				",tph.proposal_id\n" +
				",tta.vendor_nbr\n" +
				",tph.begin_date  -- 合同开始日期\n" +
				",tph.end_date  --合同结束日期\n" +
				",tta.year\n" +
				"FROM  (SELECT tta.year ,tta.vendor_nbr,tta.group_code,max(group_desc) group_desc,tta.dept_code,max(dept_desc) dept_desc,tta.brand_cn,tta.brand_en\n" +
				" FROM TTA_TERMS_AMOUNT tta \n" +
				" group by tta.year ,\n" +
				" tta.vendor_nbr,\n" +
				" tta.group_code,\n" +
				" tta.dept_code,\n" +
				" tta.brand_cn,\n" +
				" tta.brand_en ) tta\n" +
				"       join tta_proposal_header tph  on tta.year = tph.proposal_year and tta.vendor_nbr = tph.vendor_nbr\n" +
				"\n" +
				"where not  exists (SELECT 1 FROM TTA_REPORT_ABOI_SUMMARY_FIX_LINE traa \n" +
				"           where substr(traa.report_year,1,4) = substr(tta.year,1,4)\n" +
				"                 and traa.group_code =tta.group_code\n" +
				"                 and traa.dept_code = tta.dept_code\n" +
				"                 and traa.brand_cn = tta.brand_cn\n" +
				"                 and traa.brand_en = tta.brand_en\n" +
				"                 and traa.vendor_nbr = tta.vendor_nbr)\n" +
				"      and tph.status = 'C' and nvl(tph.version_status,'1') = '1'"   ;
	}

	public void setAboiFixId(Integer aboiFixId) {
		this.aboiFixId = aboiFixId;
	}

	
	public Integer getAboiFixId() {
		return aboiFixId;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	
	public String getRemake() {
		return remake;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	
	public String getActionCode() {
		return actionCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getTermsCn() {
		return termsCn;
	}

	public void setTermsCn(String termsCn) {
		this.termsCn = termsCn;
	}

	public String getReasonCode() {
		return reasonCode;
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

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	
	public String getBrandEn() {
		return brandEn;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getReportYear() {
		return reportYear;
	}

	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getAcVal() {
		return acVal;
	}

	public void setAcVal(String acVal) {
		this.acVal = acVal;
	}

	public BigDecimal getFcsInAmount() {
		return fcsInAmount;
	}

	public void setFcsInAmount(BigDecimal fcsInAmount) {
		this.fcsInAmount = fcsInAmount;
	}

	public BigDecimal getAcInAmount() {
		return acInAmount;
	}

	public void setAcInAmount(BigDecimal acInAmount) {
		this.acInAmount = acInAmount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getNetpurchase() {
		return netpurchase;
	}

	public void setNetpurchase(BigDecimal netpurchase) {
		this.netpurchase = netpurchase;
	}
}
