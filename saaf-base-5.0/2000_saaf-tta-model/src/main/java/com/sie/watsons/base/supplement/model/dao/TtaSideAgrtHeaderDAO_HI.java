package com.sie.watsons.base.supplement.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtHeaderEntity_HI;

@Component("ttaSideAgrtHeaderDAO_HI")
public class TtaSideAgrtHeaderDAO_HI extends BaseCommonDAO_HI<TtaSideAgrtHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtHeaderDAO_HI.class);

	public TtaSideAgrtHeaderDAO_HI() {
		super();
	}

}
