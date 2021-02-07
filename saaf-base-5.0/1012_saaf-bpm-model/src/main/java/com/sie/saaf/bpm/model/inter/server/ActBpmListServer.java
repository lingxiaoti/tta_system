package com.sie.saaf.bpm.model.inter.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmListEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmCategory;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmTask;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.bpm.model.inter.IBpmCallBack;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("actBpmListServer")
public class ActBpmListServer implements IActBpmList {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActBpmListServer.class);

	private static final String ALIAS = "bpm";// 加入到JSON时别名前缀

	@Autowired
	private ViewObject<ActBpmListEntity_HI> bpmListDAO_HI;

	@Autowired
	private BaseViewObject<ActBpmListEntity_HI_RO> bpmListDAO_HI_RO;

	@Autowired
	private ActBpmConfigServer bpmConfigServer;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IActBpmCategory bpmCategoryServer;

	@Autowired
	private IActBpmProcess bpmProcessServer;

	@Autowired
	private IActBpmUser bpmUserServer;

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private IBpmCallBack bpmCallBackServer;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IActBpmTask bpmTaskServer;

	@Autowired
	private IActBpmModel bpmModelServer;

	@Autowired
	private IBaseAccreditCache baseAssreditServer;

	public ActBpmListServer() {
		super();
	}

	/**
	 * 流程发起查询
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param queryParamJSON
	 *            { createdBy 发起人ID drafter 起草人 processer 流程当前办理人 listCode 流程编号
	 *            listName 流程名称 title 流程标题 businessKey 业务主键 billNo 业务申请单号
	 *            categoryId 流程分类 systemCode 系统代码 processDefinitionKey
	 *            流程定义Key，条件= processKey 流程标识，条件= startDate
	 *            流程发起起始时间，格式yyyy-MM-dd endDate 流程发起截止时间，格式yyyy-MM-dd deleteFlag
	 *            删除标记，1.已删除，0.未删除 communicated 发起沟通 Y.是 N.否 exceptional 异常 Y.是
	 *            N.否 status 流程状态 DRAFT.草稿 APPROVAL.审批中 ALLOW.审批通过 REFUSAL.审批驳回
	 *            CLOSED.已关闭 }
	 * @param pageIndex
	 *            页码索引
	 * @param pageRows
	 *            每页记录数
	 * @return 流程发起数据集合Pagination<ActBpmListEntity_HI_RO>
	 * @throws Exception
	 */
	@Override
	public Pagination<ActBpmListEntity_HI_RO> findBpmLists(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows)
			throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer condition = new StringBuffer();
		// 流程编号
		String listCode = StringUtils.trimToNull(queryParamJSON
				.getString("listCode"));
		if (listCode != null) {
			condition.append(" and upper(bpm.list_code) like :listCode");
			paramsMap.put("listCode", "%" + listCode.toUpperCase() + "%");
		}
		// 流程名称
		String listName = StringUtils.trimToNull(queryParamJSON
				.getString("listName"));
		if (listName != null) {
			condition.append(" and upper(bpm.list_name) like :listName");
			paramsMap.put("listName", "%" + listName.toUpperCase() + "%");
		}
		// 流程标题
		String title = StringUtils
				.trimToNull(queryParamJSON.getString("title"));
		if (title != null) {
			condition.append(" and upper(bpm.title) like :title");
			paramsMap.put("title", "%" + title.toUpperCase() + "%");
		}
		// 业务主键
		String businessKey = StringUtils.trimToNull(queryParamJSON
				.getString("businessKey"));
		if (businessKey != null) {
			condition.append(" and upper(bpm.business_key) like :businessKey");
			paramsMap.put("businessKey", "%" + businessKey.toUpperCase() + "%");
		}
		// 业务主键
		String businessKeyEq = StringUtils.trimToNull(queryParamJSON
				.getString("businessKeyEq"));
		if (businessKeyEq != null) {
			condition.append(" and bpm.business_key = :businessKeyEq");
			paramsMap.put("businessKeyEq", businessKeyEq);
		}
		// 业务申请单号
		String billNo = StringUtils.trimToNull(queryParamJSON
				.getString("billNo"));
		if (billNo != null) {
			condition.append(" and upper(bpm.bill_no) like :billNo");
			paramsMap.put("billNo", "%" + billNo.toUpperCase() + "%");
		}
		// 流程发起人
		String createdBy = StringUtils.trimToNull(queryParamJSON
				.getString("createdBy"));
		if (createdBy != null) {
			condition.append(" and bpm.created_by = :createdBy");
			paramsMap.put("createdBy", Integer.parseInt(createdBy));
		}
		String drafter = StringUtils.trimToNull(queryParamJSON
				.getString("drafter"));
		if (drafter != null) {
			drafter = "%" + drafter.toUpperCase() + "%";
			condition.append(" and "
					+ bpmUserServer.getSearchSQL("usr", " :drafterUserName",
							" :drafterUserFullName"));
			paramsMap.put("drafterUserName", drafter);
			paramsMap.put("drafterUserFullName", drafter);
		}
		// 流程分类
		String categoryId = StringUtils.trimToNull(queryParamJSON
				.getString("categoryId"));
		if (categoryId != null) {
			List<Integer> categoryIds = bpmCategoryServer
					.getCategoryIds(Integer.parseInt(categoryId));
			condition.append(" and bpm.category_id in ("
					+ StringUtils.join(categoryIds, ",") + ")");
		}
		// 系统代码
		String systemCode = StringUtils.trimToNull(queryParamJSON
				.getString("systemCode"));
		if (systemCode != null) {
			condition
					.append(" and exists(select 1 from act_bpm_permission where proc_def_key=bpm.proc_def_key and upper(system_code) = :systemCode)");
			paramsMap.put("systemCode", systemCode.toUpperCase());
		}
		// 流程KEY
		String processDefinitionKey = StringUtils.trimToNull(queryParamJSON
				.getString("processDefinitionKey"));
		if (processDefinitionKey != null) {
			condition.append(" and bpm.proc_def_key = :processDefinitionKey");
			paramsMap.put("processDefinitionKey", processDefinitionKey);
		}
		// 流程标识
		String processKey = StringUtils.trimToNull(queryParamJSON
				.getString("processKey"));
		if (processKey != null) {
			condition.append(" and cat.process_key = :processKey");
			paramsMap.put("processKey", processKey);
		}
		// 是否删除
		String deleteFlag = StringUtils.trimToNull(queryParamJSON
				.getString("deleteFlag"));
		if (deleteFlag != null) {
			condition.append(" and bpm.delete_flag = :deleteFlag");
			paramsMap.put("deleteFlag", Integer.parseInt(deleteFlag));
		}
		// 流程单据状态
		String status = StringUtils.trimToNull(queryParamJSON
				.getString("status"));
		if (status != null) {
			condition.append(" and bpm.result = :status");
			paramsMap.put("status", status);
		}
		// 是否发起沟通
		String communicated = StringUtils.trimToNull(queryParamJSON
				.getString("communicated"));
		if (communicated != null) {
			condition.append("Y".equals(communicated) ? " and " : " and not");
			condition
					.append(" exists(select 1 from act_bpm_communicate cmc1 where cmc1.proc_inst_id=bpm.proc_inst_id and cmc1.type='COMMON' and cmc1.delete_flag=0)");
		}
		// 是否异常
		String exceptional = StringUtils.trimToNull(queryParamJSON
				.getString("exceptional"));
		if (exceptional != null) {
			condition.append("Y".equals(exceptional) ? " and " : " and not");
			condition
					.append(" exists(select 1 from act_bpm_exception exc1 where exc1.proc_inst_id=bpm.proc_inst_id and exc1.status=0 and exc1.delete_flag=0)");
		}
		// 流程发起时间
		String startDate = StringUtils.trimToNull(queryParamJSON
				.getString("startDate"));
		if (startDate != null) {
			condition.append(" and bpm.start_time >= :startDate");
			paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
		}
		String endDate = StringUtils.trimToNull(queryParamJSON
				.getString("endDate"));
		if (endDate != null) {
			condition.append(" and bpm.start_time < :endDate");
			Calendar c = new GregorianCalendar();
			c.setTime(ConvertUtil.stringToDateYMD(endDate));
			c.add(Calendar.DATE, 1);// 日期向后移一天
			paramsMap.put("endDate", c.getTime());
		}
		// 流程当前办理人
		String processer = StringUtils.trimToNull(queryParamJSON
				.getString("processer"));
		if (processer != null) {
			condition
					.append(" and exists(select 1 from act_ru_task taskp left join act_ru_identitylink idlinkp on taskp.id_=idlinkp.id_");
			condition
					.append(" left join act_bpm_task_leavel leavelp on leavelp.task_id = taskp.id_");
			condition
					.append(" left join base_users usrp on (usrp.user_name=taskp.assignee_ or usrp.user_name=idlinkp.user_id_)");
			condition
					.append(" where upper(usrp.user_full_name) like :processer  and leavelp.top_proc_inst_id=bpm.proc_inst_id)");
			paramsMap.put("processer", "%" + processer.toUpperCase() + "%");
		}

		StringBuffer addCondition = new StringBuffer();
		//过滤六大活动审批流
		addCondition.append(" and not regexp_like(bpm.title,'DM|HWB|OSD|FG|CW|NPP') ");

		StringBuffer countSql = new StringBuffer(
				ActBpmListEntity_HI_RO.QUERY_ALL_BPMLIST_COUNT_SQL);
		countSql.append(condition);
		StringBuffer querySql = new StringBuffer(
				ActBpmListEntity_HI_RO.QUERY_ALL_BPMLIST_SQL);
		querySql.append(condition);

