package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgUserEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgUserDAO_HI")
public class MsgUserDAO_HI extends BaseCommonDAO_HI<MsgUserEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgUserDAO_HI.class);
	public MsgUserDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgUserEntity_HI entity) {
		return super.save(entity);
	}
}
