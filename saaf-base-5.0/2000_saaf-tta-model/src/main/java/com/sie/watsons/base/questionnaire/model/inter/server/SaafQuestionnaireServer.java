package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.dao.*;
import com.sie.watsons.base.questionnaire.model.dao.readonly.*;
import com.sie.watsons.base.questionnaire.model.entities.*;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireChoiceEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireHEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireLEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnaire;
import com.sie.watsons.base.rule.model.dao.RuleHDAO_HI;
import com.sie.watsons.base.rule.model.dao.RuleLDAO_HI;
import com.sie.watsons.base.rule.model.dao.readonly.RuleLDAO_HI_RO;
import com.sie.watsons.base.rule.model.dao.readonly.SubjectDAO_HI_RO;
import com.sie.watsons.base.rule.model.entities.RuleHEntity_HI;
import com.sie.watsons.base.rule.model.entities.RuleLEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.RuleLEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.SubjectEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("saafQuestionnaireServer")
public class SaafQuestionnaireServer extends BaseCommonServer<SaafQuestionnaireHEntity_HI> implements ISaafQuestionnaire {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireServer.class);
	@Autowired
	private SaafQuestionnaireHDAO_HI saafQuestionnaireHDAO_HI;
	@Autowired
	private SaafQuestionnaireHDAO_HI_RO saafQuestionnaireHDAO_HI_RO;
	@Autowired
	private SaafQuestionnaireLDAO_HI_RO saafQuestionnaireLDAO_HI_RO;
	@Autowired
	private SaafQuestionnaireLDAO_HI saafQuestionnaireLDAO_HI;
	@Autowired
	private SaafTestQuestionnaireHDAO_HI saafTestQuestionnaireHDAO_HI;

	@Autowired
	private SaafTestQuestionnaireLDAO_HI saafTestQuestionnaireLDAO_HI;
	@Autowired
	private SaafTestQuestionnaireChoiceDAO_HI saafTestQuestionnaireChoiceDAO_HI;

	@Autowired
	private SaafQuestionnaireChoiceDAO_HI saafQuestionnaireChoiceDAO_HI;

	@Autowired
	private SaafQuestionnaireChoiceDAO_HI_RO saafQuestionnaireChoiceDAO_HI_RO;

	@Autowired
	private SaafQuestionnaireResultDAO_HI saafQuestionnaireResultDAO_HI;
	@Autowired
	private SaafQuestionnairePublishDAO_HI saafQuestionnairePublishDAO_HI;
	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private TtaSubjectChoiceSecMidDAO_HI ttaSubjectChoiceSecMidDAO_HI;

	@Autowired
	private TtaSubjectChoiceSecMidDAO_HI_RO ttaSubjectChoiceSecMidDAOHiRo;

	@Autowired
	private RuleHDAO_HI ruleHDAO_HI;

	@Autowired
	private RuleLDAO_HI_RO ruleLDAO_HI_RO;

	@Autowired
	private SubjectDAO_HI_RO subjectDAO_HI_RO;


	@Autowired
	private SaafTestQuestionnaireLDAO_HI_RO saafTestQuestionnaireLDAO_HI_RO;
	
	@Autowired
	private RuleLDAO_HI ruleLDAO_HI;

	


	public SaafQuestionnaireServer() {
		super();
	}

	

	/**
	 *  更新提交的题目信息
	 * @param
	 * @return
	 */
	@Override
	public JSONObject updateSubmitQuestionnaire(JSONObject parameters) {
		List<SaafQuestionnaireLEntity_HI_RO> lineList = null;
        //1.通过题目组合选项查询相关题目信息
        JSONArray jsonArray = parameters.getJSONArray("SurveyResultData");
    	for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			Integer testQnLineId = json.getInteger("testQnLineId");//题目id
			//Integer originalQnLineId = json.getInteger("originalQnLineId");//原始模板题目id
			Integer testQnChoiceId = json.getInteger("testQnChoiceId"); //选项id
			//Integer originalQnChoiceId = json.getInteger("originalQnChoiceId");//原始模板选项Id
			String qnChoiceResult = json.getString("qnChoiceResult"); //用户选项值
			JSONObject queryMap = new JSONObject();
			queryMap.fluentPut("testQnLineId", testQnLineId).fluentPut("testQnChoiceId", testQnChoiceId);
			List<SaafTestQuestionnaireChoiceEntity_HI> saafTestQustList = saafTestQuestionnaireChoiceDAO_HI.findByProperty(queryMap);
			for (SaafTestQuestionnaireChoiceEntity_HI entity : saafTestQustList) {
				entity.setQnChoiceResult(qnChoiceResult);
			}
			//1.将用户答案更新
			saafTestQuestionnaireChoiceDAO_HI.save(saafTestQustList);
			
			//2.根据规则，匹配到规则题目信息
			/*List<RuleLEntity_HI_RO> ruleList = ruleLDAO_HI_RO.findList(RuleLEntity_HI_RO.QUERY_NEED_LOAD_PROJECT);
			if (ruleList != null) {
				for (RuleLEntity_HI_RO roEntity : ruleList) {
					Integer qnHeadId = roEntity.getQnHeadId();
				}
			}*/
			

		}
        
		return SToolUtils.convertResultJSONObj("S", "", 0, lineList);
    }
	
	
	/**
	 * 功能描述： 动态加载测试问卷
	 * @author xiaoga
	 * @date 2019/6/17
	 * @param  
	 * @return 
	 */
	@Override
	public JSONObject findLoadRuleTestQuestionnaire(JSONObject parameters) {
		List<SaafQuestionnaireLEntity_HI_RO> lineList = null;
        //1.通过题目组合选项查询相关题目信息
        JSONArray jsonArray = parameters.getJSONArray("SurveyResultData");
        //2.获取规则头信息
		List<RuleHEntity_HI> entityList = ruleHDAO_HI.findByProperty(new JSONObject());
		if (entityList == null || entityList.isEmpty()) {
			return SToolUtils.convertResultJSONObj("S", "没有题目信息", 0, null);
		}
		ArrayList<Integer> ruleIds = new ArrayList<>();
		for (RuleHEntity_HI ruleHEntityHi : entityList) {
			Integer ruleId = ruleHEntityHi.getRuleId();
			//获取规则行信息
			List<RuleLEntity_HI> ruleEntityLineList = ruleLDAO_HI.findByProperty(new JSONObject().fluentPut("qnHeadId", ruleId));
			if (ruleEntityLineList == null || ruleEntityLineList.isEmpty() ) {
				return SToolUtils.convertResultJSONObj("S", "没有规则信息", 0, null);
			}
			//待匹配的题目列表
			for (int j = 0; j < ruleEntityLineList.size(); j++ ) {
				Integer result = 0;
				RuleLEntity_HI ruleLEntity = ruleEntityLineList.get(j);
				// 获取选项信息
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					// Integer testQnLineId = json.getInteger("testQnLineId");//题目id
					Integer originalQnLineId = json.getInteger("originalQnLineId");//原始模板题目id
					// Integer testQnChoiceId = json.getInteger("testQnChoiceId"); //选项id
					//  originalQnChoiceId = json.getInteger("originalQnChoiceId");//原始模板选项Id
					String qnChoiceResult = json.getString("qnChoiceResult"); //用户选项值
					//循环判断原始题目与选项值能匹配则输出相应题目
					if (!ruleLEntity.getQnHeadId().equals(originalQnLineId) && ruleLEntity.getOptionValus().equalsIgnoreCase(qnChoiceResult)) {
						break;
					}
					result ++;
				}
				if (result == j) {
					ruleIds.add(ruleId);
				}
			}
			// 通过规则ID查询题目列表
			List<SubjectEntity_HI_RO> subjectList = subjectDAO_HI_RO.findList(SubjectEntity_HI_RO.QUERY_SUBJECT_BY_RULE, new JSONObject().fluentPut("ruleIds", ruleIds));
            List<Integer> subjectIds = new ArrayList<Integer>();
			for (SubjectEntity_HI_RO  entity_hi_ro : subjectList) {
				subjectIds.add(entity_hi_ro.getQnHeadId());
			}
			//查询题目列表
			lineList = saafQuestionnaireLDAO_HI_RO.findList(SaafQuestionnaireLEntity_HI_RO.QUERY_LINE_SQL + " and a.qn_line_id in (:subjectIds) order by a.serial_number ",
					new JSONObject().fluentPut("subjectIds", subjectIds));
			if (lineList == null || lineList.isEmpty()) {
				return SToolUtils.convertResultJSONObj("S", "没有题目信息", 0, null);
			}

			for (int i = 0; i < lineList.size(); i++) {
				StringBuffer sbSql = new StringBuffer();
				SaafQuestionnaireLEntity_HI_RO lineRow = lineList.get(i);
				sbSql.append(SaafQuestionnaireChoiceEntity_HI_RO.QUERY_CHOICE).append(" and  a.qn_line_id = " +  lineRow.getQnLineId()).append(" order by a.qn_choice asc ");
				List<SaafQuestionnaireChoiceEntity_HI_RO> choiceList = saafQuestionnaireChoiceDAO_HI_RO.findList(sbSql.toString(), new JSONObject());
				lineRow.setQnChoiceData(choiceList);
			}
		}
		return SToolUtils.convertResultJSONObj("S", "", 0, lineList);
    }

	/**
	 * 功能描述： 初始化试卷信息
	 * @author xiaoga
	 * @date 2019/6/14
	 * @param  
	 * @return 
	 */
	@Override
	public JSONObject saveTestQuestionnaireToCopy(JSONObject jsonObject) {
		//1.把模板的数据实例化
		Integer proposalId = jsonObject.getInteger("proposalId");
		if (proposalId == null) {
			return SToolUtils.convertResultJSONObj("E","proposalId 不能为空!", 0, "");
		}
		saafQuestionnaireLDAO_HI.executeSql("delete from tta_test_questionnaire_line  where proposal_id =" + proposalId);
		saafTestQuestionnaireChoiceDAO_HI.executeSql("delete from tta_test_questionnaire_choice  where proposal_id =" + proposalId);
		//2.查询模板题目信息
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("projectCategory", "1"); //一级题目
		List<SaafQuestionnaireLEntity_HI> questionList = saafQuestionnaireLDAO_HI.findByProperty(queryMap);
		if (questionList != null) {
			for (SaafQuestionnaireLEntity_HI qustEntity : questionList) {
				SaafTestQuestionnaireLEntity_HI  testqQust = JSON.parseObject(jsonObject.toJSONString(),SaafTestQuestionnaireLEntity_HI.class);
				testqQust.setProposalId(proposalId);
				testqQust.setOriginalQnLineId(qustEntity.getQnLineId());
				testqQust.setCalcRule(qustEntity.getCalcRule());
				testqQust.setSerialNumber(qustEntity.getSerialNumber());
				testqQust.setProjectTitle(qustEntity.getProjectTitle());
				testqQust.setProjectTitleAlt(qustEntity.getProjectTitleAlt());
				testqQust.setRequireFlag(qustEntity.getRequireFlag());
				testqQust.setDisplayFlag(qustEntity.getDisplayFlag());
				testqQust.setDescription(qustEntity.getDescription());
				testqQust.setProjectType(qustEntity.getProjectType());
				testqQust.setProjectCategory(qustEntity.getProjectCategory());
				saafTestQuestionnaireLDAO_HI.saveOrUpdate(testqQust);
				Integer testQnLineId = testqQust.getQnLineId();
				//3.添加相关问题选项/文本
				List<SaafQuestionnaireChoiceEntity_HI> choiceList = saafQuestionnaireChoiceDAO_HI.findByProperty(new JSONObject().fluentPut("qnLineId", qustEntity.getQnLineId()));
				if (choiceList != null) {
					List<SaafTestQuestionnaireChoiceEntity_HI> testChoiceList = new ArrayList<>();
					for (SaafQuestionnaireChoiceEntity_HI choiceEntity : choiceList) {
						SaafTestQuestionnaireChoiceEntity_HI testChoiceEntity = JSON.parseObject(jsonObject.toJSONString(), SaafTestQuestionnaireChoiceEntity_HI.class);
						testChoiceEntity.setQnLineId(testQnLineId);
						testChoiceEntity.setQnChoice(choiceEntity.getQnChoice());
						testChoiceEntity.setQnChoiceContent(choiceEntity.getQnChoiceContent());
						testChoiceEntity.setQnAnswer(choiceEntity.getQnAnswer());
						testChoiceEntity.setDisplayFlag(choiceEntity.getDisplayFlag());
						testChoiceEntity.setDescription(choiceEntity.getDescription());
						testChoiceEntity.setProposalId(proposalId);
						testChoiceEntity.setOriginalQnChoiceId(choiceEntity.getQnChoiceId());
						testChoiceList.add(testChoiceEntity);
					}
					saafTestQuestionnaireChoiceDAO_HI.save(testChoiceList);
				}
			}
		}
		return SToolUtils.convertResultJSONObj("S","模板初始化成功!", 0, null);
	}

	
	/**
	 * 根据用户id查找供应商数据
	 * @param 
	 * @author 
     * @creteTime 2019/5/13
     * @return SaafQuestionnaireLEntity_HI
     */
    @Override
    public Pagination<SaafQuestionnaireLEntity_HI> findSaafQuestionnaire(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {	
    	Map<String, Object> map = new HashMap<String, Object>();
    	StringBuffer sql = new StringBuffer(" from SaafQuestionnaireLEntity_HI");        
        Pagination<SaafQuestionnaireLEntity_HI> findList = saafQuestionnaireLDAO_HI.findPagination(sql,map,pageIndex,pageRows);
        return findList;
    }
    
    @Override
    public Pagination<SaafQuestionnaireLEntity_HI> findSaafQuestionnaires(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {	
    	Map<String, Object> map = new HashMap<String, Object>();
    	StringBuffer sql = new StringBuffer(" from SaafQuestionnaireLEntity_HI");        
        Pagination<SaafQuestionnaireLEntity_HI> findList = saafQuestionnaireLDAO_HI.findPagination(sql,map,pageIndex,pageRows);
        return findList;
    }

	/**
	 * 查询问卷调查列表
	 */
	@Override
	public Pagination<SaafQuestionnaireHEntity_HI_RO> findSaafQuestionnaireList(JSONObject parameters, Integer pageIndex, Integer pageRows){
		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqh.qn_title", "qnTitle", hql, map, "like");
		SaafToolUtils.parperParam(parameters, "sqh.qn_code", "qnCode", hql, map, "like");
		// SaafToolUtils.parperParam(parameters, "vqsh.qn_status",
		// "qnStatus",hql, map, "=");
		SaafToolUtils.parperParam(parameters, "sqh.status", "status", hql, map, "=");
		hql.append(" order by sqh.LAST_UPDATE_DATE desc ");
		Pagination<SaafQuestionnaireHEntity_HI_RO> rowSet = saafQuestionnaireHDAO_HI_RO.findPagination(hql, map,
				pageIndex, pageRows);
		return rowSet;
	}

	/**
	 * 查询问卷调查行信息
	 */
	@Override
	public JSONObject findSaafQuestionnaireById(JSONObject parameters){
		JSONObject json = new JSONObject();
		//List<SaafQuestionnaireLEntity_HI> lineList = saafQuestionnaireLDAO_HI.findList(" from SaafQuestionnaireLEntity_HI order by serialNumber", new HashMap<String, Object>());
		List<SaafQuestionnaireLEntity_HI_RO> lineList = saafQuestionnaireLDAO_HI_RO.findList(SaafQuestionnaireLEntity_HI_RO.QUERY_LINE_SQL + " order by a.serial_number ", new HashMap<String, Object>());
		if (lineList.size() > 0) {
		    StringBuffer sbSql = null;
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lineList.size(); i++) {
				sbSql = new StringBuffer();
				JSONObject lineJson = new JSONObject();
				SaafQuestionnaireLEntity_HI_RO lineRow = lineList.get(i);
				lineJson.put("qnLineId", lineRow.getQnLineId());
				lineJson.put("serialNumber", lineRow.getSerialNumber());
				lineJson.put("projectType", lineRow.getProjectType());
				lineJson.put("projectTitle", lineRow.getProjectTitle());
				lineJson.put("projectTitleAlt", lineRow.getProjectTitleAlt());
				lineJson.put("requireFlag", lineRow.getRequireFlag());
				lineJson.put("displayFlag", lineRow.getDisplayFlag());
				lineJson.put("description", lineRow.getDescription());
				lineJson.put("calcRuleName", lineRow.getCalcRuleName());
                lineJson.put("projectCategory", lineRow.getProjectCategory());
                sbSql.append(SaafQuestionnaireChoiceEntity_HI_RO.QUERY_CHOICE)
                        .append(" and  a.qn_line_id = " +  lineRow.getQnLineId())
                        .append(" order by a.qn_choice asc ");
				List<SaafQuestionnaireChoiceEntity_HI_RO> choiceList = saafQuestionnaireChoiceDAO_HI_RO.findList(sbSql.toString(), new JSONObject());
				lineJson.put("qnChoiceData", choiceList);
				jsonArray.add(lineJson);
			}
			json.put("lineList", jsonArray);
		} else {
			json.put("lineList", lineList);
		}
		return SToolUtils.convertResultJSONObj("S", "", 0, json);
	}

	/**
	 * 保存问卷调查头信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject saveSaafQuestionnaireH(JSONObject parameters){
		int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));

			if (null == parameters.get("qnTitle") || "".equals(parameters.getString("qnTitle").trim())) {
				return SToolUtils.convertResultJSONObj("F", "问卷标题不能为空!", 0, "");
			}
//			if (null == parameters.get("qnPlatform") || "".equals(parameters.getString("qnPlatform").trim())) {
//				return SToolUtils.convertResultJSONObj("F", "应用平台不能为空!", 0, "");
//			}
			if (null == parameters.get("qnType") || "".equals(parameters.getString("qnType").trim())) {
				parameters.put("qnType", "EXTERNAL_USER");//对外
				//return SToolUtils.convertResultJSONObj("F", "问卷类型不能为空!", 0, "");
			}
//			if (null == parameters.get("startDateActive")
//					|| "".equals(parameters.getString("startDateActive").trim())) {
//				return SToolUtils.convertResultJSONObj("F", "开始日期不能为空!", 0, "");
//			}


			SaafQuestionnaireHEntity_HI row = null;
			if (null == parameters.get("qnHeadId")
					|| "".equals(SToolUtils.object2String(parameters.get("qnHeadId")).trim())) {
				// 判断是否新增
				row = new SaafQuestionnaireHEntity_HI();
				// 新增，创建问卷调查编号
				//generateCodeServer.generateCode("SAAF_QUESTIONNAIRE_H")
				String qnCode = generateCodeServer.getGenerateCode("WJ", 32);
				row.setQnCode(qnCode);
				row.setStatus("NEW");
			} else {
				row = saafQuestionnaireHDAO_HI
						.findByProperty("qnHeadId", SToolUtils.object2Int(parameters.get("qnHeadId"))).get(0);
				// 判断审批状态
				if ("APPROVED".equals(row.getStatus()) || "APPROVING".equals(row.getStatus())) {
					return SToolUtils.convertResultJSONObj("F", "当前状态不允许修改!", 0, "");
				}
				row.setStatus(parameters.getString("status"));
			}
			row.setQnTitle(parameters.getString("qnTitle"));
			row.setQnType(parameters.getString("qnType"));
			row.setQnPlatform(parameters.getString("qnPlatform"));
			row.setQnStatus(parameters.getString("qnStatus"));
			row.setOrgId(parameters.getInteger("orgId"));
			row.setBreakPointAnswer(parameters.getString("breakPointAnswer"));
			row.setInvestigateObject(parameters.getString("investigateObject"));
			row.setStartDateActive(parameters.getDate("startDateActive"));
			row.setEndDateActive(parameters.getDate("endDateActive"));
			row.setBgColor(parameters.getString("bgColor"));
			row.setBgImagePath(parameters.getString("bgImagePath"));
			row.setDescription(SToolUtils.object2String(parameters.get("description")));
			row.setOperatorUserId(varUserId);
			saafQuestionnaireHDAO_HI.saveOrUpdate(row);
			return SToolUtils.convertResultJSONObj("S", "保存成功!", 1, row);

	}

	/**
	 * 删除问卷调查头信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteSaafQuestionnaireH(JSONObject parameters){
		if (null == parameters.get("qnHeadId") || "".equals(parameters.getString("qnHeadId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnaireHEntity_HI> list = saafQuestionnaireHDAO_HI.findByProperty("qnHeadId",
					SToolUtils.object2Int(parameters.get("qnHeadId")));
			if (list.size() > 0) {
				// 判断状态
				SaafQuestionnaireHEntity_HI row = list.get(0);
				if ("APPROVED".equals(row.getStatus()) || "APPROVING".equals(row.getStatus())) {
					return SToolUtils.convertResultJSONObj("F", "当前状态不允许删除!", 0, "");
				}
//				// add on 2018-07-19
//				if (row.getProcessInstanceId() != null && !"".equals(row.getProcessInstanceId().trim())) {
//					flowUtilsServer.deleteProcess(row.getProcessInstanceId());
//				}
				saafQuestionnaireHDAO_HI.delete(row);
				return SToolUtils.convertResultJSONObj("S", "删除成功!", 0, "");

			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到需要删除的信息!", 0, "");
			}
	}

	/**
	 * 复制问卷调查信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateSaafQuestionnaireToCopy(JSONObject parameters){
		int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));

			if (null == parameters.get("qnHeadId") || "".equals(parameters.getString("qnHeadId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnaireHEntity_HI> headList = saafQuestionnaireHDAO_HI.findByProperty("qnHeadId",
					SToolUtils.object2Int(parameters.get("qnHeadId")));
			if (headList.size() > 0) {
				SaafQuestionnaireHEntity_HI head = headList.get(0);
				// 复制头表
				SaafQuestionnaireHEntity_HI row = new SaafQuestionnaireHEntity_HI();
				// 创建问卷调查编号
				//generateCodeServer.generateCode("SAAF_QUESTIONNAIRE_H")
				String qnCode = generateCodeServer.getGenerateCode("WJ", 32);
				row.setQnCode(qnCode);
				row.setStatus("NEW");
				// row.setQnStatus("draft");
				row.setQnPlatform(head.getQnPlatform());
				row.setQnTitle(head.getQnTitle());
				row.setQnType(head.getQnType());
				row.setOrgId(head.getOrgId());
				row.setBreakPointAnswer(head.getBreakPointAnswer());
				row.setInvestigateObject(head.getInvestigateObject());
				row.setStartDateActive(head.getStartDateActive());
				row.setEndDateActive(head.getEndDateActive());
				row.setBgColor(head.getBgColor());
				row.setBgImagePath(head.getBgImagePath());
				row.setDescription(head.getDescription());
				row.setOperatorUserId(varUserId);
				saafQuestionnaireHDAO_HI.saveOrUpdate(row);

				List<SaafQuestionnaireLEntity_HI> lineList = saafQuestionnaireLDAO_HI.findByProperty("qnHeadId",
						SToolUtils.object2Int(parameters.get("qnHeadId")));
				if (lineList.size() > 0) {
					// 复制行表
					for (int i = 0; i < lineList.size(); i++) {
						SaafQuestionnaireLEntity_HI line = lineList.get(i);
						SaafQuestionnaireLEntity_HI lineRow = new SaafQuestionnaireLEntity_HI();
						lineRow.setSerialNumber(line.getSerialNumber());
						lineRow.setProjectType(line.getProjectType());
						lineRow.setProjectTitle(line.getProjectTitle());
						lineRow.setProjectTitleAlt(line.getProjectTitleAlt());
						lineRow.setRequireFlag(line.getRequireFlag());
						lineRow.setDisplayFlag(line.getDisplayFlag());
						lineRow.setDescription(line.getDescription());
						lineRow.setOperatorUserId(varUserId);
						saafQuestionnaireLDAO_HI.saveOrUpdate(lineRow);
						// 复制选项表(通过行id，查询出选项表信息)
						List<SaafQuestionnaireChoiceEntity_HI> choiceList = saafQuestionnaireChoiceDAO_HI
								.findByProperty("qnLineId", line.getQnLineId());
						for (int j = 0; j < choiceList.size(); j++) {
							SaafQuestionnaireChoiceEntity_HI choice = choiceList.get(j);
							SaafQuestionnaireChoiceEntity_HI choiceRow = new SaafQuestionnaireChoiceEntity_HI();
							choiceRow.setQnLineId(lineRow.getQnLineId());
							choiceRow.setQnChoice(choice.getQnChoice());
							choiceRow.setQnChoiceContent(choice.getQnChoiceContent());
							choiceRow.setQnAnswer(choice.getQnAnswer());
							choiceRow.setDisplayFlag(choice.getDisplayFlag());
							choiceRow.setDescription(choice.getDescription());
							choiceRow.setOperatorUserId(varUserId);
							saafQuestionnaireChoiceDAO_HI.saveOrUpdate(choiceRow);
						}

					}
				}
				return SToolUtils.convertResultJSONObj("S", "复制成功!", 0, "");
			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到需要复制的信息!", 0, "");
			}
	}

	/**
	 * 保存问卷调查行信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject saveSaafQuestionnaireL(JSONObject parameters){
			int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));
			if (null == parameters.get("serialNumber") || "".equals(parameters.getString("serialNumber").trim())) {
				return SToolUtils.convertResultJSONObj("F", "题目序号不能为空!", 0, "");
			}
			JSONArray jsonArray = parameters.getJSONArray("qnChoiceData");
			if (jsonArray.size() <= 0) {
				return SToolUtils.convertResultJSONObj("F", "题目选项不能为空!", 0, "");
			}

			if (StringUtils.isBlank(parameters.getString("calcRule"))  && "rule_model".equalsIgnoreCase(parameters.getString("projectType"))) {
				return SToolUtils.convertResultJSONObj("F", "自动计算模式，规则不能为空!", 0, "");
			}

			// 判断是否有行id，没有则是新增
			SaafQuestionnaireLEntity_HI lineRow = null;
			if (null == parameters.get("qnLineId") || "".equals(parameters.getString("qnLineId").trim())) {
				lineRow = new SaafQuestionnaireLEntity_HI();
			} else {
				List<SaafQuestionnaireLEntity_HI> lineList = saafQuestionnaireLDAO_HI.findByProperty("qnLineId",
						SToolUtils.object2Int(parameters.get("qnLineId")));
				if (lineList.size() > 0) {
					lineRow = lineList.get(0);
				} else {
					return SToolUtils.convertResultJSONObj("F", "未找到该条记录信息，请联系管理员!", 0, "");
				}
			}

			lineRow.setSerialNumber(parameters.getInteger("serialNumber"));
			lineRow.setProjectTitle(parameters.getString("projectTitle"));
			lineRow.setProjectTitleAlt(parameters.getString("projectTitleAlt"));
			lineRow.setProjectType(parameters.getString("projectType"));
			lineRow.setRequireFlag(parameters.getString("requireFlag"));
			lineRow.setDisplayFlag(parameters.getString("displayFlag"));
			lineRow.setDescription(parameters.getString("description"));
			lineRow.setOperatorUserId(varUserId);
            lineRow.setProjectCategory(parameters.getString("projectCategory"));
            if ("rule_model".equalsIgnoreCase(parameters.getString("projectType"))) {
				lineRow.setCalcRule(parameters.getString("calcRule"));
			} else {
				lineRow.setCalcRule(null);
			}
			saafQuestionnaireLDAO_HI.saveOrUpdate(lineRow);
			// 获取选项信息
			for (int i = 0; i < jsonArray.size(); i++) {
				// 判断是否有选项id，没有则是新增
				JSONObject json = jsonArray.getJSONObject(i);
				SaafQuestionnaireChoiceEntity_HI choiceRow = null;
				if (null == json.get("qnChoiceId") || "".equals(json.getString("qnChoiceId").trim())) {
					choiceRow = new SaafQuestionnaireChoiceEntity_HI();
				} else {
					List<SaafQuestionnaireChoiceEntity_HI> choiceList = saafQuestionnaireChoiceDAO_HI
							.findByProperty("qnChoiceId", SToolUtils.object2Int(json.get("qnChoiceId")));
					if (choiceList.size() > 0) {
						choiceRow = choiceList.get(0);
					} else {
						return SToolUtils.convertResultJSONObj("F", "未找到选项信息，请联系管理员!", 0, "");
					}
				}
				choiceRow.setQnLineId(lineRow.getQnLineId());
				choiceRow.setQnChoice(json.getString("qnChoice"));
				choiceRow.setQnChoiceContent(json.getString("qnChoiceContent"));
				choiceRow.setQnAnswer(json.getString("qnAnswer"));
				choiceRow.setDisplayFlag(json.getString("displayFlag"));
				choiceRow.setDescription(json.getString("description"));
				choiceRow.setSelectQnLineId(json.getString("selectQnLineId"));
				choiceRow.setOperatorUserId(varUserId);
				choiceRow.setQnChoiceContentAlt(json.getString("qnChoiceContentAlt"));//英文名称
				saafQuestionnaireChoiceDAO_HI.saveOrUpdate(choiceRow);
				
				///////////start////
				if (choiceRow.getQnChoiceId() != null) {
					ttaSubjectChoiceSecMidDAO_HI.deleteByChoiceId(choiceRow.getQnChoiceId());
				}
				String childQnLineIds = json.getString("childQnLineIds");
				if (StringUtils.isNotBlank(childQnLineIds)) {
					String[] childQnLineIdArr = childQnLineIds.split(",");
					for (String lineId : childQnLineIdArr) {
						TtaSubjectChoiceSecMidEntity_HI entity = JSON.parseObject(parameters.toJSONString(), TtaSubjectChoiceSecMidEntity_HI.class);
						entity.setQnLineId(Integer.parseInt(lineId));//题目id
						entity.setQnChoiceId(choiceRow.getQnChoiceId());
						ttaSubjectChoiceSecMidDAO_HI.save(entity);
					}
				}
			}
			if ("rule_model".equalsIgnoreCase(parameters.getString("projectType"))) {
				List<SaafQuestionnaireChoiceEntity_HI> choiceEntityList = saafQuestionnaireChoiceDAO_HI.findByProperty(new JSONObject().fluentPut("qnLineId", lineRow.getQnLineId()));
				if (choiceEntityList != null && !choiceEntityList.isEmpty()) {
					saafQuestionnaireChoiceDAO_HI.delete(choiceEntityList);
				}
				SaafQuestionnaireChoiceEntity_HI choiceRow = new SaafQuestionnaireChoiceEntity_HI();
				choiceRow.setQnLineId(lineRow.getQnLineId());
				choiceRow.setDisplayFlag("Y");
				choiceRow.setQnChoice("A");
				choiceRow.setHasSecLine("0");
				choiceRow.setOperatorUserId(varUserId);
				saafQuestionnaireChoiceDAO_HI.save(choiceRow);
			}
			///////////end////
			return SToolUtils.convertResultJSONObj("S", "保存成功!", 0, "");

	}

	/**
	 * 删除问卷调查信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteSaafQuestionnaireL(JSONObject parameters){
		if (null == parameters.get("qnLineId") || "".equals(parameters.getString("qnLineId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnaireLEntity_HI> lineList = saafQuestionnaireLDAO_HI.findByProperty("qnLineId",
					SToolUtils.object2Int(parameters.get("qnLineId")));
			if (lineList.size() > 0) {
				// 判断状态
				SaafQuestionnaireLEntity_HI lineRow = lineList.get(0);
				saafQuestionnaireLDAO_HI.delete(lineRow);
				// 删除选项信息
				List<SaafQuestionnaireChoiceEntity_HI> choiceList = saafQuestionnaireChoiceDAO_HI
						.findByProperty("qnLineId", SToolUtils.object2Int(parameters.get("qnLineId")));
				saafQuestionnaireChoiceDAO_HI.deleteAll(choiceList);
				return SToolUtils.convertResultJSONObj("S", "删除成功!", 0, "");

			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到需要删除的信息!", 0, "");
			}
	}

	/**
	 * 删除问卷调查选项信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteQnChoice(JSONObject parameters){
		if (null == parameters.get("qnChoiceId") || "".equals(parameters.getString("qnChoiceId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnaireChoiceEntity_HI> list = saafQuestionnaireChoiceDAO_HI.findByProperty("qnChoiceId",
					SToolUtils.object2Int(parameters.get("qnChoiceId")));
			if (list.size() > 0) {
				SaafQuestionnaireChoiceEntity_HI row = list.get(0);
				saafQuestionnaireChoiceDAO_HI.delete(row);
				return SToolUtils.convertResultJSONObj("S", "删除成功!", 0, "");

			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到需要删除的信息!", 0, "");
			}
	}

	/**
	 * 查询题目列表
	 *
	 * @param parameters
	 * @return
	 */
	@Override
	public JSONObject findSelectProjectList(JSONObject parameters){
		if (null == parameters.get("qnLineId") || "".equals(parameters.getString("qnLineId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnaireLEntity_HI> list = saafQuestionnaireLDAO_HI.findByProperty("qnLineId",
					SToolUtils.object2Int(parameters.get("qnLineId")));
			if (list.size() > 0) {
				SaafQuestionnaireLEntity_HI line = list.get(0);
				Integer serialNumber = line.getSerialNumber();
				StringBuffer hql = new StringBuffer();
				hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_PROJECT_SQL);
				hql.append(" and hsql.SERIAL_NUMBER>:serialNumber ");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("serialNumber", serialNumber);
				hql.append(" order by hsql.SERIAL_NUMBER asc ");
				List<SaafQuestionnaireHEntity_HI_RO> rowList = saafQuestionnaireHDAO_HI_RO.findList(hql.toString(), map);
				return SToolUtils.convertResultJSONObj("S", "查询成功!", rowList.size(), rowList);
			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到该条记录信息!", 0, "");
			}
	}

	/**
	 * 保存问卷调查答卷信息 SaafQuestionnaireResultEntity_HI
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
    public JSONObject saveSaafQuestionnaireResult(JSONObject parameters) {
        int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));
        // 通过头id查询出当前答卷份数
        StringBuffer hql = new StringBuffer();
        hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_MAX_RESULT_NUM);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperParam(parameters, "sqr.proposal_id", "proposalId", hql, map, "=");
        List<SaafQuestionnaireHEntity_HI_RO> rowList = saafQuestionnaireHDAO_HI_RO.findList(hql, map);
		Integer maxResultNum = 0;
        if (rowList != null && !rowList.isEmpty()) {
			maxResultNum = rowList.get(0).getMaxResultNum();
		}
        JSONArray jsonArray = parameters.getJSONArray("SurveyResultData");
        // 获取选项信息
        for (int i = 0; i < jsonArray.size(); i++) {
            // 判断是否有选项id，没有则是新增
            JSONObject json = jsonArray.getJSONObject(i);
            SaafQuestionnaireResultEntity_HI row = new SaafQuestionnaireResultEntity_HI();
            //row.setProposalId(null);//待修改
            row.setQnLineId(json.getInteger("qnLineId"));
            row.setQnChoiceId(json.getString("qnChoiceId"));
            row.setQnChoiceResult(json.getString("qnChoiceResult"));
            row.setResultNum(maxResultNum + 1);
            row.setOperatorUserId(varUserId);
            saafQuestionnaireResultDAO_HI.saveOrUpdate(row);
        }
        return SToolUtils.convertResultJSONObj("S", "保存成功!", 0, "");
    }

	/**
	 * 保存问卷调查发布信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject saveSaafQuestionnairePublish(JSONObject parameters){
		int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));
			if (null == parameters.get("startDateActive")
					|| "".equals(parameters.getString("startDateActive").trim())) {
				return SToolUtils.convertResultJSONObj("F", "开始日期不能为空!", 0, "");
			}
			if (null == parameters.get("testQnHeadId") || "".equals(parameters.getString("testQnHeadId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "问卷头Id不能为空!", 0, "");
			}

			SaafQuestionnairePublishEntity_HI row = null;
			if (null == parameters.get("publishId") || "".equals(SToolUtils.object2String(parameters.get("publishId")).trim())) {
				// 判断是否新增
				row = new SaafQuestionnairePublishEntity_HI();
				row.setStatus("draft");
				row.setFlowStatus("NEW");
				row.setTestQnHeadId(parameters.getInteger("testQnHeadId"));
				// 创建发布问卷编号
				//generateCodeServer.generateCode("SAAF_QUESTIONNAIRE_PUBLISH")
				String qnCode = generateCodeServer.getGenerateCode("WJ", 32);
				row.setPublishCode(qnCode);
			} else {
				row = saafQuestionnairePublishDAO_HI.findByProperty("publishId", SToolUtils.object2Int(parameters.get("publishId"))).get(0);
				// 判断状态
				if ("publish".equals(row.getStatus())) {
					return SToolUtils.convertResultJSONObj("F", "已发布不允许修改!", 0, "");
				}
				if("APPROVING".equals(row.getFlowStatus()) || "APPROVED".equals(row.getFlowStatus())){
					return SToolUtils.convertResultJSONObj("F", "审批中，已审批不允许修改!", 0, "");
				}
				row.setStatus(parameters.getString("status"));
			}
			row.setOrgsId(parameters.getString("orgsId"));
			row.setUsersId(parameters.getString("usersId"));
			row.setQnType(parameters.getString("qnType"));
			row.setStartDateActive(parameters.getDate("startDateActive"));
			row.setEndDateActive(parameters.getDate("endDateActive"));
			row.setDescription(SToolUtils.object2String(parameters.get("description")));
			row.setOperatorUserId(varUserId);
			saafQuestionnairePublishDAO_HI.saveOrUpdate(row);
			return SToolUtils.convertResultJSONObj("S", "保存成功!", 1, row);

	}

	/**
	 * 废弃问卷调查发布信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateSaafQuestionnairePublishToAbandon(JSONObject parameters){
		int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));

			if (null == parameters.get("publishId") || "".equals(parameters.getString("publishId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnairePublishEntity_HI> list = saafQuestionnairePublishDAO_HI.findByProperty("publishId",
					SToolUtils.object2Int(parameters.get("publishId")));
			if (list.size() > 0) {
				// 判断状态
				SaafQuestionnairePublishEntity_HI row = list.get(0);
				/*if("APPROVING".equals(row.getFlowStatus())){
					return SToolUtils.convertResultJSONObj("F", "审批中的问卷不允许废弃!", 0, "");
				}*/
				row.setStatus("abandon");
				row.setOperatorUserId(varUserId);
				saafQuestionnairePublishDAO_HI.saveOrUpdate(row);
				return SToolUtils.convertResultJSONObj("S", "废弃成功!", 0, "");

			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到需要废弃的信息!", 0, "");
			}
	}

	/**
	 * 启用发布问卷调查发布信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateSaafQuestionnairePublishToPublish(JSONObject parameters){
		int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));

			if (null == parameters.get("publishId") || "".equals(parameters.getString("publishId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnairePublishEntity_HI> list = saafQuestionnairePublishDAO_HI.findByProperty("publishId",
					SToolUtils.object2Int(parameters.get("publishId")));
			if (list.size() > 0) {
				// 判断状态
				SaafQuestionnairePublishEntity_HI row = list.get(0);
				if ("publish".equals(row.getStatus())) {
					return SToolUtils.convertResultJSONObj("F", "该问卷已发布无需再次发布!", 0, "");
				}
				if(!"APPROVED".equals(row.getFlowStatus())){
					return SToolUtils.convertResultJSONObj("F", "未审批的问卷不允许发布!", 0, "");
				}
				row.setStatus("publish");
				row.setOperatorUserId(varUserId);
				saafQuestionnairePublishDAO_HI.saveOrUpdate(row);
				return SToolUtils.convertResultJSONObj("S", "发布成功!", 0, "");

			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到需要废弃的信息!", 0, "");
			}
	}

	/**
	 * 删除问卷调查发布信息
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteSaafQuestionnairePublish(JSONObject parameters){

			if (null == parameters.get("publishId") || "".equals(parameters.getString("publishId").trim())) {
				return SToolUtils.convertResultJSONObj("F", "id不能为空!", 0, "");
			}
			List<SaafQuestionnairePublishEntity_HI> list = saafQuestionnairePublishDAO_HI.findByProperty("publishId",
					SToolUtils.object2Int(parameters.get("publishId")));
			if (list.size() > 0) {
				// 判断状态
				SaafQuestionnairePublishEntity_HI row = list.get(0);
				if ("publish".equals(row.getStatus())) {
					return SToolUtils.convertResultJSONObj("F", "已发布的不能删除!", 0, "");
				}
				if("APPROVING".equals(row.getFlowStatus()) || "APPROVED".equals(row.getFlowStatus())){
					return SToolUtils.convertResultJSONObj("F", "审批中，已审批不允许删除!", 0, "");
				}
//				// add on 2018-07-19
//				if (row.getProcessInstanceId() != null && !"".equals(row.getProcessInstanceId().trim())) {
//					flowUtilsServer.deleteProcess(row.getProcessInstanceId());
//				}
				saafQuestionnairePublishDAO_HI.delete(row);
				return SToolUtils.convertResultJSONObj("S", "删除成功!", 0, "");

			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到需要废弃的信息!", 0, "");
			}
	}

	/**
	 * 查询问卷发布列表
	 */
	@Override
	public JSONObject findSaafQuestionnairePublishList(JSONObject parameters, Integer pageIndex, Integer pageRows)
			{
		StringBuffer hql = new StringBuffer();

		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_PUBLISH_INFO);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "vqsh.qn_title", "qnTitle", hql, map, "like");
		SaafToolUtils.parperParam(parameters, "vqsh.qn_code", "qnCode", hql, map, "like");
		SaafToolUtils.parperParam(parameters, "vqsp.status", "status", hql, map, "=");
		SaafToolUtils.parperParam(parameters, "vqsp.flow_status", "flowStatus", hql, map, "=");
		SaafToolUtils.parperParam(parameters, "vqsp.PUBLISH_ID", "publishId", hql, map, "=");
		hql.append(" order by sqp.LAST_UPDATE_DATE desc ");
		Pagination<SaafQuestionnaireHEntity_HI_RO> rowSet = saafQuestionnaireHDAO_HI_RO.findPagination(hql, map,
				pageIndex, pageRows);

		JSONObject json = new JSONObject();
		json.put("count", rowSet.getCount());
		json.put("curIndex", rowSet.getCurIndex());
		json.put("firstIndex", rowSet.getFirstIndex());
		json.put("lastIndex", rowSet.getLastIndex());
		json.put("nextIndex", rowSet.getNextIndex());
		json.put("pageSize", rowSet.getPageSize());
		json.put("pagesCount", rowSet.getPagesCount());
		json.put("preIndex", rowSet.getPreIndex());

		List<SaafQuestionnaireHEntity_HI_RO> list = rowSet.getData();
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SaafQuestionnaireHEntity_HI_RO row = list.get(i);
			JSONObject arrayJson = new JSONObject();
			arrayJson.put("qnHeadId", row.getQnHeadId());
			arrayJson.put("qnCode", row.getQnCode());
			arrayJson.put("publishCode", row.getPublishCode());
			arrayJson.put("qnTitle", row.getQnTitle());
			arrayJson.put("publishId", row.getPublishId());
			arrayJson.put("orgsId", row.getOrgsId());
			arrayJson.put("startDateActive", row.getStartDateActive().toString());
			if (null == (row.getEndDateActive())) {
				arrayJson.put("endDateActive", "");
			} else {
				arrayJson.put("endDateActive", row.getEndDateActive().toString());
			}
			arrayJson.put("usersId", row.getUsersId());
			arrayJson.put("qnType", row.getQnType());
			arrayJson.put("status", row.getStatus());
			arrayJson.put("description", row.getDescription());
			arrayJson.put("statusName", row.getStatusName());
			arrayJson.put("userFullName", row.getUserFullName());
			arrayJson.put("qnTypeName", row.getQnTypeName());
			arrayJson.put("bgColor ", row.getQnTypeName());
			arrayJson.put("bgImagePath", row.getQnTypeName());
