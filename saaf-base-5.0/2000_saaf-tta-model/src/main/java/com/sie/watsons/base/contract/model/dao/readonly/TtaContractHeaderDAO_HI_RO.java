package com.sie.watsons.base.contract.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractHeaderEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaContractHeaderDAO_HI_RO")
public class TtaContractHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaContractHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractHeaderDAO_HI_RO.class);
	public TtaContractHeaderDAO_HI_RO() {
		super();
	}

}
