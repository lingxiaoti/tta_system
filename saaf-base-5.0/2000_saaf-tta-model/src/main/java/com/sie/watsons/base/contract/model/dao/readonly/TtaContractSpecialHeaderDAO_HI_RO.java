package com.sie.watsons.base.contract.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractSpecialHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaContractSpecialHeaderDAO_HI_RO")
public class TtaContractSpecialHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaContractSpecialHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractSpecialHeaderDAO_HI_RO.class);
	public TtaContractSpecialHeaderDAO_HI_RO() {
		super();
	}

}
