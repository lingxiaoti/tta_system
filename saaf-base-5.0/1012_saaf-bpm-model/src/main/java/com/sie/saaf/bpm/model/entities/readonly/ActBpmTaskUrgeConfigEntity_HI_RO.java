package com.sie.saaf.bpm.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ActBpmTaskUrgeConfigEntity_HI_RO {

	public static final String QUERY_ALL_CONFIG_SQL = "select \n" +
			"\t cfg.config_id as configId,\n" +
			"\t cat.process_key as processKey,\n" +
			"\t cfg.proc_def_key as processDefinitionKey,\n" +
			"\t cfg.start_time as startTime,\n" +
			"\t cfg.end_time as endTime,\n" +
			"\t cfg.timeout as timeout,\n" +
			"\t cfg.timegap as timegap,\n" +
			"\t cat.category_id as categoryId,\n" +
			"\t cat.category_name as categoryName,\n" +
			"\t modell.name_ as processName,\n" +
			"\t cfg.disabled as disabled,\n" +
			//ora-00904 wm_concat没有此函数，需要更换方式
			// "\t (select wm_concat((perm.ou_id || ':' || lu.meaning)) as ouIds from act_bpm_permission perm left join base_lookup_values lu on lu.lookup_code=perm.ou_id and lu.lookup_type='BASE_OU' where perm.proc_def_key=model.key_   group by proc_def_key) \n" +
			"\t (select system_code from act_bpm_permission where proc_def_key=modell.key_ and rownum = 1) as  systemCode \n" +
			"\t from \n" +
			"\t act_bpm_task_urge_config cfg \n" +
			"\t left join act_re_model modell on modell.key_ = cfg.proc_def_key \n" +
			"\t left join act_bpm_category cat on cat.category_id = modell.category_ \n" +
			"\t where\n" +
			"\t 1=1\n";

	// Fields

	private Integer configId;
	@JSONField(format = "yyyy-MM-dd")
	private Date startTime;
	@JSONField(format = "yyyy-MM-dd")
	private Date endTime;
	private String processKey;
	private String processDefinitionKey;
	private Integer timeout;
	private Integer timegap;
	private Integer disabled;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer deleteFlag;
	private Integer categoryId;
	private String categoryName;
	private String processName;
	private String ouIds;
	private String systemCode;

	public ActBpmTaskUrgeConfigEntity_HI_RO() {
	}

	public Integer getConfigId() {
		return this.configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getTimegap() {
		return timegap;
	}

	public void setTimegap(Integer timegap) {
		this.timegap = timegap;
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

	public Integer getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
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

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStartToEnd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(startTime) + " —— " + sdf.format(endTime);
	}

	public List<String> getOuIds() {
		List<String> ous = StringUtils.isBlank(ouIds) ? null : Arrays.asList(ouIds.split(","));
		if(ous != null && !ous.isEmpty()){
			for(int i = 0; i < ous.size(); i++){
				ous.set(i,ous.get(i).replaceAll(":.*",""));
			}
		}
		return ous;
	}

	public List<String> getOuNames() {
		List<String> ous = StringUtils.isBlank(ouIds) ? null : Arrays.asList(ouIds.split(","));
		if(ous != null && !ous.isEmpty()){
			for(int i = 0; i < ous.size(); i++){
				ous.set(i,ous.get(i).replaceAll("[\\d]*:",""));
			}
		}
		return ous;
	}

	public void setOuIds(String ouIds) {
		this.ouIds = ouIds;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public boolean getEnabled(){
		if(disabled == 1){
			return false;
		}else{
			return true;
		}
	}

}