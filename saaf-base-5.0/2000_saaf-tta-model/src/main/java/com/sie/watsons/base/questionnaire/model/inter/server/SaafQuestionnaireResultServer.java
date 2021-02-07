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
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireResultEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnaireResult;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("saafQuestionnaireResultServer")
public class SaafQuestionnaireResultServer extends BaseCommonServer<SaafQuestionnaireResultEntity_HI> implements ISaafQuestionnaireResult{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireResultServer.class);

	@Autowired
	private ViewObject<SaafQuestionnaireResultEntity_HI> saafQuestionnaireResultDAO_HI;

	public SaafQuestionnaireResultServer() {
		super();
	}

}
