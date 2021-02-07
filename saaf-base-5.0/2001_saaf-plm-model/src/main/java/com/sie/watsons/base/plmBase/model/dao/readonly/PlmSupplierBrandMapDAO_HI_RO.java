package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierBrandMapEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupplierBrandMapDAO_HI_RO")
public class PlmSupplierBrandMapDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplierBrandMapEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierBrandMapDAO_HI_RO.class);
	public PlmSupplierBrandMapDAO_HI_RO() {
		super();
	}

}
