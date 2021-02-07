package com.sie.saaf.base.report.model.dao;

import com.sie.saaf.base.report.model.entities.BaseReportLineEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseReportLineDAO_HI")
public class BaseReportLineDAO_HI extends BaseCommonDAO_HI<BaseReportLineEntity_HI> {
	public BaseReportLineDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseReportLineEntity_HI entity) {
		return super.save(entity);
	}
}
