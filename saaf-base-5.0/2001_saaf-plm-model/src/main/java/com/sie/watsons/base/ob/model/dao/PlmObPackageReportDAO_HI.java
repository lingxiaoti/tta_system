package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmObPackageReportEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmObPackageReportDAO_HI")
public class PlmObPackageReportDAO_HI extends BaseCommonDAO_HI<PlmObPackageReportEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmObPackageReportDAO_HI.class);
	public PlmObPackageReportDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmObPackageReportEntity_HI entity) {
		return super.save(entity);
	}
}
