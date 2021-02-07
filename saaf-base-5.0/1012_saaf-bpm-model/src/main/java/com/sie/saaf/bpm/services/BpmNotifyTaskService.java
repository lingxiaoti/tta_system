package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmNotifyTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmNotifyTask;
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

@RestController
@RequestMapping("/bpmNotifyTaskService")
public class BpmNotifyTaskService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmNotifyTaskService.class);
	@Autowired
	private IActBpmNotifyTask bpmNotifyTaskServer;
	
	@Autowired
    private IActBpmUser bpmUserServer;

	public BpmNotifyTaskService() {
		super();
	}
	
	/**
     * 待阅查询
     * @author laoqunzhao 2018-05-02
     * @param params JSONObject
	 * listCode 流程编号
	 * listName 流程名称
	 * title 流程标题
	 * businessKey 业务主键
	 * billNo 业务申请单号
	 * processDefinitionKey 流程唯一标识
	 * processInstanceId 流程实例ID
	 * receiverId 接收人ID
	 * status 0.未阅 1.已阅
	 * deleteFlag 删除标记，1.已删除，0.未删除
	 * startDate 待阅创建起始时间
	 * endDate   待阅创建截止时间
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
	@RequestMapping(method= RequestMethod.POST,value="findNotifyTasks")
	public String findNotifyTasks(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
            JSONObject queryParamJSON = this.parseObject(params);
            //只查询未删除的数据
            if(!queryParamJSON.containsKey("deleteFlag")) {
            	queryParamJSON.put("deleteFlag", 0);
            }
            //只查询当前用户数据
            if(!"true".equals(queryParamJSON.getString("searchAll"))) {
            	queryParamJSON.put("receiverId", super.getSessionUserId());
            }else {
            	queryParamJSON.remove("receiverId");
            }
            Pagination<ActBpmNotifyTaskEntity_HI_RO> pagination = bpmNotifyTaskServer.findNotifyTasks(queryParamJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            if(result.containsKey(DATA)) {
            	bpmUserServer.appendUserInfo(result.getJSONArray(DATA), "createdBy", false, null);
            }
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
            return result.toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败" + e, 0, null).toString();
        }
	}
	
	
	/**
	 * 保存待阅
	 * @author laoqunzhao 2018-05-02
     * @param params
     * processInstanceId 流程实例ID
     * taskId 任务ID
     * receiverId 接收人ID
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam String params){
	    String result = null;
		try {
			JSONObject paramsJSON = this.parseObject(params);
			ActIdUserEntity_RO user = bpmUserServer.findUserBySysId(super.getSessionUserId());
			result = bpmNotifyTaskServer.save(paramsJSON, user);
			if(SUCCESS_STATUS.equals(result)) {
			    return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
			}else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败:"+result, 0, null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败" + e, 0, null).toString();
		}
	}
	
	
	/**
     * 更新待阅状态
     * @author laoqunzhao 2018-05-02
     * @param params
     * notifyIds JSONArray 待阅ID
     * status 0.未阅 1.已阅
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="updateStatus")
    public String updateStatus(@RequestParam String params){
        try {
            JSONObject queryParamJSON = this.parseObject(params);
            if(!queryParamJSON.containsKey("status")){
				queryParamJSON.put("status", 1);
			}
            bpmNotifyTaskServer.updateStatus(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
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
