package com.sie.watsons.base.questionnaire.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaTestQuestionHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaTestQuestionHeaderDAO_HI")
public class TtaTestQuestionHeaderDAO_HI extends BaseCommonDAO_HI<TtaTestQuestionHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestQuestionHeaderDAO_HI.class);

	public TtaTestQuestionHeaderDAO_HI() {
		super();
	}

}
