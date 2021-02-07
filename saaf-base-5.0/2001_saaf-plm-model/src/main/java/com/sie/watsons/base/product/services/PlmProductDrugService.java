package com.sie.watsons.base.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.inter.IPlmProductDrug;

@RestController
@RequestMapping("/plmProductDrugService")
public class PlmProductDrugService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductDrugService.class);

	@Autowired
	private IPlmProductDrug plmProductDrugServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductDrugServer;
	}

}