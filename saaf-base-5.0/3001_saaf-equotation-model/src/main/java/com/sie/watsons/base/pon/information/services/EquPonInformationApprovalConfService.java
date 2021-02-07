package com.sie.watsons.base.pon.information.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationApprovalConfEntity_HI;
import com.sie.watsons.base.pon.information.model.inter.IEquPonInformationApprovalConf;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPonInformationApprovalConfService")
public class EquPonInformationApprovalConfService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonInformationApprovalConfService.class);

	@Autowired
	private IEquPonInformationApprovalConf equPonInformationApprovalConfServer;

	@Autowired
	private ViewObject<EquPonInformationApprovalConfEntity_HI> equPonInformationApprovalConfDAO_HI;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonInformationApprovalConfServer;
	}

	/**
	 * 报价管理-报价结果审批流程节点配置查询，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findInformationApprovalConf")
	public String findInformationApprovalConf(@RequestParam(required = false) String params,
										 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPonInformationApprovalConfServer.findInformationApprovalConf(paramsJONS,pageIndex,pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("projectCategory");
			incomingParam.add("nodepath");
			incomingParam.add("status");
			efferentParam.add("projectCategoryMeaning");
			efferentParam.add("nodepathMeaning");
			efferentParam.add("statusMeaning");
			typeParam.add("EQU_INFORMATION_CATEGORY");
			typeParam.add("EQU_INFORMATION_NODE");
			typeParam.add("EQU_INFORMATION_NODE_STATUS");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
			result.put("data",data);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 报价管理-报价结果审批流程节点配置保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveInformationApprovalConf")
	public String saveInformationApprovalConf(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			Integer userId = jsonObject.getInteger("varUserId");
			JSONArray confArray = jsonObject.getJSONArray("confDataTable");
			for(int i = 0; i < confArray.size(); i++){
				JSONObject paramsObj = parseObject(confArray.getJSONObject(i).toJSONString());
				if(!paramsObj.containsKey("confId") || null == paramsObj.getString("confId") || "".equals(paramsObj.getString("confId"))){
					paramsObj.put("status","10");
					equPonInformationApprovalConfServer.saveInformationApprovalConf(paramsObj);
				}
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, confArray.size(), null).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}
}