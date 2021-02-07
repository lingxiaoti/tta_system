package com.sie.watsons.base.questionnaire.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireHEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("saafQuestionnaireHDAO_HI_RO")
public class SaafQuestionnaireHDAO_HI_RO extends DynamicViewObjectImpl<SaafQuestionnaireHEntity_HI_RO>  {
	public SaafQuestionnaireHDAO_HI_RO() {
		super();
	}

}
