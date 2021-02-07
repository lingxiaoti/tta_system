package com.sie.watsons.base.pon.information.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationRejectionEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationInformationEntity_HI_RO;
import com.sie.watsons.base.pon.information.model.inter.IEquPonQuotationInformation;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectAttachmentEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
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
@RequestMapping("/equPonQuotationInformationService")
public class EquPonQuotationInformationService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInformationService.class);

	@Autowired
	private IEquPonQuotationInformation equPonQuotationInformationServer;


	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonQuotationInformationServer;
	}



	/**
	 * 查询报价资料开启（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPonInformationInfo")
	public String findPonInformationInfo(@RequestParam(required = false) String params,
									   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"BJZLKQ");
			//权限校验end
			Pagination<EquPonQuotationInformationEntity_HI_RO> infoList = equPonQuotationInformationServer.findPonInformationInfo(params, pageIndex, pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("informationStatus");
			incomingParam.add("projectCategory");
			incomingParam.add("sensoryTestTypes");
			efferentParam.add("informationStatusName");
			efferentParam.add("projectCategoryName");
			efferentParam.add("sensoryTestTypesName");
			typeParam.add("EQU_PON_QUOTATION_STATUS");
			typeParam.add("EQU_PROJECT_TYPE");
			typeParam.add("EQU_SENSORY_TEST_TYPE");
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			returnJson.put("data",data);
			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询报价资料开启（区分部门）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPonInformationInfoByDeptCode")
	public String findPonInformationInfoByDeptCode(@RequestParam(required = false) String params,
										 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			Pagination<EquPonQuotationInformationEntity_HI_RO> infoList = equPonQuotationInformationServer.findPonInformationInfoByDeptCode(params, pageIndex, pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("informationStatus");
			efferentParam.add("informationStatusName");
			typeParam.add("EQU_PON_QUOTATION_STATUS");
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			returnJson.put("data",data);
			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询报价资料开启（IT）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPonItInformationInfo")
	public String findPonItInformationInfo(@RequestParam(required = false) String params,
										 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			Pagination<EquPonQuotationInformationEntity_HI_RO> infoList = equPonQuotationInformationServer.findPonItInformationInfo(params, pageIndex, pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("informationStatus");
			incomingParam.add("projectCategory");
			incomingParam.add("sensoryTestTypes");
			efferentParam.add("informationStatusName");
			efferentParam.add("projectCategoryName");
			efferentParam.add("sensoryTestTypesName");
			typeParam.add("EQU_PON_QUOTATION_STATUS");
			typeParam.add("EQU_PROJECT_TYPE");
			typeParam.add("EQU_SENSORY_TEST_TYPE");
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues2(  data ,   incomingParam,  efferentParam,  typeParam);
			returnJson.put("data",data);
			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询驳回原因
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findRejectionReason")
	public String findRejectionReason(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			Pagination<EquPonInformationRejectionEntity_HI> infoList = equPonQuotationInformationServer.findRejectionReason(params);
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findInformationIdDatail")
	public String findInformationIdDatail(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			JSONObject returnJson =  equPonQuotationInformationServer.findInformationIdDatail(params);



			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, returnJson.getString("scoringFlag"), 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询报价资料开启详情(IT)
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItInformationIdDatail")
	public String findItInformationIdDatail(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			JSONObject returnJson =  equPonQuotationInformationServer.findItInformationIdDatail(params);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, returnJson.getString("scoringFlag"), 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 *  详情保存
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "savePonInvitation")
	public String savePonInvitation(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject returnJson =  equPonQuotationInformationServer.savePonInvitation(params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("headerData")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  详情保存(IT)
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "savePonItInvitation")
	public String savePonItInvitation(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject jsonParams = parseObject(params);
			JSONObject returnJson =  equPonQuotationInformationServer.savePonItInvitation(jsonParams,params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("headerData")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  报价资料开启驳回
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveRejectPonInvitation")
	public String saveRejectPonInvitation(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject returnJson =  equPonQuotationInformationServer.saveRejectPonInvitation(params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("headerData")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  报价资料开启驳回
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveItRejectPonInvitation")
	public String saveItRejectPonInvitation(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject returnJson =  equPonQuotationInformationServer.saveItRejectPonInvitation(params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("headerData")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}



	/**
	 * 退出供应商的时候把对应的所有的项目版本退出,还要把所有的报价终止
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveProjectSupplierQuit")
	public String saveProjectSupplierQuit(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonQuotationInformationServer.saveProjectSupplierQuit(jsonObject,getSessionUserId());
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
	 * 退出供应商的时候把对应的所有的项目版本退出,还要把所有的报价终止
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveItProjectSupplierQuit")
	public String saveItProjectSupplierQuit(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonQuotationInformationServer.saveItProjectSupplierQuit(jsonObject,getSessionUserId());
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
	 *  监控报价允许供应商修改报价
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateSupplierQuotation")
	public String updateSupplierQuotation(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject returnJson =  equPonQuotationInformationServer.updateSupplierQuotation(params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  监控报价允许供应商修改报价
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateSupplierQuotationIt")
	public String updateSupplierQuotationIt(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject returnJson =  equPonQuotationInformationServer.updateSupplierQuotationIt(params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 *  保存评分见证人信息
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveWitnessTeamData")
	public String saveWitnessTeamData(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject returnJson =  equPonQuotationInformationServer.saveWitnessTeamData(params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, null).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  详情保存
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveItWitnessTeamData")
	public String saveItWitnessTeamData(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			String userNumber = geSessiontUserName();
			JSONObject returnJson =  equPonQuotationInformationServer.saveItWitnessTeamData(params,userId,userNumber);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, null).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询监控报价详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBidSupplierList")
	public String findBidSupplierList(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			JSONObject returnJson =  equPonQuotationInformationServer.findBidSupplierList(params);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询（IT）监控报价
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItBidSupplierList")
	public String findItBidSupplierList(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			JSONObject returnJson =  equPonQuotationInformationServer.findItBidSupplierList(params);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}






	/**
	 * @param params
	 * @description 删除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteInformation")
	public String deleteInformation(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			equPonQuotationInformationServer.deleteInformation(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 立项lov查询
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findMaxProjectInfo")
	public String findMaxProjectInfo(@RequestParam(required = false) String params,
									 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			Pagination<EquPonQuotationInformationEntity_HI_RO> infoList = equPonQuotationInformationServer.findMaxProjectInfo(params, pageIndex, pageRows);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(infoList));
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("projectStatus");
            efferentParam.add("projectStatusMeaning");
            typeParam.add("EQU_PON_PROJECT_STATUS");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
            jsonObject.put("data",data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();

//			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
//			returnJson.put("status","S");
//			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findMaxItProjectInfo")
	public String findMaxItProjectInfo(@RequestParam(required = false) String params,
									 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			Pagination<EquPonQuotationInformationEntity_HI_RO> infoList = equPonQuotationInformationServer.findMaxItProjectInfo(params, pageIndex, pageRows);
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(infoList));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("projectStatus");
			efferentParam.add("projectStatusMeaning");
			typeParam.add("EQU_PON_PROJECT_STATUS");
			JSONArray data = jsonObject.getJSONArray("data");
			data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
			jsonObject.put("data",data);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();

//			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
//			returnJson.put("status","S");
//			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveItQuotationScoreCalculate")
	public String saveItQuotationScoreCalculate(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonQuotationInformationServer.saveQuotationScoreCalculate(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}


}