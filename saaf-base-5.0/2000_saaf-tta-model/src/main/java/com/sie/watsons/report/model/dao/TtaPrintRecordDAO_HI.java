package com.sie.watsons.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.report.model.entities.TtaPrintRecordEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaPrintRecordDAO_HI")
public class TtaPrintRecordDAO_HI extends BaseCommonDAO_HI<TtaPrintRecordEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPrintRecordDAO_HI.class);

	public TtaPrintRecordDAO_HI() {
		super();
	}

}
