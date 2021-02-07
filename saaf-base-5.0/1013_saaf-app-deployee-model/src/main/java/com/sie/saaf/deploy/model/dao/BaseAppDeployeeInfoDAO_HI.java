package com.sie.saaf.deploy.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppInfoEntity_HI;
import org.springframework.stereotype.Component;

@Component("baseAppDeployeeInfoDAO_HI")
public class BaseAppDeployeeInfoDAO_HI extends BaseCommonDAO_HI<BaseDeployeeAppInfoEntity_HI> {
	public BaseAppDeployeeInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseDeployeeAppInfoEntity_HI entity) {
		return super.save(entity);
	}
}
