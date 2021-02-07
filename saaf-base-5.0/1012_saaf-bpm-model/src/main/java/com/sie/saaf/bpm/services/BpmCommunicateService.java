package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCommunicateEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.IActBpmCommunicate;
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
@RequestMapping("/bpmCommunicateService")
public class BpmCommunicateService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmCommunicateService.class);
	@Autowired
	private IActBpmCommunicate bpmCommunicateServer;

	public BpmCommunicateService() {
		super();
	}
	
	/**
     * 流程消息查询
     * @author laoqunzhao 2018-05-02
     * @param params JSONObject
     * type 类型：COMMON.沟通   URGE.催办
     * searchKey 流程标题、流程名称、流程编码、任务名称、内容
     * processDefinitionKey 流程唯一标识
     * processInstanceId 流程实例ID
     * sender 发送人
     * startDate 消息发送起始日期 yyyy-MM-dd
     * endDate 消息发送截止日期yyyy-MM-dd
     * deleteFlag 删除标记，1.已删除，0.未删除
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
	@RequestMapping(method= RequestMethod.POST,value="findCommunicates")
	public String findCommunicates(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
            JSONObject paramJSON = this.parseObject(params);
            //只查询未删除的数据
            if(!paramJSON.containsKey("deleteFlag")) {
                paramJSON.put("deleteFlag", 0);
            }
            //只查询当前用户数据
            if(!"true".equals(paramJSON.getString("searchAll"))) {
            	paramJSON.put("receiverId", super.getSessionUserId());
            }else {
            	paramJSON.remove("receiverId");
            }
            Pagination<ActBpmCommunicateEntity_HI_RO> pagination = bpmCommunicateServer.findCommunicates(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
            return result.toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败" + e, 0, null).toString();
        }
	}
	
	
	/**
	 * 保存流程消息
	 * @author laoqunzhao 2018-05-02
     * @param params
     * type COMMON.沟通   URGE.催办
     * taskId 任务ID
     * content 内容
     * receiverId 接收人
     * messageChannel 发送渠道
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam String params){
	    String result = null;
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			result = bpmCommunicateServer.save(queryParamJSON);
			if(SUCCESS_STATUS.equals(result)) {
			    return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
			}else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败:"+result, 0, null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	
	/**
	 * 删除流程消息
	 * @author laoqunzhao 2018-05-02
	 * @param params JSONObject
     * communicateIds JSONArray 流程消息ID
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			bpmCommunicateServer.delete(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败" + e, 0, null).toString();
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
}
