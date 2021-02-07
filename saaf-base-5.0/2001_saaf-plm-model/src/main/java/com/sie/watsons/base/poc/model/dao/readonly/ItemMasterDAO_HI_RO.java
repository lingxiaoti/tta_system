package com.sie.watsons.base.poc.model.dao.readonly;

import com.sie.watsons.base.poc.model.entities.readonly.ItemMasterEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("itemMasterDAO_HI_RO")
public class ItemMasterDAO_HI_RO extends DynamicViewObjectImpl<ItemMasterEntity_HI_RO>  {
//	private static final Logger LOGGER = LoggerFactory.getLogger(ItemMasterDAO_HI_RO.class);
	public ItemMasterDAO_HI_RO() {
		super();
	}

}
