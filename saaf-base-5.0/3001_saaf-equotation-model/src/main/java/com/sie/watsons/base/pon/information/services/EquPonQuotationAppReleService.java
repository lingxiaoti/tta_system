package com.sie.watsons.base.pon.information.services;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.information.model.inter.IEquPonQuotationAppRele;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equPonQuotationAppReleService")
public class EquPonQuotationAppReleService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationAppReleService.class);

	@Autowired
	private IEquPonQuotationAppRele equPonQuotationAppReleServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonQuotationAppReleServer;
	}

}