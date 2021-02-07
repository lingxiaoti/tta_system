package com.sie.watsons.base.questionnaire.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionHeaderEntity_HI;

@Component("ttaQuestionHeaderDAO_HI")
public class TtaQuestionHeaderDAO_HI extends BaseCommonDAO_HI<TtaQuestionHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionHeaderDAO_HI.class);

	public TtaQuestionHeaderDAO_HI() {
		super();
	}

}
