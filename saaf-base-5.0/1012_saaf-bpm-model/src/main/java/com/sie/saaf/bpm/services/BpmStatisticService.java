package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticProcessEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmHistory;
import com.sie.saaf.bpm.model.inter.IActBpmStatistic;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bpmStatisticService")
public class BpmStatisticService extends CommonAbstractService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BpmStatisticService.class);
    
    @Autowired
    private IActBpmHistory bpmHistoryServer;
    
    @Autowired
    private IActBpmUser bpmUserServer;
    
    @Autowired
    private IActBpmStatistic bpmStatisticServer;
    
    /**
     * *@param params JSONObject
     * categoryId 流程分类
     * processDefinitionKey 流程定义Key
     * duration 用时时长(>=)秒
     * waiting 距今时长(>=)秒
     * businessKey 单据号
     * startDate 任务起始时间，格式yyyy-MM-dd
     * endDate 任务截止时间，格式yyyy-MM-dd
     * startCreateDate 任务创建起始时间，格式yyyy-MM-dd
     * endCreateDate 任务创建截止时间，格式yyyy-MM-dd
     * status 流程状态  DRAFTING.草稿   RUNNING.审批中  PASSED.审批通过  REJECTED.审批驳回 CLOSED.已关闭
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */

    @Permission(menuCode = "RWSPSCBB")
    @RequestMapping(method= RequestMethod.POST,value="taskTimeStatistic")
    public String taskTimeStatistic(@RequestParam(required = false) String params,
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramsJSON = this.parseObject(params);
            if(StringUtils.isBlank(paramsJSON.getString("userId")) &&  !"true".equals(paramsJSON.getString("searchAll"))) {
            	paramsJSON.put("userId", bpmUserServer.getBpmUserId(super.getUserSessionBean()));
            }
            //获取BU
            Integer ouId = getOrgId();
            JSONArray ouIdsJSON = new JSONArray();
            ouIdsJSON.add(ouId == null? -1: ouId);
            paramsJSON.put("ouIds", ouIdsJSON);
            paramsJSON.put("taskStatus", WorkflowConstant.TASK_STATUS_ALL);
            paramsJSON.put("excludeDraft", true);
            paramsJSON.put("orderBy", "task.start_time_");
            paramsJSON.put("sort", "desc");
            Pagination<ActBpmHiTaskEntity_HI_RO> pagination = bpmHistoryServer.findHistoricTasks(paramsJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(SToolUtils.STATUS, SUCCESS_STATUS);
            result.put(SToolUtils.MSG, "成功");
            return result.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * *@param params JSONObject
     * categoryId 流程分类
     * processDefinitionKey 流程定义Key
     * duration 用时时长(>=)秒
     * waiting 距今时长(>=)秒
     * businessKey 单据号
     * startDate 任务起始时间，格式yyyy-MM-dd
     * endDate 任务截止时间，格式yyyy-MM-dd
     * status 流程状态  DRAFTING.草稿   RUNNING.审批中  PASSED.审批通过  REJECTED.审批驳回 CLOSED.已关闭
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
    @RequestMapping(method= RequestMethod.POST,value="taskTimeStatisticByProcess")
    public String taskTimeStatisticByProcess(@RequestParam(required = false) String params,
                                    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramsJSON = this.parseObject(params);
            if(StringUtils.isBlank(paramsJSON.getString("userId")) &&  !"true".equals(paramsJSON.getString("searchAll"))) {
                paramsJSON.put("userId", bpmUserServer.getBpmUserId(super.getUserSessionBean()));
            }
            //paramsJSON.put("taskStatus", WorkflowConstant.TASK_STATUS_ALL);
            paramsJSON.put("excludeDraft", false);
            paramsJSON.put("orderBy", "task.start_time_");
            paramsJSON.put("sort", "desc");
            Pagination<ActBpmHiTaskEntity_HI_RO> pagination = bpmHistoryServer.findHistoricTasks(paramsJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(SToolUtils.STATUS, SUCCESS_STATUS);
            result.put(SToolUtils.MSG, "成功");
            return result.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
    
    
   
   
    @RequestMapping(method= RequestMethod.POST,value="taskStatistic")
    public String taskStatistic(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = this.parseObject(params);
            ActStatisticEntity_RO statisticEntity = bpmStatisticServer.taskStatistic(paramJSON);
            JSONObject result = SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 
            		statisticEntity.getCount(), statisticEntity.getStatistics());
            result.put("amount", statisticEntity.getAmount());
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
            return result.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败" + e, 0, null).toString();
        }
    }
    
    @RequestMapping(method= RequestMethod.POST,value="processStatistic")
    public String processStatistic(@RequestParam(required = false) String params,
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
        	ActIdUserEntity_RO user = null;
            JSONObject paramsJSON = this.parseObject(params);
            if(!paramsJSON.containsKey("searchAll") || !"true".equals(paramsJSON.getString("searchAll"))) {
            	user = bpmUserServer.findUserBySysId(super.getSessionUserId());
            }
            Pagination<ActStatisticProcessEntity_RO> pagination = bpmStatisticServer.findProcesses(paramsJSON, user, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
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
