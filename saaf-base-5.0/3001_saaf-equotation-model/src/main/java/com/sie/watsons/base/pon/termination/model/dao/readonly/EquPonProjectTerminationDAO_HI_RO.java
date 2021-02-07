package com.sie.watsons.base.pon.termination.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.termination.model.entities.readonly.EquPonProjectTerminationEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPonProjectTerminationDAO_HI_RO")
public class EquPonProjectTerminationDAO_HI_RO extends DynamicViewObjectImpl<EquPonProjectTerminationEntity_HI_RO>  {
	public EquPonProjectTerminationDAO_HI_RO() {
		super();
	}

}
