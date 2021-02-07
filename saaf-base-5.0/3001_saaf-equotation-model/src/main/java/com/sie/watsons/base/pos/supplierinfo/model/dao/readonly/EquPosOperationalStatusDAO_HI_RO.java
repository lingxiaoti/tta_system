package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosOperationalStatusEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosOperationalStatusDAO_HI_RO")
public class EquPosOperationalStatusDAO_HI_RO extends DynamicViewObjectImpl<EquPosOperationalStatusEntity_HI_RO>  {
	public EquPosOperationalStatusDAO_HI_RO() {
		super();
	}

}
