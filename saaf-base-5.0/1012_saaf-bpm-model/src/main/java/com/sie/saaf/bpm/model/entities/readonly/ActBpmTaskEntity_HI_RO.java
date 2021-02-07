package com.sie.saaf.bpm.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.utils.ConvertUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class ActBpmTaskEntity_HI_RO{

	public static final String QUERY_ALL_TASK_SQL = "SELECT \n" +
			"\t task.id_ AS taskId,\n" +
			"\t task.rev_ AS rev,\n" +
			"\t task.execution_id_ AS executionId,\n" +
			"\t task.name_ AS taskName,\n" +
			"\t task.parent_task_id_ AS parentTaskId,\n" +
			"\t task.description_ AS description,\n" +
			"\t task.task_def_key_ AS taskDefinitionId,\n" +
			"\t bpm.proc_def_id AS processDefinitionId,\n" +
            "\t bpm.proc_inst_id AS processInstanceId,\n" +
			"\t task.owner_ AS owner,\n" +
			"\t task.assignee_ AS assignee,\n" +
			"\t task.create_time_ AS createTime,\n" +
			"\t task.category_ AS status,\n" +
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
            "\t bpm.proc_def_key AS processDefinitionKey, \n" +
            "\t bpm.proc_inst_id AS bpm_processInstanceId, \n" +
            "\t bpm.created_by AS bpm_createdBy,\n" +
            "\t bpm.creation_date AS bpm_creationDate,\n" +
            "\t bpm.last_update_date AS bpm_lastUpdateDate,\n" +
			"\t bpm.responsibility_id AS responsibilityId,\n" +
            "\t usr.user_name AS bpm_userName,\n" +
            "\t usr.user_full_name AS bpm_userFullName,\n" +
            "\t case when exists(select 1 from act_bpm_communicate cmc where cmc.task_id=task.id_ and cmc.type='COMMON') then 1 else 0 end AS bpm_communicated \n" +
            "\t from \n" +
            "\t act_ru_task task \n" +
            "\t left join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
            "\t left join act_bpm_list bpm on (bpm.proc_inst_id = leavel.top_proc_inst_id)\n" +
            "\t left join base_users usr on bpm.created_by = usr.user_id\n" +
            "\t left join act_bpm_category cat on bpm.category_id = cat.category_id\n" +
            "\t where\n" +
            "\t task.suspension_state_=1 \n" + 
	        "\t and not exists(select 1 from act_ru_task sub where sub.parent_task_id_=task.id_ and sub.suspension_state_=1)\n";

	public static final String QUERY_ALL_TASK_COUNT_SQL = "select count(*)" +
			"\t from \n" +
			"\t act_ru_task task \n" +
			"\t left join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
			"\t left join act_bpm_list bpm on (bpm.proc_inst_id = leavel.top_proc_inst_id)\n" +
			"\t left join base_users usr on bpm.created_by = usr.user_id\n" +
			"\t left join act_bpm_category cat on bpm.category_id = cat.category_id\n" +
			"\t where\n" +
			"\t task.suspension_state_=1 \n" +
			"\t and not exists(select 1 from act_ru_task sub where sub.parent_task_id_=task.id_ and sub.suspension_state_=1)\n";


	public static final String QUERY_AUTO_ASSIGNEE_SQL = "SELECT ART.ID_       taskId\n" +
			"      ,BU2.USER_NAME userId\n" +
			"      ,BU.USER_NAME  currentUserId\n" +
			"FROM   act_bpm_category cate\n" +
			"      ,(SELECT DISTINCT regexp_substr(BBV.CATEGORY_IDS, '[^,]+', 1, LEVEL) CATEGORY_ID\n" +
			"                       ,BBV.client_user_id\n" +
			"                       ,BBV.delegate_user_id\n" +
			"        FROM   (SELECT A.CATEGORY_IDS\n" +
			"                      ,a.client_user_id\n" +
			"                      ,a.delegate_user_id\n" +
			"                FROM   act_bpm_task_delegate_config A\n" +
			"                WHERE  a.delete_flag = 0\n" +
			"                AND    a.start_time < SYSDATE\n" +
			"                AND    a.end_time + 1 > SYSDATE) BBV\n" +
			"        CONNECT BY regexp_substr(BBV.CATEGORY_IDS, '[^,]+', 1, LEVEL) IS NOT NULL) abtd\n" +
			"      ,act_bpm_list ABC\n" +
			"      ,act_ru_task ART\n" +
			"      ,BASE_USERS BU\n" +
			"      ,BASE_USERS BU2\n" +
			"WHERE  abtd.CATEGORY_ID = cate.CATEGORY_ID\n" +
			"AND    cate.delete_flag = 0\n" +
			"AND    ABC.LIST_NAME = cate.Category_Name\n" +
			"AND    ART.PROC_INST_ID_ = ABC.PROC_INST_ID\n" +
			"AND    BU.USER_ID = abtd.client_user_id\n" +
			"AND    BU.USER_NAME = ART.ASSIGNEE_\n" +
			"AND    BU2.USER_ID = abtd.delegate_user_id ";


	/**
	 * 查询单个任务
	 */
	public static final String QUERY_SINGLE_TASK_SQL = "SELECT \n" +
			"\t task.id_ AS taskId,\n" +
			"\t task.rev_ AS rev,\n" +
			"\t task.execution_id_ AS executionId,\n" +
			"\t task.name_ AS taskName,\n" +
			"\t task.parent_task_id_ AS parentTaskId,\n" +
			"\t task.description_ AS description,\n" +
			"\t task.task_def_key_ AS taskDefinitionId,\n" +
			"\t bpm.proc_def_id AS processDefinitionId,\n" +
			"\t bpm.proc_inst_id AS processInstanceId,\n" +
			"\t task.owner_ AS owner,\n" +
			"\t task.assignee_ AS assignee,\n" +
			"\t task.create_time_ AS createTime,\n" +
			"\t task.category_ AS status,\n" +
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
			"\t bpm.proc_def_key AS processDefinitionKey, \n" +
			"\t bpm.proc_inst_id AS bpm_processInstanceId, \n" +
			"\t bpm.created_by AS bpm_createdBy,\n" +
			"\t bpm.creation_date AS bpm_creationDate,\n" +
			"\t bpm.last_update_date AS bpm_lastUpdateDate,\n" +
			"\t bpm.responsibility_id AS responsibilityId \n" +
			"\t from \n" +
			"\t act_ru_task task \n" +
			"\t left join act_bpm_task_leavel leavel on leavel.task_id = task.id_\n" +
			"\t left join act_bpm_list bpm on bpm.proc_inst_id = leavel.top_proc_inst_id\n" +
			"\t where\n" +
			"\t 1=1 \n";


	private String taskId;
	private Integer rev;
	private String executionId;
	private String taskName;
	private String parentTaskId;
	private String description;
	private String taskDefinitionId;
	private String owner;
	private String assignee;
	private String status;
	private String userId;
	private String currentUserId;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
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
	private String bpm_processInstanceId;//流程实例ID
	private String processDefinitionKey;//流程定义KEY
	private String processDefinitionId;//流程定义ID
	private String processInstanceId;//流程实例ID
	private Integer responsibilityId;//职责ID
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



	// Constructors

	/** default constructor */
	public ActBpmTaskEntity_HI_RO() {
	}

    public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getRev() {
		return rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getTaskName() {
		if(StringUtils.isNotBlank(parentTaskId)) {
			if(taskName != null && taskName.endsWith("（助审）")) {
				return taskName + "（助审）";
			}
		}
		return taskName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getStatus() {
		if(WorkflowConstant.TASK_STATUS_RESOLVING.equals(status)) {
			return "办理中";
		}else if(WorkflowConstant.TASK_STATUS_LOCKED.equals(status)) {
			return "已锁定";
		}
		return "未接收";
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusCode() {
		return StringUtils.isNotBlank(status)?status: WorkflowConstant.TASK_STATUS_PENDING;
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

	public String getBpm_processInstanceId() {
		return bpm_processInstanceId;
	}

	public void setBpm_processInstanceId(String bpm_processInstanceId) {
		this.bpm_processInstanceId = bpm_processInstanceId;
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

	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
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
		long milliseconds = createTime == null? 0 : (new Date().getTime() - createTime.getTime());
		return ConvertUtil.getDurationDH(milliseconds);
	}

	public String getDurationDHM() {
		long milliseconds = createTime == null? 0 : (new Date().getTime() - createTime.getTime());
		return ConvertUtil.getDurationDHM(milliseconds);
	}
	
	public String getBpm_durationDH() {
		long milliseconds = bpm_startTime == null? 0 : ((bpm_endTime==null?new Date().getTime():bpm_endTime.getTime()) - bpm_startTime.getTime());
		return ConvertUtil.getDurationDH(milliseconds);
	}

	public String getBpm_durationDHM() {
		long milliseconds = bpm_startTime == null? 0 :((bpm_endTime==null?new Date().getTime():bpm_endTime.getTime()) - bpm_startTime.getTime());
		return ConvertUtil.getDurationDHM(milliseconds);
	}


}