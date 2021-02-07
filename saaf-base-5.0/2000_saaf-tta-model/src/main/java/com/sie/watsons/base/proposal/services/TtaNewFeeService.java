package com.sie.watsons.base.proposal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.proposal.model.inter.ITtaNewFee;

@RestController
@RequestMapping("/ttaNewFeeService")
public class TtaNewFeeService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewFeeService.class);

	@Autowired
	private ITtaNewFee ttaNewFeeServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaNewFeeServer;
	}

}