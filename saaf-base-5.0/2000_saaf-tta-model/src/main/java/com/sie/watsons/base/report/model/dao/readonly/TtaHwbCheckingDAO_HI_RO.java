package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbCheckingEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaHwbCheckingDAO_HI_RO")
public class TtaHwbCheckingDAO_HI_RO extends DynamicViewObjectImpl<TtaHwbCheckingEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbCheckingDAO_HI_RO.class);
	public TtaHwbCheckingDAO_HI_RO() {
		super();
	}

}
