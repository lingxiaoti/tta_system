package com.sie.watsons.base.questionnaire.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireHEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireHEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISaafQuestionnaire extends IBaseCommon<SaafQuestionnaireHEntity_HI>{

	
	/**
	 *  更新提交的题目信息
	 * @param jsonObject
	 * @return
	 */
	public JSONObject updateSubmitQuestionnaire(JSONObject jsonObject);

    /**
     * 功能描述： 动态加载测试问卷
     * @author xiaoga
     * @date 2019/6/17
     * @param  
     * @return 
     */
    public JSONObject findLoadRuleTestQuestionnaire(JSONObject jsonObject);

	/**
	 * 初始化试卷信息
	 * @param jsonObject
	 * @return
	 */
	public JSONObject saveTestQuestionnaireToCopy(JSONObject jsonObject);
	

    Pagination<SaafQuestionnaireHEntity_HI_RO> findSaafQuestionnaireList(JSONObject parameters, Integer pageIndex,
                                                                    Integer pageRows);

    JSONObject findSaafQuestionnaireById(JSONObject parameters);
    
    /**
	 * 分页查询题目
	 * @jsonObject subject
	 * @return SaafQuestionnaireLEntity_HI
	 * @author zhangganghui
	 * @creteTime 2019/6/7
	 */
	Pagination<SaafQuestionnaireLEntity_HI> findSaafQuestionnaire(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
	
	Pagination<SaafQuestionnaireLEntity_HI> findSaafQuestionnaires(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
	
	JSONObject saveSaafQuestionnaireH(JSONObject parameters);

    JSONObject deleteSaafQuestionnaireH(JSONObject parameters);

    JSONObject updateSaafQuestionnaireToCopy(JSONObject parameters);

    JSONObject saveSaafQuestionnaireL(JSONObject parameters);

    JSONObject deleteSaafQuestionnaireL(JSONObject parameters);

    JSONObject deleteQnChoice(JSONObject parameters);

    JSONObject findSelectProjectList(JSONObject parameters);

    JSONObject saveSaafQuestionnaireResult(JSONObject parameters);

    JSONObject saveSaafQuestionnairePublish(JSONObject parameters);

    JSONObject updateSaafQuestionnairePublishToAbandon(JSONObject parameters);

    JSONObject updateSaafQuestionnairePublishToPublish(JSONObject parameters);

    JSONObject deleteSaafQuestionnairePublish(JSONObject parameters);

    JSONObject findSaafQuestionnairePublishList(JSONObject parameters, Integer pageIndex, Integer pageRows)
           ;

    JSONObject findSaafQuestionnaireListByMobile(JSONObject parameters);

    JSONObject findSaafQuestionnaireByPublishId(JSONObject parameters);

    List<SaafQuestionnaireHEntity_HI_RO> findUserByOrgIdList(JSONObject parameters);

    List<SaafQuestionnaireHEntity_HI_RO> findResultPerson(JSONObject parameters);

    List<SaafQuestionnaireHEntity_HI_RO> findAnswerResultList(JSONObject parameters);

    JSONObject findSaafQuestionnaireResult(JSONObject parameters);

    JSONObject findSaafQuestionnaireResultByUserId(JSONObject parameters);
    JSONObject updateSaafQuestionnaireStatus(JSONObject parameters);

    JSONObject saveSubmitSaafQuestionnairePublish(JSONObject parameters);

    JSONObject updateSaafQuestionnairePublishStatus(JSONObject parameters);

    JSONObject getPublishNewParameters(JSONObject parameters);

    JSONObject getNewParameters(JSONObject parameters);

    JSONObject findResultReport(JSONObject parameters);

    void findReportToExport(JSONObject parameters, HttpServletRequest servletRequest,
                            HttpServletResponse servletResponse);

    /**
     * 查询题库题目及题目属性
     * @param parameters
     */
    Object findQuestionnaireLAndQuestionnaireChoice(JSONObject parameters,Integer pageIndex,Integer pageRows);
}
