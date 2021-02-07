package com.sie.saaf.base.orgStructure.model.dao.readonly;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonAssignEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("basePersonAssignDAO_HI_RO")
public class BasePersonAssignDAO_HI_RO extends DynamicViewObjectImpl<BasePersonAssignEntity_HI_RO> {
    public BasePersonAssignDAO_HI_RO() {
        super();
    }
}
