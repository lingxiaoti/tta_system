package com.sie.saaf.base.message.model.dao;

import com.sie.saaf.base.message.model.entities.BaseMessageContentEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseMessageContentDAO_HI")
public class BaseMessageContentDAO_HI extends BaseCommonDAO_HI<BaseMessageContentEntity_HI> {

    public BaseMessageContentDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseMessageContentEntity_HI entity) {
        return super.save(entity);
    }
}
