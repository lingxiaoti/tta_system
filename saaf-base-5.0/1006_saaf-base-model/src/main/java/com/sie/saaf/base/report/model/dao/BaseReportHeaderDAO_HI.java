package com.sie.saaf.base.report.model.dao;

import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseReportHeaderDAO_HI")
public class
BaseReportHeaderDAO_HI extends BaseCommonDAO_HI<BaseReportHeaderEntity_HI> {
	public BaseReportHeaderDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseReportHeaderEntity_HI entity) {
		return super.save(entity);
	}
}
