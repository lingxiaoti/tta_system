package com.sie.saaf.deploy.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppMenuEntity_HI;
import org.springframework.stereotype.Component;

@Component("baseAppDeployeeMenuDAO_HI")
public class BaseAppDeployeeMenuDAO_HI extends BaseCommonDAO_HI<BaseDeployeeAppMenuEntity_HI> {
	public BaseAppDeployeeMenuDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseDeployeeAppMenuEntity_HI entity) {
		return super.save(entity);
	}
}
