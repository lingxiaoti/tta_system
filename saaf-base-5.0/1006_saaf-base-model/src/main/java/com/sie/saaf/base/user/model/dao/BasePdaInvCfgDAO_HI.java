package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BasePdaInvCfgEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("basePdaInvCfgDAO_HI")
public class BasePdaInvCfgDAO_HI extends BaseCommonDAO_HI<BasePdaInvCfgEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BasePdaInvCfgDAO_HI.class);
	public BasePdaInvCfgDAO_HI() {
		super();
	}

}
