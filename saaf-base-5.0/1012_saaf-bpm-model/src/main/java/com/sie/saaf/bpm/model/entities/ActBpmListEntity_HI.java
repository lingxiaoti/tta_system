package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.bpm.constant.WorkflowConstant;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

import java.util.Date;


@Entity
@Table(name = "act_bpm_list")
public class ActBpmListEntity_HI{

	// Fields
	/**
	 * 
	 */
	private Integer listId;
	private String listCode;//申请编号
	private String listName;//流程名称
	private Integer status;//-1:未提交申请；0:审批中；1:审批结束
	private String result;//流程处理结果
	private String description;//申请理由
	private String businessKey;//业务主键
	private String billNo;//业务申请单号
	private String processDefinitionKey;//流程定义KEY
	private String processDefinitionId;//流程定义ID
	private String variables;//流程发起业务变量
	private String properties;//流程发起流程表单
	private String extend;//扩展属性
	private String title;//流程发起标题
	private String processInstanceId;//流程实例ID
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
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer categoryId;
    private Integer operatorUserId;


	// Constructors

	/** default constructor */
	public ActBpmListEntity_HI() {
	}

	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_LIST", sequenceName = "SEQ_ACT_BPM_LIST", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_LIST", strategy = GenerationType.SEQUENCE)
	@Column(name = "list_id", unique = true, nullable = false)
	public Integer getListId() {
		return this.listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	@Column(name = "list_code", nullable = true, length = 64)
	public String getListCode() {
		return this.listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode;
	}

	@Column(name = "list_name", nullable = true, length = 64)
	public String getListName() {
		return this.listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}
	
	@Column(name = "start_time", length = 23)
	public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", length = 23)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	@Column(name = "status", nullable = false, length = 11)
	public Integer getStatus() {
		return status;
	}

    public void setStatus(Integer status) {
		this.status = status;
	}

    @Column(name = "result", nullable = true, length = 16)
	public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "variables", nullable = true)
	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	@Column(name = "properties", nullable = true)
	public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
    
    @Column(name = "extend", nullable = true)
    public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

    @Column(name = "title", nullable = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	@Column(name = "description", nullable = true, length = 255)
	public String getDescription() {
		return description;
	}

    public void setDescription(String description) {
		this.description = description;
	}

    @Column(name = "business_key", nullable = true, length = 64)
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name = "bill_no", nullable = true, length = 128)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Column(name = "proc_def_key", nullable = true, length = 64)
	public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
    
    @Column(name = "proc_def_id", nullable = true, length = 64)
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

	@Column(name = "proc_inst_id", nullable = true, length = 64)
	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	@Column(name = "responsibility_id", nullable = true, length = 11)
	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	@Column(name = "org_id", nullable = true, length = 11)
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "user_type", nullable = true, length = 64)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "position_id", nullable = true, length = 11)
	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	
	@Column(name = "role_type", nullable = true, length = 64)
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Column(name = "cust_account_id", nullable = true, length = 11)
	public Integer getCustAccountId() {
		return custAccountId;
	}

	public void setCustAccountId(Integer custAccountId) {
		this.custAccountId = custAccountId;
	}
	
	@Column(name = "department_id", nullable = true, length = 11)
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	@Column(name = "apply_position_id", nullable = true, length = 11)
	public Integer getApplyPositionId() {
		return applyPositionId;
	}

	public void setApplyPositionId(Integer applyPositionId) {
		this.applyPositionId = applyPositionId;
	}

	@Column(name = "apply_person_id", nullable = true, length = 11)
	public Integer getApplyPersonId() {
		return applyPersonId;
	}

	public void setApplyPersonId(Integer applyPersonId) {
		this.applyPersonId = applyPersonId;
	}

	@Column(name = "created_by", nullable = true, length = 11)
    public Integer getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "creation_date", nullable = false, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "last_update_date", nullable = false, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }
    

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_updated_by", nullable = true, length = 11)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    

    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
    }
    
    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }
    
    @Column(name="delete_flag",columnDefinition="bit default 0")
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    
    @Column(name = "category_id", nullable = true, length = 11)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public String getStatusName() {
    	if(StringUtils.isNotBlank(result)) {
    		switch(result) {
            case WorkflowConstant.STATUS_DRAFTING: return "草稿";
            case WorkflowConstant.STATUS_RUNNING: return "审批中";
            case WorkflowConstant.STATUS_PASSED: return "审批通过";
            case WorkflowConstant.STATUS_REJECTED: return "审批驳回";
            case WorkflowConstant.STATUS_CLOSED: return "已关闭";
            default:break;
            }
    	}
    	if(status != null) {
    		switch(status) {
            case -1:return "草稿";
            case 0:return "审批中";
            case 1:return "审批通过";
            default:break;
            }
    	}
        return null;
    }

}