//
//			arrayJson.put("flowStatus", row.getFlowStatus());
//			arrayJson.put("flowStatusName", row.getFlowStatusName());
//			arrayJson.put("processInstanceId", row.getProcessInstanceId());

			if (null == row.getOrgsId() || "".equals(row.getOrgsId())) {
				arrayJson.put("cityList", "");
			}
			// else{
			// StringBuffer cityHql = new StringBuffer();
			// cityHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_CITYS_NAME);
			// cityHql.append(" and si.inst_id in ( " + row.getOrgsId() + "
			// )");
			// Map<String, Object> cityMap = new HashMap<String, Object>();
			// List<SaafQuestionnaireHEntity_HI_RO> cityList =
			// saafQuestionnaireHDAO_HI_RO.findList(cityHql, cityMap);
			// arrayJson.put("cityList", cityList);
			// }

			if (null == row.getUsersId() || "".equals(row.getUsersId())) {
				arrayJson.put("userList", "");
			} else {
				StringBuffer userHql = new StringBuffer();
				userHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_USERS_NAME);
				userHql.append(" and su.user_id in ( " + row.getUsersId() + " )");
				Map<String, Object> userMap = new HashMap<String, Object>();
				List<SaafQuestionnaireHEntity_HI_RO> userList = saafQuestionnaireHDAO_HI_RO.findList(userHql, userMap);
				arrayJson.put("userList", userList);
			}
			array.add(arrayJson);
		}
		json.put("data", array);
		return json;
	}

	/**
	 * 移动端查询问卷调查列表(用户查询，传userId)
	 */
	@Override
	public JSONObject findSaafQuestionnaireListByMobile(JSONObject parameters){
		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_PUBLISH_INFO);
		Map<String, Object> map = new HashMap<String, Object>();
		hql.append(" and sqp.status='publish' ");
		hql.append(" and SYSDATE()>=sqp.START_DATE_ACTIVE ");
		SaafToolUtils.parperParam(parameters, "sqp.qn_title", "qnTitle", hql, map, "like");
		SaafToolUtils.parperParam(parameters, "sqp.qn_code", "qnCode", hql, map, "like");
		hql.append(" order by sqp.START_DATE_ACTIVE desc ");
		List<SaafQuestionnaireHEntity_HI_RO> list = saafQuestionnaireHDAO_HI_RO.findList(hql, map);

		JSONObject json = new JSONObject();
		json.put("status", "S");
		json.put("msg", "");
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SaafQuestionnaireHEntity_HI_RO row = list.get(i);
			// 判断userId是否在usersId里面
			if (null != row.getUsersId() && !"".equals(row.getUsersId())) {
				StringBuffer userHql = new StringBuffer();
				userHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_USER_ID);
				Map<String, Object> userMap = new HashMap<String, Object>();
				userHql.append(" and bu.user_id in ( " + row.getUsersId() + " )");
				userHql.append(" and bu.user_id=:userId ");
				userMap.put("userId", parameters.getInteger("varUserId"));
				List<SaafQuestionnaireHEntity_HI_RO> userList = saafQuestionnaireHDAO_HI_RO.findList(userHql, userMap);
				if (userList.size() > 0) {
					// 判断这个id是否已经答卷
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("createdBy", parameters.getInteger("varUserId"));
					resultMap.put("publishId", row.getPublishId());
					List<SaafQuestionnaireResultEntity_HI> resultList = saafQuestionnaireResultDAO_HI
							.findByProperty(resultMap);

					JSONObject resJson = new JSONObject();
					resJson.put("qnHeadId", row.getQnHeadId());
					resJson.put("qnCode", row.getQnCode());
					resJson.put("qnTitle", row.getQnTitle());
					resJson.put("publishId", row.getPublishId());
					resJson.put("startDateActive", row.getStartDateActive().toString());
					if (null != row.getEndDateActive()) {
						resJson.put("endDateActive", row.getEndDateActive().toString());
					}
					resJson.put("usersId", row.getUsersId());
					resJson.put("status", row.getStatus());
					resJson.put("userFullName", row.getUserFullName());
					resJson.put("qnType", row.getQnType());
					resJson.put("qnTypeName", row.getQnTypeName());

					resJson.put("publishDate", row.getPublishDate().toString().substring(0, 10));
					if (resultList.size() > 0) {
						if ("N".equals(parameters.getString("statusFlag"))) {
							SaafQuestionnaireResultEntity_HI resultRow = resultList.get(0);
							resJson.put("submitStatus", "已提交");
							resJson.put("submitDate", resultRow.getLastUpdateDate().toString().substring(0, 10));
							array.add(resJson);
						}
					} else {
						if ("Y".equals(parameters.getString("statusFlag"))) {
							resJson.put("submitStatus", "待答卷");
							array.add(resJson);
						}
					}
				}
			}

		}
		json.put("data", array);
		json.put("count", array.size());
		return json;
	}

	/**
	 * 通过发布ID查询问卷调查行信息
	 */
	@Override
	public JSONObject findSaafQuestionnaireByPublishId(JSONObject parameters){

		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_PUBLISH_INFO);
		Map<String, Object> publishMap = new HashMap<String, Object>();
		// SaafToolUtils.parperParam(parameters, "vqsh.SURVEY_HEAD_ID",
		// "qnHeadId", hql, publishMap, "=");
		SaafToolUtils.parperParam(parameters, "sqp.PUBLISH_ID", "publishId", hql, publishMap, "=");
		hql.append(" and sqp.status='publish' ");
		hql.append(" and SYSDATE()>=sqp.START_DATE_ACTIVE ");
		hql.append(" and SYSDATE()<=ifnull(sqp.END_DATE_ACTIVE,SYSDATE()+1) ");
		List<SaafQuestionnaireHEntity_HI_RO> headList = saafQuestionnaireHDAO_HI_RO.findList(hql, publishMap);
		if (headList.size() <= 0) {
			return SToolUtils.convertResultJSONObj("F", "问卷已失效，不能答卷！", 0, "");
		}
		JSONObject json = new JSONObject();
		json.put("headList", headList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("testQnHeadId", headList.get(0).getTestQnHeadId());
		List<SaafTestQuestionnaireLEntity_HI> lineList = saafTestQuestionnaireLDAO_HI.findList(
				" from SaafTestQuestionnaireLEntity_HI where testQnHeadId=:testQnHeadId order by serialNumber", map);

		if (lineList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lineList.size(); i++) {
				JSONObject lineJson = new JSONObject();
				SaafTestQuestionnaireLEntity_HI lineRow = lineList.get(i);
				lineJson.put("testQnLineId", lineRow.getQnLineId());
				lineJson.put("serialNumber", lineRow.getSerialNumber());
				lineJson.put("projectType", lineRow.getProjectType());
				lineJson.put("projectTitle", lineRow.getProjectTitle());
				lineJson.put("projectTitleAlt", lineRow.getProjectTitleAlt());
				lineJson.put("requireFlag", lineRow.getRequireFlag());
				lineJson.put("displayFlag", lineRow.getDisplayFlag());
				lineJson.put("description", lineRow.getDescription());
				List<SaafTestQuestionnaireChoiceEntity_HI> choiceList = saafTestQuestionnaireChoiceDAO_HI.findByProperty("testQnLineId", lineRow.getQnLineId());
				lineJson.put("qnChoiceData", choiceList);
				jsonArray.add(lineJson);
			}
			json.put("lineList", jsonArray);
		} else {
			json.put("lineList", lineList);
		}
		return SToolUtils.convertResultJSONObj("S", "", 0, json);
	}

	@Override
	public List<SaafQuestionnaireHEntity_HI_RO> findUserByOrgIdList(JSONObject parameters){
		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_USER_NAME_BY_CITY_ID);

		if (null == parameters.get("responsibilitynIds") || "".equals(parameters.getString("responsibilitynIds"))) {
			hql.append(" WHERE 1=1 AND su.effective<>'Y' ");
		} else {
			hql.append(" INNER JOIN saaf_user_resp sur ON sur.user_id = su.user_id ");
			hql.append(" INNER JOIN saaf_responsibilitys sr ON sur.responsibility_id = sr.responsibility_id ");
			hql.append(" WHERE 1=1 AND su.effective<>'Y' ");
			hql.append(" AND sr.responsibility_id in (" + parameters.get("responsibilitynIds") + ")");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "si.inst_id", "instId", hql, map, "=");
		SaafToolUtils.parperParam(parameters, "su.user_full_name", "userFullName", hql, map, "like");
		hql.append(" order by si.inst_id ");

		List<SaafQuestionnaireHEntity_HI_RO> list = saafQuestionnaireHDAO_HI_RO.findList(hql, map);
		return list;
	}

	/**
	 * 通过publishId获取问卷调查答卷人信息
	 */
	@Override
	public List<SaafQuestionnaireHEntity_HI_RO> findResultPerson(JSONObject parameters){
		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_RESULT_PERSON);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqr.PUBLISH_ID", "publishId", hql, map, "=");
		hql.append(" group by sqr.PUBLISH_ID,bu.user_id ");
		List<SaafQuestionnaireHEntity_HI_RO> list = saafQuestionnaireHDAO_HI_RO.findList(hql, map);
		return list;
	}

	/**
	 * 通过publishId以及答题顺序id获取问卷调查答卷人信息
	 */
	@Override
	public List<SaafQuestionnaireHEntity_HI_RO> findAnswerResultList(JSONObject parameters){
		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_RESULT_PERSON);
		hql.append(" and sqr.RESULT_NUM in ( " + parameters.getString("resultNum") + " ) ");
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqr.PUBLISH_ID", "publishId", hql, map, "=");
		hql.append(" group by sqr.qn_HEAD_ID ");
		List<SaafQuestionnaireHEntity_HI_RO> list = saafQuestionnaireHDAO_HI_RO.findList(hql, map);
		return list;
	}

	/**
	 * 通过publishId，resultNum查询某人的答卷信息 vr_question_qn_result
	 */
	@Override
	public JSONObject findSaafQuestionnaireResult(JSONObject parameters){

		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_PUBLISH_INFO);
		Map<String, Object> publishMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqp.PUBLISH_ID", "publishId", hql, publishMap, "=");
		hql.append(" and sqp.status='publish' ");
		hql.append(" and SYSDATE()>=sqp.START_DATE_ACTIVE ");
		/// 查询的话不用控制结束时间，
		/*hql.append(" and SYSDATE()<=ifnull(vqsp.END_DATE_ACTIVE,SYSDATE()+1) ");*/
		List<SaafQuestionnaireHEntity_HI_RO> headList = saafQuestionnaireHDAO_HI_RO.findList(hql, publishMap);
		JSONObject json = new JSONObject();
		json.put("headList", headList);
		Map<String, Object> map = new HashMap<String, Object>();
		if (headList.size() > 0) {
			map.put("qnHeadId", headList.get(0).getQnHeadId());
		} else {
			return SToolUtils.convertResultJSONObj("F", "未查到需要的信息！", 0, "");
		}

		// 行信息以及答卷信息放在一起
		StringBuffer lineHql = new StringBuffer();
		lineHql.append(SaafQuestionnaireHEntity_HI_RO.CHECK_RESULT_PERSON);
		Map<String, Object> lineMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqr.PUBLISH_ID", "publishId", lineHql, lineMap, "=");
		SaafToolUtils.parperParam(parameters, "sqr.RESULT_NUM", "resultNum", lineHql, lineMap, "=");
		List<SaafQuestionnaireHEntity_HI_RO> lineList = saafQuestionnaireHDAO_HI_RO.findList(lineHql, lineMap);
		if (lineList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lineList.size(); i++) {
				JSONObject lineJson = new JSONObject();
				SaafQuestionnaireHEntity_HI_RO lineRow = lineList.get(i);
				lineJson.put("publishId", lineRow.getPublishId());
				lineJson.put("resultNum", lineRow.getResultNum());
				lineJson.put("qnChoiceId", lineRow.getQnChoiceId());
				lineJson.put("qnChoiceResult", lineRow.getQnChoiceResult());
				lineJson.put("qnLineId", lineRow.getQnLineId());
				lineJson.put("serialNumber", lineRow.getSerialNumber());
				lineJson.put("projectType", lineRow.getProjectType());
				lineJson.put("projectTitle", lineRow.getProjectTitle());
				lineJson.put("requireFlag", lineRow.getRequireFlag());
				lineJson.put("displayFlag", lineRow.getDisplayFlag());
				List<SaafTestQuestionnaireChoiceEntity_HI> choiceList = saafTestQuestionnaireChoiceDAO_HI
						.findByProperty("testQnLineId", lineRow.getQnLineId());
				lineJson.put("qnChoiceData", choiceList);
				jsonArray.add(lineJson);
			}
			json.put("lineList", jsonArray);
		} else {
			json.put("lineList", lineList);
		}
		return SToolUtils.convertResultJSONObj("S", "", 0, json);
	}

	/**
	 * 通过publishId，userId查询某人的答卷信息 add on 2018-08-16
	 */
	@Override
	public JSONObject findSaafQuestionnaireResultByUserId(JSONObject parameters){

		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_PUBLISH_INFO);
		Map<String, Object> publishMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqp.PUBLISH_ID", "publishId", hql, publishMap, "=");
		hql.append(" and sqp.status='publish' ");
		hql.append(" and SYSDATE()>=sqp.START_DATE_ACTIVE ");
		hql.append(" and SYSDATE()<=ifnull(sqp.END_DATE_ACTIVE,SYSDATE()+1) ");
		List<SaafQuestionnaireHEntity_HI_RO> headList = saafQuestionnaireHDAO_HI_RO.findList(hql, publishMap);
		JSONObject json = new JSONObject();
		json.put("headList", headList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qnHeadId", headList.get(0).getQnHeadId());

		// 行信息以及答卷信息放在一起
		StringBuffer lineHql = new StringBuffer();
		lineHql.append(SaafQuestionnaireHEntity_HI_RO.CHECK_RESULT_PERSON);
		Map<String, Object> lineMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqr.PUBLISH_ID", "publishId", lineHql, lineMap, "=");
		SaafToolUtils.parperParam(parameters, "sqr.CREATED_BY", "varUserId", lineHql, lineMap, "=");
		List<SaafQuestionnaireHEntity_HI_RO> lineList = saafQuestionnaireHDAO_HI_RO.findList(lineHql, lineMap);
		if (lineList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lineList.size(); i++) {
				JSONObject lineJson = new JSONObject();
				SaafQuestionnaireHEntity_HI_RO lineRow = lineList.get(i);
				lineJson.put("publishId", lineRow.getPublishId());
				lineJson.put("resultNum", lineRow.getResultNum());
				lineJson.put("qnChoiceId", lineRow.getQnChoiceId());
				lineJson.put("qnChoiceResult", lineRow.getQnChoiceResult());
				lineJson.put("qnLineId", lineRow.getQnLineId());
				lineJson.put("serialNumber", lineRow.getSerialNumber());
				lineJson.put("projectType", lineRow.getProjectType());
				lineJson.put("projectTitle", lineRow.getProjectTitle());
				lineJson.put("requireFlag", lineRow.getRequireFlag());
				lineJson.put("displayFlag", lineRow.getDisplayFlag());
				List<SaafQuestionnaireChoiceEntity_HI> choiceList = saafQuestionnaireChoiceDAO_HI
						.findByProperty("qnLineId", lineRow.getQnLineId());
				lineJson.put("qnChoiceData", choiceList);
				jsonArray.add(lineJson);
			}
			json.put("lineList", jsonArray);
		} else {
			json.put("lineList", lineList);
		}
		return SToolUtils.convertResultJSONObj("S", "", 0, json);
	}



	private boolean isNotNullOrEmpty(Object object) {
		return null != object && !"".equals(object);
	}

	/*@Override
	public JSONObject saveSubmitSaafQuestionnaire(JSONObject parameters){

		Integer varUserId = parameters.getInteger("varUserId");
		String varUserName = parameters.getString("varUserName");
		String varUserFullNames = parameters.getString("varUserFullNames");

		Integer qnHeadId = parameters.getInteger("qnHeadId");
		if (isNotNullOrEmpty(qnHeadId)) {
			List<SaafQuestionnaireHEntity_HI> headList = saafQuestionnaireHDAO_HI.findByProperty("qnHeadId",
					qnHeadId);

			if (headList.size() > 0) {
				SaafQuestionnaireHEntity_HI row = headList.get(0);
				// 审批中，已审批不能再次提交
				if ("APPROVING".equals(row.getStatus()) || "APPROVED".equals(row.getStatus())) {
					return SToolUtils.convertResultJSONObj("F", "当前状态不允许发起审批!", 0, "");
				}

				// 通过userId获取领导id
				Integer instThisLeaderid = 0;

				StringBuffer hql = new StringBuffer();
				hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_LEADER_SQL);
				Map<String, Object> userMap = new HashMap<String, Object>();
				SaafToolUtils.parperParam(parameters, "suao.user_id", "varUserId", hql, userMap, "=");
				List<SaafQuestionnaireHEntity_HI_RO> userList;
				userList = saafQuestionnaireHDAO_HI_RO.findList(hql, userMap);

				if (userList.size() > 0) {
					SaafQuestionnaireHEntity_HI_RO userInfo = userList.get(0);
					instThisLeaderid = userInfo.getInstThisLeaderid();
				} else {
					StringBuffer userHql = new StringBuffer();
					userHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_LEADER_SQL_1);
					Map<String, Object> userMap1 = new HashMap<String, Object>();
					SaafToolUtils.parperParam(parameters, "suao.user_id", "varUserId", userHql, userMap1, "=");
					userList = saafQuestionnaireHDAO_HI_RO.findList(userHql, userMap1);

					SaafQuestionnaireHEntity_HI_RO userInfo = userList.get(0);
					instThisLeaderid = userInfo.getInstThisLeaderid();
				}

				JSONObject vars = JSON.parseObject(JSONObject.toJSONString(row));
				vars.put("varUserId", varUserId);
				vars.put("varUserName", varUserName);
				vars.put("varUserFullNames", varUserFullNames);
				vars.put("instThisLeaderid", instThisLeaderid);
				// vars.put("cityId", value);
				vars.put("businessInfo", "调查问卷审批流程[发起人:" + varUserFullNames + ",发起时间:"
						+ SToolUtils.date2String(new Date(), "yyyy-MM-dd") + "]");
				String processInstanceId = row.getProcessInstanceId();
				if (!isNotNullOrEmpty(processInstanceId)) {
					// processInstanceId为空,启动流程
					processInstanceId = flowRuntimeServer.startFlow(vars, "VR_QUESTION_SURVEY_H",
							parameters.getString("qnHeadId"));
					// flowTaskServer.complete(processInstanceId,
					// String.valueOf(varUserId),
					// String.valueOf(varUserFullNames), "发起人提交", "提交");
				}
				// update on 2018-07-19 流程跳转到流程提交页面
				// else if (isNotNullOrEmpty(processInstanceId) &&
				// ("REJECT".equals(row.getStatus().trim()) ||
				// "RETRACT".equals(row.getStatus().trim()))) {
				// // processInstanceId不为空,且审批状态不为”驳回“和”撤回“
				// flowTaskServer.complete(processInstanceId,
				// String.valueOf(varUserId), String.valueOf(varUserFullNames),
				// "发起人重新提交", "提交");
				// }
				// 设置审批状态为”审批中“
				// row.setStatus("APPROVING");
				row.setOperatorUserId(varUserId);
				// 工作流审批状态字段
				row.setProcessInstanceId(processInstanceId);
				saafQuestionnaireHDAO_HI.saveOrUpdate(row);
				return SToolUtils.convertResultJSONObj("S", "提交成功!", 0, row);
			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到待提交的信息!", 0, "");
			}
		}
		return SToolUtils.convertResultJSONObj("S", "提交成功!", 0, "");
	}
*/
	@Override
	public JSONObject updateSaafQuestionnaireStatus(JSONObject parameters){
		SaafQuestionnaireHEntity_HI row = null;
		Integer varUserId = parameters.getInteger("varUserId");
			if (!isNotNullOrEmpty(varUserId)) {
				varUserId = -999;
			}
			Integer qnHeadId = parameters.getInteger("businessId");
			String status = parameters.getString("status");
			if (isNotNullOrEmpty(qnHeadId)) {
				row = saafQuestionnaireHDAO_HI.findByProperty("qnHeadId", qnHeadId).get(0);
				row.setStatus(status);
				row.setOperatorUserId(varUserId);
				saafQuestionnaireHDAO_HI.saveOrUpdate(row);
			}
			return SToolUtils.convertResultJSONObj("S", "审批状态修改成功", 1, saafQuestionnaireHDAO_HI);

	}

	/**
	 * 提交发布问卷申请
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject saveSubmitSaafQuestionnairePublish(JSONObject parameters){

		Integer varUserId = parameters.getInteger("varUserId");
		String varUserName = parameters.getString("varUserName");
		String varUserFullNames = parameters.getString("varUserFullNames");

		Integer publishId = parameters.getInteger("publishId");
		if (isNotNullOrEmpty(publishId)) {
			List<SaafQuestionnairePublishEntity_HI> headList =
					saafQuestionnairePublishDAO_HI.findByProperty("publishId",publishId);
			if (headList.size() > 0) {
				SaafQuestionnairePublishEntity_HI row = headList.get(0);
				// 审批中，已审批不能再次提交
				if ("APPROVING".equals(row.getFlowStatus()) || "APPROVED".equals(row.getFlowStatus())) {
					return SToolUtils.convertResultJSONObj("F", "当前状态不允许发起审批!", 0, "");
				}
				//已发布的不允许提交
				if ("publish".equals(row.getStatus())) {
					return SToolUtils.convertResultJSONObj("F", "已发布的问卷不允许发起审批!", 0, "");
				}

			/*	// 通过userId获取领导id
				Integer instThisLeaderid = 0;

				StringBuffer hql = new StringBuffer();
				hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_LEADER_SQL);
				Map<String, Object> userMap = new HashMap<String, Object>();
				SaafToolUtils.parperParam(parameters, "suao.user_id", "varUserId", hql, userMap, "=");
				List<SaafQuestionnaireHEntity_HI_RO> userList;
				userList = saafQuestionnaireHDAO_HI_RO.findList(hql, userMap);

				if (userList.size() > 0) {
					SaafQuestionnaireHEntity_HI_RO userInfo = userList.get(0);
					instThisLeaderid = userInfo.getInstThisLeaderid();
				} else {
					StringBuffer userHql = new StringBuffer();
					userHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_LEADER_SQL_1);
					Map<String, Object> userMap1 = new HashMap<String, Object>();
					SaafToolUtils.parperParam(parameters, "suao.user_id", "varUserId", userHql, userMap1, "=");
					userList = saafQuestionnaireHDAO_HI_RO.findList(userHql, userMap1);

					SaafQuestionnaireHEntity_HI_RO userInfo = userList.get(0);
					instThisLeaderid = userInfo.getInstThisLeaderid();
				}

				JSONObject vars = JSON.parseObject(JSONObject.toJSONString(row));
				vars.put("varUserId", varUserId);
				vars.put("varUserName", varUserName);
				vars.put("varUserFullNames", varUserFullNames);
				vars.put("instThisLeaderid", instThisLeaderid);
				vars.put("businessInfo", "调查问卷审批流程[发起人:" + varUserFullNames + ",发起时间:"
						+ SToolUtils.date2String(new Date(), "yyyy-MM-dd") + "]");
				String processInstanceId = row.getProcessInstanceId();
				if (!isNotNullOrEmpty(processInstanceId)) {
					// processInstanceId为空,启动流程
					processInstanceId = flowRuntimeServer.startFlow(vars, "VR_QUEST_SURVEY_PUBLISH",
							parameters.getString("publishId"));
				}
				row.setOperatorUserId(varUserId);
				// 工作流审批状态字段
				row.setProcessInstanceId(processInstanceId);*/
				saafQuestionnairePublishDAO_HI.saveOrUpdate(row);
				return SToolUtils.convertResultJSONObj("S", "提交成功!", 0, row);
			} else {
				return SToolUtils.convertResultJSONObj("F", "未找到待提交的信息!", 0, "");
			}
		}else{
			return SToolUtils.convertResultJSONObj("F", "发布Id不能为空!", 0, "");
		}

	}

	/**
	 * 发布问卷调查流程修改状态
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateSaafQuestionnairePublishStatus(JSONObject parameters){
		SaafQuestionnairePublishEntity_HI row = null;
		Integer varUserId = parameters.getInteger("varUserId");
			if (!isNotNullOrEmpty(varUserId)) {
				varUserId = -999;
			}

			Integer publishId = parameters.getInteger("businessId");
			String status = parameters.getString("status");
			if (isNotNullOrEmpty(publishId)) {
				row = saafQuestionnairePublishDAO_HI.findByProperty("publishId", publishId).get(0);
				row.setFlowStatus(status);
				row.setOperatorUserId(varUserId);
				saafQuestionnairePublishDAO_HI.saveOrUpdate(row);
			}
			return SToolUtils.convertResultJSONObj("S", "审批状态修改成功", 1, saafQuestionnairePublishDAO_HI);

	}

	/**
	 * 获取流程最新参数
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject getPublishNewParameters(JSONObject parameters){
		Integer businessId = parameters.getInteger("businessId");
		List<SaafQuestionnairePublishEntity_HI> list = saafQuestionnairePublishDAO_HI.findByProperty("publishId", businessId);
		if (list.size() > 0) {
			SaafQuestionnairePublishEntity_HI row = list.get(0);
			JSONObject newParams = JSONObject.parseObject(JSONObject.toJSONString(row));
			return newParams;
		}
		return null;
	}

	/**
	 * 获取流程最新参数
	 *
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject getNewParameters(JSONObject parameters){
		Integer businessId = parameters.getInteger("businessId");
		List<SaafQuestionnaireHEntity_HI> list = saafQuestionnaireHDAO_HI.findByProperty("qnHeadId", businessId);
		if (list.size() > 0) {
			SaafQuestionnaireHEntity_HI row = list.get(0);
			JSONObject newParams = JSONObject.parseObject(JSONObject.toJSONString(row));
			return newParams;
		}
		return null;
	}

	/**
	 * 通过publishId查询报表
	 */
	@Override
	public JSONObject findResultReport(JSONObject parameters){

		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_PUBLISH_INFO);
		Map<String, Object> publishMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqp.PUBLISH_ID", "publishId", hql, publishMap, "=");
		hql.append(" and sqp.status='publish' ");
		List<SaafQuestionnaireHEntity_HI_RO> headList = saafQuestionnaireHDAO_HI_RO.findList(hql, publishMap);
		JSONObject json = new JSONObject();
		json.put("headList", headList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("testQnHeadId", headList.get(0).getTestQnHeadId());

		// 行信息
		List<SaafTestQuestionnaireLEntity_HI> lineList = saafTestQuestionnaireLDAO_HI.findList(
				" from SaafTestQuestionnaireLEntity_HI where testQnHeadId=:testQnHeadId order by serialNumber", map);
		if (lineList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lineList.size(); i++) {
				JSONObject lineJson = new JSONObject();
				SaafTestQuestionnaireLEntity_HI lineRow = lineList.get(i);
				lineJson.put("testQnLineId", lineRow.getQnLineId());
				lineJson.put("serialNumber", lineRow.getSerialNumber());
				lineJson.put("projectType", lineRow.getProjectType());
				lineJson.put("projectTitle", lineRow.getProjectTitle());
				lineJson.put("requireFlag", lineRow.getRequireFlag());
				lineJson.put("displayFlag", lineRow.getDisplayFlag());

				StringBuffer lineHql = new StringBuffer();
				lineHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_QnChoice_SQL);
				lineHql.append(" and sqp.PUBLISH_ID=:publishId  ");
				lineHql.append(" and sqc.TEST_QN_LINE_ID=:testQnLineId  ");
				Map<String, Object> lineMap = new HashMap<String, Object>();
				lineMap.put("testQnLineId", lineRow.getQnLineId());
				lineMap.put("publishId", parameters.getInteger("publishId"));
				List<SaafQuestionnaireHEntity_HI_RO> choiceList = saafQuestionnaireHDAO_HI_RO.findList(lineHql, lineMap);

				StringBuffer resultHql = new StringBuffer();
				resultHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_RESULT_PERSON);
				resultHql.append(" and sqr.PUBLISH_ID=:publishId  ");
				resultHql.append(" and sqr.QN_LINE_ID=:testQnLineId  ");
				List<SaafQuestionnaireHEntity_HI_RO> resultList = saafQuestionnaireHDAO_HI_RO.findList(resultHql,lineMap);
				// 2018-08-23修改信息
				// List<SaafQuestionnaireResultEntity_HI> resultList=
				// saafQuestionnaireResultDAO_HI.findByProperty(lineMap);
				lineJson.put("qnChoiceData", choiceList);
				lineJson.put("qnResultData", resultList);
				jsonArray.add(lineJson);
			}
			json.put("lineList", jsonArray);
		} else {
			json.put("lineList", lineList);
		}
		return SToolUtils.convertResultJSONObj("S", "", 0, json);
	}

	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBold(true);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		// 设置右边框;
		style.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(IndexedColors.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(BorderStyle.THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(IndexedColors.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置单元格背景颜色
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		// 设置右边框;
		style.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(IndexedColors.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(BorderStyle.THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(IndexedColors.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		return style;
	}

	/**
	 * 通过publishId查询报表
	 */
	@Override
	public void findReportToExport(JSONObject parameters, HttpServletRequest servletRequest,
								   HttpServletResponse servletResponse){

		StringBuffer hql = new StringBuffer();
		hql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_SQL_PUBLISH_INFO);
		Map<String, Object> publishMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "vqsp.PUBLISH_ID", "publishId", hql, publishMap, "=");
		hql.append(" and vqsp.status='publish' ");
		List<SaafQuestionnaireHEntity_HI_RO> headList = saafQuestionnaireHDAO_HI_RO.findList(hql, publishMap);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qnHeadId", headList.get(0).getQnHeadId());

		// 设置EXCEL
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
		HSSFSheet sheet = workbook.createSheet(headList.get(0).getQnTitle()); // 创建工作表

		// 行信息
		List<SaafQuestionnaireLEntity_HI> lineList = saafQuestionnaireLDAO_HI.findList(
				" from SaafQuestionnaireLEntity_HI where qnHeadId=:qnHeadId order by serialNumber", map);

		int rowsNum = 0;
		if (lineList.size() > 0) {
			for (int i = 0; i < lineList.size(); i++) {
				SaafQuestionnaireLEntity_HI lineRow = lineList.get(i);

				StringBuffer lineHql = new StringBuffer();
				lineHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_QnChoice_SQL);
				lineHql.append(" and vqsp.PUBLISH_ID=:publishId  ");
				lineHql.append(" and vqsc.SURVEY_LINE_ID=:qnLineId  ");
				Map<String, Object> lineMap = new HashMap<String, Object>();
				lineMap.put("qnLineId", lineRow.getQnLineId());
				lineMap.put("publishId", parameters.getInteger("publishId"));
				List<SaafQuestionnaireHEntity_HI_RO> choiceList = saafQuestionnaireHDAO_HI_RO.findList(lineHql, lineMap);

				StringBuffer resultHql = new StringBuffer();
				resultHql.append(SaafQuestionnaireHEntity_HI_RO.QUERY_RESULT_PERSON);
				resultHql.append(" and vqsr.PUBLISH_ID=:publishId  ");
				resultHql.append(" and vqsr.SURVEY_LINE_ID=:qnLineId  ");
				List<SaafQuestionnaireHEntity_HI_RO> resultList = saafQuestionnaireHDAO_HI_RO.findList(resultHql,
						lineMap);
				// 产生表格标题行
				HSSFRow rowm = sheet.createRow(rowsNum);
				HSSFCell cellTiltle = rowm.createCell(0);
				rowm.setHeight((short) (25 * 35)); // 设置高度

				// sheet样式定义
				// 判断是否是textarea或者text两种类型，这两种只需要记录回答以及回答人

				if ("text".equals(lineRow.getProjectType()) || "textarea".equals(lineRow.getProjectType())) {
					// EXCEL表头标题
					String[] rowName = new String[] { "答案", "回答人" };
					HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
					columnTopStyle.setAlignment(HorizontalAlignment.LEFT);//水平居左
					HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
					sheet.addMergedRegion(new CellRangeAddress(rowsNum, rowsNum, 0, (rowName.length - 1)));
					cellTiltle.setCellStyle(columnTopStyle);
					cellTiltle.setCellValue("Q" + (i + 1) + "：" + lineRow.getProjectTitle());
					rowsNum = rowsNum + 1;
					HSSFRow rowRowName = sheet.createRow(rowsNum);
					rowRowName.setHeight((short) (25 * 25)); // 设置高度

					// 将列头设置到sheet的单元格中
					for (int n = 0; n < rowName.length; n++) {
						HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
						cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
						HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
						columnTopStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
						cellRowName.setCellValue(text); // 设置列头单元格的值
						cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
					}

					// 获取选项个数
					System.out.println("--------resultList:"+resultList.size());
					for (int n = 0; n < resultList.size(); n++) {
						HSSFRow row = sheet.createRow(rowsNum + n + 1);// 创建所需的行数
						row.setHeight((short) (25 * 20)); // 设置高度
						// 将每个值push到数组里面
						SaafQuestionnaireHEntity_HI_RO resultRow = resultList.get(n);
						List<Object> list = new ArrayList<Object>();
						list.add(resultRow.getQnChoiceResult());
						list.add(resultRow.getUserFullName());
						for (int j = 0; j < list.size(); j++) {
							HSSFCell cell = null; // 设置单元格的数据类型
							cell = row.createCell(j, CellType.STRING);
							if (!"".equals(list.get(j)) && list.get(j) != null) {
								cell.setCellValue(list.get(j).toString()); // 设置单元格的值
							} else {
								cell.setCellValue("");
							}
							cell.setCellStyle(style); // 设置单元格样式
						}
					}

					// 重新设置个数(设置一个空白行)
					rowsNum = rowsNum + resultList.size() + 1;
					// 让列宽随着导出的列长自动适应
					for (int colNum = 0; colNum < rowName.length; colNum++) {
						int columnWidth = sheet.getColumnWidth(colNum) / 256;
						for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
							HSSFRow currentRow;
							// 当前行未被使用过
							if (sheet.getRow(rowNum) == null) {
								currentRow = sheet.createRow(rowNum);
							} else {
								currentRow = sheet.getRow(rowNum);
							}
							if (currentRow.getCell(colNum) != null) {
								HSSFCell currentCell = currentRow.getCell(colNum);
								if (currentCell.getCellType() == CellType.STRING) {
									int length = currentCell.getStringCellValue().getBytes().length;
									// 设置最大宽度500
									if (length > 500) {
										length = 500;
									}
									if (columnWidth < length) {
										columnWidth = length;
									}
								}
							}
						}
						if (colNum == 0) {
							sheet.setColumnWidth(colNum, (columnWidth - 2) * 128);
						} else {
							sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
						}
					}


				} else {

					// EXCEL表头标题
					String[] rowName = new String[] { "选项", "是否答案", "回答人数", "回答人" };

					HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
					HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
					sheet.addMergedRegion(new CellRangeAddress(rowsNum, rowsNum, 0, (rowName.length - 1)));
					columnTopStyle.setAlignment(HorizontalAlignment.LEFT);//水平居左
					cellTiltle.setCellStyle(columnTopStyle);
					cellTiltle.setCellValue("Q" + (i + 1) + "：" + lineRow.getProjectTitle());
					rowsNum = rowsNum + 1;
					HSSFRow rowRowName = sheet.createRow(rowsNum);
					rowRowName.setHeight((short) (25 * 25)); // 设置高度

					// 将列头设置到sheet的单元格中
					for (int n = 0; n < rowName.length; n++) {
						HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
						cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
						columnTopStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
						HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
						cellRowName.setCellValue(text); // 设置列头单元格的值
						cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
					}

					// 获取选项个数
					for (int n = 0; n < choiceList.size(); n++) {
						HSSFRow row = sheet.createRow(rowsNum + n + 1);// 创建所需的行数
						row.setHeight((short) (25 * 20)); // 设置高度
						// 将每个值push到数组里面
						SaafQuestionnaireHEntity_HI_RO vrQuestionHRow = choiceList.get(n);
						List<Object> list = new ArrayList<Object>();
						list.add(vrQuestionHRow.getQnChoiceContent());
						if ("Y".equals(vrQuestionHRow.getQnAnswer())) {
							list.add("是");
						} else {
							list.add("否");
						}

						// 判断回答人以及回答信息
						int sum = 0;// 统计人数
						String answerName = "";

						if ("multi_selection".equals(lineRow.getProjectType())) {
							for (int m = 0; m < resultList.size(); m++) {
								String[] splitChoiceIds =null;
								if(null!=resultList.get(m).getQnChoiceId() && !"".equals(resultList.get(m).getQnChoiceId())){
									splitChoiceIds = resultList.get(m).getQnChoiceId().split(",");
									for (int p = 0; p < splitChoiceIds.length; p++) {
										if (vrQuestionHRow.getQnChoiceId().equals(splitChoiceIds[p])) {
											sum = sum + 1;
											if ("".equals(answerName)) {
												if(null==resultList.get(m).getUserFullName()){
													answerName="";
												}else{
													answerName = resultList.get(m).getUserFullName();
												}
											} else {
												answerName = answerName + ";" + resultList.get(m).getUserFullName();
											}
										}
									}
								}
							}
						} else {
							for (int m = 0; m < resultList.size(); m++) {
								// 判断是否相等
								if (vrQuestionHRow.getQnChoiceId().equals(resultList.get(m).getQnChoiceId())) {
									sum = sum + 1;
									if ("".equals(answerName)) {
										if(null==resultList.get(m).getUserFullName()){
											answerName="";
										}else{
											answerName = resultList.get(m).getUserFullName();
										}
									} else {
										answerName = answerName + ";" + resultList.get(m).getUserFullName();
									}
								}
							}
						}

						list.add(sum);
						list.add(answerName);

						for (int j = 0; j < list.size(); j++) {
							HSSFCell cell = null; // 设置单元格的数据类型
							cell = row.createCell(j, CellType.STRING);
							if (!"".equals(list.get(j)) && list.get(j) != null) {
								cell.setCellValue(list.get(j).toString()); // 设置单元格的值
							} else {
								cell.setCellValue("");
							}
							cell.setCellStyle(style); // 设置单元格样式
						}
					}

					// 重新设置个数(设置一个空白行)
					rowsNum = rowsNum + choiceList.size() + 1;
					// 让列宽随着导出的列长自动适应
					for (int colNum = 0; colNum < rowName.length; colNum++) {
						int columnWidth = sheet.getColumnWidth(colNum) / 256;
						for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
							HSSFRow currentRow;
							// 当前行未被使用过
							if (sheet.getRow(rowNum) == null) {
								currentRow = sheet.createRow(rowNum);
							} else {
								currentRow = sheet.getRow(rowNum);
							}
							if (currentRow.getCell(colNum) != null) {
								HSSFCell currentCell = currentRow.getCell(colNum);
								if (currentCell.getCellType() == CellType.STRING) {
									int length = currentCell.getStringCellValue().getBytes().length;
									// 设置最大宽度500
									if (length > 500) {
										length = 500;
									}
									if (columnWidth < length) {
										columnWidth = length;
									}
								}
							}
						}
						if (colNum == 0) {
							sheet.setColumnWidth(colNum, (columnWidth - 2) * 128);
						} else {
							//sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
						}
					}
				}
			}

			BufferedOutputStream output = null;
			if (workbook != null) {
                try {
                    String userAgent = servletRequest.getHeader("USER-AGENT");
					String targetFileName=headList.get(0).getQnTitle();
					if(StringUtils.contains(userAgent, "Firefox")){//google,火狐浏览器

                            targetFileName = new String(targetFileName.getBytes(), "ISO8859-1");

                    }else{
						targetFileName = URLEncoder.encode(targetFileName,"UTF8");
					}

					servletResponse.setHeader("Content-Disposition", "attachment;filename=" + targetFileName + ".xls");

					output = new BufferedOutputStream(servletResponse.getOutputStream());
					workbook.write(output);
					output.flush();
					servletResponse.flushBuffer();

//					FileOutputStream out = new FileOutputStream("C:/Users/liufuyou/mobile/"
//							+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
//					workbook.write(out);
//					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						if (output != null) {
							output.close();
						}
					} catch (IOException e) {
						LOGGER.error(e.getLocalizedMessage(),e);
					}
				}
			}
		}

	}

	/**
	 * 查询题库题目及题目属性
	 * @param parameters
	 */
	public Object findQuestionnaireLAndQuestionnaireChoice(JSONObject parameters,Integer pageIndex,Integer pageRows){
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qnType",parameters.getString("qnType"));
		StringBuffer sql = new StringBuffer(SaafQuestionnaireHEntity_HI_RO.QUERY_LINE);
		if(!StringUtils.isBlank(parameters.getString("testQnHeadId"))){
			map.put("testQnHeadId",parameters.getInteger("testQnHeadId"));
			sql.append(" and sqll.QN_LINE_ID not in(select stql.ORIGINAL_QN_LINE_ID from tta_test_questionnaire_line stql where stql.test_qn_head_id = :testQnHeadId) ");
		}
        sql.append(" order by if(sqll.REQUIRE_FLAG,0,9999),sqll.SERIAL_NUMBER asc ");
        Pagination<SaafQuestionnaireHEntity_HI_RO> lineList = saafQuestionnaireHDAO_HI_RO.findPagination(sql,map,pageIndex,pageRows);
		if (lineList.getData().size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lineList.getData().size(); i++) {
				JSONObject lineJson = new JSONObject();
				SaafQuestionnaireHEntity_HI_RO lineRow = lineList.getData().get(i);
				lineJson.put("qnHeadId", lineRow.getQnHeadId());
				lineJson.put("qnLineId", lineRow.getQnLineId());
				lineJson.put("serialNumber", lineRow.getSerialNumber());
				lineJson.put("projectType", lineRow.getProjectType());
				lineJson.put("projectTitle", lineRow.getProjectTitle());
				lineJson.put("projectTitleAlt", lineRow.getProjectTitleAlt());
				lineJson.put("requireFlag", lineRow.getRequireFlag());
				lineJson.put("displayFlag", lineRow.getDisplayFlag());
				lineJson.put("description", lineRow.getDescription());
				List<SaafQuestionnaireChoiceEntity_HI> choiceList = saafQuestionnaireChoiceDAO_HI
						.findByProperty("qnLineId", lineRow.getQnLineId());
				lineJson.put("qnChoiceData", choiceList);
				lineRow.setChoiceArr(choiceList);
				jsonArray.add(lineJson);
			}
			if ("Y".equalsIgnoreCase(parameters.getString("LOV_FLAG"))){
				return lineList;
			}else{
				json.put("lineList", jsonArray);
			}
		} else {
			json.put("lineList", lineList);
		}
		return json;
	}

}
