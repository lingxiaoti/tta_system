package com.sie.watsons.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.report.model.entities.readonly.TtaContractLineReportEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaContractLineReportDAO_HI_RO")
public class TtaContractLineReportDAO_HI_RO extends DynamicViewObjectImpl<TtaContractLineReportEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractLineReportDAO_HI_RO.class);
	public TtaContractLineReportDAO_HI_RO() {
		super();
	}

}
