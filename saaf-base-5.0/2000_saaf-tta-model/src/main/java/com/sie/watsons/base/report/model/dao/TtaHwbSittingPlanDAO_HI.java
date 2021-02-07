package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaHwbSittingPlanEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaHwbSittingPlanDAO_HI")
public class TtaHwbSittingPlanDAO_HI extends BaseCommonDAO_HI<TtaHwbSittingPlanEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbSittingPlanDAO_HI.class);

	public TtaHwbSittingPlanDAO_HI() {
		super();
	}

}
