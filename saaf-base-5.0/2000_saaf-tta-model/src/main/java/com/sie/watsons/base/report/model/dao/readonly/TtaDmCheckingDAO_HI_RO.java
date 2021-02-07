package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaDmCheckingDAO_HI_RO")
public class TtaDmCheckingDAO_HI_RO extends DynamicViewObjectImpl<TtaDmCheckingEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDmCheckingDAO_HI_RO.class);
	public TtaDmCheckingDAO_HI_RO() {
		super();
	}

}
