package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmDataAclHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmDataAclHeaderDAO_HI_RO")
public class PlmDataAclHeaderDAO_HI_RO extends
		DynamicViewObjectImpl<PlmDataAclHeaderEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDataAclHeaderDAO_HI_RO.class);

	public PlmDataAclHeaderDAO_HI_RO() {
		super();
	}

}
