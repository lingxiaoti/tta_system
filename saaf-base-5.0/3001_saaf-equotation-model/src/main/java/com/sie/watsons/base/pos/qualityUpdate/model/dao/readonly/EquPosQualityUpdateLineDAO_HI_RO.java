package com.sie.watsons.base.pos.qualityUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.qualityUpdate.model.entities.readonly.EquPosQualityUpdateLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosQualityUpdateLindDAO_HI_RO")
public class EquPosQualityUpdateLineDAO_HI_RO extends DynamicViewObjectImpl<EquPosQualityUpdateLineEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualityUpdateLineDAO_HI_RO.class);
	public EquPosQualityUpdateLineDAO_HI_RO() {
		super();
	}

}
