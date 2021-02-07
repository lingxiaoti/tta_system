package com.sie.watsons.report.services;

import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.report.model.inter.ITtaContractLineReport;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/ttaContractLineReportService")
public class TtaContractLineReportService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractLineReportService.class);

	@Autowired
	private ITtaContractLineReport ttaContractLineReport;

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}


	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	@RequestMapping(method = RequestMethod.POST, value = "findTermsAgrement")
	public String findTermsAgrement(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer proposalId = jsonObject.getInteger("proposalId");
			TtaProposalHeaderEntity_HI proposalEntity = ttaProposalHeaderServer.getById(proposalId);
			if (proposalEntity == null || !"Y".equalsIgnoreCase(proposalEntity.getIsTermsConfirm())) {
				return SToolUtils.convertResultJSONObj("E", "请先确认TTA Terms！", 0, null).toString();
			}
			String tradeYear = DateUtil.date2Str(proposalEntity.getBeginDate(), "yyyy");
			jsonObject.put("majorDeptCode", proposalEntity.getMajorDeptCode());
			jsonObject.put("proposalYear", proposalEntity.getProposalYear());
			jsonObject.put("proposalVersion", proposalEntity.getVersionCode());
			jsonObject.put("vendorNbrValue", proposalEntity.getVendorNbr());
			JSONObject termsAgrement = ttaContractLineReport.findTermsAgrement(jsonObject);
			if ("Y".equalsIgnoreCase(proposalEntity.getIsCrossYear())) {
				termsAgrement.fluentPut("tradeYear",tradeYear + "-" + DateUtil.date2Str(proposalEntity.getEndDate(), "yyyy"));
			} else {
				tradeYear = proposalEntity.getProposalYear();
				termsAgrement.fluentPut("tradeYear", tradeYear);
			}
			termsAgrement.put("saleType", proposalEntity.getSaleType());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, termsAgrement).toString();
			//String resullt = "{\"msg\":\"操作成功\",\"data\":{\"saleWayList\":[{\"createdBy\":-1,\"creationDate\":\"2019-06-12 14:47:59\",\"deleteFlag\":0,\"description\":\"B\",\"enabledFlag\":\"Y\",\"lastUpdateDate\":\"2019-08-07 16:44:28\",\"lastUpdateLogin\":-1,\"lastUpdatedBy\":-1,\"lookupCode\":\"B01\",\"lookupType\":\"SALE_WAY\",\"lookupValuesId\":2191571,\"meaning\":\"Purchase（returnable）可退货购销\",\"orderNo\":1,\"startDateActive\":\"2019-06-12\",\"systemCode\":\"BASE\",\"versionNum\":5},{\"createdBy\":-1,\"creationDate\":\"2019-06-12 14:47:59\",\"deleteFlag\":0,\"description\":\"B\",\"enabledFlag\":\"Y\",\"lastUpdateDate\":\"2019-08-07 16:44:28\",\"lastUpdateLogin\":-1,\"lastUpdatedBy\":-1,\"lookupCode\":\"B04\",\"lookupType\":\"SALE_WAY\",\"lookupValuesId\":2191572,\"meaning\":\"Cosignment（general）寄售（常规）\",\"orderNo\":2,\"startDateActive\":\"2019-06-12\",\"systemCode\":\"BASE\",\"versionNum\":5},{\"createdBy\":-1,\"creationDate\":\"2019-06-12 14:47:59\",\"deleteFlag\":0,\"description\":\"A\",\"enabledFlag\":\"Y\",\"lastUpdateDate\":\"2019-08-07 16:44:28\",\"lastUpdateLogin\":-1,\"lastUpdatedBy\":-1,\"lookupCode\":\"A01\",\"lookupType\":\"SALE_WAY\",\"lookupValuesId\":2191573,\"meaning\":\"Cosignment Via Ware寄售（经仓）\",\"orderNo\":3,\"startDateActive\":\"2019-06-12\",\"systemCode\":\"BASE\",\"versionNum\":5},{\"createdBy\":-1,\"creationDate\":\"2019-06-12 14:47:59\",\"deleteFlag\":0,\"description\":\"B\",\"enabledFlag\":\"Y\",\"lastUpdateDate\":\"2019-08-07 16:44:28\",\"lastUpdateLogin\":-1,\"lastUpdatedBy\":-1,\"lookupCode\":\"B02\",\"lookupType\":\"SALE_WAY\",\"lookupValuesId\":2191574,\"meaning\":\"W潮购或电子商务-可退货购销\",\"orderNo\":4,\"startDateActive\":\"2019-06-12\",\"systemCode\":\"BASE\",\"versionNum\":5},{\"createdBy\":-1,\"creationDate\":\"2019-06-12 14:47:59\",\"deleteFlag\":0,\"description\":\"B\",\"enabledFlag\":\"Y\",\"lastUpdateDate\":\"2019-08-07 16:44:28\",\"lastUpdateLogin\":-1,\"lastUpdatedBy\":-1,\"lookupCode\":\"B03\",\"lookupType\":\"SALE_WAY\",\"lookupValuesId\":2191575,\"meaning\":\"W潮购或电子商务-寄售（代发）\",\"orderNo\":5,\"startDateActive\":\"2019-06-12\",\"systemCode\":\"BASE\",\"versionNum\":5},{\"createdBy\":-1,\"creationDate\":\"2019-06-12 14:48:00\",\"deleteFlag\":0,\"description\":\"A\",\"enabledFlag\":\"Y\",\"lastUpdateDate\":\"2019-08-07 16:44:28\",\"lastUpdateLogin\":-1,\"lastUpdatedBy\":-1,\"lookupCode\":\"A02\",\"lookupType\":\"SALE_WAY\",\"lookupValuesId\":2191576,\"meaning\":\"W潮购或电子商务-寄售（经仓）\",\"orderNo\":6,\"startDateActive\":\"2019-06-12\",\"systemCode\":\"BASE\",\"versionNum\":5}],\"resultList\":[{\"meaning\":\"一、折扣折让项目\",\"reportList\":[{\"termCategory\":\"1\",\"termsCn\":\"ESTIMATED (BEFORE DISCOUNT)PURCHASE/CONSIGNMENT SALES</br>全年预估（折扣前）采购额/寄售金额<b>（此并非甲方义务，仅作参考之用）</b>\",\"termsEn\":\"\",\"termsSystem\":\"15,426,403\"},{\"contractLId\":5694,\"oiType\":\"BEOI\",\"orderNo\":\"01\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"1\",\"termsCn\":\"一般购货折扣\",\"termsEn\":\"PURCHASE DISCOUNT\"},{\"termCategory\":\"1\",\"termsCn\":\"SETTLEMENT TERMS 付款期(对账收发票后的每月15号起算)\",\"termsEn\":\"\",\"termsSystem\":\"60D\"},{\"contractLId\":5691,\"oiType\":\"BEOI\",\"orderNo\":\"02\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"1\",\"termsCn\":\"(提前付款) 购货折扣\",\"termsEn\":\"EARLY PAYMENT DISCOUNT\",\"ttaValue\":\"0.00%\",\"unit\":\"%\"},{\"contractLId\":5692,\"oiType\":\"BEOI\",\"orderNo\":\"03\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"1\",\"termsCn\":\"(残损) 购货折扣\",\"termsEn\":\"DAMAGED GOODS DISCOUNT\"},{\"contractLId\":5693,\"incomeType\":\"按含税采购额\",\"oiType\":\"BEOI\",\"orderNo\":\"04\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"1\",\"termsCn\":\"(集中收货) 购货折扣\",\"termsEn\":\"DISTRIBUTION DISCOUNT\",\"termsSystem\":\"1.47%\",\"ttaValue\":\"1.47%\",\"unit\":\"%\"},{\"contractLId\":5678,\"oiType\":\"BEOI\",\"orderNo\":\"041\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"1\",\"termsCn\":\"促销折扣\",\"termsEn\":\"PROMOTION DISCOUNT\"},{\"contractLId\":5679,\"oiType\":\"BEOI\",\"orderNo\":\"042\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"1\",\"termsCn\":\"商业发展支持\",\"termsEn\":\"BUSINESS DEVELOPMENT SUPPORT\"},{\"contractLId\":5696,\"oiType\":\"BEOI\",\"orderNo\":\"06\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"1\",\"termsCn\":\"新品额外折扣 (首6个月)\",\"termsEn\":\"INTRODUCTORY OFFER ( first 6 months from product launch)\"}]},{\"headList\":[\"（折扣前）采购额\",\"原返佣比例\"],\"meaning\":\"二、目标退佣及免费货品\",\"headBodyList\":[[\"第一层\",\"82,800,000.00元\",\"0.25%\"],[\"第二层\",\"-999\",\"-999\"],[\"第三层\",\"-999\",\"-999\"],[\"第四层\",\"-999\",\"-999\"],[\"第五层\",\"-999\",\"-999\"],[\"第六层\",\"-999\",\"-999\"],[\"第七层\",\"-999\",\"-999\"],[\"第八层\",\"-999\",\"-999\"]],\"reportList\":[{\"contractLId\":5697,\"oiType\":\"BEOI\",\"orderNo\":\"08\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"2\",\"termsCn\":\"免费货品（不含税及扣除所有折扣及退佣之金额）\",\"termsEn\":\"FREE GOODS (At net cost)\"}]},{\"meaning\":\"三、屈臣氏为供应商提供服务而收取的业务服务费\",\"reportList\":[{\"contractLId\":5706,\"oiType\":\"ABOI\",\"orderNo\":\"043\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"数据分享费\",\"termsEn\":\"DATA SHARING FEE\"},{\"contractLId\":5695,\"oiType\":\"BEOI\",\"orderNo\":\"05\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"退货处理服务费\",\"termsEn\":\"RETURNED GOODS SERVICE FEE\"},{\"contractLId\":5703,\"oiType\":\"SROI\",\"orderNo\":\"09\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"节庆促销服务费（周年庆、春节、劳动节、国庆节、圣诞节）（于2/4/5/10/12月份收取）\",\"termsEn\":\"ANNIVERSARY & FESTIVE  SALES SUPPORT\"},{\"contractLId\":5698,\"feeIntas\":1000,\"feeNotax\":943.4,\"incomeType\":\"按全国\",\"oiType\":\"SROI\",\"orderNo\":\"10\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"节庆促销服务费（38妇女节）\",\"termsEn\":\"3.8 WOMEN'S DAY  PROMOTION  SUPPORT\",\"termsSystem\":\"按全国1,000元\",\"ttaValue\":\"1,000.00元\",\"unit\":\"元\"},{\"contractLId\":5701,\"oiType\":\"SROI\",\"orderNo\":\"11\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"新城市宣传推广服务费\",\"termsEn\":\"NEW CITY MARKETING FUND\"},{\"contractLId\":5699,\"feeIntas\":248000,\"feeNotax\":233962.26,\"incomeType\":\"按店铺数量\",\"oiType\":\"SROI\",\"orderNo\":\"12\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"新店宣传推广服务费\",\"termsEn\":\"NEW STORE  SUPPORT\",\"termsSystem\":\"按店铺数量500元/店\",\"ttaValue\":\"500.00元/店\",\"unit\":\"元/店\"},{\"contractLId\":5702,\"oiType\":\"SROI\",\"orderNo\":\"13\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"店铺升级推广服务费\",\"termsEn\":\"REFIT STORE SUPPORT\",\"ttaValue\":\"0.00元/店\",\"unit\":\"元/店\"},{\"contractLId\":5684,\"feeIntas\":217800,\"feeNotax\":205471.7,\"incomeType\":\"按固定金额\",\"oiType\":\"ABOI\",\"orderNo\":\"14.01\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"促销陈列服务费\",\"termsEn\":\"DISPLAY RENTAL\",\"termsSystem\":\"全年不少于217,800.00元</br>-\",\"ttaValue\":\"217,800.00元\",\"unit\":\"元\"},{\"contractLId\":5690,\"oiType\":\"ABOI\",\"orderNo\":\"15\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"专柜促销陈列服务费\",\"termsEn\":\"COUNTER RENTAL\"},{\"contractLId\":5707,\"feeIntas\":600000,\"feeNotax\":566037.74,\"incomeType\":\"按固定金额\",\"oiType\":\"ABOI\",\"orderNo\":\"16.01\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"宣传单张、宣传牌及促销用品推广服务费\",\"termsEn\":\"PROMOTION FUND (LEAFLETS、DM&FLYER)\",\"termsSystem\":\"全年不少于600,000.00元/年</br>-\",\"ttaValue\":\"600,000.00元/年\",\"unit\":\"元/年\"},{\"contractLId\":5700,\"feeIntas\":0,\"feeNotax\":0,\"incomeType\":\"按其他协定标准\",\"oiType\":\"ABOI\",\"orderNo\":\"17\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"新品种宣传推广服务费\",\"termsEn\":\"NEW PRODUCT PROMOTION FEE\",\"termsSystem\":\"独家新品按店铺数量收取30.00元/SKU/店铺</br>-非独家新品按店铺数量收取50.00元/SKU/店铺\",\"ttaValue\":\"0.00\"},{\"contractLId\":5704,\"feeIntas\":200000,\"feeNotax\":188679.25,\"incomeType\":\"按固定金额\",\"oiType\":\"ABOI\",\"orderNo\":\"18.01\",\"proposalId\":262,\"proposalYear\":\"2019\",\"status\":\"1\",\"termCategory\":\"3\",\"termsCn\":\"商业发展基金\",\"termsEn\":\"BUSINESS DEVELOPMENT FUND\",\"termsSystem\":\"全年不少于200,000.00元</br>-TMP全年不少于100,000.00元\",\"ttaValue\":\"200,000.00元\",\"unit\":\"元\"}]}]},\"count\":0,\"status\":\"S\"}";
			//return  resullt;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			String flag = queryParamJSON.getString("flag");
			Assert.notNull(flag, "参数错误");
			List<Map<String, Object>> findShopArea = ttaContractLineReport.findShopArea(flag);
			Pagination<Map<String, Object>> pagination = new Pagination<>();
			pagination.setData(findShopArea);
			pagination.setCount(findShopArea.size());
			return  convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, pagination);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
	}

}