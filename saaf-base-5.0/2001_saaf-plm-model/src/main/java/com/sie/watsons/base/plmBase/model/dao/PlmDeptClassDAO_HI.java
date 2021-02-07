package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmDeptClassEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDeptClassDAO_HI")
public class PlmDeptClassDAO_HI extends BaseCommonDAO_HI<PlmDeptClassEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDeptClassDAO_HI.class);
	public PlmDeptClassDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDeptClassEntity_HI entity) {
		return super.save(entity);
	}
}
