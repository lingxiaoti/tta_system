package com.sie.watsons.base.withdrawal.services;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemLine;

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
@RequestMapping("/ttaSupplierItemLineService")
public class TtaSupplierItemLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemLineService.class);

	@Autowired
	private ITtaSupplierItemLine ttaSupplierItemLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSupplierItemLineServer;
	}

}