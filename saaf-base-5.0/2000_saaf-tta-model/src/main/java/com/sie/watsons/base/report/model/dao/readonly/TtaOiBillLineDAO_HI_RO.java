package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaOiBillLineDAO_HI_RO")
public class TtaOiBillLineDAO_HI_RO extends DynamicViewObjectImpl<TtaOiBillLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiBillLineDAO_HI_RO.class);
	public TtaOiBillLineDAO_HI_RO() {
		super();
	}

}
