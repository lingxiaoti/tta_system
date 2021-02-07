package com.sie.watsons.base.report.model.entities.readonly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaCwCheckingEntity_HI_RO Entity Object
 * Wed Nov 13 11:08:44 CST 2019  Auto Generate
 */

public class TtaCwCheckingEntity_HI_RO {

	public static final String  SELECT_LAST_CREATE_DATE = "select *\n" +
			"  from (select tcc.creation_date\n" +
			"          from tta_cw_checking tcc\n" +
			"         order by tcc.cw_id desc)\n" +
			" where rownum = 1 ";

	public static final String getCheckGroupCw() {
		String sql = "select " +
				"	 tcc.group_code as \"groupCode\"," +
				"	 count(tcc.cw_id) as \"cnt\"\n" +
				"  from tta_report_header trh\n" +
				" inner join tta_cw_checking tcc\n" +
				"    on trh.batch_id = tcc.batch_code\n" +
				" where trh.batch_id =:batchCode and nvl(tcc.receive_amount,0) != 0 and nvl(tcc.status,1) = 1 \n" +
				" group by tcc.group_code";
		return sql;
	}

	public static final String getCwProcessSummary(String batchId, String userCode, Integer operatorUserId) {
		String sql = "select  '" + batchId+ "' as batch_code,\n" +
				"       tcc.group_code,\n" +
				"       max(tcc.group_desc) as group_desc,\n" +
				"       sum(tcc.receive_amount) as receive_amount, -- 应收金额(含加成)\n" +
				"       sum(tcc.UNCONFIRM_AMOUNT) as unconfirm_amount, -- 需采购确认收取金额(含加成)\n" +
				"       sum(tcc.difference) as difference, -- 差异(含加成)\n" +
				"       round(nvl(sum(UNCONFIRM_AMOUNT),0)/nvl(sum(receive_amount),1),4)*100 as concludeRate, -- 达成率\n" +
				"       XMLAGG(XMLPARSE(CONTENT task.id_ || ',' WELLFORMED) ORDER BY task.id_).GETCLOBVAL() as task_ids,\n" +
				"		count(tcc.cw_id) as  group_cw_cnt\n" +
				"  from tta_report_header trh\n" +
				" inner join tta_cw_checking tcc\n" +
				"    on trh.batch_id = tcc.batch_code\n" +
				" inner join tta_report_process_header trph\n" +
				"    on tcc.process_id = trph.report_process_header_id\n" +
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
				" group by tcc.group_code";
		return sql;
	};

	public static final String getQueryProcess(String userCode, Integer operatorUserId) {
		String  query ="select \n" +
				"       tcc.*," +
				"       tcc.process_id  process_re_id,\n" +
				"		trph.batch_code process_code,\n" +
				"       trh.id,\n" +
				"       trh.status  header_status,\n" +
				"       lookup5.meaning exemptionReason2Name,\n" +
				"       lookup4.meaning exemptionReasonName,\n" +
				"decode(nvl(tcc.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
				"       lookup2.meaning processstatusname,\n" +
				"       bu_ad.user_full_name adjustByName\n" +
				"  from tta_cw_checking tcc\n" +
				" left join tta_report_process_header trph \n" +
				" on tcc.process_id = trph.report_process_header_id \n" +
				" inner join act_bpm_list bpm " +
				" on bpm.business_key = trph.report_process_header_id" +
				" inner join act_ru_task task " +
				" on bpm.proc_inst_id = task.proc_inst_id_" +
				"  left join tta_report_header trh\n" +
				"    on tcc.batch_code = trh.batch_id\n" +
				"  left join base_users bu_ad\n" +
				"    on tcc.adjust_By = bu_ad.user_id\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_CW_ACTION' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup3  on tcc.purchase = lookup3.lookup_code\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup3A  on tcc.purchase = lookup3A.lookup_code\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_CW_REASON' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup4  on tcc.exemption_Reason = lookup4.lookup_code\n" +

				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_CW_REASON1' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup5  on tcc.exemption_Reason2 = lookup5.lookup_code\n" +
				" where 1 = 1 " +
				"   and bpm.proc_def_key = 'TTA_ACTIVITY.-999' \n" +
				"	and tcc.status = 1 and tcc.process_id is not null \n" +
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
				"             and delegate.task_id = task.id_))";
		return query;
	};

	public static final String  ABOI_TEMPLATES_QUERY = "select \n" +
			"\n" +
			"tcc.prior_vendor_code, --供应商编号\n" +
			"tcc.brand_cn, --中文名称\n" +
			"tcc.dept_desc , --品类\n" +
			"null BuyerName,\n" +
			"tcc.content  , --活动\n" +
			"null  advanceCode,--代垫费用表编号\n" +
			"null  advanceAmount, --采购确认代垫费用\n" +
			"decode(sign(tcc.cw_year-Extract(year from sysdate)),-1,'*','') oldYearRecharge , --以前年度补收\n" +
			"tcc.promotion_start_date ,-- 促销开始日期\n" +
			"tcc.promotion_end_date , --促销结束日期\n" +
			"tcc.collect ,   --项目类型\n" +
			"null put_location1 ,--摆放位置1\n" +
			"'DisplayRental--Others' oi_project_code1 ,--OI项目编码1\n" +
			" decode(tcc.collect,'TTA','TTA-促销陈列服务费','')  contract_additional_remarks1, --合同补充说明1\n" +
			" tcc.unconfirm_amount  amount1,  -- 金额1  \n" +
			" null put_location2,\n" +
			" null oi_project_code2,\n" +
			" null contract_additional_remarks2,\n" +
			" null amount2              \n" +
			"from  \n" +
			"\n" +
			"tta_cw_checking tcc \n" +
			"\n" +
			"where 1=1  nvl(tcc.status,1) = 1  ";
	public static final String  SUMMARY_QUERY = "select tcc.group_desc,\n" +
			"       sum(nvl(tcc.RECEIVE_AMOUNT, 0)) chargeableBasedOnRateCard,\n" +
			"       sum(nvl(tcc.UNCONFIRM_AMOUNT, 0)) needBuyerFeedback,\n" +
			"       sum(case\n" +
			"             when nvl(tcc.PURCHASE, '-1') = 'A01' then --收取\n" +
			"              nvl(tcc.UNCONFIRM_AMOUNT, 0)\n" +
			"             else\n" +
			"              0\n" +
			"           end) ytdActualCharge,\n" +
			"       sum(case\n" +
			"             when nvl(tcc.PURCHASE, '-1') = 'A03' then --将收取\n" +
			"              nvl(tcc.UNCONFIRM_AMOUNT, 0)\n" +
			"             else\n" +
			"              0\n" +
			"           end) planToCharge,\n" +
			"       sum(case\n" +
			"             when nvl(tcc.PURCHASE, '-1') = 'A10' or\n" +
			"                  nvl(tcc.PURCHASE, '-1') = 'A11' then --折扣收取   确认不收取\n" +
			"              nvl(tcc.UNCONFIRM_AMOUNT, 0)\n" +
			"             else\n" +
			"              0\n" +
			"           end) toBeWaive\n" +
			"\n" +
			"  from tta_cw_checking tcc\n" +
			" where 1 = 1  nvl(tcc.status,1) = 1  ";
	public static final String  QUERY ="SELECT \n" +
			"TCC.CW_ID,\n" +
			"TCC.cw_year,\n" +
			"TCC.PROMOTION_SECTION,\n" +
			"TCC.PROMOTION_START_DATE,\n" +
			"TCC.PROMOTION_END_DATE,\n" +
			"TCC.PROMOTION_LOCATION,\n" +
			"TCC.TIME_DIMENSION,\n" +
			"TCC.STORES_NUM,\n" +
			"TCC.GROUP_DESC,\n" +
			"TCC.DEPT_DESC,\n" +
			"TCC.DEPT_CODE,\n" +
			"TCC.GROUP_CODE,\n" +
			"TCC.CONTENT,\n" +
			"TCC.ITEM_CODE,\n" +
			"TCC.CN_NAME,\n" +
			"TCC.BRAND_CN,\n" +
			"TCC.BRAND_EN,\n" +
			"TCC.PRIOR_VENDOR_CODE,\n" +
			"TCC.PRIOR_VENDOR_NAME,\n" +
			"TCC.CONTRACT_YEAR,\n" +
			"TCC.CONTRACT_STATUS,\n" +
			"TCC.CHARGE_STANDARDS,\n" +
			"TCC.CHARGE_MONEY,\n" +
			"TCC.CHARGE_UNIT,\n" +
			"TCC.RECEIVE_AMOUNT,\n" +
			"TCC.adjust_amount,\n" +
			"TCC.adjust_receive_amount,\n" +
			"TCC.no_adjust_amount,\n" +
			"TCC.no_adjust_receive_amount,\n" +
			"TCC.type,\n" +
			"TCC.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"TCC.ORIGINAL_AMOUNT,\n" +
			"TCC.UNCONFIRM_AMOUNT,\n" +
			"TCC.DIFFERENCE,\n" +
			"TCC.COLLECT,\n" +
			"TCC.PURCHASE,\n" +
			"TCC.EXEMPTION_REASON,\n" +
			"TCC.EXEMPTION_REASON2,\n" +
			"TCC.DEBIT_ORDER_CODE,\n" +
			"TCC.RECEIPTS,\n" +
			"TCC.BATCH_CODE,\n" +
			"TCC.Transfer_In_Person,\n" +
			"TCC.Transfer_Out_Person,\n" +
			"TCC.TRANSFER_LAST_PERSON,\n" +
			"TCC.Transfer_Last_Date,\n" +
			"tcc.transfer_in_date,\n" +
			"tcc.transfer_out_date,\n" +
			"TCC.REMARK,\n" +
			"TCC.STATUS,\n" +
			"TCC.PARENT_ID,\n" +
			"nvl(TCC.PARENT_VENDOR_CODE,tcc.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(TCC.PARENT_VENDOR_NAME,tcc.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"TCC.CREATION_DATE,\n" +
			"TCC.CREATED_BY,\n" +
			"TCC.LAST_UPDATED_BY,\n" +
			"TCC.LAST_UPDATE_DATE,\n" +
			"TCC.LAST_UPDATE_LOGIN,\n" +
			"trh.id,\n" +
			"TCC.addition_rate,\n" +
			"TCC.PROCESS_ID  processReId,\n" +
			"TCC.no_receive_amount,\n" +
			"TCC.no_unconfirm_amount,\n" +
			"TRPH.BATCH_CODE processCode,\n" +
			"TRPH.STATUS process_Status,\n" +
			"lookup5.meaning exemptionReason2Name,\n" +
			"lookup4.meaning exemptionReasonName,\n" +
			"decode(nvl(TCC.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
			"lookup2.meaning processStatusName,\n" +
			"lookup1.meaning  CHARGE_UNIT_NAME,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"trh.status headerStatus\n" +
			"\n" +
			"FROM  TTA_CW_CHECKING  TCC\n" +
			"LEFT JOIN tta_report_header trh ON TCC.batch_code = trh.batch_id\n" +
			"LEFT JOIN        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup1  ON TCC.CHARGE_UNIT = lookup1.lookup_code\n" +
			"LEFT JOIN TTA_REPORT_PROCESS_HEADER TRPH ON TCC.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID\n" +
			"LEFT JOIN        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  ON TRPH.STATUS = lookup2.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on TCC.purchase = lookup3.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3A  on TCC.purchase = lookup3A.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup4  on TCC.exemption_Reason = lookup4.lookup_code\n" +

			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON1' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup5  on TCC.exemption_Reason2 = lookup5.lookup_code\n" +
			"  left join base_users bu_ad\n" +
			"    on TCC.adjust_By = bu_ad.user_id\n" +
			" left join ( select a.business_key,\n" +
			"       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
			"       b.assignee_,\n" +
			"       bu.user_full_name as start_user_name\n" +
			"  from act_bpm_list a\n" +
			"  left join act_ru_task b\n" +
			"    on a.proc_inst_id = b.proc_inst_id_\n" +
			"   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
			"   left join base_person bp\n" +
			"   on bp.employee_number = b.assignee_\n" +
			"   left join base_users bu \n" +
			"   on bu.user_id = a.created_by \n" +
			"where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tcc.process_id  \n"  +
			"WHERE 1=1  and nvl(tcc.status,1) = 1  ";

