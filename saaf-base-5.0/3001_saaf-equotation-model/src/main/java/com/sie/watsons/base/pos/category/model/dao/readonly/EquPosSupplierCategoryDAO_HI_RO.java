package com.sie.watsons.base.pos.category.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.category.model.entities.readonly.EquPosSupplierCategoryEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosSupplierCategoryDAO_HI_RO")
public class EquPosSupplierCategoryDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierCategoryEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCategoryDAO_HI_RO.class);
	public EquPosSupplierCategoryDAO_HI_RO() {
		super();
	}

}
