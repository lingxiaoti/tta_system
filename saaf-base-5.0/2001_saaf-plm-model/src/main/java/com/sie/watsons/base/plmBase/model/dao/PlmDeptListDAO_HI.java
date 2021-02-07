package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmDeptListEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDeptListDAO_HI")
public class PlmDeptListDAO_HI extends BaseCommonDAO_HI<PlmDeptListEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDeptListDAO_HI.class);
	public PlmDeptListDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDeptListEntity_HI entity) {
		return super.save(entity);
	}
}
