package com.sie.watsons.base.api.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.api.model.entities.PlmApiLogEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmApiLogDAO_HI")
public class PlmApiLogDAO_HI extends BaseCommonDAO_HI<PlmApiLogEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmApiLogDAO_HI.class);
	public PlmApiLogDAO_HI() {
		super();
	}
}