	public static final String  QUERY_NO_BIC ="select * from  \n" +
			"    \n" +
			"   ( select \n" +
			"TCC.CW_ID,\n" +
			"TCC.cw_year,\n" +
			"TCC.PROMOTION_SECTION,\n" +
			"TCC.PROMOTION_START_DATE,\n" +
			"TCC.PROMOTION_END_DATE,\n" +
			"TCC.PROMOTION_LOCATION,\n" +
			"TCC.TIME_DIMENSION,\n" +
			"TCC.STORES_NUM,\n" +
			"TCC.GROUP_DESC,\n" +
			"TCC.DEPT_DESC,\n" +
			"TCC.DEPT_CODE,\n" +
			"TCC.GROUP_CODE,\n" +
			"TCC.CONTENT,\n" +
			"TCC.ITEM_CODE,\n" +
			"TCC.CN_NAME,\n" +
			"TCC.BRAND_CN,\n" +
			"TCC.BRAND_EN,\n" +
			"TCC.PRIOR_VENDOR_CODE,\n" +
			"TCC.PRIOR_VENDOR_NAME,\n" +
			"TCC.CONTRACT_YEAR,\n" +
			"TCC.CONTRACT_STATUS,\n" +
			"TCC.CHARGE_STANDARDS,\n" +
			"TCC.CHARGE_MONEY,\n" +
			"TCC.CHARGE_UNIT,\n" +
			"TCC.RECEIVE_AMOUNT,\n" +
			"TCC.adjust_amount,\n" +
			"TCC.adjust_receive_amount,\n" +
			"TCC.no_adjust_amount,\n" +
			"TCC.no_adjust_receive_amount,\n" +
			"TCC.type,\n" +
			"TCC.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"TCC.ORIGINAL_AMOUNT,\n" +
			"TCC.UNCONFIRM_AMOUNT,\n" +
			"TCC.DIFFERENCE,\n" +
			"TCC.COLLECT,\n" +
			"TCC.PURCHASE,\n" +
			"TCC.EXEMPTION_REASON,\n" +
			"TCC.EXEMPTION_REASON2,\n" +
			"TCC.DEBIT_ORDER_CODE,\n" +
			"TCC.RECEIPTS,\n" +
			"TCC.BATCH_CODE,\n" +
			"TCC.Transfer_In_Person,\n" +
			"TCC.Transfer_Out_Person,\n" +
			"TCC.TRANSFER_LAST_PERSON,\n" +
			"TCC.Transfer_Last_Date,\n" +
			"tcc.transfer_in_date,\n" +
			"tcc.transfer_out_date,\n" +
			"TCC.REMARK,\n" +
			"TCC.STATUS,\n" +
			"TCC.PARENT_ID,\n" +
			"nvl(TCC.PARENT_VENDOR_CODE,tcc.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(TCC.PARENT_VENDOR_NAME,tcc.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"TCC.CREATION_DATE,\n" +
			"TCC.CREATED_BY,\n" +
			"TCC.LAST_UPDATED_BY,\n" +
			"TCC.LAST_UPDATE_DATE,\n" +
			"TCC.LAST_UPDATE_LOGIN,\n" +
			"TCC.addition_rate, \n"  +
			"TCC.no_receive_amount,\n" +
			"TCC.no_unconfirm_amount,\n" +
			"   th.id id,\n" +
			"TCC.PROCESS_ID  processReId,\n" +
			"TRPH.BATCH_CODE processCode,\n" +
			"TRPH.STATUS process_Status,\n" +
			"lookup5.meaning exemptionReason2Name,\n" +
			"lookup4.meaning exemptionReasonName,\n" +
			"decode(nvl(TCC.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
			"lookup2.meaning processStatusName,\n" +
			"lookup1.meaning  CHARGE_UNIT_NAME,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"	th.status headerStatus\n" +
			"    from   \n" +
			"   tta_cw_checking tcc \n" +
			"    join (select tug.\"GROUP\" GROUP_CODE from tta_user_group_dept_brand_eft_v tug \n" +
			"         where nvl(tug.data_type,'0') = '1' \n" +
			"         and tug.user_id  =:userId\n" +
			"         and nvl(tug.\"GROUP\",'-99') !='-99'\n" +
			"         group by  tug.\"GROUP\" ) tugd on nvl(tcc.group_code,'-98') = tugd.GROUP_CODE \n" +
			"                                         and nvl(tcc.transfer_out_person,0) = 0\n" +
			"                                         and nvl(tcc.batch_code,'-1') = :batchCode\n" +
			"                                         and nvl(tcc.status,1) = 1\n" +
			"                                         and ( nvl(tcc.receive_amount,0) != 0 or nvl(tcc.adjust_receive_amount,0) != 0 )\n" +
			"     									  and tcc.group_desc != 'Own Brand'\n" +
			"    join tta_report_header th    on tcc.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                       \n" +
			"    left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup1 on tcc.CHARGE_UNIT = lookup1.lookup_code\n" +
			" LEFT JOIN TTA_REPORT_PROCESS_HEADER TRPH ON TCC.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID\n" +
			"LEFT JOIN        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  ON TRPH.STATUS = lookup2.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on TCC.purchase = lookup3.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3A  on TCC.purchase = lookup3A.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup4  on TCC.exemption_Reason = lookup4.lookup_code\n" +

			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON1' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup5  on TCC.exemption_Reason2 = lookup5.lookup_code\n" +
			"     left join base_users bu_ad on tcc.adjust_By = bu_ad.user_id\n" +
			" left join ( select a.business_key,\n" +
			"       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
			"       b.assignee_,\n" +
			"       bu.user_full_name as start_user_name\n" +
			"  from act_bpm_list a\n" +
			" left join act_ru_task b\n" +
			"    on a.proc_inst_id = b.proc_inst_id_\n" +
			"   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
			"   left join base_person bp\n" +
			"   on bp.employee_number = b.assignee_\n" +
			"   left join base_users bu \n" +
			"   on bu.user_id = a.created_by \n" +
			" where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tcc.process_id  \n"  +
			"    \n" +
			"    union all\n" +
			"    \n" +
			"    select \n" +
			"TCC.CW_ID,\n" +
			"TCC.cw_year,\n" +
			"TCC.PROMOTION_SECTION,\n" +
			"TCC.PROMOTION_START_DATE,\n" +
			"TCC.PROMOTION_END_DATE,\n" +
			"TCC.PROMOTION_LOCATION,\n" +
			"TCC.TIME_DIMENSION,\n" +
			"TCC.STORES_NUM,\n" +
			"TCC.GROUP_DESC,\n" +
			"TCC.DEPT_DESC,\n" +
			"TCC.DEPT_CODE,\n" +
			"TCC.GROUP_CODE,\n" +
			"TCC.CONTENT,\n" +
			"TCC.ITEM_CODE,\n" +
			"TCC.CN_NAME,\n" +
			"TCC.BRAND_CN,\n" +
			"TCC.BRAND_EN,\n" +
			"TCC.PRIOR_VENDOR_CODE,\n" +
			"TCC.PRIOR_VENDOR_NAME,\n" +
			"TCC.CONTRACT_YEAR,\n" +
			"TCC.CONTRACT_STATUS,\n" +
			"TCC.CHARGE_STANDARDS,\n" +
			"TCC.CHARGE_MONEY,\n" +
			"TCC.CHARGE_UNIT,\n" +
			"TCC.RECEIVE_AMOUNT,\n" +
			"TCC.adjust_amount,\n" +
			"TCC.adjust_receive_amount,\n" +
			"TCC.no_adjust_amount,\n" +
			"TCC.no_adjust_receive_amount,\n" +
			"TCC.type,\n" +
			"TCC.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"TCC.ORIGINAL_AMOUNT,\n" +
			"TCC.UNCONFIRM_AMOUNT,\n" +
			"TCC.DIFFERENCE,\n" +
			"TCC.COLLECT,\n" +
			"TCC.PURCHASE,\n" +
			"TCC.EXEMPTION_REASON,\n" +
			"TCC.EXEMPTION_REASON2,\n" +
			"TCC.DEBIT_ORDER_CODE,\n" +
			"TCC.RECEIPTS,\n" +
			"TCC.BATCH_CODE,\n" +
			"TCC.Transfer_In_Person,\n" +
			"TCC.Transfer_Out_Person,\n" +
			"TCC.TRANSFER_LAST_PERSON,\n" +
			"TCC.Transfer_Last_Date,\n" +
			"tcc.transfer_in_date,\n" +
			"tcc.transfer_out_date,\n" +
			"TCC.REMARK,\n" +
			"TCC.STATUS,\n" +
			"TCC.PARENT_ID,\n" +
			"nvl(TCC.PARENT_VENDOR_CODE,tcc.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(TCC.PARENT_VENDOR_NAME,tcc.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"TCC.CREATION_DATE,\n" +
			"TCC.CREATED_BY,\n" +
			"TCC.LAST_UPDATED_BY,\n" +
			"TCC.LAST_UPDATE_DATE,\n" +
			"TCC.LAST_UPDATE_LOGIN,\n" +
			"TCC.addition_rate, \n" +
			"TCC.no_receive_amount,\n" +
			"TCC.no_unconfirm_amount,\n" +
			"   th.id id,\n" +
			"TCC.PROCESS_ID  processReId,\n" +
			"TRPH.BATCH_CODE processCode,\n" +
			"TRPH.STATUS process_Status,\n" +

			"lookup5.meaning exemptionReason2Name,\n" +
			"lookup4.meaning exemptionReasonName,\n" +
			"decode(nvl(TCC.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
			"lookup2.meaning processStatusName,\n" +
			"	lookup1.meaning  CHARGE_UNIT_NAME,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"	th.status headerStatus\n" +
			"   \n" +
			"   from   \n" +
			"    tta_cw_checking tcc \n" +
			"    join  (select tug.\"GROUP\" GROUP_CODE ,tug.dept dept_code from tta_user_group_dept_brand_eft_v tug \n" +
			"         where nvl(tug.data_type,'0') = '2' \n" +
			"         and tug.user_id  =:userId\n" +
			"         and nvl(tug.\"GROUP\",'-99') !='-99'\n" +
			"         and nvl(tug.dept,'-96') !='-96'\n" +
			"         group by  tug.\"GROUP\",tug.dept) tugd on  nvl(tcc.GROUP_CODE,'-98') = tugd.GROUP_CODE \n" +
			"                                         and nvl(tcc.dept_code,'-97') = tugd.dept_code\n" +
			"                                         and nvl(tcc.transfer_out_person,0) = 0\n" +
			"                                         and nvl(tcc.batch_code,'-1') = :batchCode \n" +
			"                                         and nvl(tcc.status,1) = 1\n" +
			"                                         and (nvl(tcc.receive_amount,0) != 0 or nvl(tcc.adjust_receive_amount,0) != 0 )\n" +
			"     									  and tcc.group_desc != 'Own Brand'\n" +
			"    join tta_report_header th    on tcc.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                                                               \n" +
			"    left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup1 on tcc.CHARGE_UNIT = lookup1.lookup_code\n" +
			"LEFT JOIN TTA_REPORT_PROCESS_HEADER TRPH ON TCC.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID\n" +
			"LEFT JOIN        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  ON TRPH.STATUS = lookup2.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on TCC.purchase = lookup3.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3A  on TCC.purchase = lookup3A.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup4  on TCC.exemption_Reason = lookup4.lookup_code\n" +

			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON1' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup5  on TCC.exemption_Reason2 = lookup5.lookup_code\n" +
			"     left join base_users bu_ad on tcc.adjust_By = bu_ad.user_id\n" +
			" left join ( select a.business_key,\n" +
			"       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
			"       b.assignee_,\n" +
			"       bu.user_full_name as start_user_name\n" +
			"  from act_bpm_list a\n" +
			" left join act_ru_task b\n" +
			"    on a.proc_inst_id = b.proc_inst_id_\n" +
			"   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
			"   left join base_person bp\n" +
			"   on bp.employee_number = b.assignee_\n" +
			"   left join base_users bu \n" +
			"   on bu.user_id = a.created_by \n" +
			"where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tcc.process_id  \n"  +
			"    \n" +
			"    union all\n" +
			"    \n" +
			"    select \n" +
			 "TCC.CW_ID,\n" +
			"TCC.cw_year,\n" +
			"TCC.PROMOTION_SECTION,\n" +
			"TCC.PROMOTION_START_DATE,\n" +
			"TCC.PROMOTION_END_DATE,\n" +
			"TCC.PROMOTION_LOCATION,\n" +
			"TCC.TIME_DIMENSION,\n" +
			"TCC.STORES_NUM,\n" +
			"TCC.GROUP_DESC,\n" +
			"TCC.DEPT_DESC,\n" +
			"TCC.DEPT_CODE,\n" +
			"TCC.GROUP_CODE,\n" +
			"TCC.CONTENT,\n" +
			"TCC.ITEM_CODE,\n" +
			"TCC.CN_NAME,\n" +
			"TCC.BRAND_CN,\n" +
			"TCC.BRAND_EN,\n" +
			"TCC.PRIOR_VENDOR_CODE,\n" +
			"TCC.PRIOR_VENDOR_NAME,\n" +
			"TCC.CONTRACT_YEAR,\n" +
			"TCC.CONTRACT_STATUS,\n" +
			"TCC.CHARGE_STANDARDS,\n" +
			"TCC.CHARGE_MONEY,\n" +
			"TCC.CHARGE_UNIT,\n" +
			"TCC.RECEIVE_AMOUNT,\n" +
			"TCC.adjust_amount,\n" +
			"TCC.adjust_receive_amount,\n" +
			"TCC.no_adjust_amount,\n" +
			"TCC.no_adjust_receive_amount,\n" +
			"TCC.type,\n" +
			"TCC.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"TCC.ORIGINAL_AMOUNT,\n" +
			"TCC.UNCONFIRM_AMOUNT,\n" +
			"TCC.DIFFERENCE,\n" +
			"TCC.COLLECT,\n" +
			"TCC.PURCHASE,\n" +
			"TCC.EXEMPTION_REASON,\n" +
			"TCC.EXEMPTION_REASON2,\n" +
			"TCC.DEBIT_ORDER_CODE,\n" +
			"TCC.RECEIPTS,\n" +
			"TCC.BATCH_CODE,\n" +
			"TCC.Transfer_In_Person,\n" +
			"TCC.Transfer_Out_Person,\n" +
			"TCC.TRANSFER_LAST_PERSON,\n" +
			"TCC.Transfer_Last_Date,\n" +
			"tcc.transfer_in_date,\n" +
			"tcc.transfer_out_date,\n" +
			"TCC.REMARK,\n" +
			"TCC.STATUS,\n" +
			"TCC.PARENT_ID,\n" +
			"nvl(TCC.PARENT_VENDOR_CODE,tcc.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(TCC.PARENT_VENDOR_NAME,tcc.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"TCC.CREATION_DATE,\n" +
			"TCC.CREATED_BY,\n" +
			"TCC.LAST_UPDATED_BY,\n" +
			"TCC.LAST_UPDATE_DATE,\n" +
			"TCC.LAST_UPDATE_LOGIN,\n" +
			"TCC.addition_rate,"+
			"TCC.no_receive_amount,\n" +
			"TCC.no_unconfirm_amount,\n" +
			"   th.id id,\n" +
			"TCC.PROCESS_ID  processReId,\n" +
			"TRPH.BATCH_CODE processCode,\n" +
			"TRPH.STATUS process_Status,\n" +

