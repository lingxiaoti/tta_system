package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmDeptSubclassEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDeptSubclassDAO_HI")
public class PlmDeptSubclassDAO_HI extends BaseCommonDAO_HI<PlmDeptSubclassEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDeptSubclassDAO_HI.class);
	public PlmDeptSubclassDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDeptSubclassEntity_HI entity) {
		return super.save(entity);
	}
}
