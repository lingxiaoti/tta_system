package com.sie.watsons.base.item.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.item.model.entities.readonly.TtaGroupEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaGroupDAO_HI_RO")
public class TtaGroupDAO_HI_RO extends DynamicViewObjectImpl<TtaGroupEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaGroupDAO_HI_RO.class);
	public TtaGroupDAO_HI_RO() {
		super();
	}

}