			"lookup5.meaning exemptionReason2Name,\n" +
			"lookup4.meaning exemptionReasonName,\n" +
			"decode(nvl(TCC.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
			"lookup2.meaning processStatusName,\n" +
			"	lookup1.meaning  CHARGE_UNIT_NAME,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"	th.status headerStatus\n" +
			"   \n" +
			"   from   \n" +
			"    tta_cw_checking tcc \n" +
			"    join  (select tug.\"GROUP\" GROUP_CODE ,tug.dept dept_code,tug.brand_cn from tta_user_group_dept_brand_eft_v tug \n" +
			"         where nvl(tug.data_type,'0') = '3' \n" +
			"         and tug.user_id  =:userId\n" +
			"         and nvl(tug.\"GROUP\",'-99') !='-99'\n" +
			"         and nvl(tug.dept,'-96') !='-96'\n" +
			"         and nvl(tug.brand_cn,'-95') != '-95'\n" +
			"         group by  tug.\"GROUP\",tug.dept,brand_cn) tugd     on nvl(tcc.GROUP_CODE,'-98') = tugd.GROUP_CODE \n" +
			"                                         and nvl(tcc.dept_code,'-97') = tugd.dept_code\n" +
			"                                         and nvl(tcc.brand_cn,'-93') = tugd.brand_cn\n" +
			"                                         and nvl(tcc.transfer_out_person,0) = 0\n" +
			"                                         and nvl(tcc.batch_code,'-1') =:batchCode \n" +
			"                                         and nvl(tcc.status,1) = 1\n" +
			"                                         and (nvl(tcc.receive_amount,0) != 0 or nvl(tcc.adjust_receive_amount,0) != 0 )      \n" +
			"     									  and tcc.group_desc != 'Own Brand'\n" +
			"    join tta_report_header th    on tcc.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                                                       \n" +
			"    left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup1 on tcc.CHARGE_UNIT = lookup1.lookup_code\n" +
			"LEFT JOIN TTA_REPORT_PROCESS_HEADER TRPH ON TCC.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID\n" +
			"LEFT JOIN        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  ON TRPH.STATUS = lookup2.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on TCC.purchase = lookup3.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3A  on TCC.purchase = lookup3A.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup4  on TCC.exemption_Reason = lookup4.lookup_code\n" +

			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON1' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup5  on TCC.exemption_Reason2 = lookup5.lookup_code\n" +
			"     left join base_users bu_ad on tcc.adjust_By = bu_ad.user_id\n" +
			" left join ( select a.business_key,\n" +
			"       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
			"       b.assignee_,\n" +
			"       bu.user_full_name as start_user_name\n" +
			"  from act_bpm_list a\n" +
			" left join act_ru_task b\n" +
			"    on a.proc_inst_id = b.proc_inst_id_\n" +
			"   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
			"   left join base_person bp\n" +
			"   on bp.employee_number = b.assignee_\n" +
			"   left join base_users bu \n" +
			"   on bu.user_id = a.created_by \n" +
			" where a.proc_def_key = 'TTA_ACTIVITY.-999') process on process.BUSINESS_KEY = tcc.process_id  \n"  +
			"    \n" +
			"    union all\n" +
			"                                         \n" +
			"    select \n" +
			"TCC.CW_ID,\n" +
			"TCC.cw_year,\n" +
			"TCC.PROMOTION_SECTION,\n" +
			"TCC.PROMOTION_START_DATE,\n" +
			"TCC.PROMOTION_END_DATE,\n" +
			"TCC.PROMOTION_LOCATION,\n" +
			"TCC.TIME_DIMENSION,\n" +
			"TCC.STORES_NUM,\n" +
			"TCC.GROUP_DESC,\n" +
			"TCC.DEPT_DESC,\n" +
			"TCC.DEPT_CODE,\n" +
			"TCC.GROUP_CODE,\n" +
			"TCC.CONTENT,\n" +
			"TCC.ITEM_CODE,\n" +
			"TCC.CN_NAME,\n" +
			"TCC.BRAND_CN,\n" +
			"TCC.BRAND_EN,\n" +
			"TCC.PRIOR_VENDOR_CODE,\n" +
			"TCC.PRIOR_VENDOR_NAME,\n" +
			"TCC.CONTRACT_YEAR,\n" +
			"TCC.CONTRACT_STATUS,\n" +
			"TCC.CHARGE_STANDARDS,\n" +
			"TCC.CHARGE_MONEY,\n" +
			"TCC.CHARGE_UNIT,\n" +
			"TCC.RECEIVE_AMOUNT,\n" +
			"TCC.adjust_amount,\n" +
			"TCC.adjust_receive_amount,\n" +
			"TCC.no_adjust_amount,\n" +
			"TCC.no_adjust_receive_amount,\n" +
			"TCC.type,\n" +
			"TCC.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"TCC.ORIGINAL_AMOUNT,\n" +
			"TCC.UNCONFIRM_AMOUNT,\n" +
			"TCC.DIFFERENCE,\n" +
			"TCC.COLLECT,\n" +
			"TCC.PURCHASE,\n" +
			"TCC.EXEMPTION_REASON,\n" +
			"TCC.EXEMPTION_REASON2,\n" +
			"TCC.DEBIT_ORDER_CODE,\n" +
			"TCC.RECEIPTS,\n" +
			"TCC.BATCH_CODE,\n" +
			"TCC.Transfer_In_Person,\n" +
			"TCC.Transfer_Out_Person,\n" +
			"TCC.TRANSFER_LAST_PERSON,\n" +
			"TCC.Transfer_Last_Date,\n" +
			"tcc.transfer_in_date,\n" +
			"tcc.transfer_out_date,\n" +
			"TCC.REMARK,\n" +
			"TCC.STATUS,\n" +
			"TCC.PARENT_ID,\n" +
			"nvl(TCC.PARENT_VENDOR_CODE,tcc.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(TCC.PARENT_VENDOR_NAME,tcc.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"TCC.CREATION_DATE,\n" +
			"TCC.CREATED_BY,\n" +
			"TCC.LAST_UPDATED_BY,\n" +
			"TCC.LAST_UPDATE_DATE,\n" +
			"TCC.LAST_UPDATE_LOGIN,\n" +
			"TCC.addition_rate,\n"+
			"TCC.no_receive_amount,\n" +
			"TCC.no_unconfirm_amount,\n" +
			"   th.id id,\n" +
			"TCC.PROCESS_ID  processReId,\n" +
			"TRPH.BATCH_CODE processCode,\n" +
			"TRPH.STATUS process_Status,\n" +

			"lookup5.meaning exemptionReason2Name,\n" +
			"lookup4.meaning exemptionReasonName,\n" +
			"decode(nvl(TCC.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +			"lookup2.meaning processStatusName,\n" +
			"	lookup1.meaning  CHARGE_UNIT_NAME,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"	th.status headerStatus\n" +
			"     from \n" +
			"    \n" +
			"    tta_cw_checking tcc\n" +
			"    join tta_report_header th    on tcc.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y' \n" +
			"    left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup1 on tcc.CHARGE_UNIT = lookup1.lookup_code\n" +
			"LEFT JOIN TTA_REPORT_PROCESS_HEADER TRPH ON TCC.PROCESS_ID = TRPH.REPORT_PROCESS_HEADER_ID\n" +
			"LEFT JOIN        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  ON TRPH.STATUS = lookup2.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on TCC.purchase = lookup3.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3A  on TCC.purchase = lookup3A.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup4  on TCC.exemption_Reason = lookup4.lookup_code\n" +

			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_CW_REASON1' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup5  on TCC.exemption_Reason2 = lookup5.lookup_code\n" +
			"     left join base_users bu_ad on tcc.adjust_By = bu_ad.user_id\n" +
			" left join ( select a.business_key,\n" +
			"       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
			"       b.assignee_,\n" +
			"       bu.user_full_name as start_user_name\n" +
			"  from act_bpm_list a\n" +
			"  left join act_ru_task b\n" +
			"    on a.proc_inst_id = b.proc_inst_id_\n" +
			"   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
			"   left join base_person bp\n" +
			"   on bp.employee_number = b.assignee_\n" +
			"   left join base_users bu \n" +
			"   on bu.user_id = a.created_by \n" +
			"where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tcc.process_id  \n"  +
			"    \n" +
			"     where nvl(tcc.transfer_in_person,0) =:userId \n" +
			"     and nvl(tcc.batch_code,'-1') =:batchCode\n" +
			"     and nvl(tcc.status,1) = 1\n" +
			"     and ( nvl(tcc.receive_amount,0) != 0 or nvl(tcc.adjust_receive_amount,0) != 0 )   \n" +
			"     and tcc.group_desc != 'Own Brand'\n" +
			"    ) tcc_no_bic where 1=1";

	public static final String  QUERY_EXISTS = "SELECT " +
			"tom.group_code," +
			"tom.group_desc," +
			"tom.dept_code," +
			"tom.dept_desc," +
			"tom.brand_cn ," +
			"tom.group_code||'-'||tom.dept_code||'-'||tom.brand_cn  valueAll "  +
			"FROM tta_cw_checking tom\n" +
			"\n" +
			"where tom.batch_code = :batchCode\n" +
			"and  \n" +
			"not exists (SELECT 1 FROM tta_user_group_dept_brand_eft_v tug where (tug.data_type = '1' and tug.\"GROUP\" = tom.group_code)\n" +
			"                                                              or (tug.data_type = '2' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code)\n" +
			"                                                              or (tug.data_type = '3' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code and tug.brand_cn = tom.brand_cn)  )\n" +
			"                                                              \n" +
			"";


