package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgCfgEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgCfgDAO_HI")
public class MsgCfgDAO_HI extends BaseCommonDAO_HI<MsgCfgEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgCfgDAO_HI.class);
	public MsgCfgDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgCfgEntity_HI entity) {
		return super.save(entity);
	}
}
