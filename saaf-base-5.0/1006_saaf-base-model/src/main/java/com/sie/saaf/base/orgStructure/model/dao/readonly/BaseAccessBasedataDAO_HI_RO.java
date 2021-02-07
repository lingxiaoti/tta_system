package com.sie.saaf.base.orgStructure.model.dao.readonly;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseAccessBasedataEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseAccessBasedataDAO_HI_RO")
public class BaseAccessBasedataDAO_HI_RO extends DynamicViewObjectImpl<BaseAccessBasedataEntity_HI_RO> {
    public BaseAccessBasedataDAO_HI_RO() {
        super();
    }
}
