package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmRoleEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskProcesserEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import io.jsonwebtoken.lang.Assert;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("actBpmTaskConfigServer")
public class ActBpmTaskConfigServer implements IActBpmTaskConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskConfigServer.class);
	
	@Autowired
	private ViewObject<ActBpmTaskConfigEntity_HI> bpmTaskConfigDAO_HI;
	
	@Autowired
	private ViewObject<ActBpmTaskProcesserEntity_HI> bpmTaskProcesserDAO_HI;
	
	@Autowired
	private BaseViewObject<ActBpmTaskConfigEntity_HI_RO> bpmTaskConfigDAO_HI_RO;
	
	@Autowired
	private IActBpmModel bpmModelServer;

	@Autowired
	private IActBpmProcess bpmProcessServer;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private IActBpmUser bpmUserServer;
	
	@Autowired
	private IActBpmRole bpmRoleServer;

	@Autowired
	private JedisCluster jedisCluster;
	

	public ActBpmTaskConfigServer() {
		super();
	}
	
	/**
	 * 根据ID获取任务配置
	 * @author laoqunzhao 2018-05-03
	 * @param configId 设置ID
	 * @return ActBpmTaskConfigEntity_HI
	 */
	@Override
	public ActBpmTaskConfigEntity_HI getById(Integer configId) {
		ActBpmTaskConfigEntity_HI instance = bpmTaskConfigDAO_HI.getById(configId);
		if(instance != null) {
			instance.setProcessers(getProcessersByConfigId(instance.getConfigId()));
		}
        return instance;
	}
	
	/**
	 * 根据流程KEY及节点ID获取任务配置
	 * @author laoqunzhao 2018-05-03
	 * @param processDefinitionKey 流程KEY
	 * @param taskDefinitionId 任务节点ID
	 * @param includeDeleted 若没有找到，是否从删除的设置中查找
	 * @return ActBpmTaskConfigEntity_HI
	 */
	@Override
	public ActBpmTaskConfigEntity_HI findByDefinition(String processDefinitionKey, String taskDefinitionId, boolean includeDeleted) {
	    Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put("processDefinitionKey", processDefinitionKey);
	    properties.put("taskDefinitionId", taskDefinitionId);
	    properties.put("deleteFlag", 0);
	    List<ActBpmTaskConfigEntity_HI>  list = bpmTaskConfigDAO_HI.findByProperty(properties);
	    if(list != null && !list.isEmpty()) {
	    	ActBpmTaskConfigEntity_HI instance = list.get(0);
	    	instance.setProcessers(getProcessersByConfigId(instance.getConfigId()));
	        return instance;
	    }else if(includeDeleted) {
	    	ActBpmTaskConfigEntity_HI instance = findDeletedConfig(processDefinitionKey, taskDefinitionId);
	    	if(instance != null) {
	    		instance.setProcessers(getProcessersByConfigId(instance.getConfigId()));
		        return instance;
	    	}
	    }
	    return null;
	}
	

	
	/**
	 * 查询任务节点人员设置
	 * @author laoqunzhao 2018-07-17
	 * @param configId 设置ID
	 * @return List<ActBpmTaskProcesserEntity_HI>
	 */
	@Override
	public List<ActBpmTaskProcesserEntity_HI> getProcessersByConfigId(Integer configId) {
		StringBuffer hql = new StringBuffer("from ActBpmTaskProcesserEntity_HI where 1=1");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		hql.append(" and configId= :configId");
		paramsMap.put("configId", configId);
		hql.append(" order by sortId asc");
		return bpmTaskProcesserDAO_HI.findList(hql.toString(), paramsMap);
	}
	
	/**
	 * 对任务节点人员设置，将姓名、角色名称用","拼接，用于前端显示
	 * @author laoqunzhao 2018-07-17
	 * @param processers 人员设置
	 * @return List<ActBpmTaskProcesserEntity_HI>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ActBpmTaskProcesserEntity_HI> getProcessersByJoinNames(List<ActBpmTaskProcesserEntity_HI> processers) {
		if(processers == null || processers.isEmpty()) {
			return null;
		}
		List<String> bpmUserIds = new ArrayList<String>();
		List<String> roleKeys = new ArrayList<String>();
		for(ActBpmTaskProcesserEntity_HI processer: processers) {
			String processerIdsStr = processer.getProcesserIds();
			String processerRoleKeysStr = processer.getProcesserRoleKeys();
			if(StringUtils.isNotBlank(processerIdsStr)) {
				String[] processerIds = processerIdsStr.split(",");
				for(String processerId: processerIds) {
					if(StringUtils.isNotBlank(processerId) && !bpmUserIds.contains(processerId)) {
						bpmUserIds.add(processerId);
					}
				}
			}
			if(StringUtils.isNotBlank(processerRoleKeysStr)) {
				String[] processerRoleKeys = processerRoleKeysStr.split(",");
				for(String processerRoleKey: processerRoleKeys) {
					if(StringUtils.isNotBlank(processerRoleKey) && !roleKeys.contains(processerRoleKey)) {
						roleKeys.add(processerRoleKey);
					}
				}
			}
		}
		Map<String,ActIdUserEntity_RO> usersMap = null;
		Map<String,ActBpmRoleEntity_HI> rolesMap = null;
		if(!bpmUserIds.isEmpty()) {
			List<ActIdUserEntity_RO> users = bpmUserServer.findUsersByBpmIds(bpmUserIds);
			if(users != null && !users.isEmpty()) {
				usersMap = ConvertUtil.listToMap(users, "id");
			}
		}
		if(!roleKeys.isEmpty()) {
			List<ActBpmRoleEntity_HI> roles = bpmRoleServer.findByKeys(roleKeys);
			if(roles != null && !roles.isEmpty()) {
				rolesMap = ConvertUtil.listToMap(roles, "roleKey");
			}
			
		}
		//将审批人姓名、角色名称存放到属性中
		for(ActBpmTaskProcesserEntity_HI processer: processers) {
			String processerIdsStr = processer.getProcesserIds();
			String processerRoleKeysStr = processer.getProcesserRoleKeys();
			if(StringUtils.isNotBlank(processerIdsStr) && usersMap != null) {
				String[] processerIds = processerIdsStr.split(",");
				List<String> userFullNames = new ArrayList<String>();
				for(String processerId: processerIds) {
					if(usersMap.containsKey(processerId)) {
						userFullNames.add(usersMap.get(processerId).getUserFullName());
					}
				}
				processer.setUserFullNames(StringUtils.join(userFullNames, ","));
			}
			if(StringUtils.isNotBlank(processerRoleKeysStr) && rolesMap != null) {
				String[] processerRoleKeys = processerRoleKeysStr.split(",");
				List<String> roleNames = new ArrayList<String>();
				for(String processerRoleKey: processerRoleKeys) {
					if(rolesMap.containsKey(processerRoleKey)) {
						roleNames.add(rolesMap.get(processerRoleKey).getRoleName());
					}
				}
				processer.setRoleNames(StringUtils.join(roleNames, ","));
			}
		}
		return processers;
	}
	
	/**
	 * 根据流程KEY及节点ID获取已删除的任务配置
	 * @author laoqunzhao 2018-05-10
	 * @param processDefinitionKey 流程KEY
	 * @param taskDefinitionId 任务节点ID
	 * @return ActBpmTaskConfigEntity_HI
	 */
	private ActBpmTaskConfigEntity_HI findDeletedConfig(String processDefinitionKey, String taskDefinitionId) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		JSONObject parameters = new JSONObject();
		parameters.put("processDefinitionKey", processDefinitionKey);
		parameters.put("taskDefinitionId", taskDefinitionId);
		parameters.put("deleteFlag", 1);
		StringBuffer query = new StringBuffer("from ActBpmTaskConfigEntity_HI ");
        SaafToolUtils.parperHbmParam(ActBpmTaskConfigEntity_HI.class, parameters, "processDefinitionKey", "processDefinitionKey", query, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskConfigEntity_HI.class, parameters, "taskDefinitionId", "taskDefinitionId", query, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskConfigEntity_HI.class, parameters, "deleteFlag", "deleteFlag", query, queryParamMap, "=");
        //时间逆序排序
        query.append(" order by lastUpdateDate desc");
		List<ActBpmTaskConfigEntity_HI> list = bpmTaskConfigDAO_HI.findList(query.toString().replaceFirst("\\s+and\\s+", " where "), queryParamMap);
	    if(list != null && !list.isEmpty()) {
	        return list.get(0);
	    }
	    return null;
	}
	
	/**
	 * 根据流程KEY获取任务配置列表
	 * @author laoqunzhao 2018-05-03
	 * @param processDefinitionKey 流程KEY
	 * @return List<ActBpmTaskConfigEntity_HI>
	 */
	@Override
	public List<ActBpmTaskConfigEntity_HI> findByProcessDefinitionKey(String processDefinitionKey) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("processDefinitionKey", processDefinitionKey);
        properties.put("deleteFlag", 0);
        return bpmTaskConfigDAO_HI.findByProperty(properties);
    }
	
	/**
	 * 查询流程任务配置列表
	 * @author laoqunzhao 2018-05-03
	 * @param parameters JSON格式查询参数
	  * {
	  * processDefinitionKey 流程KEY
	  * processDefinitionId 流程定义ID
	  * deleteFlag 是否删除：１．已删除，０．未删除
	  * }
	 * @return List<ActBpmTaskConfigEntity_HI>
	 */
	@Override
	@Transactional(readOnly=false)
	public List<ActBpmTaskConfigEntity_HI> findTaskConfig(JSONObject parameters) throws Exception{
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer query = new StringBuffer("from ActBpmTaskConfigEntity_HI ");
		String processDefinitionId = parameters.getString("processDefinitionId");
		String processDefinitionKey = parameters.getString("processDefinitionKey");
		boolean readonly = "true".equals(parameters.getString("readonly"));
		if(StringUtils.isNotBlank(processDefinitionId)){
			ProcessDefinition processDefinition = bpmProcessServer.findProcessDefinitionById(processDefinitionId);
			if(processDefinition != null){
				processDefinitionKey = processDefinition.getKey();
				parameters.put("processDefinitionKey", processDefinitionKey);
			}
		}
        SaafToolUtils.parperHbmParam(ActBpmTaskConfigEntity_HI.class, parameters, "processDefinitionKey", "processDefinitionKey", query, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskConfigEntity_HI.class, parameters, "deleteFlag", "deleteFlag", query, queryParamMap, "=");
        query.append(" order by processDefinitionKey,sortId asc ");
		List<ActBpmTaskConfigEntity_HI> list = bpmTaskConfigDAO_HI.findList(query.toString().replaceFirst("\\s+and\\s+", " where "), queryParamMap);
		List<UserTask> userTasks = null;
		if(StringUtils.isNotBlank(processDefinitionId)){
			userTasks = bpmModelServer.findUserTasks(null, processDefinitionId, true);
		}else{
			Model model = bpmModelServer.getByKey(processDefinitionKey);
			if(model != null){
				userTasks = bpmModelServer.findUserTasks(model.getId(), null, true);
			}
		}
		if(userTasks != null && !userTasks.isEmpty()){
			List<ActBpmTaskConfigEntity_HI> taskConfigs = new ArrayList<ActBpmTaskConfigEntity_HI>();
			Map<String, ActBpmTaskConfigEntity_HI> taskConfigsMap = new HashMap<String, ActBpmTaskConfigEntity_HI>();
			if(list != null && !list.isEmpty()){
				for(ActBpmTaskConfigEntity_HI taskConfig : list){
					taskConfigsMap.put(taskConfig.getTaskDefinitionId(), taskConfig);
				}
			}
			for(int i=0; i<userTasks.size(); i++){
				UserTask userTask = userTasks.get(i);
				if(taskConfigsMap.containsKey(userTask.getId())){
					ActBpmTaskConfigEntity_HI taskConfig = taskConfigsMap.get(userTask.getId());
					if(!StringUtils.equals(taskConfig.getTaskName(), userTask.getName())){
						taskConfig.setTaskName(userTask.getName());
						taskConfig.setOperatorUserId(parameters.getInteger(WorkflowConstant.OPERATOR_USER_ID));
						if(!readonly) {
							bpmTaskConfigDAO_HI.saveOrUpdate(taskConfig);
						}
					}
					taskConfigs.add(taskConfig);
				}else{
					ActBpmTaskConfigEntity_HI taskConfig = findDeletedConfig(processDefinitionKey, userTask.getId());
					if(taskConfig != null){
						taskConfigs.add(taskConfig);
					}else{
						taskConfig = new ActBpmTaskConfigEntity_HI();
						taskConfig.setSortId(i);
						taskConfig.setTaskName(userTask.getName());
						taskConfig.setDeleteFlag(0);
						taskConfig.setProcessDefinitionKey(processDefinitionKey);
						taskConfig.setEditStatus(0);
						taskConfig.setTaskDefinitionId(userTask.getId());
						taskConfig.setEnableAutoJump(0);
						taskConfig.setOperatorUserId(parameters.getInteger(WorkflowConstant.OPERATOR_USER_ID));
						if(!readonly) {
							bpmTaskConfigDAO_HI.saveOrUpdate(taskConfig);
						}
						taskConfigs.add(taskConfig);
					}
				}
			}
			return taskConfigs;
		}
		return list;
	}
	
	@Override
	public List<ActBpmTaskConfigEntity_HI_RO> findTaskConfigByTaskId(List<String> taskIds) {
		String taskIdsStr = "'" + StringUtils.join(taskIds, "','") + "'";
		StringBuffer query = new StringBuffer(ActBpmTaskConfigEntity_HI_RO.QUERY_ALL_CONFIG_URL_SQL);
		query.append(" and exists( select 1 from act_hi_taskinst task left join act_hi_taskinst super on task.parent_task_id_=super.id_");
		query.append(" left join act_re_procdef proc on (proc.id_=task.proc_def_id_ or proc.id_=super.proc_def_id_) ");
		query.append(" where (task.id_ in (" + taskIdsStr + ") or task.parent_task_id_ in ("  + taskIdsStr + "))");
		query.append(" and proc.key_= cfg.proc_def_key and cfg.delete_flag=0 and (cfg.task_def_id=task.task_def_key_ or cfg.task_def_id=super.task_def_key_))");
		List<ActBpmTaskConfigEntity_HI_RO> list = bpmTaskConfigDAO_HI_RO.findList(query.toString());
		return list;
	}

	/**
	 * 保存任务配置
	 * @author laoqunzhao 2018-05-03
	 * @param paramsJSON
	 * {
	 * configId 主键
	 * processDefinitionKey 流程定义key
	 * taskDefinitionId 任务节点ID
	 * taskName 任务名称
	 * pcformUrl PC端表单地址
	 * mobformUrl 移动端表单地址
	 * pcDataUrl PC端数据URL
	 * mobDataUrl 移动端数据URL
	 * callbackUrl 回调服务
	 * ccIds 抄送人ID
	 * ccRoleKeys 抄送人KEY
	 * editStatus 0.只读  1.可编辑
	 * enableAutoJump 允许自动跳过
	 * operatorUserId 操作人ID
	 * processers
	 * [
	 *  processerIds 办理人ID
	 *  processerRoleKeys 办理人角色KEY
	 *  disabled 禁用
	 *  sortId 优先级
	 * ],
	 * extend:  扩展配置
	 * {
	 *   assignee_taskIds:["ID1","ID2"] 需选人的节点ID
	 * }
	 * }
	 * @param userId 操作人ID
	 * @throws Exception 
	 */
	@Override
	public void save(JSONObject paramsJSON, Integer userId) throws Exception {
		//将json转换成entity
		ActBpmTaskConfigEntity_HI instance = null;
		if(StringUtils.isNotBlank(paramsJSON.getString("configId"))){
			instance = SaafToolUtils.setEntity(ActBpmTaskConfigEntity_HI.class, paramsJSON, bpmTaskConfigDAO_HI, userId);
		}else{
			instance = JSON.toJavaObject(paramsJSON, ActBpmTaskConfigEntity_HI.class);
		}
		if(instance == null){
			return;
		}
		clearTaskConfigRedis(instance.getProcessDefinitionKey());
		Integer configId = instance.getConfigId();
		if(instance.getConfigId() != null){
			bpmTaskConfigDAO_HI.update(instance);
		}else{
			ActBpmTaskConfigEntity_HI instance_ = findByDefinition(instance.getProcessDefinitionKey(), instance.getTaskDefinitionId(), true);
			if(instance_ != null){
				configId = instance_.getConfigId();
				BeanUtils.copyProperties(instance, instance_, "configId","processDefinitionKey", "taskDefinitionId", "taskName",
						"createdBy", "creationDate", "versionNum", "deleteFlag" ,"sortId");
				bpmTaskConfigDAO_HI.update(instance_);
			}else{
				bpmTaskConfigDAO_HI.saveOrUpdate(instance);
				configId = instance.getConfigId();
			}
		}
		List<ActBpmTaskProcesserEntity_HI> processers = bpmTaskProcesserDAO_HI.findByProperty("configId", configId);
		int index = 0;
		if(paramsJSON.containsKey("processers")) {
			JSONArray processersJSON = paramsJSON.getJSONArray("processers");
			if(processersJSON != null) {
				for(int i=0; i<processersJSON.size(); i++) {
					JSONObject processerJSON = processersJSON.getJSONObject(i);
					if(processerJSON == null) {
						continue;
					}
					processerJSON.put("processerId", null);
					ActBpmTaskProcesserEntity_HI processer = null;
					if(processers != null && processers.size()>index) {
						processer = processers.get(index++);
						ActBpmTaskProcesserEntity_HI processerNew = JSON.toJavaObject(processerJSON, ActBpmTaskProcesserEntity_HI.class);
						processer.setDisabled(processerNew.getDisabled() == null?0:processerNew.getDisabled());
						processer.setDeleteFlag(0);
						processer.setProcesserIds(processerNew.getProcesserIds());
						processer.setProcesserRoleKeys(processerNew.getProcesserRoleKeys());
						processer.setSortId(processerNew.getSortId());
					}else {
						processer = JSON.toJavaObject(processerJSON, ActBpmTaskProcesserEntity_HI.class);
					}
					processer.setConfigId(configId);
					processer.setOperatorUserId(userId);
					bpmTaskProcesserDAO_HI.saveOrUpdate(processer);
				}
			}
		}
		if(processers != null && processers.size()>index) {
			while(processers.size()>index) {
				bpmTaskProcesserDAO_HI.delete(processers.get(index++));
			}
		}
		LOGGER.info("save bpm task config: " + paramsJSON.toString());
	}
	
	/**
     * 初始化任务节点配置
     * @author laoqunzhao 2018-05-03
     * @param paramsJSON JSON格式ActBpmTaskConfigEntity_HI
	 * configId 主键
	 * processDefinitionKey 流程定义key
	 * taskDefinitionId 任务节点ID
	 * taskName 任务名称
	 * pcformUrl PC端表单地址
	 * mobformUrl 移动端表单地址
	 * pcDataUrl PC端数据URL
	 * mobDataUrl 移动端数据URL
	 * callbackUrl 回调服务
	 * ccIds 抄送人ID
	 * ccRoleKeys 抄送人KEY
	 * editStatus 0.只读  1.可编辑
	 * enableAutoJump 允许自动跳过
	 * operatorUserId 操作人ID
	 * processers
	 * [
	 *  processerIds 办理人ID
	 *  processerRoleKeys 办理人角色KEY
	 *  disabled 禁用
	 *  sortId 优先级
	 * ]
	 * @param userId 操作人ID
	 * @throws Exception 
	 */
    @Override
    public void saveAll(JSONObject paramsJSON, Integer userId) throws Exception {
        String processDefinitionKey = paramsJSON.getString("processDefinitionKey");
        Assert.isTrue(StringUtils.isNotBlank(processDefinitionKey), "流程定义KEY不能为空");
        List<ActBpmTaskConfigEntity_HI> taskConfigs = findByProcessDefinitionKey(processDefinitionKey);
        //保存到所有节点
        if(taskConfigs != null && !taskConfigs.isEmpty()) {
            for(ActBpmTaskConfigEntity_HI instance: taskConfigs) {
            	paramsJSON.put("configId", instance.getConfigId());
            	paramsJSON.put("taskDefinitionId", instance.getTaskDefinitionId());
            	paramsJSON.put("taskName", instance.getTaskName());
            	save(paramsJSON, userId);
            }
        }
		clearTaskConfigRedis(processDefinitionKey);
        LOGGER.info("save bpm task config to all: " + paramsJSON.toString());
    }

    /**
	 * 标记删除流程任务设置
	 * @author laoqunzhao 2018-05-03
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	@Override
	public void delete(JSONObject paramJSON) {
	    JSONArray configIds = paramJSON.getJSONArray("configIds");
	    Integer userId = paramJSON.getInteger("operatorUserId");
        if(configIds != null && !configIds.isEmpty()){
            for(int i=0; i<configIds.size(); i++){
                int id = configIds.getIntValue(i);
				ActBpmTaskConfigEntity_HI entity = bpmTaskConfigDAO_HI.getById(id);
				if(entity != null) {
					entity.setDeleteFlag(1);
					entity.setOperatorUserId(userId);
					bpmTaskConfigDAO_HI.update(entity);
					List<ActBpmTaskProcesserEntity_HI> processers = bpmTaskProcesserDAO_HI.findByProperty("configId", id);
					if(processers != null && !processers.isEmpty()) {
						for(ActBpmTaskProcesserEntity_HI processer: processers) {
							processer.setDeleteFlag(1);
							processer.setOperatorUserId(userId);
							bpmTaskProcesserDAO_HI.update(processer);
						}
					}
					clearTaskConfigRedis(entity.getProcessDefinitionKey());
				}
			}
		}
        LOGGER.info("delete bpm task config: " + paramJSON);
	}
	
	/**
	 * 删除流程任务设置
	 * @author laoqunzhao 2018-05-03
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
	    JSONArray configIds = paramJSON.getJSONArray("configIds");
        if(configIds != null && !configIds.isEmpty()){
            for(int i=0; i<configIds.size(); i++){
                int id = configIds.getIntValue(i);
				bpmTaskConfigDAO_HI.delete(id);
				List<ActBpmTaskProcesserEntity_HI> processers = bpmTaskProcesserDAO_HI.findByProperty("configId", id);
				if(processers != null && !processers.isEmpty()) {
					for(ActBpmTaskProcesserEntity_HI processer: processers) {
						bpmTaskProcesserDAO_HI.delete(processer);
					}
				}
			}
            LOGGER.info("destory bpm task config:" + paramJSON);
		}
	}
	
	/**
	 * 根据流程定义KEY删除流程任务设置
	 * @author laoqunzhao 2018-07-19
	 * @param processDefinitionKey 流程定义KEY
	 */
	@Override
	public void destory(String processDefinitionKey) {
		Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("processDefinitionKey", processDefinitionKey);
        List<ActBpmTaskConfigEntity_HI> taskConfigs = bpmTaskConfigDAO_HI.findByProperty(properties);
        if(taskConfigs != null && !taskConfigs.isEmpty()) {
        	for(ActBpmTaskConfigEntity_HI taskConfig : taskConfigs) {
	        	bpmTaskConfigDAO_HI.delete(taskConfig);
				List<ActBpmTaskProcesserEntity_HI> processers = bpmTaskProcesserDAO_HI.findByProperty("configId", taskConfig.getConfigId());
				if(processers != null && !processers.isEmpty()) {
					for(ActBpmTaskProcesserEntity_HI processer: processers) {
						bpmTaskProcesserDAO_HI.delete(processer);
					}
				}
        	}
        }
		clearTaskConfigRedis(processDefinitionKey);
        LOGGER.info("destory bpm task config by processDefinitionKey:" + processDefinitionKey);
	}
	
	/**
	 * 初始化任务节点
	 * @author laoqunzhao 2018-05-03
	 * @param modelId 流程模型ID
     * @param initConfigsJOSN 初始化设置
	 * @param operatorUserId 操作人ID
	 */
	@Override
	@Transactional(readOnly=false)
    public void initTaskConfig(String modelId, JSONArray initConfigsJOSN ,Integer operatorUserId){
		if(StringUtils.isBlank(modelId) || operatorUserId == null) {
			return;
		}
        try {
        	Model modelData = repositoryService.getModel(modelId);
            String processDefinitionKey = modelData.getKey();
            //查询所有任务节点
            List<UserTask> userTasks = bpmModelServer.findUserTasks(modelData.getId(), null, true);
            //查询所有已配置的任务节点
            JSONObject paramJson = new JSONObject();
            paramJson.put("deleteFlag", 0);
            paramJson.put("processDefinitionKey", processDefinitionKey);
			//初始化配置
            Map<String, ActBpmTaskConfigEntity_HI> initConfigsMap = new HashMap<String, ActBpmTaskConfigEntity_HI>();
			List<ActBpmTaskConfigEntity_HI> initConfigs = new ArrayList<ActBpmTaskConfigEntity_HI>();
			if(initConfigsJOSN != null) {
				for (int i = 0; i < initConfigsJOSN.size(); i++) {
					JSONObject initConfigJOSN = initConfigsJOSN.getJSONObject(i);
					initConfigJOSN.put("configId", null);
					ActBpmTaskConfigEntity_HI initConfig = JSON.toJavaObject(initConfigJOSN, ActBpmTaskConfigEntity_HI.class);
					initConfigsMap.put(initConfig.getTaskDefinitionId(), initConfig);
				}
			}
            if(userTasks != null && !userTasks.isEmpty()) {
                for(int i=0; i<userTasks.size(); i++) {
                	UserTask userTask = userTasks.get(i);
                    ActBpmTaskConfigEntity_HI initInstance = initConfigsMap.get(userTask.getId());
                    //初始化配置不为空
                    if(initInstance != null){
						initInstance.setSortId(i+1);
						initInstance.setProcessDefinitionKey(processDefinitionKey);
						initInstance.setCreatedBy(operatorUserId);
						initInstance.setOperatorUserId(operatorUserId);
						initInstance.setDeleteFlag(0);
						initInstance.setTaskDefinitionId(userTask.getId());
						initInstance.setTaskName(userTask.getName());
						save((JSONObject)JSON.toJSON(initInstance), operatorUserId);
                    }else {
						initInstance = new ActBpmTaskConfigEntity_HI();
						initInstance.setSortId(i+1);
						initInstance.setProcessDefinitionKey(processDefinitionKey);
						initInstance.setCreatedBy(operatorUserId);
						initInstance.setOperatorUserId(operatorUserId);
						initInstance.setDeleteFlag(0);
						initInstance.setTaskDefinitionId(userTask.getId());
						initInstance.setTaskName(userTask.getName());
                        bpmTaskConfigDAO_HI.save(initInstance);
                    }
                }
            }
			clearTaskConfigRedis(processDefinitionKey);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
	}
	
	/**
	 * 获取需选人的任务节点
	 * @author laoqunzhao 2018-05-22
	 * @param processDefinitionKey 流程定义KEY
	 * @param taskDefinitionId 任务定义ID
	 * @return 任务节点集合List<UserTask>
	 * @throws Exception
	 */
	@Override
	public List<UserTask> findAssigneeUserTasks(String processDefinitionKey, String taskDefinitionId) throws Exception {

		ActBpmTaskConfigEntity_HI taskConfig = findByDefinition(processDefinitionKey, taskDefinitionId, true);
		if(taskConfig == null || StringUtils.isBlank(taskConfig.getExtend())) {
			return null;
		}
		JSONObject extendJSON = JSON.parseObject(taskConfig.getExtend(), JSONObject.class);
		if(!extendJSON.containsKey(WorkflowConstant.ASSIGNEE_TASK_IDS)) {
			return null;
		}
		JSONArray taskIds = extendJSON.getJSONArray(WorkflowConstant.ASSIGNEE_TASK_IDS);
		if(taskIds == null || taskIds.isEmpty()) {
			return null;
		}
		List<Model> modelList = repositoryService.createModelQuery().modelKey(processDefinitionKey).list();
		String modelId = modelList == null || modelList.isEmpty()?null : modelList.get(0).getId();
		List<UserTask> allUserTasks = bpmModelServer.findUserTasks(modelId, null, false);
		if(allUserTasks == null || allUserTasks.isEmpty()) {
			return null;
		}
		List<UserTask> userTasks = new ArrayList<UserTask>();
		for(UserTask userTask: allUserTasks) {
			if(taskIds.contains(userTask.getId())) {
				userTasks.add(userTask);
			}
		}
		return userTasks;
	}
	
	/**
	 * 查询未设置办理人的任务节点，用于流程发布时逻辑判断
	 * @author laoqunzhao 2018-07-22
	 * @param processDefinitionKey 流程定义KEY
	 * @return 任务节点集合List<ActBpmTaskConfigEntity_HI>
	 */
	@Override
	public List<ActBpmTaskConfigEntity_HI> findEmptyProcesserTaskConfigs(String processDefinitionKey) {
		if(StringUtils.isBlank(processDefinitionKey)) {
			return null;
		}
		StringBuffer hql = new StringBuffer("from ActBpmTaskConfigEntity_HI cfg where cfg.deleteFlag=:deleteFlag and cfg.processDefinitionKey=:processDefinitionKey");
		hql.append(" and not exists(select 1 from ActBpmTaskProcesserEntity_HI tpcs where tpcs.configId=cfg.configId and tpcs.disabled=0");
		hql.append(" and (tpcs.processerIds is not null and ltrim(tpcs.processerIds) is not null or tpcs.processerRoleKeys is not null and ltrim(tpcs.processerRoleKeys) is not null))");
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("processDefinitionKey", processDefinitionKey);
		paramsMap.put("deleteFlag", 0);
		return bpmTaskConfigDAO_HI.findList(hql.toString(), paramsMap);
	}

	/**
	 * 查询流程第一个节点的设置,优先从Redis中查询 2018-10-16
	 * @param processDefinitionKey 流程定义KEY
	 * @return ActBpmTaskConfigEntity_HI
	 */
	@Override
	public ActBpmTaskConfigEntity_HI findFirstTaskConfig(String processDefinitionKey){
		if(StringUtils.isBlank(processDefinitionKey)) {
			return null;
		}
		ActBpmTaskConfigEntity_HI instance = hget(WorkflowConstant.REDIS_PROC_FTASK_CFG, processDefinitionKey, ActBpmTaskConfigEntity_HI.class);
		if(instance == null) {
			ProcessDefinition processDefinition = bpmProcessServer.findLatestRunningProcess(processDefinitionKey);
			Assert.notNull(processDefinition, "查询流程定义失败！");
			UserTask firstTask = bpmProcessServer.getFirstUserTask(processDefinition.getId());
			Assert.notNull(firstTask, "查询发起节点失败！");
			instance = findByDefinition(processDefinitionKey, firstTask.getId(), true);
			if(instance != null) {
				//存Redis
				instance.setProcessDefinitionId(processDefinition.getId());
				hset(WorkflowConstant.REDIS_PROC_FTASK_CFG, processDefinitionKey, instance);
			}
		}
		return instance;
	}

	/**
	 * 查询流程第一个节点的设置,优先从Redis中查询 2018-10-16
	 * @param processDefinitionId 流程定义Id
	 * @return ActBpmTaskConfigEntity_HI
	 */
	@Override
	public ActBpmTaskConfigEntity_HI findFirstTaskConfigByDef(String processDefinitionId){
		if(StringUtils.isBlank(processDefinitionId)) {
			return null;
		}
		ActBpmTaskConfigEntity_HI instance = hget(WorkflowConstant.REDIS_PROC_DEF_FTASK_CFG, processDefinitionId, ActBpmTaskConfigEntity_HI.class);
		if(instance == null) {
			ProcessDefinition processDefinition = bpmProcessServer.findProcessDefinitionById(processDefinitionId);
			Assert.notNull(processDefinition, "查询流程定义失败！");
			UserTask firstTask = bpmProcessServer.getFirstUserTask(processDefinition.getId());
			Assert.notNull(firstTask, "查询发起节点失败！");
			instance = findByDefinition(processDefinition.getKey(), firstTask.getId(), true);
			if(instance != null) {
				//存Redis
				instance.setProcessDefinitionId(processDefinition.getId());
				hset(WorkflowConstant.REDIS_PROC_DEF_FTASK_CFG, processDefinitionId, instance);
			}
		}
		return instance;
	}

	/**
	 * 查询流程节点的设置,优先从Redis中查询 2018-10-16
	 * @param processDefinitionKey 流程定义KEY
	 * @return ActBpmTaskConfigEntity_HI
	 */
	@Override
	public ActBpmTaskConfigEntity_HI findTaskConfig(String processDefinitionKey, String taskDefinitionKey){
		if(StringUtils.isBlank(processDefinitionKey) || StringUtils.isBlank(taskDefinitionKey)) {
			return null;
		}
		String key = processDefinitionKey + "::" + taskDefinitionKey;//Redis键值
		ActBpmTaskConfigEntity_HI instance = hget(WorkflowConstant.REDIS_PROC_TASK_CFG, key, ActBpmTaskConfigEntity_HI.class);
		if(instance == null) {
			instance = findByDefinition(processDefinitionKey, taskDefinitionKey, true);
			if(instance != null) {
				//存Redis
				hset(WorkflowConstant.REDIS_PROC_TASK_CFG, key, instance);
			}
		}
		return instance;
	}

	/**
	 * 批量替换流程设置办理人
	 * @author laoqunzhao 2018-10-27
	 * @param processDefinitionKeys 流程唯一标识[]
	 * @param processerOld 原办理人
	 * @param processerNew 新办理人
	 * @param userId 操作人
	 */
	@Override
	public void updateTaskConfigProcessers(List<String> processDefinitionKeys ,String processerOld, String processerNew, Integer userId){
		if(processDefinitionKeys == null || processDefinitionKeys.isEmpty() || StringUtils.isBlank(processerOld) || StringUtils.isBlank(processerNew)){
			return;
		}
		List<ActBpmTaskConfigEntity_HI> configs = findByProcessDefinitionKeys(processDefinitionKeys);
		if(configs == null || configs.isEmpty()){
			return;
		}
		List<ActBpmTaskConfigEntity_HI> newConfigs = new ArrayList<>();
		//替换抄送人
		for(ActBpmTaskConfigEntity_HI config : configs){
			if(StringUtils.isBlank(config.getCcIds())
					|| !("," + config.getCcIds() + ",").contains("," + processerOld + ",")){
				continue;
			}
			String ccIds = config.getCcIds();
			if(StringUtils.equals(ccIds, processerNew)){
				continue;
			}
			//原办理人已有新办理人
			if(ccIds.matches("(^" + processerNew + ",.+)|(.+," + processerNew + ",.+)|(.+," + processerNew + "$)")){
				processerNew = "";
			}
			config.setCcIds(ccIds
					.replaceAll("^" + processerOld + "$", processerNew)
					.replaceAll("^" + processerOld + ",", processerNew + ",")
					.replaceAll("," + processerOld + ",", "," + processerNew + ",")
					.replaceAll("," + processerOld + "$", "," +processerNew)
					.replaceAll(",,", ",")
					.replaceAll("^,", "")
					.replaceAll(",$", ""));
			config.setOperatorUserId(userId);
			newConfigs.add(config);
		}
		if(newConfigs != null && !newConfigs.isEmpty()) {
			bpmTaskConfigDAO_HI.update(newConfigs);
		}
		//替换办理人
		List<ActBpmTaskProcesserEntity_HI> processers = findProcessersByProcessDefinitionKeys(processDefinitionKeys);
		if(processers == null || processers.isEmpty()){
			return;
		}
		List<ActBpmTaskProcesserEntity_HI> newProcessers = new ArrayList<>();
		for(ActBpmTaskProcesserEntity_HI processer : processers){
			if(StringUtils.isBlank(processer.getProcesserIds())
					|| !("," + processer.getProcesserIds() + ",").contains("," + processerOld + ",")){
				continue;
			}
			String processerIds = processer.getProcesserIds();
			if(StringUtils.equals(processerIds, processerNew)){
				continue;
			}
			//原办理人已有新办理人
			if(processerIds.matches("(^" + processerNew + ",.+)|(.+," + processerNew + ",.+)|(.+," + processerNew + "$)")){
				processerNew = "";
			}
			processer.setProcesserIds(processerIds
					.replaceAll("^" + processerOld + "$", processerNew)
					.replaceAll("^" + processerOld + ",", processerNew + ",")
					.replaceAll("," + processerOld + ",", "," + processerNew + ",")
					.replaceAll("," + processerOld + "$", "," +processerNew)
					.replaceAll(",,", ",")
					.replaceAll("^,", "")
					.replaceAll(",$", ""));
			processer.setOperatorUserId(userId);
			newProcessers.add(processer);
		}
		if(newProcessers != null && !newProcessers.isEmpty()){
			bpmTaskProcesserDAO_HI.update(newProcessers);
			clearTaskConfigRedis(null);
		}
	}

	/**
	 * 根据流程KEY获取任务配置列表
	 * @author laoqunzhao 2018-10-27
	 * @param processDefinitionKeys 流程KEY
	 * @return List<ActBpmTaskConfigEntity_HI>
	 */
	private List<ActBpmTaskConfigEntity_HI> findByProcessDefinitionKeys(List<String> processDefinitionKeys) {
		if(processDefinitionKeys == null || processDefinitionKeys.isEmpty()){
			return null;
		}
		StringBuffer query = new StringBuffer("from ActBpmTaskConfigEntity_HI bean where bean.processDefinitionKey in ('" + StringUtils.join(processDefinitionKeys, "','") + "')");
		return bpmTaskConfigDAO_HI.findList(query);
	}

	/**
	 * 根据流程KEY获取任务办理人列表
	 * @author laoqunzhao 2018-10-27
	 * @param processDefinitionKeys 流程KEY
	 * @return List<ActBpmTaskConfigEntity_HI>
	 */
	private List<ActBpmTaskProcesserEntity_HI> findProcessersByProcessDefinitionKeys(List<String> processDefinitionKeys) {
		if(processDefinitionKeys == null || processDefinitionKeys.isEmpty()){
			return null;
		}
		StringBuffer query = new StringBuffer("from ActBpmTaskProcesserEntity_HI bean");
		query.append(" where exists( select 1 from ActBpmTaskConfigEntity_HI config where config.configId = bean.configId");
		query.append(" and config.processDefinitionKey in ('" + StringUtils.join(processDefinitionKeys, "','") + "'))");
		return bpmTaskProcesserDAO_HI.findList(query);
	}

	/**
	 * 保存数据到Redis，有异常不抛出
	 * @param key 键
	 * @param field 字段
	 * @param value 值
	 */
	private void hset(String key, String field, Object value){
		try {
			String str = JSON.toJSONString(value);
			jedisCluster.hset(key, field, str);
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 从Redis中查询对象，有异常不抛出
	 * @param key 键
	 * @param field 字段
	 * @param clazz 对象类
	 * @return
	 */
	private <T> T hget(String key, String field, Class<T> clazz){
		try {
			String str = jedisCluster.hget(key, field);
			if(StringUtils.isNotBlank(str)) {
				JSONObject strJSON = JSON.parseObject(str);
				return JSON.toJavaObject(strJSON, clazz);
			}
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 清除流程设置相关的缓存
	 * @param processDefinitionKey 流程定义Key
	 */
	private void clearTaskConfigRedis(String processDefinitionKey){
		try {
			if(StringUtils.isBlank(processDefinitionKey)){
				jedisCluster.del(WorkflowConstant.REDIS_PROC_FTASK_CFG);
			}else{
				jedisCluster.hdel(WorkflowConstant.REDIS_PROC_FTASK_CFG, processDefinitionKey);
			}
			jedisCluster.del(WorkflowConstant.REDIS_PROC_DEF_FTASK_CFG);
			jedisCluster.del(WorkflowConstant.REDIS_PROC_TASK_CFG);
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	
}
