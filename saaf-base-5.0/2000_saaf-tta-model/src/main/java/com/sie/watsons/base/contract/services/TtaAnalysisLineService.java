package com.sie.watsons.base.contract.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.contract.model.entities.TtaAnalysisLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaAnalysisLineEntity_HI_RO;
import com.sie.watsons.base.contract.model.inter.ITtaAnalysisLine;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaAnalysisLineService")
public class TtaAnalysisLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAnalysisLineService.class);

	@Autowired
	private ITtaAnalysisLine ttaAnalysisLineServer;
	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaAnalysisLineServer;
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "reloadAnalysisSave")
	public String reloadAnalysisSave(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = this.parseObject(params);
			List<TtaAnalysisLineEntity_HI> instance = ttaAnalysisLineServer.saveReloadAnalysis(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findAnalysisData")
	public String findAnalysisData(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = this.parseObject(params);
			List<TtaAnalysisLineEntity_HI_RO> instance = ttaAnalysisLineServer.findAnalysisData(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}