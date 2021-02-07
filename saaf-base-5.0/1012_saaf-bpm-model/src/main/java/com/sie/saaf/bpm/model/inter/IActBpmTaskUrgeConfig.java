package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmTaskUrgeConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskUrgeConfigEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IActBpmTaskUrgeConfig {
	
	/**
     * 催办设置查询
     * @author sie-laoqunzhao 2018-07-05
     * @param parameters JSONObject
     * categoryId 流程分类ID
     * processDefinitionKey 流程定义Key，条件=
     * startDate 起始时间，格式yyyy-MM-dd
     * endDate 截止时间，格式yyyy-MM-dd
     * disabled 是否禁用
     * deleteFlag 删除标记，1.已删除，0.未删除
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
    Pagination<ActBpmTaskUrgeConfigEntity_HI_RO> findUrgeConfig(
			JSONObject parameters, Integer pageIndex, Integer pageRows);
    
    /**
     * 根据主键查询催办设置
     * @author sie-laoqunzhao 2018-07-05
     * @param configId 主键
     * @return ActBpmTaskUrgeConfigEntity_HI
     */
    ActBpmTaskUrgeConfigEntity_HI getById(int configId);

    /**
     * 根据流程定义KEY查询催办设置
     * @author sie-laoqunzhao 2018-07-05
     * @param processDefinitionKey 流程定义KEY
     * @return ActBpmTaskUrgeConfigEntity_HI
     */
	ActBpmTaskUrgeConfigEntity_HI getByProcessDefinitionKey(String processDefinitionKey);
    
	/**
	 * 保存催办设置
	 * @author sie-laoqunzhao 2018-07-05
	 * @param paramJSON JSON格式entity
	 * {
	 * configId 主键
	 * startTime 开始时间
	 * endTime 结束时间
	 * processDefinitionKey 流程定义KEY
	 * timeout 超时催办天数
	 * timegap 催办间隔小时
	 * disabled 是否禁用
	 * operatorUserId 操作人ID
	 * }
	 * @param userId 操作人ID
	 */
	void saveOrUpdate(JSONObject paramJSON, int userId) throws Exception;

	/**
	 * 保存催办设置
	 * @author sie-laoqunzhao 2018-07-05
	 * @param paramsJSON JSON格式entity
	 * {
	 * configId 主键
	 * startTime 开始时间
	 * endTime 结束时间
	 * processDefinitionKeys 流程定义KEY[]
	 * timeout 超时催办天数
	 * timegap 催办间隔小时
	 * disabled 是否禁用
	 * operatorUserId 操作人ID
	 * }
	 * @param userId 操作人ID
	 * @throws Exception
	 */
	void saveConfigs(JSONObject paramsJSON, int userId) throws Exception;
	
	/**
	 * 更新催办设置状态
	 * @author sie-laoqunzhao 2018-07-05
     * @param paramJSON JSONObject
     * {
     * configIds JSONArray 代办ID
     * disabled 是否禁用
     * operatorUserId 操作人ID
     * }
	 */
	void updateStatus(JSONObject paramJSON);
	
	/**
	 * 标记删除催办设置
	 * @author sie-laoqunzhao 2018-07-05
     * @param paramJSON JSONObject
     * {
     * configIds JSONArray 代办ID
     * }
	 */
	void delete(JSONObject paramJSON);
	
	/**
	 * 物理删除代办设置
     * @param paramJSON JSONObject
     * configIds JSONArray 代办ID
	 */
	void destory(JSONObject paramJSON);

	List<ActBpmTaskEntity_HI_RO> getToUrgeTasks(String processDefinitionKey);

}
