package com.sie.saaf.business.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.entities.readonly.TtaPersonInEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaPersonInDAO_HI_RO")
public class TtaPersonInDAO_HI_RO extends DynamicViewObjectImpl<TtaPersonInEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPersonInDAO_HI_RO.class);
	public TtaPersonInDAO_HI_RO() {
		super();
	}

}
