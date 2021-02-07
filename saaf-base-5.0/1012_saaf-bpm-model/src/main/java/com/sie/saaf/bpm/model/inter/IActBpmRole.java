package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmRoleEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IActBpmRole {
	
	/**
	 * 根据角色KEY查找角色
	 * @author laoqunzhao 2018-04-27
	 * @param roleKey 角色KEY
	 * @return ActBpmRoleEntity_HI
	 */
	ActBpmRoleEntity_HI getByRoleKey(String roleKey);
	
	/**
	 * 根据流程KEY查询流程角色
	 * @author laoqunzhao 2018-04-27
     * @param roleKeys 角色KEY集合
     * @return List<ActBpmRoleEntity_HI>
	 */
	List<ActBpmRoleEntity_HI> findByKeys(List<String> roleKeys);

	/**
	 * 流程角色查询
	 * @author laoqunzhao 2018-04-27
     * @param queryParamJSON
     * {
     * searchKey KEY/名称
     * deleteFlag 删除标记，1.已删除，0.未删除
     * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return Pagination<ActBpmRoleEntity_HI>
	 */
    Pagination<ActBpmRoleEntity_HI> findRoles(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
	
    /**
	 * 保存角色
	 * @author laoqunzhao 2018-04-27
	 * @param paramsJSON
	 * {
	 * roleId: 主键（自增）,
	 * roleKey: 角色KEY,
	 * roleName: 角色名称,
	 * handlerExpression: 表达式,
	 * handlerExpressionType: 表达式类型："ASSIGN". 指定人，"SQL". SQL语句, "URL". URL服务调用
	 * }
	 */
	void save(JSONObject paramsJSON);
	
	/**
	 * 标记删除流程角色
	 * @author laoqunzhao 2018-04-27
     * @param paramJSON JSONObject
     * roleIds JSONArray 流程角色ID
	 */
	void delete(JSONObject paramJSON);
	
	/**
	 * 物理删除流程角色
	 * @author laoqunzhao 2018-04-27
     * @param paramJSON JSONObject
     * roleIds JSONArray 流程角色ID
	 */
	void destory(JSONObject paramJSON);

	/**
	 * 判断角色是否存在
	 * @author laoqunzhao 2018-04-27
	 * @param roleId 角色ID
	 * @param roleKey 角色KEY
	 * @param roleName 角色名称
	 * @return 存在true,不存在false
	 */
    boolean roleExist(Integer roleId, String roleKey, String roleName);

    /**
	 * 根据角色KEY获取相应的流程用户ID
	 * @author laoqunzhao 2018-04-27
	 * @param roleKeys 角色KEY集合
	 * @param processInstanceId 流程实例ID
	 * @return List<String>或List<List<String>> 流程用户ID
	 */
	List<Object> getRoleBpmUserIds(List<String> roleKeys, String processInstanceId);

	/**
	 * 根据角色KEY获取相应的流程用户ID
	 * @author laoqunzhao 2018-04-27
	 * @param roleKeys 角色KEY集合
	 * @param bpmListId 申请单ID
	 * @return List<String>或List<List<String>>流程用户ID
	 */
	List<Object> getRoleBpmUserIds(List<String> roleKeys, Integer bpmListId);

	/**
	 * 批量替换流程角色办理人
	 * @author laoqunzhao 2018-10-27
	 * @param roleIds 流程角色ID[]
	 * @param processerOld 原办理人
	 * @param processerNew 新办理人
	 * @param userId 操作人
	 */
	void updateRoleProcessers(List<Integer> roleIds, String processerOld, String processerNew, Integer userId);
}
