package com.sie.saaf.bpm.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.utils.ConvertUtil;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.Date;

public class ActBpmListEntity_HI_RO{

	public static final String QUERY_ALL_BPMLIST_SQL = "SELECT \n" +
            "\t bpm.list_id AS listId,\n" +
            "\t bpm.list_code AS listCode,\n" +
            "\t bpm.list_name AS listName,\n" +
            "\t bpm.category_id AS categoryId,\n" +
            "\t cat.category_name AS categoryName,\n" +
            "\t bpm.start_time AS startTime,\n" +
            "\t bpm.end_time AS endTime,\n" +
            "\t bpm.status AS status, \n" +
            "\t bpm.result AS result,\n" +
            "\t bpm.title AS title, \n" +
            "\t bpm.description AS description,\n" +
            "\t bpm.business_key AS businessKey,\n" +
			"\t bpm.bill_no AS billNo, \n" +
			"\t bpm.properties AS properties, \n" +
            "\t bpm.proc_def_key AS processDefinitionKey,\n" +
            "\t bpm.proc_def_id AS processDefinitionId,\n" +
			"\t bpm.proc_inst_id AS processInstanceId,\n" +
            "\t bpm.position_id AS positionId,\n" +
			"\t bpm.responsibility_id AS responsibilityId,\n" +
			"\t bpm.org_id AS orgId,\n" +
			"\t bpm.user_type AS userType,\n" +
			"\t bpm.role_type AS roleType,\n" +
			"\t bpm.cust_account_id AS custAccountId,\n" +
			"\t bpm.department_id AS departmentId,\n" +
			"\t bpm.apply_position_id AS applyPositionId,\n" +
			"\t bpm.apply_person_id AS applyPersonId,\n" +
            "\t bpm.created_by AS createdBy,\n" +
            "\t bpm.creation_date AS creationDate,\n" +
            "\t bpm.last_update_date AS lastUpdateDate,\n" +
            "\t usr.user_name AS userName,\n" +
            "\t usr.user_full_name AS userFullName,\n" +
            //"\t exists(select 1 from act_bpm_communicate cmc where cmc.proc_inst_id=bpm.proc_inst_id and cmc.type='COMMON' and cmc.delete_flag=0) AS communicated, \n" +
            //"\t exists(select 1 from act_bpm_exception exc where exc.proc_inst_id=bpm.proc_inst_id and exc.status=0 and exc.delete_flag=0) AS exceptional \n" +
			"\tnvl(decode((select 1 from act_bpm_communicate cmc where cmc.proc_inst_id=bpm.proc_inst_id and cmc.type='COMMON' and cmc.delete_flag=0 AND ROWNUM<=1), null, 0, 1) , 0) as communicated,\n" +
			"\tnvl(decode((select 1 from act_bpm_exception exc where exc.proc_inst_id=bpm.proc_inst_id and exc.status=0 and exc.delete_flag=0 AND ROWNUM<=1 ), null, 0, 1),0) as exceptional\n" +
			" FROM \n" +
            "\t act_bpm_list bpm \n" +
            "\tLEFT JOIN base_users usr ON bpm.created_by = usr.user_id \n" +
            "\tLEFT JOIN act_bpm_category cat ON bpm.category_id = cat.category_id \n" +
            "\tWHERE\n" +
            "\t 1=1\n";

	public static final String QUERY_ALL_BPMLIST_COUNT_SQL = "select count(*)" +
			"\t FROM act_bpm_list bpm \n" +
			"\tLEFT JOIN base_users usr ON bpm.created_by = usr.user_id \n" +
			"\tLEFT JOIN act_bpm_category cat ON bpm.category_id = cat.category_id \n" +
			"\tWHERE\n" +
			"\t 1=1\n";
	
	private Integer listId;
	private String listCode;//申请编号
	private String listName;//流程名称
	private String title;//流程发起标题
	private Integer categoryId;//流程分类ID
	private String categoryName;//流程分类名称
	private Integer status;//-1:未提交申请；0:审批中；1:审批结束
	private String result;//流程处理结果
	private String description;//申请理由
	private String businessKey;//业务主键
	private String billNo;//业务申请单号
	private String processDefinitionKey;//流程定义KEY
	private String processDefinitionId;//流程定义ID
	private String processInstanceId;//流程实例ID
	private String properties;//流程发起流程表单
	private Integer positionId;//职位ID
	private Integer responsibilityId;//职责ID
	private Integer orgId;//部门ID
	private String userType;//用户类型
	private String roleType;//类型(用于判别经销商)
	private Integer custAccountId;//经销商ID
	private Integer departmentId;//部门ID
	private Integer applyPositionId;//职位ID
	private Integer applyPersonId;//员工ID
	private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//流程开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//流程结束时间
    private String userName;
    private String userFullName;
    private Integer communicated; //是否发起沟通 1.是 0.否   
    private Integer exceptional; //是否异常 1.是 0.否



	// Constructors

	/** default constructor */
	public ActBpmListEntity_HI_RO() {
	}

    public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public String getListCode() {
		return listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
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

	public String getProperties() {
		return properties;
	}

	public void setProperties(Clob properties) throws Exception{
		/**
		 * 2019/07/15
		 */
		StringBuffer buffer = new StringBuffer();
		if (properties != null) {
			BufferedReader br = null;
			try {
				Reader r = properties.getCharacterStream();  //将Clob转化成流
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
		this.properties = buffer.toString();
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Integer getCommunicated() {
		return communicated;
	}

	public void setCommunicated(Integer communicated) {
		this.communicated = communicated;
	}

	public Integer getExceptional() {
		return exceptional;
	}

	public void setExceptional(Integer exceptional) {
		this.exceptional = exceptional;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer getCustAccountId() {
		return custAccountId;
	}

	public void setCustAccountId(Integer custAccountId) {
		this.custAccountId = custAccountId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getApplyPositionId() {
		return applyPositionId;
	}

	public void setApplyPositionId(Integer applyPositionId) {
		this.applyPositionId = applyPositionId;
	}

	public Integer getApplyPersonId() {
		return applyPersonId;
	}

	public void setApplyPersonId(Integer applyPersonId) {
		this.applyPersonId = applyPersonId;
	}

	public String getStatusName() {
    	if(StringUtils.isNotBlank(result)) {
    		switch(result) {
            case WorkflowConstant.STATUS_DRAFTING: return "草稿";
            case WorkflowConstant.STATUS_RUNNING: return "审批中";
            case WorkflowConstant.STATUS_PASSED: return "审批通过";
            case WorkflowConstant.STATUS_REJECTED: return "审批驳回";
            case WorkflowConstant.STATUS_CLOSED: return "已关闭";
            case WorkflowConstant.STATUS_REVOKE: return "已撤回";
            default:break;
            }
    	}
        return null;
    }
	
	public String getDurationDH() {
		long milliseconds = startTime == null? 0 : ((endTime==null?new Date().getTime():endTime.getTime()) - startTime.getTime());
		return ConvertUtil.getDurationDH(milliseconds);
	}

	public String getDurationDHM() {
		long milliseconds = startTime == null? 0 :((endTime==null?new Date().getTime():endTime.getTime()) - startTime.getTime());
		return ConvertUtil.getDurationDHM(milliseconds);
	}
}