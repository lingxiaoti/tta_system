package com.sie.watsons.base.newbreedextend.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendHeaderEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaNewbreedExtendHeaderDAO_HI")
public class TtaNewbreedExtendHeaderDAO_HI extends BaseCommonDAO_HI<TtaNewbreedExtendHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendHeaderDAO_HI.class);

	public TtaNewbreedExtendHeaderDAO_HI() {
		super();
	}

}
