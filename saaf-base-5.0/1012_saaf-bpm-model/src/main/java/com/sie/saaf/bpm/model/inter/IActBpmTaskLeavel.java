package com.sie.saaf.bpm.model.inter;

import com.sie.saaf.bpm.model.entities.ActBpmTaskLeavelEntity_HI;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

public interface IActBpmTaskLeavel {
	
	/**
	 * 查询任务层级关系
	 * @author laoqunzhao 2018-07-09
	 * @param taskId 任务ID
	 */
	ActBpmTaskLeavelEntity_HI getById(String taskId);
	
    /**
	 * 保存任务层级关系
	 * @author laoqunzhao 2018-07-09
	 * @param taskEntity 任务
	 */
	void save(TaskEntity taskEntity);

}