	public static String getInsertReportBase(String batchCode,Integer userId,String ps,Integer year,String dateString){
		return "insert  into tta_cw_checking tcc\n" +
				"(      tcc.cw_id,\n" +
				"       tcc.promotion_section,\n" +
				"       tcc.promotion_start_date,\n" +
				"       tcc.promotion_end_date,\n" +
				"       tcc.promotion_location,\n" +
				"       tcc.time_dimension,\n" +
				"       tcc.stores_num,\n" +
				"       tcc.group_desc,\n" +
				"       tcc.dept_desc,\n" +
				"       tcc.dept_code,\n" +
				"       tcc.group_code,\n" +
				"       tcc.content,\n" +
				"       tcc.item_code,\n" +
				"       tcc.cn_name,\n" +
				"       tcc.brand_cn,\n" +
				"       tcc.brand_en,\n" +
				"       tcc.prior_vendor_code,\n" +
				"       tcc.prior_vendor_name,\n" +
				"       tcc.contract_year,\n" +
				"       tcc.contract_status,\n" +
				"       tcc.charge_standards,\n" +
				"       tcc.charge_money,\n" +
				"       tcc.charge_unit,\n" +
				"       tcc.receive_amount,\n" +
				"       tcc.original_amount,\n" +
				"       tcc.unconfirm_amount,\n" +
				"       tcc.difference,\n" +
				"       tcc.collect,\n" +
				"       tcc.status,\n" +
				"       tcc.creation_date,\n" +
				"       tcc.created_by,\n" +
				"       tcc.last_updated_by,\n" +
				"       tcc.last_update_date,\n" +
				"       tcc.last_update_login,\n" +
				"       tcc.addition_rate,\n" +
				"       tcc.cw_year,\n" +
				"       tcc.company_dept_code,\n" +
				"       tcc.company_dept_name,\n" +
				"       tcc.batch_code\n" +
				" )                                \n" +
				"                              \n" +
				"select \n" +
				"seq_tta_cw_checking.nextval,\n" +
				"startV.promotion_section ,                        --促销区间\n" +
				"startV.promotion_start_date ,                      --促销开始日期\n" +
				"startV.promotion_end_date ,                        --促销结束日期\n" +
				"startV.promotion_location ,                        --促销位置\n" +
				"startV.time_dimension,                            --周\n" +
				"startV.stores_num ,                                --店铺数量\n" +
				"startV.GROUP_DESC,             --GROUP_DESC\n" +
				"startV.dept_desc   DEPT_DESC ,                     --DEPT_DESC\n" +
				"startV.Dept_Code   DEPT_CODE,                      -- DEPT_CODE\n" +
				"startV.Group_Code  GROUP_CODE ,                     --GROUP_CODE\n" +
				"startV.\"CONTENT\",  --活动内容\n" +
				"startV.item_code    ,--产品编号\n" +
				"startV.CN_NAME,     --中文名称\n" +
				"startV.Brand_Cn      ,\n" +
				"startV.Brand_En     ,\n" +
				"startV.prior_vendor_code,            --供应商编号\n" +
				"startV.prior_vendor_name,            --供应商名称\n" +
				"startV.CONTRACT_YEAR,                --合同年份\n" +
				"startV.CONTRACT_STATUS ,                   --合同状态\n" +
				"tta_oi_dept_fee_count(startV.Group_code,\n" +
				"                      startV.dept_code1,\n" +
				"                      startV.dept_code2,\n" +
				"                      startV.dept_code3,\n" +
				"                      tfddv.standard_value,\n" +
				"                      startV.standard_value1,\n" +
				"                      startV.standard_value2,\n" +
				"                      startV.standard_value3,\n" +
				"                      tfddv.unit,\n" +
				"                      startV.unit1,\n" +
				"                      startV.unit2,\n" +
				"                      startV.unit3,\n" +
				"                      startV.old_dept_code1,\n" +
				"                      startV.old_dept_code2,\n" +
				"                      startV.old_dept_code3,\n" +
				"                      startV.old_Unit_Value,\n" +
				"                      startV.old_standard_value1,\n" +
				"                      startV.old_standard_value2,\n" +
				"                      startV.old_standard_value3,\n" +
				"                      startV.old_unit,\n" +
				"                      startV.old_unit1,\n" +
				"                      startV.old_unit2,\n" +
				"                      startV.old_unit3,\n" +
				"                      1                     \n" +
				"                      )  charge_standards, --收费标准\n" +
				"tta_oi_dept_fee_count(startV.Group_code,\n" +
				"                      startV.dept_code1,\n" +
				"                      startV.dept_code2,\n" +
				"                      startV.dept_code3,\n" +
				"                      tfddv.standard_value,\n" +
				"                      startV.standard_value1,\n" +
				"                      startV.standard_value2,\n" +
				"                      startV.standard_value3,\n" +
				"                      tfddv.unit,\n" +
				"                      startV.unit1,\n" +
				"                      startV.unit2,\n" +
				"                      startV.unit3,\n" +
				"                      startV.old_dept_code1,\n" +
				"                      startV.old_dept_code2,\n" +
				"                      startV.old_dept_code3,\n" +
				"                      startV.old_Unit_Value,\n" +
				"                      startV.old_standard_value1,\n" +
				"                      startV.old_standard_value2,\n" +
				"                      startV.old_standard_value3,\n" +
				"                      startV.old_unit,\n" +
				"                      startV.old_unit1,\n" +
				"                      startV.old_unit2,\n" +
				"                      startV.old_unit3,\n" +
				"                      2                     \n" +
				"                      )  charge_money,  --计费金额\n" +
				"tta_oi_dept_fee_count(startV.Group_code,\n" +
				"                      startV.dept_code1,\n" +
				"                      startV.dept_code2,\n" +
				"                      startV.dept_code3,\n" +
				"                      tfddv.standard_value,\n" +
				"                      startV.standard_value1,\n" +
				"                      startV.standard_value2,\n" +
				"                      startV.standard_value3,\n" +
				"                      tfddv.unit,\n" +
				"                      startV.unit1,\n" +
				"                      startV.unit2,\n" +
				"                      startV.unit3,\n" +
				"                      startV.old_dept_code1,\n" +
				"                      startV.old_dept_code2,\n" +
				"                      startV.old_dept_code3,\n" +
				"                      startV.old_Unit_Value,\n" +
				"                      startV.old_standard_value1,\n" +
				"                      startV.old_standard_value2,\n" +
				"                      startV.old_standard_value3,\n" +
				"                      startV.old_unit,\n" +
				"                      startV.old_unit1,\n" +
				"                      startV.old_unit2,\n" +
				"                      startV.old_unit3,\n" +
				"                      3                    \n" +
				"                      )  charge_unit,  --计费单位\n" +
				"    null receive_amount, --应收金额\n" +
				"    null original_amount , --原应收金额 \n" +
				"    null unconfirm_amount ,-- 采购确认金额\n" +
				"    null DIFFERENCE ,  --差异    \n" +
				"    'TTA'            \"COLLECT\" ,   --收取方式\n" +
				"    1         status,--状态  \n" +
				"  startV.creation_date,\n" +
				userId+"         created_by,\n" +
				userId  +      " last_updated_by,\n" +
				"  startV.last_update_date,\n" +
				userId+" last_update_login,\n" +
				" startV.addition_rate ,\n" +
				"  startV.POG_YEAR,\n" +
				"  tfdhv.dept_code,\n" +
				"  startV.dept_name,\n" +
				"'" +batchCode +"'" +"\n" +
				"from \n" +
				"(\n" +
				"SELECT \n" +
				"tpsl.promotion_section ,                        --促销区间\n" +
				"tpsl.promotion_start_date ,                      --促销开始日期\n" +
				"tpsl.promotion_end_date ,                        --促销结束日期\n" +
				"tpsl.promotion_location ,                        --促销位置\n" +
				"tpsl.time_dimension,                            --周\n" +
				"tpsl.stores_num ,                                --店铺数量\n" +
				"nvl(tpiw.vendor_nbr,tiuv.VENDOR_NBR)   prior_vendor_code,            --供应商编号\n" +
				"ts.supplier_name  prior_vendor_name,            --供应商名称\n" +
				"tcm.contract_date CONTRACT_YEAR,                --合同年份\n" +
				"tcm.status  CONTRACT_STATUS ,                   --合同状态\n" +
				"tta_six_action_fun (tcm.ower_dept_no,tiuv.Group_Desc,tiuv.Group_Code,'1') GROUP_DESC,             --GROUP_DESC\n" +
				"tta_six_action_fun (tcm.ower_dept_no,tiuv.Group_Desc,tiuv.Group_Code,'0') NO_GROUP_DESC,             --GROUP_DESC\n" +
				"tiuv.dept_desc   DEPT_DESC ,                     --DEPT_DESC\n" +
				"tiuv.Dept_Code   DEPT_CODE,                      -- DEPT_CODE\n" +
				"tiuv.Group_Code  GROUP_CODE ,                     --GROUP_CODE\n" +
				"('OSD'||'-'||tpsl.promotion_section||'-'||tpsl.promotion_location||'-'||\n" +
				"tta_six_action_fun (tcm.ower_dept_no,tiuv.Group_Desc,tiuv.Group_Code,'1')||'-'||\n" +
				"tiuv.Brand_Cn || '-' ||nvl(tpiw.vendor_nbr,tiuv.VENDOR_NBR) )  \"CONTENT\",  --活动内容\n" +
				"tpsl.item_code    ,--产品编号\n" +
				"toss.dept_item_no,\n" +
				"tph.dept_code1,\n" +
				"tph.dept_code2,\n" +
				"tph.dept_code3,\n" +
				"tdf.standard_value1,\n" +
				"tdf.standard_value2,\n" +
				"tdf.standard_value3,\n" +
				"tdf.unit1,\n" +
				"tdf.unit2,\n" +
				"tdf.unit3,\n" +
				"rownum v,\n" +
				"null old_dept_code1,\n" +
				"null old_dept_code2,\n" +
				"null old_dept_code3,\n" +
				"null old_Unit_Value,\n" +
				"null old_standard_value1,\n" +
				"null old_standard_value2,\n" +
				"null old_standard_value3,\n" +
				"null old_unit,\n" +
				"null old_unit1,\n" +
				"null old_unit2,\n" +
				"null old_unit3,\n" +
				"tpsl.dept_name,\n" +
				"tpsl.POG_YEAR,\n" +
				"tiuv.Brand_Cn    BRAND_CN  ,\n" +
				"tiuv.Brand_En    BRAND_EN ,\n" +
				"tcm.addition_rate,\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')    last_update_date,\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss') creation_date,\n" +
				"tiuv.Item_Desc_Cn CN_NAME    --中文名称\n" +
				"\n" +
				" FROM \n" +
				"tta_pog_space_line tpsl \n" +
				"left join \n" +
				"(  select item_nbr,vendor_nbr,receive_date from \n" +
				"    (select tpi.item_nbr,tpi.vendor_nbr,tpi.receive_date, row_number() \n" +
				"    over(partition by tpi.item_nbr order by tpi.receive_date desc ) rn\n" +
				"    from tta_purchase_in_"+year+" tpi ) where rn=1 \n" +
				") tpiw on tpsl.item_code = tpiw.item_nbr\n" +
				"left join tta_item_unique_v tiuv on  tiuv.Item_Nbr = tpsl.item_code\n" +
				"left join tta_supplier ts on nvl(tpiw.vendor_nbr,tiuv.VENDOR_NBR) = ts.supplier_code\n" +
				"left join tta_contract_master tcm on ts.supplier_code = tcm.vendor_nbr\n" +

				"left join tta_osd_sales_site toss on toss.sales_year = "+year+" and toss.sales_site = 'C'||chr(38)||'W'\n" +
				"left join (\n" +
					"Select t2.*\n" +
				"  From (select tphn.*,\n" +
				"               ma.SUPPLIER_CODE MAJOR_SUPPLIER_CODE,\n" +
				"               Row_Number() Over(Partition By ma.SUPPLIER_CODE Order By tphn.approve_date desc, tphn.PROPOSAL_YEAR desc) Row_Id\n" +
				"          from tta_proposal_header_new_version_v tphn\n" +
				"          left join tta_supplier_major_v ma\n" +
				"            on tphn.VENDOR_NBR = ma.MAJOR_SUPPLIER_CODE\n" +
				"         where tphn.status = 'C'\n" +
				"           and tphn.approve_date is not null\n" +
				"           and tphn.proposal_year <= " + year + "\n" +
				"           and tphn.proposal_year >= " + (year - 1) + "         \n" +
				"           ) t2\n" +
				" Where T2.Row_Id = 1\n" +
				") tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE \n" +
				"left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = toss.dept_item_no\n" +
				//"left join tta_proposal_header oldTpo on tph.last_year_proposal_id = oldTpo.proposal_id\n" +
				//"left join  tta_dept_fee oldTdf on  oldTpo.Proposal_Id =   oldTdf.Proposal_Id and oldTdf.line_code = toss.dept_item_no\n" +
				") startV                                   \n" +
				"left join (  select * from \n" +
				"    (select tfdh.*, row_number() \n" +
				"    over(partition by tfdh.dept_desc,tfdh.Year_Code order by tfdh.Creation_Date desc ) rn\n" +
				"    from tta_fee_dept_header tfdh where tfdh.status != 'F' AND NVL(tfdh.ACCORD_TYPE,'-1') = 'A'  ) where rn=1 \n" +
				") tfdhv on tfdhv.year_code = "+year+" and tfdhv.dept_desc = startV.dept_name\n" +
				"left join tta_fee_dept_line tfdl on tfdl.line_code = startV.dept_item_no and tfdl.feedept_id = tfdhv.feedept_id\n" +
				"left join \n" +
				"(  select * from \n" +
				"    (select tfdd.*, row_number() \n" +
				"    over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
				"    from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
				") tfddv\n" +
				"on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code =  startV.Group_code\n" +
				"where 1=1 and startV.promotion_section = '" + ps+"'\n"  ;
		/**
		 * "  Select t2.*\n" +
		 * 				"    From (Select tph.*,ma.SUPPLIER_CODE MAJOR_SUPPLIER_CODE,\n" +
		 * 				"                 Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc ) Row_Id\n" +
		 * 				"            From tta_proposal_header tph,\n" +
		 * 				"            tta_supplier_major_v ma\n" +
		 * 				"           where tph.status = 'C' and tph.approve_date is not null and tph.proposal_year <="+year+" and tph.proposal_year >="+(year -2)+" and  ma.MAJOR_SUPPLIER_CODE = tph.vendor_nbr) T2\n" +
		 * 				"   Where T2.Row_Id = 1 \n"
		 */
	}

