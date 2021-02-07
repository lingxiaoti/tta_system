package com.sie.saaf.base.sso.model.dao;

import com.sie.saaf.base.sso.model.entities.BaseSystemSsoEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseSystemSsoDAO_HI")
public class BaseSystemSsoDAO_HI extends BaseCommonDAO_HI<BaseSystemSsoEntity_HI> {
	public BaseSystemSsoDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseSystemSsoEntity_HI entity) {
		return super.save(entity);
	}
}
