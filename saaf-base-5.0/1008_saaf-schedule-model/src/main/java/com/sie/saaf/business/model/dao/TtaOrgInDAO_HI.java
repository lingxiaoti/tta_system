package com.sie.saaf.business.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.business.model.entities.TtaOrgInEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaOrgInDAO_HI")
public class TtaOrgInDAO_HI extends BaseCommonDAO_HI<TtaOrgInEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOrgInDAO_HI.class);

	public TtaOrgInDAO_HI() {
		super();
	}

}
