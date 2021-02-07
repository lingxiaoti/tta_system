package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaOiReportHeaderDAO_HI_RO")
public class TtaOiReportHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaOiReportHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportHeaderDAO_HI_RO.class);
	public TtaOiReportHeaderDAO_HI_RO() {
		super();
	}

}
