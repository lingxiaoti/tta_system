package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmHelpStatementsEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmHelpStatementsDAO_HI_RO")
public class PlmHelpStatementsDAO_HI_RO extends DynamicViewObjectImpl<PlmHelpStatementsEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmHelpStatementsDAO_HI_RO.class);
	public PlmHelpStatementsDAO_HI_RO() {
		super();
	}

}
