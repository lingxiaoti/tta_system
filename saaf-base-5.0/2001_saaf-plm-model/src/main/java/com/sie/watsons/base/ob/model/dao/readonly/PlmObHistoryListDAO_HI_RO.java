package com.sie.watsons.base.ob.model.dao.readonly;

import com.sie.watsons.base.ob.model.entities.readonly.PlmObHistoryListEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmObHistoryListDAO_HI_RO")
public class PlmObHistoryListDAO_HI_RO extends DynamicViewObjectImpl<PlmObHistoryListEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmObHistoryListDAO_HI_RO.class);
	public PlmObHistoryListDAO_HI_RO() {
		super();
	}

}
