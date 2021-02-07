package com.sie.saaf.base.report.model.dao;

import com.sie.saaf.base.report.model.entities.BaseReportGroupEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseReportGroupDAO_HI")
public class BaseReportGroupDAO_HI extends BaseCommonDAO_HI<BaseReportGroupEntity_HI> {
	public BaseReportGroupDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseReportGroupEntity_HI entity) {
		return super.save(entity);
	}
}
