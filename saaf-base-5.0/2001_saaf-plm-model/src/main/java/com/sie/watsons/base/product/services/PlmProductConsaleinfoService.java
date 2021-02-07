package com.sie.watsons.base.product.services;

import com.sie.watsons.base.product.model.inter.IPlmProductConsaleinfo;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/plmProductConsaleinfoService")
public class PlmProductConsaleinfoService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductConsaleinfoService.class);

	@Autowired
	private IPlmProductConsaleinfo plmProductConsaleinfoServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.plmProductConsaleinfoServer;
	}

}