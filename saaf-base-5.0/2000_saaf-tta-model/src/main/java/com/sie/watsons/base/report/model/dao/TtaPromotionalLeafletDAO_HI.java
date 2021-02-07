package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaPromotionalLeafletEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaPromotionalLeafletDAO_HI")
public class TtaPromotionalLeafletDAO_HI extends BaseCommonDAO_HI<TtaPromotionalLeafletEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPromotionalLeafletDAO_HI.class);

	public TtaPromotionalLeafletDAO_HI() {
		super();
	}

}
