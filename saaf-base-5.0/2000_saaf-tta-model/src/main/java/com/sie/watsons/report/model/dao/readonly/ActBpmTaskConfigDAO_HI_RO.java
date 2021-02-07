package com.sie.watsons.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.report.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("actBpmTaskConfigDAO_HI_RO")
public class ActBpmTaskConfigDAO_HI_RO extends DynamicViewObjectImpl<ActBpmTaskConfigEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskConfigDAO_HI_RO.class);
	public ActBpmTaskConfigDAO_HI_RO() {
		super();
	}

}
