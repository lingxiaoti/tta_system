package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaSummaryEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDevelopmentQaSummaryDAO_HI_RO")
public class PlmDevelopmentQaSummaryDAO_HI_RO extends DynamicViewObjectImpl<PlmDevelopmentQaSummaryEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentQaSummaryDAO_HI_RO.class);
	public PlmDevelopmentQaSummaryDAO_HI_RO() {
		super();
	}

}
