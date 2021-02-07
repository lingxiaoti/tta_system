package com.sie.saaf.base.message.model.dao;

import com.sie.saaf.base.message.model.entities.BaseMessageInstationEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseMessageInstationDAO_HI")
public class BaseMessageInstationDAO_HI extends BaseCommonDAO_HI<BaseMessageInstationEntity_HI> {

    public BaseMessageInstationDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseMessageInstationEntity_HI entity) {
        return super.save(entity);
    }
}
