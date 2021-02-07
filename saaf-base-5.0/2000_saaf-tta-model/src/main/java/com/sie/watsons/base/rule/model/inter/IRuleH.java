package com.sie.watsons.base.rule.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.sie.watsons.base.rule.model.entities.RuleHEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.RuleLEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.SubjectEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IRuleH extends IBaseCommon<RuleHEntity_HI>{
	
	String editRuleHeader(JSONObject parameters) throws Exception;
	
	String editRuleLine(List<SaafQuestionnaireLEntity_HI> paramsList) throws Exception;
	
	String editSubject(List<SaafQuestionnaireLEntity_HI> paramsList) throws Exception;
	
	Pagination<RuleLEntity_HI_RO> findRuleLEntity(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
	
	Pagination<SubjectEntity_HI_RO> findSubjectEntity(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
	
	void deleteRuleLine(Integer ruleLineId) throws Exception;
	
	void deleteSubject(Integer subjectId) throws Exception;

}
