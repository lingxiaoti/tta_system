package com.sie.watsons.base.rule.model.inter.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.sie.watsons.base.rule.model.entities.RuleHEntity_HI;
import com.sie.watsons.base.rule.model.entities.RuleLEntity_HI;
import com.sie.watsons.base.rule.model.entities.SubjectEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.RuleLEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.SubjectEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.IRuleH;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("ruleHServer")
public class RuleHServer extends BaseCommonServer<RuleHEntity_HI> implements IRuleH {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleHServer.class);

    @Autowired
    private BaseCommonDAO_HI<RuleHEntity_HI> ruleHEntity_HIDao;
    
    @Autowired
    private BaseCommonDAO_HI<RuleLEntity_HI> ruleLEntity_HI;
    
    @Autowired
    private BaseViewObject<RuleLEntity_HI_RO> ruleLDAO_HI_RO;
    
    @Autowired
    private BaseCommonDAO_HI<SubjectEntity_HI> subjectDAO_HI;
    
    @Autowired
    private BaseViewObject<SubjectEntity_HI_RO> subjectEntity_HI_RO;

    public RuleHServer() {
        super();
    }

	@Override
	public String editRuleHeader(JSONObject params) throws Exception {
		RuleHEntity_HI ruleHEntity = new RuleHEntity_HI();
		ruleHEntity.setRuleName(params.getString("ruleName"));               
		ruleHEntity.setOpen(params.getString("open"));                
		ruleHEntity.setVersionNum(params.getInteger("versionNum"));        	
		ruleHEntity.setCreationDate(new Date());             
		ruleHEntity.setCreatedBy(params.getInteger("varUserId"));      	         						
		ruleHEntity_HIDao.save(ruleHEntity);
		return ruleHEntity.getRuleId()+"";
	}
	
	@Override
	public String editRuleLine(List<SaafQuestionnaireLEntity_HI> paramsList) throws Exception {
		RuleLEntity_HI ruleLEntity=null;
		for (SaafQuestionnaireLEntity_HI e : paramsList){
			 ruleLEntity = new RuleLEntity_HI();
			 ruleLEntity.setQnHeadId(e.getQnLineId());
			 ruleLEntity.setQnTitle(e.getProjectTitle()); 
			 /*ruleLEntity.setOptionValus(String.valueOf(e.get("optionValus")));*/ //选项
			 /*ruleLEntity.setRuleId(Integer.valueOf(String.valueOf(e.get("ruleId"))));*/ //规则头表id
			 ruleLEntity.setCreatedBy(1);
			 ruleLEntity.setCreationDate(new Date());		
			 ruleLEntity_HI.save(ruleLEntity);
        }
		return ruleLEntity.getRuleLineId()+"";
	}
	
	@Override
	public String editSubject(List<SaafQuestionnaireLEntity_HI> paramsList) throws Exception {
		SubjectEntity_HI subjectEntity=null;
		for (SaafQuestionnaireLEntity_HI e : paramsList){
			 subjectEntity = new SubjectEntity_HI();
			 subjectEntity.setRuleId(e.getQnLineId());			 
			 subjectEntity.setCreatedBy(1);
			 subjectEntity.setCreationDate(new Date());
			 /*subjectEntity.setVersionNum(Integer.valueOf(String.valueOf(e.get("versionNum"))));*/
			 subjectDAO_HI.save(subjectEntity);
        }
		return subjectEntity.getSubjectId()+"";
	}

	@Override
	public Pagination<RuleLEntity_HI_RO> findRuleLEntity(JSONObject jsonObject,
			Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<>();
		String qnHeadId = jsonObject.getString("qnHeadId");
		StringBuffer sql = new StringBuffer("select RULE_LINE_ID ruleLineId,QN_HEAD_ID qnHeadId,QN_TITLE qnTitle,OPTION_VALUS optionValus,RULE_ID ruleId,CREATION_DATE creationDate,CREATED_BY createdBy,LAST_UPDATED_BY lastUpdatedBy,LAST_UPDATE_DATE lastUpdateDate,LAST_UPDATE_LOGIN lastUpdateLogin,VERSION_NUM versionNum  from TTA_BASE_RULE_LINE where 1 = 1 ");
        if (!"".equals(qnHeadId) && qnHeadId!=null){
        	sql.append(" and QN_HEAD_ID ='"+qnHeadId+"' ");
        }
        Pagination<RuleLEntity_HI_RO> ruleLList = ruleLDAO_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);

		return ruleLList;
	}

	@Override
	public Pagination<SubjectEntity_HI_RO> findSubjectEntity(
			JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<>();
		String qnHeadId = jsonObject.getString("qnHeadId");
		StringBuffer sql = new StringBuffer("select t.SUBJECT_ID subjectId,t.QN_HEAD_ID qnHeadId,l.PROJECT_TITLE projectTitle,t.RULE_ID ruleId,t.CREATION_DATE creationDate,t.CREATED_BY createdBy,t.LAST_UPDATE_DATE lastUpdateDate,t.LAST_UPDATE_LOGIN lastUpdateLogin,t.LAST_UPDATED_BY lastUpdatedBy,t.VERSION_NUM versionNum from TTA_BASE_SUBJECT t left join tta_questionnaire_line l on t.RULE_ID=l.QN_LINE_ID where 1 = 1 ");
        if (!"".equals(qnHeadId) && qnHeadId!=null){
        	sql.append(" and QN_HEAD_ID ='"+qnHeadId+"' ");
        }
        Pagination<SubjectEntity_HI_RO> subjectList = subjectEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);

		return subjectList;
	}
	
	@Override
	public void deleteRuleLine(Integer ruleLineId) throws Exception {		
		ruleLEntity_HI.delete(ruleLineId);		
	}
	
	@Override
	public void deleteSubject(Integer subjectId) throws Exception {		
		subjectDAO_HI.delete(subjectId);		
	}

}
