package com.sie.saaf.rule.model.dao.readonly;


import com.sie.saaf.rule.model.entities.readonly.RuleBusinessLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("ruleBusinessLineDAO_HI_RO")
public class RuleBusinessLineDAO_HI_RO extends DynamicViewObjectImpl<RuleBusinessLineEntity_HI_RO> {
    public RuleBusinessLineDAO_HI_RO() {
        super();
    }

}
