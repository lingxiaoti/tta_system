package com.sie.watsons.base.report.model.dao;

import com.yhg.hibernate.core.dao.ViewObjectImpl;
import com.sie.watsons.base.report.model.entities.TtaOiReportHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaOiReportHeaderDAO_HI")
public class TtaOiReportHeaderDAO_HI extends ViewObjectImpl<TtaOiReportHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportHeaderDAO_HI.class);

	public TtaOiReportHeaderDAO_HI() {
		super();
	}

	@Override
	public Object save(TtaOiReportHeaderEntity_HI entity) {
		return super.save(entity);
	}
}
