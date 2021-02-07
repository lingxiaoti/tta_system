package com.sie.watsons.base.pos.contractUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.contractUpdate.model.entities.readonly.EquPosContractUpdateHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosContractUpdateHeadDAO_HI_RO")
public class EquPosContractUpdateHeadDAO_HI_RO extends DynamicViewObjectImpl<EquPosContractUpdateHeadEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosContractUpdateHeadDAO_HI_RO.class);
	public EquPosContractUpdateHeadDAO_HI_RO() {
		super();
	}

}
