package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaNewFeeEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaNewFeeDAO_HI")
public class TtaNewFeeDAO_HI extends BaseCommonDAO_HI<TtaNewFeeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewFeeDAO_HI.class);

	public TtaNewFeeDAO_HI() {
		super();
	}

}
