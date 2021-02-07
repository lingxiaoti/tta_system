package com.sie.watsons.base.product.services;

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
import com.sie.watsons.base.api.model.entities.readonly.PlmUdaAttributeEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmUdaAttribute;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmUdaAttributeService")
public class PlmUdaAttributeService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmUdaAttributeService.class);

	@Autowired
	private IPlmUdaAttribute plmUdaAttributeServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmUdaAttributeServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findUdaById")
	public String findUdaById(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmUdaAttributeEntity_HI_RO> dataList = plmUdaAttributeServer
				.findUdaById(queryParamJSON, pageIndex, pageRows);
		// ResultUtils.getCreator(queryParamJSON.getInteger("varUserId"));
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

}