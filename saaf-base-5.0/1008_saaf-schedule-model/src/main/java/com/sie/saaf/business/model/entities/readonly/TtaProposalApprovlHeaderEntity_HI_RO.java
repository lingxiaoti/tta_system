package com.sie.saaf.business.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaProposalApprovlHeaderEntity_HI_RO Entity Object
 * Wed Mar 25 11:07:42 CST 2020  Auto Generate
 */

public class TtaProposalApprovlHeaderEntity_HI_RO {

	public static String BIC_QUERY = "     SELECT " +
			"listagg(bp.email,',') within group(order by  upper(bp.email) asc) receiver " +
			//" 'xxxx,xxxxx' receiver " +
			"FROM base_users bu,\n" +
			"                   base_person bp\n" +
			"                   \n" +
			"                   where bu.person_id = bp.person_id\n" +
			"                   and bu.user_type = '45'\n" +
			"                   \n" +
			"                   group by user_type ";

	public static String getSubmitterQuery(String dateString,String oldDateString){
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       tph.order_nbr orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       tph.vendor_nbr,\n" +
				"       tph.vendor_name,\n" +
				"       tph.proposal_year,\n" +
				"       tpth.brand_cn,\n" +
				"       lookup3.meaning sale_type_name,--销售方式\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver -- 制单人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_PROPOSAL.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp('" + dateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join tta_proposal_header tph\n" +
				"    on abl.business_key = tph.proposal_id\n" +
				"   and tph.status != 'D'\n" +
				"  left join tta_proposal_terms_header tpth\n" +
				"    on tph.proposal_id = tpth.proposal_id\n" +
				"  left join (select lookup_type, lookup_code, meaning\n" +
				"               from base_lookup_values\n" +
				"              where lookup_type = 'SALE_WAY') lookup3\n" +
				"    on lookup3.lookup_code = tph.sale_type\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='PROPOSAL_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tph.status  \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = tph.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by ";
	}
//	public static String SEND_SUBMITTER_QUERY = "select \n" +
//			"    abl.created_by --流程发起人\n" +
//			"    ,abl.business_key--业务主键(单据ID)\n" +
//			"    ,abl.result  --流程单据状态\n" +
//			"    ,tph.order_nbr orderNo\n" +
//			"    ,tph.vendor_nbr\n" +
//			"    ,tph.vendor_name\n" +
//			"    ,tph.proposal_year\n" +
//			"    ,tpth.brand_cn\n" +
//			"    ,lookup3.meaning sale_type_name\n" +
//			"    ,bu.user_name\n" +
//			"    ,bu.user_full_name\n" +
//			"    ,to_char(abl.list_id) onlyCode\n" +
//			"    ,bp.email receiver\n" +
//			//"    ,bp.email_test receiver\n" +
//			"    from   (SELECT proc_inst_id,business_key,created_by,result,list_id FROM  act_bpm_list abll\n" +
//			"            where abll.proc_def_key = 'TTA_PROPOSAL.-999' \n" +
//			"            and abll.result = 'ALLOW' and \n" +
//			"            and exists (select 1 from base_lookup_values blv where blv.lookup_type = 'TTA_APPROVAL_DATE' and abll.end_time > to_date(trim(blv.meaning),'YYYY-MM-DD HH24:MI:SS'))) abl\n" +
//			"           join tta_proposal_header tph on abl.business_key = tph.proposal_id and tph.status != 'D'\n" +
//			"           left join tta_proposal_terms_header tpth on tph.proposal_id = tpth.proposal_id\n" +
//			"           left join  (select lookup_type,lookup_code,meaning\n" +
//			"        from base_lookup_values where lookup_type='SALE_WAY') lookup3 on lookup3.lookup_code = tph.sale_type\n" +
//			"           left join base_users bu on bu.user_id = tph.created_by\n" +
//			"           left join base_users bu2 on abl.created_by = bu2.user_id\n" +
//			"           left join base_person bp on bu2.person_id = bp.person_id\n" +
//			"     where 1=1 " +
//			//"not exists (SELECT * FROM TTA_PROPOSAL_APPROVL_HEADER tpah where tpah.only_code = to_char(abl.list_id) and tpah.way =:way and tpah.status ='Y')  " +
//			"order by abl.created_by";

