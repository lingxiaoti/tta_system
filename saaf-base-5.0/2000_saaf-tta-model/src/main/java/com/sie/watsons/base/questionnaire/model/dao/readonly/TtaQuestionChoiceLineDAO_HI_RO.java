package com.sie.watsons.base.questionnaire.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionChoiceLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaQuestionChoiceLineDAO_HI_RO")
public class TtaQuestionChoiceLineDAO_HI_RO extends DynamicViewObjectImpl<TtaQuestionChoiceLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionChoiceLineDAO_HI_RO.class);
	public TtaQuestionChoiceLineDAO_HI_RO() {
		super();
	}

}
