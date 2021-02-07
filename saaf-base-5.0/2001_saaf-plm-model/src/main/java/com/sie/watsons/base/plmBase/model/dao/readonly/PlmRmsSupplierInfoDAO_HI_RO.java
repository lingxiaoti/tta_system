package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmRmsSupplierInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmRmsSupplierInfoDAO_HI_RO")
public class PlmRmsSupplierInfoDAO_HI_RO extends DynamicViewObjectImpl<PlmRmsSupplierInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsSupplierInfoDAO_HI_RO.class);
	public PlmRmsSupplierInfoDAO_HI_RO() {
		super();
	}

}
