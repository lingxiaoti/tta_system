package com.sie.watsons.base.questionnaire.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionChoiceLineEntity_HI;

@Component("ttaQuestionChoiceLineDAO_HI")
public class TtaQuestionChoiceLineDAO_HI extends BaseCommonDAO_HI<TtaQuestionChoiceLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionChoiceLineDAO_HI.class);

	public TtaQuestionChoiceLineDAO_HI() {
		super();
	}

}
