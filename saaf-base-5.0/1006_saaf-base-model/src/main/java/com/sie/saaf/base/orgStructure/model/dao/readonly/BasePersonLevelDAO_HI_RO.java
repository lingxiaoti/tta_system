package com.sie.saaf.base.orgStructure.model.dao.readonly;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonLevelEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("basePersonLevelDAO_HI_RO")
public class BasePersonLevelDAO_HI_RO extends DynamicViewObjectImpl<BasePersonLevelEntity_HI_RO> {
    public BasePersonLevelDAO_HI_RO() {
        super();
    }
}
