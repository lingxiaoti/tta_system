package com.sie.watsons.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * ActBpmTaskConfigEntity_HI_RO Entity Object
 * Mon Feb 17 15:10:30 CST 2020  Auto Generate
 */

public class ActBpmTaskConfigEntity_HI_RO {

	public static final String getNodeSql() {
		String sql = "\n" +
				"select blv.lookup_code as \"orderNum\", blv.meaning as \"nodeName\"\n" +
				"  from base_lookup_values blv\n" +
				" where  blv.enabled_flag = 'Y'\n" +
				"   and blv.delete_flag = 0\n" +
				"   and blv.start_date_active < sysdate\n" +
				"   and (blv.end_date_active >= sysdate or blv.end_date_active is null)\n" +
				"   and blv.system_code = 'BASE' ";
		return sql;
	}

	public static final String getWaitNodeCntSql() {
		String sql = "  select max(tth.dept_desc) as \"deptDesc\",\n" +
				"         b.name_ as \"nodeName\",\n" +
				"         tth.dept_code as \"deptCode\",\n" +
				"         tth.dept_code || '_' || b.name_ as \"deptNodeName\",\n" +
				"         count(1) as \"cnt\"\n" +
				"    from \n" +
				"    tta_proposal_header tph inner join tta_proposal_terms_header tth\n" +
				"    on tph.proposal_id = tth.proposal_id\n" +
				"    left join  act_bpm_list a \n" +
				"    on  tth.proposal_id = a.business_key\n" +
				"    left join act_ru_task b\n" +
				"      on a.proc_inst_id = b.proc_inst_id_\n" +
				"     and a.proc_def_id = b.proc_def_id_\n" +
				"   where a.proc_def_key =:procDefKey\n" +
				"  and tph.status != 'D' " ;
		return sql;
	}

	public static final String getContratWaitNodeCntSql() {
		String sql = "  select max(tth.dept_desc) as \"deptDesc\",\n" +
                "         b.name_ as \"nodeName\",\n" +
                "         tth.dept_code as \"deptCode\",\n" +
                "         tth.dept_code || '_' || b.name_ as \"deptNodeName\",\n" +
                "         count(1) as \"cnt\"\n" +
                "   from act_bpm_list a\n" +
                "   inner join act_ru_task b\n" +
                "      on a.proc_inst_id = b.proc_inst_id_\n" +
                "     and a.proc_def_id = b.proc_def_id_\n" +
                "   inner join tta_elec_con_header tech\n" +
                "      on tech.elec_con_header_id = a.business_key\n" +
                "   inner join tta_proposal_header_new_version_v tph\n" +
                "      on tech.contract_year = tph.proposal_year\n" +
                "     and tech.vendor_code = tph.vendor_nbr\n" +
                "   inner join tta_proposal_terms_header tth\n" +
                "      on tph.proposal_id = tth.proposal_id\n" +
                "   where a.proc_def_key =:procDefKey\n" +
                "     and tth.dept_code != '1'\n" +
                "     and tph.status != 'D'\n" +
                "     and tech.status != 'D'";
		return sql;
	}



    /**
     * Proposal need buyer sumbit to BIC
     * 去系统中供应商管理清单里有标识需交proposal的供应商数量
     */
	public static String getProposalNeedBuyerSumbitToBIC(boolean isContractFlag) {
	  /*  String sql = "select " +
                "               tpt.dept_code as \"deptCode\", " +
                "               count(1) as \"cnt\" \n" +
                "           from tta_proposal_header tph\n" +
                "           inner join tta_supplier ts\n" +
                "              on tph.vendor_nbr = ts.supplier_code\n" +
                "           inner join tta_proposal_terms_header tpt\n" +
                "              on tpt.proposal_id = tph.proposal_id\n" +
                "           where ts.IS_SUBMIT_PROPOSAL = 'Y'\n" +
				" 			  and tpt.dept_code != '1' \n" +
                "           group by dept_code";
	    return sql;*/

	  //2020.9.21原SQL
/*	  String sql = " select ts.owner_group as \"deptCode\", count(1) as \"cnt\" \n" +
			  "          from tta_supplier ts\n" +
			  //"         where ts.is_submit_proposal = 'Y'\n" +
			  (isContractFlag? "where ts.is_submit_contract = 'Y'" : "where ts.is_submit_proposal = 'Y'") + "\n" +
			  "			  and ts.is_new_supplier != 'Y'\n" +
			  "           and not exists\n" +
			  "         (select 1\n" +
			  "                  from tta_rel_supplier trs\n" +
			  "                 where trs.rel_supplier_code = ts.supplier_code)\n" +
			  "         group by ts.owner_group, ts.owner_group_name";*/
		//2020.9.21该逻辑,Proposal流程报表,不需要全新供应商这个条件
		String sql = " select ts.owner_group as \"deptCode\", count(1) as \"cnt\" \n" +
				"          from tta_supplier ts\n" +
				//"         where ts.is_submit_proposal = 'Y'\n" +
				(isContractFlag? "where ts.is_submit_contract = 'Y' and ts.is_new_supplier != 'Y' " : "where ts.is_submit_proposal = 'Y'") + "\n" +
				"           and not exists\n" +
				"         (select 1\n" +
				"                  from tta_rel_supplier trs\n" +
				"                 where trs.rel_supplier_code = ts.supplier_code)\n" +
				"         group by ts.owner_group, ts.owner_group_name";

	  return sql;
    }


