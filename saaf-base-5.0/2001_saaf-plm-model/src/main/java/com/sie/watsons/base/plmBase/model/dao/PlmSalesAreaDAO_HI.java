package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSalesAreaDAO_HI")
public class PlmSalesAreaDAO_HI extends BaseCommonDAO_HI<PlmSalesAreaEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSalesAreaDAO_HI.class);
	public PlmSalesAreaDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSalesAreaEntity_HI entity) {
		return super.save(entity);
	}
}
