package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("basePersonDAO_HI")
public class BasePersonDAO_HI extends BaseCommonDAO_HI<BasePersonEntity_HI> {
	public BasePersonDAO_HI() {
		super();
	}
}
