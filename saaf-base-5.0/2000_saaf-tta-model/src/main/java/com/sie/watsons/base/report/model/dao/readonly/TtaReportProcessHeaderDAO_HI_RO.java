package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaReportProcessHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaReportProcessHeaderDAO_HI_RO")
public class TtaReportProcessHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaReportProcessHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportProcessHeaderDAO_HI_RO.class);
	public TtaReportProcessHeaderDAO_HI_RO() {
		super();
	}

}
