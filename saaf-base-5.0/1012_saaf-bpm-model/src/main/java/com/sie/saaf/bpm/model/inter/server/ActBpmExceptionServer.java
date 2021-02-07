package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmExceptionEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmException;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("actBpmExceptionServer")
public class ActBpmExceptionServer implements IActBpmException {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmExceptionServer.class);
	
	@Autowired
	private ViewObject<ActBpmExceptionEntity_HI> bpmExceptionDAO_HI;
	
	@Autowired
	private IActBpmList bpmListServer;
	
	@Autowired
	private IActBpmProcess bpmProcessServer;

	public ActBpmExceptionServer() {
		super();
	}
	
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
	@Override
	public Pagination<ActBpmExceptionEntity_HI> findExceptions(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("from ActBpmExceptionEntity_HI bean where 1=1");
		//类型
  		String type = StringUtils.trimToNull(queryParamJSON.getString("type"));
  		if(type != null) {
  			sql.append(" and upper(bean.type) = :type");
  			paramsMap.put("type", type.toUpperCase());
  		}
  		//流程KEY
  		String processDefinitionKey = StringUtils.trimToNull(queryParamJSON.getString("processDefinitionKey"));
  		if(processDefinitionKey != null) {
  			sql.append(" and upper(bean.processDefinitionKey) = :processDefinitionKey");
  			paramsMap.put("processDefinitionKey", processDefinitionKey.toUpperCase());
  		}
  	    //流程实例ID
  		String processInstanceId = StringUtils.trimToNull(queryParamJSON.getString("processInstanceId"));
  		if(processInstanceId != null) {
  			sql.append(" and bean.processInstanceId = :processInstanceId");
  			paramsMap.put("processInstanceId", processInstanceId);
  		}
  		//是否删除
  		String deleteFlag = StringUtils.trimToNull(queryParamJSON.getString("deleteFlag"));
  		if(deleteFlag != null) {
  			sql.append(" and bean.deleteFlag = :deleteFlag");
  			paramsMap.put("deleteFlag", Integer.parseInt(deleteFlag));
  		}
  		//状态
  		String status = StringUtils.trimToNull(queryParamJSON.getString("status"));
  		if(status != null) {
  			sql.append(" and bean.status = :status");
  			paramsMap.put("status", Integer.parseInt(status));
  		}
  		sql.append(" order by bean.exceptionId desc ");
  		Pagination<ActBpmExceptionEntity_HI> pagination = bpmExceptionDAO_HI.findPagination(sql.toString(), paramsMap, pageIndex, pageRows);
  		return pagination;
	}
	
	/**
	 * 流程异常查询
	 * @author laoqunzhao 2018-07-07
     * @param processInstanceId 流程实例ID
	 */
	public List<ActBpmExceptionEntity_HI> findByProcessInstanceId(String processInstanceId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("processInstanceId", processInstanceId);
		paramsMap.put("deleteFlag", 0);
		return bpmExceptionDAO_HI.findByProperty(paramsMap);
	}
	
	/**
	 * 保存异常消息
	 * @author laoqunzhao 2018-07-07
	 * @param instance ActBpmExceptionEntity_HI
	 */
	@Override
	public void save(ActBpmExceptionEntity_HI instance) {
		try {
			instance.setOperatorUserId(-1);
			instance.setDeleteFlag(0);
			instance.setStatus(0);
			bpmExceptionDAO_HI.save(instance);
			LOGGER.info("save ActBpmExceptionEntity_HI:{}", JSON.toJSON(instance));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 保存异常消息
	 * @author laoqunzhao 2018-07-07
	 * @param taskEntity 任务
	 * @param type 类型   任务无人处理：ASSIGNEE；超时：TIMEOUT
	 * @param content 内容
	 */
	@Override
	public void save(TaskEntity taskEntity, String type, String content) {
		String topProcessInstanceId = bpmProcessServer.getTopProcessInstanceId(taskEntity);
		ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(topProcessInstanceId);
		ActBpmExceptionEntity_HI instance = new ActBpmExceptionEntity_HI();
		instance.setType(type);
		instance.setContent(content);
		instance.setProcessDefinitionKey(bpmList.getProcessDefinitionKey());
		instance.setProcessInstanceId(bpmList.getProcessInstanceId());
		instance.setTaskId(taskEntity.getId());
		instance.setTaskName(taskEntity.getName());
		instance.setOperatorUserId(-1);
		instance.setDeleteFlag(0);
		instance.setStatus(0);
		bpmExceptionDAO_HI.save(instance);		
	}

	
    /**
	 * 更新异常状态
	 * @author laoqunzhao 2018-07-07
     * @param processInstanceId 流程实例ID
     * @param status 0.未处理  1.已处理 
	 */
	@Override
	public void updateStatus(String processInstanceId, int status) {
		List<ActBpmExceptionEntity_HI> instances = findByProcessInstanceId(processInstanceId);
		if(instances != null && !instances.isEmpty()) {
			for(ActBpmExceptionEntity_HI instance: instances) {
				if(instance.getStatus() != status) {
					instance.setOperatorUserId(-1);
					instance.setStatus(status);
					bpmExceptionDAO_HI.update(instance);
				}
			}
		}
	}
	
	/**
	 * 更新异常状态
	 * @author laoqunzhao 2018-07-07
     * @param instance ActBpmExceptionEntity_HI
	 */
	@Override
	public void updateStatus(ActBpmExceptionEntity_HI instance) {
		ActBpmExceptionEntity_HI exception = bpmExceptionDAO_HI.getById(instance.getExceptionId());
		if(exception != null) {
			exception.setStatus(instance.getStatus());
			exception.setOperatorUserId(instance.getOperatorUserId());
			bpmExceptionDAO_HI.update(exception);
		}
	}

}
