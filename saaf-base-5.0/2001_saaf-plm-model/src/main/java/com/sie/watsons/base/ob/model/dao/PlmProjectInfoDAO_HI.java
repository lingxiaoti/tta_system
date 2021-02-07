package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectInfoEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProjectInfoDAO_HI")
public class PlmProjectInfoDAO_HI extends BaseCommonDAO_HI<PlmProjectInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectInfoDAO_HI.class);

	public PlmProjectInfoDAO_HI() {
		super();
	}

}
