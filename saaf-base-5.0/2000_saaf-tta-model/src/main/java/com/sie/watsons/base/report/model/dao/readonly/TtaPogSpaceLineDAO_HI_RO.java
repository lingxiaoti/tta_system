package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaPogSpaceLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaPogSpaceLineDAO_HI_RO")
public class TtaPogSpaceLineDAO_HI_RO extends DynamicViewObjectImpl<TtaPogSpaceLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPogSpaceLineDAO_HI_RO.class);
	public TtaPogSpaceLineDAO_HI_RO() {
		super();
	}

}
