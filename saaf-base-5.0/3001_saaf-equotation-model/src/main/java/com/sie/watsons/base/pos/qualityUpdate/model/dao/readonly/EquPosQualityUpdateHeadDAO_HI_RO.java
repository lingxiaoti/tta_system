package com.sie.watsons.base.pos.qualityUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.qualityUpdate.model.entities.readonly.EquPosQualityUpdateHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosQualityUpdateHeadDAO_HI_RO")
public class EquPosQualityUpdateHeadDAO_HI_RO extends DynamicViewObjectImpl<EquPosQualityUpdateHeadEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualityUpdateHeadDAO_HI_RO.class);
	public EquPosQualityUpdateHeadDAO_HI_RO() {
		super();
	}

}
