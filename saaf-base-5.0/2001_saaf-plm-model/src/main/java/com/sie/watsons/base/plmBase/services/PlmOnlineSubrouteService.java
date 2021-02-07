package com.sie.watsons.base.plmBase.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineSubrouteEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmOnlineSubrouteEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmOnlineSubroute;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmOnlineSubrouteService")
public class PlmOnlineSubrouteService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmOnlineSubrouteService.class);
	@Autowired
	private IPlmOnlineSubroute plmOnlineSubrouteServer;

	public PlmOnlineSubrouteService() {
		super();
	}

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmOnlineSubrouteServer;
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            { teamFrameworkId:框架ID }
	 * @param pageIndex
	 *            当前页码
	 * @param pageRows
	 *            页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmOnlineSubrouteInfo")
	public String findPlmOnlineSubrouteInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmOnlineSubrouteEntity_HI_RO> dataList = plmOnlineSubrouteServer
				.findPlmOnlineSubrouteInfo(queryParamJSON, pageIndex, pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 保存
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "savePlmOnlineSubrouteInfo")
	public String savePlmOnlineSubrouteInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			// int userId = parseObject.getInteger("varUserId");
			JSONArray jsonArray = (JSONArray) JSONArray
					.toJSON(plmOnlineSubrouteServer
							.savePlmOnlineSubrouteInfo(parseObject));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,
					jsonArray).toString();

		} catch (IllegalArgumentException e) {
			return getException(e, LOGGER);
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 批量删除
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "deletePlmOnlineSubrouteInfo")
	public String deletePlmOnlineSubrouteInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			int size = plmOnlineSubrouteServer
					.deletePlmOnlineSubrouteInfo(parseObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, size)
					.toString();
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

	// 生效失效
	// updatePlmOnlineSubrouteInfo
	/**
	 * @param params
	 * @return java.lang.String
	 * @description 生效失效
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "updatePlmOnlineSubrouteInfo")
	public String updatePlmOnlineSubrouteInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			String result = plmOnlineSubrouteServer
					.updatePlmOnlineSubrouteInfo(parseObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result)
					.toString();
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

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 单条删除
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "deletePlmOnlineSubrouteInfoById")
	public String deletePlmOnlineSubrouteInfoById(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			// int size = plmOnlineSubrouteServer
			// .deletePlmOnlineSubrouteInfoById(parseObject);
			Integer subroutId = parseObject.getInteger("plmOnlineSubrouteId");
			plmOnlineSubrouteServer.deleteById(subroutId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, 1)
					.toString();
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

	// 生效或失效

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 单条删除
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "updatePlmOnlineSubrouteInfoById")
	public String updatePlmOnlineSubrouteInfoById(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			Integer subroutId = parseObject.getInteger("plmOnlineSubrouteId");
			String type = parseObject.getString("type");
			if (type.equals("vli")) // 生效
			{
				PlmOnlineSubrouteEntity_HI sub = plmOnlineSubrouteServer
						.getById(subroutId);
				sub.setBillStatus("Y");
				sub.setBillStatusName("已生效");
				plmOnlineSubrouteServer.update(sub);
			} else {
				PlmOnlineSubrouteEntity_HI sub = plmOnlineSubrouteServer
						.getById(subroutId);
				sub.setBillStatus("N");
				sub.setBillStatusName("已失效");
				plmOnlineSubrouteServer.update(sub);
			}

			// plmOnlineSubrouteServer.deleteById(subroutId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, 1)
					.toString();
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
