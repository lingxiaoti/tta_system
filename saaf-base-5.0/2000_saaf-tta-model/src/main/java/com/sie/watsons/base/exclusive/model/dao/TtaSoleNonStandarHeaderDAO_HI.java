package com.sie.watsons.base.exclusive.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleNonStandarHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSoleNonStandarHeaderDAO_HI")
public class TtaSoleNonStandarHeaderDAO_HI extends BaseCommonDAO_HI<TtaSoleNonStandarHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleNonStandarHeaderDAO_HI.class);

	public TtaSoleNonStandarHeaderDAO_HI() {
		super();
	}

}
