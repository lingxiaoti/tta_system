package com.sie.watsons.base.pon.itscoring.services;

import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringDetailEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.inter.IEquPonItScoringDetail;

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
@RequestMapping("/equPonItScoringDetailService")
public class EquPonItScoringDetailService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringDetailService.class);

	@Autowired
	private IEquPonItScoringDetail equPonItScoringDetailServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonItScoringDetailServer;
	}

	/**
	 * 评分单据打分分数查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItScoringDetail")
	public String findItScoringDetail(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPonItScoringDetailServer.findScoringDetail(paramsJONS);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 评分单据打分分数保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveItScoringDetail")
	public String saveItScoringDetail(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPonItScoringDetailEntity_HI instance = equPonItScoringDetailServer.saveScoringDetail(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}
}