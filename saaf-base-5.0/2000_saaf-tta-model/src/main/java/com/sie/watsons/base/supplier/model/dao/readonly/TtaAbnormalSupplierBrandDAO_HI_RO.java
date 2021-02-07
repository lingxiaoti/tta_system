package com.sie.watsons.base.supplier.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaAbnormalSupplierBrandEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaAbnormalSupplierBrandDAO_HI_RO")
public class TtaAbnormalSupplierBrandDAO_HI_RO extends DynamicViewObjectImpl<TtaAbnormalSupplierBrandEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAbnormalSupplierBrandDAO_HI_RO.class);
	public TtaAbnormalSupplierBrandDAO_HI_RO() {
		super();
	}

}
