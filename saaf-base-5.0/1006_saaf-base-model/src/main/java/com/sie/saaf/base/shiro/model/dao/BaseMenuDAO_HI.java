package com.sie.saaf.base.shiro.model.dao;

import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseMenuDAO_HI")
public class BaseMenuDAO_HI extends BaseCommonDAO_HI<BaseMenuEntity_HI> {
	public BaseMenuDAO_HI() {
		super();
	}

}
