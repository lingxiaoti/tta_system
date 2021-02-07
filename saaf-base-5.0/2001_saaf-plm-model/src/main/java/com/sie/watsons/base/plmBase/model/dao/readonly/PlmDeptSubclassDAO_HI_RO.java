package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptSubclassEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDeptSubclassDAO_HI_RO")
public class PlmDeptSubclassDAO_HI_RO extends DynamicViewObjectImpl<PlmDeptSubclassEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDeptSubclassDAO_HI_RO.class);
	public PlmDeptSubclassDAO_HI_RO() {
		super();
	}

}
