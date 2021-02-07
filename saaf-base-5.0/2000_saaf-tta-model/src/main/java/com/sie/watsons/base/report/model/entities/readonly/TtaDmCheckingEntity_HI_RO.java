package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.util.SaafDateUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;

/**
 * TtaDmCheckingEntity_HI_RO Entity Object
 * Mon Nov 18 20:28:25 CST 2019  Auto Generate
 */

public class TtaDmCheckingEntity_HI_RO {
    private Integer dmId;
    private String durationPeriod;
   /* @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;*/
    private String dmPage;
    private String locationCode;
    private String effectiveArea;
    private Integer effectiveAreaCnt;
    //private String mapPosition;
    private String acceptUnit;
    private String itemNbr;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer yearMonth;
    private String content;
    private String groupCode;
    private String deptDesc;
    private String itemCn;
    private String brandCn;
    private String priorVendorCode;
    private String priorVendorName;
    private String contractYear;
    private String contractStatus;
    private String chargeStandards;
    private BigDecimal chargeMoney;
    private BigDecimal receiveAmount;
    private BigDecimal unconfirmAmount;
    private BigDecimal advanceAmount;
    private String advanceCode;
    private BigDecimal difference;
	private BigDecimal noDifference;
    private String collect;
    private String purchase;
    private String exemptionReason;
    private String exemptionReason2;
    private String debitOrderCode;
    private BigDecimal receipts;
    private Integer status;
    private Integer parentId;
    private String parentVendorCode;
    private String parentVendorName;
    private String batchCode;
    private String contractTerms;
    private Integer transferOutPerson;
    private Integer transferLastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    private Integer transferInPerson;
    private Integer operatorUserId;
    private String  groupDesc;
	private String 	transferInPersonName;
	private String 	transferOutPersonName;
	private String  transferLastPersonName;
	private String	reatedByName;
	private String  lastUpdatedByName;
	private String  headerStatus;
	private Integer fileId;
	private String  sourceFileName;
	private BigDecimal additionRate;
	private Integer processReId;
	private String processCode;
	private String processStatusName;
	private String taskIds;
	private int groupDmCnt;//当前按照部门提交DM数量
	private BigDecimal differenceAmt; //差异金额
	private BigDecimal reachRate; //达成率

	private Integer adjustBy;
	private BigDecimal adjustAmount;
	private BigDecimal adjustReceiveAmount;
	private String adjustByName;


	private String purchaseName; //采购行动
	private String exemptionReasonName;//原因1
	private String exemptionReason2Name;//原因2

	private String remark;// 备注
	private BigDecimal noReceiveAmount; //应收金额（不含加成）
	private BigDecimal noUnconfirmAmount; //需采购确认收取金额（不含加成）
	private String type;//数据来源1 调整应收，2.将收取来源

    private String startUserName;//流程发起人
    private String currentApprovalUser;//当前审批人

	private BigDecimal noAdjustAmount	;// 调整实收金额（不含加成手动录入）
	private BigDecimal noAdjustReceiveAmount; //调整应收金额（不含加成）
	private String valueAll;
	private String deptCode;
    private String processStatus;//单行流程状态



	public static final String  QUERY_EXISTS = "SELECT " +
			"tom.group_code," +
			"tom.group_desc," +
			"tom.dept_code," +
			"tom.dept_desc," +
			"tom.brand_cn, " +
			"tom.group_code||'-'||tom.dept_code||'-'||tom.brand_cn  value_all "  +
			"FROM tta_dm_checking tom\n" +
			"\n" +
			"where tom.batch_code = :batchCode\n" +
			"and  \n" +
			"not exists (SELECT 1 FROM tta_user_group_dept_brand tug where (tug.data_type = '1' and tug.\"GROUP\" = tom.group_code)\n" +
			"                                                              or (tug.data_type = '2' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code)\n" +
			"                                                              or (tug.data_type = '3' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code and tug.brand_cn = tom.brand_cn) )\n";
	public static final String getCheckGroupDm() {
		String sql = "select " +
				"	 tdc.group_code as \"groupCode\"," +
				"	 count(tdc.dm_id) as \"cnt\"\n" +
				"  from tta_report_header trh\n" +
				" inner join tta_dm_checking tdc\n" +
				"    on trh.batch_id = tdc.batch_code\n" +
				" where trh.batch_id =:batchCode \n" +
				" and nvl(tdc.status,1) = 1 \n" +
				" group by tdc.group_code";
		return sql;
	}

