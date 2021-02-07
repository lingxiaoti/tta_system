package com.sie.watsons.base.pon.itquotation.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoInviteFileItEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuoInviteFileItDAO_HI")
public class EquPonQuoInviteFileItDAO_HI extends BaseCommonDAO_HI<EquPonQuoInviteFileItEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoInviteFileItDAO_HI.class);
	public EquPonQuoInviteFileItDAO_HI() {
		super();
	}

	@Override
	public Object save(EquPonQuoInviteFileItEntity_HI entity) {
		return super.save(entity);
	}
}
