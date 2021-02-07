package com.sie.watsons.base.supplement.model.dao.readonly;

import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineFileEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupplementLineFileDAO_HI_RO")
public class PlmSupplementLineFileDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplementLineFileEntity_HI_RO>  {
//	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementLineFileDAO_HI_RO.class);
	public PlmSupplementLineFileDAO_HI_RO() {
		super();
	}

}
