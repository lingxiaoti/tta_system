package com.sie.watsons.base.exclusive.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleItemEntity_HI;

@Component("ttaSoleItemDAO_HI")
public class TtaSoleItemDAO_HI extends BaseCommonDAO_HI<TtaSoleItemEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleItemDAO_HI.class);

	public TtaSoleItemDAO_HI() {
		super();
	}

}
