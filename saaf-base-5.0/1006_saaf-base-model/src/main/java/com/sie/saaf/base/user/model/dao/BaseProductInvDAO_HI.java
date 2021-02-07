package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseProductInvEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseProductInvDAO_HI")
public class BaseProductInvDAO_HI extends BaseCommonDAO_HI<BaseProductInvEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseProductInvDAO_HI.class);
	public BaseProductInvDAO_HI() {
		super();
	}

}
