package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgReceiveSqlEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgReceiveSqlDAO_HI")
public class MsgReceiveSqlDAO_HI extends BaseCommonDAO_HI<MsgReceiveSqlEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgReceiveSqlDAO_HI.class);
	public MsgReceiveSqlDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgReceiveSqlEntity_HI entity) {
		return super.save(entity);
	}
}
