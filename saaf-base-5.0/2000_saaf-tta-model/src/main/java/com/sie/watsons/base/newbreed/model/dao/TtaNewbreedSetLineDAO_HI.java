package com.sie.watsons.base.newbreed.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetLineEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaNewbreedSetLineDAO_HI")
public class TtaNewbreedSetLineDAO_HI extends BaseCommonDAO_HI<TtaNewbreedSetLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetLineDAO_HI.class);

	public TtaNewbreedSetLineDAO_HI() {
		super();
	}

}
