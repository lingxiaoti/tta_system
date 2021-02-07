package com.sie.watsons.base.report.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaNppNewProductReportEntity_HI_RO Entity Object
 * Wed Nov 20 14:40:10 CST 2019  Auto Generate
 */

public class TtaNppNewProductReportEntity_HI_RO {

	public static final String  NPP_QUERY = "select \n" +
			"       tcc.NPP_ID,\n" +
			"       tcc.BATCH_CODE,\n" +
			"       TRPH.BATCH_CODE processCode,\n" +
			"       TCC.PROCESS_ID  processReId,\n" +
			"       lookup2.meaning processStatusName,\n" +
			"		trph.status as process_status,\n" +
			"       tcc.MONTH,\n" +
			"       tcc.STORE_COUN,\n" +
			"       tcc.GROUP_DESC,\n" +
			"       tcc.DEPT_DESC,\n" +
			"       tcc.ITEM_CODE,\n" +
			"       tcc.ITEM_DESC_CN,\n" +
			"       tcc.BRAND_CN,\n" +
			"       tcc.BRAND_EN,\n" +
			"       tcc.VENDOR_NBR,\n" +
			"       tcc.VENDOR_NAME,\n" +
			"       tcc.prior_vendor_code,\n" +
			"       tcc.prior_vendor_name,\n" +
			"       tcc.ACTIVITY,\n" +
			"       tcc.CTW,\n" +
			"       tcc.EB,\n" +
			"       tcc.CONTRACT_TERMS,\n" +
			"       tcc.COLLECT_WAY,\n" +
			"       lookup1.meaning collectWayName,\n" +
			"       tcc.CHARGE_STANDARDS,\n" +
			"       tcc.CHARGE_UNIT,\n" +
			"       tcc.RECEIVE_AMOUNT,\n" +
			"       tcc.RECEIVE_AMOUNT_ADD,\n" +
			"       tcc.UNCONFIRM_AMOUNT,\n" +
			"       tcc.UNCONFIRM_AMOUNT_A,\n" +
			"       tcc.adjust_amount,--调整实收金额\n" +
			"       tcc.adjust_receive_amount,--调整应收金额(含加成)\n" +
			"       tcc.no_adjust_receive_amount,--调整应收金额(不含加成)\n" +
			"       tcc.adjust_by,--调整人\n" +
			"       tcc.DIFFERENCE,\n" +
			"       tcc.COLLECT_SELECT,\n" +
			"       tcc.SOURCE_FILEPATH,\n" +
			"       ba.file_id,\n" +
			"       ba.source_file_name source_filename,\n" +
			"       tcc.PURCHASE_ACT,\n" +
			"       tcc.EXEMPTION_REASON,\n" +
			"       tcc.EXEMPTION_REASON2,\n" +
			"       tcc.DEBIT_ORDER_CODE,\n" +
			"       tcc.ABOI_RECEIPTS,\n" +
			"       tcc.TRANSFER_IN_PERSON,\n" +
			"       tcc.REMARK,\n" +
			"       tcc.STATUS,\n" +
			"       tcc.PARENT_ID,\n" +
			"       tcc.PARENT_VENDOR_CODE,\n" +
			"       tcc.PARENT_VENDOR_NAME,\n" +
			"       tcc.CREATION_DATE,\n" +
			"       tcc.CREATED_BY,\n" +
			"       tcc.LAST_UPDATED_BY,\n" +
			"       tcc.LAST_UPDATE_DATE,\n" +
			"       tcc.LAST_UPDATE_LOGIN,\n" +
			"       tcc.TRANSFER_OUT_PERSON,\n" +
			"       tcc.TRANSFER_LAST_PERSON,\n" +
			"       tcc.TRANSFER_IN_DATE,\n" +
			"       tcc.TRANSFER_OUT_DATE,\n" +
			"       tcc.TRANSFER_LAST_DATE,\n" +
			"       tcc.CHARGE_UNIT_NAME,\n" +
			"       tcc.ORIGINAL_BEFORE_AMOUNT,\n" +
			"       tcc.ADDITION_RATE,\n" +
			"       tcc.GROUP_CODE,\n" +
			"       tcc.DEPT_CODE,\n" +
			"       tcc.CHARGE_METHOD,--收费方式\n" +
			"       tcc.ANNUAL_MONTH_N,\n" +
			"       tcc.MONTH_N,\n" +
			"       tcc.IS_CREATE,\n" +
			"       bu.user_full_name      created_by_name,\n" +
			"       bu_last.user_full_name last_updated_by_name,\n" +
			"       bu_in.user_full_name   transfer_in_person_name,\n" +
			"       bu_out.user_full_name  transfer_out_person_name,\n" +
			"       bu_t_last.user_full_name   transfer_last_person_name,\n" +
			"       bu_ad.user_full_name adjustByName,\n" +
			"       trh.id,\n" +
			"       trh.status  headerStatus,\n" +
			"       lookup5.meaning exemptionReason2Name,\n" +
			"       lookup4.meaning exemptionReasonName,\n" +
			"       lookup3.meaning purchaseName,\n" +
			"       tcc.affirm_tot_store_count,\n" +
			"       tcc.add_receive_amount,\n" +
			"       process.current_approval_user as currentUserName,\n" +
			"       process.start_user_name as submitUserName\n" +
			"  from tta_npp_new_product_report tcc\n" +
			"  left join tta_report_header trh\n" +
			"    on tcc.batch_code = trh.batch_id\n" +
			"  left join base_attachment ba\n" +
			"    on ba.function_id = 'tta_npp_checking_report'\n" +
			"   and ba.business_id = tcc.npp_id\n" +
			"   and nvl(ba.delete_flag, 0) = 0\n" +
			"  left join base_users bu\n" +
			"    on tcc.created_by = bu.user_id\n" +
			"  left join base_users bu_last\n" +
			"    on tcc.last_updated_by = bu_last.user_id\n" +
			"  left join base_users bu_in\n" +
			"    on tcc.transfer_in_person = bu_in.user_id\n" +
			"  left join base_users bu_out\n" +
			"    on tcc.transfer_out_person = bu_out.user_id\n" +
			"  left join base_users bu_t_last\n" +
			"    on tcc.transfer_last_person = bu_t_last.user_id\n" +
			"  left join base_users bu_ad\n" +
			"    on tcc.adjust_By = bu_ad.user_id\n" +
			"   LEFT JOIN TTA_REPORT_PROCESS_HEADER TRPH ON tcc.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID \n" +
			"  left join \n" +
			"  (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='NEW_PAYMENT' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup1  on tcc.collect_way = lookup1.LOOKUP_CODE \n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type in('TTA_NPP_ACTION','TTA_DM_ADJ_ACTION') and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on tcc.purchase_act = lookup3.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_NPP_REASON' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup4  on tcc.exemption_Reason = lookup4.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_NPP_REASON1' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup5  on tcc.exemption_Reason2 = lookup5.lookup_code\n" +
			"left join ( select a.business_key,\n" +
			"       nvl(bp.person_name_en,bp.person_name_cn) as current_approval_user,\n" +
			"       b.assignee_,\n" +
			"       --bu.user_full_name as start_user_name\n" +
			"       nvl(bpu.person_name_en,bpu.person_name_cn) as start_user_name\n" +
			"  from act_bpm_list a\n" +
			" left join act_ru_task b\n" +
			"    on a.proc_inst_id = b.proc_inst_id_\n" +
			"   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
			"   left join base_person bp\n" +
			"   on bp.employee_number = b.assignee_\n" +
			"   left join base_users bu \n" +
			"   on bu.user_id = a.created_by \n" +
			"	left join base_person bpu on bu.person_id = bpu.person_id\n" +
			"  where a.proc_def_key = 'TTA_ACTIVITY.-999') process \n" +
			"  on tcc.process_id = process.business_key  \n" +
			" where 1 = 1 and tcc.status = 1 ";

