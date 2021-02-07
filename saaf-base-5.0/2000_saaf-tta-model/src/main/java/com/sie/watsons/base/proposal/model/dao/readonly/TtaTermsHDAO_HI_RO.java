package com.sie.watsons.base.proposal.model.dao.readonly;

import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsHEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaTermsHDAO_HI_RO")
public class TtaTermsHDAO_HI_RO extends DynamicViewObjectImpl<TtaTermsHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsHDAO_HI_RO.class);
	public TtaTermsHDAO_HI_RO() {
		super();
	}

}
