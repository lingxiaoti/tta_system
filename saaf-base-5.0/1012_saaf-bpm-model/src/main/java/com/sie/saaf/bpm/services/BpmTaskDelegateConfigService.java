package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskDelegateConfigEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.IActBpmTask;
import com.sie.saaf.bpm.model.inter.IActBpmTaskDelegateConfig;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
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
@RequestMapping("/bpmTaskDelegateConfigService")
public class BpmTaskDelegateConfigService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmTaskDelegateConfigService.class);
	@Autowired
	private IActBpmTaskDelegateConfig bpmTaskDelegateConfigServer;
	
	@Autowired
    private IActBpmUser bpmUserServer;

	@Autowired
	private IActBpmTask bpmTaskServer;

	public BpmTaskDelegateConfigService() {
		super();
	}
	
	/**
     * 流程代办设置查询
     * @param params JSONObject
     * disabled 是否禁用 true. 是，false. 否
     * deleteFlag 删除标记，1.已删除，0.未删除
     * type 1.自己委托  2.被委托
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
	@RequestMapping(method= RequestMethod.POST,value="findDelegateConfig")
	public String findDelegateConfig(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramJSON = this.parseObject(params);
            if(!paramJSON.containsKey("deleteFlag")) {
                paramJSON.put("deleteFlag", 0);//只查询未删除的数据
            }
			String varIsadmin = paramJSON.getString("varIsadmin");
            if ("Y".equalsIgnoreCase(varIsadmin)) {
				paramJSON.put("searchAll", "true");
			}
			//只查询当前用户数据
            if(!"true".equals(paramJSON.getString("searchAll"))) {
            	if("2".equals(paramJSON.getString("type"))) {
            		paramJSON.put("delegateUserId", super.getSessionUserId());
            	}else {
            		paramJSON.put("clientUserId", super.getSessionUserId());
            	}
            }
            Pagination<ActBpmTaskDelegateConfigEntity_HI_RO> pagination = bpmTaskDelegateConfigServer.findDelegateConfig(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            if(result.containsKey(DATA)) {
                JSONArray array = result.getJSONArray(DATA);
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
	 * 保存代办设置
     * @param params
     * {
	 * configId"：主键
	 * categoryIds" : 流程分类ID，多个用”,”分隔,
	 * delegateUserId" : 代理人ID,
	 * disabled"：禁用 true.是  false.否
	 * endTime" : 有效期截止时间,
	 * processDefinitionKeys" : 流程唯一标识，多个用”,”分隔,
	 * startTime" : 有效期起始时间
	 * }
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "delegateUserId", "startTime", "endTime");
			int  selectDelegateUserId =  super.getSessionUserId();
			paramsJSON.put("selectDelegateUserId",selectDelegateUserId) ;
			boolean result = bpmTaskDelegateConfigServer.save(paramsJSON);

			if(result) {
				try{bpmTaskServer.AutoSetAssignee();}catch (Exception e){}
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
			}else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败", 0, null).toString();
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 删除代办设置
	 * @param params JSONObject
     * configIds JSONArray 设置ID
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			bpmTaskDelegateConfigServer.delete(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败\n" + e, 0, null).toString();
		}
	}
	
	/**
	 * 更新代办设置状态
	 * @param params JSONObject
     * configIds JSONArray 设置ID
     * disabled 禁用 true.是  false 否
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="updateStatus")
	public String updateStatus(@RequestParam String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			bpmTaskDelegateConfigServer.updateStatus(queryParamJSON);
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