	//2020.10.22注释
	/*public static final String  QUERY_UNIT_AMOUNT = "                    SELECT \n" +
			"\n" +
			"                    ts.supplier_code,\n" +
			"                    ts.supplier_name,\n" +
			"                    tph.order_nbr,\n" +
			"                    tph.proposal_year,\n" +
			"                    tph.vendor_nbr,\n" +
			"                   tph.vendor_name,\n" +
			"                    tta_oi_dept_fee_count(:groupCode,\n" +
			"                                          tph.dept_code1,\n" +
			"                                          tph.dept_code2,\n" +
			"                                          tph.dept_code3,\n" +
			"                                          tfddv.standard_value,\n" +
			"                                          tdf.standard_value1,\n" +
			"                                          tdf.standard_value2,\n" +
			"                                          tdf.standard_value3,\n" +
			"                                          tfddv.unit,\n" +
			"                                          tdf.unit1,\n" +
			"                                          tdf.unit2,\n" +
			"                                          tdf.unit3,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          1                     \n" +
			"                                          )  charge_standards, --收费标准\n" +
			"                    tta_oi_dept_fee_count(:groupCode,\n" +
			"                                          tph.dept_code1,\n" +
			"                                          tph.dept_code2,\n" +
			"                                          tph.dept_code3,\n" +
			"                                          tfddv.standard_value,\n" +
			"                                          tdf.standard_value1,\n" +
			"                                          tdf.standard_value2,\n" +
			"                                          tdf.standard_value3,\n" +
			"                                          tfddv.unit,\n" +
			"                                          tdf.unit1,\n" +
			"                                          tdf.unit2,\n" +
			"                                          tdf.unit3,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          2                     \n" +
			"                                          )  charge_money,  --计费金额\n" +
			"                    tta_oi_dept_fee_count(:groupCode,\n" +
			"                                          tph.dept_code1,\n" +
			"                                          tph.dept_code2,\n" +
			"                                          tph.dept_code3,\n" +
			"                                          tfddv.standard_value,\n" +
			"                                          tdf.standard_value1,\n" +
			"                                          tdf.standard_value2,\n" +
			"                                          tdf.standard_value3,\n" +
			"                                          tfddv.unit,\n" +
			"                                          tdf.unit1,\n" +
			"                                          tdf.unit2,\n" +
			"                                          tdf.unit3,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          3                    \n" +
			"                                          )  charge_unit,  --计费单位\n" +
			"\n" +
			"                     nvl(tcm.addition_rate,0) addition_rate\n" +
			"\n" +
			"                     FROM \n" +
			"                    tta_supplier ts \n" +
			"                    left join tta_contract_master tcm on ts.supplier_code = tcm.vendor_nbr\n" +
			"                    left join tta_osd_sales_site toss on toss.sales_year = :year and toss.sales_site = 'C'||chr(38)||'W'\n" +
			"                    left join (   Select t2.*\n" +
			"                        From (Select tph.*,ma.MAJOR_SUPPLIER_CODE ,\n" +
			"                                     Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc ) Row_Id\n" +
			"                                From tta_proposal_header tph,\n" +
			"                               ( SELECT * FROM  tta_supplier_major_v tsm where tsm.MAJOR_SUPPLIER_CODE = :supplierCode) ma\n" +
			"                               where tph.status = 'C' and tph.approve_date is not null and tph.proposal_year <= :year and tph.proposal_year >=:oldYear and  ma.SUPPLIER_CODE = tph.vendor_nbr\n" +
			"                                    ) T2\n" +
			"                       Where T2.Row_Id = 1 \n" +
			"                    ) tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE \n" +
			"                    left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = toss.dept_item_no\n" +
			"                   left join (SELECT feedept_id FROM tta_fee_dept_header tfdh where tfdh.year_code = :year and tfdh.dept_code = '05' and  NVL(tfdh.ACCORD_TYPE,'-1') = 'A' and tfdh.status = 'C'  )  tfdhv on 1=1\n" +
			"                    left join tta_fee_dept_line tfdl on tfdl.line_code = toss.dept_item_no and tfdl.feedept_id = tfdhv.feedept_id\n" +
			"                    left join \n" +
			"                    (  select * from \n" +
			"                        (select tfdd.*, row_number() \n" +
			"                        over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
			"                        from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
			"                    ) tfddv\n" +
			"                    on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code = :groupCode\n" +
			"                    where 1=1 \n" +
			"                    \n" +
			"                    and ts.supplier_code = :supplierCode\n" +
			"                    \n" +
			"\n" +
			" ";*/

	public static final String QUERY_UNIT_AMOUNT = "select \n" +
			"                    tcc.proposal_year,\n" +
			"                    tcc.charge_standards,\n" +
			"                    tcc.charge_money,\n" +
			"                    tcc.charge_unit,\n" +
			"                    tcc.addition_rate,\n" +
			"                    (select \n" +
			"                    ( case     \n" +
			"                     when   instr(meaning,'店')>0   then   \n" +
			"                       round(nvl(:storesNum,0) * nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
			"                     else                           \n" +
			"                      round(nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
			"                     end    ) \n" +
			"                     from base_lookup_values where \n" +
			"                                lookup_type='UNIT' \n" +
			"                                and enabled_flag='Y'\n" +
			"                                and delete_flag=0 \n" +
			"                                and start_date_active<sysdate \n" +
			"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
			"                                and system_code='BASE'\n" +
			"                                and lookup_code = tcc.charge_unit\n" +
			"                      ) receive_amount, --应收金额(含加成)\n" +
			"                   (select \n" +
			"                    ( case     \n" +
			"                     when   instr(meaning,'店')>0   then   \n" +
			"                       round(nvl(:storesNum,0) * nvl(tcc.charge_money,0),0)\n" +
			"                     else                           \n" +
			"                      round(nvl(tcc.charge_money,0),0)\n" +
			"                     end    )\n" +
			"                     from base_lookup_values where \n" +
			"                                lookup_type='UNIT' \n" +
			"                                and enabled_flag='Y'\n" +
			"                                and delete_flag=0 \n" +
			"                                and start_date_active<sysdate \n" +
			"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
			"                                and system_code='BASE'\n" +
			"                                and lookup_code = tcc.charge_unit\n" +
			"                     ) no_receive_amount, \n" +
			"                   (select \n" +
			"                    ( case     \n" +
			"                     when   instr(meaning,'店')>0   then   \n" +
			"                       round(nvl(:storesNum,0) * nvl(tcc.charge_money,0),0)\n" +
			"                     else                           \n" +
			"                      round(nvl(tcc.charge_money,0),0)\n" +
			"                     end    )\n" +
			"                     from base_lookup_values where \n" +
			"                                lookup_type='UNIT' \n" +
			"                                and enabled_flag='Y'\n" +
			"                                and delete_flag=0 \n" +
			"                                and start_date_active<sysdate \n" +
			"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
			"                                and system_code='BASE'\n" +
			"                                and lookup_code = tcc.charge_unit\n" +
			"                     ) original_amount, \n" +
			"      \n" +
			"      \n" +
			"                   (select \n" +
			"                    ( case     \n" +
			"                     when   instr(meaning,'店')>0   then   \n" +
			"                       round(nvl(:storesNum,0) * nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
			"                     else                           \n" +
			"                      round(nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
			"                     end    )\n" +
			"                     from base_lookup_values where \n" +
			"                                lookup_type='UNIT' \n" +
			"                                and enabled_flag='Y'\n" +
			"                                and delete_flag=0 \n" +
			"                                and start_date_active<sysdate \n" +
			"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
			"                                and system_code='BASE'\n" +
			"                                and lookup_code = tcc.charge_unit\n" +
			"                     ) unconfirm_amount, \n" +
			"            \n" +
			"                   (select \n" +
			"                    ( case     \n" +
			"                     when   instr(meaning,'店')>0   then   \n" +
			"                       round(nvl(:storesNum,0) * nvl(tcc.charge_money,0),0)\n" +
			"                     else                           \n" +
			"                      round(nvl(tcc.charge_money,0),0)\n" +
			"                     end    )\n" +
			"                     from base_lookup_values where \n" +
			"                                lookup_type='UNIT' \n" +
			"                                and enabled_flag='Y'\n" +
			"                                and delete_flag=0 \n" +
			"                                and start_date_active<sysdate \n" +
			"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
			"                                and system_code='BASE'\n" +
			"                                and lookup_code = tcc.charge_unit\n" +
			"                     ) no_unconfirm_amount, \n" +
			"                   (select \n" +
			"                    ( case     \n" +
			"                     when   instr(meaning,'店')>0   then   \n" +
			"                       round(nvl(:storesNum,0) * nvl(tcc.charge_money,0),0)\n" +
			"                     else                           \n" +
			"                      round(nvl(tcc.charge_money,0),0)\n" +
			"                     end    )\n" +
			"                     from base_lookup_values where \n" +
			"                                lookup_type='UNIT' \n" +
			"                                and enabled_flag='Y'\n" +
			"                                and delete_flag=0 \n" +
			"                                and start_date_active<sysdate \n" +
			"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
			"                                and system_code='BASE'\n" +
			"                                and lookup_code = tcc.charge_unit\n" +
			"              ) original_before_amount,\n" +
			"            (select meaning\n" +
			"                    from base_lookup_values where \n" +
			"                                lookup_type='UNIT' \n" +
			"                                and enabled_flag='Y'\n" +
			"                                and delete_flag=0 \n" +
			"                                and start_date_active<sysdate \n" +
			"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
			"                                and system_code='BASE'\n" +
			"                                and lookup_code = tcc.charge_unit\n" +
			"              ) charge_unit_name\n" +
			"                   from \n" +
			"                    (\n" +
			"                    SELECT \n" +
			"                    ts.supplier_code,\n" +
			"                    ts.supplier_name,\n" +
			"                    tph.order_nbr,\n" +
			"                    tph.proposal_year,\n" +
			"                    tph.vendor_nbr,\n" +
			"                    tph.vendor_name,\n" +
			"                    tta_oi_dept_fee_count(:groupCode,\n" +
			"                                          tph.dept_code1,\n" +
			"                                          tph.dept_code2,\n" +
			"                                          tph.dept_code3,\n" +
			"                                          tfddv.standard_value,\n" +
			"                                          tdf.standard_value1,\n" +
			"                                          tdf.standard_value2,\n" +
			"                                          tdf.standard_value3,\n" +
			"                                          tfddv.unit,\n" +
			"                                          tdf.unit1,\n" +
			"                                          tdf.unit2,\n" +
			"                                          tdf.unit3,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          1                     \n" +
			"                                          )  charge_standards, --收费标准\n" +
			"                    tta_oi_dept_fee_count(:groupCode,\n" +
			"                                          tph.dept_code1,\n" +
			"                                          tph.dept_code2,\n" +
			"                                          tph.dept_code3,\n" +
			"                                          tfddv.standard_value,\n" +
			"                                          tdf.standard_value1,\n" +
			"                                          tdf.standard_value2,\n" +
			"                                          tdf.standard_value3,\n" +
			"                                          tfddv.unit,\n" +
			"                                          tdf.unit1,\n" +
			"                                          tdf.unit2,\n" +
			"                                          tdf.unit3,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          2                     \n" +
			"                                          )  charge_money,  --计费金额\n" +
			"                    tta_oi_dept_fee_count(:groupCode,\n" +
			"                                          tph.dept_code1,\n" +
			"                                          tph.dept_code2,\n" +
			"                                          tph.dept_code3,\n" +
			"                                          tfddv.standard_value,\n" +
			"                                          tdf.standard_value1,\n" +
			"                                          tdf.standard_value2,\n" +
			"                                          tdf.standard_value3,\n" +
			"                                          tfddv.unit,\n" +
			"                                          tdf.unit1,\n" +
			"                                          tdf.unit2,\n" +
			"                                          tdf.unit3,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          null,\n" +
			"                                          3                    \n" +
			"                                          )  charge_unit,  --计费单位\n" +
			"\n" +
			"                     nvl(tcm.addition_rate,0) addition_rate\n" +
			"\n" +
			"                     FROM \n" +
			"                    tta_supplier ts \n" +
			"                    left join tta_contract_master tcm on ts.supplier_code = tcm.vendor_nbr\n" +
			"                    left join tta_osd_sales_site toss on toss.sales_year = :year and toss.sales_site = 'C'||chr(38)||'W'\n" +
			"                    left join (   Select t2.*\n" +
			"                        From (Select tph.*,ma.MAJOR_SUPPLIER_CODE ,\n" +
			"                                     Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc ) Row_Id\n" +
			"                                From tta_proposal_header tph,\n" +
			"                               ( SELECT * FROM  tta_supplier_major_v tsm where tsm.MAJOR_SUPPLIER_CODE = :supplierCode) ma\n" +
			"                               where tph.status = 'C' and tph.approve_date is not null and tph.proposal_year <= :year and tph.proposal_year >=:oldYear and  ma.SUPPLIER_CODE = tph.vendor_nbr\n" +
			"                                    ) T2\n" +
			"                       Where T2.Row_Id = 1 \n" +
			"                    ) tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE \n" +
			"                    left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = toss.dept_item_no\n" +
			"                   left join (SELECT feedept_id FROM tta_fee_dept_header tfdh where tfdh.year_code = :year and tfdh.dept_code = '05' and  NVL(tfdh.ACCORD_TYPE,'-1') = 'A' and tfdh.status = 'C'  )  tfdhv on 1=1\n" +
			"                    left join tta_fee_dept_line tfdl on tfdl.line_code = toss.dept_item_no and tfdl.feedept_id = tfdhv.feedept_id\n" +
			"                    left join \n" +
			"                    (  select * from \n" +
			"                        (select tfdd.*, row_number() \n" +
			"                        over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
			"                        from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
			"                    ) tfddv\n" +
			"                    on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code = :groupCode\n" +
			"                    where 1=1 \n" +
			"                    \n" +
			"                    and ts.supplier_code = :supplierCode\n" +
			"                    ) tcc ";


