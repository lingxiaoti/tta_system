package com.sie.watsons.base.poc.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.poc.model.entities.XxPromGroupsEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("xxPromGroupsDAO_HI")
public class XxPromGroupsDAO_HI extends BaseCommonDAO_HI<XxPromGroupsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromGroupsDAO_HI.class);
	public XxPromGroupsDAO_HI() {
		super();
	}

	@Override
	public Object save(XxPromGroupsEntity_HI entity) {
		return super.save(entity);
	}
}
