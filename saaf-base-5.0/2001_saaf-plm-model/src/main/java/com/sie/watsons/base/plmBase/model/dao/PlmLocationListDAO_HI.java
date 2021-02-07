package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmLocationListEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmLocationListDAO_HI")
public class PlmLocationListDAO_HI extends BaseCommonDAO_HI<PlmLocationListEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmLocationListDAO_HI.class);
	public PlmLocationListDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmLocationListEntity_HI entity) {
		return super.save(entity);
	}
}
