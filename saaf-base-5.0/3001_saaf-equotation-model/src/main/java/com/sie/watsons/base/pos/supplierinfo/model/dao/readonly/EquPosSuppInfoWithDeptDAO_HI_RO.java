package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSuppInfoWithDeptEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSuppInfoWithDeptDAO_HI_RO")
public class EquPosSuppInfoWithDeptDAO_HI_RO extends DynamicViewObjectImpl<EquPosSuppInfoWithDeptEntity_HI_RO>  {
	public EquPosSuppInfoWithDeptDAO_HI_RO() {
		super();
	}

}
