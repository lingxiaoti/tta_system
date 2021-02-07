package com.sie.watsons.base.proposal.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandCountCRecordEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaBrandCountCRecordDAO_HI_RO")
public class TtaBrandCountCRecordDAO_HI_RO extends DynamicViewObjectImpl<TtaBrandCountCRecordEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandCountCRecordDAO_HI_RO.class);
	public TtaBrandCountCRecordDAO_HI_RO() {
		super();
	}

}
