package com.sie.watsons.base.questionnaire.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireLEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ISaafQuestionnaireL extends IBaseCommon<SaafQuestionnaireLEntity_HI>{

	/**
	 * 查询第二级未被关联的题目
	 * @param jsonParams
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	Pagination<SaafQuestionnaireLEntity_HI_RO> findSecQuestionnairePagination(JSONObject jsonParams, int pageIndex, int pageRows);

}
