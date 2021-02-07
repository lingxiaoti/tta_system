package com.sie.watsons.base.params.model.dao;

import com.sie.watsons.base.params.model.entities.TempParamDefEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;

@Component("tempParamDefDAO_HI")
public class TempParamDefDAO_HI extends BaseCommonDAO_HI<TempParamDefEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TempParamDefDAO_HI.class);

	public TempParamDefDAO_HI() {
		super();
	}

}
