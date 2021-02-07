package com.sie.saaf.base.feedback.model.dao;

import com.sie.saaf.base.feedback.model.entities.BaseIssueFeedbackEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseIssueFeedbackDAO_HI")
public class BaseIssueFeedbackDAO_HI extends BaseCommonDAO_HI<BaseIssueFeedbackEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseIssueFeedbackDAO_HI.class);
	public BaseIssueFeedbackDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseIssueFeedbackEntity_HI entity) {
		return super.save(entity);
	}
}
