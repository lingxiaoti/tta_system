package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgInstanceEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgInstanceDAO_HI")
public class MsgInstanceDAO_HI extends BaseCommonDAO_HI<MsgInstanceEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgInstanceDAO_HI.class);
	public MsgInstanceDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgInstanceEntity_HI entity) {
		return super.save(entity);
	}
}
