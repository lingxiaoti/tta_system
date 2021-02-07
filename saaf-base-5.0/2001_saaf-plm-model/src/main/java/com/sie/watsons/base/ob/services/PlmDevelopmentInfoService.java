package com.sie.watsons.base.ob.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentInfoEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentInfo;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmDevelopmentInfoService")
public class PlmDevelopmentInfoService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDevelopmentInfoService.class);
	@Autowired
	private IPlmDevelopmentInfo plmDevelopmentInfoServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmDevelopmentInfoServer;
	}

	/**
	 * 查询OB开发头
	 * 
	 * @param params
	 *            { teamFrameworkId:框架ID }
	 * @param pageIndex
	 *            当前页码
	 * @param pageRows
	 *            页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmDevelopmentInfoInfo")
	public String findPlmDevelopmentInfoInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<PlmDevelopmentInfoEntity_HI_RO> PlmDevelopmentInfoList = plmDevelopmentInfoServer
					.findPlmDevelopmentInfoInfo(queryParamJSON, pageIndex,
							pageRows);
			ResultUtils.getLookUpValue("PLM_OB_PRODUCT_BILL_STATUS");
			ResultUtils.getLookUpValue("PLM_DEVE_PRODUCT_STATUS");
			ResultUtils.getLookUpValue("PLM_PROJECT_PRODUCT_CATEGORY");

			queryParamJSON = (JSONObject) JSON.toJSON(PlmDevelopmentInfoList);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmDevelopmentInfoInfoNew")
	public String findPlmDevelopmentInfoInfoNew(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<PlmDevelopmentInfoEntity_HI_RO> PlmDevelopmentInfoList = plmDevelopmentInfoServer
					.findPlmDevelopmentInfoInfoNew(queryParamJSON, pageIndex,
							pageRows);

			queryParamJSON = (JSONObject) JSON.toJSON(PlmDevelopmentInfoList);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 保存OB开发头
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "savePlmDevelopmentInfoInfo")
	public String savePlmDevelopmentInfoInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			int userId = parseObject.getInteger("varUserId");
			JSONObject jsonObject = (JSONObject) JSONObject
					.toJSON(plmDevelopmentInfoServer
							.savePlmDevelopmentInfoInfo(parseObject));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,
					jsonObject).toString();

		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmDevelopmentInfoByObId")
	public String findPlmDevelopmentInfoByObId(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			String obId = parseObject.getString("obId");
			JSONObject j = new JSONObject();
			j.put("obId", obId);
			List<PlmDevelopmentInfoEntity_HI> ls = plmDevelopmentInfoServer
					.findList(j);
			if (ls.size() > 0) {
				return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,
						ls.get(0)).toString();
			} else {
				return SToolUtils.convertResultJSONObj("E", "数据为空!", 0, null)
						.toString();
			}

		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}