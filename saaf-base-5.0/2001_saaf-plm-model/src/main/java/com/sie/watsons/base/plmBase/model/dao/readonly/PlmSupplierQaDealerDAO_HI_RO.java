package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaDealerEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupplierQaDealerDAO_HI_RO")
public class PlmSupplierQaDealerDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplierQaDealerEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierQaDealerDAO_HI_RO.class);
	public PlmSupplierQaDealerDAO_HI_RO() {
		super();
	}

}
