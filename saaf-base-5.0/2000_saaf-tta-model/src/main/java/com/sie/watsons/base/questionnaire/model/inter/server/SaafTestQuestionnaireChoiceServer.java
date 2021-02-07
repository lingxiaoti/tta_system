package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.dao.SaafTestQuestionnaireHDAO_HI;
import com.sie.watsons.base.questionnaire.model.dao.readonly.SaafTestQuestionnaireHDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireHEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireHEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafTestQuestionnaireHEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireChoiceEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.questionnaire.model.inter.ISaafTestQuestionnaireChoice;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("saafTestQuestionnaireChoiceServer")
public class SaafTestQuestionnaireChoiceServer extends BaseCommonServer<SaafTestQuestionnaireChoiceEntity_HI> implements ISaafTestQuestionnaireChoice{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafTestQuestionnaireChoiceServer.class);



}
