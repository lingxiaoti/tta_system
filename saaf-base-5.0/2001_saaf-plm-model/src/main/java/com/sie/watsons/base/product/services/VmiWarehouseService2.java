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
import com.sie.watsons.base.product.model.entities.readonly.VmiWarehouseEntity_HI_RO2;
import com.sie.watsons.base.product.model.inter.IVmiWarehouse2;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/vmiWarehouseService2")
public class VmiWarehouseService2 extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiWarehouseService2.class);

	@Autowired
	private IVmiWarehouse2 vmiWarehouseServer2;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.vmiWarehouseServer2;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findWarehouseinfo")
	public String findWarehouseinfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<VmiWarehouseEntity_HI_RO2> dataList = vmiWarehouseServer2
				.findWarehouseinfo(queryParamJSON, pageIndex, pageRows);
		// ResultUtils.getCreator(queryParamJSON.getInteger("varUserId"));
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}
}