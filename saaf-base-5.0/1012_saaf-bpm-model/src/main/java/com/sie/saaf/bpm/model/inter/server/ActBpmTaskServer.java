package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.*;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActUserTaskEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.NativeHistoricActivityInstanceQuery;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component("actBpmTaskServer")
public class ActBpmTaskServer implements IActBpmTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskServer.class);

	private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	@Autowired
    private TaskService taskService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private IActBpmList bpmListServer;
    
    @Autowired
    private IActBpmProcess bpmProcessServer;
    
    @Autowired
    private IActBpmHistory bpmHistoryServer;
    
    @Autowired
    private IActBpmCategory bpmCategoryServer;
    
    @Autowired
    private IActBpmTaskConfig bpmTaskConfigServer;
    
    @Autowired
    private IActBpmRole bpmRoleServer;
    
    @Autowired
    private IActBpmNotifyTask bpmNotifyTaskServer;
    
    @Autowired
    private IActBpmUser bpmUserServer;
    
    @Autowired
    private FormService formService;
    
    @Autowired
    private IActBpmModel bpmModelServer;
    
    @Autowired
	private IActBpmTaskDelegate bpmTaskDelegateServer;
    
    @Autowired
    private IBpmCallBack bpmCallBackServer;
    
    @Autowired
    private IBpmMessage bpmMessageServer;

	@Autowired
	private IActBpmTaskLeavel bpmTaskLeavelServer;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private BaseViewObject<ActBpmTaskEntity_HI_RO> bpmTaskDAO_HI_RO;
    
    
    /**
	 * 流程待办任务查询
	 * @author laoqunzhao 2018-04-28
     * @param queryParamJSON JSONObject
     * userId BPM用户ID
     * drafter 起草人
     * createdBy 发起人ID
     * listCode 流程编号
     * listName 流程名称
     * title 流程标题
     * businessKey 业务主键
	 * billNo 业务申请单号
     * categoryId 流程分类
     * systemCode 系统代码
     * processDefinitionKey 流程定义Key，条件=
	 * processKey 流程标识，条件=
     * startDate 任务起始时间，格式yyyy-MM-dd
     * endDate 任务截止时间，格式yyyy-MM-dd
     * applyStartDate 流程发起起始时间，格式yyyy-MM-dd
     * applyEndDate 流程发起截止时间，格式yyyy-MM-dd
     * communicated 发起沟通 Y.是   N.否
     * exceptional 异常   Y.是   N.否
     * status PENDING.未接收， RESOLVING.办理中
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return 流程发起数据集合Pagination<ActBpmListEntity_HI>
	 */
	@Override
	public Pagination<ActBpmTaskEntity_HI_RO> findTodoTasks(JSONObject queryParamJSON, ActIdUserEntity_RO user, Integer pageIndex, Integer pageRows) throws Exception {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer condition = new StringBuffer();
        if(user != null) {
			condition.append(" and (task.assignee_ = :assignee1")								//指定人
              .append(" or (task.assignee_ is null and exists(select 1 from act_ru_identitylink idk where idk.task_id_=task.id_ and idk.user_id_ = :assignee2))")//候选人
              .append(" or exists(select 1 from act_bpm_task_delegate delegate where delegate.delegate_user_id = :delegateUserId"//代办
              		+ " and delegate.status = :delegateStatus and delegate.task_id = task.id_))");
          paramsMap.put("assignee1", user.getId());
          paramsMap.put("assignee2", user.getId());
          paramsMap.put("delegateUserId", user.getUserId());
          paramsMap.put("delegateStatus", WorkflowConstant.DELEGATE_STATUS_PENDING);
       }
		//流程编号
		String listCode = StringUtils.trimToNull(queryParamJSON.getString("listCode"));
		if(listCode != null) {
			condition.append(" and upper(bpm.list_code) like :listCode");
			paramsMap.put("listCode", "%" + listCode.toUpperCase() + "%");
		}
		//流程名称
		String listName = StringUtils.trimToNull(queryParamJSON.getString("listName"));
		if(listName != null) {
			condition.append(" and upper(bpm.list_name) like :listName");
			paramsMap.put("listName", "%" + listName.toUpperCase() + "%");
		}
		//流程标题
		String title = StringUtils.trimToNull(queryParamJSON.getString("title"));
		if(title != null) {
			condition.append(" and upper(bpm.title) like :title");
			paramsMap.put("title", "%" + title.toUpperCase() + "%");
		}
		//业务主键
		String businessKey = StringUtils.trimToNull(queryParamJSON.getString("businessKey"));
		if(businessKey != null) {
			condition.append(" and upper(bpm.business_key) like :businessKey");
			paramsMap.put("businessKey", "%" + businessKey.toUpperCase() + "%");
		}
		//业务申请单号
		String billNo = StringUtils.trimToNull(queryParamJSON.getString("billNo"));
		if(billNo != null) {
			condition.append(" and upper(bpm.bill_no) like :billNo");
			paramsMap.put("billNo", "%" + billNo.toUpperCase() + "%");
		}
		//流程发起人
		String createdBy = StringUtils.trimToNull(queryParamJSON.getString("createdBy"));
		if(createdBy != null) {
			condition.append(" and bpm.created_by = :createdBy");
			paramsMap.put("createdBy", Integer.parseInt(createdBy));
		}
		String drafter = StringUtils.trimToNull(queryParamJSON.getString("drafter"));
        if(drafter != null) {
      	    drafter = "%" + drafter.toUpperCase() + "%";
			condition.append(" and " + bpmUserServer.getSearchSQL("usr", " :drafterUserName", " :drafterUserFullName"));
      	    paramsMap.put("drafterUserName", drafter );
      	    paramsMap.put("drafterUserFullName", drafter);
        }
		//流程分类
		String categoryId = StringUtils.trimToNull(queryParamJSON.getString("categoryId"));
		if(categoryId != null) {
            List<Integer> categoryIds = bpmCategoryServer.getCategoryIds(Integer.parseInt(categoryId));
			condition.append(" and bpm.category_id in (" + StringUtils.join(categoryIds, ",") + ")");
        }
		//系统代码
		String systemCode = StringUtils.trimToNull(queryParamJSON.getString("systemCode"));
		if(systemCode != null) {
			condition.append(" and exists(select 1 from act_bpm_permission where proc_def_key=bpm.proc_def_key and upper(system_code) = :systemCode)");
			paramsMap.put("systemCode", systemCode.toUpperCase());
		}
		//流程KEY
		String processDefinitionKey = StringUtils.trimToNull(queryParamJSON.getString("processDefinitionKey"));
		if(processDefinitionKey != null) {
			condition.append(" and bpm.proc_def_key = :processDefinitionKey");
			paramsMap.put("processDefinitionKey", processDefinitionKey);
		}
		//流程标识
		String processKey = StringUtils.trimToNull(queryParamJSON.getString("processKey"));
		if(processKey != null) {
			condition.append(" and cat.process_key = :processKey");
			paramsMap.put("processKey", processKey);
		}
		//任务状态
		String status = StringUtils.trimToNull(queryParamJSON.getString("status"));
		if(status != null) {
			if(WorkflowConstant.TASK_STATUS_PENDING.equals(status)) {
				condition.append(" and (task.category_ is null or task.category_ = :status)");
			}else {
				condition.append(" and task.category_ = :status");
			}
			paramsMap.put("status", status);
		}
		//是否发起沟通
		String communicated = StringUtils.trimToNull(queryParamJSON.getString("communicated"));
		if(communicated != null) {
			condition.append("Y".equals(communicated)?" and ":" and not");
			condition.append(" exists(select 1 from act_bpm_communicate cmc1 where cmc1.proc_inst_id=bpm.proc_inst_id and cmc1.type='COMMON' and cmc1.delete_flag=0)");
		}
		//是否异常
		String exceptional = StringUtils.trimToNull(queryParamJSON.getString("exceptional"));
		if(exceptional != null) {
			condition.append("Y".equals(exceptional)?" and ":" and not");
			condition.append(" exists(select 1 from act_bpm_exception exc1 where exc1.proc_inst_id=bpm.proc_inst_id and exc1.status=0 and exc1.delete_flag=0)");
		}
		//任务时间
        String startDate = StringUtils.trimToNull(queryParamJSON.getString("startDate"));
        if(startDate != null) {
			condition.append(" and task.create_time_ >= :startDate");
            paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
        }
        String endDate = StringUtils.trimToNull(queryParamJSON.getString("endDate"));
        if(endDate != null) {
			condition.append(" and task.create_time_ < :endDate");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(endDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramsMap.put("endDate", c.getTime());
        }
        //流程发起时间
        String applyStartDate = StringUtils.trimToNull(queryParamJSON.getString("applyStartDate"));
        if(applyStartDate != null) {
			condition.append(" and bpm.start_time >= :applyStartDate");
            paramsMap.put("applyStartDate", ConvertUtil.stringToDateYMD(applyStartDate));
        }
        String applyEndDate = StringUtils.trimToNull(queryParamJSON.getString("applyEndDate"));
        if(applyEndDate != null) {
			condition.append(" and bpm.start_time < :applyEndDate");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(applyEndDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramsMap.put("applyEndDate", c.getTime());
        }
        StringBuffer addCondition = new StringBuffer();
        //过滤六大活动审批流
        addCondition.append(" and not regexp_like(bpm.title,'DM|HWB|OSD|FG|CW|NPP') ");
		StringBuffer countSql = new StringBuffer(ActBpmTaskEntity_HI_RO.QUERY_ALL_TASK_COUNT_SQL);
		countSql.append(condition);
		//countSql.append(addCondition);
		StringBuffer querySql = new StringBuffer(ActBpmTaskEntity_HI_RO.QUERY_ALL_TASK_SQL);
		querySql.append(condition);
		//querySql.append(addCondition);
		querySql.append(" order by task.create_time_ desc ");
		Pagination<ActBpmTaskEntity_HI_RO> pagination = bpmTaskDAO_HI_RO.findPagination(querySql.toString(),countSql.toString(), paramsMap, pageIndex, pageRows);
		
		return pagination;
	}
	
    
    /**
     * 查询正在办理的任务
     * @author laoqunzhao 2018-06-19
     * @param processInstanceId 流程实例ID
     * @return List<Task>
     */
    @Override
    public List<Task> findActiveTasks(String processInstanceId) {
    	if(StringUtils.isBlank(processInstanceId)) {
    		return null;
    	}        
        try {
        	StringBuffer sql = new StringBuffer("from act_ru_task task left join act_bpm_task_leavel leavel on task.id_=leavel.task_id ");
            sql.append(" where task.suspension_state_=1 and (task.proc_inst_id_ = #{processInstanceId1} or leavel.top_proc_inst_id = #{processInstanceId2})");
        	//有子任务时不查询父任务
            sql.append(" and not exists(select 1 from act_ru_task task1 where task.id_=task1.parent_task_id_ and task1.suspension_state_=1)");
            //查询活动任务
            NativeTaskQuery query = taskService.createNativeTaskQuery().sql("select task.* "  + sql.toString() + " order by task.create_time_ asc");
            query.parameter("processInstanceId1", processInstanceId);
            query.parameter("processInstanceId2", processInstanceId);
            return query.list();
        }catch(Exception e){
        	LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 查询任务
     * @author laoqunzhao 2018-05-04
     * @param taskId 任务ID
     * @return Task
     */
    @Override
    public Task get(String taskId) {
    	if(StringUtils.isBlank(taskId)) {
    		return null;
    	}
    	return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

	/**
	 * 查询任务
	 * @author laoqunzhao 2018-05-04
	 * @param taskId 任务ID
	 * @return ActBpmTaskEntity_HI_RO
	 */
	@Override
	public ActBpmTaskEntity_HI_RO getBpmTaskById(String taskId) {
		if(StringUtils.isBlank(taskId)) {
			return null;
		}
		StringBuffer querySql = new StringBuffer(ActBpmTaskEntity_HI_RO.QUERY_SINGLE_TASK_SQL);
		querySql.append(" and task.id_ = :taskId");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("taskId", taskId);
		List<ActBpmTaskEntity_HI_RO> tasks = bpmTaskDAO_HI_RO.findList(querySql.toString(), paramsMap);
		return tasks == null || tasks.isEmpty()? null: tasks.get(0);
	}

	/**
	 * 查询顶层父任务
	 * @author laoqunzhao 2018-05-04
	 * @param parentTaskId 父任务ID
	 * @return Task
	 */
	@Override
	public Task getTopParentTask(String parentTaskId){
		if(StringUtils.isBlank(parentTaskId)){
			return null;
		}
		TaskQuery query = taskService.createTaskQuery();
		Task parentTask = query.taskId(parentTaskId).singleResult();
		while(parentTask != null && StringUtils.isNotBlank(parentTask.getParentTaskId())) {
			parentTask = query.taskId(parentTask.getParentTaskId()).singleResult();
		}
		return parentTask;
	}


	/**
	 * 自动委托转办正在进行的流程
	 * @author laoqunzhao 2020-02-20
	 * @return Task
	 */
	@Override
	public void AutoSetAssignee() {
		StringBuffer querySql = new StringBuffer(ActBpmTaskEntity_HI_RO.QUERY_AUTO_ASSIGNEE_SQL);
		List<ActBpmTaskEntity_HI_RO> bpmTaskDAOHiRoList = bpmTaskDAO_HI_RO.findList(querySql.toString());
		for (ActBpmTaskEntity_HI_RO hiRo : bpmTaskDAOHiRoList) {
			if(hiRo.getCurrentUserId().equals(hiRo.getUserId())){
				try{taskService.complete(hiRo.getTaskId());}catch (Exception e){}
			}else{
				try{taskService.setAssignee(hiRo.getTaskId(),hiRo.getUserId());}catch (Exception e){}
			}
		}
	}
		/**
         * 完成待办任务
         * @author laoqunzhao 2018-05-04
         * @param task 任务
         * @param bpmList 申请单
         * @param delegateId 代办ID
         * @param variables 流程变量
         * @param properties 流程表单字段
         * @param user 办理人
         * @param saveonly 是否只保存不办理，true是，false否
         * @param callback 是否回调
         */
    @Override
    @Transactional
    public void complete(Task task, ActBpmListEntity_HI bpmList, Integer delegateId, Map<String, Object> variables, Map<String,String> properties,
						 ActIdUserEntity_RO user, boolean saveonly, boolean callback) {
		if (user == null || user.getUserId() == null) {
			variables.put("hasUsers", 0); //没找到人
		} else {
			variables.put("hasUsers", 1);//有人
		}
		LOGGER.info("##########################################complete 设置流程节点名称:{}, 变量 hasUsers:{}", (task != null ? task.getName() : "任务为空！"), (user != null ? user.getUserId(): "用户为空") );
		String taskId = task.getId();
		String processInstanceId = bpmList.getProcessInstanceId();
        String option = properties.get(WorkflowConstant.PROP_OPTION);//审批选项
        UserTask firstTask = bpmProcessServer.getFirstUserTask(bpmList.getProcessDefinitionId());
        if(StringUtils.equals(task.getTaskDefinitionKey(), firstTask.getId())) {
        	Assert.isTrue(!WorkflowConstant.OPERATE_REJECT.equals(option), "当前节点不可驳回");
        	Assert.isTrue(!WorkflowConstant.OPERATE_RETRIAL.equals(option), "当前节点不可驳回重审");
        	Assert.isTrue(!WorkflowConstant.OPERATE_REVOKE.equals(option), "当前节点不可撤回");
        	if(!WorkflowConstant.OPERATE_STOP.equals(option)) {
        		//将操作注释为提交审批
        		properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_SUBMIT_DRAFT);
        	}
        }
        //保存流程表单
        if(StringUtils.isNotBlank(task.getParentTaskId())) {
        	//子任务通过command方式保存流程表单
        	processInstanceId = bpmProcessServer.getProcessInstanceIdByTaskId(task.getParentTaskId());
			List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
			if(executions != null && !executions.isEmpty()){
				for(Execution execution : executions){
					if(StringUtils.isBlank(execution.getSuperExecutionId())){
						recordFormProperties((ExecutionEntity)execution, properties, taskId);
					}
				}
			}
        }else {
        	formService.saveFormData(taskId, properties);
        }
        
        //指派到具体人
        taskService.setAssignee(taskId, user.getId());
        //保存变量
        if(StringUtils.isBlank(task.getParentTaskId())) {
        	setTaskVariables(task, processInstanceId, variables);
        }
        //只保存流程相关信息，不处理任务
        if(saveonly) {
            return;
        }
        //存已有的活动任务，在办理结束后发送待办消息进行排除
		List<Task> activeTasks = findActiveTasks(processInstanceId);
        if(StringUtils.equals(option, WorkflowConstant.OPERATE_REJECT)) {
            //任务驳回(reject)
            jump(bpmList.getProcessInstanceId(), firstTask.getId(), task.getId());
        	bpmList.setResult(WorkflowConstant.STATUS_REJECTED);
        	bpmListServer.update(bpmList);
        }else if(StringUtils.equals(option, WorkflowConstant.OPERATE_RETRIAL)) {
            //驳回重审(retrial)
        	String jumpTaskDefinitionId = properties.get(WorkflowConstant.PROP_TASK_DEF_ID);
        	Assert.isTrue(StringUtils.isNotBlank(jumpTaskDefinitionId), "驳回重审节点不能为空！");
            jump(bpmList.getProcessInstanceId(), jumpTaskDefinitionId, task.getId());
            if(StringUtils.equals(jumpTaskDefinitionId, firstTask.getId())) {
            	bpmList.setResult(WorkflowConstant.STATUS_REJECTED);
            }else {
            	bpmList.setResult(WorkflowConstant.STATUS_RUNNING);
            }
            if(StringUtils.isNotBlank(properties.get(WorkflowConstant.PROP_REJECT_TASK_ID))) {
				//驳回到指定人节点
				jumpToTask(bpmList.getProcessInstanceId(), jumpTaskDefinitionId, properties.get(WorkflowConstant.PROP_REJECT_TASK_ID));
			}
			bpmListServer.update(bpmList);
        }else if(StringUtils.equals(option, WorkflowConstant.OPERATE_STOP)) {
            //流程终止(stop)
            taskService.complete(taskId, bpmProcessServer.getVariables(task, variables));
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(bpmList.getProcessInstanceId())
                    .singleResult();
            //强制终止流程
            if(processInstance != null) {
                runtimeService.deleteProcessInstance(bpmList.getProcessInstanceId(), properties.get("opinion"));
            }
            bpmList.setResult(WorkflowConstant.STATUS_REJECTED);
        	bpmListServer.update(bpmList);
        }else {
            //重审
            String jumpTaskDefinitionId = properties.get(WorkflowConstant.PROP_TASK_DEF_ID);
        	if(StringUtils.isNotBlank(jumpTaskDefinitionId)) {
        		jump(bpmList.getProcessInstanceId(), jumpTaskDefinitionId, task.getId());

                if(StringUtils.isNotBlank(properties.get(WorkflowConstant.PROP_REJECT_TASK_ID))) {
                    //驳回到指定人节点
                    jumpToTask(bpmList.getProcessInstanceId(), jumpTaskDefinitionId, properties.get(WorkflowConstant.PROP_REJECT_TASK_ID));
                }
        	}else {
        		//按流程流转

        		taskService.complete(taskId, bpmProcessServer.getVariables(task, variables));
        	}
            bpmList.setResult(WorkflowConstant.STATUS_RUNNING);
        	bpmListServer.update(bpmList);
        }
        
        //处理代办
        completeDelegateTask(taskId, (Integer)user.getUserId());
        
        //实现流程自动抄送功能
        ccTask(taskId, user);
        if(StringUtils.isNotBlank(bpmList.getProcessInstanceId())) {
			//实现自动完成相同办理人或空任务
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(bpmList.getProcessInstanceId()).singleResult();
			if(processInstance != null) {
				String jumpTaskId = jumpSameUserOrEmptyTask(processInstance, user);
				//可跳过多个节点
				while (jumpTaskId != null) {
					jumpTaskId = jumpSameUserOrEmptyTask(processInstance, user);
					//实现流程自动抄送功能
					ccTask(jumpTaskId, user);
				}
			}
		}
        //完成办理
        finishProcess(bpmList);
        //回调业务系统服务
        if(callback) {
        	bpmCallBackServer.callBack(bpmList, taskId, user.getUserId());
        }
        //发送待办消息
		bpmMessageServer.setTodoMessage(bpmList, activeTasks,null);
		//发送待办消息
		//bpmMessageServer.setTodoMessage(bpmList, activeTasks);
        //助审任务将父节点起始时间设为当前时间
        if(StringUtils.isNotBlank(task.getParentTaskId())){
        	String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
			//修改任务创建时间
			jdbcTemplate.update("UPDATE ACT_HI_TASKINST SET START_TIME_='" + createTime
					+ "' WHERE ID_='" + task.getParentTaskId() + "'");
			jdbcTemplate.update("UPDATE ACT_RU_TASK SET CREATE_TIME_='" + createTime
					+ "' WHERE ID_='" + task.getParentTaskId() + "'");
		}
    }
    
    /**
     * 自动跳过任务
     * @author laoqunzhao 2018-07-07
     * @param processInstance 流程实例
     * @param user 办理人
     */
    @Override
    @Transactional
    public String jumpSameUserOrEmptyTask(ProcessInstance processInstance, ActIdUserEntity_RO user) {
    	if(processInstance == null) {
    		return null;
    	}
    	List<Task> activeTasks = findActiveTasks(processInstance.getProcessInstanceId());
    	if(activeTasks == null || activeTasks.isEmpty()) {
    		return null;
    	}
		List<HistoricTaskInstance> historicTaskInstances = bpmHistoryServer.findHistoricTasks(processInstance.getProcessInstanceId());
    	for(Task activeTask: activeTasks){
			Map<String, Object> processParams = new HashMap<>();
			List<String> bpmUserIds = getTaskBpmUserIds(activeTask.getId());
    		if(bpmUserIds == null || bpmUserIds.isEmpty()){
    			//设置流程变量 hasUsers,如果有人返回1，否则返回0
				LOGGER.info("##########################################设置流程节点名称:{}, 变量 hasUsers:{}", activeTask.getName(), bpmUserIds);
				processParams.put("hasUsers", 0);//未找到对应的人
				//没人办理任务自动跳过
				ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(processInstance.getProcessDefinitionKey(), activeTask.getTaskDefinitionKey(), true);
				if(taskConfig.getEnableAutoJump() != null && taskConfig.getEnableAutoJump() == 1) {
					Map<String, String> properties = new HashMap<>();
					properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_SUBMIT_AUTOJUMP);
					formService.saveFormData(activeTask.getId(), properties);
					taskService.complete(activeTask.getId(), processParams);
					return activeTask.getId();
				}
			} else if(historicTaskInstances != null && !historicTaskInstances.isEmpty()
				&& StringUtils.isNotBlank(activeTask.getTaskDefinitionKey()) && StringUtils.isNotBlank(activeTask.getAssignee())){
				processParams.put("hasUsers", 1);
				LOGGER.info("##########################################设置流程节点名称:{}, 变量 hasUsers:{}", activeTask.getName(), bpmUserIds);
				//判断是否可自动跳过
				ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(processInstance.getProcessDefinitionKey(), activeTask.getTaskDefinitionKey(), true);
				if(taskConfig.getEnableAutoJump() == null || taskConfig.getEnableAutoJump() != 1) {
					continue;
				}
				//找出当前节点的流入节点，判断两个节点是否相连
				List<UserTask> prevUserTasks = bpmModelServer.findInflowUserTasks(processInstance.getProcessDefinitionId(), activeTask.getTaskDefinitionKey());
				if(prevUserTasks == null || prevUserTasks.isEmpty()) {
					continue;
				}
				for(HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
					if(user.getId().equals(historicTaskInstance.getAssignee()) && user.getId().equals(activeTask.getAssignee())) {
						for(UserTask prevUserTask: prevUserTasks) {
							if(prevUserTask.getId().equals(historicTaskInstance.getTaskDefinitionKey())) {
								Map<String, String> properties = new HashMap<>();
								properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_SUBMIT_AUTOJUMP);
								formService.saveFormData(activeTask.getId(), properties);
								taskService.complete(activeTask.getId(), processParams);
								return activeTask.getId();
							}
						}
					}
				}
			}
		}
    	/*Task activeTask = activeTasks.get(0);
    	if(StringUtils.isBlank(activeTask.getTaskDefinitionKey()) || StringUtils.isBlank(activeTask.getAssignee())) {
    		return null;
    	}
    	List<HistoricTaskInstance> historicTaskInstances = bpmHistoryServer.findHistoricTasks(processInstance.getProcessInstanceId());
    	if(historicTaskInstances == null || historicTaskInstances.isEmpty()) {
    		return null;
    	}
    	//判断是否可自动跳过
    	ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(processInstance.getProcessDefinitionKey(), activeTask.getTaskDefinitionKey(), true);
    	if(taskConfig.getEnableAutoJump() == null || taskConfig.getEnableAutoJump() != 1) {
    		return null;
    	}
    	//找出当前节点的流入节点，判断两个节点是否相连
    	List<UserTask> prevUserTasks = bpmModelServer.findInflowUserTasks(processInstance.getProcessDefinitionId(), activeTask.getTaskDefinitionKey());
    	if(prevUserTasks == null || prevUserTasks.isEmpty()) {
    		return null;
    	}
    	for(HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
    		if(user.getId().equals(historicTaskInstance.getAssignee()) && user.getId().equals(activeTask.getAssignee())) {
    			for(UserTask prevUserTask: prevUserTasks) {
    				if(prevUserTask.getId().equals(historicTaskInstance.getTaskDefinitionKey())) {
    					Map<String, String> properties = new HashMap<>();
    					properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_SUBMIT_AUTOJUMP);
    					formService.saveFormData(activeTask.getId(), properties);
    					taskService.complete(activeTask.getId());
    	    			return activeTask.getId();
    				}
    			}
    		}
    	}*/
    	return null;
    }

	private void jumpToTask(String processInstanceId, String taskDefinitionId, String taskId) {
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		Assert.notNull(historicTaskInstance, "无法跳转到指定节点！");
		//若任务无人办理自动跳过，直接返回
		if(StringUtils.isBlank(historicTaskInstance.getAssignee())){
			return;
		}
		while(true){
			List<Task> activeTasks = findActiveTasks(processInstanceId);
			Assert.isTrue(activeTasks != null && !activeTasks.isEmpty(), "无法跳转到指定节点！");
			Task activeTask = null;
			for(Task activeTask_ : activeTasks){
				if(StringUtils.equals(activeTask_.getTaskDefinitionKey(), taskDefinitionId)){
					if(activeTask == null){
						activeTask = activeTask_;
					}
					List<String> bpmUserIds = getTaskBpmUserIds(activeTask_.getId());
					//找到指定的人
					if(bpmUserIds != null && (bpmUserIds.contains(historicTaskInstance.getAssignee()) || bpmUserIds.contains(historicTaskInstance.getOwner()))){
						return;
					}
				}
			}
			Assert.notNull(activeTask, "无法跳转到指定节点！");
			taskService.complete(activeTask.getId());
            jdbcTemplate.update("UPDATE ACT_HI_TASKINST SET DELETE_REASON_='jump' WHERE ID_='" + activeTask.getId() + "'");
		}
	}
    
    /**
     * 通过流程来终止流程
     */
//    private void stopByProcess(ActBpmListEntity_HI bpmList) {
//    	try {
//    		JSONObject propertiesJSON = JSON.parseObject(bpmList.getProperties());
//    		String processInstanceId = propertiesJSON.getString(WorkflowConstant.PROCESS_INSTANCE_ID);
//    		Assert.notNull(processInstanceId, "要终止的流程不存在");
//    		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//                    .processInstanceId(processInstanceId)
//                    .singleResult();
//    		Assert.notNull(processInstance, "要终止的流程不存在或已终止");
//    		runtimeService.deleteProcessInstance(processInstanceId, propertiesJSON.getString("opinion"));
//    		ActBpmListEntity_HI bpmList_ = bpmListServer.getByProcessInstanceId(processInstanceId);
//    		bpmList_.setResult(WorkflowConstant.STATUS_REJECTED);
//        	bpmListServer.update(bpmList_);
//    	}catch(Exception e) {
//    		LOGGER.error(e.getMessage(), e);
//    	}
//    }
    
//    /**
//     * 通过流程来转办任务
//     * @param bpmList 申请单
//     */
//    private void transferByProcess(ActBpmListEntity_HI bpmList) {
//    	try {
//    		JSONObject propertiesJSON = JSON.parseObject(bpmList.getProperties());
//    		String processInstanceId = propertiesJSON.getString(WorkflowConstant.PROCESS_INSTANCE_ID);
//    		Assert.notNull(processInstanceId, "流程不存在");
//    		JSONArray transferTasksJSON = propertiesJSON.getJSONArray("transferTasks");
//    		for(int i=0; i<transferTasksJSON.size(); i++) {
//    			JSONObject transferTaskJSON = transferTasksJSON.getJSONObject(i);
//    			String taskId = transferTaskJSON.getString("taskId");
//    			if(StringUtils.isNotBlank(taskId)) {
//    				String userId = transferTaskJSON.getString("taskId");
//					List<String> taskIds = new ArrayList<>();
//					taskIds.add(taskId);
//    				transfer(taskIds, userId);
//    			}
//    		}
//    	}catch(Exception e) {
//    		LOGGER.error(e.getMessage(), e);
//    	}
//    }
    
    private void completeDelegateTask(String taskId, Integer userId) {
    	List<ActBpmTaskDelegateEntity_HI> delegates = bpmTaskDelegateServer.findByTaskId(taskId);
        if(delegates != null && !delegates.isEmpty()) {
        	for(ActBpmTaskDelegateEntity_HI delegate : delegates) {
        		delegate.setOperatorUserId(userId);
        		if(delegate.getDelegateUserId().equals(userId)) {
        			bpmTaskDelegateServer.updateStatus(delegate.getDelegateId(), WorkflowConstant.DELEGATE_STATUS_RESOLVED);
        		}else {
        			bpmTaskDelegateServer.updateStatus(delegate.getDelegateId(), WorkflowConstant.DELEGATE_STATUS_DESTROYED);
        		}
        	}
        }
    }
    
    private void finishProcess(ActBpmListEntity_HI bpmList) {
    	//根据流程实例是否存在判断流程是否结束
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(bpmList.getProcessInstanceId())
                .singleResult();
    	if(processInstance != null) {
    		return;
    	}
    	//流程结束
    	bpmList.setStatus(1);
        bpmList.setEndTime(new Date());
        if(WorkflowConstant.STATUS_RUNNING.equals(bpmList.getResult())) {
        	bpmList.setResult(WorkflowConstant.STATUS_PASSED);
        }
        bpmListServer.update(bpmList);
        try {
        	//流程结束发送通知消息给发起人
        	bpmMessageServer.sendEndMessage(bpmList);
        }catch(Exception e) {
        	LOGGER.error("消息发送失败", e);
        }
    }
    
    
    
    /**
     * 批量处理待办任务
     * @author laoqunzhao 2018-06-04
     * @param taskIds 任务ID
     * @param properties 流程表单字段
     * @param user 办理人
     */
    @Override
    @Transactional
    public void batchComplete(List<String> taskIds,Map<String, Object> variables, Map<String,String> properties, ActIdUserEntity_RO user) throws Exception {
        Assert.isTrue(taskIds != null && !taskIds.isEmpty(), "任务不存在！");
        List<Object[]> bpmLists = new ArrayList<Object[]>();
        for(String taskId : taskIds) {
        	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            if(task == null) {
            	HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            	Assert.isNull(historicTaskInstance, "任务'" + taskId + "'已办理！");
            	Assert.notNull(historicTaskInstance, "任务'" + taskId + "'不存在！");
            }
            String processInstanceId = StringUtils.isBlank(task.getParentTaskId())? task.getProcessInstanceId()
            		: bpmProcessServer.getProcessInstanceIdByTaskId(task.getParentTaskId());
            ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId); 
            Assert.notNull(bpmList, "申请单不存在！");
            //添加原先第四个为null的参数为 new HashMap<String, Object>(),保证调批量完成任务不报错 2019.12.16
        	complete(task, bpmList, null, variables, properties, user, false, false);
        	bpmList = bpmListServer.getById(bpmList.getListId());
        	bpmLists.add(new Object[] {bpmList, taskId});
        }
        //回调业务系统服务
        bpmCallBackServer.callBack(bpmLists, user.getUserId());
    }
    
    /**
     * 流程撤回
     * @author laoqunzhao 2018-06-19
     * @param processInstanceId 流程实例ID
	 *  param taskId 任务ID
     * @param variables 流程变量
     * @param properties 流程表单字段
     * @param user 办理人
     * @return 办理成功"S",不成功返回提示
     */
    @Override
    @Transactional
    public String revoke(String processInstanceId, String taskId, Map<String, Object> variables,
    		Map<String,String> properties, ActIdUserEntity_RO user) throws Exception{
    	String taskDefinitionId = null;
    	String parentTaskId = null;
		if(StringUtils.isNotBlank(taskId)){
			HistoricTaskInstance historicTaskInstance = bpmHistoryServer.getByTaskId(taskId);
			Assert.notNull(historicTaskInstance, "任务不存在！");
			Assert.isTrue(StringUtils.isNotBlank(historicTaskInstance.getTaskDefinitionKey()) || StringUtils.isNotBlank(historicTaskInstance.getParentTaskId()),
					"当前任务不可撤回！");
			while(historicTaskInstance != null && StringUtils.isNotBlank(historicTaskInstance.getParentTaskId())) {
				parentTaskId = parentTaskId == null?historicTaskInstance.getParentTaskId(): parentTaskId;
				historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(historicTaskInstance.getParentTaskId()).singleResult();
			}
			Assert.notNull(historicTaskInstance, "任务不存在！");
			Assert.isTrue(StringUtils.equals(historicTaskInstance.getAssignee(), user.getId()) ,"非本人任务不可撤回！");
			taskDefinitionId = historicTaskInstance.getTaskDefinitionKey();
			processInstanceId = StringUtils.isBlank(processInstanceId)?historicTaskInstance.getProcessInstanceId() : processInstanceId;
		}
		Assert.isTrue(StringUtils.isNotBlank(processInstanceId), "流程实例不存在！");
		ProcessInstance processInstance  = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		Assert.notNull(processInstance, "流程实例不存在！");
    	JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("processInstanceId", processInstanceId);
		queryParamJSON.put("excludeJump", "true");
		List<ActBpmHiTaskEntity_HI_RO> historicTasks = bpmHistoryServer.findHistoricTasks(queryParamJSON, -1, -1).getData();
    	Assert.notEmpty(historicTasks, "流程未发起不可撤回！");
		List<Task> activeTasks = findActiveTasks(processInstanceId);
		Assert.notEmpty(activeTasks, "流程已关闭不可撤回！");

//		for(Task activeTask : activeTasks){
//			if(WorkflowConstant.TASK_STATUS_RESOLVING.equals(activeTask.getCategory())){
//				return "撤回失败！审批人已接收此审批请求！";
//			}
//		}

		ActBpmHiTaskEntity_HI_RO firstTask = historicTasks.get(historicTasks.size() - 1);
		ActBpmHiTaskEntity_HI_RO lastTask = historicTasks.get(0);
//		Assert.isTrue("1".equals(lastTask.getRevokeStatus()), "流程当前状态不可撤回！");
		if(StringUtils.isNotBlank(taskId) && !StringUtils.equals(firstTask.getTaskDefinitionId(), lastTask.getTaskDefinitionId())){
			Assert.isTrue(taskId.equals(lastTask.getTaskId()), "流程当前状态不可撤回！");
			//撤回到任务节点
			if(StringUtils.isNotBlank(parentTaskId)){
				Task parentTask = get(parentTaskId);
				Assert.notNull(parentTask, "流程当前状态不可撤回！");
				//助审节点直接增加助审任务
				properties = (Map<String, String>) (properties != null? properties: new HashMap<>());
				String  opinion  =  properties.get("opinion");
				properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_REVOKE);
				properties.put(WorkflowConstant.PROP_OPINION, opinion==null?"撤回":opinion);
				addSubTaskRecord(parentTask, "撤回", null, user, properties);
				Task task = taskService.newTask(); // 创建一个新的任务
				task.setParentTaskId(parentTaskId); // 设置父任务ID，建立上下级关系
				task.setOwner(user.getId()); // 设置拥有人
				task.setAssignee(user.getId()); // 设置办理人
				task.setName(parentTask.getName()); // 设置任务名称
				task.setDescription(parentTask.getDescription()); // 设置任务描述
				task.setAssignee(WorkflowConstant.TASK_STATUS_RESOLVING);
				taskService.saveTask(task); // 保存任务
			}else{
				jump(processInstanceId, taskDefinitionId, taskId);
				//撤回后在发起节点添加子任务记录撤回操作
				Task task = activeTasks.get(0);
				properties = (Map<String, String>) (properties != null? properties: new HashMap<>());
				properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_REVOKE);
				properties.put(WorkflowConstant.PROP_OPINION, "撤回");
				addSubTaskRecord(task, "撤回", null, user, properties);
				//将状态改为办理中
				activeTasks = findActiveTasks(processInstanceId);
				if(activeTasks != null && !activeTasks.isEmpty()){
					Task activeTask = activeTasks.get(0);
					if(activeTasks.size() > 1){
						for(Task activeTask_ : activeTasks){
							if(StringUtils.equals(taskDefinitionId, activeTask_.getTaskDefinitionKey())){
								activeTask = activeTask_;
								break;
							}
						}
					}
					activeTask.setCategory(WorkflowConstant.TASK_STATUS_RESOLVING);
					taskService.saveTask(activeTask);
				}
			}

		}else{
			//起草撤回
//			Assert.isTrue(StringUtils.equals(firstTask.getTaskDefinitionId(), lastTask.getTaskDefinitionId()), "流程当前状态不可撤回！");
			jump(processInstanceId, firstTask.getTaskDefinitionId(), null);
			//撤回后在发起节点添加子任务记录撤回操作
			Task task = activeTasks.get(0);
			properties = (Map<String, String>) (properties != null? properties: new HashMap<>());
			String  opinion  =  properties.get("opinion");
			properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_REVOKE);
			properties.put(WorkflowConstant.PROP_OPINION, opinion==null?"撤回":opinion);
			addSubTaskRecord(task, "撤回", null, user, properties);
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId);
			bpmList.setResult(WorkflowConstant.STATUS_REVOKE);
			bpmListServer.update(bpmList);
			//将状态改为办理中
			activeTasks = findActiveTasks(processInstanceId);
			if(activeTasks != null && !activeTasks.isEmpty()){
				Task activeTask = activeTasks.get(0);
				activeTask.setCategory(WorkflowConstant.TASK_STATUS_RESOLVING);
				taskService.saveTask(activeTask);
			}
			//回调业务系统服务
			bpmCallBackServer.callBack(bpmList, taskDefinitionId, user.getUserId());
		}

    	return WorkflowConstant.STATUS_SUCESS;
    }

	/**
	 * 查询撤回状态
	 * @param processInstanceId 流程实例ID
	 * @param taskId 任务ID
	 * @return true/false
	 * @throws Exception
	 */
	@Override
    public boolean getRevokeStatus(String processInstanceId, String taskId) throws Exception{
		if(StringUtils.isNotBlank(taskId)){
			ActBpmTaskLeavelEntity_HI taskLeavel = bpmTaskLeavelServer.getById(taskId);
			if(taskLeavel == null){
				return false;
			}
			processInstanceId = taskLeavel.getTopProcessInstanceId();
			JSONObject queryParamJSON = new JSONObject();
			queryParamJSON.put("processInstanceId", processInstanceId);
			queryParamJSON.put("excludeJump", "true");
			List<ActBpmHiTaskEntity_HI_RO> historicTasks = bpmHistoryServer.findHistoricTasks(queryParamJSON, 1, 1).getData();
			if(historicTasks == null || historicTasks.isEmpty()){
				return false;
			}
			ActBpmHiTaskEntity_HI_RO lastTask = historicTasks.get(0);
			if(!StringUtils.equals(taskId, lastTask.getTaskId())){
				return false;
			}
			return "1".equals(lastTask.getRevokeStatus())? true: false;
		}else{
			if(StringUtils.isBlank(processInstanceId)){
				return false;
			}
			JSONObject queryParamJSON = new JSONObject();
			queryParamJSON.put("processInstanceId", processInstanceId);
			queryParamJSON.put("excludeJump", "true");
			List<ActBpmHiTaskEntity_HI_RO> historicTasks = bpmHistoryServer.findHistoricTasks(queryParamJSON, -1, -1).getData();
			if(historicTasks == null || historicTasks.isEmpty()){
				return false;
			}
			ActBpmHiTaskEntity_HI_RO lastTask = historicTasks.get(0);
			if(historicTasks.size() == 1){
				return "1".equals(lastTask.getRevokeStatus())? true: false;
			}else {
				ActBpmHiTaskEntity_HI_RO firstTask = historicTasks.get(historicTasks.size() - 1);
				if(StringUtils.equals(firstTask.getTaskDefinitionId(), lastTask.getTaskDefinitionId())
						&& "1".equals(lastTask.getRevokeStatus())){
					return true;
				}else{
					return false;
				}
			}
		}
	}
    
    /**
     * 任务签收，签收后只有签收人可办理
     * @author laoqunzhao 2018-05-04
     * @param taskId 任务ID
     * @param bpmUserId 签收人ID
     */
    @Override
    public void claim(String taskId, String bpmUserId){
    	Assert.isTrue(StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(bpmUserId), "参数错误");
    	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    	Assert.notNull(task, "任务不存在");
    	taskService.claim(taskId, bpmUserId);
    }
    
    /**
     * 取消任务签收
     * @author laoqunzhao 2018-05-04
     * @param taskId 任务ID
     */
    @Override
    public void unclaim(String taskId){
    	Assert.isTrue(StringUtils.isNotBlank(taskId), "参数错误");
    	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    	Assert.notNull(task, "任务不存在");
    	taskService.unclaim(taskId);
    }
    
    /**
     * 任务转办
     * @author laoqunzhao 2018-07-07
     * @param taskIds 任务ID
     * @param bpmUserId 流程用户ID
     */
    @Override
    public void transfer(List<String> taskIds, String bpmUserId){
    	Assert.isTrue(taskIds != null && !taskIds.isEmpty() && StringUtils.isNotBlank(bpmUserId), "参数错误!");
    	for(String taskId : taskIds) {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			Assert.notNull(task, "ID为" + taskId + "的任务不存在!");
			String owner = StringUtils.isNotBlank(task.getAssignee()) ? task.getAssignee() : task.getOwner();
			if (StringUtils.isNotBlank(owner)) {
				taskService.setOwner(taskId, owner);
			}
			taskService.setAssignee(taskId, bpmUserId);
			//发送待办消息
			String topProcessInstanceId = bpmProcessServer.getTopProcessInstanceIdByTaskId(taskId);
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(topProcessInstanceId);
			List<String> assigneeList = new ArrayList<String>();
			assigneeList.add(bpmUserId);
			bpmMessageServer.sendTodoMessage(bpmList, task, assigneeList);
		}
    }
    
    /**
     * 任务锁定，通过categoryId标识，1.锁定办理，2.锁定撤回
     * @author laoqunzhao 2018-06-20
     * @param taskId 任务ID
     * @param userId 办理人
     */
    @Override
    @Transactional
    public String lock(String taskId, String userId){
    	Task task = get(taskId);
    	if(task == null) {
    		return "任务不存在";
    	}
    	if(StringUtils.equals(task.getCategory(), WorkflowConstant.TASK_STATUS_RESOLVING) && !StringUtils.equals(task.getAssignee(), userId)
    			|| StringUtils.equals(task.getCategory(), WorkflowConstant.TASK_STATUS_LOCKED)) {
    		return "任务已锁定";
    	}
    	task.setCategory(WorkflowConstant.TASK_STATUS_RESOLVING);
    	/*if(!StringUtils.equals(task.getAssignee(), userId)) {
    		task.setAssignee(userId);
    	}*/
		taskService.saveTask(task);
		return WorkflowConstant.STATUS_SUCESS;
    }
    
    /**
     * 撤回锁定，通过categoryId标识，1.锁定办理，2.锁定撤回
     * @author laoqunzhao 2018-06-20
     * @param processInstanceId 流程实例ID
     */
    @Override
    @Transactional
    public String lock(String processInstanceId) {
    	try {
	    	List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	    	if(tasks == null || tasks.isEmpty()) {
	    		return "任务不存在";
	    	}
	    	for(Task task: tasks) {
	    		if(StringUtils.equals(task.getCategory(), WorkflowConstant.TASK_STATUS_RESOLVING)) {
	    			//回滚已锁定的操作
	    			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    			return "办理中不可撤回";
	    		}else {
	    			task.setCategory(WorkflowConstant.TASK_STATUS_LOCKED);
	    			taskService.saveTask(task);
	    		}
	    	}
    	}catch(Exception e) {
    		LOGGER.error(e.getMessage(), e);
    		return "失败";
    	}
    	return WorkflowConstant.STATUS_SUCESS;
    }
    
    /**
     * 实现任意节点跳转
     * @author laoqunzhao 2018-05-04
     * @param executionId 执行ID，一般为流程实例ID
     * @param activityId 节点ID
     * @param taskId 任务ID
     */
    @Override
    public void jump(String executionId, String activityId, String taskId){
        TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;
        //通过Command方式实现跳转
        taskServiceImpl.getCommandExecutor().execute(new JumpTaskCmd(executionId, activityId, taskId)); 
    }
    
    /**
     * 实现子任务表单属性的保存
     * @author laoqunzhao 2018-06-15
     * @param processInstance 流程实例
     * @param properties 表单属性
     * @param taskId 任务ID
     */
    private void recordFormProperties(ExecutionEntity processInstance, Map<String, String> properties, String taskId) {
    	TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;
        taskServiceImpl.getCommandExecutor().execute(new FormPropertiesCmd(processInstance, properties, taskId)); 
    }
    
    /**
     * 获取任务的办理人ID
     * @author laoqunzhao 2018-05-04
     * @param taskId 任务ID
     */
    @Override
    public List<String> getTaskBpmUserIds(String taskId){
    	List<String> assignList = new ArrayList<>();
    	List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskId);
        if(identityLinks!=null && !identityLinks.isEmpty()) {
            for(IdentityLink identityLink: identityLinks) {
            	if(IdentityLinkType.ASSIGNEE.equals(identityLink.getType())) {
            		if(StringUtils.isNotBlank(identityLink.getUserId())) {
            			assignList = new ArrayList<>();
            			assignList.add(identityLink.getUserId());
            			return assignList;
            		}
            	}else if(StringUtils.isNotBlank(identityLink.getUserId())) {
            		assignList.add(identityLink.getUserId());
            	}          	
            }
        }
		return assignList;
    }

    
    /**
     * 获取任务办理人
     * @author laoqunzhao 2018-05-19
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionId 任务定义ID
     * @return Activiti用户ID集合
     */
    @Override
    public List<Object> getTaskBpmUserIds(String processInstanceId, String taskDefinitionId){
    	Integer bpmListId = null;
		if(StringUtils.isNotBlank(processInstanceId)) {
			//针对有可能是子流程的情况获取顶层流程实例ID
			processInstanceId = bpmProcessServer.getTopProcessInstanceId(processInstanceId);
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId);
			if(bpmList != null) {
				bpmListId = bpmList.getListId();
			}
		}
		return getTaskBpmUserIds(bpmListId, null, taskDefinitionId);
    }
    
    /**
     * 获取任务办理人
     * @author laoqunzhao 2018-05-19
     * @param bpmListId 申请单ID
     * @param processDefinitionId 流程定义ID（当流程未发起根据流程ID获取任务办理人）
     * @param taskDefinitionId 任务定义ID
     * @return Activiti用户ID集合
     */
    @Override
    public List<Object> getTaskBpmUserIds(Integer bpmListId, String processDefinitionId, String taskDefinitionId){
    	ActBpmListEntity_HI bpmList = bpmListServer.getById(bpmListId);
		if(bpmList != null && StringUtils.isBlank(processDefinitionId)) {
			processDefinitionId = bpmList.getProcessDefinitionId();
		}
        //根据流程定义ID获取流程定义
        ProcessDefinition  processDefinition  = repositoryService.getProcessDefinition(processDefinitionId);
        if(processDefinition == null) {
        	return null;
        }
        //获取流程任务节点设置
        ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(processDefinition.getKey(), taskDefinitionId, true);
        if(taskConfig == null || taskConfig.getProcessers() == null || taskConfig.getProcessers().isEmpty()) {
        	return null;
        }
        for(ActBpmTaskProcesserEntity_HI processer: taskConfig.getProcessers()) {
        	if(processer.getDisabled() != null && processer.getDisabled()==1) {
        		continue;
        	}
        	List<Object> assignList = new ArrayList<Object>();
            List<String> roleKeyList = new ArrayList<String>();
        	String processerIdsStr = processer.getProcesserIds();
			String processerRoleKeysStr = processer.getProcesserRoleKeys();
			if(StringUtils.isNotBlank(processerIdsStr)) {
				String[] processerIds = processerIdsStr.split(",");
				for(String processerId: processerIds) {
					if(StringUtils.isNotBlank(processerId) && !assignList.contains(processerId)) {
						assignList.add(processerId);
					}
				}
			}
			if(StringUtils.isNotBlank(processerRoleKeysStr)) {
				String[] processerRoleKeys = processerRoleKeysStr.split(",");
				for(String processerRoleKey: processerRoleKeys) {
					if(StringUtils.isNotBlank(processerRoleKey) && !roleKeyList.contains(processerRoleKey)) {
						roleKeyList.add(processerRoleKey);
					}
				}
				//从角色中取出办理人
		        if(!roleKeyList.isEmpty()) {
		            //根据角色获取办理人列表
		            List<Object> userIds = bpmRoleServer.getRoleBpmUserIds(roleKeyList, bpmListId);
		            if(userIds != null && !userIds.isEmpty()) {
		                for(Object userId: userIds) {
		                    if(!assignList.contains(userId)) {
		                        assignList.add(userId);
		                    }
		                }
		            }
		        }
			}
			if(!assignList.isEmpty()) {
				return assignList;
			}
        }        
        return null;
        
    }
    
    /**
	 * 获取流程抄送人ID
	 * @author laoqunzhao 2018-05-19
	 * @param taskId 任务ID
	 * @return 抄送人ID
	 */
    @Override
    public List<String> getTaskCcBpmUserIds(String taskId) {
		List<String> assignList = new ArrayList<String>();
		if(StringUtils.isBlank(taskId)){
			return assignList;
		}
		ActBpmHiTaskEntity_HI_RO historicTaskInstance = bpmHistoryServer.getBpmHistoricTaskByTaskId(taskId);
        if(historicTaskInstance == null || StringUtils.isBlank(historicTaskInstance.getTaskDefinitionId())
				|| StringUtils.isBlank(historicTaskInstance.getProcessDefinitionKey())) {
            return assignList;
        }
        bpmHistoryServer.getByTaskId(taskId);
        List<String> roleKeyList = new ArrayList<String>();
        ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(historicTaskInstance.getProcessDefinitionKey(), historicTaskInstance.getTaskDefinitionId(), true);
        if(taskConfig != null) {
            if(StringUtils.isNotBlank(taskConfig.getCcIds())) {
                String[] assignIds = taskConfig.getCcIds().split(",");
                for(String assignId:assignIds) {
                    assignList.add(assignId);
                }
            }
            if(StringUtils.isNotBlank(taskConfig.getCcRoleKeys())) {
                String[] roleKeys = taskConfig.getCcRoleKeys().split(",");
                for(String roleKey: roleKeys) {
                    if(!roleKeyList.contains(roleKey)) {
                        roleKeyList.add(roleKey);
                    }
                } 
            }
        }
        //从角色中取出抄送人
        if(!roleKeyList.isEmpty()) {
            //根据角色获取抄送人列表
            List<Object> userIds = bpmRoleServer.getRoleBpmUserIds(roleKeyList, historicTaskInstance.getProcessInstanceId());
            if(userIds != null && !userIds.isEmpty()) {
                for(Object userId: userIds) {
                	if(userId == null) {
            			continue;
            		}
            		if(userId instanceof String) {
            			if(!assignList.contains(userId.toString())) {
            				assignList.add(userId.toString());
                        }
            		}else if(userId instanceof List) {
            			@SuppressWarnings("unchecked")
    					List<String> userIds_ = (List<String>) userId;
            			for(String userId_ :userIds_) {
            				if(!assignList.contains(userId_)) {
            					assignList.add(userId_);
                            }
            			}
            		}
                }
            }
        }
        return assignList;
    }
    
    /**
	 * 获取流程实例中任务的办理人,用于流程自定指定人
	 * @author laoqunzhao 2018-05-23
	 * @param processInstanceId 流程实例ID
	 * @param processDefinitionId 流程定义ID
	 * @param taskDefinitionId 任务定义ID
	 * @return 流程任务及指派人
	 * @throws Exception
	 */
	@Override
	public ActUserTaskEntity_RO getTaskAssignee(String processInstanceId, String processDefinitionId, String taskDefinitionId) throws Exception {
		Object tasksAssignees = runtimeService.getVariable(processInstanceId, WorkflowConstant.TASKS_ASSIGNEES);
		if(tasksAssignees == null || StringUtils.isBlank(tasksAssignees.toString())) {
			return null;
		}
		JSONArray assigneesJSON = JSON.parseArray(tasksAssignees.toString());
		List<ActUserTaskEntity_RO> actUserTasks = getTasksAssignees(assigneesJSON, processDefinitionId, taskDefinitionId);
		return actUserTasks != null && !actUserTasks.isEmpty()? actUserTasks.get(0): null;
	}
	
	/**
	 * 查询具体任务的需指定办理人的任务节点及已指定的办理人,流程发起页面
	 * @author laoqunzhao 2018-05-23
	 * @param bpmListId 申请单ID
	 * @return 需指定人的任务节点及办理人
	 * @throws Exception
	 */
	@Override
	public List<ActUserTaskEntity_RO> getFirstTaskAssignees(Integer bpmListId) throws Exception {
		ActBpmListEntity_HI bpmList = bpmListServer.getById(bpmListId);
		if(bpmList == null || StringUtils.isBlank(bpmList.getExtend())) {
			return null;
		}
		JSONObject extendJSON = JSON.parseObject(bpmList.getExtend(), JSONObject.class);
		if(!extendJSON.containsKey(WorkflowConstant.TASKS_ASSIGNEES)) {
			return null;
		}
		List<ActUserTaskEntity_RO> actUserTasks = new ArrayList<ActUserTaskEntity_RO>();
		JSONArray assigneesJSON = extendJSON.getJSONArray(WorkflowConstant.TASKS_ASSIGNEES);
		List<ActUserTaskEntity_RO> assignees = getTasksAssignees(assigneesJSON, bpmList.getProcessDefinitionId(), null);
		UserTask firstTask = bpmProcessServer.getFirstUserTask(bpmList.getProcessDefinitionId());
		//从流程设置中获取选人的任务节点
		List<UserTask> assigneeUserTasks = bpmTaskConfigServer.findAssigneeUserTasks(bpmList.getProcessDefinitionKey(), firstTask.getId());
		if(assigneeUserTasks != null && !assigneeUserTasks.isEmpty()) {
			for(UserTask assigneeUserTask: assigneeUserTasks) {
				ActUserTaskEntity_RO actUserTask = new ActUserTaskEntity_RO();
				actUserTask.setId(assigneeUserTask.getId());
				actUserTask.setName(assigneeUserTask.getName());
				if(assignees != null && !assignees.isEmpty()) {
					for(ActUserTaskEntity_RO assignee: assignees) {
						if(assignee.getId().equals(actUserTask.getId())) {
							actUserTask.setUsers(assignee.getUsers());
							break;
						}
					}
				}
				actUserTasks.add(actUserTask);
			}
		}
		return actUserTasks;
	}
	
	/**
	 * 查询具体任务的需指定办理人的任务节点及已指定的办理人,用于任务办理页面
	 * @author laoqunzhao 2018-05-23
	 * @param taskId 任务ID
	 * @return 需指定人的任务节点及办理人
	 */
	@Override
	public List<ActUserTaskEntity_RO> getTaskAssignees(String taskId) throws Exception {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null) {
			return null;
		}
		Object tasksAssignees = runtimeService.getVariable(task.getProcessInstanceId(), WorkflowConstant.TASKS_ASSIGNEES);
		if(tasksAssignees == null || StringUtils.isBlank(tasksAssignees.toString())) {
			return null;
		}
		List<ActUserTaskEntity_RO> actUserTasks = new ArrayList<ActUserTaskEntity_RO>();
		JSONArray assigneesJSON = JSON.parseArray(tasksAssignees.toString());
		List<ActUserTaskEntity_RO> assignees = getTasksAssignees(assigneesJSON, task.getProcessDefinitionId(), null);
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
		//从流程设置中获取选人的任务节点
		List<UserTask> assigneeUserTasks = bpmTaskConfigServer.findAssigneeUserTasks(processDefinition.getKey(), task.getTaskDefinitionKey());
		if(assigneeUserTasks != null && !assigneeUserTasks.isEmpty()) {
			for(UserTask assigneeUserTask: assigneeUserTasks) {
				ActUserTaskEntity_RO actUserTask = new ActUserTaskEntity_RO();
				actUserTask.setId(assigneeUserTask.getId());
				actUserTask.setName(assigneeUserTask.getName());
				if(assignees != null && !assignees.isEmpty()) {
					for(ActUserTaskEntity_RO assignee: assignees) {
						if(assignee.getId().equals(actUserTask.getId())) {
							actUserTask.setUsers(assignee.getUsers());
							break;
						}
					}
				}
				actUserTasks.add(actUserTask);
			}
		}
		return actUserTasks;
	}
	
	/**
	 * 添加子任务
	 * @author laoqunzhao 2018-06-19
	 * @param parentTaskId 父任务ID
	 * @param taskName 任务名称
	 * @param description 任务描述
	 * @param parentUserId 父任务办理人ID
	 * @param taskUserId 任务办理人ID
	 * @return 办理成功"S",不成功返回提示
	 */
	@Override
	public String addSubTask(String parentTaskId, String taskName, String description,
			Integer parentUserId, Integer taskUserId) {
        Task parentTask = this.get(parentTaskId);
        if(parentTask == null) {
        	return "任务不存在！";
        }
        ActIdUserEntity_RO  parentTaskUser = bpmUserServer.findUserBySysId(parentUserId);
        ActIdUserEntity_RO  subTaskUser = bpmUserServer.findUserBySysId(taskUserId);
        if(subTaskUser == null){
        	return "审批人不存在！";
		}
        //签收任务
        //this.claim(parentTaskId, parentTaskUser.getId());
        //增加助审操作记录
        addSubTaskRecord(parentTask, taskName, description, parentTaskUser, null);
        
        Task task = taskService.newTask(); // 创建一个新的任务
        task.setParentTaskId(parentTaskId); // 设置父任务ID，建立上下级关系
        task.setOwner(subTaskUser.getId()); // 设置拥有人
        task.setAssignee(subTaskUser.getId()); // 设置办理人
        task.setName(taskName); // 设置任务名称
        task.setDescription(description); // 设置任务描述
    	taskService.saveTask(task); // 保存任务
		//发送待办消息
		String processInstanceId = bpmProcessServer.getTopProcessInstanceIdByTaskId(parentTaskId);
    	if(processInstanceId != null){
    		ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId);
    		List<String> assigneeList = new ArrayList<String>();
    		assigneeList.add(task.getAssignee());
    		bpmMessageServer.sendTodoMessage(bpmList, task, assigneeList);
		}
		return WorkflowConstant.STATUS_SUCESS;
    }
	
	/**
	 * 将添加子任务操作作为一个任务记录记录到流程任务历史表
	 * @param parentTask 父任务
	 * @param taskName 任务名称
	 * @param description 描述
	 * @param user 审批人
	 */
	private void addSubTaskRecord(Task parentTask, String taskName, String description, ActIdUserEntity_RO user, Map<String, String> properties) {
		Task task = taskService.newTask(); // 创建一个新的任务
        task.setParentTaskId(parentTask.getId()); // 设置父任务ID，建立上下级关系
        task.setOwner(user.getId()); // 设置拥有人
        task.setAssignee(user.getId()); // 设置办理人
        task.setName(taskName); // 设置任务名称
        task.setDescription(description); // 设置任务描述
    	taskService.saveTask(task); // 保存任务
    	properties = properties != null ? properties : new HashMap<String, String>();
    	if(!properties.containsKey(WorkflowConstant.PROP_OPTION)) {
    		properties.put(WorkflowConstant.PROP_OPTION, WorkflowConstant.OPERATE_SUBMIT_ADD_SUBTASK);
    	}
    	if(!properties.containsKey(WorkflowConstant.PROP_OPINION)) {
    		properties.put(WorkflowConstant.PROP_OPINION, description);
    	}
    	String processInstanceId = bpmProcessServer.getProcessInstanceIdByTaskId(parentTask.getId());
    	List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
    	if(executions != null && !executions.isEmpty()){
    		for(Execution execution : executions){
				if(StringUtils.isBlank(execution.getSuperExecutionId())){
					recordFormProperties((ExecutionEntity)execution, properties, task.getId());
				}
			}
		}
    	task.setParentTaskId(null);//设为null去除助审标识
    	taskService.saveTask(task); // 保存任务
    	taskService.complete(task.getId());
    	//修改任务创建时间
		jdbcTemplate.update("UPDATE ACT_HI_TASKINST SET START_TIME_=to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parentTask.getCreateTime())
				+ "','yyyy-mm-dd hh24:mi:ss') WHERE ID_='" + task.getId() + "'");
	}

	/**
	 * 流程任务抄送
	 * @param taskId 任务ID
	 * @param user 抄送人
	 */
	@Override
	public void ccTaskAsynchron(final String taskId, final ActIdUserEntity_RO user) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					ccTask(taskId, user);
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}
    
    /**
     * 流程任务抄送
     * @param taskId 任务ID
	 * @param user 抄送人
     */
	@Override
    public void ccTask(String taskId, ActIdUserEntity_RO user) {
		if(StringUtils.isBlank(taskId) || user == null){
			return;
		}
        ActBpmNotifyTaskEntity_HI notify = new ActBpmNotifyTaskEntity_HI();
        notify.setStatus(0);
        notify.setTaskId(taskId);
        List<String> bpmUserIds = getTaskCcBpmUserIds(taskId);
        if(bpmUserIds == null || bpmUserIds.isEmpty()) {
        	return;
        }
        @SuppressWarnings("unchecked")
		List<Integer> userIds = (List<Integer>) bpmUserServer.findSysUserIds(bpmUserIds, null, null, "");
        if(userIds == null || userIds.isEmpty()) {
        	return;
        }
        JSONArray userJson = new JSONArray();
        if(userIds != null && !userIds.isEmpty()) {
            for(Integer userId : userIds) {
                userJson.add(userId);
            }
        }
        JSONObject msgJson = (JSONObject) JSON.toJSON(notify);
        msgJson.put("operatorUserId", user.getUserId());
        msgJson.put("userIds", userJson);
        bpmNotifyTaskServer.save(msgJson, user);
		//发送抄送消息
		bpmMessageServer.sendCcMessage(taskId, bpmUserIds);
    }

    
    
    
    
    /**
	 * 获取流程实例中任务的办理人
	 * @author laoqunzhao 2018-05-23
	 * @param assigneesJSON 任务指定人变量值JSON
	 * @param processDefinitionId 流程定义ID
	 * @param taskDefinitionId 任务定义ID，taskDefinitionId不为空，只取这个任务的办理人
	 * @return 流程任务及指派人
	 * @throws Exception
	 */
	private List<ActUserTaskEntity_RO> getTasksAssignees(JSONArray assigneesJSON, String processDefinitionId,String taskDefinitionId) throws Exception {
		if(assigneesJSON == null || assigneesJSON.isEmpty()) {
			return null;
		}
		List<ActUserTaskEntity_RO> actUserTasks = new ArrayList<ActUserTaskEntity_RO>();
		//所有用户任务节点
		List<UserTask> allUserTasks = bpmModelServer.findUserTasks(null, processDefinitionId, false);
		//所有办理人ID
		List<String> allUserIds = new ArrayList<String>();
		//将用户任务与办理人关系转换为Map结构
		Map<String,List<String>> taskUsers = new HashMap<String,List<String>>();
		for(int i=0; i<assigneesJSON.size(); i++) {
			JSONObject assigneeJSON = assigneesJSON.getJSONObject(i);
			String id = assigneeJSON.getString("id");
			//taskDefinitionId不为空，只取这个任务的办理人
			if(StringUtils.isNotBlank(taskDefinitionId) && !taskDefinitionId.equals(id)) {
				continue;
			}
			List<String> taskUserIds = new ArrayList<String>();
			if(assigneeJSON.containsKey("userIds")) {
				JSONArray userIdsJSON = assigneeJSON.getJSONArray("userIds");
				for(int j=0; j<userIdsJSON.size(); j++) {
					String userId = userIdsJSON.getString(j);
					taskUserIds.add(userId);
					if(!allUserIds.contains(userId)) {
						allUserIds.add(userId);
					}
				}
			}
			taskUsers.put(id, taskUserIds);
		}
		//查询所有的任务办理人
		List<ActIdUserEntity_RO> allUsers = null;
		if(allUserIds != null && !allUserIds.isEmpty()) {
			allUsers = bpmUserServer.findUsersBySysIds(allUserIds);
		}
		//提取任务及用户信息
		for(UserTask userTask : allUserTasks) {
			if(taskUsers.containsKey(userTask.getId())) {
				ActUserTaskEntity_RO actUserTask = new ActUserTaskEntity_RO();
				actUserTask.setId(userTask.getId());
				actUserTask.setName(userTask.getName());
				List<String> userIds = taskUsers.get(userTask.getId());
				if(userIds != null && !userIds.isEmpty() && allUsers != null && !allUsers.isEmpty()) {
					List<ActIdUserEntity_RO> users = new ArrayList<ActIdUserEntity_RO>();
					for(ActIdUserEntity_RO user: allUsers) {
						if(userIds.contains(String.valueOf(user.getUserId()))) {
							users.add(user);
						}
					}
					actUserTask.setUsers(users);
				}
				actUserTasks.add(actUserTask);
			}
		}
		return actUserTasks;
	}
	
	/**
     * 保存任务流程变量
     * @author sie-laoqunzhao 2018-07-03
     * @param task 流程任务
     * @param processInstanceId 流程实例ID
     * @param variables 变量
     */
    private void setTaskVariables(Task task, String processInstanceId, Map<String, Object> variables) {
    	//任务选人变量
    	String variableName = WorkflowConstant.TASKS_ASSIGNEES;
    	if(variables == null || !variables.containsKey(variableName)) {
    		return;
    	}
    	Object assigneeNew = variables.get(variableName);
    	if(assigneeNew == null || StringUtils.isBlank(assigneeNew.toString())) {
    		return;
    	}
    	//处理选人变量
    	Object assigneed = runtimeService.getVariable(processInstanceId, variableName);
    	if(assigneed != null && StringUtils.isNotBlank(assigneed.toString())) {
    		JSONArray assigneedJSON = JSON.parseArray(assigneed.toString());
    		JSONArray assigneeNewJSON = JSON.parseArray(assigneeNew.toString());
    		for(int i=0; i<assigneedJSON.size(); i++) {
    			boolean exist = false;
    			JSONObject json1 = assigneedJSON.getJSONObject(i);
    			for(int j=0; j<assigneeNewJSON.size(); j++) {
    				JSONObject json2 = assigneeNewJSON.getJSONObject(j);
    				if(json1.get("id").equals(json2.get("id"))) {
    					exist = true;
    					break;
    				}
        		}
    			if(!exist) {
    				assigneeNewJSON.add(json1);
    			}
    		}
    		variables.put(WorkflowConstant.TASKS_ASSIGNEES, assigneeNewJSON.toString());
    	}else {
    		variables.put(WorkflowConstant.TASKS_ASSIGNEES, assigneeNew.toString());
    	}
    	runtimeService.setVariables(processInstanceId, variables);
    }

    
    
	/**
	 * 通过CommandContext方式实例节点的自由跳转	 
	 */
    class JumpTaskCmd implements Command<Object> {  
        protected String executionId;//执行ID，一般情况下为流程实例ID
        protected String activityId;//需跳转的节点
        protected String taskId;//任务ID，当前任务标识为completed表示人工处理跳转，其他为jump表示自动跳过，可为空
        
        public JumpTaskCmd(String executionId, String activityId, String taskId) {  
            this.executionId = executionId;  
            this.activityId = activityId;  
            this.taskId = taskId;
        }  

       @Override
       public Object  execute(CommandContext commandContext) {
           List<Task> activeTasks = findActiveTasks(executionId);
           if(activeTasks != null && !activeTasks.isEmpty()) {
        	   for(Task activeTask : activeTasks) {
        		   String action = StringUtils.equals(activeTask.getId(), taskId)?"completed":"jump";
        		   commandContext.getTaskEntityManager().deleteTask((TaskEntity)activeTask, action, false);  
        	   }
           }
           ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findExecutionById(executionId);
           executionEntity.destroyScope("jump");  //
           ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();  
           ActivityImpl activity = processDefinition.findActivity(activityId);  
           Map<String, Object> properties = activity.getProperties();
           //多实例任务,创建ExecutionEntity，保证流程正常流出
           if("userTask".equals(properties.get("type")) && properties.containsKey("multiInstance")
        		   && ("sequential".equals(properties.get("multiInstance")) || "parallel".equals(properties.get("multiInstance")))) {
        	   ExecutionEntity subExecution = executionEntity.createExecution();
               subExecution.setActive(true);
               subExecution.setActivity(activity);
               subExecution.setParentId(executionId);
               subExecution.setProcessDefinitionId(executionEntity.getProcessDefinitionId());
               subExecution.executeActivity(activity);
               executionEntity.setActive(false);
           }else {
        	   executionEntity.executeActivity(activity);
           }
           //对当前任务节点更新完成时间，使得在HistoricActivity查询正常
           if(StringUtils.isNotBlank(taskId)) {
        	   updateHistoricActivityInstance(taskId, commandContext);
           }
           return executionEntity; 
       } 
       
       /**
        * 对当前任务节点更新完成时间，使得在HistoricActivity查询正常
        * @param taskId 任务ID
        */
       private void updateHistoricActivityInstance(String taskId, CommandContext commandContext) {
           StringBuffer sql = new StringBuffer("SELECT INST.* FROM ACT_HI_ACTINST INST WHERE INST.TASK_ID_ = #{taskId}");
           NativeHistoricActivityInstanceQuery historicQuery = historyService.createNativeHistoricActivityInstanceQuery().sql(sql.toString());
           historicQuery.parameter("taskId", taskId);
           List<HistoricActivityInstance> list = historicQuery.list();
           if(list != null && !list.isEmpty()) {
               DbSqlSession session = commandContext.getSession(DbSqlSession.class);
               for(HistoricActivityInstance instance:list) {
                   HistoricActivityInstanceEntity entity = (HistoricActivityInstanceEntity) instance;
                   entity.setEndTime(new Date());
                   if(entity.getStartTime() != null) {
                       entity.setDurationInMillis(entity.getEndTime().getTime() - entity.getStartTime().getTime());
                   }
                   session.update("updateHistoricActivityInstance", entity);
               }
           }
       }
      
    }
	
	class FormPropertiesCmd implements Command<Comment> {  
		private ExecutionEntity processInstance;
		private Map<String, String> properties;
		private String taskId;
          
        public FormPropertiesCmd(ExecutionEntity processInstance, Map<String, String> properties, String taskId) {  
            this.processInstance = processInstance;  
            this.properties = properties;  
            this.taskId = taskId;
        }  

       @Override
       public Comment execute(CommandContext commandContext) {
    	   commandContext.getHistoryManager().reportFormPropertiesSubmitted(processInstance, properties, taskId);
           return null; 
       }        
    }

}
