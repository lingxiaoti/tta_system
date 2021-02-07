package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskProcesserEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import org.activiti.bpmn.model.UserTask;

import java.util.List;

public interface IActBpmTaskConfig {

	/**
	 * 根据ID获取任务配置
	 * @author laoqunzhao 2018-05-03
	 * @param configId 设置ID
	 * @return ActBpmTaskConfigEntity_HI
	 */
	ActBpmTaskConfigEntity_HI getById(Integer configId);
	
	/**
	 * 根据流程KEY及节点ID获取任务配置
	 * @author laoqunzhao 2018-05-03
	 * @param processDefinitionKey 流程KEY
	 * @param taskDefinitionId 任务节点ID
	 * @param includeDeleted 若没有找到，是否从删除的设置中查找
	 * @return ActBpmTaskConfigEntity_HI
	 */
	ActBpmTaskConfigEntity_HI findByDefinition(String processDefinitionKey, String taskDefinitionId, boolean includeDeleted);
	 
	/**
	 * 根据流程KEY获取任务配置列表
	 * @author laoqunzhao 2018-05-03
	 * @param processDefinitionKey 流程KEY
	 * @return List<ActBpmTaskConfigEntity_HI>
	 */
	List<ActBpmTaskConfigEntity_HI> findByProcessDefinitionKey(String processDefinitionKey);

	
	/**
	 * 查询流程任务配置列表，优先按流程定义ID查询
	 * @author laoqunzhao 2018-05-03
	 * @param parameters
	 * {
	 * processDefinitionKey 流程KEY
	 * processDefinitionId 流程定义ID
	 * deleteFlag 是否删除：１．已删除，０．未删除
	 * }
	 * @return List<ActBpmTaskConfigEntity_HI>
	 */
	List<ActBpmTaskConfigEntity_HI> findTaskConfig(JSONObject parameters)throws Exception;

	/**
	 * 查询任务节点人员设置
	 * @author laoqunzhao 2018-07-17
	 * @param configId 设置ID
	 * @return List<ActBpmTaskProcesserEntity_HI>
	 */
	List<ActBpmTaskProcesserEntity_HI> getProcessersByConfigId(Integer configId);
	
	/**
	 * 对任务节点人员设置，将姓名、角色名称用","拼接，用于前端显示
	 * @author laoqunzhao 2018-07-17
	 * @param processers 人员设置
	 * @return List<ActBpmTaskProcesserEntity_HI>
	 */
	List<ActBpmTaskProcesserEntity_HI> getProcessersByJoinNames(List<ActBpmTaskProcesserEntity_HI> processers);
	
	/**
	 * 保存任务配置
	 * @author laoqunzhao 2018-05-03
	 * @param paramJSON JSON格式ActBpmTaskConfigEntity_HI
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
	void save(JSONObject paramJSON, Integer userId) throws Exception;
	
	/**
     * 初始化任务节点配置
     * @author laoqunzhao 2018-05-03
     * @param paramJSON JSON格式ActBpmTaskConfigEntity_HI
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
	void saveAll(JSONObject paramJSON, Integer userId) throws Exception;
	
	/**
	 * 标记删除流程任务设置
	 * @author laoqunzhao 2018-05-03
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	void delete(JSONObject paramJSON);
	
	/**
	 * 删除流程任务设置
	 * @author laoqunzhao 2018-05-03
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	void destory(JSONObject paramJSON);
	
	/**
	 * 根据流程定义KEY删除流程任务设置
	 * @author laoqunzhao 2018-07-19
	 * @param processDefinitionKey 流程定义KEY
	 */
	void destory(String processDefinitionKey);

	/**
	 * 初始化任务节点
	 * @author laoqunzhao 2018-05-03
	 * @param modelId 流程模型ID
	 *  @param initConfigsJOSN 初始化设置
	 * @param operatorUserId 操作人ID
	 */
	void initTaskConfig(String modelId, JSONArray initConfigsJOSN, Integer operatorUserId);

	/**
	 * 获取需选人的任务节点
	 * @author laoqunzhao 2018-05-22
	 * @param processDefinitionKey 流程定义KEY
	 * @param taskDefinitionId 任务定义ID
	 * @return 任务节点集合List<UserTask>
	 * @throws Exception
	 */
	List<UserTask> findAssigneeUserTasks(String processDefinitionKey, String taskDefinitionId) throws Exception;

	List<ActBpmTaskConfigEntity_HI_RO> findTaskConfigByTaskId(List<String> taskIds);

	/**
	 * 查询未设置办理人的任务节点
	 * @author laoqunzhao 2018-07-22
	 * @param processDefinitionKey 流程定义KEY
	 * @return 任务节点集合List<ActBpmTaskConfigEntity_HI>
	 */
	List<ActBpmTaskConfigEntity_HI> findEmptyProcesserTaskConfigs(String processDefinitionKey);

	/**
	 * 查询流程第一个节点的设置,优先从Redis中查询 2018-10-16
	 * @param processDefinitionKey 流程定义KEY
	 * @return ActBpmTaskConfigEntity_HI
	 */
	ActBpmTaskConfigEntity_HI findFirstTaskConfig(String processDefinitionKey);

	/**
	 * 查询流程第一个节点的设置,优先从Redis中查询 2018-10-16
	 * @param processDefinitionId 流程定义Id
	 * @return ActBpmTaskConfigEntity_HI
	 */
	ActBpmTaskConfigEntity_HI findFirstTaskConfigByDef(String processDefinitionId);

	/**
	 * 查询流程节点的设置,优先从Redis中查询 2018-10-16
	 * @param processDefinitionKey 流程定义KEY
	 * @return ActBpmTaskConfigEntity_HI
	 */
	ActBpmTaskConfigEntity_HI findTaskConfig(String processDefinitionKey, String taskDefinitionKey);


	/**
	 * 批量替换流程设置办理人
	 * @author laoqunzhao 2018-10-27
	 * @param processDefinitionKeys 流程唯一标识[]
	 * @param processerOld 原办理人
	 * @param processerNew 新办理人
	 * @param userId 操作人
	 */
	void updateTaskConfigProcessers(List<String> processDefinitionKeys, String processerOld, String processerNew, Integer userId);

	

}
