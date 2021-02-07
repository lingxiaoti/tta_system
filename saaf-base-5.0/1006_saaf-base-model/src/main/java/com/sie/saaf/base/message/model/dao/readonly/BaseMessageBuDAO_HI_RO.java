package com.sie.saaf.base.message.model.dao.readonly;

import com.sie.saaf.base.message.model.entities.readonly.BaseMessageBuEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseMessageBuDAO_HI_RO")
public class BaseMessageBuDAO_HI_RO extends DynamicViewObjectImpl<BaseMessageBuEntity_HI_RO> {

    public BaseMessageBuDAO_HI_RO() {
        super();
    }

}
