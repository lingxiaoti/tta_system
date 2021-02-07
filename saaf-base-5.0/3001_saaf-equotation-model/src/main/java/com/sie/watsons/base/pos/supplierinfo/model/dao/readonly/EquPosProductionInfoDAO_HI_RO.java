package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosProductionInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosProductionInfoDAO_HI_RO")
public class EquPosProductionInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPosProductionInfoEntity_HI_RO>  {
	public EquPosProductionInfoDAO_HI_RO() {
		super();
	}

}
