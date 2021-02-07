package com.sie.watsons.base.report.services;

import com.sie.watsons.base.report.model.inter.ITtaOiReportFieldMapping;

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
@RequestMapping("/ttaOiReportFieldMappingService")
public class TtaOiReportFieldMappingService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportFieldMappingService.class);

	@Autowired
	private ITtaOiReportFieldMapping ttaOiReportFieldMappingServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaOiReportFieldMappingServer;
	}

}