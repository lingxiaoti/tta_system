package com.sie.watsons.base.sync.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemSuppFromRmsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSyncItemSuppFromRmsDAO_HI")
public class PlmSyncItemSuppFromRmsDAO_HI extends BaseCommonDAO_HI<PlmSyncItemSuppFromRmsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemSuppFromRmsDAO_HI.class);
	public PlmSyncItemSuppFromRmsDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSyncItemSuppFromRmsEntity_HI entity) {
		return super.save(entity);
	}
}
