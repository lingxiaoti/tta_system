package com.sie.watsons.base.pos.contractUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.contractUpdate.model.entities.EquPosContractUpdateLineEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosContractUpdateLineDAO_HI")
public class EquPosContractUpdateLineDAO_HI extends BaseCommonDAO_HI<EquPosContractUpdateLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosContractUpdateLineDAO_HI.class);

	public EquPosContractUpdateLineDAO_HI() {
		super();
	}

}
