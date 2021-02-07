package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmFullLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaDmFullLineDAO_HI_RO")
public class TtaDmFullLineDAO_HI_RO extends DynamicViewObjectImpl<TtaDmFullLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDmFullLineDAO_HI_RO.class);
	public TtaDmFullLineDAO_HI_RO() {
		super();
	}

}
