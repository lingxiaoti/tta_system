package com.sie.watsons.base.proposal.model.dao.readonly;

import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsLEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaTermsLDAO_HI_RO")
public class TtaTermsLDAO_HI_RO extends DynamicViewObjectImpl<TtaTermsLEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsLDAO_HI_RO.class);
	public TtaTermsLDAO_HI_RO() {
		super();
	}

}
