package com.sie.watsons.base.api.model.dao.readonly;

import com.sie.watsons.base.api.model.entities.readonly.VmiPromotionStoreEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("vmiPromotionStoreDAO_HI_RO")
public class VmiPromotionStoreDAO_HI_RO extends DynamicViewObjectImpl<VmiPromotionStoreEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(VmiPromotionStoreDAO_HI_RO.class);
	public VmiPromotionStoreDAO_HI_RO() {
		super();
	}

}
