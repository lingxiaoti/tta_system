package com.sie.watsons.base.poc.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.poc.model.entities.ItemMasterEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("itemMasterDAO_HI")
public class ItemMasterDAO_HI extends BaseCommonDAO_HI<ItemMasterEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemMasterDAO_HI.class);
	public ItemMasterDAO_HI() {
		super();
	}

	@Override
	public Object save(ItemMasterEntity_HI entity) {
		return super.save(entity);
	}
}
