package com.sie.watsons.base.pon.itproject.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItProjectInfo;

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
@RequestMapping("/equPonItProjectInfoService")
public class EquPonItProjectInfoService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItProjectInfoService.class);

	@Autowired
	private IEquPonItProjectInfo equPonItProjectInfoServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonItProjectInfoServer;
	}

	/**
	 * IT报价管理-立项单据查询，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItProjectInfo")
	public String findItProjectInfo(@RequestParam(required = false) String params,
								  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =parseObject(params);

			if(paramsJONS.containsKey("id")){
				paramsJONS.put("projectId",paramsJONS.getInteger("id"));
			}
			JSONObject result  =equPonItProjectInfoServer.findItProjectInfo(paramsJONS,pageIndex,pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("projectStatus");
			efferentParam.add("projectStatusMeaning");
			typeParam.add("EQU_PON_PROJECT_STATUS");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues2(data,incomingParam,efferentParam,typeParam);
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
	 * IT报价管理-立项单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveItProjectInfo")
	public String saveItProjectInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPonItProjectInfoEntity_HI instance = equPonItProjectInfoServer.saveItProjectInfo(jsonObject);
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
	 * IT报价管理-立项单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteItProjectInfo")
	public String deleteItProjectInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			Integer listId = equPonItProjectInfoServer.deleteItProjectInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, listId).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findRelateDeptBussinessApproval")
	public String findRelateDeptBussinessApproval(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			JSONObject result  =equPonItProjectInfoServer.findRelateDeptBussinessApproval(paramsObject);
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
	 * 联合部门LOV，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findUnionDepartmentLov")
	public String findUnionDepartmentLov(@RequestParam(required = false) String params,
									   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject param = new JSONObject();
			paramsJONS.put("pageIndex", pageIndex);
			param.put("params", paramsJONS);
			JSONObject result = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUnionDepartmentInfo", param);
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

	@RequestMapping(method = RequestMethod.POST, value = "findQuoInformationDefaultWitnessApproval")
	public String findQuoInformationDefaultWitnessApproval(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			JSONObject result  =equPonItProjectInfoServer.findQuoInformationDefaultWitnessApproval(paramsObject);
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
	 * 查找导航菜单节点
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPonItNavigationMenuNodeList")
	public String findPonItNavigationMenuNodeList(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPonItProjectInfoServer.findPonItNavigationMenuNodeList(paramsJONS);
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
	 * 立项审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "projectApprovalCallback")
	public String projectApprovalCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPonItProjectInfoEntity_HI entity = equPonItProjectInfoServer.projectApprovalCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}