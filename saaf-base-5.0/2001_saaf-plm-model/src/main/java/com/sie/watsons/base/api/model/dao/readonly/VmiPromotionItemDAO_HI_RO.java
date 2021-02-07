package com.sie.watsons.base.api.model.dao.readonly;

import com.sie.watsons.base.api.model.entities.readonly.VmiPromotionItemEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("vmiPromotionItemDAO_HI_RO")
public class VmiPromotionItemDAO_HI_RO extends DynamicViewObjectImpl<VmiPromotionItemEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(VmiPromotionItemDAO_HI_RO.class);
	public VmiPromotionItemDAO_HI_RO() {
		super();
	}

}
