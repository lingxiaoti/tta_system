package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.rule.constant.Constant;
import com.sie.saaf.rule.model.dao.RuleBusinessLineDAO_HI;
import com.sie.saaf.rule.model.dao.RuleDimDAO_HI;
import com.sie.saaf.rule.model.dao.RuleExpressionDAO_HI;
import com.sie.saaf.rule.model.dao.RuleExpressiondimDAO_HI;
import com.sie.saaf.rule.model.dao.readonly.RuleBusinessLineDAO_HI_RO;
import com.sie.saaf.rule.model.entities.RuleBusinessLineEntity_HI;
import com.sie.saaf.rule.model.entities.RuleDimEntity_HI;
import com.sie.saaf.rule.model.entities.RuleExpressionEntity_HI;
import com.sie.saaf.rule.model.entities.RuleExpressiondimEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleBusinessLineEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleBusinessLine;
import com.sie.saaf.rule.utils.Util;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ruleBusinessLineServer")
public class RuleBusinessLineServer extends BaseCommonServer<RuleBusinessLineEntity_HI> implements IRuleBusinessLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleBusinessLineServer.class);

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Resource(name = "ruleBusinessLineDAO_HI")
    private RuleBusinessLineDAO_HI ruleBusinessLineDAO_HI;

    @Resource(name = "ruleDimDAO_HI")
    private RuleDimDAO_HI ruleDimDAOHi;

    @Resource(name = "ruleBusinessLineDAO_HI_RO")
    private RuleBusinessLineDAO_HI_RO ruleBusinessLineDAO_HI_RO;

    @Resource(name = "ruleExpressiondimDAO_HI")
    private RuleExpressiondimDAO_HI ruleExpressiondimDAO;

    @Resource(name = "ruleExpressionDAO_HI")
    private RuleExpressionDAO_HI ruleExpressionDAO;

    @Autowired
    private BaseViewObject<RuleBusinessLineEntity_HI_RO> ruleBusinessLineDao_HI_RO;

    public RuleBusinessLineServer() {
        super();
    }

    public String findRuleBusinessLineInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<RuleBusinessLineEntity_HI> findListResult = ruleBusinessLineDAO_HI.findList("from RuleBusinessLineEntity_HI", queryParamMap);
        //String resultData = JSON.toJSONString(findListResult);
        JSONObject resultStr = SToolUtils.convertResultJSONObj("S", "", findListResult.size(), findListResult);
        return resultStr.toString();
    }

    public String saveRuleBusinessLineInfo(JSONObject queryParamJSON) {
        RuleBusinessLineEntity_HI ruleBusinessLineEntity_HI = JSON.parseObject(queryParamJSON.toString(), RuleBusinessLineEntity_HI.class);
        ruleBusinessLineEntity_HI.setOperatorUserId(queryParamJSON.getIntValue("operatorUserId"));
        Object resultData = ruleBusinessLineDAO_HI.save(ruleBusinessLineEntity_HI);
        JSONObject resultStr = SToolUtils.convertResultJSONObj("S", "", 1, resultData);
        return resultStr.toString();
    }


    @Override
    public RuleBusinessLineEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                                       IllegalAccessException {
        RuleBusinessLineEntity_HI instance =
            Util.setEntity(new RuleBusinessLineEntity_HI(), parameter, ruleBusinessLineDAO_HI, parameter.getString("ruleBusinessLineId"), userId);
        if (StringUtils.isBlank(instance.getRuleBusinessLineCode())) {
            instance.setRuleBusinessLineCode(generateCodeServer.generateCode(Constant.BUSINESSLINE_CODE, Constant.DATEFORMAT_DATE_SEQ, 4));
        }
        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        ruleBusinessLineDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public Pagination<RuleBusinessLineEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(ruleBusinessLineDAO_HI_RO, RuleBusinessLineEntity_HI_RO.query, params, nowPage, pageSize, "order by rbl.rule_business_line_id");
    }

    @Override
    public List<RuleBusinessLineEntity_HI_RO> findRuleBusinessLineList(JSONObject parameters) throws Exception {
        StringBuffer sql = new StringBuffer(RuleBusinessLineEntity_HI_RO.QUERY_SQL);
        String ruleBusinessLineCode = parameters.getString("ruleBusinessLineCode");
        Map<String,Object> conditonMap = new HashMap<>();
        if(StringUtils.isNotBlank(ruleBusinessLineCode)) {
            sql.append("  and t.rule_business_line_code = :ruleBusinessLineCode ");
            conditonMap.put("ruleBusinessLineCode",ruleBusinessLineCode);
        }
        List<RuleBusinessLineEntity_HI_RO> list = ruleBusinessLineDao_HI_RO.findList(sql, conditonMap);
        return list;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        RuleBusinessLineEntity_HI instance = ruleBusinessLineDAO_HI.getById(id);
        if (instance == null)
            return false;
        List<RuleDimEntity_HI> list = ruleDimDAOHi.findByProperty("ruleBusinessLineCode", instance.getRuleBusinessLineCode());
        List<RuleExpressiondimEntity_HI> expressiondimList = ruleExpressiondimDAO.findByProperty("ruleBusinessLineCode", instance.getRuleBusinessLineCode());
        List<RuleExpressionEntity_HI> expressionList = ruleExpressionDAO.findByProperty("ruleBusinessLineCode", instance.getRuleBusinessLineCode());
        ruleDimDAOHi.delete(list);
        ruleExpressionDAO.delete(expressionList);
        ruleExpressiondimDAO.delete(expressiondimList);
        ruleBusinessLineDAO_HI.delete(instance);
        return true;
    }

    //规则引擎业务线分页
    @Override
    public Pagination<RuleBusinessLineEntity_HI_RO> pageRuleBusinessLine(JSONObject parameters,Integer pageIndex,Integer pageRows) throws Exception {
        StringBuffer sql = new StringBuffer(RuleBusinessLineEntity_HI_RO.QUERY_SQL);
        Map<String,Object> conditonMap = new HashMap<>();
        SaafToolUtils.parperParam(parameters,"t.rule_business_line_name","ruleBusinessLineName",sql,conditonMap,"fulllike");
        Pagination<RuleBusinessLineEntity_HI_RO> page = ruleBusinessLineDao_HI_RO.findPagination(sql, conditonMap, pageIndex, pageRows);
        return page;
    }

}
