package com.sie.saaf.base.report.model.dao;

import com.sie.saaf.base.report.model.entities.BaseReportChartsTypeEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseReportChartsTypeDAO_HI")
public class BaseReportChartsTypeDAO_HI extends BaseCommonDAO_HI<BaseReportChartsTypeEntity_HI> {
	public BaseReportChartsTypeDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseReportChartsTypeEntity_HI entity) {
		return super.save(entity);
	}
}
