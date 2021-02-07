package com.sie.watsons.base.item.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.item.model.entities.readonly.TtaBrandEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaBrandDAO_HI_RO")
public class TtaBrandDAO_HI_RO extends DynamicViewObjectImpl<TtaBrandEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandDAO_HI_RO.class);
	public TtaBrandDAO_HI_RO() {
		super();
	}

}
