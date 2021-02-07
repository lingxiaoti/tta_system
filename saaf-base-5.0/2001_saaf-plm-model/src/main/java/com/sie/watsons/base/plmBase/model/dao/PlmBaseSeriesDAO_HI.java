package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseSeriesEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmBaseSeriesDAO_HI")
public class PlmBaseSeriesDAO_HI extends BaseCommonDAO_HI<PlmBaseSeriesEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmBaseSeriesDAO_HI.class);

	public PlmBaseSeriesDAO_HI() {
		super();
	}

}
