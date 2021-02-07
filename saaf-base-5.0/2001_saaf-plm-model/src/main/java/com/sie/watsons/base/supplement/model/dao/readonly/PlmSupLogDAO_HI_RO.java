package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupLogEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("plmSupLogDAO_HI_RO")
public class PlmSupLogDAO_HI_RO extends DynamicViewObjectImpl<PlmSupLogEntity_HI_RO>  {
	public PlmSupLogDAO_HI_RO() {
		super();
	}

}
