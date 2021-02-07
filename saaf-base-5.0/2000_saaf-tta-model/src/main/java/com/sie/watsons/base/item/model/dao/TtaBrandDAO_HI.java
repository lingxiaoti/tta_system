package com.sie.watsons.base.item.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.item.model.entities.TtaBrandEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaBrandDAO_HI")
public class TtaBrandDAO_HI extends BaseCommonDAO_HI<TtaBrandEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandDAO_HI.class);

	public TtaBrandDAO_HI() {
		super();
	}

}
