package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandCountCRecordEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaBrandCountCRecordDAO_HI")
public class TtaBrandCountCRecordDAO_HI extends BaseCommonDAO_HI<TtaBrandCountCRecordEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandCountCRecordDAO_HI.class);

	public TtaBrandCountCRecordDAO_HI() {
		super();
	}

}
