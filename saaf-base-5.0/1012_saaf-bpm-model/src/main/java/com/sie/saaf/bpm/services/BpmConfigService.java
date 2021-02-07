package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmConfigEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmConfig;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bpmConfigService")
public class BpmConfigService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmConfigService.class);
	@Autowired
	private IActBpmConfig bpmConfigServer;

	public BpmConfigService() {
		super();
	}
	
	/**
	 * 查询流程配置
	 * @author laoqunzhao 2018-05-12
	 * @param params JSONObject
     * listId 流程申请单ID
     * processInstanceId 流程实例ID
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="get")
	public String get(@RequestParam(required = false) String params) {
		try {
            JSONObject paramJSON = this.parseObject(params);
            String processDefinitionKey = paramJSON.getString("processDefinitionKey");
            ActBpmConfigEntity_HI result = bpmConfigServer.findByProcessDefinitionKey(processDefinitionKey);
            if(result == null) {
            	ActBpmConfigEntity_HI publicConfig = bpmConfigServer.findByProcessDefinitionKey(WorkflowConstant.PUBLIC);
            	if(publicConfig != null) {
            		result = new ActBpmConfigEntity_HI();
            		BeanUtils.copyProperties(publicConfig, result, new String[] {"configId", "processDefinitionKey"});
            		result.setProcessDefinitionKey(processDefinitionKey);
            		result.setOperatorUserId(super.getSessionUserId());
            		bpmConfigServer.save((JSONObject) JSON.toJSON(result));
            		result = bpmConfigServer.findByProcessDefinitionKey(processDefinitionKey);
            	}
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败\n" + e, 0, null).toString();
        }
	}
	
	/**
	 * 保存流程配置
	 * @author laoqunzhao 2018-05-12
	 * @param params JSON格式ActBpmConfigEntity_HI
	 * configId 主键
	 * processDefinitionKey 流程定义key
	 * codingRule  流程单号编码规则
	 * titleFormat  流程标题格式化规则
	 * ccTitleFormat  抄送标题格式化规则
     * ccContentFormat  抄送内容格式化规则
     * urgeTitleFormat   催办标题格式化规则
     * urgeContentFormat  催办内容格式化规则
	 */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam String params){
		try {
			JSONObject paramJSON = this.parseObject(params);
			String result = bpmConfigServer.save(paramJSON);
			if(SUCCESS_STATUS.equals(result)) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
			}else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败", 0, null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败\n" + e, 0, null).toString();
		}
	}
	
	/**
	 * 删除流程配置
	 * @author laoqunzhao 2018-05-12
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam String params){
		try {
			JSONObject paramJSON = this.parseObject(params);;
			bpmConfigServer.delete(paramJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败\n" + e, 0, null).toString();
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
}
