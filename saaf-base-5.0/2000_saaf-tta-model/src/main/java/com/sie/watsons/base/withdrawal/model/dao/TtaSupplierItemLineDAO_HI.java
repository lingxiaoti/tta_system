package com.sie.watsons.base.withdrawal.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemLineEntity_HI;

@Component("ttaSupplierItemLineDAO_HI")
public class TtaSupplierItemLineDAO_HI extends BaseCommonDAO_HI<TtaSupplierItemLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemLineDAO_HI.class);

	public TtaSupplierItemLineDAO_HI() {
		super();
	}

	@Override
	public Object save(TtaSupplierItemLineEntity_HI entity) {
		return super.save(entity);
	}
}
