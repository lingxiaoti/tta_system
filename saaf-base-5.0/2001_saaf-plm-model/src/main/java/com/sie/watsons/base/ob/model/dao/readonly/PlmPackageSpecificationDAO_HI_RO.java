package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmPackageSpecificationEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmPackageSpecificationDAO_HI_RO")
public class PlmPackageSpecificationDAO_HI_RO extends DynamicViewObjectImpl<PlmPackageSpecificationEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmPackageSpecificationDAO_HI_RO.class);
	public PlmPackageSpecificationDAO_HI_RO() {
		super();
	}

}
