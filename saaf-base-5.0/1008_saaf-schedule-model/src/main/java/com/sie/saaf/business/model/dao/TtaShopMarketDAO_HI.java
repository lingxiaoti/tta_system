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

@Component("ttaShopMarketDAO_HI")
public class TtaShopMarketDAO_HI extends BaseCommonDAO_HI<TtaShopMarketEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaShopMarketDAO_HI.class);

	public TtaShopMarketDAO_HI() {
		super();
	}

}
