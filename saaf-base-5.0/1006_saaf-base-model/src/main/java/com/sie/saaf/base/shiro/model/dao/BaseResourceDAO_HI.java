package com.sie.saaf.base.shiro.model.dao;

import com.sie.saaf.base.shiro.model.entities.BaseResourceEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseResourceDAO_HI")
public class BaseResourceDAO_HI extends BaseCommonDAO_HI<BaseResourceEntity_HI> {
	public BaseResourceDAO_HI() {
		super();
	}

}
