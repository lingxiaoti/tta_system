package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface IActBpmProcess {
	
	/**
     * 流程定义查询
     * @author laoqunzhao 2018-05-03
     * @param parameters
     * processDefinitionKey 流程定义KEY
     * categoryId 流程分类ID
     * searchKey 流程定义KEY/流程名称
     * @param pageIndex 页码索引
	 * @param pageRows 每页记录数
     * @return Pagination<ProcessDefinition> 查询结果
     */
	Pagination<ProcessDefinition> findDefinitions(JSONObject parameters, Integer pageIndex, Integer pageRows);

	/**
     * 根据流程定义ID获取流程定义
     * @author laoqunzhao 2018-05-29
     * @param processDefinitionId 流程定义ID
     * @return ProcessDefinition流程定义
     */
	ProcessDefinition findProcessDefinitionById(String processDefinitionId);
	
	/**
     * 根据流程定义KEY获取最新版本流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionKey 流程定义KEY
     * @return ProcessDefinition最新版本的流程定义
     */
	ProcessDefinition findLatestProcess(String processDefinitionKey);
	
	/**
     * 根据流程定义KEY获取最新版本运行中的流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionKey 流程定义KEY
     * @return ProcessDefinition最新版本在运行的流程定义
     */
	ProcessDefinition findLatestRunningProcess(String processDefinitionKey);
	
	/**
     * 根据流程定义KEY获取流程列表
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionKey 流程定义KEY
     * @return List<ProcessDefinition> 流程定义集合
     */
	List<ProcessDefinition> findDefinitions(String processDefinitionKey);


	/**
	 * 启动流程
	 * @author laoqunzhao 2018-05-03
	 * @param paramJSON
	 * {
	 * listId 申请单ID
	 * description 发起说明
	 * businessKey 业务主键
	 * processDefinitionKey 流程定义KEY
	 * variables 流程发起业务变量JSONArray
	 * [{
	 * name: 变量名称,
	 * type: 变量类型long/double/boolean/date/string,
	 * value: 变量值
	 * }]
	 * properties 流程发起流程表单JSONObject
	 * {字段名称:字段内容，。。。}
	 * extend 流程发起扩展JSONObject
	 * {
	 * var_tasks_assignees:{
	 * taskDefinitionId:[userId] 指定任务办理人
	 * }
	 * }
	 * title 流程发起标题
	 * categoryId 流程分类ID,
	 * saveonly : 是否只保存表单信息，true是（不提交任务），false否（提交任务）,
	 * positionId 职位ID
	 * ouId ouID
	 * responsibilityId 职责ID
	 * applyPersonId 申请人ID
	 * applyPositionId 申请人职位ID
	 * }
	 * @param startUser 提交人
	 * @param applyUser 申请人
	 * @return 新创建的流程实例ProcessInstance
	 * @throws Exception
	 */
	ProcessInstance start(JSONObject paramJSON, ActIdUserEntity_RO startUser, ActIdUserEntity_RO applyUser) throws Exception;

    /**
	 * 终止流程
	 * @author laoqunzhao 2018-07-07
     * @param bpmList 申请单ActBpmListEntity_HI
     * @param user 操作人
	 */
    void stop(ActBpmListEntity_HI bpmList, ActIdUserEntity_RO user, String reason);
    
    /**
     * 激活流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionId 流程定义ID
     */
	void activate(String processDefinitionId);

	/**
     * 挂起流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionId 流程定义ID
     */
	void suspend(String processDefinitionId);
    
    
    /**
	 * 根据流程实例ID获取发起人ID
	 * @author laoqunzhao 2018-05-03
     * @param processInstanceId 流程实例ID
     * @return 发起人ID
	 */
	String getStartUserId(String processInstanceId);
	
	/**
	 * 根据流程定义ID获取开始节点
	 * @author laoqunzhao 2018-05-03
     * @param processDefinitionId 流程定义ID
     * @return 开始节点
	 */
	StartEvent getStartEvent(String processDefinitionId);
	
	/**
	 * 根据任务ID获取流程实例ID
	 * @author laoqunzhao 2018-07-09
	 * @param taskId 任务ID
	 * @return 流程实例ID
	 */
	String getProcessInstanceIdByTaskId(String taskId);
	
	/**
	 * 根据任务ID获取最顶层的流程实例ID
	 * @author laoqunzhao 2018-07-09
	 * @param taskId 任务ID
	 * @return 最顶层的流程实例ID
	 */
	String getTopProcessInstanceIdByTaskId(String taskId);
	
	/**
	 * 获取最顶层的流程实例ID,用于任务未持久化情形
	 * @author laoqunzhao 2018-07-10
	 * @param taskEntity TaskEntity
	 * @return 最顶层的流程实例ID
	 */
	String getTopProcessInstanceId(TaskEntity taskEntity);
	
	/**
	 * 获取最顶层的流程实例ID,用于流程对象未持久化情形
	 * @author laoqunzhao 2018-07-10
	 * @param execution ExecutionEntity
	 * @return 最顶层的流程实例ID
	 */
	String getTopProcessInstanceId(ExecutionEntity execution);
	
	/**
	 * 获取最顶层的流程实例ID,没有父层返回当前的流程实例ID
	 * @author laoqunzhao 2018-05-03
	 * @param processInstanceId 流程实例ID
	 * @return 最顶层的流程实例ID
	 */
	String getTopProcessInstanceId(String processInstanceId);

	/**
	 * 获取父层的流程实例ID
	 * @author laoqunzhao 2018-05-03
	 * @param processInstanceId 流程实例ID
	 * @return 父层的流程实例ID
	 */
	String getSuperProcessInstanceId(String processInstanceId);
	
	
	/**
	 * 根据流程定义获取第一个用户节点
	 * @author ZhangJun
	 * @createTime 2018/2/4
	 * @reurn 第一个用户节点
	 */
	UserTask getFirstUserTask(String processDefinitionId);
    
    /**
     * 根据流程定义ID和任务节点ID获取用户任务节点
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionId 流程定义ID
     * @param taskDefinitionId 任务节点ID
     * @return 用户任务节点
     */
    UserTask getUserTask(String processDefinitionId, String taskDefinitionId);

    /**
     * 获取任务节点表单
     * @author laoqunzhao 2018-05-04
     * @param processDefinitionId
     * @param taskDefinitionId
     * @param isMobile 是否移动端URL
     * @return 任务节点表单地址
     */
    //String getTaskUrl(String processDefinitionId, String taskDefinitionId, boolean isMobile);
    
    /**
     * 根据配置重新分配办理人，通过监听实现
     * @author laoqunzhao 2018-05-04
     * @param taskEntity 从GlobalEventListener获取的TaskEntity
     */
    void taskAssignee(TaskEntity taskEntity);
    
    /**
     * 分配办理人
     * @author laoqunzhao 2018-07-11
     * @param taskEntity 任务
     * @param assignList 办理人ID
     */
    void taskAssignee(TaskEntity taskEntity, List<String> assignList);
    
    /**
     * 根据配置重新分配代办人，通过监听实现
     * @author laoqunzhao 2018-05-04
     * @param taskEntity 从GlobalEventListener获取的TaskEntity
     */
    void taskDelegate(TaskEntity taskEntity);

	/**
	 * 根据配置重新分配代办人，通过监听实现（异步）
	 * @author laoqunzhao 2018-05-04
	 * @param taskEntity 从GlobalEventListener获取的TaskEntity
	 */
	void taskDelegateAsynchron(TaskEntity taskEntity, List<String> bpmUserIds);

    /**
     * 将url与参数通过传统url&的方式拼接
     * @author laoqunzhao 2018-05-04
     * @param url url
     * @param params  流程参数
     * @return 拼接后的url
     * @throws UnsupportedEncodingException
     */
    String joinProcessParams(String url, Map<String, Object> params) throws UnsupportedEncodingException;

    /**
	 * 将JSON变量集转换为Map结构
	 * @author laoqunzhao 2018-05-04
	 * @param jsonStr JSON格式的变量集
	 * @return Map结构的变量集
	 */
    Map<String, Object> jsonToVariables(String jsonStr);

    /**
	 * 在JSONArray中根据关键字查找流程定义信息，并加到JSONArray中
	 * @author laoqunzhao 2018-05-03
	 * @param array JSONArray
	 * @param jsonKeyField JSONArray中JSONObject的关键字
	 */
    void appendProcessInfo(JSONArray array, String jsonKeyField);


    /**
     * 将流程模型集合转换为JSONArray
     * @author laoqunzhao 2018-05-03
     * @param definitions 流程定义集合
     * @param includeDeployment 是否包含流程发布信息
     * @return JSONArray格式的流程定义集合
     */
	JSONArray definitionsToJsonArray(List<ProcessDefinition> definitions, boolean includeDeployment);

	/**
     * 将流程定义集合转换为JSONObejct
     * @author laoqunzhao 2018-05-03
     * @param definition 流程定义
     * @param includeDeployment 是否包含流程发布信息
     * @return JSONObject格式的流程定义
     */
	JSONObject definitionToJsonObject(ProcessDefinition definition, boolean includeDeployment);

	/**
     * 根据流程任务节点设置获取流程变量值，并与新的流程变量合并
     * @author laoqunzhao 2018-05-20
     * @param task 流程任务
     * @param variables 新流程变量
     * @return 变量值Map
     */
	Map<String, Object> getVariables(Task task, Map<String, Object> variables);

	/**
     * 根据流程任务节点设置获取流程变量值，并与新的流程变量合并
     * @author laoqunzhao 2018-05-20
     * @param bpmList 申请单
     * @param task 流程任务
     * @param variables 新流程变量
     * @return 变量值Map
     */
	Map<String, Object> getVariables(ActBpmListEntity_HI bpmList, Task task, Map<String, Object> variables);

	
	/**
	 * 根据流程定义ID获取Gooflow格式流程JSON
     * @author laoqunzhao 2018-07-30
	 * @param processDefinitionId 流程定义ID
	 * @return JSONObject
	 */
	JSONObject getGooflowProcessJSON(String processDefinitionId);

	/**
	 * 根据流程实例ID获取Gooflow格式流程JSON
     * @author laoqunzhao 2018-07-30
	 * @param processInstanceId 流程实例ID
	 * @param markActive 是否标记当前活动节点 true.是  false.否
	 * @param markHistory 是否标记历史路径  true.是  false.否 
	 * @return JSONObject
	 */
	JSONObject getGooflowProcessJSON(String processInstanceId, boolean markActive, boolean markHistory);

	/**
	 * 根据流程定义ID获取BpmnModel
	 * @author laoqunzhao 2018-05-20
	 * @param processDefinitionId 流程定义ID
	 * @return BpmnModel
	 */
	BpmnModel getBpmnModel(String processDefinitionId);

	/**
	 * 设置流程启动变量
	 * @author laoqunzhao 2018-09-10
	 * @param variables 流程变量
	 * @param bpmList 申请单
	 * @param starter 发起人
	 */
	void setStartVariables(Map<String, Object> variables, ActBpmListEntity_HI bpmList, ActIdUserEntity_RO starter);
}
