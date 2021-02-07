package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * TtaReportHeaderEntity_HI_RO Entity Object
 * Sat Aug 03 15:30:12 CST 2019  Auto Generate
 */

public class TtaProposalStatusEntity_HI_RO {
	public static String  FIXED_LIST = "SELECT * FROM   tta_proposal_status_fix_filed_v where 1=1 ";
	public static String E_FIXED_LIST = "SELECT * FROM   tta_exclusive_status_fix_filed_v where 1=1 ";
	public static String  FILED_LIST = " select blv.meaning || tps.FILED FILEDS from base_lookup_values blv \n" +
			" left join tta_proposal_status_fix_filed_v tps on 1=1\n" +
			" where blv.lookup_type = 'TTA_PROPOSAL_PROCESS_NODE'\n" +
			" order by blv.lookup_code,tps.FILED";
	public static String getFindList(List<String> fixedList, String filedString){
		return  "select \n" +
				"nvl(tph.proposal_year,to_char(sysdate,'yyyy')) \"YEAR\" --年份\n" +
				",ts.is_submit_proposal --proposal是否需提交\n" +
				",ts.no_submit_reason --不提交的原因\n" +
				",ts.is_submit_contract --p合同是否需要提交\n" +
				",decode(nvl(trs.rel_supplier_id,-1),-1,'主','从') isMajor --是否主从\n" +
				",ts.supplier_code --供应商编号\n" +
				",ts.supplier_name --供应商名称\n" +
				",supBrand.brandName --品牌_中文\n" +
				",ts.owner_group_name --合同主要负责部门\n" +
				",termsClassLookUp.meaning terms_class_name --级别\n" +
				",saleTypeLookUp.meaning saleTypeName --销售方式\n" +
				",tpth.agreement_edition --合同版本\n" +
				",tph.order_nbr || '-' || tph.version_code orderNoVC --proposal单号+版本号\n" +
				",proposalStatusLookUp.meaning proposalStatusName --proposal单据状态\n" +
				",ttaProProcess.*\n" +
				",TO_CHAR(tch.creation_date,'YYYY-MM-DD HH24:MI:SS') CONTRACT_DATE\n" +
				"from \n" +
				"\n" +
				" tta_supplier ts \n" +
				" left join tta_proposal_header tph on ts.supplier_code = tph.vendor_nbr and tph.status != 'D' and nvl(tph.version_status,'1') = '1'\n" +
				" left join tta_contract_header tch on tph.order_nbr = tch.proposal_code and tph.version_code = tch.proposal_version\n" +
				" left join tta_rel_supplier trs on ts.supplier_code = trs.rel_supplier_code\n" +
				" left join (select trsb.rel_id , \n" +
				" --listagg(trsb.rel_brand_name,'/') within group(order by  trsb.rel_supplier_id asc) brandName \n" +
				" xmlagg(xmlparse(content trsb.rel_brand_name||',' wellformed) order by trsb.rel_supplier_id).getclobval() brandName\n" +
				" from tta_rel_supplier_brand trsb group by trsb.rel_id)\n" +
				"  supBrand on ts.supplier_id = supBrand.rel_id\n" +
				" left join tta_proposal_terms_header tpth on tph.proposal_id = tpth.proposal_id\n" +
				" left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='SALE_WAY' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) saleTypeLookUp on tph.sale_type = saleTypeLookUp.lookup_code\n" +
				" left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='PROPOSAL_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) proposalStatusLookUp on tph.status = proposalStatusLookUp.lookup_code\n" +
				"      \n" +
				" left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TERMS_CLASS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) termsClassLookUp on tpth.terms_class = termsClassLookUp.lookup_code \n" +
				" left join act_bpm_list  abl on tph.proposal_id = abl.business_key and abl.proc_def_key = 'TTA_PROPOSAL.-999'\n" +
				" left join \n" +
				" \n" +
				" (    select * from (\n" +
				"    --start 内1\n" +
				"    select tphis.PROC_INST_ID_,\n" +
				"    decode(sign(tphis.current_order_no-tphis.task_order_no),-1,'',\n" +
				"    decode(tpfix.FILED,'"+fixedList.get(0)+"',tphis.USERFULLNAME,'"+fixedList.get(1)+"',tphis.text_,'"+fixedList.get(2)+"',to_char(tphis.endtime,'yyyy-mm-dd hh24:mi:ss')))  va, \n" +
				"    tphis.taskname || tpfix.FILED tasknewname\n" +
				"      from (\n" +
				"   \n" +
				"   \n" +
				"    select * from (\n" +
				"   select tp.*,row_number() over (partition by tp.processinstanceid,tp.taskDefinitionId order by tp.startTime desc) nums from (  select * from \n" +
				"  tta_proposal_status_process_his_v tpsh\n" +
				"  left join tta_proposal_status_process_text_v tpst \n" +
				"  on tpsh.processInstanceId = tpst.proc_inst_id_ \n" +
				"  and tpsh.taskId = tpst.task_id_ where  tpsh.processDefinitionKey = 'TTA_PROPOSAL.-999') tp ) tptps where tptps.nums = 1 ) tphis\n" +
				"  \n" +
				"  left join tta_proposal_status_fix_filed_v tpfix  on 1=1\n" +
				"  --end 内1\n" +
				"  ) ttaPro pivot (max(va) for tasknewname in (\n" +
				filedString +
				"  ))) ttaProProcess on abl.proc_inst_id  =  ttaProProcess.PROC_INST_ID_\n" +
				" where \n" +
				" nvl(ts.is_submit_proposal,'N') = 'Y'" ;
	}

