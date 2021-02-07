package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("plmSupplementLineDAO_HI_RO")
public class PlmSupplementLineDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplementLineEntity_HI_RO>  {
//	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementLineDAO_HI_RO.class);
	public PlmSupplementLineDAO_HI_RO() {
		super();
	}

}
