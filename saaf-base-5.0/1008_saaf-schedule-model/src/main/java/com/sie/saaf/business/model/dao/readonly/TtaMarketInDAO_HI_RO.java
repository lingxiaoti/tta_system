package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.TtaMarketInEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaMarketInDAO_HI_RO")
public class TtaMarketInDAO_HI_RO extends DynamicViewObjectImpl<TtaMarketInEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaMarketInDAO_HI_RO.class);
	public TtaMarketInDAO_HI_RO() {
		super();
	}

}
