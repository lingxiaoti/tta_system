package com.sie.watsons.base.pos.contractUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.contractUpdate.model.entities.readonly.EquPosContractUpdateLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosContractUpdateLineDAO_HI_RO")
public class EquPosContractUpdateLineDAO_HI_RO extends DynamicViewObjectImpl<EquPosContractUpdateLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosContractUpdateLineDAO_HI_RO.class);
	public EquPosContractUpdateLineDAO_HI_RO() {
		super();
	}

}
