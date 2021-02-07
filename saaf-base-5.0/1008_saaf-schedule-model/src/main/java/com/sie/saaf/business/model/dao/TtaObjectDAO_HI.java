package com.sie.saaf.business.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.business.model.entities.TtaShopMarketEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaObjectDAO_HI")
public class TtaObjectDAO_HI extends BaseCommonDAO_HI<Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaObjectDAO_HI.class);

	public TtaObjectDAO_HI() {
		super();
	}

}
