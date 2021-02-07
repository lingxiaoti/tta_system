package com.sie.saaf.bpm.model.entities.readonly;

public class ActBpmTaskConfigEntity_HI_RO {
	
	public static final String QUERY_ALL_CONFIG_URL_SQL = "SELECT \n" +
            "\t cfg.config_id AS configId,\n" +
            "\t cfg.proc_def_key AS processDefinitionKey,\n" +
            "\t cfg.task_def_id AS taskDefinitionId,\n" +
            "\t cfg.task_name AS taskName,\n" +
            "\t cfg.pcform_url AS pcformUrl,\n" +
            "\t cfg.mobform_url AS mobformUrl,\n" +
            "\t cfg.callback_url AS callbackUrl,\n" +
            "\t cfg.pc_data_url AS pcDataUrl,\n" +
            "\t cfg.mob_data_url AS mobDataUrl,\n" +
            "\t cfg.edit_status AS editStatus \n" +
            " FROM \n" +
            "\t act_bpm_task_config cfg \n" +
            "\t WHERE\n" +
            "\t 1=1\n";
	
	private Integer configId; // 主键
	private String processDefinitionKey;//流程定义KEY
	private String taskDefinitionId; // 任务定义Key
	private String taskName;  //任务名称
	private String pcformUrl; //PC端URL
	private String mobformUrl;//移动端URL
	private String processerIds;  //处理人ID，多个用逗号隔开
	private String processerRoleKeys; //处理人角色KEY，多个用逗号隔开
	private String ccIds;  //抄送人ID，多个用逗号隔开
    private String ccRoleKeys; //抄送人角色KEY，多个用逗号隔开
    private String variables;//变量
    private String extend;//扩展属性
    private String callbackUrl;//回调服务
    private String pcDataUrl;//PC端数据URL
    private String mobDataUrl;//移动端数据URL
    private Integer editStatus;//0.只读  1.可编辑
	
	public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPcformUrl() {
        return pcformUrl;
    }

    public void setPcformUrl(String pcformUrl) {
        this.pcformUrl = pcformUrl;
    }

    public String getMobformUrl() {
        return mobformUrl;
    }

    public void setMobformUrl(String mobformUrl) {
        this.mobformUrl = mobformUrl;
    }
    
    public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getPcDataUrl() {
		return pcDataUrl;
	}

	public void setPcDataUrl(String pcDataUrl) {
		this.pcDataUrl = pcDataUrl;
	}

	public String getMobDataUrl() {
		return mobDataUrl;
	}

	public void setMobDataUrl(String mobDataUrl) {
		this.mobDataUrl = mobDataUrl;
	}

	public String getProcesserIds() {
        return processerIds;
    }

    public void setProcesserIds(String processerIds) {
        this.processerIds = processerIds;
    }

    public String getProcesserRoleKeys() {
        return processerRoleKeys;
    }

    public void setProcesserRoleKeys(String processerRoleKeys) {
        this.processerRoleKeys = processerRoleKeys;
    }

    public String getCcIds() {
        return ccIds;
    }

    public void setCcIds(String ccIds) {
        this.ccIds = ccIds;
    }

    public String getCcRoleKeys() {
        return ccRoleKeys;
    }

    public void setCcRoleKeys(String ccRoleKeys) {
        this.ccRoleKeys = ccRoleKeys;
    }
    
    public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

    public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}	

    public Integer getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}

	
}
