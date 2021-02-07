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
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnairePublishEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnairePublish;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("saafQuestionnairePublishServer")
public class SaafQuestionnairePublishServer extends BaseCommonServer<SaafQuestionnairePublishEntity_HI> implements ISaafQuestionnairePublish{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnairePublishServer.class);

	@Autowired
	private ViewObject<SaafQuestionnairePublishEntity_HI> saafQuestionnairePublishDAO_HI;

	public SaafQuestionnairePublishServer() {
		super();
	}

}
