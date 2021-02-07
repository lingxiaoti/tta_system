package com.sie.watsons.base.contract.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.contract.model.entities.readonly.TtaTermsAcCountEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaTermsAcCountDAO_HI_RO")
public class TtaTermsAcCountDAO_HI_RO extends DynamicViewObjectImpl<TtaTermsAcCountEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsAcCountDAO_HI_RO.class);
	public TtaTermsAcCountDAO_HI_RO() {
		super();
	}

}
