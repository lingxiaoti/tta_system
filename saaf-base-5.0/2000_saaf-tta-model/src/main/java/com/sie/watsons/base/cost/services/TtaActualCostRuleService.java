package com.sie.watsons.base.cost.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.cost.model.entities.TtaActualCostRuleEntity_HI;
import com.sie.watsons.base.cost.model.entities.readonly.TtaActualCostRuleEntity_HI_RO;
import com.sie.watsons.base.cost.model.inter.ITtaActualCostRule;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

@RestController
@RequestMapping("/ttaActualCostRuleService")
public class TtaActualCostRuleService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaActualCostRuleService.class);

	@Autowired
	private ITtaActualCostRule ttaActualCostRuleServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaActualCostRuleServer;
	}


	@RequestMapping(method = RequestMethod.POST, value = "findCostPagination")
	public String findCostPagination(@RequestParam(required = false) String params,
									 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
									 @RequestParam(required = false, defaultValue = "10") Integer pageRows) throws Exception {
		String result = null;
		try {
			JSONObject queryParamJSON = JSONObject.parseObject(params);
			queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,  "scenario", "oiBucket", "isAppointDetail", "status");
			Pagination<TtaActualCostRuleEntity_HI_RO> costRulePagination = ttaActualCostRuleServer.findCostRulePagination(queryParamJSON, pageIndex, pageRows);
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, costRulePagination);
		} catch (Exception e) {
			LOGGER.error(".findCostPagination:{}" , e);
			result = this.convertResultJSONObj(ERROR_STATUS, "查询失败", null);
		}
		LOGGER.info(".findCostPagination 入参信息:{},出参信息:{}", new Object[]{params, result});
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateCostRule")
	public String saveOrUpdateCostRule(@RequestParam(required = false) String params) {
		String result = null;
		try {
			JSONObject jsonObject = this.parseObject(params);
			TtaActualCostRuleEntity_HI entity_hi = JSON.parseObject(jsonObject.toJSONString(), TtaActualCostRuleEntity_HI.class);
			List<TtaActualCostRuleEntity_HI> allocationBaseList = ttaActualCostRuleServer.findList(new JSONObject().fluentPut("allocationBase", entity_hi.getAllocationBase()));
			if (allocationBaseList != null && !allocationBaseList.isEmpty()) {
				if (!allocationBaseList.get(0).getId().equals(entity_hi.getId())) {
					return this.convertResultJSONObj(ERROR_STATUS, "接口名已存在，不可重复添加", null);
				}
			} else {
				ttaActualCostRuleServer.saveOrUpdateCostRule(entity_hi);
			}
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, entity_hi);
		} catch (Exception e) {
			LOGGER.error(".saveOrUpdateCostRule{}" , e);
			result = this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
		}
		LOGGER.info(".saveOrUpdateCostRule 入参信息:{},出参信息:{}", new Object[]{params, result});
		return result;
	}


	@RequestMapping(method = RequestMethod.POST, value = "deleteById")
	public String deleteById(@RequestParam(required = false) String params) {
		String result = null;
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer id = jsonObject.getInteger("id");
			Assert.notNull(id, "参数错误");
			ttaActualCostRuleServer.deleteById(id);
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
		} catch (Exception e) {
			LOGGER.error(".deleteById:{}" , e);
			result = this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
		}
		LOGGER.info(".deleteById 入参信息:{},出参信息:{}", new Object[]{params, result});
		return result;

	}

}