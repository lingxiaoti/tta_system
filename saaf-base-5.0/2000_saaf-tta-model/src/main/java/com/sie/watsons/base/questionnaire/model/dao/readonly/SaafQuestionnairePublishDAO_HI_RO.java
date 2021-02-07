package com.sie.watsons.base.questionnaire.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnairePublishEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("saafQuestionnairePublishDAO_HI_RO")
public class SaafQuestionnairePublishDAO_HI_RO extends DynamicViewObjectImpl<SaafQuestionnairePublishEntity_HI_RO>  {
	public SaafQuestionnairePublishDAO_HI_RO() {
		super();
	}

}
