package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import io.jsonwebtoken.lang.Assert;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component("actBpmTaskDelegateServer")
public class ActBpmTaskDelegateServer implements IActBpmTaskDelegate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskDelegateServer.class);
	
	@Autowired
	private ViewObject<ActBpmTaskDelegateEntity_HI> bpmTaskDelegateDAO_HI;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
    private RuntimeService runtimeService;
	
	@Autowired
    private IActBpmCategory bpmCategoryServer;
	
	@Autowired
    private IActBpmUser bpmUserServer;
	
	@Autowired
    private IActBpmModel bpmModelServer;
	
	@Autowired
    private IActBpmTask bpmTaskServer;

    @Autowired
    private IBpmMessage bpmMessageServer;

	public ActBpmTaskDelegateServer() {
		super();
	}
	
	/**
     * 流程代办查询
     * @param parameters JSONObject
     * searchKey 流程标题、流程名称、流程编码、任务名称
     * processDefinitionKey 流程唯一标识
     * processDefinitionKeys 流程唯一标识
     * processInstanceId 流程实例ID
     * clientUserId 委托人ID
     * delegateUserId 代理人ID
     * status PENDING待办；CANCELED取消；BACKED退回；RESOLVED完成；DESTROYED作废
     * deleteFlag 删除标记，1.已删除，0.未删除
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
    @Override
    public Pagination<ActBpmTaskDelegateEntity_HI> findDelegates(JSONObject parameters, Integer pageIndex, Integer pageRows) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer hql = new StringBuffer("from ActBpmTaskDelegateEntity_HI bean");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateEntity_HI.class, parameters, "processDefinitionKey", "processDefinitionKey", hql, paramMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateEntity_HI.class, parameters, "processInstanceId", "processInstanceId", hql, paramMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateEntity_HI.class, parameters, "clientUserId", "clientUserId", hql, paramMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateEntity_HI.class, parameters, "delegateUserId", "delegateUserId", hql, paramMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateEntity_HI.class, parameters, "status", "status", hql, paramMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateEntity_HI.class, parameters, "deleteFlag", "deleteFlag", hql, paramMap, "=");
        if(!paramMap.isEmpty()) {
            hql = new StringBuffer(hql.toString().replaceFirst("\\s+and\\s+", " where "));
        }
        String startDate = StringUtils.trimToNull(parameters.getString("startDate"));
        if(startDate!=null){
            hql.append(" and bean.startTime >= :startDate");
            paramMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
        }
        String endDate = StringUtils.trimToNull(parameters.getString("endDate"));
        if(endDate!=null){
            hql.append(" AND bean.startTime < :endDate");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(endDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramMap.put("endDate", c.getTime());
        }
        
        String searchKey = StringUtils.trimToNull(parameters.getString("searchKey"));
        String categoryId = StringUtils.trimToNull(parameters.getString("categoryId"));
        JSONArray processDefinitionKeysJson = parameters.containsKey("processDefinitionKeys")?parameters.getJSONArray("processDefinitionKeys"):null;
        if(searchKey!=null || categoryId!=null || processDefinitionKeysJson!=null) {
            hql.append(paramMap.isEmpty()?" where":" and");
            hql.append(" exists(select 1 from ActBpmListEntity_HI bpm where bpm.processInstanceId=bean.processInstanceId");
            if(searchKey!=null) {
                searchKey = "%" + searchKey.toUpperCase() +"%";
                hql.append(" or upper(bean.taskName) like :taskName");
                hql.append(" or upper(bpm.listCode) like :listCode");
                hql.append(" or upper(bpm.listName) like :listName");
                hql.append(" or upper(bpm.title) like :title)");
                paramMap.put("taskName", searchKey);
                paramMap.put("listCode", searchKey);
                paramMap.put("listName", searchKey);
                paramMap.put("title", searchKey);
            }
            if(categoryId!=null) {
                List<Integer> categoryIds = bpmCategoryServer.getCategoryIds(Integer.parseInt(categoryId));
                hql.append(" and bpm.categoryId in (" + StringUtils.join(categoryIds, ",") + ")");
            }
            if(processDefinitionKeysJson!=null && !processDefinitionKeysJson.isEmpty()) {
                String processDefinitionKeys = "'" + processDefinitionKeysJson.getString(0) + "'";
                for(int i=1; i<processDefinitionKeysJson.size(); i++) {
                    processDefinitionKeys += ",'" + processDefinitionKeysJson.getString(i) + "'";
                }
                hql.append(" and bpm.processDefinitionKey in (" + processDefinitionKeys + ")");
            }
            hql.append(")");
        }
        hql.append(" order by bean.delegateId desc");
        Pagination<ActBpmTaskDelegateEntity_HI> pagination = bpmTaskDelegateDAO_HI.findPagination(hql.toString(), paramMap, pageIndex, pageRows);
        return pagination;
    }
    
    @Override
    public ActBpmTaskDelegateEntity_HI getById(Integer delegateId) {
    	return bpmTaskDelegateDAO_HI.getById(delegateId);
    }
    
    @Override
    public List<ActBpmTaskDelegateEntity_HI> findByTaskId(String taskId){
    	return bpmTaskDelegateDAO_HI.findByProperty("taskId", taskId);
    }
    
    @Override
    public boolean hasDelegatePermission(String taskId, Integer delegateUserId){
    	if(StringUtils.isBlank(taskId) || delegateUserId == null) {
    		return false;
    	}
    	List<ActBpmTaskDelegateEntity_HI> delegates = findByTaskId(taskId);
    	if(delegates != null && !delegates.isEmpty()) {
    		for(ActBpmTaskDelegateEntity_HI delegate : delegates) {
    			if(delegateUserId.equals(delegate.getDelegateUserId()) 
    					&& WorkflowConstant.DELEGATE_STATUS_PENDING.equals(delegate.getStatus())) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
	 * 保存代办
	 * @param taskDelegate 代办ActBpmTaskDelegateEntity_HI
	 */
    @Override
    public boolean save(ActBpmTaskDelegateEntity_HI taskDelegate) {
        //已存在不重复添加
        if(taskDelegateExist(taskDelegate.getTaskId(), taskDelegate.getOperatorUserId(), taskDelegate.getDelegateUserId())) {
            return true;
        }
        taskDelegate.setStartTime(new Date());
        taskDelegate.setOperatorUserId(taskDelegate.getClientUserId());
        taskDelegate.setStatus(WorkflowConstant.DELEGATE_STATUS_PENDING);
        taskDelegate.setDeleteFlag(0);
        bpmTaskDelegateDAO_HI.saveOrUpdate(taskDelegate);
        //发送代办消息
        if(taskDelegate.getIsAuto() == null || !taskDelegate.getIsAuto()) {
            Task task = taskService.createTaskQuery().taskId(taskDelegate.getTaskId()).singleResult();
            Assert.notNull(task, "任务不存在！");
            List<Integer> userIds = new ArrayList<Integer>();
            userIds.add(taskDelegate.getDelegateUserId());
            bpmMessageServer.sendDelegateMessage(task, userIds);
        }
        LOGGER.info("saved bpm task delegate:"+JSON.toJSONString(taskDelegate));
        return true;
    }
	

	/**
	 * 保存代办
	 * @author laoqunzhao 2018-05-30
	 * @param paramJSON JSONObject
	 * taskIds 任务ID JSONArray
	 * operatorUserId 委托人ID
	 * delegateUserId 代办人ID
	 */
	@Override
	public boolean save(JSONObject paramJSON) {
		JSONArray taskIdsJSON = paramJSON.getJSONArray("taskIds");
		Integer clientUserId = paramJSON.getInteger("operatorUserId");
		Integer delegateUserId = paramJSON.getInteger("delegateUserId");
		for(int i=0; i<taskIdsJSON.size(); i++) {
			String taskId = taskIdsJSON.getString(i);
			//已存在不重复添加
			if(taskDelegateExist(taskId, clientUserId, delegateUserId)) {
			    continue;
			}
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if(task == null) {
				continue;
			}
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			ActBpmTaskDelegateEntity_HI taskDelegate = new ActBpmTaskDelegateEntity_HI();
			taskDelegate.setTaskId(taskId);
			taskDelegate.setClientUserId(clientUserId);
			taskDelegate.setOperatorUserId(clientUserId);
			taskDelegate.setDelegateUserId(delegateUserId);
			taskDelegate.setStartTime(new Date());
			taskDelegate.setTaskName(task.getName());
			taskDelegate.setTaskTime(task.getCreateTime());
			taskDelegate.setProcessInstanceId(task.getProcessInstanceId());
			taskDelegate.setProcessDefinitionKey(processInstance!=null?processInstance.getProcessDefinitionKey():null);
			taskDelegate.setStatus(WorkflowConstant.DELEGATE_STATUS_PENDING);
			taskDelegate.setDeleteFlag(0);
			taskDelegate.setIsAuto(false);
			bpmTaskDelegateDAO_HI.saveOrUpdate(taskDelegate);
            //发送代办消息
            List<Integer> userIds = new ArrayList<Integer>();
            userIds.add(taskDelegate.getDelegateUserId());
            bpmMessageServer.sendDelegateMessage(task, userIds);
			LOGGER.info("saved bpm task delegate:" + JSON.toJSONString(taskDelegate));
		}		
		return true;
	}
	
	
	/**
	 * 批量保存代办
	 * @param categoryIds 分类ID
	 * @param processDefinitionKeys 流程定义KEYs
	 * @param clientUserId 委托人
	 * @param delegateUserId 代理人
	 * @throws Exception
	 */
	@Override
    public void saveTaskDelegates(List<Integer> categoryIds, List<String> processDefinitionKeys,
            Integer clientUserId, Integer delegateUserId) throws Exception {
        ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(clientUserId);
        processDefinitionKeys = bpmModelServer.getProcessDefinitionKeys(categoryIds, processDefinitionKeys);
        if(processDefinitionKeys==null || processDefinitionKeys.isEmpty()) {
            return;
        }
        JSONObject taskParamJson = new JSONObject();
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(delegateUserId);
        for(String processDefinitionKey:processDefinitionKeys) {
            taskParamJson.put("processDefinitionKey", processDefinitionKey);
            //获取所有待办任务
            List<ActBpmTaskEntity_HI_RO> tasks = bpmTaskServer.findTodoTasks(taskParamJson, user, -1, -1).getData();
            if(tasks==null || tasks.isEmpty()) {
                continue;
            }
            for(ActBpmTaskEntity_HI_RO task: tasks) {
                //已存在不可重复添加
                if(taskDelegateExist(task.getTaskId(), clientUserId, delegateUserId)) {
                    continue;
                }
                ActBpmTaskDelegateEntity_HI taskDelegate = new ActBpmTaskDelegateEntity_HI();
                taskDelegate.setStartTime(new Date());
                taskDelegate.setTaskId(task.getTaskId());
                taskDelegate.setTaskName(task.getTaskName());
                taskDelegate.setTaskTime(task.getCreateTime());
                taskDelegate.setProcessInstanceId(task.getProcessInstanceId());
                taskDelegate.setProcessDefinitionKey(processDefinitionKey);
                taskDelegate.setClientUserId(clientUserId);
                taskDelegate.setOperatorUserId(clientUserId);
                taskDelegate.setDelegateUserId(delegateUserId);
                taskDelegate.setStatus(WorkflowConstant.DELEGATE_STATUS_PENDING);
                taskDelegate.setDeleteFlag(0);
                taskDelegate.setIsAuto(false);
                bpmTaskDelegateDAO_HI.saveOrUpdate(taskDelegate);
                //发送代办消息;
                bpmMessageServer.sendDelegateMessage(task, userIds);
                LOGGER.info("saved bpm task delegate:"+taskDelegate.getDelegateId());
            }
        }        
    }
	
	/**
     * 更新代办状态
     * @param id 主键
     * @param  status
     * agentIds JSONArray 代办ID
     */
    @Override
    public void updateStatus(Integer id, String status) {
        ActBpmTaskDelegateEntity_HI  entity = bpmTaskDelegateDAO_HI.getById(id);
        if(entity!=null) {
            entity.setStatus(status);
            entity.setEndTime(new Date());
            bpmTaskDelegateDAO_HI.update(entity);
            LOGGER.info("updated bpm task delegate status:"+id );
        }
    }
	
	/**
     * 更新代办状态
     * @param paramJSON JSONObject
     * agentIds JSONArray 代办ID
     */
    @Override
    public void updateStatus(JSONObject paramJSON) {
        JSONArray delegateIds = paramJSON.getJSONArray("delegateIds");
        String status = paramJSON.getString("status");
        if(delegateIds!=null && !delegateIds.isEmpty()){
            for(int i=0; i<delegateIds.size(); i++){
                int id = delegateIds.getIntValue(i);
                ActBpmTaskDelegateEntity_HI  entity = bpmTaskDelegateDAO_HI.getById(id);
                if(entity!=null) {
                    entity.setStatus(status);
                    entity.setEndTime(new Date());
                    //entity.setOperatorUserId(paramJSON.getInteger("operatorUserId"));
                    bpmTaskDelegateDAO_HI.update(entity);
                    LOGGER.info("updated bpm task delegate status:"+id +":"+status);
                }
            }
        }
    }
    
    /**
     * 根据分类或流程定义KEY批量更新状态
     * @param categoryIds
     * @param processDefinitionKeys
     * @param clientUserId
     * @param status
     * @throws Exception
     */
    @Override
    public void updateStatus(List<Integer> categoryIds, List<String> processDefinitionKeys,
            Integer clientUserId, String status) throws Exception {
        processDefinitionKeys = bpmModelServer.getProcessDefinitionKeys(categoryIds, processDefinitionKeys);
        if(processDefinitionKeys==null || processDefinitionKeys.isEmpty()) {
            return;
        }
        JSONObject parameters = new JSONObject();
        parameters.put("clientUserId", clientUserId);
        parameters.put("status", WorkflowConstant.DELEGATE_STATUS_PENDING);
        parameters.put("deleteFlag", 0);
        JSONArray processDefinitionKeysJson = new JSONArray();
        for(String processDefinitionKey:processDefinitionKeys) {
            processDefinitionKeysJson.add(processDefinitionKey);
        }
        parameters.put("processDefinitionKeys", processDefinitionKeysJson);
        Pagination<ActBpmTaskDelegateEntity_HI> delegates = findDelegates(parameters, 1, Integer.MAX_VALUE);
        if(delegates.getData()!=null && !delegates.getData().isEmpty()) {
            for(ActBpmTaskDelegateEntity_HI entity : delegates.getData()){
                entity.setStatus(status);
                entity.setOperatorUserId(clientUserId);
                bpmTaskDelegateDAO_HI.update(entity);
                LOGGER.info("updated bpm task delegate status:"+JSON.toJSONString(entity));
            }
        }
    }

    /**
     * 标记删除代办
     * @param paramJSON JSONObject
     * agentIds JSONArray 代办ID
     */
    @Override
    public void delete(JSONObject paramJSON) {
        JSONArray delegateIds = paramJSON.getJSONArray("delegateIds");
        if(delegateIds!=null && !delegateIds.isEmpty()){
            for(int i=0; i<delegateIds.size(); i++){
                int id = delegateIds.getIntValue(i);
                ActBpmTaskDelegateEntity_HI  entity = bpmTaskDelegateDAO_HI.getById(id);
                if(entity!=null) {
                    entity.setDeleteFlag(1);
                    entity.setOperatorUserId(Integer.parseInt(paramJSON.getString("operatorUserId")));
                    bpmTaskDelegateDAO_HI.update(entity);
                    LOGGER.info("deleted bpm task delegate status:"+id );
                }
            }
        }
    }
	
	/**
	 * 物理删除代办
     * @param paramJSON JSONObject
     * agentIds JSONArray 代办ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
	    JSONArray delegateIds = paramJSON.getJSONArray("delegateIds");
	    if(delegateIds!=null && !delegateIds.isEmpty()){
	        for(int i=0; i<delegateIds.size(); i++){
	            int id = delegateIds.getIntValue(i);
	            bpmTaskDelegateDAO_HI.delete(id);
	            LOGGER.info("destory bpm task delegate:"+id );
	        }
	    }
	}
	
    /**
     * 
     */
    @Override
    public List<ActBpmTaskDelegateEntity_HI> findByProperty(Map<String, Object> properties) {
        if(properties==null) {
            properties = new HashMap<String, Object>();
        }
        return bpmTaskDelegateDAO_HI.findByProperty(properties);
    }
    
    private boolean taskDelegateExist(String taskId, Integer clientUserId, Integer delegateUserId) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("taskId", taskId);
        properties.put("clientUserId", clientUserId);
        properties.put("delegateUserId", delegateUserId);
        properties.put("status", "PENDING");
        List<ActBpmTaskDelegateEntity_HI> list = findByProperty(properties);
        return list==null||list.isEmpty()?false:true;
    }
	
	
}
