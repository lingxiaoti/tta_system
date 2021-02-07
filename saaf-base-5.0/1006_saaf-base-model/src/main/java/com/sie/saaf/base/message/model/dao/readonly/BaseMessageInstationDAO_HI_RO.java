package com.sie.saaf.base.message.model.dao.readonly;

import com.sie.saaf.base.message.model.entities.readonly.BaseMessageInstationEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseMessageInstationDAO_HI_RO")
public class BaseMessageInstationDAO_HI_RO extends DynamicViewObjectImpl<BaseMessageInstationEntity_HI_RO> {

    public BaseMessageInstationDAO_HI_RO() {
        super();
    }

}
