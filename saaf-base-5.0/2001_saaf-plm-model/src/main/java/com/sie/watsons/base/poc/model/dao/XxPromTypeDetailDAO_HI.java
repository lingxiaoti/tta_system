package com.sie.watsons.base.poc.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.poc.model.entities.XxPromTypeDetailEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("xxPromTypeDetailDAO_HI")
public class XxPromTypeDetailDAO_HI extends BaseCommonDAO_HI<XxPromTypeDetailEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromTypeDetailDAO_HI.class);
	public XxPromTypeDetailDAO_HI() {
		super();
	}

	@Override
	public Object save(XxPromTypeDetailEntity_HI entity) {
		return super.save(entity);
	}
}
