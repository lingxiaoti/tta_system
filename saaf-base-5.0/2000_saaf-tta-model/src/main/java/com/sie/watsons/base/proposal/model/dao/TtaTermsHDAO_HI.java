package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsHEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaTermsHDAO_HI")
public class TtaTermsHDAO_HI extends BaseCommonDAO_HI<TtaTermsHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsHDAO_HI.class);

	public TtaTermsHDAO_HI() {
		super();
	}

}
