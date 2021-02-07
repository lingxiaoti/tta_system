package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgHistoryEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgHistoryDAO_HI")
public class MsgHistoryDAO_HI extends BaseCommonDAO_HI<MsgHistoryEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgHistoryDAO_HI.class);
	public MsgHistoryDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgHistoryEntity_HI entity) {
		return super.save(entity);
	}
}
