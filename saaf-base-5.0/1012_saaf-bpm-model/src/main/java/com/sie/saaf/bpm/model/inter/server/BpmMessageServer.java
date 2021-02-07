package com.sie.saaf.bpm.model.inter.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.bpm.configure.MessageConfig;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmCommunicateEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.constant.CloudInstanceNameConstants;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component("bpmMessageServer")
public class BpmMessageServer implements IBpmMessage {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(BpmMessageServer.class);

	private static  final String SEND_TRUN = "1";//发送开关标志

	private static final int DEFAULT_OU = 261;//默认OU

	private static final int EQUOTATION_OU = 3001;//EQU系统ou

	private static final int SEND_TRY_TIMES = 3;//失败重新请求次数
	
	private static final int MSG_TODO = 1;
	
	private static final int MSG_DELEGATE = 2;
	
	private static final int MSG_END = 3;
	
	private static final int MSG_CC = 4;
	
	private static final int MSG_URGE = 5;
	
	private static final int MSG_COMMUNICATE = 6;
	
	private static final int MSG_REJECT = 7;
	
	private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	@Autowired
    private MessageConfig messageConfig;
	
	@Autowired
	private IActBpmUser bpmUserServer;
	
	@Autowired
	private IActBpmList bpmListServer;
	
	@Autowired
	private IActBpmProcess bpmProcessServer;
	
	@Autowired
	private IActBpmHistory bpmHistoryServer;
	
	@Autowired
	private IActBpmTaskConfig bpmTaskConfigServer;

	@Autowired
	private IActBpmTask bpmTaskServer;

	@Autowired
	private IActBpmTaskDelegate bpmTaskDelegateServer;
	
	@Autowired
	private HistoryService historyService;

	@Autowired
	private ViewObject<BasePersonEntity_HI> basePersonDAO_HI;

