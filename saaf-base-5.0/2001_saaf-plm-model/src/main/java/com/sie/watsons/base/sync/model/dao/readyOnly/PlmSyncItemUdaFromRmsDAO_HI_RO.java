package com.sie.watsons.base.sync.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.sync.model.entities.readonly.PlmSyncItemUdaFromRmsEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSyncItemUdaFromRmsDAO_HI_RO")
public class PlmSyncItemUdaFromRmsDAO_HI_RO extends DynamicViewObjectImpl<PlmSyncItemUdaFromRmsEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemUdaFromRmsDAO_HI_RO.class);
	public PlmSyncItemUdaFromRmsDAO_HI_RO() {
		super();
	}

}
