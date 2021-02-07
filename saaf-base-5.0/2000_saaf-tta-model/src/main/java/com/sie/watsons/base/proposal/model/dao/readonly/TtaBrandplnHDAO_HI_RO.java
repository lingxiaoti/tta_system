package com.sie.watsons.base.proposal.model.dao.readonly;

import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnHEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaBrandplnHDAO_HI_RO")
public class TtaBrandplnHDAO_HI_RO extends DynamicViewObjectImpl<TtaBrandplnHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandplnHDAO_HI_RO.class);
	public TtaBrandplnHDAO_HI_RO() {
		super();
	}

}
