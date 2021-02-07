package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaNonObInfoEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupplierQaNonObInfoDAO_HI_RO")
public class PlmSupplierQaNonObInfoDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplierQaNonObInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierQaNonObInfoDAO_HI_RO.class);
	public PlmSupplierQaNonObInfoDAO_HI_RO() {
		super();
	}

}