	public static final String NPP_QUERY_NO_BIC = "select tcc.NPP_ID,\n" +
			"       tcc.BATCH_CODE,\n" +
			"       TRPH.BATCH_CODE processCode,\n" +
			"       lookup2.meaning processStatusName,\n" +
			"		trph.status as process_status,\n" +
			"       tcc.MONTH,\n" +
			"       tcc.STORE_COUN,\n" +
			"       tcc.GROUP_DESC,\n" +
			"       tcc.DEPT_DESC,\n" +
			"       tcc.ITEM_CODE,\n" +
			"       tcc.ITEM_DESC_CN,\n" +
			"       tcc.BRAND_CN,\n" +
			"       tcc.BRAND_EN,\n" +
			"       tcc.VENDOR_NBR,\n" +
			"       tcc.VENDOR_NAME,\n" +
			"       tcc.prior_vendor_code,\n" +
			"       tcc.prior_vendor_name,\n" +
			"       tcc.ACTIVITY,\n" +
			"       tcc.CTW,\n" +
			"       tcc.EB,\n" +
			"       tcc.CONTRACT_TERMS,\n" +
			"       tcc.COLLECT_WAY,\n" +
			"       lookup1.meaning collectWayName,\n" +
			"       tcc.CHARGE_STANDARDS,\n" +
			"       tcc.CHARGE_UNIT,\n" +
			"       tcc.RECEIVE_AMOUNT,\n" +
			"       tcc.RECEIVE_AMOUNT_ADD,\n" +
			"       tcc.UNCONFIRM_AMOUNT,\n" +
			"       tcc.UNCONFIRM_AMOUNT_A,\n" +
			"       tcc.adjust_amount,--调整实收金额\n" +
			"       tcc.adjust_receive_amount,--调整应收金额(含加成)\n" +
			"       tcc.no_adjust_receive_amount,--调整应收金额(不含加成)\n" +
			"       tcc.adjust_by,--调整人\n" +
			"       tcc.DIFFERENCE,\n" +
			"       tcc.COLLECT_SELECT,\n" +
			"       tcc.SOURCE_FILEPATH,\n" +
			"       ba.file_id,\n" +
			"       ba.source_file_name source_filename,\n" +
			"       tcc.PURCHASE_ACT,\n" +
			"       tcc.EXEMPTION_REASON,\n" +
			"       tcc.EXEMPTION_REASON2,\n" +
			"       tcc.DEBIT_ORDER_CODE,\n" +
			"       tcc.ABOI_RECEIPTS,\n" +
			"       tcc.TRANSFER_IN_PERSON,\n" +
			"       tcc.REMARK,\n" +
			"       tcc.STATUS,\n" +
			"       tcc.PARENT_ID,\n" +
			"       tcc.PARENT_VENDOR_CODE,\n" +
			"       tcc.PARENT_VENDOR_NAME,\n" +
			"       tcc.CREATION_DATE,\n" +
			"       tcc.CREATED_BY,\n" +
			"       tcc.LAST_UPDATED_BY,\n" +
			"       tcc.LAST_UPDATE_DATE,\n" +
			"       tcc.LAST_UPDATE_LOGIN,\n" +
			"       tcc.TRANSFER_OUT_PERSON,\n" +
			"       tcc.TRANSFER_LAST_PERSON,\n" +
			"       tcc.TRANSFER_IN_DATE,\n" +
			"       tcc.TRANSFER_OUT_DATE,\n" +
			"       tcc.TRANSFER_LAST_DATE,\n" +
			"       tcc.CHARGE_UNIT_NAME,\n" +
			"       tcc.ORIGINAL_BEFORE_AMOUNT,\n" +
			"       tcc.ADDITION_RATE,\n" +
			"       tcc.GROUP_CODE,\n" +
			"       tcc.DEPT_CODE,\n" +
			"       tcc.CHARGE_METHOD,--收费方式\n" +
			"       tcc.ANNUAL_MONTH_N,\n" +
			"       tcc.MONTH_N,\n" +
			"       tcc.IS_CREATE,\n" +
			"       tcc.PROCESS_ID processReId,\n" +
			"       bu.user_full_name      created_by_name,\n" +
			"       bu_last.user_full_name last_updated_by_name,\n" +
			"       bu_in.user_full_name   transfer_in_person_name,\n" +
			"       bu_out.user_full_name  transfer_out_person_name,\n" +
			"       bu_la.user_full_name   transfer_last_person_name,\n" +
			"       lookup3.meaning purchaseName,\n" +
			"       bu_ad.user_full_name adjustByName,\n" +
			"       tcc.affirm_tot_store_count,\n" +
			"       tcc.add_receive_amount,\n" +
			"       process.current_approval_user as currentUserName,\n" +
			"       process.start_user_name as submitUserName\n" +
			"  from (select t1.*\n" +
			"          from tta_npp_new_product_report t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where exists (select group_code\n" +
			"                  from (select nvl(dp.\"GROUP\", '-1') as group_code\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id =:userId\n" +
			"                           and bu.data_type = 1\n" +
			"                         group by nvl(dp.\"GROUP\", '-1')) t2\n" +
			"                 where t2.group_code = t1.group_code)\n" +
			"           and nvl(t1.batch_code, '-1') =:batchCode\n" +
			"           and nvl(t1.status, 1) = 1\n" +
			"           and nvl(th.is_publish, 'N') = 'Y'\n" +
			"			and t1.group_desc != 'Own Brand'\n" +
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_npp_new_product_report t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where exists (select group_code, dept_code\n" +
			"                  from (select nvl(dp.\"GROUP\", '-1') as group_code,\n" +
			"                               nvl(dp.dept, '-1') as dept_code\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id =:userId\n" +
			"                           and bu.data_type = 2\n" +
			"                         group by nvl(dp.\"GROUP\", '-1'),\n" +
			"                                  nvl(dp.dept, '-1')) t2\n" +
			"                 where t2.group_code = t1.group_code\n" +
			"                   and t2.dept_code = t1.dept_code)\n" +
			"           and nvl(t1.batch_code, '-1') =:batchCode\n" +
			"           and nvl(t1.status, 1) = 1\n" +
			"           and nvl(th.is_publish, 'N') = 'Y'\n" +
			"			and t1.group_desc != 'Own Brand'\n" +
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_npp_new_product_report t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where exists (select group_code, dept_code\n" +
			"                  from (select nvl(dp.\"GROUP\", '-1') as group_code,\n" +
			"                               nvl(dp.dept, '-1') as dept_code,\n" +
			"                               nvl(dp.brand_cn, '-1') as brand_cn\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id =:userId\n" +
			"                           and bu.data_type = 3\n" +
			"                         group by nvl(dp.\"GROUP\", '-1'),\n" +
			"                                  nvl(dp.dept, '-1'),\n" +
			"                                  nvl(dp.brand_cn, '-1')) t2\n" +
			"                 where t2.group_code = t1.group_code \n" +
			"                   and t2.dept_code = t1.dept_code\n" +
			"                   and t2.brand_cn = t1.brand_cn)\n" +
			"           and nvl(t1.batch_code, '-1') =:batchCode\n" +
			"           and nvl(t1.status, 1) = 1\n" +
			"           and nvl(th.is_publish, 'N') = 'Y'\n" +
			"			and t1.group_desc != 'Own Brand'\n" +
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_npp_new_product_report t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where t1.transfer_in_person =1\n" +
			"           and nvl(th.is_publish, 'N') = 'Y' \n" +
			"           and nvl(t1.batch_code, '-1') =:batchCode \n" +
			"			and t1.group_desc != 'Own Brand'\n" +
			") tcc\n" +
			"  left join base_users bu\n" +
			"    on tcc.created_by = bu.user_id\n" +
			"  left join base_attachment ba\n" +
			"    on ba.function_id = 'tta_npp_checking_report'\n" +
			"   and ba.business_id = tcc.npp_id\n" +
			"   and nvl(ba.delete_flag, 0) = 0\n" +
			"  left join base_users bu_last\n" +
			"    on tcc.last_updated_by = bu_last.user_id\n" +
			"  left join base_users bu_in\n" +
			"    on tcc.transfer_in_person = bu_in.user_id\n" +
			"  left join base_users bu_out\n" +
			"    on tcc.transfer_out_person = bu_out.user_id\n" +
			"  left join base_users bu_la\n" +
			"    on tcc.transfer_last_person = bu_la.user_id\n" +
			"  left join base_users bu_ad\n" +
			"    on tcc.adjust_By = bu_ad.user_id\n" +
			"  left join \n" +
			"  (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='NEW_PAYMENT' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup1  on tcc.collect_way = lookup1.LOOKUP_CODE\n" +
			"  LEFT JOIN TTA_REPORT_PROCESS_HEADER TRPH ON tcc.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID \n" +
			" left join \n" +
			"  (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
			" left join\n" +
			"  (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type in('TTA_NPP_ACTION','TTA_DM_ADJ_ACTION') and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on tcc.purchase_act = lookup3.lookup_code\n" +
			" left join \n" +
			" ( select a.business_key,\n" +
			"       nvl(bp.person_name_en,bp.person_name_cn) as current_approval_user,\n" +
			"       b.assignee_,\n" +
			"       --bu.user_full_name as start_user_name\n" +
			"       nvl(bpu.person_name_en,bpu.person_name_cn) as start_user_name\n" +
			"  from act_bpm_list a\n" +
			" left join act_ru_task b\n" +
			"    on a.proc_inst_id = b.proc_inst_id_\n" +
			"   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
			"   left join base_person bp\n" +
			"   on bp.employee_number = b.assignee_\n" +
			"   left join base_users bu \n" +
			"   on bu.user_id = a.created_by \n" +
			"	left join base_person bpu on bu.person_id = bpu.person_id\n" +
			"where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process \n" +
			"   on tcc.process_id = process.business_key    \n" +
			" where 1=1 ";

	public static final String QUERY_EXISTS = "SELECT tom.group_code,tom.group_desc,tom.dept_code,tom.dept_desc,tom.brand_cn, tom.group_code||'-'||tom.dept_code||'-'||tom.brand_cn  valueAll \n" +
			"FROM tta_npp_new_product_report tom\n" +
			"\n" +
			"where tom.batch_code =:batchCode\n" +
			"and  \n" +
			"not exists (SELECT 1 FROM tta_user_group_dept_brand_eft_v tug where (tug.data_type = '1' and tug.\"GROUP\" = tom.group_code)\n" +
			"                                                              or (tug.data_type = '2' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code)\n" +
			"                                                              or (tug.data_type = '3' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code and tug.brand_cn = tom.brand_cn)  )\n";

	/**
	 *
	 * @param batchCode 批次号
	 * @param userId
	 * @param year 年
	 * @param month 月
	 * @param majorDeptCode 当前登录人的主部门
	 * @return
	 */
	public static String getInsertNppReportSql(String batchCode,Integer userId,Integer year,Integer month,String majorDeptCode){
		String sql = "insert into tta_npp_new_product_report\n" +
				"  (npp_id,\n" +
				"   month,\n" +
				"   store_coun,\n" +
				"   group_code,\n" +
				"   group_desc,\n" +
				"   dept_code,\n" +
				"   dept_desc,\n" +
				"   item_code,\n" +
				"   item_desc_cn,\n" +
				"   brand_cn,\n" +
				"   brand_en,\n" +
				"   vendor_nbr,\n" +
				"   vendor_name,\n" +
				"   prior_vendor_code,\n" +
				"   prior_vendor_name,\n" +
				"   activity,\n" +
				"   ctw,\n" +
				"   eb,\n" +
				"   contract_terms,\n" +
				"   collect_way,\n" +
				"   charge_standards,\n" +
				"   charge_unit,\n" +
				"   charge_method,\n" +
				"   receive_amount,\n" +
				"   receive_amount_add,\n" +
				"   unconfirm_amount,\n" +
				"   unconfirm_amount_a,\n" +
				"   add_receive_amount,\n" +
				"   original_before_amount,\n" +
				"   difference,\n" +
				"   source_filepath,\n" +
				"   source_filename,\n" +
				"   collect_select,\n" +
				"   batch_code,\n" +
				"   status,\n" +
				"   creation_date,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   last_update_login,\n" +
				"   addition_rate,\n" +
				"   annual_month_n,\n" +
				"   month_n,\n" +
				"   affirm_tot_store_count,\n" +
				"   type) \n" +
				"  select\n" +
				"       SEQ_TTA_NPP_NEW_PRODUCT.Nextval,\n" +
				"       t.month,\n" +
				"       t.store_coun,\n" +
				"       t.group_code,\n" +
				"       t.group_desc,\n" +
				"       t.dept_code,\n" +
				"       t.dept_desc,\n" +
				"       t.item_code,\n" +
				"       t.item_desc_cn,\n" +
				"       t.brand_cn,\n" +
				"       t.brand_en,\n" +
				"       t.vendor_nbr,\n" +
				"       t.vendor_name,\n" +
				"       t.vendor_nbr,\n" +
				"       t.vendor_name,\n" +
				"       t.activity,\n" +
				"       t.ctw,\n" +
				"       t.eb,\n" +
				"       t.contract_terms,--合同条款\n" +
				"       t.collect_way,--收取方式\n" +
				"       t.charge_standards,--收费标准\n" +
				"       t.charge_unit,--收费单位\n" +
				"       t.charge_method, -- 收费方式\n" +
				"       ( --应收金额(不含加成)\n" +
				"               case     \n" +
				"                 when   t.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量;3:按固定金额\n" +
				"                   case when t.frist_rec_pog_time is not null and t.system_status = 'Y' then -- 有首次上图时间\n" +
				"                        round(nvl(t.store_coun,0) * nvl(t.charge_standards,0),0) --公式:店铺数量 * 收费标准\n" +
				"                     else\n" +
				"                       0 \n" +
				"                       end\n" +
				"                 when  t.charge_method = '1' or t.charge_method = '3' then\n" +
				"                      round(nvl(t.charge_standards,0),0) \n" +
				"                 else                           \n" +
				"                      0\n" +
				"                 end    \n" +
				"        ) receive_amount,  \n" +
				"        ( --应收金额(含加成)\n" +
				"                     case     \n" +
				"                     when   t.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                       case when t.frist_rec_pog_time is not null and t.system_status = 'Y' then -- 有首次上图时间                  \n" +
				"                            round(nvl(t.store_coun,0) * nvl(t.charge_standards,0) * (1 + nvl(t.addition_rate,0) / 100),0) --公式:店铺数量 * 收费标准 (应收金额（不含加成）*（1+加成比例）) (f=c*(1+加成比例))\n" +
				"                        else \n" +
				"                          0 \n" +
				"                          end                             \n" +
				"                     when  t.charge_method = '1' or t.charge_method = '3' then                           \n" +
				"                           round(nvl(t.charge_standards,0) * (1 + nvl(t.addition_rate,0) / 100) ,0)\n" +
				"                      else\n" +
				"                        0     \n" +
				"                     end   \n" +
				"       ) receive_amount_add,\n" +
				"       ( --采购确认收取金额(不含加成)（默认为:应收金额(不含加成)\n" +
				"               case     \n" +
				"                 when   t.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                   case when t.frist_rec_pog_time is not null and t.system_status = 'Y' then -- 有首次上图时间\n" +
				"                        round(nvl(t.store_coun,0) * nvl(t.charge_standards,0),0) --公式:店铺数量 * 收费标准\n" +
				"                     else\n" +
				"                       0 \n" +
				"                       end\n" +
				"                 when  t.charge_method = '1' or t.charge_method = '3' then\n" +
				"                      round(nvl(t.charge_standards,0),0) \n" +
				"                 else                           \n" +
				"                      0\n" +
				"                 end    \n" +
				"        ) unconfirm_amount,\n" +
				"       ( --采购确认收取金额（含加成） 默认为:应收金额(含加成)\n" +
				"                     case     \n" +
				"                     when   t.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                       case when t.frist_rec_pog_time is not null and t.system_status = 'Y' then -- 有首次上图时间                  \n" +
				"                            round(nvl(t.store_coun,0) * nvl(t.charge_standards,0) * (1 + nvl(t.addition_rate,0) / 100),0) --公式:店铺数量 * 收费标准 (应收金额（不含加成）*（1+加成比例）) (f=c*(1+加成比例))\n" +
				"                        else \n" +
				"                          0 \n" +
				"                          end                             \n" +
				"                     when  t.charge_method = '1' or t.charge_method = '3' then                           \n" +
				"                           round(nvl(t.charge_standards,0) * (1 + nvl(t.addition_rate,0) / 100) ,0)\n" +
				"                      else\n" +
				"                        0     \n" +
				"                     end   \n" +
				"       ) unconfirm_amount_a,\n" +
				"       ( --追加应收金额\n" +
				"         case when t.frist_rec_pog_time is not null and t.system_status ='Y' then\n" +
				"           0\n" +
				"         else \n" +
				"           round(nvl(t.cograph_stote_num,0) * nvl(t.charge_standards,0),0)  \n" +
				"      end\n" +
				"       ) add_receive_amount,\n" +
				"       ( --拆分前应收金额 应收金额(不含加成)\n" +
				"               case     \n" +
				"                 when   t.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                   case when t.frist_rec_pog_time is not null and t.system_status = 'Y' then -- 有首次上图时间\n" +
				"                        round(nvl(t.store_coun,0) * nvl(t.charge_standards,0),0) --公式:店铺数量 * 收费标准\n" +
				"                     else\n" +
				"                       0 \n" +
				"                       end\n" +
				"                 when  t.charge_method = '1' or t.charge_method = '3' then\n" +
				"                      round(nvl(t.charge_standards,0),0) \n" +
				"                 else                           \n" +
				"                      0\n" +
				"                 end    \n" +
				"        ) original_before_amount, \n" +
				"        0 difference,\n" +
				"       null source_filepath,                \n" +
				"       null source_filename,\n" +
				"       'TTA'  collect_select,--TTA/OnTop\n" +
				"       '" + batchCode + "'  batch_code, \n" +
				"        1  status,\n" +
				"        sysdate creation_date,\n" +
				"        " + userId + " created_by,\n" +
				"        " + userId + " last_updated_by,\n" +
				"        sysdate last_update_date,\n" +
				"        " + userId + " last_update_login,\n" +
				"        t.addition_rate,\n" +
				"        t.annual_month_n,\n" +
				"        t.month_n,\n" +
				"        0 affirm_tot_store_count,\n" +
				"        1--来源于导入   \n" +
				"  from \n" +
				"  ( \n" +
				"   select \n" +
				"          tph.proposal_id,\n" +
				"          tph.proposal_year,\n" +
				"          tph.order_nbr,\n" +
				"          tscl.system_current_id,\n" +
				"          tscl.month, --月份\n" +
				"          tscl.store_coun, --店铺数量\n" +
				"          -- decode(trim(tcm.ower_dept_no),'Own Brand','Own Brand',tiuv.Group_Desc)  GROUP_DESC, --group_desc\n" +
				"          tta_six_action_fun(tcm.ower_dept_no,tiuv.GROUP_DESC,tiuv.GROUP_CODE,'1') GROUP_DESC, --group_desc\n" +
				"          tta_six_action_fun(tcm.ower_dept_no,tiuv.GROUP_DESC,tiuv.GROUP_CODE,'0') NO_GROUP_DESC, --group_desc\n" +
				"          tiuv.group_code,--group_code\n" +
				"          tiuv.dept_desc,--DEPT_DESC\n" +
				"          tiuv.dept_code,--DEPT_CODE\n" +
				"          tscl.item  ITEM_CODE, --产品编号\n" +
				"          tiuv.Item_Desc_Cn  item_desc_cn, -- 产品名称\n" +
				"          tiuv.Brand_Cn  brand_cn,--品牌_CN\n" +
				"          tiuv.Brand_En  brand_en, --品牌_EN\n" +
				"          nvl(tpii.vendor_nbr,tiuv.vendor_nbr) vendor_nbr, --供应商编号\n" +
				"          ts.supplier_name vendor_name, --供应商名称\n" +
				"          ('NPP'||'-'||tscl.month|| '-' || tscl.item || '-'|| tiuv.Brand_Cn || '-' ||\n" +
				"            tta_six_action_fun (tcm.ower_dept_no,tiuv.Group_Desc,tiuv.Group_Code,'1')\n" +
				"            )  activity,--活动内容  nvl(tpii.vendor_nbr,tiuv.vendor_nbr)\n" +
				"          trim(tscl.ctw)  ctw,--非寄售经仓/寄售经仓\n" +
				"          trim(tscl.eb)   eb, --独家产品/非独家产品 \n" +
				"          tneh.current_year_term contract_terms,--合同条款\n" +
				"          --nvl(tneh.new_payment,'1')  collect_way, --收取方式 (1:按公司标准  2:按其他协定标准) \n" +
				"          tta_npp_server_count_value(\n" +
				"                                     --tneh.new_payment,\n" +
				"                                     --tneh.newbreed_extend_h_id,\n" +
				"                                     tscl.ctw,\n" +
				"                                     tscl.eb,\n" +
				"                                     tph.proposal_id,\n" +
				"                                     tiuv.group_code,\n" +
				"                                     tph.major_dept_code,\n" +
				"                                     substr(tscl.month_n,0,4),\n" +
				"                                     1\n" +
				"                                     ) charge_method, --收费方式 取值(1:按产品数量,2:按店铺数量,3:按固定金额)\n" +
				"            tta_npp_server_count_value(\n" +
				"                                     --tneh.new_payment,\n" +
				"                                     --tneh.newbreed_extend_h_id,\n" +
				"                                     tscl.ctw,\n" +
				"                                     tscl.eb,\n" +
				"                                     tph.proposal_id,\n" +
				"                                     tiuv.group_code,\n" +
				"                                     tph.major_dept_code,\n" +
				"                                     substr(tscl.month_n,0,4),\n" +
				"                                     2\n" +
				"                                     ) charge_standards,--- 收费标准                     \n" +
				"          tta_npp_server_count_value(\n" +
				"                                     --tneh.new_payment,\n" +
				"                                     --tneh.newbreed_extend_h_id,\n" +
				"                                     tscl.ctw,\n" +
				"                                     tscl.eb,\n" +
				"                                     tph.proposal_id,\n" +
				"                                     tiuv.group_code,\n" +
				"                                     tph.major_dept_code,\n" +
				"                                     substr(tscl.month_n,0,4),\n" +
				"                                     3\n" +
				"                                     ) charge_unit,--- 收费单位 (01:元/SKU/店铺,02:元/SKU,03:元/年)   \n" +
				"        tta_npp_server_count_value(\n" +
				"                                     --tneh.new_payment,\n" +
				"                                     --tneh.newbreed_extend_h_id,\n" +
				"                                     tscl.ctw,\n" +
				"                                     tscl.eb,\n" +
				"                                     tph.proposal_id,\n" +
				"                                     tiuv.group_code,\n" +
				"                                     tph.major_dept_code,\n" +
				"                                     substr(tscl.month_n,0,4),\n" +
				"                                     4\n" +
				"                                     ) collect_way,--收取方式 (1:按公司标准  2:按其他协定标准)                  \n" +
				"          \n" +
				"          nvl(tcm.addition_rate,0) addition_rate, -- 加成比例\n" +
				"          tcm.contract_date,--合同年份\n" +
				"          tscl.annual_month_n,\n" +
				"          tscl.month_n,\n" +
				"          tscl.frist_rec_pog_time,--首次上图时间\n" +
				"          tscl.max_store_coun,--最大店铺数量\n" +
				"          tscl.status system_status,--导入表的状态Y:生效,N:失效\n" +
				"          tscl.cograph_stote_num -- 上图数量   \n" +
				"     from TTA_SYSTEM_CURRENT_LINE tscl\n" +
				"         left join  --关联tta_purchase_in_xx表\n" +
				"          (  select item_nbr,vendor_nbr,receive_date from \n" +
				"          (select tpi.item_nbr,tpi.vendor_nbr,tpi.receive_date, row_number() \n" +
				"                  over(partition by tpi.item_nbr order by tpi.receive_date desc ) rn\n" +
				"                         from tta_purchase_in_" + year + " tpi ) where rn=1 \n" +
				"          ) tpii on nvl(trim(tscl.item),'-1') = tpii.item_nbr \n" +
				"          left join tta_item_unique_v tiuv on  tiuv.Item_Nbr = tscl.item\n" +
				"          left join tta_supplier ts on nvl(tpii.vendor_nbr,tiuv.VENDOR_NBR) = ts.supplier_code\n" +
				"          left join tta_contract_master tcm on ts.supplier_code = tcm.vendor_nbr --and tcm.CONTRACT_DATE = substr(202006,0,4)--合同部门基础表        \n" +
				"          left join (  \n" +
				"            Select t2.* ---找出主从供应商的供应商和Proposal信息(主供应商或者从供应商都有),取唯一一条数据\n" +
				"             From (\n" +
				"              Select tph.*,\n" +
				"                  ma.SUPPLIER_CODE MAJOR_SUPPLIER_CODE,\n" +
				"                  Row_Number() Over(Partition By ma.SUPPLIER_CODE Order By tph.approve_date desc,tph.PROPOSAL_YEAR desc) Row_Id\n" +
				"             From tta_proposal_header_new_version_v tph, tta_supplier_major_v ma\n" +
				"             where tph.status = 'C'\n" +
				"              and tph.approve_date is not null\n" +
				"              and tph.proposal_year <= " + year + "\n" +
				"              and tph.proposal_year >= " + (year - 2 ) + "\n" +
				"              and ma.MAJOR_SUPPLIER_CODE = tph.vendor_nbr(+)\n" +
				"              ) T2\n" +
				"            Where T2.Row_Id = 1\n" +
				"           ) tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE          \n" +
				"          left join tta_newbreed_extend_header tneh on tph.proposal_id = tneh.proposal_id\n" +
				"          \n" +
				"          where 1=1 and tscl.annual_month_n = " + month + "\n" +
				" ) t ";

		return sql;
	}

	public static String getUpdateNppReportSql(String batchCode, int userId, Integer month) {
		return "update tta_npp_new_product_report tnnpr\n" +
				"       set \n" +
				"         tnnpr.receive_amount =( --应收金额(不含加成)\n" +
				"                     case     \n" +
				"                     when   tnnpr.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                       round(nvl(tnnpr.store_coun,0) * nvl(tnnpr.charge_standards,0),0) --公式:店铺数量 * 收费标准\n" +
				"                     else                           \n" +
				"                      round(nvl(tnnpr.charge_standards,0),0)\n" +
				"                     end    \n" +
				"         ),\n" +
				"         tnnpr.receive_amount_add = ( --应收金额(含加成)\n" +
				"                       case     \n" +
				"                       when   tnnpr.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                         round(nvl(tnnpr.store_coun,0) * nvl(tnnpr.charge_standards,0) * (1+nvl(tnnpr.addition_rate,0)/100),0) --公式:店铺数量 * 收费标准 (应收金额（不含加成）*（1+加成比例）) (f=c*(1+加成比例))\n" +
				"                       else                           \n" +
				"                        round(nvl(tnnpr.charge_standards,0) * (1+nvl(tnnpr.addition_rate,0)/100) ,0)\n" +
				"                       end   \n" +
				"         ),\n" +
				"         tnnpr.unconfirm_amount = ( --采购确认收取金额(不含加成)（默认为:应收金额(不含加成)\n" +
				"                     case     \n" +
				"                     when   tnnpr.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                       round(nvl(tnnpr.store_coun,0) * nvl(tnnpr.charge_standards,0),0) --公式:店铺数量 * 收费标准\n" +
				"                     else                           \n" +
				"                      round(nvl(tnnpr.charge_standards,0),0)\n" +
				"                     end    \n" +
				"         ),\n" +
				"         tnnpr.unconfirm_amount_a = ( --采购确认收取金额（含加成） 默认为:应收金额(含加成)\n" +
				"                       case     \n" +
				"                       when   tnnpr.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                         round(nvl(tnnpr.store_coun,0) * nvl(tnnpr.charge_standards,0) * (1+nvl(tnnpr.addition_rate,0)/100),0) --公式:店铺数量 * 收费标准\n" +
				"                       else                           \n" +
				"                        round(nvl(tnnpr.charge_standards,0) * (1+nvl(tnnpr.addition_rate,0)/100) ,0)\n" +
				"                       end   \n" +
				"         ),\n" +
				"         tnnpr.difference = 0,-- 差异(含加成 仅计算负数)\n" +
				"         tnnpr.original_before_amount = ( --拆分前应收金额 应收金额(不含加成)\n" +
				"                     case     \n" +
				"                     when   tnnpr.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                       round(nvl(tnnpr.store_coun,0) * nvl(tnnpr.charge_standards,0),0) --公式:店铺数量 * 收费标准\n" +
				"                     else                           \n" +
				"                      round(nvl(tnnpr.charge_standards,0),0)\n" +
				"                     end    \n" +
				"         ) \n" +
				"         where tnnpr.annual_month_n = " + month + " and tnnpr.batch_code = '"+batchCode+"'";
	}

	public static String getUpdateNppIsCreateSql(String batchCode, int userId, Integer monthN) {
		return "update tta_npp_new_product_report tnnp set tnnp.is_create = 'Y' where tnnp.annual_month_n =" + monthN + " and tnnp.batch_code = '"+batchCode+"'";
	}

	public static final String NPP_ALLDATA_BY_QUERE = "select * from tta_npp_new_product_report tnnp";//是否考虑查询出有效的数据

    private Integer nppId;
    private String month;
    private Integer storeCoun;//店铺数量
    private String groupDesc;
    private String deptDesc;
    private String itemCode;//产品编号
    private String itemDescCn;//产品名称
    private String brandCn;//品牌中文名
    private String brandEn;//品牌英文名
    private String vendorNbr;//供应商编号
    private String vendorName;//供应商名称
    private String activity;//活动内容
    private String ctw;//  Non-CVW/CVW 非寄售经仓/寄售经仓
    private String eb;//EB /Non-EB 独家产品/非独家产品
    private String contractTerms;//合同条款
    private String collectWay;//收取方式
    private BigDecimal chargeStandards;//收费标准
    private String chargeUnit;//收费单位
    private BigDecimal receiveAmount;//应收金额(不含加成)
    private BigDecimal unconfirmAmount;//采购确认收取金额(不含加成)
    private BigDecimal difference;//差异
    private String sourceFilepath;//文件路径
    private String sourceFilename;//文件名称
    private String collectSelect;//TTA/OnTop 选择
    private String purchaseAct;//采购行动
	private String purchaseName;
    private String exemptionReason;//豁免原因-1
    private String exemptionReason2;//豁免原因-2
    private String debitOrderCode;//借记单编号
    private BigDecimal aboiReceipts;//aboi系统实收金额
    private String batchCode;//批次编号
    private Integer transferInPerson;//转办人(转入)
    private String remark;//备注
    private Integer status;//状态(1生效,0失效)
    private Integer parentId;//拆分前的ID
    private String parentVendorCode;//拆分前的VENDOR_CODE
    private String parentVendorName;//拆分前的VENDOR_NAME
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer transferOutPerson;//转办人(转出)
    private Integer transferLastPerson;//转办人(最后一次转出的人)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;//转办人(转入)时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;//转办人(转出)时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;//转办人(最后一次转出的时间)
    private String chargeUnitName;//计费单位名称
    private BigDecimal originalBeforeAmount;//拆分前的应收金额
    private Integer additionRate;//加成比例
    private Integer operatorUserId;

    private String groupCode;
    private String deptCode;
	private String isCreate;//是否生成
	private BigDecimal receiveAmountAdd;//应收金额(含加成)
	private String chargeMethod;//收费方式
	private Integer annualMonthN;//实际月份 数字类型
	private Integer monthN;//导入月 数字类型
	private BigDecimal unconfirmAmountA;//需采购确认金额(含加成)

	private String processCode;
	private String processStatusName;
	private Integer processReId;//流程状态id

	private String collectWayName;//收取方式名称

	private Integer fileId;//文件id
	private String createdByName;
	private String lastUpdatedByName;
	private String transferInPersonName;
	private String transferOutPersonName;
	private String transferLastPersonName;

	private String exemptionReasonName;
	private String exemptionReason2Name;

	private BigDecimal affirmTotStoreCount;//采购确认TOTAL店铺数
	private BigDecimal addReceiveAmount;//追加应收金额

	private int group_cnt;//当前按照部门提交NPP数量
	private BigDecimal differenceAmt;//差异金额
	private BigDecimal reachRate;//达成率
	private String taskIds;

    private Integer adjustBy;//调整人
    private String adjustByName;
    private BigDecimal adjustAmount;//调整实收金额
	private BigDecimal noAdjustReceiveAmount;//调整应收金额(不含加成)
    private BigDecimal adjustReceiveAmount;//调整应收金额(含加成)
	private String type;
	private String currentUserName;//当前审批人
	private String submitUserName;//发起人
	private String valueAll;
	private String priorVendorCode;
	private String priorVendorName;
	private String processStatus;//单行流程状态

    public static String getProcessSummary(String batchId, String userCode, Integer operatorUserId) {
		String sql = "select  '" + batchId+ "' as batch_code,\n" +
				"       tdc.group_code,\n" +
				"       max(tdc.group_desc) as group_desc,\n" +
				" 		sum(nvl(receive_amount_add,0)) as receive_amount_add, -- 应收金额(含加成)\n" +
				" 		sum(nvl(unconfirm_amount_a,0)) as unconfirm_amount_a, -- 需采购确认收取金额(含加成)\n" +
				" 		sum(nvl(unconfirm_amount_a,0))-sum(nvl(receive_amount_add,0)) as difference_amt,  -- 差异金额\n" +
				"		ROUND(decode(sum(nvl(unconfirm_amount_a,0)),0,0,sum(nvl(unconfirm_amount_a,0))/sum(nvl(unconfirm_amount_a,0))) * 100,2) as reach_rate,  -- 达成率=需采购确认收取金额(含加成)/应收金额(含加成) 【百分比】\n" +
				"  XMLAGG(XMLPARSE(CONTENT task.id_ || ',' WELLFORMED) ORDER BY task.id_).GETCLOBVAL() as task_ids,\n" +
				"  count(tdc.npp_id) as  group_cnt\n" +
				"  from tta_report_header trh\n" +
				" inner join tta_npp_new_product_report tdc\n" +
				"    on trh.batch_id = tdc.batch_code\n" +
				" inner join tta_report_process_header trph\n" +
				"    on tdc.process_id = trph.report_process_header_id\n" +
				" inner join act_bpm_list bpm\n" +
				"    on bpm.business_key = trph.report_process_header_id\n" +
				" inner join act_ru_task task\n" +
				"    on bpm.proc_inst_id = task.proc_inst_id_\n" +
				" where bpm.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
				" and task.suspension_state_ = 1\n" +
				"    and not exists (select 1\n" +
				"           from act_ru_task sub\n" +
				"          where sub.parent_task_id_ = task.id_\n" +
				"            and sub.suspension_state_ = 1)\n" +
				"    and (task.assignee_ = '"  + userCode +  "' or\n" +
				"        (task.assignee_ is null and exists\n" +
				"         (select 1\n" +
				"             from act_ru_identitylink idk\n" +
				"            where idk.task_id_ = task.id_\n" +
				"              and idk.user_id_ = '"+ userCode + "')) or exists\n" +
				"         (select 1\n" +
				"            from act_bpm_task_delegate delegate\n" +
				"           where delegate.delegate_user_id = " + operatorUserId + "\n" +
				"             and delegate.status = 'PENDING'\n" +
				"             and delegate.task_id = task.id_))" +
				"   and trh.batch_id = '" + batchId + "' \n" +
				" group by tdc.group_code";
		return sql;
    }

	public static String getQueryProcess(String userCode, Integer operatorUserId) {
		String query = "select \n" +
				"       tcc.NPP_ID,\n" +
				"       tcc.BATCH_CODE,\n" +
				"       TRPH.BATCH_CODE processCode,\n" +
				"       TCC.PROCESS_ID  processReId,\n" +
				"       lookup2.meaning processStatusName,\n" +
				"       tcc.MONTH,\n" +
				"       tcc.STORE_COUN,\n" +
				"       tcc.GROUP_DESC,\n" +
				"       tcc.DEPT_DESC,\n" +
				"       tcc.ITEM_CODE,\n" +
				"       tcc.ITEM_DESC_CN,\n" +
				"       tcc.BRAND_CN,\n" +
				"       tcc.BRAND_EN,\n" +
				"       tcc.VENDOR_NBR,\n" +
				"       tcc.VENDOR_NAME,\n" +
				"       tcc.ACTIVITY,\n" +
				"       tcc.CTW,\n" +
				"       tcc.EB,\n" +
				"       tcc.CONTRACT_TERMS,\n" +
				"       tcc.COLLECT_WAY,\n" +
				"       lookup1.meaning collectWayName,\n" +
				"       tcc.CHARGE_STANDARDS,\n" +
				"       tcc.CHARGE_UNIT,\n" +
				"       tcc.RECEIVE_AMOUNT,\n" +
				"       tcc.RECEIVE_AMOUNT_ADD,\n" +
				"       tcc.UNCONFIRM_AMOUNT,\n" +
				"       tcc.UNCONFIRM_AMOUNT_A,\n" +
				"       tcc.DIFFERENCE,\n" +
				"       tcc.COLLECT_SELECT,\n" +
				"       tcc.SOURCE_FILEPATH,\n" +
				"       ba.file_id,\n" +
				"       ba.source_file_name source_filename,\n" +
				"       tcc.PURCHASE_ACT,\n" +
				"       tcc.EXEMPTION_REASON,\n" +
				"       tcc.EXEMPTION_REASON2,\n" +
				"       tcc.DEBIT_ORDER_CODE,\n" +
				"       tcc.ABOI_RECEIPTS,\n" +
				"       tcc.TRANSFER_IN_PERSON,\n" +
				"       tcc.REMARK,\n" +
				"       tcc.STATUS,\n" +
				"       tcc.PARENT_ID,\n" +
				"       tcc.PARENT_VENDOR_CODE,\n" +
				"       tcc.PARENT_VENDOR_NAME,\n" +
				"       tcc.CREATION_DATE,\n" +
				"       tcc.CREATED_BY,\n" +
				"       tcc.LAST_UPDATED_BY,\n" +
				"       tcc.LAST_UPDATE_DATE,\n" +
				"       tcc.LAST_UPDATE_LOGIN,\n" +
				"       tcc.TRANSFER_OUT_PERSON,\n" +
				"       tcc.TRANSFER_LAST_PERSON,\n" +
				"       tcc.TRANSFER_IN_DATE,\n" +
				"       tcc.TRANSFER_OUT_DATE,\n" +
				"       tcc.TRANSFER_LAST_DATE,\n" +
				"       tcc.CHARGE_UNIT_NAME,\n" +
				"       tcc.ORIGINAL_BEFORE_AMOUNT,\n" +
				"       tcc.ADDITION_RATE,\n" +
				"       tcc.GROUP_CODE,\n" +
				"       tcc.DEPT_CODE,\n" +
				"       tcc.CHARGE_METHOD,--收费方式\n" +
				"       tcc.ANNUAL_MONTH_N,\n" +
				"       tcc.MONTH_N,\n" +
				"       tcc.IS_CREATE,\n" +
				"       bu.user_full_name      created_by_name,\n" +
				"       bu_last.user_full_name last_updated_by_name,\n" +
				"       bu_in.user_full_name   transfer_in_person_name,\n" +
				"       bu_out.user_full_name  transfer_out_person_name,\n" +
				"       bu_t_last.user_full_name   transfer_last_person_name,\n" +
				"       trh.id,\n" +
				"       trh.status  headerStatus,\n" +
				"       lookup5.meaning exemptionReason2Name,\n" +
				"       lookup4.meaning exemptionReasonName,\n" +
				"       lookup3.meaning purchaseName,\n" +
				"       tcc.affirm_tot_store_count,\n" +
				"       tcc.add_receive_amount\n" +
				"  from tta_npp_new_product_report tcc\n" +
				"  left join TTA_REPORT_PROCESS_HEADER TRPH \n" +
				"  ON tcc.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID\n" +
				"  inner join act_bpm_list bpm  \n" +
				"  on bpm.business_key = trph.report_process_header_id \n" +
				"  inner join act_ru_task task  \n" +
				"  on bpm.proc_inst_id = task.proc_inst_id_\n" +
				"  left join tta_report_header trh\n" +
				"    on tcc.batch_code = trh.batch_id\n" +
				"  left join base_attachment ba\n" +
				"    on ba.function_id = 'tta_npp_checking_report'\n" +
				"   and ba.business_id = tcc.npp_id\n" +
				"   and nvl(ba.delete_flag, 0) = 0\n" +
				"  left join base_users bu\n" +
				"    on tcc.created_by = bu.user_id\n" +
				"  left join base_users bu_last\n" +
				"    on tcc.last_updated_by = bu_last.user_id\n" +
				"  left join base_users bu_in\n" +
				"    on tcc.transfer_in_person = bu_in.user_id\n" +
				"  left join base_users bu_out\n" +
				"    on tcc.transfer_out_person = bu_out.user_id\n" +
				"  left join base_users bu_t_last\n" +
				"    on tcc.transfer_last_person = bu_t_last.user_id\n" +
				"  left join \n" +
				"  (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='NEW_PAYMENT' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  on tcc.collect_way = lookup1.LOOKUP_CODE \n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type in('TTA_NPP_ACTION','TTA_DM_ADJ_ACTION') and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup3  on tcc.purchase_act = lookup3.lookup_code\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_NPP_REASON' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup4  on tcc.exemption_Reason = lookup4.lookup_code\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_NPP_REASON1' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup5  on tcc.exemption_Reason2 = lookup5.lookup_code\n" +
				" where 1 = 1 and bpm.proc_def_key = 'TTA_ACTIVITY.-999' \n" +
				"  and tcc.status = 1 and tcc.process_id is not null \n" +
				" and task.suspension_state_ = 1\n" +
				"    and not exists (select 1\n" +
				"           from act_ru_task sub\n" +
				"          where sub.parent_task_id_ = task.id_\n" +
				"            and sub.suspension_state_ = 1)\n" +
				"    and (task.assignee_ = '"+userCode+"' or\n" +
				"        (task.assignee_ is null and exists\n" +
				"         (select 1\n" +
				"             from act_ru_identitylink idk\n" +
				"            where idk.task_id_ = task.id_\n" +
				"              and idk.user_id_ = '"+userCode+"')) or exists\n" +
				"         (select 1\n" +
				"            from act_bpm_task_delegate delegate\n" +
				"           where delegate.delegate_user_id = "+operatorUserId+"\n" +
				"             and delegate.status = 'PENDING'\n" +
				"             and delegate.task_id = task.id_)) ";
		return query;
	}

	public static String getInsertWillChargeNppReportByLastMonth(String batchCode, Integer userId, Integer month) {
		return "insert into tta_npp_new_product_report\n" +
				"    (npp_id,\n" +
				"     month,\n" +
				"     store_coun,\n" +
				"     group_desc,\n" +
				"     dept_desc,\n" +
				"     item_code,\n" +
				"     item_desc_cn,\n" +
				"     brand_cn,\n" +
				"     brand_en,\n" +
				"     vendor_nbr,\n" +
				"     vendor_name,\n" +
				"     activity,\n" +
				"     ctw,\n" +
				"     eb,\n" +
				"     contract_terms,\n" +
				"     collect_way,\n" +
				"     charge_standards,\n" +
				"     charge_unit,\n" +
				"     receive_amount,\n" +
				"     unconfirm_amount,\n" +
				"     difference,\n" +
				"     source_filepath,\n" +
				"     source_filename,\n" +
				"     collect_select,\n" +
				"     purchase_act,\n" +
				"     exemption_reason,\n" +
				"     exemption_reason2,\n" +
				"     debit_order_code,\n" +
				"     aboi_receipts,\n" +
				"     batch_code,\n" +
				"     remark,\n" +
				"     status,\n" +
				"     parent_id,\n" +
				"     parent_vendor_code,\n" +
				"     parent_vendor_name,\n" +
				"     creation_date,\n" +
				"     created_by,\n" +
				"     last_updated_by,\n" +
				"     last_update_date,\n" +
				"     last_update_login,\n" +
				"     charge_unit_name,\n" +
				"     original_before_amount,\n" +
				"     addition_rate,\n" +
				"     group_code,\n" +
				"     dept_code,\n" +
				"     charge_method,\n" +
				"     annual_month_n,\n" +
				"     month_n,\n" +
				"     receive_amount_add,\n" +
				"     unconfirm_amount_a,\n" +
				"     is_create,\n" +
				"     file_id,\n" +
				"     affirm_tot_store_count,\n" +
				"     add_receive_amount,\n" +
				"     from_where,\n" +
				"     type,\n" +
				"     proposal_order_no,\n" +
				"     company_dept_name,\n" +
				"     company_dept_code)\n" +
				"    select SEQ_TTA_NPP_NEW_PRODUCT.Nextval,\n" +
				"           (select tnc.month\n" +
				"              from tta_npp_new_product_report tnc\n" +
				"             where tnc.month_n = " + month + "\n" +
				"               and rownum = 1) month,\n" +
				"           tnn.store_coun,\n" +
				"           tnn.group_desc,\n" +
				"           tnn.dept_desc,\n" +
				"           tnn.item_code,\n" +
				"           tnn.item_desc_cn,\n" +
				"           tnn.brand_cn,\n" +
				"           tnn.brand_en,\n" +
				"           tnn.vendor_nbr,\n" +
				"           tnn.vendor_name,\n" +
				"           tnn.activity,\n" +
				"           ctw,\n" +
				"           eb,\n" +
				"           contract_terms,\n" +
				"           collect_way,\n" +
				"           charge_standards,\n" +
				"           charge_unit,\n" +
				"           receive_amount,\n" +
				"           unconfirm_amount,\n" +
				"           difference,\n" +
				"           source_filepath,\n" +
				"           source_filename,\n" +
				"           collect_select,\n" +
				"           purchase_act,\n" +
				"           exemption_reason,\n" +
				"           exemption_reason2,\n" +
				"           debit_order_code,\n" +
				"           tnn.aboi_receipts,\n" +
				"           '" + batchCode + "',\n" +
				"           tnn.remark,\n" +
				"           tnn.status,\n" +
				"           tnn.parent_id,\n" +
				"           tnn.parent_vendor_code,\n" +
				"           tnn.parent_vendor_name,\n" +
				"           sysdate,\n" +
				"           " + userId +",\n" +
				"           " + userId +",\n" +
				"           sysdate,\n" +
				"           " + userId + ",\n" +
				"           tnn.charge_unit_name,\n" +
				"           tnn.original_before_amount,\n" +
				"           tnn.addition_rate,\n" +
				"           tnn.group_code,\n" +
				"           tnn.dept_code,\n" +
				"           tnn.charge_method,\n" +
				"           " + month + ",\n" +
				"           " + month + ",\n" +
				"           tnn.add_receive_amount,\n" +
				"           tnn.unconfirm_amount_a,\n" +
				"           'Y', --生成\n" +
				"           tnn.file_id,\n" +
				"           tnn.affirm_tot_store_count,\n" +
				"           tnn.add_receive_amount,\n" +
				"           '来自上个月的将收取的数据',\n" +
				"           '3', --- 来源于上个月将收取\n" +
				"           tnn.proposal_order_no,\n" +
				"           tnn.company_dept_name,\n" +
				"           tnn.company_dept_code\n" +
				"      from tta_npp_new_product_report tnn\n" +
				"     where tnn.purchase_act = 'A03' -- 采购行动为将收取\n" +
				"       AND exists (select 1\n" +
				"              from (select max(tnnr.month_n) month_n --找出不是当前月的最大月份\n" +
				"                      from tta_npp_new_product_report tnnr\n" +
				"                     where tnnr.month_n != " + month + ") tcc\n" +
				"             where tcc.month_n = tnn.month_n) ";
	}

	public static String getInsertNppAdjuctRecieveAmt(String batchCode, Integer userId, Integer month, int ytdId) {
    	//2020.7.31这条sql不使用
    	String sql ="insert into tta_npp_ytd\n" +
				"         (ytd_id,\n" +
				"          month,\n" +
				"          store_coun,\n" +
				"          group_code,\n" +
				"          group_desc,\n" +
				"          dept_code,\n" +
				"          dept_desc,\n" +
				"          brand_cn,\n" +
				"          brand_en,\n" +
				"          vendor_nbr,\n" +
				"          vendor_name,\n" +
				"          ctw,\n" +
				"          eb,\n" +
				"          amount,\n" +
				"          collect_way,\n" +
				"          charge_method,\n" +
				"          charge_standards,\n" +
				"          creation_date,\n" +
				"          created_by,\n" +
				"          last_updated_by,\n" +
				"          last_update_date,\n" +
				"          annual_month_n,\n" +
				"          month_n,\n" +
				"          addition_rate,\n" +
				"          proposal_year\n" +
				"          )\n" +
				"        select \n" +
				"          "+ytdId+" as ytd_id,\n" +
				"           (select tnc.month\n" +
				"              from tta_npp_new_product_report tnc\n" +
				"             where tnc.month_n = " + month + "\n" +
				"               and rownum = 1) month,\n" +
				"          tab2.store_coun,\n" +
				"          tab2.group_code,\n" +
				"          tab2.group_desc,\n" +
				"          tab2.dept_code,\n" +
				"          tab2.dept_desc,\n" +
				"          tab2.brand_cn,\n" +
				"          tab2.brand_cn,\n" +
				"          tab2.vendor_nbr,\n" +
				"          tab2.vendor_name,        \n" +
				"          tab2.ctw,\n" +
				"          tab2.eb,\n" +
				"          tab2.amount,\n" +
				"          nvl(tab.new_payment,'1')  collect_way, --收取方式 (1:按公司标准  2:按其他协定标准)  -->如果收取方式为空,则默认为:按公司标准\n" +
				"           case when nvl(tab.new_payment,'1') = '1' then '2' else nvl(tab.charge_method,'2') end as charge_method, --收费方式  取值(1:按产品数量,2:按店铺数量,3:按固定金额) \n" +
				"          tta_npp_server_count(nvl(tab.new_payment,'1'),tab2.ctw,tab2.eb,\n" +
				"          '1',tab.proposal_id,tab2.group_code,tab.major_dept_code,tab2.npp_year) charge_standards,\n" +
				"           sysdate,\n" +
				"           " + userId+",\n" +
				"           " + userId+",\n" +
				"           sysdate,\n" +
				"           " + month+ ",\n" +
				"           " + month + ",\n" +
				"           nvl(tab3.addition_rate,0),\n" +
				"           tab2.npp_year\n" +
				"           from ( -- 找出一组供应商的审批通过的Proposal,时间范围为上一次时间到导入月的时间(可以取系统时间)\n" +
				"       select  tph.proposal_year,\n" +
				"               tph.proposal_id,\n" +
				"               tph.major_dept_code,\n" +
				"               ma.supplier_code,\n" +
				"               tnel.bread_name,\n" +
				"               tneh.new_payment,\n" +
				"               tnel.charge_method   \n" +
				"          from tta_proposal_header tph\n" +
				"          inner join tta_supplier_major_v ma \n" +
				"          on  tph.vendor_nbr = ma.major_supplier_code\n" +
				"          inner join tta_newbreed_extend_header tneh on tph.proposal_id = tneh.proposal_id\n" +
				"          inner join tta_newbreed_extend_line tnel on tneh.newbreed_extend_h_id = tnel.newbreed_extend_h_id\n" +
				"            where tph.approve_date <= sysdate -- 当前系统时间\n" +
				"             and tph.approve_date >= (select nvl(max(tnnr.creation_date),sysdate) creation_date \n" +
				"                      from tta_npp_new_product_report tnnr inner join tta_report_header trh on tnnr.batch_code = trh.batch_id\n" +
				"                      and trh.is_publish = 'Y' and tnnr.status = '1'\n" +
				"                     where tnnr.month_n <= "+month+")-- 上次生成NPP数据的日期(参考六大活动报表(NPP头))\n" +
				"             and nvl(tph.version_status, '1') = '1'\n" +
				"             and tph.status = 'C'  \n" +
				"        ) tab\n" +
				"        inner join ( --- 总逻辑:计算年至今的NPP数据的应收金额\n" +
				"                 select --细节:汇总求和每个供应商的每个品牌的eb,ctw的应收调整金额,店铺数量\n" +
				"                     max(substr(tnn.month_n, 0, 4)) npp_year, --年份\n" +
				"                     tnn.vendor_nbr,\n" +
				"                     max(tnn.vendor_name) as vendor_name,\n" +
				"                     tnn.group_code,\n" +
				"                     tnn.group_desc,\n" +
				"                     tnn.dept_code,\n" +
				"                     tnn.dept_desc,\n" +
				"                     tnn.brand_cn,\n" +
				"                     tnn.brand_en,\n" +
				"                     sum(nvl(tnn.receive_amount_add, 0) + nvl(tnn.adjust_receive_amount, 0)) as amount, -- 应收调整金额=应收金额含税(含加成)+调整金额之和 \n" +
				"                     sum(nvl(tnn.store_coun, 0)) store_coun, --店铺数 \n" +
				"                     tnn.eb,\n" +
				"                     tnn.ctw\n" +
				"                      from tta_npp_new_product_report tnn\n" +
				"                     inner join tta_report_header trh\n" +
				"                        on tnn.batch_code = trh.batch_id\n" +
				"                       and trh.is_publish = 'Y' --发布\n" +
				"                       and tnn.status = '1' -- 生效\n" +
				"                       and trh.report_type = 'NPP'\n" +
				"                     where tnn.month_n <= " + month+" --年至今的数据\n" +
				"                     group by tnn.vendor_nbr,\n" +
				"                              tnn.group_code,\n" +
				"                              tnn.group_desc,\n" +
				"                              tnn.dept_code,\n" +
				"                              tnn.dept_desc,\n" +
				"                              tnn.brand_cn,\n" +
				"                              tnn.brand_en,\n" +
				"                              tnn.eb,\n" +
				"                              tnn.ctw           \n" +
				"             ) tab2  \n" +
				"             on tab.proposal_year = tab2.npp_year\n" +
				"             and tab.supplier_code = nvl(tab2.vendor_nbr,'-1') \n" +
				"             and tab.bread_name = decode(tab2.eb,'EB','3','Non EB','2')\n" +
				"             left join \n" +
				"             (SELECT \n" +
				"                    tcm.vendor_nbr,\n" +
				"                    max(nvl(tcm.contract_date,0)) contract_date,\n" +
				"                    max(nvl(tcm.addition_rate,0)) addition_rate\n" +
				"                     FROM  tta_proposal_header tph,\n" +
				"               tta_supplier_major_v ma,\n" +
				"               tta_contract_master tcm\n" +
				"               where tph.vendor_nbr = ma.MAJOR_SUPPLIER_CODE\n" +
				"               and tcm.vendor_nbr =  ma.SUPPLIER_CODE\n" +
				"               and tph.status = 'C'\n" +
				"               and nvl(tph.version_status,'1')= '1'\n" +
				"               group by tcm.vendor_nbr\n" +
				"               ) tab3 on tab.supplier_code = tab3.vendor_nbr and tab.proposal_year = tab3.contract_date ";

		sql = "insert into tta_npp_ytd\n" +
				"         (ytd_id,\n" +
				"          month,\n" +
				"          store_coun,\n" +
				"          group_code,\n" +
				"          group_desc,\n" +
				"          dept_code,\n" +
				"          dept_desc,\n" +
				"          brand_cn,\n" +
				"          brand_en,\n" +
				"          vendor_nbr,\n" +
				"          vendor_name,\n" +
				"          ctw,\n" +
				"          eb,\n" +
				"          amount,\n" +
				"          collect_way,\n" +
				"          charge_method,\n" +
				"          charge_standards,\n" +
				"          creation_date,\n" +
				"          created_by,\n" +
				"          last_updated_by,\n" +
				"          last_update_date,\n" +
				"          annual_month_n,\n" +
				"          month_n,\n" +
				"          addition_rate,\n" +
				"          proposal_year\n" +
				"          )\n" +
				"          \n" +
				"          \n" +
				"        select \n" +
				"          "+ytdId+" as ytd_id,\n" +
				"           (select tnc.month\n" +
				"              from tta_npp_new_product_report tnc\n" +
				"             where tnc.month_n = "+month+"\n" +
				"               and rownum = 1) month,\n" +
				"          tab2.store_coun,\n" +
				"          tab2.group_code,\n" +
				"          tab2.group_desc,\n" +
				"          tab2.dept_code,\n" +
				"          tab2.dept_desc,\n" +
				"          tab2.brand_cn,\n" +
				"          tab2.brand_cn,\n" +
				"          tab2.vendor_nbr,\n" +
				"          tab2.vendor_name,        \n" +
				"          tab2.ctw,\n" +
				"          tab2.eb,\n" +
				"          tab2.amount,\n" +
				"          -- nvl(tab.new_payment,'1')  collect_way, --收取方式 (1:按公司标准  2:按其他协定标准)  -->如果收取方式为空,则默认为:按公司标准\n" +
				" 			tta_npp_server_count_value(\n" +
				"                                     --tab.new_payment,\n" +
				"                                     --tab.newbreed_extend_h_id,\n" +
				"                                     tab2.ctw,\n" +
				"                                     tab2.eb,\n" +
				"                                     tab.proposal_id,\n" +
				"                                     tab2.group_code,\n" +
				"                                     tab.major_dept_code,\n" +
				"                                     tab2.npp_year,\n" +
				"                                     4\n" +
				"                                     ) collect_way,--收取方式 (1:按公司标准  2:按其他协定标准)  -->如果收取方式为空,则默认为:按公司标准\n" +
				"          -- case when nvl(tab.new_payment,'1') = '1' then '2' else nvl(tab.charge_method,'2') end as charge_method, --收费方式  取值(1:按产品数量,2:按店铺数量,3:按固定金额) \n" +
				"          tta_npp_server_count_value(\n" +
				"                                     --tab.new_payment,\n" +
				"                                     --tab.newbreed_extend_h_id,\n" +
				"                                     tab2.ctw,\n" +
				"                                     tab2.eb,\n" +
				"                                     tab.proposal_id,\n" +
				"                                     tab2.group_code,\n" +
				"                                     tab.major_dept_code,\n" +
				"                                     tab2.npp_year,\n" +
				"                                     1\n" +
				"                                     ) charge_method, -- 收费方式(取值(1:按产品数量,2:按店铺数量,3:按固定金额))\n" +
				"          --tta_npp_server_count(nvl(tab.new_payment,'1'),tab2.ctw,tab2.eb,'1',tab.proposal_id,tab2.group_code,tab.major_dept_code,tab2.npp_year) charge_standards,\n" +
				"          tta_npp_server_count_value(\n" +
				"                                     --tab.new_payment,\n" +
				"                                     --tab.newbreed_extend_h_id,\n" +
				"                                     tab2.ctw,\n" +
				"                                     tab2.eb,\n" +
				"                                     tab.proposal_id,\n" +
				"                                     tab2.group_code,\n" +
				"                                     tab.major_dept_code,\n" +
				"                                     tab2.npp_year,\n" +
				"                                     2\n" +
				"                                     ) charge_standards,--- 收费标准  \n" +
				"           sysdate,\n" +
				"           1,\n" +
				"           1,\n" +
				"           sysdate,\n" +
				"           "+month+",\n" +
				"           "+month+",\n" +
				"           nvl(tab3.addition_rate,0),\n" +
				"           tab2.npp_year\n" +
				"           from ( -- 找出一组供应商的审批通过的Proposal,时间范围为上一次时间到导入月的时间(可以取系统时间)\n" +
				"       select  tph.proposal_year,\n" +
				"               tph.proposal_id,\n" +
				"               tph.major_dept_code,\n" +
				"               ma.supplier_code,\n" +
				"               tnel.bread_name,\n" +
				"               tneh.new_payment,\n" +
				"               tnel.charge_method,\n" +
				"               tneh.newbreed_extend_h_id   \n" +
				"          from tta_proposal_header tph\n" +
				"          inner join tta_supplier_major_v ma \n" +
				"          on  tph.vendor_nbr = ma.major_supplier_code\n" +
				"          inner join tta_newbreed_extend_header tneh on tph.proposal_id = tneh.proposal_id\n" +
				"          inner join tta_newbreed_extend_line tnel on tneh.newbreed_extend_h_id = tnel.newbreed_extend_h_id\n" +
				"            where tph.approve_date <= sysdate -- 当前系统时间\n" +
				"             and tph.approve_date >= (select nvl(max(tnnr.creation_date),sysdate) creation_date \n" +
				"                      from tta_npp_new_product_report tnnr inner join tta_report_header trh on tnnr.batch_code = trh.batch_id\n" +
				"                      and trh.is_publish = 'Y' and tnnr.status = '1'\n" +
				"                     where tnnr.month_n < "+month+")-- 上次生成NPP数据的日期(参考六大活动报表(NPP头))\n" +
				"             and nvl(tph.version_status, '1') = '1'\n" +
				"             and tph.status = 'C'  \n" +
				"        ) tab\n" +
				"        inner join ( --- 总逻辑:计算年至今的NPP数据的应收金额\n" +
				"                 select --细节:汇总求和每个供应商的每个品牌的eb,ctw的应收调整金额,店铺数量\n" +
				"                     max(substr(tnn.month_n, 0, 4)) npp_year, --年份\n" +
				"                     tnn.vendor_nbr,\n" +
				"                     max(tnn.vendor_name) as vendor_name,\n" +
				"                     tnn.group_code,\n" +
				"                     tnn.group_desc,\n" +
				"                     tnn.dept_code,\n" +
				"                     tnn.dept_desc,\n" +
				"                     tnn.brand_cn,\n" +
				"                     tnn.brand_en,\n" +
				"                     sum(nvl(tnn.receive_amount_add, 0) + nvl(tnn.adjust_receive_amount, 0)) as amount, -- 应收调整金额=应收金额含税(含加成)+调整金额之和 \n" +
				"                     sum(nvl(tnn.store_coun, 0)) store_coun, --店铺数 \n" +
				"                     tnn.eb,\n" +
				"                     tnn.ctw\n" +
				"                      from tta_npp_new_product_report tnn\n" +
				"                     inner join tta_report_header trh\n" +
				"                        on tnn.batch_code = trh.batch_id\n" +
				"                       and trh.is_publish = 'Y' --发布\n" +
				"                       and tnn.status = '1' -- 生效\n" +
				"                       and trh.report_type = 'NPP'\n" +
				"                     where tnn.month_n <= "+month+" --年至今的数据\n" +
				"                     group by tnn.vendor_nbr,\n" +
				"                              tnn.group_code,\n" +
				"                              tnn.group_desc,\n" +
				"                              tnn.dept_code,\n" +
				"                              tnn.dept_desc,\n" +
				"                              tnn.brand_cn,\n" +
				"                              tnn.brand_en,\n" +
				"                              tnn.eb,\n" +
				"                              tnn.ctw           \n" +
				"             ) tab2  \n" +
				"             on tab.proposal_year = tab2.npp_year\n" +
				"             and tab.supplier_code = nvl(tab2.vendor_nbr,'-1') \n" +
				"             and tab.bread_name = decode(tab2.eb,'EB','3','Non EB','2')\n" +
				"             left join \n" +
				"             (SELECT \n" +
				"                    tcm.vendor_nbr,\n" +
				"                    max(nvl(tcm.contract_date,0)) contract_date,\n" +
				"                    max(nvl(tcm.addition_rate,0)) addition_rate\n" +
				"                     FROM  tta_proposal_header tph,\n" +
				"               tta_supplier_major_v ma,\n" +
				"               tta_contract_master tcm\n" +
				"               where tph.vendor_nbr = ma.MAJOR_SUPPLIER_CODE\n" +
				"               and tcm.vendor_nbr =  ma.SUPPLIER_CODE\n" +
				"               and tph.status = 'C'\n" +
				"               and nvl(tph.version_status,'1')= '1'\n" +
				"               group by tcm.vendor_nbr\n" +
				"               ) tab3 on tab.supplier_code = tab3.vendor_nbr and tab.proposal_year = tab3.contract_date";
    	return sql;
	}

	public static String  getInsertNppAdjuctRecieveAmtByYtd(String batchCode, Integer userId, Integer month, int ytdId) {
    	String sql = "insert into tta_npp_new_product_report\n" +
				"  (npp_id,\n" +
				"   month,\n" +
				"   store_coun,\n" +
				"   group_desc,\n" +
				"   dept_desc,\n" +
				"   brand_cn,\n" +
				"   brand_en,\n" +
				"   vendor_nbr,\n" +
				"   vendor_name,\n" +
				"   ctw,\n" +
				"   eb,\n" +
				"   collect_way,\n" +
				"   charge_standards,\n" +
				"   batch_code,\n" +
				"   creation_date,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   last_update_login,\n" +
				"   addition_rate,\n" +
				"   group_code,\n" +
				"   dept_code,\n" +
				"   charge_method,\n" +
				"   annual_month_n,\n" +
				"   month_n,\n" +
				"   from_where,\n" +
				"   no_adjust_receive_amount,\n" +
				"   adjust_receive_amount,\n" +
				"   type) \n" +
				"    \n" +
				"   select \n" +
				"     SEQ_TTA_NPP_NEW_PRODUCT.Nextval,\n" +
				"     tny.month,\n" +
				"     tny.store_coun,\n" +
				"     tny.group_code,\n" +
				"     tny.dept_desc,\n" +
				"     tny.brand_cn,\n" +
				"     tny.brand_en,\n" +
				"     tny.vendor_nbr,\n" +
				"     tny.vendor_name,\n" +
				"     tny.ctw,\n" +
				"     tny.eb,\n" +
				"     tny.collect_way,\n" +
				"     tny.charge_standards,\n" +
				"     '" + batchCode +"' batch_code,\n" +
				"     sysdate,\n" +
				"     " + userId + ",\n" +
				"     " + userId + ",\n" +
				"     sysdate,\n" +
				"     " + userId + ",\n" +
				"     tny.addition_rate,\n" +
				"     tny.group_code,\n" +
				"     tny.dept_code,\n" +
				"     tny.charge_method,\n" +
				"     tny.annual_month_n,\n" +
				"     tny.month_n,\n" +
				"     null from_where,\n" +
				"     ( case when   tny.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量             \n" +
				"                          round(nvl(tny.store_coun,0) * nvl(tny.charge_standards,0) - tny.amount,0) --公式:店铺数量 * 收费标准 (应收金额（不含加成）) (f=c)                            \n" +
				"                     else                           \n" +
				"                      round(nvl(tny.charge_standards,0) - tny.amount ,0)\n" +
				"                     end) no_adjust_receive_amount, \n" +
				"     ( case when   tny.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量             \n" +
				"                          round(nvl(tny.store_coun,0) * nvl(tny.charge_standards,0) * (1 + nvl(tny.addition_rate,0) / 100) - tny.amount,0) --公式:店铺数量 * 收费标准 (应收金额（不含加成）*（1+加成比例）) (f=c*(1+加成比例))                            \n" +
				"                     else                           \n" +
				"                      round(nvl(tny.charge_standards,0) * (1 + nvl(tny.addition_rate,0) / 100) - tny.amount ,0)\n" +
				"                     end) adjust_receive_amount, \n" +
				"     2 as type \n" +
				"    from tta_npp_ytd tny where tny.ytd_id = 1\n" +
				"    and ( case when   tny.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量             \n" +
				"                          round(nvl(tny.store_coun,0) * nvl(tny.charge_standards,0) * (1 + nvl(tny.addition_rate,0) / 100) - tny.amount,0) --公式:店铺数量 * 收费标准 (应收金额（不含加成）*（1+加成比例）) (f=c*(1+加成比例))                            \n" +
				"                     else                           \n" +
				"                      round(nvl(tny.charge_standards,0) * (1 + nvl(tny.addition_rate,0) / 100) - tny.amount ,0)\n" +
				"                     end) != 0 ";
    	return sql;
	}

	public static String getUpdateReportOwn(String batchCode, Integer userId, Integer month) {
		return "update tta_npp_new_product_report ttc\n" +
				"   set ttc.purchase_act            = 'A11',--确认不收取\n" +
				"       ttc.exemption_reason    = 'NC01',--没有合同条款支持收取费用\n" +
				"       ttc.unconfirm_amount    = 0,--采购确认收取金额\n" +
				"       ttc.unconfirm_amount_a = 0 \n" +
				" where \n" +
				"   ttc.annual_month_n = " + month + " and ttc.batch_code = '" + batchCode + "'\n" +
				"   and ttc.group_desc = 'Own Brand'";
	}

	public static String getChangeVenderSql(String priorVendorCode, String ctw, String eb, Integer storeCoun, String groupCode, String year) {
		int thirdYearInt = Integer.parseInt(year) - 2;
		String thirdYear = String.valueOf(thirdYearInt);
    	String sql = "select \n" +
				"                  tb.major_supplier_code,\n" +
				"                  tb.addition_rate,\n" +
				"                  tb.charge_method,--收费方式\n" +
				"                  tb.charge_standards,\n" +
				"                  tb.proposal_year,\n" +
				"                  tb.proposal_id,\n" +
				"                  tb.major_dept_code,\n" +
				"                  tb.contract_terms, --合同条款\n" +
				"                   ( --应收金额(不含加成)\n" +
				"                       case     \n" +
				"                         when   tb.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量;3:按固定金额\n" +
				"                           \n" +
				"                           round(nvl(tb.store_coun,0) * nvl(tb.charge_standards,0),0) --公式:店铺数量 * 收费标准\n" +
				"\n" +
				"                         when  tb.charge_method = '1' or tb.charge_method = '3' then\n" +
				"                              round(nvl(tb.charge_standards,0),0) \n" +
				"                         else                           \n" +
				"                              0\n" +
				"                         end    \n" +
				"                ) receive_amount,  \n" +
				"                ( --应收金额(含加成)\n" +
				"                             case     \n" +
				"                             when   tb.charge_method = '2'   then   -- 2:按店铺数量; 1:按产品数量\n" +
				"                                                \n" +
				"                                    round(nvl(tb.store_coun,0) * nvl(tb.charge_standards,0) * (1 + nvl(tb.addition_rate,0) / 100),0) --公式:店铺数量 * 收费标准 (应收金额（不含加成）*（1+加成比例）) (f=c*(1+加成比例))\n" +
				"                                         \n" +
				"                             when  tb.charge_method = '1' or tb.charge_method = '3' then                           \n" +
				"                                   round(nvl(tb.charge_standards,0) * (1 + nvl(tb.addition_rate,0) / 100) ,0)\n" +
				"                              else\n" +
				"                                0     \n" +
				"                             end   \n" +
				"               ) receive_amount_add\n" +
				"            from (\n" +
				"           select \n" +
				"                rb.major_supplier_code,\n" +
				"                nvl(tcm.addition_rate,0) as addition_rate,\n" +
				"                tta_npp_server_count_value(\n" +
				"                                     '" + ctw + "',\n" +
				"                                     '" + eb + "',\n" +
				"                                     rb.proposal_id,\n" +
				"                                     " + groupCode + ",\n" +
				"                                     rb.major_dept_code,\n" +
				"                                     " + year + ",\n" +
				"                                     1\n" +
				"                                     ) charge_method, --收费方式 取值(1:按产品数量,2:按店铺数量,3:按固定金额)\n" +
				"                tta_npp_server_count_value(\n" +
				"                                     '" + ctw + "',\n" +
				"                                     '" + eb + "',\n" +
				"                                     rb.proposal_id,\n" +
				"                                     " + groupCode + ",\n" +
				"                                     rb.major_dept_code,\n" +
				"                                     " + year + ",\n" +
				"                                     2) as charge_standards, -- 收费标准\n" +
				"                " + storeCoun + " store_coun,\n" +
				"                rb.proposal_year,\n" +
				"                rb.proposal_id,\n" +
				"                rb.major_dept_code,\n" +
				"                tneh.current_year_term contract_terms --合同条款\n" +
				"           from (select major_supplier_code, proposal_year,proposal_id,major_dept_code\n" +
				"                   from (select ma.major_supplier_code, rv.proposal_year,rv.PROPOSAL_ID,rv.major_dept_code\n" +
				"                           from tta_proposal_header_new_version_v rv\n" +
				"                          inner join tta_supplier_major_v ma\n" +
				"                             on rv.vendor_nbr = ma.major_supplier_code\n" +
				"                          where rv.status = 'C'                       \n" +
				"                            and ma.supplier_code = '" + priorVendorCode + "'\n" +
				"                            and rv.proposal_year <='" + year + "'\n" +
				"                            and rv.proposal_year >='" + thirdYear + "'\n" +
				"                          order by rv.approve_date desc)\n" +
				"                  where rownum = 1) rb\n" +
				"                  left join tta_newbreed_extend_header tneh \n" +
				"                       on rb.proposal_id = tneh.proposal_id              \n" +
				"                  left join tta_contract_master tcm\n" +
				"                       on rb.major_supplier_code = tcm.vendor_nbr\n" +
				"              ) tb ";
    	return sql;
	}


	public void setNppId(Integer nppId) {
		this.nppId = nppId;
	}

	
	public Integer getNppId() {
		return nppId;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	
	public String getMonth() {
		return month;
	}

	public void setStoreCoun(Integer storeCoun) {
		this.storeCoun = storeCoun;
	}

	
	public Integer getStoreCoun() {
		return storeCoun;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	
	public String getItemDescCn() {
		return itemDescCn;
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

	public void setActivity(String activity) {
		this.activity = activity;
	}

	
	public String getActivity() {
		return activity;
	}

	public void setCtw(String ctw) {
		this.ctw = ctw;
	}

	
	public String getCtw() {
		return ctw;
	}

	public void setEb(String eb) {
		this.eb = eb;
	}

	
	public String getEb() {
		return eb;
	}

	public void setContractTerms(String contractTerms) {
		this.contractTerms = contractTerms;
	}

	
	public String getContractTerms() {
		return contractTerms;
	}

	public void setCollectWay(String collectWay) {
		this.collectWay = collectWay;
	}

	
	public String getCollectWay() {
		return collectWay;
	}

	public void setChargeStandards(BigDecimal chargeStandards) {
		this.chargeStandards = chargeStandards;
	}

	
	public BigDecimal getChargeStandards() {
		return chargeStandards;
	}

	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}

	
	public String getChargeUnit() {
		return chargeUnit;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	
	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setUnconfirmAmount(BigDecimal unconfirmAmount) {
		this.unconfirmAmount = unconfirmAmount;
	}

	
	public BigDecimal getUnconfirmAmount() {
		return unconfirmAmount;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	
	public BigDecimal getDifference() {
		return difference;
	}

	public void setSourceFilepath(String sourceFilepath) {
		this.sourceFilepath = sourceFilepath;
	}

	
	public String getSourceFilepath() {
		return sourceFilepath;
	}

	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	
	public String getSourceFilename() {
		return sourceFilename;
	}

	public void setCollectSelect(String collectSelect) {
		this.collectSelect = collectSelect;
	}

	
	public String getCollectSelect() {
		return collectSelect;
	}

	public void setPurchaseAct(String purchaseAct) {
		this.purchaseAct = purchaseAct;
	}

	
	public String getPurchaseAct() {
		return purchaseAct;
	}

	public void setExemptionReason(String exemptionReason) {
		this.exemptionReason = exemptionReason;
	}

	
	public String getExemptionReason() {
		return exemptionReason;
	}

	public void setExemptionReason2(String exemptionReason2) {
		this.exemptionReason2 = exemptionReason2;
	}

	
	public String getExemptionReason2() {
		return exemptionReason2;
	}

	public void setDebitOrderCode(String debitOrderCode) {
		this.debitOrderCode = debitOrderCode;
	}

	
	public String getDebitOrderCode() {
		return debitOrderCode;
	}

	public void setAboiReceipts(BigDecimal aboiReceipts) {
		this.aboiReceipts = aboiReceipts;
	}

	
	public BigDecimal getAboiReceipts() {
		return aboiReceipts;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	
	public String getBatchCode() {
		return batchCode;
	}

	public void setTransferInPerson(Integer transferInPerson) {
		this.transferInPerson = transferInPerson;
	}

	
	public Integer getTransferInPerson() {
		return transferInPerson;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentVendorCode(String parentVendorCode) {
		this.parentVendorCode = parentVendorCode;
	}

	
	public String getParentVendorCode() {
		return parentVendorCode;
	}

	public void setParentVendorName(String parentVendorName) {
		this.parentVendorName = parentVendorName;
	}

	
	public String getParentVendorName() {
		return parentVendorName;
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

	public void setTransferOutPerson(Integer transferOutPerson) {
		this.transferOutPerson = transferOutPerson;
	}

	
	public Integer getTransferOutPerson() {
		return transferOutPerson;
	}

	public void setTransferLastPerson(Integer transferLastPerson) {
		this.transferLastPerson = transferLastPerson;
	}

	
	public Integer getTransferLastPerson() {
		return transferLastPerson;
	}

	public void setTransferInDate(Date transferInDate) {
		this.transferInDate = transferInDate;
	}

	
	public Date getTransferInDate() {
		return transferInDate;
	}

	public void setTransferOutDate(Date transferOutDate) {
		this.transferOutDate = transferOutDate;
	}

	
	public Date getTransferOutDate() {
		return transferOutDate;
	}

	public void setTransferLastDate(Date transferLastDate) {
		this.transferLastDate = transferLastDate;
	}

	
	public Date getTransferLastDate() {
		return transferLastDate;
	}

	public void setChargeUnitName(String chargeUnitName) {
		this.chargeUnitName = chargeUnitName;
	}

	
	public String getChargeUnitName() {
		return chargeUnitName;
	}

	public void setOriginalBeforeAmount(BigDecimal originalBeforeAmount) {
		this.originalBeforeAmount = originalBeforeAmount;
	}

	
	public BigDecimal getOriginalBeforeAmount() {
		return originalBeforeAmount;
	}

	public void setAdditionRate(Integer additionRate) {
		this.additionRate = additionRate;
	}

	
	public Integer getAdditionRate() {
		return additionRate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
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

	public String getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	public BigDecimal getReceiveAmountAdd() {
		return receiveAmountAdd;
	}

	public void setReceiveAmountAdd(BigDecimal receiveAmountAdd) {
		this.receiveAmountAdd = receiveAmountAdd;
	}

	public String getChargeMethod() {
		return chargeMethod;
	}

	public void setChargeMethod(String chargeMethod) {
		this.chargeMethod = chargeMethod;
	}

	public Integer getAnnualMonthN() {
		return annualMonthN;
	}

	public void setAnnualMonthN(Integer annualMonthN) {
		this.annualMonthN = annualMonthN;
	}

	public Integer getMonthN() {
		return monthN;
	}

	public void setMonthN(Integer monthN) {
		this.monthN = monthN;
	}

	public BigDecimal getUnconfirmAmountA() {
		return unconfirmAmountA;
	}

	public void setUnconfirmAmountA(BigDecimal unconfirmAmountA) {
		this.unconfirmAmountA = unconfirmAmountA;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessStatusName() {
		return processStatusName;
	}

	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
	}

	public Integer getProcessReId() {
		return processReId;
	}

	public void setProcessReId(Integer processReId) {
		this.processReId = processReId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}

	public String getTransferInPersonName() {
		return transferInPersonName;
	}

	public void setTransferInPersonName(String transferInPersonName) {
		this.transferInPersonName = transferInPersonName;
	}

	public String getTransferOutPersonName() {
		return transferOutPersonName;
	}

	public void setTransferOutPersonName(String transferOutPersonName) {
		this.transferOutPersonName = transferOutPersonName;
	}

	public String getTransferLastPersonName() {
		return transferLastPersonName;
	}

	public void setTransferLastPersonName(String transferLastPersonName) {
		this.transferLastPersonName = transferLastPersonName;
	}

	public String getCollectWayName() {
		return collectWayName;
	}

	public void setCollectWayName(String collectWayName) {
		this.collectWayName = collectWayName;
	}

	public String getExemptionReasonName() {
		return exemptionReasonName;
	}

	public void setExemptionReasonName(String exemptionReasonName) {
		this.exemptionReasonName = exemptionReasonName;
	}

	public String getExemptionReason2Name() {
		return exemptionReason2Name;
	}

	public void setExemptionReason2Name(String exemptionReason2Name) {
		this.exemptionReason2Name = exemptionReason2Name;
	}

	public BigDecimal getAffirmTotStoreCount() {
		return affirmTotStoreCount;
	}

	public void setAffirmTotStoreCount(BigDecimal affirmTotStoreCount) {
		this.affirmTotStoreCount = affirmTotStoreCount;
	}

	public BigDecimal getAddReceiveAmount() {
		return addReceiveAmount;
	}

	public void setAddReceiveAmount(BigDecimal addReceiveAmount) {
		this.addReceiveAmount = addReceiveAmount;
	}

	public int getGroup_cnt() {
		return group_cnt;
	}

	public void setGroup_cnt(int group_cnt) {
		this.group_cnt = group_cnt;
	}

	public BigDecimal getDifferenceAmt() {
		return differenceAmt;
	}

	public void setDifferenceAmt(BigDecimal differenceAmt) {
		this.differenceAmt = differenceAmt;
	}

	public BigDecimal getReachRate() {
		return reachRate;
	}

	public void setReachRate(BigDecimal reachRate) {
		this.reachRate = reachRate;
	}

	public String getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}

    public Integer getAdjustBy() {
        return adjustBy;
    }

    public void setAdjustBy(Integer adjustBy) {
        this.adjustBy = adjustBy;
    }

    public String getAdjustByName() {
        return adjustByName;
    }

    public void setAdjustByName(String adjustByName) {
        this.adjustByName = adjustByName;
    }

    public BigDecimal getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(BigDecimal adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    public BigDecimal getAdjustReceiveAmount() {
        return adjustReceiveAmount;
    }

    public void setAdjustReceiveAmount(BigDecimal adjustReceiveAmount) {
        this.adjustReceiveAmount = adjustReceiveAmount;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getSubmitUserName() {
		return submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}

	public String getValueAll() {
		return valueAll;
	}

	public void setValueAll(String valueAll) {
		this.valueAll = valueAll;
	}

	public BigDecimal getNoAdjustReceiveAmount() {
		return noAdjustReceiveAmount;
	}

	public void setNoAdjustReceiveAmount(BigDecimal noAdjustReceiveAmount) {
		this.noAdjustReceiveAmount = noAdjustReceiveAmount;
	}

	public String getPriorVendorCode() {
		return priorVendorCode;
	}

	public void setPriorVendorCode(String priorVendorCode) {
		this.priorVendorCode = priorVendorCode;
	}

	public String getPriorVendorName() {
		return priorVendorName;
	}

	public void setPriorVendorName(String priorVendorName) {
		this.priorVendorName = priorVendorName;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
}
