package com.sie.watsons.base.rule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.rule.model.entities.TempParamRuleMidleEntity_HI;
import com.sie.watsons.base.rule.model.inter.ITempParamRuleMidle;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.rule.model.inter.ITempRuleDef;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/tempParamRuleMidleService")
public class TempParamRuleMidleService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TempParamRuleMidleService.class);

	@Autowired
	private ITempParamRuleMidle tempParamRuleMidleServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.tempParamRuleMidleServer;
	}

	@Autowired
	private ITempRuleDef tempRuleDefServer;

	/**
	 * 功能描述： 批量保存参数信息
	 * @author xiaoga
	 * @date 2019/5/31
	 * @param  
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveParams")
	public String saveParams(@RequestParam String params) {
		String result = "";
		try {
			List<TempParamRuleMidleEntity_HI> paramsList = JSON.parseArray(params, TempParamRuleMidleEntity_HI.class);
			if (paramsList == null || paramsList.isEmpty()) {
				return this.convertResultJSONObj(ERROR_STATUS, "必传参数不能为空", null).toString();
			}
			for (TempParamRuleMidleEntity_HI dto : paramsList) {
				dto.setOperatorUserId(this.getSessionUserId());
			}
			tempParamRuleMidleServer.saveParams(paramsList);
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null).toString();
		} catch (Exception e) {
			LOGGER.error("saveParams error:{}", e);
			result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
		}
		LOGGER.info(".saveParams 入参:{}, 出参:{}", new Object[]{params, result});
		return result;
	}


	@RequestMapping(method = RequestMethod.POST, value = "deleteParamById")
	public String deleteParamById(@RequestParam String params) {
		String result = "";
		try {
			JSONObject jsonObject = this.parseObject(params);
			JSONArray ruleParamIds = jsonObject.getJSONArray("ruleParamIds");
			List<Serializable> arrayList = new ArrayList<Serializable>();
			if (ruleParamIds != null ) {
				ruleParamIds.forEach(item -> {
					arrayList.add(Integer.parseInt(item + ""));
				});
				tempParamRuleMidleServer.deleteAll(arrayList);
			}
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null).toString();
		} catch (Exception e) {
			LOGGER.error("saveParams error:{}", e);
			result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
		}
		LOGGER.info(".saveParams 入参:{}, 出参:{}", new Object[]{params, result});
		return result;
	}
}