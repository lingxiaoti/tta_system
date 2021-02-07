package com.sie.saaf.bpm.model.inter.server;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmCategory;
import com.sie.saaf.bpm.model.inter.IActBpmHistory;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmTaskConfig;
import com.sie.saaf.bpm.model.inter.IBpmCallBack;
import com.sie.saaf.transaction.annotation.TransMessageConsumer;
import com.sie.saaf.transaction.annotation.TransMessageProvider;
import com.sie.saaf.transaction.annotation.TransMsgParam;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;

/**
 * 流程回调类 当流程任务执行后或删除草稿状态的流程回调业务系统更新业务系统状态 采用事务补偿机制，回调失败自动补偿
 * 
 * @author sie-laoqunzhao 2018-06-29
 */
@Component("bpmCallBackServer")
public class BpmCallBackServer implements IBpmCallBack {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BpmCallBackServer.class);

	@Autowired
	private IActBpmHistory bpmHistoryServer;

	@Autowired
	private IActBpmTaskConfig bpmTaskConfigServer;

	@Autowired
	private IActBpmProcess bpmProcessServer;

	@Autowired
	private IActBpmModel bpmModelServer;

	@Autowired
	private IActBpmCategory bpmCategoryServer;

	/**
	 * 回调函数
	 * 
	 * @author sie-laoqunzhao 2018-06-29
	 * @param bpmList
	 *            申请单
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            处理人ID,用于业务系统识别处理人
	 */
	@Override
	@TransMessageProvider(desc = "bpmCallBack")
	public void callBack(ActBpmListEntity_HI bpmList, String taskId,
			Object userId) {
		Assert.notNull(bpmList, "申请单为空，回调失败");
		List<Object[]> bpmLists = new ArrayList<Object[]>();
		bpmLists.add(new Object[] { bpmList, taskId });
		doCallBack(bpmLists, userId);
	}

	/**
	 * 主动方事务，发送预消息、确认消息
	 * 
	 * @author sie-laoqunzhao 2018-06-29
	 * @param bpmLists
	 *            [申请单,任务ID]
	 * @param userId
	 *            处理人ID,用于业务系统识别处理人
	 */
	@Override
	@TransMessageProvider(desc = "bpmCallBack")
	public void callBack(List<Object[]> bpmLists, Object userId) {
		doCallBack(bpmLists, userId);
	}

	private void doCallBack(List<Object[]> bpmLists, Object userId) {
		Assert.notEmpty(bpmLists, "申请单为空，回调失败！");
		for (Object[] bpmList_ : bpmLists) {
			ActBpmListEntity_HI bpmList = (ActBpmListEntity_HI) bpmList_[0];
			String taskId = bpmList_[1] == null ? null : bpmList_[1].toString();
			Assert.notNull(bpmList, "申请单为空，回调失败！");
			LOGGER.info("流程申请单{},任务ID:{},userId:{}", JSON.toJSON(bpmList),
					taskId, userId);
			String processDefinitionKey = bpmList.getProcessDefinitionKey();
			String taskDefinitionKey = null;
			if (StringUtils.isNotBlank(taskId)) {
				HistoricTaskInstance historicTaskInstance = bpmHistoryServer
						.getByTaskId(taskId);
				Assert.notNull(historicTaskInstance, "任务不存在，回调失败！");
				// 对子流程的情况，需获取任务对应的流程定义，不能用申请单中的流程KEY
				String processInstanceId = bpmProcessServer
						.getProcessInstanceIdByTaskId(taskId);
				HistoricProcessInstance historicProcessInstance = bpmHistoryServer
						.getByProcessInstanceId(processInstanceId);
				Assert.notNull(historicProcessInstance, "流程实例不存在，回调失败！");
				processDefinitionKey = historicProcessInstance
						.getProcessDefinitionKey();
				taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
				if (StringUtils.isBlank(taskDefinitionKey)) {
					historicTaskInstance = bpmHistoryServer
							.getTopTaskByTaskId(taskId);
					if (historicTaskInstance != null) {
						taskDefinitionKey = historicTaskInstance
								.getTaskDefinitionKey();
					}
				}
			} else {
				// 没有指定任务，回调第一个节点的回调服务
				UserTask firstUserTask = bpmProcessServer
						.getFirstUserTask(bpmList.getProcessDefinitionId());
				Assert.notNull(firstUserTask, "流程定义错误或不存在，回调失败！");
				taskDefinitionKey = firstUserTask.getId();
			}
			Assert.notNull(processDefinitionKey, "流程定义不存在，回调失败！");
			Assert.notNull(taskDefinitionKey, "任务定义ID不存在，回调失败！");
			ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer
					.findByDefinition(processDefinitionKey, taskDefinitionKey,
							true);
			Assert.notNull(taskConfig, "节点设置不存在，回调失败！");
			Assert.notNull(taskConfig.getCallbackUrl(), "回调服务未定义，回调失败！");
			JSONObject msg = new JSONObject();
			msg.put("url", taskConfig.getCallbackUrl());
			msg.put("id", bpmList.getBusinessKey());
			msg.put("status", bpmList.getResult());
			msg.put("userId", userId);
			msg.put("processDefinitionKey", bpmList.getProcessDefinitionKey());
			msg.put("userids", userId);
			msg.put("taskName",taskConfig.getTaskName());
			msg.put("taskDefinitionKey", taskDefinitionKey);
			Model model = bpmModelServer.getByKey(bpmList
					.getProcessDefinitionKey());
			if (model != null && StringUtils.isNotBlank(model.getCategory())) {
				ActBpmCategoryEntity_HI category = bpmCategoryServer
						.get(Integer.parseInt(model.getCategory()));
				if (category != null
						&& StringUtils.isNotBlank(category.getProcessKey())) {
					msg.put("processDefinitionKey", category.getProcessKey());
				}
			}
			RedisMessageContentBean redisMessageContentBean = new RedisMessageContentBean(
					msg.toString(), "bpmCallBackDataQueue101");
			// 发送预消息
			RemoteServiceInvoke.sendMessageBody2Redis(redisMessageContentBean);
			LOGGER.info("线程{}预发送消息:{},线程变量:{}", Thread.currentThread()
					.getName(), redisMessageContentBean.getMessageIndex(), JSON
					.toJSON(new TreeSet<>(RemoteServiceInvoke.getMessageIds())));
		}
	}

	/**
	 * 被动方事务，回调业务系统服务
	 * 
	 * @author sie-laoqunzhao 2018-06-29
	 * @param messageindexId
	 *            消息索引
	 * @param jsonObject
	 *            参数
	 */
	@Override
	@TransMessageConsumer(desc = "bpmCallBack:bpmCallBack")
	@TransMessageProvider(desc = "bpmCallBackProvider:bpmCallBackProvider")
	public void updateCallBack(@TransMsgParam Long messageindexId,
			JSONObject jsonObject) throws Exception {
		LOGGER.info("回调参数:messageIndex{},json:{}", messageindexId, jsonObject);
		String url = jsonObject.getString("url");
		jsonObject.remove("url");
		JSONObject paramsJSON = new JSONObject();
		paramsJSON.put("params", jsonObject);
		RedisMessageContentBean redisMessageContentBean = new RedisMessageContentBean();
		redisMessageContentBean.setRequestURL(url);
		redisMessageContentBean.setMessageBody(paramsJSON.toString());
		RemoteServiceInvoke.sendMessageBody2Redis(redisMessageContentBean);
	}

}
