package com.sie.saaf.bpm.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.utils.ConvertUtil;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActBpmHiTaskEntity_HI_RO{

	public static final String QUERY_ALL_HI_TASK_SQL = "select \n" +
			"\t task.id_ AS taskId,\n" +
			"\t task.execution_id_ AS executionId,\n" +
			"\t task.name_ AS taskName,\n" +
			"\t task.parent_task_id_ AS parentTaskId,\n" +
			"\t task.description_ AS description,\n" +
			"\t task.task_def_key_ AS taskDefinitionId,\n" +
			"\t bpm.proc_def_id AS processDefinitionId,\n" +
            "\t bpm.proc_inst_id AS processInstanceId,\n" +
			"\t task.owner_ AS owner,\n" +
			"\t task.assignee_ AS assignee,\n" +
			"\t task.start_time_ AS startTime,\n" +
			"\t task.claim_time_ AS claimTime,\n" +
			"\t task.end_time_ AS endTime,\n" +
			"\t 'RESOLVED' AS status,\n" +
			"\t usr1.user_id AS userId,\n" +
			"\t usr1.user_name AS userName,\n" +
            "\t usr1.user_full_name AS userFullName,\n" +
			"\t bpm.list_id AS bpm_listId,\n" +
            "\t bpm.list_code AS bpm_listCode,\n" +
            "\t bpm.list_name AS bpm_listName,\n" +
            "\t bpm.category_id AS bpm_categoryId,\n" +
            "\t cat.category_name AS bpm_categoryName,\n" +
            "\t bpm.start_time AS bpm_startTime,\n" +
            "\t bpm.end_time AS bpm_endTime,\n" +
            "\t bpm.status AS bpm_status, \n" +
            "\t bpm.result AS bpm_result,\n" +
            "\t bpm.title AS bpm_title, \n" +
            "\t bpm.description AS bpm_description, \n" +
            "\t bpm.business_key AS bpm_businessKey, \n" +
			"\t bpm.bill_no AS billNo, \n" +
			"\t bpm.properties AS bpm_properties, \n" +
			"\t bpm.responsibility_id AS bpm_responsibilityId,\n" +
            "\t bpm.proc_def_key AS processDefinitionKey, \n" +
            "\t bpm.created_by AS bpm_createdBy,\n" +
            "\t bpm.creation_date AS bpm_creationDate,\n" +
            "\t bpm.last_update_date AS bpm_lastUpdateDate,\n" +
            "\t usr.user_name AS bpm_userName,\n" +
            "\t usr.user_full_name AS bpm_userFullName,\n" +
            //"\t exists(select 1 from act_bpm_task_delegate dlg where dlg.task_id=task.id_ and dlg.status='RESOLVED') AS delegated, \n" +
            //"\t exists(select 1 from act_bpm_communicate cmc where cmc.task_id=task.id_ and cmc.type='COMMON') AS bpm_communicated, \n" +
			"\t nvl((select decode(dlg.delegate_id, null, 0, 1) FROM act_bpm_task_delegate dlg WHERE dlg.task_id = task.id_ AND dlg. STATUS = 'RESOLVED'),0) as delegated,\n" +
			"\t nvl( (select decode(cmc.communicate_id,null,0,1) FROM act_bpm_communicate cmc WHERE cmc.task_id = task.id_ AND cmc.type = 'COMMON'),0) as bpm_communicated,\n" +
			"\t case when bpm.status=0 and (task.task_def_key_ is not null or task.parent_task_id_ is not null) \n" +
			"\t and not exists(select 1 from act_ru_task rtask1 inner join act_bpm_task_leavel rleavel1 on rleavel1.task_id=rtask1.id_ \n" +
			"\t where rleavel1.top_proc_inst_id=leavel.top_proc_inst_id and rtask1.category_='RESOLVING')\n" +
			"\t and not exists(select 1 from act_hi_taskinst rtask2 inner join act_bpm_task_leavel rleavel2 on rleavel2.task_id=rtask2.id_ \n" +
			"\t left join act_hi_detail rdetail2 on rdetail2.task_id_=rtask2.id_\n" +
			"\t where rleavel2.top_proc_inst_id=leavel.top_proc_inst_id and rtask2.end_time_>task.end_time_ and rtask2.delete_reason_='completed' and rdetail2.name_='option' and rdetail2.text_<>'AJ')\n" +
			"\t and exists(select 1 from act_hi_detail rdetail where rdetail.task_id_=task.id_ and rdetail.name_='option' and (rdetail.text_='Y' or rdetail.text_='DF'))\n" +
			"\t then '1' else '0' end as revokeStatus" +
			"\t from \n" +
            "\t act_hi_taskinst task \n" +
            "\t inner join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
            "\t left join act_bpm_list bpm on bpm.proc_inst_id = leavel.top_proc_inst_id \n" +
            "\t left join base_users usr on bpm.created_by = usr.user_id\n" +
            "\t left join base_users usr1 on task.assignee_ = usr1.user_name\n" +
            "\t left join act_bpm_category cat on bpm.category_id = cat.category_id\n" +
            "\t where 1=1\n";

	/**
	 * 减少查询字段，加快查询速度
	 */
	public static final String QUERY_SIMPLE_HI_TASK_SQL = "select \n" +
			"\t task.id_ AS taskId,\n" +
			"\t task.name_ AS taskName,\n" +
			"\t task.parent_task_id_ AS parentTaskId,\n" +
			"\t task.description_ AS description,\n" +
			"\t task.task_def_key_ AS taskDefinitionId,\n" +
			"\t task.proc_def_id_ AS processDefinitionId,\n" +
            "\t task.proc_inst_id_ AS processInstanceId,\n" +
			"\t task.owner_ AS owner,\n" +
			"\t task.assignee_ AS assignee,\n" +
			"\t task.start_time_ AS startTime,\n" +
			"\t task.claim_time_ AS claimTime,\n" +
			"\t task.end_time_ AS endTime,\n" +
			"\t 'RESOLVED' AS status,\n" +
			"\t usr1.user_id AS userId,\n" +
			"\t usr1.user_name AS userName,\n" +
            "\t usr1.user_full_name AS userFullName,\n" +
			"\t bpm.status AS bpm_status, \n" +
			"\t bpm.result AS bpm_result,\n" +
			"\t bpm.proc_def_key AS processDefinitionKey,\n" +
			// "\t exists(select 1 from act_bpm_task_delegate dlg where dlg.task_id=task.id_ and dlg.status='RESOLVED') AS delegated \n" +
			"\t nvl((select decode(dlg.delegate_id, null, 0, 1) FROM act_bpm_task_delegate dlg WHERE dlg.task_id = task.id_ AND dlg. STATUS = 'RESOLVED'),0) as delegated, \n" +
			"\t  row_number() OVER(PARTITION BY task.id_  ORDER BY task.start_time_, task.end_time_ asc) as row_flg\n" +
			"\t from \n" +
            "\t act_hi_taskinst task \n" +
            "\t inner join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
            "\t left join act_bpm_list bpm on bpm.proc_inst_id = leavel.top_proc_inst_id\n" +
            "\t left join base_users usr on bpm.created_by = usr.user_id\n" +
            "\t left join base_users usr1 on task.assignee_ = usr1.user_name\n" +
            "\t left join act_bpm_category cat on bpm.category_id = cat.category_id\n" +
            "\t where 1=1\n";

	/**
	 * 查询单条时使用，使用子查询减少连接优化TiDb查询
	 */
	public static final String QUERY_SINGLE_HI_TASK_SQL = "select \n" +
			"\t task.id_ AS taskId,\n" +
			"\t task.execution_id_ AS executionId,\n" +
			"\t task.name_ AS taskName,\n" +
			"\t task.parent_task_id_ AS parentTaskId,\n" +
			"\t task.description_ AS description,\n" +
			"\t task.task_def_key_ AS taskDefinitionId,\n" +
			"\t bpm.proc_def_id AS processDefinitionId,\n" +
			"\t bpm.proc_inst_id AS processInstanceId,\n" +
			"\t task.owner_ AS owner,\n" +
			"\t task.assignee_ AS assignee,\n" +
			"\t task.start_time_ AS startTime,\n" +
			"\t task.claim_time_ AS claimTime,\n" +
			"\t task.end_time_ AS endTime,\n" +
			"\t 'RESOLVED' AS status,\n" +
			"\t bpm.list_id AS bpm_listId,\n" +
			"\t bpm.list_code AS bpm_listCode,\n" +
			"\t bpm.list_name AS bpm_listName,\n" +
			"\t bpm.category_id AS bpm_categoryId,\n" +
			"\t bpm.start_time AS bpm_startTime,\n" +
			"\t bpm.end_time AS bpm_endTime,\n" +
			"\t bpm.status AS bpm_status, \n" +
			"\t bpm.result AS bpm_result,\n" +
			"\t bpm.title AS bpm_title, \n" +
			"\t bpm.description AS bpm_description, \n" +
			"\t bpm.business_key AS bpm_businessKey, \n" +
			"\t bpm.bill_no AS billNo, \n" +
			"\t bpm.properties AS bpm_properties, \n" +
			"\t bpm.responsibility_id AS bpm_responsibilityId,\n" +
			"\t bpm.proc_def_key AS processDefinitionKey, \n" +
			"\t bpm.created_by AS bpm_createdBy,\n" +
			"\t bpm.creation_date AS bpm_creationDate,\n" +
			"\t bpm.last_update_date AS bpm_lastUpdateDate,\n" +
			"\t task.assignee_ AS userName\n" +
			"\t from \n" +
			"\t act_hi_taskinst task \n" +
			"\t inner join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
			"\t left join act_bpm_list bpm on bpm.proc_inst_id = leavel.top_proc_inst_id\n" +
			"\t where 1=1\n";

	public static final String QUERY_ALL_HI_TASK_COUNT_SQL = "select count(*)\n" +
			"\t from \n" +
			"\t act_hi_taskinst task \n" +
			"\t inner join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
			"\t left join act_bpm_list bpm on bpm.proc_inst_id = leavel.top_proc_inst_id\n" +
			"\t left join base_users usr on bpm.created_by = usr.user_id\n" +
			"\t left join base_users usr1 on task.assignee_ = usr1.user_name\n" +
			"\t left join act_bpm_category cat on bpm.category_id = cat.category_id\n" +
			"\t where 1=1\n";
	
	private String taskId;
	private String executionId;
	private String taskName;
	private String parentTaskId;
	private String description;
	private String taskDefinitionId;
	private String owner;
	private String assignee;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date claimTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	private Integer delegated; //委托审批 1.是    0，否
	private String status;
	private Integer bpm_listId;
	private String bpm_listCode;//申请编号
	private String bpm_listName;//流程名称
	private String bpm_title;//流程发起标题
	private Integer bpm_categoryId;//流程分类ID
	private String bpm_categoryName;//流程分类名称
	private Integer bpm_status;//-1:未提交申请；0:审批中；1:审批结束
	private String bpm_result;//流程处理结果
	private String bpm_description;//申请理由
	private String bpm_businessKey;//业务主键
	private String billNo;//业务申请单号
	private String processDefinitionKey;//流程定义KEY
	private String processDefinitionId;//流程定义ID
	private String processInstanceId;//流程实例ID
	private String revokeStatus;//撤回状态：1.可撤回 0.不可撤回
	private Integer bpm_createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date bpm_creationDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date bpm_lastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date bpm_startTime;//流程开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date bpm_endTime;//流程结束时间
    private String bpm_userName;
    private String bpm_userFullName;
    private Integer bpm_communicated; //是否发起沟通 1.是 0.否
	private Integer bpm_responsibilityId;//职责ID
	private String bpm_properties;//流程表单
    private String his_detail_opinion;
    private String his_detail_option;
    private Integer userId;
    private String userName;
    private String userFullName;


	// Constructors

	/** default constructor */
	public ActBpmHiTaskEntity_HI_RO() {
	}

    public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaskDefinitionId() {
		return taskDefinitionId;
	}

	public void setTaskDefinitionId(String taskDefinitionId) {
		this.taskDefinitionId = taskDefinitionId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String getStatusCode() {
		return status;
	}

	public Integer getDelegated() {
		return delegated;
	}

	public void setDelegated(Integer delegated) {
		this.delegated = delegated;
	}

	public Integer getBpm_listId() {
		return bpm_listId;
	}

	public void setBpm_listId(Integer bpm_listId) {
		this.bpm_listId = bpm_listId;
	}

	public String getBpm_listCode() {
		return bpm_listCode;
	}

	public void setBpm_listCode(String bpm_listCode) {
		this.bpm_listCode = bpm_listCode;
	}

	public String getBpm_listName() {
		return bpm_listName;
	}

	public void setBpm_listName(String bpm_listName) {
		this.bpm_listName = bpm_listName;
	}

	public String getBpm_title() {
		return bpm_title;
	}

	public void setBpm_title(String bpm_title) {
		this.bpm_title = bpm_title;
	}

	public Integer getBpm_categoryId() {
		return bpm_categoryId;
	}

	public void setBpm_categoryId(Integer bpm_categoryId) {
		this.bpm_categoryId = bpm_categoryId;
	}

	public String getBpm_categoryName() {
		return bpm_categoryName;
	}

	public void setBpm_categoryName(String bpm_categoryName) {
		this.bpm_categoryName = bpm_categoryName;
	}

	public Integer getBpm_status() {
		return bpm_status;
	}

	public void setBpm_status(Integer bpm_status) {
		this.bpm_status = bpm_status;
	}

	public String getBpm_result() {
		return bpm_result;
	}

	public void setBpm_result(String bpm_result) {
		this.bpm_result = bpm_result;
	}

	public String getBpm_description() {
		return bpm_description;
	}

	public void setBpm_description(String bpm_description) {
		this.bpm_description = bpm_description;
	}

	public String getBpm_businessKey() {
		return bpm_businessKey;
	}

	public void setBpm_businessKey(String bpm_businessKey) {
		this.bpm_businessKey = bpm_businessKey;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getBpm_createdBy() {
		return bpm_createdBy;
	}

	public void setBpm_createdBy(Integer bpm_createdBy) {
		this.bpm_createdBy = bpm_createdBy;
	}

	public Date getBpm_creationDate() {
		return bpm_creationDate;
	}

	public void setBpm_creationDate(Date bpm_creationDate) {
		this.bpm_creationDate = bpm_creationDate;
	}

	public Date getBpm_lastUpdateDate() {
		return bpm_lastUpdateDate;
	}

	public void setBpm_lastUpdateDate(Date bpm_lastUpdateDate) {
		this.bpm_lastUpdateDate = bpm_lastUpdateDate;
	}

	public Date getBpm_startTime() {
		return bpm_startTime;
	}

	public void setBpm_startTime(Date bpm_startTime) {
		this.bpm_startTime = bpm_startTime;
	}

	public Date getBpm_endTime() {
		return bpm_endTime;
	}

	public void setBpm_endTime(Date bpm_endTime) {
		this.bpm_endTime = bpm_endTime;
	}

	public String getBpm_userName() {
		return bpm_userName;
	}

	public void setBpm_userName(String bpm_userName) {
		this.bpm_userName = bpm_userName;
	}

	public String getBpm_userFullName() {
		return bpm_userFullName;
	}

	public void setBpm_userFullName(String bpm_userFullName) {
		this.bpm_userFullName = bpm_userFullName;
	}

	public Integer getBpm_communicated() {
		return bpm_communicated;
	}

	public void setBpm_communicated(Integer bpm_communicated) {
		this.bpm_communicated = bpm_communicated;
	}

	public String getHis_detail_opinion() {
		return his_detail_opinion;
	}

	public void setHis_detail_opinion(String his_detail_opinion) {
		this.his_detail_opinion = his_detail_opinion;
	}

	public String getHis_detail_option() {
		return his_detail_option;
	}

	public void setHis_detail_option(String his_detail_option) {
		this.his_detail_option = his_detail_option;
	}

	public String getRevokeStatus() {
		return revokeStatus;
	}

	public void setRevokeStatus(String revokeStatus) {
		this.revokeStatus = revokeStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserFullNameNoMarked() {
		return userFullName;
	}

	public Integer getBpm_responsibilityId() {
		return bpm_responsibilityId;
	}

	public void setBpm_responsibilityId(Integer bpm_responsibilityId) {
		this.bpm_responsibilityId = bpm_responsibilityId;
	}


	public String getBpm_properties() {
		return bpm_properties;
	}

	public void setBpm_properties(Clob bpm_properties) throws Exception {
		/**
		 * 2019/07/15
		 */
		StringBuffer buffer = new StringBuffer();
		if (bpm_properties != null) {
			BufferedReader br = null;
			try {
				Reader r = bpm_properties.getCharacterStream();  //将Clob转化成流
				br = new BufferedReader(r);
				String s = null;
				while ((s = br.readLine()) != null) {
					buffer.append(s);
				}
			} catch (Exception e) {
				e.printStackTrace();	//打印异常信息
			} finally {
				if (br != null) {
					br.close(); //关闭流
				}
			}
		}
		this.bpm_properties = buffer.toString();
	}

	public String getUserFullName() {
		if(StringUtils.isNotBlank(parentTaskId)) {
			if(!StringUtils.equals(taskName, "撤回")) {
				userFullName = StringUtils.trimToEmpty(userFullName) + "（助审）";
			}
		}
		return StringUtils.trimToEmpty(userFullName) + (delegated != null && delegated == 1?"（委托审批）":"");
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
		
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		if(endTime != null) {
			return "已办结";
		}
		if(WorkflowConstant.TASK_STATUS_RESOLVING.equals(status)) {
			return "办理中";
		}else if(WorkflowConstant.TASK_STATUS_LOCKED.equals(status)) {
			return "已锁定";
		}if(WorkflowConstant.TASK_STATUS_RESOLVED.equals(status)) {
			return "已办结";
		}
		return "未接收";
	}

	public String getBpm_statusName() {
    	if(StringUtils.isNotBlank(bpm_result)) {
    		switch(bpm_result) {
            case WorkflowConstant.STATUS_DRAFTING: return "草稿";
            case WorkflowConstant.STATUS_RUNNING: return "审批中";
            case WorkflowConstant.STATUS_PASSED: return "审批通过";
            case WorkflowConstant.STATUS_REJECTED: return "审批驳回";
            case WorkflowConstant.STATUS_CLOSED: return "已关闭";
            default:break;
            }
    	}
        return null;
    }
	
	public String getDurationDH() {
		if(endTime == null || startTime == null) {
			return null;
		}
		long milliseconds = endTime.getTime() - startTime.getTime();
		return ConvertUtil.getDurationDH(milliseconds);
	}

	public String getDurationDHM() {
		if(endTime == null || startTime == null) {
			return null;
		}
		long milliseconds = endTime.getTime() - startTime.getTime();
		return ConvertUtil.getDurationDHM(milliseconds);
	}
	
	public String getDurationDHMS() {
		if(endTime == null || startTime == null) {
			return null;
		}
		long milliseconds = endTime.getTime() - startTime.getTime();
		return ConvertUtil.getDurationDHMS(milliseconds);
	}
	
	public String getWaitingDH() {
		if(endTime != null || startTime == null) {
			return null;
		}
		long milliseconds = new Date().getTime() - startTime.getTime();
		return ConvertUtil.getDurationDH(milliseconds);
	}
	
	public String getWaitingDHM() {
		if(endTime != null || startTime == null) {
			return null;
		}
		long milliseconds = new Date().getTime() - startTime.getTime();
		return ConvertUtil.getDurationDHM(milliseconds);
	}
	
	public String getWaitingDHMS() {
		if(endTime != null || startTime == null) {
			return null;
		}
		long milliseconds = new Date().getTime() - startTime.getTime();
		return ConvertUtil.getDurationDHMS(milliseconds);
	}
	
	public String getBpm_durationDH() {
		long milliseconds = bpm_startTime == null? 0 : ((bpm_endTime==null?new Date().getTime():bpm_endTime.getTime()) - bpm_startTime.getTime());
		return ConvertUtil.getDurationDH(milliseconds);
	}

	public String getBpm_durationDHM() {
		long milliseconds = bpm_startTime == null? 0 :((bpm_endTime==null?new Date().getTime():bpm_endTime.getTime()) - bpm_startTime.getTime());
		return ConvertUtil.getDurationDHM(milliseconds);
	}
	
	public String getStartDate() {
		if(startTime != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(startTime);
		}
		return null;
	}
}