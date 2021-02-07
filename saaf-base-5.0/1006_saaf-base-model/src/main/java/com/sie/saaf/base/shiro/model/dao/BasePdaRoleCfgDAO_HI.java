package com.sie.saaf.base.shiro.model.dao;

import com.sie.saaf.base.shiro.model.entities.BasePdaRoleCfgEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("basePdaRoleCfgDAO_HI")
public class BasePdaRoleCfgDAO_HI extends BaseCommonDAO_HI<BasePdaRoleCfgEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BasePdaRoleCfgDAO_HI.class);
	public BasePdaRoleCfgDAO_HI() {
		super();
	}

}
