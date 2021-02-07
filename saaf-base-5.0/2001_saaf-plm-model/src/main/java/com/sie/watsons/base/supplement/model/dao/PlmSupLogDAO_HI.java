package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupLogEntity_HI;
import org.springframework.stereotype.Component;

@Component("plmSupLogDAO_HI")
public class PlmSupLogDAO_HI extends BaseCommonDAO_HI<PlmSupLogEntity_HI> {
	public PlmSupLogDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupLogEntity_HI entity) {
		return super.save(entity);
	}
}
