package com.sie.watsons.report.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.report.model.entities.readonly.TermsComparisionReportEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("termsComparisionReportDAO_HI_RO")
public class TermsComparisionReportDAO_HI_RO extends DynamicViewObjectImpl<TermsComparisionReportEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TermsComparisionReportEntity_HI_RO.class);
	public TermsComparisionReportDAO_HI_RO() {
		super();
	}

}
