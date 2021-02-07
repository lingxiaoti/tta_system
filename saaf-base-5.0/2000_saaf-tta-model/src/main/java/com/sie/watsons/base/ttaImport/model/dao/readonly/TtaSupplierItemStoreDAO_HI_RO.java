package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSupplierItemStoreEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSupplierItemStoreDAO_HI_RO")
public class TtaSupplierItemStoreDAO_HI_RO extends DynamicViewObjectImpl<TtaSupplierItemStoreEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemStoreDAO_HI_RO.class);
	public TtaSupplierItemStoreDAO_HI_RO() {
		super();
	}

}
