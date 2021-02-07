package com.sie.watsons.base.exclusive.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.exclusive.model.dao.TtaSoleAgrtDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaSoleSupplierDAO_HI_RO")
public class TtaSoleSupplierDAO_HI_RO extends DynamicViewObjectImpl<TtaSoleSupplierEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtDAO_HI.class);
	
	public TtaSoleSupplierDAO_HI_RO() {
		super();
	}

}
