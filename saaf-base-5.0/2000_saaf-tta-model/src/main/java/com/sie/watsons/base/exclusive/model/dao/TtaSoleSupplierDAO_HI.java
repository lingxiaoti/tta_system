package com.sie.watsons.base.exclusive.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;

@Component("ttaSoleSupplierDAO_HI")
public class TtaSoleSupplierDAO_HI extends BaseCommonDAO_HI<TtaSoleSupplierEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleSupplierDAO_HI.class);

	public TtaSoleSupplierDAO_HI() {
		super();
	}

}
