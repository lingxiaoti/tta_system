package com.sie.saaf.ewarning.model.services;

import com.sie.saaf.ewarning.model.inter.IEwHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ewHandleService")
public class EwHandleService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EwHandleService.class);

	@Autowired
	private IEwHandle ewHandleServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ewHandleServer;
	}

}