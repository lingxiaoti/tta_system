package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "act_bpm_task_config")
public class ActBpmTaskConfigEntity_HI {
	
	private Integer configId; // 主键
	private String processDefinitionKey;//流程定义KEY
	private String taskDefinitionId; // 任务定义Key
	private String taskName;  //任务名称
	private String pcformUrl; //PC端URL
	private String mobformUrl;//移动端URL
	private String ccIds;  //抄送人ID，多个用逗号隔开
    private String ccRoleKeys; //抄送人角色KEY，多个用逗号隔开
    private String variables;//变量
    private String extend;//扩展属性
    private String callbackUrl;//回调服务
    private String pcDataUrl;//PC端数据URL
    private String mobDataUrl;//移动端数据URL
    private Integer editStatus;//0.只读  1.可编辑
    private Integer enableAutoJump;//允许自动跳过
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer deleteFlag;
	private Integer sortId;
	private Integer operatorUserId;
	private String  ccUserFullNames;
	private String ccRoleNames;
	private String processDefinitionId;
	private List<ActBpmTaskProcesserEntity_HI> processers;
		
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_TASK_CONFIG", sequenceName = "SEQ_ACT_BPM_TASK_CONFIG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_TASK_CONFIG", strategy = GenerationType.SEQUENCE)
	@Column(name = "config_id", nullable = false, length = 11)
	public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    @Column(name = "proc_def_key", nullable = false, length = 64)
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    @Column(name = "task_def_id", nullable = false, length = 64)
    public String getTaskDefinitionId() {
        return taskDefinitionId;
    }

    public void setTaskDefinitionId(String taskDefinitionId) {
        this.taskDefinitionId = taskDefinitionId;
    }

    @Column(name = "task_name", nullable = true, length = 255)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "pcform_url", nullable = true, length = 255)
    public String getPcformUrl() {
        return pcformUrl;
    }

    public void setPcformUrl(String pcformUrl) {
        this.pcformUrl = pcformUrl;
    }

    @Column(name = "mobform_url", nullable = true, length = 255)
    public String getMobformUrl() {
        return mobformUrl;
    }

    public void setMobformUrl(String mobformUrl) {
        this.mobformUrl = mobformUrl;
    }
    
    @Column(name = "pc_data_url", nullable = true, length = 255)
    public String getPcDataUrl() {
		return pcDataUrl;
	}

	public void setPcDataUrl(String pcDataUrl) {
		this.pcDataUrl = pcDataUrl;
	}

	 @Column(name = "mob_data_url", nullable = true, length = 255)
	public String getMobDataUrl() {
		return mobDataUrl;
	}

	public void setMobDataUrl(String mobDataUrl) {
		this.mobDataUrl = mobDataUrl;
	}

    @Column(name = "cc_ids", nullable = true, length = 255)
    public String getCcIds() {
        return ccIds;
    }

    public void setCcIds(String ccIds) {
        this.ccIds = ccIds;
    }

    @Column(name = "cc_role_keys", nullable = true, length = 255)
    public String getCcRoleKeys() {
        return ccRoleKeys;
    }

    public void setCcRoleKeys(String ccRoleKeys) {
        this.ccRoleKeys = ccRoleKeys;
    }
    
    @Column(name = "variables", nullable = true)
    public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	@Column(name = "edit_status", nullable = true, length = 11)
	public Integer getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}
	
	@Column(name = "enable_autoJump", nullable = true, length = 11)
	public Integer getEnableAutoJump() {
		return enableAutoJump;
	}

	public void setEnableAutoJump(Integer enableAutoJump) {
		this.enableAutoJump = enableAutoJump;
	}

	@Column(name = "extend", nullable = true)
    public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
	
	@Column(name = "callback_url", nullable = true)
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
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

    @Column(name = "sort_id", nullable = true, length = 11)
    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}	

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
	
	@Transient
	public String getCcUserFullNames() {
		return ccUserFullNames;
	}

	public void setCcUserFullNames(String ccUserFullNames) {
		this.ccUserFullNames = ccUserFullNames;
	}

	@Transient
	public String getCcRoleNames() {
		return ccRoleNames;
	}

	public void setCcRoleNames(String ccRoleNames) {
		this.ccRoleNames = ccRoleNames;
	}

	@Transient
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Transient
	public List<ActBpmTaskProcesserEntity_HI> getProcessers() {
		return processers;
	}

	public void setProcessers(List<ActBpmTaskProcesserEntity_HI> processers) {
		this.processers = processers;
	}

	@Transient
	public JSONArray getVariablesJSON(){
		JSONArray variablesJSON = new JSONArray();
		if(StringUtils.isNotBlank(variables)) {
			variablesJSON = JSON.parseArray(variables);
		}
		return variablesJSON;
	}

	@Transient
	public JSONArray getEmptyVariablesJSON(){
		JSONArray variablesJSON = new JSONArray();
		if(StringUtils.isNotBlank(variables)) {
			variablesJSON = JSON.parseArray(variables);
			for(int i = variablesJSON.size()-1; i>=0; i--) {
				JSONObject variable = variablesJSON.getJSONObject(i);
				if(StringUtils.isNotBlank(variable.getString("value"))) {
					variablesJSON.remove(i);
				}
			}
		}
		return variablesJSON;
	}

	
}