	public static String  FILED_LIST_ELEC = " select blv.meaning || tps.FILED FILEDS from base_lookup_values blv \n" +
			" left join tta_proposal_status_fix_filed_v tps on 1=1\n" +
			" where blv.lookup_type = 'TTA_ELEC_PROCESS_NODE'\n" +
			" order by blv.lookup_code,tps.FILED";

	public static String  FILED_LIST_EXCLUSIVE = "select blv.meaning || tps.FILED FILEDS from base_lookup_values blv \n" +
			" left join tta_proposal_status_fix_filed_v tps on 1=1\n" +
			" where blv.lookup_type = 'TTA_EXCLUSIVE_PROCESS_NODE'\n" +
			" order by to_number(blv.lookup_code),tps.FILED";

	public static String  FILED_LIST_EXCLUSIVE_BUSSINESS = "select blv.meaning || tps.FILED FILEDS from base_lookup_values blv \n" +
			" left join tta_exclusive_status_fix_filed_v tps on 1=1\n" +
			" where blv.lookup_type = 'TTA_EXCLUSIVE_PROCESS_NODE'\n" +
			" order by to_number(blv.lookup_code),tps.FILED";


	/**
	 *
	 * @param filedString
	 * @param reportType  2:【独家协议流程进度（非标&标准）】，1:【业务合同书流程进度报表】
	 * @return
	 */
	public static String getFindContractBookList(String filedString){

		/*String partSql = "select tep.*, tsar.*\n" +
				"  from tta_elec_process_v tep\n" +
				"  left join tta_sole_agrt_report_v tsar\n" +
				"    on tep.order_no = tsar.sole_agrt_code\n" +
				"    where tep.file_type in (2,3)";//独家协议流程进度（非标&标准）*/

		String sql = "select \n" +
				"       tepv.*,\n" +
				"       ttaproprocess.*\n" +
				" from " +
				"(" +
						("select * from tta_elec_process_v tep where tep.file_type not in (2,3)") +
				" ) tepv\n" +
				" left join act_bpm_list abl\n" +
				"    on tepv.elec_con_header_id = abl.business_key\n" +
				"   and abl.proc_def_key = 'TTA_ELEC_CONTRACT.-999'\n" +
				" left join\n" +
				" (select *\n" +
				"    from (\n" +
				"          --start 内1\n" +
				"          select tphis.proc_inst_id_,\n" +
				"                  decode(sign(tphis.current_order_no - tphis.task_order_no),\n" +
				"                         -1,\n" +
				"                         '',\n" +
				"                         decode(tpfix.filed,\n" +
				"                                'A',\n" +
				"                                tphis.userfullname,\n" +
				"                                'B',\n" +
				"                                tphis.text_,\n" +
				"                                'C',\n" +
				"                                to_char(tphis.endtime, 'yyyy-mm-dd hh24:mi:ss'))) va,\n" +
				"                  tphis.taskname || tpfix.filed tasknewname\n" +
				"            from (\n" +
				"                   \n" +
				"                   select *\n" +
				"                     from (select tp.*,\n" +
				"                                   row_number() over(partition by tp.processinstanceid, tp.taskdefinitionid order by tp.starttime desc) nums\n" +
				"                              from (\n" +
				"                                  select *\n" +
				"                                      from tta_process_his_v tpsh\n" +
				"                                      left join tta_proposal_status_process_text_v tpst\n" +
				"                                        on tpsh.processinstanceid =\n" +
				"                                           tpst.proc_inst_id_\n" +
				"                                       and tpsh.taskid = tpst.task_id_\n" +
				"                                     where tpsh.processdefinitionkey ='TTA_ELEC_CONTRACT.-999'\n" +
				"                                           and tpsh.lookup_type in ('TTA_ELEC_PROCESS_NODE','-1')\n" +
				"                                           ) tp) tptps\n" +
				"                    where tptps.nums = 1) tphis\n" +
				"            left join tta_proposal_status_fix_filed_v tpfix\n" +
				"              on 1 = 1\n" +
				"          --end 内1\n" +
				"          ) ttapro\n" +
				"  pivot(max(va)\n" +
				"     for tasknewname in(\n"
						+ filedString +
				"	))) ttaproprocess\n" +
				"    on abl.proc_inst_id = ttaproprocess.proc_inst_id_\n" +
				"    order by tepv.elec_order_no,tepv.order_no";
		return sql;
	}

