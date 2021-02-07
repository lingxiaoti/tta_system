package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.bpm.model.entities.ActBpmTaskLeavelEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmTaskLeavel;
import com.yhg.hibernate.core.dao.ViewObject;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("actBpmTaskLeavelServer")
public class ActBpmTaskLeavelServer implements IActBpmTaskLeavel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskLeavelServer.class);
	
	@Autowired
	private IActBpmProcess bpmProcessServer;
	
	@Autowired
	private ViewObject<ActBpmTaskLeavelEntity_HI> bpmTaskLeavelDAO_HI;
	
	public ActBpmTaskLeavelServer() {
		super();
	}
	
	/**
	 * 查询任务层级关系
	 * @author laoqunzhao 2018-07-09
	 * @param taskId 任务ID
	 */
	@Override
	public ActBpmTaskLeavelEntity_HI getById(String taskId) {
		return bpmTaskLeavelDAO_HI.getById(taskId);
	}

	/**
	 * 保存任务层级关系
	 * @author laoqunzhao 2018-07-09
	 * @param taskEntity 任务
	 */
	@Override
	public void save(TaskEntity taskEntity) {
		ActBpmTaskLeavelEntity_HI instance = new ActBpmTaskLeavelEntity_HI();
		instance.setTaskId(taskEntity.getId());
		instance.setParentTaskId(taskEntity.getParentTaskId());
		instance.setDeleteFlag(0);
		instance.setOperatorUserId(-1);
		if(StringUtils.isNotBlank(taskEntity.getParentTaskId())) {
			ActBpmTaskLeavelEntity_HI taskLeavel = getById(taskEntity.getParentTaskId());
			instance.setTopTaskId(taskLeavel.getTopTaskId());
			instance.setTopProcessInstanceId(taskLeavel.getTopProcessInstanceId());
		}else {
			String topProcessInstanceId = bpmProcessServer.getTopProcessInstanceId(taskEntity);
			instance.setTopProcessInstanceId(topProcessInstanceId == null? taskEntity.getProcessInstanceId(): topProcessInstanceId);
			instance.setTopTaskId(taskEntity.getId());
		}
		bpmTaskLeavelDAO_HI.save(instance);
		LOGGER.info("insert task leavel:" + JSON.toJSONString(instance));
	}
	
	
}
