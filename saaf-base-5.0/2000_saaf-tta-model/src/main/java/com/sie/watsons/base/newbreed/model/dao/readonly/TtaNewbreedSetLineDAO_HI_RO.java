package com.sie.watsons.base.newbreed.model.dao.readonly;

import com.sie.watsons.base.newbreed.model.entities.readonly.TtaNewbreedSetLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaNewbreedSetLineDAO_HI_RO")
public class TtaNewbreedSetLineDAO_HI_RO extends DynamicViewObjectImpl<TtaNewbreedSetLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetLineDAO_HI_RO.class);
	public TtaNewbreedSetLineDAO_HI_RO() {
		super();
	}

}
