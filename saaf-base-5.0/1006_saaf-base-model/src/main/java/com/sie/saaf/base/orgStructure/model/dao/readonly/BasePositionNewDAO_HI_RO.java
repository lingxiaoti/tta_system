package com.sie.saaf.base.orgStructure.model.dao.readonly;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePositionNewEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("basePositionNewDAO_HI_RO")
public class BasePositionNewDAO_HI_RO extends DynamicViewObjectImpl<BasePositionNewEntity_HI_RO> {
    public BasePositionNewDAO_HI_RO() {
        super();
    }
}
