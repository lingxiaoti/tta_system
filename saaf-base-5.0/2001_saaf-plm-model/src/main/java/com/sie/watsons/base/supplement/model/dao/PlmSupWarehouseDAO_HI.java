package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupWarehouseEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupWarehouseDAO_HI")
public class PlmSupWarehouseDAO_HI extends BaseCommonDAO_HI<PlmSupWarehouseEntity_HI> {
	public PlmSupWarehouseDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupWarehouseEntity_HI entity) {
		return super.save(entity);
	}
}
