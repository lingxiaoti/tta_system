package com.sie.watsons.base.questionnaire.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaTestQuestionHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaTestQuestionHeaderDAO_HI_RO")
public class TtaTestQuestionHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaTestQuestionHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestQuestionHeaderDAO_HI_RO.class);
	public TtaTestQuestionHeaderDAO_HI_RO() {
		super();
	}

}
