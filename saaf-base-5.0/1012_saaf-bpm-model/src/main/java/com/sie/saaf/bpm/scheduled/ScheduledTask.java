package com.sie.saaf.bpm.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmExceptionEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.*;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {
	
	private  static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
	
	@Autowired
	private IActBpmModel bpmModelServer;
	
	@Autowired
	private IActBpmCommunicate bpmCommunicateServer;
	
	@Autowired
	private IActBpmTaskUrgeConfig bpmTaskUrgeConfigServer;
	
	@Autowired
	private IActBpmTask bpmTaskServer;
	
	@Autowired
	private IActBpmProcess bpmProcessServer;
	
	@Autowired
	private IActBpmException bpmExceptionServer;

	@Autowired
	private JedisCluster jedisCluster;

	
	/**
	 * 自动催办
	 */
	@Scheduled(cron="0 0/20 * * * ?")
	public void urgeTask() {
		if(getLock("BPM_SHD_URG", String.valueOf(System.currentTimeMillis()), 600000)){
			LOGGER.info("scheduled task for urge...");
			JSONObject paramsJSON = new JSONObject();
			paramsJSON.put("deployed", true);
			List<ActBpmModelEntity_HI_RO> models = bpmModelServer.findModels(paramsJSON, 1, Integer.MAX_VALUE).getData();
			if(models == null || models.isEmpty()) {
				return;
			}
			for(ActBpmModelEntity_HI_RO model: models) {
				List<ActBpmTaskEntity_HI_RO> tasks = bpmTaskUrgeConfigServer.getToUrgeTasks(model.getKey());
				if(tasks != null && !tasks.isEmpty()) {
					bpmCommunicateServer.save(tasks);
				}
			}
		}
    }
	
	/**
	 * 异常处理，检查超时任务
	 */
	@Scheduled(cron="0 0 1-6 * * ?")
	public void exceptionTask() {
		try {
			if(getLock("BPM_SHD_UDO", String.valueOf(System.currentTimeMillis()), 600000)) {
				LOGGER.info("scheduled task for timeout task...");
				JSONObject paramsJSON = new JSONObject();
				Date endDate = new Date(new Date().getTime() - 5 * 24 * 3600 * 1000);
				paramsJSON.put("exceptional", 0);
				paramsJSON.put("endDate", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
				int pageIndex = 1;
				int pageRows = 100;
				List<ActBpmTaskEntity_HI_RO> tasks = bpmTaskServer.findTodoTasks(paramsJSON, null, pageIndex, pageRows).getData();
				while(tasks != null && !tasks.isEmpty()) {
					for (ActBpmTaskEntity_HI_RO task : tasks) {
						if(StringUtils.isBlank(task.getBpm_processInstanceId())){
							continue;
						}
						ActBpmExceptionEntity_HI exception = new ActBpmExceptionEntity_HI();
						exception.setOperatorUserId(-1);
						exception.setProcessDefinitionKey(task.getProcessDefinitionKey());
						exception.setProcessInstanceId(task.getBpm_processInstanceId());
						exception.setTaskId(task.getTaskId());
						exception.setTaskName(task.getTaskName());
						exception.setType(WorkflowConstant.EXCEPTION_TYPE_TIMEOUT);
						bpmExceptionServer.save(exception);
					}
					tasks = bpmTaskServer.findTodoTasks(paramsJSON, null, ++pageIndex, pageRows).getData();
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
    }
	
	@Scheduled(cron="0 0 1-6 * * ?")
	public void dueExceptionalTask() {
		try {
			if(getLock("BPM_SHD_EXC", String.valueOf(System.currentTimeMillis()), 600000)) {
				LOGGER.info("scheduled task for exceptional task...");
				JSONObject paramsJSON = new JSONObject();
				paramsJSON.put("status", 0);
				paramsJSON.put("deleteFlag", 0);
				paramsJSON.put("type", "ASSIGNEE");
				int pageIndex = 1;
				int pageRows = 100;
				List<ActBpmExceptionEntity_HI> exceptions = bpmExceptionServer.findExceptions(paramsJSON, pageIndex, pageRows).getData();
				while (exceptions != null && !exceptions.isEmpty()) {
					for (ActBpmExceptionEntity_HI exception : exceptions) {
						Task task = bpmTaskServer.get(exception.getTaskId());
						if (task == null) {
							continue;
						}
						List<Object> bpmUserIds = bpmTaskServer.getTaskBpmUserIds(exception.getProcessInstanceId(), task.getTaskDefinitionKey());
						if (bpmUserIds == null || bpmUserIds.isEmpty()) {
							continue;
						}
						List<String> bpmUserIds_ = new ArrayList<String>();
						for (Object bpmUserId : bpmUserIds) {
							if (bpmUserId instanceof String) {
								bpmUserIds_.add(bpmUserId.toString());
							}
						}
						if (bpmUserIds_ == null || bpmUserIds_.isEmpty()) {
							continue;
						}
						bpmProcessServer.taskAssignee((TaskEntity) task, bpmUserIds_);
						exception.setStatus(1);
						exception.setOperatorUserId(-1);
						bpmExceptionServer.updateStatus(exception);
						LOGGER.info("task assignee:{} {}", task.getId(), JSON.toJSON(bpmUserIds));
					}
					exceptions = bpmExceptionServer.findExceptions(paramsJSON, ++pageIndex, pageRows).getData();
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
    }

	/**
	 * 获取锁
	 * @param lockKey 锁
	 * @param requestId 请求标识
	 * @param expireTime 超期时间
	 * @return 是否获取成功
	 */
	private boolean getLock(String lockKey, String requestId, int expireTime) {
		String result = jedisCluster.set(lockKey, requestId, "NX", "PX", expireTime);
		if ("OK".equals(result)) {
			return true;
		}
		return false;
	}


}
