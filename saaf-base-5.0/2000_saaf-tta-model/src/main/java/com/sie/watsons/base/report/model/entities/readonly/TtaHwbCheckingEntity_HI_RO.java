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
 * TtaHwbCheckingEntity_HI_RO Entity Object
 * Thu Nov 28 12:20:39 CST 2019  Auto Generate
 */

public class TtaHwbCheckingEntity_HI_RO {

	/**
	 * 模板导出查询
	 */
	public static final String  ABOI_TEMPLATES_QUERY = "" ;

	public static final String getCheckGroupHwb() {
		String sql = "select " +
				"	 thc.group_desc as \"groupDesc\"," +
				"	 count(thc.hwb_id) as \"cnt\"\n" +
				"  from tta_report_header trh\n" +
				" inner join tta_hwb_checking thc\n" +
				"    on trh.batch_id = thc.batch_code\n" +
				" where trh.batch_id =:batchCode and nvl(thc.receive_amount,0) != 0 and nvl(thc.status,1) = 1 \n" +
				" group by thc.group_desc";
		return sql;
	};

	public static final String getHwbProcessSummary(String batchId, String userCode, Integer operatorUserId) {
		String sql = "select  '" + batchId+ "' as batch_code,\n" +
				"       thc.group_code,\n" +
				"       max(thc.group_desc) as group_desc,\n" +
				"       sum(thc.receive_amount) as receive_amount, -- 应收金额(含加成)\n" +
				"       sum(thc.UNCONFIRM_AMOUNT) as unconfirm_amount, -- 需采购确认收取金额(含加成)\n" +
				"       sum(thc.difference) as difference, -- 差异(含加成)\n" +
				"       round(nvl(sum(UNCONFIRM_AMOUNT),0)/nvl(sum(receive_amount),1),4)*100 as concludeRate, -- 达成率\n" +
				"       XMLAGG(XMLPARSE(CONTENT task.id_ || ',' WELLFORMED) ORDER BY task.id_).GETCLOBVAL() as task_ids,\n" +
				"		count(thc.hwb_id) as  group_hwb_cnt\n" +
				"  from tta_report_header trh\n" +
				" inner join tta_hwb_checking thc\n" +
				"    on trh.batch_id = thc.batch_code\n" +
				" inner join tta_report_process_header trph\n" +
				"    on thc.process_id = trph.report_process_header_id\n" +
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
				" group by thc.group_code";
		return sql;
	};

	public static final String  QUERY_EXISTS = "SELECT " +
			"tom.group_code," +
			"tom.group_desc," +
			"tom.dept_code," +
			"tom.dept_desc," +
			"tom.brand_cn, " +
			"tom.group_code||'-'||tom.dept_code||'-'||tom.brand_cn  valueAll "  +
			"FROM tta_hwb_checking tom\n" +
			"\n" +
			"where tom.batch_code = :batchCode\n" +
			"and  \n" +
			"not exists (SELECT 1 FROM tta_user_group_dept_brand_eft_v tug where (tug.data_type = '1' and tug.\"GROUP\" = tom.group_code)\n" +
			"                                                              or (tug.data_type = '2' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code)\n" +
			"                                                              or (tug.data_type = '3' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code and tug.brand_cn = tom.brand_cn)  )\n" +
			"                                                              \n" +
			" ";

