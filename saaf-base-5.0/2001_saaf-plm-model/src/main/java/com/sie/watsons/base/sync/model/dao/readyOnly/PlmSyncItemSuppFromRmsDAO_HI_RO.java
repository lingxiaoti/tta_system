package com.sie.watsons.base.sync.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.sync.model.entities.readonly.PlmSyncItemSuppFromRmsEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSyncItemSuppFromRmsDAO_HI_RO")
public class PlmSyncItemSuppFromRmsDAO_HI_RO extends DynamicViewObjectImpl<PlmSyncItemSuppFromRmsEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemSuppFromRmsDAO_HI_RO.class);
	public PlmSyncItemSuppFromRmsDAO_HI_RO() {
		super();
	}

}
