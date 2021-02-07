package com.sie.saaf.rule.model.dao;


import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.rule.model.entities.RuleExpressionEntity_HI;
import org.springframework.stereotype.Component;

@Component("ruleExpressionDAO_HI")
public class RuleExpressionDAO_HI extends BaseCommonDAO_HI<RuleExpressionEntity_HI> {
    public RuleExpressionDAO_HI() {
        super();
    }

}
