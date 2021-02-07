package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaReportHeaderDAO_HI")
public class TtaReportHeaderDAO_HI extends BaseCommonDAO_HI<TtaReportHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportHeaderDAO_HI.class);

	public TtaReportHeaderDAO_HI() {
		super();
	}

}
