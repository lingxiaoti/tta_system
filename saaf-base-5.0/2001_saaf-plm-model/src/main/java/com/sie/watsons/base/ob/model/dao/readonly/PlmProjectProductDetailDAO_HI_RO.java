package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectProductDetailEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProjectProductDetailDAO_HI_RO")
public class PlmProjectProductDetailDAO_HI_RO extends DynamicViewObjectImpl<PlmProjectProductDetailEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectProductDetailDAO_HI_RO.class);
	public PlmProjectProductDetailDAO_HI_RO() {
		super();
	}

}
