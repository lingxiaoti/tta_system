package com.sie.saaf.base.orgStructure.model.dao.readonly;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseDepartmentDAO_HI_RO")
public class BaseDepartmentDAO_HI_RO extends DynamicViewObjectImpl<BaseDepartmentEntity_HI_RO> {
	public BaseDepartmentDAO_HI_RO() {
		super();
	}
}
