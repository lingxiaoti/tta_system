package com.sie.saaf.rule.model.dao.readonly;


import com.sie.saaf.rule.model.entities.readonly.RuleExpressionFullViewEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("ruleExpressionFullViewDAO_HI_RO")
public class RuleExpressionFullViewDAO_HI_RO extends DynamicViewObjectImpl<RuleExpressionFullViewEntity_HI_RO> {
    public RuleExpressionFullViewDAO_HI_RO() {
        super();
    }
}
