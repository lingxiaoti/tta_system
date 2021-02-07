package com.sie.watsons.base.questionnaire.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaTestQuestionChoiceLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaTestQuestionChoiceLineDAO_HI_RO")
public class TtaTestQuestionChoiceLineDAO_HI_RO extends DynamicViewObjectImpl<TtaTestQuestionChoiceLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestQuestionChoiceLineDAO_HI_RO.class);
	public TtaTestQuestionChoiceLineDAO_HI_RO() {
		super();
	}

}
