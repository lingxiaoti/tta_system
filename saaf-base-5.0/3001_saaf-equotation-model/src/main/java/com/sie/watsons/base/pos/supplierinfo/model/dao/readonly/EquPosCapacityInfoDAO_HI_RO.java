package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosCapacityInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosCapacityInfoDAO_HI_RO")
public class EquPosCapacityInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPosCapacityInfoEntity_HI_RO>  {
	public EquPosCapacityInfoDAO_HI_RO() {
		super();
	}

}
