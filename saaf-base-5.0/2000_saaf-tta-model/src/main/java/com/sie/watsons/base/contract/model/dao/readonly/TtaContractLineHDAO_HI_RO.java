
package com.sie.watsons.base.contract.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineHEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaContractLineHDAO_HI_RO")
public class TtaContractLineHDAO_HI_RO extends DynamicViewObjectImpl<TtaContractLineHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractLineHDAO_HI_RO.class);
	public TtaContractLineHDAO_HI_RO() {
		super();
	}

}
