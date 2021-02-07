package com.sie.watsons.base.rule.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.rule.model.dao.readonly.TtaBaseRuleHeaderDAO_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.rule.model.entities.TtaBaseRuleHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.rule.model.inter.ITtaBaseRuleHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaBaseRuleHeaderServer")
public class TtaBaseRuleHeaderServer extends BaseCommonServer<TtaBaseRuleHeaderEntity_HI> implements ITtaBaseRuleHeader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleHeaderServer.class);

    @Autowired
    private ViewObject<TtaBaseRuleHeaderEntity_HI> ttaBaseRuleHeaderDAO_HI;

    @Autowired
    private TtaBaseRuleHeaderDAO_HI_RO ttaBaseRuleHeaderDAOHiRo;

    public TtaBaseRuleHeaderServer() {
        super();
    }

    @Override
    public Pagination<TtaBaseRuleHeaderEntity_HI_RO> findCheckRulePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        StringBuffer  buffer = new StringBuffer();
        buffer.append(TtaBaseRuleHeaderEntity_HI_RO.QUERY_CHOICE_HEADER_SQL);
        SaafToolUtils.parperParam(jsonObject, " b.choice_cn_content", "choiceCnContent", buffer, queryMap, "fulllike");
        SaafToolUtils.changeQuerySort(jsonObject, buffer,  " a.serial_number, b.serial_number ", false);
        return ttaBaseRuleHeaderDAOHiRo.findPagination(buffer, queryMap, pageIndex, pageRows);
    }

    @Override
    public Pagination<TtaBaseRuleHeaderEntity_HI_RO> findCheckChildRulePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("ruleId", jsonObject.getInteger("ruleId"));
        StringBuffer  buffer = new StringBuffer();
        buffer.append(TtaBaseRuleHeaderEntity_HI_RO.QUERY_CHILD_LINE_SQL);
        SaafToolUtils.parperParam(jsonObject, " a.project_cn_title", "projectCnTitle", buffer, queryMap, "fulllike");
        SaafToolUtils.changeQuerySort(jsonObject, buffer,  " a.serial_number ", false);
        return ttaBaseRuleHeaderDAOHiRo.findPagination(buffer, queryMap, pageIndex, pageRows);

    }

    @Override
    public void saveCheckRuleList(List<TtaBaseRuleHeaderEntity_HI> entityHis) {
        ttaBaseRuleHeaderDAO_HI.saveOrUpdateAll(entityHis);
    }

    @Override
    public Pagination<TtaBaseRuleHeaderEntity_HI_RO> findRuleHeaderPagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        StringBuffer  buffer = new StringBuffer();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        buffer.append(TtaBaseRuleHeaderEntity_HI_RO.QUERY_HEADER_SQL);
        SaafToolUtils.changeQuerySort(jsonObject, buffer,  " b.serial_number ", false);
        return ttaBaseRuleHeaderDAOHiRo.findPagination(buffer, queryMap, pageIndex, pageRows);
    }
}
