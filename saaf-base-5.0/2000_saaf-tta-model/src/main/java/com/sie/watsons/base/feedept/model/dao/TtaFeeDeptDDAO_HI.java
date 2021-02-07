package com.sie.watsons.base.feedept.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptDEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaFeeDeptDDAO_HI")
public class TtaFeeDeptDDAO_HI extends BaseCommonDAO_HI<TtaFeeDeptDEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptDDAO_HI.class);

	public TtaFeeDeptDDAO_HI() {
		super();
	}

}
