package com.sie.watsons.base.proposal.model.inter.server;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.watsons.base.clause.model.entities.readonly.TtaCollectTypeLineEntity_HI_RO;
import com.sie.watsons.base.clause.model.inter.ITtaClauseTree;
import com.sie.watsons.base.clause.model.inter.ITtaCollectTypeLine;
import com.sie.watsons.base.newbreedextend.model.inter.server.TtaNewbreedExtendHeaderServer;
import com.sie.watsons.base.proposal.model.dao.TtaTermsLDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsHEntity_HI_RO;
import com.sie.watsons.base.proposal.utils.Util;
import com.sie.watsons.base.questionnaire.model.inter.server.TtaQuestionChoiceLineServer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.QueryParameterException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.clause.model.entities.TtaClauseTreeEntity_HI;
import com.sie.watsons.base.clause.model.inter.server.TtaClauseTreeServer;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractLineHEntity_HI;
import com.sie.watsons.base.contract.model.inter.server.TtaContractLineServer;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsLEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsLEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaTermsL;
import com.sie.watsons.base.questionnaire.model.dao.SaafTestQuestionnaireLDAO_HI;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnaire;
import com.yhg.hibernate.core.HibernateHandler;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component("ttaTermsLServer")
public class TtaTermsLServer extends BaseCommonServer<TtaTermsLEntity_HI> implements ITtaTermsL {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsLServer.class);

	@Autowired
	private ViewObject<TtaTermsLEntity_HI> ttaTermsLDAO_HI;

	@Autowired
	private ViewObject<TtaTermsHEntity_HI> ttaTermsHDAO_HI;

	@Autowired
	private BaseViewObject<TtaTermsLEntity_HI_RO> ttaTermsLDAO_HI_RO;

	@Autowired
	private BaseViewObject<TtaTermsHEntity_HI_RO> ttaTermsHDAO_HI_RO;

	@Autowired
	BaseViewObject<JSONObject> commonDAO_HI_DY ;

	@Autowired
	private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;

	@Autowired
	private TtaTermsLDAO_HI ttaTermsLDAO;

	@Autowired
	private ViewObject<TtaContractLineEntity_HI> ttaContractLineDAO_HI;

	@Autowired
	private ViewObject<TtaProposalHeaderEntity_HI> ttaProposalHeaderDAO_HI;

	@Autowired
	private ViewObject<TtaContractLineHEntity_HI> ttaContractLineHDAO_HI;

	@Autowired
	private TtaContractLineServer ttaContractLineServer;

	@Autowired
	private TtaNewbreedExtendHeaderServer ttaNewbreedExtendHeaderServer;

	@Autowired
	private ITtaClauseTree ttaClauseTreeServer ;

	@Autowired
	private ITtaCollectTypeLine ttaCollectTypeLineServer;

	public TtaTermsLServer() {
		super();
	}


	@Override
	public Pagination<TtaTermsLEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaTermsLEntity_HI_RO.TTA_ITEM);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Id", "itemId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "in");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Nbr", "itemNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_Cn", "itemDescCn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_En", "itemDescEn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.group_desc", "groupDesc", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.brand_Cn", "brandCn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.dept_desc", "deptDesc", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.item_Id desc", false);
		Pagination<TtaTermsLEntity_HI_RO> findList = ttaTermsLDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaTermsLEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaTermsLEntity_HI instance = SaafToolUtils.setEntity(TtaTermsLEntity_HI.class, paramsJSON, ttaTermsLDAO_HI, userId);
		ttaTermsLDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public List<TtaTermsLEntity_HI> saveOrUpdateALL(JSONArray paramsJSON,JSONArray paramsDelete, int userId,int hId,int proposalId,String beoiTax) throws Exception {
		//校验
		JSONObject purchaseDiscountJson = null;
		JSONObject returnFreightJson = null;
		//校验是否必填
		StringBuffer HeaderSql = new StringBuffer();
		HeaderSql.append(TtaTermsHEntity_HI_RO.TTA_ITEM_OLD_V);
		Map<String, Object> paramsHeaderMap = new HashMap<String, Object>();
		paramsHeaderMap.put("proposalId", proposalId);
		HeaderSql.append(" and V.proposal_Id = :proposalId");
		TtaTermsHEntity_HI_RO ttaTermsHEntitys = ttaTermsHDAO_HI_RO.get(HeaderSql, paramsHeaderMap);

		//查询对应条款名称 从字典 4 退货处理服务费 5 (集中收货)购货折扣
		StringBuffer queryBaseValue = new StringBuffer("from  BaseLookupValuesEntity_HI   tt  \n" +
				"     where  tt.lookupType ='TERMS_NAME' \n" +
				"            and tt.lookupCode in ('4','5')");
		Map<String, Object> paramMapBase = new HashMap<String, Object>();
		List<BaseLookupValuesEntity_HI> list = baseLookupValuesDAO_HI.findList(queryBaseValue.toString(), paramMapBase);
		List<BaseLookupValuesEntity_HI> returnFreightBases = list.stream().filter(en -> "4".equals(en.getLookupCode())).collect(Collectors.toList());
		List<BaseLookupValuesEntity_HI> purchaseDiscountBases = list.stream().filter(en -> "5".equals(en.getLookupCode())).collect(Collectors.toList());
		if ((0 < returnFreightBases.size()) && (0 < purchaseDiscountBases.size())) {
			for (int i = 0; i < paramsJSON.size(); i++) {
				JSONObject json = (JSONObject) paramsJSON.get(i);
				if ((!SaafToolUtils.isNullOrEmpty(json.getString("yTerms"))) && json.getString("yTerms").equals(purchaseDiscountBases.get(0).getMeaning())) {
					purchaseDiscountJson = json;
				}
				if ((!SaafToolUtils.isNullOrEmpty(json.getString("yTerms"))) && json.getString("yTerms").equals(returnFreightBases.get(0).getMeaning())) {
					returnFreightJson = json;
				}
			}
			if ((!SaafToolUtils.isNullOrEmpty(purchaseDiscountJson)) && ((!SaafToolUtils.isNullOrEmpty(returnFreightJson)))) {
				if ((!SaafToolUtils.isNullOrEmpty(purchaseDiscountJson.getString("yYear")))
						&& "Y".equals(ttaTermsHEntitys.getReturnFreightFlag()) &&
						(SaafToolUtils.isNullOrEmpty(returnFreightJson.getString("yYear")) || SaafToolUtils.isNullOrEmpty(returnFreightJson.getString("incomeType")))) {
					throw new IllegalArgumentException("集中购货不为空 且 供应商对应配置为Y，SO 退货运输费不能为空");
				}
			}
		}

		ArrayList<TtaTermsLEntity_HI> objects = new ArrayList<>();
		for (int i = 0; i < paramsDelete.size(); i++) {
			TtaTermsLEntity_HI instanceDelete = SaafToolUtils.setEntity(TtaTermsLEntity_HI.class, (JSONObject) paramsDelete.get(i), ttaTermsLDAO_HI, userId);
			ttaTermsLDAO_HI.delete(instanceDelete);
			ttaTermsLDAO_HI.fluch();
		}
		StringBuffer queryBaseValue2 = new StringBuffer("from  BaseLookupValuesEntity_HI   tt  \n" +
				"     where  tt.lookupType ='TERMS_NAME' \n" +
				"            and tt.lookupCode in ('1','9')");
		Map<String, Object> paramMapBase2 = new HashMap<String, Object>();
		List<BaseLookupValuesEntity_HI> list2 = baseLookupValuesDAO_HI.findList(queryBaseValue2.toString(), paramMapBase2);
		for (int i = 0; i < paramsJSON.size(); i++) {
			JSONObject json = (JSONObject) paramsJSON.get(i);
			if (!SaafToolUtils.isNullOrEmpty(json.get("clauseId"))) {
				TtaTermsLEntity_HI instance = SaafToolUtils.setEntity(TtaTermsLEntity_HI.class, (JSONObject) paramsJSON.get(i), ttaTermsLDAO_HI, userId);
				instance.setTermsHId(hId);
				instance.setProposalId(proposalId);
				if ("-1".equals(instance.getIncomeType())) {
					instance.setIncomeType(null);
				}
				if ((!SaafToolUtils.isNullOrEmpty(instance.getYYear()))
						&& (SaafToolUtils.isNullOrEmpty(instance.getIsMajor()) || ((!SaafToolUtils.isNullOrEmpty(instance.getIsMajor())) && "Y".equals(instance.getIsMajor())))
				) {
					if (SaafToolUtils.isNullOrEmpty(instance.getIncomeType())) {
						throw new IllegalArgumentException(instance.getYTerms() + "：存在 TTA 值 ，所以收款方式不能为空 或者清空 TTA 值");
					}
				}

				boolean flag = true;
				for (BaseLookupValuesEntity_HI baseLookupValuesEntity_hi : list2) {
					if (baseLookupValuesEntity_hi.getMeaning().equals(instance.getYTerms())) {
						flag = false;
					}
				}
				if (flag && ((!SaafToolUtils.isNullOrEmpty(instance.getIncomeType())) && (("按公司标准").equals(instance.getIncomeType()) || ("按其他协定标准").equals(instance.getIncomeType())))) {
					instance.setTermsSystem(instance.getIncomeType());
				}
				if ((!SaafToolUtils.isNullOrEmpty(instance.getUnit())) && null == instance.getUnitId()) {
					throw new IllegalArgumentException("【" + instance.getYTerms() + "】：单位ID为空 ，请切换成其他收款方式，再切换成所需要的收款方式");
				}
				instance.setOperatorUserId(userId);
				objects.add(instance);
			}

		}
		List<TtaTermsLEntity_HI> returnFee = objects
				.stream().filter(en -> "目标退佣".equals(en.getYTerms())).collect(Collectors.toList());
		BigDecimal def = new BigDecimal("100");
		BigDecimal beoi = new BigDecimal(beoiTax);
		BigDecimal yYear = new BigDecimal("0");
		if (returnFee.size() > 0) {
		for (TtaTermsLEntity_HI object : objects) {
			if ((!SaafToolUtils.isNullOrEmpty(object.getPCode())) && (!SaafToolUtils.isNullOrEmpty(returnFee.get(0).getCode()))
					&& (!SaafToolUtils.isNullOrEmpty(object.getIncomeType())) && object.getPCode().equals(returnFee.get(0).getCode())
					&& (object.getIncomeType().indexOf("含税") > -1) && (object.getIncomeType().indexOf("不含税") == -1) && (!SaafToolUtils.isNullOrEmpty(object.getYYear()))) {

				List<TtaTermsLEntity_HI> returnFees = objects
						.stream().filter(en -> (!SaafToolUtils.isNullOrEmpty(en.getPCode())) &&
								en.getPCode().equals(returnFee.get(0).getCode())
								&& (!SaafToolUtils.isNullOrEmpty(en.getIncomeType()))
								&& (en.getIncomeType().indexOf("不含税") > -1)
								&& (!en.getClauseTreeId().equals(object.getClauseTreeId()))
								&& (en.getClauseId().equals(object.getClauseId()))).collect(Collectors.toList());
				if (returnFees.size() > 0) {
					yYear = new BigDecimal(object.getYYear()).divide((def.add(beoi)).divide(def,4,BigDecimal.ROUND_HALF_UP),0,BigDecimal.ROUND_HALF_UP);
					returnFees.get(0).setYYear(yYear.toString());
				}
			}
		}
	}
		ttaTermsLDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}


	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaTermsLEntity_HI instance = ttaTermsLDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaTermsLDAO_HI.delete(instance);
	}


	@Override
	public List<TtaTermsLEntity_HI_RO> saveFindByRoId(JSONObject queryParamJSON,Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer oldSql = new StringBuffer();
		StringBuffer currentSql = new StringBuffer();
		//是否存在往年
		saveTtaTermsLEntity( queryParamJSON, userId);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap2 = new HashMap<String, Object>();
		if("0".equals(queryParamJSON.getString("oldYear"))){
			sql.append(TtaTermsLEntity_HI_RO.TTA_ITEM);
		}else{
			sql.append(TtaTermsLEntity_HI_RO.TTA_ITEM_OLD);
			paramsMap.put("proposalId", queryParamJSON.getString("proposalId")) ;

			//行表是否复制过往年的数据
			List<TtaTermsHEntity_HI> ttaTermsHList = ttaTermsHDAO_HI.findByProperty("proposalId",queryParamJSON.getInteger("proposalId")) ;
			if(0 == ttaTermsHList.size()){
				throw new IllegalArgumentException("TTA_TERMS_请先保存头信息,无法带出行信息");
			}else{
				TtaTermsHEntity_HI ttaTermsHEntity_HI = ttaTermsHList.get(0);
				//存在往年数据 并且没有初始化, 则初始化数据
				if(null == ttaTermsHEntity_HI.getIsFill() || 0 == ttaTermsHEntity_HI.getIsFill().intValue()){
					//1. 查询往行数据
					//2. 查询今年行数据
					//3. 查询单位
					//4. 查询收取方式
					List<Integer> newArrayList = new ArrayList<Integer>() ;
					paramsMap2.put("proposalId", queryParamJSON.getInteger("proposalId")) ;
					oldSql.append(TtaTermsLEntity_HI_RO.OLD_CURRENT_TTA_ITEM);
					List<TtaTermsLEntity_HI_RO> ttaTermsLEntityOldHiRo = ttaTermsLDAO_HI_RO.findList(oldSql, paramsMap2);
					currentSql.append(TtaTermsLEntity_HI_RO.TTA_ITEM);
					List<TtaTermsLEntity_HI> ttaTermsLEntityCurrentHiRo = ttaTermsLDAO_HI.findByProperty("termsHId",queryParamJSON.getInteger("termsHId"));
					List<TtaTermsLEntity_HI_RO> ttaTermsCurrentMethod = findMethod( queryParamJSON, userId);
					
					queryParamJSON.put("clauseId",ttaTermsLEntityCurrentHiRo.get(0).getClauseId());
					List<TtaCollectTypeLineEntity_HI_RO> ttaCollectTypeLineList = ttaCollectTypeLineServer.findUnit(queryParamJSON) ;

					for (TtaTermsLEntity_HI_RO ttaTermsLEntityHiRo : ttaTermsLEntityOldHiRo) {
						if( (!SaafToolUtils.isNullOrEmpty(ttaTermsLEntityHiRo.getIncomeType()))
								&& (!("请选择".equals(ttaTermsLEntityHiRo.getIncomeType())))
						        && (SaafToolUtils.isNullOrEmpty(ttaTermsLEntityHiRo.getIsMajor()) || "Y".equals(ttaTermsLEntityHiRo.getIsMajor()))
						        && !(newArrayList.contains(ttaTermsLEntityHiRo.getClauseId()))
						        && !SaafToolUtils.isNullOrEmpty(ttaTermsLEntityHiRo.getYYear())
						        && 0 != new BigDecimal(ttaTermsLEntityHiRo.getYYear()).compareTo(BigDecimal.ZERO)){
							List<TtaTermsLEntity_HI> currents = ttaTermsLEntityCurrentHiRo
									.stream().filter(en -> (!SaafToolUtils.isNullOrEmpty(en.getOldClauseId())) && en.getOldClauseId().equals(ttaTermsLEntityHiRo.getClauseId()) && ("Y".equals(ttaTermsLEntityHiRo.getIsMajor()) || SaafToolUtils.isNullOrEmpty(ttaTermsLEntityHiRo.getIsMajor()))).collect(Collectors.toList());
							if(currents.size()>0){
								TtaTermsLEntity_HI currentOne = currents.get(0);
								List<TtaTermsLEntity_HI_RO> currentMethods = ttaTermsCurrentMethod.stream().filter(en -> (!SaafToolUtils.isNullOrEmpty(en.getOldClauseTreeId2())) && en.getpClauseId().equals(currentOne.getClauseId()) && en.getOldClauseTreeId2().equals(ttaTermsLEntityHiRo.getClauseTreeId())).collect(Collectors.toList());
								if(currentMethods.size()>0){
									//是否是同层显示
									TtaTermsLEntity_HI_RO currentMethodOne = currentMethods.get(0);
									newArrayList.add(ttaTermsLEntityHiRo.getClauseId()) ;
									// 0 不是同层显示
									if("0".equals(currentMethodOne.getFlag())){
										setTtaTermsValue( currentOne,
												 currentMethodOne,
												 ttaCollectTypeLineList,
												 ttaTermsLEntityCurrentHiRo,
												  ttaTermsHEntity_HI,
												  null
												 );
									}else{
										//同层显示
										List<TtaTermsLEntity_HI_RO> currentMethodsAll = ttaTermsCurrentMethod.stream().filter(en -> en.getpClauseId().equals(currentOne.getClauseId())).collect(Collectors.toList());
										setTtaTermsValue( currentOne,
												currentMethodOne,
												ttaCollectTypeLineList,
												ttaTermsLEntityCurrentHiRo,
												ttaTermsHEntity_HI,
												null
										);
										currentMethodsAll.remove(currentMethodOne);
										for (TtaTermsLEntity_HI_RO ttaTermsLEntity_hi_ro : currentMethodsAll) {

												TtaTermsLEntity_HI currentOneNew = new TtaTermsLEntity_HI();
												setTtaTermsValue( currentOneNew,
														ttaTermsLEntity_hi_ro,
														ttaCollectTypeLineList,
														ttaTermsLEntityCurrentHiRo,
														ttaTermsHEntity_HI,
														currentOne
														);
										}
									}
								}
							}
						}
					}
/*					System.out.println(JSON.toJSONString(ttaTermsLEntityCurrentHiRo));
					Map<String,Integer> v =  new HashMap<String,Integer>();
					for (TtaTermsLEntity_HI r : ttaTermsLEntityCurrentHiRo) {
						String dd = r.getTermsHId() + r.getIncomeType() + r.getUnit() + r.getClauseId() ;
						if(v.containsKey(dd)){
							v.put(dd,v.get(dd)+1);
						}else{
							v.put(dd,1);
						}
					}
					System.out.println(JSON.toJSONString(v));*/
					ttaTermsLDAO_HI.saveOrUpdateAll(ttaTermsLEntityCurrentHiRo);
					ttaTermsHEntity_HI.setIsFill(1);
					ttaTermsHEntity_HI.setOperatorUserId(userId);
					ttaTermsHDAO_HI.saveOrUpdate(ttaTermsHEntity_HI);
					//hmb 2020.7.31注释
				/*	TtaProposalHeaderEntity_HI tph = ttaProposalHeaderDAO_HI.getById(queryParamJSON.getInteger("proposalId"));
					TtaProposalHeaderEntity_HI oldTph = null;
					List<TtaTermsHEntity_HI> tths = new ArrayList<>();
					if (!SaafToolUtils.isNullOrEmpty(tph.getLastYearProposalId())) {
						 oldTph = ttaProposalHeaderDAO_HI.getById(tph.getLastYearProposalId());
						 tths = ttaTermsHDAO_HI.findByProperty("proposalId",oldTph.getProposalId());
					}

					if ( (!SaafToolUtils.isNullOrEmpty(oldTph)) && tths.size() >0 &&  "1".equals(tths.get(0).getIsSplit()) && SaafToolUtils.isNullOrEmpty(tths.get(0).getIsSplitFlag())) {
						// 更新历史年度的拆分费用
						ttaTermsHDAO_HI.executeSqlUpdate(TtaTermsLEntity_HI_RO.UpdateSplitMoney(tph.getLastYearProposalId(),oldTph.getProposalYear(),"1",tph.getVendorNbr())) ;
						tths.get(0).setIsSplitFlag(1);
						ttaTermsHDAO_HI.saveOrUpdate(tths.get(0));
					}*/

				}
			}

		}
		sql.append("  order   by  bys_major,bys ,isMajor desc,unit") ;
		paramsMap.put("termsHId", queryParamJSON.getInteger("termsHId")) ;
        ttaTermsLDAO_HI.fluch();
		List<TtaTermsLEntity_HI_RO> lineList = ttaTermsLDAO_HI_RO.findList(sql, paramsMap);
		//校验是否必填
		StringBuffer HeaderSql = new StringBuffer();
		HeaderSql.append(TtaTermsHEntity_HI_RO.TTA_ITEM_OLD_V);
		Map<String, Object> paramsHeaderMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "V.proposal_Id", "proposalId", HeaderSql, paramsHeaderMap, "=");
		TtaTermsHEntity_HI_RO ttaTermsHEntitys = ttaTermsHDAO_HI_RO.get(HeaderSql, paramsHeaderMap);

		//查询对应条款名称 从字典 4 退货处理服务费 5 (集中收货)购货折扣
		StringBuffer queryBaseValue = new StringBuffer("from  BaseLookupValuesEntity_HI   tt  \n" +
				"     where  tt.lookupType ='TERMS_NAME' \n" +
				"            and tt.lookupCode in ('4','5')");
		Map<String, Object> paramMapBase = new HashMap<String, Object>();
		List<BaseLookupValuesEntity_HI> list = baseLookupValuesDAO_HI.findList(queryBaseValue.toString(), paramMapBase);
		List<BaseLookupValuesEntity_HI> returnFreightBases = list.stream().filter(en -> "4".equals(en.getLookupCode())).collect(Collectors.toList());
		List<BaseLookupValuesEntity_HI> purchaseDiscountBases = list.stream().filter(en -> "5".equals(en.getLookupCode())).collect(Collectors.toList());
		if ( (0 < returnFreightBases.size()) && (0 < purchaseDiscountBases.size()) ) {
			List<TtaTermsLEntity_HI_RO> purchaseDiscounts = lineList
					.stream().filter(en -> (!SaafToolUtils.isNullOrEmpty(en.getYTerms())) && en.getYTerms().equals(purchaseDiscountBases.get(0).getMeaning())).collect(Collectors.toList());
			List<TtaTermsLEntity_HI_RO> returnFreights = lineList
					.stream().filter(en -> (!SaafToolUtils.isNullOrEmpty(en.getYTerms())) && en.getYTerms().equals(returnFreightBases.get(0).getMeaning())).collect(Collectors.toList());
			if ( (0 < purchaseDiscounts.size()) && (0 < returnFreights.size()) ) {
				if ( (!SaafToolUtils.isNullOrEmpty(purchaseDiscounts.get(0).getYYear()))
					&& "Y".equals(ttaTermsHEntitys.getReturnFreightFlag())){
					returnFreights.get(0).setRequireFlag(true);
				}
			}
		}
		return  lineList;
	}

	public void setTtaTermsValue(TtaTermsLEntity_HI currentOne,
								 TtaTermsLEntity_HI_RO currentMethodOne,
								 List<TtaCollectTypeLineEntity_HI_RO> ttaCollectTypeLineList,
								 List<TtaTermsLEntity_HI> ttaTermsLEntityCurrentHiRo,
								 TtaTermsHEntity_HI ttaTermsHEntity_HI,
								 TtaTermsLEntity_HI first
								 ){
		currentOne.setIncomeType(currentMethodOne.getName());//收取方式
		currentOne.setClauseTreeId(currentMethodOne.getValue());//设置收款方式ID
		currentOne.setYYear(currentMethodOne.getTtaValue().toString());// TTA_VALUE值
		currentOne.setReferenceStandard(currentMethodOne.getDefaultValue());//设置参考标准
		if(SaafToolUtils.isNullOrEmpty(currentOne.getClauseId())){
			currentOne.setYTerms(first.getYTerms());
			currentOne.setYTermsEn(first.getYTermsEn());
			currentOne.setClauseId(currentMethodOne.getpClauseId());
			currentOne.setTermsHId(ttaTermsHEntity_HI.getTermsHId());
			currentOne.setProposalId(ttaTermsHEntity_HI.getProposalId());
			currentOne.setOldClauseId(first.getOldClauseId());
			currentOne.setOiType(first.getOiType());
			currentOne.setTermCategory(first.getTermCategory());
			currentOne.setCode(first.getCode());
			currentOne.setPCode(first.getPCode());
			ttaTermsLEntityCurrentHiRo.add(currentOne);
		}

		//获取所有主单位
		List<TtaCollectTypeLineEntity_HI_RO> ttaCoIsMajor = ttaCollectTypeLineList.stream().filter(en -> "Y".equals(en.getIsMajor()) ).collect(Collectors.toList());
		//查询单位
		List<TtaCollectTypeLineEntity_HI_RO> ttaCo = ttaCoIsMajor.stream().filter(en -> en.getClauseId().equals(currentOne.getClauseTreeId()) ).collect(Collectors.toList());
		currentOne.setUnit(ttaCo.size() == 1?ttaCo.get(0).getUnitValue():"");//设置单位
		currentOne.setRule(ttaCo.size() == 1?ttaCo.get(0).getRule():""); //设置规则
		currentOne.setIsMajor("Y");
		currentOne.setUnitId(ttaCo.size() == 1?ttaCo.get(0).getCollectTypeId():null);
		currentOne.setOrderNo(currentMethodOne.getOrderNo());//设置序号
		currentOne.setOldClauseTreeId2(currentMethodOne.getOldClauseTreeId2());
		//特殊规则
		if("Y".equals(currentOne.getYTerms()) && ("DM".equals(currentOne.getYTerms()) || "FYLER".equals(currentOne.getYTerms()) || (-1 !=currentOne.getYTerms().indexOf("陈列服务费")) )
				&&  (("按公司标准".equals(currentOne.getIncomeType())) || "按其他协定标准".equals(currentOne.getIncomeType()))){
			currentOne.setTermsSystem(currentOne.getIncomeType());
		}
		//获取从单位
		List<TtaCollectTypeLineEntity_HI_RO> subsidiaryUnits = ttaCollectTypeLineList.stream()
				.filter(en -> 1 == ttaCo.size() && null != ttaCo.get(0).getCollectTypeId() && null != en.getParentId() && en.getParentId().equals(ttaCo.get(0).getCollectTypeId()) ).collect(Collectors.toList());

		//添加从单位
		for (TtaCollectTypeLineEntity_HI_RO subsidiaryUnit : subsidiaryUnits) {
			TtaTermsLEntity_HI ttaTermsLEntity_HI = new TtaTermsLEntity_HI();
			ttaTermsLEntity_HI.setUnit(subsidiaryUnit.getUnitValue());
			ttaTermsLEntity_HI.setYYear(subsidiaryUnit.getStandardValue());
			ttaTermsLEntity_HI.setRule(subsidiaryUnit.getRule());
			ttaTermsLEntity_HI.setTtaValueRefer(subsidiaryUnit.getRule());
			ttaTermsLEntity_HI.setClauseTreeId(currentOne.getClauseTreeId());
			ttaTermsLEntity_HI.setProposalId( currentOne.getProposalId());
			ttaTermsLEntity_HI.setClauseId(currentOne.getClauseId());
			ttaTermsLEntity_HI.setOldClauseTreeId2(currentOne.getOldClauseTreeId2());
			ttaTermsLEntity_HI.setOiType(currentOne.getOiType());
			ttaTermsLEntity_HI.setOldClauseId(currentOne.getOldClauseId());
			ttaTermsLEntity_HI.setOrderNo(currentOne.getOrderNo());
			ttaTermsLEntity_HI.setParentUnitId(currentOne.getUnitId());
			ttaTermsLEntity_HI.setUnitId(subsidiaryUnit.getCollectTypeId());
			ttaTermsLEntity_HI.setIsMajor("N");
			ttaTermsLEntity_HI.setTermsHId(ttaTermsHEntity_HI.getTermsHId());
			ttaTermsLEntityCurrentHiRo.add(ttaTermsLEntity_HI);
		}
	}
	@Override
	public List<TtaTermsLEntity_HI_RO> findMethod(JSONObject queryParamJSON,Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaTermsLEntity_HI_RO.TTA_FIND_MENTHOD);
		SaafToolUtils.validateJsonParms(queryParamJSON,"termsHId");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append("  order   by ttc.order_no") ;
		paramsMap.put("termsHId", queryParamJSON.getInteger("termsHId")) ;
		return  ttaTermsLDAO_HI_RO.findList(sql, paramsMap);
	}



	@Override
	public void saveTtaTermsLEntity(JSONObject queryParamJSON,Integer userId) throws Exception{
		//查询存不存在，不存在插入数据
		StringBuffer sqlExists = new StringBuffer();
		Map<String, Object> paramsExists = new HashMap<String, Object>();
		sqlExists.append(TtaTermsLEntity_HI_RO.TTA_ITEM_LINE_EXISTS);
		paramsExists.put("termsHId",queryParamJSON.getInteger("termsHId"));
		TtaTermsLEntity_HI_RO ttaTermsLEntity_hi_ro = ttaTermsLDAO_HI_RO.get(sqlExists, paramsExists);
		if (Integer.valueOf(0).equals(ttaTermsLEntity_hi_ro.getCountNum())) {
			StringBuffer sqlExistsInsert = new StringBuffer();
			Map<String, Object> paramsExistsInsert = new HashMap<String, Object>();
			List<TtaTermsLEntity_HI> ttaTermsLEntity_list = new ArrayList<TtaTermsLEntity_HI>();
			sqlExistsInsert.append(TtaTermsLEntity_HI_RO.TTA_ITEM_LINE_EXISTS_INSERT);
			sqlExistsInsert.append(" and tct.team_framework_id = (select max(ttt.team_framework_id) " +
					"from tta_terms_frame_header ttt," +
					"       tta_proposal_header  tphr " +
					"where ttt.year =:year  " +
					"  and ttt.bill_status =:status " +
					"  and ttt.dept_code =tphr.major_dept_code" +
					"  and tphr.proposal_id = :proposalId)");

			sqlExistsInsert.append(" and nvl(tct.delete_flag,0) = 0 ") ;
			sqlExistsInsert.append(" and tct.business_type =:businessType  order   by tct.order_no") ;
			paramsExistsInsert.put("year", queryParamJSON.getInteger("year")) ;
			paramsExistsInsert.put("status", queryParamJSON.getString("status"));
			paramsExistsInsert.put("businessType", queryParamJSON.getString("businessType"));
			paramsExistsInsert.put("proposalId", queryParamJSON.getString("proposalId"));

			List<TtaTermsLEntity_HI_RO> list = ttaTermsLDAO_HI_RO.findList(sqlExistsInsert, paramsExistsInsert);
			for (TtaTermsLEntity_HI_RO terms:list ){
				TtaTermsLEntity_HI ttaTermsLEntity_hi1 = new TtaTermsLEntity_HI();
				SaafBeanUtils.copyUnionProperties(terms,ttaTermsLEntity_hi1);
				ttaTermsLEntity_hi1.setTermsHId(queryParamJSON.getInteger("termsHId"));
				ttaTermsLEntity_hi1.setOperatorUserId(userId);
				ttaTermsLEntity_hi1.setProposalId(queryParamJSON.getInteger("proposalId"));
				ttaTermsLEntity_list.add(ttaTermsLEntity_hi1);
			}
			ttaTermsLDAO_HI.saveOrUpdateAll(ttaTermsLEntity_list);
			ttaTermsLDAO_HI.fluch();
		}

	}


	@Override
	public JSONObject findValue(JSONObject queryParamJSON,Integer userId) throws Exception {

		//主从单位的TTA 相乘
		JSONArray json = queryParamJSON.getJSONArray("ttaAlls");
		BigDecimal bigDecimalAlls = new BigDecimal("0.00");
		StringBuffer clauseText = new StringBuffer();
		String currentValue = "0.00" ;
		JSONObject result = new JSONObject();
		for (int i = 0;i<json.size();i++){
			currentValue = SaafToolUtils.isNullOrEmpty(json.get(i))?"0.00":json.get(i).toString() ;
			if( 0 == i){
				bigDecimalAlls = bigDecimalAlls.add(new BigDecimal(currentValue) ) ;
			}else{
				bigDecimalAlls = bigDecimalAlls.multiply(new BigDecimal(currentValue)) ;
			}

		}
		if( (0 == bigDecimalAlls.compareTo(BigDecimal.ZERO)) && ( 0 == json.size())){
			bigDecimalAlls = bigDecimalAlls.add(new BigDecimal("1") ) ;
		}
		if (StringUtils.isBlank(queryParamJSON.getString("clauseTreeId"))){
			throw new IllegalArgumentException("请先选择收取方式");
		}
		SaafToolUtils.validateJsonParms(queryParamJSON, "clauseTreeId","proposalId");
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		TtaClauseTreeEntity_HI tct = ttaClauseTreeServer.findById(queryParamJSON.getInteger("clauseTreeId"));

		//1.计算条款
		String isDefault = tct.getIsDefault();
		if("1".equals(isDefault)){ //默认

			List<TtaTermsLEntity_HI> ttaTermsLEntity_HI = ttaTermsLDAO_HI.findByProperty("termsLId", queryParamJSON.getInteger("termsLId"));
			String yYear = ttaTermsLEntity_HI.get(0).getYYear();
			yYear  = Util.fmtMicrometer(yYear);
			clauseText.append(ttaTermsLEntity_HI.get(0).getIncomeType()).append(yYear).append(ttaTermsLEntity_HI.get(0).getUnit());

		}else if("3".equals(isDefault)){   //全局

			String expressionValueCon = tct.getExpressionValueCon();
			//List<Object> list = countSql(sql, expressionValueCon, queryParamJSON);
			List<Map<String, Object>> list2 = countSql2(sql, expressionValueCon, queryParamJSON);
			clauseText.append(list2.size() == 0 ? "" : list2.get(0).get("VALUE")) ;
		} ;
		result.put("clauseText",clauseText);
		
		//2.计算费用
		String expressionValue = tct.getExpressionValueFee();
		if("1".equals(tct.getIsGlobalFee()) && !SaafToolUtils.isNullOrEmpty(expressionValue)){  //全局变量    必须包含  当前 tta term id
			//List<Object> list = countSql(sql2, expressionValue, queryParamJSON);
			List<Map<String, Object>> list2 = countSql2(sql, expressionValue, queryParamJSON);
			result.put("fee",list2.size()==0?new BigDecimal(0.00): bigDecimalAlls.multiply( new BigDecimal (list2.get(0).get("VALUE").toString()) ));
			return result;
			
		}else if("0".equals(tct.getIsGlobalFee())){

			Map<String,Object> map = new HashMap<String,Object>();
			BigDecimal eval  = bigDecimalAlls;

			StringBuffer sqlSection = new StringBuffer();
			sqlSection.append(TtaTermsLEntity_HI_RO.TTA_SECTION);
			sqlSection.append(" and tph.proposal_Id =:proposalId");
			sqlSection.append(" and nvl(tptl.is_major,'Y') = 'Y'");
			Set<String> strings = new HashSet<>();

			if(!SaafToolUtils.isNullOrEmpty(expressionValue)){
				int i=0;
				int end =0;
				String clauseId = "";
				while (true){
					i++;
					int start = expressionValue.indexOf("{",end)+1;
					end  = expressionValue.indexOf("}",end+1);
					if(0 == start ||  50==i){
						break;
					}
					clauseId = clauseId+expressionValue.substring(start,end)+",";
					strings.add("{"+expressionValue.substring(start,end)+"}");
				}
				sqlSection.append(" and tptl.clause_tree_id in (" + clauseId + "-1)");
				map.put("proposalId",queryParamJSON.getInteger("proposalId"));
				List<TtaTermsLEntity_HI_RO> list = ttaTermsLDAO_HI_RO.findList(sqlSection, map);
				for(String s:strings){
					boolean flag = true ;
					for(TtaTermsLEntity_HI_RO v: list){
						if(s.equals(v.getClauseIdS())){
							flag = false ;
							expressionValue = 	expressionValue.replace(s,v.getYYear());
						}
					}
					if(flag){
						expressionValue = 	expressionValue.replace(s,"0");
					}
				}

				ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
				 eval = new BigDecimal(jse.eval(expressionValue).toString());
				 eval = eval.setScale(2,BigDecimal.ROUND_HALF_DOWN);
			}
			result.put("fee",bigDecimalAlls.multiply(eval)) ;
			return  result;
		}else{
			result.put("fee",new BigDecimal(0.00)) ;
			return  result;
		}
	}
	public List<Map<String, Object>> countSql2(StringBuffer sql, String expressionValue,JSONObject queryParamJSON){
        sql.setLength(0);
		sql.append(expressionValue) ;
		HashSet<String> strings = new HashSet<>();
		HashMap<String, Object> stringObjectHashMap = new HashMap<>();
		strings.add(":termsLId");
		strings.add(":proposalId");
		strings.add(":oldClauseTreeId");
		String key = "";
		for (String s:strings){
			if(-1 != sql.indexOf(s)){
				 key = s.substring(1,s.length()) ;
				stringObjectHashMap.put(key,queryParamJSON.getInteger(key));
			}
		}
		List<Map<String, Object>> maps = ttaTermsLDAO.queryNamedSQLForList(sql.toString(), stringObjectHashMap);
		return maps ;
	}
	public List<Object>  countSql (StringBuffer sql, String expressionValue,JSONObject queryParamJSON){
		sql.append(expressionValue) ;
		List<Object> list =(List<Object>)commonDAO_HI_DY.executeQuery(new HibernateHandler() {
			@Override
			public Object doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql.toString().trim());
				try{
					query.setParameter("termsLId",queryParamJSON.getInteger("termsLId"));
					query.setParameter("proposalId",queryParamJSON.getInteger("proposalId"));
					if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getInteger("oldClauseTreeId"))){
						query.setParameter("oldClauseTreeId",queryParamJSON.getInteger("oldClauseTreeId"));
					}else{
						if(-1 != sql.indexOf("oldClauseTreeId")){
							throw new IllegalArgumentException("条款表达式错误，不能含有oldClauseTreeId");
						}
					}
				}catch(QueryParameterException e){
					if(e.getMessage().contains("could not locate named parameter")){
						try{
							query.setParameter("proposalId",queryParamJSON.getInteger("proposalId"));
							if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getInteger("oldClauseTreeId"))){
								query.setParameter("oldClauseTreeId",queryParamJSON.getInteger("oldClauseTreeId"));
							}else{
								if(-1 != sql.indexOf("oldClauseTreeId")){
									throw new IllegalArgumentException("条款表达式错误，不能含有oldClauseTreeId");
								}
							}
						}catch (QueryParameterException eq){
							if(eq.getMessage().contains("could not locate named parameter")){
								try{

									if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getInteger("oldClauseTreeId"))){
										query.setParameter("oldClauseTreeId",queryParamJSON.getInteger("oldClauseTreeId"));
									}else{
										if(-1 != sql.indexOf("oldClauseTreeId")){
											throw new IllegalArgumentException("条款表达式错误，不能含有oldClauseTreeId");
										}
									}

								}catch (QueryParameterException equ){
									if(!equ.getMessage().contains("could not locate named parameter")){
										throw  new QueryParameterException(equ.getMessage());
									}
								}
							}else{
								throw  new QueryParameterException(eq.getMessage());
							}
						}
					}else{
						throw  new QueryParameterException(e.getMessage());
					}
				}

				return query.list();
			}
		});
		
		return list;
	};

	/**
	 * TTA term 确认
	 * @param queryParamJSON
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void saveTtaTermsLComfirm(JSONObject queryParamJSON,Integer userId) throws Exception{
		/**
		 * 确认动作 0    校验
		 * 确认动作 1   重新计算条款的预估费用
		 * 确认动作 2   行表数据复制到   TTA_CONTRACT_LINE   表
		 * 确认动作 3   更新头表数据   TTA TERMS 已经确认
		 * 确认动作 4   校验是否维护   NNP  以及协定标准
		 * 确认动作 5   更新协定标准  如果DM ,促销陈列服务费, FYLER为公司标准自动确认 协定标准
		 * 确认动作 6   计算往年实际费用
		 */
		SaafToolUtils.validateJsonParms(queryParamJSON, "proposalId");
		List<TtaTermsHEntity_HI> ttaTermsHEntity_his = ttaTermsHDAO_HI.findByProperty("proposalId", queryParamJSON.getInteger("proposalId"));
		TtaProposalHeaderEntity_HI byHi = ttaProposalHeaderDAO_HI.getById(queryParamJSON.getInteger("proposalId"));

		// 确认动作 1   重新计算条款的预估费用
		saveReloadCount( queryParamJSON, userId,ttaTermsHEntity_his.get(0)) ;

		//确认动作 2    行表数据复制到   TTA_CONTRACT_LINE   表
		if (!SaafToolUtils.isNullOrEmpty(byHi.getLastYearProposalId())) {
			TtaProposalHeaderEntity_HI byHistory = ttaProposalHeaderDAO_HI.getById(byHi.getLastYearProposalId());
			List<TtaTermsHEntity_HI> ttaTermsHHistory = ttaTermsHDAO_HI.findByProperty("proposalId", byHi.getLastYearProposalId());
			if ( (!SaafToolUtils.isNullOrEmpty(byHistory)) && ttaTermsHHistory.size() >0 ) {
				queryParamJSON.put("isSplit",ttaTermsHHistory.get(0).getIsSplit());
				queryParamJSON.put("oldProposalId",byHi.getLastYearProposalId());
				queryParamJSON.put("oldSplitStatus","1");
				queryParamJSON.put("oldSupplierCode",byHi.getVendorNbr());
				queryParamJSON.put("oldProposalYear",byHistory.getProposalYear());
				queryParamJSON.put("oldFcsSplitSales",ttaTermsHHistory.get(0).getFcsSplitSales());
				queryParamJSON.put("oldFcsSplitPurchse",ttaTermsHHistory.get(0).getFcsSplitPurchse());
			}

		}

		ttaContractLineServer.saveTtaTermsLToContract(queryParamJSON, userId);


		//确认动作 3   更新头表数据   TTA TERMS 已经确认

		if ("Y".equals(byHi.getIsTermsConfirm())){
			throw new IllegalArgumentException("当前TTA TERMS 已经确认过,请刷新重试");
		}
		if ( !( "Y".equals(byHi.getIsPlnConfirm()) )  ){
			throw new IllegalArgumentException("品牌计划没有确认");
		}

		//确认动作 6
		saveOrUpdateCountFee( queryParamJSON, userId) ;
		//某些条款
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append(TtaTermsLEntity_HI_RO.TTA_FEE_SUM);
		sql.append(" and tptl.proposal_id =:proposalId");
		sql.append(" and rownum =1");
		params.put("proposalId",queryParamJSON.getInteger("proposalId"));
		TtaTermsLEntity_HI_RO ttaTermsLEntity_HI_RO = ttaTermsLDAO_HI_RO.get(sql, params);

		if(!SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI_RO)){
			throw new IllegalArgumentException("子项金额大于【"+ttaTermsLEntity_HI_RO.getYTerms()+"】"+"的金额"+ttaTermsLEntity_HI_RO.getFeeIntas().toString());
		}

		//校验是否维护   NNP  以及协定标准
		StringBuffer sqlSure = new StringBuffer();
		Map<String, Object> paramsSure = new HashMap<String, Object>();
		sqlSure.append(TtaTermsLEntity_HI_RO.TTA_TERMS_CHECK);
		sqlSure.append(" and tptl.proposal_id =:proposalId");
		sqlSure.append(" and rownum = 1") ;
		paramsSure.put("proposalId",queryParamJSON.getInteger("proposalId"));
		TtaTermsLEntity_HI_RO listSure = ttaTermsLDAO_HI_RO.get(sqlSure, paramsSure);

		if(!SaafToolUtils.isNullOrEmpty(listSure)){
			throw new IllegalArgumentException(listSure.getText());
		}

		//更新协定标准  如果DM ,促销陈列服务费, FYLER为公司标准自动确认 协定标准
		StringBuffer sqlDm = new StringBuffer();
		Map<String, Object> paramsDm = new HashMap<String, Object>();
		sqlDm.append(TtaTermsLEntity_HI_RO.TTA_TERMS_DM);
		sqlDm.append(" and tptl.proposal_id =:proposalId");
		sqlDm.append(" and rownum = 1") ;
		paramsDm.put("proposalId",queryParamJSON.getInteger("proposalId"));
		TtaTermsLEntity_HI_RO listDm = ttaTermsLDAO_HI_RO.get(sqlDm, paramsDm);

		if(SaafToolUtils.isNullOrEmpty(listDm)){
			byHi.setIsDepartConfirm("Y");
		}else{
			if("N".equals(byHi.getIsDepartConfirm())){
				throw new IllegalArgumentException("请确认部门协定标准");
			}
		}

		//是否自动确认NPP 服务费用
		StringBuffer sqlNpp = new StringBuffer();
		Map<String, Object> paramsNpp = new HashMap<String, Object>();
		sqlNpp.append(TtaTermsLEntity_HI_RO.TTA_TERMS_NWE);
		sqlNpp.append(" and tptl.proposal_id =:proposalId");
		sqlNpp.append(" and rownum = 1") ;
		paramsNpp.put("proposalId",queryParamJSON.getInteger("proposalId"));
		TtaTermsLEntity_HI_RO listNpp = ttaTermsLDAO_HI_RO.get(sqlNpp, paramsNpp);

		if(SaafToolUtils.isNullOrEmpty(listDm)){
			byHi.setIsNewConfirm("Y");
		}
		byHi.setIsTermsConfirm("Y");
		//byHi.setOperatorUserId(userId);
		ttaProposalHeaderDAO_HI.saveOrUpdate(byHi);
	}

	/**
	 * 取消确认
	 * @param queryParamJSON
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void saveTtaTermsLCancelComfirm(JSONObject queryParamJSON,Integer userId) throws Exception{


		ArrayList<TtaContractLineHEntity_HI> objects = new ArrayList<TtaContractLineHEntity_HI>();
		//取消确认动作 1   更新头表数据   TTA TERMS 是否确认为否
		SaafToolUtils.validateJsonParms(queryParamJSON, "proposalId","termsHId");
		TtaProposalHeaderEntity_HI byHi = ttaProposalHeaderDAO_HI.getById(queryParamJSON.getInteger("proposalId"));
		List<TtaTermsLEntity_HI> ttaTermsLEntityList = ttaTermsLDAO_HI.findByProperty("proposalId",queryParamJSON.getInteger("proposalId"));
		for(TtaTermsLEntity_HI ttl :ttaTermsLEntityList){
		    if(!SaafToolUtils.isNullOrEmpty(ttl.getYYearOld())){
                ttl.setYYear(ttl.getYYearOld());
            }
		    if(! (  !SaafToolUtils.isNullOrEmpty(ttl.getIncomeType())
				&&  ("按公司标准".equals(ttl.getIncomeType()) || ("按其他协定标准".equals(ttl.getIncomeType())    )
			    )
			     )){
				ttl.setTermsSystem(null);
			}
		    //System.out.println(ttl.getIncomeType());
			//ttl.setTermsSystem(null);
			ttl.setFeeIntas(null);
			ttl.setFeeNotax(null);

		}
		if("N".equals(byHi.getIsTermsConfirm())){
			throw new IllegalArgumentException("当前TTA TERMS 已经是非确认状态,请刷新重试");
		}
		if(!("A".equals(byHi.getStatus()))){
			throw new IllegalArgumentException("当前 单据状态不是 制作中，请勿取消");
		}
		//更新协定标准  如果DM ,促销陈列服务费, FYLER为公司标准自动确认 协定标准
		StringBuffer sqlDm = new StringBuffer();
		Map<String, Object> paramsDm = new HashMap<String, Object>();
		sqlDm.append(TtaTermsLEntity_HI_RO.TTA_TERMS_DM);
		sqlDm.append(" and tptl.proposal_id =:proposalId");
		sqlDm.append(" and rownum = 1") ;
		paramsDm.put("proposalId",queryParamJSON.getInteger("proposalId"));
		TtaTermsLEntity_HI_RO listDm = ttaTermsLDAO_HI_RO.get(sqlDm, paramsDm);

		if(SaafToolUtils.isNullOrEmpty(listDm)){
			byHi.setIsDepartConfirm("N");
		}
		byHi.setIsTermsConfirm("N");
		ttaProposalHeaderDAO_HI.saveOrUpdate(byHi);
		ttaTermsLDAO_HI.saveOrUpdateAll(ttaTermsLEntityList);
		//取消确认动作 2   取消问卷调查
		/**
		 *
		 * 待补充
		 */
