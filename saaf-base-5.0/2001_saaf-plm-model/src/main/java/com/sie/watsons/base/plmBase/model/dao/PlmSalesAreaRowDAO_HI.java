package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaRowEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSalesAreaRowDAO_HI")
public class PlmSalesAreaRowDAO_HI extends BaseCommonDAO_HI<PlmSalesAreaRowEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSalesAreaRowDAO_HI.class);
	public PlmSalesAreaRowDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSalesAreaRowEntity_HI entity) {
		return super.save(entity);
	}
}
