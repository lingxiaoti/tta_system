package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.TtaOrgInEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaOrgInDAO_HI_RO")
public class TtaOrgInDAO_HI_RO extends DynamicViewObjectImpl<TtaOrgInEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOrgInDAO_HI_RO.class);
	public TtaOrgInDAO_HI_RO() {
		super();
	}

}
