package com.sie.watsons.base.rule.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.rule.model.dao.readonly.TtaBaseRuleLineDAO_HI_RO;
import com.sie.watsons.base.rule.model.entities.TtaBaseRuleLineEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleLineEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.ITtaBaseRuleLine;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ttaBaseRuleLineServer")
public class TtaBaseRuleLineServer extends BaseCommonServer<TtaBaseRuleLineEntity_HI> implements ITtaBaseRuleLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleLineServer.class);

    @Autowired
    private ViewObject<TtaBaseRuleLineEntity_HI> ttaBaseRuleLineDAO_HI;

    @Autowired
    private TtaBaseRuleLineDAO_HI_RO ttaBaseRuleLineDAOHiRo;

    public TtaBaseRuleLineServer() {
        super();
    }

    @Override
    public Pagination<TtaBaseRuleLineEntity_HI_RO> findRuleLinePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(TtaBaseRuleLineEntity_HI_RO.QUERY_LINE_HEADER_ID);
        SaafToolUtils.parperParam(queryParamJSON, "a.rule_id", "ruleId", sql, queryMap, "=", false);
        return ttaBaseRuleLineDAOHiRo.findPagination(sql, queryMap, pageIndex, pageRows);
    }

    @Override
    public void saveQuestionRuleLine(List<TtaBaseRuleLineEntity_HI> list) {
        ttaBaseRuleLineDAO_HI.saveOrUpdateAll(list);
    }
}
