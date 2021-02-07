package com.sie.watsons.report.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.report.model.entities.readonly.TermsComparisionReportEntity_HI_RO;
import com.sie.watsons.report.model.inter.ITermsComparisionReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;

import java.util.List;

@RestController
@RequestMapping("/termsComparisionReport")
public class TermsComparisionReportService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TermsComparisionReportService.class);

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	@Autowired
	private ITermsComparisionReport iTermsComparisionReport;
	/**
	 * @param params
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer proposalId = jsonObject.getInteger("proposalId");
			TtaProposalHeaderEntity_HI proposalEntity = ttaProposalHeaderServer.getById(proposalId);
			if (proposalEntity == null || !"Y".equalsIgnoreCase(proposalEntity.getIsTermsConfirm())) {
				return SToolUtils.convertResultJSONObj("E", "请先确认TTA Terms！", 0, null).toString();
			}
			jsonObject.put("majorDeptCode", proposalEntity.getMajorDeptCode());
			jsonObject.put("proposalYear", proposalEntity.getProposalYear());
			JSONObject terms = iTermsComparisionReport.find(jsonObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, terms).toString();
			//String result = "{\"msg\":\"操作成功\",\"data\":{\"partList3\":[{\"currentYearFeeIntas\":\"5,123,070.94\",\"currentYearTerms\":\"按含税采购额5%\",\"incomeType\":\"按含税采购额\",\"oiType\":\"BEOI\",\"termsCn\":\"免费货品（不含税及扣除所有折扣及退佣之金额）\",\"termsEn\":\"FREE GOODS (At net cost)\"},{\"currentIncomeType\":\"按固定金额\",\"currentYearFeeIntas\":\"100,000.00\",\"currentYearTerms\":\"全年不少于100,000.00元/年\",\"currentYearValue\":\"100000\",\"oiType\":\"ABOI\",\"termsCn\":\"数据分享费\",\"termsEn\":\"DATA SHARING FEE\"},{\"currentIncomeType\":\"按实际退货金额\",\"currentYearTerms\":\"按实际退货金额4%\",\"currentYearValue\":\"4\",\"oiType\":\"BEOI\",\"termsCn\":\"退货处理服务费\",\"termsEn\":\"RETURNED GOODS SERVICE FEE\"},{\"currentIncomeType\":\"按固定金额\",\"currentYearFeeIntas\":\"1,884,000.00\",\"currentYearTerms\":\"按店铺数量100元/店铺/节庆\",\"currentYearValue\":\"100000\",\"oiType\":\"SROI\",\"termsCn\":\"节庆促销服务费（周年庆、春节、劳动节、国庆节、圣诞节）（于2/4/5/10/12月份收取）\",\"termsEn\":\"ANNIVERSARY & FESTIVE  SALES SUPPORT\"},{\"currentIncomeType\":\"按地区\",\"currentYearFeeIntas\":\"20,000.00\",\"currentYearTerms\":\"按地区5,000元/区\",\"currentYearValue\":\"5000\",\"oiType\":\"SROI\",\"termsCn\":\"节庆促销服务费（38妇女节）\",\"termsEn\":\"3.8 WOMEN'S DAY  PROMOTION  SUPPORT\"},{\"currentIncomeType\":\"按新城市数量\",\"currentYearFeeIntas\":\"3,000.00\",\"currentYearTerms\":\"按新城市数量500元/城市\",\"currentYearValue\":\"500\",\"oiType\":\"SROI\",\"termsCn\":\"新城市宣传推广服务费\",\"termsEn\":\"NEW CITY MARKETING FUND\"},{\"currentIncomeType\":\"按店铺数量\",\"currentYearFeeIntas\":\"49,600.00\",\"currentYearTerms\":\"按店铺数量100元/店\",\"currentYearValue\":\"200\",\"oiType\":\"SROI\",\"termsCn\":\"新店宣传推广服务费\",\"termsEn\":\"NEW STORE  SUPPORT\"},{\"currentIncomeType\":\"按店铺数量\",\"currentYearFeeIntas\":\"97,000.00\",\"currentYearTerms\":\"按店铺数量100元/店\",\"currentYearValue\":\"100\",\"oiType\":\"SROI\",\"termsCn\":\"店铺升级推广服务费\",\"termsEn\":\"REFIT STORE SUPPORT\"},{\"currentIncomeType\":\"按固定金额\",\"currentYearTerms\":\"</br>-New Trial-Cosmetic wall</br>-New Trial-Cosmetic studio</br>-New Trial-Derma wall</br>-促销陈列服务费\",\"currentYearValue\":\"800000\",\"oiType\":\"ABOI\",\"termsCn\":\"促销陈列服务费\",\"termsEn\":\"DISPLAY RENTAL\"},{\"currentIncomeType\":\"按年按固定金额\",\"currentYearFeeIntas\":\"100,000.00\",\"currentYearTerms\":\"按年按固定金额100,000元/年\",\"currentYearValue\":\"100000\",\"oiType\":\"ABOI\",\"termsCn\":\"专柜促销陈列服务费\",\"termsEn\":\"COUNTER RENTAL\"},{\"currentIncomeType\":\"按固定金额\",\"currentYearFeeIntas\":\"500,000.00\",\"currentYearTerms\":\"全年不少于500,000.00元/年</br>-DM\",\"currentYearValue\":\"500000\",\"oiType\":\"ABOI\",\"termsCn\":\"宣传单张、宣传牌及促销用品推广服务费\",\"termsEn\":\"PROMOTION FUND (LEAFLETS、DM&FLYER)\"},{\"currentIncomeType\":\"按其他协定标准\",\"currentYearFeeIntas\":\"1,053,600.00\",\"currentYearTerms\":\"所有新品按产品数量收取100,000.00元/SKU</br>-进口新品按店铺数量收取100.00元/SKU/店铺\",\"currentYearValue\":\"1053600\",\"oiType\":\"ABOI\",\"termsCn\":\"新品种宣传推广服务费\",\"termsEn\":\"NEW PRODUCT PROMOTION FEE\"},{\"currentIncomeType\":\"按固定金额\",\"currentYearFeeIntas\":\"1,000,000.00\",\"currentYearTerms\":\"全年不少于1,000,000.00元</br>-TMP全年不少于100,000.00元</br>-CRM全年不少于100,000.00元</br>-HWB全年不少于500,000.00元</br>-Share BA全年不少于100,000.00元/年\",\"currentYearValue\":\"1000000\",\"oiType\":\"ABOI\",\"termsCn\":\"商业发展基金\",\"termsEn\":\"BUSINESS DEVELOPMENT FUND\"}],\"partList2\":{\"termsEn\":\"INCENTIVE REBATE\",\"bodyData\":[[\"目标退佣\",\"（折扣前）采购额\",\"原返佣比例\",\"（折扣前）采购额\",\"原返佣比例\"],[\"第一层\",\"\",\"\",\"3,000,000.00元\",\"1.00%\"],[\"第二层\",\"\",\"\",\"4,000,000.00元\",\"1.25%\"],[\"第三层\",\"\",\"\",\"5,000,000.00元\",\"1.50%\"],[\"第四层\",\"\",\"\",\"6,000,000.00元\",\"2.00%\"],[\"第五层\",\"\",\"\",\"7,000,000.00元\",\"2.25%\"],[\"第六层\",\"\",\"\",\"8,000,000.00元\",\"2.50%\"],[\"第七层\",\"\",\"\",\"9,000,000.00元\",\"3.00%\"],[\"第八层\",\"\",\"\",\"10,000,000.00元\",\"3.25%\"]],\"oiType\":\"BEOI\"},\"partList1\":[{\"currentIncomeType\":\"按含税采购额\",\"currentYearFeeIntas\":\"1,813,476.44\",\"currentYearTerms\":\"2%\",\"currentYearValue\":\"2\",\"oiType\":\"BEOI\",\"termsCn\":\"一般购货折扣\",\"termsEn\":\"PURCHASE DISCOUNT\"},{\"currentIncomeType\":\"按含税采购额\",\"currentYearFeeIntas\":\"906,738.22\",\"currentYearTerms\":\"1%\",\"currentYearValue\":\"1\",\"oiType\":\"BEOI\",\"termsCn\":\"(提前付款) 购货折扣\",\"termsEn\":\"EARLY PAYMENT DISCOUNT\"},{\"currentIncomeType\":\"按含税采购额\",\"currentYearFeeIntas\":\"1,813,476.44\",\"currentYearTerms\":\"2%\",\"currentYearValue\":\"2\",\"oiType\":\"BEOI\",\"termsCn\":\"(残损) 购货折扣\",\"termsEn\":\"DAMAGED GOODS DISCOUNT\"},{\"currentIncomeType\":\"按含税采购额\",\"currentYearFeeIntas\":\"3,626,952.88\",\"currentYearTerms\":\"4%\",\"currentYearValue\":\"4\",\"oiType\":\"BEOI\",\"termsCn\":\"(集中收货) 购货折扣\",\"termsEn\":\"DISTRIBUTION DISCOUNT\"},{\"currentIncomeType\":\"按含税采购额\",\"currentYearFeeIntas\":\"2,720,214.66\",\"currentYearTerms\":\"3%\",\"currentYearValue\":\"3\",\"oiType\":\"BEOI\",\"termsCn\":\"促销折扣\",\"termsEn\":\"PROMOTION DISCOUNT\"},{\"currentIncomeType\":\"按含税采购额\",\"currentYearTerms\":\"当实际含税采购额大于90673822元时,超出部分按5%收取\",\"currentYearValue\":\"5\",\"oiType\":\"BEOI\",\"termsCn\":\"商业发展支持\",\"termsEn\":\"BUSINESS DEVELOPMENT SUPPORT\"},{\"currentIncomeType\":\"收取方式\",\"currentYearTerms\":\"5%\",\"currentYearValue\":\"5\",\"oiType\":\"BEOI\",\"termsCn\":\"新品额外折扣 (首6个月)\",\"termsEn\":\"INTRODUCTORY OFFER ( first 6 months from product launch)\"}]},\"count\":1,\"status\":\"S\"}";
			//return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常，" + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 功能描述： 检查terms是否确认
	 * @author xiaoga
	 * @date 2019/9/3
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "checkTermsIsConfirm")
	public String checkTermsIsConfirm(@RequestParam(required = false) String params) {
		String reuslt =  SToolUtils.convertResultJSONObj("E", "确认TTA Terms未确认，请先确认！", 0, null).toString();
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer proposalId = jsonObject.getInteger("proposalId");
			TtaProposalHeaderEntity_HI proposalEntity = ttaProposalHeaderServer.getById(proposalId);
			if (proposalEntity == null || !"Y".equalsIgnoreCase(proposalEntity.getIsTermsConfirm())) {
				reuslt = SToolUtils.convertResultJSONObj("S", "TTA Terms已确认！", 0, null).toString();
			}
		} catch (Exception e) {
			LOGGER.error(".checkTermsIsConfirm reuslt:{}, exception:{}", reuslt, e);
		}
		return reuslt;
	}

}