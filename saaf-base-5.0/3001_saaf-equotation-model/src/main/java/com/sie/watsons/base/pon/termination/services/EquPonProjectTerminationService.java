package com.sie.watsons.base.pon.termination.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pon.termination.model.entities.EquPonProjectTerminationEntity_HI;
import com.sie.watsons.base.pon.termination.model.inter.IEquPonProjectTermination;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPonProjectTerminationService")
public class EquPonProjectTerminationService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectTerminationService.class);

	@Autowired
	private IEquPonProjectTermination equPonProjectTerminationServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonProjectTerminationServer;
	}

	/**
	 * 报价管理-立项终止单据查询，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProjectTermination")
	public String findProjectTermination(@RequestParam(required = false) String params,
									@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"LXZZ");
			//权限校验end
			JSONObject paramsJONS =parseObject(params);
			if(paramsJONS.containsKey("id")){
				paramsJONS.put("terminationId",paramsJONS.getInteger("id"));
			}
			JSONObject result  =equPonProjectTerminationServer.findProjectTermination(paramsJONS,pageIndex,pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("status");
			efferentParam.add("statusMeaning");
			typeParam.add("EQU_PON_PROJECT_STATUS");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
			result.put("data",data);
			result.put(SToolUtils.STATUS, SUCCESS_STATUS);
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

	@RequestMapping(method = RequestMethod.POST, value = "findItProjectTermination")
	public String findItProjectTermination(@RequestParam(required = false) String params,
										 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =parseObject(params);
			if(paramsJONS.containsKey("id")){
				paramsJONS.put("terminationId",paramsJONS.getInteger("id"));
			}
			JSONObject result  =equPonProjectTerminationServer.findItProjectTermination(paramsJONS,pageIndex,pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("status");
			efferentParam.add("statusMeaning");
			typeParam.add("EQU_PON_PROJECT_STATUS");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
			result.put("data",data);
			result.put(SToolUtils.STATUS, SUCCESS_STATUS);
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
	 * 报价管理-立项终止单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveProjectTermination")
	public String saveProjectTermination(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPonProjectTerminationEntity_HI instance = equPonProjectTerminationServer.saveProjectTermination(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}
		catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 报价管理-立项终止单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteProjectTermination")
	public String deleteProjectTermination(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			Integer listId = equPonProjectTerminationServer.deleteProjectTermination(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, listId).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 立项终止审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "projectTerminationCallback")
	public String projectTerminationCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPonProjectTerminationEntity_HI entity = equPonProjectTerminationServer.projectTerminationCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "itProjectTerminationCallback")
	public String itProjectTerminationCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPonProjectTerminationEntity_HI entity = equPonProjectTerminationServer.itProjectTerminationCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}