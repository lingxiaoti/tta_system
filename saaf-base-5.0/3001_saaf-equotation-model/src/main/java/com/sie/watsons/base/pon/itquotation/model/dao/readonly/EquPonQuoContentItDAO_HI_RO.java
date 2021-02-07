package com.sie.watsons.base.pon.itquotation.model.dao.readonly;

import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoContentItEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuoContentItDAO_HI_RO")
public class EquPonQuoContentItDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuoContentItEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoContentItDAO_HI_RO.class);
	public EquPonQuoContentItDAO_HI_RO() {
		super();
	}

}
