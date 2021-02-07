package com.sie.watsons.base.pos.csrUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.csrUpdate.model.entities.readonly.EquPosCsrUpdateHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosCsrUpdateHeadDAO_HI_RO")
public class EquPosCsrUpdateHeadDAO_HI_RO extends DynamicViewObjectImpl<EquPosCsrUpdateHeadEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCsrUpdateHeadDAO_HI_RO.class);
	public EquPosCsrUpdateHeadDAO_HI_RO() {
		super();
	}

}