    public static String getDeptCodeAndNameSql(boolean isContractFlag) {
		//Proposal流程报表,与tracy讨论:把is_new_supplier条件注释
        String sql = "select ts.dept_code as \"deptCode\", max(dept_desc) as \"deptDesc\"\n" +
				"  from (select ts.owner_group as dept_code, ts.owner_group_name as dept_desc\n" +
				"          from tta_supplier ts\n"
				+ (isContractFlag? "where ts.is_submit_contract = 'Y'" : "where ts.is_submit_proposal = 'Y' -- and ts.is_new_supplier != 'Y'") + "\n" +
				"           and not exists\n" +
				"         (select 1 \n" +
				"                  from tta_rel_supplier trs\n" +
				"                 where trs.rel_supplier_code = ts.supplier_code)\n" +
				"         group by ts.owner_group, ts.owner_group_name\n" +
				"        union all\n" +
				"        select tph.dept_code, dept_desc\n" +
				"          from tta_proposal_terms_header tph\n" +
				"         group by dept_code, dept_desc) ts\n" +
				" where ts.dept_code != '-1'\n" +
				" group by ts.dept_code";
        return sql;
    }


    /**
     * pending to submit 第三列
     */
    public static String getPendingToSubmitSql() {
        String sql = "select dept_code as \"deptCode\", tph.status as \"status\", count(1) as \"cnt\"\n" +
				"  from tta_proposal_header tph\n" +
				" inner join tta_supplier ts\n" +
				"    on tph.vendor_nbr = ts.supplier_code\n" +
				" inner join tta_proposal_terms_header tpt\n" +
				"    on tpt.proposal_id = tph.proposal_id\n" +
				" where ts.IS_SUBMIT_PROPOSAL = 'Y'\n" +
				"   and tph.status in ('A', 'C')\n" +
				" and tpt.dept_code != '1' " +
				" and tph.proposal_year=:proposalYear \n" +
				" group by dept_code, tph.status \n";
        return sql;
    }

    public static String getContractPendingToSubmitSql() {
        String sql = " select tech.dept_code as \"deptCode\",\n" +
                "        tech.status as \"status\",\n" +
                "        count(1) as \"cnt\"\n" +
                "   from tta_elec_con_header tech\n" +
                "  left join tta_supplier ts\n" +
                "     on tech.vendor_code = ts.supplier_code\n" +
                "  where ts.is_submit_contract = 'Y'\n" +
                "    and tech.status in ('A', 'C')\n" +
                "    and tech.dept_code != '1'\n" +
                "    and tech.contract_year =:proposalYear \n" +
                "  group by tech.dept_code, tech.status";
        return sql;
    }


    private Integer configId;
    private String procDefKey;
    private String taskDefId;
    private String taskName;
    private String pcformUrl;
    private String mobformUrl;
    private String pcDataUrl;
    private String mobDataUrl;
    private String processerIds;
    private String processerRoleKeys;
    private String ccIds;
    private String ccRoleKeys;
    private String variables;
    private String callbackUrl;
    private Integer editStatus;
    private Integer enableAutojump;
    private String extend;
    private Integer sortId;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	
	public Integer getConfigId() {
		return configId;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	
	public String getProcDefKey() {
		return procDefKey;
	}

	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	
	public String getTaskDefId() {
		return taskDefId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	
	public String getTaskName() {
		return taskName;
	}

	public void setPcformUrl(String pcformUrl) {
		this.pcformUrl = pcformUrl;
	}

	
	public String getPcformUrl() {
		return pcformUrl;
	}

	public void setMobformUrl(String mobformUrl) {
		this.mobformUrl = mobformUrl;
	}

	
	public String getMobformUrl() {
		return mobformUrl;
	}

	public void setPcDataUrl(String pcDataUrl) {
		this.pcDataUrl = pcDataUrl;
	}

	
	public String getPcDataUrl() {
		return pcDataUrl;
	}

	public void setMobDataUrl(String mobDataUrl) {
		this.mobDataUrl = mobDataUrl;
	}

	
	public String getMobDataUrl() {
		return mobDataUrl;
	}

	public void setProcesserIds(String processerIds) {
		this.processerIds = processerIds;
	}

	
	public String getProcesserIds() {
		return processerIds;
	}

	public void setProcesserRoleKeys(String processerRoleKeys) {
		this.processerRoleKeys = processerRoleKeys;
	}

	
	public String getProcesserRoleKeys() {
		return processerRoleKeys;
	}

	public void setCcIds(String ccIds) {
		this.ccIds = ccIds;
	}

	
	public String getCcIds() {
		return ccIds;
	}

	public void setCcRoleKeys(String ccRoleKeys) {
		this.ccRoleKeys = ccRoleKeys;
	}

	
	public String getCcRoleKeys() {
		return ccRoleKeys;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

	
	public String getVariables() {
		return variables;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}

	
	public Integer getEditStatus() {
		return editStatus;
	}

	public void setEnableAutojump(Integer enableAutojump) {
		this.enableAutojump = enableAutojump;
	}

	
	public Integer getEnableAutojump() {
		return enableAutojump;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	
	public String getExtend() {
		return extend;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	
	public Integer getSortId() {
		return sortId;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
