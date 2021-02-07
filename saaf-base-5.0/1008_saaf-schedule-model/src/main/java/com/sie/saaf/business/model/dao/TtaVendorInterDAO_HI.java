package com.sie.saaf.business.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.business.model.entities.TtaVendorInterEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaVendorInterDAO_HI")
public class TtaVendorInterDAO_HI extends BaseCommonDAO_HI<TtaVendorInterEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaVendorInterDAO_HI.class);

	public TtaVendorInterDAO_HI() {
		super();
	}

}
