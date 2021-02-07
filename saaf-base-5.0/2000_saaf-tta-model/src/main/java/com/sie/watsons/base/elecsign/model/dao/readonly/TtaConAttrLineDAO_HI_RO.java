package com.sie.watsons.base.elecsign.model.dao.readonly;

import com.sie.watsons.base.elecsign.model.entities.readonly.TtaConAttrLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaProposalConAttrLineDAO_HI_RO")
public class TtaConAttrLineDAO_HI_RO extends DynamicViewObjectImpl<TtaConAttrLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaConAttrLineDAO_HI_RO.class);
	public TtaConAttrLineDAO_HI_RO() {
		super();
	}
}
