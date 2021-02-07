package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgProductionInfoEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgProductionInfoDAO_HI")
public class EquPosBgProductionInfoDAO_HI extends BaseCommonDAO_HI<EquPosBgProductionInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgProductionInfoDAO_HI.class);

	public EquPosBgProductionInfoDAO_HI() {
		super();
	}

}
