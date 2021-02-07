package com.sie.watsons.base.sync.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemFromRmsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSyncItemFromRmsDAO_HI")
public class PlmSyncItemFromRmsDAO_HI extends BaseCommonDAO_HI<PlmSyncItemFromRmsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemFromRmsDAO_HI.class);
	public PlmSyncItemFromRmsDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSyncItemFromRmsEntity_HI entity) {
		return super.save(entity);
	}
}
