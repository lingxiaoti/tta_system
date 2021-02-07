package com.sie.watsons.base.rule.model.dao.readonly;

import com.sie.watsons.base.rule.model.entities.readonly.TempParamRuleMidleEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("tempParamRuleMidleDAO_HI_RO")
public class TempParamRuleMidleDAO_HI_RO extends DynamicViewObjectImpl<TempParamRuleMidleEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TempParamRuleMidleDAO_HI_RO.class);

	public TempParamRuleMidleDAO_HI_RO() {
		super();
	}

}
