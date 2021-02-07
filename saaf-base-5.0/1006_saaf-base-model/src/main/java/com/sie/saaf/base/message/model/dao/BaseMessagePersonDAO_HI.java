package com.sie.saaf.base.message.model.dao;

import com.sie.saaf.base.message.model.entities.BaseMessagePersonEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseMessagePersonDAO_HI")
public class BaseMessagePersonDAO_HI extends BaseCommonDAO_HI<BaseMessagePersonEntity_HI> {

    public BaseMessagePersonDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseMessagePersonEntity_HI entity) {
        return super.save(entity);
    }
}
