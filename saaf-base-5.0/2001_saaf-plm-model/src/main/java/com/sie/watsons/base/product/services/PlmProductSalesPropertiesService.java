package com.sie.watsons.base.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.inter.IPlmProductSalesProperties;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductSalesPropertiesService")
public class PlmProductSalesPropertiesService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductSalesPropertiesService.class);

	@Autowired
	private IPlmProductSalesProperties plmProductSalesPropertiesServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductSalesPropertiesServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteProductSalePropertiesById")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("id");
			plmProductSalesPropertiesServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}
}