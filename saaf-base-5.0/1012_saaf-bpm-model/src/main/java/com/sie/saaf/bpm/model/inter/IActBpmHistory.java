package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticEntity_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface IActBpmHistory{

	/**
	 * 办理历史查询
	 * @author laoqunzhao 2018-04-28
     * @param parameters JSONObject
     * createdBy 发起人ID
     * drafter 发起人
     * listCode 流程编号
     * listName 流程名称
     * title 流程标题
     * businessKey 业务主键
	 * billNo 业务申请单号
     * categoryId 流程分类
     * systemCode 系统代码
     * processDefinitionKey 流程定义Key，条件=
     * processInstanceId 流程实例ID，条件=
     * startDate 任务起始时间，格式yyyy-MM-dd
     * endDate 任务截止时间，格式yyyy-MM-dd
     * communicated 发起沟通 Y.是   N.否
     * status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
     * taskStatus 任务状态  PENDING.未接收   RESOLVING.办理中  RESOLVED.已办结
     * simple 只返回主要字段 true.是  false.否
     * duration 用时时长(>=)秒
     * waiting 距今时长(>=)秒
     * userId 办理人
     * excludeDraft 排除起草  true.是   false.否
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return 流程发起数据集合Pagination<ActBpmListEntity_HI>
	 * @throws Exception 
	 */
	Pagination<ActBpmHiTaskEntity_HI_RO> findHistoricTasks(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

	 /**
     * 查询任务
     * @author laoqunzhao 2018-06-20
     * @param taskId 任务ID
     * @return HistoricTaskInstance
     */
	HistoricTaskInstance getByTaskId(String taskId);
	
	/**
     * 查询任务
     * @author laoqunzhao 2018-08-02
     * @param taskId 任务ID
     * @return ActBpmHiTaskEntity_HI_RO
     */
	ActBpmHiTaskEntity_HI_RO getBpmHistoricTaskByTaskId(String taskId);

	/**
	 * 根据任务主键集查询任务
	 * @author laoqunzhao 2018-08-02
	 * @param taskIds 任务ID
	 * @return List<ActBpmHiTaskEntity_HI_RO>
	 */
	List<ActBpmHiTaskEntity_HI_RO> findBpmHistoricTasksByTaskIds(List<String> taskIds);
	
	/**
     * 查询子任务的顶层任务
     * @author laoqunzhao 2018-06-20
     * @param taskId 任务ID
     * @return HistoricTaskInstance
     */
	HistoricTaskInstance getTopTaskByTaskId(String taskId);
	
	/**
     * 查询流程实例
     * @author laoqunzhao 2018-06-20
     * @param processInstanceId 流程实例ID
     * @return HistoricProcessInstance
     */
	HistoricProcessInstance getByProcessInstanceId(String processInstanceId);
	
	/**
     * 根据流程实例ID查询流程处理记录
     * @author laoqunzhao 2018-04-27
     * @param processInstanceId 流程实例ID
     * @param extendCall 是否展开获取子流程中的历史任务
     * @return 流程处理记录List<HistoricActivityInstance>
     */
    List<HistoricActivityInstance> findHistoricActivities(String processInstanceId, boolean extendCall);
    
    
    /**
     * 根据流程实例ID获取流程中最后一个执行中任务及最后一个已完成的任务（当前环节、上一环节）
     * @author laoqunzhao 2018-04-27
     * @param processInstanceIds 流程实例ID集
	 * @param type 1.当前任务 2.最后一次任务 其他.所有
     * @return 历史记录集（当前环节、上一环节）List<ActBpmHiTaskEntity_HI_RO>
     */
    List<ActBpmHiTaskEntity_HI_RO> findHistoricTasks(List<String> processInstanceIds, Integer type);

	/**
	 * 根据流程实例ID获取表单MenuCode
	 * @author laoqunzhao 2018-04-27
	 * @param processInstanceId 流程实例ID
	 * @return MenuCode
	 */
	String getMenuCode(String processInstanceId);
    
    /**
     * 根据任务ID获取任务表单详情
     * @author laoqunzhao 2018-04-27
     * @param taskIds 任务ID集
     * @return 任务表单字段集合List<HistoricDetail>
     */
    List<HistoricDetail> findHistoricDetails(List<String> taskIds);

	/**
	 * 查询流程表单字段
	 * @author laoqunzhao 2018-04-27
	 * @param processInstanceId 任务ID集
	 * @param name 字段名
	 * @param draft 只查询提交表单
	 * @return 流程表单字段集合List<HistoricDetail>
	 */
	List<HistoricDetail> findHistoricDetails(String processInstanceId, String name, boolean draft);

    /**
     * 将流程中当前环节及上一环节的信息写入JSONArray结果 集
     * @author laoqunzhao 2018-04-27
     * @param array JSONArray结果 集
     * @param jsonKeyField 结果集中存放流程实例的key
     */
    void appendCurPrevTask(JSONArray array, String jsonKeyField);

    /**
     * 历史任务表单详情的信息写入JSONArray结果 集
     * @author laoqunzhao 2018-04-27
     * @param array JSONArray结果 集
     * @param jsonField 结果集中存放任务ID的key
     */
    void appendHistoricDetail(JSONArray array, String jsonField);

    /**
     * 查询流程的的任务记录
     * @author laoqunzhao 2018-06-19
     * @param processInstanceId 流程实例ID
     * @return List<HistoricTaskInstance>
     */
	List<HistoricTaskInstance> findHistoricTasks(String processInstanceId);

	/**
	 * 历史任务统计
	 * @param queryParamJSON
	 * {
	 *  startDate: 开始时间,
	 *  endDate: 结束时间
	 * }
	 * @return ActStatisticEntity_RO
	 */
	ActStatisticEntity_RO taskStatistic(JSONObject queryParamJSON);

	/**
	 * 审批表单信息查询
	 * @author laoqunzhao 2018-08-03
     * @param queryParamJSON
     * {
     * userId 办理人流程用户ID
     * name 表单属性名称
     * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return Pagination<String>
	 */
	Pagination<String> findHistoricTextDetail(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 可驳回/重审节点查询
	 * @param task 任务
	 * @param type 1.驳回  2.重审
	 * @return
	 */
	JSONArray getRetrialTaskNodes(Task task, int type);

	/**
	 * 查询驳回重审状态
	 * @param processInstanceId 流程实例ID
	 */
	boolean getRetrialStatus(String processInstanceId);

	/**
	 * 查询历史变量
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	Map<String, Object> getHistoricVariables(String processInstanceId);


}
