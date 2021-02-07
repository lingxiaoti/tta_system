package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticProcessEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticTaskEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmStatistic;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Component("actBpmStatisticServer")
public class ActBpmStatisticServer implements IActBpmStatistic {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmStatisticServer.class);
	
    
    @Autowired
	private BaseViewObject<ActStatisticTaskEntity_RO> statisticTaskDAO_HI;
	
	@Autowired
	private BaseViewObject<ActStatisticProcessEntity_RO> statisticProcessDAO_HI;
	    
    
    
    
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
    
    @Override
	public Pagination<ActStatisticProcessEntity_RO> findProcesses(JSONObject queryParamJSON, ActIdUserEntity_RO user, Integer pageIndex, Integer pageRows){
		Pagination<ActStatisticProcessEntity_RO> pagination = new Pagination<>();
    	//任务状态
  		String status = StringUtils.trimToNull(queryParamJSON.getString("status"));
  		//查询所有状态
  		if(status == null) {
			List<ActStatisticProcessEntity_RO> list = new ArrayList<>();
  			//已办结
			pagination = findProcessesOfResolved(queryParamJSON, user, -1, -1);
			if(pagination.getData() != null && !pagination.getData().isEmpty()){
				list.addAll(pagination.getData());
			}
  			//办理中
			queryParamJSON.put("status", WorkflowConstant.TASK_STATUS_RESOLVING);
			pagination = findProcessesOfResolving(queryParamJSON, user, -1, -1);
			if(pagination.getData() != null && !pagination.getData().isEmpty()){
				list.addAll(pagination.getData());
			}
			//未接收
			queryParamJSON.put("status", WorkflowConstant.TASK_STATUS_PENDING);
			pagination = findProcessesOfResolving(queryParamJSON, user, -1, -1);
			if(pagination.getData() != null && !pagination.getData().isEmpty()){
				list.addAll(pagination.getData());
			}
			//计算分页
			pagination.setCount(list.size());
			pagination.setCurIndex(pageIndex);
			if(pagination.getCount() > (pageIndex - 1) * pageRows){
				list = list.subList((pageIndex - 1) * pageRows, list.size());
				if(list.size() > pageRows){
					list = list.subList(0, pageRows);
				}
			}else{
				list = new ArrayList<>();
			}
			pagination.setData(list);
  		}else if(WorkflowConstant.TASK_STATUS_RESOLVED.equals(status)) {
  			pagination = findProcessesOfResolved(queryParamJSON, user, pageIndex, pageRows);
		}else if(WorkflowConstant.TASK_STATUS_RESOLVING.equals(status) || WorkflowConstant.TASK_STATUS_PENDING.equals(status)){
			pagination = findProcessesOfResolving(queryParamJSON, user, pageIndex, pageRows);  		   
		}
		return pagination;
    }
	
	
	private Pagination<ActStatisticProcessEntity_RO> findProcessesOfResolved(JSONObject queryParamJSON, ActIdUserEntity_RO user,Integer pageIndex, Integer pageRows){
		StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
  		sql.append(ActStatisticProcessEntity_RO.QUERY_ALL_SQL);
  		String processDefinitionKey = StringUtils.trimToNull(queryParamJSON.getString("processDefinitionKey"));
  		if(processDefinitionKey != null) {
  			sql.append(" and upper(model.key_) = :processDefinitionKey");
  			paramsMap.put("processDefinitionKey", processDefinitionKey.toUpperCase());
  		}
  		String processName = StringUtils.trimToNull(queryParamJSON.getString("processName"));
  		if(processName != null) {
  		    sql.append(" and upper(model.name_) like :processName");
  			paramsMap.put("processName", "%" + processName.toUpperCase() + "%");
  		}
		sql.append(" and exists(select 1 from act_hi_taskinst task left join base.act_bpm_task_leavel leavel on task.id_=leavel.task_id ");
		sql.append(" where task.end_time_ is not null and task.delete_reason_='completed'");
		if(user != null) {
			sql.append(" and upper(task.assignee_) = :assignee ");
			paramsMap.put("assignee", user.getId().toUpperCase());
		}
		sql.append(" and leavel.top_proc_inst_id=list.proc_inst_id)");
		sql.append(" group by model.key_");
		Pagination<ActStatisticProcessEntity_RO> pagination =  statisticProcessDAO_HI.findPagination(sql.toString(), paramsMap, pageIndex, pageRows);
		if(pagination.getData() != null && !pagination.getData().isEmpty()) {
			for(ActStatisticProcessEntity_RO instance: pagination.getData()) {
				instance.setStatus(WorkflowConstant.TASK_STATUS_RESOLVED);
			}
		}
		return pagination;
	}
	
	private Pagination<ActStatisticProcessEntity_RO> findProcessesOfResolving(JSONObject queryParamJSON, ActIdUserEntity_RO user,Integer pageIndex, Integer pageRows){
		StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
  		sql.append(ActStatisticProcessEntity_RO.QUERY_ALL_SQL);
  		String processDefinitionKey = StringUtils.trimToNull(queryParamJSON.getString("processDefinitionKey"));
  		if(processDefinitionKey != null) {
  		    sql.append(" and upper(model.key_) = :processDefinitionKey");
  			paramsMap.put("processDefinitionKey", processDefinitionKey.toUpperCase());
  		}
  		String processName = StringUtils.trimToNull(queryParamJSON.getString("processName"));
  		if(processName != null) {
  		    sql.append(" and upper(model.name_) like :processName");
  			paramsMap.put("processName", "%" + processName.toUpperCase() + "%");
  		}
		sql.append(" and exists(select 1 from act_hi_taskinst task left join base.act_bpm_task_leavel leavel on task.id_=leavel.task_id ");
		sql.append(" where task.end_time_ is null");
		if(user != null) {
            sql.append(" and (upper(task.assignee_) = :assignee1")	//指定人
               .append(" or (task.assignee_ is null and exists(select 1 from act_ru_identitylink idk where idk.task_id_=task.id_ and upper(idk.user_id_) = :assignee2))")//候选人
               .append(" or exists(select 1 from act_bpm_task_delegate delegate where delegate.delegate_user_id = :delegateUserId")//代办
               .append(" and delegate.status = :delegateStatus and delegate.task_id = task.id_))");
            paramsMap.put("assignee1", user.getId());
            paramsMap.put("assignee2", user.getId());
            paramsMap.put("delegateUserId", user.getUserId());
            paramsMap.put("delegateStatus", WorkflowConstant.DELEGATE_STATUS_PENDING);
       }
	   String status = StringUtils.trimToNull(queryParamJSON.getString("status"));
	   if(WorkflowConstant.TASK_STATUS_RESOLVING.equals(status)) {
		   sql.append(" and task.category_ = :status");
		   paramsMap.put("status", status);
	   }else if(WorkflowConstant.TASK_STATUS_PENDING.equals(status)) {
		   sql.append(" and task.category_ is null");
	   }
	   if(user != null) {
		   sql.append(" and upper(task.assignee_) = :assignee ");
		   paramsMap.put("assignee", user.getId().toUpperCase());
	   }
	   sql.append(" and leavel.top_proc_inst_id=list.proc_inst_id)");
	   sql.append(" group by model.key_");
	   Pagination<ActStatisticProcessEntity_RO> pagination =  statisticProcessDAO_HI.findPagination(sql.toString(), paramsMap, pageIndex, pageRows<1?1:pageRows);
	   if(pagination.getData() != null && !pagination.getData().isEmpty()) {
		   for(ActStatisticProcessEntity_RO instance: pagination.getData()) {
			   instance.setStatus(status);
		   }
	   }
	   return pagination;
	}

}
