package com.sie.watsons.base.proposal.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaDeptFeeHeaderDAO_HI_RO")
public class TtaDeptFeeHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaDeptFeeHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptFeeHeaderDAO_HI_RO.class);
	public TtaDeptFeeHeaderDAO_HI_RO() {
		super();
	}

}
