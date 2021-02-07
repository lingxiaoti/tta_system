package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgLogEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgLogDAO_HI")
public class MsgLogDAO_HI extends BaseCommonDAO_HI<MsgLogEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgLogDAO_HI.class);
	public MsgLogDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgLogEntity_HI entity) {
		return super.save(entity);
	}
}
