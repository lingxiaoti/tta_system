package com.sie.watsons.base.questionnaire.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaSubjectChoiceSecMidEntity_HI_RO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSubjectChoiceSecMidDAO_HI_RO")
public class TtaSubjectChoiceSecMidDAO_HI_RO extends DynamicViewObjectImpl<TtaSubjectChoiceSecMidEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSubjectChoiceSecMidDAO_HI_RO.class);
	public TtaSubjectChoiceSecMidDAO_HI_RO() {
		super();
	}

}
