package com.sie.watsons.base.pon.project.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProjectInfo;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equPonProjectInfoService")
public class EquPonProjectInfoService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectInfoService.class);

	@Autowired
	private IEquPonProjectInfo equPonProjectInfoServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonProjectInfoServer;
	}

	/**
	 * 报价管理-立项单据查询，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProjectInfo")
	public String findProjectInfo(@RequestParam(required = false) String params,
										  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"LXIT");
			//权限校验end
			JSONObject paramsJONS =parseObject(params);
			if(pageRows > 10){
				pageRows = 10;
			}
			if(paramsJONS.containsKey("id")){
				paramsJONS.put("projectId",paramsJONS.getInteger("id"));
			}
			JSONObject result  =equPonProjectInfoServer.findProjectInfo(paramsJONS,pageIndex,pageRows);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("projectStatus");
            efferentParam.add("projectStatusMeaning");
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
	 * 报价管理-根据立项id查询立项单据
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProjectInfoById")
	public String findProjectInfoById(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS =parseObject(params);
			JSONObject result  =equPonProjectInfoServer.findProjectInfoById(paramsJONS);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("projectStatus");
			efferentParam.add("projectStatusMeaning");
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
	 * 报价管理-立项单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveProjectInfo")
	public String saveProjectInfo(@RequestParam(required = false) String params) {
		JSONObject jsonObject = null;
		try{
			jsonObject = parseObject(params);
		}catch(JSONException ex){
			System.out.println("{\"projectInfo\":"+ params.substring(1,params.length()-1) + "}");
			jsonObject = parseObject("{\"projectInfo\":"+ params.substring(1,params.length()-1) + "}");
		}

		try {
//			JSONObject jsonObject = parseObject(params);
			EquPonProjectInfoEntity_HI instance = equPonProjectInfoServer.saveProjectInfo(jsonObject);
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

	@RequestMapping(method = RequestMethod.POST, value = "saveProjectInfos")
	public String saveProjectInfos(@RequestParam(required = false) String params) {
		JSONObject jsonObject = null;
		try{
			jsonObject = parseObject(params);
		}catch(JSONException ex){
			System.out.println("{\"projectInfo\":"+ params.substring(1,params.length()-1) + "}");
			jsonObject = parseObject("{\"projectInfo\":"+ params.substring(1,params.length()-1) + "}");
		}

		try {
//			JSONObject jsonObject = parseObject(params);
			EquPonProjectInfoEntity_HI instance = equPonProjectInfoServer.saveProjectInfos(jsonObject);
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
	 * 报价管理-立项单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteProjectInfo")
	public String deleteProjectInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			Integer listId = equPonProjectInfoServer.deleteProjectInfo(jsonObject);
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
	 * 报价管理-立项单据终止
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "terminateProjectInfo")
	public String terminateProjectInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonProjectInfoServer.terminateProjectInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		}  catch (IllegalArgumentException e) {
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
			EquPonProjectInfoEntity_HI entity = equPonProjectInfoServer.projectApprovalCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findBrandTeamApproval")
	public String findBrandTeamApproval(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS = parseObject(params);
//			paramsJONS.put("projectId",params);
			JSONObject result  =equPonProjectInfoServer.findProjectInfoById(paramsJONS);
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

	@RequestMapping(method = RequestMethod.POST, value = "findWitnessApproval")
	public String findWitnessApproval(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			JSONObject result  =equPonProjectInfoServer.findWitnessApproval(paramsObject);
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

	@RequestMapping(method = RequestMethod.POST, value = "findProjectWitnessApproval")
	public String findProjectWitnessApproval(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			JSONObject result  =equPonProjectInfoServer.findProjectWitnessApproval(paramsObject);
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

	@RequestMapping(method = RequestMethod.POST, value = "findProjectScoringApproval")
	public String findProjectScoringApproval(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			JSONObject result  =equPonProjectInfoServer.findProjectScoringApproval(paramsObject);
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

	@RequestMapping(method = RequestMethod.POST, value = "findQuoInformationWitnessApproval")
	public String findQuoInformationWitnessApproval(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			JSONObject result  =equPonProjectInfoServer.findQuoInformationWitnessApproval(paramsObject);
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
			JSONObject result  =equPonProjectInfoServer.findQuoInformationDefaultWitnessApproval(paramsObject);
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
	@RequestMapping(method = RequestMethod.POST, value = "findPonNavigationMenuNodeList")
	public String findPonNavigationMenuNodeList(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPonProjectInfoServer.findPonNavigationMenuNodeList(paramsJONS);
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
	 * 供应商报价管理查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierQuotationList")
	public String findSupplierQuotationList(@RequestParam(required = false) String params,
										  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =parseObject(params);
			JSONObject result  =equPonProjectInfoServer.findSupplierQuotationList(paramsJONS,pageIndex,pageRows);

			JSONArray data = result.getJSONArray("data");
			for(int i = 0; i < data.size(); i++){
				try {
					JSONObject resultJson = data.getJSONObject(i);
					List<String> incomingParam = new ArrayList<>();
					List<String> efferentParam = new ArrayList<>();
					List<String> typeParam = new ArrayList<>();
					if (resultJson.getString("currentHandleOrderNumber").startsWith("LX")) {
						typeParam.add("EQU_PON_PROJECT_STATUS");
					} else if (resultJson.getString("currentHandleOrderNumber").startsWith("KQ")) {
						typeParam.add("EQU_PON_QUOTATION_STATUS");
					} else if (resultJson.getString("currentHandleOrderNumber").startsWith("PF")) {
						typeParam.add("EQU_SCORING_ORDER_STATUS");
					} else if (resultJson.getString("currentHandleOrderNumber").startsWith("JGSP")) {
						typeParam.add("EQU_PON_QUOTATION_STATUS");
					}
					List<JSONObject> list = ResultUtils.getReturnJson(incomingParam, efferentParam, typeParam);
					JSONObject currentHandleOrderStatusJson = list.size() > 0 ? list.get(0) : new JSONObject();
					resultJson.put("currentHandleOrderStatusMeaning", currentHandleOrderStatusJson.getString(resultJson.getString("currentHandleOrderStatus")));
				} catch (Exception e) {

				}
			}

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
	 * 校验用户操作权限
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "validateProjectUserOperator")
	public String validateProjectUserOperator(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS =parseObject(params);
			String result  =equPonProjectInfoServer.validateProjectUserOperator(paramsJONS);
			JSONObject resultJson = new JSONObject();
			resultJson.put("result",result);
			resultJson.put(SToolUtils.STATUS, "S");
			resultJson.put(SToolUtils.MSG, SUCCESS_MSG);
			return resultJson.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}
}