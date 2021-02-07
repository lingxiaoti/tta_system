package com.sie.watsons.base.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.inter.IPlmProductLog;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/plmProductLogService")
public class PlmProductLogService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductLogService.class);

	@Autowired
	private IPlmProductLog plmProductLogServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductLogServer;
	}

}