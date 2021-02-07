package com.sie.watsons.base.questionnaire.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaQuestionHeaderDAO_HI_RO")
public class TtaQuestionHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaQuestionHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionHeaderDAO_HI_RO.class);
	public TtaQuestionHeaderDAO_HI_RO() {
		super();
	}

}
