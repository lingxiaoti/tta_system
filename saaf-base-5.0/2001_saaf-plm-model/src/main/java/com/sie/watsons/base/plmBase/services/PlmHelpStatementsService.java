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
import com.sie.watsons.base.plmBase.model.entities.PlmHelpStatementsEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmHelpStatementsEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmHelpStatements;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * @author panshenghao
 */
@RestController
@RequestMapping("/plmHelpStatementsService")
public class PlmHelpStatementsService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmHelpStatementsService.class);
	@Autowired
	private IPlmHelpStatements plmHelpStatementsServer;

	public PlmHelpStatementsService() {
		super();
	}

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmHelpStatementsServer;
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
	@RequestMapping(method = RequestMethod.POST, value = "findPlmHelpStatementsInfo")
	public String findPlmHelpStatementsInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParams = parseObject(params);
		Pagination<PlmHelpStatementsEntity_HI_RO> dataList = plmHelpStatementsServer
				.findPlmHelpStatementsInfo(queryParams, pageIndex, pageRows);
		queryParams = (JSONObject) JSON.toJSON(dataList);
		queryParams.put(SToolUtils.STATUS, "S");
		queryParams.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParams.toString();
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 保存
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "savePlmHelpStatementsInfo")
	public String savePlmHelpStatementsInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			int userId = parseObject.getInteger("varUserId");
			JSONObject jsonObject = (JSONObject) JSONObject
					.toJSON(plmHelpStatementsServer
							.savePlmHelpStatementsInfo(parseObject));
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

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 作废
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "updatePlmHelpStatementsInfo")
	public String updatePlmHelpStatementsInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);

			JSONArray headlist = parseObject.getJSONArray("headlist");
			for (int i = 0; i < headlist.size(); i++) {
				JSONObject headjson = headlist.getJSONObject(i);
				Integer id = headjson.getInteger("plmHelpStatementsId");
				PlmHelpStatementsEntity_HI obj = plmHelpStatementsServer
						.getById(id);
				obj.setBillStatusName("已作废");

				plmHelpStatementsServer.update(obj);
			}

			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null)
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

	// 批量删除
	/**
	 * @param params
	 * @return java.lang.String
	 * @description 删除
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "deletePlmHelpStatementsInfo")
	public String deletePlmHelpStatementsInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			JSONArray headlist = parseObject.getJSONArray("headlist");
			for (int i = 0; i < headlist.size(); i++) {
				JSONObject headjson = headlist.getJSONObject(i);
				Integer id = headjson.getInteger("plmHelpStatementsId");
				plmHelpStatementsServer.deleteById(id);
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null)
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
