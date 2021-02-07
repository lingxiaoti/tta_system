package com.sie.watsons.base.productEco.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductQaFileEcoEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("plmProductQaFileEcoDAO_HI_RO")
public class PlmProductQaFileEcoDAO_HI_RO extends DynamicViewObjectImpl<PlmProductQaFileEcoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductQaFileEcoDAO_HI_RO.class);
	public PlmProductQaFileEcoDAO_HI_RO() {
		super();
	}

}
