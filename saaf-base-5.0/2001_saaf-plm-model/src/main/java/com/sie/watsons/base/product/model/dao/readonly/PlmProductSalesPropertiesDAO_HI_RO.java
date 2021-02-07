package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductSalesPropertiesEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductSalesPropertiesDAO_HI_RO")
public class PlmProductSalesPropertiesDAO_HI_RO extends DynamicViewObjectImpl<PlmProductSalesPropertiesEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSalesPropertiesDAO_HI_RO.class);
	public PlmProductSalesPropertiesDAO_HI_RO() {
		super();
	}

}
