package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbAwardPolistEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaHwbAwardPolistDAO_HI_RO")
public class TtaHwbAwardPolistDAO_HI_RO extends DynamicViewObjectImpl<TtaHwbAwardPolistEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbAwardPolistDAO_HI_RO.class);
	public TtaHwbAwardPolistDAO_HI_RO() {
		super();
	}

}
