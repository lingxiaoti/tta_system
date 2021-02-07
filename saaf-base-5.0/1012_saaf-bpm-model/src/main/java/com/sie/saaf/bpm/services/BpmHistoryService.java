package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCommunicateEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/bpmHistoryService")
public class BpmHistoryService extends CommonAbstractService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BpmHistoryService.class);
    
    @Autowired
    private IActBpmHistory bpmHistoryServer;
    
    @Autowired
    private IActBpmTask bpmTaskServer;
    
    @Autowired
    private IActBpmUser bpmUserServer;
    
    @Autowired
    private IActBpmCommunicate bpmCommunicateServer;
    
    @Autowired
    private IActBpmTaskDelegate bpmTaskDelegateServer;
    
    @Autowired
    private IActBpmList bpmListServer;

	@Autowired
	private IActBpmProcess bpmProcessServer;

	@Autowired
	private IActBpmModel bpmModelServer;

	@Autowired
	private IBaseAccreditCache baseAssreditServer;
    
    /**
     * *@param params
	 *{
	 * drafter 发起人
	 * listCode 流程编号
	 * listName 流程名称
	 * title 流程标题
	 * businessKey 业务主键
	 * billNo 业务申请单号
	 * categoryId 流程分类
	 * systemCode 系统代码
	 * processDefinitionKey 流程定义Key，条件=
	 * processInstanceId 流程实例ID，条件=
	 * startDate 任务起始时间，格式yyyy-MM-dd
	 * endDate 任务截止时间，格式yyyy-MM-dd
	 * communicated 发起沟通 Y.是   N.否
	 * status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
	 * taskStatus 任务状态  PENDING.未接收   RESOLVING.办理中  RESOLVED.已办结
	 * duration 用时时长(>=)秒
	 * waiting 距今时长(>=)秒
	 * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
    @RequestMapping(method= RequestMethod.POST,value="findHistoricTasks")
    public String findHistoricTasks(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramJSON = this.parseObject(params);
            if(StringUtils.isBlank(paramJSON.getString("userId")) &&  !"true".equals(paramJSON.getString("searchAll"))) {
            	paramJSON.put("userId", bpmUserServer.getBpmUserId(super.getUserSessionBean()));
            }
            paramJSON.put("excludeDraft", true);//不查询起草节点
            Pagination<ActBpmHiTaskEntity_HI_RO> pagination = bpmHistoryServer.findHistoricTasks(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            if(pagination.getData() != null && !pagination.getData().isEmpty()) {
            	JSONArray array = result.getJSONArray(DATA);
            	bpmHistoryServer.appendHistoricDetail(array,"taskId");
                bpmHistoryServer.appendCurPrevTask(array, "processInstanceId");
            }
            result.put(SToolUtils.STATUS, SUCCESS_STATUS);
            result.put(SToolUtils.MSG, "成功");
            return result.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
    
    /**
     * 获取流程历史
     * @param params
     * processInstanceId:流程实例ID
	 * processDefinitionKey 流程KEY
	 * businessKey 业务主键
	 * ouId 事业部ID
	 * responsibilityId 职责ID
     * includeActive: 是否包含活动任务
     * @return
     */
    @RequestMapping(method= RequestMethod.POST,value="findHistoricActivities")
    public String findHistoricActivities(@RequestParam String params) {
        
        try {
            JSONObject paramsJSON = JSON.parseObject(params);
            String processInstanceId = paramsJSON.getString("processInstanceId");
            if(StringUtils.isBlank(processInstanceId)) {
				String businessKey = paramsJSON.getString("businessKey");
				String processDefinitionKey = paramsJSON.getString("processDefinitionKey");
				ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON, super.getSessionUserId());
				if(bpmList == null) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS, "申请单不存在！", 0, null).toString();
				}else if(org.apache.commons.lang3.StringUtils.isBlank(bpmList.getProcessInstanceId())) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS, "流程未发起！", 0, null).toString();
				}else {
					processInstanceId = bpmList.getProcessInstanceId();
				}
            }
			paramsJSON = new JSONObject();
            if(StringUtils.isBlank(processInstanceId)){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数：processInstanceId！", 0, null).toString();
			}
            paramsJSON.put("simple", true);
			paramsJSON.put("processInstanceId", processInstanceId);
            List<ActBpmHiTaskEntity_HI_RO> historicTasks = bpmHistoryServer.findHistoricTasks(paramsJSON, 1, Integer.MAX_VALUE).getData();
            List<Task> activeTasks = bpmTaskServer.findActiveTasks(processInstanceId);
			paramsJSON.put("type", "COMMON");
            List<ActBpmCommunicateEntity_HI_RO> communicates = bpmCommunicateServer.findCommunicates(paramsJSON, 1, Integer.MAX_VALUE).getData();
            if(historicTasks == null) {
            	historicTasks = new ArrayList<ActBpmHiTaskEntity_HI_RO>();
            }
            if(communicates != null && !communicates.isEmpty()) {
            	for(ActBpmCommunicateEntity_HI_RO communicate : communicates) {
            		ActBpmHiTaskEntity_HI_RO instance = new ActBpmHiTaskEntity_HI_RO();
            		instance.setAssignee(communicate.getUserName());
            		instance.setEndTime(communicate.getCreationDate());
            		instance.setProcessInstanceId(communicate.getProcessInstanceId());
            		instance.setProcessDefinitionKey(communicate.getProcessDefinitionKey());
            		instance.setStartTime(communicate.getCreationDate());
            		instance.setTaskName(communicate.getTaskName());
            		instance.setHis_detail_option("发消息");
            		instance.setHis_detail_opinion(communicate.getContent());
            		instance.setUserId(communicate.getCreatedBy());
            		instance.setUserName(communicate.getUserName());
            		instance.setUserFullName(communicate.getUserFullName());
            		//添加接收人标签
            		if(communicate.getReceiverId() != null) {
            			ActIdUserEntity_RO receiver = bpmUserServer.findUserBySysId(communicate.getReceiverId());
            			if(receiver != null) {
            				instance.setHis_detail_opinion(receiver.getUserFullName() + ": " + StringUtils.trimToEmpty(communicate.getContent()));
            			}
            		}
            		historicTasks.add(instance);
            	}
            }
            //排序
            if(historicTasks!=null && !historicTasks.isEmpty()) {
                Collections.sort(historicTasks, new Comparator<ActBpmHiTaskEntity_HI_RO>() {
					@Override
					public int compare(ActBpmHiTaskEntity_HI_RO o1, ActBpmHiTaskEntity_HI_RO o2) {
						return o1.getEndTime().compareTo(o2.getEndTime());
					}});
            }
            if(activeTasks != null && !activeTasks.isEmpty()) {
            	for(Task activeTask : activeTasks) {
            		ActBpmHiTaskEntity_HI_RO instance = new ActBpmHiTaskEntity_HI_RO();
            		instance.setAssignee(activeTask.getAssignee());
            		instance.setProcessInstanceId(activeTask.getProcessInstanceId());
            		instance.setStartTime(activeTask.getCreateTime());
            		instance.setTaskId(activeTask.getId());
            		instance.setTaskName(activeTask.getName());
            		instance.setParentTaskId(activeTask.getParentTaskId());
            		instance.setStatus(activeTask.getCategory());
            		//委托代办
            		List<Integer> delegateUserIds = new ArrayList<Integer>();
            		List<ActBpmTaskDelegateEntity_HI> delegates = bpmTaskDelegateServer.findByTaskId(activeTask.getId());
            		if(delegates != null && !delegates.isEmpty()) {
            			for(ActBpmTaskDelegateEntity_HI delegate : delegates) {
            				if((delegate.getDeleteFlag() == 0 || delegate.getDeleteFlag() == 0)
            						&& StringUtils.equals(delegate.getStatus(), WorkflowConstant.DELEGATE_STATUS_PENDING)
            						&& !delegateUserIds.contains(delegate.getDelegateUserId())) {
            					delegateUserIds.add(delegate.getDelegateUserId());
            				}
            			}
            		}
            		//取办理人
            		if(StringUtils.isNotBlank(activeTask.getAssignee())) {
            			instance.setUserName(activeTask.getAssignee());
            			ActIdUserEntity_RO user = bpmUserServer.findUserByBpmId(activeTask.getAssignee());
            			if(user != null) {
            				instance.setUserFullName(user.getUserFullName());
                			if(delegateUserIds.contains(user.getUserId())) {
                				delegateUserIds.remove(user.getUserId());
                			}
            			}
            		}else {
            			List<String> bpmUserIds = bpmTaskServer.getTaskBpmUserIds(activeTask.getId());
            			if(bpmUserIds != null && !bpmUserIds.isEmpty()) {
            				instance.setUserName(StringUtils.join(bpmUserIds, ","));
            			}
            			List<ActIdUserEntity_RO> users = bpmUserServer.findUsersByBpmIds(bpmUserIds);
            			if(users != null && !users.isEmpty()) {
            				List<String> userFullNames = new ArrayList<String>();
            				for(ActIdUserEntity_RO user : users) {
            					userFullNames.add(user.getUserFullName());
            					if(delegateUserIds.contains(user.getUserId())) {
                    				delegateUserIds.remove(user.getUserId());
                    			}
            				}
            				instance.setUserFullName(StringUtils.join(userFullNames, ","));
            			}
            		}
            		//委托办理人
            		if(delegateUserIds != null && !delegateUserIds.isEmpty()) {
            			List<ActIdUserEntity_RO> users = bpmUserServer.findUsersBySysIds(delegateUserIds);
            			if(users != null && !users.isEmpty()) {
            				List<String> userFullNames = new ArrayList<String>();
            				for(ActIdUserEntity_RO user : users) {
            					userFullNames.add(user.getUserFullName() + "（委托审批）");
            				}
            				if(StringUtils.isBlank(instance.getUserFullNameNoMarked())) {
            					instance.setUserFullName(StringUtils.join(userFullNames, ","));
            				}else {
            					instance.setUserFullName(instance.getUserFullNameNoMarked() + "," + StringUtils.join(userFullNames, ","));
            				}
            			}
            		}
            		historicTasks.add(instance);
            	}
            }
            JSONArray result = (JSONArray) JSON.toJSON(historicTasks);
            bpmHistoryServer.appendHistoricDetail(result,"taskId");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", result==null?0:result.size(), result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败" + e, 0, null).toString();
        } 
    }
    
    /**
     * 获取驳回重审节点列表
     * @param params
     * taskId:任务ID
     * type:1.驳回 2.重新提交
     * @return
     */
    @RequestMapping(method= RequestMethod.POST,value="findTaskNodes")
    public String findTaskNodes(@RequestParam String params) {
        
        try {
            JSONObject paramsJSON = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "taskId", "type");
            String taskId = paramsJSON.getString("taskId");
            String type =  paramsJSON.getString("type");
            Task task = bpmTaskServer.get(taskId);
            if(task == null) {
            	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "任务不存在！", 0, null).toString();
            }
            JSONArray result = bpmHistoryServer.getRetrialTaskNodes(task, "2".equals(type)?2:1);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", result==null?0:result.size(), result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } 
    }

    /**
     * 审批意见查询
     * @param params
     * {
     * searchAll true.查询全部用户 false.查询当前用户
     * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
    @RequestMapping(method= RequestMethod.POST,value="findHistoricOpinions")
    public String findHistoricOpinions(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "50") Integer pageRows) {
        try {
            JSONObject paramsJSON = this.parseObject(params);
            if(!"true".equals(paramsJSON.getString("searchAll"))) {
            	paramsJSON.put("userId", bpmUserServer.getBpmUserId(super.getUserSessionBean()));
            }
            paramsJSON.put("name", "opinion");
            Pagination<String> pagination = bpmHistoryServer.findHistoricTextDetail(paramsJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(SToolUtils.STATUS, SUCCESS_STATUS);
            result.put(SToolUtils.MSG, "成功");
            return result.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
    

}
