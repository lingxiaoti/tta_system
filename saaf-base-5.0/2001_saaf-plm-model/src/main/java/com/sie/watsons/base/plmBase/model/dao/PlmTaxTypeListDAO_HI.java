package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmTaxTypeListEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmTaxTypeListDAO_HI")
public class PlmTaxTypeListDAO_HI extends BaseCommonDAO_HI<PlmTaxTypeListEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmTaxTypeListDAO_HI.class);
	public PlmTaxTypeListDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmTaxTypeListEntity_HI entity) {
		return super.save(entity);
	}
}
