package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BasePositionEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("basePositionDAO_HI")
public class BasePositionDAO_HI extends BaseCommonDAO_HI<BasePositionEntity_HI> {
	public BasePositionDAO_HI() {
		super();
	}
}
