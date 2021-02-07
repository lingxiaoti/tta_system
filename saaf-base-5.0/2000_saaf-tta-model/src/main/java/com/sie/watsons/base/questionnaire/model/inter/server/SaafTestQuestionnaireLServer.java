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
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireLEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.questionnaire.model.inter.ISaafTestQuestionnaireL;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("saafTestQuestionnaireLServer")
public class SaafTestQuestionnaireLServer extends BaseCommonServer<SaafTestQuestionnaireLEntity_HI> implements ISaafTestQuestionnaireL{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafTestQuestionnaireLServer.class);

	@Autowired
	private ViewObject<SaafTestQuestionnaireLEntity_HI> saafTestQuestionnaireLDAO_HI;

	public SaafTestQuestionnaireLServer() {
		super();
	}

}
