package com.sie.saaf.base.message.model.dao;

import com.sie.saaf.base.message.model.entities.BaseMessageBuEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseMessageBuDAO_HI")
public class BaseMessageBuDAO_HI extends BaseCommonDAO_HI<BaseMessageBuEntity_HI> {

    public BaseMessageBuDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseMessageBuEntity_HI entity) {
        return super.save(entity);
    }
}
