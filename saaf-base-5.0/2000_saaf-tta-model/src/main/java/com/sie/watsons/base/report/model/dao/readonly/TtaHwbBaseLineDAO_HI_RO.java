package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbBaseLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaHwbBaseLineDAO_HI_RO")
public class TtaHwbBaseLineDAO_HI_RO extends DynamicViewObjectImpl<TtaHwbBaseLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbBaseLineDAO_HI_RO.class);
	public TtaHwbBaseLineDAO_HI_RO() {
		super();
	}

}