	public static String getUpdateReportVendorCod(Integer userId,String ps,String batchCode){
		return  "        update   tta_cw_checking  ttc  set  ttc.team_code = \n" +
				"    nvl((SELECT ts.supplier_code FROM tta_rel_supplier trs ,\n" +
				"                      tta_supplier ts\n" +
				"    where trs.rel_supplier_code = ttc.prior_vendor_code\n" +
				"        and trs.rel_id = ts.supplier_id and rownum =1),ttc.prior_vendor_code)" +
				" where  ttc.promotion_section = '"+ps+"' and ttc.batch_code = '" +batchCode+ "'" ;

	};

	public static String getUpdateReportOwn(Integer userId,String ps,String batchCode) {
		return  "        update   tta_cw_checking  ttc  set ttc.PURCHASE = 'A11', ttc.EXEMPTION_REASON = 'NC01' ,ttc.unconfirm_amount = 0,ttc.no_unconfirm_amount = 0\n" +
				" where  ttc.promotion_section = '"+ps+"' and ttc.batch_code = '" +batchCode+ "' and ttc.group_desc  = 'Own Brand'" ;
	};


	public static String getUpdateReportBase(Integer userId,String ps,String batchCode){
		return  "update  tta_cw_checking tcc \n" +
				"set \n" +
				"tcc.receive_amount = (select \n" +
				"                    ( case     \n" +
				"                     when   instr(meaning,'店')>0   then   \n" +
				"                       round(nvl(tcc.stores_num,0) * nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
				"                     else                           \n" +
				"                      round(nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
				"                     end    )\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tcc.charge_unit\n" +
				"      ), \n" +
				"tcc.no_receive_amount = (select \n" +
				"                    ( case     \n" +
				"                     when   instr(meaning,'店')>0   then   \n" +
				"                       round(nvl(tcc.stores_num,0) * nvl(tcc.charge_money,0),0)\n" +
				"                     else                           \n" +
				"                      round(nvl(tcc.charge_money,0),0)\n" +
				"                     end    )\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tcc.charge_unit\n" +
				"      ), \n" +
				"tcc.original_amount = (select \n" +
				"                    ( case     \n" +
				"                     when   instr(meaning,'店')>0   then   \n" +
				"                       round(nvl(tcc.stores_num,0) * nvl(tcc.charge_money,0),0)\n" +
				"                     else                           \n" +
				"                      round(nvl(tcc.charge_money,0),0)\n" +
				"                     end    )\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tcc.charge_unit\n" +
				"      ), \n" +
				"      \n" +
				"      \n" +
				"tcc.unconfirm_amount  = (select \n" +
				"                    ( case     \n" +
				"                     when   instr(meaning,'店')>0   then   \n" +
				"                       round(nvl(tcc.stores_num,0) * nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
				"                     else                           \n" +
				"                      round(nvl(tcc.charge_money,0)*(100 +nvl(tcc.addition_rate,0))/100,0)\n" +
				"                     end    )\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tcc.charge_unit\n" +
				"      ), \n" +
				"            \n" +
				"tcc.no_unconfirm_amount  = (select \n" +
				"                    ( case     \n" +
				"                     when   instr(meaning,'店')>0   then   \n" +
				"                       round(nvl(tcc.stores_num,0) * nvl(tcc.charge_money,0),0)\n" +
				"                     else                           \n" +
				"                      round(nvl(tcc.charge_money,0),0)\n" +
				"                     end    )\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tcc.charge_unit\n" +
				"      ), \n" +
				"tcc.original_before_amount  = (select \n" +
				"                    ( case     \n" +
				"                     when   instr(meaning,'店')>0   then   \n" +
				"                       round(nvl(tcc.stores_num,0) * nvl(tcc.charge_money,0),0)\n" +
				"                     else                           \n" +
				"                      round(nvl(tcc.charge_money,0),0)\n" +
				"                     end    )\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tcc.charge_unit\n" +
				"      ), \n" +
				"tcc.difference = 0 , \n" +
				"tcc.charge_unit_name =       (select meaning\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tcc.charge_unit\n" +
				"      )   \n" +
				"where exists\n" +
				"(select 1 from (  select tccvv.cw_id from \n" +
				"    (select tccv.cw_id, row_number() \n" +
				"    over(partition by tccv.team_code order by nvl((select \n" +
				"                    ( case     \n" +
				"                     when   instr(meaning,'店')>0   then   \n" +
				"                       round(nvl(tccv.stores_num,0) * nvl(tccv.charge_money,0),0)\n" +
				"                     else                           \n" +
				"                      round(nvl(tccv.charge_money,0),0)\n" +
				"                     end    )\n" +
				"        from base_lookup_values where \n" +
				"                                lookup_type='UNIT' \n" +
				"                                and enabled_flag='Y'\n" +
				"                                and delete_flag=0 \n" +
				"                                and start_date_active<sysdate \n" +
				"                                and (end_date_active>=sysdate or end_date_active is null) \n" +
				"                                and system_code='BASE'\n" +
				"                                and lookup_code = tccv.charge_unit\n" +
				"      ),0) desc ) rn\n" +
				"    from tta_cw_checking tccv where "+"tccv.promotion_section = '"+ps+"' and tccv.batch_code = '" +batchCode+ "'"+"  ) tccvv where tccvv.rn=1 and tccvv.cw_id =  tcc.cw_id\n" +
				")   ) \n" +
				"and tcc.promotion_section = '"+ps+"' and tcc.batch_code = '" +batchCode+ "'";

	}

	public static String getUpdateReportBasePog(Integer userId,String ps){
		return  "update tta_pog_space_line  tpsl set tpsl.is_create = 'Y' where tpsl.promotion_section ='"+ps+"'"  ;
	}

	public static String getUpdateReportBaseLastTimes(String batchCode,Integer userId,String ps,String dateString){
		return  "   insert  into tta_cw_checking  tcc(\n" +
				"       tcc.cw_id,\n" +
				"       tcc.promotion_section,\n" +
				"       tcc.promotion_start_date,\n" +
				"       tcc.promotion_end_date,\n" +
				"       tcc.promotion_location,\n" +
				"       tcc.time_dimension,\n" +
				"       tcc.stores_num,\n" +
				"       tcc.group_desc,\n" +
				"       tcc.dept_desc,\n" +
				"       tcc.dept_code,\n" +
				"       tcc.group_code,\n" +
				"       tcc.content,\n" +
				"       tcc.item_code,\n" +
				"       tcc.cn_name,\n" +
				"       tcc.brand_cn,\n" +
				"       tcc.brand_en,\n" +
				"       tcc.prior_vendor_code,\n" +
				"       tcc.prior_vendor_name,\n" +
				"       tcc.contract_year,\n" +
				"       tcc.contract_status,\n" +
				"       tcc.charge_standards,\n" +
				"       tcc.charge_money,\n" +
				"       tcc.charge_unit,\n" +
				"       tcc.receive_amount,\n" +
				"       tcc.NO_RECEIVE_AMOUNT,\n" +
				"       tcc.original_amount,\n" +
				"       tcc.unconfirm_amount,\n" +
				"       tcc.no_unconfirm_amount,\n" +
				"       tcc.difference,\n" +
				"       tcc.collect,\n" +
				"       tcc.status,\n" +
				"       tcc.creation_date,\n" +
				"       tcc.created_by,\n" +
				"       tcc.last_updated_by,\n" +
				"       tcc.last_update_date,\n" +
				"       tcc.last_update_login,\n" +
				"       tcc.addition_rate,\n" +
				"       tcc.cw_year,\n" +
				"       tcc.from_where,\n" +
				"       tcc.PROPOSAL_ORDER_NO,\n" +
				"       tcc.COMPANY_DEPT_NAME,\n" +
				"       tcc.PURCHASE,\n" +
				"       tcc.EXEMPTION_REASON,\n" +
				"       tcc.EXEMPTION_REASON2,\n" +
				"       tcc.NO_ADJUST_AMOUNT,\n" +
				"       tcc.NO_ADJUST_RECEIVE_AMOUNT,\n" +
				"       tcc.batch_code )\n" +
				"\n" +
				"\n" +
				"SELECT\n" +
				"       seq_tta_cw_checking.nextval,\n" +
				"       tcch.promotion_section,\n" +
				"       tcch.promotion_start_date,\n" +
				"       tcch.promotion_end_date,\n" +
				"       tcch.promotion_location,\n" +
				"       tcch.time_dimension,\n" +
				"       tcch.stores_num,\n" +
				"       tcch.group_desc,\n" +
				"       tcch.dept_desc,\n" +
				"       tcch.dept_code,\n" +
				"       tcch.group_code,\n" +
				"       tcch.content,\n" +
				"       tcch.item_code,\n" +
				"       tcch.cn_name,\n" +
				"       tcch.brand_cn,\n" +
				"       tcch.brand_en,\n" +
				"       tcch.prior_vendor_code,\n" +
				"       tcch.prior_vendor_name,\n" +
				"       tcch.contract_year,\n" +
				"       tcch.contract_status,\n" +
				"       tcch.charge_standards,\n" +
				"       tcch.charge_money,\n" +
				"       tcch.charge_unit,\n" +
				"       tcch.receive_amount,\n" +
				"       tcch.NO_RECEIVE_AMOUNT,\n" +
				"       tcch.original_amount,\n" +
				"       tcch.unconfirm_amount,\n" +
				"       tcch.no_unconfirm_amount,\n" +
				"       tcch.difference,\n" +
				"       tcch.collect,\n" +
				"       tcch.status,\n" +
				"       to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				"       tcch.created_by,\n" +
				"       tcch.last_updated_by,\n" +
				"       to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				"       tcch.last_update_login,\n" +
				"       tcch.addition_rate,\n" +
				"       tcch.cw_year,\n" +
				"       to_char(tcch.creation_date,'yyyymm'),\n" +
				"       tcch.PROPOSAL_ORDER_NO,\n" +
				"       tcch.COMPANY_DEPT_NAME,\n" +
				"       tcch.PURCHASE,\n" +
				"       tcch.EXEMPTION_REASON,\n" +
				"       tcch.EXEMPTION_REASON2,\n" +
				"       tcch.NO_ADJUST_AMOUNT,\n" +
				"       tcch.NO_ADJUST_RECEIVE_AMOUNT,\n" +
				"'" +batchCode +"'" +"\n" +
				"\n" +
				"\n" +
				"FROM  tta_cw_checking  tcch \n" +
				" inner join tta_report_header trh on tcch.batch_code = trh.batch_id\n" +
				"where tcch.purchase = 'A03' and trh.is_publish = 'Y' and nvl(tcch.status,1) = 1 -- 已发布,将收取\n" +
				"and tcch.creation_date < to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')\n" +
				"AND exists\n" +
				"(\n" +
				"SELECT 1 FROM (\n" +
				"SELECT max(tcc.creation_date) creation_date FROM    tta_cw_checking   tcc \n" +
				" inner join tta_report_header th on tcc.batch_code = th.batch_id and th.is_publish = 'Y' and nvl(tcc.status,1) = 1\n" +
				"	where tcc.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'))\n" +
                " tccb where to_char(tccb.creation_date,'yyyy-mm-dd') = to_char(tcch.creation_date,'yyyy-mm-dd'))"  ;
                //" tccb where tccb.creation_date = tcch.creation_date)"  ;
	}


	public static String getUpdateReportBaseHistory(Integer userId,String ps){
		return  "insert into \n" +
				"\n" +
				"tta_cw_checking_record\n" +
				"\n" +
				"SELECT * FROM tta_cw_checking tcc\n" +
				"\n" +
		      "where  tcc.promotion_section = '"+ps+"'";
	}

	public static String insertRecord(Integer userId,int ytdId){
		return  "insert into tta_cw_ytd_record\n" +
				"SELECT * FROM tta_cw_ytd tcy where tcy.ytd_id = "+ytdId;
	}

	public static String deleteCurYtd(Integer userId,int ytdId){
		return  " delete from tta_cw_ytd tcy where tcy.ytd_id =  "+ytdId;
	}

