package com.sie.watsons.base.poc.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.poc.model.entities.XxPromHeadEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("xxPromHeadDAO_HI")
public class XxPromHeadDAO_HI extends BaseCommonDAO_HI<XxPromHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromHeadDAO_HI.class);
	public XxPromHeadDAO_HI() {
		super();
	}

	@Override
	public Object save(XxPromHeadEntity_HI entity) {
		return super.save(entity);
	}
}
