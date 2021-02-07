package com.sie.watsons.base.plmBase.services;

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
import com.sie.watsons.base.plmBase.model.entities.PlmBaseSeriesEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseSeriesEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBaseSeries;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmBaseSeriesService")
public class PlmBaseSeriesService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseSeriesService.class);

	@Autowired
	private IPlmBaseSeries plmBaseSeriesServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmBaseSeriesServer;
	}

	// return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
	// e.getMessage()).toString();

	@RequestMapping(method = RequestMethod.POST, value = "findPlmseriesInfo")
	public String findPlmSalesAreaInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmBaseSeriesEntity_HI_RO> dataList = plmBaseSeriesServer
				.findPlmseriesInfo(queryParamJSON, pageIndex, pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "savePlmseriesInfo")
	public String savePlmSalesAreaInfo(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			PlmBaseSeriesEntity_HI obj = plmBaseSeriesServer
					.saveOrUpdate(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					obj).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}

	}

	// 查找
	@RequestMapping(method = RequestMethod.POST, value = "findPlmseriesInfoById")
	public String findPlmseriesInfoById(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			Integer id = queryParamJSON.getInteger("id");
			PlmBaseSeriesEntity_HI l = plmBaseSeriesServer.getById(id);
			return SToolUtils
					.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, l)
					.toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

	// 删除
	@RequestMapping(method = RequestMethod.POST, value = "delPlmseriesInfoById")
	public String delPlmseriesInfoById(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			Integer id = queryParamJSON.getInteger("id");
			plmBaseSeriesServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					id).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}
}