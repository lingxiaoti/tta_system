package com.sie.saaf.base.report.model.dao;

import com.sie.saaf.base.report.model.entities.BaseReportGroupHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseReportGroupHeaderDAO_HI")
public class BaseReportGroupHeaderDAO_HI extends ViewObjectImpl<BaseReportGroupHeaderEntity_HI>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseReportGroupHeaderDAO_HI.class);
	public BaseReportGroupHeaderDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseReportGroupHeaderEntity_HI entity) {
		return super.save(entity);
	}
}
