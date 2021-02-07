package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptClassEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDeptClassDAO_HI_RO")
public class PlmDeptClassDAO_HI_RO extends DynamicViewObjectImpl<PlmDeptClassEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDeptClassDAO_HI_RO.class);
	public PlmDeptClassDAO_HI_RO() {
		super();
	}

}
