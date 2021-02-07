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
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireChoiceEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnaireChoice;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("saafQuestionnaireChoiceServer")
public class SaafQuestionnaireChoiceServer extends BaseCommonServer<SaafQuestionnaireChoiceEntity_HI> implements ISaafQuestionnaireChoice{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireChoiceServer.class);

	@Autowired
	private ViewObject<SaafQuestionnaireChoiceEntity_HI> saafQuestionnaireChoiceDAO_HI;

	public SaafQuestionnaireChoiceServer() {
		super();
	}

}
