package com.sie.watsons.base.pos.csrUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.csrUpdate.model.entities.readonly.EquPosCsrUpdateLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosCsrUpdateLineDAO_HI_RO")
public class EquPosCsrUpdateLineDAO_HI_RO extends DynamicViewObjectImpl<EquPosCsrUpdateLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCsrUpdateLineDAO_HI_RO.class);
	public EquPosCsrUpdateLineDAO_HI_RO() {
		super();
	}

}
