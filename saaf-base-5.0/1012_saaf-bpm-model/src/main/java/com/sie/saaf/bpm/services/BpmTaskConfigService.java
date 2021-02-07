package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmRoleEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActUserTaskEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmRole;
import com.sie.saaf.bpm.model.inter.IActBpmTaskConfig;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import io.jsonwebtoken.lang.Assert;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bpmTaskConfigService")
public class BpmTaskConfigService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmTaskConfigService.class);
	
	@Autowired
	private IActBpmTaskConfig bpmTaskConfigServer;
	
	@Autowired
	private IActBpmUser bpmUserServer;
	
	@Autowired
	private IActBpmRole bpmRoleServer;
	
	@Autowired
	private IActBpmModel bpmModelServer;

	public BpmTaskConfigService() {
		super();
	}
	
	@RequestMapping(method= RequestMethod.POST,value="findTaskConfig")
	public String findTaskConfig(@RequestParam(required = false) String params) {
		try {
            JSONObject paramJSON = this.parseObject(params);
            if(!paramJSON.containsKey("deleteFlag")){
                paramJSON.put("deleteFlag", 0);//只查询未删除的数据
            }
            List<ActBpmTaskConfigEntity_HI> list = bpmTaskConfigServer.findTaskConfig(paramJSON);
            JSONArray result = list==null||list.isEmpty()?new JSONArray():(JSONArray) JSON.toJSON(list);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "查询成功", result.size(), result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
	}
	
	@RequestMapping(method= RequestMethod.POST,value="get")
	public String get(@RequestParam(required = false) String params) {
		try {
            JSONObject paramJSON = this.parseObject(params);
            ActBpmTaskConfigEntity_HI result = bpmTaskConfigServer.getById(paramJSON.getInteger("configId"));
            if(result != null && (StringUtils.isNotBlank(result.getCcIds()) || StringUtils.isNotBlank(result.getCcRoleKeys()))) {
            	if(StringUtils.isNotBlank(result.getCcIds())) {
            		String [] bpmUserIds = result.getCcIds().split(",");
            		List<String> bpmUserIdsList = new ArrayList<String>();
            		for(String bpmUserId : bpmUserIds) {
            			bpmUserIdsList.add(bpmUserId);
            		}
            		List<ActIdUserEntity_RO> users = bpmUserServer.findUsersByBpmIds(bpmUserIdsList);
            		String userFullNames = null;
        			if(users != null && !users.isEmpty()) {
        				for(ActIdUserEntity_RO user: users) {
        					userFullNames = (userFullNames == null?"":(userFullNames + ",")) + user.getUserFullName();
        				}
        			}
        			result.setCcUserFullNames(userFullNames);
            	}
            	if(StringUtils.isNotBlank(result.getCcRoleKeys())) {
            		String [] roleKeys = result.getCcRoleKeys().split(",");
            		List<String> roleKeysList = new ArrayList<String>();
            		for(String roleKey : roleKeys) {
            			roleKeysList.add(roleKey);
            		}
            		List<ActBpmRoleEntity_HI> roles = bpmRoleServer.findByKeys(roleKeysList);
            		String roleNames = null;
        			if(roles != null && !roles.isEmpty()) {
        				for(ActBpmRoleEntity_HI role: roles) {
        					roleNames = (roleNames == null?"":(roleNames + ",")) + role.getRoleName();
        				}
        			}
        			result.setCcRoleNames(roleNames);
            	}
            }
            if(result != null && result.getProcessers() != null && !result.getProcessers().isEmpty()) {
            	result.setProcessers(bpmTaskConfigServer.getProcessersByJoinNames(result.getProcessers()));
            }
            if(StringUtils.isNotBlank(result.getExtend())) {
            	JSONObject extendJSON = JSON.parseObject(result.getExtend());
            	if(extendJSON.containsKey(WorkflowConstant.ASSIGNEE_TASK_IDS) 
            			&& extendJSON.getJSONArray(WorkflowConstant.ASSIGNEE_TASK_IDS) != null) {
            		JSONArray assigneeTaskIdsJSON = extendJSON.getJSONArray(WorkflowConstant.ASSIGNEE_TASK_IDS);
            		if(assigneeTaskIdsJSON.size()>0) {
            			String taskNames = "";
            			Model model = bpmModelServer.getByKey(result.getProcessDefinitionKey());
            			List<UserTask> userTasks = model == null? null : bpmModelServer.findUserTasks(model.getId(), null, false);
            			if(userTasks != null && !userTasks.isEmpty()) {
            				for(UserTask userTask : userTasks) {
            					for(int i=0; i<assigneeTaskIdsJSON.size(); i++) {
            						if(userTask.getId().equals(assigneeTaskIdsJSON.get(i))) {
            							taskNames = (taskNames.isEmpty()?"":taskNames+",") + userTask.getName();
            						}
            					}
            				}
            			}
            			extendJSON.put("assignee_taskNames", taskNames);
            			result.setExtend(extendJSON.toJSONString());
            		}
            	}
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "查询成功", 0, result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
	}
	
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			String toAll = paramsJSON.getString("toAll");
			if(StringUtils.isBlank(paramsJSON.getString("pcformUrl"))) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "表单地址不能为空", 0, null).toString();
			}
			if(StringUtils.isBlank(paramsJSON.getString("callbackUrl"))) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "回调地址不能为空", 0, null).toString();
			}
			if(paramsJSON.containsKey("processers")) {
				List<Integer> sortIds = new ArrayList<Integer>();
				JSONArray processersJSON = paramsJSON.getJSONArray("processers");
				if(processersJSON != null) {
					for(int i=0; i<processersJSON.size(); i++) {
						JSONObject processerJSON = processersJSON.getJSONObject(i);
						if(!processerJSON.containsKey("sortId") || StringUtils.isBlank(processerJSON.getString("sortId"))) {
							return SToolUtils.convertResultJSONObj(ERROR_STATUS, "优先级不能为空", 0, null).toString();
						}else {
							Integer sortId = processerJSON.getInteger("sortId");
							if(sortIds.contains(sortId)) {
								return SToolUtils.convertResultJSONObj(ERROR_STATUS, "优先级不能有相同值", 0, null).toString();
							}
							sortIds.add(sortId);
						}
					}
				}
			}
			if(StringUtils.equals(toAll, "true")) {
			    bpmTaskConfigServer.saveAll(paramsJSON, super.getSessionUserId());
			}else{
			    bpmTaskConfigServer.save(paramsJSON, super.getSessionUserId());
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	
	/**
	 * 获取需选人的任务节点
	 * @author laoqunzhao 2018-05-22
	 * @param params JSONObject
	 * processDefinitionKey 流程KEY
	 * taskDefinitionId 任务定义ID
	 */
	@RequestMapping(method= RequestMethod.POST,value="findAssigneeUserTasks")
	public String findAssigneeUserTasks(@RequestParam String params){
		try {
			JSONObject paramJSON = this.parseObject(params);
			String processDefinitionKey = paramJSON.getString("processDefinitionKey");
			String taskDefinitionId = paramJSON.getString("taskDefinitionId");
			List<UserTask> userTasks = bpmTaskConfigServer.findAssigneeUserTasks(processDefinitionKey, taskDefinitionId);
			List<ActUserTaskEntity_RO> actUserTasks = new ArrayList<ActUserTaskEntity_RO>();
			if(userTasks != null && !userTasks.isEmpty()) {
				for(UserTask userTask: userTasks) {
					ActUserTaskEntity_RO actUserTask = new ActUserTaskEntity_RO();
					actUserTask.setId(userTask.getId());
					actUserTask.setName(userTask.getName());
					actUserTasks.add(actUserTask);
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", actUserTasks.size(), actUserTasks).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败\n" + e, 0, null).toString();
		}		
	}

	/**
	 * 批量替换流程设置办理人
	 * @author laoqunzhao 2018-04-27
	 * @param params
	 * {
	 * processDefinitionKeys 流程唯一标识[]
	 * processerOld 原办理人
	 * processerNew 新办理人
	 * }
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="updateTaskConfigProcessers")
	public String updateTaskConfigProcessers(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "processDefinitionKeys", "processerOld", "processerNew");
			String processerOld = paramsJSON.getString("processerOld");
			String processerNew = paramsJSON.getString("processerNew");
			ActIdUserEntity_RO user = bpmUserServer.findUserByBpmId(processerOld);
			Assert.notNull(user, "用户" + processerOld + "不存在！");
			user = bpmUserServer.findUserByBpmId(processerNew);
			Assert.notNull(user, "用户" + processerNew + "不存在！");
			JSONArray processDefinitionKeysJSON = paramsJSON.getJSONArray("processDefinitionKeys");
			List<String> processDefinitionKeys = new ArrayList<>();
			for(int i=0; i<processDefinitionKeysJSON.size(); i++){
				processDefinitionKeys.add(processDefinitionKeysJSON.getString(i));
			}
			bpmTaskConfigServer.updateTaskConfigProcessers(processDefinitionKeys, processerOld, processerNew, super.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
}
