package com.sie.watsons.base.pon.itquotation.model.dao.readonly;

import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoInviteFileItEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuoInviteFileItDAO_HI_RO")
public class EquPonQuoInviteFileItDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuoInviteFileItEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoInviteFileItDAO_HI_RO.class);
	public EquPonQuoInviteFileItDAO_HI_RO() {
		super();
	}

}
