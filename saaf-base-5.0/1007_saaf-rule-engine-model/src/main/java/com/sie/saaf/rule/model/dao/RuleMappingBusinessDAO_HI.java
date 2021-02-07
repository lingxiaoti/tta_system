package com.sie.saaf.rule.model.dao;


import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.rule.model.entities.RuleMappingBusinessEntity_HI;
import org.springframework.stereotype.Component;

@Component("ruleMappingBusinessDAO_HI")
public class RuleMappingBusinessDAO_HI extends BaseCommonDAO_HI<RuleMappingBusinessEntity_HI> {
    public RuleMappingBusinessDAO_HI() {
        super();
    }
}
