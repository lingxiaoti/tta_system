package com.sie.saaf.business.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.inter.ITtaOiSplite;
import com.sie.saaf.business.model.inter.server.TtaObjectServer;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
@RestController
@RequestMapping("/ttaOiSplitService")
public class TtaOiSplitService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiSplitService.class);

	@Autowired
	private ITtaOiSplite iTtaOiSplite;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.iTtaOiSplite;
	}


	/**
	 * 功能描述：OI分摊 场景一：SALES占比非purchase_type计算，需要用到导入数据表：tta_oi_summary_line
	 */
	@RequestMapping("/saveOiSaleScene")
	public String saveOiSaleScene(@RequestParam("params") String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			String currentYearMonth = jsonObject.getString("currentYearMonth");
			Assert.notNull(currentYearMonth, "参数不能为空");
			iTtaOiSplite.saveOiSaleScene(currentYearMonth);
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveOiSaleScene OI分摊 场景一：SALES占比非purchase_type计算失败：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	}


	/**
	 * 功能描述：OI分摊 场景二：PO（不含退货）占比计算
	 */
	@RequestMapping("/saveOiPoScene")
	public String saveOiPoScene(@RequestParam("params") String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			String currentYearMonth = jsonObject.getString("currentYearMonth");
			Assert.notNull(currentYearMonth, "参数不能为空");
			iTtaOiSplite.saveOiPoScene(currentYearMonth);
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveOiPoScene OI分摊 场景二：PO（不含退货）占比计算：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	}


	/**
	 * 功能描述：OI分摊 场景三：PO（退货RV）占比计算
	 */
	@RequestMapping("/saveOiPoRvScene")
	public String saveOiPoRvScene(@RequestParam("params") String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			String currentYearMonth = jsonObject.getString("currentYearMonth");//入参：yyyymm
			Assert.notNull(currentYearMonth, "参数不能为空");
			iTtaOiSplite.saveOiPoRvScene(currentYearMonth);
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveOiPoRvScene 场景三：PO（退货RV）占比计算：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	}


	/**
	 * 功能描述：OI分摊 场景四：ABOI,需要用到的导入数据表有：tta_aboi_summary
	 *  tta_oi_aboi_ng_suit_scene_base_ytd（tran_date）， tta_oi_aboi_ng_suit_scene_ytd(account_month)，tta_oi_aboi_ng_suit_scene_sum(account_month) 当跑错数据时，按照年月删除数据。
	 */
	@RequestMapping("/saveFourABOI")
	public String saveFourABOI(@RequestParam("params") String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			String currentYearMonth = jsonObject.getString("currentYearMonth");//入参：yyyymm
			Assert.notNull(currentYearMonth, "参数不能为空");
			iTtaOiSplite.saveFourABOI(currentYearMonth);
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveFourABOI 场景四：ABOI：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	}


	/**
	 * 功能描述：场景五：ABOI(试用装)计算,不需要计算比值
	 * tta_oi_aboi_suit_scene_ytd， tta_oi_aboi_suit_scene_sum  当跑错数据时，按照年月删除数据。
	 */
	@RequestMapping("/saveOiAboiSuitScene")
	public String saveOiAboiSuitScene(@RequestParam("params") String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			String currentYearMonth = jsonObject.getString("currentYearMonth");//入参：yyyymm
			Assert.notNull(currentYearMonth, "参数不能为空");
			iTtaOiSplite.saveOiAboiSuitScene(currentYearMonth);
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveOiAboiSuitScene 场景五：PO（退货RV）占比计算：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	}


	/**
	 * 功能描述：场景六：OTHER OI(L&N)占比计算
	 * tta_oi_ln_scene_base_ytd,tta_oi_ln_scene_ytd,tta_oi_ln_scene_sum 当跑错数据时，按照年月删除数据。
	 */
	@RequestMapping("/saveTtaOiLnScene")
	public String saveTtaOiLnScene(@RequestParam("params") String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			String currentYearMonth = jsonObject.getString("currentYearMonth");//入参：yyyymm
			Assert.notNull(currentYearMonth, "参数不能为空");
			iTtaOiSplite.saveTtaOiLnScene(currentYearMonth);
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveTtaOiLnScene 场景六：OTHER OI(L&N)占比计算：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	}


	/**
	 * 功能描述：场景测试
	 */
	@RequestMapping("/test")
	public String test(JobExecutionContext context) {
		try {
				iTtaOiSplite.findTest();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + "：OTHER OI(L&N)占比计算：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	}

}