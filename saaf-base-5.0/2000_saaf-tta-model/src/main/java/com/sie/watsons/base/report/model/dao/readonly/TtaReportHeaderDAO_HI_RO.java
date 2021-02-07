package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaReportHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaReportHeaderDAO_HI_RO")
public class TtaReportHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaReportHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportHeaderDAO_HI_RO.class);
	public TtaReportHeaderDAO_HI_RO() {
		super();
	}

}
