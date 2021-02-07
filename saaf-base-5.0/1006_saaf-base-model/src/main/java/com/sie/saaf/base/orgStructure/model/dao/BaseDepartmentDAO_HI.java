package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseDepartmentDAO_HI")
public class BaseDepartmentDAO_HI extends BaseCommonDAO_HI<BaseDepartmentEntity_HI> {
	public BaseDepartmentDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseDepartmentEntity_HI entity) {
		return super.save(entity);
	}
}
