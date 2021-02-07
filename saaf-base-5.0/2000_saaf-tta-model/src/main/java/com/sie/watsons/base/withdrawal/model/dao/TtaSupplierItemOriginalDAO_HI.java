package com.sie.watsons.base.withdrawal.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemOriginalEntity_HI;

@Component("ttaSupplierItemOriginalDAO_HI")
public class TtaSupplierItemOriginalDAO_HI extends BaseCommonDAO_HI<TtaSupplierItemOriginalEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemOriginalDAO_HI.class);

	public TtaSupplierItemOriginalDAO_HI() {
		super();
	}

	@Override
	public Object save(TtaSupplierItemOriginalEntity_HI entity) {
		return super.save(entity);
	}
}
