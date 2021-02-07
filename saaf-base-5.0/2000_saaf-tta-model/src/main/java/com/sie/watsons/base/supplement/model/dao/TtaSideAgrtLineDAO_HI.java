package com.sie.watsons.base.supplement.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtLineEntity_HI;

@Component("ttaSideAgrtLineDAO_HI")
public class TtaSideAgrtLineDAO_HI extends BaseCommonDAO_HI<TtaSideAgrtLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtLineDAO_HI.class);

	public TtaSideAgrtLineDAO_HI() {
		super();
	}

}
