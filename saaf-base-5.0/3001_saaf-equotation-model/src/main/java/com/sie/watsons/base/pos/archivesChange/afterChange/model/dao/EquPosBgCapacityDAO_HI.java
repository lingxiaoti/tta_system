package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCapacityEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgCapacityDAO_HI")
public class EquPosBgCapacityDAO_HI extends BaseCommonDAO_HI<EquPosBgCapacityEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCapacityDAO_HI.class);

	public EquPosBgCapacityDAO_HI() {
		super();
	}

}
