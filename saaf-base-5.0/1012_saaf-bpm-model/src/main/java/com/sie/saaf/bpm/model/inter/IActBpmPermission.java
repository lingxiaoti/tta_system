package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmPermissionEntity_HI;

import java.util.List;

public interface IActBpmPermission {

	
	/**
	 * 根据流程定义KEY查询流程发起权限
	 * @author laoqunzhao 2018-06-27
     * @param processDefinitionKey 流程定义KEY
     * @return List<ActBpmPermissionMngEntity_HI>
	 */
	List<ActBpmPermissionEntity_HI> findByProcessDefinitionKey(String processDefinitionKey);
	
	/**
	 * 判断是否有权限
	 * @author laoqunzhao 2018-06-28
     * @param processDefinitionKey 流程定义KEY
     * @param ouIds 事业部ID
     * @return true有 false否
	 */
	boolean hasPermission(String processDefinitionKey, List<Integer> ouIds);
	
    /**
	 * 保存流程发起权限
	 * @author laoqunzhao 2018-06-27
	 * @param paramJSON JSON格式entity
	 * processDefinitionKey: 流程定义KEY,
	 * toAll: 所有用户,
	 * [
	 *   ouId: 事业部ID,
	 *   orgId: 部门ID,
	 *   positionId: 职位ID
	 * ]
	 * @return 成功:"S",失败:提示信息
	 */
	String save(JSONObject paramJSON);
	
	
	/**
	 * 物理删除流程管理权限
	 * @author laoqunzhao 2018-06-27
     * @param processDefinitionKey 流程定义KEY
	 */
	void destory(String processDefinitionKey);

	
	
}
