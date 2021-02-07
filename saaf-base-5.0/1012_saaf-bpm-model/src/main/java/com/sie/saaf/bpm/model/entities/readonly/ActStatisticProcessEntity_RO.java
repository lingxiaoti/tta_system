package com.sie.saaf.bpm.model.entities.readonly;

import com.sie.saaf.bpm.constant.WorkflowConstant;

import java.math.BigDecimal;

/**
 * @author laoqunzhao
 * @createTime 2018-07-26
 * @description 实现流程统计
 */
public class ActStatisticProcessEntity_RO{
	 
	 public static final String QUERY_ALL_SQL = "select \n" +
			 	"\t model.key_ as processDefinitionKey,\n" + 
			 	"\t model.name_ as processName,\n" + 
			 	"\t cate.category_name as categoryName,\n" + 
	            "\t count(list.list_id) as count \n" + 
	            "\t from act_re_model model \n" + 
	            "\t inner join act_bpm_list list on list.proc_def_key=model.key_ \n" +
	            "\t left join act_bpm_category cate on model.category_=cate.category_id \n" + 
	            "\t where 1=1";

	private String processDefinitionKey;//流程KEY
	private String processName;//流程名称
	private String categoryName;
	private int count;
	private BigDecimal ratio;
	private String status;
	
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	
	public String getProcessName() {
		return processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public BigDecimal getRatio() {
		return ratio;
	}
	
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusName() {
		if(WorkflowConstant.TASK_STATUS_PENDING.equals(status)) {
			return "未接收";
		}if(WorkflowConstant.TASK_STATUS_RESOLVED.equals(status)) {
			return "已办结";
		}
		return "办理中";
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
