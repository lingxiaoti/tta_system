package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaPromotionalLeafletEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaPromotionalLeafletDAO_HI_RO")
public class TtaPromotionalLeafletDAO_HI_RO extends DynamicViewObjectImpl<TtaPromotionalLeafletEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPromotionalLeafletDAO_HI_RO.class);
	public TtaPromotionalLeafletDAO_HI_RO() {
		super();
	}

}
