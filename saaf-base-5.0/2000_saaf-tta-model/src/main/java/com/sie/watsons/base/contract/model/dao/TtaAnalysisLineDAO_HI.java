package com.sie.watsons.base.contract.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.contract.model.entities.TtaAnalysisLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaAnalysisLineDAO_HI")
public class TtaAnalysisLineDAO_HI extends BaseCommonDAO_HI<TtaAnalysisLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAnalysisLineDAO_HI.class);

	public TtaAnalysisLineDAO_HI() {
		super();
	}

}
