package com.sie.watsons.base.questionnaire.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireLEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("saafQuestionnaireLDAO_HI_RO")
public class SaafQuestionnaireLDAO_HI_RO extends DynamicViewObjectImpl<SaafQuestionnaireLEntity_HI_RO>  {
	public SaafQuestionnaireLDAO_HI_RO() {
		super();
	}

}
