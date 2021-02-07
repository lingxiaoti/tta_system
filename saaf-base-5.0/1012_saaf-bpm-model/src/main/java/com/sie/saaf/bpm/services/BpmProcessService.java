package com.sie.saaf.bpm.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.image.MyProcessDiagramGenerator;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmListEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActUserTaskEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmHistory;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmPermission;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmTask;
import com.sie.saaf.bpm.model.inter.IActBpmTaskConfig;
import com.sie.saaf.bpm.model.inter.IActBpmTaskDelegate;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.bpm.utils.VerifyUtil;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/bpmProcessService")
public class BpmProcessService extends CommonAbstractService {

	private final Logger LOGGER = LoggerFactory
			.getLogger(BpmProcessService.class);

	@Autowired
	ProcessEngineFactoryBean processEngine;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private IActBpmModel bpmModelServer;

	@Autowired
	private IActBpmTask bpmTaskServer;

	@Autowired
	private IActBpmProcess bpmProcessServer;

	@Autowired
	private IActBpmList bpmListServer;

	@Autowired
	private IActBpmTaskConfig bpmTaskConfigServer;

	@Autowired
	private IActBpmHistory bpmHistoryServer;

	@Autowired
	private IActBpmUser bpmUserServer;

	// @Autowired
	// private IBpmCallBack bpmCallBackServer;

	@Autowired
	private IActBpmPermission bpmPermissionServer;

	// @Autowired
	// private IBaseAccreditCache baseAssreditServer;

	// @Autowired
	// private IActBpmTaskLeavel bpmTaskLeavelServer;

	@Autowired
	private IActBpmTaskDelegate bpmTaskDelegateServer;

