package com.sie.saaf.rule.model.dao;


import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.rule.model.entities.RuleBusinessLineEntity_HI;
import org.springframework.stereotype.Component;

@Component("ruleBusinessLineDAO_HI")
public class RuleBusinessLineDAO_HI extends BaseCommonDAO_HI<RuleBusinessLineEntity_HI> {
    public RuleBusinessLineDAO_HI() {
        super();
    }

}
