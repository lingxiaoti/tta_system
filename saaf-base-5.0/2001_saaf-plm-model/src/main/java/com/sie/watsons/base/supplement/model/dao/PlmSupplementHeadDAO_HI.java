package com.sie.watsons.base.supplement.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementHeadEntity_HI;

@Component("plmSupplementHeadDAO_HI")
public class PlmSupplementHeadDAO_HI extends BaseCommonDAO_HI<PlmSupplementHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementHeadDAO_HI.class);
	public PlmSupplementHeadDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupplementHeadEntity_HI entity) {
		return super.save(entity);
	}
}
