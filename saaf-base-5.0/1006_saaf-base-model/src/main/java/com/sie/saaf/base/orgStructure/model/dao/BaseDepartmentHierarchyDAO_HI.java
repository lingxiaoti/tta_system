package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentHierarchyEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseDepartmentHierarchyDAO_HI")
public class BaseDepartmentHierarchyDAO_HI extends BaseCommonDAO_HI<BaseDepartmentHierarchyEntity_HI> {
	public BaseDepartmentHierarchyDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseDepartmentHierarchyEntity_HI entity) {
		return super.save(entity);
	}
}
