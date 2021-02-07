package com.sie.saaf.rule.model.dao.readonly;


import com.sie.saaf.rule.model.entities.readonly.RuleMappingBusinessEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("ruleMappingBusinessDAO_HI_RO")
public class RuleMappingBusinessDAO_HI_RO extends DynamicViewObjectImpl<RuleMappingBusinessEntity_HI_RO> {
    public RuleMappingBusinessDAO_HI_RO() {
        super();
    }

}
