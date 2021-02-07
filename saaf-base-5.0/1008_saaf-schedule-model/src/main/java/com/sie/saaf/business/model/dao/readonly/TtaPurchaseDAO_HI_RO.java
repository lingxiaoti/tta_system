package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.TtaPurchaseEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaPurchaseDAO_HI_RO")
public class TtaPurchaseDAO_HI_RO extends DynamicViewObjectImpl<TtaPurchaseEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPurchaseDAO_HI_RO.class);
	public TtaPurchaseDAO_HI_RO() {
		super();
	}

}
