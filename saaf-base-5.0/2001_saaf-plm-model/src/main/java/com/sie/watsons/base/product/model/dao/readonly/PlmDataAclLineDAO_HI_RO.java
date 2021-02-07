package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmDataAclLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmDataAclLineDAO_HI_RO")
public class PlmDataAclLineDAO_HI_RO extends
		DynamicViewObjectImpl<PlmDataAclLineEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDataAclLineDAO_HI_RO.class);

	public PlmDataAclLineDAO_HI_RO() {
		super();
	}

}
