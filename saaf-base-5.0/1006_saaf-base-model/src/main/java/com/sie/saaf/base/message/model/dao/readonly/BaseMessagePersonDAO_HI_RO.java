package com.sie.saaf.base.message.model.dao.readonly;

import com.sie.saaf.base.message.model.entities.readonly.BaseMessagePersonEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseMessagePersonDAO_HI_RO")
public class BaseMessagePersonDAO_HI_RO extends DynamicViewObjectImpl<BaseMessagePersonEntity_HI_RO> {

    public BaseMessagePersonDAO_HI_RO() {
        super();
    }

}
