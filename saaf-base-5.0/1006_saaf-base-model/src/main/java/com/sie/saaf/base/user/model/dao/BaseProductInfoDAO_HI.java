package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseProductInfoEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseProductInfoDAO_HI")
public class BaseProductInfoDAO_HI extends BaseCommonDAO_HI<BaseProductInfoEntity_HI> {
	public BaseProductInfoDAO_HI() {
		super();
	}
}
