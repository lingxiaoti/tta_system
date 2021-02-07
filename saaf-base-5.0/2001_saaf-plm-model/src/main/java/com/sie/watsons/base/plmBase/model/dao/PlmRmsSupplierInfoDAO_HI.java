package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmRmsSupplierInfoEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmRmsSupplierInfoDAO_HI")
public class PlmRmsSupplierInfoDAO_HI extends BaseCommonDAO_HI<PlmRmsSupplierInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsSupplierInfoDAO_HI.class);
	public PlmRmsSupplierInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmRmsSupplierInfoEntity_HI entity) {
		return super.save(entity);
	}
}
