package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmLocationListEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmLocationListDAO_HI_RO")
public class PlmLocationListDAO_HI_RO extends DynamicViewObjectImpl<PlmLocationListEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmLocationListDAO_HI_RO.class);
	public PlmLocationListDAO_HI_RO() {
		super();
	}

}
