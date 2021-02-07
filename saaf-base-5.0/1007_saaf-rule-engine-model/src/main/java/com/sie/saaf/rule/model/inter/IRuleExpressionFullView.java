package com.sie.saaf.rule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressionFullViewEntity_HI_RO;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface IRuleExpressionFullView {

    Map<RuleExpressionFullViewEntity_HI_RO, Object> matchingExpResult(JSONObject queryParamJSON, Map<String, Object> queryBusinessParamsMap) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    String findRuleExpressionFullViewInfo(JSONObject queryParamJSON);

}
