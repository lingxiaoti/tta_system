package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.rule.model.entities.RuleActionInstanceEntity_HI;
import com.sie.saaf.rule.model.entities.RuleExpressionEntity_HI;
import com.sie.saaf.rule.model.entities.RuleExpressiondimEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressionEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleExpression;
import com.sie.saaf.rule.utils.Util;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ruleExpressionServer")
public class RuleExpressionServer extends BaseCommonServer<RuleExpressionEntity_HI> implements IRuleExpression {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExpressionServer.class);
    private ViewObject ruleExpressionDao;
    private BaseViewObject ruleExpressionRoDao;

    @Autowired
    private ViewObject<RuleExpressionEntity_HI> ruleExpressionDao_HI;

    @Autowired
    private BaseViewObject<RuleExpressionEntity_HI_RO> ruleExpressionDao_HI_RO;

    @Autowired
    private ViewObject<RuleExpressiondimEntity_HI> ruleExpressiondimDao_HI;

    @Autowired
    private ViewObject<RuleActionInstanceEntity_HI> ruleActionInstanceDao_HI;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Resource(name = "ruleExpressionDAO_HI")
    public void setRuleExpressionDao(ViewObject ruleExpressionDao) {
        this.ruleExpressionDao = ruleExpressionDao;
    }

    @Resource(name = "ruleExpressionDAO_HI_RO")
    public void setRuleExpressionDao(BaseViewObject ruleExpressionRoDao) {
        this.ruleExpressionRoDao = ruleExpressionRoDao;
    }

    @Override
    public RuleExpressionEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                                     IllegalAccessException {
        RuleExpressionEntity_HI instance = Util.setEntity(new RuleExpressionEntity_HI(), parameter, ruleExpressionDao, parameter.get("ruleExpId"), userId);
        if (StringUtils.isBlank(instance.getRuleExpCode())) {
            String seq = instance.getRuleBusinessLineCode() + "_exp_";
            instance.setRuleExpCode(generateCodeServer.generateCode(seq,2));
        }
        if (StringUtils.isBlank(parameter.getString("effectEndDate")))
            instance.setEffectEndDate(null);
        if (instance.getEffectDate() == null)
            instance.setEffectDate(new Date());

        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        ruleExpressionDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        RuleExpressionEntity_HI instance = (RuleExpressionEntity_HI)ruleExpressionDao.getById(id);
        if (instance == null){
            return false;
        }
        //需要删除维度实例及服务实例
        Map<String,Object> conditionMap = new HashMap<>();
        conditionMap.put("ruleExpCode",instance.getRuleExpCode());
        List<RuleExpressiondimEntity_HI> dimensionDelList = ruleExpressiondimDao_HI.findByProperty(conditionMap);
        ruleExpressiondimDao_HI.deleteAll(dimensionDelList);
        List<RuleActionInstanceEntity_HI> actionDelList = ruleActionInstanceDao_HI.findByProperty(conditionMap);
        ruleActionInstanceDao_HI.deleteAll(actionDelList);
        ruleExpressionDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<RuleExpressionEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(ruleExpressionRoDao, RuleExpressionEntity_HI_RO.query, params, nowPage, pageSize, "order by  re.RULE_EXP_ID");
    }

    @Override
    public List<RuleExpressionEntity_HI> findByProperty(String name, Object value) {
        return ruleExpressionDao.findByProperty(name, value);
    }

    @Override
    public List<RuleExpressionEntity_HI> findByProperty(Map<String, Object> map) {
        return ruleExpressionDao.findByProperty(map);
    }

    @Override
    public RuleExpressionEntity_HI findById(Integer id) {
        return (RuleExpressionEntity_HI)ruleExpressionDao.getById(id);
    }

    @Override
    public Pagination<RuleExpressionEntity_HI_RO> findRuleExpressionPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sb = new StringBuffer(RuleExpressionEntity_HI_RO.query);
        Map<String,Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(RuleExpressionEntity_HI_RO.class,queryParamJSON,sb,paramsMap);
        StringBuffer countQueryString = SaafToolUtils.getSimpleSqlCountString(sb,"count(*)");
        Pagination findPages = this.ruleExpressionRoDao.findPagination(sb, countQueryString, paramsMap, pageIndex, pageRows);
        return findPages;
    }

    @Override
    public List<RuleExpressionEntity_HI_RO> getRuleExpressionByCondition(JSONObject conditonMap) throws Exception {
        StringBuffer sql = new StringBuffer(RuleExpressionEntity_HI_RO.QUERY_EXP_LIST);
        String ruleExpCode = conditonMap.getString("ruleExpCode");
        if(StringUtils.isNotBlank(ruleExpCode)) {
            sql.append("  and t.rule_exp_code = :ruleExpCode");
        }
        String ruleBusinessLineCode = conditonMap.getString("ruleBusinessLineCode");
        if(StringUtils.isNotBlank(ruleBusinessLineCode)) {
            sql.append("  and t.rule_business_line_code = :ruleBusinessLineCode");
        }
        String ruleBusinessPoint = conditonMap.getString("ruleBusinessPoint");
        if(StringUtils.isNotBlank(ruleBusinessPoint)) {
            sql.append("  and t.rule_business_point = :ruleBusinessPoint");
        }
        List<RuleExpressionEntity_HI_RO> expressionList = ruleExpressionDao_HI_RO.findList(sql,conditonMap);
        return expressionList;
    }

    @Override
    public boolean validateExpressionUnique(String ruleBusinessLineCode, String ruleBusinessPoint) throws Exception {
        Map<String,Object> conditonMap = new HashMap<>();
        conditonMap.put("ruleBusinessLineCode",ruleBusinessLineCode);
        conditonMap.put("ruleBusinessPoint",ruleBusinessPoint);
        List<RuleExpressionEntity_HI> expressionList = ruleExpressionDao_HI.findByProperty(conditonMap);
        return expressionList.size()==0;
    }

    @Override
    public boolean validateRuleExpWeight(Integer ruleExpWeight, String ruleBusinessLineCode) throws Exception {
        Map<String,Object> conditonMap = new HashMap<>();
        conditonMap.put("ruleBusinessLineCode",ruleBusinessLineCode);
        conditonMap.put("ruleExpWeight",ruleExpWeight);
        List<RuleExpressionEntity_HI> expressionList = ruleExpressionDao_HI.findByProperty(conditonMap);
        return expressionList.size()==0;
    }
}
