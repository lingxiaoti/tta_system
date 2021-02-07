package com.sie.watsons.base.pon.scoring.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonScoringTeamEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProductSpecsEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonSupplierInvitationEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.inter.server.EquPonSupplierInvitationServer;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationInfoEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.server.EquPonQuotationInfoServer;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringDetailEntity_HI;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.watsons.base.pon.scoring.model.entities.readonly.EquPonScoringInfoEntity_HI_RO;
import com.sie.watsons.base.pon.scoring.model.inter.IEquPonScoringInfo;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("equPonScoringInfoServer")
public class EquPonScoringInfoServer extends BaseCommonServer<EquPonScoringInfoEntity_HI> implements IEquPonScoringInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonScoringInfoServer.class);

	@Autowired
	private ViewObject<EquPonScoringInfoEntity_HI> equPonScoringInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPonScoringInfoEntity_HI_RO> equPonScoringInfoEntity_HI_RO;

	@Autowired
	private EquPonScoringDetailServer equPonScoringDetailServer;

	@Autowired
	private ViewObject<EquPonScoringDetailEntity_HI> equPonScoringDetailDAO_HI;

	@Autowired
	private EquPonQuotationInfoServer equPonQuotationInfoServer;

	@Autowired
	private EquPonSupplierInvitationServer equPonSupplierInvitationServer;

	@Autowired
	private ViewObject<EquPonQuotationInformationEntity_HI> equPonQuotationInformationDAO_HI;

	@Autowired
	private ViewObject<EquPonProjectInfoEntity_HI> equPonProjectInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInfoEntity_HI> equPonQuotationInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPonProductSpecsEntity_HI_RO> equPonProductSpecsEntity_HI_RO;

	@Autowired
	private BaseViewObject<EquPonSupplierInvitationEntity_HI_RO> equPonSupplierInvitationEntity_HI_RO;

	@Autowired
	private ViewObject<EquPonSupplierInvitationEntity_HI> equPonSupplierInvitationDao;

	@Autowired
	private ViewObject<EquPonScoringTeamEntity_HI> equPonScoringTeamDAO_HI;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private IFastdfs fastDfsServer;

	public EquPonScoringInfoServer() {
		super();
	}

	/**
	 * 报价管理-评分单据查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findScoringInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		String deptCode = queryParamJSON.getString("deptCode");
//		if(!"03".equals(deptCode)){
//			queryParamJSON.put("deptCode","0E");
//		}
		Integer varUserId = queryParamJSON.getInteger("varUserId");
		String varEmployeeNumber = queryParamJSON.getString("varEmployeeNumber");

//		StringBuffer sql = new StringBuffer(EquPonScoringInfoEntity_HI_RO.QUERY_SQL);

		StringBuffer sql = null;

		if(queryParamJSON.get("lovFlag")!=null&&"Y".equals(queryParamJSON.getString("lovFlag"))){
			sql = new StringBuffer(EquPonScoringInfoEntity_HI_RO.QUERY_SQL2);
		}else{
			sql = new StringBuffer(EquPonScoringInfoEntity_HI_RO.QUERY_SQL);
		}

		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonScoringInfoEntity_HI_RO.class, queryParamJSON, sql, map);
//		如果是报价结果审批查询lov就不要有重复数据  "searchType" -> "quotationApproval"
		if(queryParamJSON.get("searchType")!=null&&"quotationApproval".equals(queryParamJSON.getString("searchType"))){
			sql.append(" AND NOT EXISTS ( SELECT 1 FROM EQU_PON_QUOTATION_APPROVAL PQA WHERE PQA.scoring_id = T.SCORING_ID AND PQA.APPROVAL_STATUS <> '50' ) ");
		}
//		if(null == queryParamJSON.getString("validate")){
//			sql.append(" and (t.created_by = " + varUserId + " or st.member_number = '" + varEmployeeNumber + "')");
//		}



		sql.append(" order by t.creation_date desc");
		Pagination<EquPonScoringInfoEntity_HI_RO> pagination = equPonScoringInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-评分单据查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findScoringInfoByDeptCode(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPonScoringInfoEntity_HI_RO.QUERY_SQL_ALL);
//		String deptCode = queryParamJSON.getString("deptCode");
//		if(!"03".equals(deptCode) && !"0E".equals(deptCode)){
//			queryParamJSON.remove("deptCode");
//		}
		queryParamJSON.remove("deptCode");
		Integer varUserId = queryParamJSON.getInteger("varUserId");
		String varEmployeeNumber = queryParamJSON.getString("varEmployeeNumber");

		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonScoringInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" and (k.created_by = " + varUserId + " or k.member_number = '" + varEmployeeNumber + "')");
		sql.append(" order by k.creation_date desc");
		Pagination<EquPonScoringInfoEntity_HI_RO> pagination = equPonScoringInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public JSONObject findScoringInfoForFlow(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPonScoringInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonScoringInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonScoringInfoEntity_HI_RO> pagination = equPonScoringInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-立项单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonScoringInfoEntity_HI saveScoringInfo(JSONObject params) throws Exception {
		JSONObject scoringHeaderInfo = getParamsJSONObject(params,params.getJSONObject("scoringHeaderInfo"));
		JSONArray scoringDetailInfoArray = params.getJSONArray("scoringDetailInfo") == null ? new JSONArray() : params.getJSONArray("scoringDetailInfo");
		JSONArray invitationSupplierInfoArray = params.getJSONArray("invitationSupplierInfo") == null ? new JSONArray() : params.getJSONArray("invitationSupplierInfo");

		EquPonScoringInfoEntity_HI scoringEntity = null;
		//保存评分单据头信息
		if(scoringHeaderInfo.containsKey("scoringId")){
			//修改保存
			scoringEntity = this.saveOrUpdate(scoringHeaderInfo);
		}else{
			//新增保存
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String currentDate = format.format(new Date());
			String prefix = "PF-" + currentDate;
			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
			String scoringNumber = prefix + "-" + sequenceId;
			scoringHeaderInfo.put("scoringNumber", scoringNumber);
			scoringHeaderInfo.put("scoringStatus","10");
			scoringEntity = this.saveOrUpdate(scoringHeaderInfo);
		}

		String supplierConfirmFlag = scoringEntity.getSupplierConfirmFlag();
		if("Y".equals(supplierConfirmFlag)){
			//保存评分单据详情信息
			for(int i = 0; i < scoringDetailInfoArray.size(); i++){
				JSONObject scoringDetailInfo = getParamsJSONObject(params,scoringDetailInfoArray.getJSONObject(i));
				Object scoringDetailId = scoringDetailInfo.get("scoringDetailId");

				if(scoringDetailInfo.containsKey("scoringDetailId") && !"".equals(scoringDetailId)){
					//修改保存
					equPonScoringDetailServer.saveOrUpdate(scoringDetailInfo);
				}else{
					//新增保存
					scoringDetailInfo.put("scoringId",scoringEntity.getScoringId());
					equPonScoringDetailServer.saveOrUpdate(scoringDetailInfo);
				}
			}

			//保存邀请供应商列表信息
			for(int i = 0; i < invitationSupplierInfoArray.size(); i++){
				JSONObject invitationSupplierInfo = getParamsJSONObject(params,invitationSupplierInfoArray.getJSONObject(i));
				equPonSupplierInvitationServer.saveOrUpdate(invitationSupplierInfo);
			}
		}
		return scoringEntity;
	}

	/**
	 * 报价管理-评分单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public Integer deleteScoringInfo(JSONObject params) throws Exception {
		String scoringStatus = params.getString("scoringStatus");
		String scoringNumber = params.getString("scoringNumber");
		this.deleteById(params.getInteger("id"));
		if("40".equals(scoringStatus)){
			//查询流程信息，提取流程id
			JSONObject b = new JSONObject();
			b.put("code", scoringNumber);
			JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
			//根据流程id，终止流程
			Integer listId = resultJSON.getInteger("listId");
			return listId;
		}
		return null;
	}

	/**
	 * 报价管理-评分单据终止
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void terminateScoringInfo(JSONObject params) throws Exception {
		String terminateFlag = params.getString("terminateFlag");
		EquPonScoringInfoEntity_HI entity = this.getById(params.getInteger("scoringId"));
		entity.setTerminateFlag(terminateFlag);
		entity.setOperatorUserId(params.getInteger("varUserId"));
	}

	public BigDecimal emptyToZero(BigDecimal b){
		if(null == b){
			return new BigDecimal(0);
		}
		return b;
	}

	/**
	 * 报价管理-评分单据非价格计算
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void saveNonPriceCalculate(JSONObject params) throws Exception {
		Integer scoringId = params.getInteger("scoringId");
		Integer projectId = params.getInteger("projectId");

		//查找评分单据
		EquPonScoringInfoEntity_HI scoringEntity = this.getById(scoringId);
		scoringEntity.setNonPriceCalculateFlag("Y");

		//查找立项单据
		List<EquPonProjectInfoEntity_HI> projectList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
		EquPonProjectInfoEntity_HI projectEntity = projectList.get(0);
		String sensoryTestTypes = projectEntity.getSensoryTestTypes();
		if(!"10".equals(sensoryTestTypes)){
			//1.查找panel test分数计算行
			Map queryMap = new HashMap();
			queryMap.put("scoringType","panelTestCalculation");
			queryMap.put("scoringId",scoringId);
			List<EquPonScoringDetailEntity_HI> detailCalList = equPonScoringDetailDAO_HI.findByProperty(queryMap);

			//2.查找panel test分数汇总行
			Map queryMap2 = new HashMap();
			queryMap2.put("scoringType","panelTestTotalScore");
			queryMap2.put("scoringId",scoringId);
			List<EquPonScoringDetailEntity_HI> detailTotalList = equPonScoringDetailDAO_HI.findByProperty(queryMap2);

			//3.查找panel test分数填写行
			Map queryMap3 = new HashMap();
			queryMap3.put("scoringType","panelTest");
			queryMap3.put("scoringId",scoringId);
			List<EquPonScoringDetailEntity_HI> detailFillList = equPonScoringDetailDAO_HI.findByProperty(queryMap3);
			Map<Integer,Float> resultMap = new HashMap();
			for(int i = 0; i < detailCalList.size(); i++){
				EquPonScoringDetailEntity_HI calEntity = detailCalList.get(i);
				String productName = calEntity.getScoringItem();
				Integer supplierId = calEntity.getSupplierId();
				BigDecimal participation = emptyToZero(decimalConvern(calEntity.getParticipation()));

				EquPonScoringDetailEntity_HI fillEntity = findFillEntity(productName,supplierId,detailFillList);

				if(fillEntity != null){
					BigDecimal fillScore = emptyToZero(fillEntity.getScore());
					BigDecimal highestScore = emptyToZero(fillEntity.getHighestScore());
					BigDecimal benchMark = emptyToZero(fillEntity.getBenchMark());

					float a = fillScore.floatValue() / highestScore.floatValue();
					float b = a * 100 * participation.floatValue();
					float score = (float)(Math.round(b*100))/100;

					calEntity.setScore(new BigDecimal(score));
					calEntity.setBenchMark(benchMark);

					if(resultMap.containsKey(supplierId)){
						Float value = resultMap.get(supplierId);
						resultMap.put(supplierId,value.floatValue() + score);
					}else{
						resultMap.put(supplierId,score);
					}
				}
			}

			for(int i = 0; i < detailTotalList.size(); i++){
				EquPonScoringDetailEntity_HI totalEntity = detailTotalList.get(i);
				Integer supplierId = totalEntity.getSupplierId();
				Float totalScore = resultMap.get(supplierId);
				totalEntity.setScore(new BigDecimal(totalScore));
			}
		}

		//非价格资料结果计算
		//1.查找非价格资料计算行
		Map queryMap4 = new HashMap();
		queryMap4.put("scoringType","nonPriceCal");
		queryMap4.put("scoringId",scoringId);
		queryMap4.put("lineLv",new BigDecimal(2));
		List<EquPonScoringDetailEntity_HI> nonPriceCalList = equPonScoringDetailDAO_HI.findByProperty(queryMap4);

		//2.查找所有价格资料相关的行
		Map queryMap5 = new HashMap();
		queryMap5.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> dataList = equPonScoringDetailDAO_HI.findByProperty(queryMap5);

		//3.查找非价格资料计算汇总
		Map queryMap6 = new HashMap();
		queryMap6.put("scoringType","nonPriceCalTotalScore");
		queryMap6.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> nonPriceCalTotalList = equPonScoringDetailDAO_HI.findByProperty(queryMap6);

		//3.查找非价格资料计算排名行
		Map queryMap7 = new HashMap();
		queryMap7.put("scoringType","nonPriceCalRanking");
		queryMap7.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> nonPriceCalRankingList = equPonScoringDetailDAO_HI.findByProperty(queryMap7);
		Map<Integer,Float> nonPriceResultMap = new HashMap();

		//3.查找报价总分行
		Map queryMap8 = new HashMap();
		queryMap8.put("scoringType","quotationTotalResult");
		queryMap8.put("scoringItem","totalScore");
		queryMap8.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> quotationTotalScoreList = equPonScoringDetailDAO_HI.findByProperty(queryMap8);

		//3.查找报价总排名
		Map queryMap9 = new HashMap();
		queryMap9.put("scoringType","quotationTotalResult");
		queryMap8.put("scoringItem","totalRanking");
		queryMap9.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> quotationTotalRankingList = equPonScoringDetailDAO_HI.findByProperty(queryMap9);

		Float result = 0.0f;
		for(int i = 0; i < nonPriceCalList.size(); i++){
			EquPonScoringDetailEntity_HI nonPriceCalEntity = nonPriceCalList.get(i);
			BigDecimal weight = emptyToZero(nonPriceCalEntity.getWeighting());

			String scoringType = "";
			String scoringItem = "";
			Integer supplierId = nonPriceCalEntity.getSupplierId();

			if("Cost".equals(nonPriceCalEntity.getScoringItem())){
				continue;
			}else if("Panel test".equals(nonPriceCalEntity.getScoringItem())){
				if("10".equals(sensoryTestTypes)){
					continue;
				}
				scoringType = "panelTestTotalScore";
				scoringItem = "totalScore";
			}else if("Factory audit".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "factoryAudit";
				scoringItem = "defaultItem";
			}else if("Innovation".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "newConceptRromSupplier";
				scoringItem = "totalScore";
			}else if("PKG Innovation".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "componentSupport";
				scoringItem = "totalScore";
			}else if("Effective & accurate feedback".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "effectiveAccurateFeedback";
				scoringItem = "defaultItem";
			}else if("Payment terms".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "paymentTerms";
				scoringItem = "paymentTerms";
			}else if("Current supplier performance".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "currentSupplierPerformance";
				scoringItem = "defaultItem";
			}else if("Commercial contract".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "commercialContract";
				scoringItem = "defaultItem";
			}else if("Supplier spend profile".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "supplierSpendProfile";
				scoringItem = "defaultItem";
			}

			EquPonScoringDetailEntity_HI targetEntity = getCalculateEntity(scoringType,scoringItem,supplierId,dataList);
			BigDecimal targetScore = emptyToZero(targetEntity.getScore());
			String scoringType2 = targetEntity.getScoringType();
			if(targetEntity != null){
//				result = targetScore.floatValue() * weight.floatValue() / 100;
				if("panelTestTotalScore".equals(scoringType2)){
					result = targetScore.floatValue() * weight.floatValue() / 100;
				}else{
					result = targetScore.floatValue();
				}
				float f = (float)(Math.round(result*100))/100;
				nonPriceCalEntity.setScore(new BigDecimal(f));

				if(nonPriceResultMap.containsKey(supplierId)){
					Float value = nonPriceResultMap.get(supplierId);
					nonPriceResultMap.put(supplierId,value.floatValue() + f);
				}else{
					nonPriceResultMap.put(supplierId,f);
				}
			}
		}
        //非价格资料总分
		for(int i = 0; i < nonPriceCalTotalList.size(); i++){
			EquPonScoringDetailEntity_HI totalEntity = nonPriceCalTotalList.get(i);
			Integer supplierId = totalEntity.getSupplierId();
			Float totalScore = nonPriceResultMap.get(supplierId);
			totalEntity.setScore(new BigDecimal(totalScore));
		}

		//报价总分
		for(int i = 0; i < quotationTotalScoreList.size(); i++){
			EquPonScoringDetailEntity_HI quotationTotalScoreEntity = quotationTotalScoreList.get(i);
			Integer supplierId = quotationTotalScoreEntity.getSupplierId();
			Float totalScore = nonPriceResultMap.get(supplierId);
			quotationTotalScoreEntity.setScore(new BigDecimal(totalScore));
		}

		//排序
		List<Map.Entry<Integer, Float>> list = new ArrayList<Map.Entry<Integer, Float>>(nonPriceResultMap.entrySet()); //转换为list
		list.sort(new Comparator<Map.Entry<Integer, Float>>() {
			@Override
			public int compare(Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		//非价格资料排名
		for (int i = 0; i < list.size(); i++) {
			for(int j = 0; j < nonPriceCalRankingList.size(); j++){
				EquPonScoringDetailEntity_HI rankingEntity = nonPriceCalRankingList.get(j);
				if(nonPriceCalRankingList.get(j).getSupplierId().intValue() == list.get(i).getKey().intValue()){
					rankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}

		//报价总排名
		for (int i = 0; i < list.size(); i++) {
			for(int j = 0; j < quotationTotalRankingList.size(); j++){
				EquPonScoringDetailEntity_HI quotationTotalRankingEntity = quotationTotalRankingList.get(j);
				if(quotationTotalRankingList.get(j).getSupplierId().intValue() == list.get(i).getKey().intValue()){
					quotationTotalRankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}
	}

	/**
	 * 报价管理-报价总分计算
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void saveQuotationScoreCalculate(JSONObject params) throws Exception {
		Integer scoringId = params.getInteger("scoringId");
		Integer projectId = params.getInteger("projectId");
		String projectNumber = params.getString("projectNumber");
		String sensoryTestTypes = params.getString("sensoryTestTypes");
		String confirmFlag = params.getString("confirmFlag");

		Map<Integer,Float> resultMap = new HashMap();

		//查询立项单据信息
		List<EquPonProjectInfoEntity_HI> projectEntityList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
		String sourceProjectNumber = projectEntityList.get(0).getSourceProjectNumber();
		List<EquPonProjectInfoEntity_HI> allVersionProjectEntityList = equPonProjectInfoDAO_HI.findByProperty("sourceProjectNumber",sourceProjectNumber);
		List<EquPonQuotationInfoEntity_HI_RO> quotationDetailList = null;
		//根据立项单据号，查询供应商报价行
		for(int k = 0; k < allVersionProjectEntityList.size(); k++){
			EquPonProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);
			JSONObject queryJson = new JSONObject();
			queryJson.put("projectId",projectEntity.getProjectId());
			queryJson.put("projectNumber",projectEntity.getProjectNumber());
			quotationDetailList = equPonQuotationInfoServer.findQuotationDetails(queryJson);
			if(quotationDetailList.size() > 0){
				//校验报价单状态是否已完成
				for(int i = 0; i < quotationDetailList.size(); i++){
					EquPonQuotationInfoEntity_HI_RO row = quotationDetailList.get(i);
					if(!"BREAK".equals(row.getDocStatus())){
						if(!"COMPLETE".equals(row.getDocStatus())){
							throw new Exception("供应商【" + row.getSupplierName() + "】存在没有完成的报价单" + row.getQuotationNumber() + "，不能计算总分！");
						}
					}
				}
				break;
			}
		}

//		if(null == quotationDetailList || quotationDetailList.size() == 0){
//			throw new Exception("查询不到相关报价单记录!");
//		}


		if("10".equals(sensoryTestTypes)){
			//1.查找panel test分数计算行
			Map queryMap = new HashMap();
			queryMap.put("scoringType","panelTestCalculation");
			queryMap.put("scoringId",scoringId);
			List<EquPonScoringDetailEntity_HI> detailCalList = equPonScoringDetailDAO_HI.findByProperty(queryMap);

			//2.查找panel test分数汇总行
			Map queryMap2 = new HashMap();
			queryMap2.put("scoringType","panelTestTotalScore");
			queryMap2.put("scoringId",scoringId);
			List<EquPonScoringDetailEntity_HI> detailTotalList = equPonScoringDetailDAO_HI.findByProperty(queryMap2);

			//3.查找panel test分数填写行
			Map queryMap3 = new HashMap();
			queryMap3.put("scoringType","panelTest");
			queryMap3.put("scoringId",scoringId);
			List<EquPonScoringDetailEntity_HI> detailFillList = equPonScoringDetailDAO_HI.findByProperty(queryMap3);
			Map<Integer,Float> resultMap2 = new HashMap();
			for(int i = 0; i < detailCalList.size(); i++){
				EquPonScoringDetailEntity_HI calEntity = detailCalList.get(i);
				String productName = calEntity.getScoringItem();
				Integer supplierId = calEntity.getSupplierId();
				BigDecimal participation = emptyToZero(decimalConvern(calEntity.getParticipation()));

				EquPonScoringDetailEntity_HI fillEntity = findFillEntity(productName,supplierId,detailFillList);

				if(fillEntity != null){
					BigDecimal fillScore = emptyToZero(fillEntity.getScore());
					BigDecimal highestScore = emptyToZero(fillEntity.getHighestScore());
					BigDecimal benchMark = emptyToZero(fillEntity.getBenchMark());

					float a = fillScore.floatValue() / highestScore.floatValue();
					float b = a * 100 * participation.floatValue();
					float score = (float)(Math.round(b*100))/100;

					calEntity.setScore(new BigDecimal(score));
					calEntity.setBenchMark(benchMark);

					if(resultMap2.containsKey(supplierId)){
						Float value = resultMap2.get(supplierId);
						resultMap2.put(supplierId,value.floatValue() + score);
					}else{
						resultMap2.put(supplierId,score);
					}
				}
			}

			for(int i = 0; i < detailTotalList.size(); i++){
				EquPonScoringDetailEntity_HI totalEntity = detailTotalList.get(i);
				Integer supplierId = totalEntity.getSupplierId();
				Float totalScore = resultMap2.get(supplierId);
				totalEntity.setScore(new BigDecimal(totalScore));
			}
		}

		//Cost-查找供应商报价信息
		Map queryMap1 = new HashMap();
		queryMap1.put("scoringType","costQuotation");
		queryMap1.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> costQuotationList = equPonScoringDetailDAO_HI.findByProperty(queryMap1);

		//Cost-查找供应商采购额信息
		Map queryMap2 = new HashMap();
		queryMap2.put("scoringType","costScoring");
		queryMap2.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> costScoringList = equPonScoringDetailDAO_HI.findByProperty(queryMap2);

		//Cost-查找供应商采购总额信息
		Map queryMap3 = new HashMap();
		queryMap3.put("scoringType","costTotalCost");
		queryMap3.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> costTotalCostList = equPonScoringDetailDAO_HI.findByProperty(queryMap3);

		//Cost-查找供应商总分信息
		Map queryMap4 = new HashMap();
		queryMap4.put("scoringType","costTotalScore");
		queryMap4.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> costTotalScoreList = equPonScoringDetailDAO_HI.findByProperty(queryMap4);

		//cost-计算供应商报价
		for(int i = 0; i < quotationDetailList.size(); i++){
			EquPonQuotationInfoEntity_HI_RO quotationRow = quotationDetailList.get(i);
			for(int j = 0; j < costQuotationList.size(); j++){
				EquPonScoringDetailEntity_HI costQuotationEntity = costQuotationList.get(j);
				if(costQuotationEntity.getSupplierId().intValue() == quotationRow.getSupplierId()){
					if(quotationRow.getProductName().equals(costQuotationEntity.getScoringItem())){
						costQuotationEntity.setQuotationPrice(quotationRow.getFirstProductPrice());
					}
				}
			}
		}

		//cost-计算供应商采购额
		for(int i = 0; i < costQuotationList.size(); i++){
			EquPonScoringDetailEntity_HI costQuotationEntity = costQuotationList.get(i);
			for(int j = 0; j < costScoringList.size(); j++){
				EquPonScoringDetailEntity_HI costScoringEntity = costScoringList.get(j);
				if(costQuotationEntity.getSupplierId().intValue() == costScoringEntity.getSupplierId()){
					if(costQuotationEntity.getScoringItem().equals(costScoringEntity.getScoringItem())){
						System.out.println("getQuotationPrice: " + costQuotationEntity.getQuotationPrice());
						System.out.println("getAnnualSalesQuantity: " + costQuotationEntity.getAnnualSalesQuantity());
						float t = costQuotationEntity.getQuotationPrice().floatValue() * costQuotationEntity.getAnnualSalesQuantity().floatValue();
						float f = (float)(Math.round(t*100))/100;
						costScoringEntity.setPurchaseVolume(new BigDecimal(f));

						Integer supplierId = costScoringEntity.getSupplierId();
						if(resultMap.containsKey(supplierId)){
							Float value = resultMap.get(supplierId);
							resultMap.put(supplierId,value.floatValue() + f);
						}else{
							resultMap.put(supplierId,f);
						}
					}
				}
			}
		}

		//cost-计算供应商采购总额
		for(int i = 0; i < costTotalCostList.size(); i++){
			EquPonScoringDetailEntity_HI costTotalCostEntity = costTotalCostList.get(i);
			Integer supplierId = costTotalCostEntity.getSupplierId();
			float t = resultMap.get(supplierId).floatValue();
			float f = (float)(Math.round(t*100))/100;
			costTotalCostEntity.setPurchaseVolume(new BigDecimal(f));
		}

		//排序
		List<Map.Entry<Integer, Float>> list = new ArrayList<Map.Entry<Integer, Float>>(resultMap.entrySet()); //转换为list
		list.sort(new Comparator<Map.Entry<Integer, Float>>() {
			@Override
			public int compare(Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		float minPurchaseAmount = list.get(0).getValue().floatValue();

		//cost-计算供应商分数
		for (int i = 0; i < costTotalScoreList.size(); i++) {
			EquPonScoringDetailEntity_HI costTotalScoreEntity = costTotalScoreList.get(i);
			Integer supplierId = costTotalScoreEntity.getSupplierId();
			float t = resultMap.get(supplierId).floatValue();
			float a = minPurchaseAmount / t * 100;
			float f = (float)(Math.round(a*100))/100;
			costTotalScoreEntity.setScore(new BigDecimal(f));
		}

		//*************************************************************************//
		Map<Integer,Float> nonPriceResultMap = new HashMap<Integer,Float>();
		//1.查找非价格资料计算行
		Map queryMap5 = new HashMap();
		queryMap5.put("scoringType","nonPriceCal");
		queryMap5.put("scoringId",scoringId);
		queryMap5.put("lineLv",new BigDecimal(2));
		List<EquPonScoringDetailEntity_HI> nonPriceCalList = equPonScoringDetailDAO_HI.findByProperty(queryMap5);

		//2.查找所有价格资料相关的行
		Map queryMap6 = new HashMap();
		queryMap6.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> dataList = equPonScoringDetailDAO_HI.findByProperty(queryMap6);

		//3.查找价格资料计算汇总
		Map queryMap7 = new HashMap();
		queryMap7.put("scoringType","quotationTotalResult");
		queryMap7.put("scoringItem","totalScore");
		queryMap7.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> quotationCalTotalScoreList = equPonScoringDetailDAO_HI.findByProperty(queryMap7);

		//4.查找价格资料计算排名行
		Map queryMap8 = new HashMap();
		queryMap8.put("scoringType","quotationTotalResult");
		queryMap8.put("scoringItem","totalRanking");
		queryMap8.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> quotationCalTotalRankingList = equPonScoringDetailDAO_HI.findByProperty(queryMap8);

		//5.查找非价格资料计算汇总
		Map queryMap10 = new HashMap();
		queryMap10.put("scoringType","nonPriceCalTotalScore");
		queryMap10.put("scoringItem","totalScore");
		queryMap10.put("scoringId",scoringId);
		List<EquPonScoringDetailEntity_HI> nonQuotationCalTotalScoreList = equPonScoringDetailDAO_HI.findByProperty(queryMap10);

		Float result = 0.0f;
		for(int i = 0; i < nonPriceCalList.size(); i++) {
			EquPonScoringDetailEntity_HI nonPriceCalEntity = nonPriceCalList.get(i);
			BigDecimal weight = emptyToZero(nonPriceCalEntity.getWeighting());

			String scoringType = "";
			String scoringItem = "";
			Integer supplierId = nonPriceCalEntity.getSupplierId();

			if("Cost".equals(nonPriceCalEntity.getScoringItem())){
				scoringType = "costTotalScore";
				scoringItem = "totalScore";
			}else if("Panel test".equals(nonPriceCalEntity.getScoringItem())){
				if("20".equals(sensoryTestTypes)){
					continue;
				}
				scoringType = "panelTestTotalScore";
				scoringItem = "totalScore";
			}else{
				continue;
			}

			EquPonScoringDetailEntity_HI targetEntity = getCalculateEntity(scoringType,scoringItem,supplierId,dataList);

			if(targetEntity != null){
				result = targetEntity.getScore().floatValue() * weight.floatValue() / 100;
				float f = (float)(Math.round(result*100))/100;
				nonPriceCalEntity.setScore(new BigDecimal(f));

				if(nonPriceResultMap.containsKey(supplierId)){
					Float value = nonPriceResultMap.get(supplierId);
					nonPriceResultMap.put(supplierId,value.floatValue() + f);
				}else{
					nonPriceResultMap.put(supplierId,f);
				}
			}
		}

		for(int i = 0; i < nonQuotationCalTotalScoreList.size(); i++){
			float totalScore = 0;
			EquPonScoringDetailEntity_HI nonTotalEntity = nonQuotationCalTotalScoreList.get(i);
			Integer supplierId = nonTotalEntity.getSupplierId();
			float t = emptyToZero(nonTotalEntity.getScore()).floatValue();
			totalScore = nonPriceResultMap.get(supplierId) + t;
			//totalEntity.setScore(new BigDecimal(totalScore));
			for(int j = 0; j < quotationCalTotalScoreList.size(); j++){
				EquPonScoringDetailEntity_HI totalEntity = quotationCalTotalScoreList.get(j);
				if(totalEntity.getSupplierId().intValue() == supplierId.intValue()){
					totalEntity.setScore(new BigDecimal(totalScore));
				}
			}
			nonPriceResultMap.put(supplierId,totalScore);
		}

		//排序
		List<Map.Entry<Integer, Float>> list2 = new ArrayList<Map.Entry<Integer, Float>>(nonPriceResultMap.entrySet()); //转换为list
		list2.sort(new Comparator<Map.Entry<Integer, Float>>() {
			@Override
			public int compare(Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		for (int i = 0; i < list2.size(); i++) {
			for(int j = 0; j < quotationCalTotalRankingList.size(); j++){
				EquPonScoringDetailEntity_HI rankingEntity = quotationCalTotalRankingList.get(j);
				if(quotationCalTotalRankingList.get(j).getSupplierId().intValue() == list2.get(i).getKey().intValue()){
					rankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}

		//修改评分单据状态为已完成
		if("Y".equals(confirmFlag)){
			List<EquPonScoringInfoEntity_HI> equPonScoringInfoEntityList = equPonScoringInfoDAO_HI.findByProperty("scoringId",scoringId);
			EquPonScoringInfoEntity_HI equPonScoringInfoEntity = equPonScoringInfoEntityList.get(0);
			equPonScoringInfoEntity.setScoringStatus("60");
		}
	}

	public EquPonScoringDetailEntity_HI getCalculateEntity(String scoringType,String scoringItem,Integer supplierId,List<EquPonScoringDetailEntity_HI> entityList){
		for(int i = 0; i < entityList.size(); i++){
			EquPonScoringDetailEntity_HI entity = entityList.get(i);
			if(supplierId.intValue() == entity.getSupplierId().intValue()){
				if(scoringType.equals(entity.getScoringType())){
					if(scoringItem.equals(entity.getScoringItem())){
						return entity;
					}
				}
			}
		}
		return null;
	}

	public EquPonScoringDetailEntity_HI findFillEntity(String productName,Integer supplierId,List<EquPonScoringDetailEntity_HI> detailFillList){
		for(int j = 0; j < detailFillList.size(); j++){
			EquPonScoringDetailEntity_HI fillEntity = detailFillList.get(j);
			if(productName.equals(fillEntity.getScoringItem()) && supplierId.intValue() == fillEntity.getSupplierId().intValue()){
				return fillEntity;
			}
		}
		return null;
	}

	public BigDecimal decimalConvern(String decimalStr){
		BigDecimal result = new BigDecimal(decimalStr.replace("%", "")).divide(new BigDecimal(100));
		System.out.println(result);
		return result;
	}

	/**
	 * 解析json参数
	 */
	public JSONObject getParamsJSONObject(JSONObject params,JSONObject targetJsonObject){
		if(params.containsKey("varUserId")){
			targetJsonObject.put("varUserId",params.get("varUserId"));
		}
		if(params.containsKey("username")){
			targetJsonObject.put("username",params.get("username"));
		}
		if(params.containsKey("varSystemCode")){
			targetJsonObject.put("varSystemCode",params.get("varSystemCode"));
		}
		if(params.containsKey("varUserName")){
			targetJsonObject.put("varUserName",params.get("varUserName"));
		}
		if(params.containsKey("varEmployeeNumber")){
			targetJsonObject.put("varEmployeeNumber",params.get("varEmployeeNumber"));
		}
		if(params.containsKey("varUserFullName")){
			targetJsonObject.put("varUserFullName",params.get("varUserFullName"));
		}
		if(params.containsKey("varOrgId")){
			targetJsonObject.put("varOrgId",params.get("varOrgId"));
		}
		if(params.containsKey("varOrgCode")){
			targetJsonObject.put("varOrgCode",params.get("varOrgCode"));
		}
		if(params.containsKey("varLeaderId")){
			targetJsonObject.put("varLeaderId",params.get("varLeaderId"));
		}
		if(params.containsKey("varIsadmin")){
			targetJsonObject.put("varIsadmin",params.get("varIsadmin"));
		}
		if(params.containsKey("varUserType")){
			targetJsonObject.put("varUserType",params.get("varUserType"));
		}
		if(params.containsKey("operationOrgIds")){
			targetJsonObject.put("operationOrgIds",params.get("operationOrgIds"));
		}
		if(params.containsKey("operationOrgId")){
			targetJsonObject.put("operationOrgId",params.get("operationOrgId"));
		}
		if(params.containsKey("operationRespId")){
			targetJsonObject.put("operationRespId",params.get("operationRespId"));
		}
		if(params.containsKey("operatorUserId")){
			targetJsonObject.put("operatorUserId",params.get("operatorUserId"));
		}
		return targetJsonObject;
	}

	/**
	 * 报价管理-资料开启单据LOV，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findPonInformationInfoLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonScoringInfoEntity_HI_RO.INFORMATION_LOV_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonScoringInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.creation_date desc");
		Pagination<EquPonScoringInfoEntity_HI_RO> pagination = equPonScoringInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public void checkQuotationStatus(JSONObject queryParamJSON) throws Exception{
		Integer projectId = queryParamJSON.getInteger("projectId");
		String projectNumber = queryParamJSON.getString("projectNumber");

		//根据立项单据号，查询供应商报价行
		List<EquPonProjectInfoEntity_HI> projectEntityList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
		String sourceProjectNumber = projectEntityList.get(0).getSourceProjectNumber();
		List<EquPonProjectInfoEntity_HI> allVersionProjectEntityList = equPonProjectInfoDAO_HI.findByProperty("sourceProjectNumber",sourceProjectNumber);
		List<EquPonQuotationInfoEntity_HI_RO> quotationDetailList = null;
		for(int k = 0; k < allVersionProjectEntityList.size(); k++) {
			EquPonProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);

			JSONObject queryJson = new JSONObject();
			queryJson.put("projectId",projectEntity.getProjectId());
			queryJson.put("projectNumber",projectEntity.getProjectNumber());
			quotationDetailList = equPonQuotationInfoServer.findQuotationDetails(queryJson);
			if(quotationDetailList.size() > 0){
				//校验报价单状态是否已完成
				for(int i = 0; i < quotationDetailList.size(); i++){
					EquPonQuotationInfoEntity_HI_RO row = quotationDetailList.get(i);
					if(!"STOP".equals(row.getDocStatus())){
						//if(!"STOP".equals(row.getDocStatus())){
							throw new Exception("供应商【" + row.getSupplierName() + "】存在没有截止的报价单" + row.getQuotationNumber() + "，确认失败！");
						//}
					}
				}
//				break;
			}
		}
//		if(null == quotationDetailList || quotationDetailList.size() == 0){
//			throw new Exception("立项单据关联的供应商未全部确认参与并创建报价单!");
//		}
	}

	/**
	 * 导出评分信息
	 * @param queryParamJson 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public ResultFileEntity exportScoringTemplate(JSONObject queryParamJson, HttpServletResponse response) throws Exception {
		//工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();

		//工作表
		XSSFSheet costSheet = workbook.createSheet("Cost");
		XSSFSheet panelTestSheet = workbook.createSheet("Panel test");
		XSSFSheet resultSheet = workbook.createSheet("报价结果");

		fillcostSheetContent(costSheet,queryParamJson);
		fillpanelTestSheetContent(panelTestSheet,queryParamJson);
		fillResultSheetContent(resultSheet,queryParamJson);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] bytes = os.toByteArray();
		InputStream is = new ByteArrayInputStream(bytes);
		ResultFileEntity result = fastDfsServer.fileUpload(is, "评分数据导出.xlsx");
		return result;
	}

	public void fillcostSheetContent(XSSFSheet costSheet,JSONObject queryParamJson){
		JSONArray costColumnsArray = queryParamJson.getJSONArray("costColumns");
		JSONArray costProductQuationArray = queryParamJson.getJSONArray("costProductQuationDataTable");
		//设置列宽
		for(int i = 0; i < costColumnsArray.size() + 2; i++){
			costSheet.setColumnWidth(i,7000);
		}
		//标题行
		XSSFRow row1 = costSheet.createRow((short) 0);
		XSSFCell cell1 = row1.createCell((short) 0);
		cell1.setCellValue("item description");
		XSSFCell cell2 = row1.createCell((short) 1);
		cell2.setCellValue("Forecast Annual Quantity");
		for(int j = 0; j < costColumnsArray.size(); j++){
			JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
			String supplierName = supplierColumn.getString("supplierName");
			XSSFCell cell = row1.createCell((short) j + 2);
			cell.setCellValue(supplierName);
		}

		for(int i = 0; i < costProductQuationArray.size(); i++){
			XSSFRow r = costSheet.createRow((short) i+1);
			JSONObject quotationObj = costProductQuationArray.getJSONObject(i);
			String productName = quotationObj.getString("productName");
			String annualSalesQuantity = quotationObj.getString("annualSalesQuantity");
			XSSFCell c1 = r.createCell((short) 0);
			XSSFCell c2 = r.createCell((short) 1);
			c1.setCellValue(productName);
			c2.setCellValue(annualSalesQuantity);
			for(int j = 0; j < costColumnsArray.size(); j++){
				XSSFCell c = r.createCell((short) j + 2);
				JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
				String supplierNumber = supplierColumn.getString("supplierNumber");
				String score = quotationObj.getString(supplierNumber);
				c.setCellValue(score);
			}
		}
	}

	public void fillpanelTestSheetContent(XSSFSheet panelTestSheet,JSONObject queryParamJson){
		JSONArray costColumnsArray = queryParamJson.getJSONArray("costColumns");
		JSONArray productSpecsArray = queryParamJson.getJSONArray("productSpecsDataTable");
		//设置列宽
		for(int i = 0; i < costColumnsArray.size() + 3; i++){
			panelTestSheet.setColumnWidth(i,7000);
		}
		//标题行
		XSSFRow row1 = panelTestSheet.createRow((short) 0);
		XSSFCell cell1 = row1.createCell((short) 0);
		cell1.setCellValue("item description");
		XSSFCell cell2 = row1.createCell((short) 1);
		cell2.setCellValue("Highest Score");
		XSSFCell cell3 = row1.createCell((short) 2);
		cell3.setCellValue("benchmark");
		for(int j = 0; j < costColumnsArray.size(); j++){
			JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
			String supplierName = supplierColumn.getString("supplierName");
			XSSFCell cell = row1.createCell((short) j + 3);
			cell.setCellValue(supplierName);
		}

		for(int i = 0; i < productSpecsArray.size(); i++){
			XSSFRow r = panelTestSheet.createRow((short) i+1);
			JSONObject quotationObj = productSpecsArray.getJSONObject(i);
			String productName = quotationObj.getString("productName");
			String highestScore = quotationObj.getString("highestScore");
			String benchmark = quotationObj.getString("benchmark");
			XSSFCell c1 = r.createCell((short) 0);
			XSSFCell c2 = r.createCell((short) 1);
			XSSFCell c3 = r.createCell((short) 2);
			c1.setCellValue(productName);
			c2.setCellValue(highestScore);
			c3.setCellValue(benchmark);
			for(int j = 0; j < costColumnsArray.size(); j++){
				XSSFCell c = r.createCell((short) j + 3);
				JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
				String supplierNumber = supplierColumn.getString("supplierNumber");
				String score = quotationObj.getString(supplierNumber);
				c.setCellValue(score);
			}
		}
	}

	public void fillResultSheetContent(XSSFSheet resultSheet,JSONObject queryParamJson){
		JSONArray costColumnsArray = queryParamJson.getJSONArray("costColumns");
		JSONArray nonPriceCalArray = queryParamJson.getJSONArray("nonPriceCalTable");
		String sensoryTestType = queryParamJson.getString("sensoryTestType");
		JSONObject nonPriceCalTotalObj = queryParamJson.getJSONObject("nonPriceCalTotalParamter");
		JSONObject nonPriceCalRankObj = queryParamJson.getJSONObject("nonPriceCalRankParamter");
		//设置列宽
		for(int i = 0; i < costColumnsArray.size() + 2; i++){
			resultSheet.setColumnWidth(i,7000);
		}
		//标题行
		XSSFRow row1 = resultSheet.createRow((short) 0);
		XSSFCell cell1 = row1.createCell((short) 0);
		cell1.setCellValue("Value");
		XSSFCell cell2 = row1.createCell((short) 1);
		cell2.setCellValue("Weighting%");
		for(int j = 0; j < costColumnsArray.size(); j++){
			JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
			String supplierName = supplierColumn.getString("supplierName");
			XSSFCell cell = row1.createCell((short) j + 2);
			cell.setCellValue(supplierName);
		}

		for(int i = 0; i < nonPriceCalArray.size(); i++){
			XSSFRow r = resultSheet.createRow((short) i+1);
			JSONObject nonPriceCalObj = nonPriceCalArray.getJSONObject(i);
			String gradingDivision = nonPriceCalObj.getString("gradingDivision");
			String lineNumber = nonPriceCalObj.getString("lineNumber");
			String lineLv = nonPriceCalObj.getString("lineLv");
			String weight = nonPriceCalObj.getString("weight") + "%";
			XSSFCell c1 = r.createCell((short) 0);
			XSSFCell c2 = r.createCell((short) 1);
			c1.setCellValue(lineNumber + gradingDivision);
//			if(!"1".equals(lineLv) && !"1.1".equals(lineNumber)){
//				c2.setCellValue(weight);
//			}
			if(!"1".equals(lineLv)){
				c2.setCellValue(weight);
			}
//			if("10".equals(sensoryTestType) && "2.1".equals(lineNumber)){
//				c2.setCellValue("");
//			}
			for(int j = 0; j < costColumnsArray.size(); j++){
				XSSFCell c = r.createCell((short) j + 2);
				JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
				String supplierNumber = supplierColumn.getString("supplierNumber");
				String score = nonPriceCalObj.getString(supplierNumber);
//				if(!"1.1".equals(lineNumber)){
					c.setCellValue(score);
//				}
//				if("10".equals(sensoryTestType) && "2.1".equals(lineNumber)){
//					c.setCellValue("");
//				}
			}
		}
        //总分行
		XSSFRow totalScoreRow = resultSheet.createRow((short) nonPriceCalArray.size() + 1);
//		String weight = nonPriceCalTotalObj.getString("weight") + "%";
		String weight = "100%";
		XSSFCell scoreCell1 = totalScoreRow.createCell((short) 0);
		XSSFCell scoreCell2 = totalScoreRow.createCell((short) 1);
		scoreCell1.setCellValue("Total");
		scoreCell2.setCellValue(weight);

		for(int j = 0; j < costColumnsArray.size(); j++){
			XSSFCell c = totalScoreRow.createCell((short) j + 2);
			JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
			String supplierNumber = supplierColumn.getString("supplierNumber");
			String score = nonPriceCalTotalObj.getString(supplierNumber);
			c.setCellValue(score);
		}
		//排名行
		XSSFRow rankingRow = resultSheet.createRow((short) nonPriceCalArray.size() + 2);
		XSSFCell rankingCell1 = rankingRow.createCell((short) 0);
		rankingCell1.setCellValue("Scoring Ranking");

		CellRangeAddress cra =new CellRangeAddress(nonPriceCalArray.size() + 2, nonPriceCalArray.size() + 2, 0, 1); // 起始行, 终止行, 起始列, 终止列
		resultSheet.addMergedRegion(cra);

		for(int j = 0; j < costColumnsArray.size(); j++){
			XSSFCell c = rankingRow.createCell((short) j + 2);
			JSONObject supplierColumn = costColumnsArray.getJSONObject(j);
			String supplierNumber = supplierColumn.getString("supplierNumber");
			String score = nonPriceCalRankObj.getString(supplierNumber);
			if(!"".equals(score) && null != score){
				c.setCellValue("第" + score + "名");
			}else{
				c.setCellValue("");
			}
		}
	}


	/**
	 * panel test 导入模板下载
	 * @param queryParamJson 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public ResultFileEntity btnExportTemplage(JSONObject queryParamJson, HttpServletResponse response) throws Exception {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 设置sheet标题
		ExportExcelEntity exportExcelEntity = null;
		exportExcelEntity = generateTemplateExport(queryParamJson);

		// 创建Excel的工作sheet HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；
		//XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
		SXSSFWorkbook wb = new SXSSFWorkbook();
		SXSSFSheet sheet = wb.createSheet(exportExcelEntity.getSheetTitle());
		// 向工作表中填充内容
		writeExcelSheet(exportExcelEntity, sheet, queryParamJson);
		wb.write(os);
		byte[] bytes = os.toByteArray();
		InputStream is = new ByteArrayInputStream(bytes);
		ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "Panel Test评分模板.xlsx");
		return resultFileEntity;
	}

	private void writeExcelSheet(ExportExcelEntity expoEntity, SXSSFSheet sheet, JSONObject jsonObject) {
		// 总列数
		int colsCount = expoEntity.getSheetFieldsName().size();
		// 创建Excel的sheet的一行
		SXSSFRow row = sheet.createRow(0);
		// 创建sheet的列名
		SXSSFCell rowCell = null;
		for (int i = 0; i < colsCount; i++) {
			rowCell = row.createCell(i);
			String fieldName = expoEntity.getSheetFieldsName().get(i);
			rowCell.setCellValue(fieldName);
			// 设置自定义列宽
			if (expoEntity.getSheetColWidth() != null) {
				sheet.setColumnWidth(i, expoEntity.getSheetColWidth().get(i));
			}
		}
		//向表格内写入获取的动态数据
		List<EquPonProductSpecsEntity_HI_RO> sheetData = expoEntity.getSheetData();
		// 对获取的动态数据按照产品名称进行排序
//		sheetData = sheetData.stream().sorted((e1, e2) -> e1.getProductName().compareTo(e2.getProductName())).collect(Collectors.toList());
		for (int i = 0; i < sheetData.size(); i++) {
			row = sheet.createRow(1 + i);
			SXSSFCell productName = row.createCell(0);
			productName.setCellType(CellType.STRING);
			productName.setCellValue(sheetData.get(i).getProductName());
		}
	}

	private ExportExcelEntity generateTemplateExport(JSONObject jsonObject) throws Exception {
		String sheetTitle = "Panel Test导入模板";
		//根据projectId,查询立项邀请供应商列表
		StringBuffer sql = new StringBuffer(EquPonSupplierInvitationEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		jsonObject.put("isQuit","N");
		SaafToolUtils.parperHbmParam(EquPonSupplierInvitationEntity_HI_RO.class, jsonObject, sql, map);
		sql.append(" order by t.invitation_id asc");
		List<EquPonSupplierInvitationEntity_HI_RO> supplierInvitationList = equPonSupplierInvitationEntity_HI_RO.findList(sql, map);
        List<String> incomingParam = new ArrayList<>();
        List<String> efferentParam = new ArrayList<>();
        List<String> typeParam = new ArrayList<>();
        incomingParam.add("scoringStatus");
        incomingParam.add("relevantCatetory");
        incomingParam.add("projectCategory");
        incomingParam.add("sensoryTestTypes");
        efferentParam.add("scoringStatusMeaning");
        efferentParam.add("relevantCatetoryMeaning");
        efferentParam.add("projectCategoryMeaning");
        efferentParam.add("sensoryTestTypesMeaning");
        typeParam.add("EQU_SCORING_ORDER_STATUS");
        typeParam.add("EQU_PROJECT_CATEGORY");
        typeParam.add("EQU_PROJECT_TYPE");
        typeParam.add("EQU_SENSORY_TEST_TYPE");
        JSONArray data = JSONObject.parseArray(JSONObject.toJSONString(supplierInvitationList));
        data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
        supplierInvitationList = data.toJavaList(EquPonSupplierInvitationEntity_HI_RO.class);

        //根据projectId,查询立项产品规格
		StringBuffer sql2 = new StringBuffer(EquPonProductSpecsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map2 = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonProductSpecsEntity_HI_RO.class, jsonObject, sql2, map2);
		sql2.append(" order by t.specs_id");
		List<EquPonProductSpecsEntity_HI_RO> productSpecsList = equPonProductSpecsEntity_HI_RO.findList(sql2, map2);
        JSONArray data2 = JSONObject.parseArray(JSONObject.toJSONString(productSpecsList));
        data2 = ResultUtils.getLookUpValues(data2,incomingParam,efferentParam,typeParam);
        productSpecsList = data2.toJavaList(EquPonProductSpecsEntity_HI_RO.class);
		// 构建列名
		List<String> sheetFieldsName = new ArrayList<>();
		// 设置列宽
		List<Integer> sheetColWidth = new ArrayList<Integer>();

		sheetFieldsName.add("item description");
		sheetColWidth.add(0, 4000);

		sheetFieldsName.add("benchmark");
		sheetColWidth.add(1, 4000);

		for(int i = 0; i < supplierInvitationList.size(); i++){
			sheetFieldsName.add(supplierInvitationList.get(i).getSupplierName());
			sheetColWidth.add(i + 2, 7000);
		}
		// 构建数据
//		List<EquPonQuotationProductInfoEntity_HI> productInfoList = productInfoDao.findByProperty("quotationId", jsonObject.getInteger("sourceId"));
		if (CollectionUtils.isEmpty(productSpecsList)) {
			throw new Exception("产品规格不能为空!");
		}
		// 构建表单内容实体
		return new ExportExcelEntity(sheetTitle, sheetFieldsName, productSpecsList, sheetColWidth);
	}

	/**
	 * 批量导入Panel Test 分数
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public int updatePanelTestScoreImport(JSONObject jsonObject) throws Exception {
		Integer userId = jsonObject.getInteger("varUserId");
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		Integer scoringId = jsonObject.getJSONObject("info").getInteger("scoringId");
		Integer projectId = jsonObject.getJSONObject("info").getInteger("projectId");
		JSONArray errList = new JSONArray();

		//查询供应商邀请列表
		//根据projectId,查询立项邀请供应商列表
		StringBuffer sql = new StringBuffer(EquPonSupplierInvitationEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		jsonObject.put("isQuit","N");
		jsonObject.put("projectId",projectId);
		SaafToolUtils.parperHbmParam(EquPonSupplierInvitationEntity_HI_RO.class, jsonObject, sql, map);
		List<EquPonSupplierInvitationEntity_HI_RO> supplierInvitationList = equPonSupplierInvitationEntity_HI_RO.findList(sql, map);

		//清除panelTest分数
		Map queryMap = new HashMap();
		queryMap.put("scoringId",scoringId);
		queryMap.put("scoringType","panelTest");
		List<EquPonScoringDetailEntity_HI> scoringDetailList = equPonScoringDetailDAO_HI.findByProperty(queryMap);
		equPonScoringDetailDAO_HI.delete(scoringDetailList);

		//获取每个item description的最高分
		Map<String,BigDecimal> highScoreMap = new HashMap<String,BigDecimal>();
		for(int i = 0; i < supplierInvitationList.size(); i++){
			EquPonSupplierInvitationEntity_HI_RO supplierInvitationEntity = supplierInvitationList.get(i);
			for(int j = 0; j < jsonArray.size(); j++){
				JSONObject json = jsonArray.getJSONObject(j);
				String productName = json.getString("itemDescription");
				BigDecimal score = json.getBigDecimal(supplierInvitationEntity.getSupplierNumber());
				if(highScoreMap.containsKey(productName)){
					if(score.floatValue() > highScoreMap.get(productName).floatValue()){
						highScoreMap.put(productName,score);
					}
				}else{
					highScoreMap.put(productName,score);
				}
			}
		}


		//插入panelTest分数
		for(int i = 0; i < supplierInvitationList.size(); i++){
			EquPonSupplierInvitationEntity_HI_RO supplierInvitationEntity = supplierInvitationList.get(i);
			for(int j = 0; j < jsonArray.size(); j++){
				JSONObject json = jsonArray.getJSONObject(j);
				String productName = json.getString("itemDescription");
//				BigDecimal highestScore = emptyToZero(json.getBigDecimal("highestScore"));
				BigDecimal highestScore = highScoreMap.get(productName);
				BigDecimal benchmark = emptyToZero(json.getBigDecimal("benchmark"));
				BigDecimal score = json.getBigDecimal(supplierInvitationEntity.getSupplierNumber());
				Integer supplierId = supplierInvitationEntity.getSupplierId();

				EquPonScoringDetailEntity_HI entity = new EquPonScoringDetailEntity_HI();
				entity.setScoringId(scoringId);
				entity.setScoringType("panelTest");
				entity.setScoringItem(productName);
				entity.setSupplierId(supplierId);
				entity.setScore(score);
				entity.setHighestScore(highestScore);
				entity.setBenchMark(benchmark);
				entity.setOperatorUserId(userId);
				equPonScoringDetailDAO_HI.saveEntity(entity);
			}
		}

		return jsonArray.size();
	}

	/**
	 * 评分单据审批回调接口
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public EquPonScoringInfoEntity_HI scoringApprovalCallback(JSONObject parseObject, int userId) throws Exception {
		Integer headerId = parseObject.getIntValue("id");//单据Id
		EquPonScoringInfoEntity_HI entityHeader = this.getById(headerId);
		String orderStatus = null;//状态
		switch (parseObject.getString("status")) {
			case "REFUSAL":
				if(!"50".equals(entityHeader.getScoringStatus())){
					orderStatus = "40";
				}else{
					orderStatus = "50";
				}
				orderStatus = "40";
				break;
			case "ALLOW":
				//获取资料开启单据id
				Integer informationId = entityHeader.getInformationId();
				//查询资料开启单据
				List<EquPonQuotationInformationEntity_HI> informationEntityList = equPonQuotationInformationDAO_HI.findByProperty("informationId",informationId);
				EquPonQuotationInformationEntity_HI informationEntity = informationEntityList.get(0);
				//获取立项id
				Integer projectId = informationEntity.getProjectId();
				//查询立项单据
				List<EquPonProjectInfoEntity_HI> projectEntityList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
				EquPonProjectInfoEntity_HI projectEty = projectEntityList.get(0);
				String sourceProjectNumber = projectEty.getSourceProjectNumber();
				List<EquPonProjectInfoEntity_HI> allVersionProjectEntityList = equPonProjectInfoDAO_HI.findByProperty("sourceProjectNumber",sourceProjectNumber);
//				for(int k = 0; k < allVersionProjectEntityList.size(); k++){
//					EquPonProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);
//					List<EquPonQuotationInfoEntity_HI> infoEntityList =  equPonQuotationInfoDAO_HI.findByProperty("projectId",projectId);
//				}

//				EquPonProjectInfoEntity_HI projectEntity = projectEntityList.get(0);
				//查找报价单
//				List<EquPonQuotationInfoEntity_HI> infoEntityList =  equPonQuotationInfoDAO_HI.findByProperty("projectId",projectId);

				entityHeader.setSecondOpenDate(new Date());
				//如果单据终止状态terminateFlag为Y,报价资料开启单/报价单/立项单据状态全部调整为终止
				if("Y".equals(entityHeader.getTerminateFlag())){
					//1.修改报价资料开启单据状态为终止
					informationEntity.setInformationStatus("50");
					informationEntity.setOperatorUserId(userId);
					//2.修改立项单据状态为终止
					for(int k = 0; k < allVersionProjectEntityList.size(); k++){
						EquPonProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);
						projectEntity.setProjectStatus("50");
						projectEntity.setOperatorUserId(userId);
					}
					//3.修改报价单状态为终止
					for(int k = 0; k < allVersionProjectEntityList.size(); k++){
						EquPonProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);
						List<EquPonQuotationInfoEntity_HI> infoEntityList = equPonQuotationInfoDAO_HI.findByProperty("projectId",projectEntity.getProjectId());
						for(int i = 0; i < infoEntityList.size(); i++){
							EquPonQuotationInfoEntity_HI infoEntity = infoEntityList.get(i);
							infoEntity.setDocStatus("BREAK");
							infoEntity.setOperatorUserId(userId);
						}
					}

				}else{
					//修改已截至的报价单状态为已完成
					for(int k = 0; k < allVersionProjectEntityList.size(); k++){
						EquPonProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);
						List<EquPonQuotationInfoEntity_HI> infoEntityList = equPonQuotationInfoDAO_HI.findByProperty("projectId",projectEntity.getProjectId());
						for(int i = 0; i < infoEntityList.size(); i++){
							EquPonQuotationInfoEntity_HI infoEntity = infoEntityList.get(i);
							if("STOP".equals(infoEntity.getDocStatus())){
								infoEntity.setDocStatus("COMPLETE");
								infoEntity.setOperatorUserId(userId);
							}
						}
					}

					//修改供应商第二轮开启标志
					List<EquPonSupplierInvitationEntity_HI> suppInviteList = equPonSupplierInvitationDao.findByProperty("projectId",projectId);
					for(int i = 0; i < suppInviteList.size(); i++){
						EquPonSupplierInvitationEntity_HI suppInviteEntity = suppInviteList.get(i);
						String isQuit = suppInviteEntity.getIsQuit();
						if(null != isQuit && "Y".equals(isQuit)){
							suppInviteEntity.setSecondRoundOpening("N");
						}else{
							suppInviteEntity.setSecondRoundOpening("Y");
						}
					}
				}
				//如果单据终止状态terminateFlag为Y,单据状态修改为终止,否则为已批准
				if("Y".equals(entityHeader.getTerminateFlag())){
					orderStatus = "50";
				}else{
					orderStatus = "30";
				}


				//涉及第三方感官测试的项目，评分时感官测试分数因为必须要等非价格评分审批完之后再填，所以需要增加一个提示邮件给QA
				String sensoryTestTypes = projectEty.getSensoryTestTypes();
				if ("10".equals(sensoryTestTypes)) {
					Map queryMap = new HashMap();
					queryMap.put("projectId",projectId);
					queryMap.put("fixFlag","N");
					List<EquPonScoringTeamEntity_HI> scoringTeamList = equPonScoringTeamDAO_HI.findByProperty(queryMap);
					EquPonScoringTeamEntity_HI scoringTeamEntity = scoringTeamList.get(0);
					String memberNumber = scoringTeamEntity.getMemberNumber();
					if(!"".equals(memberNumber)){
						JSONObject paramsJson = new JSONObject();
						paramsJson.put("userName", memberNumber);
						JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
						String sendTo = resultJson.getString("email");

						String content = CommonUtils.scoreAllowMailContent(scoringTeamEntity.getMemberName(),projectEty.getProjectName());
						String subject = "QA人员评分通知";
						EmailUtil.sendInMail(subject,content,sendTo);
					}
				}

				break;
			case "DRAFT":
				orderStatus = "10";
				break;
			case "APPROVAL":
				orderStatus = "20";
				break;
			case "CLOSED":
				orderStatus = "50";
				break;
		}

		//流程节点审批通过,邮件通知owner
		CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getScoringNumber());

		entityHeader.setScoringStatus(orderStatus);
		entityHeader.setOperatorUserId(userId);
		this.saveOrUpdate(entityHeader);
		return entityHeader;
	}

	/**
	 * 校验用户操作权限
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String validateScoringUserOperator(JSONObject params) throws Exception {
		Integer userId = params.getInteger("varUserId");
		Integer scoringId = params.getInteger("scoringId");
		String operator = params.getString("operator");
		operateValidate(operator,scoringId,userId);
		return "S";
	}

	public void operateValidate(String operator,Integer scoringId,Integer userId) throws Exception{
		if(null != scoringId){
			JSONObject queryObj = new JSONObject();
			queryObj.put("scoringId",scoringId);
			queryObj.put("validate","Y");
			JSONObject scoringInfoObj = this.findScoringInfo(queryObj,1,999);
			JSONArray scoringInfoArr = scoringInfoObj.getJSONArray("data");
			JSONObject scoringInfoJson = scoringInfoArr.getJSONObject(0);
			String scoringStatus = scoringInfoJson.getString("scoringStatus");
			Integer createdBy = scoringInfoJson.getInteger("createdBy");
			String factoryAuditFlag = scoringInfoJson.getString("factoryAuditFlag");
			String panelTestFlag = scoringInfoJson.getString("panelTestFlag");
			String nonPriceCalculateFlag = scoringInfoJson.getString("nonPriceCalculateFlag");
			if(userId.intValue() != createdBy.intValue()){
				throw new Exception("非法操作,系统拒绝响应!");
			}
			if("SAVE".equals(operator)){
				if("20".equals(scoringStatus) || "50".equals(scoringStatus) || "60".equals(scoringStatus)){
					throw new Exception("非法操作,系统拒绝响应!");
				}
			}
			if("QASAVE".equals(operator)){
				if(!"N".equals(factoryAuditFlag) || !"10".equals(scoringStatus)){
					if(!"N".equals(panelTestFlag) || !"30".equals(scoringStatus)){
						throw new Exception("非法操作,系统拒绝响应!");
					}
				}
			}
			if("SUBMIT".equals(operator)){
				if("20".equals(scoringStatus) || "30".equals(scoringStatus) || "50".equals(scoringStatus) || "60".equals(scoringStatus) || !"Y".equals(nonPriceCalculateFlag)){
					throw new Exception("非法操作,系统拒绝响应!");
				}
			}
			if("QASUBMIT".equals(operator)){
				if(!"N".equals(factoryAuditFlag) || !"10".equals(scoringStatus)){
					if(!"N".equals(panelTestFlag) || !"30".equals(scoringStatus)){
						throw new Exception("非法操作,系统拒绝响应!");
					}
				}
			}
			if("TERMINATE".equals(operator)){
				if(!"40".equals(scoringStatus)){
					throw new Exception("非法操作,系统拒绝响应!");
				}
			}
		}
	}

	public JSONObject findScoringOwner(JSONObject queryParamJSON) {
		StringBuffer sql = null;
		Map<String, Object> map = new HashMap<>();
		Integer startUserId = queryParamJSON.getInteger("startUserId");
		EquPonScoringInfoEntity_HI_RO row = new EquPonScoringInfoEntity_HI_RO();
		List<EquPonScoringInfoEntity_HI_RO> list = new ArrayList<EquPonScoringInfoEntity_HI_RO>();

		JSONObject paramsJson = new JSONObject();
		paramsJson.put("userId", startUserId);
		JSONObject serviceResult = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
		if (serviceResult != null) {
			String userName = serviceResult.getString("userName");
			row.setUserId(startUserId);
			row.setUserName(userName);
		}
		Pagination<EquPonScoringInfoEntity_HI_RO> pagination = new Pagination<EquPonScoringInfoEntity_HI_RO>();
		list.add(row);
		pagination.setData(list);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}
}
