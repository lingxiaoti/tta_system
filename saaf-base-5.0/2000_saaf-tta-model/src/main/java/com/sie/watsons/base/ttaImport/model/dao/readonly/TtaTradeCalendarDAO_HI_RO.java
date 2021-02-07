package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaTradeCalendarEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaTradeCalendarDAO_HI_RO")
public class TtaTradeCalendarDAO_HI_RO extends DynamicViewObjectImpl<TtaTradeCalendarEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTradeCalendarDAO_HI_RO.class);
	public TtaTradeCalendarDAO_HI_RO() {
		super();
	}

}
