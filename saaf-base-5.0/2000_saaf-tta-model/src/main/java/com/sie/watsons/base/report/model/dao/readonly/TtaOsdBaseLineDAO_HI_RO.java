package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaOsdBaseLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaOsdBaseLineDAO_HI_RO")
public class TtaOsdBaseLineDAO_HI_RO extends DynamicViewObjectImpl<TtaOsdBaseLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOsdBaseLineDAO_HI_RO.class);
	public TtaOsdBaseLineDAO_HI_RO() {
		super();
	}

}
