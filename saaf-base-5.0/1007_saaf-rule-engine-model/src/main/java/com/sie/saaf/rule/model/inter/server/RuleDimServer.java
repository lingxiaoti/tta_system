package com.sie.saaf.rule.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.rule.model.dao.RuleDimDAO_HI;
import com.sie.saaf.rule.model.dao.RuleExpressionDAO_HI;
import com.sie.saaf.rule.model.dao.RuleExpressiondimDAO_HI;
import com.sie.saaf.rule.model.dao.readonly.RuleDimDAO_HI_RO;
import com.sie.saaf.rule.model.entities.RuleDimEntity_HI;
import com.sie.saaf.rule.model.entities.RuleExpressionEntity_HI;
import com.sie.saaf.rule.model.entities.RuleExpressiondimEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleDimEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleDim;
import com.sie.saaf.rule.utils.Util;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("ruleDimServer")
public class RuleDimServer extends BaseCommonServer<RuleDimEntity_HI> implements IRuleDim {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleDimServer.class);

    @Autowired
    private RuleDimDAO_HI ruleDimDao;
    @Autowired
    private RuleDimDAO_HI_RO ruleDimRoDao;
    @Autowired
    private RuleExpressiondimDAO_HI ruleExpressiondimDAO_HI;
    @Autowired
    private RuleExpressionDAO_HI ruleExpressionDAO_HI;

    @Override
    public RuleDimEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RuleDimEntity_HI instance = Util.setEntity(new RuleDimEntity_HI(), parameter, ruleDimDao, parameter.get("ruleDimId"), userId);
        if (StringUtils.isBlank(parameter.getString("effectEndDate")))
            instance.setEffectEndDate(null);
        if (instance.getRuleDimId() != null) {
            //修改时同步修改表达式维度
            Map<String, Object> map = new HashMap<>();
            map.put("ruleBusinessLineCode", instance.getRuleBusinessLineCode());
            map.put("ruleDimCode", instance.getRuleDimCode());
            List<RuleExpressiondimEntity_HI> list = ruleExpressiondimDAO_HI.findByProperty(map);
            if (list.size() > 0 && Objects.equals(instance.getRuleDimCode(), instance.getRuleDimCode())) {
                list.get(0).setRuleDimCode(instance.getRuleDimCode());
                list.get(0).setRuleDimName(instance.getRuleDimName());
                list.get(0).setOperatorUserId(parameter.getIntValue("operatorUserId"));
                ruleExpressiondimDAO_HI.update(list.get(0));
            }
        } else {
            //新增设置占位符
            List<RuleDimEntity_HI> list = ruleDimDao.findList("from RuleDimEntity_HI where ruleBusinessLineCode=? order by placeholder desc", instance.getRuleBusinessLineCode());
            if (list.size() == 0) {
                instance.setPlaceholder(createPlaceholder(null));
            } else {
                instance.setPlaceholder(createPlaceholder(list.get(0).getPlaceholder()));
            }
        }
        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        ruleDimDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        RuleDimEntity_HI instance = ruleDimDao.getById(id);
        if (instance == null)
            return false;
        Map<String, Object> map = new HashMap<>();
        map.put("ruleBusinessLineCode", instance.getRuleBusinessLineCode());
        map.put("ruleDimCode", instance.getRuleDimCode());
        List<RuleExpressiondimEntity_HI> list = ruleExpressiondimDAO_HI.findByProperty(map);
        for (RuleExpressiondimEntity_HI obj : list) {
            List<RuleExpressionEntity_HI> expressions = ruleExpressionDAO_HI.findByProperty("ruleExpCode", obj.getRuleExpCode());
            if (expressions.size() == 0)
                continue;
            if (expressions.get(0).getRuleSimpleExp() != null) {
                String exp = expressions.get(0).getRuleSimpleExp().toLowerCase();
                exp = exp.replace("and " + instance.getPlaceholder(), "");
                exp = exp.replace(instance.getPlaceholder(), "");
                expressions.get(0).setRuleSimpleExp(exp);
                ruleExpressionDAO_HI.update(expressions.get(0));
            }
            ruleExpressiondimDAO_HI.delete(obj);
        }
        ruleDimDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<RuleDimEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(ruleDimRoDao, RuleDimEntity_HI_RO.query, params, nowPage, pageSize, "order by rd.RULE_DIM_ID");
    }

    @Override
    public List<RuleDimEntity_HI> findByProperty(String name, Object value) {
        return ruleDimDao.findByProperty(name, value);
    }

    @Override
    public List<RuleDimEntity_HI> findByProperty(Map<String, Object> map) {
        return ruleDimDao.findByProperty(map);
    }

    @Override
    public RuleDimEntity_HI findById(Integer id) {
        return (RuleDimEntity_HI) ruleDimDao.getById(id);
    }


    public String findRuleDimInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<RuleDimEntity_HI> findListResult = ruleDimDao.findList("from RuleDimEntity_HI where rule_business_line_code = :businessLineCode", queryParamMap);
        //String resultData = JSON.toJSONString(findListResult);
        JSONObject resultStr = SToolUtils.convertResultJSONObj("S", "", findListResult.size(), findListResult);
        return resultStr.toString();
    }

    public String saveRuleDimInfo(JSONObject queryParamJSON) {
        RuleDimEntity_HI ruleDimEntity_HI = JSON.parseObject(queryParamJSON.toString(), RuleDimEntity_HI.class);
        Object resultData = ruleDimDao.save(ruleDimEntity_HI);
        JSONObject resultStr = SToolUtils.convertResultJSONObj("S", "", 1, resultData);
        return resultStr.toString();
    }


    private String createPlaceholder(String placeholder) {
        if (StringUtils.isBlank(placeholder))
            return "#aa#";
        placeholder = placeholder.replace("#", "");
        int n = Integer.valueOf(placeholder, 36);
        placeholder = Integer.toString(++n, 36);
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(placeholder);
        while (m.matches()) {
            placeholder = Integer.toString(++n, 36);
            m = p.matcher(placeholder);
        }
        return "#" + placeholder + "#";
    }

}