	/**
	 * 发送待办消息,根据申请单和任务办理前的任务判断需要发送待办消息的任务
	 * @author laoqunzhao 2018-09-02
	 * @param bpmList 申请单
	 * @param activeTasks 原活动任务
	 */
	@Override
	public void setTodoMessage(final ActBpmListEntity_HI bpmList, final List<Task> activeTasks) {
		if(bpmList == null){
			return;
		}
		//异步发送
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					List<Task> newActiveTasks = bpmTaskServer.findActiveTasks(bpmList.getProcessInstanceId());
					if(newActiveTasks != null && !newActiveTasks.isEmpty()) {
						List<String> taskIds = new ArrayList<String>();
						if(activeTasks != null && !activeTasks.isEmpty()) {
							for (Task activeTask : activeTasks) {
								taskIds.add(activeTask.getId());
							}
						}
						for(Task newActiveTask : newActiveTasks) {
							//新增的任务发送待办信息
							if (!taskIds.contains(newActiveTask.getId())) {
								List<String> assignList = bpmTaskServer.getTaskBpmUserIds(newActiveTask.getId());
								doSendTodoMessage(bpmList, newActiveTask, assignList);
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
	 * 发送待办消息,根据申请单和任务办理前的任务判断需要发送待办消息的任务
	 * 异步，事务不一致导致 获取到的数据不是最新的数据
	 * @author 2019/12/27
	 * @param bpmList 申请单
	 * @param activeTasks 原活动任务
	 */
	@Override
	public void setTodoMessage(final ActBpmListEntity_HI bpmList, final List<Task> activeTasks,String type) {

		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		if(bpmList == null){
			return;
		}
		List<Task> newActiveTasks = bpmTaskServer.findActiveTasks(bpmList.getProcessInstanceId());
		if(newActiveTasks != null && !newActiveTasks.isEmpty()) {
			List<String> taskIds = new ArrayList<String>();
			if(activeTasks != null && !activeTasks.isEmpty()) {
				for (Task activeTask : activeTasks) {
					taskIds.add(activeTask.getId());
				}
			}
			for(Task newActiveTask : newActiveTasks) {
				//新增的任务发送待办信息
				if (!taskIds.contains(newActiveTask.getId())) {
					List<String> assignList = bpmTaskServer.getTaskBpmUserIds(newActiveTask.getId());
					HashMap<String,Object> map = new HashMap<String,Object>();
					map.put("bpmList",bpmList);
					map.put("newActiveTask",newActiveTask);
					map.put("assignList",assignList);
					list.add(map);
					//doSendTodoMessage(bpmList, newActiveTask, assignList);
				}
			}
		}
		//异步发送
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					for (HashMap<String, Object> stringObjectHashMap : list) {
						doSendTodoMessage((ActBpmListEntity_HI)stringObjectHashMap.get("bpmList"), (Task)stringObjectHashMap.get("newActiveTask"), (List<String>)stringObjectHashMap.get("assignList"));
					}
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}
	
	/**
	 * 发送待办消息
	 * @author laoqunzhao 2018-07-02
	 * @param bpmList 申请单
	 * @param task 任务
	 * @param bpmUserIds 接收人流程用户ID
	 */
	@Override
	public void sendTodoMessage(final ActBpmListEntity_HI bpmList, final Task task, final List<String> bpmUserIds) {
		if(task == null || bpmUserIds == null || bpmUserIds.isEmpty()){
			return;
		}
		//异步发送
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					doSendTodoMessage(bpmList, task, bpmUserIds);
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}

	/**
	 * 发送待办消息的具体实现方法
	 * @param bpmList  申请单
	 * @param task  任务
	 * @param bpmUserIds 办理人ID
	 */
	private void doSendTodoMessage(ActBpmListEntity_HI bpmList, Task task, List<String> bpmUserIds) {
		if(task == null || bpmUserIds == null || bpmUserIds.isEmpty()){
			return;
		}
		ActBpmHiTaskEntity_HI_RO taskEntity = new ActBpmHiTaskEntity_HI_RO();
		taskEntity.setAssignee(task.getAssignee());
		taskEntity.setTaskId(task.getId());
		taskEntity.setTaskName(task.getName());
		taskEntity.setStartTime(task.getCreateTime());
		taskEntity.setTaskDefinitionId(task.getTaskDefinitionKey());
		taskEntity.setParentTaskId(task.getParentTaskId());
		List<ActIdUserEntity_RO> receivers = bpmUserServer.findUsersByBpmIds(bpmUserIds);
		UserTask firstTask = bpmList == null ? null : bpmProcessServer.getFirstUserTask(bpmList.getProcessDefinitionId());
		if(firstTask != null && StringUtils.equals(firstTask.getId(), task.getTaskDefinitionKey())) {
			//驳回
			sendMessage(bpmList, taskEntity, null, receivers, MSG_REJECT);
		}else {
			//待办
			sendMessage(bpmList, taskEntity, null, receivers, MSG_TODO);
		}
		//发送代办
		List<ActBpmTaskDelegateEntity_HI> delegateTasks = bpmTaskDelegateServer.findByTaskId(task.getId());
		if(delegateTasks != null && !delegateTasks.isEmpty()){
			for(ActBpmTaskDelegateEntity_HI delegateTask : delegateTasks) {
				List<Integer> delegateUserIds = new ArrayList<Integer>();
				delegateUserIds.add(delegateTask.getDelegateUserId());
				sendDelegateMessage(task, delegateUserIds);
			}
		}
	}

	
	/**
	 * 发送委托代办消息
	 * @author laoqunzhao 2018-07-02
	 * @param task 任务
	 * @param userIds 接收人用户ID
	 */
	@Override
	public void sendDelegateMessage(final Task task, final List<Integer> userIds) {
		if(task == null || userIds == null || userIds.isEmpty()){
			return;
		}
		//异步发送
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					ActBpmHiTaskEntity_HI_RO taskEntity = new ActBpmHiTaskEntity_HI_RO();
					taskEntity.setAssignee(task.getAssignee());
					taskEntity.setTaskId(task.getId());
					taskEntity.setTaskName(task.getName());
					taskEntity.setStartTime(task.getCreateTime());
					taskEntity.setTaskDefinitionId(task.getTaskDefinitionKey());
					taskEntity.setParentTaskId(task.getParentTaskId());
					String topProcessInstanceId = bpmProcessServer.getTopProcessInstanceIdByTaskId(task.getId());
					ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(topProcessInstanceId);
					List<ActIdUserEntity_RO> receivers = bpmUserServer.findUsersBySysIds(userIds);
					sendMessage(bpmList, taskEntity, null, receivers, MSG_DELEGATE);
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}

	/**
	 * 发送委托代办消息
	 * @author laoqunzhao 2018-07-02
	 * @param task 任务
	 * @param userIds 接收人用户ID
	 */
	@Override
	public void sendDelegateMessage(final ActBpmTaskEntity_HI_RO task, final List<Integer> userIds) {
		if(task == null || userIds == null || userIds.isEmpty()){
			return;
		}
		//异步发送
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					ActBpmHiTaskEntity_HI_RO taskEntity = new ActBpmHiTaskEntity_HI_RO();
					taskEntity.setAssignee(task.getAssignee());
					taskEntity.setTaskId(task.getTaskId());
					taskEntity.setTaskName(task.getTaskName());
					taskEntity.setStartTime(task.getCreateTime());
					taskEntity.setTaskDefinitionId(task.getTaskDefinitionId());
					taskEntity.setParentTaskId(task.getParentTaskId());
					String topProcessInstanceId = bpmProcessServer.getTopProcessInstanceIdByTaskId(task.getTaskId());
					ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(topProcessInstanceId);
					List<ActIdUserEntity_RO> receivers = bpmUserServer.findUsersBySysIds(userIds);
					sendMessage(bpmList, taskEntity, null, receivers, MSG_DELEGATE);
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}
	
	/**
	 * 发送结束消息
	 * @author laoqunzhao 2018-07-02
	 * @param bpmList 申请单
	 */
	@Override
	public void sendEndMessage(final ActBpmListEntity_HI bpmList) {
		if(bpmList == null){
			return;
		}
		//异步发送
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					List<Integer> userIds = new ArrayList<Integer>();
					userIds.add(bpmList.getCreatedBy());
					List<ActIdUserEntity_RO> receivers = bpmUserServer.findUsersBySysIds(userIds);
					sendMessage(bpmList, null, null, receivers, MSG_END);
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}
	
	/**
	 * 发送待阅消息
	 * @author laoqunzhao 2018-07-02
	 * @param taskId 任务ID
	 * @param bpmUserIds 用户ID
	 */
	@Override
	public void sendCcMessage(final String taskId, final List<String> bpmUserIds) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					ActBpmHiTaskEntity_HI_RO task = bpmHistoryServer.getBpmHistoricTaskByTaskId(taskId);
					String topProcessInstanceId = bpmProcessServer.getTopProcessInstanceIdByTaskId(taskId);
					ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(topProcessInstanceId);
					List<ActIdUserEntity_RO> receivers = bpmUserServer.findUsersByBpmIds(bpmUserIds);
					sendMessage(bpmList, task, null, receivers, MSG_CC);
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}
		
	/**
	 * 发送沟通催办消息
	 * @author laoqunzhao 2018-07-02
	 * @param communicate 沟通Entity
	 */
	@Override
	public void sendCommunicateMessage(final ActBpmCommunicateEntity_HI communicate) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					ActBpmHiTaskEntity_HI_RO task = bpmHistoryServer.getBpmHistoricTaskByTaskId(communicate.getTaskId());
					ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(communicate.getProcessInstanceId());
					List<Integer> userIds = new ArrayList<Integer>();
					userIds.add(communicate.getReceiverId());
					List<ActIdUserEntity_RO> receivers = bpmUserServer.findUsersBySysIds(userIds);
					if(WorkflowConstant.MSG_TYPE_URGE.equals(communicate.getType())) {
						//催办消息
						if(bpmList.getProcessDefinitionKey().startsWith("EQU")){
							bpmList.setOrgId(3001);
						}
						sendMessage(bpmList, task, null, receivers, MSG_URGE);
					}else {
						//发起沟通
						sendMessage(bpmList, task, communicate.getContent(), receivers, MSG_COMMUNICATE);
					}
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		};
		pool.submit(runnable);
	}

	/**
	 * 发送消息
	 * @author laoqunzhao 2018-07-02
	 * @param title 标题
	 * @param content 内容
	 * @param msgTypes 消息类型
	 * @param receivers 接收人
	 * @param ouId ou ID
	 */
	@Override
	public void sendMessage(final String title, final String content, final String[] msgTypes, final List<ActIdUserEntity_RO> receivers, final int ouId) {
		Runnable runnable = new Runnable() {
            @Override
            public void run() {
            	for(String msgType : msgTypes) {
	                try {
	                	sendMessage(title, content, null, msgType, null, receivers, ouId);
	                }catch(Exception e) {
	                    LOGGER.error(e.getMessage(), e);
	                }
            	}
            }
        };
        pool.submit(runnable);
	}

	private void sendMessage(ActBpmListEntity_HI bpmList, ActBpmHiTaskEntity_HI_RO task, String content, List<ActIdUserEntity_RO> receivers, int type) {
		try {
			Assert.notNull(bpmList , "申请单不存在，消息发送失败！");
			Assert.isTrue(receivers != null && !receivers.isEmpty(), "接收人为空，消息发送失败！");

			JSONObject templateValues = getTemplateValues(bpmList, task);
			String[] msgTypes = null;
			String title = null;
			String linkUrl = getLinkUrl(bpmList, task == null? null : task.getTaskId(), task == null? null : task.getTaskDefinitionId(),type);
			templateValues.put("linkUrl",linkUrl);

			switch(type) {
				case MSG_TODO:
					if(SEND_TRUN.equals(messageConfig.getTodoTurn())) {
						Assert.notNull(task, "任务不存在，消息发送失败！");
						msgTypes = getMsgChannel(messageConfig.getTodoChannel());
						title = formatText(templateValues, messageConfig.getTodoTitle());
						content = formatText(templateValues, messageConfig.getTodoContent());
					}
					break;
				case MSG_DELEGATE:
					if(SEND_TRUN.equals(messageConfig.getDelegateTurn())) {
						Assert.notNull(task, "任务不存在，消息发送失败！");
						msgTypes = getMsgChannel(messageConfig.getDelegateChannel());
						title = formatText(templateValues, messageConfig.getDelegateTitle());
						content = formatText(templateValues, messageConfig.getDelegateContent());
					}
					break;
				case MSG_END:
					if(SEND_TRUN.equals(messageConfig.getEndTurn())) {
						msgTypes = getMsgChannel(messageConfig.getEndChannel());
						title = formatText(templateValues, messageConfig.getEndTitle());
						content = formatText(templateValues, messageConfig.getEndContent());
					}
					break;
				case MSG_CC:
					if(SEND_TRUN.equals(messageConfig.getCcTurn())) {
						msgTypes = getMsgChannel(messageConfig.getCcChannel());
						title = formatText(templateValues, messageConfig.getCcTitle());
						content = formatText(templateValues, messageConfig.getCcContent());
					}
					break;
				case MSG_URGE:
					if(SEND_TRUN.equals(messageConfig.getUrgeTurn())) {
						msgTypes = getMsgChannel(messageConfig.getUrgeChannel());
						title = formatText(templateValues, messageConfig.getUrgeTitle());
						content = formatText(templateValues, messageConfig.getUrgeContent());
					}
					break;
				case MSG_COMMUNICATE:
					if(SEND_TRUN.equals(messageConfig.getCommunicateTurn())) {
						Assert.notNull(task, "任务不存在，消息发送失败！");
						msgTypes = getMsgChannel(messageConfig.getCommunicateChannel());
						title = formatText(templateValues, messageConfig.getCommunicateTitle());
					}
					break;
				case MSG_REJECT:
					if(SEND_TRUN.equals(messageConfig.getCommunicateTurn())) {
						msgTypes = getMsgChannel(messageConfig.getRejectChannel());
						title = formatText(templateValues, messageConfig.getRejectTitle());
						content = formatText(templateValues, messageConfig.getRejectContent());
					}
					break;
				default:break;
			}
			templateValues.put("content",content);
			//默认以站内信发送
			if(msgTypes == null || msgTypes.length==0){
				msgTypes = new String[]{WorkflowConstant.MSG_TYPE_INMAIL};
			}
			for(String msgType : msgTypes) {
				if(WorkflowConstant.MSG_TYPE_EMAIL.equals(msgType) && StringUtils.isNotBlank(linkUrl)) {
					sendMessage(title, "<a href=\"" + linkUrl + "\" target=\"_blank\">" + content + "</a>", templateValues, msgType, bpmList.getProcessInstanceId(), receivers, bpmList.getOrgId());
				}else {
					sendMessage(title, content, templateValues, msgType, bpmList.getProcessInstanceId(), receivers, bpmList.getOrgId());
				}
			}
		}catch(Exception e) {
			LOGGER.error("send message failed: bpmlist:{} task:{} exception:{}", JSON.toJSON(bpmList), JSON.toJSON(task), e);
		}
	}
	
	
	
	
	private void sendMessage(String title, String content, JSONObject templateValues, String msgType, String bizId, List<ActIdUserEntity_RO> receivers, Integer ouId) {
		if(receivers == null || receivers.isEmpty()) {
			return;
		}
		String url = messageConfig.getUrl().replace("[server]", CloudInstanceNameConstants.INSTANCE_MESSAGE);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        JSONObject params = new JSONObject();
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
//        JSONObject sendConfig = ouId == null? null : getConfigJSON(ouId, msgType);

		JSONObject sendConfig = null;
		if(ouId != null) {
			sendConfig = getConfigJSON(ouId, msgType);
			if (ouId == 3001) {
				System.out.println("【templateValues】:" + templateValues.toString());
				if (!templateValues.containsKey("AssigneeName")) {
					System.out.println("1111111111111111111");
					sendConfig = getConfigJSON(3002, msgType);
				}
			}
		}

		if(sendConfig == null){
			sendConfig = getConfigJSON(DEFAULT_OU, msgType);
		}
		System.out.println("【sendConfig】:" + sendConfig.toString());
        Assert.notNull(sendConfig, "没有消息发送配置，无法发送消息！");
        params.put("requestId", uuid);
        params.put("synchro", messageConfig.getSynchro());//异步
        params.put("bizType", msgType);
        params.put("bizId", bizId);
        params.put("msgCfgId", sendConfig.getString("msgCfgId"));
        params.put("userName", sendConfig.getString("userName"));
        params.put("password", sendConfig.getString("password"));
        params.put("msgSubject", title);
//        params.put("message", content);
		params.put("message", "");
        params.put("templateValue", templateValues);
        List<String> sendTo = new ArrayList<String>();
        for(ActIdUserEntity_RO receiver: receivers) {
        	switch(msgType) {
        	case WorkflowConstant.MSG_TYPE_WECHAT:sendTo.add(receiver.getUserName());break;
        	case WorkflowConstant.MSG_TYPE_SMS:sendTo.add(receiver.getMobilePhone());break;
        	case WorkflowConstant.MSG_TYPE_EMAIL:sendTo.add(receiver.getEmail());break;
        	case WorkflowConstant.MSG_TYPE_INMAIL:sendTo.add(receiver.getUserName());break;
        	default:break;
        	}
        }
        params.put("sendTo", StringUtils.join(sendTo, ";"));
        paramMap.put("params", params);
		int times = SEND_TRY_TIMES;//失败请求次数
		while(times-- > 0) {
			try {
				JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(url, (JSONObject) JSON.toJSON(paramMap));
				LOGGER.info("send message: url:{}; params:{}; result:{}", url, paramMap, resultJSON);
				if (resultJSON != null && WorkflowConstant.STATUS_SUCESS.equals(resultJSON.getString("status"))) {
					times = 0;
					break;
				} else {
					LOGGER.error("消息发送失败：{}", resultJSON);
				}
			} catch (Exception e) {
				LOGGER.error("消息发送失败：{}", e.getMessage());
			}
		}
	}
	
	private JSONObject getTemplateValues(ActBpmListEntity_HI bpmList, ActBpmHiTaskEntity_HI_RO task) {
		JSONObject templateJSON = new JSONObject();
		if(bpmList != null) {
			templateJSON.put("ProcessKey", bpmList.getProcessDefinitionKey());
			templateJSON.put("ProcessName", bpmList.getListName());
			templateJSON.put("Title", bpmList.getTitle());
			if(!SaafToolUtils.isNullOrEmpty(bpmList.getVariables())){
				JSONArray jsonArray = JSON.parseArray(bpmList.getVariables());
				int i = 0 ;
				for (Object obj : jsonArray) {
					i++;
					JSONObject jsonObject = (JSONObject) obj;
					String name = jsonObject.getString("name");
					if( (!SaafToolUtils.isNullOrEmpty(name)) && name.equals("param")){
						int j = 0 ;
						JSONObject jsonObject1 = JSON.parseObject(jsonObject.getString("value")) ;
						for(Map.Entry<String, Object> entry : jsonObject1.entrySet()) {
							j++;
							templateJSON.put(entry.getKey(), entry.getValue());
							if(j>3000){
								break;
							}
						}
						break;
					}
					if(i>3000){
						break;
					}
				}
			}
			//发起人
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(bpmList.getCreatedBy());
			templateJSON.put("StartUser", user != null?user.getUserFullName():"");
			templateJSON.put("StartTime", ConvertUtil.dateToString(bpmList.getStartTime()));
		}
		if(task != null) {
			templateJSON.put("TaskName", task.getTaskName());
			templateJSON.put("TaskTime", ConvertUtil.dateToString(task.getStartTime()));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			JSONObject params = new JSONObject();
			System.out.println("【Assignee】:" + task.getAssignee());
			params.put("employeeNumber", task.getAssignee());
			paramMap.put("params", params);
			String url = "http://1002-saaf-api-gateway/api/baseServer/basePersonService/findPagination";
			JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(url, (JSONObject) JSON.toJSON(paramMap));
			if (resultJSON != null && WorkflowConstant.STATUS_SUCESS.equals(resultJSON.getString("status"))) {
				JSONArray resultArray = resultJSON.getJSONArray("data");
				JSONObject dataObj = resultArray.getJSONObject(0);
				String personNameCn = dataObj.getString("personNameCn");
				if("超级管理员".equals(personNameCn)){
					personNameCn = "审批人";
				}
				templateJSON.put("AssigneeName",personNameCn);
			}
		}
		return templateJSON;
	}
	
	private String formatText(JSONObject templateValues, String formatText) {
		if(StringUtils.isBlank(formatText)) {
			return null;
		}
		if(templateValues != null) {
			for(String key: templateValues.keySet()) {
				formatText = formatText.replaceAll("\\[\\s*" + key + "\\s*\\]", templateValues.getString(key));
			}
		}
		return formatText;
	}
	
	private String[] getMsgChannel(String channelStr) {
		if(StringUtils.isBlank(channelStr)) {
			return new String[] {WorkflowConstant.MSG_TYPE_INMAIL};
		}else {
			return channelStr.split(",");
		}
	}
	

	
	private String getLinkUrl(ActBpmListEntity_HI bpmList, String taskId, String taskDefinitionId, int type) {
		if(bpmList == null) {
			return null;
		}
		String pcFormUrl = null;
		//获取表单地址
		if(StringUtils.isBlank(taskDefinitionId)) {
			if(StringUtils.isNotBlank(taskId)) {
				HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
				if(historicTaskInstance != null) {
					taskDefinitionId = historicTaskInstance.getTaskDefinitionKey();
					while(StringUtils.isNotBlank(historicTaskInstance.getParentTaskId())) {
						historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(historicTaskInstance.getParentTaskId()).singleResult();
						taskDefinitionId = historicTaskInstance == null?taskDefinitionId : historicTaskInstance.getTaskDefinitionKey();
					}
				}
			}else {
				UserTask firstUserTask = bpmProcessServer.getFirstUserTask(bpmList.getProcessDefinitionId());
				taskDefinitionId = firstUserTask == null ? null : firstUserTask.getId();
			}
		}
		if(StringUtils.isNotBlank(taskDefinitionId)) {
			ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(bpmList.getProcessDefinitionKey(), taskDefinitionId, true);
			if(taskConfig != null) {
				pcFormUrl = taskConfig.getPcformUrl();
			}
		}
		if(StringUtils.isBlank(pcFormUrl)) {
			return null;
		}
		String linkUrl = messageConfig.getPcJumpUrl();
//		String linkUrl = messageConfig.getPcJumpUrl() + pcFormUrl;
//		linkUrl += "&processInstanceId=" + bpmList.getProcessInstanceId();
//		linkUrl += "&processDefinitionId=" + bpmList.getProcessDefinitionId();
//		linkUrl += "&processDefinitionKey=" + bpmList.getProcessDefinitionKey();
//		linkUrl += "&businessKey=" + bpmList.getBusinessKey();
//		if(StringUtils.isNotBlank(taskId)) {
//			linkUrl += "&taskId=" + taskId;
//		}
//		if(StringUtils.isNotBlank(taskDefinitionId)) {
//			linkUrl += "&taskDefinitionId=" + taskDefinitionId;
//		}
//		if(MSG_CC == type) {
//			linkUrl += "&action=unread";
//		}else if(MSG_TODO == type || MSG_DELEGATE == type || MSG_URGE == type) {
//			linkUrl += "&action=approval";
//		}else if(MSG_REJECT == type) {
//			linkUrl += "&action=refusal";
//		}else {
//			linkUrl += "&action=detail";
//		}
		return linkUrl;
	}
	
	private JSONObject getConfigJSON(Integer ouId, String msgType) {
		List<JSONObject> configsJSON = messageConfig.getConfigsJSON();
		if(configsJSON != null && !configsJSON.isEmpty()) {
			for(JSONObject configJSON: configsJSON) {
				if(StringUtils.equals(configJSON.getString("ouId"), String.valueOf(ouId))
						&& StringUtils.equals(configJSON.getString("msgType"), String.valueOf(msgType))) {
					return configJSON;
				}
			}
		}
		return null;
	}


}
