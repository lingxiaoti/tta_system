package com.sie.watsons.base.pon.itproject.services;

import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItScoringTeam;

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
@RequestMapping("/equPonItScoringTeamService")
public class EquPonItScoringTeamService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringTeamService.class);

	@Autowired
	private IEquPonItScoringTeam equPonItScoringTeamServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonItScoringTeamServer;
	}

	/**
	 * 报价管理-评分小组查询，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItScoringTeam")
	public String findItScoringTeam(@RequestParam(required = false) String params,
								  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPonItScoringTeamServer.findItScoringTeam(paramsJONS,pageIndex,pageRows);
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
	 * 报价管理-评分小组删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteItScoringTeam")
	public String deleteItScoringTeam(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonItScoringTeamServer.deleteItScoringTeam(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}
}