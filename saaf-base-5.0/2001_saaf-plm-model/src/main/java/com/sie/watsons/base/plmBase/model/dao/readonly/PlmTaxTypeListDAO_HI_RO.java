package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmTaxTypeListEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmTaxTypeListDAO_HI_RO")
public class PlmTaxTypeListDAO_HI_RO extends DynamicViewObjectImpl<PlmTaxTypeListEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmTaxTypeListDAO_HI_RO.class);
	public PlmTaxTypeListDAO_HI_RO() {
		super();
	}

}
