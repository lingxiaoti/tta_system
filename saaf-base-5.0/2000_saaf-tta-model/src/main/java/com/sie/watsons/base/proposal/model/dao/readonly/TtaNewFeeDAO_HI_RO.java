package com.sie.watsons.base.proposal.model.dao.readonly;

import com.sie.watsons.base.proposal.model.entities.readonly.TtaNewFeeEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaNewFeeDAO_HI_RO")
public class TtaNewFeeDAO_HI_RO extends DynamicViewObjectImpl<TtaNewFeeEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewFeeDAO_HI_RO.class);
	public TtaNewFeeDAO_HI_RO() {
		super();
	}

}
