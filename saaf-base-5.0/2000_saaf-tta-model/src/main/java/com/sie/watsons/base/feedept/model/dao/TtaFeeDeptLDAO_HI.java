package com.sie.watsons.base.feedept.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptLEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaFeeDeptLDAO_HI")
public class TtaFeeDeptLDAO_HI extends BaseCommonDAO_HI<TtaFeeDeptLEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptLDAO_HI.class);

	public TtaFeeDeptLDAO_HI() {
		super();
	}

}
