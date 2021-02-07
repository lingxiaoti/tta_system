package com.sie.watsons.base.pos.qualityUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.EquPosQualityUpdateLineEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosQualityUpdateLindDAO_HI")
public class EquPosQualityUpdateLineDAO_HI extends BaseCommonDAO_HI<EquPosQualityUpdateLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualityUpdateLineDAO_HI.class);

	public EquPosQualityUpdateLineDAO_HI() {
		super();
	}

}
