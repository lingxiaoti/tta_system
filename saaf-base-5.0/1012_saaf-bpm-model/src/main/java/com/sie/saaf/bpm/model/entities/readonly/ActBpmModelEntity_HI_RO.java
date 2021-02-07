package com.sie.saaf.bpm.model.entities.readonly;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ActBpmModelEntity_HI_RO {

	public static final String QUERY_ALL_MODEL_SQL = "select \n" +
			"\t model.id_ as modelId,\n" +
			"\t model.rev_ as version,\n" +
			"\t model.name_ as name,\n" +
			"\t model.key_ as key,\n" +
			"\t model.category_ as categoryId,\n" +
			"\t model.create_time_ as createTime,\n" +
			"\t model.last_update_time_ as lastUpdateTime,\n" +
            "\t model.meta_info_ as metaInfo,\n" +
			"\t model.tenant_id_ as tenantId,\n" +
			"\t cate.category_name as categoryName,\n" +
			"\t cate.process_key as processKey,\n" +
			//"\t (case when exists(select 1 from act_re_procdef where key_=model.key_ and suspension_state_=1)=1 then 'true' else 'false' end) as status,\n" +
			//"\t (select dep.deploy_time_ from act_re_procdef def inner join act_re_deployment dep on def.deployment_id_=dep.id_ where def.key_=model.key_ and def.suspension_state_=1 order by dep.deploy_time_ desc limit 1) as deploymentTime,\n" +
			//ora-00904 wm_concat没有此函数，需要更换方式
			//  "\t (select wm_concat(perm.ou_id || ':' ||lu.meaning))  from act_bpm_permission perm left join base_lookup_values lu on lu.lookup_code=perm.ou_id and lu.lookup_type='BASE_OU' where perm.proc_def_key=model.key_  group by proc_def_key limit 1) as  ouIds,\n" +
			"\t nvl(decode((select 1 from act_re_procdef where key_=model.key_ and suspension_state_=1 and rownum = 1), null, 'false', 'true'), 'false') as status,\n" +
			"\t (select deploy_time_ from (select dep.deploy_time_ from act_re_procdef def inner join act_re_deployment dep on def.deployment_id_=dep.id_ where def.key_=model.key_ and def.suspension_state_=1  group by deploy_time_ order by dep.deploy_time_ desc) t where rownum = 1) as deploymentTime,\n" +
			//"\t (select system_code from act_bpm_permission where proc_def_key=model.key_ limit 1) as  systemCode \n" +
			"\t (select system_code from act_bpm_permission where proc_def_key=model.key_ and  rownum =  1) as  systemCode \n" +
			"\t from act_re_model model\n" +
			"\t left join act_bpm_category cate on cate.category_id=model.category_\n" +
			"\t where 1=1 ";

	public static final String QUERY_ALL_MODEL_COUNT_SQL = "select count(*) \n" +
			"\t from act_re_model model\n" +
			"\t left join act_bpm_category cate on cate.category_id=model.category_\n" +
			"\t where 1=1 ";

	private String modelId;
	private String key;
	private String name;
	private String categoryId;
	private String categoryName;
	private String metaInfo;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date deploymentTime;
	private String deploymentId;
	private Integer version;
	private String tenantId;
	private Boolean status;
	private String ouIds;
	private String systemCode;
	private String processKey;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		if(StringUtils.isNotBlank(metaInfo)){
			try{
				JSONObject json = JSON.parseObject(metaInfo);
				return json.getString("description");
			}catch(Exception e){

			}
		}
		return null;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getDeploymentTime() {
		return deploymentTime;
	}

	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	
	
	
	
}