	public static final String  QUERY ="select \n" +
			"       tdc.*,\n" +
            "       process.current_approval_user,\n" +
            "       process.start_user_name,\n" +
			"       action.purchase_name,\n" +
			"       reason1.exemption_reason_name,\n" +
			"       reason2.exemption_reason2_name,\n" +
			"       bu_ad.user_full_name adjustByName,\n" +
			"       tdc.process_id  process_re_id,\n" +
			"		trph.batch_code process_code,\n" +
            "		trph.status as process_status,\n" +
			"		lookup2.meaning processStatusName,\n" +
			"       bu_in.user_full_name     transfer_in_person_name,\n" +
			"       bu_out.user_full_name    transfer_out_person_name,\n" +
			"       bu_t_last.user_full_name transfer_last_person_name,\n" +
			"       bu.user_full_name        created_by_name,\n" +
			"       bu_last.user_full_name   last_updated_by_name,\n" +
			"       trh.id,\n" +
			"       trh.status  header_status,\n" +
			"       ba.file_id,\n" +
			"       ba.source_file_name\n" +
			"  from tta_dm_checking tdc\n" +
			" left join tta_report_process_header trph \n" +
			" on tdc.process_id = trph.report_process_header_id \n" +
			"  left join tta_report_header trh\n" +
			"    on tdc.batch_code = trh.batch_id\n" +
			" left join (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"  ) lookup2  ON trph.status = lookup2.lookup_code\n" +
			"  left join base_attachment ba\n" +
			"    on ba.function_id = 'tta_dm_checking_report'\n" +
			"   and ba.business_id = tdc.dm_id\n" +
			"   and nvl(ba.delete_flag, 0) = 0\n" +
			"  left join base_users bu\n" +
			"    on tdc.created_by = bu.user_id\n" +
			"  left join base_users bu_last\n" +
			"    on tdc.last_updated_by = bu_last.user_id\n" +
			"  left join base_users bu_in\n" +
			"    on tdc.transfer_in_person = bu_in.user_id\n" +
			"  left join base_users bu_out\n" +
			"    on tdc.transfer_out_person = bu_out.user_id\n" +
			"  left join base_users bu_t_last\n" +
			"    on tdc.transfer_last_person = bu_t_last.user_id\n" +
			"  left join base_users bu_ad\n" +
			"    on tdc.adjust_by = bu_ad.user_id\n" +
			" left join \n" +
			"     (select lookup_code, meaning as purchase_name\n" +
			"   from base_lookup_values\n" +
			"  where lookup_type in ('TTA_DM_ACTION','TTA_DM_ADJ_ACTION')\n" +
			"    and enabled_flag = 'Y'\n" +
			"    and delete_flag = 0\n" +
			"    and start_date_active < sysdate\n" +
			"    and (end_date_active >= sysdate or end_date_active is null)\n" +
			"    and system_code = 'BASE') action \n" +
			"    on action.lookup_code = tdc.purchase\n" +
			"   left join \n" +
			"     (select lookup_code, meaning as exemption_reason_name\n" +
			"     from base_lookup_values\n" +
			"    where lookup_type = 'TTA_DM_REASON'\n" +
			"      and enabled_flag = 'Y'\n" +
			"      and delete_flag = 0\n" +
			"      and start_date_active < sysdate\n" +
			"      and (end_date_active >= sysdate or end_date_active is null)\n" +
			"      and system_code = 'BASE') reason1\n" +
			"      on reason1.lookup_code = tdc.exemption_reason\n" +
			"   left join \n" +
			"    (select lookup_code, meaning as exemption_reason2_name\n" +
			"   from base_lookup_values\n" +
			"  where lookup_type = 'TTA_DM_REASON1'\n" +
			"    and enabled_flag = 'Y'\n" +
			"    and delete_flag = 0\n" +
			"    and start_date_active < sysdate\n" +
			"    and (end_date_active >= sysdate or end_date_active is null)\n" +
			"    and system_code = 'BASE') reason2\n" +
			"    on reason2.lookup_code = tdc.exemption_reason2 \n" +
            " left join ( select a.business_key,\n" +
            "       nvl(bp.person_name_en,bp.person_name_cn) as current_approval_user,\n" +
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
			" where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process \n" +
            "  on process.business_key = tdc.process_id\n" +
			" where 1 = 1\n" +
			"	and tdc.status = 1 \n";

	public static final String getDmProcessSummary(String batchId, String userCode, Integer operatorUserId) {
		String sql = "select  '" + batchId+ "' as batch_code,\n" +
				"       tdc.group_code,\n" +
				"       max(tdc.group_desc) as group_desc,\n" +
				" 		sum(nvl(receive_amount,0)) as receive_amount, -- 应收金额(含加成)\n" +
				" 		sum(nvl(unconfirm_amount,0)) as unconfirm_amount, -- 需采购确认收取金额(含加成)\n" +
				" 		sum(nvl(unconfirm_amount,0))-sum(nvl(receive_amount,0)) as difference_amt,  -- 差异金额\n" +
				"		ROUND(decode(sum(nvl(receive_amount,0)),0,0,sum(nvl(unconfirm_amount,0))/sum(nvl(receive_amount,0))) * 100,2) as reach_rate,  -- 达成率=需采购确认收取金额(含加成)/应收金额(含加成) 【百分比】\n" +
				"  XMLAGG(XMLPARSE(CONTENT task.id_ || ',' WELLFORMED) ORDER BY task.id_).GETCLOBVAL() as task_ids,\n" +
				"  count(tdc.dm_id) as  group_dm_cnt\n" +
				"  from tta_report_header trh\n" +
				" inner join tta_dm_checking tdc\n" +
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


	public static final String getQueryProcess(String userCode, Integer operatorUserId) {
		   String  query ="select \n" +
				"       tdc.*,\n" +
				"       tdc.process_id  process_re_id,\n" +
				"		trph.batch_code process_code,\n" +
				"		lookup2.meaning processStatusName,\n" +
				"       bu_in.user_full_name     transfer_in_person_name,\n" +
				"       bu_out.user_full_name    transfer_out_person_name,\n" +
				"       bu_t_last.user_full_name transfer_last_person_name,\n" +
				"       bu.user_full_name        created_by_name,\n" +
				"       bu_last.user_full_name   last_updated_by_name,\n" +
				"       trh.id,\n" +
				"       trh.status  header_status,\n" +
				"       ba.file_id,\n" +
				"       ba.source_file_name\n" +
				"  from tta_dm_checking tdc\n" +
				" left join tta_report_process_header trph \n" +
				" on tdc.process_id = trph.report_process_header_id \n" +
			    " inner join act_bpm_list bpm " +
			    " on bpm.business_key = trph.report_process_header_id" +
			    " inner join act_ru_task task " +
			    " on bpm.proc_inst_id = task.proc_inst_id_" +
				"  left join tta_report_header trh\n" +
				"    on tdc.batch_code = trh.batch_id\n" +
				" left join (select lookup_type,lookup_code,meaning\n" +
				"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
				"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				"  ) lookup2  ON trph.status = lookup2.lookup_code\n" +
				"  left join base_attachment ba\n" +
				"    on ba.function_id = 'tta_dm_checking_report'\n" +
				"   and ba.business_id = tdc.dm_id\n" +
				"   and nvl(ba.delete_flag, 0) = 0\n" +
				"  left join base_users bu\n" +
				"    on tdc.created_by = bu.user_id\n" +
				"  left join base_users bu_last\n" +
				"    on tdc.last_updated_by = bu_last.user_id\n" +
				"  left join base_users bu_in\n" +
				"    on tdc.transfer_in_person = bu_in.user_id\n" +
				"  left join base_users bu_out\n" +
				"    on tdc.transfer_out_person = bu_out.user_id\n" +
				"  left join base_users bu_t_last\n" +
				"    on tdc.transfer_last_person = bu_t_last.user_id\n" +
				" where 1 = 1 " +
				"   and bpm.proc_def_key = 'TTA_ACTIVITY.-999' \n" +
				"	and tdc.status = 1 and tdc.process_id is not null \n" +
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
						"             and delegate.task_id = task.id_))\n";
		return query;
	}

   public static final String getNoBicSql(String batchCodes){
			 return "select \n" +
            "       tdc.*,\n" +
            "       process.current_approval_user,\n" +
            "       process.start_user_name,\n" +
			"    	action.purchase_name,\n" +
			"       reason1.exemption_reason_name,\n" +
			"       reason2.exemption_reason2_name,\n" +
			"       bu_ad.user_full_name adjustByName,\n" +
			"       tdc.process_id  process_re_id,\n" +
			"		trph.status process_status,\n" +
			"		trph.batch_code process_code,\n" +
			"		lookup2.meaning processStatusName,\n" +
			"       bu.user_full_name      created_by_name,\n" +
			"       bu_last.user_full_name last_updated_by_name,\n" +
			"       bu_in.user_full_name   transfer_in_person_name,\n" +
			"       bu_out.user_full_name  transfer_out_person_name,\n" +
			"       ba.file_id,\n" +
			"       ba.source_file_name,\n" +
			"       bu_la.user_full_name   transfer_last_person_name\n" +
			"  from (select t1.*\n" +
			"          from tta_dm_checking t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where exists (select group_desc\n" +
			"                  from (select nvl(dp.group_desc, '-1') as group_desc\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id =:userId\n" +
			"                           and bu.data_type = 1\n" +
			"                         group by nvl(dp.group_desc, '-1')) t2\n" +
			"                 where t2.group_desc = t1.group_desc)\n"
					 +		 (StringUtils.isNotBlank(batchCodes) ? "  and t1.batch_code in (" + batchCodes +  ")\n" : "") +
			"           and nvl(t1.status, 1) = 1\n" +
			"           and nvl(th.is_publish, 'N') = 'Y'\n" +
			"			and nvl(t1.transfer_in_person,'-1') = '-1'\n" + //转办人为空
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_dm_checking t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where exists (select group_desc, dept_desc\n" +
			"                  from (select nvl(dp.group_desc, '-1') as group_desc,\n" +
			"                               nvl(dp.dept_desc, '-1') as dept_desc\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id = :userId\n" +
			"                           and bu.data_type = 2\n" +
			"                         group by nvl(dp.group_desc, '-1'),\n" +
			"                                  nvl(dp.dept_desc, '-1')) t2\n" +
			"                 where t2.group_desc = t1.group_desc\n" +
			"                   and t2.dept_desc = t1.dept_desc)\n" +
			//"           and nvl(t1.batch_code, '-1') = :batchCode\n" +
						 (StringUtils.isNotBlank(batchCodes) ? "  and t1.batch_code in (" + batchCodes +  ")\n" : "") +
			"           and nvl(t1.status, 1) = 1\n" +
			"           and nvl(th.is_publish, 'N') = 'Y'\n" +
			"			and nvl(t1.transfer_in_person,'-1') = '-1'\n" + //转办人为空
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_dm_checking t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where exists (select group_desc, dept_desc\n" +
			"                  from (select nvl(dp.group_desc, '-1') as group_desc,\n" +
			"                               nvl(dp.dept_desc, '-1') as dept_desc,\n" +
			"                               nvl(dp.brand_cn, '-1') as brand_cn\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id = :userId\n" +
			"                           and bu.data_type = 3\n" +
			"                         group by nvl(dp.group_desc, '-1'),\n" +
			"                                  nvl(dp.dept_desc, '-1'),\n" +
			"                                  nvl(dp.brand_cn, '-1')) t2\n" +
			"                 where t2.group_desc = t1.group_desc\n" +
			"                   and t2.dept_desc = t1.dept_desc\n" +
			"                   and t2.brand_cn = t1.brand_cn)\n" +
			//"           and nvl(t1.batch_code, '-1') = :batchCode\n" +
					 (StringUtils.isNotBlank(batchCodes) ? "  and t1.batch_code in (" + batchCodes +  ")\n" : "") +
			"           and nvl(t1.status, 1) = 1\n" +
			"           and nvl(th.is_publish, 'N') = 'Y'\n" +
			"			and nvl(t1.transfer_in_person,'-1') = '-1'\n" + //转办人为空
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_dm_checking t1\n" +
			"         inner join tta_report_header th\n" +
			"            on t1.batch_code = th.batch_id\n" +
			"         where t1.transfer_in_person =:userId\n" +
			"           and nvl(th.is_publish, 'N') = 'Y'" +
			//"		    and nvl(t1.batch_code, '-1') = :batchCode" +
				   (StringUtils.isNotBlank(batchCodes) ? "  and t1.batch_code in (" + batchCodes +  ")\n" : "") +
					 ") tdc\n" +
			" left join tta_report_process_header trph \n" +
			" on tdc.process_id = trph.report_process_header_id \n" +
			"left join  (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2  ON TRPH.STATUS = lookup2.lookup_code\n" +
			"  left join base_users bu\n" +
			"    on tdc.created_by = bu.user_id\n" +
			"  left join base_attachment ba\n" +
			"    on ba.function_id = 'tta_dm_checking_report'\n" +
			"   and ba.business_id = tdc.dm_id\n" +
			"   and nvl(ba.delete_flag, 0) = 0\n" +
			"  left join base_users bu_last\n" +
			"    on tdc.last_updated_by = bu_last.user_id\n" +
			"  left join base_users bu_in\n" +
			"    on tdc.transfer_in_person = bu_in.user_id\n" +
			"  left join base_users bu_out\n" +
			"    on tdc.transfer_out_person = bu_out.user_id\n" +
			"  left join base_users bu_la\n" +
			"    on tdc.transfer_last_person = bu_la.user_id\n" +
			"  left join base_users bu_ad\n" +
			"    on tdc.adjust_By = bu_ad.user_id\n" +
			" left join \n" +
			"     ( -- 采购行动 \n" +
			"     select lookup_code, meaning as purchase_name\n" +
			"   from base_lookup_values\n" +
			"  where lookup_type in ('TTA_DM_ACTION','TTA_DM_ADJ_ACTION') \n" +
			"    and enabled_flag = 'Y'\n" +
			"    and delete_flag = 0\n" +
			"    and start_date_active < sysdate\n" +
			"    and (end_date_active >= sysdate or end_date_active is null)\n" +
			"    and system_code = 'BASE') action \n" +
			"    on action.lookup_code = tdc.purchase\n" +
			"   left join \n" +
			"     ( --  豁免原因1 \n" +
			"  		select lookup_code, meaning as exemption_reason_name\n" +
			"     from base_lookup_values\n" +
			"    where lookup_type = 'TTA_DM_REASON'\n" +
			"      and enabled_flag = 'Y'\n" +
			"      and delete_flag = 0\n" +
			"      and start_date_active < sysdate\n" +
			"      and (end_date_active >= sysdate or end_date_active is null)\n" +
			"      and system_code = 'BASE') reason1\n" +
			"      on reason1.lookup_code = tdc.exemption_reason\n" +
			"   left join \n" +
			"    (  --  豁免原因2 \n" +
			"	select lookup_code, meaning as exemption_reason2_name\n" +
			"   from base_lookup_values\n" +
			"  where lookup_type = 'TTA_DM_REASON1'\n" +
			"    and enabled_flag = 'Y'\n" +
			"    and delete_flag = 0\n" +
			"    and start_date_active < sysdate\n" +
			"    and (end_date_active >= sysdate or end_date_active is null)\n" +
			"    and system_code = 'BASE') reason2\n" +
			"    on reason2.lookup_code = tdc.exemption_reason2\n" +
            " left join ( select a.business_key,\n" +
            "       nvl(bp.person_name_en,bp.person_name_cn) as current_approval_user,\n" +
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
			" where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process \n" +
            "  on process.business_key = tdc.process_id\n" +
			"  where 1 = 1 \n";
	}

	/**
	 * 发布之前检查哪些不能匹配到活动id
	 */
	public static final String getDmPublishCheck(String batchCode) {
		String sql = "select\n" +
				"   XMLAGG(XMLPARSE(CONTENT  tdc.content|| ',' WELLFORMED) ORDER BY tdc.content).GETCLOBVAL() as CONTENT_IDS\n" +
				"  from tta_dm_checking tdc \n" +
				" where not exists\n" +
				" ( \n" +
				"       select 1\n" +
				"            from (select t1.dm_id\n" +
				"                     from tta_dm_checking t1\n" +
				"                    inner join tta_report_header th\n" +
				"                       on t1.batch_code = th.batch_id\n" +
				"                    where exists (select group_desc\n" +
				"                             from (select nvl(dp.group_desc, '-1') as group_desc\n" +
				"                                     from base_users bu\n" +
				"                                    inner join tta_user_group_dept_brand_eft_v dp\n" +
				"                                       on bu.user_id = dp.user_id\n" +
				"                                      and bu.data_type = dp.data_type\n" +
				"                                    where bu.data_type = 1\n" +
				"                                    group by nvl(dp.group_desc, '-1')) t2\n" +
				"                            where t2.group_desc = t1.group_desc)\n" +
				"                      and nvl(t1.batch_code, '-1') = '" + batchCode + "'\n" +
				"                      and nvl(t1.status, 1) = 1\n" +
				"                   union\n" +
				"                   select t1.dm_id\n" +
				"                     from tta_dm_checking t1\n" +
				"                    inner join tta_report_header th\n" +
				"                       on t1.batch_code = th.batch_id\n" +
				"                    where exists (select group_desc, dept_desc\n" +
				"                             from (select nvl(dp.group_desc, '-1') as group_desc,\n" +
				"                                          nvl(dp.dept_desc, '-1') as dept_desc\n" +
				"                                     from base_users bu\n" +
				"                                    inner join tta_user_group_dept_brand_eft_v dp\n" +
				"                                       on bu.user_id = dp.user_id\n" +
				"                                      and bu.data_type = dp.data_type\n" +
				"                                    where bu.data_type = 2\n" +
				"                                    group by nvl(dp.group_desc, '-1'),\n" +
				"                                             nvl(dp.dept_desc, '-1')) t2\n" +
				"                            where t2.group_desc = t1.group_desc\n" +
				"                              and t2.dept_desc = t1.dept_desc)\n" +
				"                      and nvl(t1.batch_code, '-1') = '" + batchCode + "'\n" +
				"                      and nvl(t1.status, 1) = 1\n" +
				"                   union\n" +
				"                   select t1.dm_id\n" +
				"                     from tta_dm_checking t1\n" +
				"                    inner join tta_report_header th\n" +
				"                       on t1.batch_code = th.batch_id\n" +
				"                    where exists (select group_desc, dept_desc\n" +
				"                             from (select nvl(dp.group_desc, '-1') as group_desc,\n" +
				"                                          nvl(dp.dept_desc, '-1') as dept_desc,\n" +
				"                                          nvl(dp.brand_cn, '-1') as brand_cn\n" +
				"                                     from base_users bu\n" +
				"                                    inner join tta_user_group_dept_brand_eft_v dp\n" +
				"                                       on bu.user_id = dp.user_id\n" +
				"                                      and bu.data_type = dp.data_type\n" +
				"                                    where bu.data_type = 3\n" +
				"                                    group by nvl(dp.group_desc, '-1'),\n" +
				"                                             nvl(dp.dept_desc, '-1'),\n" +
				"                                             nvl(dp.brand_cn, '-1')) t2\n" +
				"                            where t2.group_desc = t1.group_desc\n" +
				"                              and t2.dept_desc = t1.dept_desc\n" +
				"                              and t2.brand_cn = t1.brand_cn)\n" +
				"                      and nvl(t1.batch_code, '-1') = '"+ batchCode + "'\n" +
				"                      and nvl(t1.status, 1) = 1\n" +
				"                    ) tab where tab.dm_id = tdc.dm_id\n" +
				"         ) \n" +
				"        and nvl(tdc.batch_code, '-1') = '"+ batchCode + "'\n" +
				"        and tdc.transfer_in_person is not null \n";
		return sql;
	}





	public static String getInsertReportBase(String batchCode,Integer userId, String durationPeriod, Integer year, String dateString){
		String sql = "insert into tta_dm_checking\n" +
				"  (dm_id,\n" +
				"   duration_period,\n" +
				"   dm_page,\n" +
				"   location_code,\n" +
				"   effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   accept_unit,\n" +
				"   item_nbr,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   creation_date,\n" +
				"   last_update_login,\n" +
				"   version_num,\n" +
				"   year_month,\n" +
				"   content,\n" +
				"   group_code,\n" +
				"   group_desc,\n" +
				"   dept_desc,\n" +
				"	dept_code,\n" +
				"   item_cn,\n" +
				"   brand_cn,\n" +
				"   prior_vendor_code,\n" +
				"   prior_vendor_name,\n" +
				"   contract_year,\n" +
				"   contract_status,\n" +
				"   charge_standards,\n" +
				"   charge_money,\n" +
				"   receive_amount,\n" +
				"   no_receive_amount,\n" +
				"   unconfirm_amount, -- 需采购确认收取金额(含加成)\n" +
				"   no_unconfirm_amount,-- 需采购确认收取金额(不含加成)\n" +
				"   advance_amount,\n" +
				"   advance_code,\n" +
				"   difference,\n" +
				"   no_difference,\n" +
				"   collect,\n" +
				"   purchase,\n" +
				"   exemption_reason,\n" +
				"   exemption_reason2,\n" +
				"   debit_order_code,\n" +
				"   receipts,\n" +
				"   status,\n" +
				"   parent_id,\n" +
				"   parent_vendor_code,\n" +
				"   parent_vendor_name,\n" +
				"   batch_code,\n" +
				"	contract_terms,\n" +
				"   addition_rate " +
				")\n" +
				" select \n" +
				"	seq_tta_dm_checking.nextval as dm_id,\n" +
				"   duration_period,\n" +
				"   dm_page,\n" +
				"   location_code,\n" +
				"   effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   accept_unit,\n" +
				"   item_nbr,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   creation_date,\n" +
				"   last_update_login,\n" +
				"   version_num,\n" +
				"   year_month,\n" +
				"   content,\n" +
				"   group_code,\n" +
				"   group_desc,\n" +
				"   dept_desc,\n" +
				"	dept_code,\n" +
				"   item_cn,\n" +
				"   brand_cn,\n" +
				"   prior_vendor_code,\n" +
				"   prior_vendor_name,\n" +
				"   substr(charge_standards_money,6,4) as contract_year,\n" + //年份
				"   contract_status,\n" +
				"	substr(charge_standards_money,0,4) as charge_standards,--收费标准\n" +
				"	substr(charge_standards_money,11) as charge_money, --计费金额\n" +
				"   substr(charge_standards_money,11)/4 * effective_area_cnt * (1 + nvl(addition_rate, 0))  as  receive_amount,\n" +  //则应收金额（含加成） =”计费金额“/4*“生效区域数”
				"	substr(charge_standards_money,11)/4 * effective_area_cnt  as  no_receive_amount,\n" + //则应收金额(不含加成) =”计费金额“/4*“生效区域数”
				"   substr(charge_standards_money,11)/4 * effective_area_cnt * (1 + nvl(addition_rate, 0)) as unconfirm_amount, -- 需采购确认收取金额(含加成)\n" +
				"   substr(charge_standards_money,11)/4 * effective_area_cnt as no_unconfirm_amount,-- 需采购确认收取金额(不含加成)\n" +
				"   advance_amount,\n" +
				"   advance_code,\n" +
				"   difference,\n" +
				"   no_difference,\n" +
				"   collect,\n" +
				"   purchase,\n" +
				"   exemption_reason,\n" +
				"   exemption_reason2,\n" +
				"   debit_order_code,\n" +
				"   receipts,\n" +
				"   status,\n" +
				"   parent_id,\n" +
				"   parent_vendor_code,\n" +
				"   parent_vendor_name,\n" +
				"   batch_code,\n" +
				"	contract_terms,\n" +
				"   addition_rate from " +
				"\n(\n" +
				"select \n" +
				"       dfl.duration_period as duration_period,\n" +
				"       dfl.dm_page as dm_page,\n" +
				"       dfl.location_code as location_code,\n" +
				"       dfl.effective_area as effective_area,\n" +
				"       dfl.effective_area_cnt as effective_area_cnt,\n" +
				"       dfl.accept_unit as accept_unit,\n" +
				"       dfl.item_nbr as item_nbr,\n" +
						userId + " as created_by,\n" +
						userId + " as last_updated_by,\n" +
						"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as last_update_date,\n" +
						"to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as creation_date,\n" +
						userId + " as last_update_login,\n" +
				"       0 as version_num,\n" +
				"       dfl.year_month as year_month,\n" +
				"       ('DM' || '-' || dfl.duration_period || '-' || dfl.dm_page || '-' ||\n" +
				"       dfl.location_code || '-' || dfl.effective_area || '-' ||\n" +
				"       dfl.accept_unit || '-' ||\n" +
				/*"       decode(trim(tcm.ower_dept_no),\n" +
				"               'Own Brand',\n" +
				"               'Own Brand',\n" +
				"               tiuv.group_desc) " +*/
				" tta_six_action_fun(tcm.ower_dept_no,tiuv.group_desc,tiuv.group_code,'1')" +
				"|| '-' || tiuv.brand_cn) as content, --活动内容 \n" +
				"       tiuv.group_code as group_code,\n" +
				//"		decode(trim(tcm.ower_dept_no),'Own Brand','Own Brand',tiuv.group_desc) group_desc,\n" +
				"       tta_six_action_fun(tcm.ower_dept_no,tiuv.group_desc,tiuv.group_code,'1') group_desc,\n" +
				"       tiuv.dept_desc as dept_desc,\n" +
				"       tiuv.dept_code,\n" +
				"       tiuv.item_desc_cn as item_cn,\n" +
				"       tiuv.brand_cn as brand_cn,\n" +
				"       nvl(tpiw.vendor_nbr,tiuv.vendor_nbr) as prior_vendor_code,\n" +
				"       ts.supplier_name as prior_vendor_name,\n" +
				"       tcm.contract_date as contract_year,\n" +
				"       tcm.status as contract_status,\n" +
				"		tta_fun_dm_report_charge(nvl(tpiw.vendor_nbr,tiuv.vendor_nbr), substr(dfl.year_month,0,4), tiuv.group_code, dfl.unit_code) as  charge_standards_money, --收费标准&计费金额\n" +
				"       null as receive_amount,\n" + //则应收金额 =”计费金额“/4*“生效区域数”
				"       null as unconfirm_amount, --采购确认收取金额\n" +
				"       null as advance_amount,\n" +
				"       null as advance_code,\n" +
				"       null as difference,\n" +
				"       null as no_difference,\n" +
				"       'TTA'  as collect,\n" +
				"       null as purchase,\n" +
				"       null as exemption_reason,\n" +
				"       null as exemption_reason2,\n" +
				"       null as debit_order_code,\n" +
				"       null as receipts,\n" +
				"       1 as status,\n" +
				"       null as parent_id,\n" +
				 //"       null as parent_vendor_code,\n" +
				//"       null as parent_vendor_name,\n" +
				"       nvl(tpiw.vendor_nbr,tiuv.vendor_nbr) as parent_vendor_code,\n" +
				"       ts.supplier_name as parent_vendor_name,\n" +
				"'"+ batchCode + "' as batch_code,\n" +
				" tta_fun_dm_report_contract_terms(nvl(tpiw.vendor_nbr,tiuv.vendor_nbr), substr(dfl.year_month,0,4)) as contract_terms,\n" +
				" nvl(tcm.addition_rate,0)/100 as  addition_rate\n" +
				"  from (select blv.lookup_code as unit_code, a.*\n" +
				"     from tta_dm_full_line a\n" +
				"     left join base_lookup_values blv\n" +
				"       on blv.meaning = a.accept_unit\n" +
				"      and blv.delete_flag = '0'\n" +
				"      and blv.system_code = 'BASE'\n" +
				"      and blv.lookup_type = 'UNIT') dfl\n" +
				"  left join (select item_nbr, vendor_nbr, receive_date\n" +
				"               from (select tpi.item_nbr,\n" +
				"                            tpi.vendor_nbr,\n" +
				"                            tpi.receive_date,\n" +
				"                            row_number() over(partition by tpi.item_nbr order by tpi.receive_date desc) rn\n" +
				"                       from tta_purchase_in_" + year + " tpi) \n" + //-- 修改日期
				"              where rn = 1) tpiw\n" +
				"    on dfl.item_nbr = tpiw.item_nbr\n" +
				"  left join tta_item_unique_v tiuv\n" +
				"    on tiuv.item_nbr = dfl.item_nbr\n" +
				"  left join tta_supplier ts \n" +
				"   on nvl(tpiw.vendor_nbr,tiuv.vendor_nbr) = ts.supplier_code left join tta_contract_master tcm \n" +
				"   on ts.supplier_code = tcm.vendor_nbr\n" +
				"   where dfl.duration_period='" +  durationPeriod + "'"
				+ "\n)\ttab \n";
		return  sql;
	}

	public static String getUpdateReportOwn(Integer userId,String ps,String batchCode) {
		return "update   tta_dm_checking  ttc  set ttc.PURCHASE = 'A11', ttc.EXEMPTION_REASON = 'NC01',ttc.unconfirm_amount = 0,ttc.no_unconfirm_amount = 0\n" +
				" where  ttc.duration_period = '" + ps + "' and ttc.batch_code = '" +batchCode+ "' and ttc.group_desc  = 'Own Brand'";
	};

	public static String getUpdateReportBaseDm(Integer userId,String durationPeriod){
		return  "update tta_dm_full_line  dfl set dfl.is_create = 'Y' where dfl.duration_period ='"+durationPeriod+"'"  ;
	}

	/**
	 * 调整应收金额逻辑处理：proposal变更或者制作通过的单据信息，差异调整金额插入业务表
	 */
	public static String getAdjustAmountSlq(String durationPeriod,Integer operatorUserId, String batchCode, String dateString) {
		String year = durationPeriod.substring(0,4);

		//不使用(2020,12.3,注释之后的SQL是参考此SQL修改的)
		/*String sql = "insert into tta_dm_checking\n" +
				"  (dm_id,\n" +
				"   duration_period,\n" +
				"   dm_page,\n" +
				"   location_code,\n" +
				"   effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   accept_unit,\n" +
				"   item_nbr,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   creation_date,\n" +
				"   last_update_login,\n" +
				"   version_num,\n" +
				"   year_month,\n" +
				"   content,\n" +
				"   group_code,\n" +
				"   dept_desc,\n" +
				"   dept_code,\n" +
				"   item_cn,\n" +
				"   brand_cn,\n" +
				"   prior_vendor_code,\n" +
				"   prior_vendor_name,\n" +
				"   contract_year,\n" +
				"   contract_status,\n" +
				"   charge_standards,\n" +
				"   charge_money,\n" +
				"   receive_amount,\n" +
				"   unconfirm_amount,\n" +
				"   advance_amount,\n" +
				"   advance_code,\n" +
				"   difference,\n" +
				"   no_difference,\n" +
				"   collect,\n" +
				"   purchase,\n" +
				"   exemption_reason,\n" +
				"   exemption_reason2,\n" +
				"   debit_order_code,\n" +
				"   receipts,\n" +
				"   status,\n" +
				"   parent_id,\n" +
				"   parent_vendor_code,\n" +
				"   parent_vendor_name,\n" +
				"   batch_code,\n" +
				"   contract_terms,\n" +
				"   transfer_out_person,\n" +
				"   transfer_last_person,\n" +
				"   transfer_in_date,\n" +
				"   transfer_out_date,\n" +
				"   transfer_last_date,\n" +
				"   transfer_in_person,\n" +
				"   original_amount,\n" +
				"   original_before_amount,\n" +
				"   group_desc,\n" +
				"   addition_rate,\n" +
				"   process_id,\n" +
				"   no_receive_amount,\n" +
				"   no_unconfirm_amount,\n" +
				"   adjust_by,\n" +
				"   adjust_amount,\n" +
				"   no_adjust_amount,\n" +
				"   adjust_receive_amount,\n" +
				"   no_adjust_receive_amount,\n" +
				"   type,\n" +
				"   company_dept_name,\n" +
				"   company_dept_code\n" +
				"   )\n" +
				" select  " +
				"   seq_tta_dm_checking.nextval as dm_id,\n" +
				"   duration_period,\n" +
				"   dm_page,\n" +
				"   location_code,\n" +
				"   effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   accept_unit,\n" +
				"   item_nbr,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   creation_date,\n" +
				"   last_update_login,\n" +
				"   version_num,\n" +
				"   year_month,\n" +
				"   ('DM' || '-' || duration_period || '-' ||  dm_page || '-' ||\n" +
				"        location_code || '-' || effective_area || '-' ||\n" +
				"        accept_unit || '-' ||\n" +
				" tta_six_action_fun(ower_dept_no, group_desc, group_code,'1')|| '-' || brand_cn ) as content, -- 活动内容\n" +
				"   group_code,\n" +
				"   dept_desc,\n" +
				"   dept_code,\n" +
				"   item_cn,\n" +
				"   brand_cn,\n" +
				"   prior_vendor_code,\n" +
				"   prior_vendor_name,\n" +
				"   substr(contract_year,6,4) as contract_year,\n" +
				"   contract_status,\n" +
				"   substr(contract_year,0,4) as charge_standards,\n" +
				"   substr(contract_year,11) as charge_money,\n" +
				"   receive_amount,\n" +
				"   unconfirm_amount,\n" +
				"   advance_amount,\n" +
				"   advance_code,\n" +
				"   difference,\n" +
				"   no_difference,\n" +
				"   'TTA' as collect,\n" +
				"   purchase,\n" +
				"   exemption_reason,\n" +
				"   exemption_reason2,\n" +
				"   debit_order_code,\n" +
				"   receipts,\n" +
				"   status,\n" +
				"   parent_id,\n" +
				"   parent_vendor_code,\n" +
				"   parent_vendor_name,\n" +
				"   batch_code,\n" +
				"   tta_fun_dm_report_contract_terms(prior_vendor_code, substr(contract_year,6,4)) as contract_terms,\n" +
				"   transfer_out_person,\n" +
				"   transfer_last_person,\n" +
				"   transfer_in_date,\n" +
				"   transfer_out_date,\n" +
				"   transfer_last_date,\n" +
				"   transfer_in_person,\n" +
				"   original_amount,\n" +
				"   original_before_amount,\n" +
				"   group_desc,\n" +
				"   addition_rate,\n" +
				"   process_id,\n" +
				"   no_receive_amount,\n" +
				"   (substr(contract_year,11) - no_adjust_receive_amount)  as no_unconfirm_amount,\n" + //需采购确认收取金额（不含加成）【NO_UNCONFIRM_AMOUNT】”初始值默认等于“调整应收金额（不含加成）”【NO_ADJUST_RECEIVE_AMOUNT】
				"   adjust_by,\n" +
				"   adjust_amount,\n" +
				"   no_adjust_amount,\n" +
				"   adjust_receive_amount,\n" +
				"   (substr(contract_year,11) - no_adjust_receive_amount) as  no_adjust_receive_amount,\n" +
				"   type,\n" +
				"   company_dept_name,\n" +
				"    company_dept_code\n" +
				"  from  (\n" +
				" select\n" +
				"	null as dm_id," +
				" 	tab2.duration_period as  duration_period,\n" +
				"   tab2.dm_page,\n" +
				"   tab2.location_code,\n" +
				"   tab2.effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   tab2.accept_unit as accept_unit,\n" +
				"   tab2.item_nbr as item_nbr,\n" +
				operatorUserId + " as created_by,\n" +
				operatorUserId + " as last_updated_by,\n" +
				" to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as last_update_date,\n" +
				" to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as creation_date,\n" +
				operatorUserId + " as last_update_login,\n" +
				"   0 as version_num,\n" +
				"'" + durationPeriod + "' as year_month,\n" +
				"   null as content,\n" +
				"   tab2.group_code,\n" +
				"   tab2.dept_desc,\n" +
				"   tab2.dept_code,\n" +
				"   tab2.item_cn as item_cn,\n" +
				"   tab2.brand_cn,\n" +
				"   tab2.prior_vendor_code,\n" +
				"   tab2.prior_vendor_name,\n" +
				"   tta_fun_dm_report_charge(tab2.prior_vendor_code, dm_year, group_code, tab2.unit_code)  as contract_year,\n" +
				"   'Active' as contract_status,\n" +
				"   null as charge_standards, --收费标准\n" +
				"   null as charge_money, --计费金额 \n" +
				"   null as receive_amount,\n" +
				"   null as unconfirm_amount,\n" +
				"   null as advance_amount,\n" +
				"   null as advance_code,\n" +
				"   null as difference,\n" +
				"   null as no_difference,\n" +
				"   null as collect,\n" +
				"   null as purchase,\n" +
				"   null as exemption_reason,\n" +
				"   null as exemption_reason2,\n" +
				"   null as debit_order_code,\n" +
				"   null as receipts,\n" +
				"   1 as status,\n" +
				"   null as parent_id,\n" +
				"   tab2.prior_vendor_code as parent_vendor_code,\n" +
				"   tab2.prior_vendor_name as parent_vendor_name,\n" +
				"   '" + batchCode + "' as batch_code,\n" +
				"   null as contract_terms,\n" +
				"   null as transfer_out_person,\n" +
				"   null as transfer_last_person,\n" +
				"   null as transfer_in_date,\n" +
				"   null as transfer_out_date,\n" +
				"   null as transfer_last_date,\n" +
				"   null as transfer_in_person,\n" +
				"   null as original_amount,\n" +
				"   null as original_before_amount,\n" +
				"   tab2.group_desc as group_desc,\n" +
				"   nvl(tcm.addition_rate,0)/100  as addition_rate,\n" +
				"   null as process_id,\n" +
				"   null as no_receive_amount,\n" +
				"   null as no_unconfirm_amount,\n" +
				"   null as adjust_by,\n" +
				"   substr(tta_fun_dm_report_charge(tab2.prior_vendor_code, tab2.dm_year,  tab2.group_code, tab2.unit_code),11) /4 * tab2.effective_area_cnt - tab2.amount as adjust_amount,  --调整应收金额\n" +
				"	null as no_adjust_amount,\n" +
				"	null as adjust_receive_amount,\n"+
				"	tab2.no_adjust_receive_amount as no_adjust_receive_amount,\n"+
				"   1 as type,\n" +
				"   null as company_dept_name,\n" +
				"   null as company_dept_code,\n" +
				"   tcm.ower_dept_no\n" +
				"from (\n" +
				"       select tph.proposal_year,\n" +
				"               ma.supplier_code\n" +
				"          from tta_proposal_header tph\n" +
				"          inner join tta_supplier_major_v ma \n" +
				"          on  tph.vendor_nbr = ma.major_supplier_code\n" +
				"            where tph.approve_date <= to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  -- 当前系统时间\n" +
				"             and tph.approve_date >= (select nvl(max(tdc.creation_date), sysdate)  from tta_dm_checking tdc \n" +
				"			where  tdc.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')) -- 上次生成dm单据的日期，不包含当前时间 \n" +
				"             and nvl(tph.version_status, '1') = '1'\n" +
				"             and tph.status = 'C'\n" +
				"        ) tab\n" +
				"  inner join (\n" +
				"            select  max(substr(tom.duration_period, 0, 4)) as dm_year,\n" +
				"                    tom.prior_vendor_code,\n" +
				"                    max(tom.prior_vendor_name) as prior_vendor_name,\n" +
				"                    tom.group_code,\n" +
				"                    max(tom.group_desc) as group_desc,\n" +
				"                    max(tom.dept_desc) as dept_desc,\n" +
				"                    tom.dept_code,\n" +
				"                    tom.brand_cn,\n" +
				"                    sum(nvl(tom.receive_amount, 0) + nvl(tom.adjust_receive_amount, 0)) as amount, -- 应收调整含税金额=应收含税+调整金额之和 \n" +
				"					  sum(nvl(tom.no_receive_amount,0) + nvl(tom.no_adjust_receive_amount,0)) no_adjust_receive_amount,  -- no_receive_amount\n" +
				"                    sum(effective_area_cnt) as effective_area_cnt,\n" +
				"                    blv.lookup_code as unit_code,\n" +
				"                    tom.accept_unit as accept_unit,\n" +
				"				 	 tom.item_nbr,\n " +
				"					 tom.item_cn,\n  " +
				"					 tom.duration_period,\n" +
				"					 tom.dm_page,\n" +
				"				     tom.location_code,\n" +
				"					 tom.effective_area\n" +
				"               from tta_dm_checking tom " +
				"         		inner join tta_report_header th\n" +
				"            	on tom.batch_code = th.batch_id " +
				"				and th.is_publish = 'Y' \n" +
				"				and nvl(tom.status,1) = 1 \n" +
				"				left join  base_lookup_values blv\n" +
				"               on blv.meaning = tom.accept_unit\n" +
				"                and blv.delete_flag = '0'\n" +
				"                and blv.system_code = 'BASE'\n" +
				"                and blv.lookup_type = 'UNIT'\n" +
				"              where substr(tom.duration_period, 0,4) ='" + year  + "' and tom.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss') -- 年至今的数据,不包含当前时间的数据 \n" +
				"               group by  tom.prior_vendor_code, tom.group_code,  tom.dept_code, tom.brand_cn,  blv.lookup_code, " +
				"				tom.accept_unit,\ntom.item_nbr,\n tom.item_cn,\n tom.duration_period,\n" +
				"				tom.dm_page,location_code,effective_area" +
				"             ) tab2\n" +
				"     on tab.proposal_year = tab2.dm_year\n" +
				"     and tab.supplier_code = nvl(tab2.prior_vendor_code,'-1')\n" +
				" 	  left join  tta_contract_master tcm \n" +
				"     on tcm.vendor_nbr = tab.supplier_code \n" +
				"   where substr(tta_fun_dm_report_charge(tab.supplier_code, dm_year,  group_code, tab2.unit_code),11) / 4 * effective_area_cnt - amount != 0) tb \n";*/

		String sql = "insert into tta_dm_checking\n" +
				"  (\n" +
				"   dm_id,\n" +
				"   duration_period,--促销期间(促销档期)\n" +
				"   dm_page,--DM page\n" +
				"   location_code,\n" +
				"   effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   accept_unit,\n" +
				"   item_nbr,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   creation_date,\n" +
				"   last_update_login,\n" +
				"   version_num,\n" +
				"   year_month,\n" +
				"   content,\n" +
				"   group_code,\n" +
				"   dept_desc,\n" +
				"   dept_code,\n" +
				"   item_cn,\n" +
				"   brand_cn,\n" +
				"   prior_vendor_code,\n" +
				"   prior_vendor_name,\n" +
				"   contract_year,\n" +
				"   contract_status,\n" +
				"   charge_standards,\n" +
				"   charge_money,\n" +
				"   receive_amount,\n" +
				"   unconfirm_amount,\n" +
				"   advance_amount,\n" +
				"   advance_code,\n" +
				"   difference,\n" +
				"   no_difference,\n" +
				"   collect,\n" +
				"   purchase,\n" +
				"   exemption_reason,\n" +
				"   exemption_reason2,\n" +
				"   debit_order_code,\n" +
				"   receipts,\n" +
				"   status,\n" +
				"   parent_id,\n" +
				"   parent_vendor_code,\n" +
				"   parent_vendor_name,\n" +
				"   batch_code,\n" +
				"   contract_terms,\n" +
				"   transfer_out_person,\n" +
				"   transfer_last_person,\n" +
				"   transfer_in_date,\n" +
				"   transfer_out_date,\n" +
				"   transfer_last_date,\n" +
				"   transfer_in_person,\n" +
				"   original_amount,\n" +
				"   original_before_amount,\n" +
				"   group_desc,\n" +
				"   addition_rate,\n" +
				"   process_id,\n" +
				"   no_receive_amount,\n" +
				"   no_unconfirm_amount,\n" +
				"   adjust_by,\n" +
				"   adjust_amount,-- 调整实收金额(含加成)\n" +
				"   no_adjust_amount,-- 调整实收金额(不含加成)\n" +
				"   adjust_receive_amount,\n" +
				"   no_adjust_receive_amount,\n" +
				"   type,\n" +
				"   company_dept_name,\n" +
				"   company_dept_code\n" +
				"   )\n" +
				"   \n" +
				" select     \n" +
				"   seq_tta_dm_checking.nextval as dm_id,\n" +
				"   duration_period,\n" +
				"   dm_page,\n" +
				"   location_code,\n" +
				"   effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   accept_unit,\n" +
				"   item_nbr,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   creation_date,\n" +
				"   last_update_login,\n" +
				"   version_num,\n" +
				"   year_month,\n" +
				"   ('DM' || '-' || duration_period || '-' ||  dm_page || '-' ||\n" +
				"        location_code || '-' || effective_area || '-' ||\n" +
				"        accept_unit || '-' ||\n" +
				"   tta_six_action_fun(ower_dept_no, group_desc, group_code,'1')|| '-' || brand_cn ) as content, -- 活动内容\n" +
				"   group_code,\n" +
				"   dept_desc,\n" +
				"   dept_code,\n" +
				"   item_cn,\n" +
				"   brand_cn,\n" +
				"   prior_vendor_code, --优先供应商编号\n" +
				"   prior_vendor_name, --优先供应商名称\n" +
				"   substr(contract_year,6,4) as contract_year,\n" +
				"   contract_status,\n" +
				"   substr(contract_year,0,4) as charge_standards,\n" +
				"   substr(contract_year,11) as charge_money,\n" +
				"   receive_amount,\n" +
				"   adjust_receive_amount as unconfirm_amount,--采购确认收取金额 = 调整应收金额\n" +
				"   advance_amount,\n" +
				"   advance_code,\n" +
				"   difference,\n" +
				"   no_difference,\n" +
				"   'TTA' as collect,\n" +
				"   purchase,\n" +
				"   exemption_reason,\n" +
				"   exemption_reason2,\n" +
				"   debit_order_code,\n" +
				"   receipts,\n" +
				"   status,\n" +
				"   parent_id,\n" +
				"   parent_vendor_code,\n" +
				"   parent_vendor_name,\n" +
				"   batch_code,\n" +
				"   tta_fun_dm_report_contract_terms(prior_vendor_code, substr(contract_year,6,4)) as contract_terms,\n" +
				"   transfer_out_person,\n" +
				"   transfer_last_person,\n" +
				"   transfer_in_date,\n" +
				"   transfer_out_date,\n" +
				"   transfer_last_date,\n" +
				"   transfer_in_person,\n" +
				"   original_amount,\n" +
				"   original_before_amount,\n" +
				"   group_desc,\n" +
				"   addition_rate,\n" +
				"   process_id,\n" +
				"   no_receive_amount,\n" +
				"   --(substr(contract_year,11) - no_adjust_receive_amount)  as no_unconfirm_amount,-- 采购确认收取金额 = 计费金额 - 调整应收金额  (逻辑修改:采购确认收取金额 = 调整应收金额)\n" +
				"   no_adjust_receive_amount as no_unconfirm_amount,-- 采购确认收取金额 = 调整应收金额\n" +
				"   adjust_by,\n" +
				"   adjust_amount,\n" +
				"   no_adjust_amount,\n" +
				"   adjust_receive_amount,\n" +
				"   --(substr(contract_year,11) - no_adjust_receive_amount) as  no_adjust_receive_amount,\n" +
				"   no_adjust_receive_amount,\n" +
				"   type,\n" +
				"   company_dept_name,\n" +
				"   company_dept_code\n" +
				"  from  (\n" +
				" select\n" +
				"   null as dm_id,  \n" +
				"   tab2.duration_period as  duration_period,\n" +
				"   tab2.dm_page,\n" +
				"   tab2.location_code,\n" +
				"   tab2.effective_area,\n" +
				"   effective_area_cnt,\n" +
				"   tab2.accept_unit as accept_unit,\n" +
				"   tab2.item_nbr as item_nbr,\n" +
				"   " + operatorUserId + " as created_by,\n" +
				"   " + operatorUserId + " as last_updated_by,\n" +
				"   to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as last_update_date,\n" +
				"   to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as creation_date,\n" +
				"   " + operatorUserId + " as last_update_login,\n" +
				"   0 as version_num,\n" +
				"   '" + durationPeriod + "' as year_month,\n" +
				"   null as content,\n" +
				"   tab2.group_code,\n" +
				"   tab2.dept_desc,\n" +
				"   tab2.dept_code,\n" +
				"   tab2.item_cn as item_cn,\n" +
				"   tab2.brand_cn,\n" +
				"   tab2.prior_vendor_code,\n" +
				"   tab2.prior_vendor_name,\n" +
				"   tta_fun_dm_report_charge(tab2.prior_vendor_code, dm_year, group_code, tab2.unit_code)  as contract_year,\n" +
				"   'Active' as contract_status,\n" +
				"   null as charge_standards, --收费标准\n" +
				"   null as charge_money, --计费金额 \n" +
				"   null as receive_amount,\n" +
				"   --null as unconfirm_amount,\n" +
				"   null as advance_amount,\n" +
				"   null as advance_code,\n" +
				"   null as difference,\n" +
				"   null as no_difference,\n" +
				"   null as collect,\n" +
				"   null as purchase,\n" +
				"   null as exemption_reason,\n" +
				"   null as exemption_reason2,\n" +
				"   null as debit_order_code,\n" +
				"   null as receipts,\n" +
				"   1 as status,\n" +
				"   null as parent_id,\n" +
				"   tab2.prior_vendor_code as parent_vendor_code,\n" +
				"   tab2.prior_vendor_name as parent_vendor_name,\n" +
				"   '" + batchCode + "' as batch_code,\n" +
				"   null as contract_terms,\n" +
				"   null as transfer_out_person,\n" +
				"   null as transfer_last_person,\n" +
				"   null as transfer_in_date,\n" +
				"   null as transfer_out_date,\n" +
				"   null as transfer_last_date,\n" +
				"   null as transfer_in_person,\n" +
				"   null as original_amount,\n" +
				"   null as original_before_amount,\n" +
				"   tab2.group_desc as group_desc,\n" +
				"   nvl(tcm.addition_rate,0)/100  as addition_rate,\n" +
				"   null as process_id,\n" +
				"   null as no_receive_amount,\n" +
				"   null as no_unconfirm_amount,\n" +
				"   null as adjust_by,\n" +
				"   --substr(tta_fun_dm_report_charge(tab2.prior_vendor_code, tab2.dm_year,  tab2.group_code, tab2.unit_code),11) \n" +
				"   --/4 * tab2.effective_area_cnt - tab2.amount as adjust_amount,  --调整应收金额 \n" +
				"  null as adjust_amount, -- 调整实收金额(含加成)\n" +
				"  null as no_adjust_amount, -- 调整实收金额(不含加成)\n" +
				"  --null as adjust_receive_amount,\n" +
				"  substr(tta_fun_dm_report_charge(tab2.prior_vendor_code, tab2.dm_year,  tab2.group_code, tab2.unit_code),11) \n" +
				"   / 4 * tab2.effective_area_cnt * (1 + nvl(tcm.addition_rate, 0) / 100) - tab2.amount as adjust_receive_amount,  --调整应收金额(含加成)\n" +
				"  --tab2.no_adjust_receive_amount as no_adjust_receive_amount,\n" +
				"    substr(tta_fun_dm_report_charge(tab2.prior_vendor_code, tab2.dm_year,  tab2.group_code, tab2.unit_code),11) \n" +
				"   / 4 * tab2.effective_area_cnt - tab2.no_amount as no_adjust_receive_amount,  --调整应收金额(不含加成)\n" +
				"   1 as type,--调整差异来源\n" +
				"   null as company_dept_name,\n" +
				"   null as company_dept_code,\n" +
				"   tcm.ower_dept_no\n" +
				"from (     ---- 找出上一批次到当前批次之间的Proposal数据(包含变更的)\n" +
				"       select tph.proposal_year,\n" +
				"               ma.supplier_code\n" +
				"          from tta_proposal_header tph\n" +
				"          inner join tta_supplier_major_v ma \n" +
				"          on  tph.vendor_nbr = ma.major_supplier_code\n" +
				"            where tph.approve_date <= to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  -- 当前系统时间\n" +
				"             and tph.approve_date >= (\n" +
				"       \n" +
				"        select nvl(max(tdc.creation_date), sysdate)  from tta_dm_checking tdc \n" +
				"      where  tdc.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')\n" +
				"    \n" +
				"    ) -- 上次生成dm单据的日期，不包含当前时间 \n" +
				"             and nvl(tph.version_status, '1') = '1'\n" +
				"             and tph.status = 'C'\n" +
				"    ) tab\n" +
				"  inner join ( --- 计算年至今的费用\n" +
				"            select  max(substr(tom.duration_period, 0, 4)) as dm_year,\n" +
				"                    tom.prior_vendor_code,\n" +
				"                    max(tom.prior_vendor_name) as prior_vendor_name,\n" +
				"                    tom.group_code,\n" +
				"                    max(tom.group_desc) as group_desc,\n" +
				"                    max(tom.dept_desc) as dept_desc,\n" +
				"                    tom.dept_code,\n" +
				"                    tom.brand_cn,\n" +
				"                    sum(nvl(tom.receive_amount, 0) + nvl(tom.adjust_receive_amount, 0)) as amount, -- 应收调整含税金额=应收含税+调整金额之和(含加成) \n" +
				"          --sum(nvl(tom.no_receive_amount,0) + nvl(tom.no_adjust_receive_amount,0)) no_adjust_receive_amount,  -- no_receive_amount\n" +
				"          sum(nvl(tom.no_receive_amount,0) + nvl(tom.no_adjust_receive_amount,0)) no_amount,  --应收调整含税金额=应收含税+调整金额之和(不含加成) \n" +
				"                    sum(effective_area_cnt) as effective_area_cnt,\n" +
				"                    blv.lookup_code as unit_code,\n" +
				"                    tom.accept_unit as accept_unit,\n" +
				"          tom.item_nbr,\n" +
				"          tom.item_cn,\n" +
				"          tom.duration_period,\n" +
				"          tom.dm_page,\n" +
				"          tom.location_code,\n" +
				"          tom.effective_area\n" +
				"               \n" +
				"    from tta_dm_checking tom            \n" +
				"        inner join tta_report_header th\n" +
				"              on tom.batch_code = th.batch_id         and th.is_publish = 'Y' \n" +
				"        and ( ( nvl(tom.status,1) = 1 and tom.parent_id is null) or (tom.status = 0 and tom.parent_id is null) )-- 筛选不是拆分的数据,还有已拆分的最上级的记录\n" +
				"        left join  base_lookup_values blv\n" +
				"               on blv.meaning = tom.accept_unit\n" +
				"                and blv.delete_flag = '0'\n" +
				"                and blv.system_code = 'BASE'\n" +
				"                and blv.lookup_type = 'UNIT'\n" +
				"              where substr(tom.duration_period, 0,4) ='" + year + "' \n" +
				"        and tom.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss') -- 年至今的数据,不包含当前时间的数据 \n" +
				"               group by  \n" +
				"        tom.prior_vendor_code, \n" +
				"        tom.group_code,  \n" +
				"        tom.dept_code, \n" +
				"        tom.brand_cn,  \n" +
				"        blv.lookup_code,\n" +
				"        tom.accept_unit,\n" +
				"        tom.item_nbr,\n" +
				"        tom.item_cn,\n" +
				"        tom.duration_period,\n" +
				"        tom.dm_page,\n" +
				"        location_code,\n" +
				"        effective_area             \n" +
				"    ) tab2\n" +
				"     on tab.proposal_year = tab2.dm_year\n" +
				"     and tab.supplier_code = nvl(tab2.prior_vendor_code,'-1')\n" +
				"    left join  tta_contract_master tcm \n" +
				"     on tcm.vendor_nbr = tab.supplier_code \n" +
				"   where \n" +
				"  --substr(tta_fun_dm_report_charge(tab.supplier_code, dm_year,  group_code, tab2.unit_code),11)  --- 应收金额 - 调整金额 (有变化的才出来)\n" +
				"    --/ 4 * effective_area_cnt * (1 + nvl(tcm.addition_rate, 0) / 100) - amount != 0\n" +
				"  substr(tta_fun_dm_report_charge(tab.supplier_code, dm_year,  group_code, tab2.unit_code),11)  --- 应收金额 - 调整金额 (有变化的才出来)\n" +
				"    / 4 * effective_area_cnt  - no_amount != 0\n" +
				"  \n" +
				"  ) tb ";
		return sql;
	}



	/**
	 * 将上次的将收取插入当前单据中
	 */
	public static final String getUpdateReportBaseLastTimes(String batchCode,Integer userId,String durationPeriod, String dateString) {
		/*String sql = "insert into tta_dm_checking(\n" +
				"         dm_id,\n" +
				"         duration_period,\n" +
				//"         promotion_start_date,\n" +
				//"         promotion_end_date,\n" +
				"         dm_page,\n" +
				"         location_code,\n" +
				"         effective_area,\n" +
				"         effective_area_cnt,\n" +
				//"         map_position,\n" +
				"         accept_unit,\n" +
				"         item_nbr,\n" +
				"         created_by,\n" +
				"         last_updated_by,\n" +
				"         last_update_date,\n" +
				"         creation_date,\n" +
				"         last_update_login,\n" +
				"         version_num,\n" +
				"         year_month,\n" +
				"         content,\n" +
				"         group_code,\n" +
				"         dept_desc,\n" +
				"         item_cn,\n" +
				"         brand_cn,\n" +
				"         prior_vendor_code,\n" +
				"         prior_vendor_name,\n" +
				"         contract_year,\n" +
				"         contract_status,\n" +
				"         charge_standards,\n" +
				"         charge_money,\n" +
				"         receive_amount,\n" +
				"         unconfirm_amount,\n" +
				"         advance_amount,\n" +
				"         advance_code,\n" +
				"         difference,\n" +
				"         no_difference,\n" +
				"         collect,\n" +
				"         purchase,\n" +
				"         exemption_reason,\n" +
				"         exemption_reason2,\n" +
				"         debit_order_code,\n" +
				"         receipts,\n" +
				"         status,\n" +
				"         parent_id,\n" +
				"         parent_vendor_code,\n" +
				"         parent_vendor_name,\n" +
				"         batch_code,\n" +
				"         contract_terms,\n" +
				"         transfer_out_person,\n" +
				"         transfer_last_person,\n" +
				"         transfer_in_date,\n" +
				"         transfer_out_date,\n" +
				"         transfer_last_date,\n" +
				"         transfer_in_person,\n" +
				"         original_amount,\n" +
				"         original_before_amount,\n" +
				"         group_desc,\n" +
				"         addition_rate,\n" +
				"         process_id,\n" +
				"         no_receive_amount,\n" +
				"         no_unconfirm_amount,\n" +
				"         adjust_by,\n" +
				"         adjust_amount,\n" +
				"         no_adjust_amount,\n" +
				"         adjust_receive_amount,\n" +
				"         no_adjust_receive_amount,\n" +
				"         type,\n" +
				"         company_dept_name,\n" +
				"         company_dept_code\n" +
				"  )\n" +
				"  select seq_tta_dm_checking.nextval as dm_id,\n" +
				"		  tdc.duration_period,\n" +
				//"         tdc.promotion_start_date,\n" +
				//"         tdc.promotion_end_date,\n" +
				"         tdc.dm_page,\n" +
				"         tdc.location_code,\n" +
				"         tdc.effective_area,\n" +
				"         tdc.effective_area_cnt,\n" +
				//"         tdc.map_position,\n" +
				"         tdc.accept_unit,\n" +
				"         tdc.item_nbr,\n" +
				userId + " as created_by,\n" +
				userId + " as last_updated_by,\n" +
				"         to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as  last_update_date,\n" +
				"         to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')  as  creation_date,\n" +
				userId + " as last_update_login,\n" +
				"         tdc.version_num,\n" +
				"         tdc.year_month,\n" +
				"         tdc.content,\n" +
				"         tdc.group_code,\n" +
				"         tdc.dept_desc,\n" +
				"         tdc.item_cn,\n" +
				"         tdc.brand_cn,\n" +
				"         tdc.prior_vendor_code,\n" +
				"         tdc.prior_vendor_name,\n" +
				"         tdc.contract_year,\n" +
				"         tdc.contract_status,\n" +
				"         tdc.charge_standards,\n" +
				"         tdc.charge_money,\n" +
				"         tdc.receive_amount,\n" +
				"         tdc.unconfirm_amount,\n" +
				"         tdc.advance_amount,\n" +
				"         tdc.advance_code,\n" +
				"         tdc.difference,\n" +
				"         tdc.no_difference,\n" +
				"         tdc.collect,\n" +
				"         tdc.purchase,\n" +
				"         tdc.exemption_reason,\n" +
				"         tdc.exemption_reason2,\n" +
				"         tdc.debit_order_code,\n" +
				"         tdc.receipts,\n" +
				"         tdc.status,\n" +
				"         tdc.parent_id,\n" +
				"         tdc.parent_vendor_code,\n" +
				"         tdc.parent_vendor_name,\n" +
				"'" + batchCode + "' as batch_code,\n" +
				"         tdc.contract_terms,\n" +
				"         tdc.transfer_out_person,\n" +
				"         tdc.transfer_last_person,\n" +
				"         tdc.transfer_in_date,\n" +
				"         tdc.transfer_out_date,\n" +
				"         tdc.transfer_last_date,\n" +
				"         tdc.transfer_in_person,\n" +
				"         tdc.original_amount,\n" +
				"         tdc.original_before_amount,\n" +
				"         tdc.group_desc,\n" +
				"         tdc.addition_rate,\n" +
				"         null as process_id,\n" +
				"         tdc.no_receive_amount,\n" +
				"         tdc.no_unconfirm_amount,\n" +
				"         tdc.adjust_by,\n" +
				"         tdc.adjust_amount,\n" +
				"         tdc.no_adjust_amount,\n" +
				"         tdc.adjust_receive_amount,\n" +
				"         tdc.no_adjust_receive_amount,\n" +
				"         '2' as type,\n" +
				"         tdc.company_dept_name,\n" +
				"         tdc.company_dept_code\n" +
				"    from tta_dm_checking tdc \n" +
				"    inner join tta_report_header th\n" +
				"    on tdc.batch_code = th.batch_id " +
				"	 and th.is_publish = 'Y' \n" +
				"    and nvl(tdc.status,1) = 1 \n" +
				"    where tdc.purchase = 'A03'\n" +
				"     AND exists\n" +
				"   (SELECT 1\n" +
				"      FROM (" +
				"			SELECT max(tcc.creation_date) creation_date\n" +
				"       FROM tta_dm_checking tcc\n" +
				"    	inner join tta_report_header th\n" +
				"    	on tcc.batch_code = th.batch_id " +
				"	 	and th.is_publish = 'Y' \n" +
				"    	and nvl(tcc.status,1) = 1 \n" +
				"     where tcc.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')\n" +
				" ) tccb where tccb.creation_date = tdc.creation_date) ";*/
		//以上SQL不用
		String sql = "insert into tta_dm_checking(\n" +
				"         dm_id,\n" +
				"         duration_period,\n" +
				"         dm_page,\n" +
				"         location_code,\n" +
				"         effective_area,\n" +
				"         effective_area_cnt,\n" +
				"         accept_unit,\n" +
				"         item_nbr,\n" +
				"         created_by,\n" +
				"         last_updated_by,\n" +
				"         last_update_date,\n" +
				"         creation_date,\n" +
				"         last_update_login,\n" +
				"         version_num,\n" +
				"         year_month,\n" +
				"         content,\n" +
				"         group_code,\n" +
				"         dept_desc,\n" +
				"         item_cn,\n" +
				"         brand_cn,\n" +
				"         prior_vendor_code,\n" +
				"         prior_vendor_name,\n" +
				"         contract_year,\n" +
				"         contract_status,\n" +
				"         charge_standards,\n" +
				"         charge_money,\n" +
				"         receive_amount,\n" +
				"         unconfirm_amount,\n" +
				"         advance_amount,\n" +
				"         advance_code,\n" +
				"         difference,\n" +
				"         no_difference,\n" +
				"         collect,\n" +
				"         purchase,\n" +
				"         exemption_reason,\n" +
				"         exemption_reason2,\n" +
				"         debit_order_code,\n" +
				"         receipts,\n" +
				"         status,\n" +
				"         parent_id,\n" +
				"         parent_vendor_code,\n" +
				"         parent_vendor_name,\n" +
				"         batch_code,\n" +
				"         contract_terms,\n" +
				"         transfer_out_person,\n" +
				"         transfer_last_person,\n" +
				"         transfer_in_date,\n" +
				"         transfer_out_date,\n" +
				"         transfer_last_date,\n" +
				"         transfer_in_person,\n" +
				"         original_amount,\n" +
				"         original_before_amount,\n" +
				"         group_desc,\n" +
				"         addition_rate,\n" +
				"         process_id,\n" +
				"         no_receive_amount,\n" +
				"         no_unconfirm_amount,\n" +
				"         adjust_by,\n" +
				"         adjust_amount,\n" +
				"         no_adjust_amount,\n" +
				"         adjust_receive_amount,\n" +
				"         no_adjust_receive_amount,\n" +
				"         type,\n" +
				"         company_dept_name,\n" +
				"         company_dept_code\n" +
				"  )\n" +
				"select seq_tta_dm_checking.nextval as dm_id,\n" +
				"       tdc.duration_period,\n" +
				"       tdc.dm_page,\n" +
				"       tdc.location_code,\n" +
				"       tdc.effective_area,\n" +
				"       tdc.effective_area_cnt,\n" +
				"       tdc.accept_unit,\n" +
				"       tdc.item_nbr,\n" +
				"       " + userId + " as created_by,\n" +
				"       " + userId + " as last_updated_by,\n" +
				"       to_date('" + dateString + "', 'yyyy-mm-dd hh24:mi:ss') as last_update_date,\n" +
				"       to_date('" + dateString + "', 'yyyy-mm-dd hh24:mi:ss') as creation_date,\n" +
				"       " + userId + " as last_update_login,\n" +
				"       tdc.version_num,\n" +
				"       tdc.year_month,\n" +
				"       tdc.content,\n" +
				"       tdc.group_code,\n" +
				"       tdc.dept_desc,\n" +
				"       tdc.item_cn,\n" +
				"       tdc.brand_cn,\n" +
				"       tdc.prior_vendor_code,\n" +
				"       tdc.prior_vendor_name,\n" +
				"       tdc.contract_year,\n" +
				"       tdc.contract_status,\n" +
				"       tdc.charge_standards,\n" +
				"       tdc.charge_money,\n" +
				"       tdc.receive_amount,\n" +
				"       tdc.unconfirm_amount,\n" +
				"       tdc.advance_amount,\n" +
				"       tdc.advance_code,\n" +
				"       tdc.difference,\n" +
				"       tdc.no_difference,\n" +
				"       tdc.collect,\n" +
				"       tdc.purchase,\n" +
				"       tdc.exemption_reason,\n" +
				"       tdc.exemption_reason2,\n" +
				"       tdc.debit_order_code,\n" +
				"       tdc.receipts,\n" +
				"       tdc.status,\n" +
				"       tdc.parent_id,\n" +
				"       tdc.parent_vendor_code,\n" +
				"       tdc.parent_vendor_name,\n" +
				"       '" + batchCode + "' as batch_code,\n" +
				"       tdc.contract_terms,\n" +
				"       tdc.transfer_out_person,\n" +
				"       tdc.transfer_last_person,\n" +
				"       tdc.transfer_in_date,\n" +
				"       tdc.transfer_out_date,\n" +
				"       tdc.transfer_last_date,\n" +
				"       tdc.transfer_in_person,\n" +
				"       tdc.original_amount,\n" +
				"       tdc.original_before_amount,\n" +
				"       tdc.group_desc,\n" +
				"       tdc.addition_rate,\n" +
				"       null as process_id,\n" +
				"       tdc.no_receive_amount,\n" +
				"       tdc.no_unconfirm_amount,\n" +
				"       tdc.adjust_by,\n" +
				"       tdc.adjust_amount,\n" +
				"       tdc.no_adjust_amount,\n" +
				"       tdc.adjust_receive_amount,\n" +
				"       tdc.no_adjust_receive_amount,\n" +
				"       '2' as type,\n" +
				"       tdc.company_dept_name,\n" +
				"       tdc.company_dept_code\n" +
				"  from tta_dm_checking tdc\n" +
				" inner join tta_report_header th\n" +
				"    on tdc.batch_code = th.batch_id\n" +
				"   and th.is_publish = 'Y'\n" +
				"   and nvl(tdc.status, 1) = 1\n" +
				" where tdc.purchase = 'A03'\n" +
				"   AND exists\n" +
				" (SELECT 1\n" +
				"          FROM (SELECT max(tcc.creation_date) creation_date\n" +
				"                  FROM tta_dm_checking tcc\n" +
				"                 inner join tta_report_header th\n" +
				"                    on tcc.batch_code = th.batch_id\n" +
				"                   and th.is_publish = 'Y'\n" +
				"                   and nvl(tcc.status, 1) = 1\n" +
				"                 where tcc.creation_date !=\n" +
				"                       to_date('" + dateString + "', 'yyyy-mm-dd hh24:mi:ss')) tccb\n" +
				"         where to_char(tccb.creation_date,'yyyy-mm-dd') = to_char(tdc.creation_date,'yyyy-mm-dd'))";
		return sql;
	}



	public static final String  SELECT_LAST_CREATE_DATE = "select CREATION_DATE \n" +
			"  from (select tom.creation_date\n" +
			"          from tta_dm_checking tom\n" +
			"         order by tom.osd_id desc)\n" +
			" where rownum = 1 ";


	public static final String getChangeVenderSql(String venderCode, Integer year,int effectiveAreaCnt,  String groupCode, String unit) {
		int thirdYear = year - 3;
		String sql = "select " +
				"		tb.major_supplier_code,\n" +
				"		substr(charge_standards_money,11) as charge_money, --计费金额\n" +
				"   	substr(charge_standards_money,11)/4 *" + effectiveAreaCnt + " * (1 + nvl(addition_rate, 0))  as  receive_amount, -- 应收金额（含加成）\n" +
				"   	substr(charge_standards_money,11)/4 *" + effectiveAreaCnt + " as  no_receive_amount,-- 应收金额（不含加成）\n" +
				"		tb.contract_terms,\n" +
				"		tb.proposal_year as contract_year,\n" +
				"		tb.addition_rate,\n" +
				"  		substr(charge_standards_money,0,4)  as chargeStandards\n" +
				" from (\n" +
				"         select tb.major_supplier_code,\n" +
				"                nvl(tcm.addition_rate,0)/100 as addition_rate,\n" +
				"                tta_fun_dm_report_contract_terms(tb.major_supplier_code, tb.proposal_year) as contract_terms, --合同条款\n" +
				"                tta_fun_dm_report_charge(tb.major_supplier_code, tb.proposal_year, '" + groupCode + "', '" + unit + "') as charge_standards_money, --收费标准&计费金额\n" +
				"				  proposal_year\n" +
				"     from (select major_supplier_code, proposal_year\n" +
				"                   from (select ma.major_supplier_code, rv.proposal_year\n" +
				"                           from tta_dept_fee_terms_report_v rv\n" +
				"                          inner join tta_supplier_major_v ma\n" +
				"                             on rv.vendor_nbr = ma.major_supplier_code\n" +
				"                          where rv.status = 'C'\n" +
				"                            and rv.major_dept_code = '05' --大部门\n" +
				"                            and ma.supplier_code = '" + venderCode + "'\n" +
				"                            and rv.proposal_year <=" + year + "\n" +
				"                            and rv.proposal_year >=" + thirdYear + "\n" +
				"                          order by rv.approve_date desc)\n" +
				"                  where rownum = 1) tb\n" +
				"           left join tta_contract_master tcm\n" +
				"             on tb.major_supplier_code = tcm.vendor_nbr" +
				"	) tb\n";
		return sql;
	}

	public static final String getUnitByName(String acceptUnit) {
		String sql = "select t.lookup_code from base_lookup_values t where t.lookup_type = 'UNIT' and t.system_code='BASE' and t.meaning = '" + acceptUnit + "'";
		return  sql;
	}

	public void setDmId(Integer dmId) {
		this.dmId = dmId;
	}

	
	public Integer getDmId() {
		return dmId;
	}

	public void setDurationPeriod(String durationPeriod) {
		this.durationPeriod = durationPeriod;
	}

	public String getDurationPeriod() {
		return durationPeriod;
	}

	public void setDmPage(String dmPage) {
		this.dmPage = dmPage;
	}

	
	public String getDmPage() {
		return dmPage;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	
	public String getLocationCode() {
		return locationCode;
	}

	public void setEffectiveArea(String effectiveArea) {
		this.effectiveArea = effectiveArea;
	}

	
	public String getEffectiveArea() {
		return effectiveArea;
	}

	public void setEffectiveAreaCnt(Integer effectiveAreaCnt) {
		this.effectiveAreaCnt = effectiveAreaCnt;
	}

	
	public Integer getEffectiveAreaCnt() {
		return effectiveAreaCnt;
	}


	public void setAcceptUnit(String acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	
	public String getAcceptUnit() {
		return acceptUnit;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	
	public String getItemNbr() {
		return itemNbr;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	
	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getContent() {
		return content;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setItemCn(String itemCn) {
		this.itemCn = itemCn;
	}

	
	public String getItemCn() {
		return itemCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
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

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	
	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceCode(String advanceCode) {
		this.advanceCode = advanceCode;
	}

	
	public String getAdvanceCode() {
		return advanceCode;
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

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	
	public String getBatchCode() {
		return batchCode;
	}

	public void setContractTerms(String contractTerms) {
		this.contractTerms = contractTerms;
	}

	
	public String getContractTerms() {
		return contractTerms;
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

	public void setTransferInPerson(Integer transferInPerson) {
		this.transferInPerson = transferInPerson;
	}

	
	public Integer getTransferInPerson() {
		return transferInPerson;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupDesc() {
		return groupDesc;
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

	public String getReatedByName() {
		return reatedByName;
	}

	public void setReatedByName(String reatedByName) {
		this.reatedByName = reatedByName;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}

	public String getHeaderStatus() {
		return headerStatus;
	}

	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
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

	public void setAdditionRate(BigDecimal additionRate) {
		this.additionRate = additionRate;
	}

	public BigDecimal getAdditionRate() {
		return additionRate;
	}

	public void setProcessReId(Integer processReId) {
		this.processReId = processReId;
	}

	public Integer getProcessReId() {
		return processReId;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
	}

	public String getProcessStatusName() {
		return processStatusName;
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


	public void setGroupDmCnt(int groupDmCnt) {
		this.groupDmCnt = groupDmCnt;
	}

	public int getGroupDmCnt() {
		return groupDmCnt;
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

	public Integer getAdjustBy() {
		return adjustBy;
	}

	public void setAdjustBy(Integer adjustBy) {
		this.adjustBy = adjustBy;
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

	public String getAdjustByName() {
		return adjustByName;
	}

	public void setAdjustByName(String adjustByName) {
		this.adjustByName = adjustByName;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getCurrentApprovalUser() {
        return currentApprovalUser;
    }

    public void setCurrentApprovalUser(String currentApprovalUser) {
        this.currentApprovalUser = currentApprovalUser;
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

	public String getValueAll() {
		return valueAll;
	}

	public void setValueAll(String valueAll) {
		this.valueAll = valueAll;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}


	public BigDecimal getNoDifference() {
		return noDifference;
	}

	public void setNoDifference(BigDecimal noDifference) {
		this.noDifference = noDifference;
	}

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
}
