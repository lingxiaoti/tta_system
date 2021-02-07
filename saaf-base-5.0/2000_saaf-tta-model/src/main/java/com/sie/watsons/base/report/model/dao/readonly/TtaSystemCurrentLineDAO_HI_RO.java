package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaSystemCurrentLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSystemCurrentLineDAO_HI_RO")
public class TtaSystemCurrentLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSystemCurrentLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSystemCurrentLineDAO_HI_RO.class);
	public TtaSystemCurrentLineDAO_HI_RO() {
		super();
	}

}
