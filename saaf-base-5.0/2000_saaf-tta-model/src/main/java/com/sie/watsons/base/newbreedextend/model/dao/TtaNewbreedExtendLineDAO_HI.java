package com.sie.watsons.base.newbreedextend.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendLineEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaNewbreedExtendLineDAO_HI")
public class TtaNewbreedExtendLineDAO_HI extends BaseCommonDAO_HI<TtaNewbreedExtendLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendLineDAO_HI.class);

	public TtaNewbreedExtendLineDAO_HI() {
		super();
	}

}
