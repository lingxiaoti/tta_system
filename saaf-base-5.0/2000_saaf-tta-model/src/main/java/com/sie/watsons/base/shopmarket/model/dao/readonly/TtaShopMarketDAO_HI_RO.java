package com.sie.watsons.base.shopmarket.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.shopmarket.model.entities.readonly.TtaShopMarketEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaShopMarketDAO_HI_RO")
public class TtaShopMarketDAO_HI_RO extends DynamicViewObjectImpl<TtaShopMarketEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaShopMarketDAO_HI_RO.class);
	public TtaShopMarketDAO_HI_RO() {
		super();
	}

}
