package com.sie.saaf.message.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgSourceCfgDAO_HI")
public class MsgSourceCfgDAO_HI extends BaseCommonDAO_HI<MsgSourceCfgEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgSourceCfgDAO_HI.class);
	public MsgSourceCfgDAO_HI() {
		super();
	}

	@Override
	public Object save(MsgSourceCfgEntity_HI entity) {
		return super.save(entity);
	}
}
