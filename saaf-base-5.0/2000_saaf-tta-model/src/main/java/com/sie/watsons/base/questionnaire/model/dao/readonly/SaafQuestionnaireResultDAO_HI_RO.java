package com.sie.watsons.base.questionnaire.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireResultEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("saafQuestionnaireResultDAO_HI_RO")
public class SaafQuestionnaireResultDAO_HI_RO extends DynamicViewObjectImpl<SaafQuestionnaireResultEntity_HI_RO>  {
	public SaafQuestionnaireResultDAO_HI_RO() {
		super();
	}

}
