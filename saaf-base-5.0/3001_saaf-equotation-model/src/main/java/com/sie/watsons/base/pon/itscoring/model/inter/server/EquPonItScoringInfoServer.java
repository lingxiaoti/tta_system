package com.sie.watsons.base.pon.itscoring.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItScoringTeamEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoContentItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuotationInfoItEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.inter.server.EquPonQuotationInfoItServer;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringDetailEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringMaintainEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.readonly.EquPonItScoringInfoEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.inter.server.EquPonSupplierInvitationServer;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.itscoring.model.inter.IEquPonItScoringInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonItScoringInfoServer")
public class EquPonItScoringInfoServer extends BaseCommonServer<EquPonItScoringInfoEntity_HI> implements IEquPonItScoringInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringInfoServer.class);

	@Autowired
	private ViewObject<EquPonItScoringInfoEntity_HI> equPonItScoringInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItScoringInfoEntity_HI_RO> equPonItScoringInfoEntity_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private EquPonItScoringDetailServer equPonItScoringDetailServer;

	@Autowired
	private EquPonItScoringMaintainServer equPonItScoringMaintainServer;

	@Autowired
	private EquPonSupplierInvitationServer equPonSupplierInvitationServer;

	@Autowired
	private ViewObject<EquPonItProjectInfoEntity_HI> equPonItProjectInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonItProjectInfoEntity_HI> equPonProjectInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInformationEntity_HI> equPonQuotationInformationDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringTeamEntity_HI> equPonItScoringTeamDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringDetailEntity_HI> equPonItScoringDetailDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringMaintainEntity_HI> equPonItScoringMaintainDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInfoItEntity_HI> equPonQuotationInfoItDAO_HI;

	@Autowired
	private ViewObject<EquPonQuoContentItEntity_HI> equPonQuoContentItDAO_HI;

	@Autowired
	private EquPonQuotationInfoItServer equPonQuotationInfoItServer;

	public EquPonItScoringInfoServer() {
		super();
	}

	public JSONObject findScoringInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
//		String deptCode = queryParamJSON.getString("deptCode");
//		if(!"03".equals(deptCode)){
//			queryParamJSON.put("deptCode","03");
//		}
		Integer varUserId = queryParamJSON.getInteger("varUserId");
		String varEmployeeNumber = queryParamJSON.getString("varEmployeeNumber");

		StringBuffer sql = null;

		if(queryParamJSON.get("lovFlag")!=null&&"Y".equals(queryParamJSON.getString("lovFlag"))){
			sql = new StringBuffer(EquPonItScoringInfoEntity_HI_RO.QUERY_SQL2);
		}else{
			sql = new StringBuffer(EquPonItScoringInfoEntity_HI_RO.QUERY_SQL);
		}

		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItScoringInfoEntity_HI_RO.class, queryParamJSON, sql, map);
//		如果是报价结果审批查询lov就不要有重复数据  "searchType" -> "quotationApproval"
		if(queryParamJSON.get("searchType")!=null&&"quotationApproval".equals(queryParamJSON.getString("searchType"))){
			sql.append(" AND NOT EXISTS ( SELECT 1 FROM EQU_PON_QUOTATION_APPROVAL PQA WHERE PQA.scoring_id = s.SCORING_ID AND PQA.DEPT_CODE <> '0E' AND PQA.APPROVAL_STATUS <> '50' ) ");
		}
		if(null == queryParamJSON.getString("validate")){
			if(queryParamJSON.get("lovFlag")!=null&&"Y".equals(queryParamJSON.getString("lovFlag"))){
				sql.append(" and (s.created_by = " + varUserId + ")");
				sql.append(" order by s.creation_date desc");
			}else{
				sql.append(" and (t.created_by = " + varUserId + " or st.member_number = '" + varEmployeeNumber + "')");
				sql.append(" order by t.creation_date desc");
			}

		}
