package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.dao.readonly.SaafQuestionnaireLDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireLEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnaireL;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component("saafQuestionnaireLServer")
public class SaafQuestionnaireLServer extends BaseCommonServer<SaafQuestionnaireLEntity_HI> implements ISaafQuestionnaireL{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireLServer.class);

	@Autowired
	private ViewObject<SaafQuestionnaireLEntity_HI> saafQuestionnaireLDAO_HI;
	
	@Autowired
	private SaafQuestionnaireLDAO_HI_RO saafQuestionnaireLDAO_HI_RO;

	public SaafQuestionnaireLServer() {
		super();
	}

	@Override
	public Pagination<SaafQuestionnaireLEntity_HI_RO> findSecQuestionnairePagination(JSONObject jsonParams, int pageIndex,
			int pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(SaafQuestionnaireLEntity_HI_RO.QUERY_SEC_QUS_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		jsonParams = SaafToolUtils.cleanNull(jsonParams,"projectTitle");
		//SaafToolUtils.parperParam(jsonParams, "a.project_title", "projectTitle", sql, paramsMap, "=");
		if (!StringUtils.isEmpty(jsonParams.getString("projectTitle"))) {
			sql.append(" and a.project_title like '%" + jsonParams.getString("projectTitle") + "%'");
		}
		SaafToolUtils.changeQuerySort(jsonParams, sql, "a.project_title desc", false);
		return saafQuestionnaireLDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
	}

}