	/**
	 * 流程定义列表
	 * 
	 * @param params
	 *            JSONObject processDefinitionKey 流程定义KEY categoryId 流程分类ID
	 *            searchKey 流程定义KEY/流程名称
	 * @param pageIndex
	 *            页码索引
	 * @param pageRows
	 *            每页记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findDefinitions")
	public String findDefinitions(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramJSON = this.parseObject(params);
			Pagination<ProcessDefinition> pagination = bpmProcessServer
					.findDefinitions(paramJSON, pageIndex, pageRows);
			// 将结果集转换为JSONArray格式
			JSONArray array = bpmProcessServer.definitionsToJsonArray(
					pagination.getData(), true);
			pagination.setData(null);
			JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
			result.put(DATA, array);
			result.put(STATUS, SUCCESS_STATUS);
			result.put(MSG, "成功");
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败", 0,
					null).toString();
		}
	}

	/**
	 * 流程定义列表
	 * 
	 * @param params
	 *            JSONObject key 流程定义KEY
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findByKey")
	public String findByKey(@RequestParam String params) {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			List<ProcessDefinition> definitions = bpmProcessServer
					.findDefinitions(paramJSON
							.getString("processDefinitionKey"));
			// 将结果集转换为JSONArray格式
			JSONArray array = bpmProcessServer.definitionsToJsonArray(
					definitions, true);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					null == array ? 0 : array.size(), array).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败", 0,
					null).toString();
		}
	}

	/**
	 * 删除流程定义
	 * 
	 * @param params
	 *            JSONObject deploymentId 流程部署ID
	 * @return
	 */
	@Permission(menuCode = "LCSJ")
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam String params) {
		String deploymentId = null;
		try {
			JSONObject paramJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramJSON, "deploymentId");
			deploymentId = paramJSON.getString("deploymentId");
			List<ProcessDefinition> processDefinitions = repositoryService
					.createProcessDefinitionQuery().deploymentId(deploymentId)
					.list();
			Assert.notEmpty(processDefinitions, "流程版本不存在！");
			UserSessionBean user = getUserSessionBean();
			// 非管理员判断数据权限
			if (!"Y".equals(user.getIsadmin())) {
				Integer ouId = getOrgId();
				List<Integer> ouIds = new ArrayList<Integer>();
				if (ouId != null) {
					ouIds.add(ouId);
				}
				if (!bpmPermissionServer.hasPermission(processDefinitions
						.get(0).getKey(), ouIds)) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"没权限操作", 0, null).toString();
				}
			}
			long count = historyService.createHistoricProcessInstanceQuery()
					.deploymentId(deploymentId).count();
			if (count > 0) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,
						"删除失败：流程已使用不可删除", 0, null).toString();
			}
			repositoryService.deleteDeployment(deploymentId, true);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 激活流程
	 * 
	 * @param params
	 *            JSONObject processDefinitionId 流程定义ID
	 */
	@Permission(menuCode = "LCSJ")
	@RequestMapping(method = RequestMethod.POST, value = "activate")
	public String activate(@RequestParam String params) {
		String processDefinitionId = null;
		try {
			JSONObject paramJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramJSON, "processDefinitionId");
			processDefinitionId = paramJSON.getString("processDefinitionId");
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			Assert.notNull(processDefinition, "流程版本不存在！");
			UserSessionBean user = getUserSessionBean();
			// 非管理员判断数据权限
			if (!"Y".equals(user.getIsadmin())) {
				Integer ouId = getOrgId();
				List<Integer> ouIds = new ArrayList<Integer>();
				if (ouId != null) {
					ouIds.add(ouId);
				}
				if (!bpmPermissionServer.hasPermission(
						processDefinition.getKey(), ouIds)) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"没权限操作", 0, null).toString();
				}
			}
			bpmProcessServer.activate(processDefinitionId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "流程激活失败！", 0,
					null).toString();
		}
	}

	/**
	 * 挂起流程
	 * 
	 * @param params
	 *            JSONObject processDefinitionId 流程定义ID
	 */
	@Permission(menuCode = "LCSJ")
	@RequestMapping(method = RequestMethod.POST, value = "suspend")
	public String suspend(@RequestParam String params) {
		String processDefinitionId = null;
		try {
			JSONObject paramJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramJSON, "processDefinitionId");
			processDefinitionId = paramJSON.getString("processDefinitionId");
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			Assert.notNull(processDefinition, "流程版本不存在！");
			UserSessionBean user = getUserSessionBean();
			// 非管理员判断数据权限
			if (!"Y".equals(user.getIsadmin())) {
				Integer ouId = getOrgId();
				List<Integer> ouIds = new ArrayList<Integer>();
				if (ouId != null) {
					ouIds.add(ouId);
				}
				if (!bpmPermissionServer.hasPermission(
						processDefinition.getKey(), ouIds)) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"没权限操作", 0, null).toString();
				}
			}
			bpmProcessServer.suspend(processDefinitionId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "流程挂起失败！", 0,
					null).toString();
		}
	}

	/**
	 * 获取Gooflow格式流程JSON 参数优先级processInstanceId->
	 * processDefinitionKey+businessKey -> processDefinitionId ->
	 * processDefinitionKey
	 * 
	 * @author laoqunzhao 2018-09-10
	 * @param params
	 *            { processInstanceId 流程实例ID processDefinitionId 流程定义ID
	 *            processDefinitionKey 流程定义Key businessKey 业务主键 ouId ouID
	 *            responsibilityId 职责ID }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getGooflowProcessJSON")
	public String getGooflowProcessJSON(@RequestParam String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			JSONObject result = new JSONObject();
			if (StringUtils.isNotBlank(paramsJSON
					.getString("processInstanceId"))) {
				result = bpmProcessServer.getGooflowProcessJSON(
						paramsJSON.getString("processInstanceId"), true, true);
			} else if (StringUtils.isNotBlank(paramsJSON
					.getString("processDefinitionKey"))
					&& StringUtils.isNotBlank(paramsJSON
							.getString("businessKey"))) {
				ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON,
						super.getSessionUserId());
				if (bpmList != null) {
					if (StringUtils.isNotBlank(bpmList.getProcessInstanceId())) {
						result = bpmProcessServer.getGooflowProcessJSON(
								bpmList.getProcessInstanceId(), true, true);
					} else {
						result = bpmModelServer.getGooflowModelJSON(bpmList
								.getProcessDefinitionKey());
					}
				} else {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"申请单不存在！", 0, null).toString();
				}
			} else if (StringUtils.isNotBlank(paramsJSON
					.getString("processDefinitionId"))) {
				result = bpmProcessServer.getGooflowProcessJSON(paramsJSON
						.getString("processDefinitionId"));
			} else if (StringUtils.isNotBlank(paramsJSON
					.getString("processDefinitionKey"))) {
				result = bpmModelServer.getGooflowModelJSON(paramsJSON
						.getString("processDefinitionKey"));
			} else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数！",
						0, null).toString();
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
	 * 导出流程图片
	 * 
	 * @param params
	 *            JSONObject processDefinitionId 流程定义ID processInstanceId 流程实例ID
	 */
	@RequestMapping(method = RequestMethod.GET, value = "export")
	public void export(@RequestParam String params) throws IOException {
		JSONObject paramJSON = JSON.parseObject(params);
		String processInstanceId = paramJSON.getString("processInstanceId");
		String resourceName = "";
		InputStream resourceAsStream = null;
		if (StringUtils.isNotBlank(processInstanceId)) {
			String processDefinitionId = null;
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			if (processInstance == null) {
				HistoricProcessInstance historicProcessInstance = historyService
						.createHistoricProcessInstanceQuery()
						.processInstanceId(processInstanceId).singleResult();
				processDefinitionId = historicProcessInstance
						.getProcessDefinitionId();
			} else {
				processDefinitionId = processInstance.getProcessDefinitionId();
			}
			BpmnModel bpmnModel = repositoryService
					.getBpmnModel(processDefinitionId);
			List<String> activeActivityIds = processInstance == null ? new ArrayList<String>()
					: runtimeService.getActiveActivityIds(processInstanceId);
			activeActivityIds = activeActivityIds == null ? new ArrayList<String>()
					: activeActivityIds;
			ProcessEngineConfiguration processEngineConfiguration = processEngine
					.getProcessEngineConfiguration();
			// ProcessDiagramGenerator diagramGenerator =
			// processEngineConfiguration.getProcessDiagramGenerator();
			List<HistoricActivityInstance> historicActivityInstances = bpmHistoryServer
					.findHistoricActivities(processInstanceId, false);
			List<String> lowLightedActivityIds = new ArrayList<String>();
			if (historicActivityInstances != null
					&& !historicActivityInstances.isEmpty()) {
				for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
					if (!activeActivityIds.contains(historicActivityInstance
							.getActivityId())) {
						lowLightedActivityIds.add(historicActivityInstance
								.getActivityId());
					}
				}
			}
			MyProcessDiagramGenerator diagramGenerator = new MyProcessDiagramGenerator();
			resourceAsStream = diagramGenerator.generateDiagramMy(bpmnModel,
					"png", activeActivityIds, lowLightedActivityIds,
					processEngineConfiguration.getActivityFontName(),
					processEngineConfiguration.getLabelFontName(),
					processEngineConfiguration.getAnnotationFontName(),
					processEngineConfiguration.getClassLoader(), 1.0);

		} else {
			ProcessDefinition processDefinition = null;
			String processDefinitionId = paramJSON
					.getString("processDefinitionId");
			if (StringUtils.isBlank(processDefinitionId)) {
				String processDefinitionKey = paramJSON
						.getString("processDefinitionKey");
				processDefinition = bpmProcessServer
						.findLatestRunningProcess(processDefinitionKey);
			} else {
				processDefinition = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(processDefinitionId)
						.singleResult();
			}
			// 处理异常
			if (null == processDefinition) {
				response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
				response.getOutputStream().println(
						"no main process, can't export");
				response.flushBuffer();
				return;
			}
			resourceName = processDefinition.getDiagramResourceName();
			resourceAsStream = repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(), resourceName);
		}

		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
		response.setContentType("image/png");
		response.flushBuffer();
	}

	/**
	 * 获取流程启动url表单
	 * 
	 * @param params
	 *            { listId 申请单ID processInstanceId 流程实例ID processDefinitionKey
	 *            流程KEY businessKey 业务主键 ouId 事业部ID responsibilityId 职责ID
	 *            urlNoneParams 是否在url后附加流程参数 true.是 false.否 isMobile 是否移动端
	 *            true.是 false.否 }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getStartUrl")
	public String getStartUrl(@RequestParam String params) {
		try {
			String url = null;
			String dataUrl = null;
			Integer editStatus = 0;
			JSONObject paramsJSON = JSON.parseObject(params);
			String processDefinitionId = null;
			String businessKey = paramsJSON.getString("businessKey");
			String processDefinitionKey = paramsJSON
					.getString("processDefinitionKey");
			// 为true时不再在url后附加参数
			boolean urlNoneParams = StringUtils.equals(
					paramsJSON.getString("urlNoneParams"), "true") ? true
					: false;
			boolean isMobile = StringUtils.equals(
					paramsJSON.getString("isMobile"), "true") ? true : false;
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
				// 新发起,获取BU，如有只查询当前BU下的数据
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
			}
			Assert.isTrue(StringUtils.isNotBlank(processDefinitionKey),
					"获取流程定义失败！");
			ActBpmTaskConfigEntity_HI taskConfig = StringUtils
					.isNotBlank(processDefinitionId) ? bpmTaskConfigServer
					.findFirstTaskConfigByDef(processDefinitionId)
					: bpmTaskConfigServer
							.findFirstTaskConfig(processDefinitionKey);
			if (taskConfig != null) {
				url = isMobile ? taskConfig.getMobformUrl() : taskConfig
						.getPcformUrl();
				dataUrl = isMobile ? taskConfig.getMobDataUrl() : taskConfig
						.getPcDataUrl();
				editStatus = taskConfig.getEditStatus();
				processDefinitionId = taskConfig.getProcessDefinitionId();
			}
			Map<String, Object> bpmparams = new HashMap<String, Object>();
			bpmparams.put("processDefinitionKey", processDefinitionKey);
			bpmparams.put("processDefinitionId", processDefinitionId);
			bpmparams.put("businessKey", businessKey);
			bpmparams.put("editStatus", editStatus);
			bpmparams.put("isStart", true);
			if (bpmList != null) {
				bpmparams.put("isStart", bpmList.getStatus() == -1 ? true
						: false);
				bpmparams.put("listId", bpmList.getListId());
				bpmparams.put("processInstanceId",
						bpmList.getProcessInstanceId());
				bpmparams.put("respId", bpmList.getResponsibilityId());
				bpmparams
						.put("responsibilityId", bpmList.getResponsibilityId());
				if (StringUtils.isNotBlank(bpmList.getBusinessKey())) {
					bpmparams.put("businessKey", bpmList.getBusinessKey());
				}
				if (bpmList.getStatus() == 0) {
					List<Task> tasks = bpmTaskServer.findActiveTasks(bpmList
							.getProcessInstanceId());
					// 申请
					if (tasks != null
							&& !tasks.isEmpty()
							&& taskConfig != null
							&& StringUtils.equals(tasks.get(0)
									.getTaskDefinitionKey(), taskConfig
									.getTaskDefinitionId())) {
						bpmparams.put("isStart", true);
						bpmparams.put("taskId", tasks.get(0).getId());
					}
				}
				// 菜单编码
				String menuCode = bpmHistoryServer.getMenuCode(bpmList
						.getProcessInstanceId());
				if (StringUtils.isBlank(menuCode)
						&& StringUtils.isNotBlank(bpmList.getProperties())) {
					try {
						JSONObject propertiesJSON = JSON.parseObject(bpmList
								.getProperties());
						menuCode = propertiesJSON.getString("menucode");
					} catch (Exception e) {

					}
				}
				bpmparams.put("menucode", menuCode);
				// 提交人
				bpmparams.put("commitUserId", bpmList.getCreatedBy());
			}
			if (StringUtils.isNotBlank(url) && !urlNoneParams) {
				url = bpmProcessServer.joinProcessParams(url, bpmparams);
			}
			JSONObject result = (JSONObject) JSON.toJSON(bpmparams);
			result.put("url", url);
			result.put("dataUrl", dataUrl);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					result).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils
					.convertResultJSONObj(ERROR_STATUS, "失败！", 0, null)
					.toString();
		}

	}

	/**
	 * 获取流程启动url表单
	 * 
	 * @param params
	 *            { listIds 申请单ID[] urlNoneParams 是否在url后附加流程参数 true.是 false.否
	 *            isMobile 是否移动端 true.是 false.否 }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getStartUrlByListIds")
	public String getStartUrlByListIds(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "listIds");
			// 为true时不再在url后附加参数
			boolean urlNoneParams = StringUtils.equals(
					paramsJSON.getString("urlNoneParams"), "true") ? true
					: false;
			boolean isMobile = StringUtils.equals(
					paramsJSON.getString("isMobile"), "true") ? true : false;
			List<Integer> listIds = JSON.toJavaObject(
					paramsJSON.getJSONArray("listIds"), List.class);
			List<ActBpmListEntity_HI_RO> bpmLists = bpmListServer
					.findByIds(listIds);
			JSONArray result = new JSONArray();
			for (ActBpmListEntity_HI_RO bpmList : bpmLists) {
				ActBpmTaskConfigEntity_HI taskConfig = null;
				if (bpmList.getStatus() != null
						&& (bpmList.getStatus() == 0 || bpmList.getStatus() == 1)
						&& StringUtils.isNotBlank(bpmList
								.getProcessDefinitionId())) {
					// 流程已启动，查询流程版本定义设置
					taskConfig = bpmTaskConfigServer
							.findFirstTaskConfigByDef(bpmList
									.getProcessDefinitionId());
				} else {
					// 查询最新可运行的流程设置
					taskConfig = bpmTaskConfigServer
							.findFirstTaskConfig(bpmList
									.getProcessDefinitionKey());
				}
				if (taskConfig == null) {
					continue;
				}
				String url = isMobile ? taskConfig.getMobformUrl() : taskConfig
						.getPcformUrl();
				String dataUrl = isMobile ? taskConfig.getMobDataUrl()
						: taskConfig.getPcDataUrl();
				Map<String, Object> bpmparams = new HashMap<String, Object>();
				bpmparams.put("processDefinitionKey",
						bpmList.getProcessDefinitionKey());
				bpmparams.put("processDefinitionId",
						bpmList.getProcessDefinitionId());
				bpmparams.put("isStart", bpmList.getStatus() == -1 ? true
						: false);
				bpmparams.put("listId", bpmList.getListId());
				bpmparams.put("processInstanceId",
						bpmList.getProcessInstanceId());
				bpmparams.put("respId", bpmList.getResponsibilityId());
				bpmparams
						.put("responsibilityId", bpmList.getResponsibilityId());
				bpmparams.put("businessKey", bpmList.getBusinessKey());
				bpmparams.put("editStatus", taskConfig.getEditStatus());
				bpmparams.put("isStart", true);
				if (bpmList.getStatus() == 0) {
					List<Task> tasks = bpmTaskServer.findActiveTasks(bpmList
							.getProcessInstanceId());
					// 申请
					if (tasks != null
							&& !tasks.isEmpty()
							&& taskConfig != null
							&& StringUtils.equals(tasks.get(0)
									.getTaskDefinitionKey(), taskConfig
									.getTaskDefinitionId())) {
						bpmparams.put("isStart", true);
						bpmparams.put("taskId", tasks.get(0).getId());
					}
				}
				// 菜单编码
				String menuCode = bpmHistoryServer.getMenuCode(bpmList
						.getProcessInstanceId());
				if (StringUtils.isBlank(menuCode)
						&& StringUtils.isNotBlank(bpmList.getProperties())) {
					try {
						JSONObject propertiesJSON = JSON.parseObject(bpmList
								.getProperties());
						menuCode = propertiesJSON.getString("menucode");
					} catch (Exception e) {

					}
				}
				bpmparams.put("menucode", menuCode);
				// 提交人
				bpmparams.put("commitUserId", bpmList.getCreatedBy());
				if (StringUtils.isNotBlank(url) && !urlNoneParams) {
					url = bpmProcessServer.joinProcessParams(url, bpmparams);
				}
				JSONObject bpmListJSON = (JSONObject) JSON.toJSON(bpmparams);
				bpmListJSON.put("url", url);
				bpmListJSON.put("dataUrl", dataUrl);
				result.add(bpmListJSON);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					result).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils
					.convertResultJSONObj(ERROR_STATUS, "失败！", 0, null)
					.toString();
		}

	}

	/**
	 * 获取流程任务url表单
	 * 
	 * @param { taskId 任务ID urlNoneParams 是否在url后附加流程参数 true.是 false.否 isMobile
	 *        是否移动端 true.是 false.否 }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getTaskUrl")
	public String getTaskUrl(@RequestParam String params) {
		try {
			// TimeUtil.start();
			// TimeUtil.record("start");
			JSONObject paramJSON = JSON.parseObject(params);
			String taskId = paramJSON.getString("taskId");
			// 为true时不再在url后附加参数
			boolean urlNoneParams = StringUtils.equals(
					paramJSON.getString("urlNoneParams"), "true") ? true
					: false;
			boolean isMobile = StringUtils.equals(
					paramJSON.getString("isMobile"), "true") ? true : false;
			// TimeUtil.record("task start");
			ActBpmHiTaskEntity_HI_RO task = bpmHistoryServer
					.getBpmHistoricTaskByTaskId(taskId);
			// TimeUtil.record("task end");
			Assert.notNull(task, "任务不存在!");
			String taskDefinitionId = task.getTaskDefinitionId();
			// TimeUtil.record("taskdefid start");
			if (StringUtils.isBlank(task.getTaskDefinitionId())) {
				HistoricTaskInstance topTask = bpmHistoryServer
						.getTopTaskByTaskId(task.getTaskId());
				if (topTask != null) {
					taskDefinitionId = topTask.getTaskDefinitionKey();
				}
			}
			// TimeUtil.record("taskdefid end");
			// TimeUtil.record("taskcfg start");
			ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer
					.findTaskConfig(task.getProcessDefinitionKey(),
							taskDefinitionId);
			// TimeUtil.record("taskcfg end");
			Assert.notNull(taskConfig, "查询流程设置失败!");
			// TimeUtil.record("ftaskcfg start");
			ActBpmTaskConfigEntity_HI firstTaskConfig = bpmTaskConfigServer
					.findFirstTaskConfigByDef(task.getProcessDefinitionId());
			// TimeUtil.record("ftaskcfg end");
			Assert.isTrue(StringUtils.isNotBlank(taskDefinitionId)
					|| firstTaskConfig != null, "查询流程设置失败!");
			String url = isMobile ? taskConfig.getMobformUrl() : taskConfig
					.getPcformUrl();
			String dataUrl = isMobile ? taskConfig.getMobDataUrl() : taskConfig
					.getPcDataUrl();
			Map<String, Object> bpmparams = new HashMap<String, Object>();
			bpmparams.put("taskId", task.getTaskId());
			bpmparams.put("processInstanceId", task.getProcessInstanceId());
			bpmparams.put("businessKey", task.getBpm_businessKey());
			bpmparams.put("taskDefinitionId", taskDefinitionId);
			bpmparams.put("processDefinitionId", task.getProcessDefinitionId());
			bpmparams.put("processDefinitionKey",
					task.getProcessDefinitionKey());
			bpmparams.put("listId", task.getBpm_listId());
			bpmparams.put("respId", task.getBpm_responsibilityId());
			bpmparams.put("responsibilityId", task.getBpm_responsibilityId());
			bpmparams.put("editStatus", taskConfig.getEditStatus());
			// 菜单编码
			// TimeUtil.record("menu start");
			String menuCode = bpmHistoryServer.getMenuCode(task
					.getProcessInstanceId());
			if (StringUtils.isBlank(menuCode)
					&& StringUtils.isNotBlank(task.getBpm_properties())) {
				try {
					JSONObject propertiesJSON = JSON.parseObject(task
							.getBpm_properties());
					menuCode = propertiesJSON.getString("menucode");
				} catch (Exception e) {

				}
			}
			bpmparams.put("menucode", menuCode);
			// TimeUtil.record("menu end");
			// 提交人
			bpmparams.put("commitUserId", task.getBpm_createdBy());
			bpmparams.put("editStatus", taskConfig.getEditStatus());
			bpmparams.put(
					"isStart",
					firstTaskConfig != null
							&& StringUtils.equals(
									firstTaskConfig.getTaskDefinitionId(),
									taskDefinitionId));
			if (StringUtils.isNotBlank(url) && !urlNoneParams) {
				url = bpmProcessServer.joinProcessParams(url, bpmparams);
			}
			JSONObject result = (JSONObject) JSON.toJSON(bpmparams);
			result.put("url", url);
			result.put("dataUrl", dataUrl);
			// TimeUtil.print();
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 获取流程任务url表单
	 * 
	 * @param { taskIds 任务ID[] urlNoneParams 是否在url后附加流程参数 true.是 false.否
	 *        isMobile 是否移动端 true.是 false.否 }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getTaskUrlByTaskIds")
	public String getTaskUrlByTaskIds(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "taskIds");
			JSONArray taskIdsJSON = paramsJSON.getJSONArray("taskIds");
			// 为true时不再在url后附加参数
			boolean urlNoneParams = StringUtils.equals(
					paramsJSON.getString("urlNoneParams"), "true") ? true
					: false;
			boolean isMobile = StringUtils.equals(
					paramsJSON.getString("isMobile"), "true") ? true : false;
			JSONArray result = new JSONArray();
			List<String> taskIds = new ArrayList<>();
			for (int i = 0; i < taskIdsJSON.size(); i++) {
				taskIds.add(taskIdsJSON.getString(i));
			}
			List<ActBpmHiTaskEntity_HI_RO> tasks = bpmHistoryServer
					.findBpmHistoricTasksByTaskIds(taskIds);
			if (tasks != null && !tasks.isEmpty()) {
				for (ActBpmHiTaskEntity_HI_RO task : tasks) {
					String taskDefinitionId = task.getTaskDefinitionId();
					if (StringUtils.isBlank(task.getTaskDefinitionId())) {
						HistoricTaskInstance topTask = bpmHistoryServer
								.getTopTaskByTaskId(task.getTaskId());
						if (topTask != null) {
							taskDefinitionId = topTask.getTaskDefinitionKey();
						}
					}
					ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer
							.findTaskConfig(task.getProcessDefinitionKey(),
									taskDefinitionId);
					if (taskConfig == null) {
						continue;
					}
					ActBpmTaskConfigEntity_HI firstTaskConfig = bpmTaskConfigServer
							.findFirstTaskConfigByDef(task
									.getProcessDefinitionId());
					String url = isMobile ? taskConfig.getMobformUrl()
							: taskConfig.getPcformUrl();
					String dataUrl = isMobile ? taskConfig.getMobDataUrl()
							: taskConfig.getPcDataUrl();
					Map<String, Object> bpmparams = new HashMap<String, Object>();
					bpmparams.put("taskId", task.getTaskId());
					bpmparams.put("processInstanceId",
							task.getProcessInstanceId());
					bpmparams.put("businessKey", task.getBpm_businessKey());
					bpmparams.put("taskDefinitionId", taskDefinitionId);
					bpmparams.put("processDefinitionId",
							task.getProcessDefinitionId());
					bpmparams.put("processDefinitionKey",
							task.getProcessDefinitionKey());
					bpmparams.put("listId", task.getBpm_listId());
					bpmparams.put("respId", task.getBpm_responsibilityId());
					bpmparams.put("responsibilityId",
							task.getBpm_responsibilityId());
					bpmparams.put("editStatus", taskConfig.getEditStatus());
					bpmparams.put(
							"isStart",
							firstTaskConfig != null
									&& StringUtils.equals(firstTaskConfig
											.getTaskDefinitionId(),
											taskDefinitionId));
					// 菜单编码
					String menuCode = bpmHistoryServer.getMenuCode(task
							.getProcessInstanceId());
					if (StringUtils.isBlank(menuCode)
							&& StringUtils.isNotBlank(task.getBpm_properties())) {
						try {
							JSONObject propertiesJSON = JSON.parseObject(task
									.getBpm_properties());
							menuCode = propertiesJSON.getString("menucode");
						} catch (Exception e) {

						}
					}
					bpmparams.put("menucode", menuCode);
					// 提交人
					bpmparams.put("commitUserId", task.getBpm_createdBy());
					if (StringUtils.isNotBlank(url) && !urlNoneParams) {
						url = bpmProcessServer
								.joinProcessParams(url, bpmparams);
					}
					JSONObject taskJSON = (JSONObject) JSON.toJSON(bpmparams);
					taskJSON.put("url", url);
					taskJSON.put("dataUrl", dataUrl);
					result.add(taskJSON);
				}
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
	 * 启动流程
	 * 
	 * @param params
	 *            { listId 申请单ID description 发起说明 businessKey 业务主键 billNo 单据号
	 *            title 流程标题 processDefinitionKey 流程定义KEY variables
	 *            流程发起业务变量JSONArray [{ name: 变量名称, type:
	 *            变量类型long/double/boolean/date/string, value: 变量值 }] properties
	 *            流程发起流程表单JSONObject {字段名称:字段内容，。。。} extend 流程发起扩展JSONObject {
	 *            var_tasks_assignees:{ taskDefinitionId:[userId] 指定任务办理人 } }
	 * 
	 *            saveonly : 是否只保存表单信息，true是（不提交任务），false否（提交任务）, positionId
	 *            职位ID ouId ouID responsibilityId 职责ID respId 职责ID applyPersonId
	 *            申请人ID applyPositionId 申请人职位ID userType 用户类型 roleType 类型
	 *            custAccountId 经销商ID departmentId 部门ID }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "start")
	public String start(@RequestParam String params) {
		JSONObject paramsJSON = null;
		ActIdUserEntity_RO applyUser = null;// 申请人
		ActIdUserEntity_RO startUser = null;// 提交人
		ActBpmListEntity_HI bpmList = null;
		// //////////////////////////////
		try {
			paramsJSON = this.parseObject(params);
			// 保证流程未启动状态标识正确
			paramsJSON.put("status", -1);
			// 申请为他人代替申请时，查询流程原发起人信息
			if (StringUtils.isNotBlank(paramsJSON.getString("applyPersonId"))) {
				applyUser = bpmUserServer.findUserByPositionIdAndPersonId(
						paramsJSON.get("applyPersonId"),
						paramsJSON.getInteger("applyPositionId"),
						paramsJSON.getInteger("departmentId"));
				startUser = bpmUserServer.findUserBySysId(super
						.getSessionUserId());
			} else {
				applyUser = bpmUserServer.findUserByPositionId(
						super.getSessionUserId(),
						paramsJSON.getInteger("positionId"),
						paramsJSON.getInteger("departmentId"), false);
				startUser = applyUser;
			}
			Assert.isTrue(startUser != null && applyUser != null, "申请人信息读取失败！");
			SaafToolUtils.validateJsonParms(paramsJSON, "processDefinitionKey",
					"businessKey");
			VerifyUtil.verifyJSON(paramsJSON, "properties.opinion", false, 200,
					"审批意见");
			String processDefinitionKey = paramsJSON
					.getString("processDefinitionKey");
			String businessKey = paramsJSON.getString("businessKey");
			if (StringUtils.isNotBlank(paramsJSON.getString("respId"))
					&& StringUtils.isBlank(paramsJSON
							.getString("responsibilityId"))) {
				paramsJSON.put("responsibilityId",
						paramsJSON.getString("respId"));
			}
			if (WorkflowConstant.USER_TYPE_GUIDE
					.equals(applyUser.getUserType())) {
				paramsJSON.put("userType", WorkflowConstant.USER_TYPE_GUIDE);
				paramsJSON.put("roleType", WorkflowConstant.ROLE_TYPE_GUIDE);
			}
			if (StringUtils.isBlank(paramsJSON.getString("roleType"))) {
				paramsJSON.put("roleType", WorkflowConstant.ROLE_TYPE_PERSON);
			}
			if (paramsJSON.containsKey("listId")) {
				bpmList = bpmListServer
						.getById(paramsJSON.getInteger("listId"));
			} else {
				if (bpmList == null
						&& StringUtils.isNotBlank(processDefinitionKey)
						&& StringUtils.isNotBlank(businessKey)) {
					// 获取BU，如有只查询当前BU下的数据
					Integer ouId = bpmUserServer.getOuId(paramsJSON,
							super.getSessionUserId());
					// BU不为空，尝试根据BU获取流程定义
					ActBpmModelEntity_HI_RO model = null;
					if (ouId != null) {
						model = bpmModelServer.findByProcessKeyAndOuId(
								processDefinitionKey, ouId);
						if (model != null) {
							processDefinitionKey = model.getKey();
							paramsJSON.put("processDefinitionKey",
									processDefinitionKey);
						}
					}
					if (model == null) {
						Model model1 = bpmModelServer
								.getByKey(processDefinitionKey);
						Assert.notNull(model1, "流程未定义！");
					}
					bpmList = bpmListServer.getByBusinessKey(
							processDefinitionKey, businessKey);
				}
			}
			String taskId = paramsJSON.getString("taskId");
			if (StringUtils.isNotBlank(taskId)) {
				return complete(paramsJSON, bpmList, startUser);
			} else if (bpmList != null && bpmList.getStatus() == 0) {
				List<Task> tasks = bpmTaskServer.findActiveTasks(bpmList
						.getProcessInstanceId());
				UserTask firstTask = bpmProcessServer.getFirstUserTask(bpmList
						.getProcessDefinitionId());
				// 申请
				if (tasks != null
						&& !tasks.isEmpty()
						&& StringUtils.equals(tasks.get(0)
								.getTaskDefinitionKey(), firstTask.getId())) {
					paramsJSON.put("taskId", tasks.get(0).getId());
					return complete(paramsJSON, bpmList, startUser);
				} else {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"流程已发起，不可重复提交！", 0, null).toString();
				}
			} else {
				bpmProcessServer.start(paramsJSON, startUser, applyUser);
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
						null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(
					ERROR_STATUS,
					StringUtils.isBlank(e.getMessage()) ? "流程发起失败！" : e
							.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 终止流程
	 * 
	 * @author sie-lqz 2018-07-07
	 * @param params
	 *            { listId 申请单ID processInstanceId 流程实例ID processDefinitionKey
	 *            businessKey reason 终止理由 }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "stop")
	public String stop(@RequestParam String params) {
		try {
			ActBpmListEntity_HI bpmList = null;
			JSONObject paramJSON = JSON.parseObject(params);
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super
					.getSessionUserId());
			if (StringUtils.isNotBlank(paramJSON.getString("listId"))) {
				bpmList = bpmListServer.getById(paramJSON.getInteger("listId"));
			}
			if (bpmList == null
					&& StringUtils.isNotBlank(paramJSON
							.getString("processInstanceId"))) {
				bpmList = bpmListServer.getByProcessInstanceId(paramJSON
						.getString("processInstanceId"));
			}
			if (bpmList == null
					&& StringUtils.isBlank(paramJSON
							.getString("processDefinitionKey"))
					&& StringUtils.isBlank(paramJSON.getString("businessKey"))) {
				bpmList = bpmListServer.getByBusinessKey(
						paramJSON.getString("processDefinitionKey"),
						paramJSON.getString("businessKey"));
			}
			Assert.notNull(bpmList, "申请单不存在");
			user.setToken(super.getUserSessionBean().getCertificate());
			bpmProcessServer.stop(bpmList, user, paramJSON.getString("reason"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 终止流程
	 * 
	 * @author sie-lqz 2018-07-07
	 * @param params
	 *            { listId 申请单ID processInstanceId 流程实例ID processDefinitionKey
	 *            businessKey reason 终止理由 }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "stopProcessByListId")
	public String stopProcessByListId(@RequestParam String params) {
		try {
			ActBpmListEntity_HI bpmList = null;
			JSONObject paramJSON = JSON.parseObject(params);
			ActIdUserEntity_RO user = new ActIdUserEntity_RO();
			user.setUserName("ADMIN");
			user.setUserId(1);
			if (StringUtils.isNotBlank(paramJSON.getString("listId"))) {
				bpmList = bpmListServer.getById(paramJSON.getInteger("listId"));
			}
			if (bpmList == null
					&& StringUtils.isNotBlank(paramJSON
							.getString("processInstanceId"))) {
				bpmList = bpmListServer.getByProcessInstanceId(paramJSON
						.getString("processInstanceId"));
			}
			if (bpmList == null
					&& StringUtils.isBlank(paramJSON
							.getString("processDefinitionKey"))
					&& StringUtils.isBlank(paramJSON.getString("businessKey"))) {
				bpmList = bpmListServer.getByBusinessKey(
						paramJSON.getString("processDefinitionKey"),
						paramJSON.getString("businessKey"));
			}
			Assert.notNull(bpmList, "申请单不存在");
			bpmProcessServer.stop(bpmList, user, paramJSON.getString("reason"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询流程状态
	 * 
	 * @param params
	 *            { listId 申请单ID processInstanceId 流程实例ID processDefinitionKey
	 *            流程KEY businessKey 业务主键 ouId 事业部ID responsibilityId 职责ID
	 *            processersYn 是否包含当前办理人 true.是 false.否 }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getProcessStatus")
	public String getProcessStatus(@RequestParam String params) {
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "businessKey",
					"processDefinitionKey");
			ActBpmListEntity_HI bpmList = bpmListServer.get(paramsJSON,
					super.getSessionUserId());
			if (bpmList == null) {
				// 申请单不存在，返回草稿状态
				bpmList = new ActBpmListEntity_HI();
				bpmList.setResult(WorkflowConstant.STATUS_DRAFTING);
			}
			JSONObject result = new JSONObject();
			result.put("status", bpmList.getResult());
			result.put("lastUpdateDate",
					ConvertUtil.dateToString(bpmList.getLastUpdateDate()));
			List<Task> tasks = StringUtils.isNotBlank(bpmList
					.getProcessInstanceId())
					&& "true".equals(paramsJSON.getString("processersYn")) ? bpmTaskServer
					.findActiveTasks(bpmList.getProcessInstanceId()) : null;
			if (tasks != null && !tasks.isEmpty()) {
				JSONArray tasksJSON = new JSONArray();
				for (Task task : tasks) {
					JSONObject taskJSON = new JSONObject();
					taskJSON.put("taskId", task.getId());
					taskJSON.put("taskName", task.getName());
					taskJSON.put("createTime",
							ConvertUtil.dateToString(task.getCreateTime()));
					taskJSON.put("assignee", task.getAssignee());
					taskJSON.put("taskDefinitionId",
							task.getTaskDefinitionKey());
					taskJSON.put("processDefinitionId",
							task.getProcessDefinitionId());
					taskJSON.put("processInstanceId",
							task.getProcessInstanceId());
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
					taskJSON.put("processers", usersJSON);
					tasksJSON.add(taskJSON);
				}
				result.put("tasks", tasksJSON);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					result).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils
					.convertResultJSONObj(ERROR_STATUS, "失败！", 0, null)
					.toString();
		}

	}

	/**
	 * 获取流程变量
	 * 
	 * @param params
	 *            processInstanceId:流程实例ID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getVariables")
	public String getVariables(@RequestParam String params) {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			String processInstanceId = paramJSON.getString("processInstanceId");
			Map<String, Object> variables = runtimeService
					.getVariables(processInstanceId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
					variables).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败" + e, 0,
					null).toString();
		}
	}

	/**
	 * 获取需选人的任务节点
	 * 流程未保存以processDefinitionKey作为参数，保存后以listId为参数，任务办理以taskId为参数，优先级taskId
	 * >listId>processDefinitionKey
	 * 
	 * @author laoqunzhao 2018-05-22
	 * @param params
	 *            JSONObject taskId 任务ID,为空时取第一个任务节点 listId 申请单ID
	 *            processDefinitionKey 流程KEY
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findAssigneeUserTasks")
	public String findAssigneeUserTasks(@RequestParam String params) {
		try {
			JSONObject paramJSON = this.parseObject(params);
			String taskId = paramJSON.getString("taskId");
			String listId = paramJSON.getString("listId");
			String processDefinitionKey = paramJSON
					.getString("processDefinitionKey");
			String taskDefinitionId = null;
			String processDefinitionId = null;
			if (StringUtils.isNotBlank(taskId)) {
				Task task = taskService.createTaskQuery().taskId(taskId)
						.singleResult();
				if (task == null) {
					return SToolUtils.convertResultJSONObj(ERROR_STATUS,
							"任务不存在", 0, null).toString();
				}
				// 子任务无需选人
				if (StringUtils.isNotBlank(task.getParentTaskId())) {
					return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
							"成功", 0, new ArrayList<ActUserTaskEntity_RO>())
							.toString();
				}
				taskDefinitionId = task.getTaskDefinitionKey();
				String processInstanceId = task.getProcessInstanceId();
				ProcessInstance processInstance = runtimeService
						.createProcessInstanceQuery()
						.processInstanceId(processInstanceId).singleResult();
				processDefinitionKey = processInstance
						.getProcessDefinitionKey();
				processDefinitionId = processInstance.getProcessDefinitionId();
			} else if (StringUtils.isNotBlank(listId)) {
				ActBpmListEntity_HI bpmList = bpmListServer.getById(Integer
						.parseInt(listId));
				processDefinitionKey = bpmList.getProcessDefinitionKey();
				ProcessDefinition processDefinition = bpmProcessServer
						.findLatestRunningProcess(processDefinitionKey);
				UserTask firstTask = bpmProcessServer
						.getFirstUserTask(processDefinition.getId());
				taskDefinitionId = firstTask.getId();
				processDefinitionId = processDefinition.getId();
			} else {
				ProcessDefinition processDefinition = bpmProcessServer
						.findLatestRunningProcess(processDefinitionKey);
				UserTask firstTask = bpmProcessServer
						.getFirstUserTask(processDefinition.getId());
				taskDefinitionId = firstTask.getId();
				processDefinitionId = processDefinition.getId();
			}
			// 获取该节点需要选人的节点
			List<UserTask> userTasks = bpmTaskConfigServer
					.findAssigneeUserTasks(processDefinitionKey,
							taskDefinitionId);
			List<ActUserTaskEntity_RO> actUserTasks = new ArrayList<ActUserTaskEntity_RO>();
			;
			if (userTasks != null && !userTasks.isEmpty()) {
				// UserTask转换为ActUserTaskEntity_RO,方便JSON转换
				for (UserTask userTask : userTasks) {
					ActUserTaskEntity_RO actUserTask = new ActUserTaskEntity_RO();
					actUserTask.setId(userTask.getId());
					actUserTask.setName(userTask.getName());
					actUserTasks.add(actUserTask);
				}
				// 获取流程实例中已选的人
				List<ActUserTaskEntity_RO> userTasksAssignees = null;
				if (StringUtils.isNotBlank(taskId)) {
					userTasksAssignees = bpmTaskServer.getTaskAssignees(taskId);
				} else if (StringUtils.isNotBlank(listId)) {
					userTasksAssignees = bpmTaskServer
							.getFirstTaskAssignees(Integer.parseInt(listId));
				}

				if (userTasksAssignees != null && !userTasksAssignees.isEmpty()) {
					for (ActUserTaskEntity_RO actUserTask : actUserTasks) {
						for (ActUserTaskEntity_RO userTasksAssignee : userTasksAssignees) {
							if (actUserTask.getId().equals(
									userTasksAssignee.getId())) {
								actUserTask.setUsers(userTasksAssignee
										.getUsers());
								break;
							}
						}
					}
				}
				// setTaskUsers(actUserTasks, listId, processDefinitionId);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					actUserTasks.size(), actUserTasks).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败\n" + e, 0,
					null).toString();
		}
	}

	// private void setTaskUsers(List<ActUserTaskEntity_RO> actUserTasks, String
	// listId, String processDefinitionId) {
	// for(ActUserTaskEntity_RO actUserTask: actUserTasks) {
	// List<Object>userIds_ =
	// bpmTaskServer.getTaskBpmUserIds(StringUtils.isBlank(listId)?null :
	// Integer.parseInt(listId), processDefinitionId, actUserTask.getId());
	// if(userIds_ != null && !userIds_.isEmpty()) {
	// List<String> userIds = new ArrayList<String>();
	// for(Object userId: userIds_) {
	// if(userId instanceof String) {
	// boolean existed = false;
	// if(actUserTask.getUsers() != null && !actUserTask.getUsers().isEmpty()) {
	// for(ActIdUserEntity_RO userTaskUser: actUserTask.getUsers()) {
	// if(userId.toString().equals(userTaskUser.getId())) {
	// existed = true;
	// break;
	// }
	// }
	// }
	// if(!existed) {
	// userIds.add(userId.toString());
	// }
	// }
	// }
	// if(userIds != null && !userIds.isEmpty()) {
	// List<ActIdUserEntity_RO> users =
	// bpmUserServer.findUsersByBpmIds(userIds);
	// if(users != null && !users.isEmpty()) {
	// if(actUserTask.getUsers() != null && !actUserTask.getUsers().isEmpty()) {
	// actUserTask.getUsers().addAll(users);
	// }else {
	// actUserTask.setUsers(users);
	// }
	// }
	// }
	//
	// }
	// }
	// }

	private String complete(JSONObject paramJSON, ActBpmListEntity_HI bpmList,
			ActIdUserEntity_RO user) {
		try {
			String taskId = paramJSON.getString("taskId");
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
			String delegateId = paramJSON.getString("delegateId");
			String varialbesStr = paramJSON.getString("variables");
			String saveonly = paramJSON.getString("saveonly");// 只保存不提交完成

			// 流程变量
			Map<String, Object> variables = StringUtils.isBlank(varialbesStr) ? new HashMap<String, Object>()
					: bpmProcessServer.jsonToVariables(varialbesStr);
			// 流程表单（审批选项，审批意见）
			@SuppressWarnings("unchecked")
			Map<String, String> properties = paramJSON
					.containsKey("properties") ? JSON.toJavaObject(
					paramJSON.getJSONObject("properties"), Map.class)
					: new HashMap<String, String>();
			Assert.notNull(bpmList, "申请单不存在！");
			UserTask firstTask = bpmProcessServer.getFirstUserTask(bpmList
					.getProcessDefinitionId());
			Assert.isTrue(
					StringUtils.equals(task.getTaskDefinitionKey(),
							firstTask.getId()), "非草稿状态不可提交！");
			// 重新提交，保存申请单数据
			ActBpmListEntity_HI instance = JSON.parseObject(
					paramJSON.toString(), ActBpmListEntity_HI.class);

			if (paramJSON.containsKey("title")) {
				if (!paramJSON.getString("title").equals("")) {
					bpmList.setTitle(paramJSON.getString("title"));
				}
			}
			bpmList.setCustAccountId(instance.getCustAccountId() != null ? instance
					.getCustAccountId() : bpmList.getCustAccountId());
			bpmList.setDepartmentId(instance.getDepartmentId() != null ? instance
					.getDepartmentId() : bpmList.getDepartmentId());
			bpmList.setDescription(StringUtils.isNotBlank(instance
					.getDescription()) ? instance.getDescription() : bpmList
					.getDescription());
			bpmList.setExtend(StringUtils.isNotBlank(instance.getExtend()) ? instance
					.getExtend() : bpmList.getExtend());
			bpmList.setOrgId(instance.getOrgId() != null ? instance.getOrgId()
					: bpmList.getOrgId());
			bpmList.setPositionId(instance.getPositionId() != null ? instance
					.getPositionId() : bpmList.getPositionId());
			bpmList.setProperties(StringUtils.isNotBlank(instance
					.getProperties()) ? instance.getProperties() : bpmList
					.getProperties());
			bpmList.setResponsibilityId(instance.getCustAccountId() != null ? instance
					.getCustAccountId() : bpmList.getResponsibilityId());
			bpmList.setRoleType(StringUtils.isNotBlank(instance.getRoleType()) ? instance
					.getRoleType() : bpmList.getRoleType());
			bpmList.setUserType(StringUtils.isNotBlank(instance.getUserType()) ? instance
					.getUserType() : bpmList.getUserType());
			bpmList.setVariables(StringUtils.isNotBlank(instance.getVariables()) ? instance
					.getVariables() : bpmList.getVariables());
			bpmList.setApplyPositionId(instance.getApplyPositionId() != null ? instance
					.getApplyPositionId() : bpmList.getApplyPositionId());
			bpmList.setApplyPersonId(instance.getApplyPersonId() != null ? instance
					.getApplyPersonId() : bpmList.getApplyPersonId());
			bpmProcessServer.setStartVariables(variables, bpmList, user);
			bpmListServer.update(bpmList);
			bpmTaskServer.complete(task, bpmList, StringUtils
					.isNotBlank(delegateId) ? Integer.parseInt(delegateId)
					: null, variables, properties, user, StringUtils.equals(
					saveonly, "true"), true);
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

}
