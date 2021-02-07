package com.sie.saaf.base.feedback.model.dao.readonly;

import com.sie.saaf.base.feedback.model.entities.readonly.BaseIssueFeedbackEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseIssueFeedbackDAO_HI_RO")
public class BaseIssueFeedbackDAO_HI_RO extends DynamicViewObjectImpl<BaseIssueFeedbackEntity_HI_RO> {

	public BaseIssueFeedbackDAO_HI_RO() {
		super();
	}

}
