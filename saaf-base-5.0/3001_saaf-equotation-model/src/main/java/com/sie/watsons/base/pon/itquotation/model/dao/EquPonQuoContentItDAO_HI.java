package com.sie.watsons.base.pon.itquotation.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoContentItEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuoContentItDAO_HI")
public class EquPonQuoContentItDAO_HI extends BaseCommonDAO_HI<EquPonQuoContentItEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoContentItDAO_HI.class);
	public EquPonQuoContentItDAO_HI() {
		super();
	}

	@Override
	public Object save(EquPonQuoContentItEntity_HI entity) {
		return super.save(entity);
	}
}