	public static String getFindContractList(List<String> fixedList, String filedString){
		return  "select \n" +
				"nvl(tph.proposal_year,to_char(sysdate,'yyyy')) \"YEAR\" --年份\n" +
				",ts.is_submit_proposal --proposal是否需提交\n" +
				",ts.no_submit_reason --不提交的原因\n" +
				",ts.is_submit_contract --p合同是否需要提交\n" +
				",decode(nvl(trs.rel_supplier_id,-1),-1,'主','从') isMajor --是否主从\n" +
				",ts.supplier_code --供应商编号\n" +
				",ts.supplier_name --供应商名称\n" +
				",supBrand.brandName --品牌_中文\n" +
				",ts.owner_group_name --合同主要负责部门\n" +
				",termsClassLookUp.meaning terms_class_name --级别\n" +
				",saleTypeLookUp.meaning saleTypeName --销售方式\n" +
				",tpth.agreement_edition --合同版本\n" +
				",tph.order_nbr || '-' || tph.version_code orderNoVC --proposal单号+版本号\n" +
				",proposalStatusLookUp.meaning proposalStatusName --proposal单据状态\n" +
				",ttaProProcess.*\n" +
				",TO_CHAR(tch.creation_date,'YYYY-MM-DD HH24:MI:SS') CONTRACT_DATE\n" +
				"from \n" +
				" tta_supplier ts \n" +
				" left join tta_proposal_header tph on ts.supplier_code = tph.vendor_nbr and tph.status != 'D' and nvl(tph.version_status,'1') = '1'\n" +
				" left join tta_elec_con_header tch on tph.vendor_nbr = tch.vendor_code\n" +
				"   and tph.version_code = tch.order_version \n" +
				"   and tph.proposal_year = tch.contract_year \n" +
				" left join tta_rel_supplier trs on ts.supplier_code = trs.rel_supplier_code\n" +
				" left join (select trsb.rel_id , \n" +
				" --listagg(trsb.rel_brand_name,'/') within group(order by  trsb.rel_supplier_id asc) brandName \n" +
				" xmlagg(xmlparse(content trsb.rel_brand_name||',' wellformed) order by trsb.rel_supplier_id).getclobval() brandName\n" +
				" from tta_rel_supplier_brand trsb group by trsb.rel_id)\n" +
				"  supBrand on ts.supplier_id = supBrand.rel_id\n" +
				" left join tta_proposal_terms_header tpth on tph.proposal_id = tpth.proposal_id\n" +
				" left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='SALE_WAY' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) saleTypeLookUp on tph.sale_type = saleTypeLookUp.lookup_code\n" +
				" left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='PROPOSAL_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) proposalStatusLookUp on tph.status = proposalStatusLookUp.lookup_code\n" +
				"      \n" +
				" left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TERMS_CLASS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"      ) termsClassLookUp on tpth.terms_class = termsClassLookUp.lookup_code \n" +
				" left join act_bpm_list  abl on tph.proposal_id = abl.business_key and abl.proc_def_key = 'TTA_ELEC_CONTRACT.-999'\n" +
				" left join \n" +
				" \n" +
				" (    select * from (\n" +
				"    --start 内1\n" +
				"    select tphis.PROC_INST_ID_,\n" +
				"    decode(sign(tphis.current_order_no-tphis.task_order_no),-1,'',\n" +
				"    decode(tpfix.FILED,'"+fixedList.get(0)+"',tphis.USERFULLNAME,'"+fixedList.get(1)+"',tphis.text_,'"+fixedList.get(2)+"',to_char(tphis.endtime,'yyyy-mm-dd hh24:mi:ss')))  va, \n" +
				"    tphis.taskname || tpfix.FILED tasknewname\n" +
				"      from (\n" +
				"   \n" +
				"   \n" +
				"    select * from (\n" +
				"   select tp.*,row_number() over (partition by tp.processinstanceid,tp.taskDefinitionId order by tp.startTime desc) nums from (  select * from \n" +
				"  tta_proposal_status_process_his_v tpsh\n" +
				"  left join tta_proposal_status_process_text_v tpst \n" +
				"  on tpsh.processInstanceId = tpst.proc_inst_id_ \n" +
				"  and tpsh.taskId = tpst.task_id_ where  tpsh.processDefinitionKey = 'TTA_ELEC_CONTRACT.-999') tp ) tptps where tptps.nums = 1 ) tphis\n" +
				"  \n" +
				"  left join tta_proposal_status_fix_filed_v tpfix  on 1=1\n" +
				"  --end 内1\n" +
				"  ) ttaPro pivot (max(va) for tasknewname in (\n" +
				filedString +
				"  ))) ttaProProcess on abl.proc_inst_id  =  ttaProProcess.PROC_INST_ID_\n" +
				" where \n" +
				" nvl(ts.is_submit_contract,'N') = 'Y'" ;
	}

	public static String getExclusiveSchedule(String filedString){
		return "select * from (select \n" +
				"       tepv.*,\n" +
				"       ttaproprocess.*\n" +
				" from (select * from tta_sole_agrt_report_v tsar) tepv\n" +
				" left join act_bpm_list abl\n" +
				"    on tepv.sole_agrt_h_id = abl.business_key\n" +
				"   and abl.proc_def_key in('TTA_SOLE_NON_STANDAR_HEADER.-999','TTA_SOLE_STANDAR_HEADER.-999')\n" +
				" left join\n" +
				" (select *\n" +
				"    from (\n" +
				"          --start 内1\n" +
				"          select tphis.proc_inst_id_,\n" +
				"                 decode(sign(to_number(nvl(tphis.current_order_no,'0')) - to_number(nvl(tphis.task_order_no,'0')) ),\n" +
				"                         -1,\n" +
				"                         '',\n" +
				"                         decode(tpfix.filed,\n" +
				"                                'A',\n" +
				"                                tphis.userfullname,\n" +
				"                                'B',\n" +
				"                                tphis.text_,\n" +
				"                                'C',\n" +
				"                                to_char(tphis.endtime, 'yyyy-mm-dd hh24:mi:ss'))) va,\n" +
				"                  tphis.taskname || tpfix.filed tasknewname\n" +
				"            from (\n" +
				"                   \n" +
				"                   select *\n" +
				"                     from (select tp.*,\n" +
				"                                   row_number() over(partition by tp.processinstanceid, tp.taskdefinitionid order by tp.starttime desc) nums\n" +
				"                              from (\n" +
				"                                  select *\n" +
				"                                      from (\n" +
				"                                      select\n" +
				"                                         blvPro.lookup_type,\n" +
				"                                         task.id_ AS taskId,\n" +
				"                                         task.name_ AS taskName,\n" +
				"                                         task.parent_task_id_ AS parentTaskId,\n" +
				"                                         task.description_ AS description,\n" +
				"                                         task.task_def_key_ AS taskDefinitionId,\n" +
				"                                         task.proc_def_id_ AS processDefinitionId,\n" +
				"                                         task.proc_inst_id_ AS processInstanceId,\n" +
				"                                         task.owner_ AS owner,\n" +
				"                                         task.assignee_ AS assignee,\n" +
				"                                         task.start_time_ AS startTime,\n" +
				"                                         task.claim_time_ AS claimTime,\n" +
				"                                         task.end_time_ AS endTime,\n" +
				"                                         'RESOLVED' AS status,\n" +
				"                                         usr1.user_id AS userId,\n" +
				"                                         usr1.user_name AS userName,\n" +
				"                                         usr1.user_full_name AS userFullName,\n" +
				"                                         bpm.status AS bpm_status,\n" +
				"                                         bpm.result AS bpm_result,\n" +
				"                                         bpm.proc_def_key AS processDefinitionKey,\n" +
				"                                         blvPro.Lookup task_order_no,\n" +
				"                                         last_value(blvPro.lookup) over(partition by task.proc_inst_id_ order by task.start_time_ asc \n" +
				"                                         rows between unbounded preceding and unbounded following) current_order_no\n" +
				"                                         from\n" +
				"                                         act_hi_taskinst task\n" +
				"                                         inner join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
				"                                         left join act_bpm_list bpm on bpm.proc_inst_id = leavel.top_proc_inst_id\n" +
				"                                         left join base_users usr on bpm.created_by = usr.user_id\n" +
				"                                         left join base_users usr1 on task.assignee_ = usr1.user_name\n" +
				"                                         left join act_bpm_category cat on bpm.category_id = cat.category_id\n" +
				"                                         left join\n" +
				"                                         (select '00' lookup, '发起人' meaning, '-1' as lookup_type  from dual\n" +
				"                                          union all\n" +
				"                                          select blv.lookup_code lookup,blv.meaning, blv.lookup_type\n" +
				"                                          from\n" +
				"                                          base_lookup_values blv where blv.lookup_type = 'TTA_EXCLUSIVE_PROCESS_NODE'\n" +
				"                                          )  blvPro on task.name_ = blvPro.Meaning\n" +
				"                                          where bpm.proc_def_key in('TTA_SOLE_STANDAR_HEADER.-999','TTA_SOLE_NON_STANDAR_HEADER.-999')\n" +
				"                                          and blvPro.lookup_type in ('TTA_EXCLUSIVE_PROCESS_NODE','-1')\n" +
				"                                      ) tpsh\n" +
				"                                      left join tta_proposal_status_process_text_v tpst\n" +
				"                                        on tpsh.processinstanceid =\n" +
				"                                           tpst.proc_inst_id_\n" +
				"                                       and tpsh.taskid = tpst.task_id_\n" +
				"                                     where tpsh.processdefinitionkey in('TTA_SOLE_NON_STANDAR_HEADER.-999','TTA_SOLE_STANDAR_HEADER.-999')\n" +
				"                                           and tpsh.lookup_type in ('TTA_EXCLUSIVE_PROCESS_NODE','-1')\n" +
				"                                           ) tp) tptps\n" +
				"                    where tptps.nums = 1\n" +
				"                    ) tphis  \n" +
				"            left join tta_proposal_status_fix_filed_v tpfix --- A,B,C\n" +
				"              on 1 = 1\n" +
				"          --end 内1\n" +
				"          ) ttapro\n" +
				"  pivot(max(va)\n" +
				"     for tasknewname in(\n" +
				filedString +
				  "\n))\n" +
				") ttaproprocess\n" +
				"    on abl.proc_inst_id = ttaproprocess.proc_inst_id_\n" +
				"    order by tepv.sole_agrt_code) tab where  1=1 ";
	}

	public static String getFindExclusiveJoinElecList(String filedString, String exclusiveFiledString) {
		/*"                                  select *\n" +
				"                                      from tta_process_his_v tpsh\n" +
				"                                      left join tta_proposal_status_process_text_v tpst\n" +
				"                                        on tpsh.processinstanceid =\n" +
				"                                           tpst.proc_inst_id_\n" +
				"                                       and tpsh.taskid = tpst.task_id_\n" +
				"                                     where tpsh.processdefinitionkey in('TTA_SOLE_NON_STANDAR_HEADER.-999','TTA_SOLE_STANDAR_HEADER.-999')\n" +
				"                                           and tpsh.lookup_type in ('TTA_EXCLUSIVE_PROCESS_NODE','-1')\n" +;*/
		return "select \n" +
				"       tepv.*,\n" +
				"       solesgrtprocess.*,\n" +
				"       ttaproprocess.*\n" +
				" from (select tsar.*,tep.* \n" +
				"    from tta_sole_agrt_report_v tsar\n" +
				"       left join tta_elec_process_v tep\n" +
				"            on tsar.sole_agrt_code = tep.order_no\n" +
				"            and tsar.sole_agrt_version = tep.order_version\n" +
				"            and tep.file_type in(2,3)) tepv\n" +
				" left join act_bpm_list eabl\n" +
				"    on tepv.sole_agrt_h_id = eabl.business_key\n" +
				"    and eabl.proc_def_key in('TTA_SOLE_NON_STANDAR_HEADER.-999','TTA_SOLE_STANDAR_HEADER.-999')         \n" +
				" left join act_bpm_list abl\n" +
				"    on tepv.elec_con_header_id = abl.business_key\n" +
				"   and abl.proc_def_key = 'TTA_ELEC_CONTRACT.-999'\n" +
				" left join\n" +
				" (\n" +
				" select *\n" +
				"    from (\n" +
				"          --start 内1\n" +
				"          select tphis.proc_inst_id_ e_proc_inst_id_,\n" +
				"                  decode(sign(tphis.current_order_no - tphis.task_order_no),\n" +
				"                         -1,\n" +
				"                         '',\n" +
				"                         decode(tpfix.filed,\n" +
				"                                'D',\n" +
				"                                tphis.userfullname,\n" +
				"                                'E',\n" +
				"                                tphis.text_,\n" +
				"                                'F',\n" +
				"                                to_char(tphis.endtime, 'yyyy-mm-dd hh24:mi:ss'))) va,\n" +
				"                  tphis.taskname || tpfix.filed tasknewname\n" +
				"            from (\n" +
				"                   \n" +
				"                   select *\n" +
				"                     from (select tp.*,\n" +
				"                                   row_number() over(partition by tp.processinstanceid, tp.taskdefinitionid order by tp.starttime desc) nums\n" +
				"                              from (\n" +
				" 									select *\n" +
				"                                      from (\n" +
				"                                      select\n" +
				"                                         blvPro.lookup_type,\n" +
				"                                         task.id_ AS taskId,\n" +
				"                                         task.name_ AS taskName,\n" +
				"                                         task.parent_task_id_ AS parentTaskId,\n" +
				"                                         task.description_ AS description,\n" +
				"                                         task.task_def_key_ AS taskDefinitionId,\n" +
				"                                         task.proc_def_id_ AS processDefinitionId,\n" +
				"                                         task.proc_inst_id_ AS processInstanceId,\n" +
				"                                         task.owner_ AS owner,\n" +
				"                                         task.assignee_ AS assignee,\n" +
				"                                         task.start_time_ AS startTime,\n" +
				"                                         task.claim_time_ AS claimTime,\n" +
				"                                         task.end_time_ AS endTime,\n" +
				"                                         'RESOLVED' AS status,\n" +
				"                                         usr1.user_id AS userId,\n" +
				"                                         usr1.user_name AS userName,\n" +
				"                                         usr1.user_full_name AS userFullName,\n" +
				"                                         bpm.status AS bpm_status,\n" +
				"                                         bpm.result AS bpm_result,\n" +
				"                                         bpm.proc_def_key AS processDefinitionKey,\n" +
				"                                         blvPro.Lookup task_order_no,\n" +
				"                                         last_value(blvPro.lookup) over(partition by task.proc_inst_id_ order by task.start_time_ asc \n" +
				"                                         rows between unbounded preceding and unbounded following) current_order_no\n" +
				"                                         from\n" +
				"                                         act_hi_taskinst task\n" +
				"                                         inner join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
				"                                         left join act_bpm_list bpm on bpm.proc_inst_id = leavel.top_proc_inst_id\n" +
				"                                         left join base_users usr on bpm.created_by = usr.user_id\n" +
				"                                         left join base_users usr1 on task.assignee_ = usr1.user_name\n" +
				"                                         left join act_bpm_category cat on bpm.category_id = cat.category_id\n" +
				"                                         left join\n" +
				"                                         (select '00' lookup, '发起人' meaning, '-1' as lookup_type  from dual\n" +
				"                                          union all\n" +
				"                                          select blv.lookup_code lookup,blv.meaning, blv.lookup_type\n" +
				"                                          from\n" +
				"                                          base_lookup_values blv where blv.lookup_type = 'TTA_EXCLUSIVE_PROCESS_NODE'\n" +
				"                                          )  blvPro on task.name_ = blvPro.Meaning\n" +
				"                                          where bpm.proc_def_key in('TTA_SOLE_STANDAR_HEADER.-999','TTA_SOLE_NON_STANDAR_HEADER.-999')\n" +
				"                                          and blvPro.lookup_type in ('TTA_EXCLUSIVE_PROCESS_NODE','-1')\n" +
				"                                      ) tpsh\n" +
				"                                      left join tta_proposal_status_process_text_v tpst\n" +
				"                                        on tpsh.processinstanceid =\n" +
				"                                           tpst.proc_inst_id_\n" +
				"                                       and tpsh.taskid = tpst.task_id_\n" +
				"                                     where tpsh.processdefinitionkey in('TTA_SOLE_NON_STANDAR_HEADER.-999','TTA_SOLE_STANDAR_HEADER.-999')\n" +
				"                                           and tpsh.lookup_type in ('TTA_EXCLUSIVE_PROCESS_NODE','-1')\n" +
				"                                           ) tp) tptps\n" +
				"                    where tptps.nums = 1) tphis\n" +
				"            left join tta_exclusive_status_fix_filed_v tpfix\n" +
				"              on 1 = 1\n" +
				"          --end 内1\n" +
				"          ) ttapro\n" +
				"  pivot(max(va)\n" +
				"     for tasknewname in(\n" +
				exclusiveFiledString +
				"  \n))\n" +
				") solesgrtprocess -- 独家协议审批流程节点\n" +
				"    on eabl.proc_inst_id = solesgrtprocess.e_proc_inst_id_  \n" +
				" left join\n" +
				" (\n" +
				" select *\n" +
				"    from (\n" +
				"          --start 内1\n" +
				"          select tphis.proc_inst_id_,\n" +
				"                  decode(sign(tphis.current_order_no - tphis.task_order_no),\n" +
				"                         -1,\n" +
				"                         '',\n" +
				"                         decode(tpfix.filed,\n" +
				"                                'A',\n" +
				"                                tphis.userfullname,\n" +
				"                                'B',\n" +
				"                                tphis.text_,\n" +
				"                                'C',\n" +
				"                                to_char(tphis.endtime, 'yyyy-mm-dd hh24:mi:ss'))) va,\n" +
				"                  tphis.taskname || tpfix.filed tasknewname\n" +
				"            from (\n" +
				"                   \n" +
				"                   select *\n" +
				"                     from (select tp.*,\n" +
				"                                   row_number() over(partition by tp.processinstanceid, tp.taskdefinitionid order by tp.starttime desc) nums\n" +
				"                              from (\n" +
				"                                  select *\n" +
				"                                      from tta_process_his_v tpsh\n" +
				"                                      left join tta_proposal_status_process_text_v tpst\n" +
				"                                        on tpsh.processinstanceid =\n" +
				"                                           tpst.proc_inst_id_\n" +
				"                                       and tpsh.taskid = tpst.task_id_\n" +
				"                                     where tpsh.processdefinitionkey ='TTA_ELEC_CONTRACT.-999'\n" +
				"                                           and tpsh.lookup_type in ('TTA_ELEC_PROCESS_NODE','-1')\n" +
				"                                           ) tp) tptps\n" +
				"                    where tptps.nums = 1) tphis\n" +
				"            left join tta_proposal_status_fix_filed_v tpfix\n" +
				"              on 1 = 1\n" +
				"          --end 内1\n" +
				"          ) ttapro\n" +
				"  pivot(max(va)\n" +
				"     for tasknewname in(\n" +
				filedString +
				" \n))\n" +
				") ttaproprocess -- 电子签的审批流程节点\n" +
				"    on abl.proc_inst_id = ttaproprocess.proc_inst_id_\n" +
				"    order by tepv.elec_order_no,tepv.order_no ";
	}
}
