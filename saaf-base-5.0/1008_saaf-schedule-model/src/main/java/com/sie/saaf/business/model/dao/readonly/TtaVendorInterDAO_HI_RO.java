package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.TtaVendorInterEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaVendorInterDAO_HI_RO")
public class TtaVendorInterDAO_HI_RO extends DynamicViewObjectImpl<TtaVendorInterEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaVendorInterDAO_HI_RO.class);
	public TtaVendorInterDAO_HI_RO() {
		super();
	}

}
