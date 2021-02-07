package com.sie.watsons.base.cost.model.entities;

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
 * TtaActualCostRuleEntity_HI Entity Object
 * Tue Jun 04 18:06:35 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_actual_cost_rule")
public class TtaActualCostRuleEntity_HI {
    private Integer id;
    private String oiType;
    private String scenario;
    private String oiBucket;
    private String isAppointDetail;
    private String ruleSourceData;
    private String allocationBase;
	private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_ACTUAL_COST_RULE", sequenceName = "SEQ_TTA_ACTUAL_COST_RULE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_ACTUAL_COST_RULE", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	@Column(name="oi_type", nullable=false, length=100)	
	public String getOiType() {
		return oiType;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	@Column(name="scenario", nullable=true, length=100)	
	public String getScenario() {
		return scenario;
	}

	public void setOiBucket(String oiBucket) {
		this.oiBucket = oiBucket;
	}

	@Column(name="oi_bucket", nullable=true, length=100)	
	public String getOiBucket() {
		return oiBucket;
	}

	public void setIsAppointDetail(String isAppointDetail) {
		this.isAppointDetail = isAppointDetail;
	}

	@Column(name="is_appoint_detail", nullable=true, length=2)	
	public String getIsAppointDetail() {
		return isAppointDetail;
	}

	public void setRuleSourceData(String ruleSourceData) {
		this.ruleSourceData = ruleSourceData;
	}

	@Column(name="rule_source_data", nullable=true, length=200)	
	public String getRuleSourceData() {
		return ruleSourceData;
	}

	public void setAllocationBase(String allocationBase) {
		this.allocationBase = allocationBase;
	}

	@Column(name="allocation_base", nullable=false, length=100)	
	public String getAllocationBase() {
		return allocationBase;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=2)
	public String getStatus() {
		return this.status;
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