//		sql.append(" order by t.creation_date desc");
		Pagination<EquPonItScoringInfoEntity_HI_RO> pagination = equPonItScoringInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public JSONObject findScoringInfoForFlow(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPonItScoringInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItScoringInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonItScoringInfoEntity_HI_RO> pagination = equPonItScoringInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
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

		StringBuffer sql = new StringBuffer(EquPonItScoringInfoEntity_HI_RO.INFORMATION_LOV_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItScoringInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.creation_date desc");
		Pagination<EquPonItScoringInfoEntity_HI_RO> pagination = equPonItScoringInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public void checkQuotationStatus(JSONObject queryParamJSON) throws Exception{
		Integer projectId = queryParamJSON.getInteger("projectId");
		String projectNumber = queryParamJSON.getString("projectNumber");

		//根据立项单据号，查询供应商报价行
		List<EquPonItProjectInfoEntity_HI> projectEntityList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
		String sourceProjectNumber = projectEntityList.get(0).getSourceProjectNumber();
		List<EquPonItProjectInfoEntity_HI> allVersionProjectEntityList = equPonProjectInfoDAO_HI.findByProperty("sourceProjectNumber",sourceProjectNumber);
		List<EquPonQuotationInfoItEntity_HI_RO> quotationDetailList = null;
		for(int k = 0; k < allVersionProjectEntityList.size(); k++) {
			EquPonItProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);

			JSONObject queryJson = new JSONObject();
			queryJson.put("projectId",projectEntity.getProjectId());
			queryJson.put("projectNumber",projectEntity.getProjectNumber());
			quotationDetailList = equPonQuotationInfoItServer.findQuotationDetails(queryJson);
			if(quotationDetailList.size() > 0){
				//校验报价单状态是否已完成
				for(int i = 0; i < quotationDetailList.size(); i++){
					EquPonQuotationInfoItEntity_HI_RO row = quotationDetailList.get(i);
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

	@Override
	public EquPonItScoringInfoEntity_HI saveScoringInfo(JSONObject params) throws Exception {
		JSONObject scoringHeaderInfo = getParamsJSONObject(params,params.getJSONObject("scoringHeaderInfo"));
		JSONArray scoringDetailInfoArray = params.getJSONArray("scoringDetailInfo") == null ? new JSONArray() : params.getJSONArray("scoringDetailInfo");
		JSONArray scoringMaintainInfoArray = params.getJSONArray("userScoringMaintainInfo") == null ? new JSONArray() : params.getJSONArray("userScoringMaintainInfo");
		JSONArray invitationSupplierInfoArray = params.getJSONArray("invitationSupplierInfo") == null ? new JSONArray() : params.getJSONArray("invitationSupplierInfo");

		EquPonItScoringInfoEntity_HI scoringEntity = null;
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

		Integer projectId = scoringEntity.getProjectId();

		String supplierConfirmFlag = scoringEntity.getSupplierConfirmFlag();
		if("Y".equals(supplierConfirmFlag)){
			//保存评分单据详情信息
			for(int i = 0; i < scoringDetailInfoArray.size(); i++){
				JSONObject scoringDetailInfo = getParamsJSONObject(params,scoringDetailInfoArray.getJSONObject(i));
				Object scoringDetailId = scoringDetailInfo.get("scoringDetailId");

				if(!scoringDetailInfo.containsKey("scoringDetailId") || "".equals(scoringDetailId)){
					//新增保存
					scoringDetailInfo.put("scoringId",scoringEntity.getScoringId());
				}
				equPonItScoringDetailServer.saveOrUpdate(scoringDetailInfo);
			}


			//评分单据新增保存时，为所有评分人生成评分模板
			if(!scoringHeaderInfo.containsKey("scoringId")){
				//查询相关评分人信息
				List<EquPonItScoringTeamEntity_HI> scoringTeamList = equPonItScoringTeamDAO_HI.findByProperty("projectId",projectId);
				for(int k = 0; k < scoringTeamList.size(); k++){
					EquPonItScoringTeamEntity_HI scoringTeamEntity = scoringTeamList.get(k);
					String memberNumber = scoringTeamEntity.getMemberNumber();
					String memberName = scoringTeamEntity.getMemberName();
					Integer weight = scoringTeamEntity.getWeight();
					if(weight.intValue() > 0){
						for(int i = 0; i < scoringMaintainInfoArray.size(); i++){
							JSONObject scoringMaintainInfo = getParamsJSONObject(params,scoringMaintainInfoArray.getJSONObject(i));
							scoringMaintainInfo.put("scoringId",scoringEntity.getScoringId());
							scoringMaintainInfo.put("scoringMemberNumber",memberNumber);
							scoringMaintainInfo.put("scoringMemberName",memberName);
							//如果当前操作人也是评分人之一，则保留评分，否则评分默认为空
							if(!memberNumber.equals(scoringMaintainInfo.getString("varEmployeeNumber"))){
								scoringMaintainInfo.put("score","");
								scoringMaintainInfo.put("remark","");
							}
							equPonItScoringMaintainServer.saveOrUpdate(scoringMaintainInfo);
						}
					}
				}
			}else{
				for(int i = 0; i < scoringMaintainInfoArray.size(); i++){
					JSONObject scoringMaintainInfo = getParamsJSONObject(params,scoringMaintainInfoArray.getJSONObject(i));
					Object scoringMaintainId = scoringMaintainInfo.get("scoringMaintainId");

					if(!scoringMaintainInfo.containsKey("scoringMaintainId") || "".equals(scoringMaintainId)){
						//新增保存
						scoringMaintainInfo.put("scoringId",scoringEntity.getScoringId());
					}
					equPonItScoringMaintainServer.saveOrUpdate(scoringMaintainInfo);
				}
			}



			//保存邀请供应商列表信息
//			for(int i = 0; i < invitationSupplierInfoArray.size(); i++){
//				JSONObject invitationSupplierInfo = getParamsJSONObject(params,invitationSupplierInfoArray.getJSONObject(i));
//				equPonSupplierInvitationServer.saveOrUpdate(invitationSupplierInfo);
//			}

			if(scoringHeaderInfo.containsKey("isSubmitScoreFlag")){
				String isSubmitScoreFlag = scoringHeaderInfo.getString("isSubmitScoreFlag");
				if("Y".equals(isSubmitScoreFlag)){
					//更新评分小组已评分标志Attribute1
					Map queryMap = new HashMap();
					queryMap.put("projectId",projectId);
					queryMap.put("memberNumber",scoringHeaderInfo.getString("varEmployeeNumber"));
					List<EquPonItScoringTeamEntity_HI> list = equPonItScoringTeamDAO_HI.findByProperty(queryMap);
					if(list.size() > 0){
						EquPonItScoringTeamEntity_HI ety = list.get(0);
						ety.setAttribute1("Y");
						ety.setOperatorUserId(scoringHeaderInfo.getInteger("varUserId"));
						equPonItScoringTeamDAO_HI.saveOrUpdate(ety);
					}
				}
			}

		}
		return scoringEntity;
	}

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

	@Override
	public void saveNonPriceCalculate(JSONObject params) throws Exception {
		Integer scoringId = params.getInteger("scoringId");
		Integer projectId = params.getInteger("projectId");
		nonPriceCalculate(scoringId,projectId);
	}

	public void nonPriceCalculate(Integer scoringId,Integer projectId){
		//查找评分单据
		EquPonItScoringInfoEntity_HI scoringEntity = this.getById(scoringId);
		scoringEntity.setNonPriceCalculateFlag("Y");

		//查找立项单据
		List<EquPonItProjectInfoEntity_HI> projectList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
		EquPonItProjectInfoEntity_HI projectEntity = projectList.get(0);

		//查找评分小组
		List<EquPonItScoringTeamEntity_HI> scoringTeamList = equPonItScoringTeamDAO_HI.findByProperty("projectId",projectId);
		Map<String,BigDecimal> scoringTeamMap = new HashMap<String,BigDecimal>();
		for(int i = 0; i < scoringTeamList.size(); i++){
			EquPonItScoringTeamEntity_HI scoringTeamEntity = scoringTeamList.get(i);
			String memberNumber = scoringTeamEntity.getMemberNumber();
			Integer weight = scoringTeamEntity.getWeight();
			scoringTeamMap.put(memberNumber,new BigDecimal(weight));
		}

		//查找所有评分人员评分记录
		Map queryMap4 = new HashMap();
		queryMap4.put("scoringId",scoringId);
		List<EquPonItScoringMaintainEntity_HI> scoringMaintainList = equPonItScoringMaintainDAO_HI.findByProperty(queryMap4);

		Map queryMap = new HashMap();
		queryMap.put("scoringId",scoringId);
		queryMap.put("scoringType","nonPriceCal");
		List<EquPonItScoringDetailEntity_HI> detailCalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap);

		Map queryMap2 = new HashMap();
		queryMap2.put("scoringId",scoringId);
		queryMap2.put("scoringType","nonPriceCalTotalScore");
		List<EquPonItScoringDetailEntity_HI> calTotalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap2);

		Map queryMap3 = new HashMap();
		queryMap3.put("scoringId",scoringId);
		queryMap3.put("scoringType","nonPriceCalRanking");
		List<EquPonItScoringDetailEntity_HI> calRankingList = equPonItScoringDetailDAO_HI.findByProperty(queryMap3);

		Map<Integer,Double> supplierScoreMap = new HashMap<Integer,Double>();
		Map<Integer,Double> supplierRatingMap = new HashMap<Integer,Double>();

		double supplierRatingTotal = 0.0;
		double supplierScoreTotal = 0.0;
		for(int i = 0; i < detailCalList.size(); i++){
			EquPonItScoringDetailEntity_HI detailEntity = detailCalList.get(i);
			String lineNumber = detailEntity.getLineNumber();
			double standardWeighting = detailEntity.getWeighting().doubleValue();
			double memberTotalRating = 0.0;
			double memberTotalScore = 0.0;
			if(lineNumber.startsWith("1")){
				for(int j = 0; j < scoringMaintainList.size(); j++){
					EquPonItScoringMaintainEntity_HI scoringMaintainEntity = scoringMaintainList.get(j);
					if(detailEntity.getSupplierId().intValue() == scoringMaintainEntity.getSupplierId().intValue() && detailEntity.getLineNumber().equals(scoringMaintainEntity.getLineNumber()) && detailEntity.getScoringItem().equals(scoringMaintainEntity.getScoringItem())){
						BigDecimal score = emptyToZero(scoringMaintainEntity.getScore());
						String memberNumber = scoringMaintainEntity.getScoringMemberNumber();
						BigDecimal weight = emptyToZero(scoringTeamMap.get(memberNumber));
						double memberRating = score.doubleValue() * weight.doubleValue() / 100;
						double memberScore = standardWeighting * memberRating / 100;
						memberTotalRating = memberTotalRating + memberRating;
						memberTotalScore = memberTotalScore + memberScore;
					}
				}
				detailEntity.setNonPriceRating(new BigDecimal(memberTotalRating));
				detailEntity.setNonPriceScore(new BigDecimal(memberTotalScore));

				if(supplierRatingMap.containsKey(detailEntity.getSupplierId())){
					supplierRatingTotal = supplierRatingMap.get(detailEntity.getSupplierId()) + memberTotalRating;
					supplierScoreTotal = supplierScoreMap.get(detailEntity.getSupplierId()) + memberTotalScore;
					supplierRatingMap.put(detailEntity.getSupplierId(),supplierRatingTotal);
					supplierScoreMap.put(detailEntity.getSupplierId(),supplierScoreTotal);
				}else{
					supplierRatingMap.put(detailEntity.getSupplierId(),memberTotalRating);
					supplierScoreMap.put(detailEntity.getSupplierId(),memberTotalScore);
				}
			}
		}

		//非价格计算总分
		for(int i = 0; i < calTotalList.size(); i++){
			EquPonItScoringDetailEntity_HI calTotalEntity = calTotalList.get(i);
//			calTotalEntity.setNonPriceRating(new BigDecimal(supplierRatingMap.get(calTotalEntity.getSupplierId())));
			calTotalEntity.setNonPriceScore(new BigDecimal(supplierScoreMap.get(calTotalEntity.getSupplierId())));
		}

		//排序
		List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(supplierScoreMap.entrySet()); //转换为list
		list.sort(new Comparator<Map.Entry<Integer, Double>>() {
			@Override
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		//非价格资料排名
		for (int i = 0; i < list.size(); i++) {
			for(int j = 0; j < calRankingList.size(); j++){
				EquPonItScoringDetailEntity_HI calRankingEntity = calRankingList.get(j);
				if(calRankingList.get(j).getSupplierId().intValue() == list.get(i).getKey().intValue()){
					calRankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}
	}

	@Override
	public void saveQuotationScoreCalculate(JSONObject params) throws Exception {
		Integer scoringId = params.getInteger("scoringId");
		Integer projectId = params.getInteger("projectId");
		quotationScoreCalculate(scoringId,projectId);
		nonPriceCalculate(scoringId,projectId);
	}

	public void quotationScoreCalculate(Integer scoringId,Integer projectId){
		Map queryMap = new HashMap();
		queryMap.put("scoringType","nonPriceCal");
		queryMap.put("scoringItem","2 价格");
		queryMap.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> priceCalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap);

		Map queryMap1 = new HashMap();
		queryMap1.put("scoringType","contractAmountExclusive");
		queryMap1.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> contractAmountExclusiveList = equPonItScoringDetailDAO_HI.findByProperty(queryMap1);

		Map queryMap2 = new HashMap();
		queryMap2.put("scoringType","contractAmountInclusive");
		queryMap2.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> contractAmountInclusiveList = equPonItScoringDetailDAO_HI.findByProperty(queryMap2);

		Map queryMap3 = new HashMap();
		queryMap3.put("scoringType","deductible");
		queryMap3.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> deductibleList = equPonItScoringDetailDAO_HI.findByProperty(queryMap3);

		Map queryMap4 = new HashMap();
		queryMap4.put("scoringType","contractAmountRanking");
		queryMap4.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> contractAmountRankingList = equPonItScoringDetailDAO_HI.findByProperty(queryMap4);

		Map queryMap5 = new HashMap();
		queryMap5.put("scoringId",scoringId);
		queryMap5.put("scoringType","quotationCalTotalScore");
		List<EquPonItScoringDetailEntity_HI> quotationCalTotalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap5);

		Map queryMap6 = new HashMap();
		queryMap6.put("scoringId",scoringId);
		queryMap6.put("scoringType","quotationCalRank");
		List<EquPonItScoringDetailEntity_HI> quotationCalRankingList = equPonItScoringDetailDAO_HI.findByProperty(queryMap6);

		Map queryMap7 = new HashMap();
		queryMap7.put("scoringType","nonPriceCalTotalScore");
		queryMap7.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> nonPriceCalTotalScoreList = equPonItScoringDetailDAO_HI.findByProperty(queryMap7);

		Map queryMap8 = new HashMap();
		queryMap8.put("scoringType","nonPriceCalRanking");
		queryMap8.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> nonPriceCalRankingList = equPonItScoringDetailDAO_HI.findByProperty(queryMap8);

		Map<Integer,Double> contractAmountExclusiveMap = new HashMap<Integer,Double>();
		Map<Integer,Double> contractAmountInclusiveMap = new HashMap<Integer,Double>();
		Map<Integer,Double> deductibleMap = new HashMap<Integer,Double>();

        EquPonItProjectInfoEntity_HI projectEntity = equPonItProjectInfoDAO_HI.getById(projectId);
        List<EquPonItProjectInfoEntity_HI> pList = equPonItProjectInfoDAO_HI.findByProperty("sourceProjectNumber",projectEntity.getSourceProjectNumber());
        StringBuffer projectIds = new StringBuffer("(");
        for(int i = 0; i < pList.size(); i++){
            EquPonItProjectInfoEntity_HI row = pList.get(i);
            if(i == pList.size() - 1){
                projectIds.append(row.getProjectId());
            }else{
                projectIds.append(row.getProjectId()).append(",");
            }
        }

        projectIds.append(")");
		for(int i = 0; i < contractAmountExclusiveList.size(); i++){
			EquPonItScoringDetailEntity_HI contractAmountExclusiveEntity = contractAmountExclusiveList.get(i);
			Integer supplierId = contractAmountExclusiveEntity.getSupplierId();
			//查找报价单
//			Map queryMap15 = new HashMap();
//			queryMap15.put("projectId",projectId);
//			queryMap15.put("supplierId",supplierId);
//			List<EquPonQuotationInfoItEntity_HI> quotationList = equPonQuotationInfoItDAO_HI.findByProperty(queryMap15);
            List<EquPonQuotationInfoItEntity_HI> quotationList = equPonQuotationInfoItDAO_HI.findList("from EquPonQuotationInfoItEntity_HI where supplierId = " + supplierId + " and projectId in " + projectIds.toString());


            //查找报价内容
			Map queryMap16 = new HashMap();
			queryMap16.put("quotationId",quotationList.get(0).getQuotationId());
			List<EquPonQuoContentItEntity_HI> quoContentList = equPonQuoContentItDAO_HI.findByProperty(queryMap16);

			double taxTotalSum = 0.0;//含税总额
			double taxAmountSum = 0.0;//可抵扣税款
			double nonTaxAmountSum = 0.0;//不含税价

			for(int j = 0; j < quoContentList.size(); j++){
				EquPonQuoContentItEntity_HI quoContentEntity = quoContentList.get(j);
				BigDecimal amount = quoContentEntity.getAmount();
				BigDecimal unitPrice = quoContentEntity.getUnitPrice();
				BigDecimal discount = quoContentEntity.getDiscount() == null ? new BigDecimal(100) : quoContentEntity.getDiscount();
				BigDecimal taxRate = quoContentEntity.getTaxRate();

				double taxTotal = unitPrice.doubleValue() * amount.doubleValue() * discount.doubleValue() * 0.01; //含税总价
				double taxAmount = unitPrice.doubleValue() * amount.doubleValue()* discount.doubleValue() * taxRate.doubleValue() * 0.01 * 0.01;//税额
				double nonTaxAmount = taxTotal - taxAmount;//不含税价

				taxTotalSum = taxTotalSum + taxTotal;
				taxAmountSum = taxAmountSum + taxAmount;
				nonTaxAmountSum = nonTaxAmountSum + nonTaxAmount;
			}
			contractAmountExclusiveMap.put(supplierId,nonTaxAmountSum);
			contractAmountInclusiveMap.put(supplierId,taxTotalSum);
			deductibleMap.put(supplierId,taxAmountSum);
		}


		for(int i = 0; i < contractAmountExclusiveList.size(); i++){
			EquPonItScoringDetailEntity_HI contractAmountExclusiveEntity = contractAmountExclusiveList.get(i);
			Integer supplierId = contractAmountExclusiveEntity.getSupplierId();
			contractAmountExclusiveEntity.setPriceSummary(new BigDecimal(contractAmountExclusiveMap.get(supplierId)));
		}

		for(int i = 0; i < contractAmountInclusiveList.size(); i++){
			EquPonItScoringDetailEntity_HI contractAmountInclusiveEntity = contractAmountInclusiveList.get(i);
			Integer supplierId = contractAmountInclusiveEntity.getSupplierId();
			contractAmountInclusiveEntity.setPriceSummary(new BigDecimal(contractAmountInclusiveMap.get(supplierId)));
		}

		for(int i = 0; i < deductibleList.size(); i++){
			EquPonItScoringDetailEntity_HI deductibleEntity = deductibleList.get(i);
			Integer supplierId = deductibleEntity.getSupplierId();
			deductibleEntity.setPriceSummary(new BigDecimal(deductibleMap.get(supplierId)));
		}


		//排序
		List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(contractAmountExclusiveMap.entrySet()); //转换为list
		list.sort(new Comparator<Map.Entry<Integer, Double>>() {
			@Override
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		//排名
		for (int i = 0; i < list.size(); i++) {
			for(int j = 0; j < contractAmountRankingList.size(); j++){
				EquPonItScoringDetailEntity_HI contractAmountRankingEntity = contractAmountRankingList.get(j);
				if(contractAmountRankingList.get(j).getSupplierId().intValue() == list.get(i).getKey().intValue()){
					contractAmountRankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}

//		//最低价格
//		List<Map.Entry<Integer, Double>> list3 = new ArrayList<Map.Entry<Integer, Double>>(deductibleMap.entrySet()); //转换为list
//		list3.sort(new Comparator<Map.Entry<Integer, Double>>() {
//			@Override
//			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
//				return o1.getValue().compareTo(o2.getValue());
//			}
//		});
		double minDeductible = list.get(0).getValue();
//
//		//价格评分计算
		Map<Integer, Double> priceScoreMap = new HashMap<Integer, Double>();
		for(int i = 0; i < priceCalList.size(); i++){
			EquPonItScoringDetailEntity_HI priceEntity = priceCalList.get(i);
			BigDecimal fullScore = priceEntity.getFullScore();
			BigDecimal weighting = priceEntity.getWeighting();
			Integer supplierId = priceEntity.getSupplierId();
//			double deductibleAmount = deductibleMap.get(supplierId);
			double deductibleAmount = contractAmountExclusiveMap.get(supplierId);
			System.out.println("【fullScore】:" + fullScore);
			System.out.println("【minDeductible】:" + minDeductible);
			System.out.println("【deductibleAmount】:" + deductibleAmount);
			double priceRating = fullScore.doubleValue() * (minDeductible / deductibleAmount);
			double priceScore = priceRating * weighting.doubleValue() * 0.01;
			priceEntity.setNonPriceScore(new BigDecimal(priceScore));
			priceEntity.setNonPriceRating(new BigDecimal(priceRating));
			priceScoreMap.put(supplierId,priceScore);
		}

		Map<Integer, Double> nonPriceCalTotalScoreMap = new HashMap<Integer, Double>();
//		Map<Integer, Double> nonPriceCalRankingMap = new HashMap<Integer, Double>();
		for(int i = 0; i < nonPriceCalTotalScoreList.size(); i++){
			EquPonItScoringDetailEntity_HI nonPriceCalTotalScoreEntity = nonPriceCalTotalScoreList.get(i);
			Integer supplierId = nonPriceCalTotalScoreEntity.getSupplierId();
			BigDecimal nonPriceScore = emptyToZero(nonPriceCalTotalScoreEntity.getNonPriceScore());
//			BigDecimal nonPriceRating = emptyToZero(nonPriceCalTotalScoreEntity.getNonPriceRating());
			nonPriceCalTotalScoreMap.put(supplierId,nonPriceScore.doubleValue());
//			nonPriceCalRankingMap.put(supplierId,nonPriceRating.doubleValue());
		}

//		Map<Integer, Double> nonPriceCalRankingMap = new HashMap<Integer, Double>();
//		for(int i = 0; i < nonPriceCalRankingList.size(); i++){
//			EquPonItScoringDetailEntity_HI nonPriceCalRankingEntity = nonPriceCalRankingList.get(i);
//			Integer supplierId = nonPriceCalRankingEntity.getSupplierId();
//			BigDecimal nonPriceRating = nonPriceCalRankingEntity.getNonPriceRating();
//			nonPriceCalRankingMap.put(supplierId,nonPriceRating.doubleValue());
//		}

		Map<Integer, Double> quotationCalTotalScoreMap = new HashMap<Integer, Double>();
		for(int i = 0; i < quotationCalTotalList.size(); i++){
			EquPonItScoringDetailEntity_HI quotationCalTotalScoreEntity = quotationCalTotalList.get(i);
			Integer supplierId = quotationCalTotalScoreEntity.getSupplierId();
			double priceScore = priceScoreMap.get(supplierId);
			double nonPriceCalTotalScore = nonPriceCalTotalScoreMap.get(supplierId);
			double quotationCalTotalScore = priceScore + nonPriceCalTotalScore;
			quotationCalTotalScoreMap.put(supplierId,quotationCalTotalScore);
			quotationCalTotalScoreEntity.setNonPriceScore(new BigDecimal(quotationCalTotalScore));
//			quotationCalTotalScoreEntity.setNonPriceRating(new BigDecimal(nonPriceCalRankingMap.get(supplierId)));
		}


		//总分排序
		List<Map.Entry<Integer, Double>> list2 = new ArrayList<Map.Entry<Integer, Double>>(quotationCalTotalScoreMap.entrySet()); //转换为list
		list2.sort(new Comparator<Map.Entry<Integer, Double>>() {
			@Override
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		//总分排名
		for (int i = 0; i < list2.size(); i++) {
			for(int j = 0; j < quotationCalRankingList.size(); j++){
				EquPonItScoringDetailEntity_HI quotationCalRankingEntity = quotationCalRankingList.get(j);
				if(quotationCalRankingList.get(j).getSupplierId().intValue() == list2.get(i).getKey().intValue()){
					quotationCalRankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}
	}

	/**
	 * 评分单据审批回调接口
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public EquPonItScoringInfoEntity_HI scoringApprovalCallback(JSONObject parseObject, int userId) throws Exception {
		Integer headerId = parseObject.getIntValue("id");//单据Id
		EquPonItScoringInfoEntity_HI entityHeader = this.getById(headerId);
		String orderStatus = null;//状态
		switch (parseObject.getString("status")) {
			case "REFUSAL":
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
				List<EquPonItProjectInfoEntity_HI> projectEntityList = equPonItProjectInfoDAO_HI.findByProperty("projectId",projectId);
				EquPonItProjectInfoEntity_HI projectEty = projectEntityList.get(0);
				String sourceProjectNumber = projectEty.getSourceProjectNumber();
				List<EquPonItProjectInfoEntity_HI> allVersionProjectEntityList = equPonItProjectInfoDAO_HI.findByProperty("sourceProjectNumber",sourceProjectNumber);
				//修改已截至的报价单状态为已完成
				for(int k = 0; k < allVersionProjectEntityList.size(); k++){
					EquPonItProjectInfoEntity_HI projectEntity = allVersionProjectEntityList.get(k);
					List<EquPonQuotationInfoItEntity_HI> infoEntityList = equPonQuotationInfoItDAO_HI.findByProperty("projectId",projectEntity.getProjectId());
					for(int i = 0; i < infoEntityList.size(); i++){
						EquPonQuotationInfoItEntity_HI infoEntity = infoEntityList.get(i);
						if("STOP".equals(infoEntity.getDocStatus())){
							infoEntity.setDocStatus("COMPLETE");
							infoEntity.setOperatorUserId(userId);
						}
					}
				}

				quotationScoreCalculate(entityHeader.getScoringId(),entityHeader.getProjectId());

				orderStatus = "30";
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

	public BigDecimal emptyToZero(BigDecimal b){
		if(null == b){
			return new BigDecimal(0);
		}
		return b;
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

	public Integer getScoringId(){
		StringBuffer sql = new StringBuffer(EquPonItScoringInfoEntity_HI_RO.QUERY_SCORING_ID_SQL);
		List<EquPonItScoringInfoEntity_HI_RO> list = equPonItScoringInfoEntity_HI_RO.findList(sql);
		System.out.println("【listSize】:" + list.size());
		System.out.println("【scoringId】:" + list.get(0).getScoringId());
		return list.get(0).getScoringId();
	}
}
