package com.sie.watsons.base.cost.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaActualCostRuleEntity_HI_RO Entity Object
 * Tue Jun 04 18:06:35 CST 2019  Auto Generate
 */

public class TtaActualCostRuleEntity_HI_RO {

	public static final  String QUERY_COST_RULE  ="select t.id,\n" +
			"t.oi_type,\n" +
			"t.scenario,\n" +
			"t.oi_bucket,\n" +
			"t.status,\n" +
			"t.is_appoint_detail,\n" +
			"t.rule_source_data,\n" +
			"t.allocation_base,\n" +
			"t.creation_date,\n" +
			"t.created_by,\n" +
			"t.last_updated_by,\n" +
			"t.last_update_date,\n" +
			"t.last_update_login,\n" +
			"t.version_num from tta_actual_cost_rule  t where 1 = 1";

    private Integer id;
    private String oiType;
    private String scenario;
    private String oiBucket;
    private String isAppointDetail;
    private String ruleSourceData;
    private String allocationBase;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String status;

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	
	public String getOiType() {
		return oiType;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	
	public String getScenario() {
		return scenario;
	}

	public void setOiBucket(String oiBucket) {
		this.oiBucket = oiBucket;
	}

	
	public String getOiBucket() {
		return oiBucket;
	}

	public void setIsAppointDetail(String isAppointDetail) {
		this.isAppointDetail = isAppointDetail;
	}

	
	public String getIsAppointDetail() {
		return isAppointDetail;
	}

	public void setRuleSourceData(String ruleSourceData) {
		this.ruleSourceData = ruleSourceData;
	}

	
	public String getRuleSourceData() {
		return ruleSourceData;
	}

	public void setAllocationBase(String allocationBase) {
		this.allocationBase = allocationBase;
	}

	
	public String getAllocationBase() {
		return allocationBase;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
