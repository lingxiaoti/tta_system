package com.sie.watsons.base.exclusive.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleNonStandarLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSoleNonStandarLineDAO_HI")
public class TtaSoleNonStandarLineDAO_HI extends BaseCommonDAO_HI<TtaSoleNonStandarLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleNonStandarLineDAO_HI.class);

	public TtaSoleNonStandarLineDAO_HI() {
		super();
	}

}
