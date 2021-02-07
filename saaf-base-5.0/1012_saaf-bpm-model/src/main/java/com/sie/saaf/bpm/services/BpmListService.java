package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCommunicateEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmListEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bpmListService")
public class BpmListService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmListService.class);
	
	@Autowired
	private IActBpmList bpmListServer;

	@Autowired
	private IActBpmProcess bpmProcessServer;

	@Autowired
    private IActBpmHistory bpmHistoryServer;
	
	@Autowired
    private IActBpmUser bpmUserServer;
	
	@Autowired
	private IActBpmCategory bpmCategoryServer;
	
	@Autowired
	private IActBpmCommunicate bpmCommunicateServer;

	@Autowired
	private IBaseAccreditCache baseAssreditServer;

	@Autowired
	private IActBpmModel bpmModelServer;

	public BpmListService() {
		super();
	}
	
	/**
	 * 流程申请单查询
	 * @author laoqunzhao 2018-04-28
	 * @param params JSONObject
	 * drafter 起草人
	 * processer 流程当前办理人
	 * listCode 流程编号
	 * listName 流程名称
	 * title 流程标题
	 * businessKey 业务主键
	 * billNo 业务申请单号
	 * categoryId 流程分类
	 * systemCode 系统编码
	 * processDefinitionKey 流程定义Key，条件=
	 * startDate 流程发起起始时间，格式yyyy-MM-dd
	 * endDate 流程发起截止时间，格式yyyy-MM-dd
	 * communicated 发起沟通 Y.是   N.否
	 * exceptional 异常   Y.是   N.否
	 * status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
	 * searchAll 查询所有人的发起，true.是   false.否
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="findBpmLists")
	public String findBpmLists(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
            JSONObject paramJSON = this.parseObject(params);
            //只查询未删除的数据
            if(!paramJSON.containsKey("deleteFlag")) {
                paramJSON.put("deleteFlag", 0);
            }
            //只查询当前用户数据
            if(StringUtils.isBlank(paramJSON.getString("createdBy")) &&  !"true".equals(paramJSON.getString("searchAll"))) {
            	paramJSON.put("createdBy", super.getSessionUserId());
            }
            Pagination<ActBpmListEntity_HI_RO> pagination = bpmListServer.findBpmLists(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            if(result.get(DATA) != null) {
                JSONArray array = result.getJSONArray(DATA);
                bpmHistoryServer.appendCurPrevTask(array, "processInstanceId");
                bpmUserServer.appendUserInfo(array, "prev_assignee", true, "prev");
                bpmUserServer.appendUserInfo(array, "cur_assignee", true, "cur");
                result.put(DATA, array);
            }else {
            	result.put(DATA, new JSONArray());
            }
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
            return result.toString();            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败\n" + e, 0, null).toString();
        }
	}
	
	
	
	/**
	 * 删除流程申请单
	 * @author laoqunzhao 2018-04-28
	 * @param params
	 * {
     * listId 流程申请单ID
     * processInstanceId 流程实例ID
     * processDefinitionKey 流程定义KEY
     * businessKey 业务主键
	 * ouId OU ID
	 * responsibilityId 职责ID
     * }
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam String params){
		try {
			JSONObject paramsJSON = JSON.parseObject(params);
			ActBpmListEntity_HI instance = bpmListServer.get(paramsJSON, super.getSessionUserId());
            if(instance == null) {
            	//新建申请单进行回调
				instance = new ActBpmListEntity_HI();
				instance.setProcessDefinitionKey(paramsJSON.getString("processDefinitionKey"));
				instance.setBusinessKey(paramsJSON.getString("businessKey"));
				instance.setResult(WorkflowConstant.STATUS_DELETED);
				//获取BU，如有只查询当前BU下的数据
				Integer ouId = paramsJSON.containsKey("ouId")? paramsJSON.getInteger("ouId") : null;
				if(ouId == null && StringUtils.isNotBlank(paramsJSON.getString("responsibilityId"))) {
					Integer responsibilityId = paramsJSON.getInteger("responsibilityId");
					ProFileBean proFileBean  = baseAssreditServer.getOrg(super.getSessionUserId(), responsibilityId);
					ouId = proFileBean == null? null : Integer.valueOf(proFileBean.getProfileValue());
				}
				//BU不为空，尝试根据BU获取流程定义
				if(ouId != null){
					List<ActBpmModelEntity_HI_RO> models = bpmModelServer.findByProcessKey(instance.getProcessDefinitionKey(), ouId);
					if(models != null && models.size()==1){
						instance.setProcessDefinitionKey(models.get(0).getKey());
					}
				}
				ProcessDefinition processDefinition = bpmProcessServer.findLatestRunningProcess(instance.getProcessDefinitionKey());
            	if(processDefinition == null){
					return SToolUtils.convertResultJSONObj(ERROR_STATUS, "流程定义不存在！", 0, null).toString();
				}
				instance.setProcessDefinitionId(processDefinition.getId());
            }else if(instance.getStatus()==1) {
            	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "非草稿状态不能删除！", 0, null).toString();
            }
            ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super.getSessionUserId());
			bpmListServer.delete(instance, user);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 查询流程申请单
	 * @author laoqunzhao 2018-04-28
	 * @param params JSONObject
     * listId 流程申请单ID
     * processInstanceId 流程实例ID
     * processDefinitionKey 流程定义KEY
     * businessKey  业务主键
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="get")
    public String get(@RequestParam String params){
        try {
            ActBpmListEntity_HI instance = null;
            JSONObject paramJSON = JSON.parseObject(params);
            String bpmListId = paramJSON.getString("listId");
            String processInstanceId = paramJSON.getString("processInstanceId");
            String processDefinitionKey = paramJSON.getString("processDefinitionKey");
            String businessKey = paramJSON.getString("businessKey");
            if(StringUtils.isNotBlank(bpmListId)) {
            	instance = bpmListServer.getById(Integer.parseInt(bpmListId));
            }
            if(instance == null && StringUtils.isNotBlank(processInstanceId)) {
            	instance = bpmListServer.getByProcessInstanceId(processInstanceId);
            }
            if(instance == null && StringUtils.isNotBlank(processDefinitionKey) && StringUtils.isNotBlank(businessKey)) {
            	instance = bpmListServer.getByBusinessKey(processDefinitionKey, businessKey);
            }
            if(instance!=null) {
            	JSONObject result = (JSONObject) JSONObject.toJSON(instance);
            	ActBpmCategoryEntity_HI category = bpmCategoryServer.get(instance.getCategoryId());
                if(category != null) {
                	result.put("categoryName", category.getCategoryName());
                }
                //查询是否有发消息
                if(StringUtils.isNotBlank(instance.getProcessInstanceId())) {
	                JSONObject queryParamJSON = new JSONObject();
	                queryParamJSON.put("type", "COMMON");
	                queryParamJSON.put("processInstanceId", instance.getProcessInstanceId());
	                queryParamJSON.put("deleteFlag", 0);
	                Pagination<ActBpmCommunicateEntity_HI_RO> pagination = bpmCommunicateServer.findCommunicates(queryParamJSON, 1, 1);
	                result.put("communicated", pagination.getCount()>0 ? 1 : 0);
                }else {
                	result.put("communicated", 0);
                }
                JSONArray array = new JSONArray();
                array.add(result);
                bpmUserServer.appendUserInfo(array, "createdBy", false, "");
            	return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, result).toString();
            }else {
            	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "申请单不存在！", 0, null).toString();
            }
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
