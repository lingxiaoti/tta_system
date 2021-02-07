package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentBatchInfoEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDevelopmentBatchInfoDAO_HI_RO")
public class PlmDevelopmentBatchInfoDAO_HI_RO extends DynamicViewObjectImpl<PlmDevelopmentBatchInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentBatchInfoDAO_HI_RO.class);
	public PlmDevelopmentBatchInfoDAO_HI_RO() {
		super();
	}

}
