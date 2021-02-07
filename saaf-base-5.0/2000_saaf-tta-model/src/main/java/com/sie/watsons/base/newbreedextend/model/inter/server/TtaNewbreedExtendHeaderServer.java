package com.sie.watsons.base.newbreedextend.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.*;

import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendLineEntity_HI;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendHeaderEntity_HI_RO;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendLineEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsLEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.newbreedextend.model.inter.ITtaNewbreedExtendHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaNewbreedExtendHeaderServer")
public class TtaNewbreedExtendHeaderServer extends BaseCommonServer<TtaNewbreedExtendHeaderEntity_HI> implements ITtaNewbreedExtendHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendHeaderServer.class);

	@Autowired
	private ViewObject<TtaNewbreedExtendHeaderEntity_HI> ttaNewbreedExtendHeaderDAO_HI;

	@Autowired
	private BaseViewObject<TtaNewbreedExtendHeaderEntity_HI_RO> ttaNewbreedExtendHeaderDAO_HI_RO;

	@Autowired
	private ViewObject<TtaNewbreedExtendLineEntity_HI> ttaNewbreedExtendLineDAO_HI;

	@Autowired
	private ViewObject<TtaProposalHeaderEntity_HI> ttaProposalHeaderDAO_HI;

	@Autowired
	private ViewObject<TtaTermsHEntity_HI> ttaTermsHDAO_HI;

	@Autowired
	private ViewObject<TtaTermsLEntity_HI> ttaTermsLDAO_HI;

	@Autowired
	private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;

	@Autowired
	private BaseViewObject<TtaNewbreedExtendLineEntity_HI_RO> ttaNewbreedExtendLineDAO_HI_RO;

	@Autowired
	private TtaNewbreedExtendLineServer ttaNewbreedExtendLineServer;

	public TtaNewbreedExtendHeaderServer() {
		super();
	}

	@Override
	public TtaNewbreedExtendHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON,Integer userId) throws Exception {
		SaafToolUtils.validateJsonParms(queryParamJSON, "proposalId");
		StringBuffer sql = new StringBuffer();
		sql.append(TtaNewbreedExtendHeaderEntity_HI_RO.TTA_NBE_LIST);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tneh.proposal_id", "proposalId", sql, paramsMap, "=");
		TtaNewbreedExtendHeaderEntity_HI_RO ttaNewbreedExtendHeaderEntity_hi_ro = ttaNewbreedExtendHeaderDAO_HI_RO.get(sql, paramsMap);
		if(null == ttaNewbreedExtendHeaderEntity_hi_ro){
			saveToNBExtend( queryParamJSON, userId);
			ttaNewbreedExtendHeaderEntity_hi_ro = ttaNewbreedExtendHeaderDAO_HI_RO.get(sql, paramsMap);
		}

		return ttaNewbreedExtendHeaderEntity_hi_ro;
	}

	@Override
	public void saveToNBExtend(JSONObject queryParamJSON,Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaNewbreedExtendHeaderEntity_HI_RO.TTA_NBE_INSERT);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tph.proposal_Id", "proposalId", sql, paramsMap, "=");
		//sql.append(" and tptl.y_terms in ( '新品种宣传推广服务费','新产品宣传推广服务费') ");
		  sql.append("  and exists (select 1\n" +
				  "        from base_lookup_values where lookup_type='TERMS_NAME' and enabled_flag='Y'\n" +
				  "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
				  "      and description='1' and MEANING = nvl(tptl.y_terms,'-1')\n" +
				  "      )") ;
		//paramsMap.put("yTermsB","新品种宣传推广服务费");
		//paramsMap.put("yTermsA","新产品宣传推广服务费");
		//queryParamJSON.put("yTerms", "新品种宣传推广服务费");
		//SaafToolUtils.parperParam(queryParamJSON, "tptl.y_terms", "yTerms", sql, paramsMap, "=");
		TtaNewbreedExtendHeaderEntity_HI_RO ttaNewbreedExtendHeaderEntity_hi_ro = ttaNewbreedExtendHeaderDAO_HI_RO.get(sql, paramsMap);
		if(!SaafToolUtils.isNullOrEmpty(ttaNewbreedExtendHeaderEntity_hi_ro) ){
			TtaNewbreedExtendHeaderEntity_HI ttaNewbreedExtendHeaderEntity_hi = new TtaNewbreedExtendHeaderEntity_HI();
			SaafBeanUtils.copyUnionProperties(ttaNewbreedExtendHeaderEntity_hi_ro, ttaNewbreedExtendHeaderEntity_hi);
			ttaNewbreedExtendHeaderEntity_hi.setOperatorUserId(userId);
			ttaNewbreedExtendHeaderDAO_HI.saveOrUpdate(ttaNewbreedExtendHeaderEntity_hi);
			//保存行
			saveToNBExtendL(queryParamJSON, userId, ttaNewbreedExtendHeaderEntity_hi.getNewbreedExtendHId()) ;

			ttaNewbreedExtendHeaderDAO_HI.fluch();
		}else{
			throw new IllegalArgumentException("请先维护PROPOSAL 或者 TTA TERMS");
		}

	}


	@Override
	public void saveToNBExtendL(JSONObject queryParamJSON,Integer userId,Integer hId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaNewbreedExtendLineEntity_HI_RO.TTA_NBEL_INSERT);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<TtaNewbreedExtendLineEntity_HI> arrayList = new ArrayList<TtaNewbreedExtendLineEntity_HI>();
		List<TtaNewbreedExtendLineEntity_HI_RO> ttaNewbreedExtendHeaderEntityList = ttaNewbreedExtendLineDAO_HI_RO.findList(sql, paramsMap);
		if(ttaNewbreedExtendHeaderEntityList.size()>0){
			for(TtaNewbreedExtendLineEntity_HI_RO v:ttaNewbreedExtendHeaderEntityList){
				TtaNewbreedExtendLineEntity_HI ttaNewbreedExtendLineEntity_HI = new TtaNewbreedExtendLineEntity_HI();
				ttaNewbreedExtendLineEntity_HI.setBreadName(v.getBreadName());
				ttaNewbreedExtendLineEntity_HI.setNewbreedExtendHId(hId);
				ttaNewbreedExtendLineEntity_HI.setOperatorUserId(userId);
				arrayList.add(ttaNewbreedExtendLineEntity_HI);
			}
			ttaNewbreedExtendLineDAO_HI.saveOrUpdateAll(arrayList);

		}else{
			throw new IllegalArgumentException("请先维护新品种属性数据字典");
		}

	}

	@Override
	public JSONObject saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		JSONObject jsonObject = new JSONObject();

		//保存头
		JSONObject nbe = paramsJSON.getJSONObject("nbe");

		//校验数据
		SaafToolUtils.validateJsonParms(nbe, "proposalId");
		TtaProposalHeaderEntity_HI byHi = ttaProposalHeaderDAO_HI.getById(nbe.getInteger("proposalId"));
		if ("Y".equals(byHi.getIsNewConfirm())){
			throw new IllegalArgumentException("当前新品宣传服务费 已经确认过,不能保存");
		}
		if( !("A".equals(byHi.getStatus())) ){
			throw new IllegalArgumentException("当前 单据状态不是 制作中，请勿保存");
		}

		JSONArray dataTableNBExtend = paramsJSON.getJSONArray("dataTableNBExtend");
		TtaNewbreedExtendHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaNewbreedExtendHeaderEntity_HI.class, nbe, ttaNewbreedExtendHeaderDAO_HI, userId);
		ttaNewbreedExtendHeaderDAO_HI.saveOrUpdate(instance);
        ttaNewbreedExtendHeaderDAO_HI.fluch();

		//保存行
		List<TtaNewbreedExtendLineEntity_HI>  ttaNewbreedExtendLineEntity_hiLsit = saveOrUpdateALL(dataTableNBExtend, userId, instance.getNewbreedExtendHId());
		//List<TtaNewbreedExtendLineEntity_HI_RO> instanceL = ttaNewbreedExtendLineServer.findById(paramL, jsonObject.getIntValue("varUserId"));

		//更新条款新品种信息
		saveTtaTermsL(nbe,userId,instance);

		jsonObject.put("nbe",null);
		jsonObject.put("dataTableNBExtend",null);
		return jsonObject;
	}

	@Override
	public List<TtaNewbreedExtendLineEntity_HI> saveOrUpdateALL(JSONArray paramsJSON, int userId,int hId) throws Exception {
		ArrayList<TtaNewbreedExtendLineEntity_HI> objects = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(TtaNewbreedExtendLineEntity_HI_RO.TTA_NBE_STANDARD);
		sql.append(" and tneh.newbreed_Extend_H_Id =:newbreedExtendHId");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("newbreedExtendHId",hId);
		List<TtaNewbreedExtendLineEntity_HI_RO> nblList = ttaNewbreedExtendLineDAO_HI_RO.findList(sql, paramsMap);

		for(int i = 0 ;i<paramsJSON.size();i++){
			boolean flag = true ;
			JSONObject  json = (JSONObject)paramsJSON.get(i) ;
				TtaNewbreedExtendLineEntity_HI instance = SaafToolUtils.setEntity(TtaNewbreedExtendLineEntity_HI.class, (JSONObject)paramsJSON.get(i), ttaNewbreedExtendLineDAO_HI, userId);
				for(int j = 0 ;j<nblList.size();j++){
					if(instance.getBreadName().equals(nblList.get(j).getBreadName())){
						flag = false ;
						instance.setStandard(nblList.get(j).getStandard());
					}
				}
			if(flag){
				instance.setStandard(null);
			}
				objects.add(instance);
		}
		ttaNewbreedExtendLineDAO_HI.saveOrUpdateAll(objects);
		ttaNewbreedExtendLineDAO_HI.fluch();
		return objects;
	}

	/**
	 * 新品种宣传服务费 确认
	 * @param queryParamJSON
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void saveTtaNBEComfirm(JSONObject queryParamJSON,Integer userId) throws Exception{

		//确认动作 1   更新头表数据   新品宣传服务费 已经确认
		SaafToolUtils.validateJsonParms(queryParamJSON, "proposalId","newbreedExtendHId");
		TtaProposalHeaderEntity_HI byHi = ttaProposalHeaderDAO_HI.getById(queryParamJSON.getInteger("proposalId"));
		if ("Y".equals(byHi.getIsNewConfirm())){
			throw new IllegalArgumentException("当前新品宣传服务费 已经确认过,请刷新重试");
		}
		if( !("A".equals(byHi.getStatus())) ){
			throw new IllegalArgumentException("当前 单据状态不是 制作中，请勿确认");
		}
		byHi.setIsNewConfirm("Y");
		byHi.setOperatorUserId(userId);
		ttaProposalHeaderDAO_HI.saveOrUpdate(byHi);

		//确认动作二    更新条款新品服务费的条款字段  收费标准为协定标准的时候才执行

			TtaNewbreedExtendHeaderEntity_HI newbreedExtendHId = ttaNewbreedExtendHeaderDAO_HI.getById(queryParamJSON.getInteger("newbreedExtendHId"));
		if(!SaafToolUtils.isNullOrEmpty(newbreedExtendHId.getNewPayment())) {
			saveTtaTermsL(queryParamJSON, userId, newbreedExtendHId);
		}

	}

	 public void  saveTtaTermsL (JSONObject queryParamJSON,Integer userId,TtaNewbreedExtendHeaderEntity_HI newbreedExtendHId){

		 StringBuffer querySQLSB = new StringBuffer(" from  TtaTermsLEntity_HI  as ttl \n" +
				 "WHERE exists (select 1 from BaseLookupValuesEntity_HI blv where blv.lookupType ='TERMS_NAME' \n" +
				 "                                                   and blv.description ='1' \n" +
				 "                                                   and trim(nvl(ttl.YTerms,-1)) = blv.meaning)\n" +
				 "and   exists (select 1 from BaseLookupValuesEntity_HI blv where blv.lookupType ='TERMS_NAME' \n" +
				 "                                                   and (blv.lookupCode ='2' or blv.lookupCode = '6')\n" +
				 "                                                   and trim(nvl(ttl.incomeType,-1)) = blv.meaning)\n" +
				 "and   ttl.proposalId = :proposalId");
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 paramMap.put("proposalId",queryParamJSON.getInteger("proposalId")) ;
		 List<TtaTermsLEntity_HI> list = ttaTermsLDAO_HI.findList(querySQLSB.toString(), paramMap);

		 StringBuffer queryBaseValue = new StringBuffer("from  BaseLookupValuesEntity_HI   tt  \n" +
				 "     where  tt.lookupType ='TERMS_NAME' \n" +
				 "            and tt.lookupCode ='2'");
		 Map<String, Object> paramMapBase = new HashMap<String, Object>();
		 List<BaseLookupValuesEntity_HI> list1 = baseLookupValuesDAO_HI.findList(queryBaseValue.toString(), paramMapBase);

		 //获取需要计算的税率
		 StringBuffer queryBaseValueTax = new StringBuffer("from  BaseLookupValuesEntity_HI   tt  \n" +
				 "     where  tt.lookupType ='TERM_CATEGORY_TAX' \n" +
				 "            and tt.lookupCode ='01'");
		 Map<String, Object> paramMapBaseTax = new HashMap<String, Object>();
		 List<BaseLookupValuesEntity_HI> listTax = baseLookupValuesDAO_HI.findList(queryBaseValueTax.toString(), paramMapBaseTax);

		 //获取需要计算的税率
		 StringBuffer queryBaseValueTax2 = new StringBuffer("from  BaseLookupValuesEntity_HI   tt  \n" +
				 "     where  tt.lookupValuesId ="+listTax.get(0).getParentLookupValuesId().toString());
		 Map<String, Object> paramMapBaseTax2 = new HashMap<String, Object>();
		 List<BaseLookupValuesEntity_HI> listTax2 = baseLookupValuesDAO_HI.findList(queryBaseValueTax.toString(), paramMapBaseTax);
		 BigDecimal  Tax = new BigDecimal("0.00");
		 if( 0 < list.size()){

		 	if ((list1.get(0).getMeaning()).equals(list.get(0).getIncomeType())){
				TtaTermsHEntity_HI hId = ttaTermsHDAO_HI.getById(list.get(0).getTermsHId());

				if(list.get(0).getTermCategory().equals(listTax2.get(0).getLookupCode())) {
					Tax = Tax.add(new BigDecimal(hId.getBeoiTax())) ;
				}else{
					Tax = Tax.add(new BigDecimal(listTax.get(0).getMeaning())) ;
				}
				StringBuffer sql = new StringBuffer();
				sql.append(TtaNewbreedExtendHeaderEntity_HI_RO.TTA_NBE_CLAUSE_TEXT);
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				SaafToolUtils.parperParam(queryParamJSON, "tne.newbreed_extend_h_id", "newbreedExtendHId", sql, paramsMap, "=");
				TtaNewbreedExtendHeaderEntity_HI_RO ttaNewbreedExtendHeaderEntity_hi_ro = ttaNewbreedExtendHeaderDAO_HI_RO.get(sql, paramsMap);
				if(!SaafToolUtils.isNullOrEmpty(ttaNewbreedExtendHeaderEntity_hi_ro)){
					list.get(0).setTermsSystem(ttaNewbreedExtendHeaderEntity_hi_ro.getClauseText());
					newbreedExtendHId.setCurrentYearTerm(ttaNewbreedExtendHeaderEntity_hi_ro.getClauseText());
				}

				StringBuffer sqlAmount = new StringBuffer();
				sqlAmount.append(TtaNewbreedExtendHeaderEntity_HI_RO.TTA_NBE_CLAUSE_AMOUNT);
				Map<String, Object> paramsAmountMap = new HashMap<String, Object>();
				SaafToolUtils.parperParam(queryParamJSON, "tnh.newbreed_extend_h_id", "newbreedExtendHId", sqlAmount, paramsAmountMap, "=");
				TtaNewbreedExtendHeaderEntity_HI_RO  nbes = ttaNewbreedExtendHeaderDAO_HI_RO.get(sqlAmount, paramsAmountMap);
				if(!SaafToolUtils.isNullOrEmpty(nbes)){
					list.get(0).setYYear(nbes.getClauseAmount() == null ?"":nbes.getClauseAmount().toString());

					BigDecimal bigDecimal = new BigDecimal("100");
					BigDecimal clauseAmount   = nbes.getClauseAmount() == null? new  BigDecimal("0.00"):nbes.getClauseAmount();

					BigDecimal feeNotas = clauseAmount.multiply(bigDecimal)
							.divide(bigDecimal.add(Tax),0,BigDecimal.ROUND_HALF_UP);
					if(SaafToolUtils.isNullOrEmpty(nbes.getClauseAmount())){
						list.get(0).setFeeNotax(null );
						list.get(0).setFeeIntas(null);
					}else{
						list.get(0).setFeeIntas(nbes.getClauseAmount().setScale(0,BigDecimal.ROUND_HALF_UP));
						list.get(0).setFeeNotax(feeNotas);
					}
					list.get(0).setOperatorUserId(userId);
					ttaTermsLDAO_HI.saveOrUpdate(list.get(0));
					if(SaafToolUtils.isNullOrEmpty(nbes.getClauseAmount())){
						newbreedExtendHId.setIncludeTax(null);
						newbreedExtendHId.setExcludeTax(null);
					}else{
						newbreedExtendHId.setIncludeTax(nbes.getClauseAmount().setScale(0,BigDecimal.ROUND_HALF_UP));
						newbreedExtendHId.setExcludeTax(feeNotas);
					}

					newbreedExtendHId.setOperatorUserId(userId);
					ttaNewbreedExtendHeaderDAO_HI.saveOrUpdate(newbreedExtendHId);
				}
			}else{
		 		//按公司标准
				TtaTermsHEntity_HI hId = ttaTermsHDAO_HI.getById(list.get(0).getTermsHId());
				if(list.get(0).getTermCategory().equals(listTax2.get(0).getLookupCode())) {
					Tax = Tax.add(new BigDecimal(hId.getBeoiTax())) ;
				}else{
					Tax = Tax.add(new BigDecimal(listTax.get(0).getMeaning())) ;
				}
				list.get(0).setTermsSystem(list.get(0).getIncomeType());
				newbreedExtendHId.setCurrentYearTerm(list.get(0).getIncomeType());
				StringBuffer sqlAmount = new StringBuffer();
				sqlAmount.append(TtaNewbreedExtendHeaderEntity_HI_RO.TTA_NBE_CLAUSE_AMOUNT_COMPANY);
				Map<String, Object> paramsAmountMap = new HashMap<String, Object>();
				SaafToolUtils.parperParam(queryParamJSON, "tnh.newbreed_extend_h_id", "newbreedExtendHId", sqlAmount, paramsAmountMap, "=");
				TtaNewbreedExtendHeaderEntity_HI_RO  nbes = ttaNewbreedExtendHeaderDAO_HI_RO.get(sqlAmount, paramsAmountMap);
				if(!SaafToolUtils.isNullOrEmpty(nbes)){
					if(!SaafToolUtils.isNullOrEmpty(nbes.getClauseAmount())){
						list.get(0).setYYear(nbes.getClauseAmount().toString());

						BigDecimal bigDecimal = new BigDecimal("100");
						BigDecimal clauseAmount   = nbes.getClauseAmount() == null? new  BigDecimal("0.00"):nbes.getClauseAmount();

						BigDecimal feeNotas = clauseAmount.multiply(bigDecimal)
								.divide(bigDecimal.add(Tax),0,BigDecimal.ROUND_HALF_UP);
						if(SaafToolUtils.isNullOrEmpty(nbes.getClauseAmount())){
							list.get(0).setFeeIntas(null);
							list.get(0).setFeeNotax(null);
						}else{
							list.get(0).setFeeIntas(nbes.getClauseAmount().setScale(0,BigDecimal.ROUND_HALF_UP));
							list.get(0).setFeeNotax(feeNotas);
						}
						list.get(0).setOperatorUserId(userId);
						ttaTermsLDAO_HI.saveOrUpdate(list.get(0));
						if(SaafToolUtils.isNullOrEmpty(nbes.getClauseAmount())){
							newbreedExtendHId.setIncludeTax(null);
							newbreedExtendHId.setExcludeTax(null);
						}else{
							newbreedExtendHId.setIncludeTax(nbes.getClauseAmount().setScale(0,BigDecimal.ROUND_HALF_UP));
							newbreedExtendHId.setExcludeTax(feeNotas);
						}

						newbreedExtendHId.setOperatorUserId(userId);
						ttaNewbreedExtendHeaderDAO_HI.saveOrUpdate(newbreedExtendHId);
					}else{
						list.get(0).setYYear(null);
						list.get(0).setFeeIntas(null);
						list.get(0).setFeeNotax(null);
						list.get(0).setOperatorUserId(userId);
						ttaTermsLDAO_HI.saveOrUpdate(list.get(0));

						newbreedExtendHId.setIncludeTax(null);
						newbreedExtendHId.setExcludeTax(null);
						newbreedExtendHId.setOperatorUserId(userId);
						ttaNewbreedExtendHeaderDAO_HI.saveOrUpdate(newbreedExtendHId);
					}

				}
			}


		 }
	 }
	/**
	 * 取消确认
	 * @param queryParamJSON
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void saveTtaNBECancelComfirm(JSONObject queryParamJSON,Integer userId) throws Exception{
		//取消确认动作 1   更新头表数据   新品种宣传服务费 是否确认为否
		SaafToolUtils.validateJsonParms(queryParamJSON, "proposalId");
		TtaProposalHeaderEntity_HI byHi = ttaProposalHeaderDAO_HI.getById(queryParamJSON.getInteger("proposalId"));

		if("N".equals(byHi.getIsNewConfirm())){
			throw new IllegalArgumentException("当前 NPP 已经是非确认状态,请刷新重试");
		}
		if( !("A".equals(byHi.getStatus())) ){
			throw new IllegalArgumentException("当前 单据状态不是 制作中，请勿取消");
		}
		byHi.setIsNewConfirm("N");
		byHi.setOperatorUserId(userId);
		ttaProposalHeaderDAO_HI.saveOrUpdate(byHi);

		//取消确认动作 2   更新  TTA  TERMS  字段
		List<TtaNewbreedExtendHeaderEntity_HI> newbreedExtendHIds = ttaNewbreedExtendHeaderDAO_HI.findByProperty("proposalId",queryParamJSON.getInteger("proposalId"));

		StringBuffer querySQLSB = new StringBuffer(" from  TtaTermsLEntity_HI  as ttl \n" +
				"WHERE exists (select 1 from BaseLookupValuesEntity_HI blv where blv.lookupType ='TERMS_NAME' \n" +
				"                                                   and blv.description ='1' \n" +
				"                                                   and trim(nvl(ttl.YTerms,-1)) = blv.meaning)\n" +
				"and   exists (select 1 from BaseLookupValuesEntity_HI blv where blv.lookupType ='TERMS_NAME' \n" +
				"                                                   and blv.lookupCode ='2' \n" +
				"                                                   and trim(nvl(ttl.incomeType,-1)) = blv.meaning) ");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<TtaTermsLEntity_HI> list = ttaTermsLDAO_HI.findList(querySQLSB.toString(), paramMap);
		if( 0 < list.size()){

			list.get(0).setTermsSystem(null);
			list.get(0).setYYear(null);
			list.get(0).setFeeIntas(null);
			list.get(0).setFeeNotax(null);
			list.get(0).setOperatorUserId(userId);
			ttaTermsLDAO_HI.saveOrUpdate(list.get(0));

/*			newbreedExtendHId.setCurrentYearTerm(null);
			newbreedExtendHId.setExcludeTax(null);
			newbreedExtendHId.setIncludeTax(null);
			newbreedExtendHId.setOperatorUserId(userId);
			ttaNewbreedExtendHeaderDAO_HI.saveOrUpdate(newbreedExtendHId);*/
		}else{
			throw new IllegalArgumentException("在terms 里没有找新品种宣传服务费，以及协定标准");
		}
/*		if(newbreedExtendHIds.size()>0){
			TtaNewbreedExtendHeaderEntity_HI newbreedExtendHId  = newbreedExtendHIds.get(0);
			ttaNewbreedExtendLineDAO_HI.executeSqlUpdate("delete from tta_newbreed_extend_line t where t.newbreed_extend_h_id =" + newbreedExtendHId.getNewbreedExtendHId().toString());
			ttaNewbreedExtendHeaderDAO_HI.executeSqlUpdate("delete from tta_newbreed_extend_header t where t.newbreed_extend_h_id =" + newbreedExtendHId.getNewbreedExtendHId().toString());
		}*/



	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaNewbreedExtendHeaderEntity_HI byId = ttaNewbreedExtendHeaderDAO_HI.getById(pkId);
		if (byId == null) {
			return;
		}else{
			ttaNewbreedExtendLineDAO_HI.executeSqlUpdate("delete from tta_newbreed_extend_line t where t.newbreed_extend_h_id =" + byId.getNewbreedExtendHId().toString());
			ttaNewbreedExtendHeaderDAO_HI.executeSqlUpdate("delete from tta_newbreed_extend_header t where t.newbreed_extend_h_id =" + byId.getNewbreedExtendHId().toString());
		}
	}
}
