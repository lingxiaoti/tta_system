package com.sie.watsons.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractHeaderEntity_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaTermsH;
import com.sie.watsons.base.proposal.utils.Util;
import com.sie.watsons.report.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import com.sie.watsons.report.model.entities.readonly.TtaContractLineReportEntity_HI_RO;
import com.sie.watsons.report.model.inter.ITtaContractLineReport;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

@Component("ttaContractLineReportServer")
public class TtaContractLineServerReport implements ITtaContractLineReport{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractLineServerReport.class);

	@Autowired
	private BaseCommonDAO_HI<TtaContractLineEntity_HI> ttaContractLineEntity_HI;

	@Autowired
	private BaseCommonDAO_HI<TtaContractHeaderEntity_HI> ttaContractHeaderDao;

	@Autowired
	private ITtaTermsH ttaTermsHServer;

	@Autowired
	private BaseViewObject<TtaContractLineReportEntity_HI_RO> ttaContractLineReportDAO_HI_RO;
	
	@Autowired
	private BaseCommonDAO_HI<BaseLookupValuesEntity_HI> baseDao;

	public TtaContractLineServerReport() {
		super();
	}

	@Autowired
	private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;



	@SuppressWarnings("all")
	@Override
	public JSONObject findTermsAgrement(JSONObject queryParamJSON) throws Exception{
		String venderCodeVal = queryParamJSON.getString("vendorNbrValue");
		JSONObject result = new JSONObject();
		String resource = queryParamJSON.getString("resource");
		Integer contractHId = queryParamJSON.getInteger("contractHId");
		TtaContractHeaderEntity_HI entity = null;
		if (contractHId != null) {
			entity = ttaContractHeaderDao.getById(contractHId);
		}
		String orderNo = null;
		if (entity != null) {
			orderNo = "BIC-" + DateUtil.date2Str(entity.getCreationDate(), "yyyyMMddHHmmss") + "-" + entity.getProposalCode() + "_" + entity.getProposalVersion() + "-";
		}
		//组装成orderNo
		if ("vender_contract".equalsIgnoreCase(resource)) {
			JSONObject vendorContractParams = queryParamJSON.getJSONObject("vendorContractParams");
			String venderCode = venderCodeVal = vendorContractParams.getString("venderCode");
			LOGGER.info("查询供应商条款信息");
			orderNo += venderCode;
			result = getVenderContract(queryParamJSON);
		} else {
			LOGGER.info("查询proposal 条款信息.");
			if (entity != null) {
				orderNo += entity.getVendorNbr();
				venderCodeVal = entity.getVendorNbr();
			}
			result =  getPropsoalContract(queryParamJSON);
		}
		result.put("orderNo", orderNo);
		//查询是否需要甲乙方换位显示，快码COMPANY_CONVERT
		boolean companyConvertFlag = false;
		List<Map<String, Object>> listMap = ttaContractHeaderDao.queryNamedSQLForList(TtaContractLineReportEntity_HI_RO.QUERY_COMPANY_CONVERT, new HashMap<String, Object>());
		if (listMap != null && listMap.get(0).get("MEANING") != null) {
			String[] companyArr = (listMap.get(0).get("MEANING") + "").split(",");
			for (String company : companyArr) {
				if (venderCodeVal.equalsIgnoreCase(company)) {
					companyConvertFlag = true;
					break;
				}
			}
		}
		result.put("companyConvertFlag", companyConvertFlag);

		return result;
	}

	@SuppressWarnings("all")
	private JSONObject getVenderContract(JSONObject queryParamJSON) throws Exception{
		/*0 = {HashMap$Node@25361} "proposalCode" -> "201908220055"
		1 = {HashMap$Node@25362} "venderCode" -> "P201912050001"
		2 = {HashMap$Node@25363} "proposalYear" -> "2019"
		3 = {HashMap$Node@25364} "proposalId" -> "360"
		4 = {HashMap$Node@25365} "contractHId" -> "121"*/

		JSONObject vendorContractParams = queryParamJSON.getJSONObject("vendorContractParams");
		Integer proposalId = vendorContractParams.getInteger("proposalId");
		String year = vendorContractParams.getString("proposalYear");
		Integer contractHId = vendorContractParams.getInteger("contractHId");
		String venderCode = vendorContractParams.getString("venderCode");
		String proposalCode = vendorContractParams.getString("proposalCode");
		String proposalVersion = vendorContractParams.getString("proposalVersion");

		if (proposalId == null || contractHId == null || year == null || StringUtils.isBlank(venderCode)) {
			Assert.notNull(null, "必传参数不能为空");
		}
		JSONObject result = new JSONObject();
		//1.查询条款类别信息
		List<BaseLookupValuesEntity_HI> lookUpList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERM_CATEGORY").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (lookUpList == null || lookUpList.isEmpty()) {
			LOGGER.info(".findTermsAgrement 没有设置快码");
			return null;
		}
		//3.销售方式
		List<BaseLookupValuesEntity_HI> saleWayList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "SALE_WAY").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		saleWayList.sort(new Comparator<BaseLookupValuesEntity_HI>() {
			@Override
			public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
				return (o1.getOrderNo() + "").compareTo(o2.getOrderNo() + "");
			}
		});
		result.put("saleWayList", saleWayList);
		//查询供应商对应的合同条款信息
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("proposalId", proposalId);
		paramsMap.put("contractHId", contractHId);
		paramsMap.put("venderCode", venderCode);
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(TtaContractLineReportEntity_HI_RO.getContractVendorInfoSql());


		List<Map<String, Object>> resultList = new ArrayList<>();
		List<TtaContractLineReportEntity_HI_RO> list = null;
		TtaTermsHEntity_HI_RO entity_hi_ro = ttaTermsHServer.saveFindByRoId(queryParamJSON);
		//查询合同头的采购额
		String sql = "select a.purchase, a.sales  from tta_contract_line a inner join tta_contract_header b on a.contract_h_id = b.contract_h_id \n" +
				"where b.contract_h_id =:contractHId and a.vendor_code=:venderCode and rownum = 1";
		HashMap<String, Object> queryContractMap = new HashMap<>();
		queryContractMap.put("contractHId", contractHId);
		queryContractMap.put("venderCode", venderCode);
		List<Map<String, Object>> ttaValueList = ttaContractLineEntity_HI.queryNamedSQLForList(sql, queryContractMap);
		if (ttaValueList != null && !ttaValueList.isEmpty()) {
			entity_hi_ro.setFcsPurchse(new BigDecimal(ttaValueList.get(0).get("PURCHASE") + "").setScale(0, BigDecimal.ROUND_HALF_UP));
		}
		//proposal 贸易条款，折扣折让项目首行显示
		String termsCn = "";
		//折扣折让项目首行配置
		TtaContractLineReportEntity_HI_RO entityRo = new TtaContractLineReportEntity_HI_RO();
		HashMap<String, String>  purchaseMap = new HashMap<>();
		List<BaseLookupValuesEntity_HI> termsPurchaseList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERMS_PURCHASE").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (termsPurchaseList != null && !termsPurchaseList.isEmpty()) {
			termsPurchaseList.sort(new Comparator<BaseLookupValuesEntity_HI>() {
				@Override
				public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
					return  ("" + o1.getLookupCode()).compareTo(o2.getLookupCode() + "");
				}
			});
			termsPurchaseList.forEach(item->{
				purchaseMap.put("" + item.getLookupCode(), item.getMeaning());
			});

			termsCn = purchaseMap.get(year);
			if (StringUtils.isBlank(termsCn)) {
				termsCn = termsPurchaseList.get(0).getMeaning();
			}
			String fcsPurchse = "";
			if (entity_hi_ro != null) {
				fcsPurchse = entity_hi_ro.getFcsPurchse() == null ?  "" : Util.fmtMicrometer(entity_hi_ro.getFcsPurchse() + "");
			}
			entityRo.setTermCategory("1");
			entityRo.setTermsCn(termsCn);
			entityRo.setTermsEn("");
			entityRo.setTermsSystem(fcsPurchse); //采购金额
		}

		//TERMS_PAY_DAY 付款期（天数）
		TtaContractLineReportEntity_HI_RO entityPayRo = new TtaContractLineReportEntity_HI_RO();
		List<BaseLookupValuesEntity_HI> termsPayDayList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERMS_PAY_DAY").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (termsPayDayList != null && !termsPayDayList.isEmpty()) {
			purchaseMap.clear();
			termsPayDayList.sort(new Comparator<BaseLookupValuesEntity_HI>() {
				@Override
				public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
					return  ("" + o1.getLookupCode()).compareTo(o2.getLookupCode() + "");
				}
			});
			termsPayDayList.forEach(item->{
				purchaseMap.put("" + item.getLookupCode(), item.getMeaning());
			});
			termsCn = purchaseMap.get(year);
			if (StringUtils.isBlank(termsCn)) {
				termsCn = termsPayDayList.get(0).getMeaning();
			}
			String payDays = "";
			if (entity_hi_ro != null) {
				payDays = entity_hi_ro.getPayDays() == null ?  "" : Util.fmtMicrometer(entity_hi_ro.getPayDays() + "") + "D";
			}
			entityPayRo.setTermCategory("1");
			entityPayRo.setTermsCn(termsCn);
			entityPayRo.setTermsEn("");
			entityPayRo.setTermsSystem(payDays); //TERMS_PAY_DAY 付款期（天数）
		}

		//寄售折扣%"（寄售、寄售经仓模式才会有数据，购销模式没有数据的）
		TtaContractLineReportEntity_HI_RO consignmentRo = new TtaContractLineReportEntity_HI_RO();
		List<BaseLookupValuesEntity_HI> termsConsignmentlist = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERMS_CONSIGNMENT").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (termsConsignmentlist != null && !termsConsignmentlist.isEmpty()) {
			purchaseMap.clear();
			termsConsignmentlist.sort(new Comparator<BaseLookupValuesEntity_HI>() {
				@Override
				public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
					return  ("" + o1.getLookupCode()).compareTo(o2.getLookupCode() + "");
				}
			});
			termsConsignmentlist.forEach(item->{
				purchaseMap.put("" + item.getLookupCode(), item.getMeaning());
			});

			termsCn = purchaseMap.get(year);
			if (StringUtils.isBlank(termsCn)) {
				termsCn = termsConsignmentlist.get(0).getMeaning();
			}
			String consignmentDiscount = "";
			if (entity_hi_ro != null) {
				consignmentDiscount = entity_hi_ro.getConsignmentDiscount() == null ?  "" : Util.fmtMicrometer(entity_hi_ro.getConsignmentDiscount() + "") + "%";
			}
			consignmentRo.setTermCategory("1");
			consignmentRo.setTermsCn(termsCn);
			consignmentRo.setTermsEn("");
			consignmentRo.setTermsSystem(consignmentDiscount); //（寄售、寄售经仓模式才会有数据，购销模式没有数据的）
		}


		List<TtaContractLineReportEntity_HI_RO> reportList = null;
		Map<String, Object> resMap = null;
		for (int idx = 0; idx < lookUpList.size(); idx ++) {
			resMap = new HashMap<>();
			paramsMap.put("termCategory", lookUpList.get(idx).getLookupCode());
			if (idx == 0) {
				reportList = ttaContractLineReportDAO_HI_RO.findList(sbSql + " order by for_str_rpad(a.order_no) asc ", paramsMap);
				reportList.add(0, entityRo);
				reportList.add(2, entityPayRo);
				/*if ("A01,A02,B03,B04".contains(entity_hi_ro.getSalesType())) {
					reportList.add(1,consignmentRo);
				}*/
				reportList.add(1,consignmentRo);
			} else if (idx == 1) {
				//查询第二层头部信息
				reportList = ttaContractLineReportDAO_HI_RO.findList(sbSql + " and  not regexp_like(a.terms_cn, '第*层')  order by for_str_rpad(a.order_no) asc ", paramsMap);
				if (reportList != null && reportList.size() > 0) {
					reportList.remove(0);
				}
				HashMap<String, Object> queryMap = new HashMap<>();
				queryMap.put("proposalYear", year);
				queryMap.put("majorDeptCode", queryParamJSON.get("majorDeptCode"));
				TtaContractLineReportEntity_HI_RO headList = ttaContractLineReportDAO_HI_RO.get(TtaContractLineReportEntity_HI_RO.queryFloorSql(), queryMap);

				HashMap<String, Object> floorBodyMap = new HashMap<>();
				floorBodyMap.put("termCategory", paramsMap.get("termCategory"));
				floorBodyMap.put("proposalId", proposalId);
				List<TtaContractLineReportEntity_HI_RO> headBodyList = ttaContractLineReportDAO_HI_RO.findList(TtaContractLineReportEntity_HI_RO.queryFloorBodySql(), floorBodyMap);

				List<String> headArray = Arrays.asList((headList.getTermsCn() + "").split("@@"));
				resMap.put("headList", headArray);
				ArrayList<List<String>> bodyList = new ArrayList<>();
				headBodyList.forEach(item->{
					StringBuffer buffer = new StringBuffer();
					buffer.append((item.getTermsCn() + ""));
					List<String> arra = Arrays.asList(buffer.toString().split("@@"));
					while(arra.size() < headArray.size() + 1) {
						buffer.append("@@-999");
						arra = Arrays.asList(buffer.toString().split("@@"));
					}
					bodyList.add(arra);
				});
				resMap.put("headBodyList", bodyList);
			} else {
				reportList = ttaContractLineReportDAO_HI_RO.findList(sbSql + " and not regexp_like(a.terms_cn, '第*层') order by  for_str_rpad(a.order_no) asc ", paramsMap);
			}
			resMap.put("reportList", reportList);
			resMap.put("meaning", lookUpList.get(idx).getDescription());
			resultList.add(resMap);
		}
		result.put("resultList", resultList);
		return result;
	}


	@SuppressWarnings("all")
	private JSONObject getPropsoalContract(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		String year = queryParamJSON.getString("year");
		//1.查询条款类别信息
		List<BaseLookupValuesEntity_HI> lookUpList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERM_CATEGORY").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (lookUpList == null || lookUpList.isEmpty()) {
			LOGGER.info(".findTermsAgrement 没有设置快码");
			return null;
		}
		//3.销售方式
		List<BaseLookupValuesEntity_HI> saleWayList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "SALE_WAY").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		saleWayList.sort(new Comparator<BaseLookupValuesEntity_HI>() {
			@Override
			public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
				return (o1.getOrderNo() + "").compareTo(o2.getOrderNo() + "");
			}
		});
		result.put("saleWayList", saleWayList);
		Map<String, Object> paramsMap = new HashMap<>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(TtaContractLineReportEntity_HI_RO.QUERY_TERMS_AGRT);
		SaafToolUtils.parperParam(queryParamJSON, " a.proposal_id", "proposalId", sbSql, paramsMap, "=");
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<TtaContractLineReportEntity_HI_RO> list = null;
		TtaTermsHEntity_HI_RO entity_hi_ro = ttaTermsHServer.saveFindByRoId(queryParamJSON);
		//proposal 贸易条款，折扣折让项目首行显示
		String termsCn = "";
		//折扣折让项目首行配置
		TtaContractLineReportEntity_HI_RO entityRo = new TtaContractLineReportEntity_HI_RO();
		HashMap<String, String>  purchaseMap = new HashMap<>();
		List<BaseLookupValuesEntity_HI> termsPurchaseList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERMS_PURCHASE").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (termsPurchaseList != null && !termsPurchaseList.isEmpty()) {
			termsPurchaseList.sort(new Comparator<BaseLookupValuesEntity_HI>() {
				@Override
				public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
					return  ("" + o1.getLookupCode()).compareTo(o2.getLookupCode() + "");
				}
			});
			termsPurchaseList.forEach(item->{
				purchaseMap.put("" + item.getLookupCode(), item.getMeaning());
			});

			termsCn = purchaseMap.get(year);
			if (StringUtils.isBlank(termsCn)) {
				termsCn = termsPurchaseList.get(0).getMeaning();
			}
			String fcsPurchse = "";
			if (entity_hi_ro != null) {
				fcsPurchse = entity_hi_ro.getFcsPurchse() == null ?  "" : Util.fmtMicrometer(entity_hi_ro.getFcsPurchse() + "");
			}
			entityRo.setTermCategory("1");
			entityRo.setTermsCn(termsCn);
			entityRo.setTermsEn("");
			entityRo.setTermsSystem(fcsPurchse); //采购金额
		}

		//TERMS_PAY_DAY 付款期（天数）
		TtaContractLineReportEntity_HI_RO entityPayRo = new TtaContractLineReportEntity_HI_RO();
		List<BaseLookupValuesEntity_HI> termsPayDayList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERMS_PAY_DAY").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (termsPayDayList != null && !termsPayDayList.isEmpty()) {
			purchaseMap.clear();
			termsPayDayList.sort(new Comparator<BaseLookupValuesEntity_HI>() {
				@Override
				public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
					return  ("" + o1.getLookupCode()).compareTo(o2.getLookupCode() + "");
				}
			});
			termsPayDayList.forEach(item->{
				purchaseMap.put("" + item.getLookupCode(), item.getMeaning());
			});
			termsCn = purchaseMap.get(year);
			if (StringUtils.isBlank(termsCn)) {
				termsCn = termsPayDayList.get(0).getMeaning();
			}
			String payDays = "";
			if (entity_hi_ro != null) {
				payDays = entity_hi_ro.getPayDays() == null ?  "" : Util.fmtMicrometer(entity_hi_ro.getPayDays() + "") + "D";
			}
			entityPayRo.setTermCategory("1");
			entityPayRo.setTermsCn(termsCn);
			entityPayRo.setTermsEn("");
			entityPayRo.setTermsSystem(payDays); //TERMS_PAY_DAY 付款期（天数）
		}

		//寄售折扣%"（寄售、寄售经仓模式才会有数据，购销模式没有数据的）
		TtaContractLineReportEntity_HI_RO consignmentRo = new TtaContractLineReportEntity_HI_RO();
		List<BaseLookupValuesEntity_HI> termsConsignmentlist = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERMS_CONSIGNMENT").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (termsConsignmentlist != null && !termsConsignmentlist.isEmpty()) {
			purchaseMap.clear();
			termsConsignmentlist.sort(new Comparator<BaseLookupValuesEntity_HI>() {
				@Override
				public int compare(BaseLookupValuesEntity_HI o1, BaseLookupValuesEntity_HI o2) {
					return  ("" + o1.getLookupCode()).compareTo(o2.getLookupCode() + "");
				}
			});
			termsConsignmentlist.forEach(item->{
				purchaseMap.put("" + item.getLookupCode(), item.getMeaning());
			});

			termsCn = purchaseMap.get(year);
			if (StringUtils.isBlank(termsCn)) {
				termsCn = termsConsignmentlist.get(0).getMeaning();
			}
			String consignmentDiscount = "";
			if (entity_hi_ro != null) {
				consignmentDiscount = entity_hi_ro.getConsignmentDiscount() == null ?  "" : Util.fmtMicrometer(entity_hi_ro.getConsignmentDiscount() + "") + "%";
			}
			consignmentRo.setTermCategory("1");
			consignmentRo.setTermsCn(termsCn);
			consignmentRo.setTermsEn("");
			consignmentRo.setTermsSystem(consignmentDiscount); //（寄售、寄售经仓模式才会有数据，购销模式没有数据的）
		}


		List<TtaContractLineReportEntity_HI_RO> reportList = null;
		Map<String, Object> resMap = null;
		for (int idx = 0; idx < lookUpList.size(); idx ++) {
			resMap = new HashMap<>();
			paramsMap.put("termCategory", lookUpList.get(idx).getLookupCode());
			if (idx == 0) {
				reportList = ttaContractLineReportDAO_HI_RO.findList(sbSql + " order by for_str_rpad(a.order_no) asc ", paramsMap);
				reportList.add(0, entityRo);
				reportList.add(2, entityPayRo);
				/*if ("A01,A02,B03,B04".contains(entity_hi_ro.getSalesType())) {
					reportList.add(1,consignmentRo);
				}*/
				reportList.add(1,consignmentRo);
			} else if (idx == 1) {
				//查询第二层头部信息
				reportList = ttaContractLineReportDAO_HI_RO.findList(sbSql + " and  not regexp_like(a.terms_cn, '第*层')  order by for_str_rpad(a.order_no) asc ", paramsMap);
				if (reportList != null && reportList.size() > 0) {
					reportList.remove(0);
				}
				paramsMap.put("proposalId", queryParamJSON.get("proposalId"));
				HashMap<String, Object> queryMap = new HashMap<>();
				queryMap.put("proposalYear", queryParamJSON.get("proposalYear"));
				queryMap.put("majorDeptCode", queryParamJSON.get("majorDeptCode"));
				TtaContractLineReportEntity_HI_RO headList = ttaContractLineReportDAO_HI_RO.get(TtaContractLineReportEntity_HI_RO.queryFloorSql(), queryMap);
				List<TtaContractLineReportEntity_HI_RO> headBodyList = ttaContractLineReportDAO_HI_RO.findList(TtaContractLineReportEntity_HI_RO.queryFloorBodySql(), paramsMap);
				List<String> headArray = Arrays.asList((headList.getTermsCn() + "").split("@@"));
				resMap.put("headList", headArray);
				ArrayList<List<String>> bodyList = new ArrayList<>();
				headBodyList.forEach(item->{
					StringBuffer buffer = new StringBuffer();
					buffer.append((item.getTermsCn() + ""));
					List<String> arra = Arrays.asList(buffer.toString().split("@@"));
					while(arra.size() < headArray.size() + 1) {
						buffer.append("@@-999");
						arra = Arrays.asList(buffer.toString().split("@@"));
					}
					bodyList.add(arra);
				});
				resMap.put("headBodyList", bodyList);
			} else {
				reportList = ttaContractLineReportDAO_HI_RO.findList(sbSql + " and not regexp_like(a.terms_cn, '第*层') order by  for_str_rpad(a.order_no) asc ", paramsMap);
			}
			resMap.put("reportList", reportList);
			resMap.put("meaning", lookUpList.get(idx).getDescription());
			resultList.add(resMap);
		}
		result.put("resultList", resultList);
		result.put("orderNo", queryParamJSON.getString("orderNo"));
		return result;
	}


	@Override
	public List<Map<String, Object>> findShopArea(String flag) throws Exception {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySQL = new StringBuffer();
		if("area".equals(flag)) {
			querySQL.append("select t.lookup_code as \"areaCode\", t.meaning as \"areaName\"  from base_lookup_values t where t.lookup_type = 'TTA_TERMS_AREA' and t.enabled_flag = 'Y' AND DELETE_FLAG = '0' and t.meaning not like '%全国%' ");
		}else{
			querySQL.append(" select t.lookup_code as \"warehouseCode\", t.meaning as \"warehouseName\"  from base_lookup_values t where t.lookup_type = 'TTA_TERMS_WH' and t.enabled_flag = 'Y' AND DELETE_FLAG = '0' ");
		}
		List<Map<String, Object>> maps = baseDao.queryNamedSQLForList(querySQL.toString(), queryParamMap);
		return maps;
	}

	@Override
	public String findDicUniqueResult(String lookupType, String lookupCode) {
		String remark = null;
		HashMap<String, Object> params = new HashMap<>();
		params.put("lookupType", lookupType);
		params.put("lookupCode", lookupCode);
		String nodeSql = ActBpmTaskConfigEntity_HI_RO.getNodeSql();
		StringBuffer sql = new StringBuffer();
		sql.append(nodeSql).append(" and blv.lookup_type=:lookupType and  blv.lookup_code=:lookupCode ");
		List<Map<String, Object>> list = baseDao.queryNamedSQLForList(sql.toString(), params);
		if (CollectionUtils.isNotEmpty(list) &&  sql.length() != 0) {
				remark = list.get(0).get("nodeName") + "";
		}
		return remark;
	}


}
