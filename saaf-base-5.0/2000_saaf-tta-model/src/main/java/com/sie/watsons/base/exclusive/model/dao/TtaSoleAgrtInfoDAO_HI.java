package com.sie.watsons.base.exclusive.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;

@Component("ttaSoleAgrtInfoDAO_HI")
public class TtaSoleAgrtInfoDAO_HI extends BaseCommonDAO_HI<TtaSoleAgrtInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtInfoDAO_HI.class);

	public TtaSoleAgrtInfoDAO_HI() {
		super();
	}

}
