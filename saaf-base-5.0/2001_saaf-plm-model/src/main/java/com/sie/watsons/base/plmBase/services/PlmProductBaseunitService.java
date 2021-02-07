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
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmProductBaseunitEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmProductBaseunit;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmProductBaseunitService")
public class PlmProductBaseunitService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBaseunitService.class);

	@Autowired
	private IPlmProductBaseunit plmProductBaseunitServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductBaseunitServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmProductBaseunit")
	public String findPlmOnlineSubrouteInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmProductBaseunitEntity_HI_RO> dataList = plmProductBaseunitServer
				.findPlmProductBaseunit(queryParamJSON, pageIndex, pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}
}