package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionChoiceLineEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionHeaderEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaTestQuestionChoiceLineEntity_HI;
import com.sie.watsons.base.questionnaire.model.inter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.questionnaire.model.entities.TtaTestQuestionHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaTestQuestionHeaderServer")
public class TtaTestQuestionHeaderServer extends BaseCommonServer<TtaTestQuestionHeaderEntity_HI> implements ITtaTestQuestionHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestQuestionHeaderServer.class);

	@Autowired
	private ViewObject<TtaTestQuestionHeaderEntity_HI> ttaTestQuestionHeaderDAO_HI;

	@Autowired
	private ITtaTestQuestionHeader iTtaTestQuestionHeader;

	@Autowired
	private ITtaTestQuestionChoiceLine  iTtaTestQuestionChoiceLine;

	@Autowired
	private ITtaQuestionHeader iTtaQuestionHeader;

	@Autowired
	private ITtaQuestionChoiceLine iTtaQuestionChoiceLine;



	@Autowired
	private IRule iRule;

	public TtaTestQuestionHeaderServer() {
		super();
	}

	@Override
	public JSONObject saveTestQuestionnaireToCopy(JSONObject jsonObject) {
		return null;
	}
}
