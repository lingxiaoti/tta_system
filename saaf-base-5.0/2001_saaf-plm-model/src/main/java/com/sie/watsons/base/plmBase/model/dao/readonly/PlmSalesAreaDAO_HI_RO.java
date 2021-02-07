package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSalesAreaEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSalesAreaDAO_HI_RO")
public class PlmSalesAreaDAO_HI_RO extends DynamicViewObjectImpl<PlmSalesAreaEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSalesAreaDAO_HI_RO.class);
	public PlmSalesAreaDAO_HI_RO() {
		super();
	}

}
