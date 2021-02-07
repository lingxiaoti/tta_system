package com.sie.watsons.base.clause.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.clause.model.entities.TtaCollectTypeLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaCollectTypeLineDAO_HI")
public class TtaCollectTypeLineDAO_HI extends BaseCommonDAO_HI<TtaCollectTypeLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCollectTypeLineDAO_HI.class);

	public TtaCollectTypeLineDAO_HI() {
		super();
	}

}
