package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupplementLineDAO_HI")
public class PlmSupplementLineDAO_HI extends BaseCommonDAO_HI<PlmSupplementLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementLineDAO_HI.class);
	public PlmSupplementLineDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupplementLineEntity_HI entity) {
		return super.save(entity);
	}
}
