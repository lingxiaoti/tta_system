package com.sie.watsons.base.pos.spendUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.spendUpdate.model.entities.readonly.EquPosSpendUpdateHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosSpendUpdateHeadDAO_HI_RO")
public class EquPosSpendUpdateHeadDAO_HI_RO extends DynamicViewObjectImpl<EquPosSpendUpdateHeadEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSpendUpdateHeadDAO_HI_RO.class);
	public EquPosSpendUpdateHeadDAO_HI_RO() {
		super();
	}

}
