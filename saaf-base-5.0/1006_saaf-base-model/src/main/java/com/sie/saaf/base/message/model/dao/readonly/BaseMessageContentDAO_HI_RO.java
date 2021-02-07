package com.sie.saaf.base.message.model.dao.readonly;

import com.sie.saaf.base.message.model.entities.readonly.BaseMessageContentEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseMessageContentDAO_HI_RO")
public class BaseMessageContentDAO_HI_RO extends DynamicViewObjectImpl<BaseMessageContentEntity_HI_RO> {

    public BaseMessageContentDAO_HI_RO() {
        super();
    }

}
