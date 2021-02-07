package com.sie.saaf.bpm.listeners;

import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmTask;
import com.sie.saaf.bpm.model.inter.IActBpmTaskLeavel;
import com.sie.saaf.common.util.SpringBeanUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.*;


@Component
public class GlobalEventListener implements ActivitiEventListener{
	
	public final static Log LOGGER=LogFactory.getLog(GlobalEventListener.class);

	@Override
	public boolean isFailOnException() {
		return false;
	}

	@Override
	public void onEvent(ActivitiEvent event) {
		String type = event.getType().toString();
		switch(type) {
			case "TASK_CREATED": {
					insertTaskLeavel(event);
					taskAssignee(event);
				}
				break;
			case "PROCESS_COMPLETED":
				completeProcess(event);
				break;
			case "PROCESS_STARTED":
				startProcess(event);
				break;
			case "ACTIVITY_STARTED":
				ActivitiActivityEventImpl eventImpl1 = (ActivitiActivityEventImpl)event;
				if("org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior".equals(eventImpl1.getBehaviorClass())) {
					String activityId = eventImpl1.getActivityId();
					String processDefinitionId = eventImpl1.getProcessDefinitionId();
					BpmnModel bpmModel = eventImpl1.getEngineServices().getRepositoryService().getBpmnModel(processDefinitionId);
					FlowElement element = bpmModel.getMainProcess().getFlowElement(activityId);
					if(element instanceof UserTask) {
						UserTask task = (UserTask) element;
						String variableName = "assigneeList";
						MultiInstanceLoopCharacteristics characteristics = task.getLoopCharacteristics();
						if(characteristics != null && StringUtils.isNotBlank(characteristics.getInputDataItem())) {
							variableName = characteristics.getInputDataItem();
						}
						IActBpmTask bpmTaskServer = (IActBpmTask) getBean("actBpmTaskServer");
						List<Object> assigneeList = bpmTaskServer.getTaskBpmUserIds(eventImpl1.getProcessInstanceId(), activityId);
						if(assigneeList == null) {
							assigneeList = new ArrayList<Object>();
							assigneeList.add("");
						}
						eventImpl1.getEngineServices().getRuntimeService().setVariable(eventImpl1.getProcessInstanceId(), variableName, assigneeList);
					}
				}
				break;
			case "ENTITY_UPDATED": updateEntity(event);break;
		default:break;
		}
		/*if(type.equals("SEQUENCEFLOW_TAKEN")) {
		    ActivitiSequenceFlowTakenEventImpl eventImpl = (ActivitiSequenceFlowTakenEventImpl) event;
		    System.out.println(type+"  " + eventImpl.getId());
		}*/
	}
	
	/**
	 * 保存任务层级关系 
	 * @param event
	 */
	private void insertTaskLeavel(ActivitiEvent event) {
	    ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl)event;  
        TaskEntity taskEntity=(TaskEntity)eventImpl.getEntity();
        IActBpmTaskLeavel bpmTaskLeavelServer = (IActBpmTaskLeavel)getBean("actBpmTaskLeavelServer");
        bpmTaskLeavelServer.save(taskEntity);
	}
	
	/**
	 * 设置任务办理人
	 * @param event
	 */
	private void taskAssignee(ActivitiEvent event) {
	    ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl)event;  
        TaskEntity taskEntity = (TaskEntity)eventImpl.getEntity();
        IActBpmProcess bpmProcessServer = (IActBpmProcess)getBean("actBpmProcessServer");
        //非子任务重新设置办理人
        if(StringUtils.isBlank(taskEntity.getParentTaskId())){
        	bpmProcessServer.taskAssignee(taskEntity);
        }
		bpmProcessServer.taskDelegate(taskEntity);
	}
	
	private void completeProcess(ActivitiEvent event) {
	    ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl)event; 
	    IActBpmList bpmListServer = (IActBpmList)getBean("actBpmListServer");
	    ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(eventImpl.getProcessInstanceId());
        if(bpmList != null) {
            bpmList.setStatus(1);
            if(WorkflowConstant.STATUS_RUNNING.equals(bpmList.getResult())) {
            	bpmList.setResult(WorkflowConstant.STATUS_PASSED);
            }
            bpmList.setEndTime(new Date());
            bpmListServer.update(bpmList);
        }
	}
	
	private void startProcess(ActivitiEvent event) {
		ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl)event; 
		if(ExecutionEntity.class.equals(eventImpl.getEntity().getClass())) {
			ExecutionEntity executionEntity=(ExecutionEntity)eventImpl.getEntity(); 
			RuntimeService runtimeService = (RuntimeService)getBean("runtimeService");
			String superExecutionId = executionEntity.getSuperExecutionId();
			if(superExecutionId == null) {
				return;
			}
			Map<String, Object> variables = runtimeService.getVariables(superExecutionId);
			Map<String, Object> subVariables = new HashMap<String, Object>();
			if(variables != null && !variables.isEmpty()) {
				for(String key: variables.keySet()) {
					if(key.startsWith(WorkflowConstant.PUBLIC_VARIABLE_PREFIX)) {
						subVariables.put(key, variables.get(key));
					}
				}
				if(subVariables != null && !subVariables.isEmpty()) {
					runtimeService.setVariables(executionEntity.getProcessInstanceId(), subVariables);
				}
			}
		}
		
	}

	private void updateEntity(ActivitiEvent event) {
		ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl)event;
		if(ModelEntity.class.equals(eventImpl.getEntity().getClass())) {
			ModelEntity modelEntity = (ModelEntity)eventImpl.getEntity();
			JedisCluster jedisCluster = (JedisCluster)getBean("jedisCluster");
			String processDefinitionKey = modelEntity.getKey();
			if(StringUtils.isNotBlank(processDefinitionKey)){
				jedisCluster.hdel(WorkflowConstant.REDIS_PROC_FTASK_CFG, processDefinitionKey);
				jedisCluster.hdel(WorkflowConstant.REDIS_PROC_MOD_GOOFLOW_JSON, processDefinitionKey);
			}else{
				jedisCluster.del(WorkflowConstant.REDIS_PROC_FTASK_CFG);
				jedisCluster.del(WorkflowConstant.REDIS_PROC_MOD_GOOFLOW_JSON);
			}
		}

	}

	/*private void taskDelegate(TaskEntity taskEntity){
		try {
			IActBpmProcess bpmProcessServer = (IActBpmProcess)getBean("actBpmProcessServer");
			IActBpmTask bpmTaskServer = (IActBpmTask)getBean("actBpmTaskServer");
			List<String> bpmUserIds = bpmTaskServer.getTaskBpmUserIds(taskEntity.getId());
			bpmProcessServer.taskDelegateAsynchron(taskEntity, bpmUserIds);
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}*/
	
	
	private Object getBean(String beanName) {
		return SpringBeanUtil.getBean(beanName);
	}


}
