package com.sie.watsons.base.proposal.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaDeptFeeLineDAO_HI_RO")
public class TtaDeptFeeLineDAO_HI_RO extends DynamicViewObjectImpl<TtaDeptFeeLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptFeeLineDAO_HI_RO.class);
	public TtaDeptFeeLineDAO_HI_RO() {
		super();
	}

}
