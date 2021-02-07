package com.sie.watsons.base.item.model.dao.readonly;

import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaItemDAO_HI_RO")
public class TtaItemDAO_HI_RO extends DynamicViewObjectImpl<TtaItemEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaItemDAO_HI_RO.class);
	public TtaItemDAO_HI_RO() {
		super();
	}

}
