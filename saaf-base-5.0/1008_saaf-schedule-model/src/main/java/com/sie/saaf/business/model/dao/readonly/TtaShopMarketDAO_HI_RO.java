package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.TtaShopMarketEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaShopMarketDAO_HI_RO")
public class TtaShopMarketDAO_HI_RO extends DynamicViewObjectImpl<TtaShopMarketEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaShopMarketDAO_HI_RO.class);
	public TtaShopMarketDAO_HI_RO() {
		super();
	}

}
