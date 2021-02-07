package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.questionnaire.model.entities.TtaTestQuestionChoiceLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.questionnaire.model.inter.ITtaTestQuestionChoiceLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaTestQuestionChoiceLineServer")
public class TtaTestQuestionChoiceLineServer extends BaseCommonServer<TtaTestQuestionChoiceLineEntity_HI> implements ITtaTestQuestionChoiceLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestQuestionChoiceLineServer.class);

	@Autowired
	private ViewObject<TtaTestQuestionChoiceLineEntity_HI> ttaTestQuestionChoiceLineDAO_HI;

	public TtaTestQuestionChoiceLineServer() {
		super();
	}

}
