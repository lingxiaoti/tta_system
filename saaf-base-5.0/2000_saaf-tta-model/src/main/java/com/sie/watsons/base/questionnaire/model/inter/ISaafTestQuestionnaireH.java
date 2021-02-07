package com.sie.watsons.base.questionnaire.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafTestQuestionnaireHEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ISaafTestQuestionnaireH extends IBaseCommon<SaafTestQuestionnaireHEntity_HI>{
	
	/**
	 * 通过proposalId查询问卷信息
	 * @param jsonObject
	 * @return
	 */
	public JSONObject findTestQuestionnaireByProposalId(JSONObject jsonObject);
	
    /**
     * 查询问卷调查列表
     */
    Pagination<SaafTestQuestionnaireHEntity_HI_RO> findSaafTestQuestionnaireList(JSONObject parameters, Integer pageIndex, Integer pageRows);

    /**
     * 问卷头保存
     * @param parameters
     * @return
     */
    JSONObject saveSaafTestQuestionnaireH(JSONObject parameters) throws Exception;

    /**
     * 问卷行保存
     * @param params
     * @return
     */
    boolean saveSaafTestQuestionnaireL(JSONObject params) throws Exception;

    /**
     * 查询行及choice
     * @param params
     * @return
     */
    List<SaafTestQuestionnaireHEntity_HI_RO> findLineAndchoice(JSONObject params) throws Exception;

    /**
     * 行删除
     * @param params
     * @return
     */
    boolean deleteLine(JSONObject params) throws Exception;

    /**
     * 查询问卷调查列表 LOV
     */
    Pagination<SaafTestQuestionnaireHEntity_HI_RO> findSaafTestQuestionnaireLov(JSONObject parameters, Integer pageIndex,Integer pageRows);
}