	public static final String getQueryProcess(String userCode, Integer operatorUserId) {
		String  query ="select \n" +
				"       thc.*," +
				"       thc.process_id  process_re_id,\n" +
				"		trph.batch_code process_code,\n" +
				"       trh.id,\n" +
				"       trh.status  header_status,\n" +
				"       lookup5.meaning exemptionReason2Name,\n" +
				"       lookup4.meaning exemptionReasonName,\n" +
				"       lookup3.meaning purchaseName,\n" +
				"       lookup2.meaning processstatusname,\n" +
				"       bu_ad.user_full_name adjustByName\n" +
				"  from tta_hwb_checking thc\n" +
				" left join tta_report_process_header trph \n" +
				" on thc.process_id = trph.report_process_header_id \n" +
				" inner join act_bpm_list bpm " +
				" on bpm.business_key = trph.report_process_header_id" +
				" inner join act_ru_task task " +
				" on bpm.proc_inst_id = task.proc_inst_id_" +
				"  left join tta_report_header trh\n" +
				"    on thc.batch_code = trh.batch_id\n" +
				"  left join base_users bu_ad\n" +
				"    on thc.adjust_By = bu_ad.user_id\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_HWB_ACTION' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup3  on thc.purchase = lookup3.lookup_code\n" +

				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_HWB_REASON' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup4  on thc.exemption_Reason = lookup4.lookup_code\n" +

				"left join        (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_HWB_REASON1' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup5  on thc.exemption_Reason2 = lookup5.lookup_code\n" +
				" where 1 = 1 " +
				"   and bpm.proc_def_key = 'TTA_ACTIVITY.-999' \n" +
				"	and thc.status = 1 and thc.process_id is not null \n" +
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

	/**
	 * Summary 查询
	 */
	public static final String  SUMMARY_QUERY = "";

	public static final String  QUERY = "select \n" +
			"tom.hwb_id,\n" +
			"tom.promotion_section,\n" +
			"tom.promotion_start_date,\n" +
			"tom.promotion_end_date,\n" +
			"tom.promotion_location,\n" +
			"tom.group_desc,\n" +
			"tom.dept_desc,\n" +
			"tom.dept_code,\n" +
			"tom.group_code,\n" +
			"tom.content,\n" +
			"tom.brand_cn,\n" +
			"tom.brand_en,\n" +
			"tom.prior_vendor_code,\n" +
			"tom.prior_vendor_name,\n" +
			"tom.charge_standards,\n" +
			"tom.receive_amount,\n" +
			"tom.adjust_amount,\n" +
			"tom.adjust_by,\n" +
			"tom.unconfirm_amount,\n" +
			"tom.difference,\n" +
			"tom.company_standard,\n" +
			"tom.agreement_standard,\n" +
			"tom.hwb_type,\n" +
			"tom.description_cn,\n" +
			"tom.award_description,\n" +
			"tom.num,\n" +
			"tom.collect,\n" +
			"tom.purchase,\n" +
			"tom.exemption_reason,\n" +
			"tom.exemption_reason2,\n" +
			"tom.debit_order_code,\n" +
			"tom.receipts,\n" +
			"tom.batch_code,\n" +
			"tom.transfer_in_person,\n" +
			"tom.transfer_out_person,\n" +
			"tom.transfer_last_person,\n" +
			"tom.transfer_last_date,\n" +
			"tom.transfer_in_date,\n" +
			"tom.transfer_out_date,\n" +
			"tom.remark,\n" +
			"tom.status,\n" +
			"tom.parent_id,\n" +
			"nvl(tom.PARENT_VENDOR_CODE,tom.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(tom.PARENT_VENDOR_NAME,tom.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"tom.creation_date,\n" +
			"tom.created_by,\n" +
			"tom.last_updated_by,\n" +
			"tom.last_update_date,\n" +
			"tom.last_update_login,\n" +
			"trh.id,\n" +
			"tom.addition_rate,\n" +
			"tom.process_id  processReId,\n" +
			"tom.no_receive_amount,\n" +
			"tom.no_unconfirm_amount,\n" +
			"trph.batch_code processcode,\n" +
			"TRPH.STATUS process_Status,\n" +
			"lookup5.meaning exemptionReason2Name,\n" +
			"lookup4.meaning exemptionReasonName,\n" +
			"lookup3.meaning purchaseName,\n" +
			"lookup2.meaning processstatusname,\n" +
			"trh.status headerstatus,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"bu_ad.user_full_name adjustByName\n" +
			"\n" +
			"from  tta_hwb_checking  tom\n" +
			"left join tta_report_header trh on tom.batch_code = trh.batch_id\n" +
			"left join tta_report_process_header trph on tom.process_id = trph.report_process_header_id\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_HWB_ACTION' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup3  on tom.purchase = lookup3.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_HWB_REASON' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup4  on tom.exemption_Reason = lookup4.lookup_code\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_HWB_REASON1' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup5  on tom.exemption_Reason2 = lookup5.lookup_code\n" +
			"  left join base_users bu_ad\n" +
			"    on tom.adjust_By = bu_ad.user_id\n" +
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
			"  where a.proc_def_key = 'TTA_ACTIVITY.-999' \n) process on process.BUSINESS_KEY = tom.process_id  \n"  +
			"where 1=1  and nvl(tom.status,1) = 1 ";

	public static final String  QUERY_NO_BIC = "select * from  \n" +
			"    \n" +
			"   ( select \n" +
			"tomv.hwb_id,\n" +
			"tomv.promotion_section,\n" +
			"tomv.promotion_start_date,\n" +
			"tomv.promotion_end_date,\n" +
			"tomv.promotion_location,\n" +
			"tomv.group_desc,\n" +
			"tomv.dept_desc,\n" +
			"tomv.dept_code,\n" +
			"tomv.group_code,\n" +
			"tomv.content,\n" +
			"tomv.brand_cn,\n" +
			"tomv.brand_en,\n" +
			"tomv.prior_vendor_code,\n" +
			"tomv.prior_vendor_name,\n" +
			"tomv.charge_standards,\n" +
			"tomv.receive_amount,\n" +
			"tomv.adjust_amount,\n" +
			"tomv.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"tomv.unconfirm_amount,\n" +
			"tomv.difference,\n" +
			"tomv.company_standard,\n" +
			"tomv.agreement_standard,\n" +
			"tomv.hwb_type,\n" +
			"tomv.description_cn,\n" +
			"tomv.award_description,\n" +
			"tomv.num,\n" +
			"tomv.collect,\n" +
			"tomv.purchase,\n" +
			"tomv.exemption_reason,\n" +
			"tomv.exemption_reason2,\n" +
			"tomv.debit_order_code,\n" +
			"tomv.receipts,\n" +
			"tomv.batch_code,\n" +
			"tomv.transfer_in_person,\n" +
			"tomv.transfer_out_person,\n" +
			"tomv.transfer_last_person,\n" +
			"tomv.transfer_last_date,\n" +
			"tomv.transfer_in_date,\n" +
			"tomv.transfer_out_date,\n" +
			"tomv.remark,\n" +
			"tomv.status,\n" +
			"tomv.parent_id,\n" +
			"nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"tomv.creation_date,\n" +
			"tomv.created_by,\n" +
			"tomv.last_updated_by,\n" +
			"tomv.last_update_date,\n" +
			"tomv.last_update_login,\n" +
			"tomv.addition_rate,\n" +
			"tomv.process_id  processReId,\n" +
			"tomv.no_receive_amount,\n" +
			"tomv.no_unconfirm_amount,\n" +
			"th.id id,\n" +
			"th.status headerstatus,\n" +
			"trph.batch_code processcode,\n" +
			"TRPH.STATUS process_Status,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"lookup2.meaning processstatusname\n" +

			"    from   \n" +
			"   tta_hwb_checking tomv \n" +
			"    join (select group_desc from tta_user_group_dept_brand_eft_v tug \n" +
			"         where nvl(tug.data_type,'0') = '1' \n" +
			"         and tug.user_id  =:userId\n" +
			"         and nvl(tug.group_desc,'-99') !='-99'\n" +
			"         group by  group_desc) tugd on nvl(tomv.group_desc,'-98') = tugd.group_desc \n" +
			"                                         and nvl(tomv.transfer_out_person,0) = 0\n" +
			"                                         and nvl(tomv.batch_code,'-1') = :batchCode\n" +
			"                                         and nvl(tomv.status,1) = 1\n" +
			"     									  and tomv.group_desc != 'Own Brand'\n" +
			"    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                       \n" +
			" left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
			"     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
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
			" where a.proc_def_key = 'TTA_ACTIVITY.-999') process on process.BUSINESS_KEY = tomv.process_id  \n"  +
			"    \n" +
			"    union all\n" +
			"    \n" +
			"    select \n" +
			"tomv.hwb_id,\n" +
			"tomv.promotion_section,\n" +
			"tomv.promotion_start_date,\n" +
			"tomv.promotion_end_date,\n" +
			"tomv.promotion_location,\n" +
			"tomv.group_desc,\n" +
			"tomv.dept_desc,\n" +
			"tomv.dept_code,\n" +
			"tomv.group_code,\n" +
			"tomv.content,\n" +
			"tomv.brand_cn,\n" +
			"tomv.brand_en,\n" +
			"tomv.prior_vendor_code,\n" +
			"tomv.prior_vendor_name,\n" +
			"tomv.charge_standards,\n" +
			"tomv.receive_amount,\n" +
			"tomv.adjust_amount,\n" +
			"tomv.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"tomv.unconfirm_amount,\n" +
			"tomv.difference,\n" +
			"tomv.company_standard,\n" +
			"tomv.agreement_standard,\n" +
			"tomv.hwb_type,\n" +
			"tomv.description_cn,\n" +
			"tomv.award_description,\n" +
			"tomv.num,\n" +
			"tomv.collect,\n" +
			"tomv.purchase,\n" +
			"tomv.exemption_reason,\n" +
			"tomv.exemption_reason2,\n" +
			"tomv.debit_order_code,\n" +
			"tomv.receipts,\n" +
			"tomv.batch_code,\n" +
			"tomv.transfer_in_person,\n" +
			"tomv.transfer_out_person,\n" +
			"tomv.transfer_last_person,\n" +
			"tomv.transfer_last_date,\n" +
			"tomv.transfer_in_date,\n" +
			"tomv.transfer_out_date,\n" +
			"tomv.remark,\n" +
			"tomv.status,\n" +
			"tomv.parent_id,\n" +
			"nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"tomv.creation_date,\n" +
			"tomv.created_by,\n" +
			"tomv.last_updated_by,\n" +
			"tomv.last_update_date,\n" +
			"tomv.last_update_login,\n" +
			"tomv.addition_rate,\n" +
			"tomv.process_id  processReId,\n" +
			"tomv.no_receive_amount,\n" +
			"tomv.no_unconfirm_amount,\n" +
			"th.id id,\n" +
			"th.status headerstatus,\n" +
			"trph.batch_code processcode,\n" +
			"TRPH.STATUS process_Status,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"lookup2.meaning processstatusname\n" +

			"   \n" +
			"   from   \n" +
			"    tta_hwb_checking tomv\n" +
			"    join  (select group_desc,dept_desc from tta_user_group_dept_brand_eft_v tug \n" +
			"         where nvl(tug.data_type,'0') = '2' \n" +
			"         and tug.user_id  =:userId\n" +
			"         and nvl(tug.group_desc,'-99') !='-99'\n" +
			"         and nvl(tug.dept_desc,'-96') !='-96'\n" +
			"         group by  group_desc,dept_desc) tugd on  nvl(tomv.group_desc,'-98') = tugd.group_desc \n" +
			"                                         and nvl(tomv.dept_desc,'-97') = tugd.dept_desc\n" +
			"                                         and nvl(tomv.transfer_out_person,0) = 0\n" +
			"                                         and nvl(tomv.batch_code,'-1') = :batchCode \n" +
			"                                         and nvl(tomv.status,1) = 1\n" +
			"     									  and tomv.group_desc != 'Own Brand'\n" +
			"    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                                                               \n" +
			"left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  on trph.status = lookup2.lookup_code\n" +

			"     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
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
			"where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tomv.process_id  \n"  +
			"    \n" +
			"    union all\n" +
			"    \n" +
			"    select \n" +
			"tomv.hwb_id,\n" +
			"tomv.promotion_section,\n" +
			"tomv.promotion_start_date,\n" +
			"tomv.promotion_end_date,\n" +
			"tomv.promotion_location,\n" +
			"tomv.group_desc,\n" +
			"tomv.dept_desc,\n" +
			"tomv.dept_code,\n" +
			"tomv.group_code,\n" +
			"tomv.content,\n" +
			"tomv.brand_cn,\n" +
			"tomv.brand_en,\n" +
			"tomv.prior_vendor_code,\n" +
			"tomv.prior_vendor_name,\n" +
			"tomv.charge_standards,\n" +
			"tomv.receive_amount,\n" +
			"tomv.adjust_amount,\n" +
			"tomv.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"tomv.unconfirm_amount,\n" +
			"tomv.difference,\n" +
			"tomv.company_standard,\n" +
			"tomv.agreement_standard,\n" +
			"tomv.hwb_type,\n" +
			"tomv.description_cn,\n" +
			"tomv.award_description,\n" +
			"tomv.num,\n" +
			"tomv.collect,\n" +
			"tomv.purchase,\n" +
			"tomv.exemption_reason,\n" +
			"tomv.exemption_reason2,\n" +
			"tomv.debit_order_code,\n" +
			"tomv.receipts,\n" +
			"tomv.batch_code,\n" +
			"tomv.transfer_in_person,\n" +
			"tomv.transfer_out_person,\n" +
			"tomv.transfer_last_person,\n" +
			"tomv.transfer_last_date,\n" +
			"tomv.transfer_in_date,\n" +
			"tomv.transfer_out_date,\n" +
			"tomv.remark,\n" +
			"tomv.status,\n" +
			"tomv.parent_id,\n" +
			"nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"tomv.creation_date,\n" +
			"tomv.created_by,\n" +
			"tomv.last_updated_by,\n" +
			"tomv.last_update_date,\n" +
			"tomv.last_update_login,\n" +
			"tomv.addition_rate,\n" +
			"tomv.process_id  processReId,\n" +
			"tomv.no_receive_amount,\n" +
			"tomv.no_unconfirm_amount,\n" +
			"th.id id,\n" +
			"th.status headerstatus,\n" +
			"trph.batch_code processcode,\n" +
			"TRPH.STATUS process_Status,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"lookup2.meaning processstatusname\n" +

			"   \n" +
			"   from   \n" +
			"    tta_hwb_checking tomv\n" +
			"    join  (select group_desc,dept_desc,brand_cn from tta_user_group_dept_brand_eft_v tug \n" +
			"         where nvl(tug.data_type,'0') = '3' \n" +
			"         and tug.user_id  =:userId\n" +
			"         and nvl(tug.group_desc,'-99') !='-99'\n" +
			"         and nvl(tug.dept_desc,'-96') !='-96'\n" +
			"         and nvl(tug.brand_cn,'-95') != '-95'\n" +
			"         group by  group_desc,dept_desc,brand_cn) tugd     on nvl(tomv.group_desc,'-98') = tugd.group_desc \n" +
			"                                         and nvl(tomv.dept_desc,'-97') = tugd.dept_desc\n" +
			"                                         and nvl(tomv.brand_cn,'-93') = tugd.brand_cn\n" +
			"                                         and nvl(tomv.transfer_out_person,0) = 0\n" +
			"                                         and nvl(tomv.batch_code,'-1') =:batchCode \n" +
			"                                         and nvl(tomv.status,1) = 1\n" +
			"     									  and tomv.group_desc != 'Own Brand'\n" +
			"    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                                                       \n" +
			"left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
			"     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
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
			"where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tomv.process_id  \n"  +
			"    \n" +
			"    union all\n" +
			"                                         \n" +
			"    select \n" +
			"tomv.hwb_id,\n" +
			"tomv.promotion_section,\n" +
			"tomv.promotion_start_date,\n" +
			"tomv.promotion_end_date,\n" +
			"tomv.promotion_location,\n" +
			"tomv.group_desc,\n" +
			"tomv.dept_desc,\n" +
			"tomv.dept_code,\n" +
			"tomv.group_code,\n" +
			"tomv.content,\n" +
			"tomv.brand_cn,\n" +
			"tomv.brand_en,\n" +
			"tomv.prior_vendor_code,\n" +
			"tomv.prior_vendor_name,\n" +
			"tomv.charge_standards,\n" +
			"tomv.receive_amount,\n" +
			"tomv.adjust_amount,\n" +
			"tomv.adjust_by,\n" +
			"bu_ad.user_full_name adjustByName,\n" +
			"tomv.unconfirm_amount,\n" +
			"tomv.difference,\n" +
			"tomv.company_standard,\n" +
			"tomv.agreement_standard,\n" +
			"tomv.hwb_type,\n" +
			"tomv.description_cn,\n" +
			"tomv.award_description,\n" +
			"tomv.num,\n" +
			"tomv.collect,\n" +
			"tomv.purchase,\n" +
			"tomv.exemption_reason,\n" +
			"tomv.exemption_reason2,\n" +
			"tomv.debit_order_code,\n" +
			"tomv.receipts,\n" +
			"tomv.batch_code,\n" +
			"tomv.transfer_in_person,\n" +
			"tomv.transfer_out_person,\n" +
			"tomv.transfer_last_person,\n" +
			"tomv.transfer_last_date,\n" +
			"tomv.transfer_in_date,\n" +
			"tomv.transfer_out_date,\n" +
			"tomv.remark,\n" +
			"tomv.status,\n" +
			"tomv.parent_id,\n" +
			"nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
			"nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
			"tomv.creation_date,\n" +
			"tomv.created_by,\n" +
			"tomv.last_updated_by,\n" +
			"tomv.last_update_date,\n" +
			"tomv.last_update_login,\n" +
			"tomv.addition_rate,\n" +
			"tomv.process_id  processReId,\n" +
			"tomv.no_receive_amount,\n" +
			"tomv.no_unconfirm_amount,\n" +
			"th.id id,\n" +
			"th.status headerstatus,\n" +
			"trph.batch_code processcode,\n" +
			"TRPH.STATUS process_Status,\n" +
			"process.current_User_Name,\n" +
			"process.start_user_name,\n" +
			"lookup2.meaning processstatusname\n" +

			"     from \n" +
			"    \n" +
			"    tta_hwb_checking tomv\n" +
			"    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y' \n" +
			"left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
			"left join        (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  on trph.status = lookup2.lookup_code\n" +
			"     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
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
			" where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tomv.process_id  \n"  +
			"    \n" +
			"     where nvl(tomv.transfer_in_person,0) =:userId \n" +
			"     and nvl(tomv.batch_code,'-1') =:batchCode\n" +
			"     and nvl(tomv.status,1) = 1\n" +
			"     and tomv.group_desc != 'Own Brand'\n" +
			"    ) tom where 1=1";

	public static String getInsertReportBase(String batchCode,Integer userId,String ps,Integer year,String dateString){
		return "insert into tta_hwb_checking thc\n" +
				"(       thc.hwb_id,\n" +
				"       thc.promotion_section,\n" +
				"       thc.description_cn,\n" +
				"       thc.award_description,\n" +
				"       thc.group_desc,\n" +
				"       thc.dept_desc,\n" +
				"       thc.dept_code,\n" +
				"       thc.group_code,\n" +
				"       thc.brand_cn,\n" +
				"       thc.brand_en,\n" +
				"       thc.content,\n" +
				"       thc.prior_vendor_code,\n" +
				"       thc.prior_vendor_name,\n" +
				"       thc.company_standard,\n" +
				"       thc.agreement_standard,\n" +
				"       thc.num,\n" +
				"       thc.charge_standards,\n" +
				"       thc.no_receive_amount,\n" +
				"       thc.receive_amount,\n" +
				"       thc.collect,\n" +
				"       thc.no_unconfirm_amount,\n" +
				"       thc.unconfirm_amount,\n" +
				"       thc.difference,\n" +
				"       thc.status,\n" +
				"       thc.creation_date,\n" +
				"       thc.created_by,\n" +
				"       thc.last_updated_by,\n" +
				"       thc.last_update_date,\n" +
				"       thc.last_update_login,\n" +
				"       thc.addition_rate,\n" +
				"       thc.hwb_year,\n" +
				"       thc.batch_code,\n" +
				"       thc.promotion_start_date,\n" +
				"       thc.promotion_end_date,\n" +
				"       thc.hwb_type,\n" +
				"       thc.promotion_location\n" +
				"       )\n" +
				"       \n" +
				"       select\n" +
				"        seq_tta_hwb_checking.nextval,\n" +
				"        thb.promotion_section,\n" +
				"        thb.description_cn,\n" +
				"        thb.award_description,\n" +
				"        thb.group_desc,\n" +
				"        thb.dept_desc,\n" +
				"        null,\n" +
				"        null,\n" +
				"        thb.brand_cn,\n" +
				"        null ,\n" +
				"        thb.promotion_section||'-'||decode(trim(thb.hwb_type),'Award',thb.description_cn)||'-'||thb.group_desc||'-'||thb.brand_cn||'-'||thb.vendor_code,\n" +
				"        thb.vendor_code,\n" +
				"        thb.vendor_name,\n" +
				"        thb.company_standard,\n" +
				"        thb.agreement_standard,\n" +
				"        thb.num,\n" +
				"        thb.charge_standards,\n" +
				"          (case   \n" +
				"    \n" +
				"  when nvl(thb.hwb_type,'-1') = 'Award'   then\n" +
				"       round(decode(trim(thb.charge_standards),'公司标准',nvl(thb.company_standard,0),'协定标准',nvl(thb.agreement_standard,0)),0)\n" +
				"  when nvl(thb.hwb_type,'-1') = '席位费'  then\n" +
				"      round(nvl(thb.company_standard,0) * nvl(thb.num,0),0)\n" +
				"  else  \n" +
				"     0 \n" +
				"  end ) ,\n" +
				"        round(  (case   \n" +
				"    \n" +
				"  when nvl(thb.hwb_type,'-1') = 'Award'   then\n" +
				"       decode(trim(thb.charge_standards),'公司标准',nvl(thb.company_standard,0),'协定标准',nvl(thb.agreement_standard,0))\n" +
				"  when nvl(thb.hwb_type,'-1') = '席位费'  then\n" +
				"      nvl(thb.company_standard,0) * nvl(thb.num,0)\n" +
				"  else  \n" +
				"     0 \n" +
				"  end )*(100+tcm.addition_rate)/100,0),\n" +
				"        'TTA',\n" +
				"          (case   \n" +
				"    \n" +
				"  when nvl(thb.hwb_type,'-1') = 'Award'   then\n" +
				"       round(decode(trim(thb.charge_standards),'公司标准',nvl(thb.company_standard,0),'协定标准',nvl(thb.agreement_standard,0)),0)\n" +
				"  when nvl(thb.hwb_type,'-1') = '席位费'  then\n" +
				"      round(nvl(thb.company_standard,0) * nvl(thb.num,0),0)\n" +
				"  else  \n" +
				"     0 \n" +
				"  end ) ,\n" +
				"        round(  (case   \n" +
				"    \n" +
				"  when nvl(thb.hwb_type,'-1') = 'Award'   then\n" +
				"       decode(trim(thb.charge_standards),'公司标准',nvl(thb.company_standard,0),'协定标准',nvl(thb.agreement_standard,0))\n" +
				"  when nvl(thb.hwb_type,'-1') = '席位费'  then\n" +
				"      nvl(thb.company_standard,0) * nvl(thb.num,0)\n" +
				"  else  \n" +
				"     0 \n" +
				"  end )*(100+tcm.addition_rate)/100,0), \n" +
				"        0,\n" +
				"        1,\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				userId+"         created_by,\n" +
				userId  +      " last_updated_by,\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				userId+" last_update_login,\n" +
				"        tcm.addition_rate,\n" +
				"        thb.hwb_year,\n" +
				"'" +batchCode +"'" +",\n" +
				"       thb.promotion_start_date,\n" +
				"       thb.promotion_end_date,\n" +
				"       thb.hwb_type,\n" +
				"        thb.promotion_location\n" +
				"       from \n" +
				"       \n" +
				"       tta_hwb_base_line thb\n" +
				"       left join tta_contract_master tcm on thb.vendor_code = tcm.vendor_nbr\n" +

				"       \n" +
				"       where thb.promotion_section = '" + ps+"'\n"  ;
	}

	public static String getUpdateReportBase(Integer userId,String ps){
		return "";
	}

	public static String getUpdateReportBasePog(Integer userId,String ps){
		return "update tta_hwb_base_line  tob set tob.is_create = 'Y' where tob.promotion_section ='"+ps+"'"  ;
	}

	public static String getUpdateReportBaseHistory(Integer userId,String ps){
		return  "insert  into tta_hwb_checking_record\n" +
				"\n" +
				"select * from tta_hwb_checking hwb " +
				"where  hwb.promotion_section = '"+ps+"'";
	}

	public static String getUpdateReportBaseLastTimes(String batchCode,Integer userId,String ps,String dateString){
		return  "insert into  tta_hwb_checking thc\n" +
				"(       thc.hwb_id,\n" +
				"       thc.promotion_section,\n" +
				"       thc.description_cn,\n" +
				"       thc.award_description,\n" +
				"       thc.group_desc,\n" +
				"       thc.dept_desc,\n" +
				"       thc.dept_code,\n" +
				"       thc.group_code,\n" +
				"       thc.brand_cn,\n" +
				"       thc.brand_en,\n" +
				"       thc.content,\n" +
				"       thc.prior_vendor_code,\n" +
				"       thc.prior_vendor_name,\n" +
				"       thc.company_standard,\n" +
				"       thc.agreement_standard,\n" +
				"       thc.num,\n" +
				"       thc.charge_standards,\n" +
				"       thc.no_receive_amount,\n" +
				"       thc.receive_amount,\n" +
				"       thc.collect,\n" +
				"       thc.no_unconfirm_amount,\n" +
				"       thc.unconfirm_amount,\n" +
				"       thc.difference,\n" +
				"       thc.status,\n" +
				"       thc.creation_date,\n" +
				"       thc.created_by,\n" +
				"       thc.last_updated_by,\n" +
				"       thc.last_update_date,\n" +
				"       thc.last_update_login,\n" +
				"       thc.addition_rate,\n" +
				"       thc.hwb_year,\n" +
				"       thc.batch_code,\n" +
				"       thc.promotion_start_date,\n" +
				"       thc.promotion_end_date,\n" +
				"       thc.hwb_type,\n" +
				"       thc.from_where,\n" +
				"       thc.promotion_location\n" +
				"       )\n" +
				"       \n" +
				"       SELECT\n" +
				"        seq_tta_hwb_checking.nextval,\n" +
				"       tcch.promotion_section,\n" +
				"       tcch.description_cn,\n" +
				"       tcch.award_description,\n" +
				"       tcch.group_desc,\n" +
				"       tcch.dept_desc,\n" +
				"       tcch.dept_code,\n" +
				"       tcch.group_code,\n" +
				"       tcch.brand_cn,\n" +
				"       tcch.brand_en,\n" +
				"       tcch.content,\n" +
				"       tcch.prior_vendor_code,\n" +
				"       tcch.prior_vendor_name,\n" +
				"       tcch.company_standard,\n" +
				"       tcch.agreement_standard,\n" +
				"       tcch.num,\n" +
				"       tcch.charge_standards,\n" +
				"       tcch.no_receive_amount,\n" +
				"       tcch.receive_amount,\n" +
				"       tcch.collect,\n" +
				"       tcch.no_unconfirm_amount,\n" +
				"       tcch.unconfirm_amount,\n" +
				"       tcch.difference,\n" +
				"       tcch.status,\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				"       tcch.created_by,\n" +
				"       tcch.last_updated_by,\n" +
				"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
				"       tcch.last_update_login,\n" +
				"       tcch.addition_rate,\n" +
				"       tcch.hwb_year,\n" +
				"'" +batchCode +"'," +"\n" +
				"       tcch.promotion_start_date,\n" +
				"       tcch.promotion_end_date,\n" +
				"       tcch.hwb_type,\n" +
				"       to_char(tcch.creation_date,'yyyymm'),\n" +
				"       tcch.promotion_location  \n" +
				"       \n" +
				"       FROM    tta_hwb_checking  tcch \n" +
				"	inner join tta_report_header trh on tcch.batch_code = trh.batch_id\n" +
				"	where tcch.purchase = 'A03' and trh.is_publish = 'Y' and nvl(tcch.status,1) = 1\n" +
				"AND exists\n" +
				"(\n" +
				"SELECT 1 FROM (\n" +
				"SELECT max(tcc.creation_date) creation_date FROM    tta_hwb_checking   tcc \n" +
				"	inner join tta_report_header th on tcc.batch_code = th.batch_id and th.is_publish = 'Y' and nvl(tcc.status,1) = 1\n" +
				"	where tcc.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'))\n" +
				" tccb where to_char(tccb.creation_date,'yyyy-mm-dd') = to_char(tcch.creation_date,'yyyy-mm-dd'))"  ;
	}
    private Integer hwbId;
    private String promotionSection;
    private String descriptionCn;
    private String awardDescription;
    private String groupDesc;
    private String deptDesc;
    private String deptCode;
    private String groupCode;
    private String brandCn;
    private String brandEn;
    private String content;
    private String priorVendorCode;
    private String priorVendorName;
    private BigDecimal companyStandard;
    private BigDecimal agreementStandard;
    private Integer num;
    private String chargeStandards;
    private BigDecimal noReceiveAmount;
    private BigDecimal receiveAmount;
    private String collect;
    private BigDecimal noUnconfirmAmount;
    private BigDecimal unconfirmAmount;
    private BigDecimal difference;
    private String purchase;
    private String exemptionReason;
    private String exemptionReason2;
    private String debitOrderCode;
    private BigDecimal receipts;
    private String remark;
    private Integer status;
    private Integer parentId;
    private String parentVendorCode;
    private String parentVendorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer transferOutPerson;
    private Integer transferLastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    private BigDecimal additionRate;
    private Integer processId;
    private Integer hwbYear;
    private String batchCode;
	private Integer  transferInPerson ;
	private String promotionLocation;
    private Integer operatorUserId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date promotionStartDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date promotionEndDate;
	private String hwbType ;
	private String transferInPersonName;
	private String transferOutPersonName;
	private String transferLastPersonName;
	private String createdByName;
	private String processCode;
	private String processStatusName;
	private Integer processReId;
	private String lastUpdatedByName;
	private Integer fileId;
	private String sourceFileName;
	private String purchaseName;
	private String exemptionReasonName;
	private String exemptionReason2Name;
	private BigDecimal concludeRate;
	private String taskIds;
	private int groupHwbCnt;//当前按照部门提交HWB数量
	private Integer adjustBy;
	private String adjustByName;
	private BigDecimal adjustAmount;
	private String currentUserName;
	private String startUserName;
	private String valueAll;
	private String processStatus;
	public Integer getHwbId() {
		return hwbId;
	}

	public void setHwbId(Integer hwbId) {
		this.hwbId = hwbId;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}

	
	public String getPromotionSection() {
		return promotionSection;
	}

	public void setDescriptionCn(String descriptionCn) {
		this.descriptionCn = descriptionCn;
	}

	
	public String getDescriptionCn() {
		return descriptionCn;
	}

	public void setAwardDescription(String awardDescription) {
		this.awardDescription = awardDescription;
	}

	
	public String getAwardDescription() {
		return awardDescription;
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

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getContent() {
		return content;
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

	public void setCompanyStandard(BigDecimal companyStandard) {
		this.companyStandard = companyStandard;
	}

	
	public BigDecimal getCompanyStandard() {
		return companyStandard;
	}

	public void setAgreementStandard(BigDecimal agreementStandard) {
		this.agreementStandard = agreementStandard;
	}

	
	public BigDecimal getAgreementStandard() {
		return agreementStandard;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	
	public Integer getNum() {
		return num;
	}

	public void setChargeStandards(String chargeStandards) {
		this.chargeStandards = chargeStandards;
	}

	
	public String getChargeStandards() {
		return chargeStandards;
	}

	public void setNoReceiveAmount(BigDecimal noReceiveAmount) {
		this.noReceiveAmount = noReceiveAmount;
	}

	
	public BigDecimal getNoReceiveAmount() {
		return noReceiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	
	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	
	public String getCollect() {
		return collect;
	}

	public void setNoUnconfirmAmount(BigDecimal noUnconfirmAmount) {
		this.noUnconfirmAmount = noUnconfirmAmount;
	}

	
	public BigDecimal getNoUnconfirmAmount() {
		return noUnconfirmAmount;
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

	public void setAdditionRate(BigDecimal additionRate) {
		this.additionRate = additionRate;
	}

	
	public BigDecimal getAdditionRate() {
		return additionRate;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	
	public Integer getProcessId() {
		return processId;
	}

	public void setHwbYear(Integer hwbYear) {
		this.hwbYear = hwbYear;
	}

	
	public Integer getHwbYear() {
		return hwbYear;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	
	public String getBatchCode() {
		return batchCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getTransferInPerson() {
		return transferInPerson;
	}

	public void setTransferInPerson(Integer transferInPerson) {
		this.transferInPerson = transferInPerson;
	}

	public String getPromotionLocation() {
		return promotionLocation;
	}

	public void setPromotionLocation(String promotionLocation) {
		this.promotionLocation = promotionLocation;
	}

	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	public String getHwbType() {
		return hwbType;
	}

	public void setHwbType(String hwbType) {
		this.hwbType = hwbType;
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

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
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

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
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

	public int getGroupHwbCnt() {
		return groupHwbCnt;
	}

	public void setGroupHwbCnt(int groupHwbCnt) {
		this.groupHwbCnt = groupHwbCnt;
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

	public BigDecimal getConcludeRate() {
		return concludeRate;
	}

	public void setConcludeRate(BigDecimal concludeRate) {
		this.concludeRate = concludeRate;
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

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
}
