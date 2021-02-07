package com.sie.watsons.base.sync.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.sync.model.entities.readonly.PlmSyncItemFromRmsEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSyncItemFromRmsDAO_HI_RO")
public class PlmSyncItemFromRmsDAO_HI_RO extends DynamicViewObjectImpl<PlmSyncItemFromRmsEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemFromRmsDAO_HI_RO.class);
	public PlmSyncItemFromRmsDAO_HI_RO() {
		super();
	}

}
