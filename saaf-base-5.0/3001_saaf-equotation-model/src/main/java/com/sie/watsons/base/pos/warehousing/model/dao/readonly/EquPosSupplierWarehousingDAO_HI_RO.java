package com.sie.watsons.base.pos.warehousing.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.warehousing.model.entities.readonly.EquPosSupplierWarehousingEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosSupplierWarehousingDAO_HI_RO")
public class EquPosSupplierWarehousingDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierWarehousingEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierWarehousingDAO_HI_RO.class);
	public EquPosSupplierWarehousingDAO_HI_RO() {
		super();
	}

}
