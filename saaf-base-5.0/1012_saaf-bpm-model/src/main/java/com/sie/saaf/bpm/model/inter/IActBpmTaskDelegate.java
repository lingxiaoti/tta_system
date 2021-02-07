package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface IActBpmTaskDelegate {
	
	/**
     * 流程代办查询
     * @param parameters JSONObject
     * searchKey 流程标题、流程名称、流程编码、任务名称
     * processDefinitionKey 流程唯一标识
     * processInstanceId 流程实例ID
     * type 1.委托  2及其他。代理
     * status PENDING待办；CANCELED取消；BACKED退回；FINISHED完成；DESTROYED作废
     * deleteFlag 删除标记，1.已删除，0.未删除
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
	Pagination<ActBpmTaskDelegateEntity_HI> findDelegates(JSONObject parameters, Integer pageIndex, Integer pageRows);

	ActBpmTaskDelegateEntity_HI getById(Integer delegateId);
	
	List<ActBpmTaskDelegateEntity_HI> findByProperty(Map<String, Object> properties);
	
	List<ActBpmTaskDelegateEntity_HI> findByTaskId(String taskId);
	
	boolean hasDelegatePermission(String taskId, Integer delegateUserId);
	
	/**
	 * 保存代办
	 * @author laoqunzhao 2018-05-30
	 * @param paramJSON JSONObject
	 * taskIds 任务ID JSONArray
	 * operatorUserId 委托人ID
	 * delegateUserId 代办人ID
	 */
	boolean save(JSONObject paramJSON);
	
	boolean save(ActBpmTaskDelegateEntity_HI taskDelegate);
	
	void saveTaskDelegates(List<Integer> categoryIds, List<String> processDefinitionKeys,
						   Integer clientUserId, Integer delegateUserId) throws Exception;

	void updateStatus(Integer id, String status);

	void updateStatus(JSONObject paramJSON);

	void delete(JSONObject paramJSON);

	void destory(JSONObject paramJSON);

	void updateStatus(List<Integer> categoryIds, List<String> processDefinitionKeys, Integer clientUserId,
					  String status) throws Exception;

}
