package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("plmSupShopDAO_HI_RO")
public class PlmSupShopDAO_HI_RO extends DynamicViewObjectImpl<PlmSupShopEntity_HI_RO>  {
//	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupShopDAO_HI_RO.class);
	public PlmSupShopDAO_HI_RO() {
		super();
	}

}
