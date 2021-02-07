package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.HtmlUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.dao.TtaQuestionHeaderDAO_HI;
import com.sie.watsons.base.questionnaire.model.dao.TtaTestQuestionChoiceLineDAO_HI;
import com.sie.watsons.base.questionnaire.model.dao.TtaTestQuestionHeaderDAO_HI;
import com.sie.watsons.base.questionnaire.model.dao.readonly.TtaQuestionChoiceLineDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.dao.readonly.TtaQuestionHeaderDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.dao.readonly.TtaTestQuestionChoiceLineDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.dao.readonly.TtaTestQuestionHeaderDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.*;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionChoiceLineEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionHeaderEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaTestQuestionChoiceLineEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaTestQuestionHeaderEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.IRule;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionChoiceLine;
import com.sie.watsons.base.questionnaire.model.inter.ITtaTestQuestionHeader;
import com.yhg.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("ttaQuestionChoiceLineServer")
public class TtaQuestionChoiceLineServer extends BaseCommonServer<TtaQuestionChoiceLineEntity_HI> implements ITtaQuestionChoiceLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionChoiceLineServer.class);

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private BaseCommonDAO_HI<TtaQuestionChoiceLineEntity_HI> ttaQuestionChoiceLineDAO_HI;
	
	@Autowired
	private TtaQuestionChoiceLineDAO_HI_RO ttaQuestionChoiceLineDAO_HI_RO;

	@Autowired
	private TtaQuestionHeaderDAO_HI_RO ttaQuestionHeaderDAOHiRo;
	
	@Autowired
	private TtaTestQuestionHeaderDAO_HI ttaTestQuestionHeaderDAO_HI;
	
	@Autowired
	private TtaQuestionHeaderDAO_HI ttaQuestionHeaderDAO_HI;

	@Autowired
	private TtaTestQuestionHeaderDAO_HI_RO ttaTestQuestionHeaderDAO_HI_RO;

	@Autowired
	private TtaTestQuestionChoiceLineDAO_HI_RO ttaTestQuestionChoiceLineDAO_HI_RO;

	@Autowired
	private IRule iRule;

	@Autowired
	private TtaTestQuestionChoiceLineDAO_HI ttaTestQuestionChoiceLineDAO_HI;

	@Autowired
	private BaseCommonDAO_HI<TtaQuestionNewMapDetailEntity_HI> ttaQuestionNewMapDetailDAO_HI;

	
	public TtaQuestionChoiceLineServer() {
		super();
	}

	@Override
	public void saveOrUpadateChoiceLineList(JSONObject jsonParams) {
		if (jsonParams != null && jsonParams.getJSONArray("lineArr") != null) {
			JSONArray lineArr = jsonParams.getJSONArray("lineArr");
			List<TtaQuestionChoiceLineEntity_HI> list = JSON.parseArray(lineArr.toJSONString(), TtaQuestionChoiceLineEntity_HI.class);
			Integer varUserId = jsonParams.getInteger("varUserId");
			for (TtaQuestionChoiceLineEntity_HI entity : list) {
				entity.setLastUpdatedBy(varUserId);
				entity.setCreatedBy(varUserId);
				entity.setOperatorUserId(varUserId);
				if (StringUtils.isBlank(entity.getChoiceEnContent())) {
					String code = generateCodeServer.getSequenceId("Q", 10);
					entity.setChoiceEnContent(code);
				}
			}
			ttaQuestionChoiceLineDAO_HI.saveOrUpdateAll(list);
		}
	}

	@Override
	public void saveOrUpdateChoiceLine(TtaQuestionChoiceLineEntity_HI entity) {
		ttaQuestionChoiceLineDAO_HI.saveOrUpdate(entity);
	}

	@Override
	public List<TtaQuestionChoiceLineEntity_HI_RO> findQuestionChoiceLineById(JSONObject queryParamJSON) {
		// TODO Auto-generated method stub
		Assert.notNull(queryParamJSON.getInteger("qHeaderId"), "问卷头id不能为空");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(TtaQuestionChoiceLineEntity_HI_RO.QUERY_BY_HEADER_ID);
		SaafToolUtils.parperParam(queryParamJSON, "t.q_header_id", "qHeaderId", sbSql, paramsMap, "=");
		return ttaQuestionChoiceLineDAO_HI_RO.findList(sbSql, paramsMap);
	}

	@Override
	public List<TtaQuestionChoiceLineEntity_HI_RO> findQuestionChoiceLineChild(JSONObject queryParamJSON) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(TtaQuestionChoiceLineEntity_HI_RO.QUERY_BY_HEADER_ID);
		SaafToolUtils.parperParam(queryParamJSON, "t.q_header_id", "qHeaderId", sbSql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.parent_id", "parentId", sbSql, paramsMap, "=");
		return ttaQuestionChoiceLineDAO_HI_RO.findList(sbSql, paramsMap);
	}

	/**
	 * 功能描述： 通过规则查询题目列表信息
	 * @date 2019/8/8
	 * @param
	 * @return
	 */
	@Override
	public List<TtaQuestionHeaderEntity_HI_RO> findRecQuestionHeaderList(JSONObject jsonObject) {
		//1.返回qHeaderId，choiceLineId, sqlValues
		List<Map<String, Object>> list = ttaQuestionChoiceLineDAO_HI.queryNamedSQLForList(TtaQuestionChoiceLineEntity_HI_RO.START_SQL, new HashMap<>());
		LinkedHashSet<Integer> qHeaderSet = new LinkedHashSet<>();
		this.findRecQheaderIds(list, qHeaderSet, jsonObject);
		LOGGER.info("递归查询后的结果qHeaderList :" + qHeaderSet);
		//1.题目排序
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(TtaQuestionHeaderEntity_HI_RO.QUERY_HEADER_SQL)
				.append(" and " + SaafToolUtils.buildLogicIN("t.q_header_id", new ArrayList(qHeaderSet)))
				.append(" order by t.serial_number asc ");
		List<TtaQuestionHeaderEntity_HI_RO> qHeaderList = ttaQuestionHeaderDAOHiRo.findList(sqlBuffer, new JSONObject().fluentPut("qHeaderList", qHeaderSet));
		if (qHeaderList != null) {
			sqlBuffer = new StringBuffer();
			sqlBuffer.append(TtaQuestionChoiceLineEntity_HI_RO.QUERY_BY_HEADER_ID).append(" and t.q_header_id = :qHeaderId and  nvl(t.parent_id,0) = 0 order by t.serial_number asc \t");
			for (int i = 0; i < qHeaderList.size(); i ++ ) {
				TtaQuestionHeaderEntity_HI_RO entityHiRo  = qHeaderList.get(i);
				Map<String, Object> queryMap = new HashMap<>();
				queryMap.put("qHeaderId",entityHiRo.getQHeaderId());
				List<TtaQuestionChoiceLineEntity_HI_RO> qnChoiceData = ttaQuestionChoiceLineDAO_HI_RO.findList(sqlBuffer.toString(), queryMap);
				//选项的下级题目
				if (qnChoiceData != null) {
					//自动计算的结果值，查询规则id
					setChoiceEntity(qnChoiceData, jsonObject);
					entityHiRo.setQnChoiceData(qnChoiceData);
					for (int idx = 0; idx < qnChoiceData.size(); idx ++){
						TtaQuestionChoiceLineEntity_HI_RO choiceLineEntityHiRo = qnChoiceData.get(idx);
						queryMap = new HashMap<>();
						queryMap.put("parentId", choiceLineEntityHiRo.getChoiceLineId());
						List<TtaQuestionChoiceLineEntity_HI_RO> choiceChildList = ttaQuestionChoiceLineDAO_HI_RO.findList(TtaQuestionChoiceLineEntity_HI_RO.QUERY_BY_HEADER_ID + " and  t.parent_id =:parentId and r.business_type = '1' order by t.serial_number asc ", queryMap);
						LOGGER.info("choiceParentId:{}, choiceChildList:{}" , choiceLineEntityHiRo.getChoiceLineId(), SaafToolUtils.toJson(choiceChildList));
						setChoiceEntity(choiceChildList, jsonObject);
						choiceLineEntityHiRo.setChoiceChildList(choiceChildList);
					}
				}
			}
		}
		return  qHeaderList;
	}

	@Override
	public void saveInitQuestion(List<TtaQuestionHeaderEntity_HI_RO> recQuestionHeaderList, Integer proposalId, String proposalYear) {
		if (recQuestionHeaderList == null) {
			return;
		}
		HashMap<String, Object> params = new HashMap<>();
		params.put("year", proposalYear);
		// 1.获取题目信息
		if (recQuestionHeaderList != null && !recQuestionHeaderList.isEmpty()) {
			//Date date = new Date();
			for (int idx = 0; idx < recQuestionHeaderList.size(); idx++) {
				TtaQuestionHeaderEntity_HI_RO questionHeader = recQuestionHeaderList.get(idx);
				TtaTestQuestionHeaderEntity_HI testHeader = JSON.parseObject(SaafToolUtils.toJson(questionHeader), TtaTestQuestionHeaderEntity_HI.class);
				testHeader.setSerialNumber(idx + 1);
				testHeader.setQHeaderId(null);
				testHeader.setSourceQHeaderId(questionHeader.getQHeaderId());
				testHeader.setProposalId(proposalId);
				testHeader.setStatus(0);
				testHeader.setVersionNum(null);
				String content = HtmlUtils.convertResult(testHeader.getProjectCnTitle(), params);
				testHeader.setProjectCnTitle(content);
				testHeader.setProjectEnTitle(HtmlUtils.convertResult(testHeader.getProjectEnTitle(), params));
				ttaTestQuestionHeaderDAO_HI.saveOrUpdate(testHeader);
				Integer testQheaderId = testHeader.getQHeaderId();
				List<TtaQuestionChoiceLineEntity_HI_RO> qnChoiceData = questionHeader.getQnChoiceData();
				for (TtaQuestionChoiceLineEntity_HI_RO choiceEntity : qnChoiceData) {
					if ("0".equalsIgnoreCase(choiceEntity.getIsShowStatus())) {//前端不显示的项目不显示
						continue;
					}
					Integer sourceChoiceLineId = choiceEntity.getChoiceLineId();
					String autoCalcResult = choiceEntity.getAutoCalcResult();
					TtaTestQuestionChoiceLineEntity_HI testChoice = JSON.parseObject(SaafToolUtils.toJson(choiceEntity), TtaTestQuestionChoiceLineEntity_HI.class);
					testChoice.setQHeaderId(testQheaderId);
					testChoice.setSourceChoiceLineId(sourceChoiceLineId);
					testChoice.setChoiceResult(autoCalcResult);
					testChoice.setVersionNum(null);
					testChoice.setStatus(0);
					testChoice.setRuleName(choiceEntity.getRuleName());
					testChoice.setProposalId(proposalId);
					content = HtmlUtils.convertResult(testChoice.getChoiceCnContent(), params);
					testChoice.setChoiceCnContent(content);
					testChoice.setChoiceEnContent(HtmlUtils.convertResult(testChoice.getChoiceEnContent(), params));
					testChoice.setIsShowStatus(choiceEntity.getIsShowStatus());
					ttaTestQuestionChoiceLineDAO_HI.saveOrUpdate(testChoice);
					Integer paretntChoiceId = testChoice.getChoiceLineId();
					StringBuffer sqlBuffer = new StringBuffer();
					sqlBuffer.append(TtaQuestionChoiceLineEntity_HI_RO.QUERY_BY_HEADER_ID ).append(" and t.parent_id=:sourceChoiceLineId ");
					List<TtaQuestionChoiceLineEntity_HI_RO> choiceList = ttaQuestionChoiceLineDAO_HI_RO.findList(sqlBuffer, new JSONObject().fluentPut("sourceChoiceLineId", sourceChoiceLineId));
					if (choiceList != null) {
						for (TtaQuestionChoiceLineEntity_HI_RO choiceLineEntity_hi : choiceList) {
							TtaTestQuestionChoiceLineEntity_HI testChoiceChild = JSON.parseObject(SaafToolUtils.toJson(choiceLineEntity_hi), TtaTestQuestionChoiceLineEntity_HI.class);
							testChoiceChild.setVersionNum(null);
							testChoiceChild.setStatus(0);
							testChoiceChild.setSourceChoiceLineId(choiceLineEntity_hi.getChoiceLineId());
							testChoiceChild.setParentId(paretntChoiceId);
							testChoiceChild.setRuleName(choiceLineEntity_hi.getRuleName());
							testChoiceChild.setProposalId(proposalId);
							testChoiceChild.setControlType(choiceLineEntity_hi.getControlType());
							testChoiceChild.setLookupType(choiceLineEntity_hi.getLookupType());
							content = HtmlUtils.convertResult(testChoiceChild.getChoiceCnContent(), params);
							testChoiceChild.setChoiceCnContent(content);
							testChoiceChild.setChoiceEnContent(testChoiceChild.getChoiceEnContent());
							ttaTestQuestionChoiceLineDAO_HI.saveOrUpdate(testChoiceChild);
						}
					}
				}
			}

		}
	}

	private void setChoiceEntity(List<TtaQuestionChoiceLineEntity_HI_RO> qnChoiceData, JSONObject jsonObject) {
		if (qnChoiceData == null || qnChoiceData.isEmpty()) {
			return;
		}
		for (TtaQuestionChoiceLineEntity_HI_RO choiceLineEntity : qnChoiceData) {
			//1：自动计算,2:文本,3:值,4:值+自动计算,5:值+文本
			if (StringUtils.isNotEmpty(choiceLineEntity.getChoiceType()) && "1,4".contains(choiceLineEntity.getChoiceType())){
				if (choiceLineEntity.getRuleId() == null) {
					continue;
				}
				//获取自动计算的值sql信息
				String autoCalcResult = findRuleValue(choiceLineEntity.getRuleId(), jsonObject);
				if (autoCalcResult == null) continue;
				choiceLineEntity.setAutoCalcResult(autoCalcResult);
			}
		}
	}

	public String findRuleValue(Integer ruleId, JSONObject jsonParams) {
		HashMap<String, Object> queryMap = new HashMap<>();
		RuleEntity_HI ruleEntity = iRule.getById(ruleId);
		if (ruleEntity == null) {
			return "";
		}
		String sqlValues = ruleEntity.getSqlValues();
		setParams(jsonParams, queryMap, sqlValues);
		LOGGER.info("参数值：queryMap:{}",queryMap);
		List<Map<String, Object>> ruleResultList = ttaQuestionChoiceLineDAO_HI.queryNamedSQLForList(sqlValues, queryMap);//执行规则sql
		if (ruleResultList == null || ruleResultList.isEmpty()) {
			return null;
		}
		String autoCalcResult = ruleResultList.get(0).get("VALUE") + "";
		return autoCalcResult;
	}

	private void setParams(JSONObject jsonParams, HashMap<String, Object> queryMap, String sqlValues) {
		Pattern compile = Pattern.compile(":\\w{1,}");
		Matcher matcher = compile.matcher(sqlValues);
		while(matcher.find()) {
			String mapKey = (matcher.group() + "").replace(":", "");
			Object mapValue = jsonParams.get(mapKey + "");
			LOGGER.info("参数值：mapKey:{}, mapValue:{}", mapKey, mapValue);
			queryMap.put(mapKey, mapValue);
		}
	}


	@SuppressWarnings("all")
	private void findRecQheaderIds(List<Map<String, Object>> sqlQheaderIdList, LinkedHashSet<Integer> qHeaderList, JSONObject jsonObject) {
		if (sqlQheaderIdList == null || sqlQheaderIdList.isEmpty()) {
			return;
		}
		for (Map<String, Object> map : sqlQheaderIdList) {
			String qHeaderId = map.get("qHeaderId") + "";
			String sqlValues = map.get("sqlValues") + "";
			String choiceLineId = map.get("choiceLineId") + "";
			qHeaderList.add(Integer.parseInt(qHeaderId));
			if (StringUtils.isBlank(sqlValues) || "null".equals(sqlValues)) {
				continue;
			}
			HashMap<String, Object> queryRuleMap = new HashMap<>();
			setParams(jsonObject, queryRuleMap, sqlValues);
			//执行规则中的sql
			List<Map<String, Object>> valueList = ttaQuestionChoiceLineDAO_HI.queryNamedSQLForList(sqlValues, queryRuleMap);
			if (valueList == null || valueList.isEmpty()) {
				continue;
			}
			//Assert.isTrue(valueList.size() > 1, "SQL查询的题目结果值必须唯一，请检查配置");
			String value = (valueList.get(0).get("VALUE") + "").trim(); //sql 取值
			//Assert.isTrue("Y/N".contains(value), "结果值必须为Y/N");
			if (!"Y".equalsIgnoreCase(value) && !"N".equalsIgnoreCase(value)) {
				continue;
			}
			HashMap<String, Object> queryMap = new HashMap<>();
			queryMap.put("resultValue", value);
			queryMap.put("qHeaderId", qHeaderId);
			queryMap.put("choiceLineId", choiceLineId);
			qHeaderList.add(Integer.parseInt(qHeaderId));
			//获取到问卷头部信息
			List<Map<String, Object>> qHeaderIdList = ttaQuestionChoiceLineDAO_HI.queryNamedSQLForList(TtaQuestionChoiceLineEntity_HI_RO.QUERY_HEADERIDS_SQL, queryMap);
			if (qHeaderIdList == null || qHeaderIdList.isEmpty()) {
				continue;
			}
			for (Map<String, Object> headerMap : qHeaderIdList) {
				HashMap<String, Object> query = new HashMap<>();
				qHeaderId = headerMap.get("qHeaderId") + "";
				query.put("qHeaderId", qHeaderId);
				qHeaderList.add(Integer.parseInt(qHeaderId));
				sqlQheaderIdList = ttaQuestionChoiceLineDAO_HI.queryNamedSQLForList(TtaQuestionChoiceLineEntity_HI_RO.VALUE_SQL_QHEADERID_SQL, query);// 回到标记
				findRecQheaderIds(sqlQheaderIdList, qHeaderList, jsonObject);
			}
		}
	}

	/**
	 * 功能描述：查询测试问卷
	 * @author xiaoga
	 * @date 2019/8/11
	 * @param  
	 * @return 
	 */
	@Override
	public List<TtaTestQuestionHeaderEntity_HI_RO> findTestQuestionList(JSONObject params) {
		Integer proposalId = params.getInteger("proposalId");
		StringBuffer buffer = new StringBuffer();
		buffer.append(TtaTestQuestionHeaderEntity_HI_RO.QUERY_TEST_HEADER_SQL + " \t  and t.proposal_id=:proposalId order by t.serial_number asc");
		List<TtaTestQuestionHeaderEntity_HI_RO> testQuestionHeaderDAO_hi_roList = ttaTestQuestionHeaderDAO_HI_RO.findList(buffer, new JSONObject().fluentPut("proposalId", proposalId));
		if (testQuestionHeaderDAO_hi_roList == null || testQuestionHeaderDAO_hi_roList.isEmpty()){
			return null;
		}
		StringBuffer sbsql = new StringBuffer();
		sbsql.append(TtaTestQuestionChoiceLineEntity_HI_RO.QUERY_BY_TEST_HEADER_ID);
		for (TtaTestQuestionHeaderEntity_HI_RO questionHeaderEntity : testQuestionHeaderDAO_hi_roList) {
			Integer qHeaderId = questionHeaderEntity.getQHeaderId();
			List<TtaTestQuestionChoiceLineEntity_HI_RO> testChoiceLineLiest = ttaTestQuestionChoiceLineDAO_HI_RO.findList(sbsql + " \t and t.q_header_id=:qHeaderId  order by t.serial_number asc ", new JSONObject().fluentPut("qHeaderId", qHeaderId));
			if (testChoiceLineLiest == null || testChoiceLineLiest.isEmpty()) {
				continue;
			}
			for (TtaTestQuestionChoiceLineEntity_HI_RO parentChoice :  testChoiceLineLiest) {
				List<TtaTestQuestionChoiceLineEntity_HI_RO> childTestChoiceLineLiest = ttaTestQuestionChoiceLineDAO_HI_RO.findList(sbsql + "\t and t.parent_id=:parentId order by t.serial_number asc ", new JSONObject().fluentPut("parentId", parentChoice.getChoiceLineId()));
				parentChoice.setChoiceChildList(childTestChoiceLineLiest);
			}
			questionHeaderEntity.setQnChoiceData(testChoiceLineLiest);
		}
		return testQuestionHeaderDAO_hi_roList;
	}


	@Override
	public String findQuestionEnglishRemark(Integer proposalId) {
		String result = "";
		JSONObject jsonParams = new JSONObject();
		jsonParams.put("proposalId", proposalId);
		StringBuffer content = new StringBuffer();
		Pattern pattern = Pattern.compile("&&\\w+&&");
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("proposalId", proposalId);
		List<TtaTestQuestionHeaderEntity_HI_RO> firstQuestionHeaderList = ttaTestQuestionHeaderDAO_HI_RO.findList(TtaTestQuestionHeaderEntity_HI_RO.QUERY_TEST_HEADER_SQL + " \t  and t.proposal_id=:proposalId order by t.serial_number asc", queryMap);
		List<TtaTestQuestionChoiceLineEntity_HI_RO> choiceList = ttaTestQuestionChoiceLineDAO_HI_RO.findList("select t.choice_result,t.choice_en_content  from tta_test_question_choice_line t where  t.choice_en_content is not null and t.proposal_id =:proposalId ", queryMap);
		for (TtaTestQuestionHeaderEntity_HI_RO headerEntity : firstQuestionHeaderList) {
			String projectEnTitle = (headerEntity.getProjectEnTitle() + "").trim().replace("\n", "");
			if (StringUtils.isBlank(projectEnTitle)) {
				continue;
			}
			Matcher matcher = pattern.matcher(projectEnTitle);
			while (matcher.find()) {
				String group = matcher.group();
				String idKey = group.substring(2, group.lastIndexOf("&&"));
				for (TtaTestQuestionChoiceLineEntity_HI_RO choiceEntity : choiceList) {
					if (idKey.equalsIgnoreCase(choiceEntity.getChoiceEnContent())) {
						String replaceStr = "@@";
						if (StringUtils.isNotEmpty(choiceEntity.getChoiceResult())) {
							replaceStr = choiceEntity.getChoiceResult().replaceAll("[\\[]|[\\]]|\"", "");
						}
						projectEnTitle = projectEnTitle.replace(group, replaceStr);
					}
				}
			}
			Pattern pt = Pattern.compile("[{][{].*?[}][}]");
			Matcher mc = pt.matcher(projectEnTitle);
			HashMap<String, Object> paramsMap = null;
			while (mc.find()) {
				paramsMap = new HashMap<>();
				String group = mc.group();
				String sqlValues = group.substring(2, group.lastIndexOf("}}"));
				HtmlUtils.setParams(jsonParams, paramsMap, sqlValues);
				LOGGER.info("参数值：queryMap:{}",queryMap);
				List<Map<String, Object>> resultList = ttaQuestionChoiceLineDAO_HI.queryNamedSQLForList(sqlValues, paramsMap);
				String resultValue = "@@";
				if (resultList != null && !resultList.isEmpty()) {
				    if (!(resultList.get(0).get("VALUE") + "").equalsIgnoreCase("null")) {
						resultValue = resultList.get(0).get("VALUE") + "";
					}
				}
				projectEnTitle = projectEnTitle.replace(group, resultValue);
			}
			if (projectEnTitle.contains("||")) {
				StringBuffer sb = new StringBuffer();
				String[] projectEnTitleArr = projectEnTitle.split("\\|\\|");
				for (String str :  projectEnTitleArr) {
					if (StringUtils.isBlank(str) || "null".equalsIgnoreCase(str) || (str + "").contains("@@")) {
						continue;
					}
					String lastChar = String.valueOf(str.charAt(str.length() - 1));
					if ("+".equalsIgnoreCase(lastChar)) {
						sb.append(str.substring(0, str.length() - 1));
					} else {
						sb.append(str + "\n");
					}
				}
				System.out.println(sb.length());
				if (sb.length() > 0 && sb.toString().endsWith("\n")) {
					projectEnTitle = sb.toString().substring(0, sb.toString().length() - 1);
				} else if (sb.length() > 0 && !sb.toString().endsWith("\n")) {
					projectEnTitle = sb.toString();
				} else {
					projectEnTitle = "";
				}
			}
			if (("" + projectEnTitle).contains("@@")) {
				continue;
			}
			if (StringUtils.isNotEmpty(projectEnTitle) && !"null".equalsIgnoreCase(projectEnTitle)) {
                content.append(projectEnTitle + "\n");
            }
		}
		LOGGER.info("输出英文描述如下:\r\n{}",content.toString());
		if (content.length() > 0) {
			result = content.toString().substring(0, content.toString().length() - 1).replace("\\n","\n"); //解析+||去除换行
		}
		return result;
	}

	/**
	 * 功能描述：
	 * @author xiaoga
	 * @date 2019/8/11
	 * @param
	 * @return
	 * {status:0,{choiceList[{qHeaderId
	 * :1，choiceLineId
	 * :2，choice_result:3},{qHeaderId
	 * :1，choiceLineId
	 * :2，choice_result:3}]}}
	 */
	@SuppressWarnings("all")
	@Override
	public void saveSubmitQuestion(JSONObject params, List<TtaQuestionNewMapDetailEntity_HI> newMapList) throws Exception{
		try {
			Integer proposalId = params.getInteger("proposalId");
			String status = params.getString("status");
			ttaQuestionHeaderDAO_HI.executeSqlUpdate(" update tta_test_question_header t set status = " + status + ", t.version_num=(t.version_num + 1) where t.proposal_id = " + proposalId);
			JSONArray jsonArry = params.getJSONArray("choiceList");
			Integer varUserId = params.getInteger("varUserId");
			for (TtaQuestionNewMapDetailEntity_HI entity : newMapList) {
				entity.setLastUpdatedBy(varUserId);
				entity.setCreatedBy(varUserId);
				entity.setOperatorUserId(varUserId);
			}
			ttaQuestionNewMapDetailDAO_HI.saveOrUpdateAll(newMapList);
			if ("2".contains(status)) {
				ttaQuestionHeaderDAO_HI.executeSqlUpdate("update tta_proposal_header set is_quest_confirm='Y' where proposal_id=" + proposalId);
				//return;
			}
			if (jsonArry == null || jsonArry.isEmpty()) {
				return;
			}
			for (int idx = 0; idx < jsonArry.size(); idx ++) {
				JSONObject jsonObject = jsonArry.getJSONObject(idx);
				Integer choiceLineId = jsonObject.getInteger("choiceLineId");
				String autoCalcResult = jsonObject.getString("autoCalcResult");
				String choiceType = jsonObject.getString("choiceType") + "";
				//1	自动计算, 4	值+自动计算
				if ("1,4".contains(choiceType)) {
					continue;
				}
				TtaTestQuestionChoiceLineEntity_HI entity = ttaTestQuestionChoiceLineDAO_HI.getById(choiceLineId);
				if (entity != null) {
					entity.setChoiceResult(autoCalcResult);
					ttaTestQuestionChoiceLineDAO_HI.saveOrUpdate(entity);
				}
			}
		} catch (Exception e) {
			LOGGER.error("提交问卷出错:{}", e);
			throw new Exception(e);
		}
	}

	@Override
	public void deleteCancelQuestionTest(Integer proposalId) {
		ttaQuestionHeaderDAO_HI.executeSqlUpdate("update tta_proposal_header set is_quest_confirm='N' where proposal_id=" + proposalId);
		ttaTestQuestionChoiceLineDAO_HI.executeSqlUpdate("delete from tta_test_question_choice_line t where t.proposal_id=" + proposalId);
		ttaTestQuestionChoiceLineDAO_HI.executeSqlUpdate("delete from tta_test_question_header t where t.proposal_id =" + proposalId);
		//清空 remark字段
		ttaTestQuestionChoiceLineDAO_HI.executeSqlUpdate("update tta_analysis_edit_data t set t.bic_remark = null where t.proposalid =" + + proposalId);
		//ttaTestQuestionChoiceLineDAO_HI.executeSqlUpdate("delete from tta_question_new_map_detail t where t.proposal_id=" + proposalId);
		//ttaTestQuestionChoiceLineDAO_HI.executeSqlUpdate("update base_attachment t set delete_flag=1 where t.business_id=" + proposalId + " and t.function_id = 'tta_proposal_header'");
	}


}
