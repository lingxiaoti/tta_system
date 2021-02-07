package com.sie.watsons.base.supplement.model.dao.readonly;

import org.springframework.stereotype.Component;

import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmSupplementHeadDAO_HI_RO")
public class PlmSupplementHeadDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplementHeadEntity_HI_RO>  {
//	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementHeadDAO_HI_RO.class);
	public PlmSupplementHeadDAO_HI_RO() {
		super();
	}

}
