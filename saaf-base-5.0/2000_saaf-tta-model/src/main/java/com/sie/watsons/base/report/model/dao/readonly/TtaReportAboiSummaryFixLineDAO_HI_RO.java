package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAboiSummaryFixLineEntity_HI_RO;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaReportAboiSummaryFixLineDAO_HI_RO")
public class TtaReportAboiSummaryFixLineDAO_HI_RO extends DynamicViewObjectImpl<TtaReportAboiSummaryFixLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAboiSummaryFixLineDAO_HI_RO.class);
	public TtaReportAboiSummaryFixLineDAO_HI_RO() {
		super();
	}

}
