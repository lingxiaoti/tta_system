package com.sie.saaf.base.report.model.dao;

import com.sie.saaf.base.report.model.entities.BaseReportDatasourceEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseReportDatasourceDAO_HI")
public class BaseReportDatasourceDAO_HI extends BaseCommonDAO_HI<BaseReportDatasourceEntity_HI> {
	public BaseReportDatasourceDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseReportDatasourceEntity_HI entity) {
		return super.save(entity);
	}
}
