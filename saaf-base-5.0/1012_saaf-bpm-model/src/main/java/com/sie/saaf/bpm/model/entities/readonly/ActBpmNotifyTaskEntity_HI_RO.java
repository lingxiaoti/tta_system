package com.sie.saaf.bpm.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.utils.ConvertUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class ActBpmNotifyTaskEntity_HI_RO{

	public static final String QUERY_ALL_NOTIFY_SQL = "SELECT \n" +
			"\t noti.notify_id AS notifyId,\n" +
			"\t noti.title AS title,\n" +
			"\t noti.content AS content,\n" +
			"\t noti.proc_inst_id AS processInstanceId,\n" +
			"\t noti.proc_def_key AS processDefinitionKey,\n" +
			"\t noti.task_id AS taskId,\n" +
			"\t noti.task_name AS taskName,\n" +
			"\t noti.receiver_id AS receiverId,\n" +
            "\t noti.read_time AS readTime,\n" +
			"\t noti.created_by AS createdBy,\n" +
			"\t noti.creation_date AS creationDate,\n" +
			"\t noti.last_update_date AS lastUpdateDate,\n" +
			"\t noti.status AS status, \n" +
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
            // "\t bpm.proc_def_key AS processDefinitionKey, \n" +
            "\t bpm.created_by AS bpm_createdBy,\n" +
            "\t bpm.creation_date AS bpm_creationDate,\n" +
            "\t bpm.last_update_date AS bpm_lastUpdateDate,\n" +
            "\t usr.user_name AS bpm_userName,\n" +
            "\t usr.user_full_name AS bpm_userFullName,\n" +
            //"\t exists(select 1 from act_bpm_communicate cmc where cmc.proc_inst_id=bpm.proc_inst_id and cmc.type='COMMON') AS bpm_communicated \n" +
			"\t nvl(decode((select 1 from act_bpm_communicate cmc where cmc.proc_inst_id=bpm.proc_inst_id and cmc.type='COMMON'), null, 0, 1) , 0) as bpm_communicated \n" +
            " FROM \n" +
            "\t act_bpm_notify_task noti \n" +
            "\t LEFT JOIN act_bpm_list bpm ON bpm.proc_inst_id = noti.proc_inst_id\n" +
            "\t LEFT JOIN base_users usr ON bpm.created_by = usr.user_id\n" +
            "\t LEFT JOIN act_bpm_category cat ON bpm.category_id = cat.category_id\n" +
            "\t WHERE\n" +
            "\t 1=1";

	public static final String QUERY_ALL_NOTIFY_COUNT_SQL = "SELECT count(*)\n" +
			" FROM \n" +
			"\t act_bpm_notify_task noti \n" +
			"\t LEFT JOIN act_bpm_list bpm ON bpm.proc_inst_id = noti.proc_inst_id\n" +
			"\t LEFT JOIN base_users usr ON bpm.created_by = usr.user_id\n" +
			"\t LEFT JOIN act_bpm_category cat ON bpm.category_id = cat.category_id\n" +
			"\t WHERE\n" +
			"\t 1=1";
	
	private Integer notifyId;
	private String title;
	private String content;
	private String processInstanceId;
	private String processDefinitionKey;
	private String taskId;
	private String taskName;
	private Integer receiverId;
	private Integer status;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date readTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
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
	public ActBpmNotifyTaskEntity_HI_RO() {
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	
	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
    
    public String getStatusName() {
    	if(status != null && status==1) {
    		return "已读";
    	}else {
    		return "未读";
    	}
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
		long milliseconds = creationDate == null? 0 : (new Date().getTime() - creationDate.getTime());
		return ConvertUtil.getDurationDH(milliseconds);
	}

	public String getDurationDHM() {
		long milliseconds = creationDate == null? 0 : (new Date().getTime() - creationDate.getTime());
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