	public static String getInsertCwYtd(Integer userId,String ps,int ytdId,String dateString,String startDateString,String defaultDateString ){
		return  "insert into TTA_CW_YTD toy\n" +
				"(      toy.vendor_nbr,\n" +
				"       toy.PROPOSAL_ORDER_NO,\n" +
				"       toy.promotion_location,\n" +
				"       toy.amount,\n" +
				"       toy.no_amount,\n" +
				"       toy.stores_num,\n" +
				"       toy.group_code,\n" +
				"       toy.GROUP_DESC,\n" +
				"       toy.dept_code,\n" +
				"       toy.DEPT_DESC,\n" +
				"       toy.brand_cn,\n" +
				"       toy.brand_en,\n" +
				"       toy.ytd_id,\n" +
				"       toy.creation_date,\n" +
				"       toy.created_by,\n" +
				"       toy.last_updated_by,\n" +
				"       toy.last_update_date,\n" +
				"       toy.proposal_year,\n" +
				"       toy.company_dept_name,\n" +
				"       toy.company_dept_code,\n" +
				"       toy.promotion_section,\n" +
				"       toy.proposal_vendor_nbr,\n" +
				"       toy.vendor_name,\n" +
				"       toy.item_code,\n" +
				"       toy.cn_name,\n" +
				"       toy.content,\n" +
				"       toy.proposal_id\n" +
				"       )\n" +
				"SELECT \n" +
				"tom.prior_vendor_code,\n" +
				"max(tph.order_nbr),\n" +
				"tom.promotion_location,\n" +
				"sum(nvl(tom.receive_amount,0) + nvl(tom.adjust_receive_amount,0)) amount,\n" +
				"sum(nvl(tom.no_receive_amount,0) + nvl(tom.no_adjust_receive_amount,0)) amount,\n" +
				"sum(case " +
				"     when nvl(tom.type,'0') = '0' then " +
				"    nvl(tom.stores_num,0) " +
				"    else 0  end )  stores_num,\n" +
				"tom.group_code,\n" +
				"max(tom.GROUP_DESC),\n" +
				"tom.dept_code,\n" +
				"max(tom.DEPT_DESC),\n" +
				"tom.brand_cn,\n" +
				"tom.brand_en,\n" +
				ytdId + ",\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				userId + ",\n" +
				userId + ",\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				"max(tph.proposal_year),\n" +
				"max(tom.company_dept_name),\n" +
				"max(tom.company_dept_code),\n" +
				"tom.promotion_section,\n" +
				"max(tph.vendor_nbr),\n" +
				"max(tom.prior_vendor_name),\n" +
				"tom.item_code,\n" +
				"max(tom.cn_name),\n" +
				"max(tom.content),\n" +
				"max(tph.proposal_id)" +
				" FROM \n" +
				" (   Select t2.*\n" +
				"    From (Select tph.*,ma.SUPPLIER_CODE MAJOR_SUPPLIER_CODE,\n" +
				"                 Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc) Row_Id\n" +
				"            From tta_proposal_header tph,\n" +
				"            tta_supplier_major_v ma\n" +
				"           where tph.status = 'C' and tph.approve_date is not null " +
				"and tph.approve_date <= to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss') \n" +
				"and tph.approve_date >= to_date('" + startDateString + "','yyyy-mm-dd hh24:mi:ss') \n" +
				"and  ma.MAJOR_SUPPLIER_CODE = tph.vendor_nbr) T2\n" +
				"   Where T2.Row_Id = 1 \n" +
				") tph" +
				" join tta_cw_checking tom on tom.cw_year = tph.proposal_year" +
				" and tph.MAJOR_SUPPLIER_CODE = tom.prior_vendor_code\n" +
				"where  \n" +
				" tom.creation_date >= to_date('" + defaultDateString + "','yyyy-mm-dd hh24:mi:ss')\n" +
                "and tom.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')\n" +
				"and nvl(tom.parent_id,'-1') = '-1'\n" +
				"and ( nvl(tom.receive_amount,0) != 0 or nvl(tom.adjust_receive_amount,0) != 0)\n" +
				"group by \n" +
				"tom.promotion_section,\n" +
				"tom.promotion_location,\n" +
				"tom.prior_vendor_code,\n" +
				"tom.item_code,\n" +
				"tom.group_code,\n" +
				"tom.dept_code,\n" +
				"tom.brand_cn,\n" +
				"tom.brand_en " ;
	};

	public static String getUpdateCwytdStandard(Integer userId,String ps,int ytdId){
		return  "update tta_cw_ytd toy set\n" +
				"\n" +
				"(toy.charge_standards,toy.charge_money,toy.charge_unit,addition_rate,cw_year)\n" +
				"=\n" +
				"(SELECT \n" +
				"tta_oi_dept_fee_count(\n" +
				"                      toy.GROUP_code,\n" +
				"                      tph.dept_code1,\n" +
				"                      tph.dept_code2,\n" +
				"                      tph.dept_code3,\n" +
				"                      tfddv.standard_value,\n" +
				"                      tdf.standard_value1,\n" +
				"                      tdf.standard_value2,\n" +
				"                      tdf.standard_value3,\n" +
				"                      tfddv.unit,\n" +
				"                      tdf.unit1,\n" +
				"                      tdf.unit2,\n" +
				"                      tdf.unit3,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      1                     \n" +
				"                      )  charge_standards, --收费标准\n" +
				"tta_oi_dept_fee_count(\n" +
				"                      toy.GROUP_code,\n" +
				"                      tph.dept_code1,\n" +
				"                      tph.dept_code2,\n" +
				"                      tph.dept_code3,\n" +
				"                      tfddv.standard_value,\n" +
				"                      tdf.standard_value1,\n" +
				"                      tdf.standard_value2,\n" +
				"                      tdf.standard_value3,\n" +
				"                      tfddv.unit,\n" +
				"                      tdf.unit1,\n" +
				"                      tdf.unit2,\n" +
				"                      tdf.unit3,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      2                     \n" +
				"                      )  charge_money,  --计费金额\n" +
				"tta_oi_dept_fee_count(\n" +
				"                      toy.GROUP_code,\n" +
				"                      tph.dept_code1,\n" +
				"                      tph.dept_code2,\n" +
				"                      tph.dept_code3,\n" +
				"                      tfddv.standard_value,\n" +
				"                      tdf.standard_value1,\n" +
				"                      tdf.standard_value2,\n" +
				"                      tdf.standard_value3,\n" +
				"                      tfddv.unit,\n" +
				"                      tdf.unit1,\n" +
				"                      tdf.unit2,\n" +
				"                      tdf.unit3,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      null,\n" +
				"                      3                    \n" +
				"                      )  charge_unit,  --计费单位\n" +
				" tcm.addition_rate,\n" +
				" tph.proposal_year\n" +
				"\n" +
				" FROM \n" +
				"tta_proposal_header tph \n" +
				" left join tta_osd_sales_site toss on toss.sales_year = toy.proposal_year and toss.sales_site = 'C'||chr(38)||'W' \n" +
				"left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = toss.dept_item_no \n" +
				//"left join tta_proposal_header oldTpo on tph.last_year_proposal_id = oldTpo.proposal_id\n" +
				//"left join  tta_dept_fee oldTdf on  oldTpo.Proposal_Id =   oldTdf.Proposal_Id and oldTdf.line_code = toss.dept_item_no \n" +
				"left join (  select * from \n" +
				"    (select tfdh.*, row_number() \n" +
				"    over(partition by tfdh.dept_desc,tfdh.Year_Code order by tfdh.Creation_Date desc ) rn\n" +
				"    from tta_fee_dept_header tfdh where tfdh.status != 'F' AND NVL(tfdh.ACCORD_TYPE,'-1') = 'A'  ) where rn=1 \n" +
				") tfdhv on tfdhv.year_code = toy.proposal_year and tfdhv.dept_desc = toy.company_dept_name\n" +
				"left join tta_fee_dept_line tfdl on tfdl.line_code = toss.dept_item_no and tfdl.feedept_id = tfdhv.feedept_id\n" +
				"left join tta_contract_master tcm on toy.vendor_nbr = tcm.vendor_nbr \n" +
				"left join \n" +
				"(  select * from \n" +
				"    (select tfdd.*, row_number() \n" +
				"    over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
				"    from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
				") tfddv\n" +
				"on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code =  toy.Group_code\n" +
				"where  \n" +
				" toy.proposal_id = tph.proposal_id)\n" +
				"\n" +
				"where toy.ytd_id =  "+ ytdId;
	}


	public static String getInsertYtdNotInCw(String batchCode,Integer userId,String ps,Integer year,int ytdId,String dateString){
		return
				"insert into tta_cw_checking tom \n" +
						"(tom.prior_vendor_code,\n" +
						" tom.prior_vendor_name,\n" +
						" tom.addition_rate,\n" +
						" tom.cw_id,\n" +
						" tom.promotion_location,\n" +
						" tom.promotion_section,\n" +
						" tom.stores_num,\n" +
						" tom.proposal_order_no,\n" +
						" tom.group_desc,\n" +
						" tom.dept_desc,\n" +
						" tom.group_code,\n" +
						" tom.dept_code,\n" +
						" tom.brand_cn,\n" +
						" tom.brand_en,\n" +
						" tom.charge_standards,\n" +
						" tom.charge_money,\n" +
						" tom.charge_unit,\n" +
						" tom.charge_unit_name,\n" +
						" tom.company_dept_name,\n" +
						" tom.company_dept_code,\n" +
						" tom.creation_date,\n" +
						" tom.created_by,\n" +
						" tom.last_updated_by,\n" +
						" tom.type,\n" +
						" tom.content,\n" +
						" tom.collect,\n" +
						" tom.item_code,\n" +
						" tom.cn_name,\n" +
						" tom.adjust_receive_amount,\n" +
						" tom.no_adjust_receive_amount,\n" +
						" tom.UNCONFIRM_AMOUNT,\n" +
						" tom.NO_UNCONFIRM_AMOUNT,\n" +
						" tom.batch_code,\n" +
						" tom.last_update_date,\n" +
						" tom.cw_year\n" +
						")\n" +
						"SELECT \n" +
						"toy.vendor_nbr,\n" +
						"toy.vendor_name,\n" +
						"adds.addition_rate,\n" +
						"seq_tta_cw_checking.nextval,\n" +
						"toy.promotion_location,\n" +
						"toy.promotion_section,\n" +
						"toy.stores_num,\n" +
						"toy.proposal_order_no,\n" +
						"tta_six_action_fun (adds.ower_dept_no,toy.group_desc,toy.Group_Code,'1') ,\n" +
						"toy.dept_desc,\n" +
						"toy.group_code,\n" +
						"toy.dept_code,\n" +
						"toy.brand_cn,\n" +
						"toy.brand_en,\n" +
						"toy.charge_standards,\n" +
						"toy.charge_money,\n" +
						"toy.charge_unit,\n" +
						"lookup1.meaning,\n" +
						"toy.company_dept_name,\n" +
						"toy.company_dept_code,\n" +
						"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
						userId + ",\n" +
						userId + ",\n" +
						"'1',\n" +
						"toy.content,\n" +
						" 'OnTop',\n"+
						"toy.item_code,\n" +
						"toy.cn_name,\n" +
						"( case     \n" +
						"                     when   instr(lookup1.meaning,'店')>0   then   \n" +
						"                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount,0)\n" +
						"                     else                           \n" +
						"                      round(nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount ,0)\n" +
						"                     end    ),\n" +
						"( case     \n" +
						"                     when   instr(lookup1.meaning,'店')>0   then   \n" +
						"                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)-toy.no_amount,0)\n" +
						"                     else                           \n" +
						"                      round(nvl(toy.charge_money,0)-toy.no_amount ,0)\n" +
						"                     end    ),\n" +
						"( case     \n" +
						"                     when   instr(lookup1.meaning,'店')>0   then   \n" +
						"                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount,0)\n" +
						"                     else                           \n" +
						"                      round(nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount ,0)\n" +
						"                     end    ),\n" +
						"( case     \n" +
						"                     when   instr(lookup1.meaning,'店')>0   then   \n" +
						"                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)-toy.no_amount,0)\n" +
						"                     else                           \n" +
						"                      round(nvl(toy.charge_money,0)-toy.no_amount ,0)\n" +
						"                     end    ),\n" +
						"'" +batchCode +"'" +",\n" +
						"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
						"	toy.cw_year\n" +
						" FROM   \n" +
						"\n" +
						"tta_cw_ytd toy \n" +
						"left join tta_supplier ts on ts.supplier_code = toy.vendor_nbr\n" +
						"left join        (select lookup_type,lookup_code,meaning\n" +
						"        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
						"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
						"      ) lookup1  on toy.charge_unit = lookup1.lookup_code\n" +
						"left join  (SELECT tph.order_nbr,nvl(max(tcm.addition_rate),1) addition_rate, MAX(ower_dept_no) ower_dept_no  FROM  tta_proposal_header tph,\n" +
						"               tta_supplier_major_v ma,\n" +
						"               tta_contract_master tcm\n" +
						"               \n" +
						"               where tph.vendor_nbr = ma.MAJOR_SUPPLIER_CODE\n" +
						"               and tcm.vendor_nbr =  ma.SUPPLIER_CODE\n" +
						"               and tph.status = 'C'\n" +
						"               and nvl(tph.version_status,'1')= '1'\n" +
						"               group  by tph.order_nbr )   adds   on toy.proposal_order_no = adds.order_nbr\n" +
						"where \n" +
						" toy.ytd_id =" + ytdId + "\n" +
						"and  ( case     \n" +
						"                     when   instr(lookup1.meaning,'店')>0   then   \n" +
						"                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)-toy.no_amount,0)\n" +
						"                     else                           \n" +
						"                      round(nvl(toy.charge_money,0)-toy.no_amount ,0)\n" +
						"                     end    ) !=0"   ;

	}