	public static String getQuery(String dateString,String oldDateString){
		return "select abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       tph.order_nbr || '_' || tph.version_code      orderNo,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       tph.vendor_nbr,\n" +
				"       tph.vendor_name,\n" +
				"       tph.proposal_year,\n" +
				"       tpth.brand_cn,\n" +
				"       lookup3.meaning     sale_type_name,--销售方式\n" +
				"       bu.user_name,\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_PROPOSAL.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + dateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join tta_proposal_header tph\n" +
				"    on abl.business_key = tph.proposal_id\n" +
				"   and tph.status != 'D'\n" +
				"  left join tta_proposal_terms_header tpth\n" +
				"    on tph.proposal_id = tpth.proposal_id\n" +
				"  left join (select lookup_type, lookup_code, meaning\n" +
				"               from base_lookup_values\n" +
				"              where lookup_type = 'SALE_WAY') lookup3\n" +
				"    on lookup3.lookup_code = tph.sale_type\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='PROPOSAL_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tph.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = tph.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             \n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getOsdQuery(String newDateString, String oldDateString) {
		String sql = "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       trph.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_ACTIVITY.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by\n" +
				"       from tta_report_process_header trp join \n" +
				"     tta_osd_monthly_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     --join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
		return sql;
	}

	public static String getDmQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       trph.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_ACTIVITY.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.duration_period) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by\n" +
				"       from tta_report_process_header trp join \n" +
				"     tta_dm_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     --join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getCwQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       trph.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_ACTIVITY.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by\n" +
				"       from tta_report_process_header trp join \n" +
				"     tta_cw_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     --join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getNppQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       trph.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_ACTIVITY.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(trh.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_npp_new_product_report tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}
	public static String getHwbQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       trph.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_ACTIVITY.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_hwb_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      --join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getFgQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       trph.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_ACTIVITY.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(trh.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_free_goods tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	private Integer approveId;
	private String status;//状态 Y:成功 N:失败
	private String way;//方式
	private String sendWay;//发送方式
	private String orderNo;//单据号
	private String reason;//失败原因
	private String receiver;//接收人
	private String onlyCode;//唯一编码
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private String remark;//备注
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String userName;//用户名字
	private String userFullName;//用户英文名
	private String saleTypeName;//销售方式
	private String brandCn;//品牌
	private String proposalYear;//proposal制作年度
	private String vendorName;//供应商名字
	private String vendorNbr;//供应商编号
	private String assignee;//办理人
	private String businessKey;//业务主键(单据id)
	private String orderType;//单据类型
	private String promotionSectionName;//促销期间
	private String batchCode;//批次号
	private String orderStatus;//单据状态
	private Integer isPublishBy;//发布人
	private String  isPublishByEmail;//发布人邮箱
	private String type;

	public static String getSoleStandarQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       tsah.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       tsah.vendor_code vendor_nbr,--供应商编号\n" +
				"       tsah.vendor_name vendor_name,--供应商名称\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_SOLE_STANDAR_HEADER.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"    select \n" +
				"    tsa.sole_agrt_h_id,\n" +
				"    tsa.sole_agrt_code || '_' || tsa.sole_agrt_version as order_no, \n" +
				"    tsa.status,\n" +
				"    tsa.vendor_code,\n" +
				"    tsa.vendor_name,\n" +
				"    tsa.created_by\n" +
				"   from tta_sole_agrt tsa\n" +
				"  where tsa.agrt_type = 'standard' and tsa.status != 'E'-- 独家协议标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sole_agrt_h_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='EXCLUSIVE_STAUTS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getNonSoleStandarQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       tsah.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       tsah.vendor_code vendor_nbr,--供应商编号\n" +
				"       tsah.vendor_name vendor_name,--供应商名称\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_SOLE_NON_STANDAR_HEADER.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"    select \n" +
				"    tsa.sole_agrt_h_id,\n" +
				"    tsa.sole_agrt_code || '_' || tsa.sole_agrt_version as order_no, \n" +
				"    tsa.status,\n" +
				"    tsa.vendor_code,\n" +
				"    tsa.vendor_name,\n" +
				"    tsa.created_by\n" +
				"   from tta_sole_agrt tsa\n" +
				"  where tsa.agrt_type = 'nostandard' and tsa.status != 'E'-- 独家协议非标准标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sole_agrt_h_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='EXCLUSIVE_STAUTS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getSupplementStandarQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       tsah.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       tsah.vendor_code vendor_nbr,--供应商编号\n" +
				"       tsah.vendor_name vendor_name,--供应商名称\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_SA_STANDAR_HEADER.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"  select \n" +
				"       t.sa_std_header_id,\n" +
				"       t.sa_std_code || '_' || t.sa_std_version as order_no,\n" +
				"       t.status,\n" +
				"       t.vendor_code,\n" +
				"       t.vendor_name,\n" +
				"       t.created_by \n" +
				"       from tta_sa_std_header t where t.status != 'D' --补充协议标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sa_std_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_SUP_AGM_ST_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getSupplementNonStandarQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       tsah.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       tsah.vendor_code vendor_nbr,--供应商编号\n" +
				"       tsah.vendor_name vendor_name,--供应商名称\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_SA_NON_STANDAR_HEADER') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"  select \n" +
				"       t.sa_non_standar_header_id,\n" +
				"       t.order_no || '_' || t.order_version as order_no,\n" +
				"       t.status,\n" +
				"       t.vendor_code,\n" +
				"       t.vendor_name,\n" +
				"       t.created_by \n" +
				"       from tta_sa_non_standar_header t where t.status != 'E' --补充协议标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sa_non_standar_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_STD_APPLY_HEADER_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getElecConQuery(String newDateString, String oldDateString) {
		return "select \n" +
				"       abl.created_by, --流程发起人\n" +
				"       abl.business_key, --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       abl.proc_inst_id,\n" +
				"       ark.create_time_,\n" +
				"       usersName.user_name assignee,\n" +
				"       tsah.order_no,--单据编号\n" +
				"       lookup1.meaning order_status,--单据状态\n" +
				"       tsah.vendor_code vendor_nbr,--供应商编号\n" +
				"       tsah.vendor_name vendor_name,--供应商名称\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       aht.id_             onlyCode,--任务Id\n" +
				"       bp.email            receiver -- 接收人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result\n" +
				"          FROM act_bpm_list\n" +
				"         where proc_def_key = 'TTA_ELEC_CONTRACT.-999') abl\n" +
				"  join act_ru_task ark\n" +
				"    on abl.proc_inst_id = ark.proc_inst_id_\n" +
				"   and ark.create_time_ >= \n" +
				"       to_timestamp('" + oldDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"   and ark.create_time_ <\n" +
				"       to_timestamp('" + newDateString + "', 'yyyy-mm-dd hh24:mi:ss')\n" +
				"  left join act_hi_taskinst aht\n" +
				"    on aht.proc_inst_id_ = abl.proc_inst_id\n" +
				"   and aht.end_time_ is null --审批中的流程\n" +
				"  join (\n" +
				"  select \n" +
				"       t.elec_con_header_id,\n" +
				"       t.order_no || '_' || t.order_version as order_no,\n" +
				"       t.status,\n" +
				"       t.vendor_code,\n" +
				"       t.vendor_name,\n" +
				"       t.created_by \n" +
				"       from tta_elec_con_header t where t.status != 'D' --电子签章\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.elec_con_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='PROPOSAL_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join (select task_id, bus.user_name user_name\n" +
				"               from act_bpm_task_delegate abtd, base_users bus\n" +
				"              where abtd.delete_flag = 0 -- 委派任务\n" +
				"                and abtd.status = 'PENDING'\n" +
				"                and abtd.delegate_user_id = bus.user_id\n" +
				"              group by abtd.task_id, bus.user_name\n" +
				"             union\n" +
				"             SELECT ark.id_ task_id, ark.assignee_ user_name\n" +
				"               FROM act_ru_task ark -- 运行任务\n" +
				"              where ark.assignee_ is not null\n" +
				"             union\n" +
				"             select ari.task_id_ task_id, ari.user_id_ user_name\n" +
				"               from act_ru_identitylink ari, base_users bus -- 身份认证任务\n" +
				"              where ari.user_id_ = bus.user_name\n" +
				"                and ari.task_id_ is not null) usersName\n" +
				"    on ark.id_ = usersName.Task_Id\n" +
				"  left join base_person bp\n" +
				"    on usersName.user_name = bp.employee_number\n" +
				" where 1 = 1\n" +
				" order by usersName.user_name ";
	}

	public static String getOsdSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       trph.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver, -- 制单人邮箱\n" +
				"		trph.is_publish_by,\n" +
				"		bp3.email is_publish_by_email--发布人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by,\n" +
				"       max(trh.is_publish_by) is_publish_by\n" +
				"       from tta_report_process_header trp join \n" +
				"     tta_osd_monthly_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"     join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				"  left join base_users bu3 --发布人\n" +
				"    on  trph.is_publish_by = bu3.user_id\n" +
				"  left join base_person bp3\n" +
				"    on bu3.person_id = bp3.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by ";
	}

	public static String getDmSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       trph.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver, -- 制单人邮箱\n" +
				"		trph.is_publish_by,\n" +
				"		bp3.email is_publish_by_email--发布人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.duration_period) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by,\n" +
				"		max(trh.is_publish_by) is_publish_by -- 发布人\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_dm_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				"  left join base_users bu3 --发布人\n" +
				"    on trph.is_publish_by = bu3.user_id\n" +
				"  left join base_person bp3\n" +
				"    on bu3.person_id = bp3.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by ";
	}

	public static String getCwSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       trph.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver, -- 制单人邮箱\n" +
				"		trph.is_publish_by,\n" +
				"		bp3.email is_publish_by_email--发布人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by,\n" +
				"       max(trh.is_publish_by) is_publish_by\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_cw_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				"  left join base_users bu3 --发布人\n" +
				"    on trph.is_publish_by = bu3.user_id\n" +
				"  left join base_person bp3\n" +
				"    on bu3.person_id = bp3.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by ";
	}

	public static String getNppSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       trph.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver, -- 制单人邮箱\n" +
				"		trph.is_publish_by,\n" +
				"		bp3.email is_publish_by_email--发布人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(trh.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by,\n" +
				"       max(trh.is_publish_by) is_publish_by -- 发布人\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_npp_new_product_report tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				"  left join base_users bu3 --发布人\n" +
				"    on trph.is_publish_by = bu3.user_id\n" +
				"  left join base_person bp3\n" +
				"    on bu3.person_id = bp3.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public static String getHwbSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       trph.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver, -- 制单人邮箱\n" +
				"		trph.is_publish_by,\n" +
				"		bp3.email is_publish_by_email--发布人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(tomc.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by,\n" +
				"       max(trh.is_publish_by) is_publish_by -- 发布人\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_hwb_checking tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				"  left join base_users bu3 --发布人\n" +
				"    on trph.is_publish_by = bu3.user_id\n" +
				"  left join base_person bp3\n" +
				"    on bu3.person_id = bp3.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public static String getFgSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       trph.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       trph.promotion_section promotionSectionName,--促销期间\n" +
				"       trph.batch_code,--批次号\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver, -- 制单人邮箱\n" +
				"		trph.is_publish_by,\n" +
				"		bp3.email is_publish_by_email--发布人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"       select\n" +
				"       tomc.process_id report_process_header_id, \n" +
				"       max(trp.batch_code) order_no,\n" +
				"       max(trp.status) status,\n" +
				"       max(trh.promotion_section) promotion_section,\n" +
				"       max(tomc.batch_code) batch_code, \n" +
				"       max(trp.created_by) created_by,\n" +
				"       max(trh.is_publish_by) is_publish_by -- 发布人\n" +
				"       from tta_report_process_header trp join \n" +
				"      tta_free_goods tomc on trp.report_process_header_id = tomc.process_id\n" +
				"      join tta_report_header trh on trh.batch_id =  tomc.batch_code\n" +
				"     where nvl(tomc.process_id,-1) != -1 \n" +
				"     group by tomc.process_id\n" +
				"  ) trph\n" +
				"    on abl.business_key = trph.report_process_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = trph.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = trph.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				"  left join base_users bu3 --发布人\n" +
				"    on trph.is_publish_by = bu3.user_id\n" +
				"  left join base_person bp3\n" +
				"    on bu3.person_id = bp3.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public static String getSoleStandarSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       tsah.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       tsah.vendor_code vendor_nbr,\n" +
				"       tsah.vendor_name vendor_name,\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver -- 制单人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_SOLE_STANDAR_HEADER.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"    select \n" +
				"    tsa.sole_agrt_h_id,\n" +
				"    tsa.sole_agrt_code || '_' || tsa.sole_agrt_version as order_no, \n" +
				"    tsa.status,\n" +
				"    tsa.vendor_code,\n" +
				"    tsa.vendor_name,\n" +
				"    tsa.created_by\n" +
				"   from tta_sole_agrt tsa\n" +
				"  where tsa.agrt_type = 'standard' and tsa.status != 'E'-- 独家协议标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sole_agrt_h_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='EXCLUSIVE_STAUTS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public static String getNonsoleStandarSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       tsah.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       tsah.vendor_code vendor_nbr,\n" +
				"       tsah.vendor_name vendor_name,\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver -- 制单人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_SOLE_NON_STANDAR_HEADER.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"    select \n" +
				"    tsa.sole_agrt_h_id,\n" +
				"    tsa.sole_agrt_code || '_' || tsa.sole_agrt_version as order_no, \n" +
				"    tsa.status,\n" +
				"    tsa.vendor_code,\n" +
				"    tsa.vendor_name,\n" +
				"    tsa.created_by\n" +
				"   from tta_sole_agrt tsa\n" +
				"  where tsa.agrt_type = 'nostandard' and tsa.status != 'E'-- 独家协议非标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sole_agrt_h_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='EXCLUSIVE_STAUTS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public static String getSupplementStandarSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       tsah.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       tsah.vendor_code vendor_nbr,\n" +
				"       tsah.vendor_name vendor_name,\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver -- 制单人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_SA_STANDAR_HEADER.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"  select \n" +
				"       t.sa_std_header_id,\n" +
				"       t.sa_std_code || '_' || t.sa_std_version as order_no,\n" +
				"       t.status,\n" +
				"       t.vendor_code,\n" +
				"       t.vendor_name,\n" +
				"       t.created_by \n" +
				"       from tta_sa_std_header t where t.status != 'D' --补充协议标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sa_std_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_SUP_AGM_ST_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public static String getSupplementNonStandarSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       tsah.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       tsah.vendor_code vendor_nbr,\n" +
				"       tsah.vendor_name vendor_name,\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       --bp.email receiver,提交单据的人邮箱\n" +
				"       bp2.email receiver -- 制单人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_SA_NON_STANDAR_HEADER.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"  select \n" +
				"       t.sa_non_standar_header_id,\n" +
				"       t.order_no || '_' || t.order_version as order_no,\n" +
				"       t.status,\n" +
				"       t.vendor_code,\n" +
				"       t.vendor_name,\n" +
				"       t.created_by \n" +
				"       from tta_sa_non_standar_header t where t.status != 'D' --补充协议非标准\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.sa_non_standar_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_STD_APPLY_HEADER_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public static String getElecContSubmitterQuery() {
		return "select abl.created_by, --流程发起人   \n" +
				"       abl.business_key , --业务主键(单据ID)\n" +
				"       abl.result, --流程单据状态\n" +
				"       tsah.order_no orderNo,--单据编号\n" +
				"       lookup1.meaning order_status, --单据状态\n" +
				"       tsah.vendor_code vendor_nbr,\n" +
				"       tsah.vendor_name vendor_name,\n" +
				"       bu.user_name,--制单人\n" +
				"       bu.user_full_name,\n" +
				"       to_char(abl.list_id) onlyCode,\n" +
				"       bp.email receiver--提交单据的人邮箱\n" +
				"       --bp2.email receiver -- 制单人邮箱\n" +
				"  from (SELECT proc_inst_id, business_key, created_by, result, list_id,abll.bill_no\n" +
				"          FROM act_bpm_list abll -- 审批通过的单据\n" +
				"         where abll.proc_def_key = 'TTA_ELEC_CONTRACT.-999'\n" +
				"           and abll.result = 'ALLOW'\n" +
				"           and abll.end_time >=\n" +
				"               to_timestamp(:oldDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and abll.end_time <\n" +
				"               to_timestamp(:newDateString, 'yyyy-mm-dd hh24:mi:ss')\n" +
				"           and exists\n" +
				"           (select 1\n" +
				"                    from base_lookup_values blv\n" +
				"                   where blv.lookup_type = 'TTA_APPROVAL_DATE'\n" +
				"                     and abll.end_time >\n" +
				"                         to_date(trim(blv.meaning), 'YYYY-MM-DD HH24:MI:SS'))\n" +
				"                       \n" +
				"                       ) abl\n" +
				"  join (\n" +
				"  select \n" +
				"       t.elec_con_header_id,\n" +
				"       t.order_no || '_' || t.order_version as order_no,\n" +
				"       t.status,\n" +
				"       t.vendor_code,\n" +
				"       t.vendor_name,\n" +
				"       t.created_by \n" +
				"       from tta_elec_con_header t where t.status != 'D' --电子签章\n" +
				"  ) tsah\n" +
				"    on abl.business_key = tsah.elec_con_header_id\n" +
				"  left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='PROPOSAL_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) lookup1  \n" +
				"     on lookup1.lookup_code = tsah.status \n" +
				"  left join base_users bu -- 制单人\n" +
				"    on bu.user_id = tsah.created_by\n" +
				"  left join base_person bp2 \n" +
				"    on  bu.person_id = bp2.person_id  \n" +
				"  left join base_users bu2 --提交人\n" +
				"    on abl.created_by = bu2.user_id\n" +
				"  left join base_person bp\n" +
				"    on bu2.person_id = bp.person_id\n" +
				" where 1 = 1\n" +
				" order by abl.created_by";
	}

	public void setApproveId(Integer approveId) {
		this.approveId = approveId;
	}


	public Integer getApproveId() {
		return approveId;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus() {
		return status;
	}

	public void setWay(String way) {
		this.way = way;
	}


	public String getWay() {
		return way;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getOrderNo() {
		return orderNo;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getReason() {
		return reason;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getReceiver() {
		return receiver;
	}

	public void setOnlyCode(String onlyCode) {
		this.onlyCode = onlyCode;
	}


	public String getOnlyCode() {
		return onlyCode;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getSendWay() {
		return sendWay;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getSaleTypeName() {
		return saleTypeName;
	}

	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPromotionSectionName() {
		return promotionSectionName;
	}

	public void setPromotionSectionName(String promotionSectionName) {
		this.promotionSectionName = promotionSectionName;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getIsPublishBy() {
		return isPublishBy;
	}

	public void setIsPublishBy(Integer isPublishBy) {
		this.isPublishBy = isPublishBy;
	}

	public String getIsPublishByEmail() {
		return isPublishByEmail;
	}

	public void setIsPublishByEmail(String isPublishByEmail) {
		this.isPublishByEmail = isPublishByEmail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
