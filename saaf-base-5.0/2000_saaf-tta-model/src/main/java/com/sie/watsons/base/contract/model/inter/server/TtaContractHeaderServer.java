package com.sie.watsons.base.contract.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.BigDecimalUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.dao.TtaContractHeaderDAO_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractHeaderEntity_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaAnalysisLineEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineEntity_HI_RO;
import com.sie.watsons.base.contract.model.inter.ITtaAnalysisLine;
import com.sie.watsons.base.contract.model.inter.ITtaContractHeader;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.item.model.inter.ITtaItem;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalTermsHeader;
import com.sie.watsons.base.questionnaire.model.inter.server.TtaQuestionChoiceLineServer;
import com.sie.watsons.base.report.model.entities.TtaAnalysisEditDataEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaAnalysisEditData;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSplitBrandDetailEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSplitBrandDetail;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("ttaContractHeaderServer")
public class TtaContractHeaderServer extends BaseCommonServer<TtaContractHeaderEntity_HI> implements ITtaContractHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractHeaderServer.class);

	@Autowired
	private TtaQuestionChoiceLineServer ttaQuestionChoiceLineServer;

	@Autowired
	private BaseCommonDAO_HI<TtaContractLineEntity_HI> ttaContractLineEntity_HI;

	@Autowired
	private TtaContractLineServer ttaContractLineServer;

	@Autowired
	private ViewObject<TtaContractHeaderEntity_HI> ttaContractHeaderDAO_HI;

    @Autowired
    private ViewObject<TtaAnalysisEditDataEntity_HI> ttaAnalysisEditDataDAO_HI;

	@Autowired
	private GenerateCodeServer GenerateCodeServer;

	@Autowired
	private BaseViewObject<TtaContractHeaderEntity_HI_RO> ttaContractHeaderDAO_HI_RO;

	@Autowired
	private TtaContractHeaderDAO_HI ttaContractHeaderDAO;

    @Autowired
    private ViewObject<TtaContractLineEntity_HI> ttaContractLineDAO_HI;

    @Autowired
    private BaseCommonDAO_HI<TtaContractLineEntity_HI> ttaContractEntity_HI;

	@Autowired
	private ITtaSplitBrandDetail ttaSplitBrandDetailServer;

	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	@Autowired
	private ITtaAnalysisEditData ttaAnalysisEditDataServer;

	@Autowired
	private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

	@Autowired
	private ITtaProposalTermsHeader ttaProposalTermsHeaderServer;

	@Autowired
	private ITtaItem ttaItemServer;

	@Autowired
	private ITtaAnalysisLine ttaAnalysisLineServer;

	public TtaContractHeaderServer() {
		super();
	}


	@Override
	public Pagination<TtaContractHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractHeaderEntity_HI_RO.TTA_ITEM_S_V);
		String vSql = TtaDeptFeeHeaderEntity_HI_RO.getTableDeptSql(queryParamJSON.getInteger("varUserId"),queryParamJSON.getString("varUserType")) ;
		if(!SaafToolUtils.isNullOrEmpty(vSql)){
			sql.append(" and exists (select 1 from " + vSql).append(" where b.MAJOR_DEPT_CODE = dept.department_code and b.DEPT_DESC1 = dept.group_name )");
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "a.contract_h_Id", "contractHId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "a.contract_code", "contractCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "a.vendor_nbr", "vendorNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "a.vendor_name", "vendorName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "a.bill_status", "billStatus", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "a.proposal_code", "proposalCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "a.proposal_year", "proposalYear", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "a.proposal_version", "proposalVersion", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "a.is_split", "isSplit", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "a.contract_h_Id desc", false);
		Pagination<TtaContractHeaderEntity_HI_RO> findList = ttaContractHeaderDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaContractHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		JSONObject contractHeader = paramsJSON.getJSONObject("contractHeader");//头部信息
		TtaContractHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaContractHeaderEntity_HI.class, contractHeader, ttaContractHeaderDAO_HI, userId);
		if (instance.getContractHId() == null) {
			String code = GenerateCodeServer.getApplyCodeGenerateCode("CSP", 4, true, true);
			instance.setContractCode(code);
			instance.setLastUpdateDate(new Date());
			if(StringUtils.isEmpty(instance.getIsSplit())){
				instance.setIsSplit("N");//不拆分
			}
		}
		ttaContractHeaderDAO_HI.saveOrUpdate(instance);
		String optStatus = paramsJSON.getString("optStatus");
		if (!"D".equalsIgnoreCase(optStatus)) { //不是取消操作，都可以执行更新操作。
			paramsJSON.fluentPut("billStatus", instance.getBillStatus()).fluentPut("isSplit", instance.getIsSplit());
			this.saveBatchSplitResult(paramsJSON);
		}
		//1.确认需更新合同行表为空的是否特批的状态
		String isSpecial = StringUtils.isEmpty(instance.getIsSpecial()) ? "N" : instance.getIsSpecial();
		//2.P字头供应商并且付款天数<60天，且指定一些特殊的品牌，暂时不考虑
		if ("C".equalsIgnoreCase(optStatus)) {
			//通过proposal编号和版本号更新合同头表id为空的特批字段
			String sql = TtaContractHeaderEntity_HI_RO.updateConHeaderSpecial(instance.getProposalCode(), instance.getProposalVersion(), userId, isSpecial);
			ttaContractHeaderDAO_HI.executeSqlUpdate(sql);
		} else if ("D".equalsIgnoreCase(optStatus)) {
			//取消确认
			String sql = TtaContractHeaderEntity_HI_RO.updateConHeaderSpecial(instance.getProposalCode(), instance.getProposalVersion(), userId, null);
			ttaContractHeaderDAO_HI.executeSqlUpdate(sql);
		}
		return instance;
	}

	@SuppressWarnings("all")
	private void saveBatchSplitResult(JSONObject paramsJSON) {
		String billStatus = paramsJSON.getString("billStatus");//确认状态需要校验值
		String isSplit = paramsJSON.getString("isSplit");
		JSONArray vendorContractList = paramsJSON.getJSONArray("vendorContractList");//拆分后的头部信息
		if (vendorContractList == null || vendorContractList.isEmpty()) {
			LOGGER.info("合同类表数据为空，不执行保存操作。");
			return;
		}
		//DELIVERY_GRANARY,SALES_AREA,PURCHASE,SALES,TTA_VALUE
		List<Map<String,Map<String, Object>>> entitylist = new ArrayList<>();
		//初始化采购额
		Map<String, BigDecimal> salePuracheTotalMap = new HashMap<String, BigDecimal>();
		salePuracheTotalMap.put("-5",new BigDecimal("0"));
		salePuracheTotalMap.put("-6",new BigDecimal("0"));

		for (int idx = 0; idx < vendorContractList.size(); idx ++) {
			Map<String,Map<String, Object>> rowMap = new HashMap<>();
			Map<String, Object> propertiesMap = null;
			JSONArray jsonArray = vendorContractList.getJSONArray(idx);
			HashMap<String, Object> attMap = new HashMap<>();//存储供应商编码、名称，配送仓库，销售区域，销售金额，采购金额附加信息
			for (int rowIdx = 0; rowIdx < jsonArray.size(); rowIdx ++ ) {
				JSONObject jsonMap = jsonArray.getJSONObject(rowIdx);
				String id = jsonMap.getString("id");
				String fieldName = jsonMap.getString("fieldName").toLowerCase();
				String fieldValue = jsonMap.getString("fieldValue");
				if ("-1,-2,-3,-4,-5,-6,-7".contains(id)) { //分别表示供应商编码、名称，配送仓库，销售区域，销售金额，采购金额附加信息
					attMap.put(fieldName, fieldValue);
					if ("-5,-6".contains(id)) {
						//-5：销售，-6采购数据
						Object value = salePuracheTotalMap.get(id) == null ? new BigDecimal(0) : salePuracheTotalMap.get(id);
						Assert.isTrue(!StringUtils.isEmpty(fieldValue), "销售或采购金额列必填!");
						BigDecimal totalValues = new BigDecimal(value.toString()).add(new BigDecimal(fieldValue));
						salePuracheTotalMap.put(id, totalValues);
					}
				}
				propertiesMap = rowMap.get(id);
				if (propertiesMap == null || propertiesMap.isEmpty()) {
					propertiesMap = new HashMap<>();
					rowMap.put(id, propertiesMap);
				}
				propertiesMap.putAll(attMap);
				propertiesMap.put("contractLId", id);
				propertiesMap.put(SaafToolUtils.remove_AndNextChar2UpperCase(fieldName), fieldValue);
			}
			entitylist.add(rowMap);
		}

		LinkedHashSet<String> parentIds = new LinkedHashSet<>();//父节点ids
		Map<String, BigDecimal> totalValueMap = new HashMap<>(); //求和
		//存储校验相关的求和信息
		List<Map<String,Object>> updateList = new ArrayList<>();
		for (Map<String,Map<String, Object>> rowMap : entitylist) {
			Collection<Map<String, Object>> mapValues = rowMap.values();
			for (Map<String, Object> map : mapValues) {
				updateList.add(map);
				if ("C".equalsIgnoreCase(billStatus) && "Y".equalsIgnoreCase(isSplit)) {//确认+是否拆分（是）
					if (map.containsKey("delivery_granary") && map.containsKey("sales_area")) {
						String delivery_granary = map.get("delivery_granary") + "";
						String sales_area = map.get("sales_area") + "";
						if (StringUtils.isEmpty(delivery_granary) && StringUtils.isEmpty(sales_area)) {
							Assert.isTrue(false, "配送仓库和销售不能同时为空！");
						}
						if (!StringUtils.isEmpty(delivery_granary) && !StringUtils.isEmpty(sales_area)) {
							Assert.isTrue(false, "配送仓库和销售只能选其一");
						}
					}
					checkTotal(parentIds, totalValueMap, map);
				}
			}
		}

		if ("C".equalsIgnoreCase(billStatus) && "Y".equalsIgnoreCase(isSplit)) {//确认+是否拆分（是）
			//校验开始
			String sql = "select contract_l_id as \"contractLId\",nvl(tta_value, 0) as \"ttaValue\",terms_cn as \"termsCn\", t.sales as \"sales\", t.purchase as  \"purchase\"  from tta_contract_line t where t.contract_l_id in ('" + String.join("','", parentIds) + "') order by t.order_no ";
			List<Map<String, Object>> ttaValueList = ttaContractLineEntity_HI.queryNamedSQLForList(sql, new HashMap<>());
			Assert.notEmpty(ttaValueList, "没有对应的父级条款信息");
			BigDecimal saleTotal = salePuracheTotalMap.get("-5");//拆分后销售总额
			BigDecimal purchaseTotal = salePuracheTotalMap.get("-6");//拆分后采购总额
			BigDecimal sale = new BigDecimal(ttaValueList.get(0).get("sales").toString());
			BigDecimal purchase = new BigDecimal(ttaValueList.get(0).get("purchase").toString());
			if (saleTotal.compareTo(sale) != 0) {
				Assert.isTrue(false, "请检查销售金额列求和不相等,原始值是:" + sale + ",拆分后的总值是:" + saleTotal);
			}
			if (purchaseTotal.compareTo(purchase) != 0) {
				Assert.isTrue(false, "请检查采购金额列求和不相等,原始值是:" + purchase + ",拆分后的总值是:" + purchaseTotal);
			}
			for (Map<String, Object> ttaValueMap : ttaValueList) {
				String originContractLId = ttaValueMap.get("contractLId") + "";
				String originTtaValue = ttaValueMap.get("ttaValue") == null ? "0" : ttaValueMap.get("ttaValue") + "";
				Object termsCn = ttaValueMap.get("termsCn");
				BigDecimal splitTotalValue = totalValueMap.get(originContractLId) == null ? new BigDecimal(0) : totalValueMap.get(originContractLId);
				BigDecimal bigDecimal = new BigDecimal(originTtaValue);
				if (bigDecimal.compareTo(splitTotalValue) != 0) {
					Assert.isTrue(false, "请检查条款【" + termsCn + "】列求和不相等,原始值是:" + originTtaValue + ",拆分后的总值是:" + splitTotalValue);
				}
			}
		}
		ttaContractLineEntity_HI.updateBatchListMap(updateList);
	}

	@SuppressWarnings("all")
	private void checkTotal(LinkedHashSet<String> parentIds, Map<String, BigDecimal> totalValueMap, Map<String, Object> map) {
		if (!map.containsKey("splitParentId")) {
			return;
		}
		//添加求和数据
		String splitParentId = map.get("splitParentId") + "";//拆分供应商的父级id
		parentIds.add(splitParentId);
		Object ttaValue = map.get("ttaValue");//拆分供应商的ttaValue值
		if (splitParentId != null &&  SaafToolUtils.isNumber(ttaValue)) {
			Object  ttaValueTemp = totalValueMap.get(splitParentId);
			BigDecimal valueTemp = ttaValueTemp == null ? new BigDecimal(0) : new BigDecimal(ttaValueTemp.toString());
			BigDecimal totalVlue = new BigDecimal(ttaValue.toString()).add(valueTemp);
			totalValueMap.put(splitParentId, totalVlue);
		}
	}

	/**
	 * 拉取 analysis数据
	 * @param jsonObject
	 * @param userId
	 * @param userSessionBean
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject callAnalysis(JSONObject jsonObject, int userId, UserSessionBean userSessionBean) throws Exception{
		Boolean isnull2018 = false;
		String buser = "";
		String versionCode = "";
		Assert.notNull(jsonObject.getInteger("proposalId"),"缺少当前ProposalId");
		String poprosalId = jsonObject.getInteger("proposalId").toString();
		LOGGER.info("###########################拉取Analysis数据需要的参数ProposalId<{}>#####################################",poprosalId);
		TtaProposalHeaderEntity_HI_RO ph = ttaProposalHeaderServer.findByRoId(jsonObject);
		if (null == ph) {
			throw new IllegalArgumentException("当前Proposal信息不存在,请刷新重试,如多次重试失败,请联系管理员");
		}
		Calendar calendar = Calendar.getInstance();//日历对象
		int yearStr = calendar.get(Calendar.YEAR);//获取年份
		int monthStr = calendar.get(Calendar.MONTH);//获取月份
		int dateStr = calendar.get(Calendar.DATE);//获取日期
		yearStr = Integer.parseInt(ph.getProposalYear());

		//获取当前analysis数据 ,注释
		//List<TtaAnalysisEditDataEntity_HI> analysisList = ttaAnalysisEditDataServer.findByProposalID(poprosalId);

		//第一次加载,或者点击按钮时,都查询当前analysis数据
		List<TtaAnalysisLineEntity_HI_RO> lineServerAnalysisData = ttaAnalysisLineServer.findAnalysisData(jsonObject, userId);

		List<TtaOiBillLineEntity_HI_RO> billList = null;
		//获取数据字典，合同行code，和TermCn，暂时没用
		List<BaseLookupValuesEntity_HI_RO> codeList = ttaContractLineServer.findLookUpCode().getData();

		//根据条件:proposalId,年度,TTA TERMS模块,品牌计划模块,部门协定标准模块,NPP服务费模块 为确认状态下查找
		List<TtaProposalHeaderEntity_HI> headerList2019 = ttaProposalHeaderServer.findByTerm(poprosalId,yearStr+"");

		if(null == headerList2019 || headerList2019.size()==0 ){
			String isTermsConfirm = ph.getIsTermsConfirm();
			String isPlnConfirm = ph.getIsPlnConfirm();
			String isDepartConfirm = ph.getIsDepartConfirm();
			String isNewConfirm = ph.getIsNewConfirm();
			List<String> unConfimStr = new ArrayList<>();
			String tipMsg = "";
			if ("N".equals(isTermsConfirm)){
				tipMsg = "TTA TERM";
				unConfimStr.add(tipMsg);
			}
			if ("N".equals(isPlnConfirm)){
				tipMsg = "品牌计划";
				unConfimStr.add(tipMsg);
			}
			if ("N".equals(isDepartConfirm)){
				tipMsg = "部门协定标准";
				unConfimStr.add(tipMsg);
			}
			if ("N".equals(isNewConfirm)){
				tipMsg = "NPP服务费";
				unConfimStr.add(tipMsg);
			}
			if(!unConfimStr.isEmpty()) {
				tipMsg = org.apache.commons.lang.StringUtils.join(unConfimStr,",");
			}
			throw new IllegalArgumentException("【" + tipMsg + "】模块为未确认状态,加载数据失败!");
		} else {
			if(null!=headerList2019.get(0).getCreatedBy()){
				//获取proposal单据的创建用户信息
				BaseUsersEntity_HI baseUsersEntity_hi = baseUsersDAO_HI.getById(headerList2019.get(0).getCreatedBy());
				buser = baseUsersEntity_hi.getUserFullName();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH.mm.ss");
				String dateNowStr = sdf.format(baseUsersEntity_hi.getCreationDate());
				versionCode =buser +"_"+dateNowStr;
			}
		}
		//获取TTA Terms头信息
		List<TtaProposalTermsHeaderEntity_HI> termList2019 = ttaProposalTermsHeaderServer.findByTerm(headerList2019.get(0).getProposalId()+"");

		//根据proposalId 和 proposal制作年度查询条款信息
		List<TtaContractLineEntity_HI_RO> contractLineList2019 = ttaContractLineServer.findAnalysisLine(termList2019.get(0).getProposalId()+"",yearStr+"");
		//报表头需要单独展示免费货
		List<TtaContractLineEntity_HI_RO> freegoodsList2019 = ttaContractLineServer.findAnalysisFreeGood(termList2019.get(0).getProposalId()+"");

		TtaContractLineEntity_HI_RO freeGood2019 = null;
		//如果免费货有值,那么计算如下Sales等值
		if(freegoodsList2019.size()>0){
			freeGood2019 = freegoodsList2019.get(0);
			freeGood2019.setSales(freeGood2019.getSales().divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
			freeGood2019.setPurchase(freeGood2019.getPurchase().divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
			freeGood2019.setFeeNotax((freeGood2019.getFeeNotax()==null?new BigDecimal(0):freeGood2019.getFeeNotax()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
			freeGood2019.setFeeIntas((freeGood2019.getFeeIntas()==null?new BigDecimal(0):freeGood2019.getFeeIntas()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
			freeGood2019.setTtaValue(new BigDecimal(freeGood2019.getTtaValue()==null?"0":freeGood2019.getTtaValue()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP)+"");
		} else {
			freeGood2019 = initContractLine();
		}
		List<TtaProposalHeaderEntity_HI> headerList2018 = null;
		//统计合算sumMoney
		//sumMoney(contractLineList2019);
		if(headerList2019!=null&&headerList2019.size()!=0){
			//headerList2018 = ttaProposalHeaderServer.findByTermOld((yearStr-1)+"",headerList2019.get(0).getVendorNbr());
			//改动原因:改变取往年的proposal单据方案,改为通过lastYearProposalId字段去找往年的proposal单据
			headerList2018 = ttaProposalHeaderServer.findLastProposal(headerList2019.get(0).getLastYearProposalId());

		}

		TtaItemEntity_HI_RO item = null;
		List<TtaProposalTermsHeaderEntity_HI> termList2018 = null;
		if(headerList2019.size()!=0){
			jsonObject.put("vendorNbr",headerList2019.get(0).getVendorNbr());
			item = ttaItemServer.findByVendor(jsonObject);
		}else{
			item = new TtaItemEntity_HI_RO();
			headerList2019.add(initProposalHeader());
		}
		if(item ==null){ //如果item为null ,初始化 item对象
			item = new TtaItemEntity_HI_RO();
			item.setVendorName("");
			item.setDeptDesc("");
			item.setVendorNbr(0);
			item.setBrandEn("");
		}

		//往年有Proposal,查找往年的TTA_TERMS
		if(headerList2018 !=null && headerList2018.size()!=0){
			//billList = ttaContractLineServer.findBillOi(headerList2018.get(0).getVendorNbr(),"2019").getData();
			termList2018  = ttaProposalTermsHeaderServer.findByTerm(headerList2018.get(0).getProposalId()+"");
			if(null == termList2018 || termList2018.size()==0){
				termList2018.add(initProposalTermsHeader());
			}
		}else {
			//往年没有数据
			isnull2018 = true;//设置往年的为空的标识
			//没有往年的proposal单据,就初始化往年的数据
			termList2018 = new ArrayList<TtaProposalTermsHeaderEntity_HI>();
			termList2018.add(initProposalTermsHeader());
			headerList2018.add(initProposalHeader());
		}

		List<TtaContractLineEntity_HI_RO> contractLineList2018 = new ArrayList<TtaContractLineEntity_HI_RO>();
		TtaContractLineEntity_HI_RO freeGood2018 = null;

		//如果也找不出来也要初始化空对象
		if(termList2018.get(0).getProposalId() !=0){
			contractLineList2018 = ttaContractLineServer.findAnalysisLine(headerList2018.get(0).getProposalId()+"",headerList2018.get(0).getProposalYear());
			//前端需要获取单条免费货数据，先除以一千
			List<TtaContractLineEntity_HI_RO> freegoodsList2018 = ttaContractLineServer.findAnalysisFreeGood(termList2018.get(0).getProposalId()+"");
			if(freegoodsList2018.size()>0){
				freeGood2018 = freegoodsList2018.get(0);
				freeGood2018.setSales(dealNull(freeGood2018.getSales()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
				freeGood2018.setPurchase(dealNull(freeGood2018.getPurchase()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));

				freeGood2018.setFeeNotax((freeGood2018.getFeeNotax()==null?new BigDecimal(0):freeGood2018.getFeeNotax()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
				freeGood2018.setFeeIntas((freeGood2018.getFeeIntas()==null?new BigDecimal(0):freeGood2018.getFeeIntas()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
				freeGood2018.setTtaValue(new BigDecimal(freeGood2018.getTtaValue()==null?"0":freeGood2018.getTtaValue()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP)+"");
			} else {
				freeGood2018 = initContractLine();
			}
			if(contractLineList2018.size()==0){
				//如果往年没有条款,就初始化和当年条款数目一样的空对象
				for(int i=0;i<contractLineList2019.size();i++){
					contractLineList2018.add(initContractLine());
				}
			}

			//hmb查   如果当年和往年的条款集合都不为空,那么有可能两年的条款名目对不上
			contractLineList2018 = contractLineNull(contractLineList2018,contractLineList2019);
			contractLineList2019 = contractLineNull(contractLineList2019,contractLineList2018);
			if(contractLineList2018.size()>0){
				SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String ad= "";
				if(termList2019.get(0).getActuallyCountDate() == null){
					ad=contractLineList2019.get(0).getProposalYear()+"-12-31 23:59:59";
				}else{
					ad=f.format(termList2019.get(0).getActuallyCountDate());
				}
				//统计合算sumMoney
				if ("2018".equals(headerList2018.get(0).getProposalYear())) {
					//使用的是分摊之前的表
					sumMoney(contractLineList2018,ad,headerList2019);
				} else {
					//使用的是分摊之后的表
					countSumMoney(contractLineList2018,ad,headerList2018,headerList2019);
				}
			}
		}

		///---------------------------------------------------------------------------------
		//BIC备注
		String remark = ttaQuestionChoiceLineServer.findQuestionEnglishRemark(Integer.parseInt(poprosalId));
		JSONObject bicRemarkAndBatchObject = new JSONObject();
		if (CollectionUtils.isEmpty(lineServerAnalysisData)) {//第一次加载
			String batch = "";
			if(headerList2019.size()>0){
				batch  = headerList2019.get(0).getOrderNbr()+"_"+headerList2019.get(0).getVersionCode();
			}
			bicRemarkAndBatchObject.put("batch",batch);
			bicRemarkAndBatchObject.put("bicRemark",remark);
			bicRemarkAndBatchObject.put("purchaseRemark","");
			lineServerAnalysisData = Collections.EMPTY_LIST;
		} else {//点刷新按钮的时候会进来
			TtaAnalysisLineEntity_HI_RO entityHiRo = lineServerAnalysisData.get(0);
			bicRemarkAndBatchObject.put("batch",entityHiRo.getBatchNumber());
			bicRemarkAndBatchObject.put("purchaseRemark",entityHiRo.getPurchaseRemark());
			if(org.apache.commons.lang.StringUtils.isNotBlank(entityHiRo.getBicRemark())){
				bicRemarkAndBatchObject.put("bicRemark",entityHiRo.getBicRemark().replace("<br>","&#10;"));
			}else{
				if (org.apache.commons.lang.StringUtils.isNotBlank(remark)) {
					bicRemarkAndBatchObject.put("bicRemark",remark);
				} else {
					bicRemarkAndBatchObject.put("bicRemark","");
				}
			}
		}
		///---------------------------------------------------------------------------------

		//-------------------------------以下这部分数据为数据初始化 start---------------------------------------------------------
		// tta_analysis_edit_data表的数据迁移到tta_analysis_line表中
		///==========2020.3.23 初始化已经存在的analysis数据到改造后的analysis模块======================
		//获取当前Proposal_id的analysis数据
		List<TtaAnalysisEditDataEntity_HI> analysisList = ttaAnalysisEditDataServer.findByProposalID(poprosalId);
		JSONObject oldAnalyssiObject = new JSONObject();
		String oldProposalOrderNbr = "";
		if (headerList2018.get(0).getOrderNbr() != null){
			oldProposalOrderNbr = headerList2018.get(0).getOrderNbr();
		}
		String oldAnalysisSql = "select ait.* from analysis_init_temp ait where ait.proposal_code = '"+oldProposalOrderNbr+"'";
		List<Map<String, Object>> sqlForList = ttaContractLineEntity_HI.queryNamedSQLForList(oldAnalysisSql, new HashMap<>());
		String referTableSql = "select * from refer_table";
		List<Map<String, Object>> referTableForList= ttaContractLineEntity_HI.queryNamedSQLForList(referTableSql, new HashMap<>());
		if (CollectionUtils.isNotEmpty(sqlForList)){
			LOGGER.info("analysis_init_temp表存在单据:{}",oldProposalOrderNbr);
			Map<String, Object> objectMap = sqlForList.get(0);
			oldAnalyssiObject.put("freegood",(BigDecimal)objectMap.get("FREE_GOOD"));
			oldAnalyssiObject.put("orderNbr",(String)objectMap.get("PROPOSAL_CODE"));//proposal单号
			Iterator<Map.Entry<String, Object>> iterator = objectMap.entrySet().iterator();
			while (iterator.hasNext()){
				Map.Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				for (Map<String, Object> referMap : referTableForList) {
					String column_name = (String) referMap.get("COLUMN_NAME");
					if (key.equalsIgnoreCase(column_name)) {
						String terms_name = (String) referMap.get("TERMS_NAME");
						for (TtaContractLineEntity_HI_RO entityHiRo : contractLineList2018) {
							if (terms_name.equals(entityHiRo.getTermsCn())) {
								oldAnalyssiObject.put("A" + entityHiRo.getContractLId(),(BigDecimal) entry.getValue());
								break;
							}
						}
						break;
					}
				}
			}
		} else {
			//在analysis_init_temp表中找不到Proposal单据,那么到tta_analysis_edit_data表查找
			if (headerList2018.get(0).getProposalId() != 0){
				LOGGER.info("*************Analysis数据初始化<到tta_analysis_edit_data查找数据>****************************");
				Integer proposalId = headerList2018.get(0).getProposalId();
				JSONObject queryParamJSON = new JSONObject();
				queryParamJSON.put("proposalid",proposalId);
				List<TtaAnalysisEditDataEntity_HI> list = ttaAnalysisEditDataServer.findList(queryParamJSON);
				if (CollectionUtils.isNotEmpty(list)){
					oldAnalyssiObject.put("freegood",new BigDecimal(list.get(0).getFreegoods() == null ? 0 : list.get(0).getFreegoods()));
					oldAnalyssiObject.put("orderNbr",headerList2018.get(0).getOrderNbr());//proposal单号
					String aboiList = list.get(0).getAboiList();
					if (!StringUtils.isEmpty(aboiList)) {
						JSONObject parseObject = JSONObject.parseObject(aboiList);//把json字符串变成JSON对象
						Iterator<Map.Entry<String, Object>> aboiIterator = parseObject.entrySet().iterator();
						while (aboiIterator.hasNext()) {
							Map.Entry<String, Object> entry = aboiIterator.next();
							String key = entry.getKey();
							BigDecimal value = new BigDecimal(( SaafToolUtils.isNullOrEmpty(entry.getValue()) ? "0" : entry.getValue().toString()));
							oldAnalyssiObject.put(key, value);
						}
					}
				}
			}
		}


		///===========2020.3.23 初始化已经存在的analysis数据到改造后的analysis模块=====================
		//-------------------------------以上这部分数据为数据初始化 end---------------------------------------------------------


		//往年没有数据,初始化往年的条款
		if(isnull2018){
			contractLineList2018 = oldYearNull(contractLineList2018,contractLineList2019);
		}
		//处理中文字符
		contractLineList2018 = changeChar(contractLineList2018);
		contractLineList2019 = changeChar(contractLineList2019);

		//算往年tta值,往年的预估值,例如2019TTA  计算的范围为往年的预估值:(Fcs Purchase,Fcs Sales OI费用(FEE_INTAS 费用预估))
		//处理往年的Proposal不是虚拟单的情况
		if (!"Y".equals(headerList2018.get(0).getIsSplit())) {
			countPurchaseSalesAndFeeIntas(headerList2019,headerList2018,contractLineList2018);
		}

		//处理往年的Proposal单是虚拟单,把拆后的费用预估符给费用预估字段,拆后的purchase,拆后的sales赋值给拆前的字段
		if("Y".equals(headerList2018.get(0).getIsSplit())){
			assignSplitFeeToFeeAndPurchaseSales(contractLineList2018);
		}

		//实际费用
		String purchase = "0";
		String sales = "0";
		String gp = "0";
		//预估费用
		String fpurchase = "0";
		String fsales = "0";
		String fgp = "0";
		JSONObject obj = new JSONObject();
		List<Map<String, Object>> list = ttaContractLineServer.findBrand(headerList2019.get(0).getProposalId()+"",headerList2019.get(0).getVendorNbr());
		if(list.size()>0){
			obj.put("brandVendor", list.size());
			purchase = list.get(0).get("PO_RECORD_SUM") == null ? "0" : list.get(0).get("PO_RECORD_SUM").toString();
			sales = list.get(0).get("SALES_SUM") == null ? "0" : list.get(0).get("SALES_SUM").toString();
			gp = list.get(0).get("ACTUAL_GP") == null ? "0" : list.get(0).get("ACTUAL_GP").toString();

			fpurchase = list.get(0).get("FCS_PURCHASE") == null ? "0" : list.get(0).get("FCS_PURCHASE").toString();
			fsales = list.get(0).get("FCS_SALES") == null ? "0" : list.get(0).get("FCS_SALES").toString();
			fgp = list.get(0).get("GP") == null ? "0" : list.get(0).get("GP").toString();
		}else{
			//品牌计划为空则
			obj.put("brandVendor", "null");
		}

		//obj.put("analysisData", analysisList.size()==0?analysis:analysisList.get(0));
		obj.put("analysisData", analysisList);
		obj.put("oldAnalysisData", oldAnalyssiObject);
		//obj.put("oldAnalysisData",oldAnalysis);
		obj.put("versionCode", versionCode);
		obj.put("buser", buser);
		obj.put("purchase", purchase);
		obj.put("sales", sales);
		obj.put("gp", gp);

		obj.put("fpurchase", fpurchase);
		obj.put("fsales", fsales);
		obj.put("fgp", fgp);

		obj.put("datasize", 1);
		if(freeGood2018==null){
			freeGood2018 = new TtaContractLineEntity_HI_RO();
			freeGood2018.setPurchase(new BigDecimal(0));
			freeGood2018.setSales(new BigDecimal(0));
			freeGood2018.setSumMoney("0");
			freeGood2018.setTtaValue("0");
			freeGood2018.setFeeNotax(new BigDecimal(0));
			freeGood2018.setFeeIntas(new BigDecimal(0));
		}
		obj.put("freeGood2018",(JSONObject) JSON.toJSON(freeGood2018));
		obj.put("freeGood2019",(JSONObject) JSON.toJSON(freeGood2019));
		obj.put("item",(JSONObject) JSON.toJSON(item));
		obj.put("header2018",(JSONObject) JSON.toJSON(headerList2018.get(0)));
		obj.put("hterm2018",(JSONObject) JSON.toJSON(termList2018.get(0)));
		JSONArray array= JSONArray.parseArray(JSON.toJSONString(contractLineList2018));
		obj.put("contractLineList2018",array);
		obj.put("header2019",(JSONObject) JSON.toJSON(headerList2019.get(0)));
		obj.put("hterm2019",(JSONObject) JSON.toJSON(termList2019.get(0)));
		JSONArray array2= JSONArray.parseArray(JSON.toJSONString(contractLineList2019));
		obj.put("contractLineList2019",array2);
		obj.put("remarkResult",bicRemarkAndBatchObject);
		obj.put("analysisDataList",lineServerAnalysisData);
		return obj;
	}

	private void assignSplitFeeToFeeAndPurchaseSales(List<TtaContractLineEntity_HI_RO> contractLineList2018) {
		for (TtaContractLineEntity_HI_RO entityHiRo : contractLineList2018) {
			entityHiRo.setFeeIntas(entityHiRo.getFeeSplitIntas());
			entityHiRo.setFeeNotax(entityHiRo.getFeeSplitNotax());
			entityHiRo.setPurchase(entityHiRo.getSplitPurchase());
			entityHiRo.setSales(entityHiRo.getSplitSales());
		}
	}

	public TtaContractLineEntity_HI_RO initContractLine(){
		TtaContractLineEntity_HI_RO entity = new TtaContractLineEntity_HI_RO();
		entity.setRemark("");
		entity.setFeeNotax(new BigDecimal(0));
		entity.setFeeIntas(new BigDecimal(0));
		entity.setPurchase(new BigDecimal(0));
		entity.setSales(new BigDecimal(0));
		entity.setTtaValue("0");
		entity.setUnit("0");
		entity.setTermsCn("");
		entity.setReferenceStandard(0);
		return entity;
	}

	public TtaProposalHeaderEntity_HI initProposalHeader(){
		TtaProposalHeaderEntity_HI entity = new TtaProposalHeaderEntity_HI();
		entity.setRemark("");
		entity.setProposalId(0);
		return entity;
	}

	public TtaProposalTermsHeaderEntity_HI initProposalTermsHeader(){
		TtaProposalTermsHeaderEntity_HI entity = new TtaProposalTermsHeaderEntity_HI();
		entity.setRemark("");
		entity.setBeoiTax("0");
		entity.setConsignmentDiscount(new Float(0));
		entity.setFcsPurchse(new Float(0));
		entity.setFcsSales(new Float(0));
		entity.setGp(new Float(0));
		entity.setSalesUpScale(new Float(0));
		entity.setSalesType("");
		entity.setTermsVersion("");
		entity.setProposalId(0);
		return entity;
	}

	//两年条款为空时，取有值的那一年，不一样时中文名取最新一年的
	//名字不一样的以当年为准
	/**
	 * @param list1  条款集合
	 * @param list2  条款集合
	 * @return
	 */
	public List<TtaContractLineEntity_HI_RO> contractLineNull(List<TtaContractLineEntity_HI_RO> list1,List<TtaContractLineEntity_HI_RO> list2){
		for(int j=0;j<list2.size();j++){
			if(list2.get(j).getContractLId()==null&&list1.get(j).getContractLId()==null){
				list2.remove(j);
				list1.remove(j);
			}
		}
		for(int i=0;i<list2.size();i++){

			//另一年有数据时可以覆盖
			if(list1.get(i).getContractLId()==null){
				list1.get(i).setContractLId(0);
				list1.get(i).setProposalId(0);
			}

			//hmb 重新添加注释 如果当年的条款不为空,往年的条款为空,把当年的条款数据设置到往年的条款数据中
			if(list2.get(i).getTermsCn() !=null&&list1.get(i).getTermsCn() ==null){
				list1.get(i).setTermsCn(list2.get(i).getTermsCn());
				//list1.get(i).setContractLId(list2.get(i).getContractLId());
				//list1.get(i).setContractHId(list2.get(i).getContractHId());
				list1.get(i).setPurchase(new BigDecimal(0));
				list1.get(i).setFeeIntas(new BigDecimal(0));
				list1.get(i).setPurchase(new BigDecimal(0));
				list1.get(i).setSumMoney("0");
				list1.get(i).setTtaValue("0");
				list1.get(i).setFeeNotax(new BigDecimal(0));
				list1.get(i).setOiType(list2.get(i).getOiType());
				list1.get(i).setVendorCode(list2.get(i).getVendorCode());
				list1.get(i).setProposalYear((Integer.parseInt(list2.get(i).getProposalYear())-1)+"");
				list1.get(i).setUnit(list2.get(i).getUnit());
				list1.get(i).setSales(new BigDecimal(0));
				list1.get(i).setTermsEn(list2.get(i).getTermsEn());
				list1.get(i).setDeliveryGranary("0");
				list1.get(i).setProposalId(0);
			}
			//名字不一样的情况
			//2019.12.30号 hmb注释
     /*       if(list2.get(i).getTermsCn()!=null&&list1.get(i).getTermsCn()!=null&&!list2.get(i).getTermsCn().equals(list1.get(i).getTermsCn())){
                list1.get(i).setTermsCn(list2.get(i).getTermsCn());
                list1.get(i).setTermsEn(list2.get(i).getTermsEn());
            }*/

		}
		return list1;
	}

	//新增供应商时往年数据为空，赋空值
	public List<TtaContractLineEntity_HI_RO> oldYearNull(List<TtaContractLineEntity_HI_RO> list1,List<TtaContractLineEntity_HI_RO> list2){

		for(int j=0;j<list2.size();j++){
			if(list2.get(j).getContractLId()==null){
				list2.remove(j);
				j--;//重点:要加上,因为删除一个元素,元素向前移动一位,索引继续往后加1
			}
		}
		for(int i=0;i<list2.size();i++){
			TtaContractLineEntity_HI_RO ro = new TtaContractLineEntity_HI_RO();
			ro.setTermsCn(list2.get(i).getTermsCn());
			ro.setContractLId(list2.get(i).getContractLId());
			ro.setContractHId(list2.get(i).getContractHId());
			ro.setPurchase(new BigDecimal(0));
			ro.setFeeIntas(new BigDecimal(0));
			ro.setPurchase(new BigDecimal(0));
			ro.setSumMoney("0");
			ro.setTtaValue("0");
			ro.setFeeNotax(new BigDecimal(0));
			ro.setOiType(list2.get(i).getOiType());
			ro.setVendorCode(list2.get(i).getVendorCode());
			ro.setProposalYear((Integer.parseInt(list2.get(i).getProposalYear())-1)+"");
			ro.setUnit(list2.get(i).getUnit());
			ro.setSales(new BigDecimal(0));
			ro.setTermsEn(list2.get(i).getTermsEn());
			ro.setDeliveryGranary("0");
			ro.setProposalId(0);
			list1.add(ro);
		}
		return list1;
	}

	//去除空格，中文符号转英文符号
	public List<TtaContractLineEntity_HI_RO> changeChar(List<TtaContractLineEntity_HI_RO> contractLineList){
		for(int i=0;i<contractLineList.size();i++){
			if(contractLineList.get(i).getTermsCn()!=null){
				contractLineList.get(i).setTermsCn(contractLineList.get(i).getTermsCn().trim().replace("（","(").replace("）",")"));
			}

			contractLineList.get(i).setPurchase((contractLineList.get(i).getPurchase()==null?new BigDecimal(0):contractLineList.get(i).getPurchase()));
			contractLineList.get(i).setSales((contractLineList.get(i).getSales()==null?new BigDecimal(0):contractLineList.get(i).getSales()));
			contractLineList.get(i).setFeeIntas((contractLineList.get(i).getFeeIntas()==null?new BigDecimal(0):contractLineList.get(i).getFeeIntas()));
			contractLineList.get(i).setFeeNotax((contractLineList.get(i).getFeeNotax()==null?new BigDecimal(0):contractLineList.get(i).getFeeNotax()));
			contractLineList.get(i).setTtaValue(Math.ceil(Double.parseDouble(contractLineList.get(i).getTtaValue()==null?"0":contractLineList.get(i).getTtaValue()))+"");
			//contractLineList.get(i).setSumMoney("0");
		}
		return contractLineList;
	}

	//计算出每条contractLine的summoney值
	public void sumMoney(List<TtaContractLineEntity_HI_RO> contractLineList,String actuallyCountDate,List<TtaProposalHeaderEntity_HI> headerEntityHis){
		List<Map<String, Object>> aboiList = null;
		List<Map<String, Object>> beoiList = null;
		List<Map<String, Object>> sroiList = null;
		if(contractLineList.size() > 0 && contractLineList.get(0).getVendorCode() != null){
			//因改为取2020年的上的主供应商编号，不取2019上的供应商编号相关的费用（取当年proposal单据上的供应商，不取往年proposal单据上的供应商）
			String currentYearVendorCode = headerEntityHis.get(0).getVendorNbr();
			String oldYearProposalYear = contractLineList.get(0).getProposalYear();
			//里面关联供应商，会查出多个供应商多条OI数据
			//aboiList = ttaContractLineServer.findOIList("TTA_ABOI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
			//beoiList = ttaContractLineServer.findOIList("TTA_BEOI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
			//sroiList = ttaContractLineServer.findOIList("TTA_SROI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
			aboiList = ttaContractLineServer.findOIList("TTA_ABOI_BILL_LINE",actuallyCountDate,currentYearVendorCode,oldYearProposalYear);
			beoiList = ttaContractLineServer.findOIList("TTA_BEOI_BILL_LINE",actuallyCountDate,currentYearVendorCode,oldYearProposalYear);
			sroiList = ttaContractLineServer.findOIList("TTA_SROI_BILL_LINE",actuallyCountDate,currentYearVendorCode,oldYearProposalYear);
		}
		//OIlist同一供应商存在多行，数字字段统计累加为一行
		Map<String, Object> aboiMap = sumOiDataLine(aboiList);
		Map<String, Object> beoiMap = sumOiDataLine(beoiList);
		Map<String, Object> sroiMap = sumOiDataLine(sroiList);
		System.out.println("aboiMap:" + JSONObject.toJSONString(aboiMap));
		System.out.println("beoiMap:" + JSONObject.toJSONString(beoiMap));
		System.out.println("sroiMap:" + JSONObject.toJSONString(sroiMap));
		List<Map<String, Object>> list = ttaContractLineServer.findContractLine(null);
		for(int i=0;i<contractLineList.size();i++){
			String proposalId =  contractLineList.get(i).getProposalId()+"";
			//如果为0则此条款为空
			if(proposalId.equals("0")||contractLineList.get(i).getProposalId()==null){
				continue;
			}
			Double sumMoney = new Double(0);
			String tableName = null;
			for(int m=0;m<list.size();m++){
				tableName = list.get(m).get("TABLE_NAME").toString();
				Object cell = null;
				String str =list.get(m).get("Y_TERMS_SOURCE").toString();
				String str2= contractLineList.get(i).getTermsCn();
				//判断不同的OI表来源，再获取数据
				if ("TTA_ABOI_BILL_LINE".equals(tableName)) {
					//oi映射表与合同行表条款名对应上时，才做相加统计操作
					//算实际费用的时候,例如查找2019年的条款,对应TTA_TERMS_AC_COUNT(条款对应实际费用表),用得是条款名对应的关系,故因注释 hmb
					//如下的三个条款判断都改为get(i).getOiTermCn() 之前为get(i).getTermsCn()
                       /* if(contractLineList.get(i).getTermsCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                            cell = aboiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
                        } */

					if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                         /*   if("商业发展服务费".equals(list.get(m).get("Y_TERMS_SOURCE").toString())){
                                System.out.println("条款名称:" + list.get(m).get("Y_TERMS_SOURCE").toString());
                                System.out.println("字段:" + list.get(m).get("Y_TERMS_TARGET").toString());
                            }*/
						cell = aboiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
					}
				}
				if ("TTA_BEOI_BILL_LINE".equals(tableName)) {

                        /*if (contractLineList.get(i).getOiTermCn().indexOf("集中收货")>0 && list.get(m).get("Y_TERMS_SOURCE").toString().indexOf("集中收货") > 0) {
                            System.out.println("============" + contractLineList.get(i).getOiTermCn() + "========================");
                            System.out.println("TTA_TERMS_AC_COUNT表:" + list.get(m).get("Y_TERMS_SOURCE").toString());
                        }*/
					//oi映射表与合同行表条款名对应上时，才做相加统计操作
					if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
						String keystr = ((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET")).toString();
						cell = beoiMap.get(keystr);
                            /*if (list.get(m).get("Y_TERMS_SOURCE").toString().indexOf("集中收货") > 0) {
                                System.out.println(list.get(m).get("Y_TERMS_TARGET").toString());
                                System.out.println(Double.parseDouble(cell.toString()));
                            }*/
					}
				}
				if ("TTA_SROI_BILL_LINE".equals(tableName)) {
					//oi映射表与合同行表条款名对应上时，才做相加统计操作
					//System.out.println("contractLineList条款:" + contractLineList.get(i).getOiTermCn());
					//System.out.println("TTA_TERMS_AC_COUNT:" + list.get(m).get("Y_TERMS_SOURCE").toString());
					if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
						//System.out.println("TTA_OI_CONTRACT_MAPPING条款名:" + contractLineList.get(i).getOiTermCn());
						cell = sroiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
					}
				}
				if (null != cell) {
					sumMoney = sumMoney + Double.parseDouble(cell.toString())/1000;
				}

			}
			contractLineList.get(i).setSumMoney(sumMoney.toString());
		}
	}

	//计算出单独每个OITYPE的累计值，判断是数字的字段则相加，最终返回一行数据
	public Map<String, Object>  sumOiDataLine(List<Map<String, Object>> list){
		Map<String, Object> sumMap = new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = list.get(i);
			Iterator<Map.Entry<String,Object>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String,Object> entry = it.next();
				//  System.out.println(entry.getKey()+"+"+entry.getValue());
//                if(entry.getValue()!=null&&entry.getValue().toString().indexOf("-")==0&&isNumber(entry.getValue().toString().replace("-",""))){
//                    System.out.println(entry.getValue().toString()+"++++++++++++++++++++++++++++");
//                }

				if(entry.getValue()!=null&&entry.getValue().toString().indexOf("-")<1&&isNumber(entry.getValue().toString().replace("-",""))){
					if(entry.getKey().equals("BDF001_MONTH_RECON_ADJ")){
						//Double va = Double.parseDouble(entry.getValue().toString());
						System.out.println(entry.getValue().toString()+"++++++++++++++++++++++++++++");
					}

					String bb = (sumMap.get(entry.getKey())==null?0:sumMap.get(entry.getKey())).toString();
					Double val = Double.parseDouble(entry.getValue().toString());
					Double dd = Double.parseDouble(bb)+ val;
					sumMap.put(entry.getKey(),dd);
				}else{
					String bb = (sumMap.get(entry.getKey())==null?0:sumMap.get(entry.getKey())).toString();
					sumMap.put(entry.getKey(),Double.parseDouble(bb)+0);
				}

			}
		}
		return sumMap;
	}

	//判断是否数字
	public boolean isNumber(Object object){
		String str = object.toString();
		String reg = "\\d+(\\.\\d+)?";
		return str.matches(reg);
	}
	/**
	 * 计算每一条条款的实际费用
	 * @Author hmb 添加
	 * @Date 2020.1.3
	 * @param contractLineList 往年的条款
	 * @param actuallyCountDate 实际计算时间
	 *
	 */
	public void countSumMoney(List<TtaContractLineEntity_HI_RO> contractLineList,String actuallyCountDate,List<TtaProposalHeaderEntity_HI> oldProposalList,List<TtaProposalHeaderEntity_HI> headerEntityHis){
		LOGGER.info("############进入计算实际费用操作开始###################");
		if (oldProposalList == null) {
			return;
		}
		//改为取当年Proposal单据上的供应商编号
		//String vendorCode = contractLineList.get(0).getVendorCode();
		String vendorCode = headerEntityHis.get(0).getVendorNbr();
		String oldProposalYear = contractLineList.get(0).getProposalYear();
		//1.查询oi类型的表
		List<Map<String,Object>> oiTableName = ttaContractLineServer.findOiTableName(oldProposalList.get(0).getProposalYear());
		//2.存放各种Oi类型的数据
		Map<String,List<Map<String,Object>>> oiMap = new HashMap<>();
		if(contractLineList.size() > 0 && contractLineList.get(0).getVendorCode() != null){
			//里面关联供应商，会查出多个供应商多条OI数据
			for (Map<String, Object> map : oiTableName) {
				String tableName = (String)map.get("TABLE_NAME");
				List<Map<String,Object>> resultList = ttaContractLineServer.findOiResultList(tableName,actuallyCountDate,vendorCode,oldProposalYear);
				oiMap.put(tableName,resultList);
			}
		}
		//3.OIlist同一供应商存在多行，数字字段统计累加为一行
		Map<String,Map<String,Object>> accumuOneLineMap = new HashMap<>();
		Iterator<Map.Entry<String, List<Map<String, Object>>>> entryIterator = oiMap.entrySet().iterator();
		while (entryIterator.hasNext()) {
			Map.Entry<String, List<Map<String, Object>>> listEntry = entryIterator.next();
			String key = listEntry.getKey();
			List<Map<String, Object>> value = listEntry.getValue();
			Map<String, Object> objectMap = sumOiDataLine(value);
			accumuOneLineMap.put(key,objectMap);
		}
		//4.查询OI条款名对应的表和表字段
		List<Map<String, Object>> termsAcList =  ttaContractLineServer.findTermsAcCount(oldProposalYear);
		for(int i=0;i<contractLineList.size();i++){
			String proposalId =  contractLineList.get(i).getProposalId()+"";
			//如果为0则此条款为空
			if(proposalId.equals("0")||contractLineList.get(i).getProposalId()==null){
				continue;
			}
			String oiTermCn = contractLineList.get(i).getOiTermCn();//tta_contract_line 合同表的条款名
			Double sumMoney = new Double(0);
			for(int m=0;m<termsAcList.size();m++){
				String tableName = termsAcList.get(m).get("TABLE_NAME").toString();
				String yTermsSource = termsAcList.get(m).get("Y_TERMS_SOURCE").toString();
				String yTermsTarget = termsAcList.get(m).get("Y_TERMS_TARGET").toString();
				Object cell = null;
				if (accumuOneLineMap.containsKey(tableName) && oiTermCn.equals(yTermsSource)){
					Map<String, Object> stringObjectMap = accumuOneLineMap.get(tableName);
					cell = stringObjectMap.get(yTermsTarget) == null ? 0 : stringObjectMap.get(yTermsTarget);
				}
				if (null != cell) {
					sumMoney = sumMoney + Double.parseDouble(cell.toString())/1000;
				}
			}
			contractLineList.get(i).setSumMoney(sumMoney.toString());
		}
		LOGGER.info("############进入计算实际费用操作结束###################");
	}

	@Override
    public TtaAnalysisEditDataEntity_HI saveAnalysis(JSONObject paramsJSON, int userId) throws Exception {
        String pid = paramsJSON.getString("proposalid");
        //proposalId确定唯一一条数据，一次获取ID
        String sql = "select * from TTA_ANALYSIS_EDIT_DATA a where a.PROPOSALID = '"+pid+"' " ;
        List<Map<String, Object>> list =ttaContractEntity_HI.queryNamedSQLForList(sql,new HashMap<>());

        TtaAnalysisEditDataEntity_HI instance = SaafToolUtils.setEntity(TtaAnalysisEditDataEntity_HI.class, paramsJSON, ttaContractHeaderDAO_HI, userId);
        if(list.size()>0){
            instance.setId(Integer.parseInt(list.get(0).get("ID").toString()));
        }

		String remark = ttaQuestionChoiceLineServer.findQuestionEnglishRemark(Integer.parseInt(pid));
        //如果bicRemark字段为空,那么查询问卷调查的remark字段
        if (StringUtils.isEmpty(instance.getBicRemark()) || "null".equalsIgnoreCase(instance.getBicRemark())) {
			instance.setBicRemark(remark);
		}

		//保存往年analysis信息
/*		TtaProposalHeaderEntity_HI byId = ttaProposalHeaderServer.getById(pid);
		if (byId.getLastYearProposalId() != null) {
			List<TtaAnalysisEditDataEntity_HI> analysisList = ttaAnalysisEditDataServer.findByProposalID(String.valueOf(byId.getLastYearProposalId()));
			if (CollectionUtils.isNotEmpty(analysisList)) {
				analysisList.get(0).setFreegoods(instance.getOldFreeGoods());
				analysisList.get(0).setAboiList(instance.getOldAboiList());
				ttaAnalysisEditDataDAO_HI.save(analysisList);
			}
		}*/

		ttaAnalysisEditDataDAO_HI.saveOrUpdate(instance);
		return instance;
    }


	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaContractHeaderEntity_HI instance = ttaContractHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaContractHeaderDAO_HI.delete(instance);
	}


	@Override
	public TtaContractHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractHeaderEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_H_Id", "contractHId", sql, paramsMap, "=");
		return (TtaContractHeaderEntity_HI_RO)ttaContractHeaderDAO_HI_RO.get(sql,paramsMap);
	}

	@Override
	public TtaContractHeaderEntity_HI updateSubmit(JSONObject paramsJSON, int userId) throws Exception {
		//TtaBrandplnHEntity_HI instance = SaafToolUtils.setEntity(TtaBrandplnHEntity_HI.class, paramsJSON, ttaBrandplnHDAO_HI, userId);
		Integer  contractHId  = paramsJSON.getInteger("contractHId");
		TtaContractHeaderEntity_HI instance =   ttaContractHeaderDAO_HI.getById(contractHId);

//		if(!"A".equals(instance.getBillStatus())){
//			throw new IllegalArgumentException("单据状态不为制单中，不允许提交单据");
//		}
		instance.setBillStatus("C");
		ttaContractHeaderDAO_HI.saveOrUpdate(instance);

		return null;
	}

	@Override
	public TtaContractHeaderEntity_HI updatecancel(JSONObject paramsJSON, int userId) throws Exception {
		Integer  proposalId  = paramsJSON.getInteger("contractHId");
		TtaContractHeaderEntity_HI instance =   ttaContractHeaderDAO_HI.getById(proposalId);
		if(!"C".equals(instance.getBillStatus())){
			throw new IllegalArgumentException("单据状态不为已确认，不允许作废单据");
		}
		instance.setBillStatus("D");
		ttaContractHeaderDAO_HI.saveOrUpdate(instance);
		return null;
	}

	@Override
	public Map<String, Object> callCount(JSONObject paramsJSON, int userId) throws Exception {

		Integer proposalId   = paramsJSON.getInteger("contractHId");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",proposalId);
			paramsMap.put("userId",userId);
			resultMap = ttaContractHeaderDAO.callContractCount(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");

			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
				//throw new Exception(xMsg);
			};
		} catch (Exception e) {
			e.printStackTrace();
		}


		return resultMap;
	}

	/**
	 *
	 * @param currentYearProposal 当年Proposal   Proposal制作年度为当前系统年份 + 1
	 * @param oldYearProposal 往年的Proposal  Proposal制作年度为当前系统年份
	 * @param contractLineList2018 往年的条款
	 * @throws Exception
	 */
	@Override
	public void countPurchaseSalesAndFeeIntas(List<TtaProposalHeaderEntity_HI> currentYearProposal, List<TtaProposalHeaderEntity_HI> oldYearProposal, List<TtaContractLineEntity_HI_RO> contractLineList2018) throws Exception{
		LOGGER.info("############################进入计算往年TTA值，条款费用计算开始########################################");
		String proposalYear = currentYearProposal.get(0).getProposalYear();
		String vendorNbr = currentYearProposal.get(0).getVendorNbr();
		String newOrExisting = currentYearProposal.get(0).getNewOrExisting();
		String oldproposalYear = oldYearProposal.get(0).getProposalYear();//往年的Proposal制作年度

		//1.当前供应商等于原来供应商并且不等于指定供应商并且是为拆分状态,求出purchase,sales (当前供应商拆分给其他供应商)
		List<TtaSplitBrandDetailEntity_HI_RO> splitBrandDetailEntityHiRos = ttaSplitBrandDetailServer.findSplitBrandList(oldproposalYear,vendorNbr);

		//当前供应商有往年数据,计算未被拆分出去的Fcs Purchase Fcs Sales金额
		Map<String,BigDecimal> contractMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(splitBrandDetailEntityHiRos)) {
			Integer brandProposalId = splitBrandDetailEntityHiRos.get(0).getProposalId();
			String brandProposalYear = splitBrandDetailEntityHiRos.get(0).getProposalYear();
			List<TtaContractLineEntity_HI_RO> analysisLine = ttaContractLineServer.findAnalysisLine(String.valueOf(brandProposalId), brandProposalYear);
			//BigDecimal purchase = analysisLine.get(0).getPurchase();//总purchase
			//BigDecimal sales = analysisLine.get(0).getSales();//总Sales
			if (null != analysisLine && analysisLine.size() > 0) {
				//先删除contractLId为空的记录
				Iterator<TtaContractLineEntity_HI_RO> iterator = analysisLine.iterator();
				while (iterator.hasNext()) {
					TtaContractLineEntity_HI_RO next = iterator.next();
					if (null == next.getContractLId()) {
						iterator.remove();
					}
				}
			}

			for (TtaSplitBrandDetailEntity_HI_RO detailEntityHiRo : splitBrandDetailEntityHiRos) {
				BigDecimal fcsPurchase = detailEntityHiRo.getFcsPurchase() == null ? BigDecimal.ZERO:detailEntityHiRo.getFcsPurchase();
				BigDecimal fcsSales = detailEntityHiRo.getFcsSales() == null ? BigDecimal.ZERO:detailEntityHiRo.getFcsSales();
				BigDecimal rateFcssal = detailEntityHiRo.getAmoutRateFcssal() == null? BigDecimal.ZERO:detailEntityHiRo.getAmoutRateFcssal();//百分比
				BigDecimal rateFcspur = detailEntityHiRo.getAmoutRateFcspur() == null ? BigDecimal.ZERO : detailEntityHiRo.getAmoutRateFcspur();
				BigDecimal bigDecimal = null;
				for (TtaContractLineEntity_HI_RO hiRo : analysisLine) {
					//计算每一条条款的TTA值(费用预估(含税)) 累加
					if ("ABOI".equals(hiRo.getOiType()) || "SROI".equals(hiRo.getOiType())){//按Sales比例计算
						bigDecimal = BigDecimalUtils.multiply(rateFcssal,hiRo.getFeeIntas());
					} else if ("BEOI".equals(hiRo.getOiType()) || (null != hiRo.getOrderNo() && hiRo.getOrderNo().indexOf("08") > 0)) {
						//BEOI 按purchase比例计算 免费货品按purchase比例
						bigDecimal = BigDecimalUtils.multiply(rateFcspur,hiRo.getFeeIntas());
					}
					if (contractMap.containsKey(hiRo.getTermsCn())) {
						BigDecimal contractValue = contractMap.get(hiRo.getTermsCn());
						contractMap.put(hiRo.getTermsCn(),BigDecimalUtils.add(contractValue,bigDecimal));
					} else {
						contractMap.put(hiRo.getTermsCn(),bigDecimal);
					}
				}

				//累加
				BigDecimal totalFcsPurchase = contractMap.get("totalFcsPurchase") == null ? BigDecimal.ZERO:contractMap.get("totalFcsPurchase");
				BigDecimal totalFcsSales = contractMap.get("totalFcsSales") == null ? BigDecimal.ZERO:contractMap.get("totalFcsSales");
				BigDecimal addFcsPurchase = BigDecimalUtils.add(fcsPurchase, totalFcsPurchase);
				BigDecimal addFcsSales = BigDecimalUtils.add(fcsSales, totalFcsSales);
				contractMap.put("totalFcsPurchase",addFcsPurchase);
				contractMap.put("totalFcsSales",addFcsSales);
			}

		}

		//2.当前供应商不等于原来的供应商并且等于指定供应商并且是为拆分状态的拆分品牌数据 (其他供应商指定为当前供应商)
		List<TtaSplitBrandDetailEntity_HI_RO> entityHiRosList = ttaSplitBrandDetailServer.findSplitBrandByNotSourceSupplier(oldproposalYear,vendorNbr);
		Map<String,BigDecimal> newSupplierContractMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(entityHiRosList)) {
			List<TtaContractLineEntity_HI_RO> contractLineEntityHiRos = null;
			Map<String,List<TtaContractLineEntity_HI_RO>> listMap = new HashMap<>();
			for (TtaSplitBrandDetailEntity_HI_RO hi_ro : entityHiRosList) {
				String supplierCode = hi_ro.getSupplierCode();
				Integer oldProposalId = hi_ro.getProposalId();
				String oldProposalYear = hi_ro.getProposalYear();

				BigDecimal fcsPurchase = hi_ro.getFcsPurchase() == null ? BigDecimal.ZERO:hi_ro.getFcsPurchase();
				BigDecimal fcsSales = hi_ro.getFcsSales() == null ? BigDecimal.ZERO:hi_ro.getFcsSales();
				BigDecimal rateFcssal = hi_ro.getAmoutRateFcssal() == null? BigDecimal.ZERO:hi_ro.getAmoutRateFcssal();
				BigDecimal rateFcspur = hi_ro.getAmoutRateFcspur() == null ? BigDecimal.ZERO : hi_ro.getAmoutRateFcspur();

				if (!listMap.containsKey(supplierCode)) {
					contractLineEntityHiRos = ttaContractLineServer.findAnalysisLine(String.valueOf(oldProposalId), oldProposalYear);
					if (contractLineEntityHiRos != null && contractLineEntityHiRos.size() > 0) {
						Iterator<TtaContractLineEntity_HI_RO> iterator = contractLineEntityHiRos.iterator();
						while (iterator.hasNext()) {
							TtaContractLineEntity_HI_RO next = iterator.next();
							if(next.getContractLId() == null){
								iterator.remove();
							}
						}
					}
					listMap.put(supplierCode,contractLineEntityHiRos);
				} else {
					contractLineEntityHiRos = listMap.get(supplierCode);
				}

				for (TtaContractLineEntity_HI_RO entity_hi_ro : contractLineEntityHiRos) {
					BigDecimal bigDecimal = null;
					if ("ABOI".equals(entity_hi_ro.getOiType()) || "SROI".equals(entity_hi_ro.getOiType())){//按Sales比例计算
						bigDecimal = BigDecimalUtils.multiply(rateFcssal,entity_hi_ro.getFeeIntas());
					} else if ("BEOI".equals(entity_hi_ro.getOiType()) || (null != entity_hi_ro.getOrderNo() && entity_hi_ro.getOrderNo().indexOf("08") > 0)) {
						//BEOI 按purchase比例计算 免费货品按purchase比例
						bigDecimal = BigDecimalUtils.multiply(rateFcspur,entity_hi_ro.getFeeIntas());
					}

					//计算每一条条款的TTA值(费用预估(含税))
					//BigDecimal bigDecimal = BigDecimalUtils.multiply(rateFcssal,entity_hi_ro.getFeeIntas());
					if (newSupplierContractMap.containsKey(entity_hi_ro.getTermsCn())) {
						BigDecimal contractValue = newSupplierContractMap.get(entity_hi_ro.getTermsCn());
						newSupplierContractMap.put(entity_hi_ro.getTermsCn(),BigDecimalUtils.add(contractValue,bigDecimal));
					} else {
						newSupplierContractMap.put(entity_hi_ro.getTermsCn(),bigDecimal);
					}
				}

				BigDecimal totalFcsPurchase = newSupplierContractMap.get("totalFcsPurchase") == null?BigDecimal.ZERO:newSupplierContractMap.get("totalFcsPurchase");
				BigDecimal totalFcsSales = newSupplierContractMap.get("totalFcsSales") == null?BigDecimal.ZERO:newSupplierContractMap.get("totalFcsSales");
				BigDecimal addFcsPurchase = BigDecimalUtils.add(fcsPurchase, totalFcsPurchase);
				BigDecimal addFcsSales = BigDecimalUtils.add(fcsSales, totalFcsSales);
				newSupplierContractMap.put("totalFcsPurchase",addFcsPurchase);
				newSupplierContractMap.put("totalFcsSales",addFcsSales);
			}
		}

		//3.设置当前供应商的TTA值和OI类型为ABOI,BEOI,SROI的条款的预估费用值
		for (TtaContractLineEntity_HI_RO contractLineEntityHiRo : contractLineList2018) {
			BigDecimal purchase = dealNull(contractLineEntityHiRo.getPurchase());
			BigDecimal sales = dealNull(contractLineEntityHiRo.getSales());
			String termsCn = contractLineEntityHiRo.getTermsCn();
			BigDecimal feeIntas = contractLineEntityHiRo.getFeeIntas();

			if (contractMap.size() > 0) {
				BigDecimal totalPurchase =contractMap.get("totalFcsPurchase") == null ? BigDecimal.ZERO : contractMap.get("totalFcsPurchase");
				BigDecimal totalFcsSales =  contractMap.get("totalFcsSales") == null ? BigDecimal.ZERO : contractMap.get("totalFcsSales");
				//减去(当前供应商指定给其他供应商的值purchase,Sales)
				purchase = purchase.subtract(totalPurchase);
				sales = sales.subtract(totalFcsSales);
				if (contractMap.containsKey(termsCn)) {
					BigDecimal contractFeeIntas = contractMap.get(termsCn) == null ? BigDecimal.ZERO:contractMap.get(termsCn);
					feeIntas = feeIntas.subtract(contractFeeIntas);
				}

			}

			if (newSupplierContractMap.size() > 0) {
				BigDecimal newTotalPurchase =newSupplierContractMap.get("totalFcsPurchase") == null ? BigDecimal.ZERO : newSupplierContractMap.get("totalFcsPurchase");
				BigDecimal newTotalFcsSales =  newSupplierContractMap.get("totalFcsSales") == null ? BigDecimal.ZERO : newSupplierContractMap.get("totalFcsSales");
				//加上(其他供应商指定给当前供应商的purchase,Sales)
				purchase = purchase.add(newTotalPurchase);
				sales = sales.add(newTotalFcsSales);

				if (newSupplierContractMap.containsKey(termsCn)) {
					//加上(其他供应商指定给当前供应商的purchase,Sales)
					BigDecimal newContractMap = newSupplierContractMap.get(termsCn) == null ? BigDecimal.ZERO:newSupplierContractMap.get(termsCn);
					feeIntas = feeIntas.add(newContractMap);
				}
			}

			contractLineEntityHiRo.setPurchase(purchase.abs());
			contractLineEntityHiRo.setSales(sales.abs());
			contractLineEntityHiRo.setFeeIntas(feeIntas.abs());
		}
		LOGGER.info("############################进入计算往年TTA值，条款费用计算结束########################################");
	}

	/**
	 * 处理null
	 * @param value
	 * @return
	 */
	public BigDecimal dealNull(BigDecimal value){
		BigDecimal returnValue =  value == null ? BigDecimal.ZERO : value;
		return returnValue;
	}

}
