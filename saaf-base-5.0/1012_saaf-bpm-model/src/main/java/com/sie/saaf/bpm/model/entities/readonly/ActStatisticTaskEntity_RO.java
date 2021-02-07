package com.sie.saaf.bpm.model.entities.readonly;

import java.math.BigDecimal;

/**
 * @author laoqunzhao
 * @createTime 2018-06-01
 * @description 实现流程统计
 */
public class ActStatisticTaskEntity_RO{
	
	 public static final String QUERY_TASK_SQL = "SELECT \n" +
	            "\t proc.key_ AS processDefinitionKey,\n" +
	            "\t task.task_def_key_ as taskDefinitionId,\n" +
	            "\t task.name_ AS taskName,\n" +
	            "\t proc.name_ AS processName,\n" +
	            "\t count(task.id_) AS count \n" +
	            "\t FROM \n" +
	            "\t act_hi_taskinst task \n" +
	            "\t LEFT JOIN act_re_procdef proc ON task.proc_def_id_ = proc.id_ \n" +
	            "\t WHERE\n" +
	            "\t task.task_def_key_ is not null \n";

	private String processDefinitionKey;//流程KEY
	private String processName;//流程名称
	private String taskDefinitionId;//任务
	private String taskName;//任务名称
	private int count;
	private BigDecimal ratio;
	
	
	
	public String getProcessName() {
		return processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getTaskDefinitionId() {
		return taskDefinitionId;
	}

	public void setTaskDefinitionId(String taskDefinitionId) {
		this.taskDefinitionId = taskDefinitionId;
	}
	
	

}
