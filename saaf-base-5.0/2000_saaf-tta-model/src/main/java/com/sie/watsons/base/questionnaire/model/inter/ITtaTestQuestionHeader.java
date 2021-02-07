package com.sie.watsons.base.questionnaire.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.questionnaire.model.entities.TtaTestQuestionHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaTestQuestionHeader extends IBaseCommon<TtaTestQuestionHeaderEntity_HI>{

	/**
	 * 初始化试卷信息
	 * @param jsonObject
	 * @return
	 */
	public JSONObject saveTestQuestionnaireToCopy(JSONObject jsonObject);
}
