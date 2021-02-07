package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaNppNewProductReportEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaNppNewProductReportDAO_HI")
public class TtaNppNewProductReportDAO_HI extends BaseCommonDAO_HI<TtaNppNewProductReportEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNppNewProductReportDAO_HI.class);

	public TtaNppNewProductReportDAO_HI() {
		super();
	}

}
