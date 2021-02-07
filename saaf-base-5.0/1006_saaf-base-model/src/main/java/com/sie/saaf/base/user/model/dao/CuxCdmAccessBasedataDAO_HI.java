package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.CuxCdmAccessBasedataEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cuxCdmAccessBasedataDAO_HI")
public class CuxCdmAccessBasedataDAO_HI extends BaseCommonDAO_HI<CuxCdmAccessBasedataEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CuxCdmAccessBasedataDAO_HI.class);
	public CuxCdmAccessBasedataDAO_HI() {
		super();
	}

}
