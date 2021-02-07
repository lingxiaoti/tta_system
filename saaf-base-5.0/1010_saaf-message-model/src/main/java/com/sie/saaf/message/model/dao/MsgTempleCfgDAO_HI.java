package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgTempleCfgEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgTempleCfgDAO_HI")
public class MsgTempleCfgDAO_HI extends BaseCommonDAO_HI<MsgTempleCfgEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgTempleCfgDAO_HI.class);
	public MsgTempleCfgDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgTempleCfgEntity_HI entity) {
		return super.save(entity);
	}
}
