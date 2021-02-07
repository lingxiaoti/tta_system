package com.sie.saaf.bpm.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmHistory;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmTask;
import com.sie.saaf.bpm.model.inter.IActBpmTaskConfig;
import com.sie.saaf.bpm.model.inter.IActBpmTaskDelegate;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.bpm.utils.VerifyUtil;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/bpmTaskService")
public class BpmTaskService extends CommonAbstractService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BpmTaskService.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private IActBpmTask bpmTaskServer;

	@Autowired
	private IActBpmProcess bpmProcessServer;

	@Autowired
	private IActBpmUser bpmUserServer;

	@Autowired
	private IActBpmHistory bpmHistoryServer;

	@Autowired
	private IActBpmTaskConfig bpmTaskConfigServer;

	@Autowired
	private IActBpmTaskDelegate bpmTaskDelegateServer;

	@Autowired
	private IActBpmList bpmListServer;

	@Autowired
	private IActBpmModel bpmModelServer;

	// @Autowired
	// private IActBpmTaskLeavel bpmTaskLeavelServer;

	public BpmTaskService() {
		super();
	}

	/**
	 * 流程任务待办查询
	 * 
	 * @author laoqunzhao 2018-05-04
	 * @param params
	 *            JSONObject drafter 起草人 listCode 流程编号 listName 流程名称 title 流程标题
	 *            businessKey 业务主键 billNo 业务申请单号 categoryId 流程分类 systemCode 系统代码
	 *            processDefinitionKey 流程定义Key，条件= startDate 任务起始时间，格式yyyy-MM-dd
	 *            endDate 任务截止时间，格式yyyy-MM-dd applyStartDate
	 *            流程发起起始时间，格式yyyy-MM-dd applyEndDate 流程发起截止时间，格式yyyy-MM-dd
	 *            communicated 发起沟通 Y.是 N.否 exceptional 异常 Y.是 N.否 status
	 *            PENDING.未接收， RESOLVING.办理中
	 * @param pageIndex
	 *            页码索引
	 * @param pageRows
	 *            每页记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTodoTasks")
	public String findTodoTasks(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramJSON = this.parseObject(params);
			ActIdUserEntity_RO user = null;
			// 只查询当前用户数据
			if (!"true".equals(paramJSON.getString("searchAll"))) {
				user = bpmUserServer.findUserBySysId(super.getSessionUserId());
			}
			Pagination<ActBpmTaskEntity_HI_RO> pagination = bpmTaskServer
					.findTodoTasks(paramJSON, user, pageIndex, pageRows);
			JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
			/*
			 * if(pagination.getData() != null &&
			 * !pagination.getData().isEmpty()) { JSONArray array =
			 * result.getJSONArray(DATA);
			 * bpmHistoryServer.appendCurPrevTask(array, "processInstanceId");
			 * bpmUserServer.appendUserInfo(array, "prev_assignee", true,
			 * "prev"); }
			 */
			result.put(STATUS, SUCCESS_STATUS);
			result.put(MSG, "成功");
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败\n" + e,
					0, null).toString();
		}
	}

	/**
	 * 任务处理
	 * 
	 * @author laoqunzhao 2018-05-04
	 * @param params
	 *            { properties: {流程表单字段 opinion : 审批意见, option :
	 *            审批项，"Y"通过，"RJ"驳回，"RS"驳回重审，"S"终止， taskDefinitionId: 驳回重审节点 其他字段
	 *            : 字段值 }, saveonly : 是否只保存表单信息，true是（不提交任务），false否（提交任务）,
	 *            taskId : 任务ID, delegateId：代办ID,//用于任务代办 variables : [流程变量 {
	 *            name : 变量名称, type : 变量类型long/double/boolean/date/string, value
	 *            : 变量值" }， { name : "tasks_assignees",//任务选人变量 type : "string",
	 *            value : [ //任务节点ID及办理人ID JSONArray { id : 任务节点ID, userIds :
	 *            [办理人ID JSONArray] }] }] }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "complete")
	public String complete(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "taskId");
			VerifyUtil.verifyJSON(paramsJSON, "properties.opinion", false, 200,
					"审批意见");
			String taskId = paramsJSON.getString("taskId");
			Task task = taskService.createTaskQuery().taskId(taskId)
					.singleResult();
			if (task == null) {
				HistoricTaskInstance historicTaskInstance = historyService
						.createHistoricTaskInstanceQuery().taskId(taskId)
						.singleResult();
				Assert.isNull(historicTaskInstance, "该单据状态已更新，请勿重复操作！");
				Assert.notNull(historicTaskInstance, "任务不存在！");
			}
			// 代办
			String delegateId = paramsJSON.getString("delegateId");
			String varialbesStr = paramsJSON.getString("variables");
			String saveonly = paramsJSON.getString("saveonly");// 只保存不提交完成

			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(this
					.getSessionUserId());
			List<String> taskUserIds = bpmTaskServer.getTaskBpmUserIds(taskId);
			// 审批人中没有当前用户
			if (taskUserIds == null || !taskUserIds.contains(user.getId())) {
				if (!bpmTaskDelegateServer.hasDelegatePermission(taskId,
						getSessionUserId())) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"没权限操作", 0, null).toString();
				}
			}
			// 流程变量
			Map<String, Object> variables = StringUtils.isBlank(varialbesStr) ? new HashMap<String, Object>()
					: bpmProcessServer.jsonToVariables(varialbesStr);
			// 流程表单（审批选项，审批意见）
			@SuppressWarnings("unchecked")
			Map<String, String> properties = paramsJSON
					.containsKey("properties") ? JSON.toJavaObject(
					paramsJSON.getJSONObject("properties"), Map.class)
					: new HashMap<String, String>();
			String processInstanceId = org.apache.commons.lang.StringUtils
					.isBlank(task.getParentTaskId()) ? task
					.getProcessInstanceId() : bpmProcessServer
					.getProcessInstanceIdByTaskId(task.getParentTaskId());
			ActBpmListEntity_HI bpmList = bpmListServer
					.getByProcessInstanceId(processInstanceId);
			Assert.notNull(bpmList, "申请单不存在！");
			// 优先采用前端传入的参数信息。
			String title = paramsJSON.getString("title");
			if (StringUtils.isNotBlank(title)) {
				bpmList.setTitle(title);
			}
			UserTask firstTask = bpmProcessServer.getFirstUserTask(bpmList
					.getProcessDefinitionId());
			// 重新提交，保存申请单数据
			if (StringUtils.equals(task.getTaskDefinitionKey(),
					firstTask.getId())) {
				if (StringUtils.isNotBlank(paramsJSON.getString("respId"))
						&& StringUtils.isBlank(paramsJSON
								.getString("responsibilityId"))) {
					paramsJSON.put("responsibilityId",
							paramsJSON.getString("respId"));
				}
				ActBpmListEntity_HI instance = JSON.parseObject(
						paramsJSON.toString(), ActBpmListEntity_HI.class);
				bpmList.setCustAccountId(instance.getCustAccountId() != null ? instance
						.getCustAccountId() : bpmList.getCustAccountId());
				bpmList.setDepartmentId(instance.getDepartmentId() != null ? instance
						.getDepartmentId() : bpmList.getDepartmentId());
				bpmList.setDescription(StringUtils.isNotBlank(instance
						.getDescription()) ? instance.getDescription()
						: bpmList.getDescription());
				bpmList.setExtend(StringUtils.isNotBlank(instance.getExtend()) ? instance
						.getExtend() : bpmList.getExtend());
				bpmList.setOrgId(instance.getOrgId() != null ? instance
						.getOrgId() : bpmList.getOrgId());
				bpmList.setPositionId(instance.getPositionId() != null ? instance
						.getPositionId() : bpmList.getPositionId());
				bpmList.setProperties(StringUtils.isNotBlank(instance
						.getProperties()) ? instance.getProperties() : bpmList
						.getProperties());
				bpmList.setResponsibilityId(instance.getCustAccountId() != null ? instance
						.getCustAccountId() : bpmList.getResponsibilityId());
				bpmList.setRoleType(StringUtils.isNotBlank(instance
						.getRoleType()) ? instance.getRoleType() : bpmList
						.getRoleType());
				bpmList.setUserType(StringUtils.isNotBlank(instance
						.getUserType()) ? instance.getUserType() : bpmList
						.getUserType());
				bpmList.setVariables(StringUtils.isNotBlank(instance
						.getVariables()) ? instance.getVariables() : bpmList
						.getVariables());
				bpmList.setApplyPositionId(instance.getApplyPositionId() != null ? instance
						.getApplyPositionId() : bpmList.getApplyPositionId());
				bpmList.setApplyPersonId(instance.getApplyPersonId() != null ? instance
						.getApplyPersonId() : bpmList.getApplyPersonId());
				bpmProcessServer.setStartVariables(variables, bpmList, user);
				// bpmList.setTitle(paramsJSON.getString("title"));
				if (paramsJSON.containsKey("title")) {
					if (paramsJSON.getString("title") != null && !paramsJSON.getString("title").equals("")) {
						bpmList.setTitle(title);
					}
				}
				bpmListServer.update(bpmList);
			}
			// bpmList.setTitle(paramsJSON.getString("title"));
			bpmTaskServer.complete(task, bpmList, StringUtils
					.isNotBlank(delegateId) ? Integer.parseInt(delegateId)
					: null, variables, properties, user, StringUtils.equals(
					saveonly, "true"), true);

			// try{bpmTaskServer.AutoSetAssignee();}catch (Exception e){}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 任务处理
	 * 
	 * @author laoqunzhao 2018-06-15
	 * @param params
	 *            { properties: {流程表单字段 opinion : 审批意见, option :
	 *            审批项，"Y"通过，"RJ"驳回， 其他字段 : 字段值 }, taskIds : [] 任务ID
	 * 
	 *            }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "batchComplete")
	public String batchComplete(@RequestParam String params) {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			JSONArray taskIdsJSON = paramJSON.getJSONArray("taskIds");
			List<String> taskIds = new ArrayList<String>();
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super
					.getSessionUserId());
			user.setToken(super.getUserSessionBean().getCertificate());
			for (int i = 0; i < taskIdsJSON.size(); i++) {
				String taskId = taskIdsJSON.getString(i);
				List<String> taskUserIds = bpmTaskServer
						.getTaskBpmUserIds(taskId);
				// 审批人中没有当前用户
				if (taskUserIds == null || !taskUserIds.contains(user.getId())) {
					if (!bpmTaskDelegateServer.hasDelegatePermission(taskId,
							getSessionUserId())) {
						return SToolUtils.convertResultJSONObj(ERROR_STATUS,
								"没权限操作", 0, null).toString();
					}
				}
				taskIds.add(taskId);
			}
			List<ActBpmTaskConfigEntity_HI_RO> taskConfigs = bpmTaskConfigServer
					.findTaskConfigByTaskId(taskIds);
			if (taskConfigs != null && !taskConfigs.isEmpty()) {
				for (ActBpmTaskConfigEntity_HI_RO taskConfig : taskConfigs) {
					if (taskConfig.getEditStatus() != null
							&& taskConfig.getEditStatus() == 1) {
						return SToolUtils.convertResultJSONObj(ERROR_STATUS,
								"有业务数据需填写", 0, null).toString();
					}
				}
			}
			// 流程表单（审批选项，审批意见）
			@SuppressWarnings("unchecked")
			Map<String, String> properties = paramJSON
					.containsKey("properties") ? JSON.toJavaObject(
					paramJSON.getJSONObject("properties"), Map.class)
					: new HashMap<String, String>();

			String varialbesStr = paramJSON.getString("variables");
			Map<String, Object> variables = StringUtils.isBlank(varialbesStr) ? new HashMap<String, Object>()
					: bpmProcessServer.jsonToVariables(varialbesStr);
			bpmTaskServer.batchComplete(taskIds, variables, properties, user);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 流程撤回，当taskId不为空审批撤回，taskId为空提交撤回
	 * 
	 * @author laoqunzhao 2018-06-19
	 * @param params
	 *            { taskId : 任务ID, processInstanceId : 流程实例ID,
	 *            processDefinitionKey：流程标识, businessKey：业务主键， properties: {字段 :
	 *            字段值},流程表单字段 variables : [流程变量 { name : 变量名称, type :
	 *            变量类型long/double/boolean/date/string, value : 变量值 }] }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "revoke")
	public String revoke(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			String taskId = paramsJSON.getString("taskId");
			String processInstanceId = paramsJSON
					.getString("processInstanceId");
			if (StringUtils.isBlank(processInstanceId)
					&& StringUtils.isBlank(taskId)) {
				ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON,
						super.getSessionUserId());
				if (bpmList == null) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"申请单不存在！", 0, null).toString();
				} else if (StringUtils.isBlank(bpmList.getProcessInstanceId())) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"流程未发起！", 0, null).toString();
				} else {
					processInstanceId = bpmList.getProcessInstanceId();
				}
			}
			String varialbesStr = paramsJSON.getString("variables");
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super
					.getSessionUserId());
			user.setToken(super.getUserSessionBean().getCertificate());
			// 流程变量
			Map<String, Object> variables = StringUtils.isBlank(varialbesStr) ? new HashMap<String, Object>()
					: bpmProcessServer.jsonToVariables(varialbesStr);
			// 流程表单（审批选项，审批意见）
			@SuppressWarnings("unchecked")
			Map<String, String> properties = paramsJSON
					.containsKey("properties") ? JSON.toJavaObject(
					paramsJSON.getJSONObject("properties"), Map.class)
					: new HashMap<String, String>();
			String result = bpmTaskServer.revoke(processInstanceId, taskId,
					variables, properties, user);
			if (SUCCESS_STATUS.equals(result)) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
						null).toString();
			} else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, result, 0,
						null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 流程撤回，当taskId不为空审批撤回，taskId为空提交撤回;带原因
	 * 
	 * @author laoqunzhao 2018-06-19
	 * @param params
	 *            { taskId : 任务ID, processInstanceId : 流程实例ID,
	 *            processDefinitionKey：流程标识, businessKey：业务主键， properties: {字段 :
	 *            字段值},流程表单字段 variables : [流程变量 { name : 变量名称, type :
	 *            变量类型long/double/boolean/date/string, value : 变量值 }] }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "revoke_new")
	public String revokeNew(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			String taskId = paramsJSON.getString("taskId");
			String reason = paramsJSON.getString("reason");
			String processInstanceId = paramsJSON
					.getString("processInstanceId");
			if (StringUtils.isBlank(processInstanceId)
					&& StringUtils.isBlank(taskId)) {
				ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON,
						super.getSessionUserId());
				if (bpmList == null) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"申请单不存在！", 0, null).toString();
				} else if (StringUtils.isBlank(bpmList.getProcessInstanceId())) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"流程未发起！", 0, null).toString();
				} else {
					processInstanceId = bpmList.getProcessInstanceId();
				}
			}
			String varialbesStr = paramsJSON.getString("variables");
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super
					.getSessionUserId());
			user.setToken(super.getUserSessionBean().getCertificate());
			// 流程变量
			Map<String, Object> variables = StringUtils.isBlank(varialbesStr) ? new HashMap<String, Object>()
					: bpmProcessServer.jsonToVariables(varialbesStr);
			// 流程表单（审批选项，审批意见）
			@SuppressWarnings("unchecked")
			Map<String, String> properties = paramsJSON
					.containsKey("properties") ? JSON.toJavaObject(
					paramsJSON.getJSONObject("properties"), Map.class)
					: new HashMap<String, String>();
			String result = bpmTaskServer.revoke(processInstanceId, taskId,
					variables, properties, user);
			if (SUCCESS_STATUS.equals(result)) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
						null).toString();
			} else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, result, 0,
						null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 任务锁定/流程撤回锁定
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "lock")
	public String lock(@RequestParam String params) {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			String taskId = paramJSON.getString("taskId");
			String processInstanceId = paramJSON.getString("processInstanceId");
			// 任务锁定
			String result = null;
			if (StringUtils.isNotBlank(taskId)) {
				ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super
						.getSessionUserId());
				result = bpmTaskServer.lock(taskId, user.getId());
			} else {
				result = bpmTaskServer.lock(processInstanceId);
			}
			if (SUCCESS_STATUS.equals(result)) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
						null).toString();
			} else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, result, 0,
						null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败\n" + e,
					0, null).toString();
		}
	}

	/**
	 * 获取撤回状态
	 * 
	 * @param params
	 *            { processInstanceId:流程实例ID, taskId: 任务ID }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getRevokeStatus")
	public String getRevokeStatus(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			if (StringUtils.isBlank(paramsJSON.getString("taskId"))) {
				ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON,
						super.getSessionUserId());
				Assert.notNull(bpmList, "申请单查询失败！");
				paramsJSON.put("processInstanceId",
						bpmList.getProcessInstanceId());
			}
			boolean result = bpmTaskServer.getRevokeStatus(
					paramsJSON.getString("processInstanceId"),
					paramsJSON.getString("taskId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					result ? "1" : "0").toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// /**
	// * 任务签收
	// * @param params
	// * @return
	// */
	// @RequestMapping(method= RequestMethod.POST,value="claim")
	// public String claim(@RequestParam String params) {
	//
	// try {
	// JSONObject paramJSON = JSON.parseObject(params);
	// String taskId = paramJSON.getString("taskId");
	// taskService.claim(taskId, String.valueOf(super.getSessionUserId()));
	// return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
	// null).toString();
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage(), e);
	// return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败\n" + e, 0,
	// null).toString();
	// }
	// }
	//
	// /**
	// * 取消任务签收
	// * @param params
	// * @return
	// */
	// @RequestMapping(method= RequestMethod.POST,value="unclaim")
	// public String unclaimTask(@RequestParam String params) {
	//
	// try {
	// JSONObject paramJSON = JSON.parseObject(params);
	// String taskId = paramJSON.getString("taskId");
	// List<IdentityLink> identityLinks =
	// taskService.getIdentityLinksForTask(taskId);
	// if(null != identityLinks && !identityLinks.isEmpty()){
	// boolean assignee = true;
	// for(IdentityLink identityLink:identityLinks){
	// if(null != identityLink && StringUtils.equals(identityLink.getType(),
	// "candidate")){
	// assignee = false;
	// break;
	// }
	// }
	// if(assignee){
	// return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败：非签收任务不可取消签收",
	// 0, null).toString();
	// }
	// }
	// taskService.unclaim(taskId);
	// return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
	// null).toString();
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage(), e);
	// return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败\n" + e, 0,
	// null).toString();
	// }
	// }

	/**
	 * 获取活动的任务节点
	 * 
	 * @param params
	 *            { listId 申请单ID processInstanceId 流程实例ID processDefinitionKey
	 *            流程KEY businessKey 业务主键 ouId 事业部ID responsibilityId 职责ID }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findActiveTasks")
	public String findActiveTasks(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			String processInstanceId = paramsJSON
					.getString("processInstanceId");
			if (StringUtils.isBlank(processInstanceId)) {
				ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON,
						super.getSessionUserId());
				if (bpmList == null) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"申请单不存在！", 0, null).toString();
				} else if (StringUtils.isBlank(bpmList.getProcessInstanceId())) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"流程未发起！", 0, null).toString();
				} else {
					processInstanceId = bpmList.getProcessInstanceId();
				}
			}
			JSONArray result = new JSONArray();
			List<Task> tasks = bpmTaskServer.findActiveTasks(processInstanceId);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					JSONObject data = new JSONObject();
					data.put("taskId", task.getId());
					data.put("taskName", task.getName());
					data.put("createTime",
							ConvertUtil.dateToString(task.getCreateTime()));
					data.put("assignee", task.getAssignee());
					data.put("taskDefinitionId", task.getTaskDefinitionKey());
					data.put("processDefinitionId",
							task.getProcessDefinitionId());
					data.put("processInstanceId", task.getProcessInstanceId());
					// 委托代办
					List<Integer> delegateUserIds = new ArrayList<Integer>();
					List<ActBpmTaskDelegateEntity_HI> delegates = bpmTaskDelegateServer
							.findByTaskId(task.getId());
					if (delegates != null && !delegates.isEmpty()) {
						for (ActBpmTaskDelegateEntity_HI delegate : delegates) {
							if ((delegate.getDeleteFlag() == 0 || delegate
									.getDeleteFlag() == 0)
									&& StringUtils
											.equals(delegate.getStatus(),
													WorkflowConstant.DELEGATE_STATUS_PENDING)
									&& !delegateUserIds.contains(delegate
											.getDelegateUserId())) {
								delegateUserIds.add(delegate
										.getDelegateUserId());
							}
						}
					}
					JSONArray usersJSON = new JSONArray();
					// 取办理人
					if (StringUtils.isNotBlank(task.getAssignee())) {
						ActIdUserEntity_RO user = bpmUserServer
								.findUserByBpmId(task.getAssignee());
						if (user != null) {
							if (delegateUserIds.contains(user.getUserId())) {
								delegateUserIds.remove(user.getUserId());
							}
							JSONObject userJSON = new JSONObject();
							userJSON.put("userId", user.getUserId());
							userJSON.put("personId", user.getPersonId());
							userJSON.put("userName", user.getUserName());
							userJSON.put("userFullName", user.getUserFullName());
							userJSON.put("type", "assignee");
							usersJSON.add(userJSON);
						}
					} else {
						List<String> bpmUserIds = bpmTaskServer
								.getTaskBpmUserIds(task.getId());
						List<ActIdUserEntity_RO> users = bpmUserServer
								.findUsersByBpmIds(bpmUserIds);
						if (users != null && !users.isEmpty()) {
							for (ActIdUserEntity_RO user : users) {
								JSONObject userJSON = new JSONObject();
								userJSON.put("userId", user.getUserId());
								userJSON.put("personId", user.getPersonId());
								userJSON.put("userName", user.getUserName());
								userJSON.put("userFullName",
										user.getUserFullName());
								userJSON.put("type", "assignee");
								usersJSON.add(userJSON);
								if (delegateUserIds.contains(user.getUserId())) {
									delegateUserIds.remove(user.getUserId());
								}
							}
						}
					}
					// 委托办理人
					if (delegateUserIds != null && !delegateUserIds.isEmpty()) {
						List<ActIdUserEntity_RO> users = bpmUserServer
								.findUsersBySysIds(delegateUserIds);
						if (users != null && !users.isEmpty()) {
							for (ActIdUserEntity_RO user : users) {
								JSONObject userJSON = new JSONObject();
								userJSON.put("userId", user.getUserId());
								userJSON.put("personId", user.getPersonId());
								userJSON.put("userName", user.getUserName());
								userJSON.put("userFullName",
										user.getUserFullName());
								userJSON.put("type", "delegate");
								usersJSON.add(userJSON);
							}
						}
					}
					data.put("processers", usersJSON);
					result.add(data);
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.size(), result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 获取的任务详情
	 * 
	 * @param params
	 *            { taskId 任务ID processDefinitionKey 流程key，取第一个任务节点 ouId ouID
	 *            orgId ouID responsibilityId 职责ID }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "get")
	public String get(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			String taskId = paramsJSON.getString("taskId");
			String processDefinitionKey = paramsJSON
					.getString("processDefinitionKey");
			JSONObject result = new JSONObject();
			if (StringUtils.isNotBlank(taskId)) {
				ActBpmTaskEntity_HI_RO task = bpmTaskServer
						.getBpmTaskById(taskId);
				Assert.notNull(task, "任务不存在！");
				String taskDefinitionId = task.getTaskDefinitionId();
				String processDefinitionId = task.getProcessDefinitionId();
				processDefinitionKey = task.getProcessDefinitionKey();
				if (StringUtils.isBlank(taskDefinitionId)
						&& StringUtils.isNotBlank(task.getParentTaskId())) {
					// 子任务时查询流程定义的任务
					Task parentTask = bpmTaskServer.getTopParentTask(task
							.getParentTaskId());
					taskDefinitionId = parentTask == null ? null : parentTask
							.getTaskDefinitionKey();
				}
				bpmTaskServer.lock(taskId,
						bpmUserServer.getBpmUserId(super.getUserSessionBean()));
				ActBpmTaskConfigEntity_HI firstTaskConfig = bpmTaskConfigServer
						.findFirstTaskConfigByDef(processDefinitionId);
				result.put("taskId", task.getTaskId());
				result.put("name", task.getTaskName());
				result.put("createTime", new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()));
				result.put("assignee", task.getAssignee());
				result.put("taskDefinitionId", taskDefinitionId);
				result.put("processDefinitionId", task.getProcessDefinitionId());
				result.put("processInstanceId", task.getProcessInstanceId());
				result.put(
						"isStart",
						firstTaskConfig != null
								&& StringUtils.equals(
										firstTaskConfig.getTaskDefinitionId(),
										task.getTaskDefinitionId()));
				// 查询流程设置
				ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer
						.findTaskConfig(processDefinitionKey, taskDefinitionId);
				Assert.notNull(taskConfig, "查询流程定义失败！");
				result.put("variables", taskConfig.getEmptyVariablesJSON());
				result.put("editStatus", taskConfig.getEditStatus());
				// 查询驳回重审状态
				boolean isRetrial = bpmHistoryServer.getRetrialStatus(task
						.getProcessInstanceId());
				if (isRetrial) {
					TaskEntity tmpTask = new TaskEntity();
					tmpTask.setId(task.getTaskId());
					tmpTask.setTaskDefinitionKey(task.getTaskDefinitionId());
					tmpTask.setParentTaskId(task.getParentTaskId());
					result.put("taskNodes",
							bpmHistoryServer.getRetrialTaskNodes(tmpTask, 2));
				}
				result.put("isRetrial", isRetrial);
			} else if (StringUtils.isNotBlank(processDefinitionKey)) {
				// 新发起
				Integer ouId = bpmUserServer.getOuId(paramsJSON,
						super.getSessionUserId());
				// BU不为空，尝试根据BU获取流程定义
				if (ouId != null) {
					ActBpmModelEntity_HI_RO model = bpmModelServer
							.findByProcessKeyAndOuId(processDefinitionKey, ouId);
					if (model != null) {
						processDefinitionKey = model.getKey();
					}
				}
				ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer
						.findFirstTaskConfig(processDefinitionKey);
				Assert.notNull(taskConfig, "查询流程定义失败！");
				result.put("name", taskConfig.getTaskName());
				result.put("taskDefinitionId", taskConfig.getTaskDefinitionId());
				result.put("processDefinitionId",
						taskConfig.getProcessDefinitionId());
				result.put("isRetrial", false);
				result.put("variables", taskConfig.getEmptyVariablesJSON());
				result.put("editStatus", taskConfig.getEditStatus());
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 获取任务节点有权限的用户
	 * 
	 * @author laoqunzhao 2018-05-29
	 * @param params
	 *            { taskId 任务ID taskDefinitionId 任务节点ID listId 申请单ID
	 *            processInstanceId 流程实例ID processDefinitionKey 流程KEY
	 *            businessKey 业务主键 ouId 事业部ID responsibilityId 职责ID
	 *            includeDelegate 查询委托办理人 true.是 false.否，仅参数taskId不为空时有效
	 *            searchKey 关键字检索 }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTaskUsers")
	public String findTaskUsers(
			@RequestParam String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			String taskId = paramsJSON.getString("taskId");
			String taskDefinitionId = paramsJSON.getString("taskDefinitionId");
			String processDefinitionKey = paramsJSON
					.getString("processDefinitionKey");
			String searchKey = paramsJSON.getString("searchKey");
			String processDefinitionId = null;
			List<String> userIds = null;
			if (StringUtils.isNotBlank(taskId)) {
				userIds = bpmTaskServer.getTaskBpmUserIds(taskId);
				if (userIds == null) {
					userIds = new ArrayList<String>();
				}
				if (paramsJSON.containsKey("includeDelegate")
						&& "true".equals(paramsJSON
								.getString("includeDelegate"))) {
					List<ActBpmTaskDelegateEntity_HI> delegates = bpmTaskDelegateServer
							.findByTaskId(taskId);
					if (delegates != null && !delegates.isEmpty()) {
						List<Integer> userIds_ = new ArrayList<Integer>();
						for (ActBpmTaskDelegateEntity_HI delegate : delegates) {
							userIds_.add(delegate.getDelegateUserId());
						}
						List<ActIdUserEntity_RO> users = bpmUserServer
								.findUsersBySysIds(userIds_);
						if (users != null && !users.isEmpty()) {
							for (ActIdUserEntity_RO user : users) {
								if (!userIds.contains(user.getId())) {
									userIds.add(user.getId());
								}
							}
						}
					}
				}
			} else {
				ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON,
						super.getSessionUserId());
				// 已保存草稿或已发起
				if (bpmList != null) {
					processDefinitionKey = bpmList.getProcessDefinitionKey();
					if (StringUtils.isNotBlank(bpmList.getProcessInstanceId())
							&& StringUtils.isNotBlank(bpmList
									.getProcessDefinitionId())) {
						processDefinitionId = bpmList.getProcessDefinitionId();
					}
				} else if (StringUtils.isNotBlank(processDefinitionKey)) {
					// 新发起获取BU，如有只查询当前BU下的数据
					Integer ouId = bpmUserServer.getOuId(paramsJSON,
							super.getSessionUserId());
					// BU不为空，尝试根据BU获取流程定义
					if (ouId != null) {
						ActBpmModelEntity_HI_RO model = bpmModelServer
								.findByProcessKeyAndOuId(processDefinitionKey,
										ouId);
						if (model != null) {
							processDefinitionKey = model.getKey();
						}
					}
				}
				Assert.isTrue(StringUtils.isNotBlank(processDefinitionKey),
						"获取流程定义失败！");
				if (StringUtils.isBlank(processDefinitionId)) {
					ProcessDefinition processDefinition = bpmProcessServer
							.findLatestRunningProcess(processDefinitionKey);
					Assert.notNull(processDefinition, "获取流程定义失败！");
					processDefinitionId = processDefinition.getId();
				}
				List<Object> userIds_ = bpmTaskServer.getTaskBpmUserIds(
						bpmList == null ? null : bpmList.getListId(),
						processDefinitionId, taskDefinitionId);
				if (userIds_ != null && !userIds_.isEmpty()) {
					userIds = new ArrayList<String>();
					for (Object userId : userIds_) {
						if (userId instanceof String) {
							userIds.add(userId.toString());
						}
					}
				}
			}
			Pagination<ActIdUserEntity_RO> pagination = new Pagination<ActIdUserEntity_RO>(
					pageIndex, pageRows);
			// 不查询
			if (StringUtils.isBlank(searchKey)) {
				pagination.setCount(userIds == null ? 0 : userIds.size());
				List<String> curPageUserIds = null;
				if (userIds != null && !userIds.isEmpty()) {
					// 模拟分页
					if (pageRows != null && pageRows > 0) {
						if (userIds.size() > ((pageIndex - 1) * pageRows)) {
							curPageUserIds = userIds
									.subList((pageIndex - 1) * pageRows,
											userIds.size() > pageIndex
													* pageRows ? pageIndex
													* pageRows : userIds.size());
						}
					} else {
						// 不分页
						curPageUserIds = userIds;
					}
				}
				if (curPageUserIds != null && !curPageUserIds.isEmpty()) {
					List<ActIdUserEntity_RO> users = bpmUserServer
							.findUsersByBpmIds(curPageUserIds);
					pagination.setData(users);
				}
			} else {
				// 根据用户名、姓名筛选
				List<ActIdUserEntity_RO> users = bpmUserServer
						.findUsersByBpmIds(userIds);
				if (users != null && !users.isEmpty()) {
					for (int i = users.size() - 1; i >= 0; i--) {
						ActIdUserEntity_RO user = users.get(i);
						if (!(user.getUserFullName() != null && user
								.getUserFullName().contains(searchKey))
								&& !(user.getUserName() != null && user
										.getUserName().contains(searchKey))) {
							users.remove(i);
						}
					}
				}
				pagination.setData(users);
				pagination.setCount(users == null ? 0 : users.size());
			}
			if (pagination.getData() == null) {
				pagination.setData(new ArrayList<ActIdUserEntity_RO>());
			}
			JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
			result.put(STATUS, SUCCESS_STATUS);
			result.put(MSG, "成功");
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败", 0, null)
					.toString();
		}
	}

	/**
	 * 添加助审任务
	 * 
	 * @author laoqunzhao 2018-06-19
	 * @param params
	 *            { taskId 当前任务ID taskName 新任务名称 description 任务描述 userId 办理人ID }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "addSubTask")
	public String addSubTask(@RequestParam String params) {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			String parentTaskId = paramJSON.getString("taskId");
			// String taskName = paramJSON.getString("taskName");
			String description = paramJSON.getString("description");
			String userId = paramJSON.getString("userId");
			/*
			 * if(StringUtils.isBlank(taskName)) { return
			 * SToolUtils.convertResultJSONObj(ERROR_STATUS, "任务名称不能为空", 0,
			 * null).toString(); }
			 */
			if (StringUtils.isBlank(userId)) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "办理人不存在！",
						0, null).toString();
			}
			Task parentTask = bpmTaskServer.get(parentTaskId);
			if (parentTask == null) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "任务不存在！",
						0, null).toString();
			}
			List<String> taskUserIds = bpmTaskServer
					.getTaskBpmUserIds(parentTaskId);
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super
					.getSessionUserId());
			// 审批人中没有当前用户
			if (taskUserIds == null || !taskUserIds.contains(user.getId())) {
				if (!bpmTaskDelegateServer.hasDelegatePermission(parentTaskId,
						getSessionUserId())) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"没权限操作！", 0, null).toString();
				}
			}
			if (Integer.parseInt(userId) == super.getSessionUserId().intValue()) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,
						"助审人不能为自己！", 0, null).toString();
			}
			String result = bpmTaskServer.addSubTask(parentTaskId,
					parentTask.getName(), description,
					super.getSessionUserId(), Integer.parseInt(userId));
			if (SUCCESS_STATUS.equals(result)) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
						null).toString();
			} else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, result, 0,
						null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 任务转办
	 * 
	 * @param params
	 *            { taskId 任务ID taskIds 任务ID[] userId 新办理人ID }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "transfer")
	public String transfer(@RequestParam String params) {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			String taskId = paramJSON.getString("taskId");
			String taskIds = paramJSON.getString("taskIds");
			String userId = paramJSON.getString("userId");
			if (StringUtils.isBlank(taskId) && StringUtils.isBlank(taskIds)
					|| StringUtils.isBlank(userId)) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数", 0,
						null).toString();
			}
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(Integer
					.parseInt(userId));
			if (user == null) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "转办人不存在",
						0, null).toString();
			}
			List<String> taskIdsList = new ArrayList<>();
			if (StringUtils.isNotBlank(taskId)) {
				taskIdsList.add(taskId);
			}
			if (StringUtils.isNotBlank(taskIds)) {
				JSONArray taskIdsJSON = paramJSON.getJSONArray("taskIds");
				if (taskIdsJSON != null && !taskIdsJSON.isEmpty()) {
					for (int i = 0; i < taskIdsJSON.size(); i++) {
						String taskId_ = taskIdsJSON.getString(i);
						if (StringUtils.isNotBlank(taskId_)
								&& !taskIdsList.contains(taskId_)) {
							taskIdsList.add(taskId_);
						}
					}
				}
			}
			bpmTaskServer.transfer(taskIdsList, user.getId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "test")
	public void test(@RequestParam String params) {
		JSONObject jsonObject = new JSONObject();
		String s = "{\"msgCfgId\":\"13\",\"synchro\":\"Y\",\"password\":\"\",\"bizType\":\"EMAIL\",\"sendTo\":\"471632507@qq.com\",\"msgSubject\":\"条款框架审批1235 请审批\",\"requestId\":\"5b93805eeb1441498c7edb997d2dd2c9\",\"bizId\":\"a076971a-b28d-11e9-ac2b-12c0906067a3\",\"synchro\":\"Y\",\"templateValue\":{\"ProcessName\":\"条款框架\",\"StartUser\":\"超级管理员\",\"TaskName\":\"GM审批\",\"StartTime\":\"2019-07-30 13:48:18\",\"Title\":\"条款框架审批1235\",\"TaskTime\":\"2019-07-30 13:48:30\",\"ProcessKey\":\"TTA_APP.-999\"},\"userName\":\"OAUser261\"}";
		Object parse = JSON.parse(s);
		System.out.println(parse);
		JSONObject jsonObjectw = new JSONObject();
		jsonObject.put("params", parse);

		// JSONObject jsonObject1 =
		// SaafToolUtils.preaseServiceResultJSON("http://1010-saaf-message-server/api/messageServer/messageService/sendMessage",
		// jsonObject);
	/*	JSONObject jsonObject1 = SaafToolUtils
				.preaseServiceResultJSON(
						"http://1002-SAAF-API-GATEWAY/api/messageServer/messageService/sendMessage",
						jsonObject);
		System.out.println(jsonObject1.toJSONString());*/
	}

	public static void main(String[] args) {
		String s = "{\"msgCfgId\":\"13\",\"synchro\":\"Y\",\"password\":\"\",\"bizType\":\"EMAIL\",\"sendTo\":\"471632507@qq.com\",\"msgSubject\":\"条款框架审批1235 请审批\",\"requestId\":\"5b93805eeb1441498c7edb997d2dd2c9\",\"bizId\":\"a076971a-b28d-11e9-ac2b-12c0906067a3\",\"synchro\":\"Y\",\"templateValue\":{\"ProcessName\":\"条款框架\",\"StartUser\":\"超级管理员\",\"TaskName\":\"GM审批\",\"StartTime\":\"2019-07-30 13:48:18\",\"Title\":\"条款框架审批1235\",\"TaskTime\":\"2019-07-30 13:48:30\",\"ProcessKey\":\"TTA_APP.-999\"},\"userName\":\"OAUser261\"}";
		Object parse = JSON.parse(s);
		System.out.println(parse);
	}
}