/*		if("Y".equals(byHi.getIsQuestConfirm())){
			Integer proposalId = queryParamJSON.getInteger("proposalId");
			ttaQuestionChoiceLineServer.deleteCancelQuestionTest(proposalId);
		}*/

		//取消NPP 服务费
		if("Y".equals(byHi.getIsNewConfirm())){
			ttaNewbreedExtendHeaderServer.saveTtaNBECancelComfirm(queryParamJSON,userId);
		}


		//取消动作 3   把 TTA_CONTRACT_LIEN →   TTA_CONTRACT_LIEN_H  并且删除数据
		List<TtaContractLineEntity_HI> byProperty = ttaContractLineDAO_HI.findByProperty("proposalId", queryParamJSON.getInteger("proposalId"));
		for(TtaContractLineEntity_HI t:byProperty){
			TtaContractLineHEntity_HI ttaContractLineHEntity_hi = new TtaContractLineHEntity_HI();
			SaafBeanUtils.copyUnionProperties(t,ttaContractLineHEntity_hi);
			ttaContractLineHEntity_hi.setOperatorUserId(userId);
			objects.add(ttaContractLineHEntity_hi);
		}
		ttaContractLineHDAO_HI.saveOrUpdateAll(objects);
		ttaContractLineDAO_HI.deleteAll(byProperty);

	}

	public JSONObject saveOrUpdateCountFee(JSONObject paramsJSON, Integer userId) throws Exception {
		JSONObject param=new JSONObject();

		//调用存储过程
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		paramsMap.put("Id",paramsJSON.getInteger("proposalId"));
		paramsMap.put("userId",userId);
		resultMap = ttaTermsLDAO.callCountFee(paramsMap);
		Integer xFlag = (Integer) resultMap.get("xFlag");
		String xMsg = (String) resultMap.get("xMsg");
		//Integer newId = (Integer) resultMap.get("newId");
		JSONObject result=new JSONObject(resultMap);
		//result.put("teamFrameworkId",newId);
		if (xFlag!=1) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
			throw new Exception(xMsg);
		};
		return result;
	}

	private  void saveReloadCount(JSONObject queryParamJSON,Integer userId,TtaTermsHEntity_HI ttaTermsHEntity_hi) throws Exception{
		Map<String, Object> TtaTermsLEntityHiMap = new HashMap<>();
		StringBuffer TtaTermsLEntityHiSql = new StringBuffer();
		TtaTermsLEntityHiSql.append("from  TtaTermsLEntity_HI where   proposalId =:proposalId") ;
		TtaTermsLEntityHiMap.put("proposalId",queryParamJSON.getInteger("proposalId"));
		List<TtaTermsLEntity_HI> ttaTermsLEntityListAll = ttaTermsLDAO_HI.findList(TtaTermsLEntityHiSql, TtaTermsLEntityHiMap);
		List<TtaTermsLEntity_HI> ttaTermsLEntityList =  ttaTermsLEntityListAll.stream().filter(en -> ("Y".equals(en.getIsMajor()) || SaafToolUtils.isNullOrEmpty(en.getIsMajor())) && !SaafToolUtils.isNullOrEmpty(en.getIncomeType()) && !"按公司标准".equals(en.getIncomeType())  && !"按其他协定标准".equals(en.getIncomeType())).collect(Collectors.toList());
		String beoiTax = ttaTermsHEntity_hi.getBeoiTax();
		BigDecimal bigDecimaBeoiTax = new BigDecimal(beoiTax);
		JSONObject jsonObject = new JSONObject();

		//获取需要计算的税率
		StringBuffer queryBaseValueTax = new StringBuffer("from  BaseLookupValuesEntity_HI   tt \n" +
				"      where  enabledFlag = 'Y'\n" +
				"             and deleteFlag = 0\n" +
				"             and startDateActive < sysdate\n" +
				"             and (endDateActive >= sysdate or endDateActive is null)\n" +
				"             and systemCode = 'BASE'\n" +
				"             and (lookupType = 'TERMS_FREE' or lookupType = 'TERM_CATEGORY_TAX' or lookupType = 'TERM_CATEGORY')");
		Map<String, Object> paramMapBaseTax = new HashMap<String, Object>();;
		List<BaseLookupValuesEntity_HI> listTax = baseLookupValuesDAO_HI.findList(queryBaseValueTax.toString(), paramMapBaseTax);

		List<BaseLookupValuesEntity_HI> collect = listTax.stream().filter(en -> "TERM_CATEGORY".equals(en.getLookupType()) && "3".equals(en.getLookupCode())).collect(Collectors.toList());
		List<BaseLookupValuesEntity_HI> collectTax = listTax.stream().filter(en -> "TERM_CATEGORY_TAX".equals(en.getLookupType()) && "01".equals(en.getLookupCode())).collect(Collectors.toList());
		List<BaseLookupValuesEntity_HI> getTermsFreeIt = null ;
		List<BaseLookupValuesEntity_HI> getTermsFreeYterms = null ;
		BigDecimal feeNotax= null ;
		BigDecimal feeIntas = null ;
		BigDecimal bigDecimal = new BigDecimal("100");
		JSONArray returnArray = queryParamJSON.getJSONArray("returnArray");
		//获取目标退佣
		Map target =    new HashMap<String,Object>() ;
		target.put("YTerms","目标退佣");
		target.put("proposalId",queryParamJSON.getInteger("proposalId"));
		List<TtaTermsLEntity_HI> returnFee = ttaTermsLDAO_HI.findByProperty(target);
		//List<TtaTermsLEntity_HI> returnFee = ttaTermsLEntityList.stream().filter(en -> "目标退佣".equals(en.getYTerms())).collect(Collectors.toList());
		for(int i = 0 ;i<ttaTermsLEntityList.size();i++) {
            if(!SaafToolUtils.isNullOrEmpty(ttaTermsLEntityList.get(i).getClauseTreeId()) && !SaafToolUtils.isNullOrEmpty(ttaTermsLEntityList.get(i).getYYear())){
            TtaTermsLEntity_HI ttaTermsLEntity_HI = ttaTermsLEntityList.get(i);
            //如果为红票则计算红票收益  or  续签供应商往年为红票则不计算
			if(   ("B".equals(ttaTermsHEntity_hi.getInvoiceType())  && SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("oldInvoiceType")) )
                    || ( !SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("oldInvoiceType")) && !"B".equals(queryParamJSON.getString("oldInvoiceType")) && "B".equals(ttaTermsHEntity_hi.getInvoiceType()))  ){
				//1.一般购货折扣的红票收益
				if("一般购货折扣".equals(ttaTermsLEntity_HI.getYTerms())){
					if(!SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getYYear())){
						ttaTermsLEntity_HI.setYYear( (new BigDecimal(ttaTermsLEntity_HI.getYYear())).add(new BigDecimal(queryParamJSON.getString("same"))).toString()   );
					}else{
						ttaTermsLEntity_HI.setYYear(queryParamJSON.getString("same"));
					}
					ttaTermsLDAO_HI.saveOrUpdate(ttaTermsLEntity_HI);
					ttaTermsLDAO_HI.fluch();
				}
				//2.  目标退佣的红票收益
				for(int s = 0;s<returnArray.size();s++){
					JSONObject jsb = (JSONObject)returnArray.get(s) ;
					//计算含税
					if(! SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getClauseTreeId())
					   && !SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getClauseId())
					   &&  ttaTermsLEntity_HI.getClauseTreeId().toString().equals(jsb.getString("clauseTreeId"))
					   &&  ttaTermsLEntity_HI.getClauseId().toString().equals(jsb.getString("clauseId"))){

						ttaTermsLEntity_HI.setYYear(jsb.getString("end"));
						//通过含税计算不含税的
						ttaTermsLDAO_HI.saveOrUpdate(ttaTermsLEntity_HI);
						ttaTermsLDAO_HI.fluch();
						if(!SaafToolUtils.isNullOrEmpty(returnFee.get(0).getCode())
						   && !SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getPCode())
						   && !SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getIncomeType())
						   && returnFee.get(0).getCode().equals(ttaTermsLEntity_HI.getPCode())
						   && ttaTermsLEntity_HI.getIncomeType().indexOf("不含税")>-1){

							List<TtaTermsLEntity_HI> returnFees = ttaTermsLEntityList.stream().filter(en -> !SaafToolUtils.isNullOrEmpty(en.getPCode())
							                                                                                && !SaafToolUtils.isNullOrEmpty(en.getIncomeType())
																											&& en.getIncomeType().indexOf("含税")>-1
							                                                                                && ! (en.getClauseTreeId().equals(ttaTermsLEntity_HI.getClauseTreeId()))
							                                                                                &&  en.getClauseId().equals(ttaTermsLEntity_HI.getClauseId())
																											&&  en.getPCode().equals(returnFee.get(0).getCode())).collect(Collectors.toList());
							if(returnFees.size()>0){
								String tax = new BigDecimal(ttaTermsLEntity_HI.getYYear()).multiply( (bigDecimal.add(new BigDecimal(ttaTermsHEntity_hi.getBeoiTax()))).divide(bigDecimal)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
								returnFees.get(0).setYYear(tax);
								ttaTermsLDAO_HI.saveOrUpdate(returnFees.get(0));
								ttaTermsLDAO_HI.fluch();
							}
						}


					}


				}

			}
            jsonObject.put("clauseTreeId", ttaTermsLEntityList.get(i).getClauseTreeId());
            jsonObject.put("termsLId", ttaTermsLEntityList.get(i).getTermsLId());
            jsonObject.put("proposalId", ttaTermsLEntityList.get(i).getProposalId());
            jsonObject.put("oldClauseTreeId", ttaTermsLEntityList.get(i).getOldClauseTreeId2());
            JSONArray objects = new JSONArray();
            objects = findIsMajorNo(ttaTermsLEntityListAll, ttaTermsLEntityList.get(i));
            jsonObject.put("ttaAlls", objects);
            JSONObject value = findValue(jsonObject, userId);
            System.out.println(ttaTermsLEntityList.get(i).getYTerms()+"xxxxx"+ JSONObject.toJSONString(value));
            //费用为  0  和不为0 的区别
            if (0 != value.getBigDecimal("fee").compareTo(BigDecimal.ZERO)) {
                //免费货品部分收取方式 计算不含税
                getTermsFreeIt = listTax.stream().filter(en -> !SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getIncomeType())
                        && ttaTermsLEntity_HI.getIncomeType().equals(en.getMeaning())
                        && "TERMS_FREE".equals(en.getLookupType())
                        && ("01".equals(en.getLookupCode()) || ("02".equals(en.getLookupCode())))).collect(Collectors.toList());

                getTermsFreeYterms = listTax.stream().filter(en -> !SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getYTerms())
                        && ttaTermsLEntity_HI.getYTerms().indexOf(en.getMeaning()) != -1
                        && "TERMS_FREE".equals(en.getLookupType())
                        && ("03".equals(en.getLookupCode()))).collect(Collectors.toList());
                if (collect.size() > 0) {
                    //业务类型 为 业务服务费 则  税率 为 6
                    if (ttaTermsLEntityList.get(i).getTermCategory().equals(collect.get(0).getLookupCode())) {

                        if (getTermsFreeIt.size() > 0 && getTermsFreeYterms.size() > 0) {
                            feeNotax = value.getBigDecimal("fee");
                            feeIntas = feeNotax.multiply((bigDecimal.add(new BigDecimal(collectTax.get(0).getMeaning()))).divide(bigDecimal)).setScale(0, BigDecimal.ROUND_HALF_UP);
							feeNotax =feeNotax.setScale(0, BigDecimal.ROUND_HALF_UP);
                        } else {
                            feeIntas = value.getBigDecimal("fee");
                            feeNotax = feeIntas.divide((bigDecimal.add(new BigDecimal(collectTax.get(0).getMeaning()))).divide(bigDecimal),0, BigDecimal.ROUND_HALF_UP);
							feeIntas = feeIntas.setScale(0, BigDecimal.ROUND_HALF_UP);
                        }
                    } else {
                        if (getTermsFreeIt.size() > 0 && getTermsFreeYterms.size() > 0) {
                            feeNotax = value.getBigDecimal("fee");
                            feeIntas = feeNotax.multiply((bigDecimal.add(bigDecimaBeoiTax)).divide(bigDecimal)).setScale(0, BigDecimal.ROUND_HALF_UP);
							feeNotax = feeNotax.setScale(0, BigDecimal.ROUND_HALF_UP);
                        } else {
                            feeIntas = value.getBigDecimal("fee");
                            feeNotax = feeIntas.divide((bigDecimal.add(bigDecimaBeoiTax)).divide(bigDecimal),0, BigDecimal.ROUND_HALF_UP);
							feeIntas =  feeIntas.setScale(0, BigDecimal.ROUND_HALF_UP);
                        }


                    }
                } else {
                    throw new IllegalArgumentException("TERM_CATEGORY  字典失效，或者不存在,请刷新页面重新尝试");
                }
                ttaTermsLEntityList.get(i).setFeeIntas(feeIntas);
                ttaTermsLEntityList.get(i).setFeeNotax(feeNotax);
            } else {
                ttaTermsLEntityList.get(i).setFeeIntas(null);
                ttaTermsLEntityList.get(i).setFeeNotax(null);
            }

            if (!SaafToolUtils.isNullOrEmpty(ttaTermsLEntity_HI.getYYear())) {
                ttaTermsLEntityList.get(i).setTermsSystem(value.getString("clauseText"));
            }
        }
			ttaTermsLEntityList.get(i).setOperatorUserId(userId);
			ttaTermsLDAO_HI.saveOrUpdate(ttaTermsLEntityList.get(i));
			ttaTermsLDAO_HI.fluch();
		}
	}

	private  JSONArray  findIsMajorNo (List<TtaTermsLEntity_HI> ttaTermsLEntityList,TtaTermsLEntity_HI tte) throws Exception{
		JSONArray objects = new JSONArray();
		for(TtaTermsLEntity_HI th: ttaTermsLEntityList){
			if( ! SaafToolUtils.isNullOrEmpty(th.getParentUnitId())  && th.getParentUnitId().equals(tte.getUnitId())){
				objects.add(th.getYYear());
			}
/*			if((SaafToolUtils.isNullOrEmpty(th.getIsMajor()) || "N".equals(th.getIsMajor()))
			   && th.getClauseId().equals(tte.getClauseId())){
				objects.add(th.getYYear());
			}*/
		}
		return objects ;
	}


}
