package com.sie.watsons.base.ttaImport.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaTradeCalendarEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaTradeCalendarDAO_HI")
public class TtaTradeCalendarDAO_HI extends BaseCommonDAO_HI<TtaTradeCalendarEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTradeCalendarDAO_HI.class);

	public TtaTradeCalendarDAO_HI() {
		super();
	}

}
