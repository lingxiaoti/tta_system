package com.sie.watsons.base.elecsign.services;

import com.sie.watsons.base.elecsign.model.inter.ITtaElecSignResultLine;

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
@RequestMapping("/ttaElecSignResultLineService")
public class TtaElecSignResultLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecSignResultLineService.class);

	@Autowired
	private ITtaElecSignResultLine ttaElecSignResultLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaElecSignResultLineServer;
	}

}