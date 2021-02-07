package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseDepartment;
import com.sie.saaf.bpm.constant.Variables;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActUserTaskEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.server.BaseAccreditCacheServer;
import com.yhg.hibernate.core.paging.Pagination;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.NativeProcessDefinitionQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import redis.clients.jedis.JedisCluster;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhangJun
 * @createTime 2018-02-04 18:07
 * @description
 */
@Component("actBpmProcessServer")
public class ActBpmProcessServer implements IActBpmProcess {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmCategoryServer.class);

	private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private ProcessEngine processEngine;
        	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
    private IActBpmList bpmListServer;
	
	@Autowired
    private IActBpmUser bpmUserServer;
	
	@Autowired
	private IActBpmModel bpmModelServer;
	
	@Autowired
	private IActBpmTask bpmTaskServer;
	
	@Autowired
	private IActBpmHistory bpmHistoryServer;
	
	@Autowired
	private IActBpmCategory bpmCategoryServer;
	
	@Autowired
	private IActBpmTaskConfig bpmTaskConfigServer;
	
	@Autowired
	private IActBpmTaskDelegate bpmTaskDelegateServer;
	
	@Autowired
	private IActBpmTaskDelegateConfig bpmTaskDelegateConfigServer;
	
	@Autowired
    private IBpmCallBack bpmCallBackServer;
	
	@Autowired
    private IBpmMessage bpmMessageServer;
	
	@Autowired
    private IActBpmException bpmExceptionServer;

	@Autowired
	private IBaseAccreditCache baseAccreditCacheServer;

	@Autowired
	private IBaseDepartment baseDepartmentServer;
	
	@Autowired
    private FormService formService;
	
	@Autowired
    private JedisCluster jedisCluster;
    
	
	/**
     * 流程定义查询
     * @author laoqunzhao 2018-05-03
     * @param parameters
     * processDefinitionKey 流程定义KEY
     * categoryId 流程分类ID
     * searchKey 流程定义KEY/流程名称
     * @param pageIndex 页码索引
	 * @param pageRows 每页记录数
     * @return Pagination<ProcessDefinition> 查询结果
     */
    @Override
    public Pagination<ProcessDefinition> findDefinitions(JSONObject parameters, Integer pageIndex, Integer pageRows) {
        Pagination<ProcessDefinition> pagination = new Pagination<ProcessDefinition>(pageIndex, pageRows);
        parameters = parameters == null?new JSONObject():parameters;
        try {
            StringBuffer sql = new StringBuffer(" FROM ACT_RE_PROCDEF PROC");
            Map<String, Object> params = new HashMap<String, Object>();
            //KEY查询
            String processDefinitionKey = StringUtils.trimToNull(parameters.getString("processDefinitionKey"));
			if(processDefinitionKey != null) {
				sql.append((params.isEmpty()?" WHERE":"AND") +" PROC.KEY_ = #{key1}");
				params.put("key1", processDefinitionKey);
			}
			//KEY/名称查询
            String searchKey = StringUtils.trimToNull(parameters.getString("searchKey"));
			if(searchKey != null) {
				sql.append((params.isEmpty()?" WHERE":"AND") +" (UPPER(PROC.KEY_) LIKE #{key2}");
				sql.append(" OR UPPER(PROC.NAME_) LIKE #{name})");
				params.put("key2", "%" + searchKey.toUpperCase() + "%");
				params.put("name", "%" + searchKey.toUpperCase() + "%");
			}
            //流程分类查询
			String categoryId = StringUtils.trimToNull(parameters.getString("categoryId"));
			if(categoryId != null) {
	            List<Integer> categoryIds = bpmCategoryServer.getCategoryIds(Integer.parseInt(categoryId));
	            sql.append(params.isEmpty()?" WHERE":"AND"); 
	            sql.append(" EXISTS(SELECT 1 FROM ACT_RE_DEPLOYMENT WHERE ID_=PROC.DEPLOYMENT_ID_ AND CATEGORY_ IN ('" + StringUtils.join(categoryIds, "','") + "'))");
			}
            NativeProcessDefinitionQuery processQuery = repositoryService.createNativeProcessDefinitionQuery().sql("SELECT COUNT(*) " + sql.toString());
            for(String key:params.keySet()) {
                processQuery.parameter(key, params.get(key));
            }
            long  count = processQuery.count();
            if(count>0) {
                processQuery = repositoryService.createNativeProcessDefinitionQuery().sql("SELECT PROC.* " + sql.toString());
                for(String key:params.keySet()) {
                    processQuery.parameter(key, params.get(key));
                }
                List<ProcessDefinition> list = null;
                if(pageIndex==-1 || pageRows==-1) {
				    list = processQuery.list();
				}else {
				    list = processQuery.listPage((pageIndex-1)*pageRows, pageRows);
				}
                pagination.setData(list);
            }
            pagination.setCount(count);
        }catch(Exception e){
            throw e;
        }
        return pagination;
    }
    
    /**
     * 根据流程定义ID获取流程定义
     * @author laoqunzhao 2018-05-29
     * @param processDefinitionId 流程定义ID
     * @return ProcessDefinition流程定义
     */
    @Override
	public ProcessDefinition findProcessDefinitionById(String processDefinitionId) {
    	if(StringUtils.isBlank(processDefinitionId)) {
    		return null;
    	}
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
	}

	
	 /**
     * 根据流程定义KEY获取最新版本流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionKey 流程定义KEY
     * @return ProcessDefinition最新版本的流程定义
     */
    @Override
    public ProcessDefinition findLatestProcess(String processDefinitionKey){
    	ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
        		.processDefinitionKey(processDefinitionKey)
                .latestVersion();
        return query.singleResult();
    }
    
    /**
     * 根据流程定义KEY获取最新版本运行中的流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionKey 流程定义KEY
     * @return ProcessDefinition最新版本在运行的流程定义
     */
    @Override
    public ProcessDefinition findLatestRunningProcess(String processDefinitionKey){
    	if(StringUtils.isBlank(processDefinitionKey)) {
    		return null;
    	}
    	//先从Redis中取值，没值再查询数据库
    	try {
    		String redisValue = jedisCluster.hget(WorkflowConstant.REDIS_PROC_DEF_RUNNING, processDefinitionKey);
    	    if(StringUtils.isNotBlank(redisValue)) {
    			return JSON.toJavaObject(JSON.parseObject(redisValue), ProcessDefinition.class);
    	    }
    	}catch(Exception e) {
    	}
    	ProcessDefinition processDefinition = null;
        List<ProcessDefinition> processDefinitions = findDefinitions(processDefinitionKey);
        if(processDefinitions != null && !processDefinitions.isEmpty()) {
	        for(ProcessDefinition processDefinition_: processDefinitions) {
	            if(!processDefinition_.isSuspended()) {
	            	processDefinition = processDefinition_;
	            	break;
	            }
	        }
        }
        //存Redis
        if(processDefinition != null) {
        	try {
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ProcessDefinition.class, "id","category","name","key","description","version");
        		jedisCluster.hset(WorkflowConstant.REDIS_PROC_DEF_RUNNING, processDefinitionKey, JSON.toJSONString(processDefinition, filter));
        	}catch(Exception e) {
    		}
        }
        return processDefinition;
    }
	
	
	/**
     * 根据流程定义KEY获取流程列表
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionKey 流程定义KEY
     * @return List<ProcessDefinition> 流程定义集合
     */
    @Override
    public List<ProcessDefinition> findDefinitions(String processDefinitionKey){
    	ProcessDefinitionQuery query =  repositoryService.createProcessDefinitionQuery()
        		.processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion()
                .desc();
        return query.list();
    }


    
    /**
     * 启动流程
     * @author laoqunzhao 2018-05-03
	 * @param paramJSON
	 * {
	 * listId 申请单ID
	 * description 发起说明
	 * businessKey 业务主键
	 * processDefinitionKey 流程定义KEY
	 * variables 流程发起业务变量JSONArray
	 * [{
	 * name: 变量名称,
	 * type: 变量类型long/double/boolean/date/string,
	 * value: 变量值
	 * }]
	 * properties 流程发起流程表单JSONObject
	 * {字段名称:字段内容，。。。}
	 * extend 流程发起扩展JSONObject
	 * {
	 * var_tasks_assignees:{
	 * taskDefinitionId:[userId] 指定任务办理人
	 * }
	 * }
	 * title 流程发起标题
	 * categoryId 流程分类ID,
	 * saveonly : 是否只保存表单信息，true是（不提交任务），false否（提交任务）,
	 * positionId 职位ID
	 * ouId ouID
	 * responsibilityId 职责ID
	 * applyPersonId 申请人ID
	 * applyPositionId 申请人职位ID
	 * }
     * @param startUser 提交人
	 * @param applyUser 申请人
     * @return 新创建的流程实例ProcessInstance
     * @throws Exception
     */
    @Override
    @Transactional
    public ProcessInstance start(JSONObject paramJSON, ActIdUserEntity_RO startUser, ActIdUserEntity_RO applyUser) throws Exception{
		ActBpmListEntity_HI bpmList = bpmListServer.save(paramJSON);
		if("true".equals(paramJSON.getString("saveonly"))) {
			return null;
		}
		Assert.notNull(bpmList, "申请单不存在，流程发起失败！");
		Assert.isTrue(StringUtils.isNotBlank(bpmList.getProcessDefinitionKey()), "流程类型为空，流程发起失败！");
		Assert.isTrue(StringUtils.isNotBlank(bpmList.getProcessDefinitionId()), "该流程类型下没有正在运行的流程，流程发起失败！");
		Map<String, Object> variables = new  HashMap<String, Object>();
		String businessKey = bpmList.getBusinessKey();
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(startUser.getId());
		//设置发起人变量
		setStartVariables(variables, bpmList, applyUser);
		//处理流程办理人变量
		if(StringUtils.isNotBlank(bpmList.getExtend())) {
			JSONObject extendJSON = JSON.parseObject(bpmList.getExtend(), JSONObject.class);
			if(extendJSON.containsKey(WorkflowConstant.TASKS_ASSIGNEES)) {
				JSONArray assigneesJSON = extendJSON.getJSONArray(WorkflowConstant.TASKS_ASSIGNEES);
				variables.put(WorkflowConstant.TASKS_ASSIGNEES, assigneesJSON.toString());
			}
		}
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(bpmList.getProcessDefinitionId(), businessKey, variables);
		String processInstanceId = processInstance.getId();
		bpmList.setProcessInstanceId(processInstanceId);
		bpmList.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		bpmList.setStatus(0);
		bpmList.setStartTime(new Date());
		bpmList.setResult(WorkflowConstant.STATUS_RUNNING);
		bpmListServer.update(bpmList);
		identityService.setAuthenticatedUserId(null);
		//自动完成第一步
		List<Task> activeTasks = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
		if(activeTasks!=null && !activeTasks.isEmpty()){
			@SuppressWarnings("unchecked")
			Map<String, String> properties = StringUtils.isBlank(bpmList.getProperties())?
					new HashMap<String, String>(): (Map<String, String>) JSON.parse(bpmList.getProperties());
			properties.put("option", WorkflowConstant.OPERATE_SUBMIT_DRAFT);
			FormService formService = processEngine.getProcessEngineConfiguration().getFormService();
			Task task = activeTasks.get(0);
			//流程表单
			formService.saveFormData(task.getId(), properties);
			taskService.setAssignee(task.getId(), startUser.getId());
			taskService.complete(task.getId(), getVariables(bpmList, task, variables));
			bpmTaskServer.ccTask(task.getId(), startUser);
			//实现自动完成相同办理人任务
			String jumpTaskId = bpmTaskServer.jumpSameUserOrEmptyTask(processInstance, startUser);
			//可跳过多个节点
			while(jumpTaskId != null) {
				jumpTaskId = bpmTaskServer.jumpSameUserOrEmptyTask(processInstance, startUser);
			}
			//回调业务系统服务
			bpmCallBackServer.callBack(bpmList, task.getId(), startUser.getUserId());

			//发送待办消息
			bpmMessageServer.setTodoMessage(bpmList, activeTasks,null);
			//发送待办消息
			//bpmMessageServer.setTodoMessage(bpmList, activeTasks);
		}else {
			//流程结束
			bpmList.setStatus(1);
			bpmList.setResult(WorkflowConstant.STATUS_PASSED);
			bpmListServer.update(bpmList);
			//回调业务系统服务
			bpmCallBackServer.callBack(bpmList, null, startUser.getUserId());
		}
        return processInstance;
    }

    
    /**
	 * 终止流程
	 * @author laoqunzhao 2018-07-07
     * @param bpmList 申请单ActBpmListEntity_HI
     * @param user 操作人
	 */
    @Override
    public void stop(ActBpmListEntity_HI bpmList, ActIdUserEntity_RO user, String reason) {
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(bpmList.getProcessInstanceId())
                .singleResult();
    	Assert.notNull(processInstance, "流程实例不存在");
        //强制终止流程
        runtimeService.deleteProcessInstance(bpmList.getProcessInstanceId(), user.getUserName() + ": " + reason);
        bpmList.setStatus(1);
        bpmList.setEndTime(new Date());
        bpmList.setResult(WorkflowConstant.STATUS_REJECTED);
    	bpmListServer.update(bpmList);
    	try {
        	//流程结束发送通知消息给发起人
        	bpmMessageServer.sendEndMessage(bpmList);
        }catch(Exception e) {
        	LOGGER.error("消息发送失败", e);
        }
    	//回调业务系统服务
    	bpmCallBackServer.callBack(bpmList, null, user.getUserId());
    }
    
    /**
     * 激活流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionId 流程定义ID
     */
    @Override
    public void activate(String processDefinitionId) {
    	repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
    	//清除Redis
		clearRedis();
    }
    
    /**
     * 挂起流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionId 流程定义ID
     */
    @Override
    public void suspend(String processDefinitionId) {
    	repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
    	//清除Redis
		clearRedis();
    }

    /**
	 * 根据流程实例ID获取发起人ID
	 * @author laoqunzhao 2018-05-03
     * @param processInstanceId 流程实例ID
     * @return 发起人ID
	 */
	@Override
	public String getStartUserId(String processInstanceId) {
		if(StringUtils.isBlank(processInstanceId)){
			return null;
		}
		String startUserId = null;
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		if(historicProcessInstance != null && StringUtils.isNotBlank(historicProcessInstance.getStartUserId())) {
			startUserId = historicProcessInstance.getStartUserId();
		}else{
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId);
			if(bpmList != null && bpmList.getCreatedBy() != null){
				ActIdUserEntity_RO startUser = bpmUserServer.findUserBySysId(bpmList.getCreatedBy());
				startUserId = startUser == null?null : startUser.getId();
			}
			if(StringUtils.isBlank(startUserId)){
				Object startUserIdVar = runtimeService.getVariable(processInstanceId, Variables.startUserName.name());
				if(startUserIdVar != null && StringUtils.isNotBlank(startUserIdVar.toString())) {
					startUserId = startUserIdVar.toString();
				}
			}
		}
		return startUserId;
	}
	
	/**
	 * 根据流程定义ID获取开始节点
	 * @author laoqunzhao 2018-05-03
     * @param processDefinitionId 流程定义ID
     * @return 开始节点
	 */
	@Override
	public StartEvent getStartEvent(String processDefinitionId) {
		BpmnModel bpmnModel = getBpmnModel(processDefinitionId);
        if(bpmnModel == null || bpmnModel.getMainProcess() == null) {
       	 return null;
        }
		return getStartEvent(bpmnModel);
	}
	
	/**
	 * 根据任务ID获取最顶层的流程实例ID
	 * @author laoqunzhao 2018-07-09
	 * @param taskId 任务ID
	 * @return 最顶层的流程实例ID
	 */
	@Override
	public String getTopProcessInstanceIdByTaskId(String taskId) {
		return getTopProcessInstanceId(getProcessInstanceIdByTaskId(taskId));
	}
	
	/**
	 * 根据任务ID获取流程实例ID
	 * @author laoqunzhao 2018-07-09
	 * @param taskId 任务ID
	 * @return 流程实例ID
	 */
	@Override
	public String getProcessInstanceIdByTaskId(String taskId) {
		if(StringUtils.isBlank(taskId)){
			return null;
		}
		HistoricTaskInstance historicTaskInstance = historyService
				.createHistoricTaskInstanceQuery()
				.taskId(taskId)
				.singleResult();
		while(historicTaskInstance != null && StringUtils.isNotBlank(historicTaskInstance.getParentTaskId())) {
			historicTaskInstance = historyService
					.createHistoricTaskInstanceQuery()
					.taskId(historicTaskInstance.getParentTaskId())
					.singleResult();
		}
		return historicTaskInstance == null? null :historicTaskInstance.getProcessInstanceId();
	}
	
	/**
	 * 获取最顶层的流程实例ID,用于任务未持久化情形
	 * @author laoqunzhao 2018-07-10
	 * @param taskEntity TaskEntity
	 * @return 最顶层的流程实例ID
	 */
	@Override
	public String getTopProcessInstanceId(TaskEntity taskEntity) {
		try {
			if(taskEntity.getProcessInstance() != null) {
				return getTopProcessInstanceId(taskEntity.getProcessInstance());
			}
		}catch (NullPointerException e) {
			//由Task直接转换为TaskEntity类型会抛出NullPointerException
		}
		if(StringUtils.isNotBlank(taskEntity.getParentTaskId())) {
		    return getTopProcessInstanceIdByTaskId(taskEntity.getParentTaskId());
		}else if(StringUtils.isNotBlank(taskEntity.getProcessInstanceId())){
			return getTopProcessInstanceId(taskEntity.getProcessInstanceId());
		}else {
			return null;
		}
	}
	
	/**
	 * 获取最顶层的流程实例ID,用于流程对象未持久化情形
	 * @author laoqunzhao 2018-07-10
	 * @param execution ExecutionEntity
	 * @return 最顶层的流程实例ID
	 */
	@Override
	public String getTopProcessInstanceId(ExecutionEntity execution) {
		while(execution != null && execution.getSuperExecution() != null) {
			execution = execution.getSuperExecution();
		}
		return execution == null? null: execution.getProcessInstanceId();
	}
	
	/**
	 * 获取最顶层的流程实例ID,没有父层返回当前的流程实例ID
	 * 流程实例未持久化不适用
	 * @author laoqunzhao 2018-05-03
	 * @param processInstanceId 流程实例ID
	 * @return 最顶层的流程实例ID
	 */
	@Override
	public String getTopProcessInstanceId(String processInstanceId) {
		String topProcessInstanceId = processInstanceId;
		String superProcessInstanceId = getSuperProcessInstanceId(processInstanceId);
		while(superProcessInstanceId != null) {
			topProcessInstanceId = superProcessInstanceId;
			superProcessInstanceId = getSuperProcessInstanceId(superProcessInstanceId);
		}
		return topProcessInstanceId;
	}
	
	/**
	 * 获取父层的流程实例ID
	 * @author laoqunzhao 2018-05-03
	 * @param processInstanceId 流程实例ID
	 * @return 父层的流程实例ID
	 */
	@Override
	public String getSuperProcessInstanceId(String processInstanceId) {
		HistoricProcessInstance processInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		return processInstance == null? null: processInstance.getSuperProcessInstanceId();
	}
	

	/**
	 * 根据流程定义获取第一个用户节点
	 * @author ZhangJun
	 * @createTime 2018/2/4
	 * @reurn 第一个用户节点
	 */
	@Override
	public UserTask getFirstUserTask(String processDefinitionId) {
		BpmnModel bpmnModel = getBpmnModel(processDefinitionId);
        if(bpmnModel == null || bpmnModel.getMainProcess() == null) {
       	 return null;
        }
		StartEvent startEvent = getStartEvent(bpmnModel);
		Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
		String targetRef = startEvent.getOutgoingFlows().get(0).getTargetRef();// 记录启动节点流向的任务节点Id
		for (FlowElement e : flowElements) {
			//迭代用户任务节点
			if (e instanceof UserTask) {
				UserTask task = (UserTask) e;
				if (task.getId().equals(targetRef)) {
					return task;
				}
			}
		}
		return null;
	}   
     
     /**
      * 根据流程定义ID和任务节点ID获取用户任务节点
      * @author laoqunzhao 2018-05-03
      * @param processDefinitionId 流程定义ID
      * @param taskDefinitionId 任务节点ID
      * @return 用户任务节点
      */
     @Override
     public UserTask getUserTask(String processDefinitionId, String taskDefinitionId){
    	 BpmnModel bpmnModel = getBpmnModel(processDefinitionId);
         if(bpmnModel == null || bpmnModel.getMainProcess() == null) {
        	 return null;
         }
         Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements(); 
         for (FlowElement e : flowElements) {
             if (e instanceof UserTask && e.getId().equals(taskDefinitionId)) {
                 return (UserTask) e;
             }
         }
        return null;
     }
     
     /**
      * 根据配置重新分配办理人，通过监听实现
      * @author laoqunzhao 2018-05-04
      * @param taskEntity 从GlobalEventListener获取的TaskEntity
      */
     @Override
     public void taskAssignee(TaskEntity taskEntity) {
         try{
        	 //子任务节点不重新分配办理人
        	 if(StringUtils.isNotBlank(taskEntity.getParentTaskId()) || taskEntity.getProcessInstance() == null) {
        		 return;
        	 }
        	 ProcessInstance processInstance  = taskEntity.getProcessInstance();
        	 //非子流程第一个节点重新分配回给提交人
        	 if(StringUtils.isBlank(processInstance.getSuperExecutionId())) {
	             UserTask firstTask = getFirstUserTask(taskEntity.getProcessDefinitionId());
	             if(firstTask.getId().equals(taskEntity.getTaskDefinitionKey())) {
	            	 taskService.setAssignee(taskEntity.getId(), getStartUserId(processInstance.getId()));
	                 return;
	             }
        	 }
             List<Object> assignList = new ArrayList<Object>();
             //先从指定办理人的变量中取值，若有值不再往下执行
             ActUserTaskEntity_RO taskAssign = bpmTaskServer.getTaskAssignee(taskEntity.getProcessInstanceId(),
            		 taskEntity.getProcessDefinitionId(), taskEntity.getTaskDefinitionKey());
             if(taskAssign != null && taskAssign.getUsers() != null && !taskAssign.getUsers().isEmpty()) {
            	 for(ActIdUserEntity_RO user: taskAssign.getUsers()) {
            		 assignList.add(user.getId());
            	 }
             }else {
            	 //已指定办理人(assignee)不再重新分配
                 if(StringUtils.isNotBlank(taskEntity.getAssignee())) {
                     return;
                 }
                 //子流程
                 if(StringUtils.isNotBlank(processInstance.getSuperExecutionId())) {
                	 String topProcessInstanceId = getTopProcessInstanceId(taskEntity);
                	 ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(topProcessInstanceId);
                	 assignList = bpmTaskServer.getTaskBpmUserIds(bpmList.getListId(), processInstance.getProcessDefinitionId(), taskEntity.getTaskDefinitionKey());
                 }else {
                	 //非子流程
                	 assignList = bpmTaskServer.getTaskBpmUserIds(taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey());
                 }
             }
             boolean multiInstanceLoop = false;
             BpmnModel bpmModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
			 FlowElement element = bpmModel.getMainProcess().getFlowElement(taskEntity.getTaskDefinitionKey());
		     if(element instanceof UserTask) {
			     UserTask task = (UserTask) element;
				 MultiInstanceLoopCharacteristics characteristics = task.getLoopCharacteristics();
				 if(characteristics != null) {
					 multiInstanceLoop = true;
				 }
			 }
             
             if(assignList!=null && !assignList.isEmpty()) {
            	 if(!multiInstanceLoop) {
            		 List<String> assignList_ = new ArrayList<String>();
            		 for(Object assignee: assignList) {
            			 if(assignee instanceof String) {
            				 assignList_.add(assignee.toString());
            			 }
            		 }
            		 if(assignList_ != null && !assignList_.isEmpty()) {
            			 taskAssignee(taskEntity,assignList_);
            		 }else {
            			//判断是否可自动跳过
                      	ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(processInstance.getProcessDefinitionKey(), taskEntity.getTaskDefinitionKey(), true);
                      	if(taskConfig.getEnableAutoJump() != null && taskConfig.getEnableAutoJump() == 1) {
                      		Map<String, String> properties = new HashMap<>();
        					properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_SUBMIT_AUTOJUMP);
        					formService.saveFormData(taskEntity.getId(), properties);
                      		taskService.complete(taskEntity.getId());
                      	}else {
                      		//无人办理异常
                      		bpmExceptionServer.save(taskEntity, WorkflowConstant.EXCEPTION_TYPE_ASSIGNEE, "无法找到任务办理人");
                      	}
            		 }
            	 }
             }else {
            	//判断是否可自动跳过
               	/*ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(processInstance.getProcessDefinitionKey(), taskEntity.getTaskDefinitionKey(), true);
               	if(taskConfig.getEnableAutoJump() != null && taskConfig.getEnableAutoJump() == 1) {
               		Map<String, String> properties = new HashMap<>();
					properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_SUBMIT_AUTOJUMP);
					formService.saveFormData(taskEntity.getId(), properties);
               		taskService.complete(taskEntity.getId());

               	}*/
             }
         }catch(Exception e){
        	 LOGGER.error(e.getMessage(), e);
         }
     }
     
     /**
      * 分配办理人
      * @author laoqunzhao 2018-07-11
      * @param taskEntity 任务
      * @param assignList 办理人ID
      */
     @Override
     public void taskAssignee(TaskEntity taskEntity, List<String> assignList) {
         //只有一人采用assignee方式
         if(assignList.size()==1) {
             taskService.setAssignee(taskEntity.getId(), assignList.get(0));
         }else {
             for(String userId: assignList) {
                 taskService.addCandidateUser(taskEntity.getId(), userId);
             }
         }
     }

	/**
	 * 根据配置重新分配代办人，通过监听实现（异步）
	 * @author laoqunzhao 2018-05-04
	 * @param taskEntity 从GlobalEventListener获取的TaskEntity
	 */
	@Override
	public void taskDelegateAsynchron(final TaskEntity taskEntity, final List<String> bpmUserIds) {
		if(StringUtils.isBlank(taskEntity.getParentTaskId()) && StringUtils.isBlank(taskEntity.getProcessInstanceId())
				|| bpmUserIds == null || bpmUserIds.isEmpty()) {
			return;
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					ProcessInstance processInstance  = taskEntity.getProcessInstance();
					//子任务时ProcessInstance为空，需从父任务节点获取
					if(StringUtils.isNotBlank(taskEntity.getParentTaskId())) {
						String processInstanceId = getProcessInstanceIdByTaskId(taskEntity.getParentTaskId());
						processInstance = runtimeService
								.createProcessInstanceQuery()
								.processInstanceId(processInstanceId)
								.singleResult();
					}
					//非子流程第一个节点不作代办
					if(StringUtils.isBlank(processInstance.getSuperExecutionId())) {
						UserTask firstTask = getFirstUserTask(processInstance.getProcessDefinitionId());
						if(firstTask.getId().equals(taskEntity.getTaskDefinitionKey())) {
							return;
						}
					}
					String processDefinitionKey = processInstance.getProcessDefinitionKey();
					//处理多任务实例getProcessDefinitionKey()值为空的情况
					if(StringUtils.isBlank(processDefinitionKey) && StringUtils.isNotBlank(processInstance.getProcessDefinitionId())){
						ProcessDefinition processDefinition = findProcessDefinitionById(processInstance.getProcessDefinitionId());
						if(processDefinition != null){
							processDefinitionKey = processDefinition.getKey();
						}
					}
					if(StringUtils.isBlank(processDefinitionKey)){
						return;
					}

					for(String bpmUserId : bpmUserIds) {
						Integer clientUserId = (Integer) bpmUserServer.getSysUserId(bpmUserId);
						if(clientUserId!=null) {
							List<Integer> delegateUserIds = bpmTaskDelegateConfigServer
									.getDelegateUserIds(processDefinitionKey, clientUserId);
							if(delegateUserIds!=null && !delegateUserIds.isEmpty()) {
								for(Integer delegateUserId: delegateUserIds) {
									ActBpmTaskDelegateEntity_HI entity = new ActBpmTaskDelegateEntity_HI();
									entity.setClientUserId(clientUserId);
									entity.setDelegateUserId(delegateUserId);
									entity.setIsAuto(true);
									entity.setOperatorUserId(clientUserId);
									entity.setProcessDefinitionKey(processDefinitionKey);
									entity.setProcessInstanceId(processInstance.getId());
									entity.setTaskId(taskEntity.getId());
									entity.setTaskName(taskEntity.getName());
									entity.setTaskTime(taskEntity.getCreateTime());
									bpmTaskDelegateServer.save(entity);
								}
							}
						}
					}
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}
     
     /**
      * 根据配置重新分配代办人，通过监听实现
      * @author laoqunzhao 2018-05-04
      * @param taskEntity 从GlobalEventListener获取的TaskEntity
      */
     @Override
     public void taskDelegate(TaskEntity taskEntity) {
         if(StringUtils.isBlank(taskEntity.getParentTaskId()) && StringUtils.isBlank(taskEntity.getProcessInstanceId())) {
        	 return;
         }
         List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskEntity.getId());
         if(identityLinks!=null && !identityLinks.isEmpty()) {
        	 ProcessInstance processInstance  = taskEntity.getProcessInstance();
        	 //子任务时ProcessInstance为空，需从父任务节点获取
        	 if(StringUtils.isNotBlank(taskEntity.getParentTaskId())) {
        		 String processInstanceId = getProcessInstanceIdByTaskId(taskEntity.getParentTaskId());
        		 processInstance = runtimeService
        				 .createProcessInstanceQuery()
        				 .processInstanceId(processInstanceId)
        				 .singleResult();
        	 }
        	 //非子流程第一个节点不作代办
        	 if(StringUtils.isBlank(processInstance.getSuperExecutionId())) {
	             UserTask firstTask = getFirstUserTask(processInstance.getProcessDefinitionId());
	             if(firstTask.getId().equals(taskEntity.getTaskDefinitionKey())) {
	                 return;
	             }
        	 }
             String processDefinitionKey = processInstance.getProcessDefinitionKey();
			 //处理多任务实例getProcessDefinitionKey()值为空的情况
        	 if(StringUtils.isBlank(processDefinitionKey) && StringUtils.isNotBlank(processInstance.getProcessDefinitionId())){
        	 	 ProcessDefinition processDefinition = findProcessDefinitionById(processInstance.getProcessDefinitionId());
				 if(processDefinition != null){
					 processDefinitionKey = processDefinition.getKey();
				 }
        	 }
        	 if(StringUtils.isBlank(processDefinitionKey)){
        	 	return;
			 }

             for(IdentityLink identityLink: identityLinks) {
                 String bpmUserId = identityLink.getUserId();
                 Integer clientUserId = (Integer) bpmUserServer.getSysUserId(bpmUserId);
                 if(clientUserId!=null) {
                     List<Integer> delegateUserIds = bpmTaskDelegateConfigServer
                             .getDelegateUserIds(processDefinitionKey, clientUserId);
                     if(delegateUserIds!=null && !delegateUserIds.isEmpty()) {
                         for(Integer delegateUserId: delegateUserIds) {
                             ActBpmTaskDelegateEntity_HI entity = new ActBpmTaskDelegateEntity_HI();
                             entity.setClientUserId(clientUserId);
                             entity.setDelegateUserId(delegateUserId);
                             entity.setIsAuto(true);
                             entity.setOperatorUserId(clientUserId);
                             entity.setProcessDefinitionKey(processDefinitionKey);
                             entity.setProcessInstanceId(processInstance.getId());
                             entity.setTaskId(taskEntity.getId());
                             entity.setTaskName(taskEntity.getName());
                             entity.setTaskTime(taskEntity.getCreateTime());
                             bpmTaskDelegateServer.save(entity);
                         }
                     }
                 }
             }
         }
     }
          
     /**
      * 将url与参数通过传统url&的方式拼接
      * @author laoqunzhao 2018-05-04
      * @param url url
      * @param params  流程参数
      * @return 拼接后的url
      * @throws UnsupportedEncodingException
      */
     @Override
     public String joinProcessParams(String url, Map<String,Object> params) throws UnsupportedEncodingException {
        if(params!=null && !params.isEmpty()) {
            for(String key:params.keySet()) {
                if(params.get(key)!=null) {
                    url = url + (url.contains("?")?"&":"?") + key + "=" + URLEncoder.encode(params.get(key).toString(),"utf-8");
                }
            }
        }
        return url;
     }
	
	/**
	 * 将JSON变量集转换为Map结构
	 * @author laoqunzhao 2018-05-04
	 * @param jsonStr JSON格式的变量集
	 * @return Map结构的变量集
	 */
	@Override
    public Map<String, Object> jsonToVariables(String jsonStr){
        Map<String, Object> variables = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(jsonStr)) {
            JSONArray jsonArray = JSONArray.parseArray(jsonStr);
            for(int i=0; i<jsonArray.size(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                if(!jsonObj.containsKey("name") || !jsonObj.containsKey("type") || !jsonObj.containsKey("value") 
                         || StringUtils.isBlank(jsonObj.getString("name"))) {
                     continue;
                }
                String name = jsonObj.getString("name");
                String type = jsonObj.getString("type");
                Object value = jsonObj.getString("value");
                String value_ = value == null || StringUtils.isBlank(value.toString())?null:value.toString();
                if(value_!=null) {
                     switch(type) {
                     case "date":
                         if(Pattern.matches("\\d+", value_)) {
                             value = new Date(Integer.parseInt(value_));
                         }else {
                             try {
                                value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value_);
                            } catch (ParseException e) {
                            }
                         }
                         break;
                     case "long":value = Long.parseLong(value_);break;
                     case "double":value = Double.parseDouble(value_);break;
                     case "boolean":value = StringUtils.equals(value_, "true")||StringUtils.equals(value_, "1")?true:false;break;
                     default:break;
                     }
                }else {
                     value = null;
                }
                variables.put(name, value);
            }
         }
         return variables;
    }
	 
	
	
	/**
	 * 在JSONArray中根据关键字查找流程定义信息，并加到JSONArray中
	 * @author laoqunzhao 2018-05-03
	 * @param array JSONArray
	 * @param jsonKeyField JSONArray中JSONObject的关键字
	 */
	@Override
    public void appendProcessInfo(JSONArray array, String jsonKeyField) {
        if(array == null || array.isEmpty()) {
            return;
        }
        //获取流程定义KEY
        List<String> processDefinitionKeys = ConvertUtil.getJSONArrayField(array, jsonKeyField);
        //找出最新版本的流程定义
        List<ProcessDefinition> processList = null;
        if(processDefinitionKeys!=null && !processDefinitionKeys.isEmpty()) {
            processList = findLatestRunningProcesses(processDefinitionKeys);
        }
        if(processList == null || processList.isEmpty()) {
            return;
        }
        List<String> deploymentIds = new ArrayList<String>();
        for(ProcessDefinition procdef:processList) {
            deploymentIds.add(procdef.getDeploymentId());
        }
        //建立ProcessDefinition属性与JSON属性的关系
        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("id","processDefinitionId");
        fieldMap.put("key","processDefinitionKey");
        fieldMap.put("name","processDefinitionName");
        fieldMap.put("deploymentId","deploymentId");
        fieldMap.put("version","version");
        fieldMap.put("suspended","suspended");
        //查询流程发布信息
        List<Deployment> deploymentList = findDeloyments(deploymentIds);
        @SuppressWarnings("unchecked")
        Map<String,ProcessDefinition> processMap = ConvertUtil.listToMap(processList, "key");
        //将对象转换为内置JSONArray的一部分
        ConvertUtil.appendToJSONArray(array, processMap, fieldMap, jsonKeyField, null, true);
        //Deployment
        fieldMap = new HashMap<String, String>();
        fieldMap.put("category","categoryId");
        fieldMap.put("deploymentTime","deploymentTime");
        @SuppressWarnings("unchecked")
        Map<String,Deployment> deploymentMap = ConvertUtil.listToMap(deploymentList, "id");
        ConvertUtil.appendToJSONArray(array, deploymentMap, fieldMap, "deploymentId", null, true);
    }
	
	
	/**
     * 将流程模型集合转换为JSONArray
     * @author laoqunzhao 2018-05-03
     * @param definitions 流程定义集合
     * @param includeDeployment 是否包含流程发布信息
     * @return JSONArray格式的流程定义集合
     */
    @Override
    public JSONArray definitionsToJsonArray(List<ProcessDefinition> definitions, boolean includeDeployment) {
    	JSONArray array = new JSONArray();
    	if(null!=definitions && !definitions.isEmpty()) {
    		if(includeDeployment) {
    			List<String> deploymentIds = new ArrayList<String>();
    			for(ProcessDefinition definition :definitions) {
        			array.add(definitionToJsonObject(definition, false));
        			deploymentIds.add(definition.getDeploymentId());
        		}
    			//增加部署信息
    			List<Deployment> deploymentList = findDeloyments(deploymentIds);
    			Map<String, String>fieldMap = new HashMap<String, String>();
    	        fieldMap.put("category","categoryId");
    	        fieldMap.put("deploymentTime","deploymentTime");
    	        @SuppressWarnings("unchecked")
    	        Map<String,Deployment> deploymentMap = ConvertUtil.listToMap(deploymentList, "id");
    	        ConvertUtil.appendToJSONArray(array, deploymentMap, fieldMap, "deploymentId", null, true);
			}else {
				for(ProcessDefinition definition :definitions) {
	    			array.add(definitionToJsonObject(definition, false));
	    		}
			}    		
    	}
		return array;
    }
     
    /**
     * 将流程定义集合转换为JSONObejct
     * @author laoqunzhao 2018-05-03
     * @param definition 流程定义
     * @param includeDeployment 是否包含流程发布信息
     * @return JSONObject格式的流程定义
     */
    @Override
    public JSONObject definitionToJsonObject(ProcessDefinition definition, boolean includeDeployment) {
    	JSONObject data = new JSONObject();
        data.put("id", definition.getId());
        data.put("deploymentId", definition.getDeploymentId());
        data.put("key", definition.getKey());
        data.put("name", definition.getName());
        data.put("description", definition.getDescription());
        data.put("version", definition.getVersion());
        data.put("suspended", definition.isSuspended());
        //流程部署信息
        if(includeDeployment) {
        	Deployment deployment  = repositoryService.createDeploymentQuery().deploymentId(definition.getDeploymentId()).singleResult();
    		if(deployment != null) {
    			data.put("deploymentTime", ConvertUtil.dateToString(deployment.getDeploymentTime()));
    			data.put("categoryId", deployment.getCategory());
    		}
        }
        return data;
    }
	 
	
	/**
     * 根据流程任务节点设置获取流程变量值，并与新的流程变量合并
     * @author laoqunzhao 2018-05-20
     * @param task 流程任务
     * @param variables 新流程变量
     * @return 变量值Map
     */
	@Override
	public Map<String, Object> getVariables(Task task, Map<String, Object> variables){
		ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(task.getProcessInstanceId());
		return getVariables(bpmList, task, variables);
	}
	
	/**
     * 根据流程任务节点设置获取流程变量值，并与新的流程变量合并
     * @author laoqunzhao 2018-05-20
     * @param bpmList 申请单
     * @param task 流程任务
     * @param variables 新流程变量
     * @return 变量值Map
     */
	@Override
	public Map<String, Object> getVariables(ActBpmListEntity_HI bpmList, Task task, Map<String, Object> variables){
		//获取流程任务节点设置的变量
		Map<String, Object> configVariables = getConfigVariables(bpmList, task);
		//将两组变量合并，任务节点设置的变量不覆盖新变量
		if(configVariables != null && !configVariables.isEmpty()) {
			if(variables == null || variables.isEmpty()) {
				variables = configVariables;
			}else {
				for(String key: configVariables.keySet()) {
					if(!variables.containsKey(key)) {
						variables.put(key, configVariables.get(key));
					}
				}
			}
		}
		return variables;
	}
	
	/**
	 * 根据流程定义ID获取Gooflow格式流程JSON
     * @author laoqunzhao 2018-07-30
	 * @param processDefinitionId 流程定义ID
	 * @return JSONObject
	 */
	@Override
    public JSONObject getGooflowProcessJSON(String processDefinitionId) {
		Assert.isTrue(StringUtils.isNotBlank(processDefinitionId), "缺少参数！");
        //从Redis中加载，加快速度
        try {
            String gooflowStr = jedisCluster.hget(WorkflowConstant.REDIS_PROC_DEF_GOOFLOW_JSON, processDefinitionId);
            if(StringUtils.isNotBlank(gooflowStr)) {
                return JSON.parseObject(gooflowStr);
            }
        }catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
		BpmnModel bpmnModel = getBpmnModel(processDefinitionId);
		Assert.notNull(bpmnModel, "流程定义不存在！");
        JSONObject gooflowJSON = bpmModelServer.getGooflowModelJSON(bpmnModel);
        //存Redis
        if(gooflowJSON != null){
            try {
                jedisCluster.hset(WorkflowConstant.REDIS_PROC_DEF_GOOFLOW_JSON, processDefinitionId, gooflowJSON.toString());
            }catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return gooflowJSON;
	}

    /**
     * 根据流程定义ID获取Gooflow格式流程JSON(REDIS)
     * @author laoqunzhao 2018-07-30
     * @param processDefinitionId 流程定义ID
     * @param bpmnModel BPMNModel
     * @return JSONObject
     */
    private JSONObject getGooflowProcessJSONInRedis(String processDefinitionId, BpmnModel bpmnModel) {
        Assert.isTrue(StringUtils.isNotBlank(processDefinitionId), "缺少参数！");
        //从Redis中加载，加快速度
        try {
            String gooflowStr = jedisCluster.hget(WorkflowConstant.REDIS_PROC_DEF_GOOFLOW_JSON, processDefinitionId);
            if(StringUtils.isNotBlank(gooflowStr)) {
                return JSON.parseObject(gooflowStr);
            }
        }catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        Assert.notNull(bpmnModel, "流程定义不存在！");
        JSONObject gooflowJSON = bpmModelServer.getGooflowModelJSON(bpmnModel);
        //存Redis
        if(gooflowJSON != null){
            try {
                jedisCluster.hset(WorkflowConstant.REDIS_PROC_DEF_GOOFLOW_JSON, processDefinitionId, gooflowJSON.toString());
            }catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return gooflowJSON;
    }
	
	/**
	 * 根据流程实例ID获取Gooflow格式流程JSON
     * @author laoqunzhao 2018-07-30
	 * @param processInstanceId 流程实例ID
	 * @param markActive 是否标记当前活动节点 true.是  false.否
	 * @param markHistory 是否标记历史路径  true.是  false.否 
	 * @return JSONObject
	 */
	@Override
    public JSONObject getGooflowProcessJSON(String processInstanceId, boolean markActive, boolean markHistory) {
    	Assert.isTrue(StringUtils.isNotBlank(processInstanceId), "缺少参数！");
    	String processDefinitionId = null;
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
    	if(processInstance == null) {
            HistoricProcessInstance historicProcessInstance  = historyService
            		.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            if(historicProcessInstance != null) {
            	processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            }
        }else {
        	processDefinitionId = processInstance.getProcessDefinitionId();
        }
    	Assert.notNull(processDefinitionId, "流程实例不存在！");
    	BpmnModel bpmnModel = getBpmnModel(processDefinitionId);
    	Assert.notNull(bpmnModel, "流程定义不存在！");
    	JSONObject processJSON = getGooflowProcessJSONInRedis(processDefinitionId, bpmnModel);
    	//节点标记
    	JSONObject nodesJSON = processJSON.containsKey("nodes")?processJSON.getJSONObject("nodes") : null;
    	if(nodesJSON != null) {
    		//活动节点标记
        	List<String> activeActivityIds = new ArrayList<>();
        	if(markActive && processInstance != null) {
        		//activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
				List<Task> tasks = bpmTaskServer.findActiveTasks(processInstanceId);
        		if(tasks != null && !tasks.isEmpty()) {
					for(Task task : tasks){
						String taskDefinitionKey = task.getTaskDefinitionKey();
						if(StringUtils.isBlank(taskDefinitionKey)){
							Task parentTask = task;
							while(StringUtils.isBlank(taskDefinitionKey)){
								parentTask = taskService.createTaskQuery().taskId(parentTask.getId()).singleResult();
								if(parentTask == null){
									break;
								}
								taskDefinitionKey = parentTask.getTaskDefinitionKey();
							}
						}
						activeActivityIds.add(taskDefinitionKey);
						JSONObject taskNodeJSON = nodesJSON.getJSONObject(taskDefinitionKey);
						if(taskNodeJSON != null){
							if(!taskNodeJSON.containsKey("marked")){
								//处理中
								if(WorkflowConstant.TASK_STATUS_RESOLVING.equals(task.getCategory())){
									taskNodeJSON.put("marked", true);
								}else{
									//未接收
									taskNodeJSON.put("notReceivedMarken", true);
								}
							}
							//记录任务办理人
							List<ActIdUserEntity_RO> taskUsers = getTaskUsers(task);
							taskNodeJSON.put("users", taskUsers == null? new JSONArray(): JSON.toJSON(taskUsers));
						}
					}
        		}
        	}
        	//历史记录标记
        	if(markHistory) {
	            List<HistoricActivityInstance> historicActivityInstances = bpmHistoryServer.findHistoricActivities(processInstanceId, false);
	            if(historicActivityInstances != null && !historicActivityInstances.isEmpty()) {
	            	//历史节点标记
	            	List<String> historicActivityIds = new ArrayList<String>();
	            	for(HistoricActivityInstance historicActivityInstance: historicActivityInstances) {
	            		if(!activeActivityIds.contains(historicActivityInstance.getActivityId())) {
	            			historicActivityIds.add(historicActivityInstance.getActivityId());
	            		}
	            	}
	            	if(!historicActivityIds.isEmpty()) {
						//获取历史办理人
						Map<String, List<ActIdUserEntity_RO>> taskUsersMap = getHistoricTaskUsers(bpmnModel, historicActivityInstances);
	            		for(String nodeId: nodesJSON.keySet()) {
	        				if(historicActivityIds.contains(nodeId) && nodesJSON.getJSONObject(nodeId) != null) {
	        					nodesJSON.getJSONObject(nodeId).put("customMarked", true);
								List<ActIdUserEntity_RO> taskUsers = taskUsersMap.get(nodeId);
								nodesJSON.getJSONObject(nodeId).put("users", taskUsers == null? new JSONArray(): JSON.toJSON(taskUsers));
	        				}
	        			}
	            		JSONObject linesJSON = processJSON.containsKey("lines")?processJSON.getJSONObject("lines") : null;
	            		if(linesJSON != null) {
	            			//历史路径标记
	    	            	Collection<FlowElement> elementList = bpmnModel.getMainProcess().getFlowElements();         
	    	                for(FlowElement element : elementList) {
	    	                	if(element instanceof SequenceFlow) {
	    	                		SequenceFlow flow = (SequenceFlow)element;
	    	                		if((activeActivityIds.contains(flow.getTargetRef())
	    	                				|| historicActivityIds.contains(flow.getTargetRef())) 
	    	                				&& historicActivityIds.contains(flow.getSourceRef())
	    	                				&& linesJSON.containsKey(flow.getId())
	    	                				&& linesJSON.get(flow.getId()) != null){
	    	                			linesJSON.getJSONObject(flow.getId()).put("customMarked", true);
	    	                		}
	    	                    }
	    	                }
	            		}
	            	}
	            }
        	}
    	}
		return processJSON;
	}

	private List<ActIdUserEntity_RO> getTaskUsers(Task task){
		List<ActIdUserEntity_RO> users = null;
		List<String> bpmUserIds = new ArrayList<>();
		//取办理人
		if(StringUtils.isNotBlank(task.getAssignee())) {
			bpmUserIds.add(task.getAssignee());
		}else {
			List<String> bpmUserIds_ = bpmTaskServer.getTaskBpmUserIds(task.getId());
			if(bpmUserIds_ != null && !bpmUserIds_.isEmpty()) {
				for(String bpmUserId: bpmUserIds_){
					if(!bpmUserIds.contains(bpmUserId)){
						bpmUserIds.add(bpmUserId);
					}
				}
			}
		}
		if(!bpmUserIds.isEmpty()){
			users = bpmUserServer.findUsersByBpmIds(bpmUserIds);
		}
		//委托办理人
		List<Integer> delegateUserIds = new ArrayList<Integer>();
		List<ActBpmTaskDelegateEntity_HI> delegates = bpmTaskDelegateServer.findByTaskId(task.getId());
		if(delegates != null && !delegates.isEmpty()) {
			for(ActBpmTaskDelegateEntity_HI delegate : delegates) {
				if((delegate.getDeleteFlag() == 0 || delegate.getDeleteFlag() == 0)
						&& StringUtils.equals(delegate.getStatus(), WorkflowConstant.DELEGATE_STATUS_PENDING)
						&& !delegateUserIds.contains(delegate.getDelegateUserId())) {
					delegateUserIds.add(delegate.getDelegateUserId());
				}
			}
			if(delegateUserIds != null && !delegateUserIds.isEmpty()) {
				List<ActIdUserEntity_RO> delegateUsers = bpmUserServer.findUsersBySysIds(delegateUserIds);
				if(delegateUsers != null && !delegateUsers.isEmpty()) {
					users = (List<ActIdUserEntity_RO>) (users == null? new ArrayList<>(): users);
					for(ActIdUserEntity_RO delegateUser : delegateUsers) {
						if(!bpmUserIds.contains(delegateUser.getUserName())){
							users.add(delegateUser);
						}
					}
				}
			}
		}
		return users;
	}

	private Map<String, List<ActIdUserEntity_RO>> getHistoricTaskUsers(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances){
		Map<String, List<ActIdUserEntity_RO>> taskUsersMap = new HashMap<>();
		Map<String, List<String>> taskUserIdMap = new HashMap<>();
		Process mainProcess = bpmnModel.getMainProcess();
		for(HistoricActivityInstance historicActivityInstance: historicActivityInstances) {
			if(StringUtils.isNotBlank(historicActivityInstance.getAssignee())){
				if(isMultiInstance(mainProcess, historicActivityInstance.getActivityId())){
					//多实例任务记录所有办理人
					List<String> taskUserIds = taskUserIdMap.get(historicActivityInstance.getActivityId());
					if(taskUserIds == null){
						taskUserIds = new ArrayList<>();
					}
					taskUserIds.add(historicActivityInstance.getAssignee());
					taskUserIdMap.put(historicActivityInstance.getActivityId(), taskUserIds);
				}else{
					//普通节点只记录最后办理人
					List<String> taskUserIds = new ArrayList<>();
					taskUserIds.add(historicActivityInstance.getAssignee());
					taskUserIdMap.put(historicActivityInstance.getActivityId(), taskUserIds);
				}
			}
		}
		if(!taskUserIdMap.isEmpty()){
			List<String> bpmUserIds = new ArrayList<>();
			for(String key : taskUserIdMap.keySet()){
				List<String> taskUserIds = taskUserIdMap.get(key);
				if(taskUserIds == null){
					continue;
				}
				for(String taskUserId : taskUserIds){
					if(!bpmUserIds.contains(taskUserId)){
						bpmUserIds.add(taskUserId);
					}
				}
			}
			if(!bpmUserIds.isEmpty()){
				List<ActIdUserEntity_RO> taskUsers = bpmUserServer.findUsersByBpmIds(bpmUserIds);
				if(taskUsers != null && !taskUsers.isEmpty()){
					Map<String, ActIdUserEntity_RO> usersMap = new HashMap<>();
					for(ActIdUserEntity_RO taskUser : taskUsers){
						usersMap.put(taskUser.getId(), taskUser);
					}
					for(String key : taskUserIdMap.keySet()){
						List<String> taskUserIds = taskUserIdMap.get(key);
						if(taskUserIds == null){
							continue;
						}
						List<ActIdUserEntity_RO> taskUsers_ = new ArrayList<>();
						for(String taskUserId : taskUserIds){
							if(usersMap.containsKey(taskUserId)){
								taskUsers_.add(usersMap.get(taskUserId));
							}
						}
						taskUsersMap.put(key, taskUsers_);
					}
				}
			}
		}
		return taskUsersMap;
	}

	/**
	 * 根据流程定义ID获取BpmnModel
	 * @author laoqunzhao 2018-05-20
	 * @param processDefinitionId 流程定义ID
	 * @return BpmnModel
	 */
	@Override
	public BpmnModel getBpmnModel(String processDefinitionId) {
		if(StringUtils.isBlank(processDefinitionId)) {
			return null;
		}
		return repositoryService.getBpmnModel(processDefinitionId);
	}
	
	/**
	 * 设置流程启动变量
	 * @author laoqunzhao 2018-09-10
	 * @param variables 流程变量
	 * @param bpmList 申请单
	 * @param starter 发起人
	 */
	@Override
	public void setStartVariables(Map<String, Object> variables, ActBpmListEntity_HI bpmList,ActIdUserEntity_RO starter) {
		String variablesStr = bpmList.getVariables();
		JSONArray variablesJSON = StringUtils.isBlank(variablesStr)? new JSONArray(): JSON.parseArray(variablesStr);
		//提交传入
		setJsonVariable(variablesJSON, Variables.startPositionId.name(), "long", bpmList.getPositionId());
		setJsonVariable(variablesJSON, Variables.startOrgId.name(), "long", bpmList.getOrgId());
		setJsonVariable(variablesJSON, Variables.startResponsibilityId.name(), "long", bpmList.getResponsibilityId());
		setJsonVariable(variablesJSON, Variables.startUserType.name(), "string", bpmList.getUserType());
		setJsonVariable(variablesJSON, Variables.startRoleType.name(), "string", bpmList.getRoleType());
		setJsonVariable(variablesJSON, Variables.startCustAccountId.name(), "long", bpmList.getCustAccountId());
		setJsonVariable(variablesJSON, Variables.applyPersonId.name(), "long", bpmList.getApplyPersonId());
		setJsonVariable(variablesJSON, Variables.applyPositionId.name(), "long", bpmList.getApplyPositionId());
		//自动注入
		setJsonVariable(variablesJSON, Variables.startUserId.name(), "long", starter.getUserId());
		setJsonVariable(variablesJSON, Variables.startPersonId.name(), "long", bpmList.getApplyPersonId() != null?bpmList.getApplyPersonId() : starter.getPersonId());
		setJsonVariable(variablesJSON, Variables.startUserName.name(), "string", starter.getUserName());
		setJsonVariable(variablesJSON, Variables.startPositionName.name(), "string", starter.getPositionName());
		//部门信息
		setJsonVariable(variablesJSON, Variables.startDepartmentId.name(), "long", starter.getDepartmentId());
		setJsonVariable(variablesJSON, Variables.startDepartmentCode.name(), "string", starter.getDepartmentCode());
		setJsonVariable(variablesJSON, Variables.startDepartmentName.name(), "string", starter.getDepartmentName());
		if(bpmList.getDepartmentId() != null || starter.getDepartmentId() != null){
			BaseDepartmentEntity_HI department = baseDepartmentServer.getById(bpmList.getDepartmentId() != null?bpmList.getDepartmentId():starter.getDepartmentId());
			if(department != null){
				setJsonVariable(variablesJSON, Variables.startDepartmentId.name(), "long", department.getDepartmentId());
				setJsonVariable(variablesJSON, Variables.startDepartmentCode.name(), "string", department.getDepartmentCode());
				setJsonVariable(variablesJSON, Variables.startDepartmentName.name(), "string", department.getDepartmentName());
				setJsonVariable(variablesJSON, Variables.startDepartmentType.name(), "string", department.getDepartmentType());
			}
		}
		setJsonVariable(variablesJSON, Variables.startJobId.name(), "long", starter.getJobId());
		setJsonVariable(variablesJSON, Variables.startJobCode.name(), "string", starter.getJobCode());
		setJsonVariable(variablesJSON, Variables.startJobName.name(), "string", starter.getJobName());
		//渠道
		setJsonVariable(variablesJSON, Variables.startChannel.name(), "string", "");
		if(bpmList.getResponsibilityId() != null){
			ProFileBean proFileBean = baseAccreditCacheServer.getChannelType((Integer)starter.getUserId(), bpmList.getResponsibilityId());
			if(proFileBean != null){
				setJsonVariable(variablesJSON, Variables.startChannel.name(), "string", proFileBean.getProfileValue());
			}
		}
		//部门层级
		List<Integer> leavels = new ArrayList<Integer>();
		if(starter.getDepartments() != null && !starter.getDepartments().isEmpty()){
			for(BaseDepartmentEntity_HI_RO department : starter.getDepartments()){
				Integer leavel = department.getDepartmentLevel();
				if(leavel == null){
					continue;
				}
				leavels.add(leavel.intValue());
				setJsonVariable(variablesJSON, Variables.startDepartmentId.name() + leavel, "long", department.getDepartmentId());
				setJsonVariable(variablesJSON, Variables.startDepartmentCode.name() + leavel, "string", department.getDepartmentCode());
				setJsonVariable(variablesJSON, Variables.startDepartmentName.name() + leavel, "string", department.getDepartmentName());
			}
		}
		for(int i=0; i<5 ; i++){
			if(!leavels.contains(i)){
				setJsonVariable(variablesJSON, Variables.startDepartmentId.name() + i, "string", "");
				setJsonVariable(variablesJSON, Variables.startDepartmentCode.name() + i, "string", "");
				setJsonVariable(variablesJSON, Variables.startDepartmentName.name() + i, "string", "");
			}
		}
		bpmList.setVariables(variablesJSON.toString());
		Map<String, Object> initVariables = jsonToVariables(variablesJSON.toString());
		if(initVariables != null && !initVariables.isEmpty()){
			for(String variableName : initVariables.keySet()){
				if(!variables.containsKey(variableName) || variables.get(variableName) == null
						|| StringUtils.isBlank(variables.get(variableName).toString())){
					variables.put(variableName, initVariables.get(variableName));
				}
			}
		}
	}

	private void setJsonVariable(JSONArray variablesJSON, String name, String type, Object value){
		JSONObject variableJSON = new JSONObject();
		variableJSON.put("name", name);
		variableJSON.put("type", type);
		variableJSON.put("value", value);
		variablesJSON.add(variableJSON);
	}
	
	/**
	  * 获取流程开始节点
	  * @author laoqunzhao 2018-04-27
	  * @param bpmnModel BpmnModel
	  * @return 开始节点StartEvent
	  */
    private StartEvent getStartEvent(BpmnModel bpmnModel) {
        StartEvent startEvent = null;
		if(bpmnModel != null) {
   		    Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
   		    for (FlowElement e : flowElements) {
   			    if (e instanceof StartEvent) {
   			        startEvent = (StartEvent) e;
   			        break;
   			    }
   		    }
		}
		return startEvent;
    }
	
	/**
     * 根据流程任务节点设置获取流程变量值
     * @author laoqunzhao 2018-05-20
     * @param bpmList 申请单
     * @param task 流程任务
     * @return 变量值Map
     */
    private Map<String, Object> getConfigVariables(ActBpmListEntity_HI bpmList, Task task) {
    	String processDefinitionKey = null; //流程定义KEY
    	String processInstanceId = null;    //流程实例ID
    	String taskDefinitionId = null;     //任务节点ID
    	if(task != null) {
    		//流程在运行中
    		processInstanceId = task.getProcessInstanceId();
    		//子任务
    		if(StringUtils.isNotBlank(task.getParentTaskId())) {
    			processInstanceId = getProcessInstanceIdByTaskId(task.getParentTaskId());
    		}
    		//查询流程实例获取processDefinitionKey
	    	ProcessInstance processInstance = runtimeService
	    			.createProcessInstanceQuery()
	    			.processInstanceId(processInstanceId)
	    			.singleResult();
	    	processDefinitionKey = processInstance.getProcessDefinitionKey();
	    	taskDefinitionId = task.getTaskDefinitionKey();
    	}else {
    		//流程未发起
    		processInstanceId = bpmList.getProcessInstanceId();
    		processDefinitionKey = bpmList.getProcessDefinitionKey();
    		//流程未发起时取第一个任务的变量
    		UserTask firstTask = getFirstUserTask(bpmList.getProcessDefinitionId());
    		taskDefinitionId = firstTask.getId();
    	}
    	ActBpmTaskConfigEntity_HI config = bpmTaskConfigServer.findByDefinition(processDefinitionKey, taskDefinitionId, true);
		//没有定义变量，直接返回
    	if(config == null || StringUtils.isBlank(config.getVariables())) {
			return null;
		}
    	JSONArray variablesJSON = JSON.parseArray(config.getVariables());
    	Map<String, Object> variables = new HashMap<String, Object>();
    	if(variablesJSON != null && !variablesJSON.isEmpty()) {
    		for(int i=0; i<variablesJSON.size(); i++) {
    			JSONObject variable = variablesJSON.getJSONObject(i);
    			String value = variable.getString("value");
    			//判断是否是sql语句,是sql语句执行重新赋值
    			if(value !=null && value.matches("(?i).*select\\s+.+\\s+from\\s+.+")) {
    				List<Object> paramList = new ArrayList<Object>();
    				//提取参数
    				String reg = "=\\s*#\\s*\\{([^\\}]+)\\}";
    				Pattern p = Pattern.compile(reg);
    				Matcher m = p.matcher(value);
    				while(m.find()) {
    					String param = m.group(1);
    					value = value.replaceFirst(reg, "=?");
    					paramList.add(getVariableValue(bpmList, processInstanceId, param));
    				}
    				Object[] params = paramList.toArray();
    				Object valueNew = executeSQL(value, params);
    				variable.put("value", valueNew);
    			}
    		}
    		variables = jsonToVariables(variablesJSON.toString());
    	}
    	return variables;
    }
    
    /**
     * 获取流程变量值,包含某些默认值（流程业务主键、发起人）
     * @author laoqunzhao 2018-05-20
     * @param bpmList 申请单
     * @param processInstanceId 流程实例ID
     * @param variableName 变量名
     * @return 变量值Object
     */
    private Object getVariableValue(ActBpmListEntity_HI bpmList, String processInstanceId, String variableName) {
    	if("id".equals(variableName.toLowerCase())) {
    		//当变量名为id时返回业务主键
    		return bpmList.getBusinessKey();
    	}else if(StringUtils.isNotBlank(processInstanceId)){
			//其他情况取流程变量值
			return runtimeService.getVariable(processInstanceId, variableName);
		}else {
    		Map<String, Object> variables = jsonToVariables(bpmList.getVariables());
			return variables == null ? null : variables.get(variableName);
    	}
    }
    
    /**
     * 根据流程定义KEY获取最新版本运行中的流程
     * @author laoqunzhao 2018-05-03
     * @param processDefinitionKeys 流程定义KEY集合
     * @return List<ProcessDefinition>
     */
    private List<ProcessDefinition> findLatestRunningProcesses(List<String> processDefinitionKeys){
        if(processDefinitionKeys == null || processDefinitionKeys.isEmpty()) {
            return null;
        }
        StringBuffer sql = new StringBuffer("SELECT PROC.* FROM ACT_RE_PROCDEF PROC WHERE PROC.SUSPENSION_STATE_=1");
        sql.append(" AND KEY_ IN ('" + StringUtils.join(processDefinitionKeys, "','") + "')");
        sql.append(" AND NOT EXISTS(SELECT 1 FROM ACT_RE_PROCDEF PROC1 WHERE PROC1.KEY_=PROC.KEY_ ");
        sql.append(" AND PROC1.SUSPENSION_STATE_=1 AND PROC1.VERSION_>PROC.VERSION_)");
        return repositoryService.createNativeProcessDefinitionQuery().sql(sql.toString()).list();
    }
    
    
    /**
     * 根据流程部署ID获取流程部署信息
     * @author laoqunzhao 2018-05-03
     * @param deploymentIds 流程部署ID集合
     * @return List<Deployment>
     */
    private List<Deployment> findDeloyments(List<String> deploymentIds){
        if(deploymentIds == null || deploymentIds.isEmpty()) {
            return null;
        }
        StringBuffer sql = new StringBuffer("SELECT DEP.* FROM ACT_RE_DEPLOYMENT DEP WHERE DEP.ID_ IN ('" + StringUtils.join(deploymentIds, "','") + "')");
        return repositoryService.createNativeDeploymentQuery().sql(sql.toString()).list();
    }
    
    /**
	 * 执行SQL语句返回结果
	 * @author laoqunzhao 2018-04-27
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 查询结果
	 */
	private Object executeSQL(String sql, Object[]  params){
		List<Object> values = jdbcTemplate.queryForList(sql, params, Object.class);
	    return values == null || values.isEmpty()? null: values.get(0);
	}


	private boolean isMultiInstance(Process mainProcess, String activityId){
		if(mainProcess == null || StringUtils.isBlank(activityId)){
			return false;
		}
		FlowElement element = mainProcess.getFlowElement(activityId);
		if(element != null && element instanceof UserTask) {
			UserTask task = (UserTask) element;
			MultiInstanceLoopCharacteristics characteristics = task.getLoopCharacteristics();
			if(characteristics != null && characteristics.isSequential()) {
				return true;
			}
		}
		return false;
	}


	private void clearRedis(){
		try{
			jedisCluster.del(WorkflowConstant.REDIS_PROC_FTASK_CFG);
			jedisCluster.del(WorkflowConstant.REDIS_PROC_DEF_FTASK_CFG);
			jedisCluster.del(WorkflowConstant.REDIS_PROC_TASK_CFG);
			jedisCluster.del(WorkflowConstant.REDIS_PROC_DEF_GOOFLOW_JSON);
			jedisCluster.del(WorkflowConstant.REDIS_PROC_DEF_RUNNING);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}


	    
}
