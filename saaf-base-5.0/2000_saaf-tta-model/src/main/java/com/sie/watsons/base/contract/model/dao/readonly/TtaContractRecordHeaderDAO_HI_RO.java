package com.sie.watsons.base.contract.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractRecordHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaContractRecordHeaderDAO_HI_RO")
public class TtaContractRecordHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaContractRecordHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractRecordHeaderDAO_HI_RO.class);
	public TtaContractRecordHeaderDAO_HI_RO() {
		super();
	}

}
