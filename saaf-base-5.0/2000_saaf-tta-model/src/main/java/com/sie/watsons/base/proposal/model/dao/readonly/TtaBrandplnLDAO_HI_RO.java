package com.sie.watsons.base.proposal.model.dao.readonly;

import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaBrandplnLDAO_HI_RO")
public class TtaBrandplnLDAO_HI_RO extends DynamicViewObjectImpl<TtaBrandplnLEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandplnLDAO_HI_RO.class);
	public TtaBrandplnLDAO_HI_RO() {
		super();
	}

}
