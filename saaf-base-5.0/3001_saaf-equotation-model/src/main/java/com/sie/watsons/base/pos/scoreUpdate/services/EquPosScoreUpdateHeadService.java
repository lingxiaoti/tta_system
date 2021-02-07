package com.sie.watsons.base.pos.scoreUpdate.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.readonly.EquPosScoreUpdateHeadEntity_HI_RO;
import com.sie.watsons.base.pos.scoreUpdate.model.inter.IEquPosScoreUpdateHead;
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
@RequestMapping("/equPosScoreUpdateHeadService")
public class EquPosScoreUpdateHeadService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosScoreUpdateHeadService.class);
	@Autowired
	private IEquPosScoreUpdateHead equPosScoreUpdateHeadServer;
	@Autowired
	private CommonMethods commonMethods;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosScoreUpdateHeadServer;
	}

	@PostMapping("/findScoreUpdatePagination")
	public String findScoreUpdatePagination(@RequestParam String params,
											  @RequestParam(defaultValue = "1",required = false) Integer pageIndex,
											  @RequestParam(defaultValue = "10",required = false) Integer pageRows){
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"NDPFDRGX");
			//权限校验end
			JSONObject jsonObject = parseObject(params);
			jsonObject = equPosScoreUpdateHeadServer.findScoreUpdatePagination(jsonObject,pageIndex,pageRows);

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

	@PostMapping("/findScoreUpdateHeadInfo")
	public String findScoreUpdateHeadInfo(@RequestParam("editParams") String editParams){
		try {
			JSONObject jsonObject = parseObject(editParams);
			EquPosScoreUpdateHeadEntity_HI_RO entityHiRo = equPosScoreUpdateHeadServer.findScoreUpdateHeadInfo(jsonObject);

			JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(entityHiRo));
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

	@PostMapping("/saveScoreUpdateHeadAndLine")
	public String saveScoreUpdateHeadAndLine(@RequestParam("editParams") String editParams) {
		try {
			Integer userId = getUserSessionBean().getUserId();
			Integer updateHeadId = equPosScoreUpdateHeadServer.saveScoreUpdateHeadAndLine(editParams,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, updateHeadId).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.info(e.getMessage());
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("/deleteScoreUpdateHead")
	public String deleteScoreUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			if (jsonObject.getString("docStatus") != null && !"DRAFT".equals(jsonObject.getString("docStatus"))) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "只有单据状态为拟定时才可以删除", 0, null).toString();
			}
			return equPosScoreUpdateHeadServer.deleteScoreUpdate(jsonObject, userId);
		} catch (IllegalArgumentException e) {
			LOGGER.info(e.getMessage());
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("/fileUploadForScoreUpdateHead")
	public String fileUploadForScoreUpdateHead(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getSessionUserId();
		return commonMethods.fileUpload(file, request, response, CommonEnum.EQU_POS_SCORE_UPDATE_HEAD.getValue(), "EquPosScoreUpdateHeadService",userId);
	}

	@PostMapping("/fileUploadForScoreUpdateLine")
	public String fileUploadForScoreUpdateLine(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getSessionUserId();
		return commonMethods.fileUpload(file, request, response, CommonEnum.EQU_POS_SCORE_UPDATE_LINE.getValue(), "EquPosScoreUpdateHeadService",userId);
	}

}