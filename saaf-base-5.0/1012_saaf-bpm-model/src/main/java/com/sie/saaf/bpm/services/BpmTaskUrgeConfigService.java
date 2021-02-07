package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskUrgeConfigEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.IActBpmTaskUrgeConfig;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/bpmTaskUrgeConfigService")
public class BpmTaskUrgeConfigService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmTaskUrgeConfigService.class);
	
	@Autowired
	private IActBpmTaskUrgeConfig bpmTaskUrgeConfigServer;

	public BpmTaskUrgeConfigService() {
		super();
	}
	
	/**
     * 流程催办设置查询
     * @param params JSONObject
     * categoryId 流程分类ID
     * processDefinitionKey 流程定义Key，条件=
     * startDate 起始时间，格式yyyy-MM-dd
     * endDate 截止时间，格式yyyy-MM-dd
     * disabled 是否禁用
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
	@Permission(menuCode = "CBSZ")
	@RequestMapping(method= RequestMethod.POST,value="findUrgeConfig")
	public String findUrgeConfig(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramJSON = super.parseObject(params);
            if(!paramJSON.containsKey("deleteFlag")) {
                paramJSON.put("deleteFlag", 0);//只查询未删除的数据
            }
            UserSessionBean user = super.getUserSessionBean();
            if(!"Y".equals(user.getIsadmin())) {
            	Integer orgId = super.getOrgId();
            	List<Integer> ouIds = new ArrayList<Integer>();
            	if(orgId != null) {
            		ouIds.add(orgId);
            	}else {
            		ouIds.add(-1);//没有OU权限加-1限制
            	}
        		JSONArray ouIdsJSON = (JSONArray)JSON.toJSON(ouIds);
        		paramJSON.put("ouIds", ouIdsJSON);
            }
            Pagination<ActBpmTaskUrgeConfigEntity_HI_RO> pagination = bpmTaskUrgeConfigServer.findUrgeConfig(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
            return result.toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败\n" + e, 0, null).toString();
        }
    }
	
	
	/**
	 * 保存催办设置
     * @param params
     * configId 主键
	 * startTime 开始时间
	 * endTime 结束时间
	 * processDefinitionKeys 流程定义KEY[]
	 * timeout 超时催办天数
	 * timegap 催办间隔小时
	 * disabled 是否禁用
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			paramsJSON.put("disabled", false);//默认生效
			if(StringUtils.isBlank(paramsJSON.getString("configId"))){
				//批量保存
				bpmTaskUrgeConfigServer.saveConfigs(paramsJSON, super.getSessionUserId());
			}else{
				bpmTaskUrgeConfigServer.saveOrUpdate(paramsJSON, super.getSessionUserId());
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 删除催办设置
	 * @param params JSONObject
     * configIds JSONArray 设置ID
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "configIds");
			bpmTaskUrgeConfigServer.delete(paramsJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 更新催办设置状态
	 * @param params JSONObject
     * configIds JSONArray 设置ID
     * disabled 禁用 true.是  false 否
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="updateStatus")
	public String updateStatus(@RequestParam String params){
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			bpmTaskUrgeConfigServer.updateStatus(queryParamJSON);
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
