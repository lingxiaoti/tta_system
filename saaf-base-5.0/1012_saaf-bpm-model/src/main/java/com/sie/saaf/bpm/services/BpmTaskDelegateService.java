package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmTaskDelegate;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bpmTaskDelegateService")
public class BpmTaskDelegateService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmTaskDelegateService.class);
	
	@Autowired
	private IActBpmTaskDelegate bpmTaskDelegateServer;
	
	@Autowired
    private IActBpmList bpmListServer;
    
    @Autowired
    private IActBpmUser bpmUserServer;

	public BpmTaskDelegateService() {
		super();
	}
	
	/**
     * 流程代办查询
     * @param params JSONObject
     * searchKey 流程标题、流程名称、流程编码、任务名称
     * processDefinitionKey 流程唯一标识
     * processInstanceId 流程实例ID
     * type 1.委托  2及其他。代理
     * status PENDING待办；CANCELED取消；BACKED退回；RESOLVED完成；DESTROYED作废
     * deleteFlag 删除标记，1.已删除，0.未删除
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
	@RequestMapping(method= RequestMethod.POST,value="findDelegates")
	public String findDelegates(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramJSON = this.parseObject(params);
            if(!paramJSON.containsKey("deleteFlag")) {
                paramJSON.put("deleteFlag", 0);//只查询未删除的数据
            }
            //只查询当前用户数据
            if(!"true".equals(paramJSON.getString("searchAll"))) {
            	//查询我的委托
            	if("1".equals(paramJSON.getString("type"))) {
            		paramJSON.put("clientUserId", super.getSessionUserId());
            	}else {
            		//查询我的代办
            		paramJSON.put("delegateUserId", super.getSessionUserId());
            	}
            }
            Pagination<ActBpmTaskDelegateEntity_HI> pagination = bpmTaskDelegateServer.findDelegates(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            if(result.containsKey(DATA)) {
                JSONArray array = result.getJSONArray(DATA);
                bpmListServer.appendBpmListInfo(array);
                bpmUserServer.appendUserInfo(array, "bpm_createdBy", false, "bpm");
                bpmUserServer.appendUserInfo(array, "clientUserId", false, "client");
                bpmUserServer.appendUserInfo(array, "delegateUserId", false, "delegate");
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
	 * 保存代办
     * @param params
     * taskIds 任务ID JSONArray
     * delegateUserId 代办人ID
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam("params") String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			boolean result = bpmTaskDelegateServer.save(queryParamJSON);
			if(result) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
			}else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败", 0, null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败\n" + e, 0, null).toString();
		}
	}
	
	/**
	 * 根据流程分类、流程批量添加代办
     * @param params JSONObject
     * categoryIds JSONArray 流程分类ID
     * processDefinitionKeys JSONArray 流程定义KEY
     * delegateUserId 代办人ID
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="batchSave")
	public String batchSave(@RequestParam("params") String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			Integer delegateUserId = queryParamJSON.getInteger("delegateUserId");
			List<Integer> categoryIds = getCategoryIds(queryParamJSON);
			List<String> processDefinitionKeys = getProcessDefinitionKeys(queryParamJSON);
			if(categoryIds != null && !categoryIds.isEmpty() || processDefinitionKeys != null && !processDefinitionKeys.isEmpty()) {
				bpmTaskDelegateServer.saveTaskDelegates(categoryIds, processDefinitionKeys, super.getSessionUserId(), delegateUserId);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败\n" + e, 0, null).toString();
		}
	}
	
	
	/**
	 * 删除代办
	 * @param params JSONObject
     * delegateIds JSONArray 代办ID
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			bpmTaskDelegateServer.delete(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败\n" + e, 0, null).toString();
		}
	}
	
	/**
	 * 取消代办
	 * @param params JSONObject
     * delegateIds JSONArray 代办ID
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="update")
	public String update(@RequestParam String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			bpmTaskDelegateServer.updateStatus(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败\n" + e, 0, null).toString();
		}
	}
	
	/**
	 * 根据流程分类、流程批量取消代办
     * @param params JSONObject
     * categoryIds JSONArray 流程分类ID
     * processDefinitionKeys JSONArray 流程定义KEY
     * delegateUserId 代办人ID
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="cancel")
	public String cancel(@RequestParam("params") String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			String status = WorkflowConstant.DELEGATE_STATUS_CANCELED;
			List<Integer> categoryIds = getCategoryIds(queryParamJSON);
			List<String> processDefinitionKeys = getProcessDefinitionKeys(queryParamJSON);
			if(categoryIds != null && !categoryIds.isEmpty() || processDefinitionKeys != null && !processDefinitionKeys.isEmpty()) {
				bpmTaskDelegateServer.updateStatus(categoryIds, processDefinitionKeys, super.getSessionUserId(), status);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败\n" + e, 0, null).toString();
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
	
	private List<Integer> getCategoryIds(JSONObject queryParamJSON){
		List<Integer> categoryIds = new ArrayList<Integer>();
		if(queryParamJSON != null && queryParamJSON.containsKey("categoryIds") && queryParamJSON.get("categoryIds") != null) {
			JSONArray categoryIdsJSON = queryParamJSON.getJSONArray("categoryIds");
			for(int i=0; i<categoryIdsJSON.size(); i++) {
				categoryIds.add(categoryIdsJSON.getInteger(i));
			}
		}
		return categoryIds;
	}
	
	private List<String> getProcessDefinitionKeys(JSONObject queryParamJSON){
		List<String> processDefinitionKeys = new ArrayList<String>();
		if(queryParamJSON != null && queryParamJSON.containsKey("processDefinitionKeys") && queryParamJSON.get("processDefinitionKeys") != null) {
			JSONArray processDefinitionKeysJSON = queryParamJSON.getJSONArray("processDefinitionKeys");
			for(int i=0; i<processDefinitionKeysJSON.size(); i++) {
				processDefinitionKeys.add(processDefinitionKeysJSON.getString(i));
			}
		}
		return processDefinitionKeys;
	}
}
