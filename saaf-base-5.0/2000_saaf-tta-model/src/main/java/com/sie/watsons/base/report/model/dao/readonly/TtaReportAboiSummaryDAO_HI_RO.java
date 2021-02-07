package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAboiSummaryEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaReportAboiSummaryDAO_HI_RO")
public class TtaReportAboiSummaryDAO_HI_RO extends DynamicViewObjectImpl<TtaReportAboiSummaryEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAboiSummaryDAO_HI_RO.class);
	public TtaReportAboiSummaryDAO_HI_RO() {
		super();
	}

}
