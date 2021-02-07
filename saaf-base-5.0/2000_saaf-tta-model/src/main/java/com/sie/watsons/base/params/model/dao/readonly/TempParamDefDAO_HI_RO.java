package com.sie.watsons.base.params.model.dao.readonly;

import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("tempParamDefDAO_HI_RO")
public class TempParamDefDAO_HI_RO extends DynamicViewObjectImpl<TempParamDefEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TempParamDefDAO_HI_RO.class);
	public TempParamDefDAO_HI_RO() {
		super();
	}

}
