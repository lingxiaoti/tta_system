package com.sie.watsons.base.rule.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaBaseRuleLineEntity_HI Entity Object
 * Mon Aug 05 15:50:33 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_BASE_RULE_LINE")
public class TtaBaseRuleLineEntity_HI {
    private Integer ruleLineId;
    private Integer ruleId;
    private Integer qHeaderId;
    private String isEnable;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setRuleLineId(Integer ruleLineId) {
		this.ruleLineId = ruleLineId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_BASE_RULE_LINE", sequenceName="SEQ_TTA_BASE_RULE_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_BASE_RULE_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="rule_line_id", nullable=false, length=22)	
	public Integer getRuleLineId() {
		return ruleLineId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name="rule_id", nullable=false, length=22)	
	public Integer getRuleId() {
		return ruleId;
	}


	public void setqHeaderId(Integer qHeaderId) {
		this.qHeaderId = qHeaderId;
	}

	@Column(name="q_header_id", nullable=true, length=22)
	public Integer getqHeaderId() {
		return qHeaderId;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=true, length=2)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
