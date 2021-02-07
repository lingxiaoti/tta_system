package com.sie.watsons.base.questionnaire.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaTestChoiceMidEntity_HI_RO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaTestChoiceMidDAO_HI_RO")
public class TtaTestChoiceMidDAO_HI_RO extends DynamicViewObjectImpl<TtaTestChoiceMidEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestChoiceMidDAO_HI_RO.class);
	public TtaTestChoiceMidDAO_HI_RO() {
		super();
	}

}
