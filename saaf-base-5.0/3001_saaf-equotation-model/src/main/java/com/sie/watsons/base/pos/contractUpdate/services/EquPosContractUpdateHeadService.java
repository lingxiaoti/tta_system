package com.sie.watsons.base.pos.contractUpdate.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.contractUpdate.model.entities.readonly.EquPosContractUpdateHeadEntity_HI_RO;
import com.sie.watsons.base.pos.contractUpdate.model.inter.IEquPosContractUpdateHead;
import com.sie.watsons.base.pos.utils.CommonEnum;
import com.sie.watsons.base.pos.utils.CommonMethods;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPosContractUpdateHeadService")
public class EquPosContractUpdateHeadService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosContractUpdateHeadService.class);
	@Autowired
	private IEquPosContractUpdateHead equPosContractUpdateHeadServer;
	@Autowired
	private CommonMethods commonMethods;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosContractUpdateHeadServer;
	}

	@PostMapping("/findContractUpdatePagination")
	public String findContractUpdatePagination(@RequestParam String params,
											  @RequestParam(defaultValue = "1",required = false) Integer pageIndex,
											  @RequestParam(defaultValue = "10",required = false) Integer pageRows){
		try {
			JSONObject jsonObject = parseObject(params);
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"COMMERCIALCONTRACTDRGX");
			//权限校验end
			jsonObject = equPosContractUpdateHeadServer.findContractUpdatePagination(jsonObject,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("docStatus");
			efferentParam.add("docStatusMeaning");
			typeParam.add("EQU_QUALITY_AUDIT_STATUS");
			JSONArray data = jsonObject.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
			jsonObject.put("data",data);

			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("/findContractUpdateHeadInfo")
	public String findContractUpdateHeadInfo(@RequestParam("editParams") String editParams){
		try {
			JSONObject jsonObject = parseObject(editParams);
			EquPosContractUpdateHeadEntity_HI_RO contractUpdateHeadInfo = equPosContractUpdateHeadServer.findContractUpdateHeadInfo(jsonObject);

			JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(contractUpdateHeadInfo));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("docStatus");
			efferentParam.add("docStatusMeaning");
			typeParam.add("EQU_QUALITY_AUDIT_STATUS");
			json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, json).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("/saveContractUpdateHeadAndLine")
	public String saveContractUpdateHeadAndLine(@RequestParam("editParams") String editParams) {
		try {
			Integer userId = getUserSessionBean().getUserId();
			Integer updateHeadId = equPosContractUpdateHeadServer.saveContractUpdateHeadAndLine(editParams,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, updateHeadId).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.info(e.getMessage());
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("/deleteContractUpdateHead")
	public String deleteContractUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			if (jsonObject.getString("docStatus") != null && !"DRAFT".equals(jsonObject.getString("docStatus"))) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "只有单据状态为拟定时才可以删除", 0, null).toString();
			}
			return equPosContractUpdateHeadServer.deleteContractUpdate(jsonObject, userId);
		} catch (IllegalArgumentException e) {
			LOGGER.info(e.getMessage());
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("/fileUploadForContractUpdateHead")
	public String fileUploadForContractUpdateHead(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getSessionUserId();
        return commonMethods.fileUpload(file, request, response, CommonEnum.EQU_POS_CONTRACT_UPDATE_HEAD.getValue(), "EquPosContractUpdateHeadService",userId);
	}

	@PostMapping("/fileUploadForContractUpdateLine")
	public String fileUploadForContractUpdateLine(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getSessionUserId();
		return commonMethods.fileUpload(file, request, response, CommonEnum.EQU_POS_CONTRACT_UPDATE_LINE.getValue(), "EquPosContractUpdateHeadService",userId);
	}

}