package com.sie.watsons.base.contract.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractDetailEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaContractDetailDAO_HI_RO")
public class TtaContractDetailDAO_HI_RO extends DynamicViewObjectImpl<TtaContractDetailEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractDetailDAO_HI_RO.class);
	public TtaContractDetailDAO_HI_RO() {
		super();
	}

}
