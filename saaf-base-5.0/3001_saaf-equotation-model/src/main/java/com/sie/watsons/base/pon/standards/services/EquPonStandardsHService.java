package com.sie.watsons.base.pon.standards.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsHEntity_HI;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsHEntity_HI_RO;
import com.sie.watsons.base.pon.standards.model.inter.IEquPonStandardsH;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPonStandardsHService")
public class EquPonStandardsHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonStandardsHService.class);

	@Autowired
	private IEquPonStandardsH equPonStandardsHServer;

	@Autowired
    private StringRedisTemplate stringRedisTemplate;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonStandardsHServer;
	}

	/**
	 * 查询评分标准（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPonStandardsInfo")
	public String findPonStandardsInfo(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"ZZSC");
			//权限校验end
			if(pageRows > 10){
				pageRows = 10;
			}
			Pagination<EquPonStandardsHEntity_HI_RO> infoList = equPonStandardsHServer.findPonStandardsInfo(params, pageIndex, pageRows);

			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("standardsStatus");
            efferentParam.add("standardsStatusName");
            typeParam.add("EDU_PON_STANDARD_STATUS");
            JSONArray data = returnJson.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
            returnJson.put("data",data);
            returnJson.put(SToolUtils.STATUS, SUCCESS_STATUS);
            returnJson.put(SToolUtils.MSG, SUCCESS_MSG);
            return returnJson.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询评分标准详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPonStandardsDatail")
	public String findPonStandardsDatail(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			JSONObject returnJson =  equPonStandardsHServer.findPonStandardsDatail(params);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("standardsStatus");
            efferentParam.add("standardsStatusName");
            typeParam.add("EDU_PON_STANDARD_STATUS");
//            hEntity
			JSONObject hEntity = returnJson.getJSONObject("hEntity");
			hEntity = ResultUtils.getLookUpValues(hEntity,incomingParam,efferentParam,typeParam);
			returnJson.put("hEntity",hEntity);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, returnJson).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询评分标准详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPonStandardsDatailByCode")
	public String findPonStandardsDatailByCode(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			JSONObject result  =equPonStandardsHServer.findPonStandardsDatailByCode(paramsObject);
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
	 * 提交评分标准详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "savePonStandardsSubmit")
	public String savePonStandardsSubmit(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject returnJson =  equPonStandardsHServer.savePonStandardsSubmit(jsonObject,userId);
			return   SToolUtils.convertResultJSONObj(returnJson.getString("status"), returnJson.getString("mes"), 0, returnJson).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存评分标准详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "savePonStandards")
	public String savePonStandards(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject returnJson =  equPonStandardsHServer.savePonStandards(jsonObject,userId);
			return   SToolUtils.convertResultJSONObj(returnJson.getString("status"), returnJson.getString("mes"), 0, returnJson).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存评分标准详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deletePonStandardsLine")
	public String deletePonStandardsLine(@RequestParam(required = false) String params) {
		LOGGER.info(params);
		try {
			int userId = getSessionUserId();

			JSONObject jsonObject = JSON.parseObject(params);
			if(jsonObject.get("standardsLId")==null){
				return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "删除成功", 0, null).toString();
			}
			equPonStandardsHServer.deletePonStandardsLine(jsonObject,userId);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "删除成功", 0, null).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * @param params
	 * @description 删除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deletePonStandards")
	public String deletePonStandards(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Integer listId = equPonStandardsHServer.deletePonStandards(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, listId).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询评分标准LOV
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findCopyStandardsLov")
	public String findCopyStandardsLov(@RequestParam(required = false) String params) {
		try {
			JSONObject returnJson =  equPonStandardsHServer.findPonStandardsDatail(params);
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject json =  equPonStandardsHServer.returnCopyDatail(returnJson);
			equPonStandardsHServer.deleteCopyStandardsDatail(jsonObject);
			return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, json).toString(); //JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 评分标准审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "standardsApprovalCallback")
	public String standardsApprovalCallback(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPonStandardsHEntity_HI entity = equPonStandardsHServer.standardsApprovalCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}