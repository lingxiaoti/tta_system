package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "act_bpm_task_processer")
public class ActBpmTaskProcesserEntity_HI {
	
	private Integer processerId;// 主键
	private Integer configId; // 任务节点配置外键
	private String processerIds;  //处理人ID，多个用逗号隔开
	private String processerRoleKeys; //处理人角色KEY，多个用逗号隔开
	private Integer sortId;//优先级
	private Integer disabled;//禁用
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer deleteFlag;
	private Integer operatorUserId;
	private String  userFullNames;
	private String roleNames;
	
	
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_TASK_PROCESSER", sequenceName = "SEQ_ACT_BPM_TASK_PROCESSER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_TASK_PROCESSER", strategy = GenerationType.SEQUENCE)
	@Column(name = "processer_id", nullable = false, length = 11)
	public Integer getProcesserId() {
		return processerId;
	}

	public void setProcesserId(Integer processerId) {
		this.processerId = processerId;
	}

	@Column(name = "config_id", nullable = false, length = 11)
	public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

	@Column(name = "processer_ids", nullable = true, length = 1024)
    public String getProcesserIds() {
        return processerIds;
    }

    public void setProcesserIds(String processerIds) {
        this.processerIds = processerIds;
    }

    @Column(name = "processer_role_keys", nullable = true, length = 1024)
    public String getProcesserRoleKeys() {
        return processerRoleKeys;
    }

    public void setProcesserRoleKeys(String processerRoleKeys) {
        this.processerRoleKeys = processerRoleKeys;
    }
    
    @Column(name="disabled",columnDefinition="bit default 0")
	public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
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
	public String getUserFullNames() {
		return userFullNames;
	}

	public void setUserFullNames(String userFullNames) {
		this.userFullNames = userFullNames;
	}

	@Transient
	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	
	
}
