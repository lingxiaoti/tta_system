package com.sie.watsons.base.questionnaire.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionNewMapDetailEntity_HI_RO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaQuestionNewMapDetailDAO_HI_RO")
public class TtaQuestionNewMapDetailDAO_HI_RO extends DynamicViewObjectImpl<TtaQuestionNewMapDetailEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionNewMapDetailDAO_HI_RO.class);

	public TtaQuestionNewMapDetailDAO_HI_RO() {
		super();
	}

}
