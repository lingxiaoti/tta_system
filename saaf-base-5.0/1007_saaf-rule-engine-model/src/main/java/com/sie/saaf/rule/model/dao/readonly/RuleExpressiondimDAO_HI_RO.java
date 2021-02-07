package com.sie.saaf.rule.model.dao.readonly;


import com.sie.saaf.rule.model.entities.readonly.RuleExpressiondimEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("ruleExpressiondimDAO_HI_RO")
public class RuleExpressiondimDAO_HI_RO extends DynamicViewObjectImpl<RuleExpressiondimEntity_HI_RO> {
    public RuleExpressiondimDAO_HI_RO() {
        super();
    }

}
