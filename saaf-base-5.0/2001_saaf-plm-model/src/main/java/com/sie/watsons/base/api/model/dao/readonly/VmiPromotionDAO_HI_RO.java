package com.sie.watsons.base.api.model.dao.readonly;
import com.sie.watsons.base.api.model.entities.readonly.VmiPromotionEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("vmiPromotionDAO_HI_RO")
public class VmiPromotionDAO_HI_RO extends DynamicViewObjectImpl<VmiPromotionEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(VmiPromotionDAO_HI_RO.class);
	public VmiPromotionDAO_HI_RO() {
		super();
	}

}
