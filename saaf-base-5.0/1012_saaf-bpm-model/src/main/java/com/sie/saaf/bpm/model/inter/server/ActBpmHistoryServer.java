package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmTaskLeavelEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticTaskEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Component("actBpmHistoryServer")
public class ActBpmHistoryServer implements IActBpmHistory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmHistoryServer.class);
	
	@Autowired
    private JedisCluster jedisCluster;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private IActBpmCategory bpmCategoryServer;
    
    @Autowired
    private IActBpmUser bpmUserServer;

    @Autowired
    private IActBpmModel bpmModelServer;

    @Autowired
    private IActBpmProcess bpmProcessServer;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IActBpmTask bpmTaskServer;
    
    @Autowired
    private IActBpmTaskLeavel bpmTaskLeavelServer;
    
    @Autowired
    private BaseViewObject<ActBpmHiTaskEntity_HI_RO> bpmHiTaskDAO_HI_RO;
    
    @Autowired
	private BaseViewObject<ActStatisticTaskEntity_RO> statisticTaskDAO_HI;
	    
    
    /**
	 * 办理历史查询
	 * @author laoqunzhao 2018-04-28
     * @param queryParamJSON JSONObject
     * createdBy 发起人ID
     * drafter 发起人
     * listCode 流程编号
     * listName 流程名称
     * title 流程标题
     * businessKey 业务主键
     * billNo 业务申请单号
     * categoryId 流程分类
     * systemCode 系统代码
     * processDefinitionKey 流程定义Key，条件=
     * processKey 流程标识，条件=
     * processInstanceId 流程实例ID，条件=
     * startDate 任务起始时间，格式yyyy-MM-dd
     * endDate 任务截止时间，格式yyyy-MM-dd
     * startCreateDate 任务创建起始时间，格式yyyy-MM-dd
     * endCreateDate 任务创建截止时间，格式yyyy-MM-dd
     * communicated 发起沟通 Y.是   N.否
     * status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
     * taskStatus 任务状态  PENDING.未接收   RESOLVING.办理中  RESOLVED.已办结
     * simple 只返回主要字段 true.是  false.否
     * duration 用时时长(>=)秒
     * waiting 距今时长(>=)秒
     * userId 办理人
     * excludeDraft 排除起草  true.是   false.否
     * excludeJump 排除自动跳过  true.是   false.否
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return 流程发起数据集合Pagination<ActBpmListEntity_HI>
	 * @throws Exception 
	 */
	@Override
	public Pagination<ActBpmHiTaskEntity_HI_RO> findHistoricTasks(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
        StringBuffer condition = new StringBuffer();
        StringBuffer querySql = new StringBuffer("true".equals(queryParamJSON.getString("simple"))?ActBpmHiTaskEntity_HI_RO.QUERY_SIMPLE_HI_TASK_SQL:ActBpmHiTaskEntity_HI_RO.QUERY_ALL_HI_TASK_SQL);
        condition.append(" and (task.delete_reason_ is null or task.delete_reason_<>'jump')");
		//状态
		String taskStatus = StringUtils.trimToNull(queryParamJSON.getString("taskStatus"));
		if(WorkflowConstant.TASK_STATUS_RESOLVING.equals(taskStatus)) {
            querySql = new StringBuffer(querySql.toString().replace(" 'RESOLVED' AS status,", " '" + taskStatus + "' AS status,"));
            condition.append(" and task.end_time_ is null and exists(select 1 from act_ru_task rutask where rutask.id_=task.id_ and rutask.category_ =:taskStatus)");
			paramsMap.put("taskStatus", taskStatus);
		}else if(WorkflowConstant.TASK_STATUS_PENDING.equals(taskStatus)) {
            querySql = new StringBuffer(querySql.toString().replace(" 'RESOLVED' AS status,", " '" + taskStatus + "' AS status,"));
            condition.append(" and task.end_time_ is null and exists(select 1 from act_ru_task rutask where rutask.id_=task.id_ and rutask.category_ is null)");
		}else if(WorkflowConstant.TASK_STATUS_ALL.equals(taskStatus)){
            condition.append(" and not exists(select 1 from act_ru_task rutask where rutask.parent_task_id_=task.id_ and rutask.suspension_state_=1)");
		}else{
            condition.append(" and task.end_time_ is not null");
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
		//流程实例ID
		String processInstanceId = StringUtils.trimToNull(queryParamJSON.getString("processInstanceId"));
		if(processInstanceId != null) {
            condition.append(" and bpm.proc_inst_id = :processInstanceId");
			paramsMap.put("processInstanceId", processInstanceId);
		}
		//流程单据状态
		String status = StringUtils.trimToNull(queryParamJSON.getString("status"));
		if(status != null) {
            condition.append(" and bpm.result = :status");
			paramsMap.put("status", status);
		}
		//是否发起沟通
		String communicated = StringUtils.trimToNull(queryParamJSON.getString("communicated"));
		if(communicated != null) {
            condition.append("Y".equals(communicated)?" and ":" and not");
            condition.append(" exists(select 1 from act_bpm_communicate cmc1 where cmc1.proc_inst_id=bpm.proc_inst_id and cmc1.type='COMMON' and cmc1.delete_flag=0)");
		}
        //任务创建时间
        String startCreateDate = StringUtils.trimToNull(queryParamJSON.getString("startCreateDate"));
        if(startCreateDate != null) {
            condition.append(" and task.start_time_ >= :startCreateDate");
            paramsMap.put("startCreateDate", ConvertUtil.stringToDateYMD(startCreateDate));
        }
        String endCreateDate = StringUtils.trimToNull(queryParamJSON.getString("endCreateDate"));
        if(endCreateDate != null) {
            condition.append(" and task.start_time_ < :endCreateDate");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(endCreateDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramsMap.put("endCreateDate", c.getTime());
        }
		//任务时间
        String startDate = StringUtils.trimToNull(queryParamJSON.getString("startDate"));
        if(startDate != null) {
            condition.append(" and task.end_time_ >= :startDate");
            paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
        }
        String endDate = StringUtils.trimToNull(queryParamJSON.getString("endDate"));
        if(endDate != null) {
            condition.append(" and task.end_time_ < :endDate");
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
        //用时时长用时时长(>=)秒
        String duration = StringUtils.trimToNull(queryParamJSON.getString("duration"));
        if(duration != null) {
            condition.append(" and task.end_time_ is not null and timestampdiff(second, start_time_, end_time_) >= :duration");
        	paramsMap.put("duration", Long.parseLong(duration));
        }
        //距今时长(>=)秒
        String waiting = StringUtils.trimToNull(queryParamJSON.getString("waiting"));
        if(waiting != null) {
            condition.append(" and task.end_time_ is null and timestampdiff(second, start_time_, now()) >= :waiting");
        	paramsMap.put("waiting", Long.parseLong(waiting));
        }
        //办理人
        String userId = StringUtils.trimToNull(queryParamJSON.getString("userId"));
        if(userId != null) {
            condition.append(" and task.assignee_ = :userId");
        	paramsMap.put("userId", userId);
        }
        //排除起草
        String excludeDraft = StringUtils.trimToNull(queryParamJSON.getString("excludeDraft"));
        if("true".equals(excludeDraft)) {
            condition.append(" and exists(select 1 from act_hi_taskinst dftask where dftask.proc_inst_id_=bpm.proc_inst_id and dftask.start_time_<=task.start_time_)");
            //客户需求又不过滤驳回到申请人的节点
            //condition.append(" and not exists(select 1 from act_hi_detail detail2 left join act_hi_taskinst hitask2 on detail2.task_id_=hitask2.id_ ");
            //condition.append(" where hitask2.task_def_key_=task.task_def_key_ and detail2.name_= :optionName2 and detail2.text_= :optionDF2)");
            //paramsMap.put("optionName2", "option");
            //paramsMap.put("optionDF2", "DF");
        }
        //排除自动跳过
        String excludeJump = StringUtils.trimToNull(queryParamJSON.getString("excludeJump"));
        if("true".equals(excludeJump)) {
            condition.append(" and not exists(select 1 from act_hi_detail hidetail where hidetail.task_id_=task.id_ and hidetail.name_='option' and hidetail.text_='AJ')");
        }
        //系统代码
        String systemCode = StringUtils.trimToNull(queryParamJSON.getString("systemCode"));
        List<Integer> ouIds = new ArrayList<>();
        if(queryParamJSON.containsKey("ouIds")) {
            JSONArray ouIdsJSON = queryParamJSON.getJSONArray("ouIds");
            for(int i=0; i<ouIdsJSON.size(); i++) {
                ouIds.add(ouIdsJSON.getInteger(i));
            }
        }
        if(systemCode != null || !ouIds.isEmpty()) {
            condition.append(" and exists(select 1 from act_bpm_permission where proc_def_key=bpm.proc_def_key ");
            if(systemCode != null) {
                condition.append(" and upper(system_code) = :systemCode ");
                paramsMap.put("systemCode", systemCode.toUpperCase());
            }
            if(!ouIds.isEmpty()) {
                condition.append(" and ou_id in (" + StringUtils.join(ouIds, ",") + ")");
            }
            condition.append(")");
        }
        StringBuffer addCondition = new StringBuffer();
        //过滤六大活动审批流
        addCondition.append(" and not regexp_like(bpm.title,'DM|HWB|OSD|FG|CW|NPP') ");
        StringBuffer countSql = new StringBuffer(ActBpmHiTaskEntity_HI_RO.QUERY_ALL_HI_TASK_COUNT_SQL);
        countSql.append(condition);
        //countSql.append(addCondition);
        querySql.append(condition);
        //querySql.append(addCondition);
        SaafToolUtils.changeQuerySort(queryParamJSON, querySql, "task.end_time_ desc", false);
		Pagination<ActBpmHiTaskEntity_HI_RO> pagination = bpmHiTaskDAO_HI_RO.findPagination(querySql.toString(), countSql.toString(), paramsMap, pageIndex, pageRows);
		return pagination;
	}

    
    /**
     * 查询任务
     * @author laoqunzhao 2018-06-20
     * @param taskId 任务ID
     * @return HistoricTaskInstance
     */
    @Override
    public HistoricTaskInstance getByTaskId(String taskId) {
    	if(StringUtils.isBlank(taskId)) {
    		return null;
    	}
    	return historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
    }
    
    /**
     * 查询任务
     * @author laoqunzhao 2018-08-02
     * @param taskId 任务ID
     * @return ActBpmHiTaskEntity_HI_RO
     */
    @Override
    public ActBpmHiTaskEntity_HI_RO getBpmHistoricTaskByTaskId(String taskId) {
    	if(StringUtils.isBlank(taskId)) {
    		return null;
    	}
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
 		StringBuffer sql = new StringBuffer(ActBpmHiTaskEntity_HI_RO.QUERY_SINGLE_HI_TASK_SQL);
 		sql.append(" and task.id_ = :taskId");
 		paramsMap.put("taskId", taskId);
 		List<ActBpmHiTaskEntity_HI_RO> tasks = bpmHiTaskDAO_HI_RO.findList(sql.toString(), paramsMap);
 		return tasks != null && !tasks.isEmpty() ? tasks.get(0) : null;
    }

    /**
     * 根据任务主键集查询任务
     * @author laoqunzhao 2018-08-02
     * @param taskIds 任务ID
     * @return List<ActBpmHiTaskEntity_HI_RO>
     */
    @Override
    public List<ActBpmHiTaskEntity_HI_RO> findBpmHistoricTasksByTaskIds(List<String> taskIds) {
        if(taskIds == null || taskIds.isEmpty()) {
            return null;
        }
        StringBuffer sql = new StringBuffer(ActBpmHiTaskEntity_HI_RO.QUERY_SINGLE_HI_TASK_SQL);
        sql.append(" and task.id_ in ('" + StringUtils.join(taskIds, "','") + "')");
        return bpmHiTaskDAO_HI_RO.findList(sql.toString());
    }
    
    /**
     * 查询子任务的顶层任务
     * @author laoqunzhao 2018-06-20
     * @param taskId 任务ID
     * @return HistoricTaskInstance
     */
    @Override
    public HistoricTaskInstance getTopTaskByTaskId(String taskId) {
    	if(StringUtils.isBlank(taskId)) {
    		return null;
    	}
    	HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
    	HistoricTaskInstance historicTaskInstance = query.taskId(taskId).singleResult();
    	if(historicTaskInstance != null && StringUtils.isNotBlank(historicTaskInstance.getParentTaskId())) {
    		ActBpmTaskLeavelEntity_HI taskLeavel = bpmTaskLeavelServer.getById(taskId);
    		if(taskLeavel != null && StringUtils.isNotBlank(taskLeavel.getTopTaskId())) {
    			historicTaskInstance = query.taskId(taskLeavel.getTopTaskId()).singleResult();
    		}else {
    			while(historicTaskInstance != null && StringUtils.isNotBlank(historicTaskInstance.getParentTaskId())) {
    				historicTaskInstance = query.taskId(historicTaskInstance.getParentTaskId()).singleResult();
    			}
    		}
    	}
    	return historicTaskInstance;
    }
    
    /**
     * 查询流程实例
     * @author laoqunzhao 2018-06-20
     * @param processInstanceId 流程实例ID
     * @return HistoricProcessInstance
     */
    @Override
    public HistoricProcessInstance getByProcessInstanceId(String processInstanceId){
    	if(StringUtils.isBlank(processInstanceId)) {
    		return null;
    	}
    	HistoricProcessInstance historicProcessInstance = historyService
		        .createHistoricProcessInstanceQuery()
		        .processInstanceId(processInstanceId)
		        .singleResult();
		return historicProcessInstance;
    }
    
    /**
     * 查询流程的的任务记录
     * @author laoqunzhao 2018-06-19
     * @param processInstanceId 流程实例ID
     * @return List<HistoricTaskInstance>
     */
    @Override
    public List<HistoricTaskInstance> findHistoricTasks(String processInstanceId) {
    	if(StringUtils.isBlank(processInstanceId)) {
    		return null;
    	}        
        try {
            // 根据当前人的ID查询
            StringBuffer sql = new StringBuffer("FROM ACT_HI_TASKINST T1 LEFT OUTER JOIN ACT_HI_TASKINST T2");
            sql.append(" ON T1.PARENT_TASK_ID_ = T2.ID_ WHERE T1.END_TIME_ IS NOT NULL AND T1.DELETE_REASON_='completed'");
            sql.append(" AND (T1.PROC_INST_ID_ = #{processInstanceId1} OR T2.PROC_INST_ID_ = #{processInstanceId2})");
            //查询活动任务
            NativeHistoricTaskInstanceQuery query = historyService.createNativeHistoricTaskInstanceQuery()
            		.sql("SELECT T1.* "  + sql.toString() + " ORDER BY T1.END_TIME_ ASC");
            query.parameter("processInstanceId1", processInstanceId);
            query.parameter("processInstanceId2", processInstanceId);
            return query.list();
        }catch(Exception e){
        	LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 根据流程实例ID查询流程处理记录
     * @author laoqunzhao 2018-04-27
     * @param processInstanceId 流程实例ID
     * @param extendCall 是否展开获取子流程中的历史任务
     * @return 流程处理记录List<HistoricActivityInstance>
     */
    @Override
    public List<HistoricActivityInstance> findHistoricActivities( String processInstanceId, boolean extendCall){
        List<HistoricActivityInstance> list = historyService
        		.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        //处理调用子流程
        if(extendCall) {
        	for(int i=list.size()-1; i>=0; i--){
        		HistoricActivityInstance historicActivityInstance = list.get(i);
        		if(!"callActivity".equals(historicActivityInstance.getActivityType())) {
                	continue;
                }
            	List<HistoricActivityInstance> childs = findHistoricActivities(historicActivityInstance.getCalledProcessInstanceId(), true);
                //去除子流程的开始结束节点
                for(int j=childs.size()-1; j>=0; j--){
                    HistoricActivityInstance child = childs.get(j);
                    if(child.getActivityType().equals("startEvent") || child.getActivityType().equals("endEvent")){
                        childs.remove(j);
                    }
                }
                if(childs != null && !childs.isEmpty()) {
                	list.remove(i);
                	list.addAll(i, childs);
                }
            }
        }
        return list;
    }
    
    
    /**
     * 根据流程实例ID获取流程中最后一个执行中任务及最后一个已完成的任务（当前环节、上一环节）
     * @author laoqunzhao 2018-04-27
     * @param processInstanceIds 流程实例ID集
     * @param type 1.当前任务 2.最后一次任务 其他.所有
     * @return 历史记录集（当前环节、上一环节）List<HistoricTaskInstance>
     */
    @Override
    public List<ActBpmHiTaskEntity_HI_RO> findHistoricTasks(List<String> processInstanceIds, Integer type){
        if(processInstanceIds == null || processInstanceIds.isEmpty()) {
            return null;
        }
        String processInstanceIdsStr = "'" + StringUtils.join(processInstanceIds, "','") + "'";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(ActBpmHiTaskEntity_HI_RO.QUERY_SIMPLE_HI_TASK_SQL);
		sql.append(" and leavel.top_proc_inst_id in ("+processInstanceIdsStr+")");
		if(type != null && type == 1){
            //最后一个当前任务
            sql.append(" and task.end_time_ is null and not exists(select 1 from act_hi_taskinst task1 left join act_bpm_task_leavel leavel1 on leavel1.task_id=task1.id_ where task1.start_time_>task.start_time_ and leavel1.top_proc_inst_id=leavel.top_proc_inst_id and task1.end_time_ is null)");
        }else if(type != null && type == 2){
            //最后一个已完成任务
            sql.append(" and task.end_time_ is not null and task.delete_reason_='completed' and not exists(select 1 from act_hi_taskinst task2 left join act_bpm_task_leavel leavel2 on leavel2.task_id=task2.id_ where task2.end_time_>task.end_time_ and leavel2.top_proc_inst_id=leavel.top_proc_inst_id and task2.end_time_ is not null and task2.delete_reason_='completed')");
        }else{
            //最后一个当前任务
            sql.append(" and (task.end_time_ is null and not exists(select 1 from act_hi_taskinst task1 left join act_bpm_task_leavel leavel1 on leavel1.task_id=task1.id_ where task1.start_time_>task.start_time_ and leavel1.top_proc_inst_id=leavel.top_proc_inst_id and task1.end_time_ is null)");
            //最后一个已完成任务
            sql.append(" or task.end_time_ is not null and task.delete_reason_='completed' and not exists(select 1 from act_hi_taskinst task2 left join act_bpm_task_leavel leavel2 on leavel2.task_id=task2.id_ where task2.end_time_>task.end_time_ and leavel2.top_proc_inst_id=leavel.top_proc_inst_id  and task2.end_time_ is not null and task2.delete_reason_='completed'))");
        }
       // sql.append(" group by task.id_ ");
        sql.append(" order by task.start_time_,task.end_time_");
		StringBuffer sqlBuff = new StringBuffer();
        sqlBuff.append("select * from ( ").append(sql).append(" ) t where t.row_flg = 1 ");
        //return bpmHiTaskDAO_HI_RO.findList(sql.toString() , paramsMap);
        return bpmHiTaskDAO_HI_RO.findList(sqlBuff , paramsMap);
    }

    /**
     * 根据流程实例ID获取表单MenuCode
     * @author laoqunzhao 2018-04-27
     * @param processInstanceId 流程实例ID
     * @return MenuCode
     */
    @Override
    public String getMenuCode(String processInstanceId){
        if(StringUtils.isBlank(processInstanceId)) {
            return null;
        }
        List<HistoricDetail> details = findHistoricDetails(processInstanceId, "menucode", true);
        if(details != null && !details.isEmpty()){
            for(int i=details.size()-1; i>=0; i--){
                HistoricFormProperty detail = (HistoricFormProperty) details.get(i);
                if(StringUtils.isNotBlank(detail.getPropertyValue())){
                    return detail.getPropertyValue();
                }
            }
        }
        return null;
    }
    
    /**
     * 根据任务ID获取任务表单详情
     * @author laoqunzhao 2018-04-27
     * @param taskIds 任务ID集
     * @return 任务表单字段集合List<HistoricDetail>
     */
    @Override
    public List<HistoricDetail> findHistoricDetails(List<String> taskIds){
        if(taskIds == null || taskIds.isEmpty()) {
            return null;
        }
        String taskIdStr = "'" + StringUtils.join(taskIds, "','") + "'";
        StringBuffer sql = new StringBuffer("SELECT D.* FROM ACT_HI_DETAIL D WHERE D.TYPE_='FormProperty' AND D.TASK_ID_ IN ("+taskIdStr+")");
        NativeHistoricDetailQuery  historicQuery = historyService.createNativeHistoricDetailQuery().sql(sql.toString());
        return historicQuery.list();
    }

    /**
     * 查询流程表单字段
     * @author laoqunzhao 2018-04-27
     * @param processInstanceId 任务ID集
     * @param name 字段名
     * @param draft 只查询提交表单
     * @return 流程表单字段集合List<HistoricDetail>
     */
    @Override
    public List<HistoricDetail> findHistoricDetails(String processInstanceId, String name, boolean draft){
        if(StringUtils.isBlank(processInstanceId)) {
            return null;
        }
        StringBuffer sql = new StringBuffer("SELECT D.* FROM ACT_HI_DETAIL D WHERE D.TYPE_='FormProperty' AND D.PROC_INST_ID_ = '" + processInstanceId + "'");
        if(StringUtils.isNotBlank(name)){
            sql.append(" AND D.NAME_ ='" + name +"'");
        }
        if(draft){
            sql.append("AND EXISTS(SELECT 1 FROM ACT_HI_DETAIL D1 WHERE D1.TASK_ID_=D.TASK_ID_ AND D1.NAME_='option' AND D1.TEXT_='DF')");
        }
        sql.append(" ORDER BY TIME_");
        Map<String, Object> parmasMap = new HashMap<>();
        NativeHistoricDetailQuery  historicQuery = historyService.createNativeHistoricDetailQuery().sql(sql.toString());
        return historicQuery.list();
    }
    
    /**
     * 将流程中当前环节及上一环节的信息写入JSONArray结果 集
     * @author laoqunzhao 2018-04-27
     * @param array JSONArray结果 集
     * @param jsonKeyField 结果集中存放流程实例的key
     */
    @Override
    public void appendCurPrevTask(JSONArray array, String jsonKeyField) {
        if(array == null || array.isEmpty() || StringUtils.isBlank(jsonKeyField)) {
            return;
        }
        //获取结果集中的所有流程实例ID
        List<String> processInstanceIds = ConvertUtil.getJSONArrayField(array, jsonKeyField);
        if(processInstanceIds == null || processInstanceIds.isEmpty()) {
            return;
        }
        List<ActBpmHiTaskEntity_HI_RO> historicTaskList = findHistoricTasks(processInstanceIds, null);
        if(historicTaskList != null && !historicTaskList.isEmpty()) {
            Map<String,Map<String,String>> taskMap = new HashMap<String,Map<String,String>>();
            for(ActBpmHiTaskEntity_HI_RO historicTask:historicTaskList) {
                String processInstanceId = historicTask.getProcessInstanceId();
                if(taskMap.get(processInstanceId) == null) {
                    taskMap.put(processInstanceId, new HashMap<String,String>());
                }
                //上一环节
                if(historicTask.getEndTime() != null) {
                	taskMap.get(processInstanceId).put("prev_taskId", historicTask.getTaskId());
                	taskMap.get(processInstanceId).put("prev_time", ConvertUtil.dateToString(historicTask.getEndTime()));
                    taskMap.get(processInstanceId).put("prev_taskName", historicTask.getTaskName());
                    taskMap.get(processInstanceId).put("prev_assignee", historicTask.getAssignee());
                }else {
                    //当前环节
                	taskMap.get(processInstanceId).put("cur_taskId", historicTask.getTaskId());
                    taskMap.get(processInstanceId).put("cur_time", ConvertUtil.dateToString(historicTask.getStartTime()));
                    taskMap.get(processInstanceId).put("cur_taskName", historicTask.getTaskName());
                    taskMap.get(processInstanceId).put("cur_assignee", historicTask.getAssignee());
                }
            }            
            //将对象转换为内置JSONArray的一部分
            ConvertUtil.appendToJSONArray(array, taskMap, null, jsonKeyField, null, true);
        } 
    }

    /**
     * 历史任务表单详情的信息写入JSONArray结果 集
     * @author laoqunzhao 2018-04-27
     * @param array JSONArray结果 集
     * @param jsonKeyField 结果集中存放任务ID的key
     */
    @Override
    public void appendHistoricDetail(JSONArray array, String jsonKeyField) {
        if(array == null || array.isEmpty() || StringUtils.isBlank(jsonKeyField)) {
            return;
        }
        //获取结果集中的所有任务ID
        List<String> taskIds = ConvertUtil.getJSONArrayField(array, jsonKeyField);
        if(taskIds == null || taskIds.isEmpty()) {
            return;
        }
        List<HistoricDetail> detailList = findHistoricDetails(taskIds);
        if(detailList == null || detailList.isEmpty()) {
            return;
        } 
        Map<String, List<HistoricDetail>> details = new HashMap<String, List<HistoricDetail>>();
        for(HistoricDetail detail: detailList) {
            if(details.get(detail.getTaskId())==null) {
                details.put(detail.getTaskId(), new ArrayList<HistoricDetail>());
            }
            details.get(detail.getTaskId()).add(detail);
        }
        String prev = "his_detail_";//添加前缀避免与其他属性冲突
        for(int i=0; i<array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            if(object.containsKey(jsonKeyField) && StringUtils.isNotBlank(object.getString(jsonKeyField))
                    && details.containsKey(object.getString(jsonKeyField))) {
                List<HistoricDetail> detailTemps = details.get(object.getString(jsonKeyField));
                for(HistoricDetail detailTemp:detailTemps) {
                    HistoricFormProperty field = (HistoricFormProperty) detailTemp;
                    if(WorkflowConstant.PROP_OPTION.equals(field.getPropertyId())) {
                    	String value = field.getPropertyValue();
                    	if(StringUtils.isNotBlank(value)) {
                    		switch(value) {
                    		case WorkflowConstant.OPERATE_REJECT: value = "驳回";break;
                    		case WorkflowConstant.OPERATE_RETRIAL: value = "驳回重审";break;
                    		case WorkflowConstant.OPERATE_REVOKE: value = "撤回";break;
                    		case WorkflowConstant.OPERATE_STOP: value = "终止";break;
                    		case WorkflowConstant.OPERATE_SUBMIT: value = "同意";break;
                    		case WorkflowConstant.OPERATE_SUBMIT_DRAFT: value = "提交审批";break;
                    		case WorkflowConstant.OPERATE_SUBMIT_AUTOJUMP: value = "自动跳过";break;
                    		case WorkflowConstant.OPERATE_SUBMIT_ADD_SUBTASK: value = "增加助审";break;
                    		default:break;
                    		}
                    	}
                    	object.put(prev + field.getPropertyId(), value);
                    }else {
                    	object.put(prev + field.getPropertyId(), field.getPropertyValue());
                    }
                }
            }
        }
    }

    /**
     * 历史任务统计
     * @param queryParamJSON
     * {
     *  startDate: 开始时间,
     *  endDate: 结束时间
     * }
     * @return ActStatisticEntity_RO
     */
    @Override
    public ActStatisticEntity_RO taskStatistic(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(ActStatisticTaskEntity_RO.QUERY_TASK_SQL);
        String startDate = StringUtils.trimToNull(queryParamJSON.getString("startDate"));
        if(startDate != null){
            sql.append(" and task.start_time_ >= :startDate");
            paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
        }
        String endDate = StringUtils.trimToNull(queryParamJSON.getString("endDate"));
        if(endDate != null){
            sql.append(" and task.start_time_ < :endDate:");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(endDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramsMap.put("endDate", c.getTime());
        }
        long amount = statisticTaskDAO_HI.count("select count(*) " + sql.substring(sql.toString().toLowerCase().indexOf(" from ")), paramsMap);
        sql.append(" group by proc.key_,task.task_def_key_");
        long count = statisticTaskDAO_HI.count("select count(*) " + sql.substring(sql.toString().toLowerCase().indexOf(" from ")), paramsMap);
        sql.append(" order by count desc");
        if(StringUtils.trimToNull(queryParamJSON.getString("count")) != null) {
        	sql.append(" limit " + queryParamJSON.getString("count"));
        }
        List<ActStatisticTaskEntity_RO> tasks = statisticTaskDAO_HI.findList(sql, paramsMap);
        if(tasks != null && !tasks.isEmpty()) {
        	for(ActStatisticTaskEntity_RO task: tasks) {
    			BigDecimal amount1 = new BigDecimal(amount);
    			BigDecimal ratio = new BigDecimal(task.getCount()*100).divide(amount1, 2, RoundingMode.HALF_UP);
    			task.setRatio(ratio);
    		}
        }
        ActStatisticEntity_RO instance = new ActStatisticEntity_RO();
        instance.setAmount(amount);
        instance.setCount(count);
        instance.setStatistics(tasks);
        return instance;
    }
    
    /**
	 * 审批表单信息查询
	 * @author laoqunzhao 2018-08-03
     * @param queryParamJSON
     * {
     * userId 办理人流程用户ID
     * name 表单属性名称
     * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return Pagination<String>
	 */
    @Override
    public Pagination<String> findHistoricTextDetail(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select distinct text_ as description from act_hi_detail detail")
				.append(" inner join act_hi_taskinst task on detail.task_id_=task.id_")
				.append(" where text_ is not null and ltrim(text_) is not null ");
		String userId = StringUtils.trimToNull(queryParamJSON.getString("userId"));
		if(userId != null) {
			sql.append(" and upper(task.assignee_) = :userId");
			paramsMap.put("userId", userId.toUpperCase());
		}
		String name = StringUtils.trimToNull(queryParamJSON.getString("name"));
		if(name != null) {
			sql.append(" and upper(detail.name_) = :name");
			paramsMap.put("name", name.toUpperCase());
		}
		sql.append(" and detail.type_ = :type");
		paramsMap.put("type", "FormProperty");
		//sql.append(" order by detail.time_");
		Pagination<ActBpmHiTaskEntity_HI_RO> pagination = bpmHiTaskDAO_HI_RO.findPagination(sql.toString(), paramsMap, pageIndex, pageRows);
		Pagination<String> pagination_ = new Pagination<String>();
		pagination_.setCount(pagination.getCount());
		pagination_.setCurIndex(pagination.getCurIndex());
		pagination_.setNextIndex(pagination.getNextIndex());
		pagination_.setPagesCount(pagination.getPagesCount());
		pagination_.setPageSize(pagination.getPageSize());
		pagination_.setPreIndex(pagination.getPreIndex());
		if(pagination.getData() != null && !pagination.getData().isEmpty()) {
			List<String> items = new ArrayList<String>();
			for(ActBpmHiTaskEntity_HI_RO data: pagination.getData()) {
				items.add(data.getDescription());
			}
			pagination_.setData(items);
		}
		return pagination_;
		
    }

    /**
     * 查询历史变量
     * @param processInstanceId 流程实例ID
     * @return
     */
    @Override
    public Map<String, Object> getHistoricVariables(String processInstanceId){
        if(StringUtils.isBlank(processInstanceId)){
            return null;
        }
        Map<String, Object> variables = new HashMap<>();
        List<HistoricVariableInstance> historicVariableInstances =  historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if(historicVariableInstances != null && !historicVariableInstances.isEmpty()) {
            for(HistoricVariableInstance historicVariableInstance: historicVariableInstances) {
                variables.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
            }
        }
        return variables;
    }

    /**
     * 查询驳回重审状态
     * @param processInstanceId 流程实例ID
     */
    public boolean getRetrialStatus(String processInstanceId){
        if(StringUtils.isBlank(processInstanceId)){
            return false;
        }
        List<String> processInstanceIds = new ArrayList<String>();
        processInstanceIds.add(processInstanceId);
        //查询最后一次任务
        List<ActBpmHiTaskEntity_HI_RO> historicTasks = findHistoricTasks(processInstanceIds, 2);
        if(historicTasks != null && !historicTasks.isEmpty()) {
            List<String> taskIds = new ArrayList<String>();
            for(ActBpmHiTaskEntity_HI_RO historicTask: historicTasks) {
                if(historicTask.getEndTime() != null) {
                    taskIds.add(historicTask.getTaskId());
                }
            }
            List<HistoricDetail> details = findHistoricDetails(taskIds);
            if(details != null && !details.isEmpty()) {
                for(HistoricDetail detail: details) {
                    if(detail instanceof HistoricFormProperty) {
                        HistoricFormProperty field = (HistoricFormProperty) detail;
                        //驳回重审
                        if(WorkflowConstant.PROP_OPTION.equals(field.getPropertyId())
                                && WorkflowConstant.OPERATE_RETRIAL.equals(field.getPropertyValue())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 可驳回/重审节点查询
     * @param task 任务
     * @param type 1.驳回  2.重审
     * @return
     */
    @Override
    public JSONArray getRetrialTaskNodes(Task task, int type){
        //获取申请单
        String processInstanceId = bpmProcessServer.getTopProcessInstanceIdByTaskId(task.getId());
        if(StringUtils.isBlank(processInstanceId)){
            return null;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if(processInstance == null){
            return  null;
        }
        BpmnModel bpmModel = bpmProcessServer.getBpmnModel(processInstance.getProcessDefinitionId());
        if(bpmModel == null || bpmModel.getMainProcess() == null){
            return null;
        }
        String taskDefinitionId = task.getTaskDefinitionKey();
        //子任务获取顶层任务
        if(StringUtils.isNotBlank(task.getParentTaskId())) {
            Task parentTask = bpmTaskServer.get(task.getParentTaskId());
            while(parentTask!= null && StringUtils.isNotBlank(parentTask.getParentTaskId())) {
                parentTask = bpmTaskServer.get(parentTask.getParentTaskId());
            }
            taskDefinitionId = parentTask == null? null: parentTask.getTaskDefinitionKey();
        }
        List<HistoricTaskInstance> hisTasks = findHistoricTasks(processInstanceId);
        if(hisTasks == null || hisTasks.isEmpty()){
            return null;
        }
        if(type == 2) {
            //重审
            return getRetrialTaskNodes(task, taskDefinitionId, hisTasks, bpmModel);
        }else {
            //驳回
            return getRejectTaskNodes(task, taskDefinitionId, hisTasks, bpmModel);
        }
    }

    /**
     * 查询驳回节点
     * @param task 任务
     * @param taskDefinitionId 节点ID
     * @param hisTasks 审批记录
     * @param bpmnModel 流程
     * @return JSONArray
     */
    private JSONArray getRejectTaskNodes(Task task, String taskDefinitionId, List<HistoricTaskInstance> hisTasks, BpmnModel bpmnModel){
        JSONArray taskNodesJSON = new JSONArray();
        Process mainProcess = bpmnModel.getMainProcess();
        boolean isMulti = isMultiInstance(mainProcess, taskDefinitionId);
        List<String> bpmUserIds = bpmTaskServer.getTaskBpmUserIds(task.getId());//获取当前任务的办理人，与历史办理人进行匹配
        List<String> hiBpmUserIds = new ArrayList<>();//记录历史办理人
        Map<String, ActIdUserEntity_RO> hiBpmUsersMap = new HashMap<>();//记录历史办理人
        Map<String, String> hiTaskUsersMap = new HashMap<>();//记录历史任务与办理人关系
        Map<String, List<HistoricTaskInstance>> mulInsTasksMap = new HashMap<>();//记录多实例任务
        int firstIndex = -1;//记录当前节点第一次出现的位置
        List<String[]> prevTaskDefinitionIds = new ArrayList<>();//记录当前节点第一次出现前的节点（都可以驳回）
        List<String[]> fllowTaskDefinitionIds = new ArrayList<>();//记录当前节点第一次出现后的节点（不一定可以驳回）
        //过滤子任务
        for(int i=hisTasks.size()-1; i>=0; i--) {
            HistoricTaskInstance hisTask = hisTasks.get(i);
            if (StringUtils.isNotBlank(hisTask.getParentTaskId()) || StringUtils.isBlank(hisTask.getTaskDefinitionKey())) {
                hisTasks.remove(i);
            }else if(StringUtils.isNotBlank(hisTask.getAssignee())){
                if(!hiBpmUserIds.contains(hisTask.getAssignee())){
                    hiBpmUserIds.add(hisTask.getAssignee());
                }
                if(!hiTaskUsersMap.containsKey(hisTask.getTaskDefinitionKey())){
                    hiTaskUsersMap.put(hisTask.getTaskDefinitionKey(), hisTask.getAssignee());
                }
            }
        }
        //查询历史任务办理人
        List<ActIdUserEntity_RO> bpmUsers = bpmUserServer.findUsersByBpmIds(hiBpmUserIds);
        if(bpmUsers != null && !bpmUsers.isEmpty()){
            for(ActIdUserEntity_RO bpmUser : bpmUsers){
                hiBpmUsersMap.put(bpmUser.getId(), bpmUser);
            }
        }

        for(int i=0; i<hisTasks.size(); i++){
            HistoricTaskInstance hisTask = hisTasks.get(i);
            String curTaskDefinitionId = hisTask.getTaskDefinitionKey();
            //记录最后一次出现位置
            if(taskDefinitionId.equals(curTaskDefinitionId)){
                if(!isMulti){
                    firstIndex = firstIndex == -1? i: firstIndex;
                }else {
                    prevTaskDefinitionIds.add(new String[]{curTaskDefinitionId, hisTask.getName()});
                    if(bpmUserIds != null && (bpmUserIds.contains(hisTask.getOwner()) || bpmUserIds.contains(hisTask.getAssignee()))) {
                        firstIndex = firstIndex == -1? i: firstIndex;
                    }
                }
            }else{
                //记录节点前后任务节点
                if(firstIndex == -1){
                    prevTaskDefinitionIds.add(new String[]{curTaskDefinitionId, hisTask.getName()});
                }else{
                    fllowTaskDefinitionIds.add(new String[]{curTaskDefinitionId, hisTask.getName()});
                }
            }
            //记录多实例任务
            if(isMulti && taskDefinitionId.equals(curTaskDefinitionId) || mulInsTasksMap.containsKey(curTaskDefinitionId)
                    || isMultiInstance(mainProcess, curTaskDefinitionId)){
                List<HistoricTaskInstance> mulInsTasks = mulInsTasksMap.get(curTaskDefinitionId);
                if(mulInsTasks == null){
                    mulInsTasks = new ArrayList<>();
                }
                mulInsTasks.add(hisTask);
                mulInsTasksMap.put(curTaskDefinitionId, mulInsTasks);
            }

        }
        //取驳回节点
        List<String> addedTaskDefinitionIds = new ArrayList<>();
        for(String[] prevTaskDefinitionId : prevTaskDefinitionIds){
            if(addedTaskDefinitionIds.contains(prevTaskDefinitionId[0])){
                continue;
            }
            if(!mulInsTasksMap.containsKey(prevTaskDefinitionId[0])){
                //普通任务
                JSONObject taskNodeJSON = new JSONObject();
                taskNodeJSON.put("taskDefinitionId", prevTaskDefinitionId[0]);
                taskNodeJSON.put("taskName", prevTaskDefinitionId[1]);
                if(hiTaskUsersMap.containsKey(prevTaskDefinitionId[0])){
                    String bpmUserId = hiTaskUsersMap.get(prevTaskDefinitionId[0]);
                    if(StringUtils.isNotBlank(bpmUserId)){
                        ActIdUserEntity_RO bpmUser = hiBpmUsersMap.get(bpmUserId);
                        if(bpmUser != null && StringUtils.isNotBlank(bpmUser.getUserFullName())){
                            taskNodeJSON.put("taskName", prevTaskDefinitionId[1] + "(" + bpmUser.getUserFullName() + ")") ;
                        }else{
                            taskNodeJSON.put("taskName", prevTaskDefinitionId[1] + "(" + bpmUserId + ")") ;
                        }
                    }
                }
                taskNodesJSON.add(taskNodeJSON);
                addedTaskDefinitionIds.add(prevTaskDefinitionId[0]);
            }else{
                //多实例任务
                List<String> addedMulTaskDefinitionIds = new ArrayList<>();
                List<HistoricTaskInstance> mulInsTasks = mulInsTasksMap.get(prevTaskDefinitionId[0]);
                for(HistoricTaskInstance mulInsTask : mulInsTasks){
                    if(addedMulTaskDefinitionIds.contains(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee())){
                        continue;
                    }
                    //当前节点是多实例任务节点,只有第一次出现前才可驳回
                    if(isMulti && taskDefinitionId.equals(prevTaskDefinitionId[0])&& bpmUserIds != null && (bpmUserIds.contains(mulInsTask.getOwner()) || bpmUserIds.contains(mulInsTask.getAssignee()))) {
                        break;
                    }
                    JSONObject taskNodeJSON = new JSONObject();
                    taskNodeJSON.put("taskDefinitionId", mulInsTask.getTaskDefinitionKey());
                    taskNodeJSON.put("taskName", mulInsTask.getName());
                    taskNodeJSON.put("taskId", mulInsTask.getId());
                    String bpmUserId = mulInsTask.getAssignee();
                    if(StringUtils.isNotBlank(bpmUserId)){
                        ActIdUserEntity_RO bpmUser = hiBpmUsersMap.get(bpmUserId);
                        if(bpmUser != null && StringUtils.isNotBlank(bpmUser.getUserFullName())){
                            taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUser.getUserFullName() + ")") ;
                        }else{
                            taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUserId + ")") ;
                        }
                    }
                    taskNodesJSON.add(taskNodeJSON);
                    addedMulTaskDefinitionIds.add(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee());
                }
                addedTaskDefinitionIds.add(prevTaskDefinitionId[0]);
            }
        }

        if(!fllowTaskDefinitionIds.isEmpty()){
            for(String[] fllowTaskDefinitionId : fllowTaskDefinitionIds) {
                if (taskDefinitionId.equals(fllowTaskDefinitionId[0]) || addedTaskDefinitionIds.contains(fllowTaskDefinitionId[0])) {
                    continue;
                }
                //判断能否驳回
                boolean beRejact = false;
                List<FlowElement> flowElements = bpmModelServer.getSortedFollowElements(bpmnModel, fllowTaskDefinitionId[0]);
                if(flowElements != null && !flowElements.isEmpty()){
                    for(FlowElement flowElement : flowElements){
                        if(taskDefinitionId.equals(flowElement.getId())){
                            beRejact = true;
                            break;
                        }
                    }
                }
                if(!beRejact){
                    continue;
                }
                if (!mulInsTasksMap.containsKey(fllowTaskDefinitionId[0])) {
                    //普通任务
                    JSONObject taskNodeJSON = new JSONObject();
                    taskNodeJSON.put("taskDefinitionId", fllowTaskDefinitionId[0]);
                    taskNodeJSON.put("taskName", fllowTaskDefinitionId[1]);
                    if(hiTaskUsersMap.containsKey(fllowTaskDefinitionId[0])){
                        String bpmUserId = hiTaskUsersMap.get(fllowTaskDefinitionId[0]);
                        if(StringUtils.isNotBlank(bpmUserId)){
                            ActIdUserEntity_RO bpmUser = hiBpmUsersMap.get(bpmUserId);
                            if(bpmUser != null && StringUtils.isNotBlank(bpmUser.getUserFullName())){
                                taskNodeJSON.put("taskName", fllowTaskDefinitionId[1] + "(" + bpmUser.getUserFullName() + ")") ;
                            }else{
                                taskNodeJSON.put("taskName", fllowTaskDefinitionId[1] + "(" + bpmUserId + ")") ;
                            }
                        }
                    }
                    taskNodesJSON.add(taskNodeJSON);
                    addedTaskDefinitionIds.add(fllowTaskDefinitionId[0]);
                } else {
                    //多实例任务
                    List<String> addedMulTaskDefinitionIds = new ArrayList<>();
                    List<HistoricTaskInstance> mulInsTasks = mulInsTasksMap.get(fllowTaskDefinitionId[0]);
                    for (HistoricTaskInstance mulInsTask : mulInsTasks) {
                        if (addedMulTaskDefinitionIds.contains(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee())) {
                            continue;
                        }
                        JSONObject taskNodeJSON = new JSONObject();
                        taskNodeJSON.put("taskDefinitionId", mulInsTask.getTaskDefinitionKey());
                        taskNodeJSON.put("taskName", mulInsTask.getName());
                        String bpmUserId = mulInsTask.getAssignee();
                        if(StringUtils.isNotBlank(bpmUserId)){
                            ActIdUserEntity_RO bpmUser = hiBpmUsersMap.get(bpmUserId);
                            if(bpmUser != null && StringUtils.isNotBlank(bpmUser.getUserFullName())){
                                taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUser.getUserFullName() + ")") ;
                            }else{
                                taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUserId + ")") ;
                            }
                        }
                        taskNodesJSON.add(taskNodeJSON);
                        addedMulTaskDefinitionIds.add(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee());
                    }
                    addedTaskDefinitionIds.add(fllowTaskDefinitionId[0]);
                }
            }
        }
        return taskNodesJSON;
    }

    /**
     * 查询重审节点
     * @param task 任务
     * @param taskDefinitionId 节点ID
     * @param hisTasks 审批记录
     * @param bpmnModel 流程
     * @return JSONArray
     */
    private JSONArray getRetrialTaskNodes(Task task, String taskDefinitionId, List<HistoricTaskInstance> hisTasks, BpmnModel bpmnModel){
        JSONArray taskNodesJSON = new JSONArray();
        Process mainProcess = bpmnModel.getMainProcess();
        boolean isMulti = isMultiInstance(mainProcess, taskDefinitionId);
        List<String> bpmUserIds = bpmTaskServer.getTaskBpmUserIds(task.getId());//获取当前任务的办理人，与历史办理人进行匹配
        List<String> hiBpmUserIds = new ArrayList<>();//记录历史办理人
        Map<String, ActIdUserEntity_RO> hiBpmUsersMap = new HashMap<>();//记录历史办理人
        Map<String, String> hiTaskUsersMap = new HashMap<>();//记录历史任务与办理人关系
        Map<String, List<HistoricTaskInstance>> mulInsTasksMap = new HashMap<>();//记录多实例任务
        //过滤子任务
        for(int i=hisTasks.size()-1; i>=0; i--) {
            HistoricTaskInstance hisTask = hisTasks.get(i);
            if (StringUtils.isNotBlank(hisTask.getParentTaskId()) || StringUtils.isBlank(hisTask.getTaskDefinitionKey())) {
                hisTasks.remove(i);
            }else if(StringUtils.isNotBlank(hisTask.getAssignee())){
                if(!hiBpmUserIds.contains(hisTask.getAssignee())){
                    hiBpmUserIds.add(hisTask.getAssignee());
                }
                if(!hiTaskUsersMap.containsKey(hisTask.getTaskDefinitionKey())){
                    hiTaskUsersMap.put(hisTask.getTaskDefinitionKey(), hisTask.getAssignee());
                }
            }
        }
        //查询历史任务办理人
        List<ActIdUserEntity_RO> bpmUsers = bpmUserServer.findUsersByBpmIds(hiBpmUserIds);
        if(bpmUsers != null && !bpmUsers.isEmpty()){
            for(ActIdUserEntity_RO bpmUser : bpmUsers){
                hiBpmUsersMap.put(bpmUser.getId(), bpmUser);
            }
        }

        for(int i=0; i<hisTasks.size(); i++){
            HistoricTaskInstance hisTask = hisTasks.get(i);
            String curTaskDefinitionId = hisTask.getTaskDefinitionKey();
            //记录多实例任务
            if(isMulti && taskDefinitionId.equals(curTaskDefinitionId) || isMultiInstance(mainProcess, curTaskDefinitionId)){
                List<HistoricTaskInstance> mulInsTasks = mulInsTasksMap.get(curTaskDefinitionId);
                if(mulInsTasks == null){
                    mulInsTasks = new ArrayList<>();
                }
                boolean existed = false;
                for(HistoricTaskInstance mulInsTask : mulInsTasks){
                    if(StringUtils.equals(mulInsTask.getAssignee(), hisTask.getAssignee())){
                        existed = true;
                        break;
                    }
                }
                if(!existed){
                    mulInsTasks.add(hisTask);
                    mulInsTasksMap.put(curTaskDefinitionId, mulInsTasks);
                }
            }
        }
        //记录当前节点的往下流转节点
        List<String> flowElementIds = new ArrayList<>();
        List<FlowElement> flowElements = bpmModelServer.getSortedFollowElements(bpmnModel, taskDefinitionId);
        if(flowElements != null && !flowElements.isEmpty()){
            for(FlowElement flowElement : flowElements){
                flowElementIds.add(flowElement.getId());
            }
        }
        //取重审节点
        List<String> addedTaskDefinitionIds = new ArrayList<>();
        for(int i=0; i<hisTasks.size(); i++){
            HistoricTaskInstance hisTask = hisTasks.get(i);
            String curTaskDefinitionId = hisTask.getTaskDefinitionKey();
            if(!(isMulti && taskDefinitionId.equals(curTaskDefinitionId) || flowElementIds.contains(curTaskDefinitionId))
                    || addedTaskDefinitionIds.contains(curTaskDefinitionId)){
                continue;//不可重审或已加入重审
            }
            if (!mulInsTasksMap.containsKey(curTaskDefinitionId)) {
                if(!StringUtils.equals(taskDefinitionId, curTaskDefinitionId)) {
                    //普通任务
                    JSONObject taskNodeJSON = new JSONObject();
                    taskNodeJSON.put("taskDefinitionId", curTaskDefinitionId);
                    taskNodeJSON.put("taskName", hisTask.getName());
                    if(hiTaskUsersMap.containsKey(curTaskDefinitionId)){
                        String bpmUserId = hiTaskUsersMap.get(curTaskDefinitionId);
                        if(StringUtils.isNotBlank(bpmUserId)){
                            ActIdUserEntity_RO bpmUser = hiBpmUsersMap.get(bpmUserId);
                            if(bpmUser != null && StringUtils.isNotBlank(bpmUser.getUserFullName())){
                                taskNodeJSON.put("taskName", hisTask.getName() + "(" + bpmUser.getUserFullName() + ")") ;
                            }else{
                                taskNodeJSON.put("taskName", hisTask.getName() + "(" + bpmUserId + ")") ;
                            }
                        }
                    }
                    taskNodesJSON.add(taskNodeJSON);
                    addedTaskDefinitionIds.add(curTaskDefinitionId);
                }
            } else {
                //多实例任务
                List<HistoricTaskInstance> mulInsTasks = mulInsTasksMap.get(curTaskDefinitionId);
                if(isMulti && taskDefinitionId.equals(curTaskDefinitionId)){
                    //当前节点是多任务节点,只取当前人后的节点
                    boolean beRetrial = false;
                    List<String> addedMulTaskDefinitionIds_ = new ArrayList<>();
                    JSONArray mulTaskNodesJSON = new JSONArray();
                    for (HistoricTaskInstance mulInsTask : mulInsTasks) {
                        if(bpmUserIds != null && (bpmUserIds.contains(mulInsTask.getOwner()) || bpmUserIds.contains(mulInsTask.getAssignee()))) {
                            beRetrial = true;
                            mulTaskNodesJSON = new JSONArray();
                            addedMulTaskDefinitionIds_ = new ArrayList<>();
                            continue;
                        }
                        if(!beRetrial || addedMulTaskDefinitionIds_.contains(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee())) {
                            continue;
                        }
                        JSONObject taskNodeJSON = new JSONObject();
                        taskNodeJSON.put("taskDefinitionId", mulInsTask.getTaskDefinitionKey());
                        taskNodeJSON.put("taskName", mulInsTask.getName());
                        taskNodeJSON.put("taskId", mulInsTask.getId());
                        String bpmUserId = mulInsTask.getAssignee();
                        if(StringUtils.isNotBlank(bpmUserId)){
                            ActIdUserEntity_RO bpmUser = hiBpmUsersMap.get(bpmUserId);
                            if(bpmUser != null && StringUtils.isNotBlank(bpmUser.getUserFullName())){
                                taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUser.getUserFullName() + ")") ;
                            }else{
                                taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUserId + ")") ;
                            }
                        }
                        mulTaskNodesJSON.add(taskNodeJSON);
                        addedMulTaskDefinitionIds_.add(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee());
                    }
                    for(int j=0; j<mulTaskNodesJSON.size(); j++){
                        JSONObject mulTaskNodeJSON = mulTaskNodesJSON.getJSONObject(j);
                        taskNodesJSON.add(mulTaskNodeJSON);
                    }
                }else {
                    List<String> addedMulTaskDefinitionIds = new ArrayList<>();
                    for (HistoricTaskInstance mulInsTask : mulInsTasks) {
                        if (addedMulTaskDefinitionIds.contains(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee())) {
                            continue;
                        }
                        JSONObject taskNodeJSON = new JSONObject();
                        taskNodeJSON.put("taskDefinitionId", mulInsTask.getTaskDefinitionKey());
                        taskNodeJSON.put("taskName", mulInsTask.getName());
                        taskNodeJSON.put("taskId", mulInsTask.getId());
                        String bpmUserId = mulInsTask.getAssignee();
                        if(StringUtils.isNotBlank(bpmUserId)){
                            ActIdUserEntity_RO bpmUser = hiBpmUsersMap.get(bpmUserId);
                            if(bpmUser != null && StringUtils.isNotBlank(bpmUser.getUserFullName())){
                                taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUser.getUserFullName() + ")") ;
                            }else{
                                taskNodeJSON.put("taskName", mulInsTask.getName() + "(" + bpmUserId + ")") ;
                            }
                        }
                        taskNodesJSON.add(taskNodeJSON);
                        addedMulTaskDefinitionIds.add(mulInsTask.getTaskDefinitionKey() + "::" + mulInsTask.getAssignee());
                    }
                }
                addedTaskDefinitionIds.add(curTaskDefinitionId);
            }
        }
        return taskNodesJSON;
    }

    /**
     * 判断是否多实例任务
     * @param mainProcess
     * @param activityId
     * @return
     */
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
    

}
