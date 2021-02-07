package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmObHistoryListEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmObHistoryListDAO_HI")
public class PlmObHistoryListDAO_HI extends BaseCommonDAO_HI<PlmObHistoryListEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmObHistoryListDAO_HI.class);
	public PlmObHistoryListDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmObHistoryListEntity_HI entity) {
		return super.save(entity);
	}
}
