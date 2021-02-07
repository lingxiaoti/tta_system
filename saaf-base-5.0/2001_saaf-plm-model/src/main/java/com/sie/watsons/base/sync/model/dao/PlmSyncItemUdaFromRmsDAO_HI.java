package com.sie.watsons.base.sync.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemUdaFromRmsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSyncItemUdaFromRmsDAO_HI")
public class PlmSyncItemUdaFromRmsDAO_HI extends BaseCommonDAO_HI<PlmSyncItemUdaFromRmsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemUdaFromRmsDAO_HI.class);
	public PlmSyncItemUdaFromRmsDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSyncItemUdaFromRmsEntity_HI entity) {
		return super.save(entity);
	}
}
