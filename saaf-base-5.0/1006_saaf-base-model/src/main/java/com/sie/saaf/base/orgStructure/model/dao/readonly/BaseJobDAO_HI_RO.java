package com.sie.saaf.base.orgStructure.model.dao.readonly;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseJobEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseJobDAO_HI_RO")
public class BaseJobDAO_HI_RO extends DynamicViewObjectImpl<BaseJobEntity_HI_RO> {
    public BaseJobDAO_HI_RO() {
        super();
    }
}