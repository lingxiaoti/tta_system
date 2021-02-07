package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaHwbBaseLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaHwbBaseLineDAO_HI")
public class TtaHwbBaseLineDAO_HI extends BaseCommonDAO_HI<TtaHwbBaseLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbBaseLineDAO_HI.class);

	public TtaHwbBaseLineDAO_HI() {
		super();
	}

}
