package com.sie.watsons.base.rule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.rule.model.entities.TempParamRuleMidleEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("tempParamRuleMidleDAO_HI")
public class TempParamRuleMidleDAO_HI extends BaseCommonDAO_HI<TempParamRuleMidleEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TempParamRuleMidleDAO_HI.class);

	public TempParamRuleMidleDAO_HI() {
		super();
	}

}
