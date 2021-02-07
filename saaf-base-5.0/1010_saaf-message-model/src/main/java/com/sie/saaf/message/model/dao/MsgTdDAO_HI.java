package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgTdEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgTdDAO_HI")
public class MsgTdDAO_HI extends BaseCommonDAO_HI<MsgTdEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgTdDAO_HI.class);
	public MsgTdDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgTdEntity_HI entity) {
		return super.save(entity);
	}
}
