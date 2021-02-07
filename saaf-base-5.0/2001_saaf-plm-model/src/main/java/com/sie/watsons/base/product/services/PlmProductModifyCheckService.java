package com.sie.watsons.base.product.services;

import java.util.List;

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
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductModifyCheckEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductModifyCheck;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductModifyCheckService")
public class PlmProductModifyCheckService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductModifyCheckService.class);

	@Autowired
	private IPlmProductModifyCheck plmProductModifyCheckServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductModifyCheckServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findProductModifyCheckByEcoId")
	public String findProductModifyCheckByEcoId(
			@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = JSONObject.parseObject(params);

			SaafToolUtils.validateJsonParms(jsonObject, "ecoId");
			List<PlmProductModifyCheckEntity_HI> result = plmProductModifyCheckServer
					.findProductModifyCheckByEcoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", "保存成功", result.size(),
					result).toJSONString();
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

}