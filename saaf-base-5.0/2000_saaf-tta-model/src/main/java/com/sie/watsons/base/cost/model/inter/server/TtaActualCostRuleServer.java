package com.sie.watsons.base.cost.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.cost.model.dao.readonly.TtaActualCostRuleDAO_HI_RO;
import com.sie.watsons.base.cost.model.entities.readonly.TtaActualCostRuleEntity_HI_RO;
import com.sie.watsons.base.cost.model.entities.TtaActualCostRuleEntity_HI;
import com.sie.watsons.base.cost.model.inter.ITtaActualCostRule;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaActualCostRuleServer")
public class TtaActualCostRuleServer extends BaseCommonServer<TtaActualCostRuleEntity_HI> implements ITtaActualCostRule {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaActualCostRuleServer.class);

	@Autowired
	private ViewObject<TtaActualCostRuleEntity_HI> ttaActualCostRuleDAO_HI;

	@Autowired
	private TtaActualCostRuleDAO_HI_RO ttaActualCostRuleDAO_HI_RO;

	public TtaActualCostRuleServer() {
		super();
	}

	@Override
	public Pagination<TtaActualCostRuleEntity_HI_RO> findCostRulePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaActualCostRuleEntity_HI_RO.QUERY_COST_RULE);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "t.status", "status", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.scenar_io", "scenarIo", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.oi_bucket", "oiBucket", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.is_appoint_detail", "isAppointDetail", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "t.id desc", false);
		return ttaActualCostRuleDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
	}

	@Override
	public void saveOrUpdateCostRule(TtaActualCostRuleEntity_HI entityHi) {
		ttaActualCostRuleDAO_HI.saveOrUpdate(entityHi);
	}

	@Override
	public void deleteById(Integer id) {
		ttaActualCostRuleDAO_HI.delete(id);
	}
}
