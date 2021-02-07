package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActUserTaskEntity_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface IActBpmTask {

	/**
	 * 流程待办任务查询
	 * @author laoqunzhao 2018-04-28
     * @param parameters JSONObject
     * userId BPM用户ID
     * drafter 起草人
     * createdBy 发起人ID
     * listCode 流程编号
     * listName 流程名称
     * title 流程标题
     * businessKey 业务主键
     * categoryId 流程分类
     * systemCode 系统代码
     * processDefinitionKey 流程定义Key，条件=
     * startDate 任务起始时间，格式yyyy-MM-dd
     * endDate 任务截止时间，格式yyyy-MM-dd
     * applyStartDate 流程发起起始时间，格式yyyy-MM-dd
     * applyEndDate 流程发起截止时间，格式yyyy-MM-dd
     * communicated 发起沟通 Y.是   N.否
     * exceptional 异常   Y.是   N.否
     * status PENDING.未接收， RESOLVING.办理中
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return 流程发起数据集合Pagination<ActBpmListEntity_HI>
	 * @throws Exception 
	 */
	Pagination<ActBpmTaskEntity_HI_RO> findTodoTasks(JSONObject parameters, ActIdUserEntity_RO user, Integer pageIndex, Integer pageRows) throws Exception;

	/**
     * 查询正在办理的任务
     * @author laoqunzhao 2018-06-19
     * @param processInstanceId 流程实例ID
     * @return List<Task>
     */
	List<Task> findActiveTasks(String processInstanceId);

	/**
     * 查询任务
     * @author laoqunzhao 2018-05-04
     * @param taskId 任务ID
     * @return Task
     */
	Task get(String taskId);

	/**
	 * 查询任务
	 * @author laoqunzhao 2018-05-04
	 * @param taskId 任务ID
	 * @return ActBpmTaskEntity_HI_RO
	 */
	ActBpmTaskEntity_HI_RO getBpmTaskById(String taskId);

	/**
	 * 查询顶层父任务
	 * @author laoqunzhao 2018-05-04
	 * @param parentTaskId 父任务ID
	 * @return Task
	 */
	Task getTopParentTask(String parentTaskId);

    /**
     * 完成待办任务
     * @author laoqunzhao 2018-05-04
     * @param task 任务
	 * @param bpmList 申请单
     * @param delegateId 代办ID
     * @param variables 流程变量
     * @param properties 流程表单字段
     * @param user 办理人
     * @param saveonly 是否只保存不办理，true是，false否
     * @param callback 是否回调
     */
    void complete(Task task, ActBpmListEntity_HI bpmList, Integer delegateId, Map<String, Object> variables, Map<String, String> properties,
				  ActIdUserEntity_RO user, boolean saveonly, boolean callback);

    /**
     * 自动跳过任务
     * @author laoqunzhao 2018-07-07
     * @param processInstance 流程实例
     * @param user 办理人
     */
	String jumpSameUserOrEmptyTask(ProcessInstance processInstance, ActIdUserEntity_RO user);

	/**
     * 批量处理待办任务
     * @author laoqunzhao 2018-06-04
     * @param taskIds 任务ID
     * @param properties 流程表单字段
     * @param user 办理人
     * @throws Exception
     */
    void batchComplete(List<String> taskIds, Map<String, Object> variables, Map<String, String> properties, ActIdUserEntity_RO user)
			throws Exception;

	/**
	 * 自动委托转办正在进行的流程
	 * @author laoqunzhao 2020-02-20
	 * @return Task
	 */
	void AutoSetAssignee();

    /**
     * 流程撤回
     * @author laoqunzhao 2018-06-19
     * @param processInstanceId 流程实例ID
	 * @param taskId 任务ID
     * @param variables 流程变量
     * @param properties 流程表单字段
     * @param user 办理人
     * @return 办理成功"S",不成功返回提示
     */
    String revoke(String processInstanceId, String taskId, Map<String, Object> variables, Map<String, String> properties, ActIdUserEntity_RO user)throws Exception;

	/**
	 * 查询撤回状态
	 * @param processInstanceId 流程实例ID
	 * @param taskId 任务ID
	 * @return true/false
	 * @throws Exception
	 */
	boolean getRevokeStatus(String processInstanceId, String taskId) throws Exception;

    /**
     * 任务签收，签收后只有签收人可办理
     * @author laoqunzhao 2018-05-04
     * @param taskId 任务ID
     * @param userId 签收人ID
     */
    void claim(String taskId, String userId);

    /**
     * 取消任务签收
     * @author laoqunzhao 2018-05-04
     * @param taskId 任务ID
     */
	void unclaim(String taskId);

	/**
     * 任务转办
     * @author laoqunzhao 2018-07-07
     * @param taskIds 任务ID
     * @param bpmUserId 流程用户ID
     */
	void transfer(List<String> taskIds, String bpmUserId);

	/**
     * 任务锁定，通过tenantId标识，1.锁定办理，2.锁定撤回
     * @author laoqunzhao 2018-06-20
     * @param taskId 任务ID
     * @param userId 办理人
     */
	String lock(String taskId, String userId);

	/**
     * 撤回锁定，通过tenantId标识，1.锁定办理，2.锁定撤回
     * @author laoqunzhao 2018-06-20
     * @param processInstanceId 流程实例ID
     */
	String lock(String processInstanceId);

	/**
     * 实现任意节点跳转
     * @author laoqunzhao 2018-05-04
     * @param executionId 执行ID，一般为流程实例ID
     * @param activityId 节点ID
     * @param taskId 任务ID
     */
	void jump(String executionId, String activityId, String taskId);

    /**
     * 获取任务办理人
     * @author laoqunzhao 2018-05-19
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionId 任务定义ID
     * @return Activiti用户ID集合
     */
	List<Object> getTaskBpmUserIds(String processInstanceId, String taskDefinitionId);

	/**
     * 获取任务办理人
     * @author laoqunzhao 2018-05-19
     * @param bpmListId 申请单ID
     * @param processDefinitionId 流程定义ID（当流程未发起根据流程ID获取任务办理人）
     * @param taskDefinitionId 任务定义ID
     * @return Activiti用户ID集合
     */
	List<Object> getTaskBpmUserIds(Integer bpmListId, String processDefinitionId, String taskDefinitionId);

	/**
	 * 获取流程抄送人ID
	 * @author laoqunzhao 2018-05-19
	 * @param taskId 任务ID
	 * @return 抄送人ID
	 * @throws Exception
	 */
	List<String> getTaskCcBpmUserIds(String taskId);

	/**
	 * 获取流程实例中任务的办理人,用于流程自定指定人
	 * @author laoqunzhao 2018-05-23
	 * @param processInstanceId 流程实例ID
	 * @param processDefinitionId 流程定义ID
	 * @param taskDefinitionId 任务定义ID
	 * @return 流程任务及指派人
	 * @throws Exception
	 */
	ActUserTaskEntity_RO getTaskAssignee(String processInstanceId, String processDefinitionId,
										 String taskDefinitionId) throws Exception;

	/**
	 * 查询具体任务的需指定办理人的任务节点及已指定的办理人,流程发起页面
	 * @author laoqunzhao 2018-05-23
	 * @param bpmListId 申请单ID
	 * @return 需指定人的任务节点及办理人
	 * @throws Exception
	 */
	List<ActUserTaskEntity_RO> getFirstTaskAssignees(Integer bpmListId) throws Exception;


	/**
	 * 查询具体任务的需指定办理人的任务节点及已指定的办理人,用于任务办理页面
	 * @author laoqunzhao 2018-05-23
	 * @param taskId 任务ID
	 * @return 需指定人的任务节点及办理人
	 * @throws Exception
	 */
	List<ActUserTaskEntity_RO> getTaskAssignees(String taskId) throws Exception;



	List<String> getTaskBpmUserIds(String taskId);

	/**
	 * 添加子任务
	 * @author laoqunzhao 2018-06-19
	 * @param parentTaskId 父任务ID
	 * @param taskName 任务名称
	 * @param description 任务描述
	 * @param parentUserId 父任务办理人ID
	 * @param taskUserId 任务办理人ID
	 * @return 办理成功"S",不成功返回提示
	 */
	String addSubTask(String parentTaskId, String taskName, String description,
					  Integer parentUserId, Integer taskUserId);

	/**
	 * 流程任务抄送
	 * @param taskId 任务ID
	 * @param user 抄送人
	 */
	void ccTask(String taskId, ActIdUserEntity_RO user);

	/**
	 * 流程任务抄送(异步)
	 * @param taskId 任务ID
	 * @param user 抄送人
	 */
	void ccTaskAsynchron(String taskId, ActIdUserEntity_RO user);


}
