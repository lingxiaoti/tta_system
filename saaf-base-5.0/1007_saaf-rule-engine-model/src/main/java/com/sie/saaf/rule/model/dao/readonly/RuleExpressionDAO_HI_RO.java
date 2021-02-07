package com.sie.saaf.rule.model.dao.readonly;



import com.sie.saaf.rule.model.entities.readonly.RuleExpressionEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("ruleExpressionDAO_HI_RO")
public class RuleExpressionDAO_HI_RO extends DynamicViewObjectImpl<RuleExpressionEntity_HI_RO> {
    public RuleExpressionDAO_HI_RO() {
        super();
    }

}
