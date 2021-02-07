package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.inter.ITtaReportAboiSummary;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaReportAboiSummaryService")
public class TtaReportAboiSummaryService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAboiSummaryService.class);

	@Autowired
	private ITtaReportAboiSummary ttaReportAboiSummaryServer;


	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaReportAboiSummaryServer;
	}



}