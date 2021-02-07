package com.sie.watsons.base.questionnaire.model.inter.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafTestQuestionnaireLEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.dao.SaafTestQuestionnaireChoiceDAO_HI;
import com.sie.watsons.base.questionnaire.model.dao.SaafTestQuestionnaireHDAO_HI;
import com.sie.watsons.base.questionnaire.model.dao.SaafTestQuestionnaireLDAO_HI;
import com.sie.watsons.base.questionnaire.model.dao.readonly.SaafTestQuestionnaireChoiceDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.dao.readonly.SaafTestQuestionnaireHDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.dao.readonly.SaafTestQuestionnaireLDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireChoiceEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireHEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireLEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireChoiceEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafTestQuestionnaireChoiceEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafTestQuestionnaireHEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ISaafTestQuestionnaireH;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@Component("saafTestQuestionnaireHServer")
public class SaafTestQuestionnaireHServer extends BaseCommonServer<SaafTestQuestionnaireHEntity_HI> implements ISaafTestQuestionnaireH{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafTestQuestionnaireHServer.class);

	@Autowired
	private SaafTestQuestionnaireChoiceDAO_HI saafTestQuestionnaireChoiceDAO_HI;
	@Autowired
	private SaafTestQuestionnaireHDAO_HI_RO saafTestQuestionnaireHDAO_HI_RO;
	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private SaafTestQuestionnaireHDAO_HI saafTestQuestionnaireHDAO_HI;
	@Autowired
	private SaafTestQuestionnaireLDAO_HI saafTestQuestionnaireLDAO_HI;
	
	@Autowired
	private SaafTestQuestionnaireChoiceDAO_HI_RO saafTestQuestionnaireChoiceEntity_HI_RO;
	
	@Autowired
	private SaafTestQuestionnaireLDAO_HI_RO saafTestQuestionnaireLDAO_HI_RO;


	/**
	 * 查询问卷调查列表
	 */
	public Pagination<SaafTestQuestionnaireHEntity_HI_RO> findSaafTestQuestionnaireList(JSONObject parameters, Integer pageIndex, Integer pageRows){
		StringBuffer hql = new StringBuffer();
		hql.append(SaafTestQuestionnaireHEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "stqh.qn_title", "qnTitle", hql, map, "like");
		SaafToolUtils.parperParam(parameters, "stqh.qn_code", "qnCode", hql, map, "like");
		SaafToolUtils.parperParam(parameters, "stqh.status", "status", hql, map, "=");
		SaafToolUtils.parperParam(parameters, "stqh.test_qn_head_id", "testQnHeadId", hql, map, "=");
		hql.append(" order by stqh.LAST_UPDATE_DATE desc ");
		Pagination<SaafTestQuestionnaireHEntity_HI_RO> rowSet = saafTestQuestionnaireHDAO_HI_RO.findPagination(hql, map,pageIndex, pageRows);
		return rowSet;
	}

	/**
	 * 问卷头保存
	 * @param parameters
	 * @return
	 */
	public JSONObject saveSaafTestQuestionnaireH(JSONObject parameters) throws Exception {
		try {
			if (null == parameters.get("qnTitle") || "".equals(parameters.getString("qnTitle").trim())) {
				return SToolUtils.convertResultJSONObj("F", "问卷标题不能为空!", 0, "");
			}
			if (null == parameters.get("qnType") || "".equals(parameters.getString("qnType").trim())) {
				return SToolUtils.convertResultJSONObj("F", "问卷类型不能为空!", 0, "");
			}
			SaafTestQuestionnaireHEntity_HI row = null;
			if (null == parameters.get("testQnHeadId") || "".equals(SToolUtils.object2String(parameters.get("testQnHeadId")).trim())) {
				// 判断是否新增
				row = new SaafTestQuestionnaireHEntity_HI();
				// 新增，创建问卷调查编号
				//generateCodeServer.generateCode("SAAF_TEST_QUESTIONNAIRE_H");
				String qnCode = generateCodeServer.getGenerateCode("WJ", 32);
				row.setQnCode(qnCode);
				row.setStatus("NEW");
			} else {
				row = saafTestQuestionnaireHDAO_HI.findByProperty("testQnHeadId", SToolUtils.object2Int(parameters.get("testQnHeadId"))).get(0);
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
			row.setOperatorUserId(parameters.getInteger("varUserId"));
			saafTestQuestionnaireHDAO_HI.saveOrUpdate(row);
			return SToolUtils.convertResultJSONObj("S", "保存成功!", 1, row);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 问卷行保存
	 * @param params
	 * @return
	 */
	public boolean saveSaafTestQuestionnaireL(JSONObject params) throws Exception {
		Integer testQnHeadId = params.getInteger("testQnHeadId");
		Assert.notNull(testQnHeadId,"testQnHeadId不能为空");
		Integer userId = params.getInteger("varUserId");

		try {
			if (params.containsKey("lineList") && params.getJSONArray("lineList").size() > 0){
                JSONArray lineList = params.getJSONArray("lineList");
                for (int i=0;i<lineList.size();i++){
                    JSONObject json = lineList.getJSONObject(i);
                    SaafTestQuestionnaireLEntity_HI saafTestQuestionnaireLEntity_HI = JSON.toJavaObject(json,SaafTestQuestionnaireLEntity_HI.class);
                    saafTestQuestionnaireLEntity_HI.setOriginalQnLineId(json.getInteger("qnLineId"));
                    saafTestQuestionnaireLEntity_HI.setOperatorUserId(userId);
                    saafTestQuestionnaireLDAO_HI.save(saafTestQuestionnaireLEntity_HI);

                    if (json.containsKey("choiceArr") && json.getJSONArray("choiceArr").size() > 0){
                        JSONArray choiceArr = json.getJSONArray("choiceArr");
                        for (int j=0;j<choiceArr.size();j++){
                            JSONObject choiceLine = choiceArr.getJSONObject(j);
                            SaafTestQuestionnaireChoiceEntity_HI saafTestQuestionnaireChoiceEntity_HI = JSON.toJavaObject(choiceLine,SaafTestQuestionnaireChoiceEntity_HI.class);
                            saafTestQuestionnaireChoiceEntity_HI.setQnLineId(saafTestQuestionnaireLEntity_HI.getQnLineId());
                            saafTestQuestionnaireChoiceEntity_HI.setOperatorUserId(userId);
                            saafTestQuestionnaireChoiceDAO_HI.save(saafTestQuestionnaireChoiceEntity_HI);
                        }
                    }
                }
            }
            return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 查询行及choice
	 * @param params
	 * @return
	 */
	public List<SaafTestQuestionnaireHEntity_HI_RO> findLineAndchoice(JSONObject params) throws Exception {
		Integer testQnHeadId = params.getInteger("testQnHeadId");
		Assert.notNull(testQnHeadId,"testQnHeadId不能为空");

		StringBuffer sql = new StringBuffer(SaafTestQuestionnaireHEntity_HI_RO.QUERY_LINE_AND_CHOICES);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(params, "qtql.TEST_QN_HEAD_ID","testQnHeadId", sql, paramsMap, "=");
		sql.append(" order by qtql.SERIAL_NUMBER asc ");
		List<SaafTestQuestionnaireHEntity_HI_RO> list = saafTestQuestionnaireHDAO_HI_RO.findList(sql,paramsMap);
		for (SaafTestQuestionnaireHEntity_HI_RO en : list){
			List<SaafTestQuestionnaireChoiceEntity_HI> choiceList = saafTestQuestionnaireChoiceDAO_HI.findByProperty("testQnLineId", en.getTestQnLineId());
			en.setChoiceList(choiceList);
		}
		return list;
	}

	/**
	 * 行删除
	 * @param params
	 * @return
	 */
	public boolean deleteLine(JSONObject params) throws Exception {
		Integer testQnLineId = params.getInteger("testQnLineId");
		Assert.notNull(testQnLineId,"testQnLineId不能为空");
		saafTestQuestionnaireLDAO_HI.delete(testQnLineId);
		return true;
	}

	/**
	 * 查询问卷调查列表 LOV
	 */
	public Pagination<SaafTestQuestionnaireHEntity_HI_RO> findSaafTestQuestionnaireLov(JSONObject parameters, Integer pageIndex,Integer pageRows){
		StringBuffer hql = new StringBuffer();
		hql.append(SaafTestQuestionnaireHEntity_HI_RO.QUERY_LIST_LOV);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "sqh.qn_title", "qnTitle", hql, map, "like");
		SaafToolUtils.parperParam(parameters, "sqh.qn_code", "qnCode", hql, map, "like");
		// SaafToolUtils.parperParam(parameters, "vqsh.qn_status",
		// "qnStatus",hql, map, "=");
		SaafToolUtils.parperParam(parameters, "sqh.status", "status", hql, map, "=");
		hql.append(" order by sqh.LAST_UPDATE_DATE desc ");
		Pagination<SaafTestQuestionnaireHEntity_HI_RO> rowSet = saafTestQuestionnaireHDAO_HI_RO.findPagination(hql, map,
				pageIndex, pageRows);
		return rowSet;
	}

	@Override
	public JSONObject findTestQuestionnaireByProposalId(JSONObject jsonObject) {
		JSONObject json = new JSONObject();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(SaafTestQuestionnaireLEntity_HI_RO.QUERY_TEST_QUESTIONNAIRE).append(" and  proposal_id=" + jsonObject.getInteger("proposalId")).append(" order by a.serial_number");
		List<SaafTestQuestionnaireLEntity_HI_RO> lineList = saafTestQuestionnaireLDAO_HI_RO.findList(sqlBuffer.toString(), new HashMap<String, Object>());
		if (lineList.size() > 0) {
		    StringBuffer sbSql = null;
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lineList.size(); i++) {
				sbSql = new StringBuffer();
				JSONObject lineJson = new JSONObject();
				SaafTestQuestionnaireLEntity_HI_RO lineRow = lineList.get(i);
				lineJson.put("qnLineId", lineRow.getQnLineId());
				lineJson.put("serialNumber", lineRow.getSerialNumber());
				lineJson.put("projectType", lineRow.getProjectType());
				lineJson.put("projectTitle", lineRow.getProjectTitle());
				lineJson.put("projectTitleAlt", lineRow.getProjectTitleAlt());
				lineJson.put("requireFlag", lineRow.getRequireFlag());
				lineJson.put("displayFlag", lineRow.getDisplayFlag());
				lineJson.put("description", lineRow.getDescription());
                lineJson.put("projectCategory", lineRow.getProjectCategory());
				String projectType = lineRow.getProjectType();
                sbSql.append(SaafTestQuestionnaireChoiceEntity_HI_RO.QUERY_CHOICE)
                        .append(" and  a.qn_line_id = " +  lineRow.getQnLineId())
                        .append(" order by a.qn_choice asc ");
				List<SaafTestQuestionnaireChoiceEntity_HI_RO> choiceList = saafTestQuestionnaireChoiceEntity_HI_RO.findList(sbSql.toString(), new JSONObject());
				String qnChoiceResult = choiceList.get(0).getQnChoiceResult();
				if ("text".equals(projectType) || "textarea".equals(projectType)) {
					lineJson.put("itemValue", qnChoiceResult);
				} else if ("drop_down_list".equals(projectType)) {
				//	lineJson.put("itemValue", SaafToolUtils.getCharIdx(qnChoiceResult));
				}
				lineJson.put("qnChoiceData", choiceList);
				jsonArray.add(lineJson);
			}
			json.put("lineList", jsonArray);
		} else {
			json.put("lineList", lineList);
		}
		return SToolUtils.convertResultJSONObj("S", "", 0, json);
	}

}
