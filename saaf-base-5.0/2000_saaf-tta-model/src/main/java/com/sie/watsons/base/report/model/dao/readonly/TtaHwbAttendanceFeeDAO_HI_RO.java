package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbAttendanceFeeEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaHwbAttendanceFeeDAO_HI_RO")
public class TtaHwbAttendanceFeeDAO_HI_RO extends DynamicViewObjectImpl<TtaHwbAttendanceFeeEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbAttendanceFeeDAO_HI_RO.class);
	public TtaHwbAttendanceFeeDAO_HI_RO() {
		super();
	}

}
