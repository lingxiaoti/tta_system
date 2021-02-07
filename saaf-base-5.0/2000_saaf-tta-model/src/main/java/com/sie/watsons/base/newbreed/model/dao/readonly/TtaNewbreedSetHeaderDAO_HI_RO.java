package com.sie.watsons.base.newbreed.model.dao.readonly;

import com.sie.watsons.base.newbreed.model.entities.readonly.TtaNewbreedSetHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaNewbreedSetHeaderDAO_HI_RO")
public class TtaNewbreedSetHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaNewbreedSetHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetHeaderDAO_HI_RO.class);
	public TtaNewbreedSetHeaderDAO_HI_RO() {
		super();
	}

}