/*		if (!"true".equals(queryParamJSON.getString("searchAll"))) {
			countSql.append(addCondition);
			querySql.append(addCondition);
		}*/
		querySql.append(" order by bpm.creation_date desc ");
		/* 应查询分页有问题，所以减少一个参数 */
		/*
		 * Pagination<ActBpmListEntity_HI_RO> pagination =
		 * bpmListDAO_HI_RO.findPagination(querySql.toString(),
		 * countSql.toString(), paramsMap, pageIndex, pageRows);
		 */
		Pagination<ActBpmListEntity_HI_RO> pagination = bpmListDAO_HI_RO
				.findPagination(querySql.toString(), paramsMap, pageIndex,
						pageRows);
		return pagination;
	}

	/**
	 * 根据ID查询申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param listId
	 *            申请单ID
	 * @return ActBpmListEntity_HI
	 */
	@Override
	public ActBpmListEntity_HI getById(Integer listId) {
		return bpmListDAO_HI.getById(listId);
	}

	/**
	 * 根据ID查询申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param listIds
	 *            申请单ID
	 * @return List<ActBpmListEntity_HI>
	 */
	@Override
	public List<ActBpmListEntity_HI_RO> findByIds(List<Integer> listIds) {
		if (listIds == null || listIds.isEmpty()) {
			return null;
		}
		StringBuffer querySql = new StringBuffer(
				ActBpmListEntity_HI_RO.QUERY_ALL_BPMLIST_SQL);
		querySql.append(" and bpm.list_id in ("
				+ StringUtils.join(listIds, ",") + ")");
		return bpmListDAO_HI_RO.findList(querySql.toString());
	}

	/**
	 * 根据流程实例ID查询申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param processInstanceId
	 *            流程实例ID
	 * @return ActBpmListEntity_HI
	 */
	@Override
	public ActBpmListEntity_HI getByProcessInstanceId(String processInstanceId) {
		if (StringUtils.isBlank(processInstanceId)) {
			return null;
		}
		List<ActBpmListEntity_HI> list = bpmListDAO_HI.findByProperty(
				"processInstanceId", processInstanceId);
		// 结果集为空获取顶层流程实例ID再查询（针对子流程情况）
		if (list == null || list.isEmpty()) {
			String topProcessInstanceId = bpmProcessServer
					.getTopProcessInstanceId(processInstanceId);
			if (!StringUtils.equals(topProcessInstanceId, processInstanceId)) {
				list = bpmListDAO_HI.findByProperty("processInstanceId",
						topProcessInstanceId);
			}
		}
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}

	@Override
	public ActBpmListEntity_HI getByBusinessKey(String processDefinitionKey,
			String businessKey) {
		if (StringUtils.isBlank(processDefinitionKey)
				|| StringUtils.isBlank(businessKey)) {
			return null;
		}
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("processDefinitionKey", processDefinitionKey);
		props.put("businessKey", businessKey);
		List<ActBpmListEntity_HI> list = bpmListDAO_HI.findByProperty(props);
		ActBpmListEntity_HI bpmList = null;
		if (list != null && !list.isEmpty()) {
			for (ActBpmListEntity_HI instance : list) {
				if (bpmList == null) {
					bpmList = instance;
				} else if (new Integer(1).equals(bpmList.getDeleteFlag())
						&& new Integer(0).equals(instance.getDeleteFlag())) {
					bpmList = instance;
				}
			}
		}
		return bpmList;
	}

	public ActBpmListEntity_HI getByProcessKeyAndBusinessKey(String processKey,
			String businessKey) {
		if (StringUtils.isBlank(processKey) || StringUtils.isBlank(businessKey)) {
			return null;
		}
		Map<String, Object> paramsMap = new JSONObject();
		paramsMap.put("processKey", processKey);
		paramsMap.put("businessKey", businessKey);
		String hql = "from ActBpmListEntity_HI bpm where bpm.businessKey =:businessKey and exists(select 1 from ActBpmCategoryEntity_HI cat "
				+ " where cat.processKey =:processKey and cat.categoryId=bpm.categoryId)";
		List<ActBpmListEntity_HI> list = bpmListDAO_HI.findList(hql, paramsMap);
		ActBpmListEntity_HI bpmList = null;
		if (list != null && !list.isEmpty()) {
			for (ActBpmListEntity_HI instance : list) {
				if (bpmList == null) {
					bpmList = instance;
				} else if (new Integer(1).equals(bpmList.getDeleteFlag())
						&& new Integer(0).equals(instance.getDeleteFlag())) {
					bpmList = instance;
				}
			}
		}
		return bpmList;
	}

	/**
	 * 根据流程实例ID查询申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param paramsJSON
	 *            { listId 申请单ID processInstanceId 流程实例ID processDefinitionKey
	 *            流程定义Key/流程标识 businessKey 业务主键 ouId OU ID responsibilityId 职责ID
	 *            }
	 * @param
	 * @param userId
	 *            用户ID
	 * @return ActBpmListEntity_HI
	 */
	@Override
	public ActBpmListEntity_HI get(JSONObject paramsJSON, Integer userId) {
		ActBpmListEntity_HI bpmList = null;
		if (StringUtils.isNotBlank(paramsJSON.getString("listId"))) {
			bpmList = getById(paramsJSON.getInteger("listId"));
		}
		if (bpmList == null
				&& StringUtils.isNotBlank(paramsJSON
						.getString("processInstanceId"))) {
			bpmList = getByProcessInstanceId(paramsJSON
					.getString("processInstanceId"));
		}
		if (bpmList == null) {
			String businessKey = paramsJSON.getString("businessKey");
			String processDefinitionKey = paramsJSON
					.getString("processDefinitionKey");
			if (StringUtils.isBlank(businessKey)
					|| StringUtils.isBlank(processDefinitionKey)) {
				return null;
			}
			// 获取BU，如有只查询当前BU下的数据
			Integer ouId = bpmUserServer.getOuId(paramsJSON, userId);
			// BU不为空，尝试根据BU获取流程定义
			if (ouId != null) {
				ActBpmModelEntity_HI_RO model = bpmModelServer
						.findByProcessKeyAndOuId(processDefinitionKey, ouId);
				if (model != null) {
					bpmList = getByBusinessKey(model.getKey(), businessKey);
				}
			}
			if (bpmList == null) {
				List<ActBpmModelEntity_HI_RO> models = bpmModelServer
						.findByProcessKeyInRedis(processDefinitionKey, ouId);
				if (models != null && !models.isEmpty()) {
					List<String> processDefinitionKeys = new ArrayList<>();
					for (ActBpmModelEntity_HI_RO model : models) {
						processDefinitionKeys.add(model.getKey());
					}
					Map<String, Object> paramsMap = new HashMap<>();
					paramsMap.put("businessKey", businessKey);
					StringBuffer querySql = new StringBuffer(
							"from ActBpmListEntity_HI bpm where bpm.businessKey=:businessKey");
					querySql.append(" and bpm.processDefinitionKey in ('"
							+ StringUtils.join(processDefinitionKeys, "','")
							+ "')");
					List<ActBpmListEntity_HI> instances = bpmListDAO_HI
							.findList(querySql.toString(), paramsMap);
					if (instances != null && instances.size() == 1) {
						bpmList = instances.get(0);
					} else if (instances != null && instances.size() == 2) {
						// 同时有已删除及未删除的数据，返回未删除的数据
						if (new Integer(1).equals(instances.get(0)
								.getDeleteFlag())
								&& new Integer(0).equals(instances.get(1)
										.getDeleteFlag())) {
							bpmList = instances.get(1);
						} else if (new Integer(0).equals(instances.get(0)
								.getDeleteFlag())
								&& new Integer(1).equals(instances.get(1)
										.getDeleteFlag())) {
							bpmList = instances.get(0);
						}
					}
				}
				if (bpmList == null) {
					bpmList = getByBusinessKey(processDefinitionKey,
							businessKey);
					if (bpmList == null) {
						bpmList = getByProcessKeyAndBusinessKey(
								processDefinitionKey, businessKey);
					}
				}
			}
		}
		return bpmList;
	}

	/**
	 * 保存申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param paramJSON
	 *            JSON格式entity listId 申请单ID description 发起说明 businessKey 业务主键
	 *            billNo 业务申请单号 processDefinitionKey 流程定义KEY variables
	 *            流程发起业务变量JSONArray [{ name: 变量名称, type:
	 *            变量类型long/double/boolean/date/string, value: 变量值 }...]
	 *            properties 流程发起流程表单JSONObject {字段名称:字段内容，。。。} title 流程发起标题
	 *            categoryId 流程分类ID operatorUserId 操作人ID
	 */
	@Override
	public ActBpmListEntity_HI save(JSONObject paramJSON) {
		// 将json转换成entity
		ActBpmListEntity_HI instance = JSON.parseObject(paramJSON.toString(),
				ActBpmListEntity_HI.class);
		ActBpmListEntity_HI bpmList = null;
		if (instance.getListId() != null) {
			bpmList = bpmListDAO_HI.getById(instance.getListId());
			Assert.notNull(bpmList, "申请单不存在");
		} else {
			bpmList = getByBusinessKey(instance.getProcessDefinitionKey(),
					instance.getBusinessKey());
		}
		// 已有申请单，进行更新
		if (bpmList != null) {
			Assert.isNull(bpmList.getProcessInstanceId(), "流程已发起，申请单保存失败");
			// 提取流程信息
			ProcessDefinition processDefinition = bpmProcessServer
					.findLatestRunningProcess(instance
							.getProcessDefinitionKey());
			Assert.notNull(processDefinition, "该流程类型下没有正在运行的流程，流程发起失败！");
			Model model = repositoryService.createModelQuery()
					.modelKey(instance.getProcessDefinitionKey())
					.singleResult();
			Assert.notNull(model, "流程未定义！");
			bpmList.setListName(model.getName());
			bpmList.setProcessDefinitionId(processDefinition.getId());
			bpmList.setProcessDefinitionKey(instance.getProcessDefinitionKey());
			bpmList.setOperatorUserId(instance.getOperatorUserId());
			bpmList.setProperties(instance.getProperties());
			bpmList.setDescription(instance.getDescription());
			bpmList.setVariables(instance.getVariables());
			bpmList.setExtend(instance.getExtend());
			bpmList.setPositionId(instance.getPositionId());
			bpmList.setOrgId(instance.getOrgId());
			bpmList.setResponsibilityId(instance.getResponsibilityId());
			bpmList.setUserType(instance.getUserType());
			bpmList.setRoleType(instance.getRoleType());
			bpmList.setCustAccountId(instance.getCustAccountId());
			bpmList.setDepartmentId(instance.getDepartmentId());
			bpmList.setApplyPersonId(instance.getApplyPersonId());
			bpmList.setApplyPositionId(instance.getApplyPositionId());
			if (StringUtils.isNotBlank(instance.getBusinessKey())) {
				bpmList.setBusinessKey(instance.getBusinessKey());
			}
			if (StringUtils.isNotBlank(instance.getBillNo())) {
				bpmList.setBillNo(instance.getBillNo());
			}
			// 创建流程标题
			if (StringUtils.isBlank(instance.getTitle())
					&& StringUtils.isBlank(bpmList.getTitle())) {
				bpmList.setTitle(createTitle(bpmList));
			} else if (StringUtils.isNotBlank(instance.getTitle())) {
				bpmList.setTitle(instance.getTitle());
			}
			bpmListDAO_HI.update(bpmList);
			LOGGER.info("save bpmlist:" + paramJSON.toString());
			return bpmList;
		} else {
			// 首次提交获取流程信息
			ProcessDefinition processDefinition = bpmProcessServer
					.findLatestRunningProcess(instance
							.getProcessDefinitionKey());
			Assert.notNull(processDefinition, "该流程类型下没有正在运行的流程，流程发起失败！");
			Model model = repositoryService.createModelQuery()
					.modelKey(instance.getProcessDefinitionKey())
					.singleResult();
			Assert.notNull(model, "流程未定义！");
			instance.setListName(model.getName());
			instance.setProcessDefinitionId(processDefinition.getId());
			instance.setCategoryId(model.getCategory() == null ? null : Integer
					.parseInt(model.getCategory()));
			instance.setDeleteFlag(0);
			instance.setStatus(-1);
			instance.setResult(WorkflowConstant.STATUS_DRAFTING);
			bpmListDAO_HI.save(instance);
			// 创建流程标题
			if (StringUtils.isBlank(instance.getTitle())) {
				instance.setTitle(createTitle(instance));
			}
			// 创建流程编码
			instance.setListCode(createListCode(instance));
			bpmListDAO_HI.update(instance);
			LOGGER.info("save bpmlist:" + paramJSON.toString());
			return instance;
		}
	}

	/**
	 * 根据流程流转的情况更改流程属性
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param instance
	 *            ActBpmListEntity_HI
	 */
	@Override
	public void update(ActBpmListEntity_HI instance) {
		ActBpmListEntity_HI instanceDb = bpmListDAO_HI.getById(instance
				.getListId());
		if (instanceDb == null) {
			return;
		}
		// 只更改状态属性
		instanceDb.setStatus(instance.getStatus());
		instanceDb.setTitle(instance.getTitle());
		instanceDb.setResult(instance.getResult());
		instanceDb.setStartTime(instance.getStartTime());
		instanceDb.setEndTime(instance.getEndTime());
		instanceDb.setProperties(instance.getProperties());
		instanceDb.setProcessDefinitionId(instance.getProcessDefinitionId());
		instanceDb.setProcessInstanceId(instance.getProcessInstanceId());
		instanceDb.setProperties(instance.getProperties());
		instanceDb.setDescription(instance.getDescription());
		instanceDb.setVariables(instance.getVariables());
		instanceDb.setExtend(instance.getExtend());
		instanceDb.setPositionId(instance.getPositionId());
		instanceDb.setOrgId(instance.getOrgId());
		instanceDb.setResponsibilityId(instance.getResponsibilityId());
		instanceDb.setUserType(instance.getUserType());
		instanceDb.setRoleType(instance.getRoleType());
		instanceDb.setCustAccountId(instance.getCustAccountId());
		instanceDb.setDepartmentId(instance.getDepartmentId());
		instanceDb.setApplyPersonId(instance.getApplyPersonId());
		instanceDb.setApplyPositionId(instance.getApplyPositionId());
		instanceDb
				.setOperatorUserId(null == instance.getOperatorUserId() ? instance
						.getCreatedBy() : instance.getOperatorUserId());
		bpmListDAO_HI.update(instanceDb);
	}

	/**
	 * 标记删除流程申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param instance
	 *            申请单
	 */
	@Override
	public void delete(ActBpmListEntity_HI instance, ActIdUserEntity_RO user) {
		if (instance == null) {
			return;
		}
		if (instance.getListId() != null) {
			UserTask firstTask = bpmProcessServer.getFirstUserTask(instance
					.getProcessDefinitionId());
			List<Task> tasks = bpmTaskServer.findActiveTasks(instance
					.getProcessInstanceId());
			Assert.isTrue(
					tasks == null
							|| tasks.isEmpty()
							|| StringUtils.equals(tasks.get(0)
									.getTaskDefinitionKey(), firstTask.getId()),
					"非草稿状态不能删除！");
			instance.setDeleteFlag(1);
			instance.setLastUpdateDate(new Date());
			instance.setResult(WorkflowConstant.STATUS_DELETED);
			instance.setOperatorUserId(Integer.valueOf(user.getUserId()
					.toString()));
			bpmListDAO_HI.update(instance);
			if (StringUtils.isNotBlank(instance.getProcessInstanceId())) {
				ProcessInstance processInstance = runtimeService
						.createProcessInstanceQuery()
						.processInstanceId(instance.getProcessInstanceId())
						.singleResult();
				// 强制终止流程
				if (processInstance != null) {
					runtimeService.deleteProcessInstance(
							instance.getProcessInstanceId(), null);
				}
			}
		}
		// 回调业务系统服务
		bpmCallBackServer.callBack(instance, null, user.getUserId());
		LOGGER.info("delete bpmlist:" + instance.getListId());
	}

	/**
	 * 物理删除流程申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param paramJSON
	 *            JSONObject listIds JSONArray 申请单ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
		JSONArray listIds = paramJSON.getJSONArray("listIds");
		if (listIds != null && !listIds.isEmpty()) {
			for (int i = 0; i < listIds.size(); i++) {
				int id = listIds.getIntValue(i);
				bpmListDAO_HI.delete(id);
			}
			LOGGER.info("destory bpmlist:" + paramJSON.toString());
		}
	}

	/**
	 * 判断某个流程下是否有申请单
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param processDefinitionKey
	 *            流程定义KEY
	 * @return true：有，false：没有
	 */
	@Override
	public boolean hasSubmited(String processDefinitionKey) {
		String hql = "select count(*) from ActBpmListEntity_HI where processDefinitionKey=?";
		Integer count = bpmListDAO_HI.count(hql, processDefinitionKey);
		return count > 0 ? true : false;
	}

	/**
	 * 将申请单信息写入JSONArray结果 集
	 *
	 * @author laoqunzhao 2018-04-28
	 * @param array
	 *            JSONArray结果 集
	 */
	@Override
	public void appendBpmListInfo(JSONArray array) {
		if (array == null || array.isEmpty()) {
			return;
		}
		String jsonKeyField = "processInstanceId";
		List<String> processInstanceIds = ConvertUtil.getJSONArrayField(array,
				jsonKeyField);
		if (processInstanceIds == null || processInstanceIds.isEmpty()) {
			return;
		}
		List<ActBpmListEntity_HI_RO> instances = getByProcessInstanceIds(processInstanceIds);
		@SuppressWarnings("unchecked")
		Map<String, ActBpmListEntity_HI_RO> bpmListMap = ConvertUtil.listToMap(
				instances, jsonKeyField);

		// 处理子流程ProcessInstanceId无法映射的情况
		// 子流程集
		Map<String, String> topProcessInstanceIdsMap = new HashMap<String, String>();
		List<String> topProcessInstanceIds = new ArrayList<String>();
		for (String processInstanceId : processInstanceIds) {
			if (!bpmListMap.containsKey(processInstanceId)) {
				String topProcessInstanceId = bpmProcessServer
						.getTopProcessInstanceId(processInstanceId);
				topProcessInstanceIdsMap.put(topProcessInstanceId,
						processInstanceId);
				topProcessInstanceIds.add(topProcessInstanceId);
			}
		}
		if (topProcessInstanceIds != null && !topProcessInstanceIds.isEmpty()) {
			instances = getByProcessInstanceIds(topProcessInstanceIds);
			@SuppressWarnings("unchecked")
			Map<String, ActBpmListEntity_HI_RO> topBpmListMap = ConvertUtil
					.listToMap(instances, jsonKeyField);
			if (topBpmListMap != null && !topBpmListMap.isEmpty()) {
				for (String topProcessInstanceId : topProcessInstanceIds) {
					if (topBpmListMap.containsKey(topProcessInstanceId)) {
						bpmListMap.put(topProcessInstanceIdsMap
								.get(topProcessInstanceId), topBpmListMap
								.get(topProcessInstanceId));
					}
				}
			}
		}
		// 将对象转换为内置JSONArray的一部分
		ConvertUtil.appendToJSONArray(array, bpmListMap, null, jsonKeyField,
				ALIAS, false);
	}

	/**
	 * 根据流程实例ID查询流程申请单，只支持到第一层子流程
	 *
	 * @author laoqunzhao 2018-05-13
	 * @param processInstanceIds
	 *            流程实例ID
	 * @return
	 */
	private List<ActBpmListEntity_HI_RO> getByProcessInstanceIds(
			List<String> processInstanceIds) {
		List<ActBpmListEntity_HI_RO> instances = null;
		if (processInstanceIds != null && !processInstanceIds.isEmpty()) {
			StringBuffer sql = new StringBuffer(
					ActBpmListEntity_HI_RO.QUERY_ALL_BPMLIST_SQL);
			sql.append(" and exists(select 1 from act_hi_procinst proc where proc.proc_inst_id_=bpm.proc_inst_id or proc.super_process_instance_id_=bpm.proc_inst_id ");
			sql.append(" and proc.proc_inst_id_ in ('"
					+ StringUtils.join(processInstanceIds, "','") + "'))");
			instances = bpmListDAO_HI_RO.findList(sql.toString());
		}
		return instances;
	}

	/**
	 * 创建流程编码
	 *
	 * @author laoqunzhao 2018-05-13
	 * @param bpmList
	 *            申请单
	 * @return 新的流程编码
	 */
	private String createListCode(ActBpmListEntity_HI bpmList) {
		String processDefinitionKey = bpmList.getProcessDefinitionKey();
		ActBpmConfigEntity_HI config = bpmConfigServer
				.findByProcessDefinitionKey(processDefinitionKey);
		// 系统内置编码规则
		String codingRule = WorkflowConstant.LIST_CODE_FORMAT;
		if (config == null) {
			// 没有流程的配置采用流程的通用配置
			config = bpmConfigServer
					.findByProcessDefinitionKey(WorkflowConstant.PUBLIC);
		}
		if (config != null && StringUtils.isNotBlank(config.getCodingRule())) {
			codingRule = config.getCodingRule();
		}
		// 格式化ProcessKey
		String listCode = codingRule.replaceAll("\\[\\s*ProcessKey\\s*\\]",
				processDefinitionKey);
		// 格式化日期
		String reg = "\\[\\s*date\\s*:\\s*([^\\]]+)\\s*\\]";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(codingRule);
		if (m.find()) {
			String date = new SimpleDateFormat(m.group(1)).format(bpmList
					.getCreationDate());
			listCode = listCode.replaceAll(reg, date);
		}
		// 格式化流水码
		reg = "\\[(\\{([^\\}]+)\\})?(\\s*seq\\s*:\\s*([\\d]+)\\s*)\\]";
		p = Pattern.compile(reg);
		m = p.matcher(codingRule);
		if (m.find()) {
			String date = null;
			if (m.group(2) != null) {
				date = new SimpleDateFormat(m.group(2)).format(bpmList
						.getCreationDate());
			}
			int seqLength = Integer.parseInt(m.group(4));
			long nextCode = getNextCode(processDefinitionKey, date,
					bpmList.getCreatedBy());
			String seq = joinSequence(nextCode, seqLength);
			String nextListCode = listCode.replaceAll(reg, seq);
			while (existListCode(nextListCode)) {
				nextCode = getNextCode(processDefinitionKey, date,
						bpmList.getCreatedBy());
				seq = joinSequence(nextCode, seqLength);
				nextListCode = listCode.replaceAll(reg, seq);
			}
			listCode = nextListCode;
		}
		return listCode;

	}

	/**
	 * 创建流程编码
	 *
	 * @author laoqunzhao 2018-05-13
	 * @param bpmList
	 *            申请单
	 * @return 新的流程编码
	 */
	private String createTitle(ActBpmListEntity_HI bpmList) {
		String processDefinitionKey = bpmList.getProcessDefinitionKey();
		ActBpmConfigEntity_HI config = bpmConfigServer
				.findByProcessDefinitionKey(processDefinitionKey);
		// 默认标题格式
		String titleFormat = WorkflowConstant.LIST_TITLE_FORMAT;
		if (config == null) {
			// 没有流程的配置采用流程的通用配置
			config = bpmConfigServer
					.findByProcessDefinitionKey(WorkflowConstant.PUBLIC);
		}
		if (config != null && StringUtils.isNotBlank(config.getTitleFormat())) {
			titleFormat = config.getTitleFormat();
		}
		// 格式化ProcessKey
		String title = titleFormat.replaceAll("\\[\\s*ProcessKey\\s*\\]",
				processDefinitionKey);
		// 格式化ProcessName
		title = title.replaceAll("\\[\\s*ProcessName\\s*\\]",
				bpmList.getListName());
		// 格式化发起人
		ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(bpmList
				.getCreatedBy());
		title = title.replaceAll("\\[\\s*StartUser\\s*\\]",
				user.getUserFullName());
		// 格式化日期
		String reg = "\\[\\s*date\\s*:\\s*([^\\]]+)\\s*\\]";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(title);
		if (m.find()) {
			String date = new SimpleDateFormat(m.group(1)).format(bpmList
					.getCreationDate());
			title = title.replaceAll(reg, date);
		}
		// 格式化流程变量
		reg = "\\[\\s*(\\w+)\\s*\\]";
		Map<String, Object> variables = bpmProcessServer
				.jsonToVariables(bpmList.getVariables());
		variables = bpmProcessServer.getVariables(bpmList, null, variables);
		p = Pattern.compile(reg);
		m = p.matcher(title);
		while (m.find()) {
			String variableName = m.group(1);
			if (variables.containsKey(variableName)) {
				title = title.replace(m.group(),
						variables.get(variableName) == null ? "" : variables
								.get(variableName).toString());
			}
		}
		return title;

	}

	/**
	 * 将日期和流水号组成流程码，如(20180501， 100， 6)组成的流水码为20180501000100
	 *
	 * @author laoqunzhao 2018-05-13
	 * @param nextCode
	 *            新的流水号
	 * @param seqLength
	 *            流水号的长度
	 * @return 新的流水码
	 */
	private String joinSequence(long nextCode, int seqLength) {
		String seq = String.valueOf(nextCode);
		// 补0
		for (int i = seqLength - seq.length(); i > 0; i--) {
			seq = "0" + seq;
		}
		return seq;
	}

	/**
	 * 获取新的流水号，并对数据库中的流水号加1
	 *
	 * @author laoqunzhao 2018-05-13
	 * @param processDefinitionKey
	 *            流程KEY
	 * @param prefix
	 *            流水码前缀
	 * @return 新的流水号
	 */
	private long getNextCode(String processDefinitionKey, String prefix,
			Integer userId) {
		Long nextCode = 1L;
		String key = "BPM_LIST_INCR_KEY";
		String field = processDefinitionKey
				+ (StringUtils.isBlank(prefix) ? "" : ("," + prefix));
		if (jedisCluster.hexists(key, field)) {
			nextCode = jedisCluster.hincrBy(key, field, 1);
		} else {
			jedisCluster.hset(key, field, String.valueOf(nextCode));
			jedisCluster.persist(key);
		}
		return nextCode;
	}

	/**
	 * 判断流程编码是否存在
	 *
	 * @author laoqunzhao 2018-05-13
	 * @param listCode
	 *            流程编码
	 * @return true：存在，false：不存在
	 */
	private boolean existListCode(String listCode) {
		List<ActBpmListEntity_HI> list = bpmListDAO_HI.findByProperty(
				"listCode", listCode);
		return list == null || list.isEmpty() ? false : true;
	}

}
