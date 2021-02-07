package com.sie.saaf.business.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.business.model.entities.TtaMarketInEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaMarketInDAO_HI")
public class TtaMarketInDAO_HI extends BaseCommonDAO_HI<TtaMarketInEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaMarketInDAO_HI.class);

	public TtaMarketInDAO_HI() {
		super();
	}

}
