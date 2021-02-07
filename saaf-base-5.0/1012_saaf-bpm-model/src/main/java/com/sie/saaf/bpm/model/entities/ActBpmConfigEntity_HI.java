package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "act_bpm_config")
public class ActBpmConfigEntity_HI {
	private Integer configId; // 主键
	private String processDefinitionKey;//流程定义KEY
	private String codingRule; //流程单号编码规则
	private String titleFormat; //流程标题格式化规则
	private String ccTitleFormat;  //抄送标题格式化规则
    private String ccContentFormat; //抄送内容格式化规则
    private String urgeTitleFormat;  //催办标题格式化规则
    private String urgeContentFormat; //催办内容格式化规则
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
	
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_CONFIG", sequenceName = "SEQ_ACT_BPM_CONFIG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_CONFIG", strategy = GenerationType.SEQUENCE)
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

    @Column(name = "coding_rule", nullable = false, length = 64)
    public String getCodingRule() {
		return codingRule;
	}

	public void setCodingRule(String codingRule) {
		this.codingRule = codingRule;
	}

	@Column(name = "title_format", nullable = false, length = 128)
	public String getTitleFormat() {
		return titleFormat;
	}

	public void setTitleFormat(String titleFormat) {
		this.titleFormat = titleFormat;
	}

	@Column(name = "cc_title_format", nullable = false, length = 128)
	public String getCcTitleFormat() {
		return ccTitleFormat;
	}

	public void setCcTitleFormat(String ccTitleFormat) {
		this.ccTitleFormat = ccTitleFormat;
	}

	@Column(name = "cc_content_format", nullable = false, length = 128)
	public String getCcContentFormat() {
		return ccContentFormat;
	}

	public void setCcContentFormat(String ccContentFormat) {
		this.ccContentFormat = ccContentFormat;
	}

	@Column(name = "urge_title_format", nullable = false, length = 128)
	public String getUrgeTitleFormat() {
		return urgeTitleFormat;
	}

	public void setUrgeTitleFormat(String urgeTitleFormat) {
		this.urgeTitleFormat = urgeTitleFormat;
	}

	@Column(name = "urge_content_format", nullable = false, length = 128)
	public String getUrgeContentFormat() {
		return urgeContentFormat;
	}

	public void setUrgeContentFormat(String urgeContentFormat) {
		this.urgeContentFormat = urgeContentFormat;
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

    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
	
	
}
