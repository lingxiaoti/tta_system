package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmExceptionEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

public interface IActBpmException {

	/**
	 * 流程异常查询
	 * @author laoqunzhao 2018-07-07
     * @param queryParamJSON JSONObject
     * type 类型：任务无人处理：ASSIGNEE；超时：TIMEOUT
     * processDefinitionKey 流程唯一标识
     * processInstanceId 流程实例ID
     * deleteFlag 0.未删除 1.已删除
     * status 0.未处理  1.已处理
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
	 */
	Pagination<ActBpmExceptionEntity_HI> findExceptions(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
	
	/**
	 * 保存异常消息
	 * @author laoqunzhao 2018-07-07
	 * @param instance ActBpmExceptionEntity_HI
	 */
    void save(ActBpmExceptionEntity_HI instance);
    
    /**
	 * 保存异常消息
	 * @author laoqunzhao 2018-07-07
	 * @param taskEntity 任务
	 * @param type 类型   任务无人处理：ASSIGNEE；超时：TIMEOUT
	 * @param content 内容
	 */
    void save(TaskEntity taskEntity, String type, String content);
    
	
    /**
	 * 更新异常状态
	 * @author laoqunzhao 2018-07-07
     * @param processInstanceId 流程实例ID
     * @param status 0.未处理  1.已处理 
	 */
	void updateStatus(String processInstanceId, int status);

	/**
	 * 更新异常状态
	 * @author laoqunzhao 2018-07-07
     * @param instance ActBpmExceptionEntity_HI
	 */
	void updateStatus(ActBpmExceptionEntity_HI instance);
	


	
}