    private Integer cwId;
	private Integer id;
    private String promotionSection;
    @JSONField(format="yyyy-MM-dd")
    private Date promotionStartDate;
    @JSONField(format="yyyy-MM-dd")
    private Date promotionEndDate;
    private String promotionLocation;
    private Integer timeDimension;
    private Integer storesNum;
    private String groupDesc;
    private String deptDesc;
    private String deptCode;
    private String groupCode;
    private String content;
    private String itemCode;
	private Integer cwYear;
    private String cnName;
    private String brandCn;
    private String brandEn;
    private String priorVendorCode;
    private String priorVendorName;
    private String contractYear;
    private String contractStatus;
    private String chargeStandards;
    private BigDecimal chargeMoney;
    private String chargeUnit;
	private String chargeUnitName;
	private BigDecimal receiveAmount;
    private BigDecimal originalAmount;
    private BigDecimal unconfirmAmount;
    private BigDecimal difference;
    private String collect;
    private String purchase;
    private String exemptionReason;
    private String exemptionReason2;
    private String debitOrderCode;
    private BigDecimal receipts;
    private String batchCode;
    private Integer transferInPerson;
	private Integer transferOutPerson;
	private Integer transferLastPerson;
	private String transferInPersonName;
	private String transferOutPersonName;
	private String transferLastPersonName;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date transferLastDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date transferInDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date transferOutDate;
	private String remark;
    private Integer status;
	private String headerStatus ;
    private Integer parentId;
    private String parentVendorCode;
    private String parentVendorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
	private String createdByName;
    private Integer lastUpdatedBy;
	private String lastUpdatedByName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private BigDecimal additionRate;
	private Integer fileId;
	private String sourceFileName;
	private String referenceVendorCode;
	private String processCode;
	private String processStatusName;
	private Integer processReId;
	private BigDecimal chargeableBasedOnRateCard;
	private BigDecimal needBuyerFeedback ;
	private BigDecimal ytdActualCharge;
	private BigDecimal planToCharge ;
	private BigDecimal toBeWaive ;
	private String BuyerName ;
	private String advanceCode;
	private BigDecimal advanceAmount;
	private String oldYearRecharge ;
	private String putLocation1 ;
	private String oiProjectCode1 ;
	private String contractAdditionalRemarks1 ;
	private BigDecimal amount1 ;
	private String putLocation2 ;
	private String oiProjectCode2 ;
	private String contractAdditionalRemarks2 ;
	private BigDecimal amount2 ;
	private BigDecimal noReceiveAmount;
	private BigDecimal noUnconfirmAmount;
	private String purchaseName;
	private String exemptionReasonName;
	private String exemptionReason2Name;
	private BigDecimal concludeRate;
	private String taskIds;
	private int groupCwCnt;//当前按照部门提交cw数量
	private Integer adjustBy;
	private String adjustByName;
	private BigDecimal adjustAmount;
	private BigDecimal adjustReceiveAmount;
	private BigDecimal noAdjustAmount;
	private BigDecimal noAdjustReceiveAmount;
	private String type;
	private String currentUserName;
	private String startUserName;
	private String valueAll;
	private String processStatus;
	public void setCwId(Integer cwId) {
		this.cwId = cwId;
	}

	
	public Integer getCwId() {
		return cwId;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}

	
	public String getPromotionSection() {
		return promotionSection;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	
	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	
	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionLocation(String promotionLocation) {
		this.promotionLocation = promotionLocation;
	}

	
	public String getPromotionLocation() {
		return promotionLocation;
	}

	public void setTimeDimension(Integer timeDimension) {
		this.timeDimension = timeDimension;
	}

	
	public Integer getTimeDimension() {
		return timeDimension;
	}

	public void setStoresNum(Integer storesNum) {
		this.storesNum = storesNum;
	}

	
	public Integer getStoresNum() {
		return storesNum;
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

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getContent() {
		return content;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	
	public String getCnName() {
		return cnName;
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

	public void setPriorVendorCode(String priorVendorCode) {
		this.priorVendorCode = priorVendorCode;
	}

	
	public String getPriorVendorCode() {
		return priorVendorCode;
	}

	public void setPriorVendorName(String priorVendorName) {
		this.priorVendorName = priorVendorName;
	}

	
	public String getPriorVendorName() {
		return priorVendorName;
	}

	public void setContractYear(String contractYear) {
		this.contractYear = contractYear;
	}

	
	public String getContractYear() {
		return contractYear;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	
	public String getContractStatus() {
		return contractStatus;
	}

	public void setChargeStandards(String chargeStandards) {
		this.chargeStandards = chargeStandards;
	}

	
	public String getChargeStandards() {
		return chargeStandards;
	}

	public void setChargeMoney(BigDecimal chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	
	public BigDecimal getChargeMoney() {
		return chargeMoney;
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

	public void setOriginalAmount(BigDecimal originalAmount) {
		this.originalAmount = originalAmount;
	}

	
	public BigDecimal getOriginalAmount() {
		return originalAmount;
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

	public void setCollect(String collect) {
		this.collect = collect;
	}

	
	public String getCollect() {
		return collect;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	
	public String getPurchase() {
		return purchase;
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

	public void setReceipts(BigDecimal receipts) {
		this.receipts = receipts;
	}

	
	public BigDecimal getReceipts() {
		return receipts;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getTransferOutPerson() {
		return transferOutPerson;
	}

	public void setTransferOutPerson(Integer transferOutPerson) {
		this.transferOutPerson = transferOutPerson;
	}

	public Integer getTransferLastPerson() {
		return transferLastPerson;
	}

	public void setTransferLastPerson(Integer transferLastPerson) {
		this.transferLastPerson = transferLastPerson;
	}

	public Date getTransferLastDate() {
		return transferLastDate;
	}

	public void setTransferLastDate(Date transferLastDate) {
		this.transferLastDate = transferLastDate;
	}

	public Date getTransferInDate() {
		return transferInDate;
	}

	public void setTransferInDate(Date transferInDate) {
		this.transferInDate = transferInDate;
	}

	public Date getTransferOutDate() {
		return transferOutDate;
	}

	public void setTransferOutDate(Date transferOutDate) {
		this.transferOutDate = transferOutDate;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHeaderStatus() {
		return headerStatus;
	}

	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}

	public String getChargeUnitName() {
		return chargeUnitName;
	}

	public void setChargeUnitName(String chargeUnitName) {
		this.chargeUnitName = chargeUnitName;
	}

	public BigDecimal getAdditionRate() {
		return additionRate;
	}

	public void setAdditionRate(BigDecimal additionRate) {
		this.additionRate = additionRate;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public Integer getProcessReId() {
		return processReId;
	}

	public void setProcessReId(Integer processReId) {
		this.processReId = processReId;
	}

	public String getReferenceVendorCode() {
		return referenceVendorCode;
	}

	public void setReferenceVendorCode(String referenceVendorCode) {
		this.referenceVendorCode = referenceVendorCode;
	}

	public String getProcessStatusName() {
		return processStatusName;
	}

	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
	}

	public BigDecimal getChargeableBasedOnRateCard() {
		return chargeableBasedOnRateCard;
	}

	public void setChargeableBasedOnRateCard(BigDecimal chargeableBasedOnRateCard) {
		this.chargeableBasedOnRateCard = chargeableBasedOnRateCard;
	}

	public BigDecimal getNeedBuyerFeedback() {
		return needBuyerFeedback;
	}

	public void setNeedBuyerFeedback(BigDecimal needBuyerFeedback) {
		this.needBuyerFeedback = needBuyerFeedback;
	}

	public BigDecimal getYtdActualCharge() {
		return ytdActualCharge;
	}

	public void setYtdActualCharge(BigDecimal ytdActualCharge) {
		this.ytdActualCharge = ytdActualCharge;
	}

	public BigDecimal getPlanToCharge() {
		return planToCharge;
	}

	public void setPlanToCharge(BigDecimal planToCharge) {
		this.planToCharge = planToCharge;
	}

	public BigDecimal getToBeWaive() {
		return toBeWaive;
	}

	public void setToBeWaive(BigDecimal toBeWaive) {
		this.toBeWaive = toBeWaive;
	}

	public String getBuyerName() {
		return BuyerName;
	}

	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}

	public String getAdvanceCode() {
		return advanceCode;
	}

	public void setAdvanceCode(String advanceCode) {
		this.advanceCode = advanceCode;
	}

	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getOldYearRecharge() {
		return oldYearRecharge;
	}

	public void setOldYearRecharge(String oldYearRecharge) {
		this.oldYearRecharge = oldYearRecharge;
	}

	public String getPutLocation1() {
		return putLocation1;
	}

	public void setPutLocation1(String putLocation1) {
		this.putLocation1 = putLocation1;
	}

	public String getOiProjectCode1() {
		return oiProjectCode1;
	}

	public void setOiProjectCode1(String oiProjectCode1) {
		this.oiProjectCode1 = oiProjectCode1;
	}

	public String getContractAdditionalRemarks1() {
		return contractAdditionalRemarks1;
	}

	public void setContractAdditionalRemarks1(String contractAdditionalRemarks1) {
		this.contractAdditionalRemarks1 = contractAdditionalRemarks1;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public String getPutLocation2() {
		return putLocation2;
	}

	public void setPutLocation2(String putLocation2) {
		this.putLocation2 = putLocation2;
	}

	public String getOiProjectCode2() {
		return oiProjectCode2;
	}

	public void setOiProjectCode2(String oiProjectCode2) {
		this.oiProjectCode2 = oiProjectCode2;
	}

	public String getContractAdditionalRemarks2() {
		return contractAdditionalRemarks2;
	}

	public void setContractAdditionalRemarks2(String contractAdditionalRemarks2) {
		this.contractAdditionalRemarks2 = contractAdditionalRemarks2;
	}

	public BigDecimal getAmount2() {
		return amount2;
	}

	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
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

	public BigDecimal getNoReceiveAmount() {
		return noReceiveAmount;
	}

	public void setNoReceiveAmount(BigDecimal noReceiveAmount) {
		this.noReceiveAmount = noReceiveAmount;
	}

	public BigDecimal getNoUnconfirmAmount() {
		return noUnconfirmAmount;
	}

	public void setNoUnconfirmAmount(BigDecimal noUnconfirmAmount) {
		this.noUnconfirmAmount = noUnconfirmAmount;
	}

	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
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

	public BigDecimal getConcludeRate() {
		return concludeRate;
	}

	public void setConcludeRate(BigDecimal concludeRate) {
		this.concludeRate = concludeRate;
	}

	public int getGroupCwCnt() {
		return groupCwCnt;
	}

	public void setGroupCwCnt(int groupCwCnt) {
		this.groupCwCnt = groupCwCnt;
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

	public void setTaskIds(Clob taskIds) {
		StringBuffer buffer = new StringBuffer();
		if (taskIds != null) {
			BufferedReader br = null;
			try {
				Reader r = taskIds.getCharacterStream();  //将Clob转化成流
				br = new BufferedReader(r);
				String s = null;
				while ((s = br.readLine()) != null) {
					buffer.append(s);
				}
				this.taskIds = "";
				String[] arr = buffer.substring(0, buffer.length() - 1).split(",");
				for (String str : arr) {
					if(!this.taskIds.contains(str)) {
						this.taskIds += str + ",";
					}
				}
				this.taskIds = this.taskIds.substring(0, this.taskIds.length() - 1);
			} catch (Exception e) {
				e.printStackTrace();	//打印异常信息
			} finally {
				if (br != null) {
					try {
						br.close(); //关闭流
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String getTaskIds() {
		return taskIds;
	}

	public BigDecimal getNoAdjustAmount() {
		return noAdjustAmount;
	}

	public void setNoAdjustAmount(BigDecimal noAdjustAmount) {
		this.noAdjustAmount = noAdjustAmount;
	}

	public BigDecimal getNoAdjustReceiveAmount() {
		return noAdjustReceiveAmount;
	}

	public void setNoAdjustReceiveAmount(BigDecimal noAdjustReceiveAmount) {
		this.noAdjustReceiveAmount = noAdjustReceiveAmount;
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

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getValueAll() {
		return valueAll;
	}

	public void setValueAll(String valueAll) {
		this.valueAll = valueAll;
	}

	public Integer getCwYear() {
		return cwYear;
	}

	public void setCwYear(Integer cwYear) {
		this.cwYear = cwYear;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

}
