package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSalesAreaRowEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSalesAreaRowDAO_HI_RO")
public class PlmSalesAreaRowDAO_HI_RO extends DynamicViewObjectImpl<PlmSalesAreaRowEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSalesAreaRowDAO_HI_RO.class);
	public PlmSalesAreaRowDAO_HI_RO() {
		super();
	}

}
