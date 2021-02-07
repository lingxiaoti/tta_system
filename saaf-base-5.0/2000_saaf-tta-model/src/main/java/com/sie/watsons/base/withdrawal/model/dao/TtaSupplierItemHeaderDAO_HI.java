package com.sie.watsons.base.withdrawal.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemHeaderEntity_HI;


@Component("ttaSupplierItemHeaderDAO_HI")
public class TtaSupplierItemHeaderDAO_HI extends BaseCommonDAO_HI<TtaSupplierItemHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemHeaderDAO_HI.class);

	public TtaSupplierItemHeaderDAO_HI() {
		super();
	}

	@Override
	public Object save(TtaSupplierItemHeaderEntity_HI entity) {
		return super.save(entity);
	}
}
